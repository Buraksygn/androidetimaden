package com.etimaden.SevkiyatIslemleri;

import android.os.Bundle;
import android.os.StrictMode;
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
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.etimaden.GirisSayfasi;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cResponseResult.Sevkiyat_isemri;
import com.etimaden.cResponseResult.Urun_tag;
import com.etimaden.service.IslemResponse.cIslemSonuc_01;
import com.etimaden.service.IslemResponse.cIslemSonuc_02;
import com.etimaden.service.frg_aktif_isemri_indirme.service_ekleSevkiyatUrun_cikarma;
import com.etimaden.service.frg_aktif_isemri_indirme.service_secYuklenenUrunListesi;
import com.etimaden.service.frg_aktif_isemri_indirme.service_secYuklenenUrunListesi_nakil;
import com.etimaden.service.request.requestekleSevkiyatUrun_cikarma;
import com.etimaden.service.request.requestsecYuklenenUrunListesi;
import com.etimaden.service.request.requestsecYuklenenUrunListesi_nakil;
import com.etimaden.service.response.ViewekleSevkiyatUrun_cikarma;
import com.etimaden.service.response.ViewsecYuklenenUrunListesi;
import com.etimaden.service.response.ViewsecYuklenenUrunListesi_nakil;
import com.etimaden.service.response.responseUrun_tag;
import com.etimaden.ugr_demo.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.etimaden.cSabitDegerler._zkullaniciadi;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;

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

    List<responseUrun_tag> urun_listesi_indirilen = new ArrayList<>();
    List<responseUrun_tag> urun_listesi_yuklenen = new ArrayList<>();


    ArrayList<HashMap<String, String>> _UrunListe;


    private Integer imageid[] = {
            R.drawable.redpoint,
            R.drawable.greenpoint};



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
        fn_Basla();

    }

    public void fn_BarkodOkutuldu(String barcode)
    {
        ekleSevkiyatUrun_cikarma(barcode);

        fn_Basla();
    }

    private void fn_Basla()
    {
        _myIslem.fn_IndirmeListeTemize();

        cIslemSonuc_02 _Temp02 = secYuklenenUrunListesi_nakil(aktif_sevk_isemri);

        if(_Temp02._zSonuc.equals("1"))
        {
            urun_listesi_yuklenen = _Temp02.zDizi;

            if (urun_listesi_yuklenen == null)
            {
                urun_listesi_yuklenen = new ArrayList<>();
            }
            else
            {

                for(int intSayac=0;intSayac<urun_listesi_yuklenen.size();intSayac++)
                {
                    responseUrun_tag _Gecici = urun_listesi_yuklenen.get((intSayac));

                    _myIslem.fn_ListeKayit(_Gecici.get_palet_kod(),_Gecici.get_lotno(),_Gecici.get_palet_agirligi(),"0");
                }
            }
        }

        cIslemSonuc_01 _Temp01 = secYuklenenUrunListesi(aktif_sevk_isemri);



        try
        {

            if(_Temp01._zSonuc.equals("1"))
            {
                urun_listesi_indirilen =_Temp01.zDizi;
            }

            if (urun_listesi_indirilen == null)
            {
                urun_listesi_indirilen = new ArrayList<>();
            }
            else
            {
                for(int intSayac=0;intSayac<urun_listesi_indirilen.size();intSayac++)
                {
                    responseUrun_tag _Gecici = urun_listesi_indirilen.get((intSayac));

                    _myIslem.fn_ListeIndirildi(_Gecici.get_palet_kod(),_Gecici.get_lotno(),_Gecici.get_palet_agirligi(),"1");
                }

            }

            fn_ListeDoldur();


        }catch (Exception ex_001)
        {
            SweetAlertDialog pHataDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
            pHataDialog.setTitleText("HATA 14");
            pHataDialog.setContentText(ex_001.toString());
            //pDialog.setContentText(_TempEpc);
            //pHataDialog.setCancelable(false);
            pHataDialog.show();
        }



    }

    private void fn_ListeDoldur() {
        try
        {
            //int xUrunListesi_count = urun_listesi_yuklenen.size();
            //String agirlik = "0";
           // int miktar = 0;

            _UrunListe = _myIslem.fn_IndirmeListe();

           // int count =_UrunListe.size();

            adapter = new SimpleAdapter(getContext(), _UrunListe,
                    R.layout.liste_urun_indirme,
                    new String[]{"durum", "depo_sira","paletkod","lotno"},
                    // new String[]{"kod_sap","count","dolu_konteyner_sayisi","dolu_konteyner_toplam_miktar","bos_konteyner_sayisi"},
                    //new int[]{R.id.yazi_kod_resim,R.id.yazi_kod_sira, R.id.yazi_palet_id,R.id.yazi_lot_no});
                    new int[]{  R.id.yazi_kod_durum, R.id.yazi_kod_sira, R.id.yazi_palet_id,R.id.yazi_lot_no}){

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View row=convertView;
                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    if(convertView==null)
                        row = inflater.inflate(R.layout.liste_urun_indirme, null, true);

                    // get filled view from SimpleAdapter
                    View itemView=super.getView(position, convertView, parent);

                    TextView _TExt = (TextView)row.findViewById(R.id.yazi_kod_durum);

                    String _Yazi = _TExt.getText().toString().trim();

                    if(_Yazi.equals("")==false && _Yazi.equals(" ") == false)
                    {
                        int _Bak =Integer.parseInt(_TExt.getText().toString());

                         ImageView imageFlag = (ImageView) row.findViewById(R.id.imageViewFlag);

                        imageFlag.setImageResource(imageid[_Bak]);
                        //imageFlag.setImageResource( R.drawable.redpoint);

                    }





                    return itemView;
                }

            };
            _Liste.setAdapter(adapter);

            //urun_listesi_yuklenen.stream().

           // List<Urun_tag> ul_bekleyen = urun_listesi_yuklenen.Where(w => !urun_listesi_indirilen.Any(a => a.palet_kod.Equals(w.palet_kod))).ToList();
            //List<Urun_tag> ul_bekleyen = urun_listesi_yuklenen.stream().filter(w->w.get_palet_kod().equals())

        }catch (Exception ex_002 )
        {
            new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("HATA")
                    .setContentText(ex_002.toString())
                    .setConfirmText("Tamam")
                    .showCancelButton(false)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog)
                        {
                            sDialog.dismissWithAnimation();

                            sDialog.hide();
                        }
                    })
                    .show();
        }
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




    public cIslemSonuc_01 secYuklenenUrunListesi(Sevkiyat_isemri sevk_ie)
    {
        List<Urun_tag> _Yanit = new ArrayList<>();

        pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE);
        pDialog.setTitleText("YÜKLENİYOR{secYuklenenUrunListesi}");
        pDialog.setContentText("Kontrol ediliyor Lütfen bekleyiniz.");
       // pDialog.setContentText(_TempEpc);
        pDialog.setCancelable(false);
        pDialog.show();
        pDialog.findViewById(R.id.confirm_button).setVisibility(View.GONE);

        try
        {
            StrictMode.ThreadPolicy gfgPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(gfgPolicy);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(_OnlineUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            service_secYuklenenUrunListesi _Service = retrofit.create(service_secYuklenenUrunListesi.class);

            try
            {

                requestsecYuklenenUrunListesi _Param = new requestsecYuklenenUrunListesi();
                _Param._zsunucu_ip_adresi = _ayarsunucuip;
                _Param._zaktif_alt_tesis = _ayaraktifalttesis;
                _Param._zaktif_tesis = _ayaraktiftesis;
                _Param._zkullaniciadi = _zkullaniciadi;
                _Param._zsifre = _zsifre;
                _Param.aktif_sunucu = _ayaraktifsunucu;
                _Param.aktif_kullanici = _ayaraktifkullanici;
                _Param.islem_id = sevk_ie.islem_id;

                Call<ViewsecYuklenenUrunListesi> callSync = _Service.fn_secYuklenenUrunListesi(_Param);

                Response<ViewsecYuklenenUrunListesi> response = callSync.execute();

                ViewsecYuklenenUrunListesi _Gelen = response.body();

                if (_Gelen.get_zSonuc().equals("0"))
                {
                    pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    pDialog.setTitle("HATA 09");
                    pDialog.setContentText(_Gelen.get_zHataAciklama());

                    cIslemSonuc_01 _Return =new cIslemSonuc_01();
                    _Return._zSonuc="0";
                    _Return.zDizi=null;

                    return _Return;
                }
                else
                {
                    try
                    {
                        pDialog.hide();
                    }catch (Exception ex)
                    {

                    }

                    cIslemSonuc_01 _Return =new cIslemSonuc_01();
                    _Return._zSonuc="1";
                    _Return.zDizi = _Gelen.get_zDizi();

                    return  _Return;
                }

            }catch (Exception ex_03 )
            {
                try
                {
                    pDialog.hide();

                }catch (Exception ex_01)
                {

                }

                SweetAlertDialog pHataDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                pHataDialog.setTitleText("HATA 112");
                pHataDialog.setContentText(ex_03.toString());
                //pDialog.setContentText(_TempEpc);
                //pHataDialog.setCancelable(false);
                pHataDialog.show();

                cIslemSonuc_01 _Return01=new cIslemSonuc_01();

                _Return01._zSonuc = "0";

                _Return01.zDizi=null;

                return  _Return01;
            }

        }catch (Exception ex_02)
        {
            SweetAlertDialog pHataDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
            pHataDialog.setTitleText("HATA 01");
            pHataDialog.setContentText(ex_02.toString());
            //pDialog.setContentText(_TempEpc);
            //pHataDialog.setCancelable(false);
            pHataDialog.show();

            cIslemSonuc_01 _Return01=new cIslemSonuc_01();

            _Return01._zSonuc = "0";
            _Return01.zDizi=null;

            return  _Return01;
        }
    }

    public cIslemSonuc_02 secYuklenenUrunListesi_nakil(Sevkiyat_isemri sevk_ie)
    {
        cIslemSonuc_02 _Return_02=new cIslemSonuc_02();

        try {
            pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE);
            pDialog.setTitleText("YÜKLENİYOR{secYuklenenUrunListesi_nakil}");
            pDialog.setContentText("Kontrol ediliyor Lütfen bekleyiniz.");
            //pDialog.setContentText(_TempEpc);
            pDialog.setCancelable(false);
            pDialog.show();
            pDialog.findViewById(R.id.confirm_button).setVisibility(View.GONE);

            StrictMode.ThreadPolicy gfgPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(gfgPolicy);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(_OnlineUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            service_secYuklenenUrunListesi_nakil _Service = retrofit.create(service_secYuklenenUrunListesi_nakil.class);

            requestsecYuklenenUrunListesi_nakil _Param = new requestsecYuklenenUrunListesi_nakil();
            _Param._zsunucu_ip_adresi = _ayarsunucuip;
            _Param._zaktif_alt_tesis = _ayaraktifalttesis;
            _Param._zaktif_tesis = _ayaraktiftesis;
            _Param._zkullaniciadi = _zkullaniciadi;
            _Param._zsifre = _zsifre;
            _Param.aktif_sunucu = _ayaraktifsunucu;
            _Param.aktif_kullanici = _ayaraktifkullanici;
            _Param.alt_rota = sevk_ie.alt_rota;

            Call<ViewsecYuklenenUrunListesi_nakil> callSync = _Service.fn_secYuklenenUrunListesi_nakil(_Param);

            Response<ViewsecYuklenenUrunListesi_nakil> response = callSync.execute();

            ViewsecYuklenenUrunListesi_nakil _Yanit = response.body();

            if (_Yanit.get_zSonuc().equals("0"))
            {
                pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                pDialog.setTitle("HATA 09");
                pDialog.setContentText(_Yanit.get_zHataAciklama());
                pDialog.setCancelable(false);
                pDialog.findViewById(R.id.confirm_button).setVisibility(View.VISIBLE);

                _Return_02=new cIslemSonuc_02();
                _Return_02._zSonuc="0";
                _Return_02.zDizi=null;

                return _Return_02;
            }
            else
            {
                try
                {
                    pDialog.hide();
                }catch (Exception ex)
                {

                }

                _Return_02=new cIslemSonuc_02();
                _Return_02._zSonuc="1";
                _Return_02.zDizi=_Yanit.get_zDizi();

                return _Return_02;
            }


        }catch (Exception ex_002 )
        {

            new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("HATA")
                    .setContentText(ex_002.toString())
                    .setConfirmText("Tamam")
                    .showCancelButton(false)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog)
                        {
                            sDialog.dismissWithAnimation();

                            sDialog.hide();
                        }
                    })
                    .show();

            cIslemSonuc_02 _Return_03=new cIslemSonuc_02();
            _Return_03._zSonuc="0";
            _Return_03.zDizi=null;

            return  _Return_02;

        }
    }

    public ViewekleSevkiyatUrun_cikarma ekleSevkiyatUrun_cikarma(String _Barkod)
    {
        try
        {

            StrictMode.ThreadPolicy gfgPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(gfgPolicy);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(_OnlineUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            service_ekleSevkiyatUrun_cikarma _Service = retrofit.create(service_ekleSevkiyatUrun_cikarma.class);

            requestekleSevkiyatUrun_cikarma _Param = new requestekleSevkiyatUrun_cikarma();
            _Param._zsunucu_ip_adresi = _ayarsunucuip;
            _Param._zaktif_alt_tesis = _ayaraktifalttesis;
            _Param._zaktif_tesis = _ayaraktiftesis;
            _Param._zkullaniciadi = _zkullaniciadi;
            _Param._zsifre = _zsifre;
            _Param.aktif_sunucu = _ayaraktifsunucu;
            _Param.aktif_kullanici = _ayaraktifkullanici;
           _Param.hedef_depo=aktif_sevk_isemri.hedef_depo;
           _Param.isemri_detay_id=aktif_sevk_isemri.isemri_detay_id;
           _Param.islem_id=aktif_sevk_isemri.islem_id;
           _Param.kod_sap=aktif_sevk_isemri.kod_sap;
           _Param.isemri_id=aktif_sevk_isemri.isemri_id;
           _Param.hedef_isletme_kodu=aktif_sevk_isemri.hedef_isletme_kodu;
           _Param.urun_listesi = urun_listesi_indirilen;
           _Param.tag=_Barkod;

            Call<ViewekleSevkiyatUrun_cikarma> callSync = _Service.fn_ekleSevkiyatUrun_cikarma(_Param);

            Response<ViewekleSevkiyatUrun_cikarma> response = callSync.execute();

            ViewekleSevkiyatUrun_cikarma _Yanit = response.body();

            return  _Yanit;

        }catch (Exception ex_001)
        {
            new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("HATA")
                    .setContentText(ex_001.toString())
                    .setConfirmText("Tamam")
                    .showCancelButton(false)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog)
                        {
                            sDialog.dismissWithAnimation();

                            sDialog.hide();
                        }
                    })
                    .show();

            ViewekleSevkiyatUrun_cikarma _Yanit=new ViewekleSevkiyatUrun_cikarma();
            _Yanit.set_zSonuc("0");
            return  _Yanit;
        }
    }
}
