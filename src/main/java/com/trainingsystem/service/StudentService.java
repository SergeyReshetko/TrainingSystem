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

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class StudentService {
    
    private final StudentsRepository studentRepository;
    private final StudentMapper studentMapper;
    private final GroupService groupService;
    
    public StudentDto findById(long id) {
        log.info("Getting student by id {}", id);
        
        var studentEntity = getStudentEntity(id);
        return studentMapper.toStudentDto(studentEntity);
    }
    
    public List<StudentDto> findAll() {
        log.info("Getting all students");
        
        List<StudentEntity> studentEntities = studentRepository.findAll();
        return studentEntities.stream()
                       .map(studentMapper::toStudentDto)
                       .toList();
    }
    
    public List<StudentDto> findAllByGroupNumber(Integer groupNumber) {
        log.info("Getting all students by group number {}", groupNumber);
        
        List<StudentEntity> studentEntities = getListStudentsByGroupNumber(groupNumber);
        return studentEntities.stream()
                       .map(studentMapper::toStudentDto)
                       .toList();
    }
    
    @Transactional
    public StudentDto saveStudent(StudentDto studentDto) {
        log.info("Saving student {}", studentDto);
        
        var studentEntity = studentMapper.toStudentEntity(studentDto);
        var groupEntity = groupService.getByGroupNumber(studentDto.getGroupNumber());
        studentEntity.setGroup(groupEntity);
        groupEntity.getStudents().add(studentEntity);
        studentEntity = studentRepository.save(studentEntity);
        return studentMapper.toStudentDto(studentEntity);
    }
    
    @Transactional
    public StudentDto updateStudent(StudentDto studentDto) {
        log.info("Updating student {}", studentDto);
        
        var studentEntity = getStudentEntity(studentDto.getId());
        var groupEntity = groupService.getByGroupNumber(studentDto.getGroupNumber());
        studentEntity.setFirstName(studentDto.getFirstName());
        studentEntity.setLastName(studentDto.getLastName());
        studentEntity.setGroup(groupEntity);
        studentRepository.save(studentEntity);
        return studentMapper.toStudentDto(studentEntity);
    }
    
    @Transactional
    public StudentDto deleteStudent(long id) {
        log.info("Deleting student by id {}", id);
        
        var studentEntity = getStudentEntity(id);
        studentRepository.delete(studentEntity);
        return studentMapper.toStudentDto(studentEntity);
    }
    
    protected StudentEntity getStudentEntity(long id) {
        return studentRepository.findById(id).
                       orElseThrow(() -> new StudentNotFoundException(
                               "No student found with id " + id
                       ));
    }
    
    protected List<StudentEntity> getListStudentsByGroupNumber(Integer groupNumber) {
        return studentRepository.findAllByGroupGroupNumber(groupNumber)
                       .orElseThrow(() -> new StudentNotFoundException(
                               "No student found with groupNumber " + groupNumber
                       ));
    }
}

