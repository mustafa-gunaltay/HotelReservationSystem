package com.mustafa.hotelreservationsystem.models;

import java.time.LocalDate;

public class Reservation extends Entity {
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDate bookingDate;
    private Receptionist receptionist;

    public Reservation(long id, LocalDate checkInDate, LocalDate checkOutDate, LocalDate bookingDate, Receptionist receptionist) {
        super(id);
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingDate = bookingDate;
        this.receptionist = receptionist;
    }

    public Reservation(long id, LocalDate checkInDate, LocalDate checkOutDate, LocalDate bookingDate) {
        super(id);
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingDate = bookingDate;
        this.receptionist = null;
    }

    public Reservation(LocalDate checkInDate, LocalDate checkOutDate, LocalDate bookingDate, Receptionist receptionist) {
        // id = 0
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingDate = bookingDate;
        this.receptionist = receptionist;
    }
}
