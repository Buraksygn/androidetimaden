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
import com.etimaden.SevkiyatIslemleri.Arac_aktivayon_islemleri.frg_arac_bulundu;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cResponseResult.Sevkiyat_isemri;
import com.etimaden.cResponseResult.Viewget_yukleme_palet_sayisi_miktar;
import com.etimaden.cResponseResult.Viewguncelle_sevk_hareket;
import com.etimaden.cResponseResult.ViewsevkiyatDevam;
import com.etimaden.cResponseResult.ViewsevkiyatIptal;
import com.etimaden.cResponseResult.viewDeger_01;
import com.etimaden.cResponseResult.viewparsiyel_kontrol_01;
import com.etimaden.cResponseResult.viewsevkiyatKapat;
import com.etimaden.genel.Genel;
import com.etimaden.genel.SweetAlertDialogG;
import com.etimaden.persos.Persos;
import com.etimaden.request.request_guncelle_sevk_hareket;
import com.etimaden.request.request_sevkiyat_isemri;
import com.etimaden.request.request_string;
import com.etimaden.ugr_demo.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


import static com.etimaden.cSabitDegerler._ipAdresi3G;
import static com.etimaden.cSabitDegerler._zkullaniciadi;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;

public class frg_arac_onayla extends Fragment {

    //private ProgressDialog progressDialog;
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

    SweetAlertDialogG pDialog;
    Persos persos;
    public String _EkleAracAktivasyonuUrl = "";
    public String _UpdateAracAktivasyonuUrl = "";
    public String _AracKapatma = "";
    public String _AraTartim = "";
    public String _AraTartim_01 = "";
    public String _SevkiyatDevam = "";

    public String _IslemTamam = "";

    public Sevkiyat_isemri aktif_sevk_isemri;

    public String _Yazi = "";

    public String _OnlineUrlTartimIptal = "";
    public String _OnlineUrlget_yukleme_palet_sayisi_miktar = "";
    public String _OnlineGuncelleSevkHareket = "";
    public String _OnlineUrlp="";
    public String _zMiktar = "";
    public String _zPaletSayisi = "";

    public viewparsiyel_kontrol_01 _YanitAraTartim = new viewparsiyel_kontrol_01();

    public frg_arac_onayla() {
        // Required empty public constructor
    }

    public static frg_arac_onayla newInstance() {

        return new frg_arac_onayla();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
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

        btn_01 = (Button) getView().findViewById(R.id.btn_01);
        btn_01.playSoundEffect(0);
        btn_01.setOnClickListener(new fn_btn_01());

        btn_04 = (Button) getView().findViewById(R.id.btn_04);
        btn_04.playSoundEffect(0);
        btn_04.setOnClickListener(new fn_btn_04());

        btn_03 = (Button) getView().findViewById(R.id.btn_03);
        btn_03.playSoundEffect(0);
        btn_03.setOnClickListener(new fn_Ara_Tartima_Yolla());
    }


