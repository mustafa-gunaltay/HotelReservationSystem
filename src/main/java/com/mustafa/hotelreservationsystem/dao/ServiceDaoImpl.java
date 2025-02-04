package com.mustafa.hotelreservationsystem.dao;

import com.mustafa.hotelreservationsystem.exceptions.db.ZeroRowsAffectedOrReturnedException;
import com.mustafa.hotelreservationsystem.models.Entity;
import com.mustafa.hotelreservationsystem.models.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceDaoImpl implements ServiceDao {

    private static final String URL = "jdbc:mysql://localhost:3306/hotelreservationsystemrd";
    private static final String USER = "root";
    private static final String PASSWORD = "karakama123--";

    @Override
    public void save(Entity e) {

        Service s = (Service) e;

        String serviceName = s.getServiceName();
        int price = s.getPrice();

        String query = "INSERT INTO service (serviceName, price) VALUES (?, ?)";

        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query)
        )
        {
            ps.setString(1, serviceName);
            ps.setInt(2, price);

            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " rows affected");

        }
        catch (SQLException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void update(Entity e) throws ZeroRowsAffectedOrReturnedException {

        Service s = (Service) e;

        long id = e.getId();
        String serviceName = s.getServiceName();
        int price = s.getPrice();

        String query = "UPDATE service SET serviceName = ?, price = ? WHERE id = ?";

        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query)
        )
        {
             ps.setString(1, serviceName);
             ps.setInt(2, price);
             ps.setLong(3, id);

             int rowsAffected = ps.executeUpdate();
             System.out.println(rowsAffected + " rows affected");
             if (rowsAffected < 1) {
                 throw new ZeroRowsAffectedOrReturnedException("Zero rows affected on UPDATE", id);
             }

        }
        catch (SQLException ex){
            System.out.println(ex);
        }

    }


    @Override
    public Service retrieve(long id) throws ZeroRowsAffectedOrReturnedException {

        Service result = null;

        String query = "SELECT serviceName, price FROM service WHERE id = ?";

        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query)
        )
        {
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String serviceName = rs.getString("serviceName");
                int price = rs.getInt("price");

                result = new Service(id, serviceName, price);
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
    public Service delete(long id) throws ZeroRowsAffectedOrReturnedException {

        Service deletedService = retrieve(id);

        String query = "DELETE FROM service WHERE id = ?";

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

        return deletedService;
    }

    @Override
    public List<Service> retrieveAllServices() {

        List<Service> allServices = new ArrayList<Service>();

        String query = "SELECT id, serviceName, price FROM service";

        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query)
        )
        {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("id");
                String serviceName = rs.getString("serviceName");
                int price = rs.getInt("price");

                allServices.add(new Service(id, serviceName, price));
            }
        }
        catch (SQLException ex){
            System.out.println(ex);
        }

        return allServices;
    }

    @Override
    public void updateSpecifiedServiceField(long id, String fieldName, Object fieldValue) throws ZeroRowsAffectedOrReturnedException {

        List<String> allowedFields = List.of("serviceName", "price");

        if ( ! allowedFields.contains(fieldName) ) {
            System.out.println("Field: " + fieldName + " is not match any table name of service");
        }


        String query = "UPDATE service SET " + fieldName + " = ? WHERE id = ?";

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
        }

    }


}
