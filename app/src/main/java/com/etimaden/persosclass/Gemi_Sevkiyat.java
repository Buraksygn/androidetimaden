package com.etimaden.persosclass;

public class Gemi_Sevkiyat {
    public String gemi_sevkiyat_id = "";
    public String ise_sap_kod = "";
    public String gemi_adi = "";
    public String ara_plaka = "";
    public String ara_barkod = "";
    public String sofor_adi = "";
    public String yukleme_durumu = "";
    public String hasar_durumu = "";
    public String yukleme_tarihi = "";
    public String yukleme_kullanici = "";

    public Gemi_Sevkiyat() {}

    public Gemi_Sevkiyat(
            String _gemi_sevkiyat_id,
            String _ise_sap_kod,
            String _gemi_adi,
            String _ara_plaka,
            String _ara_barkod,
            String _sofor_adi,
            String _yukleme_durumu,
            String _hasar_durumu,
            String _yukleme_tarihi,
            String _yukleme_kullanici) {
        this.gemi_sevkiyat_id = _gemi_sevkiyat_id;
        this.ise_sap_kod = _ise_sap_kod;
        this.gemi_adi = _gemi_adi;
        this.ara_plaka = _ara_plaka;
        this.ara_barkod = _ara_barkod;
        this.sofor_adi = _sofor_adi;
        this.yukleme_durumu = _yukleme_durumu;
        this.hasar_durumu = _hasar_durumu;
        this.yukleme_tarihi = _yukleme_tarihi;
        this.yukleme_kullanici = _yukleme_kullanici;
    }

    public String getGemi_sevkiyat_id() {
        return gemi_sevkiyat_id;
    }

    public void setGemi_sevkiyat_id(String _gemi_sevkiyat_id) {
        this.gemi_sevkiyat_id = _gemi_sevkiyat_id;
    }

    public String getIse_sap_kod() {
        return ise_sap_kod;
    }

    public void setIse_sap_kod(String _ise_sap_kod) {
        this.ise_sap_kod = _ise_sap_kod;
    }

    public String getGemi_adi() {
        return gemi_adi;
    }

    public void setGemi_adi(String _gemi_adi) {
        this.gemi_adi = _gemi_adi;
    }

    public String getAra_plaka() {
        return ara_plaka;
    }

    public void setAra_plaka(String _ara_plaka) {
        this.ara_plaka = _ara_plaka;
    }

    public String getAra_barkod() {
        return ara_barkod;
    }

    public void setAra_barkod(String _ara_barkod) {
        this.ara_barkod = _ara_barkod;
    }

    public String getSofor_adi() {
        return sofor_adi;
    }

    public void setSofor_adi(String _sofor_adi) {
        this.sofor_adi = _sofor_adi;
    }

    public String getYukleme_durumu() {
        return yukleme_durumu;
    }

    public void setYukleme_durumu(String _yukleme_durumu) {
        this.yukleme_durumu = _yukleme_durumu;
    }

    public String getHasar_durumu() {
        return hasar_durumu;
    }

    public void setHasar_durumu(String _hasar_durumu) {
        this.hasar_durumu = _hasar_durumu;
    }

    public String getYukleme_tarihi() {
        return yukleme_tarihi;
    }

    public void setYukleme_tarihi(String _yukleme_tarihi) {
        this.yukleme_tarihi = _yukleme_tarihi;
    }

    public String getYukleme_kullanici() {
        return yukleme_kullanici;
    }

    public void setYukleme_kullanici(String _yukleme_kullanici) {
        this.yukleme_kullanici = _yukleme_kullanici;
    }
}