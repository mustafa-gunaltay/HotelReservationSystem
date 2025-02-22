package com.mustafa.hotelreservationsystem.services;

import com.mustafa.hotelreservationsystem.exceptions.db.DuplicateEntryException;
import com.mustafa.hotelreservationsystem.exceptions.db.NoReferencedRowException;
import com.mustafa.hotelreservationsystem.exceptions.db.ZeroRowsAffectedOrReturnedException;
import com.mustafa.hotelreservationsystem.exceptions.general.*;
import com.mustafa.hotelreservationsystem.models.*;

import java.time.LocalDate;
import java.util.List;

public class ServiceTests {
    public static void adminServiceImplUnitTest(){
        AdminService aService = new AdminServiceImpl();


        // creating new admin
        Admin newAdmin1 = new Admin("veli soylu", "userveli", "pwveli");
        Admin newAdmin2 = new Admin(null, "dsadfdaasd", "pwveli");

//        try {
//            aService.createAdmin(newAdmin1);
//        } catch (SameEntityValueExistInDbException e) {
//            System.out.println(e.getMessage());
//        }

        try {
            aService.createAdmin(newAdmin2);
        } catch (SameEntityValueExistInDbException e) {
            System.out.println(e.getMessage());
        }


        // get admins
        try{
            Admin a = aService.getAdmin(19);
            System.out.println(a);
        }
        catch(EntityNotFoundByIdException e){
            System.out.println(e.getMessage() + " -  id: " + ((ZeroRowsAffectedOrReturnedException)e.getCause()).getId());
        }

        try{
            Admin a = aService.getAdmin(99);
            System.out.println(a);
        }
        catch(EntityNotFoundByIdException e){
            System.out.println(e.getMessage() + " -  id: " + ((ZeroRowsAffectedOrReturnedException)e.getCause()).getId());
        }


        // get all admins
        List<Admin> allAdmins = aService.getAllAdmins();
        if ( ! allAdmins.isEmpty()){
            for (Admin admin : allAdmins){
                System.out.println(admin);
            }
        }


            // updating admin
            // Not = Bir admini update edebilmek icin icinde o admine ait id bilinmeli


        // changing admin's full name
        try{
            aService.changeFullName(99, "1-birinci");
        } catch (EntityNotFoundByIdException e) {
            System.out.println(e.getMessage() + " -  id: " + ((ZeroRowsAffectedOrReturnedException)e.getCause()).getId());
        }

        // changing admin's username
        try {
            aService.changeUsername(18, "musti user");
        } catch (SameEntityValueExistInDbException e) {
            System.out.println(e.getMessage());
        } catch (EntityNotFoundByIdException e) {
            System.out.println(e.getMessage() + " -  id: " + ((ZeroRowsAffectedOrReturnedException)e.getCause()).getId());
        }

        try {
            aService.changeUsername(99, "sdadsafas");
        } catch (SameEntityValueExistInDbException e) {
            System.out.println(e.getMessage());
        } catch (EntityNotFoundByIdException e) {
            System.out.println(e.getMessage() + " -  id: " + ((ZeroRowsAffectedOrReturnedException)e.getCause()).getId());
        }


        // changing admin's paswordd
        try{
            aService.changePassword(99, "3-ucuncu");
        } catch (EntityNotFoundByIdException e) {
            System.out.println(e.getMessage() + " -  id: " + ((ZeroRowsAffectedOrReturnedException)e.getCause()).getId());
        }


        // validation of username and password of admin
//        try {
//            aService.validateAdmin("dsaads", "musti123");
//            System.out.println("Successful Admin login");
//        } catch (InvalidAdminException e) {
//            System.out.println(e.getMessage());
//        }
//
//        try {
//            aService.validateAdmin("musti user", "sdadsa");;
//            System.out.println("Successful Admin login");
//        } catch (InvalidAdminException e) {
//            System.out.println(e.getMessage());
//        }


    }

