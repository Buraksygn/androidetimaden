package com.etimaden.SevkiyatIslemleri.Zayiat_islemleri;

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

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.GirisSayfasi;
import com.etimaden.SevkiyatIslemleri.frg_aktif_arac_secimi;
import com.etimaden.SevkiyatIslemleri.frg_sevkiyat_menu_panel;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cResponseResult.Arac;
import com.etimaden.frg_ana_sayfa;
import com.etimaden.genel.Genel;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.Zayi;
import com.etimaden.request.request_sevkiyat_zayi;
import com.etimaden.request.request_sevkiyat_zayi_arac;
import com.etimaden.request.request_string;
import com.etimaden.ugr_demo.R;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class frg_zayi_arac_onayla_indirme extends Fragment {

    Button _btngeri;
    Button _btn_01;
    Button _btn_02;
    Button _btn_03;
    Button _btn_04;

    Zayi aktif_zayi = null;
    Arac arac = null;

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

    public frg_zayi_arac_onayla_indirme() {
        // Required empty public constructor
    }


    private void fn_AyarlariYukle()
    {
        ((GirisSayfasi) getActivity()).fn_ModRFID();


        _ayarbaglantituru=_myIslem.fn_baglanti_turu();
        _ayarsunucuip=_myIslem.fn_sunucu_ip();
        _ayaraktifkullanici=_myIslem.fn_aktif_kullanici();
        _ayaraktifdepo=_myIslem.fn_aktif_depo();
        _ayaraktifalttesis=_myIslem.fn_aktif_alt_tesis();
        _ayaraktiftesis=_myIslem.fn_aktif_tesis();
        _ayaraktifsunucu=_myIslem.fn_aktif_sunucu();
        _ayaraktifisletmeeslesme=_myIslem.fn_isletmeeslesme();

        if(_ayarbaglantituru.equals("wifi"))
        {
            _OnlineUrl = "http://"+_ayarsunucuip+":"+_zportWifi+"/";
        }
        else
        {
            _OnlineUrl = "http:/"+_ipAdresi3G+":"+_zport3G+"/";
        }
        persos = new Persos(_OnlineUrl);

    }

    public void fn_senddata(Zayi aktif_zayi, Arac arac)
    {
        this.aktif_zayi = aktif_zayi;
        this.arac = arac;
    }

    public static frg_zayi_arac_onayla_indirme newInstance()
    {
        return new frg_zayi_arac_onayla_indirme();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_zayi_menu_panel, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        ((GirisSayfasi)getActivity()).fn_ModRFID();

        new VeriTabani(getContext()).fn_EpcTemizle();

        _myIslem = new VeriTabani(getContext());

        fn_AyarlariYukle();


        ((GirisSayfasi)getActivity()).fn_ListeTemizle();

        _btngeri=(Button)getView().findViewById(R.id.btncikis);
        _btngeri.playSoundEffect(0);
        _btngeri.setOnClickListener(new fn_Geri());

        _btn_01=(Button)getView().findViewById(R.id.btn_01);
        _btn_01.playSoundEffect(0);
        _btn_01.setOnClickListener(new fn_btn_01());

        _btn_02 =(Button)getView().findViewById(R.id.btn_02);
        _btn_02.playSoundEffect(0);
        _btn_02.setOnClickListener(new fn_btn_02());

        _btn_03=(Button)getView().findViewById(R.id.btn_03);
        _btn_03.playSoundEffect(0);
        _btn_03.setOnClickListener(new fn_btn_03());

        _btn_04 =(Button)getView().findViewById(R.id.btn_04);
        _btn_04.playSoundEffect(0);
        _btn_04.setOnClickListener(new fn_btn_04());

    }



    private class fn_btn_01 implements View.OnClickListener {
        @Override
        public void onClick(View view)
        {
            Genel.playButtonClikSound(getContext());
            try {
                _btn_01.setEnabled(false);
                _btn_02.setEnabled(false);
                _btn_03.setEnabled(false);
                _btn_04.setEnabled(false);
                _btngeri.setEnabled(false);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }

            try
            {
                request_sevkiyat_zayi_arac _Param= new request_sevkiyat_zayi_arac();
                _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
                _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
                _Param.set_zaktif_tesis(_ayaraktiftesis);
                _Param.set_zsurum(_sbtVerisyon);
                _Param.set_zkullaniciadi(_zkullaniciadi);
                _Param.set_zsifre(_zsifre);
                _Param.setAktif_sunucu(_ayaraktifsunucu);
                _Param.setAktif_kullanici(_ayaraktifkullanici);

                _Param.set_zayi(aktif_zayi);
                _Param.set_arac(arac);

                Genel.showProgressDialog(getContext());
                Boolean acZayiArac = persos.fn_ac_zayi_arac(_Param);
                Genel.dismissProgressDialog();

                if (aktif_zayi.zay_id_isemrihareket_yeni == "" && acZayiArac)
                {
                    request_sevkiyat_zayi _Param1= new request_sevkiyat_zayi();
                    _Param1.set_zsunucu_ip_adresi(_ayarsunucuip);
                    _Param1.set_zaktif_alt_tesis(_ayaraktifalttesis);
                    _Param1.set_zaktif_tesis(_ayaraktiftesis);
                    _Param1.set_zsurum(_sbtVerisyon);
                    _Param1.set_zkullaniciadi(_zkullaniciadi);
                    _Param1.set_zsifre(_zsifre);
                    _Param1.setAktif_sunucu(_ayaraktifsunucu);
                    _Param1.setAktif_kullanici(_ayaraktifkullanici);

                    _Param1.set_zayi(aktif_zayi);

                    Genel.showProgressDialog(getContext());
                    Boolean guncelleDepoZayiArac = persos.fn_guncelle_depo_zayi_arac(_Param1);
                    Genel.dismissProgressDialog();

                    new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("ONAY")
                            .setContentText("İşlem onaylandı.")
                            .setContentTextSize(20)
                            .setConfirmText("TAMAM")
                            .showCancelButton(false)
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    frg_sevkiyat_menu_panel fragmentyeni = new frg_sevkiyat_menu_panel();
                                    FragmentManager fragmentManager = getFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_sevkiyat_menu_panel").addToBackStack(null);
                                    fragmentTransaction.commit();
                                }
                            })
                            .show();
                    return;
                }
                else if (aktif_zayi.zay_id_isemrihareket_yeni != "")
                {
                    request_sevkiyat_zayi _Param1= new request_sevkiyat_zayi();
                    _Param1.set_zsunucu_ip_adresi(_ayarsunucuip);
                    _Param1.set_zaktif_alt_tesis(_ayaraktifalttesis);
                    _Param1.set_zaktif_tesis(_ayaraktiftesis);
                    _Param1.set_zsurum(_sbtVerisyon);
                    _Param1.set_zkullaniciadi(_zkullaniciadi);
                    _Param1.set_zsifre(_zsifre);
                    _Param1.setAktif_sunucu(_ayaraktifsunucu);
                    _Param1.setAktif_kullanici(_ayaraktifkullanici);

                    _Param1.set_zayi(aktif_zayi);

                    Genel.showProgressDialog(getContext());
                    Boolean guncelleDepoZayiArac = persos.fn_guncelle_depo_zayi_arac(_Param1);
                    Genel.dismissProgressDialog();

                    new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("ONAY")
                            .setContentText("İşlem onaylandı.")
                            .setContentTextSize(20)
                            .setConfirmText("TAMAM")
                            .showCancelButton(false)
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    frg_sevkiyat_menu_panel fragmentyeni = new frg_sevkiyat_menu_panel();
                                    FragmentManager fragmentManager = getFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_sevkiyat_menu_panel").addToBackStack(null);
                                    fragmentTransaction.commit();
                                }
                            })
                            .show();
                    return;
                }
                else
                {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("HATA")
                            .setContentTextSize(25)
                            .setContentText("İşlem yapılamadı. \r\n Veritabanı hatası.")
                            .showCancelButton(false)
                            .show();

                }
            }
            catch (Exception ex)
            {
                Genel.dismissProgressDialog();
                ex.printStackTrace();
            }
            try
            {
                _btn_01.setEnabled(true);
                _btn_02.setEnabled(true);
                _btn_03.setEnabled(true);
                _btn_04.setEnabled(true);
                _btngeri.setEnabled(true);

            }
            catch (Exception ex){
                ex.printStackTrace();
            }

        }
    }

    private class fn_btn_02 implements View.OnClickListener {
        @Override
        public void onClick(View view)
        {

        }
    }

    private class fn_btn_03 implements View.OnClickListener {
        @Override
        public void onClick(View view)
        {
            Genel.playButtonClikSound(getContext());

            try
            {
                if (aktif_zayi.zay_id_isemrihareket_yeni== "")
                {
                    return;
                }

                else
                {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("İNDİRME MİKTARI UYARISI")
                            .setContentText("BU İŞLEMİ ONAYLARSANIZ OKUTULMAYAN ÜRÜNLER İÇİN ZAYİ İŞLEMİ UYGULANACAKTIR. BU İŞLEMİ ONAYLIYOR MUSUNUZ?")
                            .setContentTextSize(20)
                            .setConfirmText("EVET")
                            .setCancelText("HAYIR")
                            .showCancelButton(true)
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    return;
                                }
                            })
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    request_sevkiyat_zayi_arac _Param= new request_sevkiyat_zayi_arac();
                                    _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
                                    _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
                                    _Param.set_zaktif_tesis(_ayaraktiftesis);
                                    _Param.set_zsurum(_sbtVerisyon);
                                    _Param.set_zkullaniciadi(_zkullaniciadi);
                                    _Param.set_zsifre(_zsifre);
                                    _Param.setAktif_sunucu(_ayaraktifsunucu);
                                    _Param.setAktif_kullanici(_ayaraktifkullanici);

                                    _Param.set_zayi(aktif_zayi);
                                    _Param.set_arac(arac);

                                    Genel.showProgressDialog(getContext());
                                    Boolean kapatZayiArac = persos.fn_kapat_zayi_arac(_Param);
                                    Genel.dismissProgressDialog();


                                    frg_sevkiyat_menu_panel fragmentyeni = new frg_sevkiyat_menu_panel();
                                    FragmentManager fragmentManager = getFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_sevkiyat_menu_panel").addToBackStack(null);
                                    fragmentTransaction.commit();

                                }
                            })
                            .show();


                }

            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    private class fn_btn_04 implements View.OnClickListener {
        @Override
        public void onClick(View view)
        {
            frg_sevkiyat_menu_panel fragmentyeni = new frg_sevkiyat_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_sevkiyat_menu_panel").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View view)
        {
            frg_zayi_arac_bulundu_indirme fragmentyeni = new frg_zayi_arac_bulundu_indirme();
            fragmentyeni.fn_senddata(aktif_zayi,arac);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_zayi_arac_bulundu_indirme").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}
