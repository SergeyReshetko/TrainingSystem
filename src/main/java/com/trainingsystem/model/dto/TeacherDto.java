package com.trainingsystem.model.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@RequiredArgsConstructor
@Component
public class TeacherDto {
    private Integer teacherId;
    private String firstName;
    private String lastName;
    private CourseDto courseDto;
}
