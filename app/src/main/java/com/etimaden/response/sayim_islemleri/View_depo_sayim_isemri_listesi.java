package com.etimaden.response.sayim_islemleri;

import com.etimaden.persosclass.Depo_sayım_isemri;
import com.etimaden.persosclass.malzeme_sayim_isemri;

import java.util.List;

public class View_depo_sayim_isemri_listesi {

    public String _zHataAciklama = "";
    public String _zAciklama = "";
    public String _zSonuc = "";

    public List<Depo_sayım_isemri> _depo_sayim_isemri_listesi;

    public View_depo_sayim_isemri_listesi() {
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

    public List<Depo_sayım_isemri> get_depo_sayim_isemri_listesi() {
        return _depo_sayim_isemri_listesi;
    }

    public void set_depo_sayim_isemri_listesi(List<Depo_sayım_isemri> _depo_sayim_isemri_listesi) {
        this._depo_sayim_isemri_listesi = _depo_sayim_isemri_listesi;
    }
}
