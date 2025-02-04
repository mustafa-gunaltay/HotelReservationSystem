package com.mustafa.hotelreservationsystem.dao;

import com.mustafa.hotelreservationsystem.exceptions.db.DuplicateEntryException;
import com.mustafa.hotelreservationsystem.exceptions.db.ZeroRowsAffectedOrReturnedException;
import com.mustafa.hotelreservationsystem.models.Customer;
import com.mustafa.hotelreservationsystem.models.Entity;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao{

    private static final String URL = "jdbc:mysql://localhost:3306/hotelreservationsystemrd";
    private static final String USER = "root";
    private static final String PASSWORD = "karakama123--";


    // verilen phone number ile onceden sistemde kayitli baska bir phone number ayni ise (phoneNumber -> unique)
    @Override
    public void save(Entity e) throws DuplicateEntryException {
        Customer c = (Customer) e;

        String fullName = c.getFullName();
        String phoneNumber = c.getPhoneNumber();
        LocalDate birthDate = c.getBirthDate();
        String description = c.getDescription();

        String query = "INSERT INTO customer (fullName, phoneNumber, birthDate, description) VALUES (?, ?, ? ,?)";
        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query)
        )
        {
            ps.setString(1, fullName);
            ps.setString(2, phoneNumber);
            ps.setDate(3, Date.valueOf(birthDate));
            if (description != null) {
                ps.setString(4, description);
            }
            else {
                ps.setNull(4, Types.VARCHAR);
            }

            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " rows affected");

        }
        catch (SQLException ex){
            System.out.println(ex);

            int errorCode = ex.getErrorCode();

            if (errorCode == MySqlErrors.DUPLICATE_ENTRY.getCode()){
                throw new DuplicateEntryException("Duplicate Entry for unique field", phoneNumber);
            }
        }

    }

    @Override
    public void update(Entity e) throws DuplicateEntryException, ZeroRowsAffectedOrReturnedException {

        Customer c = (Customer) e;

        long id = c.getId();
        String fullName = c.getFullName();
        String phoneNumber = c.getPhoneNumber();
        LocalDate birthDate = c.getBirthDate();
        String description = c.getDescription();

        String query = "UPDATE customer SET fullName = ?, phoneNumber = ?, birthDate = ?, description = ? WHERE id = ?";

        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query)
        )
        {
            ps.setString(1, fullName);
            ps.setString(2, phoneNumber);
            ps.setDate(3, Date.valueOf(birthDate));
            if (description != null) {
                ps.setString(4, description);
            }
            else {
                ps.setNull(4, Types.VARCHAR);
            }
            ps.setLong(5, id);

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
                throw new DuplicateEntryException("Duplicate Entry for unique field", phoneNumber);
            }
        }

    }

    @Override
    public Customer retrieve(long id) throws ZeroRowsAffectedOrReturnedException {

        Customer result = null;

        String query = "SELECT id, fullName, phoneNumber, birthDate, description FROM customer WHERE id = ?";

        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query)
        )
        {
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                long targetId = rs.getLong("id");
                String fullName = rs.getString("fullName");
                String phoneNumber = rs.getString("phoneNumber");
                LocalDate birthDate = rs.getDate("birthDate").toLocalDate();
                String description = rs.getString("description");

                result = new Customer(targetId, fullName, phoneNumber, birthDate, description);
            }

        }
        catch (SQLException ex){
            System.out.println(ex);
        }

        if (result == null) {
            throw new ZeroRowsAffectedOrReturnedException("Zero rows returned on SELECT", id);
        }
        else{
            return result;
        }

    }

    @Override
    public Customer delete(long id) throws ZeroRowsAffectedOrReturnedException {

        Customer deletedCustomer = retrieve(id);

        String query = "DELETE FROM customer WHERE id = ?";

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

        return deletedCustomer;
    }

    @Override
    public List<Customer> retrieveAllCustomers() {

        List<Customer> allCustomers = new ArrayList<>();

        String query = "SELECT id, fullName, phoneNumber, birthDate, description FROM customer";

        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query)
        )
        {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("id");
                String fullName = rs.getString("fullName");
                String phoneNumber = rs.getString("phoneNumber");
                LocalDate birthDate = rs.getDate("birthDate").toLocalDate();
                String description = rs.getString("description");

                allCustomers.add(new Customer(id, fullName, phoneNumber, birthDate, description));
            }
        }
        catch (SQLException ex){
            System.out.println(ex);
        }

        return allCustomers;

    }

    @Override
    public void updateSpecifiedCustomerField(long id, String fieldName, Object fieldValue) throws DuplicateEntryException, ZeroRowsAffectedOrReturnedException {

        List<String> allowedFields = List.of("fullName", "phoneNumber", "birthDate", "description");

        if ( ! allowedFields.contains(fieldName)) {
            System.out.println("Field: " + fieldName + " is not match any table name of customer");
            return;
        }

        String query = "UPDATE customer SET " + fieldName + " = ? WHERE id = ?";

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
                if (fieldValue instanceof String && fieldName.equals("phoneNumber")) {
                    throw new DuplicateEntryException("Duplicate Entry for unique field", (String) fieldValue);
                }
            }
        }
    }
}
