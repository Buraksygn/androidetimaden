package com.etimaden.response.frg_paket_uretim_ekrani;

import com.etimaden.persosclass.Urun_tag;

public class View_secEtiket {

    public String _zHataAciklama = "";
    public String _zAciklama = "";
    public String _zSonuc = "";

    public Urun_tag _tag;


    public View_secEtiket() {
    }

    public View_secEtiket(String _zHataAciklama, String _zAciklama, String _zSonuc, Urun_tag _tagtt) {
        this._zHataAciklama = _zHataAciklama;
        this._zAciklama = _zAciklama;
        this._zSonuc = _zSonuc;
        this._tag = _tagtt;
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

    public Urun_tag get_tag() {
        return _tag;
    }

    public void set_tag(Urun_tag _tagtt) {
        this._tag = _tagtt;
    }
}
