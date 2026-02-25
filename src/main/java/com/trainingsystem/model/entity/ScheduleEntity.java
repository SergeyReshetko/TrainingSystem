package com.trainingsystem.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "schedules")
public class ScheduleEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long scheduleId;
    
    @Column(name = "date_lecture", unique = true, nullable = false)
    private LocalDate date;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", referencedColumnName = "teacher_id", unique = true, nullable = false)
    private TeacherEntity schedule_teachers;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_number", referencedColumnName = "group_number", unique = true, nullable = false)
    private GroupEntity schedule_groups;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", referencedColumnName = "course_id", unique = true, nullable = false)
    private CourseEntity schedule_courses;
}
