package com.mustafa.hotelreservationsystem.dao;

import com.mustafa.hotelreservationsystem.models.Feature;
import com.mustafa.hotelreservationsystem.models.Room;

import java.util.List;

public interface RoomDao extends Crudable{
    @Override
    Room retrieve(long id);
    @Override
    Room delete(long id);

    List<Room> retrieveAllRooms();
    void updateSpecifiedRoomField(long id, String fieldName, Object fieldValue);
    boolean linkRoomToReservation(long roomId, long resId);
    boolean bindRoomAndFeature(long roomId, long featureId);
    boolean bindRoomAndService(long roomId, long serviceId);
}
