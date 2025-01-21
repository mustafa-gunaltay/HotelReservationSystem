package com.mustafa.hotelreservationsystem.services;

import com.mustafa.hotelreservationsystem.exceptions.general.InvalidAdminException;
import com.mustafa.hotelreservationsystem.exceptions.general.InvalidAdminPasswordException;
import com.mustafa.hotelreservationsystem.exceptions.general.InvalidAdminUsernameException;
import com.mustafa.hotelreservationsystem.models.Admin;

public interface AdminService {
    void createAdmin(Admin a);
    void changefullName(long id, String newFullName);
    void changeUsername(long id, String newUsername);
    void changePassword(long id, String newPassword);
    void validateAdmin(String username, String pw) throws InvalidAdminUsernameException, InvalidAdminPasswordException;
}
