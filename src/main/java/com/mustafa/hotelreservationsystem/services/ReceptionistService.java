package com.mustafa.hotelreservationsystem.services;

import com.mustafa.hotelreservationsystem.models.Receptionist;

import java.util.List;

public interface ReceptionistService {
    void createReceptionist(Receptionist r);
    void changefullName(long id, String newFullName);
    void changeUsername(long id, String newUsername);
    void changePassword(long id, String newPassword);
    List<Receptionist> getAllReceptionists();
    void validateReceptionist(String username, String pw);

}
