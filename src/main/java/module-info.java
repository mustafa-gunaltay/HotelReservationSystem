module com.mustafa.hotelreservationsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    //requires jdk.incubator.vector;

    opens com.mustafa.hotelreservationsystem to javafx.fxml;
    exports com.mustafa.hotelreservationsystem;

    //exports com.mustafa.hotelreservationsystem.ui;
    //opens com.mustafa.hotelreservationsystem.ui to javafx.fxml;

    exports com.mustafa.hotelreservationsystem.ui.controllers;
    opens com.mustafa.hotelreservationsystem.ui.controllers to javafx.fxml;
}
