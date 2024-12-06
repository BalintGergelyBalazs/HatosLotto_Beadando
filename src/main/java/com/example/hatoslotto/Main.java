package com.example.hatoslotto;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Betöltjük az FXML fájlt (hello-view.fxml)
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("HatosLotto - Főmenü");
        stage.setScene(scene);
        stage.show();

        // Teszteljük az adatbázis kapcsolatot
        try (Connection conn = DatabaseUtil.connect()) {
            if (conn != null) {
                System.out.println("Sikeres kapcsolat az adatbázissal!");
            }
        } catch (Exception e) {
            System.out.println("Adatbázis kapcsolat sikertelen: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
