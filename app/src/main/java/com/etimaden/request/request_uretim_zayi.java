package com.etimaden.request;

import com.etimaden.persosclass.Urun_tag;

public class request_uretim_zayi {
    private String _zsunucu_ip_adresi="";
    private String _zaktif_alt_tesis;
    private String _zaktif_tesis ;
    private String _zsurum ;
    private String _zkullaniciadi ;
    private String _zsifre;
    private String aktif_sunucu;
    private String aktif_kullanici;

    private Urun_tag _Urun_tag;
    private String _islem_turu;

    public request_uretim_zayi() {
    }

    public request_uretim_zayi(String _zsunucu_ip_adresi, String _zaktif_alt_tesis, String _zaktif_tesis, String _zsurum, String _zkullaniciadi, String _zsifre, String aktif_sunucu, String aktif_kullanici, Urun_tag _Urun_tag, String _islem_turu) {
        this._zsunucu_ip_adresi = _zsunucu_ip_adresi;
        this._zaktif_alt_tesis = _zaktif_alt_tesis;
        this._zaktif_tesis = _zaktif_tesis;
        this._zsurum = _zsurum;
        this._zkullaniciadi = _zkullaniciadi;
        this._zsifre = _zsifre;
        this.aktif_sunucu = aktif_sunucu;
        this.aktif_kullanici = aktif_kullanici;
        this._Urun_tag = _Urun_tag;
        this._islem_turu = _islem_turu;
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

    public Urun_tag get_Urun_tag() {
        return _Urun_tag;
    }

    public void set_Urun_tag(Urun_tag _Urun_tag) {
        this._Urun_tag = _Urun_tag;
    }

    public String get_islem_turu() {
        return _islem_turu;
    }

    public void set_islem_turu(String _islem_turu) {
        this._islem_turu = _islem_turu;
    }
}
