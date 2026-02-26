package com.trainingsystem.service;

import com.trainingsystem.dao.TeachersRepository;
import com.trainingsystem.exception.TeacherNotFoundException;
import com.trainingsystem.model.dto.TeacherDto;
import com.trainingsystem.model.entity.TeacherEntity;
import com.trainingsystem.model.mapper.TeacherMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class TeacherService {
    
    private final TeachersRepository teachersRepository;
    private final TeacherMapper teacherMapper;
    
    public TeacherDto findById(Integer id) {
        log.info("Getting teacher by id {}", id);
        TeacherEntity teacherEntity = teachersRepository.findById(id)
                                              .orElseThrow(() -> new TeacherNotFoundException(
                                                      "No teacher found with id " + id
                                              ));
        return teacherMapper.toTeacherDto(teacherEntity);
    }
    
    @Transactional
    public TeacherDto saveTeacher(TeacherDto teacherDto) {
        log.info("Saving teacher {}", teacherDto);
        var entityTeacherToSave = teacherMapper.toTeacherEntity(teacherDto);
        entityTeacherToSave = teachersRepository.save(entityTeacherToSave);
        return teacherMapper.toTeacherDto(entityTeacherToSave);
    }
}
