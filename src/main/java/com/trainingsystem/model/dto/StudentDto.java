package com.trainingsystem.model.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@RequiredArgsConstructor
@Component
public class StudentDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer groupNumber;
    private GroupDto group;
}

