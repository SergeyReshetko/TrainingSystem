package com.trainingsystem.dao;

import com.trainingsystem.model.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentsRepository extends JpaRepository<StudentEntity, Long> {
    
    Optional<List<StudentEntity>> findAllByGroupGroupNumber(Integer groupNumber);
}