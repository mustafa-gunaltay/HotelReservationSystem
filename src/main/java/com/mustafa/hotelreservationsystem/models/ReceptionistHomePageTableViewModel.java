package com.mustafa.hotelreservationsystem.models;

import java.time.LocalDate;

public class ReceptionistHomePageTableViewModel {
    private long reservationId;
    private String roomsNames;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String customersNames;

    public ReceptionistHomePageTableViewModel() {
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
}
