package com.mustafa.hotelreservationsystem.ui.controllers;

import com.mustafa.hotelreservationsystem.exceptions.general.EntityNotFoundByIdException;
import com.mustafa.hotelreservationsystem.exceptions.general.SameEntityValueExistInDbException;
import com.mustafa.hotelreservationsystem.models.Customer;
import com.mustafa.hotelreservationsystem.models.ReservationWithCustomerAndRoom;
import com.mustafa.hotelreservationsystem.models.ReservationWithCustomerAndRoomTableViewModel;
import com.mustafa.hotelreservationsystem.services.CustomerService;
import com.mustafa.hotelreservationsystem.services.CustomerServiceImpl;
import com.mustafa.hotelreservationsystem.services.ReservationService;
import com.mustafa.hotelreservationsystem.services.ReservationServiceImpl;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EditingCustomersPageController implements Initializable, HomePageReturnable {

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
    private TextField tfTargetFullName;
    @FXML
    private TextField tfTargetPhoneNumber;

    @FXML
    private RadioButton rbAdd;
    @FXML
    private RadioButton rbUpdate;
    @FXML
    private RadioButton rbDelete;

    @FXML
    private Button btnAdd;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;

    @FXML
    private Label lblInformation;
    @FXML
    private Label lblInformationFullName;
    @FXML
    private Label lblInformationPhoneNumber;
    @FXML
    private Label lblInformationBirthDate;
    @FXML
    private Label lblInformationDescription;


    @FXML
    private Label lblAddFeedBack;
    @FXML
    private Label lblUpdateFeedBack;
    @FXML
    private Label lblDeleteFeedBack;

    @FXML
    private TextField tfFullName;
    @FXML
    private TextField tfPhoneNumber;
    @FXML
    private DatePicker dpBirthDate;
    @FXML
    private TextArea taDescription;


    private ObservableList<Customer> customersData = FXCollections.observableArrayList();
    private List<Customer> allCustomers = new ArrayList<>();


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
        tblClmCustomerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblClmCustomerFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        tblClmCustomerPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        tblClmCustomerBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        tblClmCustomerDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        Utils.setupTableViewSelectionMultiple(tvCustomers);

        rbAdd.setSelected(true);
        onRadioButtonAddClicked();
        // labellar bosaltilacak
    }

    public void setTableView(List<Customer> customers) {
        customersData.setAll(customers);
        tvCustomers.setItems(customersData);

        allCustomers = customers;
    }


    @FXML
    public void onRadioButtonAddClicked() {
        btnAdd.setVisible(true);
        btnUpdate.setVisible(false);
        btnDelete.setVisible(false);

        lblAddFeedBack.setVisible(true);
        lblUpdateFeedBack.setVisible(false);
        lblDeleteFeedBack.setVisible(false);

        setInformationFieldsVisibility(true);

        lblInformation.setText("Add New Customer");
        setEmptyAllInformationFields();
        setEmptyAllFeedBackFields();
    }


    @FXML
    public void onRadioButtonUpdateClicked() {
        btnAdd.setVisible(false);
        btnUpdate.setVisible(true);
        btnDelete.setVisible(false);

        lblAddFeedBack.setVisible(false);
        lblUpdateFeedBack.setVisible(true);
        lblDeleteFeedBack.setVisible(false);

        setInformationFieldsVisibility(true);

        lblInformation.setText("Update Selected Customers");
        setEmptyAllInformationFields();
        setEmptyAllFeedBackFields();
    }


    @FXML
    public void onRadioButtonDeleteClicked() {
        btnAdd.setVisible(false);
        btnUpdate.setVisible(false);
        btnDelete.setVisible(true);

        lblAddFeedBack.setVisible(false);
        lblUpdateFeedBack.setVisible(false);
        lblDeleteFeedBack.setVisible(true);

        setInformationFieldsVisibility(false);

        lblInformation.setText("Delete Selected Customers");
        setEmptyAllFeedBackFields();
    }

    private void setInformationFieldsVisibility(boolean visible) {
        tfFullName.setVisible(visible);
        tfPhoneNumber.setVisible(visible);
        dpBirthDate.setVisible(visible);
        taDescription.setVisible(visible);

        lblInformationFullName.setVisible(visible);
        lblInformationPhoneNumber.setVisible(visible);
        lblInformationBirthDate.setVisible(visible);
        lblInformationDescription.setVisible(visible);
    }

    private void setEmptyAllInformationFields() {
        tfFullName.setText("");
        tfPhoneNumber.setText("");
        dpBirthDate.setValue(null);
        taDescription.setText("");
    }

    private void setEmptyAllFeedBackFields() {
        lblAddFeedBack.setText("");
        lblUpdateFeedBack.setText("");
        lblDeleteFeedBack.setText("");
    }


    @FXML
    public void onButtonAddClicked() {

        if (tfFullName.getText().isEmpty() ) {
            AlertManager.showWarning("Warning", "Please enter your full name");
            return;
        }
        String fullName = tfFullName.getText();

        if (tfPhoneNumber.getText().isEmpty()) {
            AlertManager.showWarning("Warning", "Please enter a phone number");
            return;
        }
        String phoneNumber = tfPhoneNumber.getText();

        if (dpBirthDate.getValue() == null) {
            AlertManager.showWarning("Warning", "Please enter a birth date");
            return;
        }
        LocalDate birthDate = dpBirthDate.getValue();

        String description = null;
        if ( ! taDescription.getText().isEmpty() ) {
            description = taDescription.getText();
        }



        try{
            CustomerService customerService = new CustomerServiceImpl();
            customerService.createCustomer(new Customer(fullName, phoneNumber, birthDate, description));
            setTableView(customerService.getAllCustomers());

        } catch (SameEntityValueExistInDbException e) {
            AlertManager.showWarning("Warning", "Same phone number already exists in system");
            return;
        }

        lblAddFeedBack.setText("Add process successful");

    }


    @FXML
    public void onButtonUpdateClicked() {

        CustomerService customerService = new CustomerServiceImpl();

        if (tvCustomers.getSelectionModel().getSelectedItem() == null) {
            AlertManager.showWarning("Warning", "Please select at least one customer to update");
            return;
        }

        String fullName = null;
        if ( ! tfFullName.getText().isEmpty()) {
            fullName = tfFullName.getText();
        }

        String phoneNumber = null;
        if ( ! tfPhoneNumber.getText().isEmpty()) {
            phoneNumber = tfPhoneNumber.getText();
            if (customerService.isPhoneNumberTaken(phoneNumber)) {
                AlertManager.showWarning("Warning", "Phone number already taken");
                return;
            }
        }

        LocalDate birthDate = null;
        if (dpBirthDate.getValue() != null) {
            birthDate = dpBirthDate.getValue();
        }

        String description = null;
        if ( ! taDescription.getText().isEmpty() ) {
            description = taDescription.getText();
        }





        List<Customer> selectedCustomers = tvCustomers.getSelectionModel().getSelectedItems();
        for (Customer customer : selectedCustomers) {

            long customerId = customer.getId();

            if ( fullName != null ){
                try{
                    customerService.changeFullName(customerId, fullName);
                } catch (EntityNotFoundByIdException e) {
                    AlertManager.showError("Error", e.getMessage());
                    return;
                }
            }

            if ( phoneNumber != null ){
                try{
                    customerService.changePhoneNumber(customerId, phoneNumber);
                } catch (EntityNotFoundByIdException e) {
                    AlertManager.showError("Error", e.getMessage());
                    return;
                } catch (SameEntityValueExistInDbException e) {
                    // it is already checked above
                }
            }

            if ( birthDate != null ){
                try {
                    customerService.changeBirthDate(customerId, birthDate);
                }
                catch (EntityNotFoundByIdException e) {
                    AlertManager.showError("Error", e.getMessage());
                    return;
                }
            }

            if (description != null ){
                try {
                    customerService.changeDescription(customerId, description);
                } catch (EntityNotFoundByIdException e) {
                    AlertManager.showError("Error", e.getMessage());
                    return;
                }
            }
        }


        setTableView(customerService.getAllCustomers());
        lblUpdateFeedBack.setText("Update process successful");

    }


    @FXML
    public void onButtonDeleteClicked() {

        if (tvCustomers.getSelectionModel().getSelectedItem() == null) {
            AlertManager.showWarning("Warning", "Please select at least one customer to delete");
            return;
        }

        CustomerService customerService = new CustomerServiceImpl();
        List<Customer> selectedCustomers = tvCustomers.getSelectionModel().getSelectedItems();
        for (Customer customer : selectedCustomers) {
            long customerId = customer.getId();

            try {
                customerService.deleteCustomer(customerId);
            } catch (EntityNotFoundByIdException e) {
                AlertManager.showError("Error", e.getMessage());
                return;
            }
        }

        setTableView(customerService.getAllCustomers());
        lblDeleteFeedBack.setText("Delete process successful");
    }


    @FXML
    public void onClearAllFiltersClicked() {
        tfTargetFullName.clear();
        tfTargetPhoneNumber.clear();

        customersData.setAll(allCustomers);
    }


    @FXML
    public void onSearchClicked() {

        String targetFullName = tfTargetFullName.getText();
        String targetPhoneNumber = tfTargetPhoneNumber.getText();

        ObservableList<Customer> result = FXCollections.observableArrayList();

        for (Customer customer : allCustomers) {

            String fullName = customer.getFullName();
            String phoneNumber = customer.getPhoneNumber();

            boolean matchesFullName = fullName.isEmpty() || fullName.contains(targetFullName);

            boolean matchesPhoneNumber = phoneNumber.isEmpty() || phoneNumber.contains(targetPhoneNumber);

            if (matchesFullName && matchesPhoneNumber) {
                result.add(customer);
            }
        }

        customersData.setAll(result);
    }



}
