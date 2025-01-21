package com.mustafa.hotelreservationsystem.exceptions.general;

public class InvalidAdminPasswordException extends InvalidAdminException{

    private String password;

    public InvalidAdminPasswordException(String message) {
        super(message);
    }

    public String getPassword() {
        return password;
    }
}
