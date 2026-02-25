package com.trainingsystem.controller;

import com.trainingsystem.model.dto.GroupDto;
import com.trainingsystem.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/group")
@RequiredArgsConstructor
public class GroupController {
    
    private final GroupService groupService;
    
    @GetMapping("/{groupNumber}")
    public ResponseEntity<GroupDto> getGroupNumber(@PathVariable Integer groupNumber) {
        return ResponseEntity.status(HttpStatus.OK).body(groupService.getGroupByNumber(groupNumber));
    }
    
    @PostMapping
    public ResponseEntity<GroupDto> saveGroup(@RequestBody GroupDto groupDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(groupService.saveGroup(groupDto));
    }
}