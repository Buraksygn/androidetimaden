package com.etimaden.SevkiyatIslemleri;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
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
import com.etimaden.DataModel.mblsEmriKonteyner;
import com.etimaden.GirisSayfasi;
import com.etimaden.adapter.apmblKonteynerIsEmri;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cResponseResult.Sevkiyat_isemri;
import com.etimaden.cResponseResult.ViewAracAktivasyon;
import com.etimaden.ugr_demo.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.etimaden.cSabitDegerler._ipAdresi3G;
import static com.etimaden.cSabitDegerler._zkullaniciadi;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;

public class frg_konteyner_isemri_secimi  extends Fragment {

    public Sevkiyat_isemri aktif_sevk_isemri;

    //private Handler handler;
    SweetAlertDialog pDialog;

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

    public String _OnlineUrl = "";
    public String _OnlineUrlTamamla = "";

    ArrayList<mblsEmriKonteyner> dataModels;

    public mblsEmriKonteyner _SeciliListe;


    //public ListAdapter adapter;

    private static apmblKonteynerIsEmri adapter;

    public ListView _Liste;

    Button btn_3;

    public frg_konteyner_isemri_secimi() {
        // Required empty public constructor
    }

    public static frg_konteyner_isemri_secimi newInstance() {

        return new frg_konteyner_isemri_secimi();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_konteyner_isemri_secimi, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        _myIslem = new VeriTabani(getContext());

        ((GirisSayfasi)getActivity()).fn_ModRFID();

        fn_AyarlariYukle();

        new VeriTabani(getContext()).fn_EpcTemizle();

        ((GirisSayfasi) getActivity()).fn_ListeTemizle();
        ((GirisSayfasi) getActivity()).fn_GucAyarla(248);

        _btngeri = (Button) getView().findViewById(R.id.btngeri);
        _btngeri.playSoundEffect(0);
        _btngeri.setOnClickListener(new fn_Geri());

        btn_3 = (Button) getView().findViewById(R.id.btn_03);
        btn_3.playSoundEffect(0);
        btn_3.setOnClickListener(new fn_btn_3());

        _Liste = (ListView) getView().findViewById(R.id.isemri_list);

        _Liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //  mblsEmriKonteyner dataModel= dataModels.get(position);

                _SeciliListe = dataModels.get(position);
                // _SeciliEpc = dataModel.getetiketepc();
                //Toast.makeText(getContext(), _SeciliEpc, Toast.LENGTH_SHORT).show();
            }
        });

        fn_SevkDegerlendir();
    }

    private void fn_SevkDegerlendir() {
        pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE);
        pDialog.setTitleText("YÜKLENİYOR");
        pDialog.setContentText(" Kontrol ediliyor Lütfen bekleyiniz.");
        //pDialog.setContentText(_TempEpc);
        pDialog.setCancelable(false);
        pDialog.show();
        pDialog.findViewById(R.id.confirm_button).setVisibility(View.GONE);

        JSONObject parametre = new JSONObject();

        try {

            parametre.put("_zrfid", "0000000000000000");
            //parametre.put("_zrfid", "737767300020220000002884");
            parametre.put("_zsunucu_ip_adresi", _ayarsunucuip);
            parametre.put("_zaktif_alt_tesis", _ayaraktifalttesis);
            parametre.put("_zaktif_tesis", _ayaraktiftesis);
            parametre.put("_zkullaniciadi", _zkullaniciadi);
            parametre.put("_zsifre", _zsifre);


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
                _OnlineUrl,
                parametre,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            ObjectMapper objectMapper = new ObjectMapper();

                            ViewAracAktivasyon _Yanit = objectMapper.readValue(response.toString(), ViewAracAktivasyon.class);

                            if (_Yanit._zSonuc.equals("0")) {
                                pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                pDialog.setTitle("HATA");
                                pDialog.setContentText(_Yanit._zHataAciklama);
                            } else {
                                try {
                                    //_myIslem.fn_IsEmirleriTemizle();

                                    dataModels = new ArrayList<>();

                                    for (int intSayac = 0; intSayac < _Yanit._zDiziaktif_sevk_isemri.size(); intSayac++) {
                                        String alici_isletme = _Yanit._zDiziaktif_sevk_isemri.get(intSayac).alici_isletme.toString().trim();

                                        String alici = _Yanit._zDiziaktif_sevk_isemri.get(intSayac).alici.toString().trim();

                                        if (alici_isletme.equals("")) {
                                            alici_isletme = alici;
                                        }

                                        dataModels.add(new mblsEmriKonteyner(
                                                intSayac + 1,
                                                _Yanit._zDiziaktif_sevk_isemri.get(intSayac).kod_sap.toString().trim() + "",
                                                _Yanit._zDiziaktif_sevk_isemri.get(intSayac).bookingno.toString().trim() + "",
                                                _Yanit._zDiziaktif_sevk_isemri.get(intSayac).urun_adi.toString().trim() + "",
                                                _Yanit._zDiziaktif_sevk_isemri.get(intSayac).kalan_agirlik.toString().trim() + "",
                                                _Yanit._zDiziaktif_sevk_isemri.get(intSayac).kalan_palet_sayisi.toString().trim() + "",
                                                _Yanit._zDiziaktif_sevk_isemri.get(intSayac).yapilan_miktar.toString().trim() + "",
                                                _Yanit._zDiziaktif_sevk_isemri.get(intSayac).yapilan_adet.toString().trim() + "",
                                                alici_isletme + "",
                                                _Yanit._zDiziaktif_sevk_isemri.get(intSayac).sefer_no.toString().trim() + "",
                                                _Yanit._zDiziaktif_sevk_isemri.get(intSayac).isemri_id.toString().trim() + "",
                                                _Yanit._zDiziaktif_sevk_isemri.get(intSayac).isemri_detay_id.toString().trim() + "",
                                                _Yanit._zDiziaktif_sevk_isemri.get(intSayac).hedef_isletme_esleme.toString().trim() + "",
                                                _Yanit._zDiziaktif_sevk_isemri.get(intSayac).hedef_isletme_kodu.toString().trim() + "",
                                                _Yanit._zDiziaktif_sevk_isemri.get(intSayac).hedef_isletme_alt_kodu.toString().trim() + "",
                                                _Yanit._zDiziaktif_sevk_isemri.get(intSayac).depo_kodu.toString().trim() + "",
                                                _Yanit._zDiziaktif_sevk_isemri.get(intSayac).hedef_depo.toString().trim() + "",
                                                _Yanit._zDiziaktif_sevk_isemri.get(intSayac).urun_kodu.toString().trim() + "",
                                                _Yanit._zDiziaktif_sevk_isemri.get(intSayac).isletme_esleme.toString().trim() + ""
                                                )
                                        );
                                    }

                                    adapter = new apmblKonteynerIsEmri(dataModels, getContext());

                                    _Liste.setAdapter(adapter);

                                    // fn_Listele();
                                } catch (Exception ex) {
                                    Toast.makeText(getContext(), ex.toString(), Toast.LENGTH_SHORT).show();

                                }
                                pDialog.hide();
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

    private void fn_IsEmirleriYukle() {

    }

    private void fn_Listele() {
        /*
        ArrayList<HashMap<String, String>> _GorevListe = _myIslem.fn_KonteynerIsEmriListele();

        int count =_GorevListe.size();

        adapter = new SimpleAdapter(getContext(), _GorevListe,
                R.layout.list_row,
                new String[]{"kod_sap","bookingno","urun_adi","kalan_agirlik","kalan_palet_sayisi","yapilan_miktar","yapilan_adet","alici"},
                new int[]{R.id.yazi_kod_sap,R.id.yazi_bookingno,R.id.yazi_urun_adi,R.id.yazi_kalan_agirlik,R.id.yazi_kalan_palet_sayisi,R.id.yazi_yapilan_miktar,R.id.yazi_yapilan_adet,R.id.yazi_alici});

        _Liste.setAdapter(adapter);

         */
    }

    private void fn_AyarlariYukle() {
        _ayarbaglantituru = _myIslem.fn_baglanti_turu();
        _ayarsunucuip = _myIslem.fn_sunucu_ip();
        _ayaraktifkullanici = _myIslem.fn_aktif_kullanici();
        _ayaraktifdepo = _myIslem.fn_aktif_depo();
        _ayaraktifalttesis = _myIslem.fn_aktif_alt_tesis();
        _ayaraktiftesis = _myIslem.fn_aktif_tesis();
        _ayaraktifsunucu = _myIslem.fn_aktif_sunucu();
        _ayaraktifisletmeeslesme = _myIslem.fn_isletmeeslesme();

        if (_ayarbaglantituru.equals("wifi")) {
            _OnlineUrl = "http://" + _ayarsunucuip + ":" + _zportWifi + "/api/secSevkiyatIsemriListesi";
            _OnlineUrlTamamla = "http://" + _ayarsunucuip + ":" + _zportWifi + "/api/ac_yerde_konteyner";
        } else {
            _OnlineUrl = "http://"+_ipAdresi3G+":" + _zport3G + "/api/secSevkiyatIsemriListesi";
            _OnlineUrlTamamla = "http://"+_ipAdresi3G+":" + _zport3G + "/api/ac_yerde_konteyner";
        }
    }


    public void fn_senddata(Sevkiyat_isemri v_aktif_sevk_isemri) {
        this.aktif_sevk_isemri = v_aktif_sevk_isemri;
    }

    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            frg_konteyner_yukleme_aktivasyon fragmentyeni = new frg_konteyner_yukleme_aktivasyon();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_konteyner_yukleme_aktivasyon").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_btn_3 implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            //Log.d("Tag","SATIR 01");

            pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE);
            pDialog.setTitleText("İŞLEM YAPILIYOR");
            pDialog.setContentText("İşlem tamamlanıyor. Lütfen Bekleyiniz");
            //pDialog.setContentText(_TempEpc);
            pDialog.setCancelable(false);
            pDialog.show();
            pDialog.findViewById(R.id.confirm_button).setVisibility(View.GONE);

            // Log.d("Tag","SATIR 01");
            JSONObject parametre = new JSONObject();

            try {

                // JSONObject jsonObj = new JSONObject(aktif_sevk_isemri);

                String Arac_Plaka = aktif_sevk_isemri.arac_plaka;
                //String Arac_Plaka = jsonObj.getString("arac_plaka").trim();

                String Arac_Kodu = aktif_sevk_isemri.arac_kodu;
                //String Arac_Kodu=jsonObj.getString("arac_kodu").trim();

                String sefer_no = _SeciliListe.getsefer_no();
                String isemri_detay_id = _SeciliListe.getisemri_detay_id();
                String isemri_id = _SeciliListe.getisemri_id();
                String hedef_isletme_esleme = _SeciliListe.gethedef_isletme_esleme();
                String hedef_isletme_kodu = _SeciliListe.gethedef_isletme_kodu();
                String hedef_isletme_alt_kodu = _SeciliListe.gethedef_isletme_alt_kodu();
                String depo_kodu = _SeciliListe.getdepo_kodu();
                String hedef_depo = _SeciliListe.gethedef_depo();
                String urun_kodu = _SeciliListe.geturun_kodu();
                String isletme_esleme = _SeciliListe.getisletme_esleme();
                String kod_sap = _SeciliListe.getkod_sap();


                parametre.put("_zsunucu_ip_adresi", _ayarsunucuip);
                parametre.put("aktif_tesis", _ayaraktiftesis);
                parametre.put("sefer_no", sefer_no);
                parametre.put("isemri_detay_id", isemri_detay_id);
                parametre.put("isletme_esleme", isletme_esleme);
                parametre.put("kod_sap", kod_sap);
                parametre.put("isemri_id", isemri_id);
                parametre.put("hedef_isletme_esleme", hedef_isletme_esleme);
                parametre.put("hedef_isletme_kodu", hedef_isletme_kodu);
                parametre.put("hedef_isletme_alt_kodu", hedef_isletme_alt_kodu);
                parametre.put("depo_kodu", depo_kodu);
                parametre.put("hedef_depo", hedef_depo);
                parametre.put("urun_kodu", urun_kodu);
                parametre.put("konteyner_arac_kodu", Arac_Kodu);
                parametre.put("konteyner_arac_plaka", Arac_Plaka);
                parametre.put("aktif_sunucu", _ayaraktifsunucu);
                parametre.put("aktif_kullanici", _ayaraktifkullanici);
                parametre.put("aktif_alt_tesis", _ayaraktifalttesis);
                parametre.put("_zkullaniciadi", _zkullaniciadi);
                parametre.put("_zsifre", _zsifre);


            } catch (JSONException error) {
                error.printStackTrace();


            }

            Cache cache = new DiskBasedCache(getContext().getCacheDir(), 2 * 1024 * 1024); // 1MB cap
            Network network = new BasicNetwork(new HurlStack());

            RequestQueue queue = new RequestQueue(cache, network);
            queue.start();


            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    _OnlineUrlTamamla,
                    parametre,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                // pDialog.hide();

                                String _zHataAciklamasi = response.getString("_zHataAciklamasi");
                                String _zYanit = response.getString("_zHataVarmi");
                                if (_zYanit.equals("0")) {
                                    pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                    pDialog.setTitle("HATA");
                                    pDialog.setContentTextSize(22);
                                    pDialog.setContentText(_zHataAciklamasi);
                                } else {
                                    try {
                                        pDialog.hide();
                                    } catch (Exception ex) {

                                    }
                                    new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("TAMAMLANDI")
                                            .setContentText("İşlem başarı ile tamamlanmıştır.")
                                            .setConfirmText("TAMAM")
                                            .setContentTextSize(22)
                                            .showCancelButton(false)
                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sDialog) {
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

                                //Toast.makeText(getApplicationContext(), "_zSayfaAdiAciklama =" + _zHataAciklamasi, Toast.LENGTH_SHORT).show();

                            } catch (JSONException ex) {
                                Toast.makeText(getContext(), "error =" + ex.toString(), Toast.LENGTH_SHORT).show();
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

    private class fn_btn_03 implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String _Sayi = _SeciliListe.getkod_sap();

            int _Dur = 1;
        }
    }
}
