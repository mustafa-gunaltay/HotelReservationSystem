package com.mustafa.hotelreservationsystem.ui.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/*
   Main sinifimiz icinde en basta SceneManager.setStage(stage) dedigimiz icin ve ardindan
   SceneManager sinifina ait statik switchScene() metotlarini kullandigimiz icin proje boyunca sadece 1 tane stage'imiz olacak
   ve o stage'in icinde sadece sahneler(scene) degisecek
   (sahne initialize edilerek veya edilmeyerek degisecek - hangi switchScene() metodunun cagrildigina bagli olarak)
*/
public class SceneManager {
    private static Stage mainStage;

    public static void setStage(Stage stage) {
        mainStage = stage;
    }

    // Bu metot verilen fxml'i acmadan once o fxml'in controller sinifinin ustundeki metotlarindan istenenleri cagirarak
    // o fxml'i initialize etmemizi saglar
    public static <T> void switchScene(String fxmlFile, SceneInitializer<T> initializer){
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlFile));

        Parent root = null;
        try {
            root = loader.load();
        }
        catch (IOException e) {
            System.out.println("IOException has been thrown, message = " + e.getMessage());
//            e.printStackTrace();
            return;
        }

        // Controller'ı al ve parametreyi işle
        T controller = loader.getController();
        initializer.initialize(controller);
        /*
         * bu kisimda verilen fxml'e ait loader'in yukledigi controller ile switchScene() metodunu kullanirken
         * icine labmda olarak yazdigimiz controller'in tipi uyusmassa ClassCastException alinir
         * Örneğin, Scene2.fxml dosyasının controller'ı Scene2Controller ise, lambda ifadesinde de Scene2Controller tipi kullanılmalıdır.
         * */


        mainStage.setScene(new Scene(root));
        mainStage.show();

    }


    public static void switchScene(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlFile));
        Parent root = loader.load();

        mainStage.setScene(new Scene(root));
        mainStage.show();
    }
}

