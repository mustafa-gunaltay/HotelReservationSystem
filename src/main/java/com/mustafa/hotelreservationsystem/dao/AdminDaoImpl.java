package com.mustafa.hotelreservationsystem.dao;

import com.mustafa.hotelreservationsystem.models.Admin;
import com.mustafa.hotelreservationsystem.models.Entity;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdminDaoImpl implements AdminDao {

    private static final String URL = "jdbc:mysql://localhost:3306/hotelreservationsystemrd";
    private static final String USER = "root";
    private static final String PASSWORD = "karakama123--";

    // id'yi auto increment yapildigi icin paramtere kismina id almayan varlik kuruculari ile atama yapilmali
    @Override
    public void save(Entity e){

        // id database'de AUTO_INCREMENT oldugundan harici olarak id'yi insert etmeye gerek yok
        Admin newAdmin = (Admin) e;
        String fullName = newAdmin.getFullName();
        String username = newAdmin.getUsername();
        String passwordd = newAdmin.getPasswordd();

        String query = "INSERT INTO admin (fullName, username, passwordd) VALUES (?, ?, ?)";
        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query)
            )
        {
            ps.setString(1, fullName);
            ps.setString(2, username);
            ps.setString(3, passwordd);


            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " rows affected");

        }
        catch (SQLException ex){
            System.out.println(ex);
        }

    }


    // o id'de bir entity yoksa nolacak senaryosu
    @Override
    public void update(Entity e){

        Admin admin = (Admin) e;
        long idOfAdminToBeUpdated = admin.getId();
        String fullName = admin.getFullName();
        String username = admin.getUsername();
        String passwordd = admin.getPasswordd();

        String query = "UPDATE admin SET fullName = ?, username = ?, passwordd = ? WHERE id = ?";
        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query)
            )
        {
            ps.setString(1, fullName);
            ps.setString(2, username);
            ps.setString(3, passwordd);
            ps.setLong(4, idOfAdminToBeUpdated);

            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " rows affected");

        }
        catch (SQLException ex){
            System.out.println(ex);
        }

    }


    // o id'de bir entity yoksa nolacak senaryosu
    @Override
    public Admin retrieve(long id){

        Admin result = null;

        String query = "SELECT fullName, username, passwordd FROM admin WHERE id = ?";

        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query)
            )
        {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){

                String fullName = rs.getString("fullName");
                String username = rs.getString("username");
                String passwordd = rs.getString("passwordd");

                result = new Admin(id, fullName, username, passwordd);
            }
        }
        catch (SQLException ex){
            System.out.println(ex);
        }

        return result;
    }



    // o id'de bir entity yoksa nolacak senaryosu
    @Override
    public Admin delete(long id){

        // retriece den firlatilan exception i yonet veya firlat
        Admin deletedAdmin = retrieve(id);

        String query = "DELETE FROM admin WHERE id = ?";

        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query)
            )
        {

            ps.setLong(1, id);

            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " rows affected");

        }
        catch (SQLException ex){
            System.out.println(ex);
        }

        return deletedAdmin;
    }


    // donen admin listesi bossa ne olacak
    @Override
    public List<Admin> retrieveAllAdmins(){

        List<Admin> allAdmins = new ArrayList<>();

        String query = "SELECT id, fullName, username, passwordd FROM admin";

        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query)
            )
        {

            ResultSet rs = ps.executeQuery();

            while (rs.next()){

                long id = rs.getLong("id");
                String fullName = rs.getString("fullName");
                String username = rs.getString("username");
                String passwordd = rs.getString("passwordd");

                allAdmins.add(new Admin(id, fullName, username, passwordd));
            }
        }
        catch (SQLException ex){
            System.out.println(ex);
        }

        return allAdmins;
    }


    // bu id de hic bir adminin olmamasi durumu
    @Override
    public void updateSpecifiedAdminField(long id, String fieldName, Object fieldValue) {

        List<String> allowedFields = Arrays.asList("fullName", "username", "passwordd");

        if (!allowedFields.contains(fieldName)) {
            System.out.println("Field: " + fieldName + " is not match any table name of admin");
            // exception firlat
        }

        String query = "UPDATE admin SET " + fieldName + " = ? WHERE id = ?";

        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query)
            )
        {
                ps.setObject(1, fieldValue);
                ps.setLong(2, id);

                int rows = ps.executeUpdate();
                System.out.println(rows + " rows affected");
        }
        catch (SQLException ex) {
            System.out.println(ex);
        }
    }

}
