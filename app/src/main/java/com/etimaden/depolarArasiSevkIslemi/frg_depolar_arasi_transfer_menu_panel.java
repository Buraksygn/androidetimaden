package com.etimaden.depolarArasiSevkIslemi;

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
import com.etimaden.depolarArasiSevkIslemi.Lot_detay_sorgulama.frg_lot_sorgulama;
import com.etimaden.depolarArasiSevkIslemi.Urun_sorgulama.frg_urun_sorgulama;
import com.etimaden.depolarArasiSevkIslemi.sayilamayan_aktivasyon.frg_sayilamayan_ayirma;
import com.etimaden.depolarArasiSevkIslemi.sayilamayan_aktivasyon.frg_sayilamayan_onay;
import com.etimaden.frg_ana_sayfa;
import com.etimaden.manipulasyon.Ambalaj_tipi_degisimi.frg_ambalaj_tipi_degisimi;
import com.etimaden.manipulasyon.ellecleme.frg_ellecleme_menu_panel;
import com.etimaden.manipulasyon.geribesleme.frg_geribesleme_menu_panel;
import com.etimaden.manipulasyon.kirliAmbalajDegisimi.frg_kirli_ambalaj_menu_panel;
import com.etimaden.manipulasyon.palet_duzenleme_islemleri.frg_palet_dagitma_islemi;
import com.etimaden.ugr_demo.R;

import retrofit2.Retrofit;

public class frg_depolar_arasi_transfer_menu_panel extends Fragment {

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

    Button _btnDepoTransferIslemleri;
    Button _btnLotDegisimi;
    Button _btnUrunSorgulama;
    Button _btnSatilamayanEtiketAyirma;
    Button _btnSatilamayanEtiketDegisimi;
    Button _btnLotDetaySorgulama;
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

    public frg_depolar_arasi_transfer_menu_panel() {
        // Required empty public constructor
    }

    public static frg_depolar_arasi_transfer_menu_panel newInstance()
    {
        return new frg_depolar_arasi_transfer_menu_panel();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_depolar_arasi_transfer_menu_panel, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        _myIslem = new VeriTabani(getContext());

        fn_AyarlariYukle();

        _btnDepoTransferIslemleri=(Button)getView().findViewById(R.id.btnDepoTransferIslemleri);
        _btnDepoTransferIslemleri.playSoundEffect(SoundEffectConstants.CLICK);
        _btnDepoTransferIslemleri.setOnClickListener(new fn_btnDepoTransferIslemleri());

        _btnLotDegisimi=(Button)getView().findViewById(R.id.btnLotDegisimi);
        _btnLotDegisimi.playSoundEffect(SoundEffectConstants.CLICK);
        _btnLotDegisimi.setOnClickListener(new fn_btnLotDegisimi());

        _btnUrunSorgulama=(Button)getView().findViewById(R.id.btnUrunSorgulama);
        _btnUrunSorgulama.playSoundEffect(SoundEffectConstants.CLICK);
        _btnUrunSorgulama.setOnClickListener(new fn_btnUrunSorgulama());

        _btnSatilamayanEtiketAyirma=(Button)getView().findViewById(R.id.btnSatilamayanEtiketAyirma);
        _btnSatilamayanEtiketAyirma.playSoundEffect(SoundEffectConstants.CLICK);
        _btnSatilamayanEtiketAyirma.setOnClickListener(new fn_btnSatilamayanEtiketAyirma());

        _btnSatilamayanEtiketDegisimi=(Button)getView().findViewById(R.id.btnSatilamayanEtiketDegisimi);
        _btnSatilamayanEtiketDegisimi.playSoundEffect(SoundEffectConstants.CLICK);
        _btnSatilamayanEtiketDegisimi.setOnClickListener(new fn_btnSatilamayanEtiketDegisimi());

        _btnLotDetaySorgulama=(Button)getView().findViewById(R.id.btnLotDetaySorgulama);
        _btnLotDetaySorgulama.playSoundEffect(SoundEffectConstants.CLICK);
        _btnLotDetaySorgulama.setOnClickListener(new fn_btnLotDetaySorgulama());

        _btnGeri=(Button)getView().findViewById(R.id.btnGeri);
        _btnGeri.playSoundEffect(0);
        _btnGeri.setOnClickListener(new fn_btnGeri());

    }

    private class fn_btnDepoTransferIslemleri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_depo_secimi_transfer fragmentyeni = new frg_depo_secimi_transfer();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_depo_secimi_transfer").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_btnLotDegisimi implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_lot_degistirme_onayi fragmentyeni = new frg_lot_degistirme_onayi();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_lot_degistirme_onayi").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_btnUrunSorgulama implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_urun_sorgulama fragmentyeni = new frg_urun_sorgulama();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_urun_sorgulama").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_btnSatilamayanEtiketAyirma implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_sayilamayan_ayirma fragmentyeni = new frg_sayilamayan_ayirma();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_sayilamayan_ayirma").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_btnSatilamayanEtiketDegisimi implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_sayilamayan_onay fragmentyeni = new frg_sayilamayan_onay();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_sayilamayan_onay").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_btnLotDetaySorgulama implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_lot_sorgulama fragmentyeni = new frg_lot_sorgulama();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_lot_sorgulama").addToBackStack(null);
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


