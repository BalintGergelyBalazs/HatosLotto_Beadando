package com.example.hatoslotto;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class HelloController {

    @FXML
    protected void onOlvasMenuClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("olvas-menu.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            Stage stage = new Stage();
            stage.setTitle("Olvas Almenü");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Hiba az Olvas almenü megnyitásakor: " + e.getMessage());
        }
    }

    @FXML
    protected void onOlvas2MenuClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("olvas2-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            Stage stage = new Stage();
            stage.setTitle("Olvas2 (Szűrés) almenü");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Hiba az Olvas2 almenü megnyitásakor: " + e.getMessage());
        }
    }

    @FXML
    protected void onIrMenuClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ir-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 400, 300);
            Stage stage = new Stage();
            stage.setTitle("Ír almenü");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Hiba az Ír almenü megnyitásakor: " + e.getMessage());
        }
    }

    @FXML
    protected void onModositMenuClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("modosit-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 400, 300);
            Stage stage = new Stage();
            stage.setTitle("Módosít almenü");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Hiba a Módosít almenü megnyitásakor: " + e.getMessage());
        }
    }

    @FXML
    protected void onTorolMenuClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("torol-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 400, 300);
            Stage stage = new Stage();
            stage.setTitle("Töröl almenü");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Hiba a Töröl almenü megnyitásakor: " + e.getMessage());
        }
    }

    @FXML
    protected void onAboutMenuClick() {
        Stage stage = new Stage();
        stage.setTitle("Névjegy");
        stage.setScene(new Scene(new Label("HatosLotto alkalmazás\nKészítette: [Név]\n2024")));
        stage.show();
    }

    @FXML
    protected void onParhuzamosMenuClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("parhuzamos-menu.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 400, 300);
            Stage stage = new Stage();
            stage.setTitle("Párhuzamos Almenü");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Hiba a Párhuzamos almenü megnyitásakor: " + e.getMessage());
        }
    }

    @FXML
    protected void onForexAccountsMenuClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("forex-accounts-menu.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            Stage stage = new Stage();
            stage.setTitle("Forex Számlainformációk");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Hiba a Forex számlainformációk megnyitásakor: " + e.getMessage());
        }
    }

    @FXML
    protected void onForexPricingMenuClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("forex-pricing-menu.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 400, 300);
            Stage stage = new Stage();
            stage.setTitle("Forex Aktuális Árfolyamok");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Hiba az Aktuális árak almenü megnyitásakor: " + e.getMessage());
        }
    }

    @FXML
    protected void onForexHistoricalMenuClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("forex-historical-menu.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            Stage stage = new Stage();
            stage.setTitle("Forex Historikus Árfolyamok");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Hiba a Historikus árak almenü megnyitásakor: " + e.getMessage());
        }
    }

    @FXML
    protected void onForexPositionMenuClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("forex-position-menu.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 400, 300);
            Stage stage = new Stage();
            stage.setTitle("Pozíció Nyitása");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Hiba a Pozíció Nyitás almenü megnyitásakor: " + e.getMessage());
        }
    }

    @FXML
    protected void onForexCloseTradeMenuClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("forex-close-trade-menu.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 400, 200);
            Stage stage = new Stage();
            stage.setTitle("Pozíció Zárása");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Hiba a Pozíció Zárás almenü megnyitásakor: " + e.getMessage());
        }
    }

    @FXML
    protected void onForexOpenTradesMenuClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("forex-open-trades-menu.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Nyitott Pozíciók");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Hiba a Nyitott Pozíciók almenü megnyitásakor: " + e.getMessage());
        }
    }

}
