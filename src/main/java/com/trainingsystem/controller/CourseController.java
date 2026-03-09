package com.trainingsystem.controller;

import com.trainingsystem.model.dto.CourseDto;
import com.trainingsystem.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/course")
@RequiredArgsConstructor
public class CourseController {
    
    private final CourseService courseService;
    
    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getCourse(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.findById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.findAll());
    }
    
    @PostMapping
    public ResponseEntity<CourseDto> createCourse(@RequestBody CourseDto courseDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.createCourse(courseDto));
    }
    
    @PutMapping
    public ResponseEntity<CourseDto> updateCourse(@RequestBody CourseDto courseDto) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.updateCourse(courseDto));
    }
    
    @PutMapping("/{id}/add/group/{groupId}")
    public ResponseEntity<CourseDto> addGroup(@PathVariable Integer id, @PathVariable Integer groupId) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.addGroup(id, groupId));
    }
    
    @PutMapping("/{id}/remove/group/{groupId}")
    public ResponseEntity<CourseDto> removeGroup(@PathVariable Integer id, @PathVariable Integer groupId) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.removeGroup(id, groupId));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<CourseDto> deleteCourse(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.deleteCourse(id));
    }
}