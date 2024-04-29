package com.etimaden.SayimIslemleri.Demirbas_sayim_islemi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.SayimIslemleri.Demirbas_sayim_islemi.Demirbas_arama.frg_demirbas_arama_menu_panel;
import com.etimaden.SayimIslemleri.Depo_sayim_islemi.frg_sayim_islemi_urun_aktivasyon;
import com.etimaden.SayimIslemleri.frg_sayim_menu_panel;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.depolarArasiSevkIslemi.Depo_cikis.frg_depo_secimi_transfer;
import com.etimaden.frg_ana_sayfa;
import com.etimaden.ugr_demo.R;

import retrofit2.Retrofit;

public class frg_demirbas_sayim_menu_panel extends Fragment {

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

    Retrofit retrofit;

    Button _btnDemirbasArama;
    Button _btnDemirbasSorgula;
    Button _btnEtiketBasimKontrol;
    Button _btnDemirbasSayimiOlustur;
    Button _btnDemirbasSay;
    Button _btnGeri;

    private void fn_AyarlariYukle()
    {
        _ayarbaglantituru=_myIslem.fn_baglanti_turu();
        _ayarsunucuip=_myIslem.fn_sunucu_ip();
        _ayaraktifkullanici=_myIslem.fn_aktif_kullanici();
        _ayaraktifdepo=_myIslem.fn_aktif_depo();
        _ayaraktifalttesis=_myIslem.fn_aktif_alt_tesis();
        _ayaraktiftesis=_myIslem.fn_aktif_tesis();
        _ayaraktifsunucu=_myIslem.fn_aktif_sunucu();
        _ayaraktifisletmeeslesme=_myIslem.fn_isletmeeslesme();

    }

    public frg_demirbas_sayim_menu_panel() {
        // Required empty public constructor
    }

    public static frg_demirbas_sayim_menu_panel newInstance()
    {
        return new frg_demirbas_sayim_menu_panel();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_demirbas_sayim_menu_panel, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        _myIslem = new VeriTabani(getContext());

        fn_AyarlariYukle();

        _btnDemirbasArama=(Button)getView().findViewById(R.id.btnDemirbasArama);
        _btnDemirbasArama.playSoundEffect(SoundEffectConstants.CLICK);
        _btnDemirbasArama.setOnClickListener(new fn_btnDemirbasArama());

        _btnDemirbasSorgula=(Button)getView().findViewById(R.id.btnDemirbasSorgula);
        _btnDemirbasSorgula.playSoundEffect(SoundEffectConstants.CLICK);
        _btnDemirbasSorgula.setOnClickListener(new fn_btnDemirbasSorgula());

        _btnEtiketBasimKontrol=(Button)getView().findViewById(R.id.btnEtiketBasimKontrol);
        _btnEtiketBasimKontrol.playSoundEffect(SoundEffectConstants.CLICK);
        _btnEtiketBasimKontrol.setOnClickListener(new fn_btnEtiketBasimKontrol());

        _btnDemirbasSayimiOlustur=(Button)getView().findViewById(R.id.btnDemirbasSayimiOlustur);
        _btnDemirbasSayimiOlustur.playSoundEffect(SoundEffectConstants.CLICK);
        _btnDemirbasSayimiOlustur.setOnClickListener(new fn_btnDemirbasSayimiOlustur());

        _btnDemirbasSay=(Button)getView().findViewById(R.id.btnDemirbasSay);
        _btnDemirbasSay.playSoundEffect(SoundEffectConstants.CLICK);
        _btnDemirbasSay.setOnClickListener(new fn_btnDemirbasSay());

        _btnGeri=(Button)getView().findViewById(R.id.btnGeri);
        _btnGeri.playSoundEffect(0);
        _btnGeri.setOnClickListener(new fn_btnGeri());

    }

    private class fn_btnDemirbasArama implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_demirbas_arama_menu_panel fragmentyeni = new frg_demirbas_arama_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_demirbas_arama_menu_panel").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_btnDemirbasSorgula implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_demirbas_sorgula fragmentyeni = new frg_demirbas_sorgula();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_demirbas_sorgula").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_btnEtiketBasimKontrol implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_db_isletme_secimi fragmentyeni = new frg_db_isletme_secimi();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_db_isletme_secimi").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_btnDemirbasSayimiOlustur implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_ds_isletme_secimi fragmentyeni = new frg_ds_isletme_secimi();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_ds_isletme_secimi").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_btnDemirbasSay implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_ds_isemri_secimi fragmentyeni = new frg_ds_isemri_secimi();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_ds_isemri_secimi").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_btnGeri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_sayim_menu_panel fragmentyeni = new frg_sayim_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_sayim_menu_panel").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

}


