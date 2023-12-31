package com.etimaden;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.Elden_satis_islemi.frg_perakende_satis_is_emri_secimi;
import com.etimaden.SevkiyatIslemleri.frg_isemri_degistir;
import com.etimaden.SevkiyatIslemleri.frg_satilmis_etiket;
import com.etimaden.SevkiyatIslemleri.frg_sevkiyat_menu_panel;
import com.etimaden.UretimIslemleri.frg_uretim_menu_panel;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.depolarArasiSevkIslemi.frg_depolar_arasi_transfer_menu_panel;
import com.etimaden.digerislemler.frg_sifre_degistir;
import com.etimaden.manipulasyon.frg_manipulasyon_menu_panel;
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
