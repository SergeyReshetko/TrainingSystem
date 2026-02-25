package com.trainingsystem.service;

import com.trainingsystem.dao.CoursesRepository;
import com.trainingsystem.exception.CourseNotFoundException;
import com.trainingsystem.model.dto.CourseDto;
import com.trainingsystem.model.entity.CourseEntity;
import com.trainingsystem.model.mapper.CourseMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CourseService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseService.class);
    
    private final CoursesRepository courseRepository;
    private final CourseMapper courseMapper;
    
    public CourseDto getCourseById(Integer id) {
        LOGGER.info("Getting course by id {}", id);
        CourseEntity courseEntity = courseRepository.findById(id)
                                            .orElseThrow(() -> new CourseNotFoundException(
                                                    "No course found with id " + id
                                            ));
        return courseMapper.toCourseDto(courseEntity);
    }
}
