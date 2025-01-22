package com.mustafa.hotelreservationsystem.exceptions.general;

public class InvalidAdminPasswordException extends InvalidAdminException{

    private String password;

    public InvalidAdminPasswordException(String message, String password) {
        super(message);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
