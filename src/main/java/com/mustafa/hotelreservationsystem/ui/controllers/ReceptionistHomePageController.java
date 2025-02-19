package com.mustafa.hotelreservationsystem.ui.controllers;

import com.mustafa.hotelreservationsystem.models.Receptionist;
import com.mustafa.hotelreservationsystem.models.ReceptionistHomePageTableViewModel;
import com.mustafa.hotelreservationsystem.models.ReservationWithCustomerAndRoom;
import com.mustafa.hotelreservationsystem.ui.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ReceptionistHomePageController implements Initializable {

    @FXML
    private TableView<ReceptionistHomePageTableViewModel> tvReceptionistHomePage;
    @FXML
    private TableColumn<ReceptionistHomePageTableViewModel, String> tblClmReservationId;
    @FXML
    private TableColumn<ReceptionistHomePageTableViewModel, String> tblClmRoomsNames;
    @FXML
    private TableColumn<ReceptionistHomePageTableViewModel, LocalDate> tblClmCheckInDate;
    @FXML
    private TableColumn<ReceptionistHomePageTableViewModel, LocalDate> tblClmCheckOutDate;
    @FXML
    private TableColumn<ReceptionistHomePageTableViewModel, String> tblClmCustomersNames;

    private ObservableList<ReceptionistHomePageTableViewModel> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tblClmReservationId.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
        tblClmRoomsNames.setCellValueFactory(new PropertyValueFactory<>("roomsNames"));
        tblClmCheckInDate.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        tblClmCheckOutDate.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
        tblClmCustomersNames.setCellValueFactory(new PropertyValueFactory<>("customersNames"));

        Utils.setupTableViewSelectionMultiple(tvReceptionistHomePage);
    }

    public void setTableView(List<ReceptionistHomePageTableViewModel> tableViewModel) {
        data.setAll(tableViewModel);
        tvReceptionistHomePage.setItems(data);
    }
}
