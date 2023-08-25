package com.etimaden.response.frg_paket_uretim_ekrani;

import com.etimaden.persosclass.uretim_detay;

import java.util.List;

public class View_sec_uretim_detay {

    public String _zHataAciklama = "";
    public String _zAciklama = "";
    public String _zSonuc = "";
    public List<uretim_detay> _uretim_detay_list;

    public View_sec_uretim_detay() {
    }

    public View_sec_uretim_detay(String _zHataAciklama, String _zAciklama, String _zSonuc, List<uretim_detay> _uretim_detay_list) {
        this._zHataAciklama = _zHataAciklama;
        this._zAciklama = _zAciklama;
        this._zSonuc = _zSonuc;
        this._uretim_detay_list = _uretim_detay_list;
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

    public List<uretim_detay> get_uretim_detay_list() {
        return _uretim_detay_list;
    }

    public void set_uretim_detay_list(List<uretim_detay> _uretim_detay_list) {
        this._uretim_detay_list = _uretim_detay_list;
    }
}
