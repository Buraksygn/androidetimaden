package com.etimaden.response.sayim_islemleri;

import com.etimaden.persosclass.Duran_Varlik_sap;
import com.etimaden.persosclass.Zimmet_sonuc_sap;

public class View_zimmet_sonuc_sap {
    public String _zHataAciklama = "";
    public String _zAciklama = "";
    public String _zSonuc = "";

    public Zimmet_sonuc_sap _zimmet_sonuc_sap;

    public View_zimmet_sonuc_sap() {
    }

    public View_zimmet_sonuc_sap(String _zHataAciklama, String _zAciklama, String _zSonuc, Zimmet_sonuc_sap _zimmet_sonuc_sap) {
        this._zHataAciklama = _zHataAciklama;
        this._zAciklama = _zAciklama;
        this._zSonuc = _zSonuc;
        this._zimmet_sonuc_sap = _zimmet_sonuc_sap;
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

    public Zimmet_sonuc_sap get_zimmet_sonuc_sap() {
        return _zimmet_sonuc_sap;
    }

    public void set_zimmet_sonuc_sap(Zimmet_sonuc_sap _zimmet_sonuc_sap) {
        this._zimmet_sonuc_sap = _zimmet_sonuc_sap;
    }
}
