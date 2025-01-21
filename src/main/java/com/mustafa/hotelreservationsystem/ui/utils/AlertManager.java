package com.mustafa.hotelreservationsystem.ui.utils;

import javafx.scene.control.Alert;

public class AlertManager {

    public static void showWarning(String title, String content) {
        showAlert(Alert.AlertType.WARNING, title, content);
    }

    public static void showError(String title, String content) {
        showAlert(Alert.AlertType.ERROR, title, content);
    }

    public static void showInfo(String title, String content) {
        showAlert(Alert.AlertType.INFORMATION, title, content);
    }

    public static void showConfirmation(String title, String content) {
        showAlert(Alert.AlertType.CONFIRMATION, title, content);
    }

    private static void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
