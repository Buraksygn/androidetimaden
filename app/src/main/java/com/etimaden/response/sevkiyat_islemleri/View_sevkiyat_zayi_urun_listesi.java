package com.etimaden.response.sevkiyat_islemleri;

import com.etimaden.persosclass.Zayi;
import com.etimaden.persosclass.Zayi_urun;

import java.util.List;

public class View_sevkiyat_zayi_urun_listesi {

    public String _zHataAciklama = "";
    public String _zAciklama = "";
    public String _zSonuc = "";

    public List<Zayi_urun> _zayi_urun_listesi;

    public View_sevkiyat_zayi_urun_listesi() {
    }

    public View_sevkiyat_zayi_urun_listesi(String _zHataAciklama, String _zAciklama, String _zSonuc, List<Zayi_urun> _zayi_urun_listesi) {
        this._zHataAciklama = _zHataAciklama;
        this._zAciklama = _zAciklama;
        this._zSonuc = _zSonuc;
        this._zayi_urun_listesi = _zayi_urun_listesi;
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

    public List<Zayi_urun> get_zayi_urun_listesi() {
        return _zayi_urun_listesi;
    }

    public void set_zayi_urun_listesi(List<Zayi_urun> _zayi_urun_listesi) {
        this._zayi_urun_listesi = _zayi_urun_listesi;
    }
}
