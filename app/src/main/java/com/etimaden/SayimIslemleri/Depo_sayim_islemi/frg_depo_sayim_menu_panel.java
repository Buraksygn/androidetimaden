package com.etimaden.SayimIslemleri.Depo_sayim_islemi;

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

import com.etimaden.cIslem.VeriTabani;
import com.etimaden.depolarArasiSevkIslemi.Depo_cikis.frg_depo_secimi_transfer;
import com.etimaden.depolarArasiSevkIslemi.Depo_giris.frg_lot_degistirme_onayi;
import com.etimaden.depolarArasiSevkIslemi.Urun_sorgulama.frg_urun_sorgulama;
import com.etimaden.frg_ana_sayfa;
import com.etimaden.ugr_demo.R;

import retrofit2.Retrofit;

public class frg_depo_sayim_menu_panel extends Fragment {

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

    Button _btnDepoSayim;
    Button _btnUrunuSayimDisiBirak;
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

    public frg_depo_sayim_menu_panel() {
        // Required empty public constructor
    }

    public static frg_depo_sayim_menu_panel newInstance()
    {
        return new frg_depo_sayim_menu_panel();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_depo_sayim_menu_panel, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        _myIslem = new VeriTabani(getContext());

        fn_AyarlariYukle();

        _btnDepoSayim=(Button)getView().findViewById(R.id.btnDepoSayim);
        _btnDepoSayim.playSoundEffect(SoundEffectConstants.CLICK);
        _btnDepoSayim.setOnClickListener(new fn_btnDepoSayim());

        _btnUrunuSayimDisiBirak=(Button)getView().findViewById(R.id.btnUrunuSayimDisiBirak);
        _btnUrunuSayimDisiBirak.playSoundEffect(SoundEffectConstants.CLICK);
        _btnUrunuSayimDisiBirak.setOnClickListener(new fn_btnUrunuSayimDisiBirak());

        _btnGeri=(Button)getView().findViewById(R.id.btnGeri);
        _btnGeri.playSoundEffect(0);
        _btnGeri.setOnClickListener(new fn_btnGeri());

        if (_ayaraktiftesis.equals("1001")) {
            _btnDepoSayim.setVisibility(View.INVISIBLE);
        }

    }

    private class fn_btnDepoSayim implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_depo_secimi_transfer fragmentyeni = new frg_depo_secimi_transfer();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_depo_secimi_transfer").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_btnUrunuSayimDisiBirak implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_sayim_islemi_urun_aktivasyon fragmentyeni = new frg_sayim_islemi_urun_aktivasyon();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_sayim_islemi_urun_aktivasyon").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_btnGeri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_ana_sayfa fragmentyeni = new frg_ana_sayfa();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_ana_sayfa").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

}


