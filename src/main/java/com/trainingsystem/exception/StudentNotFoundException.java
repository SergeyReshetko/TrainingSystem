package com.trainingsystem.exception;

import jakarta.persistence.EntityNotFoundException;

public class StudentNotFoundException extends EntityNotFoundException {
    public StudentNotFoundException(String message) {
        super(message);
    }
}