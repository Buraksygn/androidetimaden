package com.etimaden.Elden_satis_islemi;

import static com.etimaden.cSabitDegerler._ipAdresi3G;
import static com.etimaden.cSabitDegerler._sbtVerisyon;
import static com.etimaden.cSabitDegerler._zkullaniciadi;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.GirisSayfasi;
import com.etimaden.SevkiyatIslemleri.frg_konteyner_bulundu;
import com.etimaden.adapter.apmblDepolararasiSevkSayilamayanAyirma;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cResponseResult.Sevkiyat_isemri;
import com.etimaden.cResponseResult.Urun_sevkiyat;
import com.etimaden.depolarArasiSevkIslemi.frg_depolar_arasi_transfer_menu_panel;
import com.etimaden.frg_ana_sayfa;
import com.etimaden.genel.Genel;
import com.etimaden.genel.SweetAlertDialogG;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.Urun_tag;
import com.etimaden.request.request_integer_sevkiyat_isemri;
import com.etimaden.request.request_integer_sevkiyat_isemri_stringlist;
import com.etimaden.request.request_secEtiket;
import com.etimaden.request.request_string;
import com.etimaden.request.request_string_string_string;
import com.etimaden.request.request_uruntag_list;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;
import java.util.List;

public class frg_perakende_satis extends Fragment {
    VeriTabani _myIslem;
    String _ayaraktifkullanici = "";
    String _ayaraktifdepo = "";
    String _ayaraktifalttesis = "";
    String _ayaraktiftesis = "";
    String _ayaraktifsunucu = "";
    String _ayaraktifisletmeeslesme = "";
    String _ayarbaglantituru = "";
    String _ayarsunucuip = "";
    String _ayarversiyon = "";
    String _OnlineUrl = "";

    Persos persos;

    ImageView _infoImage;
    TextView _txtBaslik;
    Button _btn_01;
    TextView _txtDetay;
    Button _btnGeri;
    Button _btnOkuma;


    boolean isReadable = true;
    Sevkiyat_isemri aktif_sevk_isemri = null;
    int agirlik = 0;
    int adet = 0;

