package com.trainingsystem.service;

import com.trainingsystem.dao.GroupsRepository;
import com.trainingsystem.exception.GroupNotFoundException;
import com.trainingsystem.model.dto.GroupDto;
import com.trainingsystem.model.entity.GroupEntity;
import com.trainingsystem.model.mapper.GroupMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GroupService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupService.class);
    
    private final GroupsRepository groupRepository;
    private final GroupMapper groupMapper;
    
    public GroupDto getGroupByNumber(Integer groupNumber) {
        LOGGER.info("Getting course by number {}", groupNumber);
        GroupEntity groupEntity = groupRepository.findByGroupNumber(groupNumber).
                                          orElseThrow(() -> new GroupNotFoundException(
                                                  "No groupNumber found with " + groupNumber
                                          ));
        groupMapper.toGroupDto(groupEntity);
        return groupMapper.toGroupDto(groupEntity);
    }
    
    @Transactional
    public GroupDto saveGroup(GroupDto groupDto) {
        LOGGER.info("Saving group {}", groupDto);
        var entityGroupToSave = groupMapper.toGroupEntity(groupDto);
        entityGroupToSave = groupRepository.save(entityGroupToSave);
        return groupMapper.toGroupDto(entityGroupToSave);
    }
}