    public static void receptionistServiceImplUnitTest(){

        ReceptionistService rService = new ReceptionistServiceImpl();

        // creating new receptionist
        Receptionist r1 = new Receptionist(null, "yeni kullanici", "kullanici123");
        Receptionist r2 = new Receptionist("ali kilic", "user ali", "ali123");
        Receptionist r3 = new Receptionist("mehmet soylu", "user mehmet", "mehmet123");

        try{
            rService.createReceptionist(r1);
        } catch (SameEntityValueExistInDbException e) {
            System.out.println("x - " + e.getMessage());
        }

        try{
            rService.createReceptionist(r2);
        } catch (SameEntityValueExistInDbException e) {
            System.out.println("x - " + e.getMessage());
        }


        // get all receptionists
        List<Receptionist> allReceptionists = rService.getAllReceptionists();
        if ( ! allReceptionists.isEmpty()){
            for (Receptionist r : allReceptionists){
                System.out.println(r);
            }
        }


        // get receptionist
        try{
            Receptionist r = rService.getReceptionist(7);
            System.out.println(r);
        }
        catch(EntityNotFoundByIdException e){
            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
        }


        try{
            Receptionist r = rService.getReceptionist(99);
            System.out.println(r);
        }
        catch(EntityNotFoundByIdException e){
            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
        }


            // updating admin
            // Not = Bir resepsiyonisti update edebilmek icin icinde o resepsiyoniste ait id bilinmeli

        //changing receptionist's full name
        try {
            rService.changeFullName(5, "new ali kilic");
        }
        catch (EntityNotFoundByIdException e) {
            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
        }

        //changing receptionist's username

        try {
            rService.changeUsername(6, "user must");
        } catch (SameEntityValueExistInDbException e) {
            System.out.println("x - " + e.getMessage());
        } catch (EntityNotFoundByIdException e) {
            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
        }

        try {
            rService.changeUsername(99, "user must");
        } catch (SameEntityValueExistInDbException e) {
            System.out.println("x - " + e.getMessage());
        } catch (EntityNotFoundByIdException e) {
            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
        }

        //changing receptionist's passwordd
        try {
            rService.changePassword(99, "newmehmet123");
        }
        catch (EntityNotFoundByIdException e) {
            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
        }

        // validation of username and password of receptionist
//        try {
//            rService.validateReceptionist("dsasad", "dsasad");
//        }
//        catch (InvalidReceptionistException e){
//            System.out.println(e);
//        }
//
//        try {
//            rService.validateReceptionist("new user mehmet", "Mehmet123");
//        }
//        catch (InvalidReceptionistException e){
//            System.out.println(e);
//        }
//
//        try {
//            rService.validateReceptionist("new user mehmet", "newmehmet123");
//        }
//        catch (InvalidReceptionistException e){
//            System.out.println(e);
//        }


        try{
            Receptionist deletedReceptionist = rService.deleteReceptionist(10);
            System.out.println(deletedReceptionist);
        }
        catch(EntityNotFoundByIdException e){
            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
        }

        try{
            Receptionist deletedReceptionist = rService.deleteReceptionist(99);
            System.out.println(deletedReceptionist);
        }
        catch(EntityNotFoundByIdException e){
            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
        }

    }



