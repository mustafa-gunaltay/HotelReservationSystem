package com.mustafa.hotelreservationsystem.services;

import com.mustafa.hotelreservationsystem.exceptions.general.EntityNotFoundByIdException;
import com.mustafa.hotelreservationsystem.exceptions.general.ReferencedEntityNotFoundException;
import com.mustafa.hotelreservationsystem.models.Room;

import java.util.List;

public interface RoomService {
    void createRoom(Room r);
    Room getRoom(long id) throws EntityNotFoundByIdException;
    Room deleteRoom(long id) throws EntityNotFoundByIdException;
    List<Room> getAllRooms();
    void changeRoomName(long id, String newRoomName) throws EntityNotFoundByIdException;
    void changeCapacity(long id, long newCapacity) throws EntityNotFoundByIdException;
    void changePrice(long id, long newPrice) throws EntityNotFoundByIdException;

    void addRoomToReservation(long roomId, long resId) throws ReferencedEntityNotFoundException, EntityNotFoundByIdException;
    void deleteRoomFromReservation(long roomId) throws EntityNotFoundByIdException;
    void changeRoomOnReservation(long roomId, long newResId) throws ReferencedEntityNotFoundException, EntityNotFoundByIdException;

    void addFeatureToRoom(long featureId, long roomId) throws ReferencedEntityNotFoundException;
    void deleteFeatureFromRoom(long featureId, long roomId) throws EntityNotFoundByIdException;
    void changeFeatureOnRoom(long oldFeatureId, long newFeatureId, long roomId) throws ReferencedEntityNotFoundException, EntityNotFoundByIdException;

    void addServiceToRoom(long serviceId, long roomId) throws ReferencedEntityNotFoundException;
    void deleteServiceFromRoom(long serviceId, long roomId) throws EntityNotFoundByIdException;
    void changeServiceOnRoom(long oldServiceId, long newServiceId, long roomId) throws ReferencedEntityNotFoundException, EntityNotFoundByIdException;

}
