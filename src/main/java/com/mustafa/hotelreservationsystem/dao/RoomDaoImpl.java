package com.mustafa.hotelreservationsystem.dao;

import com.mustafa.hotelreservationsystem.models.Entity;
import com.mustafa.hotelreservationsystem.models.Receptionist;
import com.mustafa.hotelreservationsystem.models.Reservation;
import com.mustafa.hotelreservationsystem.models.Room;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class RoomDaoImpl implements RoomDao{

    private static final String URL = "jdbc:mysql://localhost:3306/hotelreservationsystemrd";
    private static final String USER = "root";
    private static final String PASSWORD = "karakama123--";

    @Override
    public void save(Entity e) {
        Room r = (Room) e;
        String roomName = r.getRoomName();
        int capacity = r.getCapacity();
        int price = r.getPrice();
        boolean isReserved = r.isReserved();

        long reservationId = 999;
        if (isReserved){
            reservationId = r.getReservation().getId();
        }


        String query = "INSERT INTO room (roomName, capacity, price, isReserved, reservationId) VALUES (?, ?, ?, ?, ?)";
        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query)
        )
        {
            ps.setString(1, roomName);
            ps.setInt(2, capacity);
            ps.setInt(3, price);
            ps.setBoolean(4, isReserved);
            if (isReserved){
                ps.setLong(5, reservationId);
            }
            else {
                ps.setNull(5, java.sql.Types.INTEGER);
            }

            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " rows affected");
        }
        catch (SQLException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void update(Entity e) {

    }

    @Override
    public Room retrieve(long id) {
        return null;
    }

    @Override
    public Room delete(long id) {
        return null;
    }

    @Override
    public List<Room> retrieveAllRooms() {
        return List.of();
    }

    @Override
    public void updateSpecifiedRoomField(long id, String fieldName, Object fieldValue) {

    }

    @Override
    public boolean linkRoomToReservation(long roomId, long resId) {
        return false;
    }

    @Override
    public boolean bindRoomAndFeature(long roomId, long featureId) {
        return false;
    }

    @Override
    public boolean bindRoomAndService(long roomId, long serviceId) {
        return false;
    }
}
