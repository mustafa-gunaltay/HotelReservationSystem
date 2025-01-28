package com.mustafa.hotelreservationsystem.dao;

import com.mustafa.hotelreservationsystem.models.Entity;
import com.mustafa.hotelreservationsystem.models.Reservation;
import com.mustafa.hotelreservationsystem.models.Room;

import java.util.List;

public interface ReservationDao extends Crudable{
    @Override
    Reservation retrieve(long id);
    @Override
    Reservation delete(long id);

    List<Reservation> retrieveAllReservations();
    void updateSpecifiedReservationField(long id, String fieldName, Object fieldValue);
    void bindReservationAndCustomer(long resId, long cusId);
    void unbindReservationAndCustomer(long resId, long cusId);
    void linkReservationToReceptionist(long resId, long recId);
    void unlinkReservationFromReceptionist(long resId);
}
