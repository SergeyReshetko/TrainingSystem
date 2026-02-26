package com.trainingsystem.service;

import com.trainingsystem.dao.CoursesRepository;
import com.trainingsystem.exception.CourseNotFoundException;
import com.trainingsystem.model.dto.CourseDto;
import com.trainingsystem.model.entity.CourseEntity;
import com.trainingsystem.model.mapper.CourseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class CourseService {
    
    private final CoursesRepository courseRepository;
    private final CourseMapper courseMapper;
    
    public CourseDto findById(Integer id) {
        log.info("Getting course by id {}", id);
        CourseEntity courseEntity = courseRepository.findById(id)
                                            .orElseThrow(() -> new CourseNotFoundException(
                                                    "No course found with id " + id
                                            ));
        return courseMapper.toCourseDto(courseEntity);
    }
}
