package com.etimaden.request;

public class request_sevkiyat_rota_agirlik_konteyner {

    private String _zsunucu_ip_adresi="";
    private String _zaktif_alt_tesis;
    private String _zaktif_tesis ;
    private String _zsurum ;
    private String _zkullaniciadi ;
    private String _zsifre;
    private String aktif_sunucu;
    private String aktif_kullanici;

    private String _rota_id;
    private String _agirlik;
    private String _konteyner;

    public request_sevkiyat_rota_agirlik_konteyner() {
    }

    public request_sevkiyat_rota_agirlik_konteyner(String _zsunucu_ip_adresi, String _zaktif_alt_tesis, String _zaktif_tesis, String _zsurum, String _zkullaniciadi, String _zsifre, String aktif_sunucu, String aktif_kullanici, String _rota_id, String _agirlik, String _konteyner) {
        this._zsunucu_ip_adresi = _zsunucu_ip_adresi;
        this._zaktif_alt_tesis = _zaktif_alt_tesis;
        this._zaktif_tesis = _zaktif_tesis;
        this._zsurum = _zsurum;
        this._zkullaniciadi = _zkullaniciadi;
        this._zsifre = _zsifre;
        this.aktif_sunucu = aktif_sunucu;
        this.aktif_kullanici = aktif_kullanici;
        this._rota_id = _rota_id;
        this._agirlik = _agirlik;
        this._konteyner = _konteyner;
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

    public String get_rota_id() {
        return _rota_id;
    }

    public void set_rota_id(String _rota_id) {
        this._rota_id = _rota_id;
    }

    public String get_agirlik() {
        return _agirlik;
    }

    public void set_agirlik(String _agirlik) {
        this._agirlik = _agirlik;
    }

    public String get_konteyner() {
        return _konteyner;
    }

    public void set_konteyner(String _konteyner) {
        this._konteyner = _konteyner;
    }
}
