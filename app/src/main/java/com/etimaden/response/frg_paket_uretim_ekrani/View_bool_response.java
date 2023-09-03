package com.etimaden.response.frg_paket_uretim_ekrani;

public class View_bool_response {
    public String _zHataAciklama = "";
    public String _zAciklama = "";
    public String _zSonuc = "";

    public Boolean _result;

    public View_bool_response() {
    }

    public View_bool_response(String _zHataAciklama, String _zAciklama, String _zSonuc, boolean _result) {
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

    public boolean get_result() {
        return _result;
    }

    public void set_result(boolean _result) {
        this._result = _result;
    }
}
