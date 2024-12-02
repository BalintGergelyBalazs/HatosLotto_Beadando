package com.example.hatoslotto;

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

public class ForexAccountsController {

    @FXML
    private TableView<AccountInfo> accountsTable;
    @FXML
    private TableColumn<AccountInfo, String> accountIdCol;
    @FXML
    private TableColumn<AccountInfo, String> currencyCol;
    @FXML
    private TableColumn<AccountInfo, Double> balanceCol;
    @FXML
    private TableColumn<AccountInfo, String> accountTypeCol;
    @FXML
    private TableColumn<AccountInfo, Integer> openTradesCol;

    private final ObservableList<AccountInfo> accountData = FXCollections.observableArrayList();

    private static final String API_TOKEN = "186560cdecdd5a95fbb9ef4bcc0dec78-f845800240a7ec379f915fcf1cb83d1e";
    private static final String BASE_URL = "https://api-fxpractice.oanda.com/v3/accounts";

    @FXML
    public void initialize() {
        // Táblázat oszlopainak beállítása
        accountIdCol.setCellValueFactory(new PropertyValueFactory<>("accountId"));
        currencyCol.setCellValueFactory(new PropertyValueFactory<>("currency"));
        balanceCol.setCellValueFactory(new PropertyValueFactory<>("balance"));
        accountTypeCol.setCellValueFactory(new PropertyValueFactory<>("accountType"));
        openTradesCol.setCellValueFactory(new PropertyValueFactory<>("openTrades"));

        // Táblázat adatainak összekapcsolása
        accountsTable.setItems(accountData);
    }

    @FXML
    protected void onLoadDataClick() {
        // Adatok betöltése az Oanda API-ból
        try {
            String jsonResponse = fetchAccountData();
            parseAndDisplayData(jsonResponse);
        } catch (Exception e) {
            System.out.println("Hiba az adatok betöltése során: " + e.getMessage());
        }
    }

    private String fetchAccountData() throws Exception {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(BASE_URL)
                .addHeader("Authorization", "Bearer " + API_TOKEN)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new Exception("HTTP Hiba: " + response.code());
            }
            return response.body().string();
        }
    }

    private void parseAndDisplayData(String jsonResponse) {
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
        accountData.clear();

        // API válaszban a számlák ID-jait lekérjük
        if (jsonObject.has("accounts")) {
            jsonObject.getAsJsonArray("accounts").forEach(account -> {
                JsonObject acc = account.getAsJsonObject();
                String accountId = acc.get("id").getAsString();

                // Részletes adatok lekérése az adott számlához
                JsonObject detailedAccountInfo = fetchDetailedAccountData(accountId);
                if (detailedAccountInfo != null) {
                    String currency = detailedAccountInfo.has("currency") ? detailedAccountInfo.get("currency").getAsString() : "N/A";
                    double balance = detailedAccountInfo.has("balance") ? detailedAccountInfo.get("balance").getAsDouble() : 0.0;
                    String accountType = detailedAccountInfo.has("accountType") ? detailedAccountInfo.get("accountType").getAsString() : "N/A";
                    int openTrades = detailedAccountInfo.has("openTradeCount") ? detailedAccountInfo.get("openTradeCount").getAsInt() : 0;

                    accountData.add(new AccountInfo(accountId, currency, balance, accountType, openTrades));
                }
            });
        } else {
            System.out.println("Nem található 'accounts' mező a JSON válaszban.");
        }
    }

    // Új metódus a részletes számlainformációk lekéréséhez
    private JsonObject fetchDetailedAccountData(String accountId) {
        OkHttpClient client = new OkHttpClient();

        String url = "https://api-fxpractice.oanda.com/v3/accounts/" + accountId;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + API_TOKEN)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return JsonParser.parseString(response.body().string()).getAsJsonObject();
            } else {
                System.out.println("Hiba az API hívás során: " + response.code());
            }
        } catch (Exception e) {
            System.out.println("Hiba a részletes számlaadatok lekérése során: " + e.getMessage());
        }
        return null;
    }
}
