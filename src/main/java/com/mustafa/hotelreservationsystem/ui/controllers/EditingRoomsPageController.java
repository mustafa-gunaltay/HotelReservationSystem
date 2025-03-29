package com.mustafa.hotelreservationsystem.ui.controllers;

import com.mustafa.hotelreservationsystem.exceptions.general.EntityNotFoundByIdException;
import com.mustafa.hotelreservationsystem.models.Admin;
import com.mustafa.hotelreservationsystem.models.Room;
import com.mustafa.hotelreservationsystem.services.AdminService;
import com.mustafa.hotelreservationsystem.services.AdminServiceImpl;
import com.mustafa.hotelreservationsystem.services.RoomService;
import com.mustafa.hotelreservationsystem.services.RoomServiceImpl;
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

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EditingRoomsPageController implements Initializable, HomePageReturnable {

    @FXML
    private TableView<Room> tvRooms;
    @FXML
    private TableColumn<Room, Integer> tblClmRoomId;
    @FXML
    private TableColumn<Room, String> tblClmRoomName;
    @FXML
    private TableColumn<Room, Integer> tblClmRoomCapacity;
    @FXML
    private TableColumn<Room, Integer> tblClmRoomUnitPrice;

    @FXML
    private TextField tfTargetRoomName;
    @FXML
    private TextField tfTargetCapacity;
    @FXML
    private TextField tfTargetUnitPrice;

    @FXML
    private RadioButton rbAdd;
    @FXML
    private RadioButton rbUpdate;
    @FXML
    private RadioButton rbDelete;

    @FXML
    private TextField tfRoomName;
    @FXML
    private TextField tfCapacity;
    @FXML
    private TextField tfUnitPrice;

    @FXML
    private Label lblInformation;
    @FXML
    private Label lblInformationRoomName;
    @FXML
    private Label lblInformationCapacity;
    @FXML
    private Label lblInformationUnitPrice;
    @FXML
    private Label lblSearchFeedback;

    @FXML
    private Button btnAdd;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;


    @FXML
    private Label lblAddFeedback;
    @FXML
    private Label lblUpdateFeedback;
    @FXML
    private Label lblDeleteFeedback;


    private ObservableList<Room> roomsData = FXCollections.observableArrayList();
    private List<Room> allRooms = new ArrayList<>();


    @Override
    public void goBackHomePage() {
        SceneManager.switchScene("/com/mustafa/hotelreservationsystem/ui/controllers/AdminHomePage.fxml",
                new SceneInitializer<AdminHomePageController>() {
                    @Override
                    public void initialize(AdminHomePageController controller) {
                        AdminService adminService = new AdminServiceImpl();
                        List<Admin> allAdmins = adminService.getAllAdmins();
                        controller.setTableView(allAdmins);
                    }

                }
        );
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tblClmRoomId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblClmRoomName.setCellValueFactory(new PropertyValueFactory<>("roomName"));
        tblClmRoomCapacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        tblClmRoomUnitPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        Utils.setupTableViewSelectionMultiple(tvRooms);

        rbAdd.setSelected(true);
        onRadioButtonAddClicked();

        lblSearchFeedback.setText("");
    }


    public void setTableView(List<Room> rooms) {
        roomsData.setAll(rooms);
        tvRooms.setItems(roomsData);

        allRooms = rooms;
    }

    @FXML
    public void onButtonAddClicked() {

        String roomName = tfRoomName.getText();
        if (roomName.isEmpty() ) {
            AlertManager.showWarning("Warning", "Please enter a room name");
            return;
        }

        int capacity;
        String roomCapacity = tfCapacity.getText();
        if (roomCapacity.isEmpty() ) {
            AlertManager.showWarning("Warning", "Please enter a room capacity");
            return;
        }
        else {
            try {
                capacity = Integer.parseInt(roomCapacity);
            } catch (NumberFormatException e) {
                AlertManager.showWarning("Warning", "Please enter a valid room capacity");
                return;
            }
        }

        int price;
        String roomUnitPrice = tfUnitPrice.getText();
        if (roomUnitPrice.isEmpty() ) {
            AlertManager.showWarning("Warning", "Please enter a room unit price");
            return;
        }
        else {
            try {
                price = Integer.parseInt(roomUnitPrice);
            }
            catch (NumberFormatException e) {
                AlertManager.showWarning("Warning", "Please enter a valid room unit price");
                return;
            }
        }

        RoomService roomService = new RoomServiceImpl();
        roomService.createRoom(new Room(roomName, capacity, price, false, null));
        setTableView(roomService.getAllRooms());

        lblAddFeedback.setText("Adding process successful");

    }


    @FXML
    public void onButtonUpdateClicked() {

        if (tvRooms.getSelectionModel().getSelectedItem() == null) {
            AlertManager.showWarning("Warning", "Please select at least one room to update");
            return;
        }


        String roomName = tfRoomName.getText();

        int capacity = -1;
        String roomCapacity = tfCapacity.getText();
        if ( ! roomCapacity.isEmpty() ) {
            try {
                capacity = Integer.parseInt(roomCapacity);
            }
            catch (NumberFormatException e) {
                AlertManager.showWarning("Warning", "Please enter a valid room capacity");
                return;
            }
        }

        int price = -1;
        String roomUnitPrice = tfUnitPrice.getText();
        if ( ! roomUnitPrice.isEmpty() ) {
            try {
                price = Integer.parseInt(roomUnitPrice);
            }
            catch (NumberFormatException e) {
                AlertManager.showWarning("Warning", "Please enter a valid room unit price");
                return;
            }
        }


        RoomService roomService = new RoomServiceImpl();
        List<Room> selectedRooms = tvRooms.getSelectionModel().getSelectedItems();
        for (Room room : selectedRooms) {

            long roomId = room.getId();

            if ( ! roomName.isEmpty()){
                try{
                    roomService.changeRoomName(roomId, roomName);
                } catch (EntityNotFoundByIdException e) {
                    AlertManager.showError("Error", e.getMessage());
                    return;
                }
            }

            if (capacity != -1) {
                try{
                    roomService.changeCapacity(roomId, capacity);
                } catch (EntityNotFoundByIdException e) {
                    AlertManager.showError("Error", e.getMessage());
                    return;
                }
            }

            if (price != -1) {
                try{
                    roomService.changePrice(roomId, price);
                } catch (EntityNotFoundByIdException e) {
                    AlertManager.showError("Error", e.getMessage());
                    return;
                }
            }
        }


        setTableView(roomService.getAllRooms());
        lblUpdateFeedback.setText("Update process successful");


    }


    @FXML
    public void onButtonDeleteClicked() {
        if (tvRooms.getSelectionModel().getSelectedItem() == null) {
            AlertManager.showWarning("Warning", "Please select at least one room to delete");
            return;
        }

        RoomService roomService = new RoomServiceImpl();
        List<Room> selectedRooms = tvRooms.getSelectionModel().getSelectedItems();
        for (Room room : selectedRooms) {
            long roomId = room.getId();

            try{
                roomService.deleteRoom(roomId);
            }
            catch (EntityNotFoundByIdException e) {
                AlertManager.showError("Error", e.getMessage());
                return;
            }
        }

        setTableView(roomService.getAllRooms());
        lblDeleteFeedback.setText("Delete process successful");

    }

    @FXML
    public void onButtonSearchClicked() {

        String targetRoomName = tfTargetRoomName.getText();

        int minRoomCapacity = -1;
        String minRoomCapacityInString = tfTargetCapacity.getText();
        if ( ! minRoomCapacityInString.isEmpty()) {
            try{
                minRoomCapacity = Integer.parseInt(minRoomCapacityInString);
            }
            catch (NumberFormatException e) {
                AlertManager.showWarning("Warning", "Please enter a valid room capacity to search");
                lblSearchFeedback.setText("");
                return;
            }
        }

        int minRoomPrice = -1;
        String minRoomPriceInString = tfTargetUnitPrice.getText();
        if ( ! minRoomPriceInString.isEmpty()) {
            try{
                minRoomPrice = Integer.parseInt(minRoomPriceInString);
            }
            catch (NumberFormatException e) {
                AlertManager.showWarning("Warning", "Please enter a valid room unit price to search");
                lblSearchFeedback.setText("");
                return;
            }
        }


        ObservableList<Room> result = FXCollections.observableArrayList();

        for (Room room : allRooms) {

            String roomName = room.getRoomName();
            int capacity = room.getCapacity();
            int price = room.getPrice();

            boolean matchesRoomName = targetRoomName.isEmpty() || roomName.contains(targetRoomName);
            boolean matchesCapacity = (minRoomCapacity == -1) || (capacity >= minRoomCapacity);
            boolean matchesPrice = (minRoomPrice == -1) || (price >= minRoomPrice);

            if (matchesRoomName && matchesCapacity && matchesPrice) {
                result.add(room);
            }
        }

        roomsData.setAll(result);
        lblSearchFeedback.setText(result.size() + " rooms found");

    }

    @FXML
    public void onButtonClearAllFiltersClicked() {
        tfTargetRoomName.clear();
        tfTargetCapacity.clear();
        tfTargetUnitPrice.clear();

        roomsData.setAll(allRooms);
        lblSearchFeedback.setText("");
    }

    @FXML
    public void onRadioButtonAddClicked() {
        btnAdd.setVisible(true);
        btnUpdate.setVisible(false);
        btnDelete.setVisible(false);

        lblAddFeedback.setVisible(true);
        lblUpdateFeedback.setVisible(false);
        lblDeleteFeedback.setVisible(false);

        setInformationFieldsVisibility(true);

        lblInformation.setText("Add New Room");
        setEmptyAllInformationFields();
        setEmptyAllFeedbackFields();
    }

    @FXML
    public void onRadioButtonUpdateClicked() {
        btnAdd.setVisible(false);
        btnUpdate.setVisible(true);
        btnDelete.setVisible(false);

        lblAddFeedback.setVisible(false);
        lblUpdateFeedback.setVisible(true);
        lblDeleteFeedback.setVisible(false);

        setInformationFieldsVisibility(true);

        lblInformation.setText("Update Selected Rooms");
        setEmptyAllInformationFields();
        setEmptyAllFeedbackFields();
    }

    @FXML
    public void onRadioButtonDeleteClicked() {
        btnAdd.setVisible(false);
        btnUpdate.setVisible(false);
        btnDelete.setVisible(true);

        lblAddFeedback.setVisible(false);
        lblUpdateFeedback.setVisible(false);
        lblDeleteFeedback.setVisible(true);

        setInformationFieldsVisibility(false);

        lblInformation.setText("Delete Selected Rooms");
        setEmptyAllFeedbackFields();
    }


    private void setInformationFieldsVisibility(boolean visible) {
        tfRoomName.setVisible(visible);
        tfCapacity.setVisible(visible);
        tfUnitPrice.setVisible(visible);

        lblInformationRoomName.setVisible(visible);
        lblInformationCapacity.setVisible(visible);
        lblInformationUnitPrice.setVisible(visible);
    }

    private void setEmptyAllInformationFields() {
        tfRoomName.setText("");
        tfCapacity.setText("");
        tfUnitPrice.setText("");
    }

    private void setEmptyAllFeedbackFields() {
        lblAddFeedback.setText("");
        lblUpdateFeedback.setText("");
        lblDeleteFeedback.setText("");
    }


}
