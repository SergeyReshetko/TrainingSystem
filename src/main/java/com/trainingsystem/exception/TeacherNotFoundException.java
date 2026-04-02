package com.trainingsystem.exception;

import jakarta.persistence.EntityNotFoundException;

public class TeacherNotFoundException extends EntityNotFoundException {
    public TeacherNotFoundException(String massage) { super(massage); }
}