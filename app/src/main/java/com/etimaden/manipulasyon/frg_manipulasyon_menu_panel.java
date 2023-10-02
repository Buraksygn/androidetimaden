package com.etimaden.manipulasyon;

import static com.etimaden.cSabitDegerler._ipAdresi3G;
import static com.etimaden.cSabitDegerler._zport3G;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.GirisSayfasi;
import com.etimaden.UretimIslemleri.Paket_uretim.frg_shrink_ayirma;
import com.etimaden.UretimIslemleri.Paket_uretim.frg_shrink_onay;
import com.etimaden.UretimIslemleri.Paket_uretim.frg_uretim_iptal;
import com.etimaden.UretimIslemleri.Paket_uretim.frg_uretim_zayi;
import com.etimaden.UretimIslemleri.Uretim_sorgulama.frg_uretim_detay_ekrani;
import com.etimaden.UretimIslemleri.frg_paket_uretim_ekrani;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.frg_ana_sayfa;
import com.etimaden.manipulasyon.Ambalaj_tipi_degisimi.frg_ambalaj_tipi_degisimi;
import com.etimaden.manipulasyon.kirliAmbalajDegisimi.frg_kirli_ambalaj_menu_panel;
import com.etimaden.senkronResponse.ViewtoplamaTest;
import com.etimaden.senkronResult.requesttoplamaTest;
import com.etimaden.servisbaglanti.test_Controller;
import com.etimaden.ugr_demo.R;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class frg_manipulasyon_menu_panel extends Fragment {

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

    Button _btnAmbalajTuruDegismi;
    Button _btnKirliAmbalajDegisimi;
    Button _btnEllecleme;
    Button _btnDokmeUruneCevir;
    Button _btnPaletDagitmaIslemi;
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

    public frg_manipulasyon_menu_panel() {
        // Required empty public constructor
    }

    public static frg_manipulasyon_menu_panel newInstance()
    {
        return new frg_manipulasyon_menu_panel();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_manipulasyon_menu_panel, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        _myIslem = new VeriTabani(getContext());

        fn_AyarlariYukle();

        _btnAmbalajTuruDegismi=(Button)getView().findViewById(R.id.btnAmbalajTuruDegismi);
        _btnAmbalajTuruDegismi.playSoundEffect(0);
        _btnAmbalajTuruDegismi.setOnClickListener(new fn_btnAmbalajTuruDegismi());

        _btnKirliAmbalajDegisimi=(Button)getView().findViewById(R.id.btnKirliAmbalajDegisimi);
        _btnKirliAmbalajDegisimi.playSoundEffect(0);
        _btnKirliAmbalajDegisimi.setOnClickListener(new fn_btnKirliAmbalajDegisimi());

        _btnEllecleme=(Button)getView().findViewById(R.id.btnEllecleme);
        _btnEllecleme.playSoundEffect(0);
        _btnEllecleme.setOnClickListener(new fn_btnEllecleme());

        _btnDokmeUruneCevir=(Button)getView().findViewById(R.id.btnDokmeUruneCevir);
        _btnDokmeUruneCevir.playSoundEffect(0);
        _btnDokmeUruneCevir.setOnClickListener(new fn_btnDokmeUruneCevir());

        _btnPaletDagitmaIslemi=(Button)getView().findViewById(R.id.btnPaletDagitmaIslemi);
        _btnPaletDagitmaIslemi.playSoundEffect(0);
        _btnPaletDagitmaIslemi.setOnClickListener(new fn_btnPaletDagitmaIslemi());

        _btnGeri=(Button)getView().findViewById(R.id.btnGeri);
        _btnGeri.playSoundEffect(0);
        _btnGeri.setOnClickListener(new fn_btnGeri());

    }

    private class fn_btnAmbalajTuruDegismi implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_ambalaj_tipi_degisimi fragmentyeni = new frg_ambalaj_tipi_degisimi();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_ambalaj_tipi_degisimi").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_btnKirliAmbalajDegisimi implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_kirli_ambalaj_menu_panel fragmentyeni = new frg_kirli_ambalaj_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_kirli_ambalaj_menu_panel").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_btnEllecleme implements View.OnClickListener {
        @Override
        public void onClick(View view) {

        }
    }

    private class fn_btnDokmeUruneCevir implements View.OnClickListener {
        @Override
        public void onClick(View view) {

        }
    }

    private class fn_btnPaletDagitmaIslemi implements View.OnClickListener {
        @Override
        public void onClick(View view) {

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


