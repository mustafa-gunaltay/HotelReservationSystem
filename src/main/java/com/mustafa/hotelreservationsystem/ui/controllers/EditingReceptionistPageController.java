package com.mustafa.hotelreservationsystem.ui.controllers;

import com.mustafa.hotelreservationsystem.exceptions.general.EntityNotFoundByIdException;
import com.mustafa.hotelreservationsystem.exceptions.general.SameEntityValueExistInDbException;
import com.mustafa.hotelreservationsystem.models.Admin;
import com.mustafa.hotelreservationsystem.models.Receptionist;
import com.mustafa.hotelreservationsystem.services.AdminService;
import com.mustafa.hotelreservationsystem.services.AdminServiceImpl;
import com.mustafa.hotelreservationsystem.services.ReceptionistService;
import com.mustafa.hotelreservationsystem.services.ReceptionistServiceImpl;
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

public class EditingReceptionistPageController implements Initializable {

    @FXML
    private TableView<Receptionist> tvReceptionist;
    @FXML
    private TableColumn<Receptionist, Long> tblClmId;
    @FXML
    private TableColumn<Receptionist, String> tblClmFullName;
    @FXML
    private TableColumn<Receptionist, String> tblClmUsername;
    @FXML
    private TableColumn<Receptionist, String> tblClmPassword;

    @FXML
    private TextField tfFullName;
    @FXML
    private TextField tfUsername;
    @FXML
    private TextField tfPassword;

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
    private Label lblFullName;
    @FXML
    private Label lblUsername;
    @FXML
    private Label lblPassword;
    @FXML
    private Label lblInformation;

    @FXML
    private Label lblAddProcessFeedBack;
    @FXML
    private Label lblUpdateProcessFeedBack;
    @FXML
    private Label lblDeleteProcessFeedBack;

    private ObservableList<Receptionist> data = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tblClmId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblClmFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        tblClmUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        tblClmPassword.setCellValueFactory(new PropertyValueFactory<>("passwordd"));

        Utils.setupTableViewSelectionMultiple(tvReceptionist);

