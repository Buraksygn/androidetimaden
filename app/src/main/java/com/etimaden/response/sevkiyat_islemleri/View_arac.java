package com.etimaden.response.sevkiyat_islemleri;

import com.etimaden.cResponseResult.Arac;
import com.etimaden.cResponseResult.Sevkiyat_isemri;

public class View_arac {
    public String _zHataAciklama = "";
    public String _zAciklama = "";
    public String _zSonuc = "";

    public Arac _result;

    public View_arac() {
    }

    public View_arac(String _zHataAciklama, String _zAciklama, String _zSonuc, Arac _result) {
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

    public Arac get_result() {
        return _result;
    }

    public void set_result(Arac _result) {
        this._result = _result;
    }
}
