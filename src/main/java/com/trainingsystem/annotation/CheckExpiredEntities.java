package com.trainingsystem.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckExpiredEntities {
    String cron() default "0 0 3 * * ?";
    
    Class<?> entityType() default Object.class;
    
    String dateField() default "date";
    
    ExpiredAction action() default ExpiredAction.DELETE;
    
    enum ExpiredAction {
        DELETE
    }
}
