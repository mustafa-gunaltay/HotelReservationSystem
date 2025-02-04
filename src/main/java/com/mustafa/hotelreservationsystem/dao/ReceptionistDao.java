package com.mustafa.hotelreservationsystem.dao;

import com.mustafa.hotelreservationsystem.exceptions.db.DuplicateEntryException;
import com.mustafa.hotelreservationsystem.exceptions.db.ZeroRowsAffectedOrReturnedException;
import com.mustafa.hotelreservationsystem.models.Entity;
import com.mustafa.hotelreservationsystem.models.Receptionist;

import java.util.List;

public interface ReceptionistDao extends Crudable{

    @Override
    void save(Entity e) throws DuplicateEntryException;
    @Override
    void update(Entity e) throws DuplicateEntryException, ZeroRowsAffectedOrReturnedException;
    @Override
    Receptionist retrieve(long id) throws ZeroRowsAffectedOrReturnedException;
    @Override
    Receptionist delete(long id) throws ZeroRowsAffectedOrReturnedException;

    List<Receptionist> retrieveAllReceptionists();
    void updateSpecifiedReceptionistField(long id, String fieldName, Object fieldValue) throws DuplicateEntryException, ZeroRowsAffectedOrReturnedException;
}
