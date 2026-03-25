package com.trainingsystem.dao;

import com.trainingsystem.model.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface SchedulesRepository extends JpaRepository<ScheduleEntity, Long> {
    
    @Modifying
    @Query("DELETE FROM ScheduleEntity s WHERE s.startDate < :oneYearAgo")
    void deleteExpiredSchedules(@Param("oneYearAgo") LocalDateTime oneYearAgo);
}