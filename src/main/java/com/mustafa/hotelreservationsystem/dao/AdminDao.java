package com.mustafa.hotelreservationsystem.dao;

import com.mustafa.hotelreservationsystem.exceptions.db.DuplicateEntryException;
import com.mustafa.hotelreservationsystem.exceptions.db.ZeroRowsAffectedOrReturnedException;
import com.mustafa.hotelreservationsystem.models.Admin;
import com.mustafa.hotelreservationsystem.models.Entity;

import java.util.List;

public interface AdminDao extends Crudable{
    @Override
    void save(Entity e) throws DuplicateEntryException;

    @Override
    void update(Entity e) throws DuplicateEntryException, ZeroRowsAffectedOrReturnedException;
    @Override
    Admin retrieve(long id) throws ZeroRowsAffectedOrReturnedException;

    @Override
    Admin delete(long id) throws ZeroRowsAffectedOrReturnedException;

    List<Admin> retrieveAllAdmins();
    void updateSpecifiedAdminField(long id, String fieldName, Object fieldValue) throws DuplicateEntryException, ZeroRowsAffectedOrReturnedException;
}
