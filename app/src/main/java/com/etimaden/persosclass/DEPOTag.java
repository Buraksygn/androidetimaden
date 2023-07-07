package com.etimaden.persosclass;

public class DEPOTag {

    public DEPOTag() {
    }

    public DEPOTag(String depo_id, String depo_adi, String depo_giris_tag, String depo_cikis_tag, String alt_isletme_kod, String isletme_kod, String depo_turu, String depo_kod_isletmeesleme) {
        this.depo_id = depo_id;
        this.depo_adi = depo_adi;
        this.depo_giris_tag = depo_giris_tag;
        this.depo_cikis_tag = depo_cikis_tag;
        this.alt_isletme_kod = alt_isletme_kod;
        this.isletme_kod = isletme_kod;
        this.depo_turu = depo_turu;
        this.depo_kod_isletmeesleme = depo_kod_isletmeesleme;
    }

    public String getDepo_id() {
        return depo_id;
    }

    public void setDepo_id(String depo_id) {
        this.depo_id = depo_id;
    }

    public String getDepo_adi() {
        return depo_adi;
    }

    public void setDepo_adi(String depo_adi) {
        this.depo_adi = depo_adi;
    }

    public String getDepo_giris_tag() {
        return depo_giris_tag;
    }

    public void setDepo_giris_tag(String depo_giris_tag) {
        this.depo_giris_tag = depo_giris_tag;
    }

    public String getDepo_cikis_tag() {
        return depo_cikis_tag;
    }

    public void setDepo_cikis_tag(String depo_cikis_tag) {
        this.depo_cikis_tag = depo_cikis_tag;
    }

    public String getAlt_isletme_kod() {
        return alt_isletme_kod;
    }

    public void setAlt_isletme_kod(String alt_isletme_kod) {
        this.alt_isletme_kod = alt_isletme_kod;
    }

    public String getIsletme_kod() {
        return isletme_kod;
    }

    public void setIsletme_kod(String isletme_kod) {
        this.isletme_kod = isletme_kod;
    }

    public String getDepo_turu() {
        return depo_turu;
    }

    public void setDepo_turu(String depo_turu) {
        this.depo_turu = depo_turu;
    }

    public String getDepo_kod_isletmeesleme() {
        return depo_kod_isletmeesleme;
    }

    public void setDepo_kod_isletmeesleme(String depo_kod_isletmeesleme) {
        this.depo_kod_isletmeesleme = depo_kod_isletmeesleme;
    }

    private String depo_id = "";
    private String depo_adi = "";
    private String depo_giris_tag = "";
    private String depo_cikis_tag = "";
    private String alt_isletme_kod = "";
    private String isletme_kod = "";
    private String depo_turu = "";
    private String depo_kod_isletmeesleme = "";
}
