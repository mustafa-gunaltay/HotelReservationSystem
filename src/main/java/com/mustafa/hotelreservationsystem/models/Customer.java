package com.mustafa.hotelreservationsystem.models;

import java.time.LocalDate;

public class Customer extends Entity{
    private String fullName;
    private String phoneNumber;
    private LocalDate birthDate;
    private String description;

    public Customer(long id, String fullName, String phoneNumber, LocalDate birthDate, String description) {
        super(id);
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.description = description;
    }

    public Customer(long id, String fullName, String phoneNumber, LocalDate birthDate) {
        super(id);
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.description = null;
    }

    public Customer(String fullName, String phoneNumber, LocalDate birthDate, String description) {
        // id = 0
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.description = description;
    }
}
