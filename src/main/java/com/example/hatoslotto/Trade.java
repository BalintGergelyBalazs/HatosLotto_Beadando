package com.example.hatoslotto;

import javafx.beans.property.*;

public class Trade {
    private final StringProperty tradeId;
    private final StringProperty instrument;
    private final IntegerProperty units;
    private final DoubleProperty price;

    public Trade(String tradeId, String instrument, int units, double price) {
        this.tradeId = new SimpleStringProperty(tradeId);
        this.instrument = new SimpleStringProperty(instrument);
        this.units = new SimpleIntegerProperty(units);
        this.price = new SimpleDoubleProperty(price);
    }

    public StringProperty tradeIdProperty() {
        return tradeId;
    }

    public StringProperty instrumentProperty() {
        return instrument;
    }

    public IntegerProperty unitsProperty() {
        return units;
    }

    public DoubleProperty priceProperty() {
        return price;
    }
}
