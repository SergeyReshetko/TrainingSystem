package com.trainingsystem.dao;

import com.trainingsystem.model.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SchedulesRepository extends JpaRepository<ScheduleEntity, Long> {
    
    @Query("SELECT s FROM ScheduleEntity s WHERE s.date < :oneYearAgo")
    List<ScheduleEntity> findExpiringToday(@Param("oneYearAgo") LocalDate oneYearAgo );
}