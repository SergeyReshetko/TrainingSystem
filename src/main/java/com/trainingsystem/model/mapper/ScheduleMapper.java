package com.trainingsystem.model.mapper;

import com.trainingsystem.model.dto.ScheduleDto;
import com.trainingsystem.model.entity.ScheduleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {
    
    @Mapping(source = "scheduleId", target = "scheduleId")
    @Mapping(source = "date", target = "date")
    @Mapping(source = "schedule_teachers.lastName", target = "teacherLastName")
    @Mapping(source = "schedule_teachers.firstName", target = "teacherFirstName")
    @Mapping(source = "schedule_groups.groupNumber", target = "groupNumber")
    @Mapping(source = "schedule_courses.courseName", target = "courseName")
    ScheduleDto toScheduleDto(ScheduleEntity scheduleEntity);
}