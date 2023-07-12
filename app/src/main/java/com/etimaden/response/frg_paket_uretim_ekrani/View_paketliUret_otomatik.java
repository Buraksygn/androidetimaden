package com.etimaden.response.frg_paket_uretim_ekrani;

public class View_paketliUret_otomatik {

    public String _zHataAciklama = "";
    public String _zAciklama = "";
    public String _zSonuc = "";

    public Boolean _bSonuc =false;


    public View_paketliUret_otomatik() {
    }

    public View_paketliUret_otomatik(String _zHataAciklama, String _zAciklama, String _zSonuc, Boolean _bSonuc) {
        this._zHataAciklama = _zHataAciklama;
        this._zAciklama = _zAciklama;
        this._zSonuc = _zSonuc;
        this._bSonuc = _bSonuc;
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

    public Boolean get_bSonuc() {
        return _bSonuc;
    }

    public void set_bSonuc(Boolean _bSonuc) {
        this._bSonuc = _bSonuc;
    }
}
