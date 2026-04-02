package com.trainingsystem.controller;

import com.trainingsystem.model.dto.CourseDto;
import com.trainingsystem.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/course")
@RequiredArgsConstructor
@Tag(name = "Courses", description = "API для управления курсами")
public class CourseController {
    
    private final CourseService courseService;
    
    @Operation(
            summary = "Получить курс по ID",
            description = "Возвращает курс по его идентификатору",
            tags = {"Course"},
            parameters = {
                    @Parameter(
                            name = "id",
                            required = true,
                            description = "Это идентификатор курса",
                            schema = @Schema(implementation = CourseDto.class)
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Курс найден",
                            content = @Content(schema = @Schema(implementation = CourseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Курс не найден",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getCourse(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.findById(id));
    }
    
    @Operation(
            summary = "Получить все курсы",
            description = "Возвращает список всех курсов"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Список курсов получен"
    )
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