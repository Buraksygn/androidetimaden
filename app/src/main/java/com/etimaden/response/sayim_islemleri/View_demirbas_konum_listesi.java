package com.etimaden.response.sayim_islemleri;

import com.etimaden.persosclass.Demirbas_Konum;
import com.etimaden.persosclass.malzeme_sayim_isemri;

import java.util.List;

public class View_demirbas_konum_listesi {

    public String _zHataAciklama = "";
    public String _zAciklama = "";
    public String _zSonuc = "";

    public List<Demirbas_Konum> _demirbas_konum_listesi;

    public View_demirbas_konum_listesi() {
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

    public List<Demirbas_Konum> get_demirbas_konum_listesi() {
        return _demirbas_konum_listesi;
    }

    public void set_demirbas_konum_listesi(List<Demirbas_Konum> _demirbas_konum_listesi) {
        this._demirbas_konum_listesi = _demirbas_konum_listesi;
    }
}
