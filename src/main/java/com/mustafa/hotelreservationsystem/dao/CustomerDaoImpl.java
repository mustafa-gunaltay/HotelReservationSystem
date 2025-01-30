package com.mustafa.hotelreservationsystem.dao;

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


    @Override
    public void save(Entity e) {
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
            ps.setString(4, description);

            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " rows affected");

        }
        catch (SQLException ex){
            System.out.println(ex);
        }

    }

    @Override
    public void update(Entity e) {

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
            ps.setString(4, description);
            ps.setLong(5, id);

            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " rows affected");
        }
        catch (SQLException ex){
            System.out.println(ex);
        }

    }

    @Override
    public Customer retrieve(long id) {

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

        return result;
    }

    @Override
    public Customer delete(long id) {

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
    public void updateSpecifiedCustomerField(long id, String fieldName, Object fieldValue) {

        List<String> allowedFields = List.of("fullName", "phoneNumber", "birthDate", "description");

        if ( ! allowedFields.contains(fieldName)) {
            System.out.println("Field: " + fieldName + " is not match any table name of customer");
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
        }
        catch (SQLException ex){
            System.out.println(ex);
        }
    }
}