    public static void featureServiceImplUnitTest(){

        FeatureService fService = new FeatureServiceImpl();

        // creating new feature
        Feature f1 = new Feature("fea1", 101);
        Feature f2 = new Feature("fea2", 200);

//        fService.createFeature(f1);
//        fService.createFeature(f2);


        // get all feature
//        List<Feature> allFeatures = fService.getAllFeatures();
//        if ( ! allFeatures.isEmpty()) {
//            for (Feature f : allFeatures) {
//                System.out.println(f);
//            }
//        }

        // get one feature
        try {
            Feature f = fService.getFeature(88);
            System.out.println(f);
        } catch (EntityNotFoundByIdException e) {
            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
        }


            // updating admin

        // changing feature's name
        try{
            fService.changeFeatureName(88, "yeni yaptigim f1 feature");
        }
        catch (EntityNotFoundByIdException e){
            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
        }

        // changing feature's price
        try{
            fService.changePrice(88, 9999);
        }
        catch (EntityNotFoundByIdException e){
            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
        }

        // delete feature
        try{
            Feature deletedFeature = fService.deleteFeature(88);
            System.out.println(deletedFeature);
        }
        catch (EntityNotFoundByIdException e){
            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
        }

        try{
            Feature deletedFeature = fService.deleteFeature(13);
            System.out.println(deletedFeature);
        }
        catch (EntityNotFoundByIdException e){
            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
        }
    }

