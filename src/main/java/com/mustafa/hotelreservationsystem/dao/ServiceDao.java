package com.mustafa.hotelreservationsystem.dao;

import com.mustafa.hotelreservationsystem.exceptions.db.DuplicateEntryException;
import com.mustafa.hotelreservationsystem.models.Entity;
import com.mustafa.hotelreservationsystem.models.Service;

import java.util.List;

public interface ServiceDao extends Crudable {
    @Override
    void save(Entity e);
    @Override
    void update(Entity e);
    @Override
    Service retrieve(long id);
    @Override
    Service delete(long id);

    List<Service> retrieveAllServices();
    void updateSpecifiedServiceField(long id, String fieldName, Object fieldValue);
}
