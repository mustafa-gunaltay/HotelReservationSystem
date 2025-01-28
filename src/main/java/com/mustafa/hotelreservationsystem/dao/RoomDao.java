package com.mustafa.hotelreservationsystem.dao;

import com.mustafa.hotelreservationsystem.models.Room;

import java.util.List;

public interface RoomDao extends Crudable{
    @Override
    Room retrieve(long id);
    @Override
    Room delete(long id);

    List<Room> retrieveAllRooms();
    void updateSpecifiedRoomField(long id, String fieldName, Object fieldValue);
    void linkRoomToReservation(long roomId, long resId);
    void unlinkRoomFromReservation(long roomId);
    void bindRoomAndFeature(long roomId, long featureId);
    void unbindRoomAndFeature(long roomId, long featureId);
    void bindRoomAndService(long roomId, long serviceId);
    void unbindRoomAndService(long roomId, long serviceId);
}
