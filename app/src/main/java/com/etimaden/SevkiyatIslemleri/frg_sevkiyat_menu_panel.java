package com.etimaden.SevkiyatIslemleri;

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
import com.etimaden.SevkiyatIslemleri.Arac_aktivayon_islemleri.frg_arac_aktivasyon;
import com.etimaden.SevkiyatIslemleri.Silobas_islemleri.frg_aktif_silobas_arac_secimi;
import com.etimaden.SevkiyatIslemleri.Zayiat_islemleri.frg_zayi_menu_panel;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.frg_ana_sayfa;
import com.etimaden.genel.Genel;
import com.etimaden.genel.SweetAlertDialogG;
import com.etimaden.persos.Persos;
import com.etimaden.request.request_bos;
import com.etimaden.ugr_demo.R;



public class frg_sevkiyat_menu_panel extends Fragment {

    Button _btngeri;
    Button _btn_01;
    Button _btn_02;
    Button _btn_03;

    Button _btn_04;
    Button _btn_05;
    Button _btn_06;

    VeriTabani _myIslem;
    public String _ayaraktifkullanici = "";
    public String _ayaraktifdepo = "";
    public String _ayaraktifalttesis = "";
    public String _ayaraktiftesis = "";
    public String _ayaraktifsunucu = "";
    public String _ayaraktifisletmeeslesme = "";
    public String _ayarbaglantituru = "";
    public String _ayarsunucuip = "";
    public String _ayarversiyon = "";
    String _OnlineUrl = "";
    Persos persos;

    public frg_sevkiyat_menu_panel() {
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
        persos = new Persos(_OnlineUrl,getContext());

    }

    public static frg_sevkiyat_menu_panel newInstance()
    {
        return new frg_sevkiyat_menu_panel();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_sevkiyat_menu_panel, container, false);
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

        _btn_03 = (Button) getView().findViewById(R.id.btn_03);
        _btn_03.playSoundEffect(0);
        _btn_03.setOnClickListener(new fn_btn_03());

        _btn_04 = (Button) getView().findViewById(R.id.btn_04);
        _btn_04.playSoundEffect(0);
        _btn_04.setOnClickListener(new fn_btn_04());

        _btn_05 = (Button) getView().findViewById(R.id.btn_05);
        _btn_05.playSoundEffect(0);
        _btn_05.setOnClickListener(new fn_btn_05());

        _btn_06 = (Button) getView().findViewById(R.id.btn_06);
        _btn_06.playSoundEffect(0);
        _btn_06.setOnClickListener(new fn_btn_06());


        //_btn_03.setOnClickListener(new fn_btn_03_01());
    }

    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View view)
        {
            frg_ana_sayfa fragmentyeni = new frg_ana_sayfa();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_ana_sayfa").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_btn_01 implements View.OnClickListener {
        @Override
        public void onClick(View view)
        {
            /*
            if(_ayaraktiftesis.equals("2003"))
            {
                frg_arac_aktivasyon_2003 fragmentyeni = new frg_arac_aktivasyon_2003();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_arac_aktivasyon_2003").addToBackStack(null);
                fragmentTransaction.commit();
            }
            else
            {
                frg_arac_aktivasyon fragmentyeni = new frg_arac_aktivasyon();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_arac_aktivasyon").addToBackStack(null);
                fragmentTransaction.commit();
            }*/

            ((GirisSayfasi) getActivity()).fn_ModRFID();

            frg_arac_aktivasyon fragmentyeni = new frg_arac_aktivasyon();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_arac_aktivasyon").addToBackStack(null);
            fragmentTransaction.commit();

        }
    }

    private class fn_btn_02 implements View.OnClickListener {
        @Override
        public void onClick(View view)
        {
            ((GirisSayfasi) getActivity()).fn_ModRFID();

            frg_aktif_arac_secimi fragmentyeni = new frg_aktif_arac_secimi();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_aktif_arac_secimi").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_btn_03 implements View.OnClickListener {
        @Override
        public void onClick(View view)
        {
            ((GirisSayfasi) getActivity()).fn_ModRFID();

            frg_konteyner_yukleme_aktivasyon fragmentyeni = new frg_konteyner_yukleme_aktivasyon();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_konteyner_yukleme_aktivasyon").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_btn_04 implements View.OnClickListener {
        @Override
        public void onClick(View view)
        {
            ((GirisSayfasi) getActivity()).fn_ModRFID();

            frg_aktif_silobas_arac_secimi fragmentyeni = new frg_aktif_silobas_arac_secimi();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_aktif_silobas_arac_secimi").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_btn_05 implements View.OnClickListener {
        @Override
        public void onClick(View view)
        {
            ((GirisSayfasi) getActivity()).fn_ModRFID();

            frg_zayi_menu_panel fragmentyeni = new frg_zayi_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_zayi_menu_panel").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_btn_06 implements View.OnClickListener {
        @Override
        public void onClick(View view)
        {
            try
            {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                        .setTitleText("Onay")
                        .setContentText("Vagonların satış işlemi SAP'ye aktarılacak. Onaylıyor musunuz?")
                        .setContentTextSize(20)
                        .setConfirmText("EVET")
                        .setCancelText("HAYIR")
                        .showCancelButton(true)
                        .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialogG sDialog) {
                                sDialog.dismissWithAnimation();
                                request_bos _Param1= new request_bos();
                                _Param1.set_zsunucu_ip_adresi(_ayarsunucuip);
                                _Param1.set_zaktif_alt_tesis(_ayaraktifalttesis);
                                _Param1.set_zaktif_tesis(_ayaraktiftesis);
                                _Param1.set_zsurum(_sbtVerisyon);
                                _Param1.set_zkullaniciadi(_zkullaniciadi);
                                _Param1.set_zsifre(_zsifre);
                                _Param1.setAktif_sunucu(_ayaraktifsunucu);
                                _Param1.setAktif_kullanici(_ayaraktifkullanici);

                                Genel.showProgressDialog(getContext());
                                boolean islem = persos.fn_guncelle_vagon_satis(_Param1);
                                Genel.dismissProgressDialog();

                                if (islem)
                                {
                                    boolean result=true;
                                    while (result)
                                    {
                                        Genel.showProgressDialog(getContext());
                                        result = persos.fn_guncelle_vagon_satis(_Param1);
                                        Genel.dismissProgressDialog();
                                    }
                                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                                            .setTitleText("ONAY")
                                            .setContentText("İşlem onaylandı.")
                                            .setContentTextSize(20)
                                            .setConfirmText("TAMAM")
                                            .showCancelButton(false)
                                            .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialogG sDialog) {
                                                    sDialog.dismissWithAnimation();
                                                }
                                            })
                                            .show();
                                }
                                else
                                {
                                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                                            .setTitleText("HATA")
                                            .setContentTextSize(25)
                                            .setContentText("İşlem YAPILAMADI. LÜTFEN DAHA SONRA TEKRAR DENEYİNİZ. \r\n BAĞLANTI HATASI")
                                            .showCancelButton(false)
                                            .show();
                                }
                            }
                        })
                        .show();



            }
            catch (Exception ex) {
                Genel.printStackTrace(ex,getContext());
            }
        }
    }

    private class fn_btn_03_01 implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            ((GirisSayfasi) getActivity()).fn_ModRFID();

            frg_timer_test fragmentyeni = new frg_timer_test();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_timer_test").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}
