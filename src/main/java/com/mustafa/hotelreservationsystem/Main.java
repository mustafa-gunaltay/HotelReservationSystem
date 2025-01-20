package com.mustafa.hotelreservationsystem;

import com.mustafa.hotelreservationsystem.dao.DaoTests;
import com.mustafa.hotelreservationsystem.services.ServiceTests;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

//        DaoTests.featureDaoImplUnitTest();
//        DaoTests.adminDaoImplUnitTest();
//        DaoTests.receptionistDaoImplUnitTest();

//        ServiceTests.adminServiceImplTest();
    }

    public static void main(String[] args) {
        launch();
    }
}