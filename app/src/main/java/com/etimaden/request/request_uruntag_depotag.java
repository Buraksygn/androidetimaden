package com.etimaden.request;

import com.etimaden.persosclass.DEPOTag;
import com.etimaden.persosclass.Urun_tag;

public class request_uruntag_depotag {

    private String _zsunucu_ip_adresi="";
    private String _zaktif_alt_tesis;
    private String _zaktif_tesis ;
    private String _zsurum ;
    private String _zkullaniciadi ;
    private String _zsifre;
    private String aktif_sunucu;
    private String aktif_kullanici;


    private Urun_tag urun;
    private DEPOTag depo;

    public request_uruntag_depotag() {
    }

    public request_uruntag_depotag(String _zsunucu_ip_adresi, String _zaktif_alt_tesis, String _zaktif_tesis, String _zsurum, String _zkullaniciadi, String _zsifre, String aktif_sunucu, String aktif_kullanici, Urun_tag urun, DEPOTag depo) {
        this._zsunucu_ip_adresi = _zsunucu_ip_adresi;
        this._zaktif_alt_tesis = _zaktif_alt_tesis;
        this._zaktif_tesis = _zaktif_tesis;
        this._zsurum = _zsurum;
        this._zkullaniciadi = _zkullaniciadi;
        this._zsifre = _zsifre;
        this.aktif_sunucu = aktif_sunucu;
        this.aktif_kullanici = aktif_kullanici;
        this.urun = urun;
        this.depo = depo;
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

    public Urun_tag getUrun() {
        return urun;
    }

    public void setUrun(Urun_tag urun) {
        this.urun = urun;
    }

    public DEPOTag getDepo() {
        return depo;
    }

    public void setDepo(DEPOTag depo) {
        this.depo = depo;
    }
}
