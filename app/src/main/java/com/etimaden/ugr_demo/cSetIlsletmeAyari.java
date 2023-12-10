package com.etimaden.ugr_demo;

import android.content.Context;

import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cSabitDegerler;

public class cSetIlsletmeAyari
{
    String aktif_kullanici="";
    String aktif_depo="";
    String aktif_alt_tesis="";
    String aktif_tesis="";
    String aktif_sunucu="";
    String aktif_isletmeesleme="";
    String baglanti_turu="";
    String sunucu_ip="";


    VeriTabani _myIslem;

    //fn_setTestGenelMerkez

    public void  fn_setTestGenelMerkez(Context v_Context)
    {
        aktif_kullanici = "andr_EmetIsletme";
        aktif_depo = "URN1";
        aktif_alt_tesis = "4001-01";
        aktif_tesis = "4001";
        aktif_sunucu = "002";
        aktif_isletmeesleme = "4001";
        baglanti_turu = "wifi";
        sunucu_ip = "192.168.9.129";

        _myIslem=new VeriTabani(v_Context);
        _myIslem.fn_Kayit();
        _myIslem.fn_BaslangicAyarlari(cSabitDegerler._sbtVerisyon , baglanti_turu, aktif_kullanici, aktif_depo, aktif_alt_tesis, aktif_tesis, aktif_sunucu, aktif_isletmeesleme, sunucu_ip);
    }



    public void  fn_setEmetIsletme(Context v_Context){
        aktif_kullanici = "andr_EmetIsletme";
        aktif_depo = "URN1";
        aktif_alt_tesis = "4001-01";
        aktif_tesis = "4001";
        aktif_sunucu = "002";
        aktif_isletmeesleme = "4001";
        baglanti_turu="wifi";
        sunucu_ip="10.112.24.24";

        _myIslem=new VeriTabani(v_Context);
        _myIslem.fn_Kayit();
        _myIslem.fn_BaslangicAyarlari(cSabitDegerler._sbtVerisyon , baglanti_turu, aktif_kullanici, aktif_depo, aktif_alt_tesis, aktif_tesis, aktif_sunucu, aktif_isletmeesleme, sunucu_ip);

    }

    public void fn_setEmetEspey(Context v_Context) {
        aktif_depo = "URN1";
        aktif_tesis = "4001";
        aktif_isletmeesleme = "4001";
        aktif_alt_tesis = "4001-02";
        aktif_sunucu = "002";
        aktif_kullanici = "andr_EmetEspey";
        baglanti_turu="wifi";
        sunucu_ip="10.112.24.24";

        _myIslem=new VeriTabani(v_Context);
        _myIslem.fn_Kayit();
        _myIslem.fn_BaslangicAyarlari(cSabitDegerler._sbtVerisyon,baglanti_turu, aktif_kullanici, aktif_depo, aktif_alt_tesis, aktif_tesis, aktif_sunucu, aktif_isletmeesleme, sunucu_ip);
    }

    public void fn_setEmetHisarcik(Context v_Context)
    {
        aktif_depo = "URN1";
        aktif_tesis = "4001";
        aktif_isletmeesleme = "4001";
        aktif_alt_tesis = "4001-03";
        aktif_sunucu = "002";
        aktif_kullanici = "andr_EmetHisarcik";
        baglanti_turu="wifi";
        sunucu_ip="10.112.24.24";

        _myIslem=new VeriTabani(v_Context);
        _myIslem.fn_Kayit();
        _myIslem.fn_BaslangicAyarlari(cSabitDegerler._sbtVerisyon,baglanti_turu, aktif_kullanici, aktif_depo, aktif_alt_tesis, aktif_tesis, aktif_sunucu, aktif_isletmeesleme, sunucu_ip);
    }

