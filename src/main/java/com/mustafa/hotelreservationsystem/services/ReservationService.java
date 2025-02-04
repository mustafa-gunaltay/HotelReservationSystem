package com.mustafa.hotelreservationsystem.services;

import com.mustafa.hotelreservationsystem.exceptions.general.EntityNotFoundByIdException;
import com.mustafa.hotelreservationsystem.exceptions.general.ReferencedEntityNotFoundException;
import com.mustafa.hotelreservationsystem.models.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface ReservationService {
    void createReservation(Reservation r);
    void changeCheckInDate(long id, LocalDate newCheckInDate) throws EntityNotFoundByIdException;
    void changeCheckOutDate(long id, LocalDate newCheckOutDate) throws EntityNotFoundByIdException;
    void changeBookingDate(long id, LocalDate newBookingDate) throws EntityNotFoundByIdException;
    Reservation deleteReservation(long id) throws EntityNotFoundByIdException;
    void addCustomerToReservation(long resId, long idOfCusToBeAdded) throws ReferencedEntityNotFoundException;
    void changeCustomerOnReservation(long oldResId, long newResId, long idOfCusToBeChanged) throws ReferencedEntityNotFoundException, EntityNotFoundByIdException;
    void deleteCustomerFromReservation(long resId, long idOfCusToBeRemoved) throws EntityNotFoundByIdException;
    void addReceptionistToReservation(long resId, long recId) throws ReferencedEntityNotFoundException, EntityNotFoundByIdException;
    Reservation getReservation(long id) throws EntityNotFoundByIdException;
    List<Reservation> getAllReservations();
}
