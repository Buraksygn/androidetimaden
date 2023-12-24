package com.etimaden.response.sayim_islemleri;

import com.etimaden.persosclass.Urun_tag;
import com.etimaden.persosclass.malzeme_sayim_isemri;

import java.util.List;

public class View_malzeme_sayim_isemri_listesi {

    public String _zHataAciklama = "";
    public String _zAciklama = "";
    public String _zSonuc = "";

    public List<malzeme_sayim_isemri> _malzeme_sayim_isemri_listesi;

    public View_malzeme_sayim_isemri_listesi() {
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

    public List<malzeme_sayim_isemri> get_malzeme_sayim_isemri_listesi() {
        return _malzeme_sayim_isemri_listesi;
    }

    public void set_malzeme_sayim_isemri_listesi(List<malzeme_sayim_isemri> _malzeme_sayim_isemri_listesi) {
        this._malzeme_sayim_isemri_listesi = _malzeme_sayim_isemri_listesi;
    }
}
