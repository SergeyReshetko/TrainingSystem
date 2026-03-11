package com.trainingsystem.model.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class StudentDto {
    Long id;
    String firstName;
    String lastName;
    Integer groupNumber;
    GroupDto group;
}

