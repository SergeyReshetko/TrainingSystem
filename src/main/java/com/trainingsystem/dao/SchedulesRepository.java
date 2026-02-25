package com.trainingsystem.dao;

import com.trainingsystem.model.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedulesRepository extends JpaRepository<ScheduleEntity, Long> {
}