        // checking add process by default
        rbAdd.setSelected(true);
        onRadioButtonAddClicked();
    }

    public void setTableView(List<Receptionist> allReceptionist) {
        data.setAll(allReceptionist); // Mevcut listeyi günceller
        tvReceptionist.setItems(data);
    }


    @FXML
    public void onRadioButtonAddClicked() {

        lblInformation.setText("Add New Receptionist");

        lblFullName.setVisible(true);
        lblUsername.setVisible(true);
        lblPassword.setVisible(true);

        tfFullName.setVisible(true);
        tfUsername.setVisible(true);
        tfPassword.setVisible(true);

        btnAdd.setVisible(true);
        btnUpdate.setVisible(false);
        btnDelete.setVisible(false);

        setEmptyAllTextFields();
        setEmptyAllFeedBackLabels();

    }

    @FXML
    public void onRadioButtonUpdateClicked() {

        lblInformation.setText("Update Selected Receptionists");

        lblFullName.setVisible(true);
        lblUsername.setVisible(true);
        lblPassword.setVisible(true);

        tfFullName.setVisible(true);
        tfUsername.setVisible(true);
        tfPassword.setVisible(true);

        btnAdd.setVisible(false);
        btnUpdate.setVisible(true);
        btnDelete.setVisible(false);

        setEmptyAllTextFields();
        setEmptyAllFeedBackLabels();

    }

    @FXML
    public void onRadioButtonDeleteClicked() {

        lblInformation.setText("Delete Selected Receptionists");

        lblFullName.setVisible(false);
        lblUsername.setVisible(false);
        lblPassword.setVisible(false);

        tfFullName.setVisible(false);
        tfUsername.setVisible(false);
        tfPassword.setVisible(false);

        btnAdd.setVisible(false);
        btnUpdate.setVisible(false);
        btnDelete.setVisible(true);

        setEmptyAllFeedBackLabels();
    }

    private void setEmptyAllTextFields() {
        tfFullName.clear();
        tfUsername.clear();
        tfPassword.clear();
    }

    private void setEmptyAllFeedBackLabels(){
        lblAddProcessFeedBack.setText("");
        lblUpdateProcessFeedBack.setText("");
        lblDeleteProcessFeedBack.setText("");
    }


    @FXML
    public void onButtonAddClicked() {

        String fullName = null;
        if ( ! tfFullName.getText().isEmpty() ) {
            fullName = tfFullName.getText();
        }

        if (tfUsername.getText().isEmpty()) {
            AlertManager.showWarning("Warning", "Please enter username");
            return;
        }
        String username = tfUsername.getText();

        if (tfPassword.getText().isEmpty()) {
            AlertManager.showWarning("Warning", "Please enter password");
            return;
        }
        String password = tfPassword.getText();

        ReceptionistService receptionistService = new ReceptionistServiceImpl();
        try{
            receptionistService.createReceptionist(new Receptionist(fullName, username, password));
        } catch (SameEntityValueExistInDbException e) {
            AlertManager.showWarning("Warning", e.getMessage());
            return;
        }

        setTableView(receptionistService.getAllReceptionists());
        lblAddProcessFeedBack.setText("Add Process Successful");
        lblUpdateProcessFeedBack.setText("");
        lblDeleteProcessFeedBack.setText("");

    }


    @FXML
    public void onButtonUpdateClicked() {

        if (tvReceptionist.getSelectionModel().getSelectedItem() == null) {
            AlertManager.showWarning("Warning", "Please select at least one Receptionist");
            return;
        }

        String fullName = null;
        if ( ! tfFullName.getText().isEmpty() ) {
            fullName = tfFullName.getText();
        }
        String username = null;
        if ( ! tfUsername.getText().isEmpty() ) {
            username = tfUsername.getText();
        }
        String password = null;
        if ( ! tfPassword.getText().isEmpty() ) {
            password = tfPassword.getText();
        }

        ReceptionistService receptionistService = new ReceptionistServiceImpl();
        if (receptionistService.isUsernameTaken(username)) {
            AlertManager.showWarning("Warning", "Username is already taken");
            return;
        }
        List<Receptionist> selectedReceptionists = tvReceptionist.getSelectionModel().getSelectedItems();
        for ( Receptionist r : selectedReceptionists ) {

            long id = r.getId();

            if (fullName != null) {
                try{
                    receptionistService.changeFullName(id, fullName);
                }
                catch (EntityNotFoundByIdException e){
                    AlertManager.showWarning("Warning", e.getMessage());
                    return;
                }
            }

            if (username != null) {
                try {
                    receptionistService.changeUsername(id, username);
                } catch (EntityNotFoundByIdException e) {
                    AlertManager.showWarning("Essential Warning", e.getMessage());
                    return;
                } catch (SameEntityValueExistInDbException e) {
                    AlertManager.showWarning("Warning", e.getMessage());
                    return;
                }
            }

            if (password != null) {
                try {
                    receptionistService.changePassword(id, password);
                } catch (EntityNotFoundByIdException e) {
                    AlertManager.showWarning("Warning", e.getMessage());
                    return;
                }
            }

        }

        setTableView(receptionistService.getAllReceptionists());
        lblAddProcessFeedBack.setText("");
        lblUpdateProcessFeedBack.setText("Update Process Successful");
        lblDeleteProcessFeedBack.setText("");

    }



    @FXML
    public void onButtonDeleteClicked() {
        if (tvReceptionist.getSelectionModel().getSelectedItem() == null) {
            AlertManager.showWarning("Warning", "Please select at least one Receptionist");
            return;
        }

        ReceptionistService receptionistService = new ReceptionistServiceImpl();
        List<Receptionist> selectedReceptionists = tvReceptionist.getSelectionModel().getSelectedItems();
        for ( Receptionist r : selectedReceptionists ) {

            long id = r.getId();

            try{
                receptionistService.deleteReceptionist(id);
            } catch (EntityNotFoundByIdException e) {
                AlertManager.showWarning("Essential Warning", e.getMessage());
                return;
            }
        }

        setTableView(receptionistService.getAllReceptionists());
        lblAddProcessFeedBack.setText("");
        lblUpdateProcessFeedBack.setText("");
        lblDeleteProcessFeedBack.setText("Delete Process Successful");

    }

}
