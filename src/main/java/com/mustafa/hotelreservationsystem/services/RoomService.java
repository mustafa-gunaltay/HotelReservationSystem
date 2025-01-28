package com.mustafa.hotelreservationsystem.services;

import com.mustafa.hotelreservationsystem.models.Room;

import java.util.List;

public interface RoomService {
    void createRoom(Room r);
    Room getRoom(long id);
    Room deleteRoom(long id);
    List<Room> getAllRooms();
    void changeRoomName(long id, String newRoomName);
    void changeCapacity(long id, long newCapacity);
    void changePrice(long id, long newPrice);

    void addRoomToReservation(long roomId, long resId);
    void deleteRoomFromReservation(long roomId);
    void changeRoomOnReservation(long roomId, long newResId);

    void addFeatureToRoom(long featureId, long roomId);
    void deleteFeatureFromRoom(long featureId, long roomId);
    void changeFeatureOnRoom(long oldFeatureId, long newFeatureId, long roomId);

    void addServiceToRoom(long serviceId, long roomId);
    void deleteServiceFromRoom(long serviceId, long roomId);
    void changeServiceOnRoom(long oldServiceId, long newServiceId, long roomId);

}
