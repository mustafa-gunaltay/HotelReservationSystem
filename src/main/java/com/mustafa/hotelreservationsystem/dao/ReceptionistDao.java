package com.mustafa.hotelreservationsystem.dao;

import com.mustafa.hotelreservationsystem.models.Entity;
import com.mustafa.hotelreservationsystem.models.Receptionist;

import java.util.List;

public interface ReceptionistDao extends Crudable{
    @Override
    Receptionist retrieve(long id);
    @Override
    Receptionist delete(long id);

    List<Receptionist> retrieveAllReceptionists();
    void updateSpecifiedReceptionistField(long id, String fieldName, Object fieldValue);
}
