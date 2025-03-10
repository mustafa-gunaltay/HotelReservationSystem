package com.mustafa.hotelreservationsystem.services;

import com.mustafa.hotelreservationsystem.exceptions.db.ZeroRowsAffectedOrReturnedException;
import com.mustafa.hotelreservationsystem.exceptions.general.EntityNotFoundByIdException;
import com.mustafa.hotelreservationsystem.exceptions.general.SameEntityValueExistInDbException;
import com.mustafa.hotelreservationsystem.models.Customer;

import java.time.LocalDate;
import java.util.List;

public interface CustomerService {
    void createCustomer(Customer c) throws SameEntityValueExistInDbException;
    Customer getCustomer(long id) throws EntityNotFoundByIdException;
    List<Customer> getAllCustomers();
    Customer deleteCustomer(long id) throws EntityNotFoundByIdException;
    void changeFullName(long id, String newFullName) throws EntityNotFoundByIdException;
    void changePhoneNumber(long id, String newPhoneNumber) throws SameEntityValueExistInDbException, EntityNotFoundByIdException;
    void changeBirthDate(long id, LocalDate birthDate) throws EntityNotFoundByIdException;
    void changeDescription(long id, String newDescription) throws EntityNotFoundByIdException;

    boolean isPhoneNumberTaken(String phoneNumber);
}
