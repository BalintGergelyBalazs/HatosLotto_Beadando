package com.example.hatoslotto;

public class Huzott {
    private int id;
    private int huzasId;
    private int szam;

    public Huzott(int id, int huzasId, int szam) {
        this.id = id;
        this.huzasId = huzasId;
        this.szam = szam;
    }

    public int getId() {
        return id;
    }

    public int getHuzasId() {
        return huzasId;
    }

    public int getSzam() {
        return szam;
    }
}
