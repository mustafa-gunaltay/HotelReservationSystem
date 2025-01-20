package com.mustafa.hotelreservationsystem.models;

public class Service extends Entity {
    private String serviceName;
    private int price;

    public Service(long id, String name, int price) {
        super(id);
        this.serviceName = name;
        this.price = price;
    }

    public Service(String serviceName, int price) {
        // id = 0
        this.serviceName = serviceName;
        this.price = price;
    }
}
