package com.trainingsystem.exception;

import jakarta.persistence.EntityNotFoundException;

public class CourseNotFoundException extends EntityNotFoundException {
    public CourseNotFoundException(String message) {
        super(message);
    }
}