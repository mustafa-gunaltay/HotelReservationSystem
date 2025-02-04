package com.mustafa.hotelreservationsystem.services;

import com.mustafa.hotelreservationsystem.dao.RoomDao;
import com.mustafa.hotelreservationsystem.dao.RoomDaoImpl;
import com.mustafa.hotelreservationsystem.exceptions.db.NoReferencedRowException;
import com.mustafa.hotelreservationsystem.exceptions.db.ZeroRowsAffectedOrReturnedException;
import com.mustafa.hotelreservationsystem.exceptions.general.EntityNotFoundByIdException;
import com.mustafa.hotelreservationsystem.exceptions.general.ReferencedEntityNotFoundException;
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
    public Room getRoom(long id) throws EntityNotFoundByIdException {

        try{
            Room targetRoom = roomDao.retrieve(id);
            return targetRoom;
        } catch (ZeroRowsAffectedOrReturnedException e) {
            throw new EntityNotFoundByIdException("Room not found by id", e);
        }

    }

    @Override
    public Room deleteRoom(long id) throws EntityNotFoundByIdException {

        try {
            Room deletedRoom = roomDao.delete(id);
            return deletedRoom;
        }
        catch (ZeroRowsAffectedOrReturnedException e) {
            throw new EntityNotFoundByIdException("Room not found by id", e);
        }
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
    public void changeRoomName(long id, String newRoomName) throws EntityNotFoundByIdException {

        try{
            roomDao.updateSpecifiedRoomField(id, "roomName", newRoomName);
        }
        catch (NoReferencedRowException e){
            System.out.println(e); // never thrown because it only throws exception when fieldName is "reservationId"
        } catch (ZeroRowsAffectedOrReturnedException e) {
            throw new EntityNotFoundByIdException("Room not found by id", e);
        }
    }

    @Override
    public void changeCapacity(long id, long newCapacity) throws EntityNotFoundByIdException {

        try{
            roomDao.updateSpecifiedRoomField(id, "capacity", newCapacity);
        }
        catch (NoReferencedRowException e){
            System.out.println(e); // never thrown because it only throws exception when fieldName is "reservationId"
        } catch (ZeroRowsAffectedOrReturnedException e) {
            throw new EntityNotFoundByIdException("Room not found by id", e);
        }
    }

    @Override
    public void changePrice(long id, long newPrice) throws EntityNotFoundByIdException {

        try{
            roomDao.updateSpecifiedRoomField(id, "price", newPrice);
        }
        catch (NoReferencedRowException e){
            System.out.println(e); // never thrown because it only throws exception when fieldName is "reservationId"
        } catch (ZeroRowsAffectedOrReturnedException e) {
            throw new EntityNotFoundByIdException("Room not found by id", e);
        }
    }

    @Override
    public void addRoomToReservation(long roomId, long resId) throws ReferencedEntityNotFoundException, EntityNotFoundByIdException {

        try{
            roomDao.linkRoomToReservation(roomId, resId);
            // isReserved is set true on linkRoomToReservation() method
        } catch (NoReferencedRowException e) {
            throw new ReferencedEntityNotFoundException("Reservation not found", e);
        } catch (ZeroRowsAffectedOrReturnedException e) {
            throw new EntityNotFoundByIdException("Room not found by id", e);
        }

    }

    @Override
    public void deleteRoomFromReservation(long roomId) throws EntityNotFoundByIdException{
        try {
            roomDao.unlinkRoomFromReservation(roomId);
            // isReserved is set false on linkRoomToReservation() method
        } catch (ZeroRowsAffectedOrReturnedException e) {
            throw new EntityNotFoundByIdException("Room not found by id", e);
        }

    }

    @Override
    public void changeRoomOnReservation(long roomId, long newResId) throws ReferencedEntityNotFoundException, EntityNotFoundByIdException {

        try{
            roomDao.linkRoomToReservation(roomId, newResId);
        } catch (NoReferencedRowException e) {
            throw new ReferencedEntityNotFoundException("Reservation to be changed instead is not found", e);
        } catch (ZeroRowsAffectedOrReturnedException e) {
            throw new EntityNotFoundByIdException("Room not found by id", e);
        }
    }

    @Override
    public void addFeatureToRoom(long featureId, long roomId) throws ReferencedEntityNotFoundException {
        try {
            roomDao.bindRoomAndFeature(roomId, featureId);
        } catch (NoReferencedRowException e) {
            throw new ReferencedEntityNotFoundException("Room or Feature not found", e);
        }
    }

    @Override
    public void deleteFeatureFromRoom(long featureId, long roomId) throws EntityNotFoundByIdException{
        try {
            roomDao.unbindRoomAndFeature(roomId, featureId);
        } catch (ZeroRowsAffectedOrReturnedException e) {
            throw new EntityNotFoundByIdException("Room and Feature pair not found by their id", e);
        }
    }

    @Override
    public void changeFeatureOnRoom(long oldFeatureId, long newFeatureId, long roomId) throws ReferencedEntityNotFoundException, EntityNotFoundByIdException {

        try {
            roomDao.bindRoomAndFeature(roomId, newFeatureId);
        } catch (NoReferencedRowException e) {
            throw new ReferencedEntityNotFoundException("Feature that will be changed instead or Room is not found", e);
        }

        try {
            roomDao.unbindRoomAndFeature(roomId, oldFeatureId);
        } catch (ZeroRowsAffectedOrReturnedException e) {
            throw new EntityNotFoundByIdException("Room and Feature pair not found by their id", e);
        }
    }

    @Override
    public void addServiceToRoom(long serviceId, long roomId) throws ReferencedEntityNotFoundException {
        try {
            roomDao.bindRoomAndService(roomId, serviceId);
        } catch (NoReferencedRowException e) {
            throw new ReferencedEntityNotFoundException("Room or Service not found", e);
        }
    }

    @Override
    public void deleteServiceFromRoom(long serviceId, long roomId) throws EntityNotFoundByIdException{
        try {
            roomDao.unbindRoomAndService(roomId, serviceId);
        } catch (ZeroRowsAffectedOrReturnedException e) {
            throw new EntityNotFoundByIdException("Room and Service pair not found by their id", e);
        }
    }

    @Override
    public void changeServiceOnRoom(long oldServiceId, long newServiceId, long roomId) throws ReferencedEntityNotFoundException, EntityNotFoundByIdException {

        try {
            roomDao.bindRoomAndService(roomId, newServiceId);
        } catch (NoReferencedRowException e) {
            throw new ReferencedEntityNotFoundException("Service that will be changed instead or Room is not found", e);
        }

        try {
            roomDao.unbindRoomAndService(roomId, oldServiceId);
        } catch (ZeroRowsAffectedOrReturnedException e) {
            throw new EntityNotFoundByIdException("Room and Service pair not found by their id", e);
        }
    }
}
