package com.etimaden.SevkiyatIslemleri.Silobas_islemleri;

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

import com.etimaden.SevkiyatIslemleri.frg_sevkiyat_menu_panel;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cResponseResult.Sevkiyat_isemri;
import com.etimaden.persos.Persos;
import com.etimaden.ugr_demo.R;

public class frg_aktif_silobas_arac_bulundu extends Fragment {

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
    Button _btnIsEmriDegistir;
    TextView _txtDetaylar;

    Sevkiyat_isemri aktif_sevk_isemri = null;

    public frg_aktif_silobas_arac_bulundu() {
        // Required empty public constructor
    }

    public static frg_aktif_silobas_arac_bulundu newInstance()
    {
        return new frg_aktif_silobas_arac_bulundu();
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

    public void fn_senddata(Sevkiyat_isemri  aktif_sevk_isemri)
    {
        this.aktif_sevk_isemri = aktif_sevk_isemri;
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
            str += "\r\nARAÇ PLAKASI : " + aktif_sevk_isemri.arac_plaka ;
            str += "\r\nDEPO KODU : " + aktif_sevk_isemri.depo_kodu;
            str += "\r\nDEPO ADI : " + aktif_sevk_isemri.depo_adi;
            str += "\r\nSAP : " + aktif_sevk_isemri.kod_sap;
            str += "\r\nÜRÜN ADI : " + aktif_sevk_isemri.urun_adi;

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
            frg_aktif_silobas_isemri_degistir fragmentyeni = new frg_aktif_silobas_isemri_degistir();
            fragmentyeni.fn_senddata(aktif_sevk_isemri);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_aktif_silobas_Isemri_degistir").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_sevkiyat_menu_panel fragmentyeni = new frg_sevkiyat_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_sevkiyat_menu_panel").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

}
