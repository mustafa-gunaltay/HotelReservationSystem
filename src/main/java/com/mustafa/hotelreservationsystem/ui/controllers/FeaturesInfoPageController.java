package com.mustafa.hotelreservationsystem.ui.controllers;

import com.mustafa.hotelreservationsystem.models.Feature;
import com.mustafa.hotelreservationsystem.models.ReservationWithCustomerAndRoom;
import com.mustafa.hotelreservationsystem.models.ReservationWithCustomerAndRoomTableViewModel;
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

public class FeaturesInfoPageController implements Initializable, HomePageReturnable {

    @FXML
    private TableView<Feature> tvFeatures;
    @FXML
    private TableColumn<Feature, Long> tblClmFeatureId;
    @FXML
    private TableColumn<Feature, String> tblClmFeatureName;
    @FXML
    private TableColumn<Feature, Long> tblClmFeaturePrice;

    @FXML
    private TextField tfTargetFeatureName;
    @FXML
    private Label lblSearchFeedBack;

    private ObservableList<Feature> featuresData = FXCollections.observableArrayList();
    private List<Feature> allFeatures = new ArrayList<>();


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
        tblClmFeatureId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblClmFeatureName.setCellValueFactory(new PropertyValueFactory<>("featureName"));
        tblClmFeaturePrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        Utils.setupTableViewSelectionMultiple(tvFeatures);

        lblSearchFeedBack.setText("");
    }


    public void setTableView(List<Feature> features) {
        featuresData.setAll(features);
        tvFeatures.setItems(featuresData);

        allFeatures = features;
    }


    @FXML
    public void onSearchClicked() {

        String targetFeatureName = tfTargetFeatureName.getText();
        ObservableList<Feature> result = FXCollections.observableArrayList();


        if (targetFeatureName.isEmpty()) {
            result.addAll(allFeatures);
        }
        else {
            for (Feature feature : allFeatures) {
                if (feature.getFeatureName().contains(targetFeatureName)) {
                    result.add(feature);
                }
            }
        }

        featuresData.setAll(result);
        lblSearchFeedBack.setText(result.size() + " features found");

    }




}
