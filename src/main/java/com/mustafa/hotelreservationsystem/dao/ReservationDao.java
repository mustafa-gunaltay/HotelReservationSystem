package com.mustafa.hotelreservationsystem.dao;


import com.mustafa.hotelreservationsystem.exceptions.db.DuplicateEntryException;
import com.mustafa.hotelreservationsystem.exceptions.db.NoReferencedRowException;
import com.mustafa.hotelreservationsystem.exceptions.db.ZeroRowsAffectedOrReturnedException;
import com.mustafa.hotelreservationsystem.models.Entity;
import com.mustafa.hotelreservationsystem.models.Reservation;
import com.mustafa.hotelreservationsystem.models.ReservationWithCustomerAndRoom;

import java.util.List;

public interface ReservationDao extends Crudable{
    @Override
    void save(Entity e);
    @Override
    void update(Entity e) throws NoReferencedRowException, ZeroRowsAffectedOrReturnedException;
    @Override
    Reservation retrieve(long id) throws ZeroRowsAffectedOrReturnedException;
    @Override
    Reservation delete(long id) throws ZeroRowsAffectedOrReturnedException;

    List<Reservation> retrieveAllReservations();
    void updateSpecifiedReservationField(long id, String fieldName, Object fieldValue) throws NoReferencedRowException, ZeroRowsAffectedOrReturnedException;
    void bindReservationAndCustomer(long resId, long cusId) throws NoReferencedRowException, DuplicateEntryException;
    void unbindReservationAndCustomer(long resId, long cusId) throws ZeroRowsAffectedOrReturnedException;
    void linkReservationToReceptionist(long resId, long recId) throws NoReferencedRowException, ZeroRowsAffectedOrReturnedException;
    void unlinkReservationFromReceptionist(long resId) throws ZeroRowsAffectedOrReturnedException;

    List<ReservationWithCustomerAndRoom> retrieveAllReservationsWithItsCustomersAndRooms();
}
