package com.example.hatoslotto;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class OlvasController {

    @FXML
    private TableView<Huzas> huzasTable;
    @FXML
    private TableColumn<Huzas, Integer> huzasIdCol;
    @FXML
    private TableColumn<Huzas, Integer> huzasEvCol;
    @FXML
    private TableColumn<Huzas, Integer> huzasHetCol;

    @FXML
    private TableView<Huzott> huzottTable;
    @FXML
    private TableColumn<Huzott, Integer> huzottIdCol;
    @FXML
    private TableColumn<Huzott, Integer> huzottHuzasIdCol;
    @FXML
    private TableColumn<Huzott, Integer> huzottSzamCol;

    @FXML
    private TableView<Nyeremeny> nyeremenyTable;
    @FXML
    private TableColumn<Nyeremeny, Integer> nyeremenyIdCol;
    @FXML
    private TableColumn<Nyeremeny, Integer> nyeremenyHuzasIdCol;
    @FXML
    private TableColumn<Nyeremeny, Integer> nyeremenyTalalatCol;
    @FXML
    private TableColumn<Nyeremeny, Integer> nyeremenyDarabCol;
    @FXML
    private TableColumn<Nyeremeny, Integer> nyeremenyErtekCol;

    private ObservableList<Huzas> huzasRecords = FXCollections.observableArrayList();
    private ObservableList<Huzott> huzottRecords = FXCollections.observableArrayList();
    private ObservableList<Nyeremeny> nyeremenyRecords = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Huzas táblázat beállítása
        huzasIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        huzasEvCol.setCellValueFactory(new PropertyValueFactory<>("ev"));
        huzasHetCol.setCellValueFactory(new PropertyValueFactory<>("het"));
        huzasRecords.addAll(DatabaseUtil.getHuzasRecords());
        huzasTable.setItems(huzasRecords);

        // Huzott táblázat beállítása
        huzottIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        huzottHuzasIdCol.setCellValueFactory(new PropertyValueFactory<>("huzasId"));
        huzottSzamCol.setCellValueFactory(new PropertyValueFactory<>("szam"));
        huzottRecords.addAll(DatabaseUtil.getHuzottRecords());
        huzottTable.setItems(huzottRecords);

        // Nyeremeny táblázat beállítása
        nyeremenyIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nyeremenyHuzasIdCol.setCellValueFactory(new PropertyValueFactory<>("huzasId"));
        nyeremenyTalalatCol.setCellValueFactory(new PropertyValueFactory<>("talalat"));
        nyeremenyDarabCol.setCellValueFactory(new PropertyValueFactory<>("darab"));
        nyeremenyErtekCol.setCellValueFactory(new PropertyValueFactory<>("ertek"));
        nyeremenyRecords.addAll(DatabaseUtil.getNyeremenyRecords());
        nyeremenyTable.setItems(nyeremenyRecords);
    }
}
