package com.example.hatoslotto;

public class AccountInfo {
    private String accountId;
    private String currency;
    private double balance;
    private String accountType;
    private int openTrades;

    public AccountInfo(String accountId, String currency, double balance, String accountType, int openTrades) {
        this.accountId = accountId;
        this.currency = currency;
        this.balance = balance;
        this.accountType = accountType;
        this.openTrades = openTrades;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getCurrency() {
        return currency;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public int getOpenTrades() {
        return openTrades;
    }
}
