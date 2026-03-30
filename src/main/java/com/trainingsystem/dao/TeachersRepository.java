package com.trainingsystem.dao;

import com.trainingsystem.model.entity.TeacherEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeachersRepository extends JpaRepository<TeacherEntity, Integer> {
    
    @EntityGraph(attributePaths = {"course"})
    @Override
    @NonNull
    List<TeacherEntity> findAll();
}