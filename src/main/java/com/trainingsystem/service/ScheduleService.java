package com.trainingsystem.service;

import com.trainingsystem.dao.SchedulesRepository;
import com.trainingsystem.exception.ScheduleNotFoundException;
import com.trainingsystem.model.dto.ScheduleDto;
import com.trainingsystem.model.entity.ScheduleEntity;
import com.trainingsystem.model.mapper.ScheduleMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ScheduleService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleService.class);
    
    private final SchedulesRepository schedulesRepository;
    private final ScheduleMapper scheduleMapper;
    
    
    public ScheduleDto getSchedulesById(Long id) {
        LOGGER.info("Getting schedule by id {}", id);
        ScheduleEntity scheduleEntity = schedulesRepository.findById(id)
                                                .orElseThrow(() -> new ScheduleNotFoundException(
                                                        "No Schedule found with id " + id
                                                ));
        return scheduleMapper.toScheduleDto(scheduleEntity);
    }
}
