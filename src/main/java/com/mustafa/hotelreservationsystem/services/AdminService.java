package com.mustafa.hotelreservationsystem.services;

import com.mustafa.hotelreservationsystem.exceptions.general.*;
import com.mustafa.hotelreservationsystem.models.Admin;

import java.util.List;

public interface AdminService {
    void createAdmin(Admin a) throws SameEntityValueExistInDbException;
    void changeFullName(long id, String newFullName) throws EntityNotFoundByIdException;
    void changeUsername(long id, String newUsername) throws SameEntityValueExistInDbException, EntityNotFoundByIdException;
    void changePassword(long id, String newPassword) throws EntityNotFoundByIdException;
    void validateAdmin(String username, String pw) throws InvalidAdminUsernameException, InvalidAdminPasswordException;
    Admin getAdmin(long id) throws EntityNotFoundByIdException;
    List<Admin> getAllAdmins();

    Admin getAdminByUsername(String username) throws EntityNotFoundByUsernameException;
}
