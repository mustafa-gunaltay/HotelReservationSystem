package com.mustafa.hotelreservationsystem.ui.controllers;

import com.mustafa.hotelreservationsystem.models.ReservationWithCustomerAndRoom;
import com.mustafa.hotelreservationsystem.models.ReservationWithCustomerAndRoomTableViewModel;
import com.mustafa.hotelreservationsystem.models.RoomWithFeatureAndServiceTableViewModel;
import com.mustafa.hotelreservationsystem.services.ReservationService;
import com.mustafa.hotelreservationsystem.services.ReservationServiceImpl;
import com.mustafa.hotelreservationsystem.ui.utils.SceneInitializer;
import com.mustafa.hotelreservationsystem.ui.utils.SceneManager;
import com.mustafa.hotelreservationsystem.ui.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RoomsInfoPageController implements Initializable, HomePageReturnable {

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
    private TextField tfTargetRoomName;
    @FXML
    private Label lblSearchFeedBack;


    private ObservableList<RoomWithFeatureAndServiceTableViewModel> roomtableViewData = FXCollections.observableArrayList();
    private List<RoomWithFeatureAndServiceTableViewModel> allReservations = new ArrayList<>();


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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tblClmRoomId.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        tblClmRoomName.setCellValueFactory(new PropertyValueFactory<>("roomName"));
        tblClmRoomCapacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        tblClmRoomUnitPrice.setCellValueFactory(new PropertyValueFactory<>("roomUnitPrice"));
        tblClmRoomFeatures.setCellValueFactory(new PropertyValueFactory<>("featureNames"));
        tblClmRoomServices.setCellValueFactory(new PropertyValueFactory<>("serviceNames"));

        Utils.setupTableViewSelectionMultiple(tvRoomsWithTheirFeaturesAndServices);

        lblSearchFeedBack.setText("");
    }


    public void setTableView(List<RoomWithFeatureAndServiceTableViewModel> rooms) {

        roomtableViewData.setAll(rooms);
        tvRoomsWithTheirFeaturesAndServices.setItems(roomtableViewData);

        allReservations = rooms;
    }


    @FXML
    public void onSearchClicked() {

        String targetRoomName = tfTargetRoomName.getText();


        ObservableList<RoomWithFeatureAndServiceTableViewModel> result = FXCollections.observableArrayList();

        if (targetRoomName.isEmpty()) {
            result.setAll(allReservations);
        }
        else {
            for (RoomWithFeatureAndServiceTableViewModel room : allReservations) {
                if (room.getRoomName().contains(targetRoomName)) {
                    result.add(room);
                }
            }

        }

        roomtableViewData.setAll(result);
        lblSearchFeedBack.setText(result.size() + " rooms found");
    }
}
