package com.trainingsystem.model.mapper;

import com.trainingsystem.model.dto.StudentDto;
import com.trainingsystem.model.entity.StudentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentEntity toStudentEntity(StudentDto studentDto);
    
    @Mapping(source = "group", target = "group")
    @Mapping(source = "group.groupNumber", target = "groupNumber")
    StudentDto toStudentDto(StudentEntity student);
}