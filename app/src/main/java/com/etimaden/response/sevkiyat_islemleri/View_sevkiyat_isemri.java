package com.etimaden.response.sevkiyat_islemleri;

import com.etimaden.cResponseResult.Sevkiyat_isemri;

public class View_sevkiyat_isemri {
    public String _zHataAciklama = "";
    public String _zAciklama = "";
    public String _zSonuc = "";

    public Sevkiyat_isemri _result;

    public View_sevkiyat_isemri() {
    }

    public View_sevkiyat_isemri(String _zHataAciklama, String _zAciklama, String _zSonuc, Sevkiyat_isemri _result) {
        this._zHataAciklama = _zHataAciklama;
        this._zAciklama = _zAciklama;
        this._zSonuc = _zSonuc;
        this._result = _result;
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

    public Sevkiyat_isemri get_result() {
        return _result;
    }

    public void set_result(Sevkiyat_isemri _result) {
        this._result = _result;
    }
}
