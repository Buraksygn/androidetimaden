package com.etimaden.SevkiyatIslemleri.Arac_aktivayon_islemleri;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.GirisSayfasi;
import com.etimaden.SevkiyatIslemleri.frg_arac_onayla;
import com.etimaden.SevkiyatIslemleri.frg_arac_secim_bilgi;
import com.etimaden.SevkiyatIslemleri.frg_isemri_degistir;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cResponseResult.Sevkiyat_isemri;
import com.etimaden.genel.SweetAlertDialogG;
import com.etimaden.ugr_demo.R;


public class frg_arac_bulundu extends Fragment {

    public Sevkiyat_isemri aktif_sevk_isemri ;

    private Handler handler;
    //SweetAlertDialogG pDialog;

    ImageView _img;

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
    TextView _txtdepoadi;

    Button _btnileri;

    Button _btnIsEmriDegistir;

    public frg_arac_bulundu() {
        // Required empty public constructor
    }

    public static frg_arac_bulundu newInstance() {

        return new frg_arac_bulundu();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_arac_bulundu, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        _myIslem = new VeriTabani(getContext());

        _txtAciklama=(TextView)getView().findViewById(R.id.txtaciklama);

        _txtdepoadi=(TextView)getView().findViewById(R.id.txtdepoadi);

        fn_AyarlariYukle();

        new VeriTabani(getContext()).fn_EpcTemizle();

        ((GirisSayfasi) getActivity()).fn_ListeTemizle();

        ((GirisSayfasi) getActivity()).fn_GucAyarla(248);

        _btngeri = (Button) getView().findViewById(R.id.btngeri);
        _btngeri.playSoundEffect(0);
        _btngeri.setOnClickListener(new fn_Geri());

        _btnileri = (Button) getView().findViewById(R.id.btnileri);
        _btnileri.playSoundEffect(0);
        _btnileri.setOnClickListener(new fn_Ileri());

        _btnIsEmriDegistir=(Button)getView().findViewById(R.id.btnisemridegistir);
        _btnIsEmriDegistir.playSoundEffect(0);
        _btnIsEmriDegistir.setOnClickListener(new fn_IsemirDegistir());

        _img=(ImageView)getView().findViewById(R.id.imageView5);
        _img.playSoundEffect(0);
        _img.setOnClickListener(new fn_Detay());

        fn_AracBilgileri();
    }

    private void fn_AracBilgileri() {

        String _Aciklama = "ARAÇ İŞ EMRİ DETAYI\n\n";

        _Aciklama="";
        _Aciklama += "ARAÇ PLAKASI : " + aktif_sevk_isemri.arac_plaka +"\n\n";
        if (aktif_sevk_isemri.alici_isletme.equals(""))
        {
            _Aciklama += "ALICI : " + aktif_sevk_isemri.alici + "\n\n";
            _Aciklama += "BOOKING NO : " + aktif_sevk_isemri.bookingno+ "\n\n";
        }
        else
        {
            _Aciklama += "ALICI : " + aktif_sevk_isemri.alici_isletme + "\n\n";
        }

        _Aciklama += "ÜRÜN ADI : " +  aktif_sevk_isemri.urun_adi+ "\n\n";

        _txtAciklama.setText(_Aciklama);

        String _Temp="("+aktif_sevk_isemri.depo_adi+")DEPO";

        _txtdepoadi.setText(_Temp);
    }

    private void fn_AyarlariYukle()
    {
        ((GirisSayfasi)getActivity()).fn_ModRFID();

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
            frg_arac_aktivasyon fragmentyeni = new frg_arac_aktivasyon();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_arac_aktivasyon").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    public void fn_senddata(Sevkiyat_isemri v_aktif_sevk_isemri)
    {
        this.aktif_sevk_isemri=v_aktif_sevk_isemri;
    }

    private class fn_Ileri implements View.OnClickListener {
        @Override
        public void onClick(View view)
        {
            frg_arac_onayla fragmentyeni = new frg_arac_onayla();
            fragmentyeni.fn_senddata(aktif_sevk_isemri);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_arac_onayla").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_IsemirDegistir implements View.OnClickListener {
        @Override
        public void onClick(View view)
        {
            if (!aktif_sevk_isemri.yapilan_adet.equals("0"))
            {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.SUCCESS_TYPE)
                        .setTitleText("İŞ DEĞİŞİMİ YAPILAMAZ")
                        .setContentText("ARAÇ ÜZERİNDE YÜKLENEN ÜRÜN BULUNMAKTADIR (Sayı = " + aktif_sevk_isemri.yapilan_adet + "). BU DURUMDA İŞ DEĞİŞİMİ YAPILAMAZ.ÜRÜNLERİ BOŞALTTIKTAN SONRA TEKRAR DENEYİNİZ.")
                        .setConfirmText("Tamam")
                        .showCancelButton(false)
                        .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialogG sDialog)
                            {
                                sDialog.dismissWithAnimation();

                                sDialog.hide();
                            }
                        })
                        .show();
            }
            else
            {
                frg_isemri_degistir fragmentyeni = new frg_isemri_degistir();
                fragmentyeni.fn_senddata(aktif_sevk_isemri);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_isemri_degistir").addToBackStack(null);
                fragmentTransaction.commit();
            }
        }
    }

    private class fn_Detay implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_arac_secim_bilgi fragmentyeni = new frg_arac_secim_bilgi();
            fragmentyeni.fn_senddata(aktif_sevk_isemri);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_arac_secim_bilgi").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}
