package com.trainingsystem.model.entity;

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
@Table(name = "student_groups")
public class GroupEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Integer groupId;
    
    @Column(name = "group_number", nullable = false, unique = true)
    private Integer groupNumber;
    
    @OneToMany(mappedBy = "studentGroups", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StudentEntity> studentEntity = new ArrayList<>();
    
    @OneToMany(mappedBy = "schedule_groups", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ScheduleEntity> scheduleEntity = new ArrayList<>();
}
