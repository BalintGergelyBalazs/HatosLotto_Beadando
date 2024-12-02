package com.example.hatoslotto;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import okhttp3.*;

public class ForexPositionController {

    @FXML
    private ComboBox<String> currencyPairComboBox;
    @FXML
    private TextField unitsTextField;
    @FXML
    private RadioButton buyRadioButton;
    @FXML
    private RadioButton sellRadioButton;
    @FXML
    private Label resultLabel;

    private ToggleGroup directionToggleGroup;

    private static final String API_TOKEN = "186560cdecdd5a95fbb9ef4bcc0dec78-f845800240a7ec379f915fcf1cb83d1e";
    private static final String ACCOUNT_ID = "101-004-30484113-001";
    private static final String BASE_URL = "https://api-fxpractice.oanda.com/v3/accounts/" + ACCOUNT_ID + "/orders";

    @FXML
    public void initialize() {
        // Devizapárok feltöltése a lenyíló listába
        currencyPairComboBox.getItems().addAll("EUR_USD", "GBP_USD", "USD_JPY", "AUD_USD", "USD_CAD");

        // ToggleGroup létrehozása és beállítása
        directionToggleGroup = new ToggleGroup();
        buyRadioButton.setToggleGroup(directionToggleGroup);
        sellRadioButton.setToggleGroup(directionToggleGroup);
    }

    @FXML
    protected void onOpenPositionClick() {
        String selectedPair = currencyPairComboBox.getValue();
        String unitsText = unitsTextField.getText();
        boolean isBuy = buyRadioButton.isSelected();
        boolean isSell = sellRadioButton.isSelected();

        if (selectedPair == null || unitsText == null || unitsText.isEmpty() || (!isBuy && !isSell)) {
            resultLabel.setText("Kérlek, töltsd ki az összes mezőt!");
            return;
        }

        try {
            int units = Integer.parseInt(unitsText);
            if (isSell) units = -units; // Negatív mennyiség eladás esetén

            String result = openPosition(selectedPair, units);
            resultLabel.setText(result);
        } catch (NumberFormatException e) {
            resultLabel.setText("A mennyiségnek számnak kell lennie!");
        } catch (Exception e) {
            resultLabel.setText("Hiba a pozíció nyitása során: " + e.getMessage());
        }
    }

    private String openPosition(String currencyPair, int units) throws Exception {
        OkHttpClient client = new OkHttpClient();

        JsonObject orderObject = new JsonObject();
        orderObject.addProperty("instrument", currencyPair);
        orderObject.addProperty("units", units);
        orderObject.addProperty("type", "MARKET");
        orderObject.addProperty("timeInForce", "FOK");

        JsonObject jsonBody = new JsonObject();
        jsonBody.add("order", orderObject);

        RequestBody body = RequestBody.create(
                jsonBody.toString(),
                MediaType.get("application/json")
        );

        Request request = new Request.Builder()
                .url(BASE_URL)
                .post(body)
                .addHeader("Authorization", "Bearer " + API_TOKEN)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            System.out.println("API válasz: " + responseBody); // Naplózza a válasz

            if (response.isSuccessful()) {
                JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();

                // Ellenőrizze, hogy van-e "orderCancelTransaction" és a "reason" értéke "MARKET_HALTED"
                if (jsonResponse.has("orderCancelTransaction")) {
                    JsonObject cancelTransaction = jsonResponse.getAsJsonObject("orderCancelTransaction");
                    if (cancelTransaction.has("reason") && cancelTransaction.get("reason").getAsString().equals("MARKET_HALTED")) {
                        return "A piac zárva van, a pozíció nem nyitható meg.";
                    }
                }

                // Ellenőrizze, hogy van-e "orderFillTransaction"
                if (jsonResponse.has("orderFillTransaction")) {
                    return "Pozíció megnyitva! ID: " + jsonResponse.get("orderFillTransaction").getAsJsonObject().get("id").getAsString();
                }

                return "Pozíció nyitása részben sikerült, de nincs visszaigazolás.";
            } else {
                throw new Exception("HTTP Hiba: " + response.code() + ", Válasz: " + responseBody);
            }
        }
    }




}
