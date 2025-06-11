package com.etimaden;

import static com.etimaden.cSabitDegerler._ipAdresi3G;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.Elden_satis_islemi.frg_perakende_satis_is_emri_secimi;
import com.etimaden.GemiIslemleri.frg_aktif_gemi_secimi;
import com.etimaden.SayimIslemleri.frg_sayim_menu_panel;
import com.etimaden.SevkiyatIslemleri.frg_isemri_degistir;
import com.etimaden.SevkiyatIslemleri.frg_satilmis_etiket;
import com.etimaden.SevkiyatIslemleri.frg_sevkiyat_menu_panel;
import com.etimaden.UretimIslemleri.frg_uretim_menu_panel;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.depolarArasiSevkIslemi.frg_depolar_arasi_transfer_menu_panel;
import com.etimaden.digerislemler.frg_sifre_degistir;
import com.etimaden.manipulasyon.frg_manipulasyon_menu_panel;
import com.etimaden.persos.Persos;
import com.etimaden.ugr_demo.MainActivity;
import com.etimaden.ugr_demo.R;

public class frg_ana_sayfa extends Fragment
{
    ImageView _btnCikis;
    ImageView _imgsifredegistir;

    Button _btnUretim;
    Button _btnSevkiyat;
 //   Button _btnSatilmisEtiket;
    Button _btnmanipulasyon;
    Button _btnTest;
    Button _btndepoduzenleme;
    Button _btneldensatis;
    Button _btnsayim;

    Button _btngemifoy;


    //TEST START
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
    //TEST END

    public frg_ana_sayfa() {
        // Required empty public constructor
    }

