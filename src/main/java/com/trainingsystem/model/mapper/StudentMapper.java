package com.trainingsystem.model.mapper;

import com.trainingsystem.model.dto.StudentDto;
import com.trainingsystem.model.entity.StudentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface StudentMapper {
    StudentEntity toStudentEntity(StudentDto studentDto);
    
    StudentDto toStudentDto(StudentEntity studentEntity);
}