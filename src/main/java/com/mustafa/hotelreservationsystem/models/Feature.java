package com.mustafa.hotelreservationsystem.models;

public class Feature extends Entity{
    private String featureName;
    private int price;

    public Feature(long id, String name, int price) {
        super(id);
        this.featureName = name;
        this.price = price;
    }

    public Feature(String featureName, int price) {
        // id = 0
        this.featureName = featureName;
        this.price = price;
    }

    public String getFeatureName() {
        return featureName;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Feature{" +
                "id=" + id +
                ", price=" + price +
                ", featureName='" + featureName + '\'' +
                '}';
    }
}
