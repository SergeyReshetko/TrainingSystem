package com.trainingsystem.model.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "students")
@ToString(exclude = "group")
public class StudentEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "first_name", nullable = false, length = 64)
    private String firstName;
    
    @Column(name = "last_name", nullable = false, length = 64)
    private String lastName;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "group_number", referencedColumnName = "group_number")
    private GroupEntity group;
}
