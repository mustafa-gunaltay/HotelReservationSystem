package com.mustafa.hotelreservationsystem;

import com.mustafa.hotelreservationsystem.dao.DaoTests;
import com.mustafa.hotelreservationsystem.services.ServiceTests;
import com.mustafa.hotelreservationsystem.ui.utils.SceneManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/mustafa/hotelreservationsystem/ui/controllers/LoginPage.fxml"));
//        Scene scene = new Scene(fxmlLoader.load());
//        stage.setTitle("Login Page");
//        stage.setScene(scene);
//        stage.show();

        SceneManager.setStage(stage);
        SceneManager.switchScene("/com/mustafa/hotelreservationsystem/ui/controllers/LoginPage.fxml");

//        DaoTests.featureDaoImplUnitTest();
//        DaoTests.adminDaoImplUnitTest();
//        DaoTests.receptionistDaoImplUnitTest();
//        DaoTests.roomDaoImplUnitTest();
//        DaoTests.reservationDaoImplUnitTest();
//        DaoTests.customerDaoImplUnitTest();
//        DaoTests.serviceDaoImplUnitTest();
        
//        ServiceTests.adminServiceImplUnitTest();
//        ServiceTests.receptionistServiceImplUnitTest();
//        ServiceTests.featureServiceImplUnitTest();
//        ServiceTests.reservationServiceImplUnitTest();
//        ServiceTests.roomServiceImplUnitTest();
//        ServiceTests.customerServiceImplUnitTest();
//        ServiceTests.serviceServiceImplUnitTest();
    }

    public static void main(String[] args) {
        launch();
    }
}