package com.etimaden.service.request;

import com.etimaden.cResponseResult.Arac;
import com.etimaden.cResponseResult.Urun_tag;
import com.etimaden.service.response.responseUrun_tag;

import java.util.ArrayList;
import java.util.List;

public class requestekleSevkiyatUrun_cikarma {
    public String _zsunucu_ip_adresi;
    public String _zaktif_alt_tesis;
    public String _zaktif_tesis;
    public String _zsurum;
    public String _zkullaniciadi;
    public String _zsifre;
    public String aktif_sunucu;
    public String aktif_kullanici;



    public List<responseUrun_tag> urun_listesi=new ArrayList<>();
    public String tag;
    public String hedef_depo;
    public String isemri_detay_id;
    public String islem_id ;
    public String kod_sap;
    public String isemri_id ;
    public String hedef_isletme_kodu ;

}
