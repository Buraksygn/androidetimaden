package com.etimaden.persosclass;

public class malzeme_sayim_isemri {
    private String mls_kod_sap;
    private String mls_kod_isletme;
    private String mls_kod_depo;
    private String msd_id;
    private String msd_kod_urun;
    private String msd_flag_durumu;
    private String mls_kod;

    public malzeme_sayim_isemri() {
    }

    public malzeme_sayim_isemri(String mls_kod_sap, String mls_kod_isletme, String mls_kod_depo, String msd_id, String msd_kod_urun, String msd_flag_durumu, String mls_kod) {
        this.mls_kod_sap = mls_kod_sap;
        this.mls_kod_isletme = mls_kod_isletme;
        this.mls_kod_depo = mls_kod_depo;
        this.msd_id = msd_id;
        this.msd_kod_urun = msd_kod_urun;
        this.msd_flag_durumu = msd_flag_durumu;
        this.mls_kod = mls_kod;
    }

    public String getMls_kod_sap() {
        return mls_kod_sap;
    }

    public void setMls_kod_sap(String mls_kod_sap) {
        this.mls_kod_sap = mls_kod_sap;
    }

    public String getMls_kod_isletme() {
        return mls_kod_isletme;
    }

    public void setMls_kod_isletme(String mls_kod_isletme) {
        this.mls_kod_isletme = mls_kod_isletme;
    }

    public String getMls_kod_depo() {
        return mls_kod_depo;
    }

    public void setMls_kod_depo(String mls_kod_depo) {
        this.mls_kod_depo = mls_kod_depo;
    }

    public String getMsd_id() {
        return msd_id;
    }

    public void setMsd_id(String msd_id) {
        this.msd_id = msd_id;
    }

    public String getMsd_kod_urun() {
        return msd_kod_urun;
    }

    public void setMsd_kod_urun(String msd_kod_urun) {
        this.msd_kod_urun = msd_kod_urun;
    }

    public String getMsd_flag_durumu() {
        return msd_flag_durumu;
    }

    public void setMsd_flag_durumu(String msd_flag_durumu) {
        this.msd_flag_durumu = msd_flag_durumu;
    }

    public String getMls_kod() {
        return mls_kod;
    }

    public void setMls_kod(String mls_kod) {
        this.mls_kod = mls_kod;
    }
}
