package com.mustafa.hotelreservationsystem.ui.utils;

import javafx.scene.Node;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

public class Utils {


    public static <T> void setupTableViewSelection(TableView<T> tableView) {
        // Çoklu seçim modunu aktifleştir
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // CTRL tuşuna basmadan çoklu seçim yapılmasını sağla
        tableView.addEventFilter(MouseEvent.MOUSE_PRESSED, evt -> {
            Node node = evt.getPickResult().getIntersectedNode();

            // TableRow bulunana kadar yukarı çık
            while (node != null && node != tableView && !(node instanceof TableRow)) {
                node = node.getParent();
            }

            if (node instanceof TableRow<?> row) {
                evt.consume(); // Varsayılan işleme izin verme
                tableView.requestFocus();

                if (!row.isEmpty()) {
                    int index = row.getIndex();
                    if (row.isSelected()) {
                        tableView.getSelectionModel().clearSelection(index);
                    } else {
                        tableView.getSelectionModel().select(index);
                    }
                }
            }
        });
    }
}
