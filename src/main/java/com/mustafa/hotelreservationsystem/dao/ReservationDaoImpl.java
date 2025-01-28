package com.mustafa.hotelreservationsystem.dao;

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
    public void update(Entity e) {

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
        }
        catch (SQLException ex){
            System.out.println(ex);
        }
    }

    // Gorevi = id si verilen Rezervasyonu getirir
    // Exception = bu id de bir reservasyon bulunamamasi durumu
    @Override
    public Reservation retrieve(long id) {
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
                    receptionist = receptionistDao.retrieve(receptionistId);
                }

                result = new Reservation(targetid, checkInDate, checkOutDate, bookingDate, receptionist);
            }
        }
        catch (SQLException ex){
            System.out.println(ex);
        }

        return result;
    }

    // Gorevi = id si verilen rezervasyonu db'den silecek
    // Exception = verilen id'de rezervasyon yoksa
    @Override
    public Reservation delete(long id) {

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
                    receptionist = rDao.retrieve(receptionistId);
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
    public void updateSpecifiedReservationField(long id, String fieldName, Object fieldValue) {

        List<String> allowedFieldNames = Arrays.asList("checkInDate", "checkOutDate", "bookingDate", "receptionistId");

        if (!allowedFieldNames.contains(fieldName)){
            System.out.println("Field: " + fieldName + " is not match any table name of feature");
        }

        String query = "UPDATE reservation SET " + fieldName + " = ? WHERE id = ?";
        try(
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query)
        )
        {
            ps.setObject(1, fieldValue);
            ps.setLong(2, id);

            int rowsaffected = ps.executeUpdate();
            System.out.println(rowsaffected + " rows affected");
        }
        catch (SQLException ex){
            System.out.println(ex);
        }
    }

    // Gorevi = rezervasyon ve customer'i reservation_customer ara tablosuna koyar
    // Exception;
    // 1- onceden reservation_customer tablosunda resId ve cusId si ayni olan bir satir varsa
    // 2- Reservation tablosunda resId li bir rezervasyon varligi yoksa
    // 3- Customer tablosunda cusId li bir musteri varligi yoksa
    @Override
    public void bindReservationAndCustomer(long resId, long cusId) {

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
        }
    }

    // Gorevi = reservation_customer ara tablosunda resId ve cusId olan satiri siler
    // Exception = ara tabloda resId ve cusId nin oldugu bir satir yoksa
    @Override
    public void unbindReservationAndCustomer(long resId, long cusId) {

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
        }
        catch (SQLException ex){
            System.out.println(ex);
        }
    }


    // Gorevi;
    // 1- bir rezervasyona kayitli olmayan resespsiyonisti kayit etmek icin (bu durum sadece bir resepsiyonist silidniginde gerceklesebilir)
    // 2- bir rezervasyona kayitli olan respsiyonisti baska bir baska bir respesiyonist ile degistirmek icin
    // Exception;
    // 1- resId id'li bir rezervasyon bulunamazsa
    // 2- verilen recId receptionist tablosunda olan bir resepsiyonistin id'si ile eslesmiyorsa
    @Override
    public void linkReservationToReceptionist(long resId, long recId) {

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
        }
        catch (SQLException ex){
            System.out.println(ex);
        }
    }

    // Gorevi = Rezervasyondan, o rezervasyonu yapan resepsiyonistin id'sini cikartir
    // Exception = resId id'li bir rezervasyon bulunamazsa
    @Override
    public void unlinkReservationFromReceptionist(long resId) {

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
        }
        catch (SQLException ex){
            System.out.println(ex);
        }

    }
}
