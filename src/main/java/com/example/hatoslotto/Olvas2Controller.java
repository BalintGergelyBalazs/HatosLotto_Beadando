package com.example.hatoslotto;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.stream.Collectors;

public class Olvas2Controller {

    @FXML
    private TextField szamField;

    @FXML
    private ComboBox<Integer> huzasIdCombo;

    @FXML
    private RadioButton opcio1Radio;

    @FXML
    private RadioButton opcio2Radio;

    @FXML
    private TableView<Huzas> huzasTable;

    @FXML
    private TableColumn<Huzas, Integer> huzasIdColumn;

    @FXML
    private TableColumn<Huzas, Integer> huzasEvColumn;

    @FXML
    private TableColumn<Huzas, Integer> huzasHetColumn;

    @FXML
    private TableView<Huzott> huzottTable;

    @FXML
    private TableColumn<Huzott, Integer> huzottIdColumn;

    @FXML
    private TableColumn<Huzott, Integer> huzottHuzasIdColumn;

    @FXML
    private TableColumn<Huzott, Integer> huzottSzamColumn;

    @FXML
    private TableView<Nyeremeny> nyeremenyTable;

    @FXML
    private TableColumn<Nyeremeny, Integer> nyeremenyIdColumn;

    @FXML
    private TableColumn<Nyeremeny, Integer> nyeremenyHuzasIdColumn;

    @FXML
    private TableColumn<Nyeremeny, Integer> nyeremenyTalalatColumn;

    @FXML
    private TableColumn<Nyeremeny, Integer> nyeremenyDarabColumn;

    @FXML
    private TableColumn<Nyeremeny, Integer> nyeremenyErtekColumn;

    private ObservableList<Huzas> allHuzasRecords = FXCollections.observableArrayList();
    private ObservableList<Huzott> allHuzottRecords = FXCollections.observableArrayList();
    private ObservableList<Nyeremeny> allNyeremenyRecords = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Beállítjuk a táblázat oszlopait
        huzasIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        huzasEvColumn.setCellValueFactory(new PropertyValueFactory<>("ev"));
        huzasHetColumn.setCellValueFactory(new PropertyValueFactory<>("het"));

        huzottIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        huzottHuzasIdColumn.setCellValueFactory(new PropertyValueFactory<>("huzasId"));
        huzottSzamColumn.setCellValueFactory(new PropertyValueFactory<>("szam"));

        nyeremenyIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nyeremenyHuzasIdColumn.setCellValueFactory(new PropertyValueFactory<>("huzasId"));
        nyeremenyTalalatColumn.setCellValueFactory(new PropertyValueFactory<>("talalat"));
        nyeremenyDarabColumn.setCellValueFactory(new PropertyValueFactory<>("darab"));
        nyeremenyErtekColumn.setCellValueFactory(new PropertyValueFactory<>("ertek"));

        // Betöltjük az összes adatot
        allHuzasRecords.addAll(DatabaseUtil.getHuzasRecords());
        allHuzottRecords.addAll(DatabaseUtil.getHuzottRecords());
        allNyeremenyRecords.addAll(DatabaseUtil.getNyeremenyRecords());

        // Alapértelmezett megjelenítés
        huzasTable.setItems(allHuzasRecords);
        huzottTable.setItems(allHuzottRecords);
        nyeremenyTable.setItems(allNyeremenyRecords);

        // Töltjük a lenyíló listát
        huzasIdCombo.setItems(FXCollections.observableArrayList(
                allHuzasRecords.stream().map(Huzas::getId).distinct().collect(Collectors.toList())
        ));
    }

    @FXML
    protected void onFilterButtonClick() {
        ObservableList<Huzas> filteredHuzas = FXCollections.observableArrayList(allHuzasRecords);
        ObservableList<Huzott> filteredHuzott = FXCollections.observableArrayList(allHuzottRecords);
        ObservableList<Nyeremeny> filteredNyeremeny = FXCollections.observableArrayList(allNyeremenyRecords);

        // Szám szűrés
        String szamInput = szamField.getText().trim();
        if (!szamInput.isEmpty()) {
            try {
                int szam = Integer.parseInt(szamInput);
                filteredHuzott.retainAll(filteredHuzott.stream()
                        .filter(record -> record.getSzam() == szam)
                        .toList());
            } catch (NumberFormatException e) {
                System.out.println("Hibás számformátum: " + szamInput);
            }
        }

        // Lenyíló lista szűrés
        Integer selectedHuzasId = huzasIdCombo.getValue();
        if (selectedHuzasId != null) {
            filteredHuzas.retainAll(filteredHuzas.stream()
                    .filter(record -> record.getId() == selectedHuzasId)
                    .toList());
            filteredHuzott.retainAll(filteredHuzott.stream()
                    .filter(record -> record.getHuzasId() == selectedHuzasId)
                    .toList());
            filteredNyeremeny.retainAll(filteredNyeremeny.stream()
                    .filter(record -> record.getHuzasId() == selectedHuzasId)
                    .toList());
        }

        // Radio gomb szűrés
        if (opcio1Radio.isSelected()) {
            filteredHuzas.retainAll(filteredHuzas.stream()
                    .filter(record -> record.getId() % 2 == 0)
                    .toList());
        } else if (opcio2Radio.isSelected()) {
            filteredHuzas.retainAll(filteredHuzas.stream()
                    .filter(record -> record.getId() % 2 != 0)
                    .toList());
        }

        // Frissített adatok megjelenítése
        huzasTable.setItems(filteredHuzas);
        huzottTable.setItems(filteredHuzott);
        nyeremenyTable.setItems(filteredNyeremeny);
    }

    @FXML
    protected void onClearFiltersButtonClick() {
        // Szűrések törlése
        szamField.clear();
        huzasIdCombo.getSelectionModel().clearSelection();
        opcio1Radio.setSelected(false);
        opcio2Radio.setSelected(false);

        // Alapértelmezett adatok visszaállítása
        huzasTable.setItems(allHuzasRecords);
        huzottTable.setItems(allHuzottRecords);
        nyeremenyTable.setItems(allNyeremenyRecords);
    }
}
