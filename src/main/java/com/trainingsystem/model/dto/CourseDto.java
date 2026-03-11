package com.trainingsystem.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class CourseDto {
    Integer id;
    String name;
    Integer teacherId;
    String teacherFullName;
    @JsonIgnoreProperties("courses")
    List<GroupDto> groups;
}