    public static void reservationServiceImplUnitTest(){

        ReservationService reservationService = new ReservationServiceImpl();

        // creating reservation
        Receptionist receptionist1 = new Receptionist(5, "new ali kilic", "user ali", "ali123");
        Receptionist receptionist2 = new Receptionist(6, "mehmet soylu", "new user mehmet", "newmehmet123");

        Reservation r1 = new Reservation( // id'si 16 olacak
                LocalDate.of(3024, 10, 1),
                LocalDate.of(3024, 10, 10),
                LocalDate.of(3024, 10, 15),
                receptionist1
        );

        Reservation r2 = new Reservation( // 17
                LocalDate.of(3024, 11, 15),
                LocalDate.of(3024, 11, 20),
                LocalDate.of(3024, 11, 30),
                receptionist1
        );

        Reservation r3 = new Reservation( // 18
                LocalDate.of(3024, 12, 5),
                LocalDate.of(3024, 12, 12),
                LocalDate.of(3024, 12, 20),
                receptionist2
        );

//        reservationService.createReservation(r1);
//        reservationService.createReservation(r2);
//        reservationService.createReservation(r3);


        // change checkInDate
//        try{
//            reservationService.changeCheckInDate(99, LocalDate.of(2024, 7, 2));
//        } catch (EntityNotFoundByIdException e) {
//            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
//        }

        // change checkOutDate
//        try{
//            reservationService.changeCheckOutDate(99, LocalDate.of(2024, 7, 11));
//        } catch (EntityNotFoundByIdException e) {
//            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
//        }


        // change bookingDate
//        try{
//            reservationService.changeBookingDate(99, LocalDate.of(2024, 6, 16));
//        } catch (EntityNotFoundByIdException e) {
//            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
//        }


        // get all reservations
//        List<Reservation> allReservations = reservationService.getAllReservations();
//        if ( ! allReservations.isEmpty() ) {
//            for (Reservation r : allReservations) {
//                System.out.println(r);
//            }
//        }


        // get reservation
//        try {
//            Reservation temp = reservationService.getReservation(99); // +
//            System.out.println(temp);
//        } catch (EntityNotFoundByIdException e) {
//            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
//        }

//        try {
//            Reservation temp = reservationService.getReservation(99);
//            System.out.println(temp);
//        } catch (EntityNotFoundByIdException e) {
//            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
//        }



        // add customer to reservation
//        try{
//            reservationService.addCustomerToReservation(11, 2); // -
//        } catch (ReferencedEntityNotFoundException e) {
//            NoReferencedRowException ne = (NoReferencedRowException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " -  " + ne.getMessage());
//        } catch (SameEntityValueExistInDbException e) {
//            DuplicateEntryException de = (DuplicateEntryException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - " + de.getDuplicateEntryName());
//        }
//
//
//        try{
//            reservationService.addCustomerToReservation(99, 1); // -
//        } catch (ReferencedEntityNotFoundException e) {
//            NoReferencedRowException ne = (NoReferencedRowException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " -  " + ne.getMessage());
//        } catch (SameEntityValueExistInDbException e) {
//            DuplicateEntryException de = (DuplicateEntryException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - " + de.getDuplicateEntryName());
//        }
//
//        try{
//            reservationService.addCustomerToReservation(17, 99); // -
//        } catch (ReferencedEntityNotFoundException e) {
//            NoReferencedRowException ne = (NoReferencedRowException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " -  " + ne.getMessage());
//        } catch (SameEntityValueExistInDbException e) {
//            DuplicateEntryException de = (DuplicateEntryException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - " + de.getDuplicateEntryName());
//        }
//
//
//        try{
//            reservationService.addCustomerToReservation(98, 99); // -
//        } catch (ReferencedEntityNotFoundException e) {
//            NoReferencedRowException ne = (NoReferencedRowException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " -  " + ne.getMessage());
//        } catch (SameEntityValueExistInDbException e) {
//            DuplicateEntryException de = (DuplicateEntryException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - " + de.getDuplicateEntryName());
//        }


        // change customer on reservation
//        try{
//            reservationService.changeCustomerOnReservation(13, 15, 8); // -
//        } catch (ReferencedEntityNotFoundException e) {
//            NoReferencedRowException ne = (NoReferencedRowException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " -  " + ne.getMessage());
//        } catch (EntityNotFoundByIdException e) {
//            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
//        } catch (SameEntityValueExistInDbException e) {
//            DuplicateEntryException de = (DuplicateEntryException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - " + de.getDuplicateEntryName());
//        }
//
//
//        try{
//            reservationService.changeCustomerOnReservation(17, 99, 2); // -
//        } catch (ReferencedEntityNotFoundException e) {
//            NoReferencedRowException ne = (NoReferencedRowException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " -  " + ne.getMessage());
//        } catch (EntityNotFoundByIdException e) {
//            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
//        } catch (SameEntityValueExistInDbException e) {
//            DuplicateEntryException de = (DuplicateEntryException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - " + de.getDuplicateEntryName());
//        }
//
//
//        try{
//            reservationService.changeCustomerOnReservation(17, 15, 99); // -
//        } catch (ReferencedEntityNotFoundException e) {
//            NoReferencedRowException ne = (NoReferencedRowException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " -  " + ne.getMessage());
//        } catch (EntityNotFoundByIdException e) {
//            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
//        } catch (SameEntityValueExistInDbException e) {
//            DuplicateEntryException de = (DuplicateEntryException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - " + de.getDuplicateEntryName());
//        }



        // adding receptionist to reservation
//        try{
//            reservationService.addReceptionistToReservation(9, 4); // +
//        } catch (ReferencedEntityNotFoundException e) {
//            NoReferencedRowException ne = (NoReferencedRowException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " -  " + ne.getMessage());
//        } catch (EntityNotFoundByIdException e) {
//            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
//        }


//        try{
//            reservationService.addReceptionistToReservation(11, 6); // +
//        } catch (ReferencedEntityNotFoundException e) {
//            NoReferencedRowException ne = (NoReferencedRowException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " -  " + ne.getMessage());
//        } catch (EntityNotFoundByIdException e) {
//            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
//        }


//        try{
//            reservationService.addReceptionistToReservation(99, 4); // +
//        } catch (ReferencedEntityNotFoundException e) {
//            NoReferencedRowException ne = (NoReferencedRowException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " -  " + ne.getMessage());
//        } catch (EntityNotFoundByIdException e) {
//            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
//        }


//        try{
//            reservationService.addReceptionistToReservation(12, 99); // +
//        } catch (ReferencedEntityNotFoundException e) {
//            NoReferencedRowException ne = (NoReferencedRowException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " -  " + ne.getMessage());
//        } catch (EntityNotFoundByIdException e) {
//            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
//        }


        // delete customer from reservation
//        try{
//            reservationService.deleteCustomerFromReservation(99, 1); // -
//        } catch (EntityNotFoundByIdException e) {
//            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - id1: " + z.getId() + " -  id2: " + z.getSecondId());
//        }
//
//        try{
//            reservationService.deleteCustomerFromReservation(11, 99); // -
//        } catch (EntityNotFoundByIdException e) {
//            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - id1: " + z.getId() + " -  id2: " + z.getSecondId());
//        }

//        try {
//            reservationService.deleteCustomerFromReservation(15, 8); // -
//        } catch (EntityNotFoundByIdException e) {
//            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - id1: " + z.getId() + " -  id2: " + z.getSecondId());
//        }



        // delete reservation
//        try {
//            Reservation deletedReservation = reservationService.deleteReservation(10);
//            System.out.println(deletedReservation);
//        } catch (EntityNotFoundByIdException e) {
//            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
//        }
//
//        try {
//            Reservation deletedReservation = reservationService.deleteReservation(99);
//            System.out.println(deletedReservation);
//        } catch (EntityNotFoundByIdException e) {
//            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
//        }


        // get all reservations one by one with its customers and rooms
        List<ReservationWithCustomerAndRoom> allReservationsWithItsCustomers =  reservationService.getAllReservationsWithTheirCustomersAndRooms();
        for (ReservationWithCustomerAndRoom reservationWithCustomer : allReservationsWithItsCustomers) {
            System.out.println(reservationWithCustomer);
        }


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
//        roomService.createRoom(r1);
//        roomService.createRoom(r2);
//        roomService.createRoom(r3);


        // changing roomName
//        try{
//            roomService.changeRoomName(99, "New Deluxe Room");
//        } catch (EntityNotFoundByIdException e) {
//            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
//        }

        // changing capacity
//        try{
//            roomService.changeCapacity(99, 160);
//        } catch (EntityNotFoundByIdException e) {
//            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
//        }

        // changing price
//        try{
//            roomService.changePrice(99, 80);
//        } catch (EntityNotFoundByIdException e) {
//            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
//        }

        // get room
//        Room temp;
//        try {
//            temp = roomService.getRoom(8);
//            System.out.println(temp);
//        }
//        catch (EntityNotFoundByIdException e) {
//            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
//        }
//
//        try {
//            temp = roomService.getRoom(99);
//            System.out.println(temp);
//        }
//        catch (EntityNotFoundByIdException e) {
//            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
//        }



        // get all rooms
//        List<Room> allRooms = roomService.getAllRooms();
//        if ( ! allRooms.isEmpty()) {
//            for (Room r : allRooms) {
//                System.out.println(r);
//            }
//        }



        // add room to reservation
//        try{
//            roomService.addRoomToReservation(99, 11);
//        } catch (EntityNotFoundByIdException e) {
//            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
//        } catch (ReferencedEntityNotFoundException e) {
//            NoReferencedRowException ne = (NoReferencedRowException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " -  " + ne.getMessage());
//        }
//
//        try{
//            roomService.addRoomToReservation(9, 88);
//        } catch (EntityNotFoundByIdException e) {
//            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
//        } catch (ReferencedEntityNotFoundException e) {
//            NoReferencedRowException ne = (NoReferencedRowException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " -  " + ne.getMessage());
//        }
//
//        try{
//            roomService.addRoomToReservation(6, 13);
//        } catch (EntityNotFoundByIdException e) {
//            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
//        } catch (ReferencedEntityNotFoundException e) {
//            NoReferencedRowException ne = (NoReferencedRowException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " -  " + ne.getMessage());
//        }


        // change room on reservation
//        try{
//            roomService.changeRoomOnReservation(99, 11);
//        } catch (EntityNotFoundByIdException e) {
//            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
//        } catch (ReferencedEntityNotFoundException e) {
//            NoReferencedRowException ne = (NoReferencedRowException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " -  " + ne.getMessage());
//        }
//
//
//        try{
//            roomService.changeRoomOnReservation(9, 88);
//        } catch (EntityNotFoundByIdException e) {
//            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
//        } catch (ReferencedEntityNotFoundException e) {
//            NoReferencedRowException ne = (NoReferencedRowException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " -  " + ne.getMessage());
//        }




        // delete room from reservation
//        try{
//            roomService.deleteRoomFromReservation(99);
//        } catch (EntityNotFoundByIdException e) {
//            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
//        }
//
//        try{
//            roomService.deleteRoomFromReservation(8);
//        } catch (EntityNotFoundByIdException e) {
//            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
//        }




        // add feature to room
//        try{
//            roomService.addFeatureToRoom(99, 8);
//        } catch (ReferencedEntityNotFoundException e) {
//            NoReferencedRowException ne = (NoReferencedRowException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " -  " + ne.getMessage());
//        } catch (SameEntityValueExistInDbException e) {
//            DuplicateEntryException de = (DuplicateEntryException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - " + de.getDuplicateEntryName());
//        }
//
//        try{
//            roomService.addFeatureToRoom(12, 99);
//        } catch (ReferencedEntityNotFoundException e) {
//            NoReferencedRowException ne = (NoReferencedRowException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " -  " + ne.getMessage());
//        } catch (SameEntityValueExistInDbException e) {
//            DuplicateEntryException de = (DuplicateEntryException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - " + de.getDuplicateEntryName());
//        }
//
//        try{
//            roomService.addFeatureToRoom(12, 5);
//        } catch (ReferencedEntityNotFoundException e) {
//            NoReferencedRowException ne = (NoReferencedRowException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " -  " + ne.getMessage());
//        } catch (SameEntityValueExistInDbException e) {
//            DuplicateEntryException de = (DuplicateEntryException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - " + de.getDuplicateEntryName());
//        }


        // change feature on room
//        try{
//            roomService.changeFeatureOnRoom(12, 13,  99);
//        } catch (EntityNotFoundByIdException e) {
//            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
//        } catch (ReferencedEntityNotFoundException e) {
//            NoReferencedRowException ne = (NoReferencedRowException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " -  " + ne.getMessage());
//        } catch (SameEntityValueExistInDbException e) {
//            DuplicateEntryException de = (DuplicateEntryException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - " + de.getDuplicateEntryName());
//        }
//
//
//        try{
//            roomService.changeFeatureOnRoom(11, 12,  8);
//        } catch (EntityNotFoundByIdException e) {
//            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
//        } catch (ReferencedEntityNotFoundException e) {
//            NoReferencedRowException ne = (NoReferencedRowException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " -  " + ne.getMessage());
//        } catch (SameEntityValueExistInDbException e) {
//            DuplicateEntryException de = (DuplicateEntryException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - " + de.getDuplicateEntryName());
//        }
//
//
//        try{
//            roomService.changeFeatureOnRoom(99, 14,  6);
//        } catch (EntityNotFoundByIdException e) {
//            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
//        } catch (ReferencedEntityNotFoundException e) {
//            NoReferencedRowException ne = (NoReferencedRowException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " -  " + ne.getMessage());
//        } catch (SameEntityValueExistInDbException e) {
//            DuplicateEntryException de = (DuplicateEntryException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - " + de.getDuplicateEntryName());
//        }





        // delete feature from room
//        try{
//            roomService.deleteFeatureFromRoom(14, 6);
//        } catch (EntityNotFoundByIdException e) {
//            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
//        }
//
//        try{
//            roomService.deleteFeatureFromRoom(20, 50);
//        } catch (EntityNotFoundByIdException e) {
//            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
//        }



        // add service to room
//        try{
//            roomService.addServiceToRoom(1, 7);
//        } catch (ReferencedEntityNotFoundException e) {
//            NoReferencedRowException ne = (NoReferencedRowException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " -  " + ne.getMessage());
//        } catch (SameEntityValueExistInDbException e) {
//            DuplicateEntryException de = (DuplicateEntryException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - " + de.getDuplicateEntryName());
//        }

//        try{
//            roomService.addServiceToRoom(99, 8);
//        } catch (ReferencedEntityNotFoundException e) {
//            NoReferencedRowException ne = (NoReferencedRowException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " -  " + ne.getMessage());
//        } catch (SameEntityValueExistInDbException e) {
//            DuplicateEntryException de = (DuplicateEntryException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - " + de.getDuplicateEntryName());
//        }
//
//        try{
//            roomService.addServiceToRoom(4, 5);
//        } catch (ReferencedEntityNotFoundException e) {
//            NoReferencedRowException ne = (NoReferencedRowException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " -  " + ne.getMessage());
//        } catch (SameEntityValueExistInDbException e) {
//            DuplicateEntryException de = (DuplicateEntryException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - " + de.getDuplicateEntryName());
//        }



        // change service on room
//        try{
//            roomService.changeServiceOnRoom(2, 3, 8);
//        } catch (EntityNotFoundByIdException e) {
//            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
//        } catch (ReferencedEntityNotFoundException e) {
//            NoReferencedRowException ne = (NoReferencedRowException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " -  " + ne.getMessage());
//        } catch (SameEntityValueExistInDbException e) {
//            DuplicateEntryException de = (DuplicateEntryException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - " + de.getDuplicateEntryName());
//        }
//
//
//        try{
//            roomService.changeServiceOnRoom(2, 3, 99);
//        } catch (EntityNotFoundByIdException e) {
//            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
//        } catch (ReferencedEntityNotFoundException e) {
//            NoReferencedRowException ne = (NoReferencedRowException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " -  " + ne.getMessage());
//        } catch (SameEntityValueExistInDbException e) {
//            DuplicateEntryException de = (DuplicateEntryException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - " + de.getDuplicateEntryName());
//        }
//
//
//        try{
//            roomService.changeServiceOnRoom(99, 3, 7);
//        } catch (EntityNotFoundByIdException e) {
//            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
//        } catch (ReferencedEntityNotFoundException e) {
//            NoReferencedRowException ne = (NoReferencedRowException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " -  " + ne.getMessage());
//        } catch (SameEntityValueExistInDbException e) {
//            DuplicateEntryException de = (DuplicateEntryException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - " + de.getDuplicateEntryName());
//        }



        // delete service from room
//        try{
//            roomService.deleteServiceFromRoom(4, 5);
//        } catch (EntityNotFoundByIdException e) {
//            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
//        }
//
//        try{
//            roomService.deleteServiceFromRoom(30, 89);
//        } catch (EntityNotFoundByIdException e) {
//            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
//        }



        // delete room
//        try {
//            Room deletedRoom = roomService.deleteRoom(99);
//            System.out.println(deletedRoom);
//        } catch (EntityNotFoundByIdException e) {
//            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
//        }


        // get all rooms woth their features and services
        List<RoomWithFeatureAndService> all = roomService.getAllRoomsWithTheirFeaturesAndServices();
        for (RoomWithFeatureAndService roomWithFeatureAndService : all) {
            System.out.println(roomWithFeatureAndService);
        }


    }




