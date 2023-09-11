package com.etimaden.SevkiyatIslemleri;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.GirisSayfasi;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cResponseResult.Sevkiyat_isemri;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;

public class frg_aktif_isemri_indirme  extends Fragment {

    SweetAlertDialog pDialog;

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

    Button _btngeri;

    //public String _sAktif_sevk_isemri="";

    public String _OnlineUrl = "";

    public ListAdapter adapter;

    public ListView _Liste;

    public Sevkiyat_isemri aktif_sevk_isemri = null;

    TextView _txtBaslik;
    TextView _txtYazi;

    public RadioButton triggerRFID;
    public RadioButton triggerScanner;

    Button _btn_03;

    ImageView _imgBilgi;




    ArrayList<HashMap<String, String>> _UrunListe;


    private Integer imageid[] = {
            R.drawable.redpoint,
            R.drawable.greenpoint
    };



    public static frg_aktif_isemri_indirme newInstance() {

        return new frg_aktif_isemri_indirme();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_aktif_isemri_indirme, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        ((GirisSayfasi) getActivity()).fn_ModBarkod();

        _myIslem = new VeriTabani(getContext());

        fn_AyarlariYukle();

        _myIslem.fn_EpcTemizle();

        ((GirisSayfasi) getActivity()).fn_ListeTemizle();

        triggerRFID = (RadioButton) getView().findViewById(R.id.radio_trigger_rfid);
        triggerRFID.setOnClickListener(new fn_triggerRFID());

        triggerScanner = (RadioButton) getView().findViewById(R.id.radio_trigger_scanner);
        triggerScanner.setOnClickListener(new fn_triggerScanner());

        _btn_03 = (Button) getView().findViewById(R.id.btn_03);
        _btn_03.playSoundEffect(0);
        _btn_03.setOnClickListener(new fn_btn_03());

        _txtBaslik = (TextView) getView().findViewById(R.id.txtYazi6);

        _txtYazi = (TextView) getView().findViewById(R.id.textView20);

        _Liste = (ListView) getView().findViewById(R.id.indirme_list);

        fn_BildirimYaz();

        _imgBilgi = (ImageView)getView().findViewById(R.id.imgBilgi);
        _imgBilgi.playSoundEffect(0);
        _imgBilgi.setOnClickListener(new fn_BilgiVer());

       // fn_BekleyenleriGetir();


    }

    public void fn_BarkodOkutuldu(String barcode)
    {



    }





    private void fn_BildirimYaz()
    {

            String _Aciklama="";

            _Aciklama = "ARAÇ : " + aktif_sevk_isemri.arac_plaka + " - SAP KODU : " + aktif_sevk_isemri.kod_sap + System.lineSeparator();
            if (aktif_sevk_isemri.alici_isletme.equals(""))
            {
                _Aciklama += " ALICI : " + aktif_sevk_isemri.alici + System.lineSeparator();
                _Aciklama += " BOOKING NO : " + aktif_sevk_isemri.bookingno +System.lineSeparator();
            }
            else
            {
                _Aciklama += " ALICI : " + aktif_sevk_isemri.alici_isletme + System.lineSeparator();
            }

            _Aciklama += " MAL KABUL LİSTESİ";

            _txtBaslik.setText(_Aciklama);
        /*
        *  baslik.Text = "ARAÇ : " + aktif_sevk_isemri.arac_plaka + " - SAP KODU : " + aktif_sevk_isemri.kod_sap + Environment.NewLine;
            if (aktif_sevk_isemri.alici_isletme.Equals(""))
            {
                baslik.Text += "ALICI : " + aktif_sevk_isemri.alici + Environment.NewLine;
                baslik.Text += "BOOKING NO : " + aktif_sevk_isemri.bookingno + Environment.NewLine;
            }
            else
                baslik.Text += "ALICI : " + aktif_sevk_isemri.alici_isletme + Environment.NewLine;
            baslik.Text += "YÜKLEME LİSTESİ";
        * */
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
            _OnlineUrl = "http://" + _ayarsunucuip + ":" + _zportWifi + "/";

           //_OnlineUrlIndirilen = "http://"+_ayarsunucuip+":"+_zportWifi+"/api/secYuklenenUrunListesi";
           //_OnlineUrlYuklenen = "http://"+_ayarsunucuip+":"+_zportWifi+"/api/secYuklenenUrunListesi_nakil";
           //_OnlineUrlHepsi = "http://"+_ayarsunucuip+":"+_zportWifi+"/api/secYuklenenUrunListesi_Hepsi";
        }
        else
        {
            _OnlineUrl = "http://88.255.50.73:" + _zport3G + "/";
            //_OnlineUrlIndirilen = "http://88.255.50.73:"+_zport3G+"/api/secYuklenenUrunListesi";
            //_OnlineUrlYuklenen = "http://88.255.50.73:"+_zport3G+"/api/secYuklenenUrunListesi_nakil";
            //_OnlineUrlHepsi = "http://88.255.50.73:"+_zport3G+"/api/secYuklenenUrunListesi_Hepsi";
        }
    }

    public void fn_senddata(Sevkiyat_isemri  v_aktif_sevk_isemri)
    {
        aktif_sevk_isemri = v_aktif_sevk_isemri;
    }

    private class fn_triggerRFID implements View.OnClickListener {
        @Override
        public void onClick(View view)
        {
            ((GirisSayfasi) getActivity()).fn_ModRFID();

            ((GirisSayfasi) getActivity()).fn_GucAyarla(60);
        }
    }

    private class fn_triggerScanner implements View.OnClickListener
    {
        @Override
        public void onClick(View view) {
            ((GirisSayfasi) getActivity()).fn_ModBarkod();
        }
    }

    private class fn_btn_03 implements View.OnClickListener {
        @Override
        public void onClick(View view)
        {
            ((GirisSayfasi) getActivity()).fn_ModRFID();

            ((GirisSayfasi) getActivity()).fn_GucAyarla(248);

            frg_sevkiyat_menu_panel fragmentyeni = new frg_sevkiyat_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_sevkiyat_menu_panel").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_BilgiVer implements View.OnClickListener {
        @Override
        public void onClick(View view)
        {
            frg_arac_indirme_bilgi fragmentyeni = new frg_arac_indirme_bilgi();

            fragmentyeni.fn_senddata(aktif_sevk_isemri);
            //fragmentyeni.fn_sendYanitlar(_Yanitlar);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_arac_indirme_bilgi").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }









}
