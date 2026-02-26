package com.trainingsystem.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "teachers")
public class TeacherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private Integer id;
    
    @Column(name = "first_name", nullable = false, length = 64)
    private String firstName;
    
    @Column(name = "last_name", nullable = false, length = 64)
    private String lastName;
    
    @OneToOne(mappedBy = "teachers", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = true)
    @JsonManagedReference("teacher_id")
    private CourseEntity course;
    
    @OneToMany(mappedBy = "schedule_teachers", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ScheduleEntity> scheduleEntity = new ArrayList<>();
}
