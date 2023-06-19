package com.etimaden.service.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class uretim_etiket implements Serializable {

    @SerializedName("isletme")
    private String isletme;

    @SerializedName("sap_kodu")
    private String sap_kodu;

    @SerializedName("isemri_kodu")
    private String isemri_kodu;

    @SerializedName("depo")
    private String depo;

    @SerializedName("silo")
    private String silo;

    @SerializedName("isemri_detay")
    private String isemri_detay;

    @SerializedName("palet_miktar")
    private String palet_miktar;

    @SerializedName("torba_miktar")
    private String torba_miktar;

    @SerializedName("palet_dizim")
    private String palet_dizim;

    @SerializedName("paket_tipi")
    private String paket_tipi;

    @SerializedName("etiket_durumu")
    private String etiket_durumu;

    @SerializedName("etiket_uretim_uygunlugu")
    private String etiket_uretim_uygunlugu;

    @SerializedName("urun_kodu")
    private String urun_kodu;

    @SerializedName("serino_kod")
    private String serino_kod;

    @SerializedName("palet_kod")
    private String palet_kod;

    @SerializedName("serino_rfid")
    private String serino_rfid;

    @SerializedName("ser_lotno")
    private String ser_lotno;

    @SerializedName("depo_silo_secimi")
    private String depo_silo_secimi;

    @SerializedName("isemri_tipi")
    private String isemri_tipi;

    @SerializedName("isemri_tipialt")
    private String isemri_tipialt;

    public String getisletme() { return isletme;}
    public String getsap_kodu() { return sap_kodu;}
    public String getisemri_kodu() { return isemri_kodu; }
    public String getdepo (){ return depo; }
    public String getsilo (){ return silo; }
    public String getisemri_detay (){ return isemri_detay; }
    public String getpalet_miktar (){ return palet_miktar; }
    public String gettorba_miktar (){ return torba_miktar; }
    public String getpalet_dizim (){ return palet_dizim; }
    public String getpaket_tipi (){ return paket_tipi; }
    public String getetiket_durumu() { return etiket_durumu; }
    public String getetiket_uretim_uygunlugu (){ return etiket_uretim_uygunlugu; }
    public String geturun_kodu (){ return urun_kodu; }
    public String getserino_kod (){ return serino_kod; }
    public String getpalet_kod (){ return palet_kod; }
    public String getserino_rfid() { return serino_rfid; }
    public String getser_lotno (){ return ser_lotno; }
    public String getdepo_silo_secimi() { return depo_silo_secimi; }
    public String getisemri_tipi (){ return isemri_tipi; }
    public String getisemri_tipialt() { return isemri_tipialt; }

    public void setdepo(String v_Gelen){depo=v_Gelen;}
    public void setsilo(String v_Gelen){silo=v_Gelen;}
}
