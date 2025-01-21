package com.mustafa.hotelreservationsystem.exceptions.general;

public class InvalidReceptionistUsernameException extends InvalidReceptionistException{

    private String username;

    public InvalidReceptionistUsernameException(String message) {
        super(message);
    }

    public String getUsername() {
        return username;
    }
}
