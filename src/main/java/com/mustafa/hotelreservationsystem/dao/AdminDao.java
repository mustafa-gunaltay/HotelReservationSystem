package com.mustafa.hotelreservationsystem.dao;

import com.mustafa.hotelreservationsystem.exceptions.db.DuplicateEntryException;
import com.mustafa.hotelreservationsystem.models.Admin;
import com.mustafa.hotelreservationsystem.models.Entity;

import java.util.List;

public interface AdminDao extends Crudable{
    @Override
    void save(Entity e) throws DuplicateEntryException;

    @Override
    void update(Entity e) throws DuplicateEntryException;
    @Override
    Admin retrieve(long id);

    @Override
    Admin delete(long id);

    List<Admin> retrieveAllAdmins();
    void updateSpecifiedAdminField(long id, String fieldName, Object fieldValue) throws DuplicateEntryException;
}
