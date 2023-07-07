package com.etimaden.response.frg_paket_uretim_ekrani;

public class View_yari_otomatik_paket_kontrol_et {

    public View_yari_otomatik_paket_kontrol_et() {
    }

    public View_yari_otomatik_paket_kontrol_et(String _zHataAciklama, String _zAciklama, String _zSonuc, String _lot) {
        this._zHataAciklama = _zHataAciklama;
        this._zAciklama = _zAciklama;
        this._zSonuc = _zSonuc;
        this._lot = _lot;
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

    public String get_lot() {
        return _lot;
    }

    public void set_lot(String _lot) {
        this._lot = _lot;
    }

    private String _zHataAciklama = "";
    private String _zAciklama = "";
    private String _zSonuc = "";
    private String _lot = "";
}
