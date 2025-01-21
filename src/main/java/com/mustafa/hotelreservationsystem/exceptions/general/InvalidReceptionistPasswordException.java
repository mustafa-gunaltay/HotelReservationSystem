package com.mustafa.hotelreservationsystem.exceptions.general;

public class InvalidReceptionistPasswordException extends InvalidReceptionistException{

    private String password;

    public InvalidReceptionistPasswordException(String message) {
        super(message);
    }

    public String getPassword() {
        return password;
    }
}
