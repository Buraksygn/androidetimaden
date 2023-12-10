package com.etimaden.request;

import com.etimaden.cResponseResult.Sevkiyat_isemri;

import java.util.List;

public class request_integer_sevkiyat_isemri_stringlist {

    public String _zsunucu_ip_adresi;
    public String _zaktif_alt_tesis;
    public String _zaktif_tesis ;
    public String _zsurum ;
    public String _zkullaniciadi ;
    public String _zsifre;
    public String aktif_sunucu;
    public String aktif_kullanici;

    private Integer _intValue;
    private Sevkiyat_isemri _sevkiyat_isemri;
    private List<String> _stringList;

    public request_integer_sevkiyat_isemri_stringlist() {
    }

    public request_integer_sevkiyat_isemri_stringlist(String _zsunucu_ip_adresi, String _zaktif_alt_tesis, String _zaktif_tesis, String _zsurum, String _zkullaniciadi, String _zsifre, String aktif_sunucu, String aktif_kullanici, Integer _intValue, Sevkiyat_isemri _sevkiyat_isemri, List<String> _stringList) {
        this._zsunucu_ip_adresi = _zsunucu_ip_adresi;
        this._zaktif_alt_tesis = _zaktif_alt_tesis;
        this._zaktif_tesis = _zaktif_tesis;
        this._zsurum = _zsurum;
        this._zkullaniciadi = _zkullaniciadi;
        this._zsifre = _zsifre;
        this.aktif_sunucu = aktif_sunucu;
        this.aktif_kullanici = aktif_kullanici;
        this._intValue = _intValue;
        this._sevkiyat_isemri = _sevkiyat_isemri;
        this._stringList = _stringList;
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

    public Integer get_intValue() {
        return _intValue;
    }

    public void set_intValue(Integer _intValue) {
        this._intValue = _intValue;
    }

    public Sevkiyat_isemri get_sevkiyat_isemri() {
        return _sevkiyat_isemri;
    }

    public void set_sevkiyat_isemri(Sevkiyat_isemri _sevkiyat_isemri) {
        this._sevkiyat_isemri = _sevkiyat_isemri;
    }

    public List<String> get_stringList() {
        return _stringList;
    }

    public void set_stringList(List<String> _stringList) {
        this._stringList = _stringList;
    }
}
