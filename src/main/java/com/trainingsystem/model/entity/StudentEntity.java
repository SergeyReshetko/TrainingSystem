package com.trainingsystem.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "students")
@ToString(exclude = "group")
public class StudentEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    Long id;
    
    @Column(name = "first_name", nullable = false, length = 64)
    String firstName;
    
    @Column(name = "last_name", nullable = false, length = 64)
    String lastName;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "group_number", referencedColumnName = "group_number")
    GroupEntity group;
}
