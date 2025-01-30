package com.mustafa.hotelreservationsystem.dao;

import com.mustafa.hotelreservationsystem.models.Customer;

import java.util.List;

public interface CustomerDao extends Crudable {
    @Override
    Customer retrieve(long id);
    @Override
    Customer delete(long id);

    List<Customer> retrieveAllCustomers();
    void updateSpecifiedCustomerField(long id, String fieldName, Object fieldValue);
}
