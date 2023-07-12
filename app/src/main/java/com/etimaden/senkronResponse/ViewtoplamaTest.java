package com.etimaden.senkronResponse;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ViewtoplamaTest implements Serializable {

    @SerializedName("_zHataAciklama")
    public String _zHataAciklama = "";

    @SerializedName("_zAciklama")
    public String _zAciklama = "";

    @SerializedName("_zSonuc")
    public String _zSonuc = "";

    @SerializedName("_toplam")
    public int _toplam;

    public ViewtoplamaTest() {
    }

    public ViewtoplamaTest(String _zHataAciklama, String _zAciklama, String _zSonuc, int _toplam)
    {
        this._zHataAciklama = _zHataAciklama;
        this._zAciklama = _zAciklama;
        this._zSonuc = _zSonuc;
        this._toplam = _toplam;
    }

    public void set_zHataAciklama(String _zHataAciklama) {
        this._zHataAciklama = _zHataAciklama;
    }

    public void set_zAciklama(String _zAciklama) {
        this._zAciklama = _zAciklama;
    }

    public void set_zSonuc(String _zSonuc) {
        this._zSonuc = _zSonuc;
    }

    public void set_toplam(int _toplam) {
        this._toplam = _toplam;
    }

    public String get_zHataAciklama() {
        return _zHataAciklama;
    }

    public String get_zAciklama() {
        return _zAciklama;
    }

    public String get_zSonuc() {
        return _zSonuc;
    }

    public int get_toplam() {
        return _toplam;
    }
}
