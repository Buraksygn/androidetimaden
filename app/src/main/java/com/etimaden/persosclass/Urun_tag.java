package com.etimaden.persosclass;

public class Urun_tag
{

    public String isemri_kod ;
    public String serino_kayit_id;
    public String uretim_isemri_tipi;
    public String uretim_isemri_tipi_alt;
    public String isemri_detay_ID;
    public String depo_kod;
    public String urun_kod;
    public String palet_agirligi;
    public String palet_dizim_sayisi;
    public String torba_agirlik;
    public String karakteristikler;
    public String depo_adi;
    public String lotno ;
    public String vardiya;
    public String urun_adi;
    public String aciklama ;
    public String rfid ;
    public String kod ;
    public String palet_rfid ;
    public String isemri_sap_kod ;
    public String son_depo_kod ;
    public String son_depo_adi ;
    public String palet_kod ;
    public String islem_durumu ;
    public String etiket_turu ;
    public String kullanici_kod ;
    public String buffer_id ;
    public String kayıt_parametresi ;
    public String depo_silo_kod_list ;
    public String isletme ;
    public String isletme_alt ;
    public String isletme_esleme ;
    public String isletme_adi ;
    public String uretim_dakika_onayi ;
    public String uretim_lot_sayi_kontol ;
    public String kilitli ;
    public String MAN_TİP ;



    public Urun_tag()
    {
        this.isemri_kod = "";
        this.serino_kayit_id = "";
        this.uretim_isemri_tipi = "";
        this.uretim_isemri_tipi_alt = "";
        this.isemri_detay_ID = "";
        this.depo_kod = "";
        this.urun_kod = "";
        this.palet_agirligi = "";
        this.palet_dizim_sayisi = "";
        this.torba_agirlik = "";
        this.karakteristikler = "";
        this.depo_adi = "";
        this.lotno = "";
        this.vardiya = "";
        this.urun_adi = "";
        this.aciklama = "";
        this.rfid = "";
        this.kod = "";
        this.palet_rfid = "";
        this.isemri_sap_kod = "";
        this.palet_kod = "";
        this.islem_durumu = "";
        this.etiket_turu = "";
        this.kullanici_kod = "";
        this.son_depo_kod = "";
        this.son_depo_adi = "";
        this.buffer_id = "";
        this.kayıt_parametresi = "";
        this.depo_silo_kod_list = "";
        this.isletme = "";
        this.isletme_alt = "";
        this.isletme_esleme = "";
        this.isletme_adi = "";
        this.uretim_dakika_onayi = "";
        this.uretim_lot_sayi_kontol = "";
        this.kilitli = "";
        this.MAN_TİP = "";


    }

