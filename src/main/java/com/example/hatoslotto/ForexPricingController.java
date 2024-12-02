package com.example.hatoslotto;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Arrays;
import java.util.List;

public class ForexPricingController {

    @FXML
    private ComboBox<String> currencyPairComboBox;

    @FXML
    private Label priceLabel;

    private static final String API_TOKEN = "186560cdecdd5a95fbb9ef4bcc0dec78-f845800240a7ec379f915fcf1cb83d1e";
    private static final String ACCOUNT_ID = "101-004-30484113-001";
    private static final String BASE_URL = "https://api-fxpractice.oanda.com/v3/accounts/" + ACCOUNT_ID + "/pricing";

    @FXML
    public void initialize() {
        // Devizapárok feltöltése a lenyíló listába
        List<String> currencyPairs = Arrays.asList("EUR_USD", "GBP_USD", "USD_JPY", "AUD_USD", "USD_CAD");
        currencyPairComboBox.getItems().addAll(currencyPairs);
    }

    @FXML
    protected void onShowPriceClick() {
        String selectedPair = currencyPairComboBox.getValue();
        if (selectedPair == null || selectedPair.isEmpty()) {
            priceLabel.setText("Kérlek, válassz devizapárt!");
            return;
        }

        // Árfolyam lekérése az API-ból
        try {
            String price = fetchCurrentPrice(selectedPair);
            if (price != null) {
                priceLabel.setText("Ár: " + price);
            } else {
                priceLabel.setText("Nem érhető el az árfolyam!");
            }
        } catch (Exception e) {
            priceLabel.setText("Hiba az árfolyam lekérése során!");
            System.out.println("Hiba: " + e.getMessage());
        }
    }

    private String fetchCurrentPrice(String currencyPair) throws Exception {
        OkHttpClient client = new OkHttpClient();

        String url = BASE_URL + "?instruments=" + currencyPair;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + API_TOKEN)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                JsonObject jsonObject = JsonParser.parseString(response.body().string()).getAsJsonObject();
                if (jsonObject.has("prices")) {
                    JsonObject priceObject = jsonObject.getAsJsonArray("prices").get(0).getAsJsonObject();
                    return priceObject.get("bids").getAsJsonArray().get(0).getAsJsonObject().get("price").getAsString();
                }
            } else {
                System.out.println("HTTP hiba: " + response.code());
            }
        } catch (Exception e) {
            System.out.println("Hiba az API hívás során: " + e.getMessage());
        }

        return null;
    }
}
