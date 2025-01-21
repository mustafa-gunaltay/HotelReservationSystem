package com.mustafa.hotelreservationsystem.ui.controllers;

import com.mustafa.hotelreservationsystem.exceptions.general.*;
import com.mustafa.hotelreservationsystem.services.AdminService;
import com.mustafa.hotelreservationsystem.services.AdminServiceImpl;
import com.mustafa.hotelreservationsystem.services.ReceptionistService;
import com.mustafa.hotelreservationsystem.services.ReceptionistServiceImpl;
import com.mustafa.hotelreservationsystem.ui.utils.AlertManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class LoginPageController {

    @FXML
    TextField textFieldUsername;

    @FXML
    TextField textFieldPassword;

    @FXML
    RadioButton radioButtonAdmin;

    @FXML
    RadioButton radioButtonReceptionist;

    public void onLoginClick(){

        if (!radioButtonAdmin.isSelected() && !radioButtonReceptionist.isSelected()){
            AlertManager.showWarning("Warning", "At least 1 role must be checked");
            return;
        }

        String username = textFieldUsername.getText();
        String password = textFieldPassword.getText();

        if (radioButtonAdmin.isSelected()){
            AdminService aService = new AdminServiceImpl();
            try {
                aService.validateAdmin(username, password);
            }
            catch (InvalidAdminUsernameException e){
                AlertManager.showWarning("Warning", e.getMessage());
            }
            catch (InvalidAdminPasswordException e){
                AlertManager.showWarning("Warning", e.getMessage());
            }

        }
        else if (radioButtonReceptionist.isSelected()) {
            ReceptionistService rService = new ReceptionistServiceImpl();
            try{
                rService.validateReceptionist(username, password);
            }
            catch (InvalidReceptionistUsernameException e) {
                AlertManager.showWarning("Warning", e.getMessage());
            }
            catch (InvalidReceptionistPasswordException e) {
                AlertManager.showWarning("Warning", e.getMessage());
            }

        }


    }



}