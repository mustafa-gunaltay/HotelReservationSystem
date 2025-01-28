package com.mustafa.hotelreservationsystem.dao;


import com.mustafa.hotelreservationsystem.models.Feature;
import com.mustafa.hotelreservationsystem.models.Receptionist;
import com.mustafa.hotelreservationsystem.models.Reservation;
import com.mustafa.hotelreservationsystem.models.Room;

import java.time.LocalDate;
import java.util.List;

public class DaoTests {
    public static void featureDaoImplUnitTest(){

        FeatureDao fdao = new FeatureDaoImpl();

        // creating feature
//        fdao.save(new Feature("feature 1", 1000));
//        fdao.save(new Feature("feature 2", 2000));
//        fdao.save(new Feature("feature 3", 3000));

        // retrieving all features
//        List<Feature> features = fdao.retrieveAllFeatures();
//        for (Feature feature : features) {
//            System.out.println(feature);
//        }

        // updating feature
//        fdao.update(new Feature(5, "updatedFeature1", 2999));


        // updating specified attribute with specified value
        fdao.updateSpecifiedFeatureField(9, "featureName", "new f1");


        // retrieving feature
//        Feature f1 = fdao.retrieve(2);
//        System.out.println(f1);

        // deleting feature
//        fdao.delete(2);
    }


    public static void adminDaoImplUnitTest(){

        AdminDao aDao = new AdminDaoImpl();

        // creating admin
//        aDao.save(new Admin("mustafa gunaltay", "user1", "pw1"));
//        aDao.save(new Admin("ali kilic", "user2", "pw2"));
//        aDao.save(new Admin("mehmet soylu", "user3", "pw3"));

        // retrieving all admins
//        List<Admin> adminList = aDao.retrieveAllAdmins();
//        for (Admin admin : adminList) {
//            System.out.println(admin);
//        }

        // updating admin
//        aDao.update(new Admin(2, "ali kilicoglu", "userr2", "pww2"));

        // updating specified attribute with specified value
//        aDao.updateSpecifiedAdminField(16, "passwordd", "now passwordd");

        // retrieve admin
//        Admin ra = aDao.retrieve(2);
//        System.out.println("Retrieve admin whose id is 2: \n" + ra);

        // delete admin
//        Admin da = aDao.delete(2);
//        System.out.println("Delete admin whose id is 2: \n" + da);

    }


    public static void receptionistDaoImplUnitTest(){

        ReceptionistDao rDao = new ReceptionistDaoImpl();

        // creating receptionist
//        Receptionist r1 = new Receptionist("must gun", "user must", "must123");
//        Receptionist r2 = new Receptionist("ali kilic", "user ali", "ali123");
//        Receptionist r3 = new Receptionist("mehmet soylu", "user mehmet", "mehmet123");
//        rDao.save(r1);
//        rDao.save(r2);
//        rDao.save(r3);

        // retrieving receptionists
//        List<Receptionist> allReceptionist = rDao.retrieveAllReceptionists();
//        for (Receptionist r : allReceptionist){
//            System.out.println(r);
//        }

        // updatig receptionist
//        Receptionist rToBeUpdated = new Receptionist(2, "new ali kilic", "new user ali", "newali123");
//        rDao.update(rToBeUpdated);

        // updating specified attribute with specified value
        rDao.updateSpecifiedReceptionistField(3, "dsadsa", "updated user mehmetov");

        // retrieve receptionist
//        Receptionist rRetrieve = rDao.retrieve(2);
//        System.out.println(rRetrieve);

        // delete receptionist
//        Receptionist rDeleted = rDao.delete(2);
//        System.out.println(rDeleted);

    }



