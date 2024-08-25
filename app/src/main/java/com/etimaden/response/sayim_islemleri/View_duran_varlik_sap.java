package com.etimaden.response.sayim_islemleri;

import com.etimaden.persosclass.Duran_Varlik_sap;

public class View_duran_varlik_sap {
    public String _zHataAciklama = "";
    public String _zAciklama = "";
    public String _zSonuc = "";

    public Duran_Varlik_sap _duran_varlik_sap;

    public View_duran_varlik_sap() {
    }

    public View_duran_varlik_sap(String _zHataAciklama, String _zAciklama, String _zSonuc, Duran_Varlik_sap _duran_varlik_sap) {
        this._zHataAciklama = _zHataAciklama;
        this._zAciklama = _zAciklama;
        this._zSonuc = _zSonuc;
        this._duran_varlik_sap = _duran_varlik_sap;
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

    public Duran_Varlik_sap get_duran_varlik_sap() {
        return _duran_varlik_sap;
    }

    public void set_duran_varlik_sap(Duran_Varlik_sap _duran_varlik_sap) {
        this._duran_varlik_sap = _duran_varlik_sap;
    }
}
