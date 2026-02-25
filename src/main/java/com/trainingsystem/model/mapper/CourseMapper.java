package com.trainingsystem.model.mapper;

import com.trainingsystem.model.dto.CourseDto;
import com.trainingsystem.model.entity.CourseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CourseMapper {
    CourseDto toCourseDto(CourseEntity courseEntity);
}