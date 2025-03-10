package com.mustafa.hotelreservationsystem.services;

import com.mustafa.hotelreservationsystem.dao.CustomerDao;
import com.mustafa.hotelreservationsystem.dao.CustomerDaoImpl;
import com.mustafa.hotelreservationsystem.exceptions.db.DuplicateEntryException;
import com.mustafa.hotelreservationsystem.exceptions.db.ZeroRowsAffectedOrReturnedException;
import com.mustafa.hotelreservationsystem.exceptions.general.EntityNotFoundByIdException;
import com.mustafa.hotelreservationsystem.exceptions.general.SameEntityValueExistInDbException;
import com.mustafa.hotelreservationsystem.models.Customer;

import java.time.LocalDate;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    private CustomerDao customerDao;

    public CustomerServiceImpl() {
        customerDao = new CustomerDaoImpl();
    }

    @Override
    public void createCustomer(Customer c) throws SameEntityValueExistInDbException{

        try{
            customerDao.save(c);
        } catch (DuplicateEntryException e) {
            throw new SameEntityValueExistInDbException("Phone number already taken", e);
        }
    }

    @Override
    public Customer getCustomer(long id) throws EntityNotFoundByIdException {

        try{
            Customer targetCustomer = customerDao.retrieve(id);
            return targetCustomer;
        } catch (ZeroRowsAffectedOrReturnedException e) {
            throw new EntityNotFoundByIdException("Customer not found by id", e);
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> allCustomers = customerDao.retrieveAllCustomers();
        if ( ! allCustomers.isEmpty() ){
            return allCustomers;
        }
        else {
            System.out.println("public List<Customer> getAllCustomers() -> returned empty list");
            return allCustomers;
        }
    }

    @Override
    public Customer deleteCustomer(long id) throws EntityNotFoundByIdException{

        try {
            Customer deletedCustomer = customerDao.delete(id);
            return deletedCustomer;
        } catch (ZeroRowsAffectedOrReturnedException e) {
            throw new EntityNotFoundByIdException("Customer not found by id", e);
        }
    }

    @Override
    public void changeFullName(long id, String newFullName) throws EntityNotFoundByIdException{

        try{
            customerDao.updateSpecifiedCustomerField(id, "fullName", newFullName);
        }
        catch (DuplicateEntryException e){
            System.out.println(e); // never throws exception because field name is not "phoneNumber"
        } catch (ZeroRowsAffectedOrReturnedException e) {
            throw new EntityNotFoundByIdException("Customer not found by id", e);
        }
    }

    @Override
    public void changePhoneNumber(long id, String newPhoneNumber) throws SameEntityValueExistInDbException, EntityNotFoundByIdException {

        try{
            customerDao.updateSpecifiedCustomerField(id, "phoneNumber", newPhoneNumber);
        } catch (DuplicateEntryException e) {
            throw new SameEntityValueExistInDbException("Phone number already taken", e);
        } catch (ZeroRowsAffectedOrReturnedException e) {
            throw new EntityNotFoundByIdException("Customer not found by id", e);
        }
    }

    @Override
    public void changeBirthDate(long id, LocalDate birthDate) throws EntityNotFoundByIdException {

        try{
            customerDao.updateSpecifiedCustomerField(id, "birthDate", birthDate);
        }
        catch (DuplicateEntryException e){
            System.out.println(e); // never throws exception because field name is not "phoneNumber"
        } catch (ZeroRowsAffectedOrReturnedException e) {
            throw new EntityNotFoundByIdException("Customer not found by id", e);
        }
    }

    @Override
    public void changeDescription(long id, String newDescription) throws EntityNotFoundByIdException {

        try{
            customerDao.updateSpecifiedCustomerField(id, "description", newDescription);
        }
        catch (DuplicateEntryException e){
            System.out.println(e); // never throws exception because field name is not "phoneNumber"
        } catch (ZeroRowsAffectedOrReturnedException e) {
            throw new EntityNotFoundByIdException("Customer not found by id", e);
        }
    }

    @Override
    public boolean isPhoneNumberTaken(String phoneNumber) {
        List<Customer> allCustomers = customerDao.retrieveAllCustomers();
        for (Customer customer : allCustomers) {
            if (customer.getPhoneNumber().equals(phoneNumber)) {
                return true;
            }
        }
        return false;
    }


}
