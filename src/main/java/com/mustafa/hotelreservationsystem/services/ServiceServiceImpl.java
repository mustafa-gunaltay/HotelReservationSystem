package com.mustafa.hotelreservationsystem.services;

import com.mustafa.hotelreservationsystem.dao.ServiceDao;
import com.mustafa.hotelreservationsystem.dao.ServiceDaoImpl;
import com.mustafa.hotelreservationsystem.models.Service;

import java.util.List;

public class ServiceServiceImpl implements ServiceService {

    ServiceDao serviceDao;

    public ServiceServiceImpl() {
        serviceDao = new ServiceDaoImpl();
    }

    @Override
    public void createService(Service s) {
        serviceDao.save(s);
    }

    @Override
    public Service getService(long id) {
        Service targetService = serviceDao.retrieve(id);
        if (targetService != null){
            return targetService;
        }
        else {
            System.out.println("public Service getService(long id) -> returned null");
            return null;
        }
    }

    @Override
    public List<Service> getAllServices() {
        List<Service> allServices = serviceDao.retrieveAllServices();
        if ( ! allServices.isEmpty() ){
            return allServices;
        }
        else {
            System.out.println("public List<Service> getAllServices() -> returned empty list");
            return allServices;
        }
    }

    @Override
    public Service deleteService(long id) {
        Service deletedService = serviceDao.delete(id);
        return deletedService;
    }

    @Override
    public void changeServiceName(long id, String newServiceName) {
        serviceDao.updateSpecifiedServiceField(id, "serviceName", newServiceName);
    }

    @Override
    public void changePrice(long id, int newPrice) {
        serviceDao.updateSpecifiedServiceField(id, "price", newPrice);
    }
}
