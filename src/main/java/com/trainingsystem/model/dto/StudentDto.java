package com.trainingsystem.model.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@RequiredArgsConstructor
@Component
public class StudentDto {
    private Integer studentId;
    private String firstName;
    private String lastName;
    private Integer groupNumber;
}

