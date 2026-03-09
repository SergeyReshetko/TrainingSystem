package com.trainingsystem.model.mapper;

import com.trainingsystem.model.dto.ScheduleDto;
import com.trainingsystem.model.entity.ScheduleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CourseMapper.class, GroupMapper.class, TeacherMapper.class})
public interface ScheduleMapper {
    
    ScheduleEntity toScheduleEntity(ScheduleDto scheduleDto);
    
    @Mapping(source = "id", target = "id")
    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "group.id", target = "groupId")
    @Mapping(source = "teacher.id", target = "teacherId")
    @Mapping(source = "startTime", target = "startTime")
    @Mapping(source = "endTime", target = "endTime")
    @Mapping(source = "date", target = "date")
    ScheduleDto toScheduleDto(ScheduleEntity schedule);
}