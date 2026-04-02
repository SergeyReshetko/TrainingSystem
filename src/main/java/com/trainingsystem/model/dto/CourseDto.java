package com.trainingsystem.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseDto {
    Integer id;
    String name;
    Integer teacherId;
    String teacherFullName;
    @JsonIgnoreProperties("courses")
    List<GroupDto> groups;
}