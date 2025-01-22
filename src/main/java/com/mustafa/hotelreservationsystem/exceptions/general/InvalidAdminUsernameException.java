package com.mustafa.hotelreservationsystem.exceptions.general;

public class InvalidAdminUsernameException extends InvalidAdminException{

    private String username;

    public InvalidAdminUsernameException(String message, String username){
        super(message);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
