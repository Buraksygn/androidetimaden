package com.etimaden.response.sevkiyat_islemleri;

import com.etimaden.persosclass.Arac;

public class View_arac2 {
    public String _zHataAciklama = "";
    public String _zAciklama = "";
    public String _zSonuc = "";

    public Arac _zarac;

    public View_arac2() {
    }

    public View_arac2(String _zHataAciklama, String _zAciklama, String _zSonuc, Arac _zarac) {
        this._zHataAciklama = _zHataAciklama;
        this._zAciklama = _zAciklama;
        this._zSonuc = _zSonuc;
        this._zarac = _zarac;
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

    public Arac get_zarac() {
        return _zarac;
    }

    public void set_zarac(Arac _zarac) {
        this._zarac = _zarac;
    }
}
