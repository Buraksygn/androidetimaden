package com.etimaden.persosclass;

public class Depo_sayım_isemri {
    public String say_kod_isletme = "";
    public String say_kod_isletmeesleme = "";
    public String say_kod_sap = "";
    public String say_kod_isemri = "";
    public String say_dosya_id = "";
    public String say_ad = "";

    public Depo_sayım_isemri() {
    }

    public Depo_sayım_isemri(String say_kod_isletme, String say_kod_isletmeesleme, String say_kod_sap, String say_kod_isemri, String say_dosya_id, String say_ad) {
        this.say_kod_isletme = say_kod_isletme;
        this.say_kod_isletmeesleme = say_kod_isletmeesleme;
        this.say_kod_sap = say_kod_sap;
        this.say_kod_isemri = say_kod_isemri;
        this.say_dosya_id = say_dosya_id;
        this.say_ad = say_ad;
    }

    public String getSay_kod_isletme() {
        return say_kod_isletme;
    }

    public void setSay_kod_isletme(String say_kod_isletme) {
        this.say_kod_isletme = say_kod_isletme;
    }

    public String getSay_kod_isletmeesleme() {
        return say_kod_isletmeesleme;
    }

    public void setSay_kod_isletmeesleme(String say_kod_isletmeesleme) {
        this.say_kod_isletmeesleme = say_kod_isletmeesleme;
    }

    public String getSay_kod_sap() {
        return say_kod_sap;
    }

    public void setSay_kod_sap(String say_kod_sap) {
        this.say_kod_sap = say_kod_sap;
    }

    public String getSay_kod_isemri() {
        return say_kod_isemri;
    }

    public void setSay_kod_isemri(String say_kod_isemri) {
        this.say_kod_isemri = say_kod_isemri;
    }

    public String getSay_dosya_id() {
        return say_dosya_id;
    }

    public void setSay_dosya_id(String say_dosya_id) {
        this.say_dosya_id = say_dosya_id;
    }

    public String getSay_ad() {
        return say_ad;
    }

    public void setSay_ad(String say_ad) {
        this.say_ad = say_ad;
    }
}
