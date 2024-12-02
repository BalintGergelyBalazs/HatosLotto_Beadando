package com.example.hatoslotto;

public class Nyeremeny {
    private int id;
    private int huzasId;
    private int talalat;
    private int darab;
    private int ertek;

    public Nyeremeny(int id, int huzasId, int talalat, int darab, int ertek) {
        this.id = id;
        this.huzasId = huzasId;
        this.talalat = talalat;
        this.darab = darab;
        this.ertek = ertek;
    }

    public int getId() {
        return id;
    }

    public int getHuzasId() {
        return huzasId;
    }

    public int getTalalat() {
        return talalat;
    }

    public int getDarab() {
        return darab;
    }

    public int getErtek() {
        return ertek;
    }
}

