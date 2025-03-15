package com.mustafa.hotelreservationsystem.ui.controllers;

import com.mustafa.hotelreservationsystem.exceptions.general.EntityNotFoundByIdException;
import com.mustafa.hotelreservationsystem.models.Admin;
import com.mustafa.hotelreservationsystem.models.Service;
import com.mustafa.hotelreservationsystem.services.AdminService;
import com.mustafa.hotelreservationsystem.services.AdminServiceImpl;
import com.mustafa.hotelreservationsystem.services.ServiceService;
import com.mustafa.hotelreservationsystem.services.ServiceServiceImpl;
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
import java.util.List;
import java.util.ResourceBundle;

public class EditingServicesPageController implements Initializable, HomePageReturnable {

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
    private TextField tfTargetServicePrice;

    @FXML
    private Button btnAdd;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;


    @FXML
    private Label lblInformation;
    @FXML
    private Label lblInformationServiceName;
    @FXML
    private Label lblInformationServicePrice;
    @FXML
    private Label lblSearchFeedback;
    @FXML
    private Label lblAddFeedback;
    @FXML
    private Label lblUpdateFeedback;
    @FXML
    private Label lblDeleteFeedback;

    @FXML
    private RadioButton rbAdd;
    @FXML
    private RadioButton rbUpdate;
    @FXML
    private RadioButton rbDelete;

    @FXML
    private TextField tfServiceName;
    @FXML
    private TextField tfServicePrice;


    private ObservableList<Service> servicesData = FXCollections.observableArrayList();
    private List<Service> allServices;



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

        tblClmServiceId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblClmServiceName.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        tblClmServicePrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        Utils.setupTableViewSelectionMultiple(tvServices);

        rbAdd.setSelected(true);
        onRadioButtonAddClicked();

        lblSearchFeedback.setText("");
    }

    public void setTableView (List<Service> allServices) {
        servicesData.setAll(allServices);
        tvServices.setItems(servicesData);

        this.allServices = allServices;
    }


    @FXML
    public void onButtonSearchClicked() {

        String targetServiceName = tfTargetServiceName.getText();

        long targetMinServicePrice;
        if (tfTargetServicePrice.getText().isEmpty()) {
            targetMinServicePrice = 0L;
        }
        else {
            try {
                targetMinServicePrice = Long.parseLong(tfTargetServicePrice.getText());
            } catch (NumberFormatException e) {
                AlertManager.showWarning("Warning", "Please enter a valid price");
                return;
            }
        }

        ObservableList<Service> result = FXCollections.observableArrayList();

        for (Service service : allServices) {

            String serviceName = service.getServiceName();
            int servicePrice = service.getPrice();

            boolean matchesServiceName = targetServiceName.isEmpty() || serviceName.contains(targetServiceName);

            boolean matchesServicePrice = targetMinServicePrice == 0L || servicePrice >= (int) targetMinServicePrice;

            if (matchesServiceName && matchesServicePrice) {
                result.add(service);
            }
        }

        servicesData.setAll(result);
        lblSearchFeedback.setText( result.size() + " services found");

    }


    @FXML
    public void onButtonClearAllFiltersClicked() {
        tfTargetServiceName.clear();
        tfTargetServicePrice.clear();

        servicesData.setAll(allServices);

        lblSearchFeedback.setText("");
    }


    @FXML
    public void onButtonAddClicked() {

        if (tfServiceName.getText().isEmpty() ) {
            AlertManager.showWarning("Warning", "Please enter service name");
            return;
        }
        String serviceName = tfServiceName.getText();

        long servicePrice;
        if (tfServicePrice.getText().isEmpty()) {
            AlertManager.showWarning("Warning", "Please enter service price");
            return;
        }
        else {
            try{
                servicePrice = Long.parseLong(tfServicePrice.getText());
            } catch (NumberFormatException e) {
                AlertManager.showWarning("Warning", "Please enter a valid price");
                return;
            }
        }




        ServiceService serviceService = new ServiceServiceImpl();
        serviceService.createService(new Service(serviceName, (int) servicePrice));
        setTableView(serviceService.getAllServices());

        lblAddFeedback.setText("Adding process successful");

    }



    @FXML
    public void onButtonUpdateClicked() {

        if (tvServices.getSelectionModel().getSelectedItem() == null) {
            AlertManager.showWarning("Warning", "Please select at least one service to update");
            return;
        }

        String serviceName = null;
        if ( ! tfServiceName.getText().isEmpty()) {
            serviceName = tfServiceName.getText();
        }

        long servicePrice = 0L;
        if ( ! tfServicePrice.getText().isEmpty()) {
            try{
                servicePrice = Long.parseLong(tfServicePrice.getText());
            } catch (NumberFormatException e) {
                AlertManager.showWarning("Warning", "Please enter a valid price");
                return;
            }
        }

        ServiceService serviceService = new ServiceServiceImpl();
        List<Service> selectedServices = tvServices.getSelectionModel().getSelectedItems();
        for (Service service : selectedServices) {

            long serviceId = service.getId();

            if (serviceName != null){
                try {
                    serviceService.changeServiceName(serviceId, serviceName);
                } catch (EntityNotFoundByIdException e) {
                    AlertManager.showError("Error", e.getMessage());
                    return;
                }
            }

            if (servicePrice != 0L) {
                try{
                    serviceService.changePrice(serviceId, (int) servicePrice);
                } catch (EntityNotFoundByIdException e) {
                    AlertManager.showError("Error", e.getMessage());
                    return;
                }
            }
        }

        setTableView(serviceService.getAllServices());
        lblUpdateFeedback.setText("Updating process successful");

    }


    @FXML
    public void onButtonDeleteClicked() {

        if (tvServices.getSelectionModel().getSelectedItem() == null) {
            AlertManager.showWarning("Warning", "Please select at least one service to delete");
            return;
        }

        ServiceService serviceService = new ServiceServiceImpl();
        List<Service> selectedServices = tvServices.getSelectionModel().getSelectedItems();
        for (Service service : selectedServices) {

            long serviceId = service.getId();

            try{
                serviceService.deleteService(serviceId);
            } catch (EntityNotFoundByIdException e) {
                AlertManager.showError("Error", e.getMessage());
                return;
            }
        }

        setTableView(serviceService.getAllServices());
        lblDeleteFeedback.setText("Deleting process successful");

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

        lblInformation.setText("Add New Service");
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

        lblInformation.setText("Update Selected Services");
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

        lblInformation.setText("Delete Selected Services");
        setEmptyAllFeedbackFields();
    }

    private void setInformationFieldsVisibility(boolean visible) {
        tfServiceName.setVisible(visible);
        tfServicePrice.setVisible(visible);

        lblInformationServiceName.setVisible(visible);
        lblInformationServicePrice.setVisible(visible);
    }

    private void setEmptyAllInformationFields() {
        tfServiceName.setText("");
        tfServicePrice.setText("");
    }

    private void setEmptyAllFeedbackFields() {
        lblAddFeedback.setText("");
        lblUpdateFeedback.setText("");
        lblDeleteFeedback.setText("");
    }

}
