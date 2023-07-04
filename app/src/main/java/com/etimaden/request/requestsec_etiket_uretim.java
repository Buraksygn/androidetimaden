package com.etimaden.request;

public class requestsec_etiket_uretim {

    private String _zsunucu_ip_adresi;
    private String _zaktif_alt_tesis;
    private String _zaktif_tesis ;
    private String _zsurum ;
    private String _zkullaniciadi ;
    private String _zsifre;
    private String aktif_sunucu;
    private String aktif_kullanici;


    private String _etiket;

    public requestsec_etiket_uretim(){}


    public requestsec_etiket_uretim(
            String v_zsunucu_ip_adresi,
            String v_zaktif_alt_tesis,
            String v_zaktif_tesis,
            String v_zsurum,
            String v_zkullaniciadi,
            String v_zsifre,
            String vaktif_sunucu,
            String vaktif_kullanici,
            String v_etiket) {

        _zsunucu_ip_adresi = v_zsunucu_ip_adresi;
        _zaktif_alt_tesis = v_zaktif_alt_tesis;
        _zaktif_tesis = v_zaktif_tesis;
        _zsurum = v_zsurum;
        _zkullaniciadi = v_zkullaniciadi;
        _zsifre = v_zsifre;
        aktif_sunucu = vaktif_sunucu;
        aktif_kullanici = vaktif_kullanici;


        _etiket = v_etiket;

    }

    //region standart değerler için get ve set  fonksiyonları
    public String get_zsunucu_ip_adresi(){return _zsunucu_ip_adresi;}
    public String get_zaktif_alt_tesis(){return _zaktif_alt_tesis;}
    public String get_zaktif_tesis(){return _zaktif_tesis;}
    public String get_zsurum(){return _zsurum;}
    public String get_zkullaniciadi(){return _zkullaniciadi;}
    public String get_zsifre(){return _zsifre;}
    public String getaktif_sunucu(){return aktif_sunucu;}
    public String getaktif_kullanici(){return aktif_kullanici;}

    public void set_zsunucu_ip_adresi(String v_zsunucu_ip_adresi){_zsunucu_ip_adresi = v_zsunucu_ip_adresi;}
    public void set_zaktif_alt_tesis(String v_zaktif_alt_tesis){_zaktif_alt_tesis = v_zaktif_alt_tesis;}
    public void set_zaktif_tesis(String v_zaktif_tesis){_zaktif_tesis = v_zaktif_tesis;}
    public void set_zsurum(String v_zsurum){_zsurum = v_zsurum;}
    public void set_zkullaniciadi(String v_zkullaniciadi){_zkullaniciadi = v_zkullaniciadi;}
    public void set_zsifre(String v_zsifre){_zsifre = v_zsifre;}
    public void setaktif_sunucu( String vaktif_sunucu){aktif_sunucu = vaktif_sunucu;}
    public void setaktif_kullanici(String vaktif_kullanici){aktif_kullanici = vaktif_kullanici;}

    //
    public String get_etiket(){return _etiket;}
    public void set_etiket(String v_etiket){ _etiket = v_etiket;}
}
