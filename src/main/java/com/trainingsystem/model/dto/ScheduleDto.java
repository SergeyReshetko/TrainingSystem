package com.trainingsystem.model.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@Component
public class ScheduleDto {
    private Long scheduleId;
    private LocalDate date;
    private String teacherLastName;
    private String teacherFirstName;
    private String groupNumber;
    private String courseName;
}
