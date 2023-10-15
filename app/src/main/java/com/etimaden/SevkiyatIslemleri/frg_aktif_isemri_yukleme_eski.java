package com.etimaden.SevkiyatIslemleri;

import static com.etimaden.cSabitDegerler._zkullaniciadi;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.etimaden.DataModel.mdlIsemriYukleme;
import com.etimaden.GirisSayfasi;
import com.etimaden.adapter.apmdlIsemriYukleme;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cResponseResult.Sevkiyat_isemri;
import com.etimaden.cResponseResult.Viewsec_sevkiyat_urun;
import com.etimaden.cResponseResult.Viewsec_sevkiyat_urun_listesi;
import com.etimaden.genel.Genel;
import com.etimaden.genel.SweetAlertDialogG;
import com.etimaden.ugr_demo.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class frg_aktif_isemri_yukleme_eski extends Fragment {

    //SweetAlertDialogG pDialog;

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
    Button _btn_03;

    String _zhareket_id="";
    String _zisletme="";
    String _zurun_kodu="";
    String _zkarakteristikler="";
    String _zpalet_agirligi="";
    String _zlotno="";
    String _zisemri_id="";
    String _zkod_sap="";

    public String _OnlineUrl = "";
    public String _BarkodOkutma = "";

    //public ListAdapter adapter;

    public ListView _Liste;

    public String _Yanitlar="";

    TextView _txtBaslik;
    TextView _txtYazi;

    ArrayList<mdlIsemriYukleme> dataModels;

    int _BitenMiktar = 0;

    int _iToplam = 0;

    public RadioButton triggerRFID;
    public RadioButton triggerScanner;

    Button _imgBilgi;

    ImageButton _btnYenile;

    private static apmdlIsemriYukleme adapter;

    public Sevkiyat_isemri _caktif_sevk_isemri;

    public static frg_aktif_isemri_yukleme_eski newInstance() {

        return new frg_aktif_isemri_yukleme_eski();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_aktif_isemri_yukleme_eski, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((GirisSayfasi) getActivity()).fn_ModBarkod();

        _myIslem = new VeriTabani(getContext());

        fn_AyarlariYukle();

        _myIslem.fn_EpcTemizle();

        ((GirisSayfasi) getActivity()).fn_ListeTemizle();
        //  ((GirisSayfasi) getActivity()).fn_GucAyarla(248);


        triggerRFID = (RadioButton) getView().findViewById(R.id.radio_trigger_rfid);
        triggerRFID.setOnClickListener(new fn_triggerRFID());

        triggerScanner = (RadioButton) getView().findViewById(R.id.radio_trigger_scanner);
        triggerScanner.setOnClickListener(new fn_triggerScanner());


        _btn_03 = (Button) getView().findViewById(R.id.btn_03);
        _btn_03.playSoundEffect(0);
        _btn_03.setOnClickListener(new fn_btn_03());

        _btnYenile = (ImageButton)getView().findViewById(R.id.btnyenile);
        _btnYenile.playSoundEffect(0);
        _btnYenile.setOnClickListener(new fn_Yenile());

        // _btngeri = (Button) getView().findViewById(R.id.btngeri);
        // _btngeri.playSoundEffect(0);
        // _btngeri.setOnClickListener(new fn_Geri());

        _txtBaslik = (TextView) getView().findViewById(R.id.txtYazi6);

        _txtYazi = (TextView) getView().findViewById(R.id.textView20);
        _txtYazi.setText("0");

      // _Cikar=(TextView)getView().findViewById(R.id.yazi_cikar);
        _imgBilgi = (Button)getView().findViewById(R.id.imgBilgi);
        _imgBilgi.playSoundEffect(0);
        _imgBilgi.setOnClickListener(new fn_Bilgilendirme());

        _Liste = (ListView) getView().findViewById(R.id.isemri_list);

        fn_BildirimYaz();

        fn_BekleyenleriGetir();




        //_Cikar = (TextView) getView().findViewById(android.R.id.yazi_cikar);
        //_Cikar.setOnClickListener(new fn_BeniCikar());

    }





    private void fn_BekleyenleriGetir() {


        ((GirisSayfasi) getActivity()).fn_ListeTemizle();


        JSONObject parametre = new JSONObject();
        try
        {
            parametre.put("_zsunucu_ip_adresi", _ayarsunucuip);
            parametre.put("_zaktif_alt_tesis", _ayaraktifalttesis);
            parametre.put("_zaktif_tesis", _ayaraktiftesis);
            parametre.put("_zhareket_id", _zhareket_id);
            parametre.put("_zkullaniciadi", _zkullaniciadi);
            parametre.put("_zsifre", _zsifre);
            parametre.put("aktif_sunucu", _ayaraktifsunucu);
            parametre.put("aktif_kullanici", _ayaraktifkullanici);
        }
        catch (JSONException error)
        {
            error.printStackTrace();
        }

        RequestQueue mRequestQueue;

        Cache cache = new DiskBasedCache(getContext().getCacheDir(), 2*1024 * 1024); // 1MB cap

        Network network = new BasicNetwork(new HurlStack());

        RequestQueue queue = new RequestQueue(cache, network);
        queue.start();

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                _OnlineUrl,
                parametre,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            ObjectMapper objectMapper = new ObjectMapper();

                            Viewsec_sevkiyat_urun_listesi _Yanit = objectMapper.readValue(response.toString(), Viewsec_sevkiyat_urun_listesi.class);

                            if(_Yanit.equals("0"))
                            {
                                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                                        .setTitleText("HATA")
                                        .setContentText(_Yanit._zHataAciklama)
                                        .show();

                            }

                            else
                            {
                                try {

                                    int _Boyut = _Yanit._zDiziaktif_sevk_isemri.size();

                                    dataModels= new ArrayList<>();

                                    _iToplam = 0;

                                    for(int iSayac = 0;iSayac<_Boyut;iSayac++)
                                    {

                                        _iToplam += Integer.parseInt(_Yanit._zDiziaktif_sevk_isemri.get(iSayac).palet_agirligi.toString());

                                        dataModels.add(new mdlIsemriYukleme(
                                                //iSayac + 1,
                                                _Boyut-iSayac ,
                                                "",
                                                _Yanit._zDiziaktif_sevk_isemri.get(iSayac).kod.toString(),
                                                _Yanit._zDiziaktif_sevk_isemri.get(iSayac).lotno.toString()
                                        ));
                                    }

                                    adapter= new apmdlIsemriYukleme(dataModels,getContext());

                                    _Liste.setAdapter(adapter);

                                    _txtYazi.setText("YÜKLENEN MİKTAR = "+_iToplam);

                                    _caktif_sevk_isemri.yapilan_miktar = _iToplam+"";
                                    _caktif_sevk_isemri.yapilan_adet = _Boyut+"";



/*
                                    JSONArray _Array = response.getJSONArray("_zDiziaktif_sevk_isemri");

                                    ArrayList<HashMap<String, String>> _IsEmirListesi = new ArrayList<>();

                                    _myIslem.fn_IsEmirleriTemizle();

                                    for (int i = 0; i < _Array.length(); i++)
                                    {
                                        JSONObject actor = _Array.getJSONObject(i);

                                        String v_urun_kod = actor.getString("urun_kod").trim();
                                        String v_palet_agirligi = actor.getString("palet_agirligi").trim();
                                        String v_palet_dizim = actor.getString("palet_dizim").trim();
                                        String v_torba_agirlik = actor.getString("torba_agirlik").trim();
                                        String v_karakteristikler = actor.getString("karakteristikler").trim();
                                        String v_lotno = actor.getString("lotno").trim();
                                        String v_urun_adi = actor.getString("urun_adi").trim();
                                        String v_rfid = actor.getString("rfid").trim();
                                        String v_kod = actor.getString("kod").trim();
                                        String v_palet_rfid = actor.getString("palet_rfid").trim();
                                        String v_palet_kod = actor.getString("palet_kod").trim();
                                        String v_islem_durumu = actor.getString("islem_durumu").trim();
                                        String v_etiket_turu = actor.getString("etiket_turu").trim();
                                        String v_isletme = actor.getString("isletme").trim();
                                        String v_isletme_esleme = actor.getString("isletme_esleme").trim();
                                        String v_isletme_adi = actor.getString("isletme_adi").trim();
                                        String v_kilitli = actor.getString("kilitli").trim();

                                        _myIslem.fn_yuklenenetiketYukle(
                                                v_urun_kod,
                                                v_palet_agirligi,
                                                v_palet_dizim,
                                                v_torba_agirlik,
                                                v_karakteristikler,
                                                v_lotno,
                                                v_urun_adi,
                                                v_rfid,
                                                v_kod,
                                                v_palet_rfid,
                                                v_palet_kod,
                                                v_islem_durumu,
                                                v_etiket_turu,
                                                v_isletme,
                                                v_isletme_esleme,
                                                v_isletme_adi,
                                                v_kilitli);
                                    }

                                    fn_Listele();*/
                                }catch (Exception ex)
                                {
                                    Genel.printStackTrace(ex,getContext());
                                    //Toast.makeText(getContext(), ex.toString(), Toast.LENGTH_SHORT).show();

                                }
                            }



                            //Toast.makeText(getApplicationContext(), "_zSayfaAdiAciklama =" + _zHataAciklamasi, Toast.LENGTH_SHORT).show();

                        } catch (JsonMappingException e) {
                            e.printStackTrace();
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //Toast.makeText(getContext(), "error =" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }


        );
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);

    }





    private void fn_BildirimYaz() {

        // JSONObject jsonObj = new JSONObject(aktif_sevk_isemri);

        //String _Aciklama="ARAÇ : " + jsonObj.getString("arac_plaka") + " - SAP KODU : " +jsonObj.getString("kod_sap");
        String _Aciklama = "ARAÇ : " + _caktif_sevk_isemri.arac_plaka + " - SAP KODU : " + _caktif_sevk_isemri.kod_sap;

        _zhareket_id = _caktif_sevk_isemri.islem_id.trim();
        _zisletme = _caktif_sevk_isemri.isletme.trim();
        _zurun_kodu = _caktif_sevk_isemri.urun_kodu.trim();
        _zkarakteristikler = _caktif_sevk_isemri.karakteristikler.trim();
        _zpalet_agirligi = _caktif_sevk_isemri.palet_agirligi.trim();
        _zlotno = _caktif_sevk_isemri.lotno.trim();
        _zisemri_id = _caktif_sevk_isemri.isemri_id.trim();
        _zkod_sap = _caktif_sevk_isemri.kod_sap.trim();

        if (_caktif_sevk_isemri.alici_isletme.equals("")) {
            _Aciklama += "  ALICI : " + _caktif_sevk_isemri.alici + "\n";
            _Aciklama += "BOOKING NO : " + _caktif_sevk_isemri.bookingno + "\n";
        } else
            _Aciklama += "  ALICI : " + _caktif_sevk_isemri.alici_isletme + "\n";

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
            _OnlineUrl = "http://"+_ayarsunucuip+":"+_zportWifi+"/api/sec_sevkiyat_urun_listesi";
            _BarkodOkutma = "http://"+_ayarsunucuip+":"+_zportWifi+"/api/sec_sevkiyat_urun";
        }
        else
        {
            _OnlineUrl = "http://88.255.50.73:"+_zport3G+"/api/sec_sevkiyat_urun_listesi";
            _BarkodOkutma = "http://88.255.50.73:"+_zport3G+"/api/sec_sevkiyat_urun";
        }
    }

    public void fn_senddata(Sevkiyat_isemri  v_aktif_sevk_isemri)
    {
        _caktif_sevk_isemri = v_aktif_sevk_isemri;
    }

    public void fn_BarkodOkutuldu(String barcode)
    {
        JSONObject parametre = new JSONObject();

        try
        {
            parametre.put("_zkullaniciadi", _zkullaniciadi);
            parametre.put("_zsifre", _zsifre);
            parametre.put("_zrfid", barcode);
            parametre.put("_zhareket_id", _zhareket_id);
            parametre.put("_zisletme", _zisletme);
            parametre.put("_zurun_kodu", _zurun_kodu);
            parametre.put("_zkarakteristikler", _zkarakteristikler);
            parametre.put("_zpalet_agirligi", _zpalet_agirligi);
            parametre.put("_zlotno", _zlotno);
            parametre.put("_zisemri_id", _zisemri_id);
            parametre.put("_zkod_sap", _zkod_sap);
            parametre.put("_zsunucu_ip_adresi", _ayarsunucuip);
            parametre.put("_zaktif_alt_tesis", _ayaraktifalttesis);
            parametre.put("_zaktif_tesis", _ayaraktiftesis);
            parametre.put("aktif_sunucu", _ayaraktifsunucu);
            parametre.put("aktif_kullanici", _ayaraktifkullanici);
        }
        catch (JSONException error)
        {
            error.printStackTrace();
        }

        RequestQueue mRequestQueue;

        Cache cache = new DiskBasedCache(getContext().getCacheDir(), 2*1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());

        RequestQueue queue = new RequestQueue(cache, network);
        queue.start();

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                _BarkodOkutma,
                parametre,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            ObjectMapper objectMapper = new ObjectMapper();

                            Viewsec_sevkiyat_urun _Yanit = objectMapper.readValue(response.toString(), Viewsec_sevkiyat_urun.class);

                            if(_Yanit._zSonuc.equals("0"))
                            {
                                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                                        .setTitleText("HATA")
                                        .setContentText(_Yanit._zHataAciklama)
                                        .show();
                            }
                            else
                            {
                                fn_BekleyenleriGetir();
                            }

                        } catch (JsonMappingException e) {
                            e.printStackTrace();
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //Toast.makeText(getContext(), "error =" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }


        );
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);


    }

    private class fn_Geri implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            ((GirisSayfasi) getActivity()).fn_ModRFID();

            frg_aktif_isemri_secimi fragmentyeni = new frg_aktif_isemri_secimi();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_aktif_isemri_secimi").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_btn_03 implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            ((GirisSayfasi) getActivity()).fn_ModRFID();
            ((GirisSayfasi) getActivity()).fn_GucAyarla(248);

            frg_aktif_isemri_secimi fragmentyeni = new frg_aktif_isemri_secimi();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_aktif_isemri_secimi").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }


    private class fn_triggerRFID implements View.OnClickListener {
        @Override
        public void onClick(View view)
        {
            ((GirisSayfasi) getActivity()).fn_ModRFID();

            ((GirisSayfasi) getActivity()).fn_GucAyarla(60);
        }
    }

    private class fn_triggerScanner implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            ((GirisSayfasi) getActivity()).fn_ModBarkod();
        }
    }

    public void fn_cikar(View v)
    {
        //Toast.makeText(getContext(), "Clicked on Button", Toast.LENGTH_LONG).show();
    }

    private class fn_Yenile implements View.OnClickListener {
        @Override
        public void onClick(View v)
        {
            Genel.lockButtonClick(v,getActivity());
            fn_BekleyenleriGetir();
        }
    }
    private class fn_Bilgilendirme implements View.OnClickListener {
        @Override
        public void onClick(View v)
        {
            frg_arac_yukleme_bilgi fragmentyeni = new frg_arac_yukleme_bilgi();
            fragmentyeni.fn_senddata(_caktif_sevk_isemri);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_arac_yukleme_bilgi").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}
