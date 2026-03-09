package com.trainingsystem.model.mapper;

import com.trainingsystem.model.dto.GroupDto;
import com.trainingsystem.model.entity.GroupEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    
    GroupEntity toGroupEntity(GroupDto groupDto);
    
    GroupDto toGroupDto(GroupEntity group);
}