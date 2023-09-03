package com.etimaden.cResponseResult;

import com.etimaden.persosclass.uretim_detay;

import java.util.List;

public class View_sec_sevk_miktar {

    public String _zHataAciklama = "";
    public String _zAciklama = "";
    public String _zSonuc = "";
    public List<String> _miktarlar;

    public View_sec_sevk_miktar() {
    }

    public View_sec_sevk_miktar(String _zHataAciklama, String _zAciklama, String _zSonuc, List<String> _miktarlar) {
        this._zHataAciklama = _zHataAciklama;
        this._zAciklama = _zAciklama;
        this._zSonuc = _zSonuc;
        this._miktarlar = _miktarlar;
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

    public List<String> get_miktarlar() {
        return _miktarlar;
    }

    public void set_miktarlar(List<String> _miktarlar) {
        this._miktarlar = _miktarlar;
    }
}
