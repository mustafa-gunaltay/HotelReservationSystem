package com.mustafa.hotelreservationsystem.dao;

import com.mustafa.hotelreservationsystem.exceptions.db.DuplicateEntryException;
import com.mustafa.hotelreservationsystem.exceptions.db.NoReferencedRowException;
import com.mustafa.hotelreservationsystem.exceptions.db.ZeroRowsAffectedOrReturnedException;
import com.mustafa.hotelreservationsystem.models.Entity;
import com.mustafa.hotelreservationsystem.models.Reservation;
import com.mustafa.hotelreservationsystem.models.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoomDaoImpl implements RoomDao{

    private static final String URL = "jdbc:mysql://localhost:3306/hotelreservationsystemrd";
    private static final String USER = "root";
    private static final String PASSWORD = "karakama123--";



    // Gorevi = hem rezervasyonlu hem de rezervasyonsuz oda olusturmak icin
    // Not = gecilen room nesnesindeki reservation attribute'u icindeki id degeri dolu olmali
    @Override
    public void save(Entity e) {
        Room r = (Room) e;
        String roomName = r.getRoomName();
        int capacity = r.getCapacity();
        int price = r.getPrice();
        boolean isReserved = r.isReserved();

        long reservationId;
        if (isReserved){
            reservationId = r.getReservation().getId();
        }
        else {
            reservationId = 0;
        }


        String query = "INSERT INTO room (roomName, capacity, price, isReserved, reservationId) VALUES (?, ?, ?, ?, ?)";
        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query)
        )
        {
            ps.setString(1, roomName);
            ps.setInt(2, capacity);
            ps.setInt(3, price);
            ps.setBoolean(4, isReserved);
            if (isReserved){
                ps.setLong(5, reservationId);
            }
            else {
                ps.setNull(5, java.sql.Types.INTEGER);
            }

            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " rows affected");
        }
        catch (SQLException ex){
            System.out.println(ex);
        }
    }

    // Gorevi = Tum odayi ayni anda gunceller (hem rezervasyonlu oda, hem rezervasyonsuz oda seklinde guncelleme yapabilir)
    // Exception = Verilen id'de oda yoksa
    @Override
    public void update(Entity e) throws NoReferencedRowException, ZeroRowsAffectedOrReturnedException {
        Room r = (Room) e;
        long id = r.getId();
        String roomName = r.getRoomName();
        int capacity = r.getCapacity();
        int price = r.getPrice();
        boolean isReserved = r.isReserved();

        long reservationId = 0;
        if (isReserved){
            if (r.getReservation() != null) {
                reservationId = r.getReservation().getId();
            }
            else{
                System.out.println("Reservation is null");
                return;
            }
        }

        String query = "UPDATE room SET roomName = ?, capacity = ?, price = ?, isReserved = ?, reservationId = ? WHERE id = ?";
        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query);
        )
        {
            ps.setString(1, roomName);
            ps.setInt(2, capacity);
            ps.setInt(3, price);
            ps.setBoolean(4, isReserved);
            if (isReserved){
                ps.setLong(5, reservationId);
            }
            else {
                ps.setNull(5, java.sql.Types.INTEGER);
            }
            ps.setLong(6, id);


            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " rows affected");
            if (rowsAffected < 1){
                throw new ZeroRowsAffectedOrReturnedException("Zero rows affected on UPDATE", id);
            }
        }
        catch (SQLException ex){
            System.out.println(ex);

            int errorCode = ex.getErrorCode();
            if (errorCode == MySqlErrors.FOREIGN_KEY_CONSTRAINT_VIOLATION.getCode()){
                throw new NoReferencedRowException("No corresponding row in Reservation table - reservationId: " + reservationId);
            }
        }

    }

    // normalde 1, 2 ve 3 nolu satirlarin acik olmasi lazim fakat database'den getirilen reservationId yi retrieve
    // kullanarak getiremiyorum cunku reservation'in dao sinifi daha yazilmadi
    // reservationDao sinifi yazilinca bu metot tekrar test edilecek
    // reservationDao yazildiktan sonra 1,2,3 satirlari acilacak ve x,y satirlari silinecek

    // Gorevi = verilen id'deki odayi getirir
    // Exception = verilen id'de oda yoksa
    @Override
    public Room retrieve(long id) throws ZeroRowsAffectedOrReturnedException{

        Room result = null;

        String query = "SELECT roomName, capacity, price, isReserved, reservationId FROM room WHERE id = ?";
        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query);
            )
        {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String roomName = rs.getString("roomName");
                int capacity = rs.getInt("capacity");
                int price = rs.getInt("price");
                boolean isReserved = rs.getBoolean("isReserved");

                Reservation reservation;
                if (isReserved) {
                    long reservationId = rs.getLong("reservationId");
                    ReservationDao reservationDao = new ReservationDaoImpl();
                    try{
                        reservation = reservationDao.retrieve(reservationId);
                    } catch (ZeroRowsAffectedOrReturnedException e) {
                        System.out.println("Reservation with 'id' is not found");
                        reservation = null;
                    }
                } else {
                    reservation = null;
                }

                result = new Room(id, roomName, capacity, price, isReserved, reservation);
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

    // Gorevi = id si verilen oda db'den silecek
    // Exception = verilen id'de oda yoksa
    @Override
    public Room delete(long id) throws ZeroRowsAffectedOrReturnedException{

        Room deletedRoom = retrieve(id);

        String query = "DELETE FROM room WHERE id = ?";

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

        return deletedRoom;

    }


    // Gorevi = Tum oda varliklarini liste olrak getirir
    // Exception = liste bossa
    @Override
    public List<Room> retrieveAllRooms() {

        List<Room> allRooms = new ArrayList<>();

        String query = "SELECT id, roomName, capacity, price, isReserved, reservationId FROM room";

        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query)
        )
        {
            ResultSet rs = ps.executeQuery();

            while (rs.next()){

                long id = rs.getLong("id");
                String roomName = rs.getString("roomName");
                int capacity = rs.getInt("capacity");
                int price = rs.getInt("price");
                boolean isReserved = rs.getBoolean("isReserved");


                Reservation reservation;
                if (isReserved) {
                    long reservationId = rs.getLong("reservationId");
                    ReservationDao reservationDao = new ReservationDaoImpl();
                    try{
                        reservation = reservationDao.retrieve(reservationId);
                    } catch (ZeroRowsAffectedOrReturnedException e) {
                        System.out.println("Reservation with 'reservationId' is not found");
                        reservation = null;
                    }
                }
                else {
                    reservation = null;
                }
                
                allRooms.add(new Room(id, roomName, capacity, price, isReserved, reservation));

            }
        }
        catch (SQLException ex){
            System.out.println(ex);
        }

        return allRooms;
    }


    // Gorevi = id si verilen odada verilen alana verilen degeri atar,
    // NOT    = fieldName = reservationId olmasi durmunda, fieldValue long verilmeli

    // Exception = verilen id'de oda yoksa
    // Exception = verilen alan unique ise ve onceden olan bir deger tekrar atanmak istendiyse
    @Override
    public void updateSpecifiedRoomField(long id, String fieldName, Object fieldValue) throws NoReferencedRowException, ZeroRowsAffectedOrReturnedException {

        List<String> allowedFieldNames = Arrays.asList("roomName", "capacity", "price", "isReserved", "reservationId");

        if (!allowedFieldNames.contains(fieldName)){
            System.out.println("Field: " + fieldName + " is not match any table name of room");
            return;
        }

        String query = "UPDATE room SET " + fieldName + " = ? WHERE id = ?";

        try (
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
            if (errorCode == MySqlErrors.FOREIGN_KEY_CONSTRAINT_VIOLATION.getCode() && fieldName.equals("reservationId")){
                throw new NoReferencedRowException("No corresponding row in Reservation table - reservationId: " + fieldValue);
            }
        }
    }


    // Gorevi;
    // 1- bir rezervasyona kayitli olmayan odayi kayit etmek icin
    // 2- bir rezervasyona kayitli olan odai baska bir rezervasyona kayit etmek icin
    // Exception;
    // 1- roomId id'li bir oda bulunamazsa
    // 2- verilen resId reservation tablosunda olan bir rezervasyon id'si ile eslesmiyorsa
    @Override
    public void linkRoomToReservation(long roomId, long resId) throws NoReferencedRowException, ZeroRowsAffectedOrReturnedException {

        String query = "UPDATE room SET reservationId = ? WHERE id = ?";

        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query)
           )
        {
            ps.setLong(1, resId);
            ps.setLong(2, roomId);

            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " rows affected");
            if (rowsAffected < 1){
                throw new ZeroRowsAffectedOrReturnedException("Zero rows affected on UPDATE", roomId);
            }

            updateSpecifiedRoomField(roomId, "isReserved", true);

        }
        catch (SQLException ex){
            System.out.println(ex);

            int errorCode = ex.getErrorCode();
            if (errorCode == MySqlErrors.FOREIGN_KEY_CONSTRAINT_VIOLATION.getCode()){
                throw new NoReferencedRowException("No corresponding row in Reservation table - reservationId: " + resId);
            }
        }

    }


    // Gorevi = Odayi, icinde oldugu rezervasyondan cikartir (rezervationId yi null yapar)
    // Exception = roomId id'li bir oda bulunamazsa
    @Override
    public void unlinkRoomFromReservation(long roomId) throws ZeroRowsAffectedOrReturnedException {

        String query = "UPDATE room SET reservationId = ? WHERE id = ?";

        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query)
        )
        {
            ps.setNull(1, java.sql.Types.INTEGER);
            ps.setLong(2, roomId);

            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " rows affected");
            if (rowsAffected < 1){
                throw new ZeroRowsAffectedOrReturnedException("Zero rows affected on UPDATE", roomId);
            }


            try{
                updateSpecifiedRoomField(roomId, "isReserved", false);
            }
            catch (Exception e){
                System.out.println(e); // never thrown because it only throws exception when fieldName is "reservationId"
            }

        }
        catch (SQLException ex){
            System.out.println(ex);
        }

    }



    // Gorevi = room ve feature'i reservation_customer ara tablosuna koyar
    // Exception;
    // 1- onceden room_feature tablosunda roomId ve featureId si ayni olan bir satir varsa
    // 2- Room tablosunda roomId li bir oda varligi yoksa
    // 3- Feature tablosunda featureId li bir ozellik varligi yoksa
    @Override
    public void bindRoomAndFeature(long roomId, long featureId) throws NoReferencedRowException, DuplicateEntryException {

        String query = "INSERT INTO room_feature (roomId, featureId) VALUES (?, ?)";

        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query)
            )
        {
            ps.setLong(1, roomId);
            ps.setLong(2, featureId);

            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " rows affected");
        }
        catch (SQLException ex){
            System.out.println(ex);

            int errorCode = ex.getErrorCode();
            if (errorCode == MySqlErrors.FOREIGN_KEY_CONSTRAINT_VIOLATION.getCode()){
                throw new NoReferencedRowException("No corresponding row in Reservation table - roomId: " + roomId + " featureId: " + featureId);
            }
            if (errorCode == MySqlErrors.DUPLICATE_ENTRY.getCode()){
                throw new DuplicateEntryException("Duplicate entry for primary key set", "roomId: " + roomId + " - featureId: " + featureId);
            }
        }

    }


    // Gorevi = room_feature ara tablosunda roomId ve featureId olan satiri siler
    // Exception = ara tabloda roomId ve featureId nin oldugu bir satir yoksa
    @Override
    public void unbindRoomAndFeature(long roomId, long featureId) throws ZeroRowsAffectedOrReturnedException {

        String query = "DELETE FROM room_feature WHERE roomId = ? AND featureId = ?";

        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query)
        )
        {
            ps.setLong(1, roomId);
            ps.setLong(2, featureId);

            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " rows affected");
            if (rowsAffected < 1){
                throw new ZeroRowsAffectedOrReturnedException("Zero rows affected on DELETE", roomId, featureId);
            }
        }
        catch (SQLException ex){
            System.out.println(ex);
        }

    }



    // Gorevi = room ve service'i reservation_service ara tablosuna koyar
    // Exception;
    // 1- onceden room_service tablosunda roomId ve serviceId si ayni olan bir satir varsa
    // 2- Room tablosunda roomId li bir oda varligi yoksa
    // 3- Service tablosunda serviceId li bir servis varligi yoksa
    @Override
    public void bindRoomAndService(long roomId, long serviceId) throws NoReferencedRowException, DuplicateEntryException {

        String query = "INSERT INTO room_service (roomId, serviceId) VALUES (?, ?)";

        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query)
            )
        {
            ps.setLong(1, roomId);
            ps.setLong(2, serviceId);

            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " rows affected");
        }
        catch (SQLException ex){
            System.out.println(ex);

            int errorCode = ex.getErrorCode();
            if (errorCode == MySqlErrors.FOREIGN_KEY_CONSTRAINT_VIOLATION.getCode()){
                throw new NoReferencedRowException("No corresponding row in Reservation table - roomId: " + roomId + " serviceId: " + serviceId);
            }
            if (errorCode == MySqlErrors.DUPLICATE_ENTRY.getCode()){
                throw new DuplicateEntryException("Duplicate entry for primary key set", "roomId: " + roomId + " - serviceId: " + serviceId);
            }

        }

    }


    // Gorevi = room_service ara tablosunda roomId ve serviceId olan satiri siler
    // Exception = ara tabloda roomId ve serviceId nin oldugu bir satir yoksa
    @Override
    public void unbindRoomAndService(long roomId, long serviceId) throws ZeroRowsAffectedOrReturnedException {

        String query = "DELETE FROM room_service WHERE roomId = ? AND serviceId = ?";

        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query)
        )
        {
            ps.setLong(1, roomId);
            ps.setLong(2, serviceId);

            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " rows affected");
            if (rowsAffected < 1){
                throw new ZeroRowsAffectedOrReturnedException("Zero rows affected on DELETE", roomId, serviceId);
            }

        }
        catch (SQLException ex){
            System.out.println(ex);
        }

    }
}
