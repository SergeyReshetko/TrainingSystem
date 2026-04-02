package com.trainingsystem.controller;

import com.trainingsystem.model.dto.TeacherCreateDto;
import com.trainingsystem.model.dto.TeacherDto;
import com.trainingsystem.service.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teacher")
@RequiredArgsConstructor
public class TeacherController {
    
    private final TeacherService teacherService;
    
    @GetMapping("/{id}")
    public ResponseEntity<TeacherDto> getTeacher(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(teacherService.findById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<TeacherDto>> getTeachers() {
        return ResponseEntity.status(HttpStatus.OK).body(teacherService.findAll());
    }
    
    @PostMapping
    public ResponseEntity<TeacherDto> saveTeacher(@Valid @RequestBody TeacherCreateDto teacherCreateDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(teacherService.saveTeacher(teacherCreateDto));
    }
    
    @PutMapping
    public ResponseEntity<TeacherDto> updateTeacher(@Valid @RequestBody TeacherDto teacherDto) {
        return ResponseEntity.status(HttpStatus.OK).body(teacherService.updateTeacher(teacherDto));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<TeacherDto> deleteTeacher(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(teacherService.deleteTeacher(id));
    }
    
}