package com.etimaden.request;

import com.etimaden.persosclass.Zayi;
import com.etimaden.persosclass.Zayi_urun;

import java.util.List;

public class request_sevkiyat_zayi_zayiurun_list_zayiurun {

    private String _zsunucu_ip_adresi="";
    private String _zaktif_alt_tesis;
    private String _zaktif_tesis ;
    private String _zsurum ;
    private String _zkullaniciadi ;
    private String _zsifre;
    private String aktif_sunucu;
    private String aktif_kullanici;

    private Zayi sevk;
    private Zayi_urun depo_urun;
    private List<Zayi_urun> urun_listesi;

    public request_sevkiyat_zayi_zayiurun_list_zayiurun() {
    }

    public request_sevkiyat_zayi_zayiurun_list_zayiurun(String _zsunucu_ip_adresi, String _zaktif_alt_tesis, String _zaktif_tesis, String _zsurum, String _zkullaniciadi, String _zsifre, String aktif_sunucu, String aktif_kullanici, Zayi sevk, Zayi_urun depo_urun, List<Zayi_urun> urun_listesi) {
        this._zsunucu_ip_adresi = _zsunucu_ip_adresi;
        this._zaktif_alt_tesis = _zaktif_alt_tesis;
        this._zaktif_tesis = _zaktif_tesis;
        this._zsurum = _zsurum;
        this._zkullaniciadi = _zkullaniciadi;
        this._zsifre = _zsifre;
        this.aktif_sunucu = aktif_sunucu;
        this.aktif_kullanici = aktif_kullanici;
        this.sevk = sevk;
        this.depo_urun = depo_urun;
        this.urun_listesi = urun_listesi;
    }

    public String get_zsunucu_ip_adresi() {
        return _zsunucu_ip_adresi;
    }

    public void set_zsunucu_ip_adresi(String _zsunucu_ip_adresi) {
        this._zsunucu_ip_adresi = _zsunucu_ip_adresi;
    }

    public String get_zaktif_alt_tesis() {
        return _zaktif_alt_tesis;
    }

    public void set_zaktif_alt_tesis(String _zaktif_alt_tesis) {
        this._zaktif_alt_tesis = _zaktif_alt_tesis;
    }

    public String get_zaktif_tesis() {
        return _zaktif_tesis;
    }

    public void set_zaktif_tesis(String _zaktif_tesis) {
        this._zaktif_tesis = _zaktif_tesis;
    }

    public String get_zsurum() {
        return _zsurum;
    }

    public void set_zsurum(String _zsurum) {
        this._zsurum = _zsurum;
    }

    public String get_zkullaniciadi() {
        return _zkullaniciadi;
    }

    public void set_zkullaniciadi(String _zkullaniciadi) {
        this._zkullaniciadi = _zkullaniciadi;
    }

    public String get_zsifre() {
        return _zsifre;
    }

    public void set_zsifre(String _zsifre) {
        this._zsifre = _zsifre;
    }

    public String getAktif_sunucu() {
        return aktif_sunucu;
    }

    public void setAktif_sunucu(String aktif_sunucu) {
        this.aktif_sunucu = aktif_sunucu;
    }

    public String getAktif_kullanici() {
        return aktif_kullanici;
    }

    public void setAktif_kullanici(String aktif_kullanici) {
        this.aktif_kullanici = aktif_kullanici;
    }

    public Zayi getSevk() {
        return sevk;
    }

    public void setSevk(Zayi sevk) {
        this.sevk = sevk;
    }

    public Zayi_urun getDepo_urun() {
        return depo_urun;
    }

    public void setDepo_urun(Zayi_urun depo_urun) {
        this.depo_urun = depo_urun;
    }

    public List<Zayi_urun> getUrun_listesi() {
        return urun_listesi;
    }

    public void setUrun_listesi(List<Zayi_urun> urun_listesi) {
        this.urun_listesi = urun_listesi;
    }
}
