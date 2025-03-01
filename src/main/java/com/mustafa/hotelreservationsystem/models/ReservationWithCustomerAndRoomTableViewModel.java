package com.mustafa.hotelreservationsystem.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ReservationWithCustomerAndRoomTableViewModel {
    private long reservationId;
    private String roomsNames;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String customersNames;

    public ReservationWithCustomerAndRoomTableViewModel() {
        this.reservationId = 0;
        this.roomsNames = "";
        this.checkInDate = null;
        this.checkOutDate = null;
        this.customersNames = "";
    }

    @Override
    public String toString() {
        return "ReceptionistHomePageTableViewModel{" +
                "reservationId=" + reservationId +
                ", roomsNames='" + roomsNames + '\'' +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", customersNames='" + customersNames + '\'' +
                '}';
    }

    public long getReservationId() {
        return reservationId;
    }

    // for defaultGet
    public String getRoomsNamesDefault() {
        return roomsNames;
    }

    // for propertyValueFavtory gettting
    public String getRoomsNames() {
        return roomsNames.substring(2);
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    // for default get
    public String getCustomersNamesDefault() {
        return customersNames;
    }

    // for propertyValueFavtory gettting
    public String getCustomersNames() {
        return customersNames.substring(2);
    }

    public void setReservationId(long reservationId) {
        this.reservationId = reservationId;
    }

    public void setRoomsNames(String roomsNames) {
        this.roomsNames = roomsNames;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public void setCustomersNames(String customersNames) {
        this.customersNames = customersNames;
    }


    public static List<ReservationWithCustomerAndRoomTableViewModel> transformInnerJoinResultToTableViewModelFormat
            (List<ReservationWithCustomerAndRoom> allReservationsWithCustomerAndRoom) {

        /*
         * res cus room
         * 13  4   6
         * 13  5   9
         * 14  5   10
         * 14  5   11
         * */


        List<Long> differentReservationIds = new ArrayList<>();
        for (ReservationWithCustomerAndRoom eachInnerJoinRow : allReservationsWithCustomerAndRoom) {

            long reservationId = eachInnerJoinRow.getReservationId();

            if ( ! differentReservationIds.contains(reservationId)) {
                differentReservationIds.add(reservationId);
            }
        }

        // ayni reservationId ye sahip satirlari grup olarak iceren liste
        List<List<ReservationWithCustomerAndRoom>> differentRowsByReservationId = new ArrayList<>();
        for (int i = 0; i < differentReservationIds.size(); i++) {
            List<ReservationWithCustomerAndRoom> reservationsForThisId = new ArrayList<>(); // Yeni bir liste oluştur

            for (ReservationWithCustomerAndRoom eachInnerJoinRow : allReservationsWithCustomerAndRoom) {
                if (eachInnerJoinRow.getReservationId() == differentReservationIds.get(i)) {
                    reservationsForThisId.add(eachInnerJoinRow); // Listeye eleman ekle
                }
            }
            differentRowsByReservationId.add(reservationsForThisId); // Dış listeye ekle
        }


        List<Long> differentCustomerIds = new ArrayList<>();
        List<Long> differentRoomIds = new ArrayList<>();
        List<ReservationWithCustomerAndRoomTableViewModel> result = new ArrayList<>();

        for ( int i=0 ; i < differentRowsByReservationId.size(); i++ ) {

            List<ReservationWithCustomerAndRoom> eachListThatHasSameReservationId = differentRowsByReservationId.get(i);

            long reservationId = eachListThatHasSameReservationId.getFirst().getReservationId();
            LocalDate checkInDate = eachListThatHasSameReservationId.getFirst().getCheckInDate();
            LocalDate checkOutDate = eachListThatHasSameReservationId.getFirst().getCheckOutDate();

            ReservationWithCustomerAndRoomTableViewModel oneTableViewRow = new ReservationWithCustomerAndRoomTableViewModel();
            oneTableViewRow.setReservationId(reservationId);
            oneTableViewRow.setCheckInDate(checkInDate);
            oneTableViewRow.setCheckOutDate(checkOutDate);

            for (int j=0 ; j < eachListThatHasSameReservationId.size() ; j++ ) {

                ReservationWithCustomerAndRoom oneRow = eachListThatHasSameReservationId.get(j);

                if ( ! differentCustomerIds.contains(oneRow.getCustomerId()) ) {
                    String currentNames = oneTableViewRow.getCustomersNamesDefault();
                    oneTableViewRow.setCustomersNames(currentNames + ", " + oneRow.getFullName());

                    differentCustomerIds.add(oneRow.getCustomerId());
                }

                if ( ! differentRoomIds.contains(oneRow.getRoomId()) ) {
                    String currentRoomNames = oneTableViewRow.getRoomsNamesDefault();
                    oneTableViewRow.setRoomsNames(currentRoomNames + ", " + oneRow.getRoomName());

                    differentRoomIds.add(oneRow.getRoomId());
                }

            }
            differentCustomerIds.clear();
            differentRoomIds.clear();

            result.add(oneTableViewRow);
        }

        result.sort(Comparator.comparing(ReservationWithCustomerAndRoomTableViewModel::getReservationId));
        return result;
    }
}
