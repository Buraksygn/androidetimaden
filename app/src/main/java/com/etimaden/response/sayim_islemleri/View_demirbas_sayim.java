package com.etimaden.response.sayim_islemleri;

import com.etimaden.persosclass.demirbas_sayim;

public class View_demirbas_sayim {
    public String _zHataAciklama = "";
    public String _zAciklama = "";
    public String _zSonuc = "";

    public demirbas_sayim _demirbas_sayim;

    public View_demirbas_sayim() {
    }

    public View_demirbas_sayim(String _zHataAciklama, String _zAciklama, String _zSonuc, demirbas_sayim _demirbas_sayim) {
        this._zHataAciklama = _zHataAciklama;
        this._zAciklama = _zAciklama;
        this._zSonuc = _zSonuc;
        this._demirbas_sayim = _demirbas_sayim;
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

    public demirbas_sayim get_demirbas_sayim() {
        return _demirbas_sayim;
    }

    public void set_demirbas_sayim(demirbas_sayim _demirbas_sayim) {
        this._demirbas_sayim = _demirbas_sayim;
    }
}
