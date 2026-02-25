package com.trainingsystem.service;

import com.trainingsystem.dao.StudentsRepository;
import com.trainingsystem.exception.StudentNotFoundException;
import com.trainingsystem.model.dto.StudentDto;
import com.trainingsystem.model.entity.StudentEntity;
import com.trainingsystem.model.mapper.StudentMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StudentService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);
    
    private final StudentsRepository studentRepository;
    private final StudentMapper studentMapper;
    
    public StudentDto getStudentById(long id) {
        LOGGER.info("Getting student by id {}", id);
        StudentEntity studentEntity = studentRepository.findById(id)
                                              .orElseThrow(() -> new StudentNotFoundException(
                                                      "No student found with id " + id
                                              ));
        return studentMapper.toStudentDto(studentEntity);
    }
    
    @Transactional
    public StudentDto saveStudent(StudentDto studentDto) {
        LOGGER.info("Saving student {}", studentDto);
        var entityStudentToSave = studentMapper.toStudentEntity(studentDto);
        entityStudentToSave = studentRepository.save(entityStudentToSave);
        return studentMapper.toStudentDto(entityStudentToSave);
    }
}

