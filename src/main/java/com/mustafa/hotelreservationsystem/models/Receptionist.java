package com.mustafa.hotelreservationsystem.models;

import com.mustafa.hotelreservationsystem.dao.ReceptionistDao;

public class Receptionist extends Entity{
    private String fullName;
    private String username;
    private String passwordd;

    public static Receptionist currentReceptionist;

    public Receptionist(long id, String fullName, String username, String password) {
        super(id);
        this.fullName = fullName;
        this.username = username;
        this.passwordd = password;
    }

    public Receptionist(long id, String username, String password) {
        super(id);
        this.fullName = null;
        this.username = username;
        this.passwordd = password;
    }

    public Receptionist(String fullName, String username, String passwordd) {
        // id = 0
        this.fullName = fullName;
        this.username = username;
        this.passwordd = passwordd;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordd() {
        return passwordd;
    }

    @Override
    public String toString() {
        return "Receptionist{" +
                "fullName='" + fullName + '\'' +
                ", username='" + username + '\'' +
                ", passwordd='" + passwordd + '\'' +
                ", id=" + id +
                '}';
    }
}
