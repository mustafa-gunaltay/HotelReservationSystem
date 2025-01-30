package com.mustafa.hotelreservationsystem.dao;

import com.mustafa.hotelreservationsystem.models.Service;

import java.util.List;

public interface ServiceDao extends Crudable {
    @Override
    Service retrieve(long id);
    @Override
    Service delete(long id);

    List<Service> retrieveAllServices();
    void updateSpecifiedServiceField(long id, String fieldName, Object fieldValue);
}
