package com.example.hatoslotto;

import javafx.beans.property.*;

public class Candle {
    private final StringProperty date;
    private final DoubleProperty open;
    private final DoubleProperty close;
    private final DoubleProperty low;
    private final DoubleProperty high;

    public Candle(String date, double open, double close, double low, double high) {
        this.date = new SimpleStringProperty(date);
        this.open = new SimpleDoubleProperty(open);
        this.close = new SimpleDoubleProperty(close);
        this.low = new SimpleDoubleProperty(low);
        this.high = new SimpleDoubleProperty(high);
    }

    public StringProperty dateProperty() {
        return date;
    }

    public DoubleProperty openProperty() {
        return open;
    }

    public DoubleProperty closeProperty() {
        return close;
    }

    public DoubleProperty lowProperty() {
        return low;
    }

    public DoubleProperty highProperty() {
        return high;
    }
}
