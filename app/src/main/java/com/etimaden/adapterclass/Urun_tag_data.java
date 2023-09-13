package com.etimaden.adapterclass;

import com.etimaden.persosclass.Urun_tag;

public class Urun_tag_data {

    private Urun_tag urun_tag ;
    private int rowColor;
    private int rowIconDrawable;
    private Boolean bekleyen;

    public Urun_tag_data() {
    }

    public Urun_tag_data(Urun_tag urun_tag, int rowColor, int rowIconDrawable, Boolean bekleyen) {
        this.urun_tag = urun_tag;
        this.rowColor = rowColor;
        this.rowIconDrawable = rowIconDrawable;
        this.bekleyen = bekleyen;
    }

    public Urun_tag getUrun_tag() {
        return urun_tag;
    }

    public void setUrun_tag(Urun_tag urun_tag) {
        this.urun_tag = urun_tag;
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