    public void fn_setEmetEmirler(Context v_Context)
    {
        aktif_depo = "URN1";
        aktif_tesis = "4002";
        aktif_isletmeesleme = "4001";
        aktif_alt_tesis = "4002-01";
        aktif_sunucu = "002";
        aktif_kullanici = "andr_EmetEmirler";
        baglanti_turu="wifi";
        sunucu_ip="10.112.24.24";

        _myIslem=new VeriTabani(v_Context);
        _myIslem.fn_Kayit();
        _myIslem.fn_BaslangicAyarlari(cSabitDegerler._sbtVerisyon,baglanti_turu, aktif_kullanici, aktif_depo, aktif_alt_tesis, aktif_tesis, aktif_sunucu, aktif_isletmeesleme, sunucu_ip);
    }

    public void fn_setBigadicIsletme(Context v_Context) {
        aktif_depo = "MUR1";
        aktif_tesis = "3001";
        aktif_alt_tesis = "3001-01";
        aktif_sunucu = "003";
        aktif_isletmeesleme = "3001";
        aktif_kullanici = "andr_BigadicIsletme";
        baglanti_turu="wifi";
        sunucu_ip="10.64.24.24";

        _myIslem=new VeriTabani(v_Context);
        _myIslem.fn_Kayit();
        _myIslem.fn_BaslangicAyarlari(cSabitDegerler._sbtVerisyon,baglanti_turu, aktif_kullanici, aktif_depo, aktif_alt_tesis, aktif_tesis, aktif_sunucu, aktif_isletmeesleme, sunucu_ip);
    }

    public void fn_setBigadicNusrat(Context v_Context) {
        aktif_depo = "MUR1";
        aktif_tesis = "3001";
        aktif_alt_tesis = "3001-02";
        aktif_sunucu = "003";
        aktif_kullanici = "andr_BigadicNusrat";
        aktif_isletmeesleme = "3001";
        baglanti_turu="wifi";
        sunucu_ip="10.64.24.24";

        _myIslem=new VeriTabani(v_Context);
        _myIslem.fn_Kayit();
        _myIslem.fn_BaslangicAyarlari(cSabitDegerler._sbtVerisyon,baglanti_turu, aktif_kullanici, aktif_depo, aktif_alt_tesis, aktif_tesis, aktif_sunucu, aktif_isletmeesleme, sunucu_ip);
    }

    public void fn_setBigadicKestelek(Context v_Context) {
        aktif_depo = "MUR1";
        aktif_tesis = "3002";
        aktif_alt_tesis = "3002-01";
        aktif_sunucu = "003";
        aktif_kullanici = "andr_kestelek";
        aktif_isletmeesleme = "3001";
        baglanti_turu="wifi";
        sunucu_ip="10.64.24.24";

        _myIslem=new VeriTabani(v_Context);
        _myIslem.fn_Kayit();
        _myIslem.fn_BaslangicAyarlari(cSabitDegerler._sbtVerisyon,baglanti_turu, aktif_kullanici, aktif_depo, aktif_alt_tesis, aktif_tesis, aktif_sunucu, aktif_isletmeesleme, sunucu_ip);
    }

    public void fn_setBandirmaBorAsit(Context v_Context) {
        aktif_depo = "MUR1";
        aktif_tesis = "2001";
        aktif_alt_tesis = "2001-01";
        aktif_sunucu = "001";
        aktif_isletmeesleme = "2001";
        aktif_kullanici = "andr_bandirma_isletme";
        baglanti_turu="wifi";
        sunucu_ip="10.48.24.24";

        _myIslem=new VeriTabani(v_Context);
        _myIslem.fn_Kayit();
        _myIslem.fn_BaslangicAyarlari(cSabitDegerler._sbtVerisyon,baglanti_turu, aktif_kullanici, aktif_depo, aktif_alt_tesis, aktif_tesis, aktif_sunucu, aktif_isletmeesleme, sunucu_ip);

    }

    public void fn_setBandirmaLojistik(Context v_Context) {

        //fn_setBandirmaBorAsit(v_Context);


        aktif_depo = "MUR1";
        aktif_tesis = "2003";
        aktif_isletmeesleme = "2003";
        aktif_alt_tesis = "2004-01";
        aktif_sunucu = "001";
        aktif_kullanici = "andr_liman";
        baglanti_turu="wifi";
        sunucu_ip="10.48.24.24";

        _myIslem=new VeriTabani(v_Context);
        _myIslem.fn_Kayit();
        _myIslem.fn_BaslangicAyarlari(cSabitDegerler._sbtVerisyon,baglanti_turu, aktif_kullanici, aktif_depo, aktif_alt_tesis, aktif_tesis, aktif_sunucu, aktif_isletmeesleme, sunucu_ip);

    }


