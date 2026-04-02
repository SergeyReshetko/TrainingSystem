package com.trainingsystem.dao;

import com.trainingsystem.model.entity.StudentEntity;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentsRepository extends JpaRepository<StudentEntity, Long> {
    
    @EntityGraph(attributePaths = {"group"})
    @Override
    @NonNull
    Page<StudentEntity> findAll(@NonNull Pageable pageable);
    
    Optional<List<StudentEntity>> findAllByGroupGroupNumber(Integer groupNumber);
}