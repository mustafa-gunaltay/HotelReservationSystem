package com.mustafa.hotelreservationsystem.services;

import com.mustafa.hotelreservationsystem.models.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface ReservationService {
    void createReservation(Reservation r);
    void changeCheckInDate(long id, LocalDate newCheckInDate);
    void changeCheckOutDate(long id, LocalDate newCheckOutDate);
    void changeBookingDate(long id, LocalDate newBookingDate);
    Reservation deleteReservation(long id);
    void addCustomerToReservation(long resId, long idOfCusToBeAdded);
    void changeCustomerOnReservation(long oldResId, long newResId, long idOfCusToBeChanged);
    void deleteCustomerFromReservation(long resId, long idOfCusToBeRemoved);
    void addReceptionistToReservation(long resId, long recId);
    Reservation getReservation(long id);
    List<Reservation> getAllReservations();
}
