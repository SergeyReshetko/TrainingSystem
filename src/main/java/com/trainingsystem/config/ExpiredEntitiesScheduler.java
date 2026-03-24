package com.trainingsystem.config;

import com.trainingsystem.annotation.CheckExpiredEntities;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Component
@EnableScheduling
@Slf4j
public class ExpiredEntitiesScheduler {
    
    private final ApplicationContext applicationContext;
    private final List<ScheduledMethodInfo> scheduledMethods = new ArrayList<>();
    
    @PostConstruct
    public void init() {
        scanForScheduledMethods();
        log.info("Found {} scheduled methods", scheduledMethods.size());
    }
    
    @Scheduled(cron = "* * 3 * * ?")
    public void performExpiredAction() {
        log.info("Scanning {} scheduled methods", scheduledMethods.size());
        
        for (ScheduledMethodInfo info : scheduledMethods) {
            if (shouldRunNow(info.cronExpression)) {
                executeCheck(info.bean, info.method, info.annotation);
            }
        }
    }
    
    private void scanForScheduledMethods() {
        Map<String, Object> beans = applicationContext.getBeansOfType(Object.class);
        for (Object bean : beans.values()) {
            
            Class<?> targetClass = AopUtils.getTargetClass(bean);
            Method[] methods = targetClass.getDeclaredMethods();
            
            for (Method method : methods) {
                if (method.isAnnotationPresent(CheckExpiredEntities.class)) {
                    log.info("Found scheduled method {}", method.getName());
                    
                    CheckExpiredEntities annotation =
                            method.getAnnotation(CheckExpiredEntities.class);
                    
                    scheduledMethods.add(new ScheduledMethodInfo(
                                    bean,
                                    method,
                                    annotation,
                                    annotation.cron()
                            )
                    );
                }
            }
        }
    }
    
    private boolean shouldRunNow(String cronExpression) {
        try {
            CronExpression cron = CronExpression.parse(cronExpression);
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime previous = cron.next(now.minusSeconds(1));
            
            if (previous == null) {
                return false;
            }
            
            long secondsBetween = ChronoUnit.SECONDS.between(previous, now);
            return secondsBetween >= 0 && secondsBetween <= 1;
        } catch (Exception e) {
            log.error("CRON parsing error {}: {}", cronExpression, e.getMessage(), e);
            return false;
        }
    }
    
    private void executeCheck(Object bean, Method method, CheckExpiredEntities annotation) {
        try {
            log.info("[*] Executing check expired entities {} method name {}",
                    bean.getClass().getSimpleName(),
                    method.getName()
            );
            log.info("\nParameters entity: {}\nField = {}\nAction = {}",
                    annotation.entityType().getSimpleName(),
                    annotation.dateField(),
                    annotation.action()
            );
            method.setAccessible(true);
            method.invoke(bean);
        } catch (InvocationTargetException e) {
            log.error("[X] Error in method {}: {}", method.getName(), e.getMessage(), e);
        } catch (IllegalAccessException e) {
            log.error("[X] System error when calling {}", e.getMessage(), e);
        }
    }
    
    private record ScheduledMethodInfo(Object bean,
                                       Method method,
                                       CheckExpiredEntities annotation,
                                       String cronExpression
    ) {
    }
}
