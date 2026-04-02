package com.trainingsystem.exception;

import jakarta.persistence.EntityNotFoundException;

public class ScheduleNotFoundException extends EntityNotFoundException {
    public ScheduleNotFoundException(String message) {
        super(message);
    }
}