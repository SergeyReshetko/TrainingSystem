package com.trainingsystem.model.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@RequiredArgsConstructor
@Component
public class GroupDto {
    private Integer groupId;
    private Integer groupNumber;
}