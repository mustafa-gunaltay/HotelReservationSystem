package com.mustafa.hotelreservationsystem.dao;

import com.mustafa.hotelreservationsystem.exceptions.db.DuplicateEntryException;
import com.mustafa.hotelreservationsystem.exceptions.db.ZeroRowsAffectedOrReturnedException;
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
    public void save(Entity e) throws DuplicateEntryException {
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
            if (fullName != null) {
                ps.setString(1, fullName);
            }
            else {
                ps.setNull(1, java.sql.Types.VARCHAR);
            }
            ps.setString(2, username);
            ps.setString(3, passwordd);

            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " rows affected");
        }
        catch (SQLException ex){
            System.out.println(ex);

            int errorCode = ex.getErrorCode();
            if (errorCode == MySqlErrors.DUPLICATE_ENTRY.getCode()){
                throw new DuplicateEntryException("Duplicate Entry for unique field", username);
            }
        }
    }

    @Override
    public void update(Entity e) throws DuplicateEntryException, ZeroRowsAffectedOrReturnedException{
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
            if (fullName != null) {
                ps.setString(1, fullName);
            }
            else {
                ps.setNull(1, java.sql.Types.VARCHAR);
            }
            ps.setString(2, username);
            ps.setString(3, passwordd);
            ps.setLong(4, idOfReceptionistToBeUpdated);

            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " rows affected");
            if (rowsAffected < 1) {
                throw new ZeroRowsAffectedOrReturnedException("Zero rows affected on UPDATE", idOfReceptionistToBeUpdated);
            }

        }
        catch (SQLException ex){
            System.out.println(ex);

            int errorCode = ex.getErrorCode();
            if (errorCode == MySqlErrors.DUPLICATE_ENTRY.getCode()){
                throw new DuplicateEntryException("Duplicate Entry for unique field", username);
            }
        }

    }

    @Override
    public Receptionist retrieve(long id) throws ZeroRowsAffectedOrReturnedException {

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

        if (result == null) {
            throw new ZeroRowsAffectedOrReturnedException("Zero rows returned on SELECT", id);
        }
        else {
            return result;
        }

    }

    @Override
    public Receptionist delete(long id) throws ZeroRowsAffectedOrReturnedException{

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
            if (rowsAffected < 1) {
                throw new ZeroRowsAffectedOrReturnedException("Zero rows affected on DELETE", id);
            }

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
    public void updateSpecifiedReceptionistField(long id, String fieldName, Object fieldValue) throws DuplicateEntryException, ZeroRowsAffectedOrReturnedException {

        List<String> allowedFieldNames = Arrays.asList("fullName", "username", "passwordd");

        if (!allowedFieldNames.contains(fieldName)){
            System.out.println("Field: " + fieldName + " is not match any table name of receptionist");
            return;
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
            if (rowsAffected < 1) {
                throw new ZeroRowsAffectedOrReturnedException("Zero rows affected on UPDATE", id);
            }
        }
        catch (SQLException ex){
            System.out.println(ex);

            int errorCode = ex.getErrorCode();
            if (errorCode == MySqlErrors.DUPLICATE_ENTRY.getCode()){
                if (fieldValue instanceof String && fieldName.equals("username")) {
                    throw new DuplicateEntryException("Duplicate Entry for unique field", (String) fieldValue);
                }
            }
        }
    }
}
