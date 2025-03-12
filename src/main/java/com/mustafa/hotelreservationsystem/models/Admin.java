package com.mustafa.hotelreservationsystem.models;

public class Admin extends Entity{
    private String fullName;
    private String username;
    private String passwordd;

    public static Admin currentAdmin;

    public Admin(long id, String fullName, String username, String password) {
        super(id);
        this.fullName = fullName;
        this.username = username;
        this.passwordd = password;
    }

    public Admin(long id, String username, String password) {
        super(id);
        this.fullName = null;
        this.username = username;
        this.passwordd = password;
    }

    // save metodunda kullanilabilmesi icin her entity'e id si olmayan bir constructor verdim
    public Admin(String fullName, String username, String passwordd) {
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
        return "Admin{" +
                "fullName='" + fullName + '\'' +
                ", username='" + username + '\'' +
                ", passwordd='" + passwordd + '\'' +
                ", id=" + id +
                '}';
    }
}
