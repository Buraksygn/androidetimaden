package com.etimaden.response.frg_paket_uretim_ekrani;

import com.etimaden.persosclass.etiket_no;



public class View_sec_etiket_no {

    private String _zHataAciklama = "";
    private String _zAciklama = "";
    private String _zSonuc = "";

    private etiket_no _tag;

    public View_sec_etiket_no(String _zHataAciklama, String _zAciklama, String _zSonuc, etiket_no _tag) {
        this._zHataAciklama = _zHataAciklama;
        this._zAciklama = _zAciklama;
        this._zSonuc = _zSonuc;
        this._tag = _tag;
    }

    public View_sec_etiket_no() {
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

    public etiket_no get_tag() {
        return _tag;
    }

    public void set_tag(etiket_no _tag) {
        this._tag = _tag;
    }



}