    public Urun_tag(
            String isemri_kod,
            String serino_kayit_id,
            String uretim_isemri_tipi,
            String uretim_isemri_tipi_alt,
            String isemri_detay_ID,
            String depo_kod,
            String urun_kod,
            String palet_agirligi,
            String palet_dizim_sayisi,
            String torba_agirlik,
            String karakteristikler,
            String depo_adi,
            String lotno,
            String vardiya,
            String urun_adi,
            String aciklama,
            String rfid,
            String kod,
            String palet_rfid,
            String isemri_sap_kod,
            String palet_kod,
            String islem_durumu,
            String etiket_turu,
            String kullanici_kod,
            String son_depo_kod,
            String son_depo_adi,
            String buffer_id,
            String kayıt_parametresi,
            String depo_silo_kod_list,
            String isletme ,
            String isletme_alt ,
            String isletme_esleme,
            String isletme_adi,
            String uretim_dakika_onayi ,
            String uretim_lot_sayi_kontol,
            String kilitli,
            String MAN_TİP

    )
    {
        this.isemri_kod = isemri_kod;
        this.serino_kayit_id = serino_kayit_id;
        this.uretim_isemri_tipi = uretim_isemri_tipi;
        this.uretim_isemri_tipi_alt = uretim_isemri_tipi_alt;
        this.isemri_detay_ID = isemri_detay_ID;
        this.depo_kod = depo_kod;
        this.urun_kod = urun_kod;
        this.palet_agirligi = palet_agirligi;
        this.palet_dizim_sayisi = palet_dizim_sayisi;
        this.torba_agirlik = torba_agirlik;
        this.karakteristikler = karakteristikler;
        this.depo_adi = depo_adi;
        this.lotno = lotno;
        this.vardiya = vardiya;
        this.urun_adi = urun_adi;
        this.aciklama = aciklama;
        this.rfid = rfid;
        this.kod = kod;
        this.palet_rfid = palet_rfid;
        this.isemri_sap_kod = isemri_sap_kod;
        this.palet_kod = palet_kod;
        this.islem_durumu = islem_durumu;
        this.etiket_turu = etiket_turu;
        this.kullanici_kod = kullanici_kod;
        this.son_depo_kod = son_depo_kod;
        this.son_depo_adi = son_depo_adi;
        this.buffer_id = buffer_id;
        this.kayıt_parametresi = kayıt_parametresi;
        this.depo_silo_kod_list = depo_silo_kod_list;
        this.isletme = isletme;
        this.isletme_alt = isletme_alt;
        this.isletme_esleme = isletme_esleme;
        this.isletme_adi = isletme_adi ;
        this.uretim_dakika_onayi = uretim_dakika_onayi;
        this.uretim_lot_sayi_kontol = uretim_lot_sayi_kontol;
        this.kilitli = kilitli;
        this.MAN_TİP = MAN_TİP;

    }

    public String getIsemri_kod() {
        return isemri_kod;
    }

    public void setIsemri_kod(String isemri_kod) {
        this.isemri_kod = isemri_kod;
    }

    public String getSerino_kayit_id() {
        return serino_kayit_id;
    }

    public void setSerino_kayit_id(String serino_kayit_id) {
        this.serino_kayit_id = serino_kayit_id;
    }

    public String getUretim_isemri_tipi() {
        return uretim_isemri_tipi;
    }

    public void setUretim_isemri_tipi(String uretim_isemri_tipi) {
        this.uretim_isemri_tipi = uretim_isemri_tipi;
    }

    public String getUretim_isemri_tipi_alt() {
        return uretim_isemri_tipi_alt;
    }

    public void setUretim_isemri_tipi_alt(String uretim_isemri_tipi_alt) {
        this.uretim_isemri_tipi_alt = uretim_isemri_tipi_alt;
    }

    public String getIsemri_detay_ID() {
        return isemri_detay_ID;
    }

    public void setIsemri_detay_ID(String isemri_detay_ID) {
        this.isemri_detay_ID = isemri_detay_ID;
    }

    public String getDepo_kod() {
        return depo_kod;
    }

    public void setDepo_kod(String depo_kod) {
        this.depo_kod = depo_kod;
    }

    public String getUrun_kod() {
        return urun_kod;
    }

    public void setUrun_kod(String urun_kod) {
        this.urun_kod = urun_kod;
    }

    public String getPalet_agirligi() {
        return palet_agirligi;
    }

    public void setPalet_agirligi(String palet_agirligi) {
        this.palet_agirligi = palet_agirligi;
    }

    public String getPalet_dizim_sayisi() {
        return palet_dizim_sayisi;
    }

    public void setPalet_dizim_sayisi(String palet_dizim_sayisi) {
        this.palet_dizim_sayisi = palet_dizim_sayisi;
    }

    public String getTorba_agirlik() {
        return torba_agirlik;
    }

    public void setTorba_agirlik(String torba_agirlik) {
        this.torba_agirlik = torba_agirlik;
    }

    public String getKarakteristikler() {
        return karakteristikler;
    }

    public void setKarakteristikler(String karakteristikler) {
        this.karakteristikler = karakteristikler;
    }

    public String getDepo_adi() {
        return depo_adi;
    }

    public void setDepo_adi(String depo_adi) {
        this.depo_adi = depo_adi;
    }

    public String getLotno() {
        return lotno;
    }

