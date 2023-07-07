package com.etimaden.response.frg_paket_uretim_ekrani;

public class View_etiket_kontrol {

    private String _zHataAciklama = "";
    private String _zAciklama = "";
    private String _zSonuc = "";

    private String _durum="";

    public View_etiket_kontrol(String _zHataAciklama, String _zAciklama, String _zSonuc, String _durum) {
        this._zHataAciklama = _zHataAciklama;
        this._zAciklama = _zAciklama;
        this._zSonuc = _zSonuc;
        this._durum = _durum;
    }

    public View_etiket_kontrol() {
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

    public String get_durum() {
        return _durum;
    }

    public void set_durum(String _durum) {
        this._durum = _durum;
    }


}
