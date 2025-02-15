package com.mustafa.hotelreservationsystem.ui.controllers;

import com.mustafa.hotelreservationsystem.exceptions.general.*;
import com.mustafa.hotelreservationsystem.models.Admin;
import com.mustafa.hotelreservationsystem.services.AdminService;
import com.mustafa.hotelreservationsystem.services.AdminServiceImpl;
import com.mustafa.hotelreservationsystem.services.ReceptionistService;
import com.mustafa.hotelreservationsystem.services.ReceptionistServiceImpl;
import com.mustafa.hotelreservationsystem.ui.utils.AlertManager;
import com.mustafa.hotelreservationsystem.ui.utils.SceneInitializer;
import com.mustafa.hotelreservationsystem.ui.utils.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {

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
            catch (InvalidAdminException e){
                AlertManager.showWarning("Warning", e.getMessage());
            }


        }
        else if (radioButtonReceptionist.isSelected()) {
            ReceptionistService rService = new ReceptionistServiceImpl();
            try{
                rService.validateReceptionist(username, password);
            }
            catch (InvalidReceptionistException e) {
                AlertManager.showWarning("Warning", e.getMessage());
            }

        }


        SceneManager.switchScene("/com/mustafa/hotelreservationsystem/ui/controllers/AdminHomePage.fxml",
                                         new SceneInitializer<AdminHomePageController>() {
            @Override
            public void initialize(AdminHomePageController controller) {
                AdminService adminService = new AdminServiceImpl();
                List<Admin> allAdmins = adminService.getAllAdmins();
                controller.setTableView(allAdmins);
            }
        });

    }

    private void otomatikDoldur(){
        textFieldUsername.setText("mamo");
        textFieldPassword.setText("mamo123");

        radioButtonAdmin.setSelected(true);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        otomatikDoldur();
    }
}