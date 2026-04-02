package com.trainingsystem.controller;

import com.trainingsystem.model.dto.ScheduleDto;
import com.trainingsystem.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schedule")
@RequiredArgsConstructor
public class SchedulesController {
    
    private final ScheduleService scheduleService;
    
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDto> getSchedule(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<ScheduleDto>> getAllSchedules() {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findAllSchedules());
    }
    
    @GetMapping("/group/{id}")
    public ResponseEntity<List<ScheduleDto>> getAllSchedulesByGroupId(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findAllSchedulesByGroupId(id));
    }
    
    @GetMapping("/teacher/{id}")
    public ResponseEntity<List<ScheduleDto>> getAllSchedulesByTeacherId(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findAllSchedulesByTeacherId(id));
    }
    
    @PostMapping
    public ResponseEntity<ScheduleDto> createSchedule(@RequestBody ScheduleDto scheduleDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.addSchedule(scheduleDto));
    }
    
    @PutMapping
    public ResponseEntity<ScheduleDto> updateSchedule(@RequestBody ScheduleDto scheduleDto) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.updateSchedule(scheduleDto));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ScheduleDto> deleteSchedule(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.deleteSchedule(id));
    }
}