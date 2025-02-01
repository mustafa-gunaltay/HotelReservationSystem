package com.mustafa.hotelreservationsystem.dao;

import com.mustafa.hotelreservationsystem.exceptions.db.DuplicateEntryException;
import com.mustafa.hotelreservationsystem.exceptions.general.InvalidReceptionistPasswordException;
import com.mustafa.hotelreservationsystem.exceptions.general.InvalidReceptionistUsernameException;
import com.mustafa.hotelreservationsystem.models.Entity;
import com.mustafa.hotelreservationsystem.models.Receptionist;

import java.util.List;

public interface ReceptionistDao extends Crudable{

    @Override
    void save(Entity e) throws DuplicateEntryException;
    @Override
    void update(Entity e) throws DuplicateEntryException;
    @Override
    Receptionist retrieve(long id);
    @Override
    Receptionist delete(long id);

    List<Receptionist> retrieveAllReceptionists();
    void updateSpecifiedReceptionistField(long id, String fieldName, Object fieldValue) throws DuplicateEntryException;
}
