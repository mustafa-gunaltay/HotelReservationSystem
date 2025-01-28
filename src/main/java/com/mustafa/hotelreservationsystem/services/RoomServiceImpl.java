package com.mustafa.hotelreservationsystem.services;

import com.mustafa.hotelreservationsystem.dao.RoomDao;
import com.mustafa.hotelreservationsystem.dao.RoomDaoImpl;
import com.mustafa.hotelreservationsystem.models.Room;

import java.util.List;

public class RoomServiceImpl implements RoomService{

    private RoomDao roomDao;

    public RoomServiceImpl() {
        roomDao = new RoomDaoImpl();
    }

    @Override
    public void createRoom(Room r) {
        roomDao.save(r);
    }

    @Override
    public Room getRoom(long id) {
        Room targetRoom = roomDao.retrieve(id);
        if (targetRoom != null){
            return targetRoom;
        }
        else {
            System.out.println("public Room getRoom(long id) -> returned null");
            return null;
        }
    }

    @Override
    public Room deleteRoom(long id) {
        Room deletedRoom = roomDao.delete(id);
        return deletedRoom;
    }

    @Override
    public List<Room> getAllRooms() {
        List<Room> allRooms = roomDao.retrieveAllRooms();
        if ( ! allRooms.isEmpty() ){
            return allRooms;
        }
        else {
            System.out.println("public List<Room> getAllRooms() -> returned empty list");
            return allRooms;
        }
    }

    @Override
    public void changeRoomName(long id, String newRoomName) {
        roomDao.updateSpecifiedRoomField(id, "roomName", newRoomName);
    }

    @Override
    public void changeCapacity(long id, long newCapacity) {
        roomDao.updateSpecifiedRoomField(id, "capacity", newCapacity);
    }

    @Override
    public void changePrice(long id, long newPrice) {
        roomDao.updateSpecifiedRoomField(id, "price", newPrice);
    }

    @Override
    public void addRoomToReservation(long roomId, long resId) {
        roomDao.linkRoomToReservation(roomId, resId);
        // isReserved is set true on linkRoomToReservation() method
    }

    @Override
    public void deleteRoomFromReservation(long roomId) {
        roomDao.unlinkRoomFromReservation(roomId);
        // isReserved is set false on linkRoomToReservation() method
    }

    @Override
    public void changeRoomOnReservation(long roomId, long newResId) {
        roomDao.linkRoomToReservation(roomId, newResId);
    }

    @Override
    public void addFeatureToRoom(long featureId, long roomId) {
        roomDao.bindRoomAndFeature(roomId, featureId);
    }

    @Override
    public void deleteFeatureFromRoom(long featureId, long roomId) {
        roomDao.unbindRoomAndFeature(roomId, featureId);
    }

    @Override
    public void changeFeatureOnRoom(long oldFeatureId, long newFeatureId, long roomId) {
        roomDao.unbindRoomAndFeature(roomId, oldFeatureId);
        roomDao.bindRoomAndFeature(roomId, newFeatureId);
    }

    @Override
    public void addServiceToRoom(long serviceId, long roomId) {
        roomDao.bindRoomAndService(roomId, serviceId);
    }

    @Override
    public void deleteServiceFromRoom(long serviceId, long roomId) {
        roomDao.unbindRoomAndService(roomId, serviceId);
    }

    @Override
    public void changeServiceOnRoom(long oldServiceId, long newServiceId, long roomId) {
        roomDao.unbindRoomAndService(roomId, oldServiceId);
        roomDao.bindRoomAndService(roomId, newServiceId);
    }
}
