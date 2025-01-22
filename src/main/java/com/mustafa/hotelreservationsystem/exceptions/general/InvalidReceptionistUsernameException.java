package com.mustafa.hotelreservationsystem.exceptions.general;

public class InvalidReceptionistUsernameException extends InvalidReceptionistException{

    private String username;

    public InvalidReceptionistUsernameException(String message, String usernmae) {
        super(message);
        this.username = usernmae;
    }

    public String getUsername() {
        return username;
    }
}
