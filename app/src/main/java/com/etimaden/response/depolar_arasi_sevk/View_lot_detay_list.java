package com.etimaden.response.depolar_arasi_sevk;

import com.etimaden.persosclass.lot_detay;

import java.util.List;

public class View_lot_detay_list {
    public String _zHataAciklama = "";
    public String _zAciklama = "";
    public String _zSonuc = "";

    public List<lot_detay> _list;

    public View_lot_detay_list() {
    }

    public View_lot_detay_list(String _zHataAciklama, String _zAciklama, String _zSonuc, List<lot_detay> _list) {
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

    public List<lot_detay> get_list() {
        return _list;
    }

    public void set_list(List<lot_detay> _list) {
        this._list = _list;
    }
}
