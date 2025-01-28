package com.mustafa.hotelreservationsystem.models;

public class Room extends Entity{
    private String roomName;
    private int capacity;
    private int price;
    private boolean isReserved;
    private Reservation reservation;

    public Room(long id, String name, int capacity, int price, boolean isReserved, Reservation reservation) {
        super(id);
        this.roomName = name;
        this.capacity = capacity;
        this.price = price;
        this.isReserved = isReserved;
        this.reservation = reservation;
    }

    public Room(long id, String name, int capacity, int price, boolean isReserved) {
        super(id);
        this.roomName = name;
        this.capacity = capacity;
        this.price = price;
        this.isReserved = isReserved;
        this.reservation = null;
    }

    public Room(String roomName, int capacity, int price, boolean isReserved, Reservation reservation) {
        // id = 0
        this.roomName = roomName;
        this.capacity = capacity;
        this.price = price;
        this.isReserved = isReserved;
        this.reservation = reservation;
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

    public Reservation getReservation() {
        return reservation;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomName='" + roomName + '\'' +
                ", capacity=" + capacity +
                ", price=" + price +
                ", isReserved=" + isReserved +
                ", reservation=" + reservation +
                ", id=" + id +
                '}';
    }
}
