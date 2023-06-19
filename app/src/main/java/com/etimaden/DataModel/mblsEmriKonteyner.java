package com.etimaden.DataModel;

public class mblsEmriKonteyner {

    int sirano=0;

    String kod_sap="";
    String bookingno="";
    String urun_adi="";
    String kalan_agirlik="";
    String kalan_palet_sayisi="";
    String yapilan_miktar="";
    String yapilan_adet="";
    String alici_isletme="";
    String sefer_no="";
    String isemri_id="";
    String isemri_detay_id="";
    String hedef_isletme_esleme="";
    String hedef_isletme_kodu="";
    String hedef_isletme_alt_kodu="";
    String depo_kodu="";
    String hedef_depo="";
    String urun_kodu="";
    String isletme_esleme="";


    public mblsEmriKonteyner( int v_sirano,
                              String v_kod_sap ,
                              String v_bookingno,
                              String v_urun_adi,
                              String v_kalan_agirlik,
                              String v_kalan_palet_sayisi,
                              String v_yapilan_miktar,
                              String v_yapilan_adet,
                              String v_alici_isletme,
                              String v_sefer_no,
                              String v_isemri_id,
                              String v_isemri_detay_id,
                              String v_hedef_isletme_esleme,
                              String v_hedef_isletme_kodu,
                              String v_hedef_isletme_alt_kodu,
                              String v_depo_kodu,
                              String v_hedef_depo,
                              String v_urun_kodu,
                              String v_isletme_esleme )
    {
        sirano = v_sirano;
        kod_sap = v_kod_sap;
        bookingno = v_bookingno;
        urun_adi = v_urun_adi;
        kalan_agirlik = v_kalan_agirlik;
        kalan_palet_sayisi = v_kalan_palet_sayisi;
        yapilan_miktar = v_yapilan_miktar;
        yapilan_adet = v_yapilan_adet;
        alici_isletme = v_alici_isletme;
        sefer_no = v_sefer_no;
        isemri_id = v_isemri_id;
        isemri_detay_id = v_isemri_detay_id;
        hedef_isletme_esleme = v_hedef_isletme_esleme;
        hedef_isletme_kodu = v_hedef_isletme_kodu;
        hedef_isletme_alt_kodu = v_hedef_isletme_alt_kodu;
        depo_kodu = v_depo_kodu;
        hedef_depo = v_hedef_depo;
        urun_kodu = v_urun_kodu;
        isletme_esleme = v_isletme_esleme;
    }

    public int getsirano(){return sirano;}

    public String getyazi_sira_no(){return sirano+"";}
    public String getkod_sap(){return kod_sap;}
    public String getbookingno(){return bookingno;}
    public String geturun_adi(){return urun_adi;}
    public String getkalan_agirlik(){return kalan_agirlik;}
    public String getkalan_palet_sayisi(){return kalan_palet_sayisi;}
    public String getyapilan_miktar(){return yapilan_miktar;}
    public String getyapilan_adet(){return yapilan_adet;}
    public String getalici_isletme(){return alici_isletme;}
    public String getsefer_no(){return sefer_no;}
    public String getisemri_id(){return isemri_id;}
    public String getisemri_detay_id(){return isemri_detay_id;}
    public String gethedef_isletme_esleme(){return hedef_isletme_esleme;}
    public String gethedef_isletme_kodu(){return hedef_isletme_kodu;}
    public String gethedef_isletme_alt_kodu(){return hedef_isletme_alt_kodu;}
    public String getdepo_kodu(){return depo_kodu;}
    public String gethedef_depo(){return hedef_depo;}
    public String geturun_kodu(){return urun_kodu;}
    public String getisletme_esleme(){return isletme_esleme;}
}
