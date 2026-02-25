package com.trainingsystem.exception;

import jakarta.persistence.EntityNotFoundException;

public class GroupNotFoundException extends EntityNotFoundException {
    public GroupNotFoundException(String message) {
        super(message);
    }
}