package com.mustafa.hotelreservationsystem.services;

import com.mustafa.hotelreservationsystem.models.Service;

import java.util.List;

public interface ServiceService {

    void createService(Service s);
    Service getService(long id);
    List<Service> getAllServices();
    Service deleteService(long id);
    void changeServiceName(long id, String newServiceName);
    void changePrice(long id, int newPrice);

}
