package com.mustafa.hotelreservationsystem.exceptions.db;

public class DuplicateEntryException extends Exception {

    String duplicateEntryName;

    public DuplicateEntryException(String message, String duplicateEntryName) {
        super(message);
        this.duplicateEntryName = duplicateEntryName;
    }

    public String getDuplicateEntryName() {
        return duplicateEntryName;
    }
}
