package com.mustafa.hotelreservationsystem.dao;

import com.mustafa.hotelreservationsystem.exceptions.db.DuplicateEntryException;
import com.mustafa.hotelreservationsystem.models.Customer;
import com.mustafa.hotelreservationsystem.models.Entity;

import java.util.List;

public interface CustomerDao extends Crudable {
    @Override
    void save(Entity e) throws DuplicateEntryException;
    @Override
    void update(Entity e) throws DuplicateEntryException;
    @Override
    Customer retrieve(long id);
    @Override
    Customer delete(long id);

    List<Customer> retrieveAllCustomers();
    void updateSpecifiedCustomerField(long id, String fieldName, Object fieldValue) throws DuplicateEntryException;
}
