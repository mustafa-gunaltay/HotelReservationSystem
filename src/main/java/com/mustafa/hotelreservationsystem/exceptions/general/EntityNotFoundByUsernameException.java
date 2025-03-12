package com.mustafa.hotelreservationsystem.exceptions.general;

public class EntityNotFoundByUsernameException extends Exception {

    private String username;

    public EntityNotFoundByUsernameException(String username, String message, Throwable cause) {
        super(message, cause);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
