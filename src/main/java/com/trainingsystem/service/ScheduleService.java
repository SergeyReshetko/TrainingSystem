package com.trainingsystem.service;

import com.trainingsystem.dao.SchedulesRepository;
import com.trainingsystem.exception.ScheduleNotFoundException;
import com.trainingsystem.model.dto.ScheduleDto;
import com.trainingsystem.model.entity.ScheduleEntity;
import com.trainingsystem.model.mapper.ScheduleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class ScheduleService {
    
    private final SchedulesRepository schedulesRepository;
    private final ScheduleMapper scheduleMapper;
    
    public ScheduleDto findById(Long id) {
        log.info("Getting schedule by id {}", id);
        ScheduleEntity scheduleEntity = schedulesRepository.findById(id)
                                                .orElseThrow(() -> new ScheduleNotFoundException(
                                                        "No Schedule found with id " + id
                                                ));
        return scheduleMapper.toScheduleDto(scheduleEntity);
    }
}
