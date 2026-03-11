package com.trainingsystem.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class ScheduleDto {
    Long id;
    Integer courseId;
    Integer teacherId;
    Integer groupId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "H:mm:ss")
    LocalTime startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "H:mm:ss")
    LocalTime endTime;
    LocalDate date;
}
