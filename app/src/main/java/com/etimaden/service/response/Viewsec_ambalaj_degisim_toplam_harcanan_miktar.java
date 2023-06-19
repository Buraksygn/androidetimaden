package com.etimaden.service.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Viewsec_ambalaj_degisim_toplam_harcanan_miktar implements Serializable {

    @SerializedName("_zHataAciklama")
    private String _zHataAciklama;

    @SerializedName("_zAciklama")
    private String _zAciklama;

    @SerializedName("_zSonuc")
    private String _zSonuc;

    @SerializedName("_miktar")
    private String _miktar;

    public String get_zHataAciklama() {
        return _zHataAciklama;
    }
    public String get_zAciklama() {
        return _zAciklama;
    }
    public String get_zSonuc() {
        return _zSonuc;
    }
    public String get_miktar() { return _miktar; }

}
