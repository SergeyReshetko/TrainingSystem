package com.trainingsystem.service;

import com.trainingsystem.dao.GroupsRepository;
import com.trainingsystem.dao.StudentsRepository;
import com.trainingsystem.exception.GroupNotFoundException;
import com.trainingsystem.model.dto.GroupDto;
import com.trainingsystem.model.entity.GroupEntity;
import com.trainingsystem.model.entity.StudentEntity;
import com.trainingsystem.model.mapper.GroupMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class GroupService {
    
    private final GroupsRepository groupRepository;
    private final StudentsRepository studentsRepository;
    private final GroupMapper groupMapper;
    
    public GroupDto findById(Integer groupNumber) {
        log.info("Finding group with id {}", groupNumber);
        var groupEntity = getById(groupNumber);
        return groupMapper.toGroupDto(groupEntity);
    }
    
    @Transactional
    public GroupDto findByGroupNumber(Integer groupNumber) {
        log.info("Getting group by number {}", groupNumber);
        
        var groupEntity = getByGroupNumber(groupNumber);
        groupMapper.toGroupDto(groupEntity);
        return groupMapper.toGroupDto(groupEntity);
    }
    
    @Transactional
    public List<GroupDto> findAllByCroupNumber() {
        log.info("Finding all groups by croup number");
        
        return groupRepository.findAll().stream()
                       .map(groupMapper::toGroupDto)
                       .toList();
    }
    
    @Transactional
    public GroupDto saveGroup(GroupDto groupDto) {
        log.info("Saving group {}", groupDto);
        
        var groupEntity = groupMapper.toGroupEntity(groupDto);
        groupEntity = groupRepository.save(groupEntity);
        return groupMapper.toGroupDto(groupEntity);
    }
    
    @Transactional
    public GroupDto updateGroup(GroupDto newGroupDto) {
        log.info("Updating group {}", newGroupDto);
        
        var groupEntity = getById(newGroupDto.getId());
        groupEntity.setGroupNumber(newGroupDto.getGroupNumber());
        groupEntity.setSchedules(groupMapper.toGroupEntity(newGroupDto).getSchedules());
        groupRepository.save(groupEntity);
        return groupMapper.toGroupDto(groupEntity);
    }
    
    @Transactional
    public GroupDto deleteGroup(Integer id) {
        log.info("Deleting group {}", id);
        
        var groupEntity = getById(id);
        for (StudentEntity student : groupEntity.getStudents()) {
            student.setGroup(null);
            studentsRepository.save(student);
        }
        groupEntity.getStudents().clear();
        groupEntity.getCourses().clear();
        groupRepository.delete(groupEntity);
        return groupMapper.toGroupDto(groupEntity);
    }
    
    protected GroupEntity getById(Integer id) {
        log.info("Getting group by id {}", id);
        
        return groupRepository.findById(id)
                       .orElseThrow(() -> new GroupNotFoundException("No group found with id " + id));
    }
    
    protected GroupEntity getByGroupNumber(Integer groupNumber) {
        return groupRepository.findByGroupNumber(groupNumber)
                       .orElseThrow(() -> new GroupNotFoundException("No groupNumber found with " + groupNumber));
    }
}
