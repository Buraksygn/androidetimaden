package com.etimaden.persosclass;

public class lot_detay {

    private String serino_miktar;
    private String serino_lot;
    private String serino_isletme;
    private String serino;
    private String durum;

    public lot_detay() {
    }

    public lot_detay(String serino_miktar, String serino_lot, String serino_isletme, String serino, String durum) {
        this.serino_miktar = serino_miktar;
        this.serino_lot = serino_lot;
        this.serino_isletme = serino_isletme;
        this.serino = serino;
        this.durum = durum;
    }

    public String getSerino_miktar() {
        return serino_miktar;
    }

    public void setSerino_miktar(String serino_miktar) {
        this.serino_miktar = serino_miktar;
    }

    public String getSerino_lot() {
        return serino_lot;
    }

    public void setSerino_lot(String serino_lot) {
        this.serino_lot = serino_lot;
    }

    public String getSerino_isletme() {
        return serino_isletme;
    }

    public void setSerino_isletme(String serino_isletme) {
        this.serino_isletme = serino_isletme;
    }

    public String getSerino() {
        return serino;
    }

    public void setSerino(String serino) {
        this.serino = serino;
    }

    public String getDurum() {
        return durum;
    }

    public void setDurum(String durum) {
        this.durum = durum;
    }
}
