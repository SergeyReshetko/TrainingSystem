package com.trainingsystem.controller;

import com.trainingsystem.model.dto.StudentDto;
import com.trainingsystem.service.StudentCriteriaService;
import com.trainingsystem.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
@RequiredArgsConstructor
public class StudentController {
    
    private final StudentService studentService;
    private final StudentCriteriaService studentCriteriaService;
    
    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudent(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.findById(id));
    }
    
    @GetMapping
    public ResponseEntity<Page<StudentDto>> getAllStudents(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.findAll(pageable));
    }
    
    @GetMapping("/predicates")
    public ResponseEntity<Page<StudentDto>> getAllStudentsByPredicates(
            @RequestParam(name = "firstName", required = false) String firstName,
            @RequestParam(name = "lastName", required = false) String lastName,
            @RequestParam(name = "groupNumber", required = false) Integer groupNumber,
            Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                studentCriteriaService
                        .findAllStudentsByPredicates(
                                firstName,
                                lastName,
                                groupNumber,
                                pageable
                        )
        );
    }
    
    @GetMapping("/groupNumber/{groupNumber}")
    public ResponseEntity<List<StudentDto>> getAllStudentsByGroupNumber(@PathVariable Integer groupNumber) {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.findAllByGroupNumber(groupNumber));
    }
    
    @PostMapping
    public ResponseEntity<StudentDto> createStudent(@RequestBody StudentDto studentDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.saveStudent(studentDto));
    }
    
    @PutMapping
    public ResponseEntity<StudentDto> updateStudent(@RequestBody StudentDto studentDto) {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.updateStudent(studentDto));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<StudentDto> deleteStudent(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.deleteStudent(id));
    }
}