package com.mustafa.hotelreservationsystem.services;

import com.mustafa.hotelreservationsystem.dao.ReservationDao;
import com.mustafa.hotelreservationsystem.dao.ReservationDaoImpl;
import com.mustafa.hotelreservationsystem.exceptions.db.DuplicateEntryException;
import com.mustafa.hotelreservationsystem.exceptions.db.NoReferencedRowException;
import com.mustafa.hotelreservationsystem.exceptions.db.ZeroRowsAffectedOrReturnedException;
import com.mustafa.hotelreservationsystem.exceptions.general.EntityNotFoundByIdException;
import com.mustafa.hotelreservationsystem.exceptions.general.ReferencedEntityNotFoundException;
import com.mustafa.hotelreservationsystem.exceptions.general.SameEntityValueExistInDbException;
import com.mustafa.hotelreservationsystem.models.Reservation;
import com.mustafa.hotelreservationsystem.models.ReservationWithCustomerAndRoom;

import java.time.LocalDate;
import java.util.List;

public class ReservationServiceImpl implements ReservationService{

    private ReservationDao reservationDao;

    public ReservationServiceImpl() {
        reservationDao = new ReservationDaoImpl();
    }

    @Override
    public void createReservation(Reservation r) {
        reservationDao.save(r);
    }

    @Override
    public void changeCheckInDate(long id, LocalDate newCheckInDate) throws EntityNotFoundByIdException {

        try{
            reservationDao.updateSpecifiedReservationField(id, "checkInDate", newCheckInDate);
        }
        catch (NoReferencedRowException e){
            System.out.println(e); // never thrown because it only throws Exception when fieldName is "receptionistId"
        } catch (ZeroRowsAffectedOrReturnedException e) {
            throw new EntityNotFoundByIdException("Reservation is not found by id", e);
        }
    }

    @Override
    public void changeCheckOutDate(long id, LocalDate newCheckOutDate) throws EntityNotFoundByIdException {

        try{
            reservationDao.updateSpecifiedReservationField(id, "checkOutDate", newCheckOutDate);
        }
        catch (NoReferencedRowException e){
            System.out.println(e); // never thrown because it only throws Exception when fieldName is "receptionistId"
        } catch (ZeroRowsAffectedOrReturnedException e) {
            throw new EntityNotFoundByIdException("Reservation is not found by id", e);
        }
    }

    @Override
    public void changeBookingDate(long id, LocalDate newBookingDate) throws EntityNotFoundByIdException {

        try{
            reservationDao.updateSpecifiedReservationField(id, "bookingDate", newBookingDate);
        }
        catch (NoReferencedRowException e){
            System.out.println(e); // never thrown because it only throws Exception when fieldName is "receptionistId"
        } catch (ZeroRowsAffectedOrReturnedException e) {
            throw new EntityNotFoundByIdException("Reservation is not found by id", e);
        }
    }

    @Override
    public Reservation deleteReservation(long id) throws EntityNotFoundByIdException {

        try {
            Reservation deletedReservation = reservationDao.delete(id);
            return deletedReservation;
        } catch (ZeroRowsAffectedOrReturnedException e) {
            throw new EntityNotFoundByIdException("Reservation is not found by id", e);
        }
    }


    // Gorevi = Onceden sistemde olan bir customer'i bir rezervasyonun icine eklemek
    @Override
    public void addCustomerToReservation(long resId, long idOfCusToBeAdded) throws ReferencedEntityNotFoundException, SameEntityValueExistInDbException {

        try{
            reservationDao.bindReservationAndCustomer(resId, idOfCusToBeAdded);
        } catch (NoReferencedRowException e) {
            throw new ReferencedEntityNotFoundException("Reservation or Customer not found", e);
        } catch (DuplicateEntryException e) {
            throw new SameEntityValueExistInDbException("Reservation and Customer pair already exist", e);
        }
    }

    // Gorevi = Bir rezervasyondan musteri cikartilacak ve baska bir rezervasyona eklenecek
    @Override
    public void changeCustomerOnReservation(long oldResId, long newResId, long idOfCusToBeChanged) throws ReferencedEntityNotFoundException, EntityNotFoundByIdException, SameEntityValueExistInDbException {

        try{
            reservationDao.bindReservationAndCustomer(newResId, idOfCusToBeChanged);
        }
        catch (NoReferencedRowException e) {
            throw new ReferencedEntityNotFoundException("Reservation that will be changed instead or Customer not found", e);
        } catch (DuplicateEntryException e) {
            throw new SameEntityValueExistInDbException("Reservation and Customer pair already exist", e);
        }

        try{
            reservationDao.unbindReservationAndCustomer(oldResId, idOfCusToBeChanged);
        } catch (ZeroRowsAffectedOrReturnedException e) {
            System.out.println("New reservation and customer pair added but old reservation and customer pair to be deleted is not found");
            throw new EntityNotFoundByIdException("Reservation and Customer pair to be deleted is not found by their id", e);
        }

    }

    @Override
    public void deleteCustomerFromReservation(long resId, long idOfCusToBeRemoved) throws EntityNotFoundByIdException {

        try{
            reservationDao.unbindReservationAndCustomer(resId, idOfCusToBeRemoved);
        } catch (ZeroRowsAffectedOrReturnedException e) {
            throw new EntityNotFoundByIdException("Reservation and Customer pair to be deleted is not found by their id", e);
        }
    }

    @Override
    public void addReceptionistToReservation(long resId, long recId) throws ReferencedEntityNotFoundException, EntityNotFoundByIdException {

        try{
            reservationDao.linkReservationToReceptionist(resId, recId);
        } catch (NoReferencedRowException e) {
            throw new ReferencedEntityNotFoundException("Receptionist not found", e);
        } catch (ZeroRowsAffectedOrReturnedException e) {
            throw new EntityNotFoundByIdException("Reservation is not found by id", e);
        }
    }

    @Override
    public Reservation getReservation(long id) throws EntityNotFoundByIdException {

        try{
            Reservation targetReservation = reservationDao.retrieve(id);
            return targetReservation;
        } catch (ZeroRowsAffectedOrReturnedException e) {
            throw new EntityNotFoundByIdException("Reservation is not found by id", e);
        }

    }

    @Override
    public List<Reservation> getAllReservations() {
        List<Reservation> allReservations = reservationDao.retrieveAllReservations();
        if ( ! allReservations.isEmpty()){
            return allReservations;
        }
        else{
            System.out.println("public List<Reservation> getAllReservations() -> returned empty list");
            return allReservations;
        }
    }

    // ReceptionistHomePage'deki tableview icin olusturuldu
    @Override
    public List<ReservationWithCustomerAndRoom> getAllReservationsWithTheirCustomersAndRooms() {
        List<ReservationWithCustomerAndRoom> allReservationsWithItsCustomersAndRooms = reservationDao.retrieveAllReservationsWithTheirCustomersAndRooms();
        if ( ! allReservationsWithItsCustomersAndRooms.isEmpty()){
            return allReservationsWithItsCustomersAndRooms;
        }
        else{
            System.out.println("List<ReservationWithCustomerAndRoom> getAllReservationsWithItsCustomersAndItsRooms() -> returned empty list");
            return allReservationsWithItsCustomersAndRooms;
        }
    }


    @Override
    public Reservation getLastReservation() throws EntityNotFoundByIdException {

        try{
            Reservation lastReservation = reservationDao.retrieveLastReservation();
            return lastReservation;
        }
        catch (ZeroRowsAffectedOrReturnedException e) {
            throw new EntityNotFoundByIdException("Last reservation is not exist, reservation table is empty", e);
        }
    }
}
