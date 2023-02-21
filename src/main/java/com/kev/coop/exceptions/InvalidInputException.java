package com.kev.coop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidInputException extends RuntimeException {
    public InvalidInputException() {
        super("Invalid input, check that values being passed conform to requirements");
    }

    public InvalidInputException(String message) {
        super(message);
    }

}