    public static void customerServiceImplUnitTest(){

        CustomerService cService = new CustomerServiceImpl();

        // create customer
        Customer customer1 = new Customer("Ali Veli", "123-1234", LocalDate.of(1995, 3, 25), "Sadık müşteri");
        Customer customer2 = new Customer("Mehmet Aslan", "555-5555", LocalDate.of(1998, 7, 10), "Yeni üye");
        Customer customer3 = new Customer("Gamze Arslan", "555-5566", LocalDate.of(1982, 12, 5), "Premium üye");

//        try{
//            cService.createCustomer(customer1);
//        } catch (SameEntityValueExistInDbException e) {
//            System.out.println(e.getMessage());
//        }

//        try{
//            cService.createCustomer(customer2);
//        } catch (SameEntityValueExistInDbException e) {
//            DuplicateEntryException de = (DuplicateEntryException) e.getCause();
//            System.out.println(e.getMessage() + " -  DuplicateEntryName: " + de.getDuplicateEntryName());
//        }

//        try{
//            cService.createCustomer(customer3);
//        } catch (SameEntityValueExistInDbException e) {
//            System.out.println(e.getMessage());
//        }


        // get all customer
//        List<Customer> allCustomers = cService.getAllCustomers();
//        if ( ! allCustomers.isEmpty()) {
//            for (Customer c : allCustomers) {
//                System.out.println(c);
//            }
//        }

        // change full name
//        try{
//            cService.changeFullName(99, "Ahmet Celik");
//        } catch (EntityNotFoundByIdException e) {
//            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
//        }


        // change phone number
//        try{
//            cService.changePhoneNumber(99, "232-4323");
//        } catch (EntityNotFoundByIdException e) {
//            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
//        } catch (SameEntityValueExistInDbException e) {
//            DuplicateEntryException de = (DuplicateEntryException) e.getCause();
//            System.out.println(e.getMessage() + " -  DuplicateEntryName: " + de.getDuplicateEntryName());
//        }

//        try{
//            cService.changePhoneNumber(11, "555-5555");
//        } catch (EntityNotFoundByIdException e) {
//            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
//            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
//        } catch (SameEntityValueExistInDbException e) {
//            DuplicateEntryException de = (DuplicateEntryException) e.getCause();
//            System.out.println(e.getMessage() + " -  DuplicateEntryName: " + de.getDuplicateEntryName());
//        }


        // change birth date
//        try{
//            cService.changeBirthDate(99, LocalDate.of(1996, 4, 26));
//        } catch (EntityNotFoundByIdException e) {
//            System.out.println(e.getMessage());
//        }

        // change description
        try{
            cService.changeDescription(99, "new description");
        } catch (EntityNotFoundByIdException e) {
            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
        }


        // get one customer
        try {
            Customer temp = cService.getCustomer(15);
            System.out.println(temp);
        } catch (EntityNotFoundByIdException e) {
            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
        }

        try {
            Customer temp = cService.getCustomer(99);
            System.out.println(temp);
        } catch (EntityNotFoundByIdException e) {
            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
        }

        // delete customer
        try {
            Customer deletedCustomer = cService.deleteCustomer(9);
            System.out.println(deletedCustomer);
        } catch (EntityNotFoundByIdException e) {
            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
        }

        try {
            Customer deletedCustomer = cService.deleteCustomer(99);
            System.out.println(deletedCustomer);
        } catch (EntityNotFoundByIdException e) {
            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
        }

    }



