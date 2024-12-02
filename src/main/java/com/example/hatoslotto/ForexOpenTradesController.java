package com.example.hatoslotto;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ForexOpenTradesController {

    @FXML
    private TableView<Trade> openTradesTable;
    @FXML
    private TableColumn<Trade, String> tradeIdCol;
    @FXML
    private TableColumn<Trade, String> instrumentCol;
    @FXML
    private TableColumn<Trade, Integer> unitsCol;
    @FXML
    private TableColumn<Trade, Double> priceCol;

    private static final String API_TOKEN = "186560cdecdd5a95fbb9ef4bcc0dec78-f845800240a7ec379f915fcf1cb83d1e";
    private static final String ACCOUNT_ID = "101-004-30484113-001";
    private static final String BASE_URL = "https://api-fxpractice.oanda.com/v3/accounts/" + ACCOUNT_ID + "/openTrades";

    private final ObservableList<Trade> tradesData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        tradeIdCol.setCellValueFactory(new PropertyValueFactory<>("tradeId"));
        instrumentCol.setCellValueFactory(new PropertyValueFactory<>("instrument"));
        unitsCol.setCellValueFactory(new PropertyValueFactory<>("units"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        openTradesTable.setItems(tradesData);
    }

    @FXML
    protected void onRefreshClick() {
        try {
            fetchOpenTrades();
        } catch (Exception e) {
            System.out.println("Hiba a nyitott pozíciók lekérése során: " + e.getMessage());
        }
    }

    private void fetchOpenTrades() throws Exception {
        // Szimulált adatok hétvégi teszteléshez
        if (isWeekend()) {
            tradesData.clear();
            tradesData.add(new Trade("1", "EUR_USD", 1000, 1.1234));
            tradesData.add(new Trade("2", "GBP_USD", -500, 1.2345));
            tradesData.add(new Trade("3", "USD_JPY", 2000, 110.67));
            System.out.println("Szimulált adatok betöltve.");
            return;
        }

        // Valós adatok API hívással
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(BASE_URL)
                .addHeader("Authorization", "Bearer " + API_TOKEN)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            System.out.println("Nyitott pozíciók API válasz: " + responseBody);

            if (response.isSuccessful()) {
                JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();
                JsonArray trades = jsonResponse.getAsJsonArray("trades");

                tradesData.clear();
                trades.forEach(tradeElement -> {
                    JsonObject trade = tradeElement.getAsJsonObject();
                    String tradeId = trade.get("id").getAsString();
                    String instrument = trade.get("instrument").getAsString();
                    int units = trade.get("currentUnits").getAsInt();
                    double price = trade.get("price").getAsDouble();

                    tradesData.add(new Trade(tradeId, instrument, units, price));
                });
            } else {
                throw new Exception("HTTP Hiba: " + response.code());
            }
        }
    }

    // Hétvége ellenőrzése
    private boolean isWeekend() {
        java.time.DayOfWeek today = java.time.LocalDate.now().getDayOfWeek();
        return today == java.time.DayOfWeek.SATURDAY || today == java.time.DayOfWeek.SUNDAY;
    }



}
