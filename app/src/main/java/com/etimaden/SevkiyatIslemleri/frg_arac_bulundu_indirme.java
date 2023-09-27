package com.etimaden.SevkiyatIslemleri;

import android.os.Bundle;
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
import com.etimaden.SevkiyatIslemleri.Arac_aktivayon_islemleri.frg_arac_aktivasyon;
import com.etimaden.SevkiyatIslemleri.Arac_aktivayon_islemleri.frg_arac_onayla_indirme;
import com.etimaden.SevkiyatIslemleri.Arac_aktivayon_islemleri.frg_isemri_degistir_transfer;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cResponseResult.Sevkiyat_isemri;
import com.etimaden.genel.Genel;
import com.etimaden.genel.SweetAlertDialogG;
import com.etimaden.ugr_demo.R;

import static com.etimaden.cSabitDegerler._ipAdresi3G;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;



public class frg_arac_bulundu_indirme extends Fragment {


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
    public String _OnlineUrl = "";

    Button _btngeri;
    Button _btnileri;
    Button _btnIsemriDegistir;
    ImageView _imageView7;
    TextView _txtAciklama;

    Sevkiyat_isemri aktif_sevk_isemri=null;


    public frg_arac_bulundu_indirme()
    {

    }

    public static frg_arac_bulundu_indirme newInstance() {

        return new frg_arac_bulundu_indirme();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_arac_bulundu_indirme, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

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
            _OnlineUrl = "http://"+_ayarsunucuip+":"+ _zportWifi+"/api/AracAktivasyon_2003";
        }
        else
        {
            _OnlineUrl = "http://"+_ipAdresi3G+":"+_zport3G+"/api/AracAktivasyon_2003";
        }
    }

    public void fn_senddata(Sevkiyat_isemri v_aktif_sevk_isemri)
    {
        aktif_sevk_isemri=v_aktif_sevk_isemri;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        _myIslem = new VeriTabani(getContext());

        _imageView7 = (ImageView)getView().findViewById(R.id.imageView7);
        _imageView7.playSoundEffect(0);
        _imageView7.setOnClickListener(new fn_Detay());

        _btnileri=(Button)getView().findViewById(R.id.btnileri);
        _btnileri.playSoundEffect(0);
        _btnileri.setOnClickListener(new fn_Onay());

        _btngeri=(Button)getView().findViewById(R.id.btngeri);
        _btngeri.playSoundEffect(0);
        _btngeri.setOnClickListener(new fn_Geri());

        _btnIsemriDegistir=(Button)getView().findViewById(R.id.btnIsemriDegistir);
        _btnIsemriDegistir.playSoundEffect(0);
        _btnIsemriDegistir.setOnClickListener(new fn_btnIsemriDegistir());

        fn_AyarlariYukle();

        _txtAciklama = (TextView) getView().findViewById(R.id.txtaciklama);

        fn_DetayYaz();
    }

    private void fn_DetayYaz()
    {
        String _Aciklama="";
        _Aciklama += "ARAÇ İŞ EMRİ DETAYI\\n";
        _Aciklama += "ARAÇ PLAKASI : " + aktif_sevk_isemri.arac_plaka + "\r\n";

        if (aktif_sevk_isemri.alici_isletme.equals(""))
        {
            _Aciklama += "ALICI : " + aktif_sevk_isemri.alici + "\r\n";
            _Aciklama += "BOOKING NO : " + aktif_sevk_isemri.bookingno  + "\r\n";
        }
        else
        {
            _Aciklama +="ALICI : " + aktif_sevk_isemri.alici_isletme + "\r\n";
        }

        _Aciklama +="ÜRÜN ADI : " + aktif_sevk_isemri.urun_adi + "\r\n";
        _txtAciklama.setText(_Aciklama);

    }

    private class fn_btnIsemriDegistir implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            try
            {
                try
                {
                    if (!aktif_sevk_isemri.yapilan_adet.equals("0"))
                    {
                        new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                                .setTitleText("İŞ DEĞİŞİMİ YAPILAMAZ")
                                .setContentTextSize(25)
                                .setContentText("ARAÇ ÜZERİNDE YÜKLENEN ÜRÜN BULUNMAKTADIR. BU DURUMDA İŞ DEĞİŞİMİ YAPILAMAZ \r\n ÜRÜNLERİ BOŞALTTIKTAN SONRA TEKRAR DENEYİNİZ.")
                                .showCancelButton(false)
                                .show();
                        return;
                    }
                }
                catch (Exception ex)
                {
                    Genel.printStackTrace(ex,getContext());
                }

                frg_isemri_degistir_transfer fragmentyeni = new frg_isemri_degistir_transfer();
                fragmentyeni.fn_senddata(aktif_sevk_isemri);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_isemri_degistir_transfer").addToBackStack(null);
                fragmentTransaction.commit();
            }
            catch (Exception ex)
            {
                Genel.printStackTrace(ex,getContext());
            }
        }
    }

    private class fn_Detay implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_arac_bulundu_indirme_detay fragmentyeni = new frg_arac_bulundu_indirme_detay();
            fragmentyeni.fn_senddata(aktif_sevk_isemri);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_arac_bulundu_indirme_detay").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_Onay implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            try
            {
                frg_arac_onayla_indirme fragmentyeni = new frg_arac_onayla_indirme();
                fragmentyeni.fn_senddata(aktif_sevk_isemri);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_arac_onayla_indirme").addToBackStack(null);
                fragmentTransaction.commit();
            }
            catch (Exception ex)
            {
                Genel.printStackTrace(ex,getContext());
            }
        }
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
}
