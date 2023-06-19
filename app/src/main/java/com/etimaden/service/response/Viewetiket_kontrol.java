package com.etimaden.service.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Viewetiket_kontrol  implements Serializable {

    @SerializedName("_zHataAciklama")
    private String _zHataAciklama;

    @SerializedName("_zAciklama")
    private String _zAciklama;

    @SerializedName("_zSonuc")
    private String _zSonuc;

    public String get_zHataAciklama() {
        return _zHataAciklama;
    }
    public String get_zAciklama() {
        return _zAciklama;
    }
    public String get_zSonuc() {
        return _zSonuc;
    }
}
