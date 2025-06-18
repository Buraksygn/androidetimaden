package com.etimaden.persosclass;

public class Gemi_Sevkiyat {

    public String ise_sap_kod ="";
    public String urun_ad ="";
    public String ise_id ="";
    public String isd_id ="";
    public String ise_kod_sap ="";
    public String uru_kod ="";
    public String ara_plaka ="";
    public String ara_rfid ="";
    public String ara_kod ="";
    public String isd_kod_isletme ="";
    public String hasar_durumu ="";

    public Gemi_Sevkiyat(){}

    public Gemi_Sevkiyat(
            String _ise_sap_kod ,
            String _urun_ad ,
            String _ise_id,
            String _isd_id,
            String _ise_kod_sap,
            String _uru_kod,
            String _ara_plaka,
            String _ara_rfid,
            String _ara_kod,
            String _isd_kod_isletme,
            String _hasar_durumu){
        this.ise_sap_kod = _ise_sap_kod;
        this.urun_ad = _urun_ad;
        this.ise_id = _ise_id;
        this.isd_id = _isd_id;
        this.ise_kod_sap = _ise_kod_sap;
        this.uru_kod = _uru_kod;
        this.ara_plaka = _ara_plaka;
        this.ara_rfid = _ara_rfid;
        this.ara_kod = _ara_kod;
        this.isd_kod_isletme = _isd_kod_isletme;
        this.hasar_durumu = _hasar_durumu;
    }
    public String getIse_sap_kod(){return ise_sap_kod;}

    public void setIse_sap_kod(String _ise_sap_kod){
        this.ise_sap_kod = _ise_sap_kod;
    }

    public String getUrun_ad(){return urun_ad;}

    public void setUrun_ad(String _urun_ad){
        this.urun_ad = _urun_ad;
    }

    public String getIse_id(){return ise_id;}

    public void setIse_id(String _ise_id){
        this.ise_id = _ise_id;
    }

    public String getIsd_id(){return isd_id;}

    public void setIsd_id(String _isd_id){
        this.isd_id = _isd_id;
    }

    public String getIse_kod_sap(){return ise_kod_sap;}

    public void setIse_kod_sap(String _ise_kod_sap){
        this.ise_sap_kod = _ise_kod_sap;
    }
    public String getAra_plaka(){return ara_plaka;}

    public void setAra_plaka(String _ara_plaka){
        this.ara_plaka = _ara_plaka;
    }

    public String getAra_rfid(){return ara_rfid;}

    public void setAra_rfid(String _ara_rfid){
        this.ara_rfid = _ara_rfid;
    }

    public String getHasar_durumu(){return hasar_durumu;}

    public void setHasar_durumu(String _hasar_durumu){
        this.hasar_durumu = _hasar_durumu;
    }


}
