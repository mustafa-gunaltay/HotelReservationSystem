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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.application.Platform;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EditingRoomCustomizationPageController implements Initializable, HomePageReturnable {

    @FXML
    private TableView<Feature> tvFeatures;
    @FXML
    private TableView<Service> tvServices;
    @FXML
    private TableView<RoomWithFeatureAndServiceTableViewModel> tvRoomsWithTheirFeaturesAndServices;
    @FXML
    private TableColumn<Feature, Long> tblClmFeatureId;
    @FXML
    private TableColumn<Feature, String> tblClmFeatureName;
    @FXML
    private TableColumn<Feature, Integer> tblClmFeaturePrice;
    @FXML
    private TableColumn<Service, Long> tblClmServiceId;
    @FXML
    private TableColumn<Service, String> tblClmServiceName;
    @FXML
    private TableColumn<Service, Integer> tblClmServicePrice;
    @FXML
    private TableColumn<RoomWithFeatureAndServiceTableViewModel, Long> tblClmRoomId;
    @FXML
    private TableColumn<RoomWithFeatureAndServiceTableViewModel, String> tblClmRoomName;
    @FXML
    private TableColumn<RoomWithFeatureAndServiceTableViewModel, Integer> tblClmRoomCapacity;
    @FXML
    private TableColumn<RoomWithFeatureAndServiceTableViewModel, String> tblClmRoomUnitPrice;
    @FXML
    private TableColumn<RoomWithFeatureAndServiceTableViewModel, String> tblClmFeatureNames;
    @FXML
    private TableColumn<RoomWithFeatureAndServiceTableViewModel, String> tblClmServiceNames;


    @FXML
    private Label lblAddFeedBack;
    @FXML
    private Label lblDeleteFeedBack;
    @FXML
    private Button btnAddition;
    @FXML
    private Button btnDeletion;
    @FXML
    private Label lblFeatureTableInformation;
    @FXML
    private Label lblServiceTableInformation;
    @FXML
    private Label lblRoomTableInformation;
    @FXML
    private RadioButton rbAdd;

    private ObservableList<Feature> featureData = FXCollections.observableArrayList();
    private ObservableList<Service> serviceData = FXCollections.observableArrayList();
    private ObservableList<RoomWithFeatureAndServiceTableViewModel> roomDataWithFeaturesAndServices = FXCollections.observableArrayList();

    @FXML
    @Override
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

    @FXML
    public void onRadioButtonAddClicked(){
        btnAddition.setVisible(true);
        btnDeletion.setVisible(false);

        lblAddFeedBack.setVisible(true);
        lblDeleteFeedBack.setVisible(false);

        lblFeatureTableInformation.setText("Select Feature(s) To Be Added");
        lblServiceTableInformation.setText("Select Service(s) To Be Added");
        lblRoomTableInformation.setText("Select Available Room(s) to Add Selected Features And Services");
        lblAddFeedBack.setText("");
        lblDeleteFeedBack.setText("");
    }

    @FXML
    public void onRadioButtonDeleteClicked(){
        btnAddition.setVisible(false);
        btnDeletion.setVisible(true);

        lblAddFeedBack.setVisible(false);
        lblDeleteFeedBack.setVisible(true);

        lblFeatureTableInformation.setText("Select Feature(s) To Be Deleted");
        lblServiceTableInformation.setText("Select Service(s) To Be Deleted");
        lblRoomTableInformation.setText("Select Available Room(s) to Delete Selected Features And Services");
        lblAddFeedBack.setText("");
        lblDeleteFeedBack.setText("");
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Utils.setupTableViewSelectionMultiple(tvFeatures);
        Utils.setupTableViewSelectionMultiple(tvServices);
        Utils.setupTableViewSelectionMultiple(tvRoomsWithTheirFeaturesAndServices);

        tblClmFeatureId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblClmFeatureName.setCellValueFactory(new PropertyValueFactory<>("featureName"));
        tblClmFeaturePrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        tblClmServiceId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblClmServiceName.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        tblClmServicePrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        tblClmRoomId.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        tblClmRoomName.setCellValueFactory(new PropertyValueFactory<>("roomName"));
        tblClmRoomCapacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        tblClmRoomUnitPrice.setCellValueFactory(new PropertyValueFactory<>("roomUnitPrice"));
        tblClmFeatureNames.setCellValueFactory(new PropertyValueFactory<>("featureNames"));
        tblClmServiceNames.setCellValueFactory(new PropertyValueFactory<>("serviceNames"));


        lblAddFeedBack.setText("");
        lblDeleteFeedBack.setText("");

        rbAdd.setSelected(true);
        onRadioButtonAddClicked();
    }


    public void setTableView(List<Feature> features, List<Service> services, List<RoomWithFeatureAndServiceTableViewModel> roomsWithFeaturesAndServices) {

        featureData.setAll(features);
        tvFeatures.setItems(featureData);

        serviceData.setAll(services);
        tvServices.setItems(serviceData);

        roomDataWithFeaturesAndServices.setAll(roomsWithFeaturesAndServices);
        tvRoomsWithTheirFeaturesAndServices.setItems(roomDataWithFeaturesAndServices);

    }


    @FXML
    public void onApplyAdditionClicked(){

        ObservableList<RoomWithFeatureAndServiceTableViewModel> selectedRooms;
        if ( ! tvRoomsWithTheirFeaturesAndServices.getSelectionModel().isEmpty()) {
            selectedRooms = tvRoomsWithTheirFeaturesAndServices.getSelectionModel().getSelectedItems();
        }
        else{
            lblAddFeedBack.setText("Room Customization Failed");
            AlertManager.showWarning("Warning", "Please select a room to add selected features and services");
            return;
        }

        if (tvFeatures.getSelectionModel().isEmpty() && tvServices.getSelectionModel().isEmpty()) {
            lblAddFeedBack.setText("Room Customization Failed");
            AlertManager.showWarning("Warning", "At least one feature or service must be selected to add room");
            return;
        }



        RoomService roomService = new RoomServiceImpl();

        if ( ! tvFeatures.getSelectionModel().isEmpty()){

            ObservableList<Feature> selectedFeatures = tvFeatures.getSelectionModel().getSelectedItems();
            for (RoomWithFeatureAndServiceTableViewModel room : selectedRooms){
                for (Feature feature : selectedFeatures){
                    try {
                        roomService.addFeatureToRoom(feature.getId(), room.getRoomId());
                        refreshRoomWithFeaturesAndServicesTable();

                    } catch (ReferencedEntityNotFoundException e) {
                        lblAddFeedBack.setText("Room Customization Failed");
                        AlertManager.showWarning("Essential Warning", "Room or Feature not found in the system");
                        return;
                    } catch (SameEntityValueExistInDbException e) {
                        lblAddFeedBack.setText("Room Customization Failed");
                        AlertManager.showWarning("Warning", "One of the selected rooms is already have one of the selected features");
                        return;
                    }
                }
            }

        }


        if ( ! tvServices.getSelectionModel().isEmpty()){

            ObservableList<Service> selectedServices = tvServices.getSelectionModel().getSelectedItems();
            for (Service service : selectedServices){
                for (RoomWithFeatureAndServiceTableViewModel room : selectedRooms){
                    try {
                        roomService.addServiceToRoom(service.getId(), room.getRoomId());
                        refreshRoomWithFeaturesAndServicesTable();

                    } catch (ReferencedEntityNotFoundException e) {
                        lblAddFeedBack.setText("Room Customization Failed");
                        AlertManager.showWarning("Essential Warning", "Room or Service not found in the system");
                        return;
                    } catch (SameEntityValueExistInDbException e) {
                        lblAddFeedBack.setText("Room Customization Failed");
                        AlertManager.showWarning("Warning", "One of the selected rooms is already have one of the selected services");
                        return;
                    }
                }
            }
        }


        lblAddFeedBack.setText("Room Customization Successful");
    }


    @FXML
    public void onApplyDeletionClicked(){

        ObservableList<RoomWithFeatureAndServiceTableViewModel> selectedRooms;
        if ( ! tvRoomsWithTheirFeaturesAndServices.getSelectionModel().isEmpty()) {
            selectedRooms = tvRoomsWithTheirFeaturesAndServices.getSelectionModel().getSelectedItems();
        }
        else{
            lblAddFeedBack.setText("Room Customization Failed");
            AlertManager.showWarning("Warning", "Please select a room to add selected features and services");
            return;
        }


        if (tvFeatures.getSelectionModel().isEmpty() && tvServices.getSelectionModel().isEmpty()) {
            lblDeleteFeedBack.setText("Room Customization Failed");
            AlertManager.showWarning("Warning", "At least one feature or service must be selected to delete from room");
            return;
        }


        RoomService roomService = new RoomServiceImpl();

        if ( ! tvFeatures.getSelectionModel().isEmpty()){
            ObservableList<Feature> selectedFeatures = tvFeatures.getSelectionModel().getSelectedItems();
            for (RoomWithFeatureAndServiceTableViewModel room : selectedRooms){
                for (Feature feature : selectedFeatures){
                    try {
                        roomService.deleteFeatureFromRoom(feature.getId(), room.getRoomId());
                        refreshRoomWithFeaturesAndServicesTable();

                    } catch (EntityNotFoundByIdException e) {
                        lblDeleteFeedBack.setText("Room Customization Failed");
                        AlertManager.showWarning("Warning", "Room is already not have selected Feature");
                        return;
                    }
                }
            }
        }


        if ( ! tvServices.getSelectionModel().isEmpty()){

            ObservableList<Service> selectedServices = tvServices.getSelectionModel().getSelectedItems();
            for (Service service : selectedServices){
                for (RoomWithFeatureAndServiceTableViewModel room : selectedRooms){
                    try {
                        roomService.deleteServiceFromRoom(service.getId(), room.getRoomId());
                        refreshRoomWithFeaturesAndServicesTable();

                    } catch (EntityNotFoundByIdException e) {
                        lblDeleteFeedBack.setText("Room Customization Failed");
                        AlertManager.showWarning("Warning", "Room is already not have selected Service");
                        return;
                    }
                }
            }
        }

        lblDeleteFeedBack.setText("Room Customization Successful");
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
                roomDataWithFeaturesAndServices.setAll(updatedRooms);
                tvRoomsWithTheirFeaturesAndServices.setItems(roomDataWithFeaturesAndServices);
            }

            // System.out.println("Room table refreshed successfully.");
        });
    }











}
