package com.mustafa.hotelreservationsystem.models;

// inner join sorgusunda room ile service ve feature'lari birlikte alabilmek icin olusturulmus model sinif
// sadece reserve edilmemis odalari iceren sorgu sonuclarini tutacak dolayisiyla isReserved ve Reservation gibi alanlari yok
public class RoomWithFeatureAndService {
    private long roomId;
    private String roomName;
    private int roomCapacity;
    private int roomUnitPrice;

    private long featureId;
    private String featureName;

    private long serviceId;
    private String serviceName;

    public RoomWithFeatureAndService(long roomId, String roomName, int roomCapacity, int roomUnitPrice, long featureId, String featureName, long serviceId, String serviceName) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomCapacity = roomCapacity;
        this.roomUnitPrice = roomUnitPrice;
        this.featureId = featureId;
        this.featureName = featureName;
        this.serviceId = serviceId;
        this.serviceName = serviceName;
    }

    @Override
    public String toString() {
        return "RoomWithFeatureAndService{" +
                "roomId=" + roomId +
                ", roomName='" + roomName + '\'' +
                ", roomCapacity=" + roomCapacity +
                ", roomUnitPrice=" + roomUnitPrice +
                ", featureId=" + featureId +
                ", featureName='" + featureName + '\'' +
                ", serviceId=" + serviceId +
                ", serviceName='" + serviceName + '\'' +
                '}';
    }

    public long getRoomId() {
        return roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public int getRoomCapacity() {
        return roomCapacity;
    }

    public int getRoomUnitPrice() {
        return roomUnitPrice;
    }

    public long getFeatureId() {
        return featureId;
    }

    public String getFeatureName() {
        return featureName;
    }

    public long getServiceId() {
        return serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }
}
