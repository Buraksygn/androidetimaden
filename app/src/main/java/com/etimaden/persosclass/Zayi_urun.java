package com.etimaden.persosclass;

public class Zayi_urun {

    public String har_id ;
    public String serino ;
    public String agirlik;
    public String lotno;

    public Zayi_urun() {
    }

    public Zayi_urun(String har_id, String serino, String agirlik, String lotno) {
        this.har_id = har_id;
        this.serino = serino;
        this.agirlik = agirlik;
        this.lotno = lotno;
    }

    public String getHar_id() {
        return har_id;
    }

    public void setHar_id(String har_id) {
        this.har_id = har_id;
    }

    public String getSerino() {
        return serino;
    }

    public void setSerino(String serino) {
        this.serino = serino;
    }

    public String getAgirlik() {
        return agirlik;
    }

    public void setAgirlik(String agirlik) {
        this.agirlik = agirlik;
    }

    public String getLotno() {
        return lotno;
    }

    public void setLotno(String lotno) {
        this.lotno = lotno;
    }
}