    public static void serviceServiceImplUnitTest(){

        ServiceService sService = new ServiceServiceImpl();

        // create service
        Service service1 = new Service("asd1", 800);
        Service service2 = new Service("dsa2", 1500);
        Service service3 = new Service("sda3", 2000);

        sService.createService(service1);
        sService.createService(service2);
        sService.createService(service3);

        // get all services
        List<Service> allServices = sService.getAllServices();
        for (Service s : allServices){
            System.out.println(s);
        }

        // change service name
        try{
            sService.changeServiceName(99, "changed service");
        } catch (EntityNotFoundByIdException e) {
            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
        }

        // change price
        try{
            sService.changePrice(99, 1000);
        } catch (EntityNotFoundByIdException e) {
            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
        }

        // get one service
        try {
            Service temp = sService.getService(4);
            System.out.println(temp);
        }
        catch (EntityNotFoundByIdException e) {
            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
        }

        try {
            Service temp = sService.getService(99);
            System.out.println(temp);
        }
        catch (EntityNotFoundByIdException e) {
            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
        }

        // delete service
        try {
            Service deletedService = sService.deleteService(99);
            System.out.println(deletedService);
        }
        catch (EntityNotFoundByIdException e) {
            ZeroRowsAffectedOrReturnedException z = (ZeroRowsAffectedOrReturnedException) e.getCause();
            System.out.println("x - " + e.getMessage() + " - id: " + z.getId());
        }

    }


}














