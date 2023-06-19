package com.etimaden.service.request;

public class requestyari_otomatik_paket_kontrol_et {
    public String _zsunucu_ip_adresi;
    public String _zaktif_alt_tesis;
    public String _zaktif_tesis;
    public String _zsurum;
    public String _zkullaniciadi;
    public String _zsifre;
    public String aktif_sunucu;
    public String aktif_kullanici;


    public String serino;

    public requestyari_otomatik_paket_kontrol_et(){}


    public requestyari_otomatik_paket_kontrol_et(String v__zsunucu_ip_adresi,String v__zaktif_alt_tesis,String v__zaktif_tesis,String v__zsurum,String v__zkullaniciadi,String v__zsifre,String v_aktif_sunucu,String v_aktif_kullanici,String v_serino)
    {
        _zsunucu_ip_adresi = v__zsunucu_ip_adresi;
        _zaktif_alt_tesis = v__zaktif_alt_tesis;
        _zaktif_tesis = v__zaktif_tesis;
        _zsurum = v__zsurum;
        _zkullaniciadi = v__zkullaniciadi;
        _zsifre = v__zsifre;
        aktif_sunucu = v_aktif_sunucu;
        aktif_kullanici = v_aktif_kullanici;
        serino = v_serino;
    }
}
