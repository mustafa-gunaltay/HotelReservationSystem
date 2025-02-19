package com.mustafa.hotelreservationsystem.ui.utils;

import com.mustafa.hotelreservationsystem.models.ReceptionistHomePageTableViewModel;
import com.mustafa.hotelreservationsystem.models.ReservationWithCustomerAndRoom;
import javafx.scene.Node;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Utils {


    public static <T> void setupTableViewSelectionMultiple(TableView<T> tableView) {
        // Çoklu seçim modunu aktifleştir
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // CTRL tuşuna basmadan çoklu seçim yapılmasını sağla
        tableView.addEventFilter(MouseEvent.MOUSE_PRESSED, evt -> {
            Node node = evt.getPickResult().getIntersectedNode();

            // TableRow bulunana kadar yukarı çık
            while (node != null && node != tableView && !(node instanceof TableRow)) {
                node = node.getParent();
            }

            if (node instanceof TableRow<?> row) {
                evt.consume(); // Varsayılan işleme izin verme
                tableView.requestFocus();

                if (!row.isEmpty()) {
                    int index = row.getIndex();
                    if (row.isSelected()) {
                        tableView.getSelectionModel().clearSelection(index);
                    } else {
                        tableView.getSelectionModel().select(index);
                    }
                }
            }
        });
    }


    public static List<ReceptionistHomePageTableViewModel> transformInnerJoinResultToTableViewModelFormat
            (List<ReservationWithCustomerAndRoom> allReservationsWithCustomerAndRoom) {

        /*
        * res cus room
        * 13  4   6
        * 13  5   9
        * 14  5   10
        * 14  5   11
        * */


        List<Long> differentReservationIds = new ArrayList<>();
        for (ReservationWithCustomerAndRoom eachInnerJoinRow : allReservationsWithCustomerAndRoom) {

            long reservationId = eachInnerJoinRow.getReservationId();

            if ( ! differentReservationIds.contains(reservationId)) {
                differentReservationIds.add(reservationId);
            }
        }

        // ayni reservationId ye sahip satirlari grup olarak iceren liste
        List<List<ReservationWithCustomerAndRoom>> differentRowsByReservationId = new ArrayList<>();
        for (int i = 0; i < differentReservationIds.size(); i++) {
            List<ReservationWithCustomerAndRoom> reservationsForThisId = new ArrayList<>(); // Yeni bir liste oluştur

            for (ReservationWithCustomerAndRoom eachInnerJoinRow : allReservationsWithCustomerAndRoom) {
                if (eachInnerJoinRow.getReservationId() == differentReservationIds.get(i)) {
                    reservationsForThisId.add(eachInnerJoinRow); // Listeye eleman ekle
                }
            }
            differentRowsByReservationId.add(reservationsForThisId); // Dış listeye ekle
        }


        List<Long> differentCustomerIds = new ArrayList<>();
        List<Long> differentRoomIds = new ArrayList<>();
        List<ReceptionistHomePageTableViewModel> result = new ArrayList<>();

        for ( int i=0 ; i < differentRowsByReservationId.size(); i++ ) {

            List<ReservationWithCustomerAndRoom> eachListThatHasSameReservationId = differentRowsByReservationId.get(i);

            long reservationId = eachListThatHasSameReservationId.getFirst().getReservationId();
            LocalDate checkInDate = eachListThatHasSameReservationId.getFirst().getCheckInDate();
            LocalDate checkOutDate = eachListThatHasSameReservationId.getFirst().getCheckOutDate();

            ReceptionistHomePageTableViewModel oneResult = new ReceptionistHomePageTableViewModel();
            oneResult.setReservationId(reservationId);
            oneResult.setCheckInDate(checkInDate);
            oneResult.setCheckOutDate(checkOutDate);

            for (int j=0 ; j < eachListThatHasSameReservationId.size() ; j++ ) {

                ReservationWithCustomerAndRoom oneRow = eachListThatHasSameReservationId.get(j);

                if ( ! differentCustomerIds.contains(oneRow.getCustomerId()) ) {
                    String currentNames = oneResult.getCustomersNamesDefault();
                    oneResult.setCustomersNames(currentNames + ", " + oneRow.getFullName());

                    differentCustomerIds.add(oneRow.getCustomerId());
                }

                if ( ! differentRoomIds.contains(oneRow.getRoomId()) ) {
                    String currentRoomNames = oneResult.getRoomsNamesDefault();
                    oneResult.setRoomsNames(currentRoomNames + ", " + oneRow.getRoomName());

                    differentRoomIds.add(oneRow.getRoomId());
                }

            }
            differentCustomerIds.clear();
            differentRoomIds.clear();

            result.add(oneResult);
        }

        return result;
    }

}
