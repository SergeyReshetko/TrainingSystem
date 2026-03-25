package com.trainingsystem.controller;

import com.trainingsystem.model.dto.StudentDto;
import com.trainingsystem.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
@RequiredArgsConstructor
public class StudentController {
    
    private final StudentService studentService;
    
    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudent(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.findById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<StudentDto>> getAllStudents(
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "pageNumber", required = false) Integer pageNumber
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.findAll(pageSize, pageNumber));
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