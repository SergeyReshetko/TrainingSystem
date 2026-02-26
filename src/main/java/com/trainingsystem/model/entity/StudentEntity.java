package com.trainingsystem.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "students")
public class StudentEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long id;
    
    @Column(name = "first_name", nullable = false, length = 64)
    private String firstName;
    
    @Column(name = "last_name", nullable = false, length = 64)
    private String lastName;
    
    @Column(name = "group_number", nullable = false, unique = true)
    private Integer groupNumber;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_number", referencedColumnName = "group_number",
            insertable = false, updatable = false)
    private GroupEntity studentGroups;
}
