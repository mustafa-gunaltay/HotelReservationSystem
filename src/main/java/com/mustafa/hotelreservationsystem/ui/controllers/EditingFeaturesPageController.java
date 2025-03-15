package com.mustafa.hotelreservationsystem.ui.controllers;

import com.mustafa.hotelreservationsystem.exceptions.general.EntityNotFoundByIdException;
import com.mustafa.hotelreservationsystem.models.Admin;
import com.mustafa.hotelreservationsystem.models.Feature;
import com.mustafa.hotelreservationsystem.models.Service;
import com.mustafa.hotelreservationsystem.services.*;
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

public class EditingFeaturesPageController implements Initializable, HomePageReturnable {

    @FXML
    private TableView<Feature> tvFeatures;
    @FXML
    private TableColumn<Feature, Long> tblClmFeatureId;
    @FXML
    private TableColumn<Feature, String> tblClmFeatureName;
    @FXML
    private TableColumn<Feature, Long> tblClmFeaturePrice;

    @FXML
    private TextField tfFeatureName;
    @FXML
    private TextField tfFeaturePrice;
    @FXML
    private TextField tfTargetFeatureName;
    @FXML
    private TextField tfTargetFeaturePrice;

    @FXML
    private Button btnAdd;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;

    @FXML
    private Label lblInformation;
    @FXML
    private Label lblInformationFeatureName;
    @FXML
    private Label lblInformationFeaturePrice;
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


    private ObservableList<Feature> featuresData = FXCollections.observableArrayList();
    private List<Feature> allFeatures;


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

        tblClmFeatureId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblClmFeatureName.setCellValueFactory(new PropertyValueFactory<>("featureName"));
        tblClmFeaturePrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        Utils.setupTableViewSelectionMultiple(tvFeatures);

        rbAdd.setSelected(true);
        onRadioButtonAddClicked();

        lblSearchFeedback.setText("");

    }


    public void setTableView (List<Feature> allFeatures) {
        featuresData.setAll(allFeatures);
        tvFeatures.setItems(featuresData);

        this.allFeatures = allFeatures;
    }


    @FXML
    public void onButtonSearchClicked() {

        String targetFeatureName = tfTargetFeatureName.getText();

        long targetMinFeaturePrice;
        if (tfTargetFeaturePrice.getText().isEmpty()) {
            targetMinFeaturePrice = 0L;
        }
        else {
            try {
                targetMinFeaturePrice = Long.parseLong(tfTargetFeaturePrice.getText());
            } catch (NumberFormatException e) {
                AlertManager.showWarning("Warning", "Please enter a valid price");
                return;
            }
        }

        ObservableList<Feature> result = FXCollections.observableArrayList();

        for (Feature feature : allFeatures) {

            String featureName = feature.getFeatureName();
            int featurePrice = feature.getPrice();

            boolean matchesFeatureName = targetFeatureName.isEmpty() || featureName.contains(targetFeatureName);

            boolean matchesFeaturePrice = targetMinFeaturePrice == 0L || featurePrice >= (int) targetMinFeaturePrice;

            if (matchesFeatureName && matchesFeaturePrice) {
                result.add(feature);
            }
        }

        featuresData.setAll(result);
        lblSearchFeedback.setText( result.size() + " services found");

    }


    @FXML
    public void onButtonClearAllFiltersClicked() {
        tfTargetFeatureName.clear();
        tfTargetFeaturePrice.clear();

        featuresData.setAll(allFeatures);

        lblSearchFeedback.setText("");
    }


    @FXML
    public void onButtonAddClicked() {

        if (tfFeatureName.getText().isEmpty() ) {
            AlertManager.showWarning("Warning", "Please enter feature name");
            return;
        }
        String featureName = tfFeatureName.getText();

        long featurePrice;
        if (tfFeaturePrice.getText().isEmpty()) {
            AlertManager.showWarning("Warning", "Please enter feature price");
            return;
        }
        else {
            try{
                featurePrice = Long.parseLong(tfFeaturePrice.getText());
            } catch (NumberFormatException e) {
                AlertManager.showWarning("Warning", "Please enter a valid price");
                return;
            }
        }


        FeatureService featureService = new FeatureServiceImpl();
        featureService.createFeature(new Feature(featureName, (int) featurePrice));
        setTableView(featureService.getAllFeatures());

        lblAddFeedback.setText("Adding process successful");

    }



    @FXML
    public void onButtonUpdateClicked() {

        if (tvFeatures.getSelectionModel().getSelectedItem() == null) {
            AlertManager.showWarning("Warning", "Please select at least one feature to update");
            return;
        }

        String featureName = null;
        if ( ! tfFeatureName.getText().isEmpty()) {
            featureName = tfFeatureName.getText();
        }

        long servicePrice = 0L;
        if ( ! tfFeaturePrice.getText().isEmpty()) {
            try{
                servicePrice = Long.parseLong(tfFeaturePrice.getText());
            } catch (NumberFormatException e) {
                AlertManager.showWarning("Warning", "Please enter a valid price");
                return;
            }
        }


        FeatureService featureService = new FeatureServiceImpl();
        List<Feature> selectedFeatures = tvFeatures.getSelectionModel().getSelectedItems();
        for (Feature feature : selectedFeatures) {

            long featureId = feature.getId();

            if (featureName != null){
                try {
                    featureService.changeFeatureName(featureId, featureName);
                } catch (EntityNotFoundByIdException e) {
                    AlertManager.showError("Error", e.getMessage());
                    return;
                }
            }

            if (servicePrice != 0L) {
                try{
                    featureService.changePrice(featureId, (int) servicePrice);
                } catch (EntityNotFoundByIdException e) {
                    AlertManager.showError("Error", e.getMessage());
                    return;
                }
            }
        }

        setTableView(featureService.getAllFeatures());
        lblUpdateFeedback.setText("Updating process successful");

    }


    @FXML
    public void onButtonDeleteClicked() {

        if (tvFeatures.getSelectionModel().getSelectedItem() == null) {
            AlertManager.showWarning("Warning", "Please select at least one feature to delete");
            return;
        }


        FeatureService featureService = new FeatureServiceImpl();
        List<Feature> selectedFeatures = tvFeatures.getSelectionModel().getSelectedItems();
        for (Feature feature : selectedFeatures) {

            long featureId = feature.getId();

            try{
                featureService.deleteFeature(featureId);
            } catch (EntityNotFoundByIdException e) {
                AlertManager.showError("Error", e.getMessage());
                return;
            }
        }

        setTableView(featureService.getAllFeatures());
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

        lblInformation.setText("Add New Feature");
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

        lblInformation.setText("Update Selected Features");
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

        lblInformation.setText("Delete Selected Features");
        setEmptyAllFeedbackFields();
    }

    private void setInformationFieldsVisibility(boolean visible) {
        tfFeatureName.setVisible(visible);
        tfFeaturePrice.setVisible(visible);

        lblInformationFeatureName.setVisible(visible);
        lblInformationFeaturePrice.setVisible(visible);
    }

    private void setEmptyAllInformationFields() {
        tfFeatureName.setText("");
        tfFeaturePrice.setText("");
    }

    private void setEmptyAllFeedbackFields() {
        lblAddFeedback.setText("");
        lblUpdateFeedback.setText("");
        lblDeleteFeedback.setText("");
    }


}
