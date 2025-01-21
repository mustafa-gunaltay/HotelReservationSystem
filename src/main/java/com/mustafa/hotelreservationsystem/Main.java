package com.mustafa.hotelreservationsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/mustafa/hotelreservationsystem/ui/controllers/LoginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login Page");
        stage.setScene(scene);
        stage.show();

//        DaoTests.featureDaoImplUnitTest();
//        DaoTests.adminDaoImplUnitTest();
//        DaoTests.receptionistDaoImplUnitTest();

//        ServiceTests.adminServiceImplUnitTest();
//        ServiceTests.ReceptionistServiceImplUnitTest();
    }

    public static void main(String[] args) {
        launch();
    }
}