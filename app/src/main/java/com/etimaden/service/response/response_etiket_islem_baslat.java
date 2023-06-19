package com.etimaden.service.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class response_etiket_islem_baslat  implements Serializable {
    @SerializedName("_zHataAciklama")
    private String _zHataAciklama;

    @SerializedName("_zAciklama")
    private String _zAciklama;

    @SerializedName("_zSonuc")
    private String _zSonuc;

    @SerializedName("_zEtiket")
    private uretim_etiket _zEtiket;

    public String get_zHataAciklama() {
        return _zHataAciklama;
    }

    public String get_zAciklama() {
        return _zAciklama;
    }

    public String get_zSonuc() {
        return _zSonuc;
    }

    public uretim_etiket get_zEtiket() { return _zEtiket;}
}
