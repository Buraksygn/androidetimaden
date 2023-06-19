package com.etimaden.SevkiyatIslemleri;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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
import com.android.volley.toolbox.Volley;
import com.etimaden.GirisSayfasi;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cResponseResult.Sevkiyat_isemri;
import com.etimaden.cResponseResult.Viewget_yukleme_palet_sayisi_miktar;
import com.etimaden.cResponseResult.Viewguncelle_sevk_hareket;
import com.etimaden.cResponseResult.ViewsevkiyatDevam;
import com.etimaden.cResponseResult.ViewsevkiyatIptal;
import com.etimaden.cResponseResult.viewDeger_01;
import com.etimaden.cResponseResult.viewparsiyel_kontrol_01;
import com.etimaden.cResponseResult.viewsevkiyatKapat;
import com.etimaden.ugr_demo.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.etimaden.cSabitDegerler._zkullaniciadi;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;

public class frg_arac_onayla extends Fragment {

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

    Button btn_02;
    Button btn_04;
    Button btn_05;
    Button btn_03;

    Button btn_01;

    SweetAlertDialog pDialog;

    public String _EkleAracAktivasyonuUrl = "";
    public String _UpdateAracAktivasyonuUrl = "";
    public String _AracKapatma = "";
    public String _AraTartim = "";
    public String _AraTartim_01 = "";
    public String _SevkiyatDevam = "";

    public String _IslemTamam = "";

    public Sevkiyat_isemri aktif_sevk_isemri ;

    public String _Yazi="";

    public  String _OnlineUrlTartimIptal="";
    public  String _OnlineUrlget_yukleme_palet_sayisi_miktar="";
    public  String _OnlineGuncelleSevkHareket="";
    public  String _zMiktar="";
    public  String _zPaletSayisi="";

    public viewparsiyel_kontrol_01 _YanitAraTartim =new viewparsiyel_kontrol_01();

    public frg_arac_onayla() {
        // Required empty public constructor
    }

    public static frg_arac_onayla newInstance() {

        return new frg_arac_onayla();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_arac_onayla, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        _myIslem = new VeriTabani(getContext());

        fn_AyarlariYukle();

        // new VeriTabani(getContext()).fn_EpcTemizle();

        // ((GirisSayfasi) getActivity()).fn_ListeTemizle();
        //((GirisSayfasi) getActivity()).fn_GucAyarla(248);

        _btngeri = (Button) getView().findViewById(R.id.btngeri);
        _btngeri.playSoundEffect(0);
        _btngeri.setOnClickListener(new fn_Geri());

        btn_02 = (Button) getView().findViewById(R.id.btn_02);
        btn_02.playSoundEffect(0);
        btn_02.setOnClickListener(new fn_arac_aktive_et());

        btn_05 = (Button) getView().findViewById(R.id.btn_05);
        btn_05.playSoundEffect(0);
        btn_05.setOnClickListener(new fn_ana_menu());

        btn_01 = (Button)getView().findViewById(R.id.btn_01);
        btn_01.playSoundEffect(0);
        btn_01.setOnClickListener(new fn_btn_01());

        btn_04 = (Button)getView().findViewById(R.id.btn_04);
        btn_04.playSoundEffect(0);
        btn_04.setOnClickListener(new fn_btn_04());

        btn_03 = (Button)getView().findViewById(R.id.btn_03);
        btn_03.playSoundEffect(0);
        btn_03.setOnClickListener(new fn_Ara_Tartima_Yolla());
    }



    public void fn_IptalEt()
    {
        pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE);
        pDialog.setTitleText("YÜKLENİYOR");
        pDialog.setContentText(" Kontrol ediliyor Lütfen bekleyiniz.");
        //pDialog.setContentText(_TempEpc);
        pDialog.setCancelable(false);
        pDialog.show();
        pDialog.findViewById(R.id.confirm_button).setVisibility(View.GONE);

        Cache cache = new DiskBasedCache(getContext().getCacheDir(), 2*1024 * 1024); // 2MB cap
        Network network = new BasicNetwork(new HurlStack());
        JSONObject parametre = new JSONObject();
        RequestQueue queue = new RequestQueue(cache, network);
        queue.start();

        try {

            parametre.put("islem_id", aktif_sevk_isemri.islem_id);
            parametre.put("rota_id", aktif_sevk_isemri.rota_id);
            parametre.put("_zsunucu_ip_adresi", _ayarsunucuip);
            parametre.put("_zaktif_alt_tesis", _ayaraktifalttesis);
            parametre.put("_zaktif_tesis", _ayaraktiftesis);
            parametre.put("_zkullaniciadi", _zkullaniciadi);
            parametre.put("_zsifre", _zsifre);


        } catch (JSONException error) {
            error.printStackTrace();
        }


        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                _OnlineUrlTartimIptal,
                parametre,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // pDialog.hide();

                            ObjectMapper objectMapper = new ObjectMapper();

                            ViewsevkiyatIptal _Yanit = objectMapper.readValue(response.toString(), ViewsevkiyatIptal.class);

