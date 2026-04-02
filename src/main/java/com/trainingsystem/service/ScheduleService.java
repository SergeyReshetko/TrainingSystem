package com.trainingsystem.service;

import com.trainingsystem.dao.SchedulesRepository;
import com.trainingsystem.exception.GroupNotFoundException;
import com.trainingsystem.exception.ScheduleNotFoundException;
import com.trainingsystem.exception.TimeIlLegalArgumentException;
import com.trainingsystem.model.dto.ScheduleDto;
import com.trainingsystem.model.entity.CourseEntity;
import com.trainingsystem.model.entity.GroupEntity;
import com.trainingsystem.model.entity.ScheduleEntity;
import com.trainingsystem.model.mapper.ScheduleMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ScheduleService {
    
    private final SchedulesRepository schedulesRepository;
    private final ScheduleMapper scheduleMapper;
    private final CourseService courseService;
    private final GroupService groupService;
    private final TeacherService teacherService;
    
    @Transactional
    public ScheduleDto findById(Long id) {
        log.info("Getting schedule by id {}", id);
        
        var scheduleEntity = getScheduleEntity(id);
        return scheduleMapper.toScheduleDto(scheduleEntity);
    }
    
    @Transactional
    public List<ScheduleDto> findAllSchedules() {
        log.info("Getting all schedules");
        
        List<ScheduleEntity> scheduleEntities = schedulesRepository.findAll();
        return scheduleEntities.stream().map(scheduleMapper::toScheduleDto).toList();
    }
    
    @Transactional
    public List<ScheduleDto> findAllSchedulesByGroupId(Integer id) {
        log.info("Getting schedule by group id {}", id);
        
        var groupEntity = groupService.getById(id);
        return groupEntity.getSchedules().stream().map(scheduleMapper::toScheduleDto).toList();
    }
    
    @Transactional
    public List<ScheduleDto> findAllSchedulesByTeacherId(Integer id) {
        log.info("Getting schedule by teacher id {}", id);
        
        var teacherEntity = teacherService.getTeacherEntity(id);
        return teacherEntity.getSchedules().stream().map(scheduleMapper::toScheduleDto).toList();
    }
    
    @Transactional
    public ScheduleDto addSchedule(ScheduleDto scheduleDto) {
        log.info("Adding schedule {}", scheduleDto);
        
        createEndTime(scheduleDto);
        var scheduleEntity = scheduleMapper.toScheduleEntity(scheduleDto);
        var courseEntity = courseService.getCourseEntity(scheduleDto.getCourseId());
        scheduleEntity.setCourse(courseEntity);
        var groupEntity = getGroupEntityFromCourse(courseEntity, scheduleDto);
        chackTime(groupEntity, scheduleDto);
        scheduleEntity.setTeacher(courseEntity.getTeacher());
        scheduleEntity.setGroup(groupEntity);
        schedulesRepository.save(scheduleEntity);
        courseEntity.getGroups().add(groupEntity);
        groupEntity.getSchedules().add(scheduleEntity);
        courseEntity.getTeacher().getSchedules().add(scheduleEntity);
        return scheduleMapper.toScheduleDto(scheduleEntity);
    }
    
    @Transactional
    public ScheduleDto updateSchedule(ScheduleDto scheduleDto) {
        log.info("Updating schedule {}", scheduleDto);
        
        createEndTime(scheduleDto);
        var scheduleEntity = getScheduleEntity(scheduleDto.getId());
        var groupEntity = groupService.getById(scheduleDto.getGroupId());
        if (!groupEntity.getId().equals(scheduleDto.getGroupId())) {
            log.info("Updating schedule group {}", scheduleDto.getGroupId());
            throw new GroupNotFoundException("Group not found with id: "
                                                     + groupEntity.getId()
                                                     + " from input group id: " + scheduleDto.getGroupId());
        }
        chackTime(groupEntity, scheduleDto);
        scheduleEntity.setStartDate(scheduleDto.getStartDate());
        scheduleEntity.setEndDate(scheduleDto.getEndDate());
        schedulesRepository.save(scheduleEntity);
        return scheduleMapper.toScheduleDto(scheduleEntity);
    }
    
    @Transactional
    public ScheduleDto deleteSchedule(Long id) {
        log.info("Deleting schedule {}", id);
        
        var scheduleEntity = getScheduleEntity(id);
        schedulesRepository.delete(scheduleEntity);
        return scheduleMapper.toScheduleDto(scheduleEntity);
    }
    
    private ScheduleEntity getScheduleEntity(Long id) {
        return schedulesRepository.findById(id)
                       .orElseThrow(() -> new ScheduleNotFoundException(
                               "No Schedule found with id " + id
                       ));
    }
    
    private GroupEntity getGroupEntityFromCourse(CourseEntity courseEntity, ScheduleDto scheduleDto) {
        return courseEntity.getGroups().stream()
                       .filter(g -> g.getId().equals(scheduleDto.getGroupId()))
                       .findFirst()
                       .orElseThrow(
                               () -> new GroupNotFoundException(
                                       "Group not found with id: " + scheduleDto.getGroupId() +
                                               " in course id: " + scheduleDto.getCourseId()
                               )
                       );
    }
    
    private void createEndTime(ScheduleDto scheduleDto) {
        if (scheduleDto.getEndDate() == null) {
            LocalDateTime endTime = scheduleDto.getStartDate().plusHours(1).plusMinutes(30);
            scheduleDto.setEndDate(endTime);
        }
    }
    
    private void chackTime(GroupEntity groupEntity, ScheduleDto scheduleDto) {
        List<ScheduleEntity> schedules = groupEntity.getSchedules().stream()
                                                 .filter(
                                                         g -> g.getGroup().getId().equals(scheduleDto.getGroupId())
                                                 )
                                                 .toList();
        
        boolean timeConflict = schedules.stream()
                                       .anyMatch(s -> hasTimeConflict(
                                               scheduleDto.getStartDate(), scheduleDto.getEndDate(),
                                               s.getStartDate(), s.getEndDate()
                                       ));
        if (timeConflict) {
            throw new TimeIlLegalArgumentException("Time is less than start time");
        }
    }
    
    private boolean hasTimeConflict(LocalDateTime start1,
                                    LocalDateTime end1,
                                    LocalDateTime start2,
                                    LocalDateTime end2) {
        return !(end1.isBefore(start2) || start1.isAfter(end2));
    }
}