    public void fn_setKirkaIsletme(Context v_Context) {
        aktif_depo = "MUR1";
        aktif_tesis = "5001";
        aktif_alt_tesis = "5001-01";
        aktif_sunucu = "004";
        aktif_kullanici = "andr_kirka";
        aktif_isletmeesleme = "5001";
        baglanti_turu="wifi";
        sunucu_ip="10.88.24.24";

        _myIslem=new VeriTabani(v_Context);
        _myIslem.fn_Kayit();
        _myIslem.fn_BaslangicAyarlari(cSabitDegerler._sbtVerisyon,baglanti_turu, aktif_kullanici, aktif_depo, aktif_alt_tesis, aktif_tesis, aktif_sunucu, aktif_isletmeesleme, sunucu_ip);
    }

    public void fn_setKirkaDegirmenozu(Context v_Context) {
        aktif_depo = "MUR1";
        aktif_tesis = "5002";
        aktif_alt_tesis = "5002-01";
        aktif_sunucu = "004";
        aktif_isletmeesleme = "5001";
        aktif_kullanici = "andr_degirmenozu";
        baglanti_turu="wifi";
        sunucu_ip="10.88.24.24";

        _myIslem=new VeriTabani(v_Context);
        _myIslem.fn_Kayit();
        _myIslem.fn_BaslangicAyarlari(cSabitDegerler._sbtVerisyon,baglanti_turu, aktif_kullanici, aktif_depo, aktif_alt_tesis, aktif_tesis, aktif_sunucu, aktif_isletmeesleme, sunucu_ip);
    }

    public void fn_setAnkara(Context v_Context) {
        aktif_depo = "";
        aktif_tesis = "1001";
        aktif_isletmeesleme = "1001";
        aktif_alt_tesis = "1001-01";
        aktif_sunucu = "001";
        aktif_kullanici = "andr_ankara";
        baglanti_turu="wifi";
        sunucu_ip="10.24.153.24";

        _myIslem=new VeriTabani(v_Context);
        _myIslem.fn_Kayit();
        _myIslem.fn_BaslangicAyarlari(cSabitDegerler._sbtVerisyon,baglanti_turu, aktif_kullanici, aktif_depo, aktif_alt_tesis, aktif_tesis, aktif_sunucu, aktif_isletmeesleme, sunucu_ip);
    }

    public void fn_setGenel(Context v_Context) {
        aktif_depo = "";
        aktif_tesis = "1001";
        aktif_isletmeesleme = "1001";
        aktif_alt_tesis = "1001-01";
        aktif_sunucu = "001";
        aktif_kullanici = "andr_GSM";
        baglanti_turu="wifi";
        sunucu_ip="10.88.24.24";

        _myIslem=new VeriTabani(v_Context);
        _myIslem.fn_Kayit();
        _myIslem.fn_BaslangicAyarlari(cSabitDegerler._sbtVerisyon,baglanti_turu, aktif_kullanici, aktif_depo, aktif_alt_tesis, aktif_tesis, aktif_sunucu, aktif_isletmeesleme, sunucu_ip);
    }
    public void fn_setTest(Context v_Context) {
        aktif_kullanici = "andr_EmetIsletme";
        aktif_depo = "URN1";
        aktif_alt_tesis = "2001-01";
        aktif_tesis = "2001";
        aktif_sunucu = "002";
        aktif_isletmeesleme = "2001";
        baglanti_turu="wifi";
        sunucu_ip="192.168.9.150";

        _myIslem=new VeriTabani(v_Context);
        _myIslem.fn_Kayit();
        _myIslem.fn_BaslangicAyarlari(cSabitDegerler._sbtVerisyon,baglanti_turu, aktif_kullanici, aktif_depo, aktif_alt_tesis, aktif_tesis, aktif_sunucu, aktif_isletmeesleme, sunucu_ip);
    }
}
