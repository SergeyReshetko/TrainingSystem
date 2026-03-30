package com.trainingsystem.model.mapper;

import com.trainingsystem.model.dto.TeacherCreateDto;
import com.trainingsystem.model.entity.TeacherEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeacherCreateMapper {
    
    TeacherEntity toTeacherEntity(TeacherCreateDto teacherCreateDto);
}
