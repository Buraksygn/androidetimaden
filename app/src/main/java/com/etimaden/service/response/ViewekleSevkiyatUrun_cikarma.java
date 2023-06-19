package com.etimaden.service.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ViewekleSevkiyatUrun_cikarma  implements Serializable {

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

    public void set_zSonuc(String v_Gelen) {
        _zSonuc = _zSonuc;
    }

}