                            if (_Yanit._zSonuc.equals("0"))
                            {
                                pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                pDialog.setTitle("HATA");
                                pDialog.setContentTextSize(20);
                                pDialog.setContentText(_Yanit._zHataAciklama);
                                pDialog.findViewById(R.id.confirm_button).setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                if(_Yanit._zDurum.equals("1"))
                                {
                                    try
                                    {
                                        if(pDialog.isShowing()==true)
                                        {
                                            pDialog.hide();
                                        }
                                    }catch (Exception ex)
                                    {

                                    }

                                    frg_sevkiyat_menu_panel fragmentyeni = new frg_sevkiyat_menu_panel();
                                    FragmentManager fragmentManager = getFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_sevkiyat_menu_panel").addToBackStack(null);
                                    fragmentTransaction.commit();
                                }
                            }

                        } catch (JsonMappingException ex2) {
                            Toast.makeText(getContext(), "error =" + ex2.toString(), Toast.LENGTH_SHORT).show();
                        } catch (JsonProcessingException ex1) {
                            Toast.makeText(getContext(), "error =" + ex1.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.hide();
                        Toast.makeText(getContext(), "error =" + error, Toast.LENGTH_SHORT).show();
                    }
                }
        );
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);
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

        if(_ayarbaglantituru.equals("wifi"))
        {
            _EkleAracAktivasyonuUrl = "http://"+_ayarsunucuip+":"+_zportWifi+"/api/EkleAracAktivasyonu";
            _UpdateAracAktivasyonuUrl = "http://"+_ayarsunucuip+":"+_zportWifi+"/api/UpdateAracAktivasyonu";
            _AracKapatma = "http://"+_ayarsunucuip+":"+_zportWifi+"/api/sec_sevk_miktar";
            _AraTartim = "http://"+_ayarsunucuip+":"+_zportWifi+"/api/sec_sevk_miktar";
            _AraTartim_01 = "http://"+_ayarsunucuip+":"+_zportWifi+"/api/parsiyel_kontrol_01";
            _IslemTamam = "http://"+_ayarsunucuip+":"+_zportWifi+"/api/sevkiyatKapat";
            _OnlineGuncelleSevkHareket  = "http://"+_ayarsunucuip+":"+_zportWifi+"/api/guncelle_sevk_hareket";
            _OnlineUrlTartimIptal = "http://"+_ayarsunucuip+":"+_zportWifi+"/api/sevkiyatIptal";
            _OnlineUrlget_yukleme_palet_sayisi_miktar = "http://"+_ayarsunucuip+":"+_zportWifi+"/api/get_yukleme_palet_sayisi_miktar";
            _SevkiyatDevam = "http://"+_ayarsunucuip+":"+_zportWifi+"/api/sevkiyatDevam";
        }
        else
        {
            _EkleAracAktivasyonuUrl = "http://88.255.50.73:"+_zport3G+"/api/EkleAracAktivasyonu";
            _UpdateAracAktivasyonuUrl = "http://88.255.50.73:"+_zport3G+"/api/UpdateAracAktivasyonu";
            _AracKapatma = "http://88.255.50.73:"+_zport3G+"/api/sec_sevk_miktar";
            _AraTartim = "http://88.255.50.73:"+_zport3G+"/api/sec_sevk_miktar";
            _AraTartim_01 = "http://88.255.50.73:"+_zport3G+"/api/parsiyel_kontrol_01";
            _IslemTamam = "http://88.255.50.73:"+_zport3G+"/api/sevkiyatKapat";
            _OnlineUrlTartimIptal = "http://88.255.50.73:"+_zport3G+"/api/sevkiyatIptal";
            _OnlineGuncelleSevkHareket  = "http://"+_ayarsunucuip+":"+_zportWifi+"/api/guncelle_sevk_hareket";
            _OnlineUrlget_yukleme_palet_sayisi_miktar = "http://88.255.50.73:"+_zport3G+"/api/get_yukleme_palet_sayisi_miktar";
            _SevkiyatDevam = "http://88.255.50.73:"+_zport3G+"/api/sevkiyatDevam";
        }
    }

    public void fn_senddata(Sevkiyat_isemri v_aktif_sevk_isemri)
    {
        this.aktif_sevk_isemri=v_aktif_sevk_isemri;
    }


    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_arac_bulundu fragmentyeni = new frg_arac_bulundu();
            fragmentyeni.fn_senddata(aktif_sevk_isemri);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_arac_bulundu").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_arac_aktive_et implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Boolean _bDevam=true;

            String konteyner_turu = "";
            String kont_kodu = "";
            String islem_id= "";
            String depo_kodu= "";
            String urun_kodu= "";
            String isemri_id= "";
            String kod_sap= "";
            String rota_id= "";
            String id_aracisemri= "";
            String isemri_detay_id= "";
            String arac_kodu= "";
            String aktif_sunucu= "";
            String aktif_kullanici= "";


            try {
                //JSONObject jsonObj = new JSONObject(aktif_sevk_isemri);
                konteyner_turu = aktif_sevk_isemri.konteyner_turu.trim();
                kont_kodu = aktif_sevk_isemri.kont_kodu.trim();
                islem_id = aktif_sevk_isemri.islem_id.trim();
                depo_kodu = aktif_sevk_isemri.depo_kodu.trim();
                urun_kodu = aktif_sevk_isemri.urun_kodu.trim();
                isemri_id = aktif_sevk_isemri.isemri_id.trim();
                kod_sap = aktif_sevk_isemri.kod_sap.trim();
                rota_id = aktif_sevk_isemri.rota_id.trim();
                id_aracisemri = aktif_sevk_isemri.id_aracisemri.trim();
                isemri_detay_id = aktif_sevk_isemri.isemri_detay_id.trim();
                arac_kodu = aktif_sevk_isemri.arac_kodu.trim();
                aktif_sunucu = _ayaraktifsunucu;
                aktif_kullanici = _ayaraktifkullanici;

                if (!konteyner_turu.equals("") && kont_kodu.equals("")) {
                    pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                    pDialog.setTitleText("ARAÇ TANIMI EKSİK");
                    pDialog.setContentText("KONTEYNER TANIMLAMA VE EŞLEŞTİRME İŞLEMİ YAPMADAN BU İŞLEMİ GERÇEKLEŞTİREMEZSİNİZ.");
                    pDialog.show();

                    _bDevam = false;
                    // Program.giveHataMesaji("ARAÇ TANIMI EKSİK","KONTEYNER TANIMLAMA VE EŞLEŞTİRME İŞLEMİ YAPMADAN BU İŞLEMİ GERÇEKLEŞTİREMEZSİNİZ.","");
                    return;
                }
            }
            catch (Exception ex) {

            }

            if(_bDevam == true)
            {

                pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
                pDialog.setTitleText("KONTROL");
                pDialog.setContentText("Kontrol ediliyor Lütfen bekleyiniz.");
                //pDialog.setContentText(_TempEpc);
                pDialog.setCancelable(false);
                pDialog.show();
                pDialog.findViewById(R.id.confirm_button).setVisibility(View.GONE);

                RequestQueue queue = Volley.newRequestQueue(getContext());
                JSONObject parametre = new JSONObject();


                if(islem_id.equals(""))
                {
//Ekle Araç Aktivasyonu
                    try {


                        parametre.put("depo_kodu", depo_kodu);
                        parametre.put("urun_kodu", urun_kodu);
                        parametre.put("isemri_id", isemri_id);
                        parametre.put("kod_sap", kod_sap);
                        parametre.put("rota_id", rota_id);
                        parametre.put("id_aracisemri", id_aracisemri);
                        parametre.put("isemri_detay_id", isemri_detay_id);
                        parametre.put("arac_kodu", arac_kodu);
                        parametre.put("kont_kodu", kont_kodu);
                        parametre.put("aktif_sunucu", aktif_sunucu);
                        parametre.put("aktif_kullanici", aktif_kullanici);
                        parametre.put("_zsunucu_ip_adresi", _ayarsunucuip);
                        parametre.put("_zaktif_alt_tesis", _ayaraktifalttesis);
                        parametre.put("_zaktif_tesis", _ayaraktiftesis);
                        parametre.put("_zkullaniciadi", _zkullaniciadi);
                        parametre.put("_zsifre", _zsifre);
                    } catch (JSONException error) {
                        error.printStackTrace();
                    }

                    JsonObjectRequest request = new JsonObjectRequest(
                            Request.Method.POST,
                            _EkleAracAktivasyonuUrl,
                            parametre,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        // pDialog.hide();

                                        String _zSonuc = response.getString("_zSonuc");
                                        String _zHataAciklama = response.getString("_zHataAciklama");
                                        String _zAciklama = response.getString("_zAciklama");


                                        if (_zSonuc.equals("0"))
                                        {
                                            pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                            pDialog.setTitle("HATA");
                                            pDialog.setContentText(_zHataAciklama);
                                            pDialog.findViewById(R.id.confirm_button).setVisibility(View.VISIBLE);
                                        }
                                        else
                                        {
                                            pDialog.hide();

                                            new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                                    .setTitleText("TAMAMLANDI")
                                                    .setContentText("İşlem onaylandı.")
                                                    .setConfirmText("TAMAM")
                                                    .showCancelButton(false)
                                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                        @Override
                                                        public void onClick(SweetAlertDialog sDialog)
                                                        {
                                                            sDialog.dismissWithAnimation();

                                                            sDialog.hide();

                                                            frg_sevkiyat_menu_panel fragmentyeni = new frg_sevkiyat_menu_panel();
                                                            FragmentManager fragmentManager = getFragmentManager();
                                                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_sevkiyat_menu_panel");
                                                            fragmentTransaction.commit();
                                                        }
                                                    })
                                                    .show();

                                        }

                                    } catch (JSONException ex) {
                                        Toast.makeText(getContext(), "error =" + ex.toString(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    pDialog.hide();
                                    Toast.makeText(getContext(), "error =" + error, Toast.LENGTH_SHORT).show();
                                }
                            }


                    );
                    int socketTimeout = 30000;//30 seconds - change to what you want
                    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                    request.setRetryPolicy(policy);
                    queue.add(request);

                }
                else
                {
                    try {

                        parametre.put("kayit_parametresi", "");
                        parametre.put("aktif_kullanici", aktif_kullanici);
                        parametre.put("_zsunucu_ip_adresi", _ayarsunucuip);
                        parametre.put("_zaktif_alt_tesis", _ayaraktifalttesis);
                        parametre.put("_zaktif_tesis", _ayaraktiftesis);
                        parametre.put("aktif_sunucu", aktif_sunucu);
                        parametre.put("isemri_detay_id", isemri_detay_id);
                        parametre.put("kod_sap", kod_sap);
                        parametre.put("isemri_id", isemri_id);
                        parametre.put("urun_kodu", urun_kodu);
                        parametre.put("kont_kodu", kont_kodu);
                        parametre.put("rota_id", rota_id);
                        parametre.put("_zkullaniciadi", _zkullaniciadi);
                        parametre.put("_zsifre", _zsifre);

                    } catch (JSONException error) {
                        error.printStackTrace();
                    }

                    JsonObjectRequest request = new JsonObjectRequest(
                            Request.Method.POST,
                            _UpdateAracAktivasyonuUrl,
                            parametre,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        // pDialog.hide();

                                        String _zSonuc = response.getString("_zSonuc");
                                        String _zHataAciklama = response.getString("_zHataAciklama");
                                        String _zAciklama = response.getString("_zAciklama");


                                        if (_zSonuc.equals("0"))
                                        {
                                            pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                            pDialog.setTitle("HATA");
                                            pDialog.setContentText(_zHataAciklama);
                                            pDialog.findViewById(R.id.confirm_button).setVisibility(View.VISIBLE);
                                        }
                                        else
                                        {
                                            pDialog.hide();

                                            new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                                    .setTitleText("ONAY")
                                                    .setContentText("İşlem onaylandı.")
                                                    .setCancelText("Hayır")
                                                    .setConfirmText("TAMAM")
                                                    .showCancelButton(false)
                                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                        @Override
                                                        public void onClick(SweetAlertDialog sDialog)
                                                        {
                                                            sDialog.dismissWithAnimation();

                                                            sDialog.hide();

                                                            frg_sevkiyat_menu_panel fragmentyeni = new frg_sevkiyat_menu_panel();
                                                            FragmentManager fragmentManager = getFragmentManager();
                                                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_sevkiyat_menu_panel");
                                                            fragmentTransaction.commit();


                                                        }
                                                    })
                                                    .show();

                                        }

                                    } catch (JSONException ex) {
                                        Toast.makeText(getContext(), "error =" + ex.toString(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    pDialog.hide();
                                    Toast.makeText(getContext(), "error =" + error, Toast.LENGTH_SHORT).show();
                                }
                            }


                    );
                    int socketTimeout = 30000;//30 seconds - change to what you want
                    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                    request.setRetryPolicy(policy);
                    queue.add(request);
                }
            }




        }
    }

    private class fn_ana_menu implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_sevkiyat_menu_panel fragmentyeni = new frg_sevkiyat_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_sevkiyat_menu_panel");
            fragmentTransaction.commit();
        }
    }

    private class fn_btn_01 implements View.OnClickListener {
        @Override
        public void onClick(View view)
        {
            frg_konteyner_aktivasyon fragmentyeni = new frg_konteyner_aktivasyon();
            fragmentyeni.fn_senddata(aktif_sevk_isemri);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_konteyner_aktivasyon");
            fragmentTransaction.commit();
        }
    }

    private class fn_btn_04 implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            if (aktif_sevk_isemri.islem_id.equals("")) {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentText("Araç henüz açılmadığı için kapatamazsınız. Lütfen önce aracı açınız")
                        .setConfirmText("TAMAM")
                        .showCancelButton(false)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                sDialog.hide();
                            }
                        })
                        .show();

            } else {

                pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE);
                pDialog.setTitleText("YÜKLENİYOR");
                pDialog.setContentText(aktif_sevk_isemri.arac_plaka + " kontrol ediliyor Lütfen bekleyiniz.");
                //pDialog.setContentText(_TempEpc);
                pDialog.setCancelable(false);
                pDialog.show();
                pDialog.findViewById(R.id.confirm_button).setVisibility(View.GONE);

                JSONObject parametre = new JSONObject();

                try {
                    parametre.put("_zkullaniciadi", _zkullaniciadi);
                    parametre.put("_zsifre", _zsifre);
                    parametre.put("_zsunucu_ip_adresi", _ayarsunucuip);
                    parametre.put("_zaktif_alt_tesis", _ayaraktifalttesis);
                    parametre.put("_zaktif_tesis", _ayaraktiftesis);
                    parametre.put("_zislem_id", aktif_sevk_isemri.islem_id);
                    parametre.put("_zkonteyner_turu", aktif_sevk_isemri.konteyner_turu);
                    parametre.put("_zkont_rfid", aktif_sevk_isemri.kont_rfid);
                    parametre.put("_zisemri_detay_id", aktif_sevk_isemri.isemri_detay_id);
                    parametre.put("aktif_sunucu", _ayaraktifsunucu);
                    parametre.put("aktif_kullanici", _ayaraktifkullanici);
                } catch (JSONException error) {
                    error.printStackTrace();
                }

                RequestQueue mRequestQueue;

                Cache cache = new DiskBasedCache(getContext().getCacheDir(), 2 * 1024 * 1024); // 1MB cap
                Network network = new BasicNetwork(new HurlStack());

                RequestQueue queue = new RequestQueue(cache, network);
                queue.start();

                JsonObjectRequest request = new JsonObjectRequest(
                        Request.Method.POST,
                        _AracKapatma,
                        parametre,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    ObjectMapper objectMapper = new ObjectMapper();


                                    viewDeger_01 _Yanit = objectMapper.readValue(response.toString(), viewDeger_01.class);

                                    if (_Yanit._zSonuc.equals("0")) {
                                        pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                        pDialog.setTitle("HATA");
                                        pDialog.setContentText(_Yanit._zHataAciklama);
                                        pDialog.findViewById(R.id.confirm_button).setVisibility(View.VISIBLE);
                                    } else {
                                        if (_Yanit._zSoruSor.equals("1")) {
                                            //
                                            pDialog.changeAlertType(SweetAlertDialog.WARNING_TYPE);
                                            pDialog.setTitle("UYARI");
                                            pDialog.setContentText("ARACA YÜKLEME YAPILMAMIŞ DURUMDA. BU ARACIN KAYDINI İPTAL ETMEK İSTEDİĞİNİZDEN EMİN MİSİNİZ?");
                                            pDialog.findViewById(R.id.confirm_button).setVisibility(View.VISIBLE);

                                            pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sDialog) {
                                                    sDialog.dismissWithAnimation();

                                                    /*BEGIN------>*/

                                                    fn_IptalEt();

                                                    /*<--------END*/
                                                }
                                            });
                                        } else {
                                            pDialog.setTitle("ARAÇ KAPAMA UYARISI");

                                            _Yazi = "PLAKA : " + aktif_sevk_isemri.arac_plaka + "<br>  ARAÇ İÇİNDE ;\n PALET SAYISI: " + _Yanit._zPaletSayisi +
                                                    "<br> MİKTAR: " + _Yanit._zMiktar + " KG<br>  ÜRÜN BULUNMAKTADIR !!<br>  ARACI KAPATMAK İSTİYOR MUSUNUZ ? ";

                                            pDialog.changeAlertType(SweetAlertDialog.WARNING_TYPE);
                                            pDialog.setContentText(_Yazi);
                                            pDialog.showCancelButton(true);
                                            pDialog.setCancelText("İptal Et");
                                            pDialog.setConfirmText("ARACI KAPAT");
                                            pDialog.findViewById(R.id.confirm_button).setVisibility(View.VISIBLE);
                                            pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sDialog) {
                                                    sDialog.dismissWithAnimation();

                                                    /*BEGIN------>*/

                                                    fn_AraciKapat();

                                                    /*<--------END*/
                                                }
                                            });


                                        }
                                    }


                                } catch (JsonMappingException e) {
                                    pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                    pDialog.setTitle("HATA");
                                    pDialog.setContentText(e.toString());
                                    pDialog.findViewById(R.id.confirm_button).setVisibility(View.VISIBLE);
                                    e.printStackTrace();
                                } catch (JsonProcessingException e) {
                                    pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                    pDialog.setTitle("HATA");
                                    pDialog.setContentText(e.toString());

                                    pDialog.findViewById(R.id.confirm_button).setVisibility(View.VISIBLE);
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                pDialog.hide();
                                Toast.makeText(getContext(), "error =" + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }


                );
                int socketTimeout = 30000;//30 seconds - change to what you want
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                request.setRetryPolicy(policy);
                queue.add(request);
            }
        }
    }

    private void fn_AraciKapat()
    {
        if(pDialog !=null && pDialog.isShowing())
        {
            pDialog.hide();
        }

        pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.setTitleText("YÜKLENİYOR");
        pDialog.setContentText("Araç Kapatılıyor. Lütfen Bekleyiniz");
        //pDialog.setContentText(_TempEpc);
        pDialog.setCancelable(false);
        pDialog.show();
        pDialog.findViewById(R.id.confirm_button).setVisibility(View.GONE);

        Cache cache = new DiskBasedCache(getContext().getCacheDir(), 2*1024 * 1024); // 2MB cap
        Network network = new BasicNetwork(new HurlStack());
        JSONObject parametre = new JSONObject();

        RequestQueue queue = new RequestQueue(cache, network);
        queue.start();

        try
        {

            parametre.put("_zsunucu_ip_adresi", _ayarsunucuip);
            parametre.put("_zaktif_alt_tesis", _ayaraktifalttesis);
            parametre.put("_zaktif_tesis", _ayaraktiftesis);
            parametre.put("_zkullaniciadi", _zkullaniciadi);
            parametre.put("_zsifre", _zsifre);
            parametre.put("aktif_sunucu", _ayaraktifsunucu);
            parametre.put("aktif_kullanici", _ayaraktifkullanici);
            parametre.put("_zislem_id", aktif_sevk_isemri.islem_id);
            parametre.put("_zkont_kodu", aktif_sevk_isemri.kont_kodu);
            parametre.put("_zrota_id", aktif_sevk_isemri.rota_id);
        }
        catch (Exception ex )
        {
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                _IslemTamam,
                parametre,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            ObjectMapper objectMapper = new ObjectMapper();

                            viewsevkiyatKapat _Yanit = objectMapper.readValue(response.toString(), viewsevkiyatKapat.class);

                            if (_Yanit._zSonuc.equals("0"))
                            {
                                pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                pDialog.setTitle("HATA");
                                pDialog.setContentText(""+_Yanit._zHataAciklama);
                                pDialog.findViewById(R.id.confirm_button).setVisibility(View.VISIBLE);
                            }
                            else
                            {

                                pDialog.hide();

                                new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("TAMAMLANDI")
                                        .setContentText("İşlem başarı ile tamamlanmıştır.")
                                        .setConfirmText("TAMAM")
                                        .showCancelButton(false)
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog)
                                            {
                                                sDialog.dismissWithAnimation();

                                                sDialog.hide();

                                                frg_sevkiyat_menu_panel fragmentyeni = new frg_sevkiyat_menu_panel();
                                                FragmentManager fragmentManager = getFragmentManager();
                                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_sevkiyat_menu_panel");
                                                fragmentTransaction.commit();
                                            }
                                        })
                                        .show();
                            }



                        }
                        catch (JsonMappingException exxx) {
                            Log.d("hata", exxx.getMessage());
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.hide();
                        Toast.makeText(getContext(), "error =" + error, Toast.LENGTH_SHORT).show();
                    }
                }


        );
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);
    }



    private class fn_Ara_Tartima_Yolla implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            if (aktif_sevk_isemri.islem_id.equals(""))
            {
                SweetAlertDialog pDialogUyari = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                pDialogUyari.setTitleText("ARAÇ TANIMI EKSİK");
                pDialogUyari.setContentText("SEVKİYAT DETAYLARINI TAMAMLAMADAN YAPMADAN BU İŞLEMİ GERÇEKLEŞTİREMEZSİNİZ.");
                pDialogUyari.show();
            }
            else
            {
                pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE);
                pDialog.setTitleText("YÜKLENİYOR");
                pDialog.setContentText(aktif_sevk_isemri.arac_plaka + " kontrol ediliyor Lütfen bekleyiniz.");
                //pDialog.setContentText(_TempEpc);
                pDialog.setCancelable(false);
                pDialog.show();
                pDialog.findViewById(R.id.confirm_button).setVisibility(View.GONE);

                JSONObject parametre = new JSONObject();

                try
                {
                    parametre.put("_zkullaniciadi", _zkullaniciadi);
                    parametre.put("_zsifre", _zsifre);
                    parametre.put("_zsunucu_ip_adresi", _ayarsunucuip);
                    parametre.put("_zaktif_alt_tesis", _ayaraktifalttesis);
                    parametre.put("_zaktif_tesis", _ayaraktiftesis);
                    parametre.put("aktif_sunucu", _ayaraktifsunucu);
                    parametre.put("aktif_kullanici", _ayaraktifkullanici);

                    parametre.put("sevk_hareket_id", aktif_sevk_isemri.islem_id);

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
                        _AraTartim_01,
                        parametre,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try
                                {
                                    ObjectMapper objectMapper = new ObjectMapper();

                                    _YanitAraTartim = objectMapper.readValue(response.toString(), viewparsiyel_kontrol_01.class);

                                    if(_YanitAraTartim._zSonuc.equals("0"))
                                    {
                                        pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                        pDialog.setTitle("HATA");
                                        pDialog.setContentTextSize(20);
                                        pDialog.setContentText(_YanitAraTartim._zHataAciklama);
                                        pDialog.findViewById(R.id.confirm_button).setVisibility(View.VISIBLE);
                                    }
                                    else
                                    {
                                        try
                                        {
                                            if(pDialog != null && pDialog.isShowing() )
                                            {
                                                pDialog.hide();
                                            }
                                        }catch (Exception ex)
                                        {

                                        }

                                        /*BEGIN ----------->*/

                                        if (_YanitAraTartim._deger01.equals("0"))
                                        {
                                            new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                                                    .setTitleText("YÜKLEME MİKTARI UYARISI")
                                                    .setContentText("ARACA YÜKLEME YAPILMAMIŞ DURUMDA. BU ARACIN KAYDINI İPTAL ETMEK İSTEDİĞİNİZDEN EMİN MİSİNİZ?")
                                                    .setCancelText("Hayır")
                                                    .setContentTextSize(20)
                                                    .setConfirmText("EVET")
                                                    .showCancelButton(true)
                                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                        @Override
                                                        public void onClick(SweetAlertDialog sDialog)
                                                        {
                                                            sDialog.dismissWithAnimation();

                                                            sDialog.hide();

                                                            fn_IptalEt();
                                                        }
                                                    })
                                                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                        @Override
                                                        public void onClick(SweetAlertDialog sDialog)
                                                        {
                                                            sDialog.cancel();
                                                        }
                                                    })
                                                    .show();
                                        }
                                        else
                                        {
                                           fn_guncelle_sevk_hareket();
                                        }

                                        /*<--------------END*/
                                    }


                                } catch (JsonMappingException e)
                                {
                                    pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                    pDialog.setTitle("HATA");
                                    pDialog.setContentText(e.toString());
                                    pDialog.findViewById(R.id.confirm_button).setVisibility(View.VISIBLE);
                                    e.printStackTrace();
                                } catch (JsonProcessingException e)
                                {
                                    pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                    pDialog.setTitle("HATA");
                                    pDialog.setContentText(e.toString());
                                    pDialog.findViewById(R.id.confirm_button).setVisibility(View.VISIBLE);
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                pDialog.hide();
                                Toast.makeText(getContext(), "error =" + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }


                );
                int socketTimeout = 30000;//30 seconds - change to what you want
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                request.setRetryPolicy(policy);
                queue.add(request);
            }
        }



        private void fn_guncelle_sevk_hareket()
        {
            pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE);
            pDialog.setTitleText("YÜKLENİYOR");
            pDialog.setContentText(" Kontrol ediliyor Lütfen bekleyiniz.");
            //pDialog.setContentText(_TempEpc);
            pDialog.setCancelable(false);
            pDialog.show();
            pDialog.findViewById(R.id.confirm_button).setVisibility(View.GONE);

            Cache cache = new DiskBasedCache(getContext().getCacheDir(), 2 * 1024 * 1024); // 2MB cap
            Network network = new BasicNetwork(new HurlStack());
            JSONObject parametre = new JSONObject();
            RequestQueue queue = new RequestQueue(cache, network);
            queue.start();

            try {

                parametre.put("_zkullaniciadi", _zkullaniciadi);
                parametre.put("_zsifre", _zsifre);
                parametre.put("_zsunucu_ip_adresi", _ayarsunucuip);
                parametre.put("_zaktif_alt_tesis", _ayaraktifalttesis);
                parametre.put("_zaktif_tesis", _ayaraktiftesis);
                parametre.put("aktif_sunucu", _ayaraktifsunucu);
                parametre.put("aktif_kullanici", _ayaraktifkullanici);

                parametre.put("hareket_id", aktif_sevk_isemri.islem_id);
                parametre.put("_zMiktar", _YanitAraTartim._deger01);
                parametre.put("_zPaletSayisi", _YanitAraTartim._deger02);


            } catch (JSONException error) {
                error.printStackTrace();
            }


            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    _OnlineGuncelleSevkHareket,
                    parametre,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                // pDialog.hide();

                                ObjectMapper objectMapper = new ObjectMapper();

                                String _Gelen=response.toString();

                                int _Sayi=1;


                                Viewguncelle_sevk_hareket _Yanit = objectMapper.readValue(response.toString(), Viewguncelle_sevk_hareket.class);




                                if (_Yanit._zSonuc.equals("0"))
                                {
                                    pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                    pDialog.setTitle("HATA");
                                    pDialog.setContentText(_Yanit._zHataAciklama);
                                    pDialog.findViewById(R.id.confirm_button).setVisibility(View.VISIBLE);
                                }
                                else
                                {
                                    try
                                    {
                                        if(pDialog.isShowing())
                                        {
                                            pDialog.hide();
                                        }
                                    }catch (Exception ex)
                                    {

                                    }

                                    fn_get_yukleme_palet_sayisi_miktar();
                                }

                            } catch (JsonMappingException ex2) {
                                Toast.makeText(getContext(), "error =" + ex2.toString(), Toast.LENGTH_SHORT).show();
                            } catch (JsonProcessingException ex1) {
                                Toast.makeText(getContext(), "error =" + ex1.toString(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pDialog.hide();
                            Toast.makeText(getContext(), "error =" + error, Toast.LENGTH_SHORT).show();
                        }
                    }
            );
            int socketTimeout = 45000;//30 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            request.setRetryPolicy(policy);
            queue.add(request);

        }




        private void fn_get_yukleme_palet_sayisi_miktar()
        {

            pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE);
            pDialog.setTitleText("YÜKLENİYOR");
            pDialog.setContentText(" Kontrol ediliyor Lütfen bekleyiniz.");
            //pDialog.setContentText(_TempEpc);
            pDialog.setCancelable(false);
            pDialog.show();
            pDialog.findViewById(R.id.confirm_button).setVisibility(View.GONE);

            Cache cache = new DiskBasedCache(getContext().getCacheDir(), 2*1024 * 1024); // 2MB cap
            Network network = new BasicNetwork(new HurlStack());
            JSONObject parametre = new JSONObject();
            RequestQueue queue = new RequestQueue(cache, network);
            queue.start();

            try {

                parametre.put("_zkullaniciadi", _zkullaniciadi);
                parametre.put("_zsifre", _zsifre);
                parametre.put("_zsunucu_ip_adresi", _ayarsunucuip);
                parametre.put("_zaktif_alt_tesis", _ayaraktifalttesis);
                parametre.put("_zaktif_tesis", _ayaraktiftesis);
                parametre.put("aktif_sunucu", _ayaraktifsunucu);
                parametre.put("aktif_kullanici", _ayaraktifkullanici);

                parametre.put("_zdetay_id", aktif_sevk_isemri.isemri_detay_id);


            } catch (JSONException error) {
                error.printStackTrace();
            }



            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    _OnlineUrlget_yukleme_palet_sayisi_miktar,
                    parametre,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                // pDialog.hide();

                                ObjectMapper objectMapper = new ObjectMapper();

                                Viewget_yukleme_palet_sayisi_miktar _Yanit = objectMapper.readValue(response.toString(), Viewget_yukleme_palet_sayisi_miktar.class);

                                if (_Yanit._zSonuc.equals("0"))
                                {
                                    pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                    pDialog.setTitle("HATA");
                                    pDialog.setContentText(_Yanit._zHataAciklama);
                                    pDialog.findViewById(R.id.confirm_button).setVisibility(View.VISIBLE);
                                }
                                else
                                {
                                  try
                                  {
                                      try
                                      {
                                          if(pDialog.isShowing()==true)
                                          {
                                              pDialog.hide();
                                          }
                                      }catch (Exception ex)
                                      {

                                      }


                                      if (!aktif_sevk_isemri.konteyner_turu.equals("") && aktif_sevk_isemri.kont_rfid.equals(""))
                                      {
                                          new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                                  .setTitleText("HATA")
                                                  .setContentText("ARAÇ TANIMI EKSİK.KONTEYNER TANIMLAMA VE EŞLEŞTİRME İŞLEMİ YAPMADAN BU İŞLEMİ GERÇEKLEŞTİREMEZSİNİZ")
                                                  .setConfirmText("TAMAM")
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

                                        else
                                      {
                                          fn_AraTartimDevam();
                                      }


                                  }catch (Exception ex )
                                  {

                                  }
                                }

                            } catch (JsonMappingException ex2)
                            {
                                Toast.makeText(getContext(), "error =" + ex2.toString(), Toast.LENGTH_SHORT).show();
                            }
                            catch (JsonProcessingException ex1)
                            {
                                Toast.makeText(getContext(), "error =" + ex1.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pDialog.hide();
                            Toast.makeText(getContext(), "error =" + error, Toast.LENGTH_SHORT).show();
                        }
                    }
            );
            int socketTimeout = 30000;//30 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            request.setRetryPolicy(policy);
            queue.add(request);
        }
    }



    private void fn_AraTartimDevam() {

        String _Aciklama = "";

        _Aciklama = "PLAKA : '" + aktif_sevk_isemri.arac_plaka + "<br>'" +
                "ARAÇ İÇİNDE ;<br>  " +
                "PALET SAYISI: " + _YanitAraTartim._deger02 + "<br>" +
                "MİKTAR: " + _YanitAraTartim._deger01 + " KG <br>" +
                "ÜRÜN BULUNMAKTADIR !! <br>" +
                "Aracı 'ARA TARTIM' işlemine devam etmek istiyor musunuz ? ";


        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("ARA TARTIM ONAYI")
                .setContentText(_Aciklama)
                .setCancelText("İptal")
                .setConfirmText("TAMAM")
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();

                        sDialog.hide();

                        fn_sevkiyatDevam();
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                })
                .show();
    }

    private void fn_sevkiyatDevam() {


        pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE);
        pDialog.setTitleText("YÜKLENİYOR");
        pDialog.setContentText(" Kontrol ediliyor Lütfen bekleyiniz.");
        pDialog.setContentTextSize(20);
        //pDialog.setContentText(_TempEpc);
        pDialog.setCancelable(false);
        pDialog.show();
        pDialog.findViewById(R.id.confirm_button).setVisibility(View.GONE);

        Cache cache = new DiskBasedCache(getContext().getCacheDir(), 2 * 1024 * 1024); // 2MB cap
        Network network = new BasicNetwork(new HurlStack());
        JSONObject parametre = new JSONObject();
        RequestQueue queue = new RequestQueue(cache, network);
        queue.start();

        try {

            parametre.put("_zkullaniciadi", _zkullaniciadi);
            parametre.put("_zsifre", _zsifre);
            parametre.put("_zsunucu_ip_adresi", _ayarsunucuip);
            parametre.put("_zaktif_alt_tesis", _ayaraktifalttesis);
            parametre.put("_zaktif_tesis", _ayaraktiftesis);
            parametre.put("aktif_sunucu", _ayaraktifsunucu);
            parametre.put("aktif_kullanici", _ayaraktifkullanici);

            parametre.put("_zish_miktar", _YanitAraTartim._deger01);
            parametre.put("ish_paletsayisi", _YanitAraTartim._deger02);
            parametre.put("_zish_id", aktif_sevk_isemri.islem_id);
            parametre.put("_zkont_kodu", aktif_sevk_isemri.kont_kodu);
            parametre.put("_zrota_id", aktif_sevk_isemri.rota_id);


        } catch (JSONException error) {
            error.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                _SevkiyatDevam,
                parametre,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // pDialog.hide();

                            ObjectMapper objectMapper = new ObjectMapper();

                            ViewsevkiyatDevam _Yanit = objectMapper.readValue(response.toString(), ViewsevkiyatDevam.class);

                            if (_Yanit._zSonuc.equals("0"))
                            {
                                pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                pDialog.setTitle("HATA");
                                pDialog.setContentTextSize(20);
                                pDialog.setContentText(_Yanit._zHataAciklama);
                                pDialog.findViewById(R.id.confirm_button).setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                pDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                pDialog.setTitle("TAMAM");
                                pDialog.setContentTextSize(20);
                                pDialog.setContentText("İşlem başarı ile tamamlanmıştır.");
                                pDialog.findViewById(R.id.confirm_button).setVisibility(View.VISIBLE);

                                pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog)
                                    {
                                        sDialog.dismissWithAnimation();

                                        sDialog.hide();

                                        frg_sevkiyat_menu_panel fragmentyeni = new frg_sevkiyat_menu_panel();
                                        FragmentManager fragmentManager = getFragmentManager();
                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                        fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_sevkiyat_menu_panel");
                                        fragmentTransaction.commit();
                                    }});
                            }

                        } catch (JsonMappingException ex2)
                        {
                            Toast.makeText(getContext(), "error =" + ex2.toString(), Toast.LENGTH_SHORT).show();
                        }
                        catch (JsonProcessingException ex1)
                        {
                            Toast.makeText(getContext(), "error =" + ex1.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.hide();
                        Toast.makeText(getContext(), "error =" + error, Toast.LENGTH_SHORT).show();
                    }
                }
        );
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);
    }
}
