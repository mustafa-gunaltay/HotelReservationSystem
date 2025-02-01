package com.mustafa.hotelreservationsystem.services;

import com.mustafa.hotelreservationsystem.dao.CustomerDao;
import com.mustafa.hotelreservationsystem.dao.CustomerDaoImpl;
import com.mustafa.hotelreservationsystem.exceptions.db.DuplicateEntryException;
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
    public Customer getCustomer(long id) {
        Customer targetCustomer = customerDao.retrieve(id);
        if (targetCustomer != null){
            return targetCustomer;
        }
        else {
            System.out.println("public Customer getCustomer(long id) -> returned null");
            return null;
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
    public Customer deleteCustomer(long id) {
        Customer deletedCustomer = customerDao.delete(id);
        return deletedCustomer;
    }

    @Override
    public void changeFullName(long id, String newFullName) {

        try{
            customerDao.updateSpecifiedCustomerField(id, "fullName", newFullName);
        }
        catch (Exception e){
            System.out.println(e); // never throws exception because field name is not "phoneNumber"
        }
    }

    @Override
    public void changePhoneNumber(long id, String newPhoneNumber) throws SameEntityValueExistInDbException{

        try{
            customerDao.updateSpecifiedCustomerField(id, "phoneNumber", newPhoneNumber);
        } catch (DuplicateEntryException e) {
            throw new SameEntityValueExistInDbException("Phone number already taken", e);
        }
    }

    @Override
    public void changeBirthDate(long id, LocalDate birthDate) {

        try{
            customerDao.updateSpecifiedCustomerField(id, "birthDate", birthDate);
        }
        catch (Exception e){
            System.out.println(e); // never throws exception because field name is not "phoneNumber"
        }
    }

    @Override
    public void changeDescription(long id, String newDescription) {

        try{
            customerDao.updateSpecifiedCustomerField(id, "description", newDescription);
        }
        catch (Exception e){
            System.out.println(e); // never throws exception because field name is not "phoneNumber"
        }
    }


}
