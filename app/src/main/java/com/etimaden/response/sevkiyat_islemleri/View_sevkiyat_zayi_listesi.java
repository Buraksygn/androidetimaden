package com.etimaden.response.sevkiyat_islemleri;

import com.etimaden.cResponseResult.Sevkiyat_isemri;
import com.etimaden.persosclass.Zayi;

import java.util.List;

public class View_sevkiyat_zayi_listesi {

    public String _zHataAciklama = "";
    public String _zAciklama = "";
    public String _zSonuc = "";

    public List<Zayi> _zayi_listesi;

    public View_sevkiyat_zayi_listesi() {
    }

    public View_sevkiyat_zayi_listesi(String _zHataAciklama, String _zAciklama, String _zSonuc, List<Zayi> _zayi_listesi) {
        this._zHataAciklama = _zHataAciklama;
        this._zAciklama = _zAciklama;
        this._zSonuc = _zSonuc;
        this._zayi_listesi = _zayi_listesi;
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

    public List<Zayi> get_zayi_listesi() {
        return _zayi_listesi;
    }

    public void set_zayi_listesi(List<Zayi> _zayi_listesi) {
        this._zayi_listesi = _zayi_listesi;
    }
}
