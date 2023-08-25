package com.etimaden.UretimIslemleri;

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
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.frg_ana_sayfa;
import com.etimaden.senkronResponse.ViewtoplamaTest;
import com.etimaden.senkronResult.requesttoplamaTest;
import com.etimaden.servisbaglanti.test_Controller;
import com.etimaden.ugr_demo.R;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.etimaden.cSabitDegerler._ipAdresi3G;
import static com.etimaden.cSabitDegerler._zport3G;

public class frg_uretim_menu_panel extends Fragment {


    Button _btnYeniUretim;
    Button _btnShrinkOlusturma;
    Button _btnShrinkEslestirme;
    Button _btnUretimIptali;
    Button _btnUretimSorgulama;
    Button _btnUretimZayi;
    Button _btnYariOtomatikUretim;
    Button _btngeri;


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

        StrictMode.ThreadPolicy gfgPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(gfgPolicy);
    }

    public frg_uretim_menu_panel() {
        // Required empty public constructor
    }

    public static frg_uretim_menu_panel newInstance()
    {
        return new frg_uretim_menu_panel();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_uretim_menu_panel, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        new VeriTabani(getContext()).fn_EpcTemizle();

        _myIslem = new VeriTabani(getContext());

        fn_AyarlariYukle();

        ((GirisSayfasi) getActivity()).fn_ModBarkod();
        ((GirisSayfasi) getActivity()).fn_ListeTemizle();

        _btnYeniUretim=(Button)getView().findViewById(R.id.btnYeniUretim);
        _btnYeniUretim.playSoundEffect(0);
        _btnYeniUretim.setOnClickListener(new fn_YeniUretim());

        _btnShrinkOlusturma=(Button)getView().findViewById(R.id.btnShrinkOlusturma);
        _btnShrinkOlusturma.playSoundEffect(0);
        _btnShrinkOlusturma.setOnClickListener(new fn_ShrinkOlusturma());

        _btnShrinkEslestirme=(Button)getView().findViewById(R.id.btnShrinkEslestirme);
        _btnShrinkEslestirme.playSoundEffect(0);
        _btnShrinkEslestirme.setOnClickListener(new fn_ShrinkEslestirme());

        _btnUretimIptali=(Button)getView().findViewById(R.id.btnUretimIptali);
        _btnUretimIptali.playSoundEffect(0);
        _btnUretimIptali.setOnClickListener(new fn_UretimIptali());

        _btnUretimSorgulama=(Button)getView().findViewById(R.id.btnUretimSorgulama);
        _btnUretimSorgulama.playSoundEffect(0);
        _btnUretimSorgulama.setOnClickListener(new fn_UretimSorgulama());

        _btnUretimZayi=(Button)getView().findViewById(R.id.btnUretimZayi);
        _btnUretimZayi.playSoundEffect(0);
        _btnUretimZayi.setOnClickListener(new fn_UretimZayi());

        _btnYariOtomatikUretim=(Button)getView().findViewById(R.id.btnYariOtomatikUretim);
        _btnYariOtomatikUretim.playSoundEffect(0);
        _btnYariOtomatikUretim.setOnClickListener(new fn_YariOtomatikUretim());

        _btngeri=(Button)getView().findViewById(R.id.btncikis);
        _btngeri.playSoundEffect(0);
        _btngeri.setOnClickListener(new fn_Geri());

        String _OnlineUrl = "http://"+_ipAdresi3G+":"+_zport3G+"/";


        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(75, TimeUnit.SECONDS)
                .connectTimeout(75, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(_OnlineUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

    }



    private class fn_YeniUretim implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_paket_uretim_ekrani fragmentyeni = new frg_paket_uretim_ekrani();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_paket_uretim_ekrani").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_ShrinkOlusturma implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_shrink_ayirma fragmentyeni = new frg_shrink_ayirma();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_shrink_ayirma").addToBackStack(null);
            fragmentTransaction.commit();

        }
    }

    private class fn_ShrinkEslestirme implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_shrink_onay fragmentyeni = new frg_shrink_onay();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_shrink_onay").addToBackStack(null);
            fragmentTransaction.commit();

        }
    }
    private class fn_UretimIptali implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_uretim_iptal fragmentyeni = new frg_uretim_iptal();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_uretim_iptal").addToBackStack(null);
            fragmentTransaction.commit();

        }
    }
    private class fn_UretimSorgulama implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            frg_uretim_detay_ekrani fragmentyeni = new frg_uretim_detay_ekrani();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_uretim_detay_ekrani").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
    private class fn_UretimZayi implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_uretim_zayi fragmentyeni = new frg_uretim_zayi();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_uretim_zayi").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
    private class fn_YariOtomatikUretim implements View.OnClickListener {
        @Override
        public void onClick(View view) {

        }
    }


    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_ana_sayfa fragmentyeni = new frg_ana_sayfa();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_ana_sayfa").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }




    private class fn_YeniUretim_01 implements View.OnClickListener {
        @Override
        public void onClick(View v)
        {
            int _Sayi_01=0;
            int _Sayi_02=0;
            int _Sayi_03=0;


            requesttoplamaTest v_Param = new requesttoplamaTest();
            v_Param._girilendeger="4";
            test_Controller _Servis = retrofit.create(test_Controller.class);
            Call<ViewtoplamaTest> callSync_01 = _Servis.fn_toplamaTest_01(v_Param);


            try {
                Response<ViewtoplamaTest> _Response = callSync_01.execute();



                ViewtoplamaTest _Yanit = _Response.body();
                _Sayi_01 = _Yanit.get_toplam();



                 v_Param = new requesttoplamaTest();
                v_Param._girilendeger="5";

                Call<ViewtoplamaTest> callSync_02 = _Servis.fn_toplamaTest_02(v_Param);
                _Response = callSync_02.execute();
                _Yanit = _Response.body();
                _Sayi_02 = _Yanit.get_toplam();

                _Sayi_03 = _Sayi_01 + _Sayi_02;



                _btnYeniUretim.setText("Cevap = "+_Sayi_03);

                Toast.makeText(getContext(), "Cevap = "+_Sayi_03, Toast.LENGTH_SHORT).show();


            } catch (IOException e) {
                Toast.makeText(getContext(), "Hata = "+e.getMessage(), Toast.LENGTH_SHORT).show();
                _btnYeniUretim.setText("Hata = "+e.getMessage());
                int _Dur = 1 ;
                e.printStackTrace();
            }


        }
    }
}


