package com.etimaden.SayimIslemleri.Demirbas_sayim_islemi.Demirbas_arama;

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

import com.etimaden.SayimIslemleri.Demirbas_sayim_islemi.frg_demirbas_sayim_menu_panel;
import com.etimaden.SayimIslemleri.Depo_sayim_islemi.frg_sayim_islemi_urun_aktivasyon;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.depolarArasiSevkIslemi.Depo_cikis.frg_depo_secimi_transfer;
import com.etimaden.frg_ana_sayfa;
import com.etimaden.ugr_demo.R;

import retrofit2.Retrofit;

public class frg_demirbas_arama_menu_panel extends Fragment {

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

    Button _btnIsemriIleDemirbasArama;
    Button _btnDemirbasNoIleDemirbasArama;
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

    public frg_demirbas_arama_menu_panel() {
        // Required empty public constructor
    }

    public static frg_demirbas_arama_menu_panel newInstance()
    {
        return new frg_demirbas_arama_menu_panel();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_demirbas_arama_menu_panel, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        _myIslem = new VeriTabani(getContext());

        fn_AyarlariYukle();

        _btnIsemriIleDemirbasArama=(Button)getView().findViewById(R.id.btnIsemriIleDemirbasArama);
        _btnIsemriIleDemirbasArama.playSoundEffect(SoundEffectConstants.CLICK);
        _btnIsemriIleDemirbasArama.setOnClickListener(new fn_btnIsemriIleDemirbasArama());

        _btnDemirbasNoIleDemirbasArama=(Button)getView().findViewById(R.id.btnDemirbasNoIleDemirbasArama);
        _btnDemirbasNoIleDemirbasArama.playSoundEffect(SoundEffectConstants.CLICK);
        _btnDemirbasNoIleDemirbasArama.setOnClickListener(new fn_btnDemirbasNoIleDemirbasArama());

        _btnGeri=(Button)getView().findViewById(R.id.btnGeri);
        _btnGeri.playSoundEffect(0);
        _btnGeri.setOnClickListener(new fn_btnGeri());

    }

    private class fn_btnIsemriIleDemirbasArama implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_da_isemri_secimi fragmentyeni = new frg_da_isemri_secimi();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_da_isemri_secimi").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_btnDemirbasNoIleDemirbasArama implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_da_demirbasno_girisi fragmentyeni = new frg_da_demirbasno_girisi();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_da_demirbasno_girisi").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_btnGeri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_demirbas_sayim_menu_panel fragmentyeni = new frg_demirbas_sayim_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_demirbas_sayim_menu_panel").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

}


