package com.example.hatoslotto;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TorolController {

    @FXML
    private ComboBox<Huzott> recordComboBox;

    @FXML
    private Label statusLabel;

    private ObservableList<Huzott> records = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadRecords();
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
    protected void onDeleteRecordButtonClick() {
        Huzott selectedRecord = recordComboBox.getValue();
        if (selectedRecord == null) {
            statusLabel.setText("Válassz egy rekordot!");
            return;
        }

        // Rekord törlése az adatbázisból
        if (deleteRecordFromDatabase(selectedRecord.getId())) {
            statusLabel.setText("Rekord sikeresen törölve!");

            // Frissítjük a lenyíló lista tartalmát
            loadRecords();
        } else {
            statusLabel.setText("Hiba a rekord törlése során.");
        }
    }

    private boolean deleteRecordFromDatabase(int id) {
        String deleteQuery = "DELETE FROM huzott WHERE id = ?";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (Exception e) {
            System.out.println("Hiba a rekord törlése során: " + e.getMessage());
            return false;
        }
    }
}
