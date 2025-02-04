package com.mustafa.hotelreservationsystem.exceptions.db;

public class ZeroRowsAffectedOrReturnedException extends Exception {

    private long id;
    private long secondId; // for unbind methods

    public ZeroRowsAffectedOrReturnedException(String message, long id) {
        super(message);
        this.id = id;
    }

    public ZeroRowsAffectedOrReturnedException(String message, long id, long secondId) {
        super(message);
        this.id = id;
        this.secondId = secondId;
    }

    public long getId() {
        return id;
    }

    public long getSecondId() {
        return secondId;
    }
}
