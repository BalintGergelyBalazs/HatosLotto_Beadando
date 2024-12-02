package com.example.hatoslotto;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class IrController {

    @FXML
    private TextField huzasIdField;

    @FXML
    private TextField szamField;

    @FXML
    private Label statusLabel;

    @FXML
    protected void onAddRecordButtonClick() {
        // Ellenőrizzük, hogy a mezők nincsenek üresen
        if (huzasIdField.getText().isEmpty() || szamField.getText().isEmpty()) {
            statusLabel.setText("Minden mezőt ki kell tölteni!");
            return;
        }

        try {
            int huzasId = Integer.parseInt(huzasIdField.getText());
            int szam = Integer.parseInt(szamField.getText());

            // Új rekord hozzáadása az adatbázishoz
            if (addRecordToDatabase(huzasId, szam)) {
                // Ellenőrizzük, hogy bekerült-e az adat
                if (isRecordInDatabase(huzasId, szam)) {
                    statusLabel.setText("Sikeresen hozzáadva! Húzás ID: " + huzasId + ", Szám: " + szam);
                } else {
                    statusLabel.setText("Hiba az ellenőrzés során: Nem található az adatbázisban!");
                }
                huzasIdField.clear();
                szamField.clear();
            } else {
                statusLabel.setText("Hiba a rekord hozzáadása során.");
            }
        } catch (NumberFormatException e) {
            statusLabel.setText("Húzás ID és Szám mező csak számokat tartalmazhat!");
        }
    }

    private boolean addRecordToDatabase(int huzasId, int szam) {
        String insertQuery = "INSERT INTO huzott (huzasid, szam) VALUES (?, ?)";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
            pstmt.setInt(1, huzasId);
            pstmt.setInt(2, szam);
            pstmt.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Hiba az adatbázisművelet során: " + e.getMessage());
            return false;
        }
    }

    private boolean isRecordInDatabase(int huzasId, int szam) {
        String selectQuery = "SELECT COUNT(*) AS count FROM huzott WHERE huzasid = ? AND szam = ?";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement pstmt = conn.prepareStatement(selectQuery)) {
            pstmt.setInt(1, huzasId);
            pstmt.setInt(2, szam);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count") > 0;
            }
        } catch (Exception e) {
            System.out.println("Hiba az ellenőrzés során: " + e.getMessage());
        }
        return false;
    }
}
