package com.example.hatoslotto;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class ForexHistoricalController {

    @FXML
    private ComboBox<String> currencyPairComboBox;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private TableView<Candle> candlesTable;
    @FXML
    private TableColumn<Candle, String> dateCol;
    @FXML
    private TableColumn<Candle, Double> openCol;
    @FXML
    private TableColumn<Candle, Double> closeCol;
    @FXML
    private TableColumn<Candle, Double> lowCol;
    @FXML
    private TableColumn<Candle, Double> highCol;
    @FXML
    private LineChart<String, Number> lineChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    private static final String API_TOKEN = "186560cdecdd5a95fbb9ef4bcc0dec78-f845800240a7ec379f915fcf1cb83d1e";
    private static final String BASE_URL = "https://api-fxpractice.oanda.com/v3/instruments";

    private final ObservableList<Candle> candleData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Devizapárok feltöltése a lenyíló listába
        List<String> currencyPairs = Arrays.asList("EUR_USD", "GBP_USD", "USD_JPY", "AUD_USD", "USD_CAD");
        currencyPairComboBox.getItems().addAll(currencyPairs);

        // Táblázat oszlopainak beállítása
        dateCol.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        openCol.setCellValueFactory(cellData -> cellData.getValue().openProperty().asObject());
        closeCol.setCellValueFactory(cellData -> cellData.getValue().closeProperty().asObject());
        lowCol.setCellValueFactory(cellData -> cellData.getValue().lowProperty().asObject());
        highCol.setCellValueFactory(cellData -> cellData.getValue().highProperty().asObject());

        candlesTable.setItems(candleData);
    }

    @FXML
    protected void onLoadDataClick() {
        String selectedPair = currencyPairComboBox.getValue();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        if (selectedPair == null || startDate == null || endDate == null) {
            showAlert("Kérlek, válassz devizapárt és dátumokat!");
            return;
        }

        try {
            fetchHistoricalData(selectedPair, startDate, endDate);
        } catch (Exception e) {
            showAlert("Hiba az adatok betöltése során: " + e.getMessage());
        }
    }

    private void fetchHistoricalData(String currencyPair, LocalDate startDate, LocalDate endDate) throws Exception {
        OkHttpClient client = new OkHttpClient();

        // A LocalDate átalakítása ISO8601 formátumra (UTC időzóna hozzáadása)
        String from = startDate.atStartOfDay().atZone(java.time.ZoneId.systemDefault()).toInstant().toString();
        String to = endDate.atStartOfDay().atZone(java.time.ZoneId.systemDefault()).toInstant().toString();

        String url = BASE_URL + "/" + currencyPair + "/candles"
                + "?from=" + from
                + "&to=" + to
                + "&granularity=D";

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + API_TOKEN)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                JsonArray candles = JsonParser.parseString(response.body().string())
                        .getAsJsonObject()
                        .getAsJsonArray("candles");

                candleData.clear();
                lineChart.getData().clear();
                XYChart.Series<String, Number> series = new XYChart.Series<>();
                series.setName(currencyPair);

                candles.forEach(candle -> {
                    JsonObject candleObj = candle.getAsJsonObject();
                    String time = candleObj.get("time").getAsString();
                    double open = candleObj.getAsJsonObject("mid").get("o").getAsDouble();
                    double close = candleObj.getAsJsonObject("mid").get("c").getAsDouble();
                    double low = candleObj.getAsJsonObject("mid").get("l").getAsDouble();
                    double high = candleObj.getAsJsonObject("mid").get("h").getAsDouble();

                    candleData.add(new Candle(time, open, close, low, high));
                    series.getData().add(new XYChart.Data<>(time, close));
                });

                lineChart.getData().add(series);
            } else {
                throw new Exception("HTTP Hiba: " + response.code());
            }
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }
}
