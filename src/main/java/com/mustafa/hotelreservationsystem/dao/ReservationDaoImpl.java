package com.mustafa.hotelreservationsystem.dao;

import com.mustafa.hotelreservationsystem.exceptions.db.DuplicateEntryException;
import com.mustafa.hotelreservationsystem.exceptions.db.NoReferencedRowException;
import com.mustafa.hotelreservationsystem.exceptions.db.ZeroRowsAffectedOrReturnedException;
import com.mustafa.hotelreservationsystem.models.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReservationDaoImpl implements ReservationDao{


    private static final String URL = "jdbc:mysql://localhost:3306/hotelreservationsystemrd";
    private static final String USER = "root";
    private static final String PASSWORD = "karakama123--";



    // NOT = Bir rezervasyon da resepsiyonistId null olabilir ama bunun tek olasiligi bir rezervasyonu yapan
    //       resepsiyonistin sistemden silinme durumudur
    // Bunun disinda bir rezervasyonun resepsiyonist olmadan yapilma durumu soz konusu degildir, cunku en basta
    // login isleminde bir resepsiyonist sisteme giris yapacaktir

    // Gorevi = recepsionisti olan Rezervasyonlari DB'e kaydetmek
    // Not = gecilen reservation nesnesindeki receptionist attribute'u icindeki id degeri dolu olmali
    @Override
    public void save(Entity e) {
        Reservation r = (Reservation) e;
        LocalDate checkInDate = r.getCheckInDate();
        LocalDate checkOutDate = r.getCheckOutDate();
        LocalDate bookingDate = r.getBookingDate();

        Receptionist receptionist = r.getReceptionist(); // resepsiyonistsiz rezervasyon yapilamaz
        if (receptionist == null || receptionist.getId() == 0){
            System.out.println("Receptionist can not be null / a reservation can't be arranged without receptionist");
            return;
        }



        String query = "INSERT INTO reservation (checkInDate, checkOutDate, bookingDate, receptionistId) VALUES (?, ?, ?, ?)";
        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query)
        )
        {

            ps.setDate(1, Date.valueOf(checkInDate));
            ps.setDate(2, Date.valueOf(checkOutDate));
            ps.setDate(3, Date.valueOf(bookingDate));
            ps.setLong(4, receptionist.getId());

            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " rows affected");
        }
        catch (SQLException ex){
            System.out.println(ex);
        }
    }


    // Gorevi = Rezervasyonun tum alanlarini ayni anda gunceller (hicbir alan bos olmamali)
    // Exception = Olmayan bir resepsiyonistin Id numarasi degistirilmeye calisilirsa ne olacak
    @Override
    public void update(Entity e) throws NoReferencedRowException, ZeroRowsAffectedOrReturnedException {

        Reservation r = (Reservation) e;
        long id = r.getId();
        LocalDate checkInDate = r.getCheckInDate();
        LocalDate checkOutDate = r.getCheckOutDate();
        LocalDate bookingDate = r.getBookingDate();

        Receptionist receptionist = r.getReceptionist(); // resepsiyonistsiz rezervasyon yapilamaz
        if (receptionist == null || receptionist.getId() == 0){
            System.out.println("Receptionist can not be null / a reservation can't be arranged without receptionist");
            return;
        }


        String query = "UPDATE reservation SET checkInDate = ?, checkOutDate = ?, bookingDate = ?, receptionistId = ? WHERE id = ?";
        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query);
        )
        {
            ps.setDate(1, Date.valueOf(checkInDate));
            ps.setDate(2, Date.valueOf(checkOutDate));
            ps.setDate(3, Date.valueOf(bookingDate));
            ps.setLong(4, receptionist.getId());
            ps.setLong(5, id);

            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " rows affected");
            if (rowsAffected < 1){
                throw new ZeroRowsAffectedOrReturnedException("Zero rows affected on UPDATE", id);
            }
        }
        catch (SQLException ex){
            System.out.println(ex);

            int errorCode = ex.getErrorCode();
            if (errorCode == MySqlErrors.FOREIGN_KEY_CONSTRAINT_VIOLATION.getCode()) {
                throw new NoReferencedRowException("No corresponding row in receptionist table - receptionistId: " + receptionist.getId());
            }
        }
    }

    // Gorevi = id si verilen Rezervasyonu getirir
    // Exception = bu id de bir reservasyon bulunamamasi durumu
    @Override
    public Reservation retrieve(long id) throws ZeroRowsAffectedOrReturnedException {
        Reservation result = null;

        String query = "SELECT id, checkInDate, checkOutDate, bookingDate, receptionistId FROM reservation WHERE id = ?";

        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query)
        )
        {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                long targetid = rs.getLong("id");
                LocalDate checkInDate = rs.getDate("checkInDate").toLocalDate();
                LocalDate checkOutDate = rs.getDate("checkOutDate").toLocalDate();
                LocalDate bookingDate = rs.getDate("bookingDate").toLocalDate();

                long receptionistId = rs.getLong("receptionistId");
                Receptionist receptionist;
                if (rs.wasNull()) {
                    receptionist = null;
                } else {
                    ReceptionistDao receptionistDao = new ReceptionistDaoImpl();
                    try{
                        receptionist = receptionistDao.retrieve(receptionistId);
                    } catch (ZeroRowsAffectedOrReturnedException e) {
                        System.out.println("Receptionist with 'receptionistId' is not found");
                        receptionist = null;
                    }
                }

                result = new Reservation(targetid, checkInDate, checkOutDate, bookingDate, receptionist);
            }
        }
        catch (SQLException ex){
            System.out.println(ex);
        }

        if (result == null){
            throw new ZeroRowsAffectedOrReturnedException("Zero rows returned on SELECT", id);
        }
        else {
            return result;
        }

    }

    // Gorevi = id si verilen rezervasyonu db'den silecek
    // Exception = verilen id'de rezervasyon yoksa
    @Override
    public Reservation delete(long id) throws ZeroRowsAffectedOrReturnedException {

        Reservation deletedReservation = retrieve(id);

        String query = "DELETE FROM reservation WHERE id = ?";

        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query)
        )
        {

            ps.setLong(1, id);

            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " rows affected");
            if (rowsAffected < 1){
                throw new ZeroRowsAffectedOrReturnedException("Zero rows affected on DELETE", id);
            }

        }
        catch (SQLException ex){
            System.out.println(ex);
        }

        return deletedReservation;
    }


    // Gorevi = Tum rezervasyon varliklarini liste olrak getirir
    // Exception = liste bossa
    @Override
    public List<Reservation> retrieveAllReservations() {

        List<Reservation> allReservations = new ArrayList<>();

        String query = "SELECT id, checkInDate, checkOutDate, bookingDate, receptionistId FROM reservation";

        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query)
        )
        {

            ResultSet rs = ps.executeQuery();

            while (rs.next()){

                long id = rs.getLong("id");
                LocalDate checkInDate = rs.getDate("checkInDate").toLocalDate();
                LocalDate checkOutDate = rs.getDate("checkOutDate").toLocalDate();
                LocalDate bookingDate = rs.getDate("bookingDate").toLocalDate();

                Receptionist receptionist;
                long receptionistId = rs.getLong("receptionistId");
                if (rs.wasNull()){
                    receptionist = null;
                }
                else{
                    ReceptionistDao rDao = new ReceptionistDaoImpl();

                    try{
                        receptionist = rDao.retrieve(receptionistId);
                    }
                    catch (ZeroRowsAffectedOrReturnedException e){
                        System.out.println("Receptionist with 'receptionistId' is not found");
                        receptionist = null;
                    }
                }

                allReservations.add(new Reservation(id, checkInDate, checkOutDate, bookingDate, receptionist));

            }
        }
        catch (SQLException ex){
            System.out.println(ex);
        }

        return allReservations;
    }


    // Gorevi = id si verilen rezervasyonda verilen alana verilen degeri atar,
    // NOT    = fieldName = receptionistId olmasi durmunda, fieldValue long verilmeli

    // Exception = verilen id'de rezervasyon yoksa
    // Exception = verilen alan unique ise ve onceden olan bir deger tekrar atanmak istendiyse
    @Override
    public void updateSpecifiedReservationField(long id, String fieldName, Object fieldValue) throws NoReferencedRowException, ZeroRowsAffectedOrReturnedException{

        List<String> allowedFieldNames = Arrays.asList("checkInDate", "checkOutDate", "bookingDate", "receptionistId");

        if (!allowedFieldNames.contains(fieldName)){
            System.out.println("Field: " + fieldName + " is not match any table name of feature");
            return;
        }

        String query = "UPDATE reservation SET " + fieldName + " = ? WHERE id = ?";
        try(
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query)
        )
        {
            ps.setObject(1, fieldValue);
            ps.setLong(2, id);

            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " rows affected");
            if (rowsAffected < 1){
                throw new ZeroRowsAffectedOrReturnedException("Zero rows affected on UPDATE", id);
            }
        }
        catch (SQLException ex){
            System.out.println(ex);

            int errorCode = ex.getErrorCode();
            if (errorCode == MySqlErrors.FOREIGN_KEY_CONSTRAINT_VIOLATION.getCode() && fieldName.equals("receptionistId")){
                throw new NoReferencedRowException("No corresponding row in receptionist table - receptionistId: " + fieldValue);
            }
        }
    }

    // Gorevi = rezervasyon ve customer'i reservation_customer ara tablosuna koyar
    // Exception;
    // 1- onceden reservation_customer tablosunda resId ve cusId si ayni olan bir satir varsa
    // 2- Reservation tablosunda resId li bir rezervasyon varligi yoksa
    // 3- Customer tablosunda cusId li bir musteri varligi yoksa
    @Override
    public void bindReservationAndCustomer(long resId, long cusId) throws NoReferencedRowException, DuplicateEntryException{

        String query = "INSERT INTO reservation_customer (reservationId, customerId) VALUES (?, ?)";

        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query)
        )
        {
            ps.setLong(1, resId);
            ps.setLong(2, cusId);

            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " rows affected");
        }
        catch (SQLException ex){
            System.out.println(ex);

            int errorCode = ex.getErrorCode();
            if (errorCode == MySqlErrors.FOREIGN_KEY_CONSTRAINT_VIOLATION.getCode()){
                throw new NoReferencedRowException("No corresponding row in reservation or customer table - reservationId: " + resId + " customerId: " + cusId);
            }
            if (errorCode == MySqlErrors.DUPLICATE_ENTRY.getCode()){
                throw new DuplicateEntryException("Duplicate entry for primary key set", "resId: " + resId + " -  cusId: " + cusId);
            }
        }
    }

    // Gorevi = reservation_customer ara tablosunda resId ve cusId olan satiri siler
    // Exception = ara tabloda resId ve cusId nin oldugu bir satir yoksa
    @Override
    public void unbindReservationAndCustomer(long resId, long cusId) throws ZeroRowsAffectedOrReturnedException {

        String query = "DELETE FROM reservation_customer WHERE reservationId = ? AND customerId = ?";

        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query)
        )
        {
            ps.setLong(1, resId);
            ps.setLong(2, cusId);

            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " rows affected");
            if (rowsAffected < 1){
                throw new ZeroRowsAffectedOrReturnedException("Zero rows affected on DELETE", resId, cusId);
            }
        }
        catch (SQLException ex){
            System.out.println(ex);
        }
    }


    // Gorevi;
    // 1- bir resepsiyoniste kayitli olmayan rezervasyonu kayit etmek icin (bu durum sadece bir resepsiyonist silidniginde gerceklesebilir)
    // 2- bir resepsinoiste kayitli olan rezervasyonu baska bir baska bir respesiyonist ile degistirmek icin
    // Exception;
    // 1- resId id'li bir rezervasyon bulunamazsa
    // 2- verilen recId receptionist tablosunda olan bir resepsiyonistin id'si ile eslesmiyorsa
    @Override
    public void linkReservationToReceptionist(long resId, long recId) throws NoReferencedRowException, ZeroRowsAffectedOrReturnedException {

        String query = "UPDATE reservation SET receptionistId = ? WHERE id = ?";

        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query)
        )
        {
            ps.setLong(1, recId);
            ps.setLong(2, resId);

            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " rows affected");
            if (rowsAffected < 1){
                throw new ZeroRowsAffectedOrReturnedException("Zero rows affected on DELETE", resId);
            }

        }
        catch (SQLException ex){
            System.out.println(ex);

            int errorCode = ex.getErrorCode();
            if (errorCode == MySqlErrors.FOREIGN_KEY_CONSTRAINT_VIOLATION.getCode()){
                throw new NoReferencedRowException("No corresponding row in receptionist table - receptionistId: " + recId);
            }
        }
    }

    // Gorevi = Rezervasyondan, o rezervasyonu yapan resepsiyonistin id'sini cikartir
    // Exception = resId id'li bir rezervasyon bulunamazsa
    @Override
    public void unlinkReservationFromReceptionist(long resId) throws ZeroRowsAffectedOrReturnedException {

        String query = "UPDATE reservation SET receptionistId = ? WHERE id = ?";

        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query)
        )
        {
            ps.setNull(1, java.sql.Types.INTEGER);
            ps.setLong(2, resId);

            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " rows affected");
            if (rowsAffected < 1){
                throw new ZeroRowsAffectedOrReturnedException("Zero rows affected on DELETE", resId);
            }
        }
        catch (SQLException ex){
            System.out.println(ex);
        }

    }


    @Override
    public List<ReservationWithCustomerAndRoom> retrieveAllReservationsWithItsCustomersAndRooms(){

        List<ReservationWithCustomerAndRoom> allReservationsWithItsCustomersAndRooms = new ArrayList<>();

        String query = "SELECT reservation.id AS reservationId, reservation.checkInDate, reservation.checkOutDate, reservation.bookingDate, reservation.receptionistId, \n" +
                "customer.id AS customerId, customer.fullName, customer.phoneNumber, customer.birthDate, customer.description, \n" +
                "room.id AS roomId, room.roomName, room.capacity, room.price, room.isReserved\n" +
                "FROM reservation\n" +
                "INNER JOIN reservation_customer ON reservation.id = reservation_customer.reservationId\n" +
                "INNER JOIN customer ON customer.id = reservation_customer.customerId\n" +
                "INNER JOIN room ON room.reservationId = reservation.id;";

        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query)
        )
        {
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                long reservationId = rs.getLong("reservationId");
                LocalDate checkInDate = rs.getDate("checkInDate").toLocalDate();
                LocalDate checkOutDate = rs.getDate("checkOutDate").toLocalDate();
                LocalDate bookingDate = rs.getDate("bookingDate").toLocalDate();
                Receptionist receptionist;
                long receptionistId = rs.getLong("receptionistId");
                if (rs.wasNull()){
                    receptionist = null;
                }
                else{
                    ReceptionistDao rDao = new ReceptionistDaoImpl();

                    try{
                        receptionist = rDao.retrieve(receptionistId);
                    }
                    catch (ZeroRowsAffectedOrReturnedException e){
                        System.out.println("Receptionist with 'receptionistId' is not found");
                        receptionist = null;
                    }
                }

                long customerId = rs.getLong("customerId");
                String fullName = rs.getString("fullName");
                String phoneNumber = rs.getString("phoneNumber");
                LocalDate birthDate = rs.getDate("birthDate").toLocalDate();
                String description = rs.getString("description");

                long roomId = rs.getLong("roomId");
                String roomName = rs.getString("roomName");
                int capacity = rs.getInt("capacity");
                int price = rs.getInt("price");
                boolean isReserved = rs.getBoolean("isReserved");

                allReservationsWithItsCustomersAndRooms.add(
                        new ReservationWithCustomerAndRoom
                                (reservationId, checkInDate, checkOutDate, bookingDate, receptionist,
                                customerId, fullName, phoneNumber, birthDate, description,
                                roomId, roomName, capacity, price, isReserved)
                );
            }
        }
        catch (SQLException ex){
            System.out.println(ex);
        }

        return allReservationsWithItsCustomersAndRooms;
    }
}
