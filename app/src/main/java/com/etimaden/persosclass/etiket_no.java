package com.etimaden.persosclass;

public class etiket_no {
    public etiket_no(String eti_yil, String eti_ay, String eti_gun, String eti_isletme, String eti_sirano) {
        this.eti_yil = eti_yil;
        this.eti_ay = eti_ay;
        this.eti_gun = eti_gun;
        this.eti_isletme = eti_isletme;
        this.eti_sirano = eti_sirano;
    }

    public etiket_no() {
    }

    public String getEti_yil() {
        return eti_yil;
    }

    public void setEti_yil(String eti_yil) {
        this.eti_yil = eti_yil;
    }

    public String getEti_ay() {
        return eti_ay;
    }

    public void setEti_ay(String eti_ay) {
        this.eti_ay = eti_ay;
    }

    public String getEti_gun() {
        return eti_gun;
    }

    public void setEti_gun(String eti_gun) {
        this.eti_gun = eti_gun;
    }

    public String getEti_isletme() {
        return eti_isletme;
    }

    public void setEti_isletme(String eti_isletme) {
        this.eti_isletme = eti_isletme;
    }

    public String getEti_sirano() {
        return eti_sirano;
    }

    public void setEti_sirano(String eti_sirano) {
        this.eti_sirano = eti_sirano;
    }

    private String eti_yil;
    private String eti_ay;
    private String eti_gun;
    private String eti_isletme;
    private String eti_sirano;

}
