package com.trainingsystem.model.mapper;

import com.trainingsystem.model.dto.GroupDto;
import com.trainingsystem.model.entity.GroupEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface GroupMapper {
    GroupEntity toGroupEntity(GroupDto groupDto);
    
    GroupDto toGroupDto(GroupEntity groupEntity);
}