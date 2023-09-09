package com.etimaden.request;

public class requestsecDepoTanimlari {


    private String _zsunucu_ip_adresi;
    private String _zaktif_alt_tesis;
    private String _zaktif_tesis ;
    private String _zsurum ;
    private String _zkullaniciadi ;
    private String _zsifre;
    private String aktif_sunucu;
    private String aktif_kullanici;

    private String isletme;
    private String depo_silo_secimi;
    private String depo_turu;
    private Boolean depo_silo_secimi_kontrol;
    //private String depo_id;

    public requestsecDepoTanimlari() {
    }

    public String get_zsunucu_ip_adresi() {
        return _zsunucu_ip_adresi;
    }

    public void set_zsunucu_ip_adresi(String _zsunucu_ip_adresi) {
        this._zsunucu_ip_adresi = _zsunucu_ip_adresi;
    }

    public String get_zaktif_alt_tesis() {
        return _zaktif_alt_tesis;
    }

    public void set_zaktif_alt_tesis(String _zaktif_alt_tesis) {
        this._zaktif_alt_tesis = _zaktif_alt_tesis;
    }

    public String get_zaktif_tesis() {
        return _zaktif_tesis;
    }

    public void set_zaktif_tesis(String _zaktif_tesis) {
        this._zaktif_tesis = _zaktif_tesis;
    }

    public String get_zsurum() {
        return _zsurum;
    }

    public void set_zsurum(String _zsurum) {
        this._zsurum = _zsurum;
    }

    public String get_zkullaniciadi() {
        return _zkullaniciadi;
    }

    public void set_zkullaniciadi(String _zkullaniciadi) {
        this._zkullaniciadi = _zkullaniciadi;
    }

    public String get_zsifre() {
        return _zsifre;
    }

    public void set_zsifre(String _zsifre) {
        this._zsifre = _zsifre;
    }

    public String getAktif_sunucu() {
        return aktif_sunucu;
    }

    public void setAktif_sunucu(String aktif_sunucu) {
        this.aktif_sunucu = aktif_sunucu;
    }

    public String getAktif_kullanici() {
        return aktif_kullanici;
    }

    public void setAktif_kullanici(String aktif_kullanici) {
        this.aktif_kullanici = aktif_kullanici;
    }

    public String getIsletme() {
        return isletme;
    }

    public void setIsletme(String isletme) {
        this.isletme = isletme;
    }

    public String getDepo_silo_secimi() {
        return depo_silo_secimi;
    }

    public void setDepo_silo_secimi(String depo_silo_secimi) {
        this.depo_silo_secimi = depo_silo_secimi;
    }

    public String getDepo_turu() {
        return depo_turu;
    }

    public void setDepo_turu(String depo_turu) {
        this.depo_turu = depo_turu;
    }



   // public String getDepo_id() {
       // return depo_id;
    //}

    //public void setDepo_id(String depo_id) {
     //   this.depo_id = depo_id;
  //  }


    public Boolean getDepo_silo_secimi_kontrol() {
        return depo_silo_secimi_kontrol;
    }

    public void setDepo_silo_secimi_kontrol(Boolean depo_silo_secimi_kontrol) {
        this.depo_silo_secimi_kontrol = depo_silo_secimi_kontrol;
    }

    public requestsecDepoTanimlari(String _zsunucu_ip_adresi, String _zaktif_alt_tesis, String _zaktif_tesis, String _zsurum, String _zkullaniciadi, String _zsifre, String aktif_sunucu, String aktif_kullanici, String isletme, String depo_silo_secimi, String depo_turu, Boolean depo_silo_secimi_kontrol) {
        this._zsunucu_ip_adresi = _zsunucu_ip_adresi;
        this._zaktif_alt_tesis = _zaktif_alt_tesis;
        this._zaktif_tesis = _zaktif_tesis;
        this._zsurum = _zsurum;
        this._zkullaniciadi = _zkullaniciadi;
        this._zsifre = _zsifre;
        this.aktif_sunucu = aktif_sunucu;
        this.aktif_kullanici = aktif_kullanici;
        this.isletme = isletme;
        this.depo_silo_secimi = depo_silo_secimi;
        this.depo_turu = depo_turu;
        this.depo_silo_secimi_kontrol = depo_silo_secimi_kontrol;
    }
}
