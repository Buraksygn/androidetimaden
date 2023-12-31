package com.etimaden.adapterclass;

import android.graphics.Color;

import com.etimaden.persosclass.Zayi_urun;

public class Zayi_urun_data {

    private Zayi_urun zayi_urun ;
    private int rowColor;
    private int rowIconDrawable;
    private Boolean bekleyen;

    public Zayi_urun_data() {
    }

    public Zayi_urun_data(Zayi_urun zayi_urun, int rowColor, int rowIconDrawable, Boolean bekleyen) {
        this.zayi_urun = zayi_urun;
        this.rowColor = rowColor;
        this.rowIconDrawable = rowIconDrawable;
        this.bekleyen = bekleyen;
    }

    public Zayi_urun getZayi_urun() {
        return zayi_urun;
    }

    public void setZayi_urun(Zayi_urun zayi_urun) {
        this.zayi_urun = zayi_urun;
    }

    public int getRowColor() {
        return rowColor;
    }

    public void setRowColor(int rowColor) {
        this.rowColor = rowColor;
    }

    public Boolean getBekleyen() {
        return bekleyen;
    }

    public void setBekleyen(Boolean bekleyen) {
        this.bekleyen = bekleyen;
    }

    public int getRowIconDrawable() {
        return rowIconDrawable;
    }

    public void setRowIconDrawable(int rowIconDrawable) {
        this.rowIconDrawable = rowIconDrawable;
    }
}
