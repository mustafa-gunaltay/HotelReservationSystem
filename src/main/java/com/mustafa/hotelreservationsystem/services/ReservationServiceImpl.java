package com.mustafa.hotelreservationsystem.services;

import com.mustafa.hotelreservationsystem.dao.ReservationDao;
import com.mustafa.hotelreservationsystem.dao.ReservationDaoImpl;
import com.mustafa.hotelreservationsystem.models.Reservation;

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
    public void changeCheckInDate(long id, LocalDate newCheckInDate) {
        reservationDao.updateSpecifiedReservationField(id, "checkInDate", newCheckInDate);
    }

    @Override
    public void changeCheckOutDate(long id, LocalDate newCheckOutDate) {
        reservationDao.updateSpecifiedReservationField(id, "checkOutDate", newCheckOutDate);
    }

    @Override
    public void changeBookingDate(long id, LocalDate newBookingDate) {
        reservationDao.updateSpecifiedReservationField(id, "bookingDate", newBookingDate);
    }

    @Override
    public Reservation deleteReservation(long id) {
        Reservation deletedReservation = reservationDao.delete(id);
        return deletedReservation;
    }


    // Gorevi = Onceden sistemde olan bir customer'i bir rezervasyonun icine eklemek
    @Override
    public void addCustomerToReservation(long resId, long idOfCusToBeAdded) {
        reservationDao.bindReservationAndCustomer(resId, idOfCusToBeAdded);
    }

    // Gorevi = Bir rezervasyondan musteri cikartilacak ve baska bir rezervasyona eklenecek
    @Override
    public void changeCustomerOnReservation(long oldResId, long newResId, long idOfCusToBeChanged) {
        reservationDao.unbindReservationAndCustomer(oldResId, idOfCusToBeChanged);
        reservationDao.bindReservationAndCustomer(newResId, idOfCusToBeChanged);
    }

    @Override
    public void deleteCustomerFromReservation(long resId, long idOfCusToBeRemoved) {
        reservationDao.unbindReservationAndCustomer(resId, idOfCusToBeRemoved);
    }

    @Override
    public void addReceptionistToReservation(long resId, long recId) {
        reservationDao.linkReservationToReceptionist(resId, recId);
    }

    @Override
    public Reservation getReservation(long id) {
        Reservation targetReservation = reservationDao.retrieve(id);
        if (targetReservation != null){
            return targetReservation;
        }
        else{
            System.out.println("public Reservation getReservation(long id) -> returned null");
            return null;
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
}
