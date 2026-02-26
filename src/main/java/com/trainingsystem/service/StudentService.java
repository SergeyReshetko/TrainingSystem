package com.trainingsystem.service;

import com.trainingsystem.dao.StudentsRepository;
import com.trainingsystem.exception.StudentNotFoundException;
import com.trainingsystem.model.dto.StudentDto;
import com.trainingsystem.model.entity.StudentEntity;
import com.trainingsystem.model.mapper.StudentMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class StudentService {
    
    private final StudentsRepository studentRepository;
    private final StudentMapper studentMapper;
    
    public StudentDto findById(long id) {
        log.info("Getting student by id {}", id);
        StudentEntity studentEntity = studentRepository.findById(id)
                                              .orElseThrow(() -> new StudentNotFoundException(
                                                      "No student found with id " + id
                                              ));
        return studentMapper.toStudentDto(studentEntity);
    }
    
    @Transactional
    public StudentDto saveStudent(StudentDto studentDto) {
        log.info("Saving student {}", studentDto);
        var entityStudentToSave = studentMapper.toStudentEntity(studentDto);
        entityStudentToSave = studentRepository.save(entityStudentToSave);
        return studentMapper.toStudentDto(entityStudentToSave);
    }
}

