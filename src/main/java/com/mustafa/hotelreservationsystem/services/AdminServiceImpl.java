package com.mustafa.hotelreservationsystem.services;

import com.mustafa.hotelreservationsystem.dao.AdminDao;
import com.mustafa.hotelreservationsystem.dao.AdminDaoImpl;
import com.mustafa.hotelreservationsystem.exceptions.db.DuplicateEntryException;
import com.mustafa.hotelreservationsystem.exceptions.db.ZeroRowsAffectedOrReturnedException;
import com.mustafa.hotelreservationsystem.exceptions.general.*;
import com.mustafa.hotelreservationsystem.models.Admin;
import com.mustafa.hotelreservationsystem.models.Receptionist;

import java.util.ArrayList;
import java.util.List;

public class AdminServiceImpl implements AdminService {

    AdminDao adminDao;

    public AdminServiceImpl() {
        adminDao = new AdminDaoImpl();
    }

    // a Admin'inin icinde id degeri birr sey ifade etmeyecek cunku db kisminda auto_increment var
    @Override
    public void createAdmin(Admin a) throws SameEntityValueExistInDbException{

        try{
            adminDao.save(a);
        } catch (DuplicateEntryException e) {
            throw new SameEntityValueExistInDbException("Admin username already taken", e);
        }
    }

    @Override
    public void changeFullName(long id, String newFullName) throws EntityNotFoundByIdException {

        try{
            adminDao.updateSpecifiedAdminField(id, "fullName", newFullName);
        } catch (DuplicateEntryException e) {
            System.out.println(e); // never thrown
        } catch (ZeroRowsAffectedOrReturnedException e) {
            throw new EntityNotFoundByIdException("Admin not found by id", e);
        }

    }

    @Override
    public void changeUsername(long id, String newUsername) throws SameEntityValueExistInDbException, EntityNotFoundByIdException {

        try{
            adminDao.updateSpecifiedAdminField(id, "username", newUsername);
        } catch (DuplicateEntryException e) {
            throw new SameEntityValueExistInDbException("Admin username already taken", e);
        } catch (ZeroRowsAffectedOrReturnedException e) {
            throw new EntityNotFoundByIdException("Admin not found by id", e);
        }

    }

    @Override
    public void changePassword(long id, String newPassword) throws EntityNotFoundByIdException {

        try{
            adminDao.updateSpecifiedAdminField(id, "passwordd", newPassword);;
        } catch (DuplicateEntryException e) {
            System.out.println(e); // never thrown
        } catch (ZeroRowsAffectedOrReturnedException e) {
            throw new EntityNotFoundByIdException("Admin not found by id", e);
        }

    }


    // istenildigi yerde cagrilacak, exception firlatmamasi demek bu username ve pw'nin valid oldugu anlamina gelecek
    @Override
    public void validateAdmin(String username, String pw) throws InvalidAdminUsernameException, InvalidAdminPasswordException {
        List<Admin> allAdmins = adminDao.retrieveAllAdmins();
        List<String> usernames = new ArrayList<>();
        List<String> passwords = new ArrayList<>();
        boolean usernameExist = false;
        boolean pwExist = false;

        for (Admin a : allAdmins){
            usernames.add(a.getUsername());
            passwords.add(a.getPasswordd());
        }

        for (String userN : usernames){
            if (userN.equals(username)){
                usernameExist = true;
                break;
            }
        }
        for (String pww : passwords){
            if (pww.equals(pw)){
                pwExist = true;
                break;
            }
        }

        if (!usernameExist){
            System.out.println("Username does not exist in system");
            throw new InvalidAdminUsernameException("No admin account found for the provided username: " + username, username);
        }

        if (!pwExist){
            System.out.println("Password does not match with username");
            throw new InvalidAdminPasswordException("Username does not match with provided password: " + pw, pw);
        }

    }

    @Override
    public Admin getAdmin(long id) throws EntityNotFoundByIdException{
        try{
            Admin targetAdmin = adminDao.retrieve(id);
            return targetAdmin;
        }
        catch (ZeroRowsAffectedOrReturnedException e){
            throw new EntityNotFoundByIdException("Admin not found by id", e);
        }

//        if (targetAdmin != null){
//            return targetAdmin;
//        }
//        else {
//            System.out.println("public Admin getAdmin(long id) returned null");
//            return null;
//        }
    }

    @Override
    public List<Admin> getAllAdmins() {
        List<Admin> allAdmins = adminDao.retrieveAllAdmins();
        if ( ! allAdmins.isEmpty() ){
            return allAdmins;
        }
        else {
            System.out.println("public List<Admin> getAllAdmins() returned empty list");
            return allAdmins;
        }
    }


    @Override
    public Admin getAdminByUsername(String username) throws EntityNotFoundByUsernameException {

        try{
            Admin result = adminDao.retrieveAdminByUsername(username);
            return result;
        }
        catch (ZeroRowsAffectedOrReturnedException e) {
            throw new EntityNotFoundByUsernameException("Admin not found", username, e);
        }
    }

}
