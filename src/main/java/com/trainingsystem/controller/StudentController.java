package com.trainingsystem.controller;

import com.trainingsystem.model.dto.StudentDto;
import com.trainingsystem.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/student")
@RequiredArgsConstructor
public class StudentController {
    
    private final StudentService studentService;
    
    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudent(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.getStudentById(id));
    }
    
    @PostMapping
    public ResponseEntity<StudentDto> createStudent(@RequestBody StudentDto studentDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.saveStudent(studentDto));
    }
}