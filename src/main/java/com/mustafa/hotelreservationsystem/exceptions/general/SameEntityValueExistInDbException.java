package com.mustafa.hotelreservationsystem.exceptions.general;

public class SameEntityValueExistInDbException extends Exception {
    public SameEntityValueExistInDbException(String message) {
      super(message);
    }

  public SameEntityValueExistInDbException(String message, Throwable cause) {
    super(message, cause);
  }
}
