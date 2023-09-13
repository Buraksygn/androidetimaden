package com.etimaden.response.sevkiyat_islemleri;

import com.etimaden.cResponseResult.Sevkiyat_isemri;
import com.etimaden.persosclass.cBekleyen_Arac_Listesi;

import java.util.List;

public class View_sevkiyat_bekleyen_arac_listesi {

    public String _zHataAciklama = "";
    public String _zAciklama = "";
    public String _zSonuc = "";

    public List<cBekleyen_Arac_Listesi> _bekleyen_arac_listesi;

    public View_sevkiyat_bekleyen_arac_listesi() {
    }

    public View_sevkiyat_bekleyen_arac_listesi(String _zHataAciklama, String _zAciklama, String _zSonuc, List<cBekleyen_Arac_Listesi> _bekleyen_arac_listesi) {
        this._zHataAciklama = _zHataAciklama;
        this._zAciklama = _zAciklama;
        this._zSonuc = _zSonuc;
        this._bekleyen_arac_listesi = _bekleyen_arac_listesi;
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

    public List<cBekleyen_Arac_Listesi> get_bekleyen_arac_listesi() {
        return _bekleyen_arac_listesi;
    }

    public void set_bekleyen_arac_listesi(List<cBekleyen_Arac_Listesi> _bekleyen_arac_listesi) {
        this._bekleyen_arac_listesi = _bekleyen_arac_listesi;
    }
}
