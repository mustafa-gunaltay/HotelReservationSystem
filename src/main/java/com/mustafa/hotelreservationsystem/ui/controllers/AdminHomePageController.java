package com.mustafa.hotelreservationsystem.ui.controllers;

import com.mustafa.hotelreservationsystem.models.Admin;
import com.mustafa.hotelreservationsystem.models.Customer;
import com.mustafa.hotelreservationsystem.models.Receptionist;
import com.mustafa.hotelreservationsystem.models.Service;
import com.mustafa.hotelreservationsystem.services.*;
import com.mustafa.hotelreservationsystem.ui.utils.SceneInitializer;
import com.mustafa.hotelreservationsystem.ui.utils.SceneManager;
import com.mustafa.hotelreservationsystem.ui.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminHomePageController implements Initializable {

    @FXML
    private TableView<Admin> tvAdmin;

    @FXML
    private TableColumn<Admin, Long> tblClmId;

    @FXML
    private TableColumn<Admin, String> tblClmFullName;

    @FXML
    private TableColumn<Admin, String> tblClmUsername;

    @FXML
    private TableColumn<Admin, String> tblClmPassword;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tblClmId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblClmFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        tblClmUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        tblClmPassword.setCellValueFactory(new PropertyValueFactory<>("passwordd"));

        Utils.setupTableViewSelectionMultiple(tvAdmin);
    }

    public void setTableView(List<Admin> admins) {
        ObservableList<Admin> data = FXCollections.observableList(admins);
        tvAdmin.setItems(data);
    }

    @FXML
    public void onEditReceptionistClick() {

        SceneManager.switchScene("/com/mustafa/hotelreservationsystem/ui/controllers/EditingReceptionistPage.fxml",
                new SceneInitializer<EditingReceptionistPageController>() {
                    @Override
                    public void initialize(EditingReceptionistPageController controller) {
                        ReceptionistService receptionistService = new ReceptionistServiceImpl();
                        List<Receptionist> allReceptionists = receptionistService.getAllReceptionists();
                        controller.setTableView(allReceptionists);
                    }
                }
        );

    }


    @FXML
    public void onEditCustomersClicked() {

        SceneManager.switchScene("/com/mustafa/hotelreservationsystem/ui/controllers/EditingCustomersPage.fxml",
                new SceneInitializer<EditingCustomersPageController>() {
                    @Override
                    public void initialize(EditingCustomersPageController controller) {

                        CustomerService customerService = new CustomerServiceImpl();
                        List<Customer> allCustomers = customerService.getAllCustomers();

                        controller.setTableView(allCustomers);

                    }
                }
        );

    }

    @FXML
    public void onEditServicesClicked() {

        SceneManager.switchScene("/com/mustafa/hotelreservationsystem/ui/controllers/EditingServicesPage.fxml",
                new SceneInitializer<EditingServicesPageController>() {
                    @Override
                    public void initialize(EditingServicesPageController controller) {

                        ServiceService serviceService = new ServiceServiceImpl();
                        List<Service> allServices = serviceService.getAllServices();

                        controller.setTableView(allServices);

                    }
                }
        );

    }


}
