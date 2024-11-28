package com.etimaden.response.sayim_islemleri;

import com.etimaden.persosclass.Demirbas_Konum;
import com.etimaden.persosclass.demirbas_sayim;

import java.util.List;

public class View_demirbas_sayim_listesi {

    public String _zHataAciklama = "";
    public String _zAciklama = "";
    public String _zSonuc = "";

    public List<demirbas_sayim> _demirbas_sayim_listesi;

    public View_demirbas_sayim_listesi() {
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

    public List<demirbas_sayim> get_demirbas_sayim_listesi() {
        return _demirbas_sayim_listesi;
    }

    public void set_demirbas_sayim_listesi(List<demirbas_sayim> _demirbas_sayim_listesi) {
        this._demirbas_sayim_listesi = _demirbas_sayim_listesi;
    }
}
