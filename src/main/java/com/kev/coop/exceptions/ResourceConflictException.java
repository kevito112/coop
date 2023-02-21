package com.kev.coop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceConflictException extends RuntimeException {
    public ResourceConflictException() {
        super("Resource is already taken by another user");
    }

    public ResourceConflictException(String message) {
        super(message);
    }
}