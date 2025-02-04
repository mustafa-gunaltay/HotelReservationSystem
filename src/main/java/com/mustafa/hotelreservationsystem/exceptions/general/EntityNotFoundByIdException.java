package com.mustafa.hotelreservationsystem.exceptions.general;

public class EntityNotFoundByIdException extends RuntimeException {

    public EntityNotFoundByIdException(String message) {
        super(message);
    }

    public EntityNotFoundByIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
