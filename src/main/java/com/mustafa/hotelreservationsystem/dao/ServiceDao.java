package com.mustafa.hotelreservationsystem.dao;

import com.mustafa.hotelreservationsystem.exceptions.db.ZeroRowsAffectedOrReturnedException;
import com.mustafa.hotelreservationsystem.models.Entity;
import com.mustafa.hotelreservationsystem.models.Service;

import java.util.List;

public interface ServiceDao extends Crudable {
    @Override
    void save(Entity e);
    @Override
    void update(Entity e) throws ZeroRowsAffectedOrReturnedException;
    @Override
    Service retrieve(long id) throws ZeroRowsAffectedOrReturnedException;
    @Override
    Service delete(long id) throws ZeroRowsAffectedOrReturnedException;

    List<Service> retrieveAllServices();
    void updateSpecifiedServiceField(long id, String fieldName, Object fieldValue) throws ZeroRowsAffectedOrReturnedException;
}
