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
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    ScheduleDto toScheduleDto(ScheduleEntity schedule);
}