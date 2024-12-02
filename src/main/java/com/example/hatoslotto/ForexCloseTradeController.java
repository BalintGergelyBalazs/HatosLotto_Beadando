package com.example.hatoslotto;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import okhttp3.*;

public class ForexCloseTradeController {

    @FXML
    private TextField tradeIdTextField;
    @FXML
    private Label resultLabel;

    private static final String API_TOKEN = "186560cdecdd5a95fbb9ef4bcc0dec78-f845800240a7ec379f915fcf1cb83d1e";
    private static final String ACCOUNT_ID = "101-004-30484113-001";
    private static final String BASE_URL = "https://api-fxpractice.oanda.com/v3/accounts/" + ACCOUNT_ID + "/trades/";

    @FXML
    protected void onCloseTradeClick() {
        String tradeId = tradeIdTextField.getText();

        if (tradeId == null || tradeId.isEmpty()) {
            resultLabel.setText("Kérlek, add meg a pozíció ID-ját!");
            return;
        }

        try {
            String result = closeTrade(tradeId);
            resultLabel.setText(result);
        } catch (Exception e) {
            resultLabel.setText("Hiba a pozíció zárása során: " + e.getMessage());
        }
    }

    private String closeTrade(String tradeId) throws Exception {
        OkHttpClient client = new OkHttpClient();

        String url = BASE_URL + tradeId + "/close";

        Request request = new Request.Builder()
                .url(url)
                .put(RequestBody.create("", MediaType.get("application/json")))
                .addHeader("Authorization", "Bearer " + API_TOKEN)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            System.out.println("API válasz: " + responseBody);

            if (response.isSuccessful()) {
                JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();
                return "Pozíció sikeresen lezárva! ID: " + tradeId;
            } else {
                JsonObject errorResponse = JsonParser.parseString(responseBody).getAsJsonObject();
                String errorMessage = errorResponse.has("errorMessage") ? errorResponse.get("errorMessage").getAsString() : "Ismeretlen hiba";
                String errorCode = errorResponse.has("errorCode") ? errorResponse.get("errorCode").getAsString() : "Nincs kód";
                throw new Exception("API Hiba: " + errorMessage + " (" + errorCode + ")");
            }
        }
    }
}
