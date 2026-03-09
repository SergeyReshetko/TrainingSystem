package com.trainingsystem.service;

import com.trainingsystem.dao.CoursesRepository;
import com.trainingsystem.dao.TeachersRepository;
import com.trainingsystem.exception.CourseNotFoundException;
import com.trainingsystem.model.dto.CourseDto;
import com.trainingsystem.model.entity.CourseEntity;
import com.trainingsystem.model.entity.GroupEntity;
import com.trainingsystem.model.mapper.CourseMapper;
import com.trainingsystem.model.mapper.TeacherMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class CourseService {
    
    private final CoursesRepository courseRepository;
    private final TeachersRepository teachersRepository;
    private final TeacherService teacherService;
    private final GroupService groupService;
    private final CourseMapper courseMapper;
    private final TeacherMapper teacherMapper;
    
    @Transactional
    public CourseDto findById(Integer id) {
        log.info("Getting course by id {}", id);
        
        var courseEntity = getCourseEntity(id);
        return courseMapper.toCourseDto(courseEntity);
    }
    
    public List<CourseDto> findAll() {
        log.info("Getting all courses");
        
        List<CourseEntity> courseEntities = courseRepository.findAllWithTeacher()
                                                    .orElseThrow(() -> new CourseNotFoundException(
                                                            "No course found"
                                                    ));
        return courseEntities.stream().map(courseMapper::toCourseDto).toList();
    }
    
    @Transactional
    public CourseDto createCourse(CourseDto courseDto) {
        log.info("Creating course {}", courseDto);
        
        var courseEntity = courseMapper.toCourseEntity(courseDto);
        var teacherEntity = teacherMapper.toTeacherEntity(
                teacherService.findById(courseDto.getTeacherId()));
        courseEntity.setTeacher(teacherEntity);
        courseEntity = courseRepository.save(courseEntity);
        return courseMapper.toCourseDto(courseEntity);
    }
    
    @Transactional
    public CourseDto updateCourse(CourseDto courseDto) {
        log.info("Updating course {}", courseDto);
        
        var courseEntity = getCourseEntity(courseDto.getId());
        courseEntity.setName(courseDto.getName());
        if (courseDto.getTeacherId() != null) {
            courseEntity.setTeacher(teacherMapper.toTeacherEntity(teacherService.findById(courseDto.getTeacherId())));
        }
        courseEntity = courseRepository.save(courseEntity);
        return courseMapper.toCourseDto(courseEntity);
    }
    
    @Transactional
    public CourseDto addGroup(Integer courseId, Integer groupId) {
        log.info("Adding group {} in course {}", groupId, courseId);
        
        var courseEntity = getCourseEntity(courseId);
        var groupEntity = groupService.getById(groupId);
        courseEntity.getGroups().add(groupEntity);
        groupEntity.getCourses().add(courseEntity);
        courseEntity = courseRepository.save(courseEntity);
        return courseMapper.toCourseDto(courseEntity);
    }
    
    @Transactional
    public CourseDto removeGroup(Integer courseId, Integer groupId) {
        log.info("Removing group {} from course {}", groupId, courseId);
        
        var courseEntity = getCourseEntity(courseId);
        var groupEntity = groupService.getById(groupId);
        courseEntity.getGroups().remove(groupEntity);
        groupEntity.getCourses().remove(courseEntity);
        courseEntity = courseRepository.save(courseEntity);
        return courseMapper.toCourseDto(courseEntity);
    }
    
    @Transactional
    public CourseDto deleteCourse(Integer id) {
        log.info("Deleting course {}", id);
        
        var courseEntity = getCourseEntity(id);
        var teacherEntity = courseEntity.getTeacher();
        for (GroupEntity group : courseEntity.getGroups()) {
            group.getCourses().remove(courseEntity);
        }
        courseEntity.getGroups().clear();
        if (teacherEntity != null) {
            teacherEntity.setCourse(null);
            teachersRepository.save(teacherEntity);
        }
        courseRepository.delete(courseEntity);
        return courseMapper.toCourseDto(courseEntity);
    }
    
    protected CourseEntity getCourseEntity(Integer id) {
        return courseRepository.findByIdWithGroups(id).orElseThrow(() -> new CourseNotFoundException(
                "No course found with id " + id));
    }
}
