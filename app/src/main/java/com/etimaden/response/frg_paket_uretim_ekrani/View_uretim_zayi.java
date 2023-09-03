package com.etimaden.response.frg_paket_uretim_ekrani;

public class View_uretim_zayi {
    public String _zHataAciklama = "";
    public String _zAciklama = "";
    public String _zSonuc = "";

    public Boolean _uretim_zayi;

    public View_uretim_zayi() {
    }

    public View_uretim_zayi(String _zHataAciklama, String _zAciklama, String _zSonuc, boolean _uretim_zayi) {
        this._zHataAciklama = _zHataAciklama;
        this._zAciklama = _zAciklama;
        this._zSonuc = _zSonuc;
        this._uretim_zayi = _uretim_zayi;
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

    public boolean get_uretim_zayi() {
        return _uretim_zayi;
    }

    public void set_uretim_zayi(boolean _uretim_zayi) {
        this._uretim_zayi = _uretim_zayi;
    }
}
