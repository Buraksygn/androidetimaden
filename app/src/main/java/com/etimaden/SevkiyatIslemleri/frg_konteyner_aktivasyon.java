package com.etimaden.SevkiyatIslemleri;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.SoundEffectConstants;
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
import com.etimaden.cResponseResult.ViewsecKonteyner;
import com.etimaden.cResponseResult.viewsevkiyatKapat;
import com.etimaden.genel.Genel;
import com.etimaden.genel.SweetAlertDialogG;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.Arac;
import com.etimaden.request.request_sevkiyat_isemri_sevkiyat_isemri;
import com.etimaden.request.request_string;
import com.etimaden.response.sevkiyat_islemleri.View_arac;
import com.etimaden.response.sevkiyat_islemleri.View_arac2;
import com.etimaden.ugr_demo.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;


import static com.etimaden.cSabitDegerler._ipAdresi3G;
import static com.etimaden.cSabitDegerler._sbtVerisyon;
import static com.etimaden.cSabitDegerler._zkullaniciadi;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class frg_konteyner_aktivasyon extends Fragment {

    SweetAlertDialogG pDialog;

    String arac_plaka="";
    String arac_kod="";
    String _sec_konteyner="";
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
    String _OnlineUrlP = "";
    Persos persos;


    Button _btngeri;

    public Sevkiyat_isemri aktif_sevk_isemri ;

    public String _sec_aracUrl = "";
    public String _sevkiyat_konteyner_ayirUrl = "";
    public String _OnlineUrl = "";
    String v_alt_rota="";
    String v_isKonteynerAvailable="2";

    boolean _IslemVar = false;

    public frg_konteyner_aktivasyon() {
        // Required empty public constructor
    }

    public static frg_konteyner_aktivasyon newInstance() {

        return new frg_konteyner_aktivasyon();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_konteyner_aktivasyon, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((GirisSayfasi) getActivity()).fn_ModRFID();

        _myIslem = new VeriTabani(getContext());

        fn_AyarlariYukle();

        new VeriTabani(getContext()).fn_EpcTemizle();

        ((GirisSayfasi) getActivity()).fn_ListeTemizle();
        ((GirisSayfasi) getActivity()).fn_GucAyarla(230);


        _btngeri = (Button) getView().findViewById(R.id.btncikis);
        _btngeri.playSoundEffect(SoundEffectConstants.CLICK);
        _btngeri.setOnClickListener(new fn_Geri());
    }


    public void fn_arac_ac(String v_epc) {

        final FragmentManager fragmentManager=getFragmentManager();

        if (_IslemVar == false)
        {
            _IslemVar = true;

            //((GirisSayfasi)getActivity()).fn_ListeTemizle();


            pDialog = new SweetAlertDialogG(getContext(), SweetAlertDialogG.NORMAL_TYPE);
            pDialog.setTitleText("YÜKLENİYOR");
            pDialog.setContentText(v_epc + " Kontrol ediliyor Lütfen bekleyiniz.");
            //pDialog.setContentText(_TempEpc);
            pDialog.setCancelable(false);
            pDialog.show();
            pDialog.findViewById(R.id.confirm_button).setVisibility(View.GONE);
            // mIsReading=true;

            try {

                //JSONObject jsonObj = new JSONObject(aktif_sevk_isemri);
                String kont_rfid = aktif_sevk_isemri.kont_rfid.trim();//gelmedi
                String aktif_sunucu = _ayaraktifsunucu;
                String aktif_kullanici = _ayaraktifkullanici;

                RequestQueue queue = Volley.newRequestQueue(getContext());
                JSONObject parametre = new JSONObject();

                if (_ayaraktiftesis.equals("5002"))
                {
                    if (kont_rfid.equals(v_epc))
                    {

                        try {
                            parametre.put("rfid", v_epc);
                            parametre.put("aktif_sunucu", aktif_sunucu);
                            parametre.put("aktif_kullanici", aktif_kullanici);
                            parametre.put("_zsunucu_ip_adresi", _ayarsunucuip);
                            parametre.put("_zaktif_alt_tesis", _ayaraktifalttesis);
                            parametre.put("_zaktif_tesis", _ayaraktiftesis);
                            parametre.put("_zsurum", _sbtVerisyon);
                            parametre.put("_zkullaniciadi", _zkullaniciadi);
                            parametre.put("_zsifre", _zsifre);

                            JsonObjectRequest request = new JsonObjectRequest(
                                    Request.Method.POST,
                                    _sec_aracUrl,
                                    parametre,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {
                                                // pDialog.hide();

                                                String _zSonuc = response.getString("_zSonuc");
                                                String _zHataAciklama = response.getString("_zHataAciklama");
                                                String _zAciklama = response.getString("_zAciklama");

                                                ObjectMapper objectMapper = new ObjectMapper();
                                                View_arac2 _view_arac =objectMapper.readValue(response.toString(), View_arac2.class);

                                                arac_plaka = _view_arac.get_zarac().arac_plaka;
                                                arac_kod = _view_arac.get_zarac().arac_kod;


                                                if (_zSonuc.equals("0")) {



                                                    pDialog.changeAlertType(SweetAlertDialogG.ERROR_TYPE);
                                                    pDialog.setTitle("HATA");
                                                    pDialog.setContentText("KONTEYNER KAYDI ALINAMADI TEKRAR DENEYİNİZ. KONTEYNER AYIRMA İŞLEMİ (ARAÇ SORGULAMA).");
                                                    pDialog.showCancelButton(false);
                                                    pDialog.findViewById(R.id.cancel_button).setVisibility(View.INVISIBLE);
                                                    pDialog.findViewById(R.id.confirm_button).setVisibility(View.VISIBLE);

                                                    pDialog.setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                                        @Override
                                                        public void onClick(SweetAlertDialogG sDialog)
                                                        {
                                                            sDialog.dismissWithAnimation();

                                                            sDialog.hide();

                                                            //((GirisSayfasi)getActivity()).fn_ListeTemizle();

                                                            _IslemVar = false;

                                                        }});

                                                } else
                                                {
                                                    if (pDialog != null && pDialog.isShowing()) {
                                                        pDialog.hide();
                                                    }

                                                    String _Yazi = "KONTEYNER PLAKA : " + arac_plaka + " KONTEYNER AYIRMA İŞLEMİ. KONTEYNERI AYIRMA İŞLEMİNE DEVAM ETMEK İSTEDİĞİNİZDEN EMİN MİSİNİZ?";

                                                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.SUCCESS_TYPE)
                                                            .setTitleText("KONTEYNER AYIRMA")
                                                            .setContentText(_Yazi)
                                                            .setCancelText("İptal")
                                                            .setContentTextSize(20)
                                                            .setConfirmText("TAMAM")
                                                            .showCancelButton(true)
                                                            .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                                                @Override
                                                                public void onClick(SweetAlertDialogG sDialog) {
                                                                    sDialog.dismissWithAnimation();

                                                                    //sDialog.hide();

                                                                    pDialog = new SweetAlertDialogG(getContext(), SweetAlertDialogG.NORMAL_TYPE);
                                                                    pDialog.setTitleText("YÜKLENİYOR");
                                                                    pDialog.setContentText(" Kontrol ediliyor Lütfen bekleyiniz.");
                                                                    //pDialog.setContentText(_TempEpc);
                                                                    pDialog.setCancelable(false);
                                                                  try
                                                                  {

                                                                  }catch (Exception ex)
                                                                  {

                                                                      pDialog.show();
                                                                  }


                                                                    //pDialog.findViewById(R.id.confirm_button).setVisibility(View.GONE);

                                                                    RequestQueue queue = Volley.newRequestQueue(getContext());
                                                                    JSONObject parametre = new JSONObject();

                                                                    JSONObject jsonObj = null;
                                                                    String kont_rfid = "";
                                                                    String aktif_sunucu = "";
                                                                    String aktif_kullanici = "";
                                                                    String isemri_detay_id = "";
                                                                    String isletme_esleme = "";
                                                                    String aktif_tesis = "";
                                                                    String kod_sap = "";
                                                                    String isemri_id = "";
                                                                    String kont_kodu = "";
                                                                    String _kullaniciadi = "";
                                                                    String _sifre = "";
                                                                    String hedef_isletme_esleme = "";
                                                                    String hedef_isletme_kodu = "";
                                                                    String aktif_alt_tesis = "";
                                                                    String hedef_isletme_alt_kodu = "";
                                                                    String depo_kodu = "";
                                                                    String hedef_depo = "";
                                                                    String urun_kodu = "";
                                                                    String islem_id = "";
                                                                    String rota_id = "";
                                                                    try {
                                                                        //  jsonObj = new JSONObject(aktif_sevk_isemri);
                                                                        kont_rfid = aktif_sevk_isemri.kont_rfid.trim();
                                                                        rota_id = aktif_sevk_isemri.rota_id.trim();

                                                                        islem_id = aktif_sevk_isemri.islem_id.trim();
                                                                        hedef_isletme_alt_kodu = aktif_sevk_isemri.hedef_isletme_alt_kodu.trim();
                                                                        hedef_depo = aktif_sevk_isemri.hedef_depo.trim();
                                                                        urun_kodu = aktif_sevk_isemri.urun_kodu.trim();
                                                                        depo_kodu = aktif_sevk_isemri.depo_kodu.trim();
                                                                        kont_kodu = aktif_sevk_isemri.kont_kodu.trim();
                                                                        isemri_id = aktif_sevk_isemri.isemri_id.trim();
                                                                        hedef_isletme_kodu = aktif_sevk_isemri.hedef_isletme_kodu.trim();
                                                                        hedef_isletme_esleme = aktif_sevk_isemri.hedef_isletme_esleme.trim();
                                                                        isemri_detay_id = aktif_sevk_isemri.isemri_detay_id.trim();
                                                                        isletme_esleme = aktif_sevk_isemri.isletme_esleme.trim();
                                                                        kod_sap = aktif_sevk_isemri.kod_sap.trim();
                                                                        aktif_sunucu = _ayaraktifsunucu;
                                                                        aktif_kullanici = _ayaraktifkullanici;
                                                                        aktif_tesis = _ayaraktiftesis;
                                                                        aktif_alt_tesis = _ayaraktifalttesis;
                                                                        _kullaniciadi = _zkullaniciadi;
                                                                        _sifre = _zsifre;

                                                                        parametre.put("_zkullaniciadi", _kullaniciadi);
                                                                        parametre.put("_zsifre", _sifre);
                                                                        parametre.put("isemri_detay_id", isemri_detay_id);
                                                                        parametre.put("isletme_esleme", isletme_esleme);
                                                                        parametre.put("aktif_tesis", aktif_tesis);
                                                                        parametre.put("kod_sap", kod_sap);
                                                                        parametre.put("isemri_id", isemri_id);
                                                                        parametre.put("kont_kodu", kont_kodu);
                                                                        parametre.put("arac_plaka", arac_plaka);
                                                                        parametre.put("hedef_isletme_esleme", hedef_isletme_esleme);
                                                                        parametre.put("hedef_isletme_kodu", hedef_isletme_kodu);
                                                                        parametre.put("aktif_alt_tesis", aktif_alt_tesis);
                                                                        parametre.put("hedef_isletme_alt_kodu", hedef_isletme_alt_kodu);
                                                                        parametre.put("depo_kodu", depo_kodu);
                                                                        parametre.put("hedef_depo", hedef_depo);
                                                                        parametre.put("arac_kod", arac_kod);
                                                                        parametre.put("urun_kodu", urun_kodu);
                                                                        parametre.put("islem_id", islem_id);
                                                                        parametre.put("rota_id", rota_id);
                                                                        parametre.put("_zsunucu_ip_adresi", _ayarsunucuip);
                                                                        parametre.put("aktif_sunucu", aktif_sunucu);
                                                                        parametre.put("aktif_kullanici", aktif_kullanici);
                                                                    } catch (JSONException e) {
                                                                        if (pDialog != null && pDialog.isShowing()) {
                                                                            pDialog.hide();
                                                                        }
                                                                        Genel.printStackTrace(e,getContext());
                                                                    }

                                                                    JsonObjectRequest request = new JsonObjectRequest(
                                                                            Request.Method.POST,
                                                                            _sevkiyat_konteyner_ayirUrl,
                                                                            parametre,
                                                                            new Response.Listener<JSONObject>() {
                                                                                @Override
                                                                                public void onResponse(JSONObject response) {
                                                                                    try {
                                                                                        // pDialog.hide();

                                                                                        String _zHataAciklama = response.getString("_zHataAciklama");
                                                                                        String _zAciklama = response.getString("_zAciklama");
                                                                                        String _zSonuc = response.getString("_zSonuc");


                                                                                        if (_zSonuc.equals("0")) {
                                                                                            pDialog.changeAlertType(SweetAlertDialogG.ERROR_TYPE);
                                                                                            pDialog.setTitle("HATA");
                                                                                            pDialog.setContentText("KAYIT YAPILAMADI. DAHA SONRA TEKRAR DENEYİNİZ. KONTEYNER AYIRMA İŞLEMİ(İŞLEM KAYDI OLUŞTUR).");
                                                                                            pDialog.findViewById(R.id.confirm_button).setVisibility(View.VISIBLE);
                                                                                            pDialog.findViewById(R.id.cancel_button).setVisibility(View.INVISIBLE);

                                                                                            pDialog.setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                                                                                @Override
                                                                                                public void onClick(SweetAlertDialogG sDialog)
                                                                                                {
                                                                                                    sDialog.dismissWithAnimation();

                                                                                                    sDialog.hide();

                                                                                                    //((GirisSayfasi)getActivity()).fn_ListeTemizle();

                                                                                                    _IslemVar = false;

                                                                                                }});
                                                                                        } else {
                                                                                            if (pDialog != null && pDialog.isShowing()) {
                                                                                                pDialog.hide();
                                                                                            }

                                                                                            new SweetAlertDialogG(getContext(), SweetAlertDialogG.SUCCESS_TYPE)
                                                                                                    .setTitleText("ONAY")
                                                                                                    .setContentText("İşlem onaylandı.")
                                                                                                    .setConfirmText("TAMAM")
                                                                                                    .setContentTextSize(20)
                                                                                                    .showCancelButton(false)
                                                                                                    .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                                                                                        @Override
                                                                                                        public void onClick(SweetAlertDialogG sDialog) {
                                                                                                            sDialog.dismissWithAnimation();

                                                                                                            sDialog.hide();

                                                                                                            //((GirisSayfasi)getActivity()).fn_ListeTemizle();

                                                                                                            frg_sevkiyat_menu_panel fragmentyeni = new frg_sevkiyat_menu_panel();
                                                                                                            //FragmentManager fragmentManager = getFragmentManager();
                                                                                                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                                                                            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_sevkiyat_menu_panel");
                                                                                                            fragmentTransaction.commit();
                                                                                                        }
                                                                                                    })
                                                                                                    .show();
                                                                                        }

                                                                                    } catch (JSONException ex) {
                                                                                        if (pDialog != null && pDialog.isShowing()) {
                                                                                            pDialog.hide();
                                                                                        }
                                                                                        Toast.makeText(getContext(), "error =" + ex.toString(), Toast.LENGTH_SHORT).show();
                                                                                        Genel.printStackTrace(ex,getContext());
                                                                                    }

                                                                                }
                                                                            },
                                                                            new Response.ErrorListener() {
                                                                                @Override
                                                                                public void onErrorResponse(VolleyError error) {
                                                                                    if (pDialog != null && pDialog.isShowing()) {
                                                                                        pDialog.hide();
                                                                                    }
                                                                                    Toast.makeText(getContext(), "error =" + error, Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            });
                                                                    int socketTimeout = 30000;//30 seconds - change to what you want
                                                                    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                                                                    request.setRetryPolicy(policy);
                                                                    queue.add(request);
                                                                }
                                                            })
                                                            .show();

                                                }

                                            } catch (JSONException ex) {
                                                if (pDialog != null && pDialog.isShowing()) {
                                                    pDialog.hide();
                                                }
                                                Toast.makeText(getContext(), "error =" + ex.toString(), Toast.LENGTH_SHORT).show();
                                                Genel.printStackTrace(ex,getContext());
                                            } catch (JsonMappingException ex) {
                                                Genel.printStackTrace(ex,getContext());
                                            } catch (JsonProcessingException ex) {
                                                Genel.printStackTrace(ex,getContext());
                                            }

                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            if (pDialog != null && pDialog.isShowing()) {
                                                pDialog.hide();
                                            }
                                            Toast.makeText(getContext(), "error =" + error, Toast.LENGTH_SHORT).show();
                                        }
                                    }


                            );
                            int socketTimeout = 30000;//30 seconds - change to what you want
                            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                            request.setRetryPolicy(policy);
                            queue.add(request);


                        } catch (JSONException error) {
                            if (pDialog != null && pDialog.isShowing()) {
                                pDialog.hide();
                            }
                            Genel.printStackTrace(error,getContext());
                        }

                    }
                    else{
                        if (pDialog != null && pDialog.isShowing()) {
                            pDialog.hide();
                        }
                        request_string _Param= new request_string();
                        _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
                        _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
                        _Param.set_zaktif_tesis(_ayaraktiftesis);
                        _Param.set_zsurum(_sbtVerisyon);
                        _Param.set_zkullaniciadi(_zkullaniciadi);
                        _Param.set_zsifre(_zsifre);
                        _Param.setAktif_sunucu(_ayaraktifsunucu);
                        _Param.setAktif_kullanici(_ayaraktifkullanici);

                        _Param.set_value(v_epc);

                        Genel.showProgressDialog(getContext());
                        List<Sevkiyat_isemri> result= persos.fn_sec_yerde_konteyner(_Param);
                        if(result==null){
                            result= Collections.emptyList();
                        }
                        Genel.dismissProgressDialog();

                        final List<Sevkiyat_isemri> konteyner_list=result;

                        if (konteyner_list.size() > 0)
                        {
                            new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                                    .setTitleText("KONTEYNER ONAY")
                                    .setContentText("KONTEYNER PLAKA : " + konteyner_list.get(0).arac_plaka + "\r\nKONTEYNER OKUNDU. İŞLEME DEVAM ETMEK İSTEDİĞİNİZDEN EMİN MİSİNİZ?")
                                    .setContentTextSize(20)
                                    .setConfirmText("EVET")
                                    .setCancelText("HAYIR")
                                    .showCancelButton(true)
                                    .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialogG sDialog) {
                                            sDialog.dismissWithAnimation();

                                            if (konteyner_list.get(0).islem_id.equals(""))
                                            {

                                                aktif_sevk_isemri.kont_kodu = konteyner_list.get(0).kont_kodu;
                                                new SweetAlertDialogG(getContext(), SweetAlertDialogG.SUCCESS_TYPE)
                                                        .setTitleText("ONAY")
                                                        .setContentText("İşlem onaylandı.")
                                                        .setContentTextSize(20)
                                                        .setConfirmText("TAMAM")
                                                        .showCancelButton(false)
                                                        .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                                            @Override
                                                            public void onClick(SweetAlertDialogG sDialog) {
                                                                sDialog.dismissWithAnimation();

                                                                frg_arac_bulundu fragmentyeni = new frg_arac_bulundu();
                                                                fragmentyeni.fn_senddata(aktif_sevk_isemri);
                                                                FragmentManager fragmentManager = getFragmentManager();
                                                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                                fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_arac_bulundu").addToBackStack(null);
                                                                fragmentTransaction.commit();

                                                                return;
                                                            }
                                                        }).show();
                                                return;
                                            }

                                            if (!konteyner_list.get(0).isemri_detay_id.equals(aktif_sevk_isemri.isemri_detay_id))
                                            {
                                                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                                                        .setTitleText("HATA")
                                                        .setContentTextSize(25)
                                                        .setContentText("KONTEYNER SİPARİŞİ İLE ARAÇ SİPARİŞİ UYUŞMUYOR. \r\n SİPARİŞİ KONTROL EDİNİZ..")
                                                        .showCancelButton(false)
                                                        .show();
                                                _IslemVar=false;
                                                return;
                                            }

                                            request_string _Param= new request_string();
                                            _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
                                            _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
                                            _Param.set_zaktif_tesis(_ayaraktiftesis);
                                            _Param.set_zsurum(_sbtVerisyon);
                                            _Param.set_zkullaniciadi(_zkullaniciadi);
                                            _Param.set_zsifre(_zsifre);
                                            _Param.setAktif_sunucu(_ayaraktifsunucu);
                                            _Param.setAktif_kullanici(_ayaraktifkullanici);

                                            _Param.set_value(konteyner_list.get(0).islem_id);

                                            Genel.showProgressDialog(getContext());
                                            List<String> result= persos.fn_sec_sevk_miktar(_Param);
                                            ArrayList<String> miktarlar=new ArrayList<>();
                                            if(result!=null) {
                                                miktarlar = new ArrayList<>(result);
                                            }
                                            Genel.dismissProgressDialog();

                                            if (miktarlar.get(0).equals("0"))
                                            {
                                                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                                                        .setTitleText("HATA")
                                                        .setContentTextSize(25)
                                                        .setContentText("KONTEYNER İÇERİSİNDE HERHANGİ BİR YÜKLEME BULUNMUYOR. \r\n KONTEYNER YÜKLEMESİNİ KONTROL EDİNİZ..")
                                                        .showCancelButton(false)
                                                        .show();
                                                _IslemVar=false;
                                                return;
                                            }

                                            new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                                                    .setTitleText("KONTEYNER EŞLEŞTİRME UYARISI")
                                                    .setContentText("KONTEYNER : '" + konteyner_list.get(0).arac_plaka + "'" +
                                                            "\r\nKONTEYNER İÇİNDE ;\r\nPALET SAYISI: " + miktarlar.get(1) +
                                                            "\r\nMİKTAR: " + miktarlar.get(0) + " KG\r\n ÜRÜN BULUNMAKTADIR !!" +
                                                            "\r\n Konteyner 'YÜKLEME' kaydını tamamlamak istiyor musunuz ? ")
                                                    .setContentTextSize(20)
                                                    .setConfirmText("EVET")
                                                    .setCancelText("HAYIR")
                                                    .showCancelButton(true)
                                                    .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                                        @Override
                                                        public void onClick(SweetAlertDialogG sDialog) {
                                                            sDialog.dismissWithAnimation();

                                                            request_sevkiyat_isemri_sevkiyat_isemri _Param= new request_sevkiyat_isemri_sevkiyat_isemri();
                                                            _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
                                                            _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
                                                            _Param.set_zaktif_tesis(_ayaraktiftesis);
                                                            _Param.set_zsurum(_sbtVerisyon);
                                                            _Param.set_zkullaniciadi(_zkullaniciadi);
                                                            _Param.set_zsifre(_zsifre);
                                                            _Param.setAktif_sunucu(_ayaraktifsunucu);
                                                            _Param.setAktif_kullanici(_ayaraktifkullanici);

                                                            _Param.setSevk_is(aktif_sevk_isemri);
                                                            _Param.setKonteyner(konteyner_list.get(0));

                                                            Genel.showProgressDialog(getContext());
                                                            Boolean result= persos.fn_arac_konteyner_esleme(_Param);
                                                            Genel.dismissProgressDialog();

                                                            if (result)
                                                            {
                                                                new SweetAlertDialogG(getContext(), SweetAlertDialogG.SUCCESS_TYPE)
                                                                        .setTitleText("ONAY")
                                                                        .setContentText("İşlem onaylandı.")
                                                                        .setContentTextSize(20)
                                                                        .setConfirmText("TAMAM")
                                                                        .showCancelButton(false)
                                                                        .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                                                            @Override
                                                                            public void onClick(SweetAlertDialogG sDialog) {
                                                                                sDialog.dismissWithAnimation();

                                                                                request_string _Param= new request_string();
                                                                                _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
                                                                                _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
                                                                                _Param.set_zaktif_tesis(_ayaraktiftesis);
                                                                                _Param.set_zsurum(_sbtVerisyon);
                                                                                _Param.set_zkullaniciadi(_zkullaniciadi);
                                                                                _Param.set_zsifre(_zsifre);
                                                                                _Param.setAktif_sunucu(_ayaraktifsunucu);
                                                                                _Param.setAktif_kullanici(_ayaraktifkullanici);

                                                                                _Param.set_value(aktif_sevk_isemri.arac_rfid);

                                                                                Genel.showProgressDialog(getContext());
                                                                                List<Sevkiyat_isemri> result= persos.fn_secKantarIsemriListesi(_Param);
                                                                                Sevkiyat_isemri aktif_sevk_isemri2=null;
                                                                                if(result!=null) {
                                                                                    for(Sevkiyat_isemri w : result){
                                                                                        if(w.arac_rfid.equals(aktif_sevk_isemri.arac_rfid)){
                                                                                            aktif_sevk_isemri2=w;
                                                                                            break;
                                                                                        }
                                                                                    }
                                                                                }
                                                                                Genel.dismissProgressDialog();

                                                                                frg_arac_onayla fragmentyeni = new frg_arac_onayla();
                                                                                fragmentyeni.fn_senddata(aktif_sevk_isemri2);
                                                                                FragmentManager fragmentManager = getFragmentManager();
                                                                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                                                fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_arac_onayla").addToBackStack(null);
                                                                                fragmentTransaction.commit();

                                                                                return;
                                                                            }
                                                                        }).show();


                                                            }
                                                            else
                                                            {
                                                                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                                                                        .setTitleText("HATA")
                                                                        .setContentTextSize(25)
                                                                        .setContentText("KAYIT YAPILAMADI \r\n NETWORK BAĞLANTISINI KONTROL EDİNİZ..")
                                                                        .showCancelButton(false)
                                                                        .show();
                                                                _IslemVar=false;
                                                            }

                                                            return;
                                                        }
                                                    })
                                                    .setCancelClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                                        @Override
                                                        public void onClick(SweetAlertDialogG sDialog) {
                                                            sDialog.dismissWithAnimation();
                                                            return;
                                                        }
                                                    })
                                                    .show();

                                            return;
                                        }
                                    })
                                    .setCancelClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialogG sDialog) {
                                            sDialog.dismissWithAnimation();
                                            _IslemVar=false;
                                            return;
                                        }
                                    })
                                    .show();


                        }else {
                            _IslemVar = false;
                        }
                    }
                }
                // Diğer İşletmeler
                else {
                    Cache cache = new DiskBasedCache(getContext().getCacheDir(), 2 * 1024 * 1024); // 2MB cap
                    Network network = new BasicNetwork(new HurlStack());
                    parametre = new JSONObject();
                    queue = new RequestQueue(cache, network);
                    queue.start();

                    try {


                        parametre.put("_zrfid", v_epc);
                        //parametre.put("_zrfid", "737767300020220000002884");
                        parametre.put("_zsunucu_ip_adresi", _ayarsunucuip);
                        parametre.put("_zaktif_alt_tesis", _ayaraktifalttesis);
                        parametre.put("_zaktif_tesis", _ayaraktiftesis);
                        parametre.put("_zkullaniciadi", _zkullaniciadi);
                        parametre.put("_zsifre", _zsifre);
                        //parametre.put("aktif_sunucu", _ayaraktifsunucu);
                        //parametre.put("aktif_kullanici", _ayaraktifkullanici);


                    } catch (Exception ex) {
                        Genel.printStackTrace(ex,getContext());
                    }


                    JsonObjectRequest request = new JsonObjectRequest(
                            Request.Method.POST,
                            _sec_konteyner,
                            parametre,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        // pDialog.hide();

                                        ObjectMapper objectMapper = new ObjectMapper();

                                        final ViewsecKonteyner _Yanit = objectMapper.readValue(response.toString(), ViewsecKonteyner.class);


                                        //String _zSayfaAdiAciklama=response.getString("_zSayfaAdiAciklama");
                                        //final String  _zSayfaAdi=response.getString("_zSayfaAdi");
                                        String _zHataAciklamasi = _Yanit._zHataAciklama;

                                        final String _zYanit = _Yanit._zSonuc;

                                        if (_zYanit.equals("0")) {
                                            pDialog.changeAlertType(SweetAlertDialogG.ERROR_TYPE);
                                            pDialog.setTitle("HATA");
                                            pDialog.setContentTextSize(20);
                                            pDialog.setContentText(_zHataAciklamasi);
                                            pDialog.setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialogG sDialog)
                                                {
                                                    sDialog.dismissWithAnimation();

                                                    sDialog.hide();

                                                    //((GirisSayfasi)getActivity()).fn_ListeTemizle();

                                                    _IslemVar = false;

                                                }});
                                        } else {
                                            try {



                                            /*
                                            JSONObject _Arac=response.getJSONObject("_zArac");

                                            String v_arac_kod = _Arac.getString("arac_kod").trim();
                                            String v_arac_rfid = _Arac.getString("arac_rfid").trim();
                                            String v_arac_plaka = _Arac.getString("arac_plaka").trim();
                                            String v_arac_istihab_haddi = _Arac.getString("arac_istihab_haddi").trim();
                                            String v_arac_bos_tartim = _Arac.getString("arac_bos_tartim").trim();
                                            v_isKonteynerAvailable = _Arac.getString("_zisKonteynerAva").trim();
                                            */

                                                String v_arac_kod = _Yanit._zArac.arac_kod.trim();
                                                String v_arac_rfid = _Yanit._zArac.arac_rfid.trim();
                                                String v_arac_plaka = _Yanit._zArac.arac_plaka.trim();
                                                String v_arac_istihab_haddi = _Yanit._zArac.arac_istihab_haddi.trim();
                                                String v_arac_bos_tartim = _Yanit._zArac.arac_bos_tartim.trim();
                                                v_isKonteynerAvailable = _Yanit._zisKonteynerAva.trim();

                                                if (v_arac_kod.equals("")) {
                                                    pDialog.changeAlertType(SweetAlertDialogG.ERROR_TYPE);
                                                    pDialog.setTitle("HATA");
                                                    pDialog.setContentTextSize(20);
                                                    pDialog.setContentText("Aktif konteyner bulunamadı.. Konteyner tanımlama işlemi tamamlanmamış.");

                                                    pDialog.setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                                        @Override
                                                        public void onClick(SweetAlertDialogG sDialog)
                                                        {
                                                            sDialog.dismissWithAnimation();

                                                            sDialog.hide();

                                                            //((GirisSayfasi)getActivity()).fn_ListeTemizle();

                                                            _IslemVar = false;

                                                        }});

                                                } else {
                                                    if (v_alt_rota.equals("") && v_isKonteynerAvailable.equals("-1")) {
                                                        pDialog.changeAlertType(SweetAlertDialogG.ERROR_TYPE);
                                                        pDialog.setTitle("YANLIŞ KONTEYNER");
                                                        pDialog.setContentTextSize(20);
                                                        pDialog.setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                                            @Override
                                                            public void onClick(SweetAlertDialogG sDialog)
                                                            {
                                                                sDialog.dismissWithAnimation();

                                                                sDialog.hide();

                                                                //((GirisSayfasi)getActivity()).fn_ListeTemizle();

                                                                _IslemVar = false;

                                                            }});
                                                        pDialog.setContentText("AYNI KONTEYNER NUMARASI BAŞKA BİR ARAÇ İÇİN KULLANILMIŞ.. Lütfen doğru konteyner numarası okutunuz.");
                                                        pDialog.findViewById(R.id.confirm_button).setVisibility(View.VISIBLE);
                                                        pDialog.findViewById(R.id.cancel_button).setVisibility(View.INVISIBLE);

                                                    } else {
                                                        try {
                                                            if (pDialog != null && pDialog.isShowing()) {
                                                                pDialog.hide();
                                                            }

                                                            String _pAciklama = "KONTEYNER PLAKA : " + v_arac_plaka + " KONTEYNER OKUNDU. İŞLEME DEVAM ETMEK İSTEDİĞİNİZDEN EMİN MİSİNİZ?";

                                                            SweetAlertDialogG pConfirm = new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE);
                                                            pConfirm.setTitleText("KONTEYNER ONAY");
                                                            pConfirm.setContentText(_pAciklama);
                                                            pConfirm.setCancelText("İptal");
                                                            pConfirm.setContentTextSize(20);
                                                            pConfirm.setConfirmText("DEVAM ET");
                                                            pConfirm.setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                                                @Override
                                                                public void onClick(SweetAlertDialogG sDialog) {
                                                                    sDialog.dismissWithAnimation();

                                                                    sDialog.hide();

                                                                    aktif_sevk_isemri.kont_kodu = _Yanit._zArac.arac_kod;

                                                                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.SUCCESS_TYPE)
                                                                            .setTitleText("ONAY")
                                                                            .setContentText("İşlem onaylandı.")
                                                                            .setCancelText("Hayır")
                                                                            .setContentTextSize(20)
                                                                            .setConfirmText("TAMAM")
                                                                            .showCancelButton(false)
                                                                            .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                                                                @Override
                                                                                public void onClick(SweetAlertDialogG sDialog) {
                                                                                    sDialog.dismissWithAnimation();

                                                                                    sDialog.hide();

                                                                                    frg_arac_onayla fragmentyeni = new frg_arac_onayla();
                                                                                    fragmentyeni.fn_senddata(aktif_sevk_isemri);
                                                                                    FragmentManager fragmentManager = getFragmentManager();
                                                                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                                                    fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_arac_onayla").addToBackStack(null);
                                                                                    fragmentTransaction.commit();

                                                                                }
                                                                            })
                                                                            .show();
                                                                }
                                                            })
                                                                    .show();


                                                        } catch (Exception ex) {
                                                            Genel.printStackTrace(ex,getContext());
                                                        }
                                                    }
                                                }


                                                //fn_Listele();
                                            } catch (Exception ex) {
                                                if (pDialog != null && pDialog.isShowing()) {
                                                    pDialog.hide();
                                                }
                                                Toast.makeText(getContext(), ex.toString(), Toast.LENGTH_SHORT).show();
                                                Genel.printStackTrace(ex,getContext());
                                            }

                                        }


                                        //Toast.makeText(getApplicationContext(), "_zSayfaAdiAciklama =" + _zHataAciklamasi, Toast.LENGTH_SHORT).show();

                                    } catch (JsonMappingException e) {
                                        if (pDialog != null && pDialog.isShowing()) {
                                            pDialog.hide();
                                        }
                                        Genel.printStackTrace(e,getContext());
                                    } catch (JsonProcessingException ex_02) {
                                        if (pDialog != null && pDialog.isShowing()) {
                                            pDialog.hide();
                                        }
                                        Toast.makeText(getContext(), "error =" + ex_02.toString(), Toast.LENGTH_LONG).show();
                                        Genel.printStackTrace(ex_02,getContext());
                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    error.printStackTrace();
                                    if (pDialog != null && pDialog.isShowing()) {
                                        pDialog.hide();
                                    }
                                    Toast.makeText(getContext(), "error =" + error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }


                    );
                    int socketTimeout = 30000;//30 seconds - change to what you want
                    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                    request.setRetryPolicy(policy);
                    queue.add(request);


                }
            } catch (Exception ex) {
                Genel.printStackTrace(ex,getContext());
                if (pDialog != null && pDialog.isShowing()) {
                    pDialog.hide();
                }

                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentText("Sistemsel bir hata oluştu." + ex.toString())
                        .setCancelText("Hayır")
                        .setConfirmText("TAMAM")
                        .setContentTextSize(20)
                        .showCancelButton(false)
                        .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialogG sDialog) {
                                sDialog.dismissWithAnimation();

                                sDialog.hide();
                            }
                        })
                        .show();

                //((GirisSayfasi)getActivity()).fn_ListeTemizle();

                _IslemVar = false;
            }
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


        //   JSONObject jsonObj = new JSONObject(aktif_sevk_isemri);

        v_alt_rota = aktif_sevk_isemri.alt_rota.trim();


        if(_ayarbaglantituru.equals("wifi"))
        {
            _sec_aracUrl = "http://"+_ayarsunucuip+":"+_zportWifi+"/api/sec_arac";
            _sevkiyat_konteyner_ayirUrl = "http://"+_ayarsunucuip+":"+_zportWifi+"/api/sevkiyat_konteyner_ayir";
            _sec_konteyner = "http://"+_ayarsunucuip+":"+_zportWifi+"/api/secKonteyner";
        }
        else
        {
            _sec_aracUrl = "http://"+_ipAdresi3G+":"+_zport3G+"/api/sec_arac";
            _sevkiyat_konteyner_ayirUrl = "http://"+_ipAdresi3G+":"+_zport3G+"/api/sevkiyat_konteyner_ayir";
            _sec_konteyner = "http://"+_ipAdresi3G+":"+_zport3G+"/api/secKonteyner";
        }

        if(_ayarbaglantituru.equals("wifi"))
        {
            _OnlineUrl = "http://"+_ayarsunucuip+":"+_zportWifi+"/";
        }
        else
        {
            _OnlineUrl = "http:/"+_ipAdresi3G+":"+_zport3G+"/";
        }
        persos = new Persos(_OnlineUrl,getContext());
    }

    public void fn_senddata(Sevkiyat_isemri v_aktif_sevk_isemri)
    {
        this.aktif_sevk_isemri=v_aktif_sevk_isemri;
    }

    private class fn_Geri implements View.OnClickListener {

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
}
