package com.trainingsystem.service;

import com.trainingsystem.dao.StudentsRepository;
import com.trainingsystem.exception.StudentNotFoundException;
import com.trainingsystem.model.dto.StudentDto;
import com.trainingsystem.model.entity.StudentEntity;
import com.trainingsystem.model.mapper.StudentMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class StudentService {
    
    private final StudentsRepository studentRepository;
    private final StudentMapper studentMapper;
    private final GroupService groupService;
    @Value("${app.pageSize:10}")
    private int defaultPageSize;
    @Value("${app.pageNumber:0}")
    private int defaultPageNumber;
    
    public StudentDto findById(long id) {
        log.info("Getting student by id {}", id);
        
        var studentEntity = getStudentEntity(id);
        return studentMapper.toStudentDto(studentEntity);
    }
    
    @NonNull
    @Transactional(readOnly = true)
    public Page<StudentDto> findAll(Pageable pageable) {
        log.info("Getting all students");
        
        if (pageable == null) {
            pageable = PageRequest.of(defaultPageNumber, defaultPageSize);
        }
        
        Page<StudentEntity> studentEntities = studentRepository.findAll(pageable);
        
        return studentEntities.map(studentMapper::toStudentDto);
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
    
    private StudentEntity getStudentEntity(long id) {
        return studentRepository.findById(id).
                       orElseThrow(() -> new StudentNotFoundException(
                               "No student found with id " + id
                       ));
    }
    
    private List<StudentEntity> getListStudentsByGroupNumber(Integer groupNumber) {
        return studentRepository.findAllByGroupGroupNumber(groupNumber)
                       .orElseThrow(() -> new StudentNotFoundException(
                               "No student found with groupNumber " + groupNumber
                       ));
    }
}

