package com.trainingsystem.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "courses")
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Integer id;
    
    @Column(name = "course_name", nullable = false, length = 64, unique = true)
    private String name;
    
    @OneToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "teacher_id", nullable = false,
            unique = true, insertable = false, updatable = false)
    @JsonBackReference("teacher_id")
    private TeacherEntity teachers;
    
    @OneToMany(mappedBy = "schedule_courses", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ScheduleEntity> scheduleEntity = new ArrayList<>();
}