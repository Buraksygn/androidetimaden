package com.etimaden.SevkiyatIslemleri;

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
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.frg_ana_sayfa;
import com.etimaden.ugr_demo.R;



public class frg_sevkiyat_menu_panel extends Fragment {

    Button _btngeri;
    Button _btn_01;
    Button _btn_02;
    Button _btn_03;

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
}
