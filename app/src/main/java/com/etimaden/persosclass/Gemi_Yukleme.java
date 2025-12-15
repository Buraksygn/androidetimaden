package com.etimaden.persosclass;

public class Gemi_Yukleme {
    public String toplam_miktar ="";
    public String yuklenen_miktar ="";
    public String kalan_miktar ="";

    public Gemi_Yukleme() {

    }

    public Gemi_Yukleme(String _toplamMiktar , String _yuklenenMiktar, String _kalanMiktar){
        this.toplam_miktar = _toplamMiktar;
        this.yuklenen_miktar = _yuklenenMiktar;
        this.kalan_miktar = _kalanMiktar;
    }

    public String getToplamMiktar() {
        return toplam_miktar;
    }
    public String getYuklenenMiktar() {
        return yuklenen_miktar;
    }
    public String getKalanMiktar() { return kalan_miktar;}
}
