package com.mustafa.hotelreservationsystem.ui.controllers;

import com.mustafa.hotelreservationsystem.models.Feature;
import com.mustafa.hotelreservationsystem.models.ReservationWithCustomerAndRoom;
import com.mustafa.hotelreservationsystem.models.ReservationWithCustomerAndRoomTableViewModel;
import com.mustafa.hotelreservationsystem.models.Service;
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

public class ServicesInfoPageController implements Initializable, HomePageReturnable {

    @FXML
    private TableView<Service> tvServices;
    @FXML
    private TableColumn<Service, Long> tblClmServiceId;
    @FXML
    private TableColumn<Service, String> tblClmServiceName;
    @FXML
    private TableColumn<Service, Long> tblClmServicePrice;

    @FXML
    private TextField tfTargetServiceName;
    @FXML
    private Label lblSearchFeedBack;

    private ObservableList<Service> servicesData = FXCollections.observableArrayList();
    private List<Service> allServices = new ArrayList<>();


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

        tblClmServiceId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblClmServiceName.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        tblClmServicePrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        Utils.setupTableViewSelectionMultiple(tvServices);

        lblSearchFeedBack.setText("");
    }

    public void setTableView(List<Service> services) {
        servicesData.setAll(services);
        tvServices.setItems(servicesData);

        allServices.addAll(services);
    }


    @FXML
    public void onSearchClicked() {

        String targetServiceName = tfTargetServiceName.getText();
        ObservableList<Service> result = FXCollections.observableArrayList();


        if (targetServiceName.isEmpty()) {
            result.addAll(allServices);
        }
        else {
            for (Service service : allServices) {
                if (service.getServiceName().contains(targetServiceName)) {
                    result.add(service);
                }
            }
        }

        servicesData.setAll(result);
        lblSearchFeedBack.setText(result.size() + " services found");

    }


}
