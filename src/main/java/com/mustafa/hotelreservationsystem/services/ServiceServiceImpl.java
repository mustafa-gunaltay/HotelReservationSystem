package com.mustafa.hotelreservationsystem.services;

import com.mustafa.hotelreservationsystem.dao.ServiceDao;
import com.mustafa.hotelreservationsystem.dao.ServiceDaoImpl;
import com.mustafa.hotelreservationsystem.exceptions.db.ZeroRowsAffectedOrReturnedException;
import com.mustafa.hotelreservationsystem.exceptions.general.EntityNotFoundByIdException;
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
    public Service getService(long id) throws EntityNotFoundByIdException {

        try{
            Service targetService = serviceDao.retrieve(id);
            return targetService;
        } catch (ZeroRowsAffectedOrReturnedException e) {
            throw new EntityNotFoundByIdException("Service not found by id", e);
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
    public Service deleteService(long id) throws EntityNotFoundByIdException {

        try {
            Service deletedService = serviceDao.delete(id);
            return deletedService;
        }
        catch (ZeroRowsAffectedOrReturnedException e) {
            throw new EntityNotFoundByIdException("Service not found by id", e);
        }
    }

    @Override
    public void changeServiceName(long id, String newServiceName) throws EntityNotFoundByIdException {
        try {
            serviceDao.updateSpecifiedServiceField(id, "serviceName", newServiceName);
        } catch (ZeroRowsAffectedOrReturnedException e) {
            throw new EntityNotFoundByIdException("Service not found by id", e);
        }
    }

    @Override
    public void changePrice(long id, int newPrice) throws EntityNotFoundByIdException {
        try {
            serviceDao.updateSpecifiedServiceField(id, "price", newPrice);
        } catch (ZeroRowsAffectedOrReturnedException e) {
            throw new EntityNotFoundByIdException("Service not found by id", e);
        }
    }
}
