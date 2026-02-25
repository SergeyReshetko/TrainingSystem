package com.trainingsystem.model.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@RequiredArgsConstructor
@Component
public class CourseDto {
    private Integer courseId;
    private String courseName;
}