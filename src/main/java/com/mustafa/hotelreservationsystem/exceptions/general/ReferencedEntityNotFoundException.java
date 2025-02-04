package com.mustafa.hotelreservationsystem.exceptions.general;

public class ReferencedEntityNotFoundException extends Exception{

    public ReferencedEntityNotFoundException(String message) {
      super(message);
    }

    public ReferencedEntityNotFoundException(String message, Throwable cause) {
      super(message, cause);
    }
}
