package com.etimaden.persosclass;

public class Gemi {
    public String gemiAd ="";
    public String gemiKod ="";

    public Gemi() {

    }

    public Gemi(String _gemiAd , String _gemiKod){
        this.gemiAd = _gemiAd;
        this.gemiKod = _gemiKod;
    }

    public String getGemiAd() {
        return gemiAd;
    }
    public String getGemiKod() {
        return gemiKod;
    }
}
