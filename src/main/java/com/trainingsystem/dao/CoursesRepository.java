package com.trainingsystem.dao;

import com.trainingsystem.model.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoursesRepository extends JpaRepository<CourseEntity, Integer> {
    
    @Query("""
                SELECT c FROM CourseEntity c
                            LEFT JOIN FETCH c.teacher
                            LEFT JOIN FETCH c.groups
            """)
    Optional<List<CourseEntity>> findAllWithTeacher();
    
    @Query("SELECT c FROM CourseEntity c LEFT JOIN FETCH c.groups WHERE c.id = :id")
    Optional<CourseEntity> findByIdWithGroups(@Param("id") Integer id);
}