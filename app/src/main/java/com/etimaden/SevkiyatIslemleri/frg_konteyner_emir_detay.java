package com.etimaden.SevkiyatIslemleri;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.GirisSayfasi;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cResponseResult.Sevkiyat_isemri;
import com.etimaden.ugr_demo.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class frg_konteyner_emir_detay extends Fragment {

    public Sevkiyat_isemri aktif_sevk_isemri ;

    SweetAlertDialog pDialog;

    Button _btngeri;
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

    TextView _txtAciklama;


    public frg_konteyner_emir_detay() {
        // Required empty public constructor
    }

    public static frg_konteyner_emir_detay newInstance() {

        return new frg_konteyner_emir_detay();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_konteyner_emir_detay, container, false);
    }


    public void fn_senddata(Sevkiyat_isemri v_aktif_sevk_isemri)
    {
        this.aktif_sevk_isemri=v_aktif_sevk_isemri;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        _myIslem = new VeriTabani(getContext());

        ((GirisSayfasi)getActivity()).fn_ModRFID();

        fn_AyarlariYukle();

        new VeriTabani(getContext()).fn_EpcTemizle();

        ((GirisSayfasi) getActivity()).fn_ListeTemizle();
        ((GirisSayfasi) getActivity()).fn_GucAyarla(248);

        _btngeri = (Button) getView().findViewById(R.id.btngeri);
        _btngeri.playSoundEffect(0);
        _btngeri.setOnClickListener(new fn_Geri());

        _txtAciklama=(TextView)getView().findViewById(R.id.txtaciklama);

        fn_AracBilgileri();
    }

    private void fn_AracBilgileri() {

        String _Yazi="";

        if (aktif_sevk_isemri.alici_isletme.equals(""))
        {
            _Yazi += "ALICI : " + aktif_sevk_isemri.alici + "\n\n";
        //    _Yazi += "BOOKING NO : " + aktif_sevk_isemri.bookingno+ "\n\n";
        }
        else
            _Yazi += "ALICI : " + aktif_sevk_isemri.alici_isletme + "\n\n";
        _Yazi += "ARAÇ PLAKASI : " + aktif_sevk_isemri.arac_plaka + "\n\n";
        if (!aktif_sevk_isemri.konteyner_turu.equals(""))
            _Yazi += "KONTEYNER  : " + aktif_sevk_isemri.kont_kodu+ "\n\n";
        _Yazi += "SAP KODU : " + aktif_sevk_isemri.kod_sap + "\n\n";
        _Yazi += "ÜRÜN ADI : " + aktif_sevk_isemri.urun_adi+ "\n\n";
        _Yazi += "TORBA AĞIRLIĞI : " + aktif_sevk_isemri.miktar_torba + "\n\n";
        _Yazi += "PALET AĞIRLIĞI : " + aktif_sevk_isemri.palet_agirligi + "\n\n";
        _Yazi += "YAPILAN ADET : " + aktif_sevk_isemri.yapilan_adet + "\n\n";
        _Yazi += "YAPILAN MİKTAR : " + aktif_sevk_isemri.yapilan_miktar + "\n\n";
        _Yazi += "İŞEMRİ KALAN ADET : " + aktif_sevk_isemri.kalan_palet_sayisi + "\n\n";
        _Yazi += "İŞEMRİ KALAN MİKTAR : " + aktif_sevk_isemri.kalan_agirlik + "\n\n";

        _txtAciklama.setText(_Yazi);
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
    }

    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_konteyner_bulundu fragmentyeni = new frg_konteyner_bulundu();
            fragmentyeni.fn_senddata(aktif_sevk_isemri);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_konteyner_bulundu").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}
