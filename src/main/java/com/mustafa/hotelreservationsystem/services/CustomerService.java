package com.mustafa.hotelreservationsystem.services;

import com.mustafa.hotelreservationsystem.models.Customer;

import java.time.LocalDate;
import java.util.List;

public interface CustomerService {
    void createCustomer(Customer c);
    Customer getCustomer(long id);
    List<Customer> getAllCustomers();
    Customer deleteCustomer(long id);
    void changeFullName(long id, String newFullName);
    void changePhoneNumber(long id, String newPhoneNumber);
    void changeBirthDate(long id, LocalDate birthDate);
    void changeDescription(long id, String newDescription);
}
