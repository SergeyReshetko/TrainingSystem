package com.trainingsystem.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeacherDto {
    @NotNull(message = "ID cannot be null")
    @Positive(message = "ID cannot be negative number")
    Integer id;
    @NotNull(message = "First name cannot be null")
    @NotBlank(message = "First name cannot be blank")
    String firstName;
    @NotNull(message = "Last name cannot be null")
    @NotBlank(message = "Last name cannot be blank")
    String lastName;
}
