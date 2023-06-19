package com.etimaden.service.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class etiket_no  implements Serializable {

    @SerializedName("eti_yil")
    private String eti_yil;

    @SerializedName("eti_ay")
    private String eti_ay;

    @SerializedName("eti_gun")
    private String eti_gun;

    @SerializedName("eti_isletme")
    private String eti_isletme;

    @SerializedName("eti_sirano")
    private String eti_sirano;

    public String geteti_yil() { return eti_yil;}
    public String geteti_ay() { return eti_ay;}
    public String geteti_gun() { return eti_gun;}
    public String geteti_isletme() { return eti_isletme;}
    public String geteti_sirano() { return eti_sirano;}

}
