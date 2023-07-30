package com.etimaden.DataModel;

public class mblDigerEtiket {

    int sirano;

    String kod = "";
    String durum= "";
    String urunadi= "";

    public mblDigerEtiket() {
    }

    public mblDigerEtiket(int sirano, String kod, String durum, String urunadi) {
        this.sirano = sirano;
        this.kod = kod;
        this.durum = durum;
        this.urunadi = urunadi;
    }

    public int getSirano() {
        return sirano;
    }

    public void setSirano(int sirano) {
        this.sirano = sirano;
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public String getDurum() {
        return durum;
    }

    public void setDurum(String durum) {
        this.durum = durum;
    }

    public String getUrunadi() {
        return urunadi;
    }

    public void setUrunadi(String urunadi) {
        this.urunadi = urunadi;
    }
}
