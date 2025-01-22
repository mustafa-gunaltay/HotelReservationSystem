package com.mustafa.hotelreservationsystem.services;

import com.mustafa.hotelreservationsystem.dao.AdminDao;
import com.mustafa.hotelreservationsystem.dao.AdminDaoImpl;
import com.mustafa.hotelreservationsystem.exceptions.general.InvalidAdminException;
import com.mustafa.hotelreservationsystem.exceptions.general.InvalidAdminPasswordException;
import com.mustafa.hotelreservationsystem.exceptions.general.InvalidAdminUsernameException;
import com.mustafa.hotelreservationsystem.models.Admin;

import java.util.ArrayList;
import java.util.List;

public class AdminServiceImpl implements AdminService {

    AdminDao adminDao;

    public AdminServiceImpl() {
        adminDao = new AdminDaoImpl();
    }

    // a Admin'inin icinde id degeri birr sey ifade etmeyecek cunku db kisminda auto_increment var
    @Override
    public void createAdmin(Admin a) {
        adminDao.save(a);
    }

    @Override
    public void changefullName(long id, String newFullName) {
        adminDao.updateSpecifiedAdminField(id, "fullName", newFullName);
    }

    @Override
    public void changeUsername(long id, String newUsername) {
        adminDao.updateSpecifiedAdminField(id, "username", newUsername);
    }

    @Override
    public void changePassword(long id, String newPassword) {
        adminDao.updateSpecifiedAdminField(id, "passwordd", newPassword);
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


}
