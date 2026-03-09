package com.trainingsystem.model.mapper;

import com.trainingsystem.model.dto.CourseDto;
import com.trainingsystem.model.entity.CourseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    
    CourseEntity toCourseEntity(CourseDto courseDto);
    
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "teacher.id", target = "teacherId")
    @Mapping(target = "teacherFullName", expression = "java(getTeacherFullName(course))")
    CourseDto toCourseDto(CourseEntity course);
    
    default String getTeacherFullName(CourseEntity courseEntity) {
        if (courseEntity.getTeacher() == null) {
            return "Teacher not assigned";
        }
        return courseEntity.getTeacher().getFirstName() + " " + courseEntity.getTeacher().getLastName();
    }
}