    public void fn_IptalEt() {
        pDialog = new SweetAlertDialogG(getContext(), SweetAlertDialogG.NORMAL_TYPE);
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

            parametre.put("islem_id", aktif_sevk_isemri.islem_id);
            parametre.put("rota_id", aktif_sevk_isemri.rota_id);
            parametre.put("_zsunucu_ip_adresi", _ayarsunucuip);
            parametre.put("_zaktif_alt_tesis", _ayaraktifalttesis);
            parametre.put("_zaktif_tesis", _ayaraktiftesis);
            parametre.put("_zkullaniciadi", _zkullaniciadi);
            parametre.put("_zsifre", _zsifre);


        } catch (JSONException ex) {
            Genel.printStackTrace(ex,getContext());
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

                            if (_Yanit._zSonuc.equals("0")) {
                                pDialog.changeAlertType(SweetAlertDialogG.ERROR_TYPE);
                                pDialog.setTitle("HATA");
                                pDialog.setContentTextSize(20);
                                pDialog.setContentText(_Yanit._zHataAciklama);
                                pDialog.findViewById(R.id.confirm_button).setVisibility(View.VISIBLE);
                            } else {
                                if (_Yanit._zDurum.equals("1")) {
                                    try {
                                        if (pDialog.isShowing() == true) {
                                            pDialog.hide();
                                        }
                                    } catch (Exception ex) {
                                        Genel.printStackTrace(ex,getContext());
                                    }

                                    frg_sevkiyat_menu_panel fragmentyeni = new frg_sevkiyat_menu_panel();
                                    FragmentManager fragmentManager = getFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_sevkiyat_menu_panel").addToBackStack(null);
                                    fragmentTransaction.commit();
                                }
                            }

                        } catch (JsonMappingException ex2) {
                            Genel.printStackTrace(ex2,getContext());
                            Toast.makeText(getContext(), "error =" + ex2.toString(), Toast.LENGTH_SHORT).show();
                        } catch (JsonProcessingException ex1) {
                            Genel.printStackTrace(ex1,getContext());
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

    private void fn_AyarlariYukle() {
        ((GirisSayfasi) getActivity()).fn_ModRFID();

        _ayarbaglantituru = _myIslem.fn_baglanti_turu();
        _ayarsunucuip = _myIslem.fn_sunucu_ip();
        _ayaraktifkullanici = _myIslem.fn_aktif_kullanici();
        _ayaraktifdepo = _myIslem.fn_aktif_depo();
        _ayaraktifalttesis = _myIslem.fn_aktif_alt_tesis();
        _ayaraktiftesis = _myIslem.fn_aktif_tesis();
        _ayaraktifsunucu = _myIslem.fn_aktif_sunucu();
        _ayaraktifisletmeeslesme = _myIslem.fn_isletmeeslesme();

        if (_ayarbaglantituru.equals("wifi")) {
            _EkleAracAktivasyonuUrl = "http://" + _ayarsunucuip + ":" + _zportWifi + "/api/EkleAracAktivasyonu";
            _UpdateAracAktivasyonuUrl = "http://" + _ayarsunucuip + ":" + _zportWifi + "/api/UpdateAracAktivasyonu";
            _AracKapatma = "http://" + _ayarsunucuip + ":" + _zportWifi + "/api/sec_sevk_miktar";
            _AraTartim = "http://" + _ayarsunucuip + ":" + _zportWifi + "/api/sec_sevk_miktar";
            _AraTartim_01 = "http://" + _ayarsunucuip + ":" + _zportWifi + "/api/parsiyel_kontrol_01";
            _IslemTamam = "http://" + _ayarsunucuip + ":" + _zportWifi + "/api/sevkiyatKapat";
            _OnlineGuncelleSevkHareket = "http://" + _ayarsunucuip + ":" + _zportWifi + "/api/guncelle_sevk_hareket";
            _OnlineUrlTartimIptal = "http://" + _ayarsunucuip + ":" + _zportWifi + "/api/sevkiyatIptal";
            _OnlineUrlget_yukleme_palet_sayisi_miktar = "http://" + _ayarsunucuip + ":" + _zportWifi + "/api/get_yukleme_palet_sayisi_miktar";
            _SevkiyatDevam = "http://" + _ayarsunucuip + ":" + _zportWifi + "/api/sevkiyatDevam";
        } else {
            _EkleAracAktivasyonuUrl = "http://" + _ipAdresi3G + ":" + _zport3G + "/api/EkleAracAktivasyonu";
            _UpdateAracAktivasyonuUrl = "http://" + _ipAdresi3G + ":" + _zport3G + "/api/UpdateAracAktivasyonu";
            _AracKapatma = "http://" + _ipAdresi3G + ":" + _zport3G + "/api/sec_sevk_miktar";
            _AraTartim = "http://" + _ipAdresi3G + ":" + _zport3G + "/api/sec_sevk_miktar";
            _AraTartim_01 = "http://" + _ipAdresi3G + ":" + _zport3G + "/api/parsiyel_kontrol_01";
            _IslemTamam = "http://" + _ipAdresi3G + ":" + _zport3G + "/api/sevkiyatKapat";
            _OnlineUrlTartimIptal = "http://" + _ipAdresi3G + ":" + _zport3G + "/api/sevkiyatIptal";
            _OnlineGuncelleSevkHareket = "http://" + _ayarsunucuip + ":" + _zportWifi + "/api/guncelle_sevk_hareket";
            _OnlineUrlget_yukleme_palet_sayisi_miktar = "http://" + _ipAdresi3G + ":" + _zport3G + "/api/get_yukleme_palet_sayisi_miktar";
            _SevkiyatDevam = "http://" + _ipAdresi3G + ":" + _zport3G + "/api/sevkiyatDevam";
        }

        if(_ayarbaglantituru.equals("wifi"))
        {
            _OnlineUrlp = "http://"+_ayarsunucuip+":"+_zportWifi+"/";
        }
        else
        {
            _OnlineUrlp = "http:/"+_ipAdresi3G+":"+_zport3G+"/";
        }
        persos = new Persos(_OnlineUrlp,getContext());

    }

    public void fn_senddata(Sevkiyat_isemri v_aktif_sevk_isemri) {
        this.aktif_sevk_isemri = v_aktif_sevk_isemri;
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
            Genel.lockButtonClick(view,getActivity());
            Boolean _bDevam = true;

            String konteyner_turu = "";
            String kont_kodu = "";
            String islem_id = "";
            String depo_kodu = "";
            String urun_kodu = "";
            String isemri_id = "";
            String kod_sap = "";
            String rota_id = "";
            String id_aracisemri = "";
            String isemri_detay_id = "";
            String arac_kodu = "";
            String aktif_sunucu = "";
            String aktif_kullanici = "";


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
                    pDialog = new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE);
                    pDialog.setTitleText("ARAÇ TANIMI EKSİK");
                    pDialog.setContentText("KONTEYNER TANIMLAMA VE EŞLEŞTİRME İŞLEMİ YAPMADAN BU İŞLEMİ GERÇEKLEŞTİREMEZSİNİZ.");
                    pDialog.show();

                    _bDevam = false;
                    // Program.giveHataMesaji("ARAÇ TANIMI EKSİK","KONTEYNER TANIMLAMA VE EŞLEŞTİRME İŞLEMİ YAPMADAN BU İŞLEMİ GERÇEKLEŞTİREMEZSİNİZ.","");
                    return;
                }
            } catch (Exception ex) {
                Genel.printStackTrace(ex,getContext());
            }

            if (_bDevam == true) {

                pDialog = new SweetAlertDialogG(getContext(), SweetAlertDialogG.PROGRESS_TYPE);
                pDialog.setTitleText("KONTROL");
                pDialog.setContentText("Kontrol ediliyor Lütfen bekleyiniz.");
                //pDialog.setContentText(_TempEpc);
                pDialog.setCancelable(false);
                pDialog.show();
                pDialog.findViewById(R.id.confirm_button).setVisibility(View.GONE);

                RequestQueue queue = Volley.newRequestQueue(getContext());
                JSONObject parametre = new JSONObject();


                if (islem_id.equals("")) {
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
                    } catch (JSONException ex) {
                        Genel.printStackTrace(ex,getContext());
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


                                        if (_zSonuc.equals("0")) {
                                            pDialog.changeAlertType(SweetAlertDialogG.ERROR_TYPE);
                                            pDialog.setTitle("HATA");
                                            pDialog.setContentText(_zHataAciklama);
                                            pDialog.findViewById(R.id.confirm_button).setVisibility(View.VISIBLE);
                                        } else {
                                            pDialog.hide();

                                            new SweetAlertDialogG(getContext(), SweetAlertDialogG.SUCCESS_TYPE)
                                                    .setTitleText("TAMAMLANDI")
                                                    .setContentText("İşlem onaylandı.")
                                                    .setConfirmText("TAMAM")
                                                    .showCancelButton(false)
                                                    .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                                        @Override
                                                        public void onClick(SweetAlertDialogG sDialog) {
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
                                        Genel.printStackTrace(ex,getContext());
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

                } else {
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

                    } catch (JSONException ex) {
                        Genel.printStackTrace(ex,getContext());
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


                                        if (_zSonuc.equals("0")) {
                                            pDialog.changeAlertType(SweetAlertDialogG.ERROR_TYPE);
                                            pDialog.setTitle("HATA");
                                            pDialog.setContentText(_zHataAciklama);
                                            pDialog.findViewById(R.id.confirm_button).setVisibility(View.VISIBLE);
                                        } else {
                                            pDialog.hide();

                                            new SweetAlertDialogG(getContext(), SweetAlertDialogG.SUCCESS_TYPE)
                                                    .setTitleText("ONAY")
                                                    .setContentText("İşlem onaylandı.")
                                                    .setCancelText("Hayır")
                                                    .setConfirmText("TAMAM")
                                                    .showCancelButton(false)
                                                    .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                                        @Override
                                                        public void onClick(SweetAlertDialogG sDialog) {
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
                                        Genel.printStackTrace(ex,getContext());
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
        public void onClick(View view) {
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
            Genel.lockButtonClick(view,getActivity());
            if (aktif_sevk_isemri.islem_id.equals("")) {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentText("Araç henüz açılmadığı için kapatamazsınız. Lütfen önce aracı açınız")
                        .setConfirmText("TAMAM")
                        .showCancelButton(false)
                        .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialogG sDialog) {
                                sDialog.dismissWithAnimation();
                                sDialog.hide();
                            }
                        })
                        .show();

            } else {

                pDialog = new SweetAlertDialogG(getContext(), SweetAlertDialogG.NORMAL_TYPE);
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
                } catch (JSONException ex) {
                    Genel.printStackTrace(ex,getContext());
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
                                        pDialog.changeAlertType(SweetAlertDialogG.ERROR_TYPE);
                                        pDialog.setTitle("HATA");
                                        String hataText=_Yanit._zHataAciklama==null? "Servis Hatası!" : _Yanit._zHataAciklama;
                                        pDialog.setContentText(hataText);
                                        pDialog.findViewById(R.id.confirm_button).setVisibility(View.VISIBLE);
                                    } else {
                                        if (_Yanit._zSoruSor.equals("1")) {
                                            //
                                            pDialog.changeAlertType(SweetAlertDialogG.WARNING_TYPE);
                                            pDialog.setTitle("UYARI");
                                            pDialog.setContentText("ARACA YÜKLEME YAPILMAMIŞ DURUMDA. BU ARACIN KAYDINI İPTAL ETMEK İSTEDİĞİNİZDEN EMİN MİSİNİZ?");
                                            pDialog.findViewById(R.id.confirm_button).setVisibility(View.VISIBLE);
                                            pDialog.showCancelButton(true);
                                            pDialog.setCancelText("İPTAL");
                                            pDialog.setConfirmText("KAPAT");
                                            pDialog.setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialogG sDialog) {
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

                                            pDialog.changeAlertType(SweetAlertDialogG.WARNING_TYPE);
                                            pDialog.setContentText(_Yazi);
                                            pDialog.showCancelButton(true);
                                            pDialog.setCancelText("İptal Et");
                                            pDialog.setConfirmText("ARACI KAPAT");
                                            pDialog.findViewById(R.id.confirm_button).setVisibility(View.VISIBLE);
                                            pDialog.setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialogG sDialog) {
                                                    sDialog.dismissWithAnimation();

                                                    /*BEGIN------>*/

                                                    fn_AraciKapat();

                                                    /*<--------END*/
                                                }
                                            });


                                        }
                                    }


                                } catch (JsonMappingException e) {
                                    pDialog.changeAlertType(SweetAlertDialogG.ERROR_TYPE);
                                    pDialog.setTitle("HATA");
                                    pDialog.setContentText(e.toString());
                                    pDialog.findViewById(R.id.confirm_button).setVisibility(View.VISIBLE);
                                    e.printStackTrace();
                                } catch (JsonProcessingException e) {
                                    pDialog.changeAlertType(SweetAlertDialogG.ERROR_TYPE);
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

    private void fn_AraciKapat() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.hide();
        }

        pDialog = new SweetAlertDialogG(getContext(), SweetAlertDialogG.PROGRESS_TYPE);
        pDialog.setTitleText("YÜKLENİYOR");
        pDialog.setContentText("Araç Kapatılıyor. Lütfen Bekleyiniz");
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
        } catch (Exception ex) {
            Genel.printStackTrace(ex,getContext());
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

                            if (_Yanit._zSonuc.equals("0")) {
                                pDialog.changeAlertType(SweetAlertDialogG.ERROR_TYPE);
                                pDialog.setTitle("HATA");
                                pDialog.setContentText("" + _Yanit._zHataAciklama);
                                pDialog.findViewById(R.id.confirm_button).setVisibility(View.VISIBLE);
                            } else {

                                pDialog.hide();

                                new SweetAlertDialogG(getContext(), SweetAlertDialogG.SUCCESS_TYPE)
                                        .setTitleText("TAMAMLANDI")
                                        .setContentText("İşlem başarı ile tamamlanmıştır.")
                                        .setConfirmText("TAMAM")
                                        .showCancelButton(false)
                                        .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialogG sDialog) {
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


                        } catch (JsonMappingException exxx) {
                            Genel.printStackTrace(exxx,getContext());
                        } catch (JsonProcessingException e) {
                            Genel.printStackTrace(e,getContext());
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

    private void fn_Arac_İptalOnConfirm() {

        request_sevkiyat_isemri v_Gelen = new request_sevkiyat_isemri();
        v_Gelen.set_zaktif_alt_tesis(_ayaraktifalttesis);
        v_Gelen.set_zaktif_tesis(_ayaraktiftesis);
        v_Gelen.set_zkullaniciadi(_zkullaniciadi);
        v_Gelen.set_zsifre(_zsifre);
        v_Gelen.set_zsunucu_ip_adresi(_ayarsunucuip);
        //v_Gelen.set_zsurum(_sbtVerisyon);
        v_Gelen.setAktif_kullanici(_ayaraktifkullanici);
        v_Gelen.setAktif_sunucu(_ayaraktifsunucu);
        v_Gelen.set_sevkiyat_ismeri(aktif_sevk_isemri);

        Genel.showProgressDialog(getContext());
        Boolean result = persos.fn_sevkiyatIptal(v_Gelen);
        Genel.dismissProgressDialog();

        if (result != null && result != false) {
            frg_sevkiyat_menu_panel fragmentyeni = new frg_sevkiyat_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_sevkiyat_menu_panel");
            fragmentTransaction.commit();
        } else {

            new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                    .setTitleText("HATA")
                    .setContentTextSize(25)
                    .setContentText("SEVKİYAT İPTAL İŞLEMİ GERÇEKLEŞTİRİLEMEDİ.")
                    .showCancelButton(false)
                    .show();
            return;
        }

        //Program.setPage(new Sevkiyat_Islemleri.Sevkiyat_Menu_Panel.Sevkiyat_Menu_Panel());

    }

    private void fn_Ara_Tartim_IslemiOnConfirm() {

        request_sevkiyat_isemri v_Gelen = new request_sevkiyat_isemri();
        v_Gelen.set_zaktif_alt_tesis(_ayaraktifalttesis);
        v_Gelen.set_zaktif_tesis(_ayaraktiftesis);
        v_Gelen.set_zkullaniciadi(_zkullaniciadi);
        v_Gelen.set_zsifre(_zsifre);
        v_Gelen.set_zsunucu_ip_adresi(_ayarsunucuip);
        //v_Gelen.set_zsurum(_sbtVerisyon);
        v_Gelen.setAktif_kullanici(_ayaraktifkullanici);
        v_Gelen.setAktif_sunucu(_ayaraktifsunucu);
        v_Gelen.set_sevkiyat_ismeri(aktif_sevk_isemri);

        Genel.showProgressDialog(getContext());
        Boolean islem_sonucu = persos.fn_sevkiyatDevam(v_Gelen);
        Genel.dismissProgressDialog();
        if (islem_sonucu) {
            new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                    .setTitleText("İşlem Onayı")
                    .setContentText("İşlem başarı ile tamamlanmıştır.")
                    .setContentTextSize(20)
                    .setConfirmText("TAMAM")
                    .showCancelButton(false)
                    .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialogG sDialog) {

                            frg_sevkiyat_menu_panel fragmentyeni = new frg_sevkiyat_menu_panel();
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_sevkiyat_menu_panel");
                            fragmentTransaction.commit();

                            sDialog.dismissWithAnimation();
                        }
                    })
                    .show();

            return;

        } else {
            new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                    .setTitleText("İşlem Başarısız")
                    .setContentTextSize(25)
                    .setContentText("Kayıt yapılamadı.\r\n Kayıt yapılamadı.")
                    .showCancelButton(false)
                    .show();
            return;


        }


    }

    private void fn_Ara_Tartima_YollaIslem() {

        try {
            if (aktif_sevk_isemri.islem_id.equals("")) {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("ARAÇ TANIMI EKSİK")
                        .setContentTextSize(25)
                        .setContentText("SEVKİYAT DETAYLARINI TAMAMLAMADAN YAPMADAN BU İŞLEMİ GERÇEKLEŞTİREMEZSİNİZ.")
                        .showCancelButton(false)
                        .show();
                return;

            }

            request_string v_Gelen = new request_string();
            v_Gelen.set_zaktif_alt_tesis(_ayaraktifalttesis);
            v_Gelen.set_zaktif_tesis(_ayaraktiftesis);
            v_Gelen.set_zkullaniciadi(_zkullaniciadi);
            v_Gelen.set_zsifre(_zsifre);
            v_Gelen.set_zsunucu_ip_adresi(_ayarsunucuip);
            v_Gelen.setAktif_kullanici(_ayaraktifkullanici);
            v_Gelen.setAktif_sunucu(_ayaraktifsunucu);
            v_Gelen.set_value(aktif_sevk_isemri.islem_id);
            //Urun_tag tag = persos.fn_secEtiket(v_Gelen);
            Genel.showProgressDialog(getContext());
            List<String> miktarlar = persos.fn_sec_sevk_miktar(v_Gelen);
            Genel.dismissProgressDialog();
            if (miktarlar.get(0).equals("0")) {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.PROGRESS_TYPE)
                        .setTitleText("YÜKLEME MİKTARI UYARISI")
                        .setContentText("ARACA YÜKLEME YAPILMAMIŞ DURUMDA. BU ARACIN KAYDINI İPTAL ETMEK İSTEDİĞİNİZDEN EMİN MİSİNİZ?")
                        .setContentTextSize(20)
                        .setConfirmText("EVET")
                        .setCancelText("HAYIR")
                        .showCancelButton(true)
                        .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialogG sDialog) {
                                fn_Arac_İptalOnConfirm();
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();

            }

            request_guncelle_sevk_hareket Param = new request_guncelle_sevk_hareket();
            Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
            Param.set_zaktif_tesis(_ayaraktiftesis);
            Param.set_zkullaniciadi(_zkullaniciadi);
            Param.set_zsifre(_zsifre);
            Param.set_zsunucu_ip_adresi(_ayarsunucuip);
            Param.setAktif_kullanici(_ayaraktifkullanici);
            Param.setAktif_sunucu(_ayaraktifsunucu);
            //Param.set_value(aktif_sevk_isemri.islem_id);
            Param.set_hareket(aktif_sevk_isemri.islem_id);
            Param.set_miktarlar(miktarlar);

            Genel.showProgressDialog(getContext());
            Boolean result = persos.fn_guncelle_sevk_hareket(Param);
            Genel.dismissProgressDialog();

            if (result == false || result == null) {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("Miktar Uyarısı")
                        .setContentTextSize(25)
                        .setContentText("Yükleme miktarı güncellenemedi.")
                        .showCancelButton(false)
                        .show();
                return;

            }
            request_string Param2 = new request_string();
            Param2.set_zaktif_alt_tesis(_ayaraktifalttesis);
            Param2.set_zaktif_tesis(_ayaraktiftesis);
            Param2.set_zkullaniciadi(_zkullaniciadi);
            Param2.set_zsifre(_zsifre);
            Param2.set_zsunucu_ip_adresi(_ayarsunucuip);
            Param2.setAktif_kullanici(_ayaraktifkullanici);
            Param2.setAktif_sunucu(_ayaraktifsunucu);
            Param2.set_value(aktif_sevk_isemri.isemri_detay_id);

            Genel.showProgressDialog(getContext());
            List<String> sevk_miktarları = persos.fn_get_yukleme_palet_sayisi_miktar(Param2);
            Genel.dismissProgressDialog();

            if (sevk_miktarları.size() > 0) {
                int kalan_miktar = Integer.parseInt(sevk_miktarları.get(0));
                int kalan_palet = Integer.parseInt(sevk_miktarları.get(1));

                if (kalan_miktar < 0) {
                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                            .setTitleText("Miktar Uyarısı")
                            .setContentTextSize(25)
                            .setContentText("İş emrinden fazla miktarda ürün yüklemesi yapılamaz.")
                            .showCancelButton(false)
                            .show();
                    return;

                }
            } else {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("Onay Hatası")
                        .setContentTextSize(25)
                        .setContentText("Kayıt yapılamadı.Toplam miktar detayına ulaşılamadı")
                        .showCancelButton(false)
                        .show();
                return;

            }


            try {
                if (!aktif_sevk_isemri.konteyner_turu.equals("") && aktif_sevk_isemri.kont_rfid.equals("")) {
                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                            .setTitleText("ARAÇ TANIMI EKSİK")
                            .setContentTextSize(25)
                            .setContentText("KONTEYNER TANIMLAMA VE EŞLEŞTİRME İŞLEMİ YAPMADAN BU İŞLEMİ GERÇEKLEŞTİREMEZSİNİZ.")
                            .showCancelButton(false)
                            .show();
                    return;

                }
            } catch (Exception ex) {
                Genel.printStackTrace(ex,getContext());
            }


            try {

                new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                        .setTitleText("ARA TARTIM UYARISI")
                        .setContentText("PLAKA : '" + aktif_sevk_isemri.arac_plaka + "'"
                                + "\r\nARAÇ İÇİNDE ;"
                                + "\r\nPALET SAYISI: " + miktarlar.get(1)
                                + "\r\nMİKTAR: " + miktarlar.get(0) + " KG"
                                + "\r\nÜRÜN BULUNMAKTADIR !!"
                                + "\r\nAracı 'ARA TARTIM' işlemine devam etmek istiyor musunuz ? ")
                        .setContentTextSize(20)
                        .setConfirmText("EVET")
                        .setCancelText("HAYIR")
                        .showCancelButton(true)
                        .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialogG sDialog) {
                                fn_Ara_Tartim_IslemiOnConfirm();
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();


            } catch (Exception ex) {
                Genel.printStackTrace(ex,getContext());
            }

        } catch (Exception ex) {
            Genel.printStackTrace(ex,getContext());
            new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                    .setTitleText("Onay Hatası")
                    .setContentTextSize(25)
                    .setContentText("Kayıt yapılamadı.\r\n Toplam miktar exception")
                    .showCancelButton(false)
                    .show();
            return;

        }

    }

    private class fn_Ara_Tartima_Yolla implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Genel.lockButtonClick(view,getActivity());
            fn_Ara_Tartima_YollaIslem();
