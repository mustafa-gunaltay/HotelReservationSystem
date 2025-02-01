package com.mustafa.hotelreservationsystem.services;

import com.mustafa.hotelreservationsystem.exceptions.general.InvalidReceptionistPasswordException;
import com.mustafa.hotelreservationsystem.exceptions.general.InvalidReceptionistUsernameException;
import com.mustafa.hotelreservationsystem.exceptions.general.SameEntityValueExistInDbException;
import com.mustafa.hotelreservationsystem.models.Receptionist;

import java.util.List;

public interface ReceptionistService {
    void createReceptionist(Receptionist r) throws SameEntityValueExistInDbException;
    void changefullName(long id, String newFullName);
    void changeUsername(long id, String newUsername) throws SameEntityValueExistInDbException;
    void changePassword(long id, String newPassword);
    List<Receptionist> getAllReceptionists();
    void validateReceptionist(String username, String pw) throws InvalidReceptionistUsernameException, InvalidReceptionistPasswordException;
    Receptionist deleteReceptionist(long id);
    Receptionist getReceptionist(long id);

}
