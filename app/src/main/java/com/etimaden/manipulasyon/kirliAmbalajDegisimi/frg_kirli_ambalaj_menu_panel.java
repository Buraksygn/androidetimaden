package com.etimaden.manipulasyon.kirliAmbalajDegisimi;

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

import com.etimaden.cIslem.VeriTabani;
import com.etimaden.frg_ana_sayfa;
import com.etimaden.manipulasyon.Ambalaj_tipi_degisimi.frg_ambalaj_tipi_degisimi;
import com.etimaden.manipulasyon.frg_manipulasyon_menu_panel;
import com.etimaden.ugr_demo.R;

import retrofit2.Retrofit;

public class frg_kirli_ambalaj_menu_panel extends Fragment {

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


    Button _btnUrunAyirmaIslemi;
    Button _btnIslemTamamla;
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

    public frg_kirli_ambalaj_menu_panel() {
        // Required empty public constructor
    }

    public static frg_kirli_ambalaj_menu_panel newInstance()
    {
        return new frg_kirli_ambalaj_menu_panel();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_kirli_ambalaj_menu_panel, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        _myIslem = new VeriTabani(getContext());

        fn_AyarlariYukle();

        _btnUrunAyirmaIslemi=(Button)getView().findViewById(R.id.btnUrunAyirmaIslemi);
        _btnUrunAyirmaIslemi.playSoundEffect(0);
        _btnUrunAyirmaIslemi.setOnClickListener(new fn_btnUrunAyirmaIslemi());

        _btnIslemTamamla=(Button)getView().findViewById(R.id.btnIslemTamamla);
        _btnIslemTamamla.playSoundEffect(0);
        _btnIslemTamamla.setOnClickListener(new fn_btnIslemTamamla());

        _btnGeri=(Button)getView().findViewById(R.id.btnGeri);
        _btnGeri.playSoundEffect(0);
        _btnGeri.setOnClickListener(new fn_btnGeri());

    }

    private class fn_btnUrunAyirmaIslemi implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_kirli_ambalaj_degisimi fragmentyeni = new frg_kirli_ambalaj_degisimi();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_kirli_ambalaj_degisimi").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_btnIslemTamamla implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_kirli_ambalaj_degisim_onay fragmentyeni = new frg_kirli_ambalaj_degisim_onay();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_kirli_ambalaj_degisim_onay").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_btnGeri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_manipulasyon_menu_panel fragmentyeni = new frg_manipulasyon_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_manipulasyon_menu_panel").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

}