//            if (aktif_sevk_isemri.islem_id.equals("")) {
//                SweetAlertDialogG pDialogUyari = new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE);
//                pDialogUyari.setTitleText("ARAÇ TANIMI EKSİK");
//                pDialogUyari.setContentText("SEVKİYAT DETAYLARINI TAMAMLAMADAN YAPMADAN BU İŞLEMİ GERÇEKLEŞTİREMEZSİNİZ.");
//                pDialogUyari.show();
//            } else {
//                pDialog = new SweetAlertDialogG(getContext(), SweetAlertDialogG.NORMAL_TYPE);
//                pDialog.setTitleText("YÜKLENİYOR");
//                pDialog.setContentText(aktif_sevk_isemri.arac_plaka + " kontrol ediliyor Lütfen bekleyiniz.");
//                //pDialog.setContentText(_TempEpc);
//                pDialog.setCancelable(false);
//                pDialog.show();
//                pDialog.findViewById(R.id.confirm_button).setVisibility(View.GONE);
//
//                JSONObject parametre = new JSONObject();
//
//                try {
//                    parametre.put("_zkullaniciadi", _zkullaniciadi);
//                    parametre.put("_zsifre", _zsifre);
//                    parametre.put("_zsunucu_ip_adresi", _ayarsunucuip);
//                    parametre.put("_zaktif_alt_tesis", _ayaraktifalttesis);
//                    parametre.put("_zaktif_tesis", _ayaraktiftesis);
//                    parametre.put("aktif_sunucu", _ayaraktifsunucu);
//                    parametre.put("aktif_kullanici", _ayaraktifkullanici);
//
//                    parametre.put("sevk_hareket_id", aktif_sevk_isemri.islem_id);
//
//                } catch (JSONException error) {
//                    error.printStackTrace();
//                }
//
//                RequestQueue mRequestQueue;
//
//                Cache cache = new DiskBasedCache(getContext().getCacheDir(), 2 * 1024 * 1024); // 1MB cap
//                Network network = new BasicNetwork(new HurlStack());
//
//                RequestQueue queue = new RequestQueue(cache, network);
//                queue.start();
//
//                JsonObjectRequest request = new JsonObjectRequest(
//                        Request.Method.POST,
//                        _AraTartim_01,
//                        parametre,
//                        new Response.Listener<JSONObject>() {
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                try {
//                                    ObjectMapper objectMapper = new ObjectMapper();
//
//                                    _YanitAraTartim = objectMapper.readValue(response.toString(), viewparsiyel_kontrol_01.class);
//
//                                    if (_YanitAraTartim._zSonuc.equals("0")) {
//                                        pDialog.changeAlertType(SweetAlertDialogG.ERROR_TYPE);
//                                        pDialog.setTitle("HATA");
//                                        pDialog.setContentTextSize(20);
//                                        pDialog.setContentText(_YanitAraTartim._zHataAciklama);
//                                        pDialog.findViewById(R.id.confirm_button).setVisibility(View.VISIBLE);
//                                    } else {
//                                        try {
//                                            if (pDialog != null && pDialog.isShowing()) {
//                                                pDialog.hide();
//                                            }
//                                        } catch (Exception ex) {
//
//                                        }
//
//                                        /*BEGIN ----------->*/
//
//                                        if (_YanitAraTartim._deger01.equals("0")) {
//                                            new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
//                                                    .setTitleText("YÜKLEME MİKTARI UYARISI")
//                                                    .setContentText("ARACA YÜKLEME YAPILMAMIŞ DURUMDA. BU ARACIN KAYDINI İPTAL ETMEK İSTEDİĞİNİZDEN EMİN MİSİNİZ?")
//                                                    .setCancelText("Hayır")
//                                                    .setContentTextSize(20)
//                                                    .setConfirmText("EVET")
//                                                    .showCancelButton(true)
//                                                    .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
//                                                        @Override
//                                                        public void onClick(SweetAlertDialogG sDialog) {
//                                                            sDialog.dismissWithAnimation();
//
//                                                            sDialog.hide();
//
//                                                            fn_IptalEt();
//                                                        }
//                                                    })
//                                                    .setCancelClickListener(new SweetAlertDialogG.OnSweetClickListener() {
//                                                        @Override
//                                                        public void onClick(SweetAlertDialogG sDialog) {
//                                                            sDialog.cancel();
//                                                        }
//                                                    })
//                                                    .show();
//                                        } else {
//                                            fn_guncelle_sevk_hareket();
//                                        }
//
//                                        /*<--------------END*/
//                                    }
//
//
//                                } catch (JsonMappingException e) {
//                                    pDialog.changeAlertType(SweetAlertDialogG.ERROR_TYPE);
//                                    pDialog.setTitle("HATA");
//                                    pDialog.setContentText(e.toString());
//                                    pDialog.findViewById(R.id.confirm_button).setVisibility(View.VISIBLE);
//                                    e.printStackTrace();
//                                } catch (JsonProcessingException e) {
//                                    pDialog.changeAlertType(SweetAlertDialogG.ERROR_TYPE);
//                                    pDialog.setTitle("HATA");
//                                    pDialog.setContentText(e.toString());
//                                    pDialog.findViewById(R.id.confirm_button).setVisibility(View.VISIBLE);
//                                    e.printStackTrace();
//                                }
//
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                pDialog.hide();
//                                Toast.makeText(getContext(), "error =" + error.toString(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//
//                );
//                int socketTimeout = 30000;//30 seconds - change to what you want
//                RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//                request.setRetryPolicy(policy);
//                queue.add(request);
//            }
        }


        private void fn_guncelle_sevk_hareket() {
            pDialog = new SweetAlertDialogG(getContext(), SweetAlertDialogG.NORMAL_TYPE);
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


            } catch (JSONException ex) {
                Genel.printStackTrace(ex,getContext());
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

                                String _Gelen = response.toString();

                                int _Sayi = 1;


                                Viewguncelle_sevk_hareket _Yanit = objectMapper.readValue(response.toString(), Viewguncelle_sevk_hareket.class);


                                if (_Yanit._zSonuc.equals("0")) {
                                    pDialog.changeAlertType(SweetAlertDialogG.ERROR_TYPE);
                                    pDialog.setTitle("HATA");
                                    pDialog.setContentText(_Yanit._zHataAciklama);
                                    pDialog.findViewById(R.id.confirm_button).setVisibility(View.VISIBLE);
                                } else {
                                    try {
                                        if (pDialog.isShowing()) {
                                            pDialog.hide();
                                        }
                                    } catch (Exception ex) {
                                        Genel.printStackTrace(ex,getContext());
                                    }

                                    fn_get_yukleme_palet_sayisi_miktar();
                                }

                            } catch (JsonMappingException ex2) {
                                Genel.printStackTrace(ex2,getContext());
                                Toast.makeText(getContext(), "error =" + ex2.toString(), Toast.LENGTH_SHORT).show();
                            } catch (JsonProcessingException ex1) {
                                Genel.printStackTrace(ex1,getContext());
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


        private void fn_get_yukleme_palet_sayisi_miktar() {

            pDialog = new SweetAlertDialogG(getContext(), SweetAlertDialogG.NORMAL_TYPE);
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

                parametre.put("_zdetay_id", aktif_sevk_isemri.isemri_detay_id);


            } catch (JSONException ex) {
                Genel.printStackTrace(ex,getContext());
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

                                if (_Yanit._zSonuc.equals("0")) {
                                    pDialog.changeAlertType(SweetAlertDialogG.ERROR_TYPE);
                                    pDialog.setTitle("HATA");
                                    pDialog.setContentText(_Yanit._zHataAciklama);
                                    pDialog.findViewById(R.id.confirm_button).setVisibility(View.VISIBLE);
                                } else {
                                    try {
                                        try {
                                            if (pDialog.isShowing() == true) {
                                                pDialog.hide();
                                            }
                                        } catch (Exception ex) {
                                            Genel.printStackTrace(ex,getContext());
                                        }


                                        if (!aktif_sevk_isemri.konteyner_turu.equals("") && aktif_sevk_isemri.kont_rfid.equals("")) {
                                            new SweetAlertDialogG(getContext(), SweetAlertDialogG.SUCCESS_TYPE)
                                                    .setTitleText("HATA")
                                                    .setContentText("ARAÇ TANIMI EKSİK.KONTEYNER TANIMLAMA VE EŞLEŞTİRME İŞLEMİ YAPMADAN BU İŞLEMİ GERÇEKLEŞTİREMEZSİNİZ")
                                                    .setConfirmText("TAMAM")
                                                    .showCancelButton(false)
                                                    .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                                        @Override
                                                        public void onClick(SweetAlertDialogG sDialog) {
                                                            sDialog.dismissWithAnimation();

                                                            sDialog.hide();
                                                        }
                                                    })
                                                    .show();
                                        } else {
                                            fn_AraTartimDevam();
                                        }


                                    } catch (Exception ex) {
                                        Genel.printStackTrace(ex,getContext());
                                    }
                                }

                            } catch (JsonMappingException ex2) {
                                Genel.printStackTrace(ex2,getContext());
                                Toast.makeText(getContext(), "error =" + ex2.toString(), Toast.LENGTH_SHORT).show();
                            } catch (JsonProcessingException ex1) {
                                Genel.printStackTrace(ex1,getContext());
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


        new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                .setTitleText("ARA TARTIM ONAYI")
                .setContentText(_Aciklama)
                .setCancelText("İptal")
                .setConfirmText("TAMAM")
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialogG sDialog) {
                        sDialog.dismissWithAnimation();

                        sDialog.hide();

                        fn_sevkiyatDevam();
                    }
                })
                .setCancelClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialogG sDialog) {
                        sDialog.cancel();
                    }
                })
                .show();
    }

    private void fn_sevkiyatDevam() {


        pDialog = new SweetAlertDialogG(getContext(), SweetAlertDialogG.NORMAL_TYPE);
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


        } catch (JSONException ex) {

            Genel.printStackTrace(ex,getContext());
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

                            if (_Yanit._zSonuc.equals("0")) {
                                pDialog.changeAlertType(SweetAlertDialogG.ERROR_TYPE);
                                pDialog.setTitle("HATA");
                                pDialog.setContentTextSize(20);
                                pDialog.setContentText(_Yanit._zHataAciklama);
                                pDialog.findViewById(R.id.confirm_button).setVisibility(View.VISIBLE);
                            } else {
                                pDialog.changeAlertType(SweetAlertDialogG.SUCCESS_TYPE);
                                pDialog.setTitle("TAMAM");
                                pDialog.setContentTextSize(20);
                                pDialog.setContentText("İşlem başarı ile tamamlanmıştır.");
                                pDialog.findViewById(R.id.confirm_button).setVisibility(View.VISIBLE);

                                pDialog.setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialogG sDialog) {
                                        sDialog.dismissWithAnimation();

                                        sDialog.hide();

                                        frg_sevkiyat_menu_panel fragmentyeni = new frg_sevkiyat_menu_panel();
                                        FragmentManager fragmentManager = getFragmentManager();
                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                        fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_sevkiyat_menu_panel");
                                        fragmentTransaction.commit();
                                    }
                                });
                            }

                        } catch (JsonMappingException ex2) {
                            Genel.printStackTrace(ex2,getContext());
                            Toast.makeText(getContext(), "error =" + ex2.toString(), Toast.LENGTH_SHORT).show();
                        } catch (JsonProcessingException ex1) {
                            Genel.printStackTrace(ex1,getContext());
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
