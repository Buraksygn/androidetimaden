package com.etimaden.response.frg_paket_uretim_ekrani;

public class View_get_lot_toplami {

    public String _zHataAciklama = "";
    public String _zAciklama = "";
    public String _zSonuc = "";

    public String _lot_miktar = "";


    public View_get_lot_toplami() {
    }

    public View_get_lot_toplami(String _zHataAciklama, String _zAciklama, String _zSonuc, String _lot_miktar) {
        this._zHataAciklama = _zHataAciklama;
        this._zAciklama = _zAciklama;
        this._zSonuc = _zSonuc;
        this._lot_miktar = _lot_miktar;
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

    public String get_lot_miktar() {
        return _lot_miktar;
    }

    public void set_lot_miktar(String _lot_miktar) {
        this._lot_miktar = _lot_miktar;
    }
}