    public void setLotno(String lotno) {
        this.lotno = lotno;
    }

    public String getVardiya() {
        return vardiya;
    }

    public void setVardiya(String vardiya) {
        this.vardiya = vardiya;
    }

    public String getUrun_adi() {
        return urun_adi;
    }

    public void setUrun_adi(String urun_adi) {
        this.urun_adi = urun_adi;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public String getPalet_rfid() {
        return palet_rfid;
    }

    public void setPalet_rfid(String palet_rfid) {
        this.palet_rfid = palet_rfid;
    }

    public String getIsemri_sap_kod() {
        return isemri_sap_kod;
    }

    public void setIsemri_sap_kod(String isemri_sap_kod) {
        this.isemri_sap_kod = isemri_sap_kod;
    }

    public String getSon_depo_kod() {
        return son_depo_kod;
    }

    public void setSon_depo_kod(String son_depo_kod) {
        this.son_depo_kod = son_depo_kod;
    }

    public String getSon_depo_adi() {
        return son_depo_adi;
    }

    public void setSon_depo_adi(String son_depo_adi) {
        this.son_depo_adi = son_depo_adi;
    }

    public String getPalet_kod() {
        return palet_kod;
    }

    public void setPalet_kod(String palet_kod) {
        this.palet_kod = palet_kod;
    }

    public String getIslem_durumu() {
        return islem_durumu;
    }

    public void setIslem_durumu(String islem_durumu) {
        this.islem_durumu = islem_durumu;
    }

    public String getEtiket_turu() {
        return etiket_turu;
    }

    public void setEtiket_turu(String etiket_turu) {
        this.etiket_turu = etiket_turu;
    }

    public String getKullanici_kod() {
        return kullanici_kod;
    }

    public void setKullanici_kod(String kullanici_kod) {
        this.kullanici_kod = kullanici_kod;
    }

    public String getBuffer_id() {
        return buffer_id;
    }

    public void setBuffer_id(String buffer_id) {
        this.buffer_id = buffer_id;
    }

    public String getKayıt_parametresi() {
        return kayıt_parametresi;
    }

    public void setKayıt_parametresi(String kayıt_parametresi) {
        this.kayıt_parametresi = kayıt_parametresi;
    }

    public String getDepo_silo_kod_list() {
        return depo_silo_kod_list;
    }

    public void setDepo_silo_kod_list(String depo_silo_kod_list) {
        this.depo_silo_kod_list = depo_silo_kod_list;
    }

    public String getIsletme() {
        return isletme;
    }

    public void setIsletme(String isletme) {
        this.isletme = isletme;
    }

    public String getIsletme_alt() {
        return isletme_alt;
    }

    public void setIsletme_alt(String isletme_alt) {
        this.isletme_alt = isletme_alt;
    }

    public String getIsletme_esleme() {
        return isletme_esleme;
    }

    public void setIsletme_esleme(String isletme_esleme) {
        this.isletme_esleme = isletme_esleme;
    }

    public String getIsletme_adi() {
        return isletme_adi;
    }

    public void setIsletme_adi(String isletme_adi) {
        this.isletme_adi = isletme_adi;
    }

    public String getUretim_dakika_onayi() {
        return uretim_dakika_onayi;
    }

    public void setUretim_dakika_onayi(String uretim_dakika_onayi) {
        this.uretim_dakika_onayi = uretim_dakika_onayi;
    }

    public String getUretim_lot_sayi_kontol() {
        return uretim_lot_sayi_kontol;
    }

    public void setUretim_lot_sayi_kontol(String uretim_lot_sayi_kontol) {
        this.uretim_lot_sayi_kontol = uretim_lot_sayi_kontol;
    }

    public String getKilitli() {
        return kilitli;
    }

    public void setKilitli(String kilitli) {
        this.kilitli = kilitli;
    }

    public String getMAN_TİP() {
        return MAN_TİP;
    }

    public void setMAN_TİP(String MAN_TİP) {
        this.MAN_TİP = MAN_TİP;
    }
}
