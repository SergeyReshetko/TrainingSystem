package com.trainingsystem.model.mapper;

import com.trainingsystem.model.dto.TeacherDto;
import com.trainingsystem.model.entity.TeacherEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
    TeacherEntity toTeacherEntity(TeacherDto teacherDto);
    
    TeacherDto toTeacherDto(TeacherEntity teacher);
    
}
