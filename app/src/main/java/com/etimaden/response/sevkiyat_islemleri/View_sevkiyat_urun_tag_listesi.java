package com.etimaden.response.sevkiyat_islemleri;

import com.etimaden.cResponseResult.Sevkiyat_isemri;
import com.etimaden.persosclass.Urun_tag;

import java.util.List;

public class View_sevkiyat_urun_tag_listesi {

    public String _zHataAciklama = "";
    public String _zAciklama = "";
    public String _zSonuc = "";

    public List<Urun_tag> _urun_tag_listesi;

    public View_sevkiyat_urun_tag_listesi() {
    }

    public View_sevkiyat_urun_tag_listesi(String _zHataAciklama, String _zAciklama, String _zSonuc, List<Urun_tag> _urun_tag_listesi) {
        this._zHataAciklama = _zHataAciklama;
        this._zAciklama = _zAciklama;
        this._zSonuc = _zSonuc;
        this._urun_tag_listesi = _urun_tag_listesi;
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

    public List<Urun_tag> get_urun_tag_listesi() {
        return _urun_tag_listesi;
    }

    public void set_urun_tag_listesi(List<Urun_tag> _urun_tag_listesi) {
        this._urun_tag_listesi = _urun_tag_listesi;
    }
}
