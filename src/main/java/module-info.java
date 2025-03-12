module com.mustafa.hotelreservationsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.management;

    opens com.mustafa.hotelreservationsystem to javafx.fxml;
    exports com.mustafa.hotelreservationsystem;

    //exports com.mustafa.hotelreservationsystem.ui;
    //opens com.mustafa.hotelreservationsystem.ui to javafx.fxml;

    exports com.mustafa.hotelreservationsystem.ui.controllers;
    opens com.mustafa.hotelreservationsystem.ui.controllers to javafx.fxml;

    opens com.mustafa.hotelreservationsystem.models;

}

//module com.mustafa.hotelreservationsystem {
//        requires javafx.controls;
//        requires javafx.fxml;
//        requires java.sql;
//        //requires jdk.incubator.vector;
//
//        // FXML'e erişim için sadece gerekli paketleri açıyoruz.
//        opens com.mustafa.hotelreservationsystem to javafx.fxml; // Ana paket açıldı
//        opens com.mustafa.hotelreservationsystem.models to javafx.fxml; // Model paketini de açtık
//
//        // UI Controllers'ı açıyoruz çünkü JavaFX UI sınıflarına ihtiyaç duyuyor
//        opens com.mustafa.hotelreservationsystem.ui.controllers to javafx.fxml;
//
//        exports com.mustafa.hotelreservationsystem;
//        exports com.mustafa.hotelreservationsystem.ui.controllers;
//        exports com.mustafa.hotelreservationsystem.models; // Eğer dışa açmak istiyorsanız burayı açabilirsiniz
//        }

