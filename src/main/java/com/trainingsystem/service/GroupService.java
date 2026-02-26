package com.trainingsystem.service;

import com.trainingsystem.dao.GroupsRepository;
import com.trainingsystem.exception.GroupNotFoundException;
import com.trainingsystem.model.dto.GroupDto;
import com.trainingsystem.model.entity.GroupEntity;
import com.trainingsystem.model.mapper.GroupMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class GroupService {
    
    private final GroupsRepository groupRepository;
    private final GroupMapper groupMapper;
    
    public GroupDto findByGroupNumber(Integer groupNumber) {
        log.info("Getting course by number {}", groupNumber);
        GroupEntity groupEntity = groupRepository.findByGroupNumber(groupNumber).
                                          orElseThrow(() -> new GroupNotFoundException(
                                                  "No groupNumber found with " + groupNumber
                                          ));
        groupMapper.toGroupDto(groupEntity);
        return groupMapper.toGroupDto(groupEntity);
    }
    
    @Transactional
    public GroupDto saveGroup(GroupDto groupDto) {
        log.info("Saving group {}", groupDto);
        var entityGroupToSave = groupMapper.toGroupEntity(groupDto);
        entityGroupToSave = groupRepository.save(entityGroupToSave);
        return groupMapper.toGroupDto(entityGroupToSave);
    }
}
