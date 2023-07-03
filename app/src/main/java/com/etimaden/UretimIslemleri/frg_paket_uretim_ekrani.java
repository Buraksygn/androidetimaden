package com.etimaden.UretimIslemleri;

import android.opengl.Visibility;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.NotificationCompatSideChannelService;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
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
import com.etimaden.SevkiyatIslemleri.frg_aktif_isemri_secimi;
import com.etimaden.SevkiyatIslemleri.frg_arac_yukleme_bilgi;
import com.etimaden.adapter.apmdlIsemriYukleme;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cIslem.cTanimEnum;
import com.etimaden.cResponseResult.DEPOTag;
import com.etimaden.cResponseResult.Viewcoklu_etiket_kontrol;
import com.etimaden.cResponseResult.Viewsec_etiket_uretim;
import com.etimaden.cResponseResult.Viewsec_sevkiyat_urun_listesi;
import com.etimaden.service.response.Viewsec_ambalaj_degisim_toplam_harcanan_miktar;
import com.etimaden.service.response.uretim_etiket;
import com.etimaden.ugr_demo.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.etimaden.cSabitDegerler._zkullaniciadi;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;


public class frg_paket_uretim_ekrani extends Fragment {


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



    String _OnlineUrlsecEtiket = "";
    String _OnlineUrlsec_ambalaj_degisim_toplam_harcanan_miktar = "";
    String _OnlineUrlcoklu_etiket_kontrol = "";
   // Boolean _IslemVarMi = false;


    Button _btnEtiketsizUretim;
    Button _btnIptal;
    Button _btncikis;

    TextView _txtPaletId;
    TextView _txtParcaBir;
    TextView _txtAra;
    TextView _txtParcaIki;

   // cTanimEnum._eDurum islemDurumu = cTanimEnum._eDurum.ISLEM_YOK;

    uretim_etiket aktif_Palet = null;

    List<String> paket_listesi=new ArrayList<>();

    DEPOTag depo = null;

    DEPOTag silo = null;

    uretim_etiket aktif_urun = null;

    Boolean isReadeable = true;

    int islemDurumu = 0;
    //  0 : etiket okutma bekleniyor
    //  1 : bigbag üretim işlemi yapılıyor
    //  2 : paketli palet üretim işlemi yapılıyor
    //  3 : paket üretim işlemi yapılıyor

    Viewsec_ambalaj_degisim_toplam_harcanan_miktar _Yanit02=new Viewsec_ambalaj_degisim_toplam_harcanan_miktar();

    public static frg_paket_uretim_ekrani newInstance() {

        return new frg_paket_uretim_ekrani();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_paket_uretim_ekrani , container, false);
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

        _btnEtiketsizUretim = (Button)getView().findViewById(R.id.btnEtiketsizUretim);
        _btnEtiketsizUretim.playSoundEffect(0);
        _btnEtiketsizUretim.setOnClickListener(new fn_btnEtiketsizUretim());

        _btncikis = (Button)getView().findViewById(R.id.btncikis);
        _btncikis.playSoundEffect(0);
        _btncikis.setOnClickListener(new fn_btncikis());

        _btnIptal = (Button)getView().findViewById(R.id.btnIptal);
        _btnIptal.playSoundEffect(0);
        _btnIptal.setOnClickListener(new fn_btnIptal());

        _txtPaletId = (TextView)getView().findViewById(R.id.txtPaletId);

        _txtParcaBir = (TextView)getView().findViewById(R.id.txtParcaBir);

        _txtAra = (TextView)getView().findViewById(R.id.txtAra);

        _txtParcaIki = (TextView)getView().findViewById(R.id.txtParcaIki);

        fn_AltPanelGorunsunmu(false);

        depo=new DEPOTag();
        depo.depo_id="-1";

