package com.mustafa.hotelreservationsystem.models;

// EditingRoomCustomizationPage'deki inner join ile olusturulan room ile alakali TableView in kullanacagi model sinif

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RoomWithFeatureAndServiceTableViewModel {

    private long roomId;
    private String roomName;
    private int capacity;
    private int roomUnitPrice;

    private String featureNames;
    private String serviceNames;

    public RoomWithFeatureAndServiceTableViewModel() {
        this.roomId = 0;
        this.roomName = null;
        this.capacity = 0;
        this.featureNames = "";
        this.serviceNames = "";
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setRoomUnitPrice(int roomUnitPrice) {
        this.roomUnitPrice = roomUnitPrice;
    }

    public void setFeatureNames(String featureNames) {
        this.featureNames = featureNames;
    }

    public void setServiceNames(String serviceNames) {
        this.serviceNames = serviceNames;
    }

    public String getFeatureNamesDefault() {
        return featureNames;
    }

    public String getServiceNamesDefault() {
        return serviceNames;
    }

    public String getFeatureNames() {
        return featureNames.substring(2);
    }

    public String getServiceNames() {
        return serviceNames.substring(2);
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

    public int getRoomUnitPrice() {
        return roomUnitPrice;
    }

    @Override
    public String toString() {
        return "RoomWithFeatureAndServiceTableViewModel{" +
                "roomId=" + roomId +
                ", roomName='" + roomName + '\'' +
                ", capacity=" + capacity +
                ", roomUnitPrice=" + roomUnitPrice +
                ", featureNames='" + featureNames + '\'' +
                ", serviceNames='" + serviceNames + '\'' +
                '}';
    }

    public static List<RoomWithFeatureAndServiceTableViewModel> transformInnerJoinResultToTableViewModelFormat(
            List<RoomWithFeatureAndService> allRoomsWithFeatureAndService) {

        /*
         * ro fea ser
         * 13  4   6
         * 13  5   9
         * 14  5   10
         * 14  5   11
         * */

        List<Long> differentRoomIds = new ArrayList<>();
        for (RoomWithFeatureAndService eachRow : allRoomsWithFeatureAndService) {
            long roomId = eachRow.getRoomId();
            if ( ! differentRoomIds.contains(roomId)) {
                differentRoomIds.add(roomId);
            }
        }


        List<List<RoomWithFeatureAndService>> differentRowsByRoomId = new ArrayList<>();
        for (int i = 0; i < differentRoomIds.size(); i++) {

            List<RoomWithFeatureAndService> rowForThisId = new ArrayList<>();

            for (RoomWithFeatureAndService eachRow : allRoomsWithFeatureAndService) {
                if ( eachRow.getRoomId() == differentRoomIds.get(i)) {
                    rowForThisId.add(eachRow);
                }
            }

            differentRowsByRoomId.add(rowForThisId);
        }


        List<Long> differentFeatureIds = new ArrayList<>();
        List<Long> differentServiceIds = new ArrayList<>();
        List<RoomWithFeatureAndServiceTableViewModel> result = new ArrayList<>();
        for (int i = 0; i < differentRowsByRoomId.size(); i++) {

            List<RoomWithFeatureAndService> rowsThatHasSameRoomId = differentRowsByRoomId.get(i);

            long roomId = rowsThatHasSameRoomId.getFirst().getRoomId();
            String roomName = rowsThatHasSameRoomId.getFirst().getRoomName();
            int capacity = rowsThatHasSameRoomId.getFirst().getRoomCapacity();
            int roomUnitPrice = rowsThatHasSameRoomId.getFirst().getRoomUnitPrice();

            RoomWithFeatureAndServiceTableViewModel oneTableViewRow = new RoomWithFeatureAndServiceTableViewModel();
            oneTableViewRow.setRoomId(roomId);
            oneTableViewRow.setRoomName(roomName);
            oneTableViewRow.setCapacity(capacity);
            oneTableViewRow.setRoomUnitPrice(roomUnitPrice);

            for (int j=0 ; j < rowsThatHasSameRoomId.size(); j++) {

                RoomWithFeatureAndService oneRow = rowsThatHasSameRoomId.get(j);

                long featureId = oneRow.getFeatureId();
                if ( ! differentFeatureIds.contains(featureId)) {
                    differentFeatureIds.add(featureId);
                    String currentFeatureName = oneTableViewRow.getFeatureNamesDefault();
                    oneTableViewRow.setFeatureNames(currentFeatureName + ", " + oneRow.getFeatureName());
                }

                long serviceId = oneRow.getServiceId();
                if ( ! differentServiceIds.contains(serviceId)) {
                    differentServiceIds.add(serviceId);
                    String currentServiceName = oneTableViewRow.getServiceNamesDefault();
                    oneTableViewRow.setServiceNames(currentServiceName + ", " + oneRow.getServiceName());
                }
            }
            differentFeatureIds.clear();
            differentServiceIds.clear();

            result.add(oneTableViewRow);

        }

        result.sort(Comparator.comparing(RoomWithFeatureAndServiceTableViewModel::getRoomId));
        return result;
    }
}