    public static frg_perakende_satis newInstance() {

        return new frg_perakende_satis();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_perakende_satis, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    private void fn_AyarlariYukle() {


        _ayarbaglantituru = _myIslem.fn_baglanti_turu();
        _ayarsunucuip = _myIslem.fn_sunucu_ip();
        _ayaraktifkullanici = _myIslem.fn_aktif_kullanici();
        _ayaraktifdepo = _myIslem.fn_aktif_depo();
        _ayaraktifalttesis = _myIslem.fn_aktif_alt_tesis();
        _ayaraktiftesis = _myIslem.fn_aktif_tesis();
        _ayaraktifsunucu = _myIslem.fn_aktif_sunucu();
        _ayaraktifisletmeeslesme = _myIslem.fn_isletmeeslesme();

        if (_ayarbaglantituru.equals("wifi"))
        {
            _OnlineUrl = "http://" + _ayarsunucuip + ":" + _zportWifi + "/";
        }
        else
        {
            _OnlineUrl = "http://" + _ipAdresi3G + ":" + _zport3G + "/";
        }

        persos = new Persos(_OnlineUrl,getContext());
    }

    public void fn_senddata(Sevkiyat_isemri aktif_sevk_isemri)
    {
        this.aktif_sevk_isemri=aktif_sevk_isemri;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        ((GirisSayfasi) getActivity()).fn_ModBarkod();

        _myIslem = new VeriTabani(getContext());

        fn_AyarlariYukle();

        _myIslem.fn_EpcTemizle();

        ((GirisSayfasi) getActivity()).fn_ListeTemizle();



        _infoImage = (ImageView)getView().findViewById(R.id.infoImage);
        _infoImage.playSoundEffect(0);
        _infoImage.setOnClickListener(new fn_DetayGoster());

        _txtBaslik=(TextView)getView().findViewById(R.id.txtBaslik);
        String baslik = "SAP KODU : " + aktif_sevk_isemri.kod_sap + "\n";
        if (aktif_sevk_isemri.alici_isletme.equals(""))
        {
            baslik += "ALICI : " + aktif_sevk_isemri.alici + "\n";
        }
        baslik += "SATIŞ LİSTESİ";
        _txtBaslik.setText(baslik);

        _btn_01= (Button)getView().findViewById(R.id.btn_01);
        _btn_01.playSoundEffect(0);
        _btn_01.setOnClickListener(new fn_btn_01());

        _txtDetay=(TextView)getView().findViewById(R.id.txtDetay);

        _btnGeri = (Button)getView().findViewById(R.id.btnGeri);
        _btnGeri.playSoundEffect(0);
        _btnGeri.setOnClickListener(new fn_Geri());

        _btnOkuma = (Button)getView().findViewById(R.id.btnOkuma);
        _btnOkuma.playSoundEffect(0);
        _btnOkuma.setOnClickListener(new fn_okumaDegistir());
        _btnOkuma.setText("KAREKOD");




    }

    public void barkodOkundu(String barkod){

        try
        {
            barkod = barkod.substring(barkod.length()-24);
            if (!isReadable)
            {
                return;
            }
            isReadable = false;
            Genel.playQuestionSound(getContext());

            request_string v_Gelen=new request_string();
            v_Gelen.set_value(barkod);
            v_Gelen.set_zaktif_alt_tesis(_ayaraktifalttesis);
            v_Gelen.set_zaktif_tesis(_ayaraktiftesis);
            v_Gelen.set_zkullaniciadi(_zkullaniciadi);
            v_Gelen.set_zsifre(_zsifre);
            v_Gelen.set_zsunucu_ip_adresi(_ayarsunucuip);
            v_Gelen.set_zsurum(_sbtVerisyon);
            v_Gelen.setAktif_kullanici(_ayaraktifkullanici);
            v_Gelen.setAktif_sunucu(_ayaraktifsunucu);

            Genel.showProgressDialog(getContext());
            Urun_sevkiyat tag = persos.fn_sec_sevkiyat_urun(v_Gelen);
            Genel.dismissProgressDialog();

            if (tag == null)
            {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Lütfen uygun bir ürün etiketi okutunuz. Ürün kaydı bulunamadı..")
                        .showCancelButton(false)
                        .show();
                isReadable = true;
            }
            else
            {
                urunDegerlendir(tag);
            }

        }
        catch (Exception ex)
        {
            Genel.printStackTrace(ex,getContext());
            isReadable = true;
        }



    }

    public void rfidOkundu(String rfid){
        try
        {

            if (!isReadable)
            {
                return;
            }
            isReadable = false;
            Genel.playQuestionSound(getContext());

            request_string v_Gelen=new request_string();
            v_Gelen.set_value(rfid);
            v_Gelen.set_zaktif_alt_tesis(_ayaraktifalttesis);
            v_Gelen.set_zaktif_tesis(_ayaraktiftesis);
            v_Gelen.set_zkullaniciadi(_zkullaniciadi);
            v_Gelen.set_zsifre(_zsifre);
            v_Gelen.set_zsunucu_ip_adresi(_ayarsunucuip);
            v_Gelen.set_zsurum(_sbtVerisyon);
            v_Gelen.setAktif_kullanici(_ayaraktifkullanici);
            v_Gelen.setAktif_sunucu(_ayaraktifsunucu);

            Genel.showProgressDialog(getContext());
            Urun_sevkiyat tag = persos.fn_sec_sevkiyat_urun(v_Gelen);
            Genel.dismissProgressDialog();

            if (tag == null)
            {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Lütfen uygun bir ürün etiketi okutunuz. Ürün kaydı bulunamadı..")
                        .showCancelButton(false)
                        .show();
                isReadable = true;
            }
            else
            {
                urunDegerlendir(tag);
            }


        }
        catch (Exception ex)
        {
            Genel.printStackTrace(ex,getContext());
            isReadable = true;
        }

    }

    private void urunDegerlendir(Urun_sevkiyat tag)
    {
        try
        {
            if (aktif_sevk_isemri.urun_kodu.equals(tag.urun_kod) && aktif_sevk_isemri.miktar_torba.equals(tag.torba_agirlik))
            {
                try
                {
                    int miktar_agirlik = Integer.parseInt(tag.torba_agirlik);
                    agirlik += miktar_agirlik;
                    adet++;
                }
                catch (Exception ex)
                {
                    Genel.printStackTrace(ex,getContext());
                }

                request_string_string_string v_Gelen=new request_string_string_string();
                v_Gelen.set_zaktif_alt_tesis(_ayaraktifalttesis);
                v_Gelen.set_zaktif_tesis(_ayaraktiftesis);
                v_Gelen.set_zkullaniciadi(_zkullaniciadi);
                v_Gelen.set_zsifre(_zsifre);
                v_Gelen.set_zsunucu_ip_adresi(_ayarsunucuip);
                v_Gelen.set_zsurum(_sbtVerisyon);
                v_Gelen.setAktif_kullanici(_ayaraktifkullanici);
                v_Gelen.setAktif_sunucu(_ayaraktifsunucu);

                v_Gelen.set_value(aktif_sevk_isemri.urun_kodu);
                v_Gelen.set_value2(aktif_sevk_isemri.miktar_torba);
                v_Gelen.set_value3(aktif_sevk_isemri.isletme);

                Genel.showProgressDialog(getContext());
                String toplam_mikar = persos.fn_sec_toplam_dkmlot_miktar(v_Gelen);
                Genel.dismissProgressDialog();
                int toplam_dkm_mikar = 0;
                try
                {
                    toplam_dkm_mikar = Integer.parseInt(toplam_mikar);
                }
                catch (Exception ex)
                {
                    Genel.printStackTrace(ex,getContext());
                }


                if(toplam_mikar != null && toplam_dkm_mikar >= agirlik)
                {
                    String detay = "\nMİKTARLAR : \nOKUTULAN MİKTAR : " + agirlik
                            + "\nOKUTULAN ADET : " + adet
                            + "\nİŞ EMRİ KALAN MİKTAR : " + aktif_sevk_isemri.kalan_agirlik
                            + "\n*ŞU AN OKUTULAN ETİKETLER\nKALAN MİKTARDAN AZALMAYCAKTIR. " + agirlik;
                    _txtDetay.setText(detay);
                }
            }
            isReadable=true;
        }
        catch (Exception ex)
        {
            Genel.printStackTrace(ex,getContext());
            new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                    .setTitleText("HATA")
                    .setContentTextSize(25)
                    .setContentText("İlgili iş emri kaydı bulunamadı. \r\n " + ex.getMessage())
                    .showCancelButton(false)
                    .show();
            isReadable=true;
        }
    }


    private class fn_btn_01 implements View.OnClickListener {
        @Override
        public void onClick(View view)
        {
            try
            {
                Genel.lockButtonClick(view,getActivity());

                if(adet == 0)
                {
                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                            .setTitleText("HATA")
                            .setContentTextSize(25)
                            .setContentText("Ürün listenizde ürün bulunmamaktadır.")
                            .showCancelButton(false)
                            .show();
                }
                else
                {
                    request_string_string_string v_Gelen=new request_string_string_string();
                    v_Gelen.set_zaktif_alt_tesis(_ayaraktifalttesis);
                    v_Gelen.set_zaktif_tesis(_ayaraktiftesis);
                    v_Gelen.set_zkullaniciadi(_zkullaniciadi);
                    v_Gelen.set_zsifre(_zsifre);
                    v_Gelen.set_zsunucu_ip_adresi(_ayarsunucuip);
                    v_Gelen.set_zsurum(_sbtVerisyon);
                    v_Gelen.setAktif_kullanici(_ayaraktifkullanici);
                    v_Gelen.setAktif_sunucu(_ayaraktifsunucu);

                    v_Gelen.set_value(aktif_sevk_isemri.urun_kodu);
                    v_Gelen.set_value2(aktif_sevk_isemri.miktar_torba);
                    v_Gelen.set_value3(aktif_sevk_isemri.isletme);

                    Genel.showProgressDialog(getContext());
                    String toplam_mikar = persos.fn_sec_toplam_dkmlot_miktar(v_Gelen);
                    Genel.dismissProgressDialog();

                    int toplam_dkm_mikar = 0;
                    try
                    {
                        toplam_dkm_mikar = Integer.parseInt(toplam_mikar);
                    }
                    catch (Exception ex)
                    {
                        Genel.printStackTrace(ex,getContext());
                    }


                    if (toplam_mikar == null || toplam_dkm_mikar == 0 || toplam_dkm_mikar < agirlik)
                    {
                        new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                                .setTitleText("HATA")
                                .setContentTextSize(25)
                                .setContentText("DKMPAKET lotunda yeterli miktarda ürün bulunmamaktadır. \r\n DKMPAKET Toplam Miktar(KG) : " + toplam_dkm_mikar)
                                .showCancelButton(false)
                                .show();
                        return;
                    }

                    Integer toplam_satıs_isemri_mikari = 0;
                    try
                    {
                        toplam_satıs_isemri_mikari = Integer.parseInt(aktif_sevk_isemri.kalan_agirlik);
                    }
                    catch (Exception ex)
                    {
                        Genel.printStackTrace(ex,getContext());
                    }

                    if (toplam_satıs_isemri_mikari == null || toplam_satıs_isemri_mikari == 0 || toplam_satıs_isemri_mikari < agirlik)
                    {
                        new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                                .setTitleText("HATA")
                                .setContentTextSize(25)
                                .setContentText("Sipariş miktarından fazla ürün satışı yapılamaz. \r\n Sipariş kalan(KG) : " + toplam_satıs_isemri_mikari + "\r\nOKUTULAN (KG ): " + agirlik)
                                .showCancelButton(false)
                                .show();
                        return;
                    }

                    request_integer_sevkiyat_isemri _Param=new request_integer_sevkiyat_isemri();
                    _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
                    _Param.set_zaktif_tesis(_ayaraktiftesis);
                    _Param.set_zkullaniciadi(_zkullaniciadi);
                    _Param.set_zsifre(_zsifre);
                    _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
                    _Param.set_zsurum(_sbtVerisyon);
                    _Param.setAktif_kullanici(_ayaraktifkullanici);
                    _Param.setAktif_sunucu(_ayaraktifsunucu);

                    _Param.set_intValue(adet);
                    _Param.set_sevkiyat_isemri(aktif_sevk_isemri);

                    Genel.showProgressDialog(getContext());
                    final List<String> etiket_listesi = persos.fn_sec_dkm_etiket_listesi(_Param);
                    Genel.dismissProgressDialog();

                    if (etiket_listesi.size() != adet)
                    {
                        new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                                .setTitleText("HATA")
                                .setContentTextSize(25)
                                .setContentText("DKMPAKET lotunda yeterli miktarda ürün bulunmamaktadır. \r\n DKMPAKET Toplam Miktar(KG) : " + toplam_dkm_mikar)
                                .showCancelButton(false)
                                .show();
                        return;
                    }

                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                            .setTitleText("SATIŞ ONAYI")
                            .setContentText("SAP KODU : "+aktif_sevk_isemri.kod_sap +
                                "\r\nMÜŞTERİ : " + aktif_sevk_isemri.alici +
                                "\r\nÜRÜN : " + aktif_sevk_isemri.urun_adi+
                                "\r\nÜRÜN BİRİM MİKTAR :" + aktif_sevk_isemri.miktar_torba+
                                "\r\nTOPLAM ADET : " + adet +
                                "\r\nSATIŞ KAYDI OLUŞTURULACAK.!! BU İŞLEMİ TAMAMLAMAK İSTİYOR MUSUNUZ ?")
                            .setContentTextSize(20)
                            .setConfirmText("EVET")
                            .setCancelText("HAYIR")
                            .showCancelButton(true)
                            .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialogG sDialog) {
                                    sDialog.dismissWithAnimation();

                                    request_integer_sevkiyat_isemri_stringlist _Param=new request_integer_sevkiyat_isemri_stringlist();
                                    _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
                                    _Param.set_zaktif_tesis(_ayaraktiftesis);
                                    _Param.set_zkullaniciadi(_zkullaniciadi);
                                    _Param.set_zsifre(_zsifre);
                                    _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
                                    _Param.set_zsurum(_sbtVerisyon);
                                    _Param.setAktif_kullanici(_ayaraktifkullanici);
                                    _Param.setAktif_sunucu(_ayaraktifsunucu);

                                    _Param.set_intValue(adet);
                                    _Param.set_sevkiyat_isemri(aktif_sevk_isemri);
                                    _Param.set_stringList(etiket_listesi);

                                    Genel.showProgressDialog(getContext());
                                    Boolean res_return = persos.fn_onayla_perakende_satis(_Param);
                                    Genel.dismissProgressDialog();

                                    if (res_return)
                                    {
                                        new SweetAlertDialogG(getContext(), SweetAlertDialogG.SUCCESS_TYPE)
                                                .setTitleText("İşlem Onayı")
                                                .setContentText("Satış kaydı oluşturuldu.")
                                                .setContentTextSize(20)
                                                .setConfirmText("TAMAM")
                                                .showCancelButton(false)
                                                .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialogG sDialog) {
                                                        sDialog.dismissWithAnimation();

                                                        frg_ana_sayfa fragmentyeni = new frg_ana_sayfa();
                                                        FragmentManager fragmentManager = getFragmentManager();
                                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                        fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_ana_sayfa").addToBackStack(null);
                                                        fragmentTransaction.commit();

                                                        return;
                                                    }
                                                }).show();
                                    }
                                    else
                                    {
                                        new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                                                .setTitleText("BAĞLANTI HATASI")
                                                .setContentTextSize(25)
                                                .setContentText("Bağlantı yapılamadı. Lütfen daha sonra tekrar deneyiniz.")
                                                .showCancelButton(false)
                                                .show();
                                    }

                                    return;
                                }
                            })
                            .setCancelClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialogG sDialog) {
                                    sDialog.dismissWithAnimation();
                                    return;
                                }
                            })
                            .show();

                }

            }
            catch (Exception ex)
            {
                Genel.printStackTrace(ex,getContext());
            }
        }
    }

    private class fn_DetayGoster implements View.OnClickListener {
        @Override
        public void onClick(View view)
        {
            try
            {
                Genel.playButtonClikSound(getContext());
                String str = "";
                if (aktif_sevk_isemri.alici_isletme.equals(""))
                {
                    str += "ALICI : " + aktif_sevk_isemri.alici + "\r\n";
                    str += "BOOKING NO : " + aktif_sevk_isemri.bookingno + "\r\n";
                }
                else
                    str += "ALICI : " + aktif_sevk_isemri.alici_isletme + "\r\n";
                str += "ARAÇ PLAKASI : " + aktif_sevk_isemri.arac_plaka + "\r\n";
                if (!aktif_sevk_isemri.konteyner_turu.equals(""))
                    str += "KONTEYNER  : " + aktif_sevk_isemri.kont_kodu + "\r\n";
                str += "SAP KODU : " + aktif_sevk_isemri.kod_sap + "\r\n";
                str += "ÜRÜN ADI : " + aktif_sevk_isemri.urun_adi + "\r\n";
                str += "TORBA AĞIRLIĞI : " + aktif_sevk_isemri.miktar_torba + "\r\n";
                str += "PALET AĞIRLIĞI : " + aktif_sevk_isemri.palet_agirligi + "\r\n";
                str += "Y.ADET/ İŞEMRİ K.ADET : " + "\r\n" + aktif_sevk_isemri.yapilan_adet + " / " + aktif_sevk_isemri.kalan_palet_sayisi;

                new SweetAlertDialogG(getContext(), SweetAlertDialogG.SUCCESS_TYPE)
                        .setTitleText("İŞ DETAYI")
                        .setContentText(str)
                        .setContentTextSize(20)
                        .setConfirmText("TAMAM")
                        .showCancelButton(false)
                        .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialogG sDialog) {
                                sDialog.dismissWithAnimation();
                                return;
                            }
                        }).show();

            }
            catch (Exception ex)
            {
                Genel.printStackTrace(ex,getContext());
            }
        }
    }

    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View view)
        {
            frg_perakende_satis_is_emri_secimi fragmentyeni = new frg_perakende_satis_is_emri_secimi();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_perakende_satis_is_emri_secimi").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_okumaDegistir implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Genel.showProgressDialog(getContext());
            if(_btnOkuma.getText().toString().equals("KAREKOD")){
                ((GirisSayfasi) getActivity()).fn_ModRFID();
                _btnOkuma.setText("RFID");
            }else{
                ((GirisSayfasi) getActivity()).fn_ModBarkod();
                _btnOkuma.setText("KAREKOD");
            }
            Genel.dismissProgressDialog();
        }
    }

}
