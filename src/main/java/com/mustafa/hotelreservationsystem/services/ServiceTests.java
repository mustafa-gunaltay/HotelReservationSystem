package com.mustafa.hotelreservationsystem.services;

import com.mustafa.hotelreservationsystem.exceptions.general.InvalidAdminException;
import com.mustafa.hotelreservationsystem.exceptions.general.InvalidReceptionistException;
import com.mustafa.hotelreservationsystem.models.*;

import java.time.LocalDate;
import java.util.List;

public class ServiceTests {
    public static void adminServiceImplUnitTest(){
        AdminService aService = new AdminServiceImpl();

            // creating new admin
//        Admin newAdmin = new Admin("veli soylu", "userveli", "pwveli");
//        aService.createAdmin(newAdmin);



            // updating admin
            // Not = Bir admini update edebilmek icin icinde o admine ait id bilinmeli


        // changing admin's full name
//        aService.changefullName(17, "1-birinci");

        // changing admin's username
//        aService.changeUsername(17, "2-ikinci");

        // changing admin's paswordd
//        aService.changePassword(17, "3-ucuncu");

        // validation of username and password of admin
        try {
            aService.validateAdmin("dsaads", "musti123");
        } catch (InvalidAdminException e) {
            System.out.println(e.getMessage());
        }

        try {
            aService.validateAdmin("musti user", "sdadsa");;
        } catch (InvalidAdminException e) {
            System.out.println(e.getMessage());
        }


    }

    public static void receptionistServiceImplUnitTest(){

        ReceptionistService rService = new ReceptionistServiceImpl();

        // creating new receptionist
//        Receptionist r1 = new Receptionist("must gun", "user must", "must123");
//        Receptionist r2 = new Receptionist("ali kilic", "user ali", "ali123");
//        Receptionist r3 = new Receptionist("mehmet soylu", "user mehmet", "mehmet123");
//        rService.createReceptionist(r1);
//        rService.createReceptionist(r2);
//        rService.createReceptionist(r3);

        // get all receptionists
//        List<Receptionist> allReceptionists = rService.getAllReceptionists();
//        for (Receptionist r : allReceptionists){
//            System.out.println(r);
//        }


            // updating admin
            // Not = Bir resepsiyonisti update edebilmek icin icinde o resepsiyoniste ait id bilinmeli

        //changing receptionist's full name
//        rService.changefullName(5, "new ali kilic");

        //changing receptionist's username
//        rService.changeUsername(6, "new user mehmet");

        //changing receptionist's passwordd
//        rService.changePassword(6, "newmehmet123");

        // validation of username and password of receptionist
        try {
            rService.validateReceptionist("dsasad", "dsasad");
        }
        catch (InvalidReceptionistException e){
            System.out.println(e);
        }

        try {
            rService.validateReceptionist("new user mehmet", "Mehmet123");
        }
        catch (InvalidReceptionistException e){
            System.out.println(e);
        }

        try {
            rService.validateReceptionist("new user mehmet", "newmehmet123");
        }
        catch (InvalidReceptionistException e){
            System.out.println(e);
        }


    }



    public static void featureServiceImplUnitTest(){

        FeatureService fService = new FeatureServiceImpl();

        // creating new receptionist
        Feature f1 = new Feature("f1", 101);
        Feature f2 = new Feature("f2", 200);
        fService.createFeature(f1);
        fService.createFeature(f2);


        // get all receptionists
        List<Feature> allFeatures = fService.getAllFeatures();
        for (Feature f : allFeatures){
            System.out.println(f);
        }

            // updating admin

        // changing feature's name
        fService.changeFeatureName(12, "yeni yaptigim f1 feature");

        // changing feature's price
        fService.changePrice(13, 9999);

        // delete feature
        fService.deleteFeature(10);
    }

