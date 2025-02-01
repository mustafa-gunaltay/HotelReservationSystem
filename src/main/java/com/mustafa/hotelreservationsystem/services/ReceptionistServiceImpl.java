package com.mustafa.hotelreservationsystem.services;

import com.mustafa.hotelreservationsystem.dao.ReceptionistDao;
import com.mustafa.hotelreservationsystem.dao.ReceptionistDaoImpl;
import com.mustafa.hotelreservationsystem.exceptions.db.DuplicateEntryException;
import com.mustafa.hotelreservationsystem.exceptions.general.InvalidReceptionistPasswordException;
import com.mustafa.hotelreservationsystem.exceptions.general.InvalidReceptionistUsernameException;
import com.mustafa.hotelreservationsystem.exceptions.general.SameEntityValueExistInDbException;
import com.mustafa.hotelreservationsystem.models.Admin;
import com.mustafa.hotelreservationsystem.models.Feature;
import com.mustafa.hotelreservationsystem.models.Receptionist;

import java.util.ArrayList;
import java.util.List;

public class ReceptionistServiceImpl implements ReceptionistService{

    ReceptionistDao receptionistDao;

    public ReceptionistServiceImpl() {
        receptionistDao = new ReceptionistDaoImpl();
    }

    @Override
    public void createReceptionist(Receptionist r) throws SameEntityValueExistInDbException {

        try{
            receptionistDao.save(r);
        } catch (DuplicateEntryException e) {
            throw new SameEntityValueExistInDbException("Receptionist username already taken", e);
        }
    }

    @Override
    public void changefullName(long id, String newFullName) {
        try{
            receptionistDao.updateSpecifiedReceptionistField(id, "fullName", newFullName);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void changeUsername(long id, String newUsername) throws SameEntityValueExistInDbException{

        try{
            receptionistDao.updateSpecifiedReceptionistField(id, "username", newUsername);
        } catch (DuplicateEntryException e) {
            throw new SameEntityValueExistInDbException("Receptionist username already taken", e);
        }

    }

    @Override
    public void changePassword(long id, String newPassword) {

        try {
            receptionistDao.updateSpecifiedReceptionistField(id, "passwordd", newPassword);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public List<Receptionist> getAllReceptionists() {
        List<Receptionist> allReceptionists = receptionistDao.retrieveAllReceptionists();
        if ( ! allReceptionists.isEmpty() ){
            return allReceptionists;
        }
        else {
            System.out.println("public List<Receptionist> getAllReceptionists -> returned empty list");
            return allReceptionists;
        }
    }

    @Override
    public void validateReceptionist(String username, String pw) throws InvalidReceptionistUsernameException, InvalidReceptionistPasswordException {
        List<Receptionist> allReceptionists = receptionistDao.retrieveAllReceptionists();
        List<String> usernames = new ArrayList<>();
        List<String> passwords = new ArrayList<>();
        boolean usernameExist = false;
        boolean pwExist = false;

        for (Receptionist r : allReceptionists){
            usernames.add(r.getUsername());
            passwords.add(r.getPasswordd());
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
            throw new InvalidReceptionistUsernameException("No receptionist account found for the provided username: " + username, username);
        }

        if (!pwExist){
            System.out.println("Password does not match with username");
            throw new InvalidReceptionistPasswordException("Username does not match with provided password: " + pw, pw);
        }
    }

    @Override
    public Receptionist deleteReceptionist(long id) {
        Receptionist deletedReceptionist = receptionistDao.delete(id);
        return  deletedReceptionist;
    }

    @Override
    public Receptionist getReceptionist(long id) {
        Receptionist targetReceptionist = receptionistDao.retrieve(id);
        if (targetReceptionist != null){
            return targetReceptionist;
        }
        else{
            System.out.println("public Receptionist getReceptionist(long id) -> returned null");
            return null;
        }
    }
}
