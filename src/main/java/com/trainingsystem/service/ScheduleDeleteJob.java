package com.trainingsystem.service;

import com.trainingsystem.dao.SchedulesRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
@Slf4j
public class ScheduleDeleteJob {
    
    private final SchedulesRepository schedulesRepository;
    
    @Transactional
    @Scheduled(cron = "${scheduling.tasks.clean-expired-entities.cron:0 0 3 * * ?}")
    public void deactivateExpiredSchedule() {
        log.info("Deactivating expired schedules");
        LocalDateTime oneYearAgo = LocalDateTime.now().minusYears(1);
        schedulesRepository.deleteExpiredSchedules(oneYearAgo);
    }
}
