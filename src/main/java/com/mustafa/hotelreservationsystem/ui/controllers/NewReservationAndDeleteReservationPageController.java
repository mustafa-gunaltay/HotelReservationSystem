package com.mustafa.hotelreservationsystem.ui.controllers;

import com.mustafa.hotelreservationsystem.exceptions.general.EntityNotFoundByIdException;
import com.mustafa.hotelreservationsystem.exceptions.general.ReferencedEntityNotFoundException;
import com.mustafa.hotelreservationsystem.exceptions.general.SameEntityValueExistInDbException;
import com.mustafa.hotelreservationsystem.models.*;
import com.mustafa.hotelreservationsystem.services.*;
import com.mustafa.hotelreservationsystem.ui.utils.AlertManager;
import com.mustafa.hotelreservationsystem.ui.utils.SceneInitializer;
import com.mustafa.hotelreservationsystem.ui.utils.SceneManager;
import com.mustafa.hotelreservationsystem.ui.utils.Utils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class NewReservationAndDeleteReservationPageController implements Initializable, HomePageReturnable {

    @FXML
    private TableView<Customer> tvCustomers;
    @FXML
    private TableColumn<Customer, Long> tblClmCustomerId;
    @FXML
    private TableColumn<Customer, String> tblClmCustomerFullName;
    @FXML
    private TableColumn<Customer, String> tblClmCustomerPhoneNumber;
    @FXML
    private TableColumn<Customer, LocalDate> tblClmCustomerBirthDate;
    @FXML
    private TableColumn<Customer, String> tblClmCustomerDescription;

    @FXML
    private TableView<RoomWithFeatureAndServiceTableViewModel> tvRoomsWithTheirFeaturesAndServices;
    @FXML
    private TableColumn<RoomWithFeatureAndServiceTableViewModel, Long> tblClmRoomId;
    @FXML
    private TableColumn<RoomWithFeatureAndServiceTableViewModel, String> tblClmRoomName;
    @FXML
    private TableColumn<RoomWithFeatureAndServiceTableViewModel, Integer> tblClmRoomCapacity;
    @FXML
    private TableColumn<RoomWithFeatureAndServiceTableViewModel, Integer> tblClmRoomUnitPrice;
    @FXML
    private TableColumn<RoomWithFeatureAndServiceTableViewModel, String> tblClmRoomFeatures;
    @FXML
    private TableColumn<RoomWithFeatureAndServiceTableViewModel, String> tblClmRoomServices;


    @FXML
    private TableView<ReservationWithCustomerAndRoomTableViewModel> tvReservationWithTheirRoomsAndCustomers;
    @FXML
    private TableColumn<ReservationWithCustomerAndRoomTableViewModel, Long> tblClmReservationId;
    @FXML
    private TableColumn<ReservationWithCustomerAndRoomTableViewModel, String> tblClmReservationRoomNames;
    @FXML
    private TableColumn<ReservationWithCustomerAndRoomTableViewModel, LocalDate> tblClmReservationCheckInDate;
    @FXML
    private TableColumn<ReservationWithCustomerAndRoomTableViewModel, String> tblClmReservationCheckOutDate;
    @FXML
    private TableColumn<ReservationWithCustomerAndRoomTableViewModel, String> tblClmReservationCustomers;


    @FXML
    private DatePicker dpCheckInDate;
    @FXML
    private DatePicker dpCheckOutDate;
    @FXML
    private Label lblCreateFeedBack;
    @FXML
    private Label lblDeleteFeedBack;


    private ObservableList<Customer> customerData = FXCollections.observableArrayList();
    private ObservableList<RoomWithFeatureAndServiceTableViewModel> roomsData = FXCollections.observableArrayList();
    private ObservableList<ReservationWithCustomerAndRoomTableViewModel> reservationsData = FXCollections.observableArrayList();

    @Override
    @FXML
    public void goBackHomePage() {
        SceneManager.switchScene("/com/mustafa/hotelreservationsystem/ui/controllers/ReceptionistHomePage.fxml",
                new SceneInitializer<ReceptionistHomePageController>() {
                    @Override
                    public void initialize(ReceptionistHomePageController controller) {
                        ReservationService reservationService = new ReservationServiceImpl();
                        List<ReservationWithCustomerAndRoom> all = reservationService.getAllReservationsWithTheirCustomersAndRooms();

                        controller.setTableView(ReservationWithCustomerAndRoomTableViewModel.transformInnerJoinResultToTableViewModelFormat(all));
                    }

                }
        );
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tblClmCustomerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblClmCustomerFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        tblClmCustomerPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        tblClmCustomerBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        tblClmCustomerDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        tblClmRoomId.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        tblClmRoomName.setCellValueFactory(new PropertyValueFactory<>("roomName"));
        tblClmRoomCapacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        tblClmRoomUnitPrice.setCellValueFactory(new PropertyValueFactory<>("roomUnitPrice"));
        tblClmRoomFeatures.setCellValueFactory(new PropertyValueFactory<>("featureNames"));
        tblClmRoomServices.setCellValueFactory(new PropertyValueFactory<>("serviceNames"));

        tblClmReservationId.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
        tblClmReservationRoomNames.setCellValueFactory(new PropertyValueFactory<>("roomsNames"));
        tblClmReservationCheckInDate.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        tblClmReservationCheckOutDate.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
        tblClmReservationCustomers.setCellValueFactory(new PropertyValueFactory<>("customersNames"));

        Utils.setupTableViewSelectionMultiple(tvCustomers);
        Utils.setupTableViewSelectionMultiple(tvRoomsWithTheirFeaturesAndServices);
        Utils.setupTableViewSelectionMultiple(tvReservationWithTheirRoomsAndCustomers);

        lblCreateFeedBack.setText("");
        lblDeleteFeedBack.setText("");
    }


    public void setTableView(List<Customer> cusotmers,
                             List<RoomWithFeatureAndServiceTableViewModel> roomsWithFeatureAndServices,
                             List<ReservationWithCustomerAndRoomTableViewModel> reservationsWithCustomersAndRooms) {


        customerData.setAll(cusotmers);
        tvCustomers.setItems(customerData);

        roomsData.setAll(roomsWithFeatureAndServices);
        tvRoomsWithTheirFeaturesAndServices.setItems(roomsData);

        reservationsData.setAll(reservationsWithCustomersAndRooms);
        tvReservationWithTheirRoomsAndCustomers.setItems(reservationsData);
    }

    @FXML
    public void onChangeRoomCustomizationCliecked(){
        SceneManager.switchScene("/com/mustafa/hotelreservationsystem/ui/controllers/EditingRoomCustomizationPage.fxml",
                new SceneInitializer<EditingRoomCustomizationPageController>() {
                    @Override
                    public void initialize(EditingRoomCustomizationPageController controller) {
                        FeatureService featureService = new FeatureServiceImpl();
                        ServiceService serviceService = new ServiceServiceImpl();
                        RoomService roomService = new RoomServiceImpl();

                        List<Feature> allFeatures = featureService.getAllFeatures();
                        List<Service> allServices = serviceService.getAllServices();
                        List<RoomWithFeatureAndService> allRooms = roomService.getAllRoomsWithTheirFeaturesAndServices(false);

                        controller.setTableView(allFeatures, allServices, RoomWithFeatureAndServiceTableViewModel.transformInnerJoinResultToTableViewModelFormat(allRooms));

                    }
                }
        );
    }


    @FXML
    public void onCreateReservationClicked(){

        if (dpCheckInDate.getValue() == null || dpCheckOutDate.getValue() == null){
            lblCreateFeedBack.setText("Reservation can not be created");
            AlertManager.showWarning("Warning", "Check-In date and Check-out date can not be empty");
            return;
        }

        if (tvCustomers.getSelectionModel().isEmpty() || tvRoomsWithTheirFeaturesAndServices.getSelectionModel().isEmpty()){
            lblCreateFeedBack.setText("Reservation can not be created");
            AlertManager.showWarning("Warning", "At least one customer and one room must be selected");
            return;
        }


        int totalCustomerCount = tvCustomers.getSelectionModel().getSelectedItems().size();
        int totalRoomCapacity = 0;
        List <RoomWithFeatureAndServiceTableViewModel> allSelectedRooms = tvRoomsWithTheirFeaturesAndServices.getSelectionModel().getSelectedItems();
        for (RoomWithFeatureAndServiceTableViewModel room : allSelectedRooms) {
            totalRoomCapacity += room.getCapacity();
        }
        if (totalCustomerCount > totalRoomCapacity) {
            lblCreateFeedBack.setText("Reservation can not be created");
            AlertManager.showWarning("Warning", "Total room capacity: " + totalRoomCapacity + " is not enough for " + totalCustomerCount + " customers.");
            return;
        }


        ReservationService reservationService = new ReservationServiceImpl();

        Reservation newReservation = new Reservation(
                dpCheckInDate.getValue(),
                dpCheckOutDate.getValue(),
                LocalDate.now(),
                Receptionist.currentReceptionist);

        reservationService.createReservation(newReservation);
        try{
            newReservation = reservationService.getLastReservation();
        } catch (EntityNotFoundByIdException e) {
            System.out.println("There is no reservation with this id"); // impossible
            return;
        }


        // son olusan rezervasyonu alip id sine gore ona room ve customer ekleme islemleri yapiliyor
        List<Customer> selectedCustomers = tvCustomers.getSelectionModel().getSelectedItems();
        List<RoomWithFeatureAndServiceTableViewModel> selectedRooms = tvRoomsWithTheirFeaturesAndServices.getSelectionModel().getSelectedItems();

        for (Customer customer : selectedCustomers) {
            try {
                reservationService.addCustomerToReservation(newReservation.getId(), customer.getId());
            } catch (ReferencedEntityNotFoundException e) {
                lblCreateFeedBack.setText("Reservation can not be created");
                AlertManager.showError("Error", "Reservation or Customer is not found");
                return;
            } catch (SameEntityValueExistInDbException e) {
                lblCreateFeedBack.setText("Reservation can not be created");
                AlertManager.showWarning("Warning", "One of the selected customers is already in reservation");
                return;
            }
        }

        RoomService roomService = new RoomServiceImpl();
        for (RoomWithFeatureAndServiceTableViewModel room : selectedRooms) {
            try {
                roomService.addRoomToReservation(room.getRoomId(), newReservation.getId());
            } catch (ReferencedEntityNotFoundException e) {
                lblCreateFeedBack.setText("Reservation can not be created");
                AlertManager.showError("Error", "Reservation is not found");
                return;
            } catch (EntityNotFoundByIdException e) {
                lblCreateFeedBack.setText("Reservation can not be created");
                AlertManager.showError("Error", "Room is not found");
                return;
            }
        }

        refreshReservationWithTheirRoomsAndCustomersTable();
        refreshRoomWithFeaturesAndServicesTable();
        lblCreateFeedBack.setText("Reservation has been created");
        lblDeleteFeedBack.setText("");


    }

    private void refreshReservationWithTheirRoomsAndCustomersTable() {
        // Veritabanından güncel verileri al
        ReservationService reservationService = new ReservationServiceImpl();
        List<ReservationWithCustomerAndRoomTableViewModel> updatedReservations = ReservationWithCustomerAndRoomTableViewModel.transformInnerJoinResultToTableViewModelFormat(reservationService.getAllReservationsWithTheirCustomersAndRooms());

        // UI üzerinde tabloyu güncelle
        Platform.runLater(() -> {
            // Eğer veriler boşsa, tabloyu temizle
            if (updatedReservations == null || updatedReservations.isEmpty()) {
                tvReservationWithTheirRoomsAndCustomers.getItems().clear();
            } else {
                // Veriler varsa, tabloyu güncelle
                reservationsData.setAll(updatedReservations);
                tvReservationWithTheirRoomsAndCustomers.setItems(reservationsData);
            }

            // System.out.println("Reservation table refreshed successfully.");
        });
    }


    @FXML
    public void onDeleteReservationClicked() {

        ReservationService reservationService = new ReservationServiceImpl();

        if (tvReservationWithTheirRoomsAndCustomers.getSelectionModel().isEmpty()) {
            lblDeleteFeedBack.setText("Reservation can not be deleted");
            AlertManager.showWarning("Warning", "No reservation selected for deletion");
            return;
        }

        List<ReservationWithCustomerAndRoomTableViewModel> selectedReservations = tvReservationWithTheirRoomsAndCustomers.getSelectionModel().getSelectedItems();
        for (ReservationWithCustomerAndRoomTableViewModel reservation : selectedReservations) {
            try {
                reservationService.deleteReservation(reservation.getReservationId());
            } catch (EntityNotFoundByIdException e) {
                lblDeleteFeedBack.setText("Reservation can not be deleted");
                AlertManager.showError("Error", "Reservation is not found");
                return;
            }
        }

        refreshReservationWithTheirRoomsAndCustomersTable();
        refreshRoomWithFeaturesAndServicesTable();
        lblCreateFeedBack.setText("");
        lblDeleteFeedBack.setText("Reservation has been deleted");

    }


    private void refreshRoomWithFeaturesAndServicesTable() {
        // Veritabanından güncel verileri al
        RoomService roomService = new RoomServiceImpl();
        List<RoomWithFeatureAndServiceTableViewModel> updatedRooms = RoomWithFeatureAndServiceTableViewModel.transformInnerJoinResultToTableViewModelFormat(roomService.getAllRoomsWithTheirFeaturesAndServices(false));

        // UI üzerinde tabloyu güncelle
        Platform.runLater(() -> {
            // Eğer veriler boşsa, tabloyu temizle
            if (updatedRooms == null || updatedRooms.isEmpty()) {
                tvRoomsWithTheirFeaturesAndServices.getItems().clear();
            } else {
                // Veriler varsa, tabloyu güncelle
                roomsData.setAll(updatedRooms);
                tvRoomsWithTheirFeaturesAndServices.setItems(roomsData);
            }

            // System.out.println("Room table refreshed successfully.");
        });
    }


    @FXML
    public void onCheckInDateClearClicked() {
        dpCheckInDate.setValue(null);
    }

    @FXML
    public void onCheckOutDateClearClicked() {
        dpCheckOutDate.setValue(null);
    }





}
