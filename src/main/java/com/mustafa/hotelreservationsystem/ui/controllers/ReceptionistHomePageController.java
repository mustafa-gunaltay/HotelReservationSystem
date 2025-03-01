package com.mustafa.hotelreservationsystem.ui.controllers;

import com.mustafa.hotelreservationsystem.models.*;
import com.mustafa.hotelreservationsystem.services.*;
import com.mustafa.hotelreservationsystem.ui.utils.AlertManager;
import com.mustafa.hotelreservationsystem.ui.utils.SceneInitializer;
import com.mustafa.hotelreservationsystem.ui.utils.SceneManager;
import com.mustafa.hotelreservationsystem.ui.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ReceptionistHomePageController implements Initializable {

    @FXML
    private TableView<ReservationWithCustomerAndRoomTableViewModel> tvReceptionistHomePage;
    @FXML
    private TableColumn<ReservationWithCustomerAndRoomTableViewModel, Long> tblClmReservationId;
    @FXML
    private TableColumn<ReservationWithCustomerAndRoomTableViewModel, String> tblClmRoomsNames;
    @FXML
    private TableColumn<ReservationWithCustomerAndRoomTableViewModel, LocalDate> tblClmCheckInDate;
    @FXML
    private TableColumn<ReservationWithCustomerAndRoomTableViewModel, LocalDate> tblClmCheckOutDate;
    @FXML
    private TableColumn<ReservationWithCustomerAndRoomTableViewModel, String> tblClmCustomersNames;

    @FXML
    private DatePicker dpCheckInDate;
    @FXML
    private DatePicker dpCheckOutDate;
    @FXML
    private TextField tfTargetRoomName;

    private ObservableList<ReservationWithCustomerAndRoomTableViewModel> data = FXCollections.observableArrayList();
    private List<ReservationWithCustomerAndRoomTableViewModel> allReservations = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tblClmReservationId.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
        tblClmRoomsNames.setCellValueFactory(new PropertyValueFactory<>("roomsNames"));
        tblClmCheckInDate.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        tblClmCheckOutDate.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
        tblClmCustomersNames.setCellValueFactory(new PropertyValueFactory<>("customersNames"));

        Utils.setupTableViewSelectionMultiple(tvReceptionistHomePage);
    }

    public void setTableView(List<ReservationWithCustomerAndRoomTableViewModel> tableViewModel) {
        data.setAll(tableViewModel);
        tvReceptionistHomePage.setItems(data);

        allReservations = tableViewModel;
    }

    @FXML
    public void onEditRoomCustomizationClicked(){

        SceneManager.switchScene("/com/mustafa/hotelreservationsystem/ui/controllers/EditingRoomCustomizationPage.fxml",
                new SceneInitializer<EditingRoomCustomizationPageController>() {
                    @Override
                    public void initialize(EditingRoomCustomizationPageController controller) {
                        FeatureService featureService = new FeatureServiceImpl();
                        ServiceService serviceService = new ServiceServiceImpl();
                        RoomService roomService = new RoomServiceImpl();

                        List<Feature> allFeatures = featureService.getAllFeatures();
                        List<Service> allServices = serviceService.getAllServices();
                        List<RoomWithFeatureAndService> allRooms = roomService.getAllRoomsWithTheirFeaturesAndServices();

                        controller.setTableView(allFeatures, allServices, RoomWithFeatureAndServiceTableViewModel.transformInnerJoinResultToTableViewModelFormat(allRooms));

                    }
                }
        );

    }


    @FXML
    public void onCreateOrDeleteReservationClicked(){

        SceneManager.switchScene("/com/mustafa/hotelreservationsystem/ui/controllers/NewReservationAndDeleteReservationPage.fxml",
                new SceneInitializer<NewReservationAndDeleteReservationPageController>() {
                    @Override
                    public void initialize(NewReservationAndDeleteReservationPageController controller) {

                        CustomerService customerService = new CustomerServiceImpl();
                        RoomService roomService = new RoomServiceImpl();
                        ReservationService reservationService = new ReservationServiceImpl();

                        List<Customer> allCustomers = customerService.getAllCustomers();
                        List<RoomWithFeatureAndService> allRoomsWithTheirFeaturesAndServices = roomService.getAllRoomsWithTheirFeaturesAndServices();
                        List<ReservationWithCustomerAndRoom> allReservationsWithTheirRoomsAndCustomers = reservationService.getAllReservationsWithTheirCustomersAndRooms();

                        List<RoomWithFeatureAndServiceTableViewModel> allRoomsWithTheirFeaturesAndServicesOnTableViewModelFormat = RoomWithFeatureAndServiceTableViewModel.transformInnerJoinResultToTableViewModelFormat(allRoomsWithTheirFeaturesAndServices);
                        List<ReservationWithCustomerAndRoomTableViewModel> allReservationsWithTheirRoomsAndCustomersOnTableViewModelFormat = ReservationWithCustomerAndRoomTableViewModel.transformInnerJoinResultToTableViewModelFormat(allReservationsWithTheirRoomsAndCustomers);

                        controller.setTableView(allCustomers, allRoomsWithTheirFeaturesAndServicesOnTableViewModelFormat, allReservationsWithTheirRoomsAndCustomersOnTableViewModelFormat);
                    }
                }
        );
    }


    @FXML
    public void onSearchClicked() {
        String targetRoomName = tfTargetRoomName.getText();
        LocalDate targetCheckInDate = dpCheckInDate.getValue();
        LocalDate targetCheckOutDate = dpCheckOutDate.getValue();

        List<ReservationWithCustomerAndRoomTableViewModel> result = FXCollections.observableArrayList();

        for (ReservationWithCustomerAndRoomTableViewModel reservation : allReservations) {
            LocalDate checkInDate = reservation.getCheckInDate();
            LocalDate checkOutDate = reservation.getCheckOutDate();
            String roomNames = reservation.getRoomsNames();

            boolean matchesCheckIn = (targetCheckInDate == null) ||
                    (checkInDate.isAfter(targetCheckInDate) || checkInDate.isEqual(targetCheckInDate));

            boolean matchesCheckOut = (targetCheckOutDate == null) ||
                    (checkOutDate.isBefore(targetCheckOutDate) || checkOutDate.isEqual(targetCheckOutDate));

            boolean matchesRoomName = (targetRoomName.isEmpty()) || roomNames.contains(targetRoomName);

            if (matchesCheckIn && matchesCheckOut && matchesRoomName) {
                result.add(reservation);
            }
        }

        data.setAll(result);
    }


    @FXML
    public void onClearAllFiltersClicked(){
        onCheckInDateClearClicked();
        onCheckOutDateClearClicked();
        onRoomNameClearClicked();
    }

    @FXML
    public void onCheckInDateClearClicked(){
        dpCheckInDate.setValue(null);
    }

    @FXML
    public void onCheckOutDateClearClicked(){
        dpCheckOutDate.setValue(null);
    }

    @FXML
    public void onRoomNameClearClicked(){
        tfTargetRoomName.clear();
    }
}
