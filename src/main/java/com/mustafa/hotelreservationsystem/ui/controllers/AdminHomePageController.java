package com.mustafa.hotelreservationsystem.ui.controllers;

import com.mustafa.hotelreservationsystem.models.Admin;
import com.mustafa.hotelreservationsystem.models.Receptionist;
import com.mustafa.hotelreservationsystem.services.ReceptionistService;
import com.mustafa.hotelreservationsystem.services.ReceptionistServiceImpl;
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
                });

    }


}
