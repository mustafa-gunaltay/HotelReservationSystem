package com.mustafa.hotelreservationsystem.exceptions.general;

public class InvalidAdminUsernameException extends InvalidAdminException{

    private String username;

    public InvalidAdminUsernameException(String message){
        super(message);
    }

    public String getUsername() {
        return username;
    }
}