    public static frg_ana_sayfa newInstance()
    {
        return new frg_ana_sayfa();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_ana_sayfa, container, false);
    }

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


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        _myIslem = new VeriTabani(getContext());

        fn_AyarlariYukle();

        _btngemifoy = view.findViewById(R.id.btngemifoy);
        //if(_ayaraktiftesis.equals("2003") || _ayaraktifalttesis.equals("2004-01")){
        //    _btngemifoy.setVisibility(View.VISIBLE);
        //}else{
        //    _btngemifoy.setVisibility(View.GONE);
        //}
        //// Veriyi çekmek
        //SharedPreferences sharedPref = getActivity().getSharedPreferences("liman", Context.MODE_PRIVATE);
        //boolean isBanliman = sharedPref.getBoolean("banliman", false);  // default false
        //if (isBanliman) {
        //    //Log.d("Banliman", "Veri true");
        //    _btngemifoy.setVisibility(View.VISIBLE);
        //} else {
        //    _btngemifoy.setVisibility(View.GONE);
        //}
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

      //  ((GirisSayfasi) getActivity()).fn_ModBarkod();

        ((GirisSayfasi)getActivity()).fn_ListeTemizle();
        ((GirisSayfasi) getActivity()).fn_ModBarkod();

        _btnCikis=(ImageView)getView().findViewById(R.id.imgCikis);
        _btnCikis.playSoundEffect(SoundEffectConstants.CLICK);
        _btnCikis.setOnClickListener(new fn_Cikis());

        _imgsifredegistir=(ImageView)getView().findViewById(R.id.imgsifredegistir);
        _imgsifredegistir.playSoundEffect(SoundEffectConstants.CLICK);
        _imgsifredegistir.setOnClickListener(new fn_sifredegistir());

        _btnSevkiyat=(Button)getView().findViewById(R.id.btnSevkiyat);
        _btnSevkiyat.playSoundEffect(SoundEffectConstants.CLICK);
        _btnSevkiyat.setOnClickListener(new fn_Sevkiyat());

        _btnUretim= (Button)getView().findViewById(R.id.btnUretim);
        _btnUretim.playSoundEffect(SoundEffectConstants.CLICK);
        _btnUretim.setOnClickListener(new fn_Uretim());

        //_btnSatilmisEtiket= (Button)getView().findViewById(R.id.btnSatilmisEtiket);
        //_btnSatilmisEtiket.playSoundEffect(0);
        //_btnSatilmisEtiket.setOnClickListener(new fn_btnSatilmisEtiket());

        _btnmanipulasyon= (Button)getView().findViewById(R.id.btnmanipulasyon);
        _btnmanipulasyon.playSoundEffect(SoundEffectConstants.CLICK);
        _btnmanipulasyon.setOnClickListener(new fn_btnmanipulasyon());

        _btndepoduzenleme= (Button)getView().findViewById(R.id.btndepoduzenleme);
        _btndepoduzenleme.playSoundEffect(SoundEffectConstants.CLICK);
        _btndepoduzenleme.setOnClickListener(new fn_btndepoduzenleme());

        _btneldensatis= (Button)getView().findViewById(R.id.btneldensatis);
        _btneldensatis.playSoundEffect(SoundEffectConstants.CLICK);
        _btneldensatis.setOnClickListener(new fn_btneldensatis());

        _btnsayim= (Button)getView().findViewById(R.id.btnsayim);
        _btnsayim.playSoundEffect(SoundEffectConstants.CLICK);
        _btnsayim.setOnClickListener(new fn_btnsayim()); //_btnsayim

        _btngemifoy = (Button)getView().findViewById(R.id.btngemifoy);
        _btngemifoy.playSoundEffect(SoundEffectConstants.CLICK);
        //_btngemifoy.setOnClickListener(new fn_gemifoy());
        _btngemifoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frg_aktif_gemi_secimi fragmentyeni = new frg_aktif_gemi_secimi();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayoutForFragments,fragmentyeni,"frg_aktif_gemi_secimi").addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    public void fn_BarkodOkutuldu(final String barcode)
    {

    }

    private class fn_Cikis implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            ((GirisSayfasi)getActivity()).fn_Cikis();
        }
    }

    private class fn_Sevkiyat implements View.OnClickListener {
        @Override
        public void onClick(View view)
        {
            frg_sevkiyat_menu_panel fragmentyeni = new frg_sevkiyat_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_sevkiyat_menu_panel").addToBackStack(null);
            fragmentTransaction.commit();


            /*
            Fragment fragment = null;
            fragment=new frg_sevkiyat_menu_panel().newInstance();

            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, TAG_CONTENT_FRAGMENT).addToBackStack(null).commit();
            */

        }
    }

    private class fn_Test implements View.OnClickListener {
        @Override
        public void onClick(View view) {
           /*
            frg_test_01 fragmentyeni = new frg_test_01();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_test_01").addToBackStack(null);
            fragmentTransaction.commit();
            */

            frg_isemri_degistir fragmentyeni = new frg_isemri_degistir();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_isemri_degistir").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_Uretim implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_uretim_menu_panel fragmentyeni = new frg_uretim_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_uretim_menu_panel").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
    private class fn_btnSatilmisEtiket implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            frg_satilmis_etiket fragmentyeni = new frg_satilmis_etiket();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_satilmis_etiket").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_btnmanipulasyon implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            frg_manipulasyon_menu_panel fragmentyeni = new frg_manipulasyon_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_manipulasyon_menu_panel").addToBackStack(null);
            fragmentTransaction.commit();

        }
    }

    private class fn_btndepoduzenleme implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            frg_depolar_arasi_transfer_menu_panel fragmentyeni = new frg_depolar_arasi_transfer_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_depolar_arasi_transfer_menu_panel").addToBackStack(null);
            fragmentTransaction.commit();

        }
    }

    private class fn_btneldensatis implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            frg_perakende_satis_is_emri_secimi fragmentyeni = new frg_perakende_satis_is_emri_secimi();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_perakende_satis_is_emri_secimi").addToBackStack(null);
            fragmentTransaction.commit();

        }
    }
    private class fn_btnsayim implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            //frg_sayim_menu_panel fragmentyeni = new frg_sayim_menu_panel();
            //FragmentManager fragmentManager = getFragmentManager();
            //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_sayim_menu_panel").addToBackStack(null);
            //fragmentTransaction.commit();

        }
    }

    private class fn_sifredegistir implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            frg_sifre_degistir fragmentyeni = new frg_sifre_degistir();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_sifre_degistir").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}