        silo=new DEPOTag();
        silo.depo_id="-1";
    }

    private void fn_AltPanelGorunsunmu(boolean _bGoster) {
        if (_bGoster == true)
        {
            _btnEtiketsizUretim.setVisibility(View.VISIBLE);

            _btnIptal.setVisibility(View.VISIBLE);

            _txtPaletId.setVisibility(View.VISIBLE);
            _txtParcaBir.setVisibility(View.VISIBLE);
            _txtAra.setVisibility(View.VISIBLE);
            _txtParcaIki.setVisibility(View.VISIBLE);
        }
        else
            {
            _btnEtiketsizUretim.setVisibility(View.INVISIBLE);

            _btnIptal.setVisibility(View.INVISIBLE);

            _txtPaletId.setVisibility(View.INVISIBLE);
            _txtParcaBir.setVisibility(View.INVISIBLE);
            _txtAra.setVisibility(View.INVISIBLE);
            _txtParcaIki.setVisibility(View.INVISIBLE);

        }
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
            _OnlineUrlsecEtiket = "http://"+_ayarsunucuip+":"+_zportWifi+"/api/sec_etiket_uretim";
            _OnlineUrlsec_ambalaj_degisim_toplam_harcanan_miktar = "http://"+_ayarsunucuip+":"+_zportWifi+"/api/sec_ambalaj_degisim_toplam_harcanan_miktar";
            _OnlineUrlcoklu_etiket_kontrol = "http://"+_ayarsunucuip+":"+_zportWifi+"/api/coklu_etiket_kontrol";
        }
        else
        {
            _OnlineUrlsecEtiket = "http://88.255.50.73:"+_zport3G+"/api/sec_etiket_uretim";
            _OnlineUrlsec_ambalaj_degisim_toplam_harcanan_miktar = "http://88.255.50.73:"+_zport3G+"/api/sec_ambalaj_degisim_toplam_harcanan_miktar";
            _OnlineUrlcoklu_etiket_kontrol = "http://88.255.50.73:"+_zport3G+"/api/coklu_etiket_kontrol";
        }
    }

    private void paketli_palet_basla(uretim_etiket etiket)
    {
        try {

            islemDurumu = 1;

            aktif_Palet = etiket;

            paket_listesi.clear();

            _txtParcaBir.setText(paket_listesi.size()+"");
            _txtParcaIki.setText(etiket.getpalet_dizim()+"");
            fn_AltPanelGorunsunmu(true);

        } catch (Exception ex) {
            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("HATA")
                    .setContentText("HTN 7613 = "+ex.toString())
                    .setContentTextSize(20)
                    .setConfirmText("TAMAM")
                    .showCancelButton(false)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
                    {
                        @Override
                        public void onClick(SweetAlertDialog sDialog)
                        {
                            sDialog.dismissWithAnimation();
                        }
                    })
                    .show();
        }
    }


    private void paketli_palet_uretim_tamamla(uretim_etiket etiket) {

        String serino_kod = etiket.getserino_kod() + "";


        if (paket_listesi.size() > 0) {

            new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE)
                    .setTitleText("ONAY")
                    .setContentText(serino_kod + " seri nolu ürünün üretim işlemini tamamlamak istiyor musunuz ?")
                    .setCancelText("Hayır")
                    .setContentTextSize(20)
                    .setConfirmText("EVET")
                    .showCancelButton(true)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();

                            JSONObject parametre = new JSONObject();

                            try {
                                parametre.put("_zsunucu_ip_adresi", _ayarsunucuip);
                                parametre.put("_zaktif_alt_tesis", _ayaraktifalttesis);
                                parametre.put("_zaktif_tesis", _ayaraktiftesis);
                                parametre.put("_zkullaniciadi", _zkullaniciadi);
                                parametre.put("_zsifre", _zsifre);
                                parametre.put("aktif_sunucu", _ayaraktifsunucu);
                                parametre.put("aktif_kullanici", _ayaraktifkullanici);

                                parametre.put("_etiket_01", paket_listesi.get(0).toString());
                                parametre.put("_etiket_02", paket_listesi.get(paket_listesi.size() - 1).toString());
                            } catch (JSONException error) {
                                error.printStackTrace();
                            }

                            RequestQueue mRequestQueue;

                            Cache cache = new DiskBasedCache(getContext().getCacheDir(), 2 * 1024 * 1024); // 2MB cap

                            Network network = new BasicNetwork(new HurlStack());

                            RequestQueue queue = new RequestQueue(cache, network);
                            queue.start();

                            JsonObjectRequest request = new JsonObjectRequest(
                                    Request.Method.POST,
                                    _OnlineUrlcoklu_etiket_kontrol,
                                    parametre,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {
                                                ObjectMapper objectMapper = new ObjectMapper();

                                                Viewcoklu_etiket_kontrol _Yanit = objectMapper.readValue(response.toString(), Viewcoklu_etiket_kontrol.class);

                                                if (_Yanit._zSonuc.equals("0")) {
                                                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                                            .setTitleText("HATA")
                                                            .setContentText(_Yanit._zHataAciklama)
                                                            .setContentTextSize(20)
                                                            .setConfirmText("TAMAM")
                                                            .showCancelButton(false)
                                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                                @Override
                                                                public void onClick(SweetAlertDialog sDialog) {
                                                                    sDialog.dismissWithAnimation();
                                                                }
                                                            })
                                                            .show();

                                                } else
                                                {

                                                    aktif_Palet.setdepo(depo.depo_id);

                                                    aktif_Palet.setsilo(silo.depo_id);

                                                    try {




                                                    } catch (Exception ex) {
                                                        Toast.makeText(getContext(), ex.toString(), Toast.LENGTH_SHORT).show();
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

                                            Toast.makeText(getContext(), "error =" + error.toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                            );
                            int socketTimeout = 30000;//30 seconds - change to what you want
                            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                            request.setRetryPolicy(policy);
                            queue.add(request);
                        }
                    })
                    .show();
        }


    }


    private void manipulasyon_paketli_uret(final uretim_etiket etiket)
    {
        final String palet_miktar =  etiket.getpalet_miktar().toString();
        final String serino_kod =  etiket.getserino_kod().toString();
        final String ser_lotno =  etiket.getser_lotno().toString();
        final String urun_kodu =  etiket.geturun_kodu().toString();


        JSONObject parametre = new JSONObject();



        try
        {
            parametre.put("_zsunucu_ip_adresi", _ayarsunucuip);
            parametre.put("_zaktif_alt_tesis", _ayaraktifalttesis);
            parametre.put("_zaktif_tesis", _ayaraktiftesis);
            parametre.put("_zkullaniciadi", _zkullaniciadi);
            parametre.put("_zsifre", _zsifre);
            parametre.put("aktif_sunucu", _ayaraktifsunucu);
            parametre.put("aktif_kullanici", _ayaraktifkullanici);

            parametre.put("sap_kodu", etiket.getsap_kodu().toString());
        }
        catch (JSONException error)
        {
            error.printStackTrace();
        }

        RequestQueue mRequestQueue;

        Cache cache = new DiskBasedCache(getContext().getCacheDir(), 2*1024 * 1024); // 2MB cap

        Network network = new BasicNetwork(new HurlStack());

        RequestQueue queue = new RequestQueue(cache, network);
        queue.start();

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                _OnlineUrlsec_ambalaj_degisim_toplam_harcanan_miktar,
                parametre,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ObjectMapper objectMapper = new ObjectMapper();

                            _Yanit02 = objectMapper.readValue(response.toString(), Viewsec_ambalaj_degisim_toplam_harcanan_miktar.class);

                            if (_Yanit02._zSonuc.equals("0"))
                            {
                                new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("HATA")
                                        .setContentText("Sistemsel bir hata oluştu <br/>"+_Yanit02._zHataAciklama)
                                        .setContentTextSize(20)
                                        .setConfirmText("TAMAM")
                                        .showCancelButton(false)
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
                                        {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog)
                                            {
                                                sDialog.dismissWithAnimation();
                                            }
                                        })
                                        .show();
                            }
                            else
                            {
                                try
                                {
                                    String miktar = _Yanit02._miktar;

                                    if (miktar.equals("-1") ==true)
                                    {
                                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                                .setTitleText("YETERSİZ ÜRÜN MİKTARI")
                                                .setContentText("BU İŞ EMRİNE BAĞLI ÜRETİM YAPMAK İÇİN YETERLİ ÜRÜN HARCAMA İŞLEMİ YAPILMAMIŞ. YETERLİ HARCAMA YAPTIKTAN SONRA İLGİLİ İŞ EMRİNE DEVAM EDEBİLİRSİNİZ.")
                                                .setContentTextSize(20)
                                                .setConfirmText("TAMAM")
                                                .showCancelButton(false)
                                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
                                                {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sDialog)
                                                    {
                                                        sDialog.dismissWithAnimation();
                                                    }
                                                })
                                                .show();
                                    }
                                    else
                                    {
                                        try
                                        {
                                            int miktar_int  = Integer.parseInt(miktar);
                                            int eklenecek_miktar = Integer.parseInt(palet_miktar);
                                            miktar_int = miktar_int - eklenecek_miktar;



                                            if (miktar_int >= 0)
                                            {

                                                String _Yazi="";

                                                _Yazi+="SERİNO : "+serino_kod;
                                                _Yazi+="<br/>";
                                                _Yazi+="LOTNO : "+ser_lotno;
                                                _Yazi+="<br/>";
                                                _Yazi+="ÜRÜN : "+urun_kodu;
                                                _Yazi+="<br/>";
                                                _Yazi+="KULLANILABİLİR MİKTAR : "+miktar+" KG";
                                                _Yazi+="<br/>";
                                                _Yazi+="PAKET TİPİ DEĞİŞTİRME İŞLEMİNE DEVAM ETMEK İSTİYOR MUSUNUZ ?";

                                                new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE)
                                                        .setTitleText("PAKET TİPİ DEĞİŞTİR")
                                                        .setContentText(_Yazi)
                                                        .setCancelText("İptal")
                                                        .showCancelButton(true)
                                                        .setContentTextSize(20)
                                                        .setConfirmText("TAMAM")
                                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
                                                        {
                                                            @Override
                                                            public void onClick(SweetAlertDialog sDialog)
                                                            {
                                                                if (islemDurumu == 0)
                                                                {
                                                                    paketli_palet_basla(etiket);
                                                                }

                                                                else
                                                                {
                                                                    if (islemDurumu == 2)
                                                                    {
                                                                        paketli_palet_uretim_tamamla(etiket);
                                                                    }
                                                                }
                                                            }
                                                        })
                                                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                            @Override
                                                            public void onClick(SweetAlertDialog sDialog) {
                                                                sDialog.dismissWithAnimation();
                                                            }
                                                        })
                                                        .show();
                                            }
                                            else
                                            {

                                                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                                                        .setTitleText("YETERSİZ ÜRÜN MİKTARI")
                                                        .setContentText("BU İŞ EMRİNE BAĞLI ÜRETİM YAPMAK İÇİN YETERLİ ÜRÜN HARCAMA İŞLEMİ YAPILMAMIŞ..")
                                                        .setContentTextSize(20)
                                                        .setConfirmText("TAMAM")
                                                        .showCancelButton(false)
                                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
                                                        {
                                                            @Override
                                                            public void onClick(SweetAlertDialog sDialog)
                                                            {
                                                                sDialog.dismissWithAnimation();
                                                            }
                                                        })
                                                        .show();
                                            }



                                        }catch (Exception ex)
                                        {
                                            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                                    .setTitleText("HATA")
                                                    .setContentText("HTN 756"+ex.toString())
                                                    .setContentTextSize(20)
                                                    .setConfirmText("TAMAM")
                                                    .showCancelButton(false)
                                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
                                                    {
                                                        @Override
                                                        public void onClick(SweetAlertDialog sDialog)
                                                        {
                                                            sDialog.dismissWithAnimation();
                                                        }
                                                    })
                                                    .show();
                                        }

                                    }
                                }
                                catch (Exception ex)
                                {
                                    Toast.makeText(getContext(), ex.toString(), Toast.LENGTH_SHORT).show();
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

                        Toast.makeText(getContext(), "error =" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);


        //Program.persos.sec_ambalaj_degisim_toplam_harcanan_miktar(etiket);
    }




    public void fn_BarkodOkutuldu(String v_Gelen)
    {

        if(v_Gelen.length()==24)
        {
            etiket_islem_baslat(v_Gelen);
        }
        else
            {

            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("HATA")
                    .setContentText("Hatalı etiket değeri")
                    .showCancelButton(false)
                    .setContentTextSize(20)
                    .setConfirmText("TAMAM")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();

                           //_IslemVarMi = true;
                        }
                    })
                    .show();
        }
    }



    private void etiket_islem_baslat(String str) {

        try
        {

            if (islemDurumu == 0)
            {
                    //region etiket sorgula
                JSONObject parametre = new JSONObject();
                try
                {
                    parametre.put("_zsunucu_ip_adresi", _ayarsunucuip);
                    parametre.put("_zaktif_alt_tesis", _ayaraktifalttesis);
                    parametre.put("_zaktif_tesis", _ayaraktiftesis);
                    parametre.put("_zkullaniciadi", _zkullaniciadi);
                    parametre.put("_zsifre", _zsifre);
                    parametre.put("aktif_sunucu", _ayaraktifsunucu);
                    parametre.put("aktif_kullanici", _ayaraktifkullanici);

                    parametre.put("_etiket", str);
                }
                catch (JSONException error)
                {
                    error.printStackTrace();
                }

                RequestQueue mRequestQueue;

                Cache cache = new DiskBasedCache(getContext().getCacheDir(), 2*1024 * 1024); // 2MB cap

                Network network = new BasicNetwork(new HurlStack());

                RequestQueue queue = new RequestQueue(cache, network);
                queue.start();


                JsonObjectRequest request = new JsonObjectRequest(
                        Request.Method.POST,
                        _OnlineUrlsecEtiket,
                        parametre,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {

                                    ObjectMapper objectMapper = new ObjectMapper();

                                    Viewsec_etiket_uretim _Yanit = objectMapper.readValue(response.toString(), Viewsec_etiket_uretim.class);

                                    uretim_etiket etiket = _Yanit._tag;

                                    if(_Yanit.equals("0"))
                                    {
                                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                                .setTitleText("HATA")
                                                .setContentText(_Yanit._zHataAciklama)
                                                .show();

                                    }

                                    else
                                    {
                                        try {
                                            if(_Yanit._tag.getetiket_durumu().equals("-1"))
                                            {
                                                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                                        .setTitleText("UYARI")
                                                        .setContentText("Lütfen uygun bir ürün etiketi okutunuz. Ürün kaydı bulunamadı..")
                                                        .showCancelButton(false)
                                                        .setConfirmText("TAMAM")
                                                        .show();

                                            }
                                            else
                                            {
                                                if(_Yanit._tag.getetiket_durumu().equals("0") == false)
                                                {
                                                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                                            .setTitleText("Üretilmiş Etiket")
                                                            .setContentText("Üretim için uygun olmayan etiket.Etiketin üretim işlemi tamamlanmıştır.")
                                                            .showCancelButton(false)
                                                            .setConfirmText("TAMAM")
                                                            .show();
                                                }
                                                else
                                                {
                                                    if(_Yanit._tag.getetiket_uretim_uygunlugu().equals("1") == false)
                                                    {
                                                        String _Yazi="ÜRETİM ONAYI ALINAMADI.";
                                                        _Yazi+="<br/>";
                                                        _Yazi+="ÜRETİM KURALLARI";
                                                        _Yazi+="1) İLK 5 DAKİKA İÇİNDE ÜRETİM İŞLEMİNİ GERÇEKLEŞTİREMEZSİNİZ.";
                                                        _Yazi+="<br/>";
                                                        _Yazi+="2) 3 GÜN İÇİNDE ÜRETİM KAYDI GERÇEKLEŞMEYEN ETİKET İÇİN ÜRETİM KAYDI OLUŞTURULAMAZ.";
                                                        _Yazi+="<br/>";
                                                        _Yazi+="3) SİSTEM İÇİNDE AYNI İŞ EMRİNE AİT EN FAZLA 2 ADET ÜRETİMİ TAMAMLANMAMIŞ LOT BULUNABİLİR.";
                                                        _Yazi+="<br/>";


                                                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                                                .setTitleText("ONAY ALINAMADI")
                                                                .setContentText(_Yazi)
                                                                .showCancelButton(false)
                                                                .setConfirmText("TAMAM")
                                                                .show();
                                                    }
                                                    else
                                                    {
                                                        if(depo.depo_id.equals("-1") || silo.depo_id.equals("-1"))
                                                        {
                                                            frg_depo_secimi fragmentyeni = new frg_depo_secimi();
                                                            fragmentyeni.fn_senddata(_Yanit._tag);
                                                            FragmentManager fragmentManager = getFragmentManager();
                                                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_depo_secimi").addToBackStack(null);
                                                            fragmentTransaction.commit();
                                                        }
                                                        else
                                                        {
                                                            if ((_Yanit._tag.getdepo_silo_secimi().equals("") == false && (!etiket.getdepo_silo_secimi().contains(depo.depo_id) || !etiket.getdepo_silo_secimi().contains(silo.depo_id))))
                                                            {
                                                                frg_depo_secimi fragmentyeni = new frg_depo_secimi();
                                                                fragmentyeni.fn_senddata(etiket);
                                                                FragmentManager fragmentManager = getFragmentManager();
                                                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                                fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_depo_secimi").addToBackStack(null);
                                                                fragmentTransaction.commit();
                                                            }
                                                            else
                                                            {
                                                                if (etiket.getpaket_tipi().equals("1"))
                                                                {
                                                                    if (etiket.getisemri_tipialt().equals("350"))
                                                                    {
                                                                        manipulasyon_paketli_uret(etiket);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                        }catch (Exception ex)
                                        {
                                            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                                    .setTitleText("HATA 175")
                                                    .setContentText(ex.toString())
                                                    .show();

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

                                Toast.makeText(getContext(), "error =" + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }


                );
                int socketTimeout = 30000;//30 seconds - change to what you want
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                request.setRetryPolicy(policy);
                queue.add(request);
                    //endregion
            }


        }catch (Exception ex )
        {
            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("HATA")
                    .setContentText("Sistesem bir hata oluştu = "+ex.toString())
                    .showCancelButton(false)
                    .setContentTextSize(20)
                    .setConfirmText("TAMAM")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();

                            //_IslemVarMi = true;
                        }
                    })
                    .show();
        }

    }

    private class fn_btnEtiketsizUretim implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }

    private class fn_btnIptal implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }

    private class fn_btncikis implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            frg_uretim_menu_panel fragmentyeni = new frg_uretim_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_uretim_menu_panel").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}