    public static void roomDaoImplUnitTest(){

        RoomDao roomDao = new RoomDaoImpl();

        // creating room
        Receptionist onlyReceptionist = new Receptionist(4, "must gun", "user must", "must123");

        Reservation reservation1 = new Reservation(
                8,
                LocalDate.of(2024, 7, 15),
                LocalDate.of(2024, 7, 20),
                LocalDate.of(2024, 6, 30),
                onlyReceptionist
        );
        Reservation reservation2 = new Reservation(
                9,
                LocalDate.of(2024, 7, 15),
                LocalDate.of(2024, 7, 20),
                LocalDate.of(2024, 6, 30),
                onlyReceptionist
        );
        Reservation reservation3 = new Reservation(
                10,
                LocalDate.of(2024, 8, 5),
                LocalDate.of(2024, 8, 12),
                LocalDate.of(2024, 7, 20),
                onlyReceptionist
        );

        // rezervasyonsuz oda - id olacak 4
        Room room1 = new Room("r 1", 10, 1000, false, null);

        // rezervasyonsuz oda - 5
        Room room2 = new Room("r 2", 20, 2000, false, null);

        // rezervasyonlu oda - 6
        Room room3 = new Room("r 3", 30, 3000, true, reservation3);

//        roomDao.save(room1); // rezervasyonsuz
//        roomDao.save(room2); // rezervasyonsuz
//        roomDao.save(room3); // rezervasyonlu


        // retrieving all rooms
//        List<Room> allRooms = roomDao.retrieveAllRooms();
//        for (Room r : allRooms){
//            System.out.println(r);
//        }

        // updating room
//        Room roomToBeUpdated;
//
//        roomToBeUpdated = new Room(5, "new room 2", 21, 2001, true, reservation2);
//        roomDao.update(roomToBeUpdated);
//
//        roomToBeUpdated = new Room(5, "newnew room 2", 22, 2002, false, null);
//        roomDao.update(roomToBeUpdated);


        // retireve one room by id
//        Room temp;
//        temp = roomDao.retrieve(5);
//        System.out.println(temp);

        // updating specified room field with specified value
//        roomDao.updateSpecifiedRoomField(4, "price", 2003);


        // linking room to reservation (hem yeni baglantilar icin hem de olan baglantilari guncelemek icin)
//        roomDao.linkRoomToReservation(1, 1);
//        roomDao.linkRoomToReservation(3, 10);

        // unlinking room from reservation
//        roomDao.unlinkRoomFromReservation(1);
//        roomDao.unlinkRoomFromReservation(3);



        // binding room and feature
//        roomDao.bindRoomAndFeature(3, 11);

        // unbinding room and feature
//        roomDao.unbindRoomAndFeature(3, 11);

        // binding room and service
//        roomDao.bindRoomAndService(3, 1);

        // unbinding room and service
//        roomDao.unbindRoomAndService(3, 1);

        // deleting room
        Room deletedRoom = roomDao.delete(3);
        System.out.println(deletedRoom);

    }



    public static void reservationDaoImplUnitTest(){

        ReservationDao rDao = new ReservationDaoImpl();

        // creating reservation
        Receptionist receptionist1 = new Receptionist(5, "new ali kilic", "user ali", "ali123");
        Receptionist receptionist2 = new Receptionist(6, "mehmet soylu", "new user mehmet", "newmehmet123");

        Reservation reservation1 = new Reservation( // id'si 10 olacak
                LocalDate.of(3024, 6, 1),
                LocalDate.of(3024, 6, 10),
                LocalDate.of(3024, 5, 15),
                receptionist1
        );

        Reservation reservation2 = new Reservation( // 11
                LocalDate.of(3024, 7, 15),
                LocalDate.of(3024, 7, 20),
                LocalDate.of(3024, 6, 30),
                receptionist1
        );

        Reservation reservation3 = new Reservation( // 12
                LocalDate.of(3024, 8, 5),
                LocalDate.of(3024, 8, 12),
                LocalDate.of(3024, 7, 20),
                receptionist2
        );

//        rDao.save(reservation1);
//        rDao.save(reservation2);
//        rDao.save(reservation3);


        // retrieving all reservations
//        List<Reservation> allReservations = rDao.retrieveAllReservations();
//        for (Reservation r : allReservations){
//            System.out.println(r);
//        }

        // updating reservation
        Reservation resToBeUpdated = new Reservation(
                10,
                LocalDate.of(3024, 6, 12),
                LocalDate.of(3024, 6, 21),
                LocalDate.of(3024, 5, 26),
                receptionist1
        );
//        rDao.update(resToBeUpdated);

        // retrieve one reservation
//        Reservation temp;
//        temp = rDao.retrieve(10);
//        System.out.println(temp);

        // updating specified reservation field with specified value
//        rDao.updateSpecifiedReservationField(11, "checkInDate", LocalDate.of(2025, 8, 16));
//        rDao.updateSpecifiedReservationField(11, "receptionistId", 6);
        rDao.updateSpecifiedReservationField(8, "receptionistId", 4);


        // binding reservation and customer
//        rDao.bindReservationAndCustomer(12, 1);
//        rDao.bindReservationAndCustomer(12, 2);
//        rDao.bindReservationAndCustomer(11, 2);

        // unbinding reservation and customer
//        rDao.unbindReservationAndCustomer(12, 1);
//        rDao.unbindReservationAndCustomer(12, 2);
//        rDao.unbindReservationAndCustomer(11, 2);


        // unlinking reservation from receptionist
//        rDao.unlinkReservationFromReceptionist(11);
//        rDao.unlinkReservationFromReceptionist(12);

        // linking reservation to receptionist
//        rDao.linkReservationToReceptionist(11, 5);
//        rDao.linkReservationToReceptionist(12, 5);




    }


}
