package com.etimaden.response.sevkiyat_islemleri;

import com.etimaden.cResponseResult.Sevkiyat_isemri;

import java.util.List;

public class View_sevkiyat_isemri_listesi {

    public String _zHataAciklama = "";
    public String _zAciklama = "";
    public String _zSonuc = "";

    public List<Sevkiyat_isemri> _sevk_listesi;

    public View_sevkiyat_isemri_listesi() {
    }

    public View_sevkiyat_isemri_listesi(String _zHataAciklama, String _zAciklama, String _zSonuc, List<Sevkiyat_isemri> _sevk_listesi) {
        this._zHataAciklama = _zHataAciklama;
        this._zAciklama = _zAciklama;
        this._zSonuc = _zSonuc;
        this._sevk_listesi = _sevk_listesi;
    }

    public String get_zHataAciklama() {
        return _zHataAciklama;
    }

    public void set_zHataAciklama(String _zHataAciklama) {
        this._zHataAciklama = _zHataAciklama;
    }

    public String get_zAciklama() {
        return _zAciklama;
    }

    public void set_zAciklama(String _zAciklama) {
        this._zAciklama = _zAciklama;
    }

    public String get_zSonuc() {
        return _zSonuc;
    }

    public void set_zSonuc(String _zSonuc) {
        this._zSonuc = _zSonuc;
    }

    public List<Sevkiyat_isemri> get_sevk_listesi() {
        return _sevk_listesi;
    }

    public void set_sevk_listesi(List<Sevkiyat_isemri> _sevk_listesi) {
        this._sevk_listesi = _sevk_listesi;
    }
}
