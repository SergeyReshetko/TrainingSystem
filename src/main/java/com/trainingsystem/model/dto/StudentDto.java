package com.trainingsystem.model.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentDto {
    Long id;
    String firstName;
    String lastName;
    Integer groupNumber;
    GroupDto group;
}

