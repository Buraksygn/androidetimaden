package com.etimaden.response.frg_paket_uretim_ekrani;

import com.etimaden.persosclass.DEPOTag;

import java.util.List;

public class ViewsecDepoTanimlari
{
    public ViewsecDepoTanimlari(String _zHataAciklama, String _zAciklama, String _zSonuc, List<DEPOTag> _DepoListesi) {
        this._zHataAciklama = _zHataAciklama;
        this._zAciklama = _zAciklama;
        this._zSonuc = _zSonuc;
        this._DepoListesi = _DepoListesi;
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

    public List<DEPOTag> get_DepoListesi() {
        return _DepoListesi;
    }

    public void set_DepoListesi(List<DEPOTag> _DepoListesi) {
        this._DepoListesi = _DepoListesi;
    }

    private String _zHataAciklama = "";
    private String _zAciklama = "";
    private String _zSonuc = "";

    private List<DEPOTag> _DepoListesi;

}
