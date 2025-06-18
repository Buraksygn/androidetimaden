package com.etimaden.persosclass;

public class Gemi_Teslimat {
    public String arac_plaka ="";
    public String arac_kod ="";
    public String arac_teslimat_id ="";
    public String ise_sap_kod ="";
    public String urun_ad ="";
    public String ise_id ="";
    public Gemi_Teslimat(){}
    public Gemi_Teslimat(
            String _arac_plaka ,
            String _arac_kod ,
            String _arac_teslimat_id ,
            String _ise_sap_kod,
            String _urun_ad,
            String _ise_id){
        this.arac_plaka = _arac_plaka;
        this.arac_kod = _arac_kod;
        this.arac_teslimat_id = _arac_teslimat_id;
        this.ise_sap_kod = _ise_sap_kod;
        this.urun_ad = _urun_ad;
        this.ise_id = _ise_id;
    }

    public String getArac_plaka(){return arac_plaka;}

    public void setArac_plaka(String _arac_plaka){
        this.arac_plaka = _arac_plaka;
    }

    public String getArac_kod(){return arac_kod;}

    public void setArac_kod(String _arac_Kod){
        this.arac_kod = _arac_Kod;
    }

    public String getArac_teslimat_id(){
        return arac_teslimat_id;
    }

    public void setArac_teslimat_id(String _arac_teslimat_id){
        this.arac_teslimat_id = _arac_teslimat_id;
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




}
