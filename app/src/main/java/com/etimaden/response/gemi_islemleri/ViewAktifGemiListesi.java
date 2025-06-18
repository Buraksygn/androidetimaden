package com.etimaden.response.gemi_islemleri;

import com.etimaden.persosclass.DEPOTag;
import com.etimaden.persosclass.Gemi;

import java.util.List;

public class ViewAktifGemiListesi {

    private String _zHataAciklama = "";
    private String _zAciklama = "";
    private String _zSonuc = "";

    private List<Gemi> _gemiListesi;

    public ViewAktifGemiListesi(String _zHataAciklama, String _zAciklama, String _zSonuc, List<Gemi> _gemiListesi) {
        this._zHataAciklama = _zHataAciklama;
        this._zAciklama = _zAciklama;
        this._zSonuc = _zSonuc;
        this._gemiListesi = _gemiListesi;
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

    public List<Gemi> getGemiListesi() {
        return _gemiListesi;
    }


}
