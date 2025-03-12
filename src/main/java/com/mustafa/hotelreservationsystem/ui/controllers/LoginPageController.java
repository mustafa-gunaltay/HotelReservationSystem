package com.mustafa.hotelreservationsystem.ui.controllers;

import com.mustafa.hotelreservationsystem.exceptions.general.*;
import com.mustafa.hotelreservationsystem.models.Admin;
import com.mustafa.hotelreservationsystem.models.Receptionist;
import com.mustafa.hotelreservationsystem.models.ReservationWithCustomerAndRoomTableViewModel;
import com.mustafa.hotelreservationsystem.models.ReservationWithCustomerAndRoom;
import com.mustafa.hotelreservationsystem.services.*;
import com.mustafa.hotelreservationsystem.ui.utils.AlertManager;
import com.mustafa.hotelreservationsystem.ui.utils.SceneInitializer;
import com.mustafa.hotelreservationsystem.ui.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    @FXML
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

                try{
                    Admin.currentAdmin = aService.getAdminByUsername(username);
                } catch (EntityNotFoundByUsernameException e) {
                    // never throws exception because admin can't login without proper username and pw
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
            catch (InvalidAdminException e){
                AlertManager.showWarning("Warning", e.getMessage());
            }


        }
        else if (radioButtonReceptionist.isSelected()) {
            ReceptionistService rService = new ReceptionistServiceImpl();
            try{
                rService.validateReceptionist(username, password);
                try{
                    Receptionist.currentReceptionist = rService.getReceptionistByUsername(username);
                } catch (EntityNotFoundByUsernameException e) {
                    // never throws exception because receptionist can't login without proper username and pw
                }

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
            catch (InvalidReceptionistException e) {
                AlertManager.showWarning("Warning", e.getMessage());
            }

        }




    }

    private void otomatikDoldur(){ // test icin
        textFieldUsername.setText("mamo");
        textFieldPassword.setText("mamo123");

        radioButtonAdmin.setSelected(true);
    }

    private void otomatikDoldur2(){ // test icin
        textFieldUsername.setText("ozy");
        textFieldPassword.setText("ozy123");

        radioButtonReceptionist.setSelected(true);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { // test icin
        otomatikDoldur2();
    }



}