package com.trainingsystem.controller;

import com.trainingsystem.model.dto.CourseDto;
import com.trainingsystem.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/course")
@RequiredArgsConstructor
public class CourseController {
    
    private final CourseService courseService;
    
    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getCourse(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getCourseById(id));
    }
}