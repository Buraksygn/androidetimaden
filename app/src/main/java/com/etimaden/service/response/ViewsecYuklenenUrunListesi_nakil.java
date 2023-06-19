package com.etimaden.service.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ViewsecYuklenenUrunListesi_nakil implements Serializable {

    @SerializedName("_zHataAciklama")
    private String _zHataAciklama;

    @SerializedName("_zAciklama")
    private String _zAciklama;

    @SerializedName("_zSonuc")
    private String _zSonuc;

    @SerializedName("_zDizi")
    private List<responseUrun_tag> _zDizi ;

    public String get_zHataAciklama() {
        return _zHataAciklama;
    }

    public String get_zAciklama() {
        return _zAciklama;
    }

    public String get_zSonuc() {
        return _zSonuc;
    }

    public List<responseUrun_tag> get_zDizi() {
        return _zDizi;
    }
}
