package com.mustafa.hotelreservationsystem.exceptions.general;

public class InvalidReceptionistPasswordException extends InvalidReceptionistException{

    private String password;

    public InvalidReceptionistPasswordException(String message, String password) {
        super(message);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
