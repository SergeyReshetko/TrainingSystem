package com.trainingsystem.service;

import com.trainingsystem.dao.CoursesRepository;
import com.trainingsystem.dao.TeachersRepository;
import com.trainingsystem.exception.TeacherNotFoundException;
import com.trainingsystem.model.dto.TeacherDto;
import com.trainingsystem.model.entity.TeacherEntity;
import com.trainingsystem.model.mapper.TeacherMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class TeacherService {
    
    private final TeachersRepository teachersRepository;
    private final CoursesRepository coursesRepository;
    private final TeacherMapper teacherMapper;
    
    @Transactional
    public TeacherDto findById(Integer id) {
        log.info("Getting teacher by id {}", id);
        
        var teacherEntity = getTeacherEntity(id);
        return teacherMapper.toTeacherDto(teacherEntity);
    }
    
    @Transactional
    public List<TeacherDto> findAll() {
        log.info("Getting all teachers");
        
        return teachersRepository.findAll().stream()
                       .map(teacherMapper::toTeacherDto)
                       .toList();
    }
    
    @Transactional
    public TeacherDto saveTeacher(TeacherDto teacherDto) {
        log.info("Saving teacher {}", teacherDto);
        
        var teacherEntity = teacherMapper.toTeacherEntity(teacherDto);
        teacherEntity = teachersRepository.save(teacherEntity);
        return teacherMapper.toTeacherDto(teacherEntity);
    }
    
    @Transactional
    public TeacherDto updateTeacher(TeacherDto teacherDto) {
        log.info("Updating teacher {}", teacherDto);
        
        var teacherEntity = getTeacherEntity(teacherDto.getId());
        teacherEntity.setFirstName(teacherDto.getFirstName());
        teacherEntity.setLastName(teacherDto.getLastName());
        teacherEntity = teachersRepository.save(teacherEntity);
        return teacherMapper.toTeacherDto(teacherEntity);
    }
    
    @Transactional
    public TeacherDto deleteTeacher(Integer id) {
        log.info("Deleting teacher {}", id);
        
        var teacherEntity = getTeacherEntity(id);
        var courseEntity = teacherEntity.getCourse();
        if (courseEntity != null) {
            courseEntity.setTeacher(null);
            coursesRepository.save(courseEntity);
        }
        teachersRepository.delete(teacherEntity);
        return teacherMapper.toTeacherDto(teacherEntity);
    }
    
    protected TeacherEntity getTeacherEntity(Integer id) {
        return teachersRepository.findById(id)
                       .orElseThrow(() -> new TeacherNotFoundException(
                               "No teacher found with id " + id
                       ));
    }
}
