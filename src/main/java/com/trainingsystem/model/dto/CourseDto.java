package com.trainingsystem.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@RequiredArgsConstructor
@Component
public class CourseDto {
    private Integer id;
    private String name;
    private Integer teacherId;
    private String teacherFullName;
    @JsonIgnoreProperties("courses")
    private List<GroupDto> groups;
}