package com.mustafa.hotelreservationsystem.dao;

import com.mustafa.hotelreservationsystem.models.Admin;
import com.mustafa.hotelreservationsystem.models.Entity;
import com.mustafa.hotelreservationsystem.models.Feature;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FeatureDaoImpl implements FeatureDao {

    private static final String URL = "jdbc:mysql://localhost:3306/hotelreservationsystemrd";
    private static final String USER = "root";
    private static final String PASSWORD = "karakama123--";


    // id'yi auto increment yapildigi icin paramtere kismina id almayan varlik kuruculari ile atama yapilmali
    @Override
    public void save(Entity e){

        // id database'de AUTO_INCREMENT oldugundan harici olarak id'yi insert etmeye gerek yok
        Feature newFeature = (Feature) e;
        String featureName = newFeature.getFeatureName();
        int price = newFeature.getPrice();

        String query = "INSERT INTO feature (featureName, price) VALUES (?, ?)";
        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query)
            )
        {
            ps.setString(1, featureName);
            ps.setInt(2, price);

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
        Feature feature = (Feature) e;
        long idOfEntityToBeUpdated = feature.getId();
        String featureName = feature.getFeatureName();
        int price = feature.getPrice();

        String query = "UPDATE feature SET featureName = ?, price = ? WHERE id = ?";
        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query)
            )
        {
            ps.setString(1, featureName);
            ps.setInt(2, price);
            ps.setLong(3, idOfEntityToBeUpdated);

            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " rows affected");

        }
        catch (SQLException ex){
            System.out.println(ex);
        }

    }



    // o id'de bir entity yoksa nolacak senaryosu
    @Override
    public Feature retrieve(long id){

        Feature result = null;

        String query = "SELECT id, featureName, price FROM feature WHERE id = ?";

        try ( Connection conn = DriverManager.getConnection(URL, USER, PASSWORD))
        {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                long targetId = rs.getLong("id");
                String targetFeatureName = rs.getString("featureName");
                int targetPrice = rs.getInt("price");

                result = new Feature(targetId, targetFeatureName, targetPrice);
            }
        }
        catch (SQLException ex){
            System.out.println(ex);
        }

        return result;
    }

    // o id'de bir entity yoksa nolacak senaryosu
    @Override
    public Feature delete(long id){

        Feature deletedFeature = (Feature) retrieve(id);

        String query = "DELETE FROM feature WHERE id = ?";

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

        return deletedFeature;
    }


    @Override
    public List<Feature> retrieveAllFeatures(){

        List<Feature> allFeatures = new ArrayList<>();

        String query = "SELECT id, featureName, price FROM feature";

        try ( Connection conn = DriverManager.getConnection(URL, USER, PASSWORD))
        {
            PreparedStatement ps = conn.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){

                long id = rs.getLong("id");
                String username = rs.getString("featureName");
                int price = rs.getInt("price");

                allFeatures.add(new Feature(id, username, price));
            }
        }
        catch (SQLException ex){
            System.out.println(ex);
        }

        return allFeatures;
    }


    @Override
    public void updateSpecifiedFeatureField(long id, String fieldName, Object fieldValue){

        List<String> allowedFieldNames = Arrays.asList("featureName", "price");

        if (!allowedFieldNames.contains(fieldName)){
            System.out.println("Field: " + fieldName + " is not match any table name of feature");
        }

        String query = "UPDATE feature SET " + fieldName + " = ? WHERE id = ?";
        try(
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query)
           )
        {
            ps.setObject(1, fieldValue);
            ps.setLong(2, id);

            int rowsaffected = ps.executeUpdate();
            System.out.println(rowsaffected + " rows affected");
        }
        catch (SQLException ex){
            System.out.println(ex);
        }

    }
}
