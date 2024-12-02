package com.example.hatoslotto;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DatabaseUtil {
    private static final String URL = "jdbc:sqlite:" + DatabaseUtil.class.getResource("/db/hatoslotto.db").getPath();

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Kapcsolat létrejött az adatbázissal!");
        } catch (SQLException e) {
            System.out.println("Hiba az adatbázis kapcsolódás során: " + e.getMessage());
        }
        return conn;
    }

    public static ObservableList<Huzas> getHuzasRecords() {
        ObservableList<Huzas> records = FXCollections.observableArrayList();
        String query = "SELECT id, ev, het FROM huzas LIMIT 100";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                records.add(new Huzas(rs.getInt("id"), rs.getInt("ev"), rs.getInt("het")));
            }
        } catch (Exception e) {
            System.out.println("Hiba a Huzas tábla lekérdezése során: " + e.getMessage());
        }
        return records;
    }

    public static ObservableList<Huzott> getHuzottRecords() {
        ObservableList<Huzott> records = FXCollections.observableArrayList();
        String query = "SELECT id, huzasid, szam FROM huzott LIMIT 100";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                records.add(new Huzott(rs.getInt("id"), rs.getInt("huzasid"), rs.getInt("szam")));
            }
        } catch (Exception e) {
            System.out.println("Hiba a Huzott tábla lekérdezése során: " + e.getMessage());
        }
        return records;
    }

    public static ObservableList<Nyeremeny> getNyeremenyRecords() {
        ObservableList<Nyeremeny> records = FXCollections.observableArrayList();
        String query = "SELECT id, huzasid, talalat, darab, ertek FROM nyeremeny LIMIT 100";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                records.add(new Nyeremeny(rs.getInt("id"), rs.getInt("huzasid"), rs.getInt("talalat"),
                        rs.getInt("darab"), rs.getInt("ertek")));
            }
        } catch (Exception e) {
            System.out.println("Hiba a Nyeremeny tábla lekérdezése során: " + e.getMessage());
        }
        return records;
    }


}
