package com.trainingsystem.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@RequiredArgsConstructor
@Component
public class ScheduleDto {
    private Long id;
    private Integer courseId;
    private Integer teacherId;
    private Integer groupId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "H:mm:ss")
    private LocalTime startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "H:mm:ss")
    private LocalTime endTime;
    private LocalDate date;
}