    public static void reservationServiceImplUnitTest(){

        ReservationService reservationService = new ReservationServiceImpl();

        // creating reservation
        Receptionist receptionist1 = new Receptionist(5, "new ali kilic", "user ali", "ali123");
        Receptionist receptionist2 = new Receptionist(6, "mehmet soylu", "new user mehmet", "newmehmet123");

        Reservation r1 = new Reservation( // id'si 13 olacak
                LocalDate.of(3024, 6, 1),
                LocalDate.of(3024, 6, 10),
                LocalDate.of(3024, 5, 15),
                receptionist1
        );

        Reservation r2 = new Reservation( // 14
                LocalDate.of(3024, 7, 15),
                LocalDate.of(3024, 7, 20),
                LocalDate.of(3024, 6, 30),
                receptionist1
        );

        Reservation r3 = new Reservation( // 15
                LocalDate.of(3024, 8, 5),
                LocalDate.of(3024, 8, 12),
                LocalDate.of(3024, 7, 20),
                receptionist2
        );

//        reservationService.createReservation(r1);
//        reservationService.createReservation(r2);
//        reservationService.createReservation(r3);


        // change checkInDate
//        reservationService.changeCheckInDate(13, LocalDate.of(2024, 7, 2));

        // change checkOutDate
        reservationService.changeCheckOutDate(13, LocalDate.of(2024, 7, 11));

        // change bookingDate
        reservationService.changeBookingDate(13, LocalDate.of(2024, 6, 16));

        // get all reservations
        List<Reservation> allReservations = reservationService.getAllReservations();
        for (Reservation r : allReservations){
            System.out.println(r);
        }

        // get reservation
        Reservation temp = reservationService.getReservation(14);
        System.out.println(temp);



        // add customer to reservation
        reservationService.addCustomerToReservation(13, 1);
        reservationService.addCustomerToReservation(14, 2);

        // change customer on reservation
        reservationService.changeCustomerOnReservation(13, 10, 1);
        reservationService.changeCustomerOnReservation(14, 11, 2);

        // delete customer from reservation
        reservationService.deleteCustomerFromReservation(10, 1);
        reservationService.deleteCustomerFromReservation(11, 2);

        // delete reservation
        Reservation deletedReservation = reservationService.deleteReservation(14);
        System.out.println(deletedReservation);



    }



    public static void roomServiceImplUnitTest(){

        RoomService roomService = new RoomServiceImpl();


        Receptionist receptionist1 = new Receptionist(5, "new ali kilic", "user ali", "ali123");
        Receptionist receptionist2 = new Receptionist(6, "mehmet soylu", "new user mehmet", "newmehmet123");

        Reservation re1 = new Reservation( // 10
                10,
                LocalDate.of(3024, 6, 1),
                LocalDate.of(3024, 6, 10),
                LocalDate.of(3024, 5, 15),
                receptionist1
        );

        Reservation re2 = new Reservation( // 11
                11,
                LocalDate.of(3024, 7, 15),
                LocalDate.of(3024, 7, 20),
                LocalDate.of(3024, 6, 30),
                receptionist1
        );

        Reservation re3 = new Reservation( // 12
                12,
                LocalDate.of(3024, 8, 5),
                LocalDate.of(3024, 8, 12),
                LocalDate.of(3024, 7, 20),
                receptionist2
        );


        Room r1 = new Room(7, "Deluxe Room", 2, 150, false, null);
        Room r2 = new Room(8, "Suite Room", 4, 300, true, re2);
        Room r3 = new Room(9, "Standard Room", 1, 100, false, null);

        // creating room
        roomService.createRoom(r1);
        roomService.createRoom(r2);
        roomService.createRoom(r3);


        // changing roomName
        roomService.changeRoomName(7, "New Deluxe Room");

        // changing capacity
        roomService.changeCapacity(7, 160);

        // changing price
        roomService.changePrice(9, 80);

        // get room
        Room temp;
        temp = roomService.getRoom(7);
        System.out.println(temp);
        temp = roomService.getRoom(9);
        System.out.println(temp);


        // get all rooms
        List<Room> allRooms = roomService.getAllRooms();
        for (Room r : allRooms){
            System.out.println(r);
        }



        // add room to reservation
        roomService.addRoomToReservation(7, 10);
        roomService.addRoomToReservation(9, 12);

        // change room on reservation
        roomService.changeRoomOnReservation(7, 8);
        roomService.changeRoomOnReservation(9, 9);

        // delete room from reservation
        roomService.deleteRoomFromReservation(7);
        roomService.deleteRoomFromReservation(9);



        // add feature to room
        roomService.addFeatureToRoom(11, 8);
        roomService.addFeatureToRoom(12, 9);

        // change feature on room
        roomService.changeFeatureOnRoom(11, 12,  8);
        roomService.changeFeatureOnRoom(12, 13,  9);


        // delete feature from room
        roomService.deleteFeatureFromRoom(12, 8);
        roomService.deleteFeatureFromRoom(13, 9);



        // add service to room
        roomService.addServiceToRoom(1, 7);
        roomService.addServiceToRoom(2, 8);

        // change service on room
        roomService.changeServiceOnRoom(1, 2, 7);
        roomService.changeServiceOnRoom(2, 3, 8);

        // delete service from room
        roomService.deleteServiceFromRoom(2, 7);
        roomService.deleteServiceFromRoom(3, 8);



        // delete room
        Room deletedRoom = roomService.deleteRoom(7);
        System.out.println(deletedRoom);


    }


}














