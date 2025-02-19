package com.mustafa.hotelreservationsystem.models;

import java.time.LocalDate;

public class ReservationWithCustomerAndRoom {
    private long reservationId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDate bookingDate;
    private Receptionist receptionist;

    private long customerId;
    private String fullName;
    private String phoneNumber;
    private LocalDate birthDate;
    private String description;

    private long roomId;
    private String roomName;
    private int capacity;
    private int price;
    private boolean isReserved;

    public ReservationWithCustomerAndRoom
            (long reservationId, LocalDate checkInDate, LocalDate checkOutDate, LocalDate bookingDate, Receptionist receptionist,
             long customerId, String fullName, String phoneNumber, LocalDate birthDate, String description,
             long roomId, String roomName, int capacity, int price, boolean isReserved) {
        this.reservationId = reservationId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingDate = bookingDate;
        this.receptionist = receptionist;
        this.customerId = customerId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.description = description;
        this.roomId = roomId;
        this.roomName = roomName;
        this.capacity = capacity;
        this.price = price;
        this.isReserved = isReserved;
    }

    @Override
    public String toString() {
        return "ReservationWithCustomerAndRoom{" +
                "reservationId=" + reservationId +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", bookingDate=" + bookingDate +
                ", receptionist=" + receptionist +
                ", customerId=" + customerId +
                ", fullName='" + fullName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", birthDate=" + birthDate +
                ", description='" + description + '\'' +
                ", roomId=" + roomId +
                ", roomName='" + roomName + '\'' +
                ", capacity=" + capacity +
                ", price=" + price +
                ", isReserved=" + isReserved +
                '}';
    }

    public long getReservationId() {
        return reservationId;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public Receptionist getReceptionist() {
        return receptionist;
    }

    public long getCustomerId() {
        return customerId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getDescription() {
        return description;
    }

    public long getRoomId() {
        return roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getPrice() {
        return price;
    }

    public boolean isReserved() {
        return isReserved;
    }
}
