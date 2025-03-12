package com.mustafa.hotelreservationsystem.services;

import com.mustafa.hotelreservationsystem.exceptions.general.*;
import com.mustafa.hotelreservationsystem.models.Receptionist;

import java.util.List;

public interface ReceptionistService {
    void createReceptionist(Receptionist r) throws SameEntityValueExistInDbException;
    void changeFullName(long id, String newFullName) throws EntityNotFoundByIdException;
    void changeUsername(long id, String newUsername) throws SameEntityValueExistInDbException, EntityNotFoundByIdException;
    void changePassword(long id, String newPassword) throws EntityNotFoundByIdException;
    List<Receptionist> getAllReceptionists();
    void validateReceptionist(String username, String pw) throws InvalidReceptionistUsernameException, InvalidReceptionistPasswordException;
    Receptionist deleteReceptionist(long id) throws EntityNotFoundByIdException;
    Receptionist getReceptionist(long id) throws EntityNotFoundByIdException;
    boolean isUsernameTaken(String username);
    Receptionist getReceptionistByUsername(String username) throws EntityNotFoundByUsernameException;

}
