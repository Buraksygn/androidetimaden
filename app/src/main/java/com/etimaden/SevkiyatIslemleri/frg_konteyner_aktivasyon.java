package com.etimaden.SevkiyatIslemleri;

import android.os.Bundle;
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
import com.etimaden.cResponseResult.ViewsecKonteyner;
import com.etimaden.ugr_demo.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.etimaden.cSabitDegerler._ipAdresi3G;
import static com.etimaden.cSabitDegerler._sbtVerisyon;
import static com.etimaden.cSabitDegerler._zkullaniciadi;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;

public class frg_konteyner_aktivasyon extends Fragment {

    SweetAlertDialog pDialog;

    String arac_plaka="";
    String arac_kod="";

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
        _btngeri.playSoundEffect(0);
        _btngeri.setOnClickListener(new fn_Geri());
    }


    public void fn_arac_ac(String v_epc) {

        if (_IslemVar == false)
        {
            _IslemVar = true;

            ((GirisSayfasi)getActivity()).fn_ListeTemizle();


            pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE);
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
                                                arac_plaka = response.getString("arac_plaka");
                                                arac_kod = response.getString("arac_kod");


                                                if (_zSonuc.equals("0")) {



                                                    pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                                    pDialog.setTitle("HATA");
                                                    pDialog.setContentText("KONTEYNER KAYDI ALINAMADI TEKRAR DENEYİNİZ. KONTEYNER AYIRMA İŞLEMİ (ARAÇ SORGULAMA).");
                                                    pDialog.showCancelButton(false);
                                                    pDialog.findViewById(R.id.cancel_button).setVisibility(View.INVISIBLE);
                                                    pDialog.findViewById(R.id.confirm_button).setVisibility(View.VISIBLE);

                                                    pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                        @Override
                                                        public void onClick(SweetAlertDialog sDialog)
                                                        {
                                                            sDialog.dismissWithAnimation();

                                                            sDialog.hide();

                                                            ((GirisSayfasi)getActivity()).fn_ListeTemizle();

                                                            _IslemVar = false;

                                                        }});

                                                } else {
                                                    pDialog.hide();

                                                    String _Yazi = "KONTEYNER PLAKA : " + arac_plaka + " KONTEYNER AYIRMA İŞLEMİ. KONTEYNERI AYIRMA İŞLEMİNE DEVAM ETMEK İSTEDİĞİNİZDEN EMİN MİSİNİZ?";

                                                    new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                                            .setTitleText("KONTEYNER AYIRMA")
                                                            .setContentText(_Yazi)
                                                            .setCancelText("İptal")
                                                            .setContentTextSize(20)
                                                            .setConfirmText("TAMAM")
                                                            .showCancelButton(true)
                                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                                @Override
                                                                public void onClick(SweetAlertDialog sDialog) {
                                                                    sDialog.dismissWithAnimation();

                                                                    //sDialog.hide();

                                                                    pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE);
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


                                                                    pDialog.findViewById(R.id.confirm_button).setVisibility(View.GONE);

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
                                                                    } catch (JSONException e) {
                                                                        e.printStackTrace();
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
                                                                                            pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                                                                            pDialog.setTitle("HATA");
                                                                                            pDialog.setContentText("KAYIT YAPILAMADI. DAHA SONRA TEKRAR DENEYİNİZ. KONTEYNER AYIRMA İŞLEMİ(İŞLEM KAYDI OLUŞTUR).");
                                                                                            pDialog.findViewById(R.id.confirm_button).setVisibility(View.VISIBLE);
                                                                                            pDialog.findViewById(R.id.cancel_button).setVisibility(View.INVISIBLE);

                                                                                            pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                                                                @Override
                                                                                                public void onClick(SweetAlertDialog sDialog)
                                                                                                {
                                                                                                    sDialog.dismissWithAnimation();

                                                                                                    sDialog.hide();

                                                                                                    ((GirisSayfasi)getActivity()).fn_ListeTemizle();

                                                                                                    _IslemVar = false;

                                                                                                }});
                                                                                        } else {
                                                                                            pDialog.hide();

                                                                                            new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                                                                                    .setTitleText("ONAY")
                                                                                                    .setContentText("İşlem onaylandı.")
                                                                                                    .setCancelText("Hayır")
                                                                                                    .setConfirmText("TAMAM")
                                                                                                    .setContentTextSize(20)
                                                                                                    .showCancelButton(false)
                                                                                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                                                                        @Override
                                                                                                        public void onClick(SweetAlertDialog sDialog) {
                                                                                                            sDialog.dismissWithAnimation();

                                                                                                            sDialog.hide();

                                                                                                            ((GirisSayfasi)getActivity()).fn_ListeTemizle();

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


                        } catch (JSONException error) {
                            error.printStackTrace();
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

                    } catch (Exception ex) {

                    }


                    JsonObjectRequest request = new JsonObjectRequest(
                            Request.Method.POST,
                            _OnlineUrl,
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
                                            pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                            pDialog.setTitle("HATA");
                                            pDialog.setContentTextSize(20);
                                            pDialog.setContentText(_zHataAciklamasi);
                                            pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sDialog)
                                                {
                                                    sDialog.dismissWithAnimation();

                                                    sDialog.hide();

                                                    ((GirisSayfasi)getActivity()).fn_ListeTemizle();

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
                                                    pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                                    pDialog.setTitle("HATA");
                                                    pDialog.setContentTextSize(20);
                                                    pDialog.setContentText("Aktif konteyner bulunamadı.. Konteyner tanımlama işlemi tamamlanmamış.");

                                                    pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                        @Override
                                                        public void onClick(SweetAlertDialog sDialog)
                                                        {
                                                            sDialog.dismissWithAnimation();

                                                            sDialog.hide();

                                                            ((GirisSayfasi)getActivity()).fn_ListeTemizle();

                                                            _IslemVar = false;

                                                        }});

                                                } else {
                                                    if (v_alt_rota.equals("") && v_isKonteynerAvailable.equals("-1")) {
                                                        pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                                        pDialog.setTitle("YANLIŞ KONTEYNER");
                                                        pDialog.setContentTextSize(20);
                                                        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                            @Override
                                                            public void onClick(SweetAlertDialog sDialog)
                                                            {
                                                                sDialog.dismissWithAnimation();

                                                                sDialog.hide();

                                                                ((GirisSayfasi)getActivity()).fn_ListeTemizle();

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

                                                            SweetAlertDialog pConfirm = new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE);
                                                            pConfirm.setTitleText("KONTEYNER ONAY");
                                                            pConfirm.setContentText(_pAciklama);
                                                            pConfirm.setCancelText("İptal");
                                                            pConfirm.setContentTextSize(20);
                                                            pConfirm.setConfirmText("DEVAM ET");
                                                            pConfirm.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                                @Override
                                                                public void onClick(SweetAlertDialog sDialog) {
                                                                    sDialog.dismissWithAnimation();

                                                                    sDialog.hide();

                                                                    aktif_sevk_isemri.kont_kodu = _Yanit._zArac.arac_kod;

                                                                    new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                                                            .setTitleText("ONAY")
                                                                            .setContentText("İşlem onaylandı.")
                                                                            .setCancelText("Hayır")
                                                                            .setContentTextSize(20)
                                                                            .setConfirmText("TAMAM")
                                                                            .showCancelButton(false)
                                                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                                                @Override
                                                                                public void onClick(SweetAlertDialog sDialog) {
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

                                                        }
                                                    }
                                                }


                                                //fn_Listele();
                                            } catch (Exception ex) {
                                                Toast.makeText(getContext(), ex.toString(), Toast.LENGTH_SHORT).show();

                                            }
                                            pDialog.hide();
                                        }


                                        //Toast.makeText(getApplicationContext(), "_zSayfaAdiAciklama =" + _zHataAciklamasi, Toast.LENGTH_SHORT).show();

                                    } catch (JsonMappingException e) {
                                        e.printStackTrace();
                                    } catch (JsonProcessingException ex_02) {
                                        Toast.makeText(getContext(), "error =" + ex_02.toString(), Toast.LENGTH_LONG).show();
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
            } catch (Exception ex) {
                pDialog.hide();

                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentText("Sistemsel bir hata oluştu." + ex.toString())
                        .setCancelText("Hayır")
                        .setConfirmText("TAMAM")
                        .setContentTextSize(20)
                        .showCancelButton(false)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();

                                sDialog.hide();
                            }
                        })
                        .show();

                ((GirisSayfasi)getActivity()).fn_ListeTemizle();

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
            _OnlineUrl = "http://"+_ayarsunucuip+":"+_zportWifi+"/api/secKonteyner";
        }
        else
        {
            _sec_aracUrl = "http://"+_ipAdresi3G+":"+_zport3G+"/api/sec_arac";
            _sevkiyat_konteyner_ayirUrl = "http://"+_ipAdresi3G+":"+_zport3G+"/api/sevkiyat_konteyner_ayir";
            _OnlineUrl = "http://"+_ipAdresi3G+":"+_zport3G+"/api/secKonteyner";
        }
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
