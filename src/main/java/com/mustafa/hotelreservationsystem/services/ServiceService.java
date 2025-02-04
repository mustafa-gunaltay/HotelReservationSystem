package com.mustafa.hotelreservationsystem.services;

import com.mustafa.hotelreservationsystem.exceptions.general.EntityNotFoundByIdException;
import com.mustafa.hotelreservationsystem.models.Service;

import java.util.List;

public interface ServiceService {

    void createService(Service s);
    Service getService(long id) throws EntityNotFoundByIdException;
    List<Service> getAllServices();
    Service deleteService(long id) throws EntityNotFoundByIdException;
    void changeServiceName(long id, String newServiceName) throws EntityNotFoundByIdException;
    void changePrice(long id, int newPrice) throws EntityNotFoundByIdException;

}
