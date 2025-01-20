package com.mustafa.hotelreservationsystem.dao;

import com.mustafa.hotelreservationsystem.models.Admin;

import java.util.List;

public interface AdminDao extends Crudable{
    @Override
    Admin retrieve(long id);

    @Override
    Admin delete(long id);

    List<Admin> retrieveAllAdmins();
    void updateSpecifiedAdminField(long id, String fieldName, Object fieldValue);
}
