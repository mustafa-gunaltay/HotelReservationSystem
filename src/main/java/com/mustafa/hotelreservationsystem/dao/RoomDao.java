package com.mustafa.hotelreservationsystem.dao;

import com.mustafa.hotelreservationsystem.exceptions.db.DuplicateEntryException;
import com.mustafa.hotelreservationsystem.exceptions.db.NoReferencedRowException;
import com.mustafa.hotelreservationsystem.exceptions.db.ZeroRowsAffectedOrReturnedException;
import com.mustafa.hotelreservationsystem.models.Entity;
import com.mustafa.hotelreservationsystem.models.Room;
import com.mustafa.hotelreservationsystem.models.RoomWithFeatureAndService;

import java.util.List;

public interface RoomDao extends Crudable{
    @Override
    void save(Entity e);
    @Override
    void update(Entity e) throws NoReferencedRowException, ZeroRowsAffectedOrReturnedException;
    @Override
    Room retrieve(long id) throws ZeroRowsAffectedOrReturnedException;
    @Override
    Room delete(long id) throws ZeroRowsAffectedOrReturnedException;

    List<Room> retrieveAllRooms();
    void updateSpecifiedRoomField(long id, String fieldName, Object fieldValue) throws NoReferencedRowException, ZeroRowsAffectedOrReturnedException;
    void linkRoomToReservation(long roomId, long resId) throws NoReferencedRowException, ZeroRowsAffectedOrReturnedException;
    void unlinkRoomFromReservation(long roomId) throws ZeroRowsAffectedOrReturnedException;
    void bindRoomAndFeature(long roomId, long featureId) throws NoReferencedRowException, DuplicateEntryException;
    void unbindRoomAndFeature(long roomId, long featureId) throws ZeroRowsAffectedOrReturnedException;
    void bindRoomAndService(long roomId, long serviceId) throws NoReferencedRowException, DuplicateEntryException;
    void unbindRoomAndService(long roomId, long serviceId) throws ZeroRowsAffectedOrReturnedException;

    List<RoomWithFeatureAndService> retrieveAllRoomsWithTheirFeaturesAndServices();
}
