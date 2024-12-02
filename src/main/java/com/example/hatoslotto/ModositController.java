package com.example.hatoslotto;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ModositController {

    @FXML
    private ComboBox<Huzott> recordComboBox;

    @FXML
    private TextField huzasIdField;

    @FXML
    private TextField szamField;

    @FXML
    private Label statusLabel;

    private ObservableList<Huzott> records = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadRecords();

        // Ha kiválasztanak egy rekordot, töltsük be az értékeket az űrlap mezőibe
        recordComboBox.setOnAction(event -> {
            Huzott selectedRecord = recordComboBox.getValue();
            if (selectedRecord != null) {
                huzasIdField.setText(String.valueOf(selectedRecord.getHuzasId()));
                szamField.setText(String.valueOf(selectedRecord.getSzam()));
            }
        });
    }

    private void loadRecords() {
        records.clear();
        String query = "SELECT id, huzasid, szam FROM huzott";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                records.add(new Huzott(rs.getInt("id"), rs.getInt("huzasid"), rs.getInt("szam")));
            }
            recordComboBox.setItems(records);

        } catch (Exception e) {
            System.out.println("Hiba a rekordok betöltése során: " + e.getMessage());
        }
    }


    @FXML
    protected void onModifyRecordButtonClick() {
        Huzott selectedRecord = recordComboBox.getValue();
        if (selectedRecord == null) {
            statusLabel.setText("Válassz egy rekordot!");
            return;
        }

        try {
            int newHuzasId = Integer.parseInt(huzasIdField.getText());
            int newSzam = Integer.parseInt(szamField.getText());

            // Rekord frissítése az adatbázisban
            if (updateRecordInDatabase(selectedRecord.getId(), newHuzasId, newSzam)) {
                statusLabel.setText("Rekord sikeresen módosítva!");

                // Töröljük a kiválasztást és az űrlap mezőit
                recordComboBox.getSelectionModel().clearSelection();
                huzasIdField.clear();
                szamField.clear();

                // Frissítjük a lenyíló lista tartalmát
                loadRecords();
            } else {
                statusLabel.setText("Hiba a rekord módosítása során.");
            }
        } catch (NumberFormatException e) {
            statusLabel.setText("Húzás ID és Szám mező csak számokat tartalmazhat!");
        }
    }

    private boolean updateRecordInDatabase(int id, int newHuzasId, int newSzam) {
        String updateQuery = "UPDATE huzott SET huzasid = ?, szam = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {

            pstmt.setInt(1, newHuzasId);
            pstmt.setInt(2, newSzam);
            pstmt.setInt(3, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (Exception e) {
            System.out.println("Hiba a rekord módosítása során: " + e.getMessage());
            return false;
        }
    }
}
