package com.trainingsystem.controller;

import com.trainingsystem.model.dto.GroupDto;
import com.trainingsystem.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/group")
@RequiredArgsConstructor
public class GroupController {
    
    private final GroupService groupService;
    
    @GetMapping("/{id}")
    public ResponseEntity<GroupDto> getGroup(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(groupService.findById(id));
    }
    
    @GetMapping("/groupNumber/{groupNumber}")
    public ResponseEntity<GroupDto> getGroupNumber(@PathVariable Integer groupNumber) {
        return ResponseEntity.status(HttpStatus.OK).body(groupService.findByGroupNumber(groupNumber));
    }
    
    @GetMapping
    public ResponseEntity<List<GroupDto>> getAllGroups() {
        return ResponseEntity.status(HttpStatus.OK).body(groupService.findAllByCroupNumber());
    }
    
    @PostMapping
    public ResponseEntity<GroupDto> saveGroup(@RequestBody GroupDto groupDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(groupService.saveGroup(groupDto));
    }
    
    @PutMapping
    public ResponseEntity<GroupDto> updateGroup(@RequestBody GroupDto newGroupDto) {
        return ResponseEntity.status(HttpStatus.OK).body(groupService.updateGroup(newGroupDto));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<GroupDto> deleteGroup(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(groupService.deleteGroup(id));
    }
}