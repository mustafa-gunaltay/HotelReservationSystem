package com.mustafa.hotelreservationsystem.dao;

public enum MySqlErrors {

    DUPLICATE_ENTRY(1062),
    FOREIGN_KEY_CONSTRAINT_FAIL(1451),
    ACCESS_DENIED(1045);

    private final int code;

    MySqlErrors(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
