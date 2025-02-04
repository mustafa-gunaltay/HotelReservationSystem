package com.mustafa.hotelreservationsystem.dao;

public enum MySqlErrors {

    DUPLICATE_ENTRY(1062),
    FOREIGN_KEY_CONSTRAINT_VIOLATION(1452);
    // 1451'e sebebiyet verebilecek hatalar foreign key'lere tanimlanan "on delete" ek kisitlamalari ile handle edildi

    private final int code;

    MySqlErrors(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
