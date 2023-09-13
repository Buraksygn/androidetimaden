package com.etimaden.SevkiyatIslemleri.Zayiat_islemleri;

import static com.etimaden.cSabitDegerler._ipAdresi3G;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;

import android.os.Bundle;
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

import com.etimaden.cIslem.VeriTabani;
import com.etimaden.persosclass.Arac;
import com.etimaden.genel.Genel;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.Zayi;
import com.etimaden.ugr_demo.R;

public class frg_zayi_arac_bulundu_indirme extends Fragment {

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


    Button _btngeri;
    Button _btnOnay;
    Button _btnIsEmriDegistir;
    TextView _txtDetaylar;

    Zayi aktif_zayi = null;
    Arac arac = null;

    public frg_zayi_arac_bulundu_indirme() {
        // Required empty public constructor
    }

    public static frg_zayi_arac_bulundu_indirme newInstance()
    {
        return new frg_zayi_arac_bulundu_indirme();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_aktif_silobas_arac_bulundu, container, false);
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
            _OnlineUrl = "http://"+_ayarsunucuip+":"+_zportWifi+"/";
        }
        else
        {
            _OnlineUrl = "http:/"+_ipAdresi3G+":"+_zport3G+"/";
        }
        persos = new Persos(_OnlineUrl);
    }

    public void fn_senddata(Zayi aktif_zayi, Arac arac)
    {
        this.aktif_zayi = aktif_zayi;
        this.arac = arac;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        new VeriTabani(getContext()).fn_EpcTemizle();

        _myIslem = new VeriTabani(getContext());


        _txtDetaylar=(TextView)getView().findViewById(R.id.txtDetaylar);


        _btnIsEmriDegistir = (Button)getView().findViewById(R.id.btnIsEmriDegistir);
        _btnIsEmriDegistir.playSoundEffect(0);
        _btnIsEmriDegistir.setOnClickListener(new fn_btnIsEmriDegistir());

        _btnOnay = (Button)getView().findViewById(R.id.btnOnay);
        _btnOnay.playSoundEffect(0);
        _btnOnay.setOnClickListener(new fn_btnOnay());

        _btngeri = (Button)getView().findViewById(R.id.btngeri);
        _btngeri.playSoundEffect(0);
        _btngeri.setOnClickListener(new fn_Geri());

        fn_AyarlariYukle();

        detayGoster();
    }

    private void detayGoster()
    {
        try
        {
            String str = "ARAÇ İŞ EMRİ DETAYI";
            str += "\r\nARAÇ PLAKASI : " + aktif_zayi.zay_eski_plaka ;
            str += "\r\nSAP KODU : " + aktif_zayi.zay_sap_kodu;
            str += "\r\nÜRÜN ADI : " + aktif_zayi.zay_urun_adi;


            setDetayEkranı(str);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void setDetayEkranı(String str)
    {
        try
        {
            _txtDetaylar.setText(str);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private class fn_btnIsEmriDegistir implements View.OnClickListener {
        @Override
        public void onClick(View view) {

        }
    }

    private class fn_btnOnay implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Genel.playButtonClikSound(getContext());
            try
            {
                frg_zayi_arac_onayla_indirme fragmentyeni = new frg_zayi_arac_onayla_indirme();
                fragmentyeni.fn_senddata(aktif_zayi,arac);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_zayi_arac_onayla_indirme").addToBackStack(null);
                fragmentTransaction.commit();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }

    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_zayi_aktivasyon fragmentyeni = new frg_zayi_aktivasyon();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_zayi_aktivasyon").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

}
