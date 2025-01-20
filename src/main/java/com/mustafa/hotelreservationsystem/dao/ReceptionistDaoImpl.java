package com.mustafa.hotelreservationsystem.dao;

import com.mustafa.hotelreservationsystem.models.Entity;
import com.mustafa.hotelreservationsystem.models.Receptionist;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReceptionistDaoImpl implements ReceptionistDao{

    private static final String URL = "jdbc:mysql://localhost:3306/hotelreservationsystemrd";
    private static final String USER = "root";
    private static final String PASSWORD = "karakama123--";

    @Override
    public void save(Entity e) {
        Receptionist r = (Receptionist) e;
        String fullName = r.getFullName();
        String username = r.getUsername();
        String passwordd = r.getPasswordd();

        String query = "INSERT INTO receptionist (fullName, username, passwordd) VALUES (?, ?, ?)";
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

    @Override
    public void update(Entity e) {
        Receptionist r = (Receptionist) e;
        long idOfReceptionistToBeUpdated = r.getId();
        String fullName = r.getFullName();
        String username = r.getUsername();
        String passwordd = r.getPasswordd();

        String query = "UPDATE receptionist SET fullName = ?, username = ?, passwordd = ? WHERE id = ?";
        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query);
            )
        {
            ps.setString(1, fullName);
            ps.setString(2, username);
            ps.setString(3, passwordd);
            ps.setLong(4, idOfReceptionistToBeUpdated);

            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " rows affected");
        }
        catch (SQLException ex){
            System.out.println(ex);
        }

    }

    @Override
    public Receptionist retrieve(long id) {

        Receptionist result = null;

        String query = "SELECT fullName, username, passwordd FROM receptionist WHERE id = ?";
        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query);
            )
        {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                String fullName = rs.getString("fullName");
                String username = rs.getString("username");
                String passwordd = rs.getString("passwordd");

                result = new Receptionist(id, fullName, username, passwordd);
            }


        }
        catch (SQLException ex){
            System.out.println(ex);
        }

        return result;
    }

    @Override
    public Receptionist delete(long id) {

        Receptionist deletedReceptionist = retrieve(id);

        String query = "DELETE FROM receptionist WHERE id = ?";

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

        return deletedReceptionist;
    }


    @Override
    public List<Receptionist> retrieveAllReceptionists() {

        List<Receptionist> allReceptionists = new ArrayList<>();

        String query = "SELECT id, fullName, username, passwordd FROM receptionist";

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

                allReceptionists.add(new Receptionist(id, fullName, username, passwordd));
            }
        }
        catch (SQLException ex){
            System.out.println(ex);
        }

        return allReceptionists;
    }

    @Override
    public void updateSpecifiedReceptionistField(long id, String fieldName, Object fieldValue) {

        List<String> allowedFieldNames = Arrays.asList("fullName", "username", "passwordd");

        if (!allowedFieldNames.contains(fieldName)){
            System.out.println("Field: " + fieldName + " is not match any table name of receptionist");
        }

        String query = "UPDATE receptionist SET " + fieldName + " = ? WHERE id = ?";

        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query)
            )
        {
            ps.setObject(1, fieldValue);
            ps.setLong(2, id);

            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " rows affected");
        }
        catch (SQLException ex){
            System.out.println(ex);
        }
    }
}
