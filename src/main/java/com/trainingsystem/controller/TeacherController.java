package com.trainingsystem.controller;

import com.trainingsystem.model.dto.TeacherDto;
import com.trainingsystem.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/teacher")
@RequiredArgsConstructor
public class TeacherController {
    
    private final TeacherService teacherService;
    
    @GetMapping("/{id}")
    public ResponseEntity<TeacherDto> getTeacher(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(teacherService.getTeacherById(id));
    }
    
    @PostMapping
    public ResponseEntity<TeacherDto> saveTeacher(TeacherDto teacherDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(teacherService.saveTeacher(teacherDto));
    }
}