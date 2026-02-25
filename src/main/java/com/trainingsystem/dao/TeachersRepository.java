package com.trainingsystem.dao;

import com.trainingsystem.model.entity.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeachersRepository extends JpaRepository<TeacherEntity, Integer> {
}