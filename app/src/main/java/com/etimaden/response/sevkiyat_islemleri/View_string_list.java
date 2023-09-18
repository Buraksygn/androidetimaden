package com.etimaden.response.sevkiyat_islemleri;

import com.etimaden.cResponseResult.Urun_sevkiyat;

import java.util.List;

public class View_string_list {
    public String _zHataAciklama = "";
    public String _zAciklama = "";
    public String _zSonuc = "";

    public List<String> _list;

    public View_string_list() {
    }

    public View_string_list(String _zHataAciklama, String _zAciklama, String _zSonuc, List<String> _list) {
        this._zHataAciklama = _zHataAciklama;
        this._zAciklama = _zAciklama;
        this._zSonuc = _zSonuc;
        this._list = _list;
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

    public List<String> get_list() {
        return _list;
    }

    public void set_list(List<String> _list) {
        this._list = _list;
    }
}
