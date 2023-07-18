package com.etimaden.SevkiyatIslemleri;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
import com.etimaden.DataModel.mdlIsemriSecimi;
import com.etimaden.GirisSayfasi;
import com.etimaden.adapter.apmblIsEmriSecimi;
import com.etimaden.adapter.apmblKonteynerIsEmri;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cResponseResult.Sevkiyat_isemri;
import com.etimaden.cResponseResult.ViewAracAktivasyon;
import com.etimaden.cResponseResult.ViewonaylaSevkIsDegisimi;
import com.etimaden.ugr_demo.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.etimaden.cSabitDegerler._zkullaniciadi;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;

public class frg_isemri_degistir extends Fragment {

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

    public String _OnlineUrl = "";
    public String _OnlineUrlIsEmriDegistir = "";

    public Sevkiyat_isemri aktif_sevk_isemri;

    private static apmblIsEmriSecimi adapter;

    ArrayList<mdlIsemriSecimi> dataModels;

    public ListView _Liste;

    public mdlIsemriSecimi _SeciliListe;

    Button _btn_03;

    public frg_isemri_degistir() {
        // Required empty public constructor
    }

    public static frg_isemri_degistir newInstance() {

        return new frg_isemri_degistir();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_isemri_degistir, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((GirisSayfasi)getActivity()).fn_ModRFID();

        _myIslem = new VeriTabani(getContext());

        fn_AyarlariYukle();

        _btngeri = (Button) getView().findViewById(R.id.btngeri);
        _btngeri.playSoundEffect(0);
        _btngeri.setOnClickListener(new fn_Geri());

        _btn_03 = (Button) getView().findViewById(R.id.btn_03);
        _btn_03.playSoundEffect(0);
        _btn_03.setOnClickListener(new fn_btn_03());


        _SeciliListe = new mdlIsemriSecimi("-1");

        _Liste = (ListView) getView().findViewById(R.id.isemri_list);


        _Liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _SeciliListe = dataModels.get(position);
            }
        });


        fn_IsEmirListesi();
    }

    private void fn_IsEmirListesi() {

        pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE);
        pDialog.setTitleText("YÜKLENİYOR");
        pDialog.setContentText("İş emir listesi yükleniyor. Lütfen bekleyiniz");
        //pDialog.setContentText(_TempEpc);
        pDialog.setCancelable(false);
        pDialog.setContentTextSize(20);
        pDialog.show();
        pDialog.findViewById(R.id.confirm_button).setVisibility(View.GONE);

        JSONObject parametre = new JSONObject();

        try {
            parametre.put("_zsunucu_ip_adresi", _ayarsunucuip);
            parametre.put("_zaktif_alt_tesis", _ayaraktifalttesis);
            parametre.put("_zaktif_tesis", _ayaraktiftesis);
            parametre.put("aktif_sunucu", _ayaraktifsunucu);
            parametre.put("aktif_kullanici", _ayaraktifkullanici);
            parametre.put("_zkullaniciadi", _zkullaniciadi);
            parametre.put("_zsifre", _zsifre);
            parametre.put("_zrfid", "1111111111111111");
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
                        ObjectMapper objectMapper = new ObjectMapper();

                        try {
                            ViewAracAktivasyon _Yanit = objectMapper.readValue(response.toString(), ViewAracAktivasyon.class);


                            if (_Yanit._zSonuc.equals("0")) {
                                pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                pDialog.setTitle("HATA");
                                pDialog.setContentText(_Yanit._zHataAciklama);
                            } else {
                                // _myIslem.fn_IsEmirleriTemizle();

                                dataModels = new ArrayList<>();

                                for (int intSayac = 0; intSayac < _Yanit._zDiziaktif_sevk_isemri.size(); intSayac++) {
                                    Sevkiyat_isemri _cTemp = _Yanit._zDiziaktif_sevk_isemri.get(intSayac);

                                    dataModels.add(new mdlIsemriSecimi(
                                            intSayac + 1,
                                            _cTemp.isemri_detay_id + "".toString().trim(),
                                            _cTemp.isemri_id + "".toString().trim(),
                                            _cTemp.urun_adi + "",
                                            _cTemp.urun_kodu + "",
                                            _cTemp.depo_adi + "".toString().trim(),
                                            _cTemp.depo_kodu + "".toString().trim(),
                                            _cTemp.lotno + "".toString().trim(),
                                            _cTemp.kalan_agirlik + "".toString().trim(),
                                            _cTemp.palet_agirligi + "".toString().trim(),
                                            _cTemp.paletdizim_sayisi + "".toString().trim(),
                                            _cTemp.kalan_palet_sayisi + "".toString().trim(),
                                            _cTemp.miktar_torba + "".toString().trim(),
                                            _cTemp.konteyner_turu + "".toString().trim(),
                                            _cTemp.karakteristikler + "".toString().trim(),
                                            _cTemp.yapilan_miktar + "".toString().trim(),
                                            _cTemp.yapilan_adet + "".toString().trim(),
                                            _cTemp.indirmeBindirme + "".toString().trim(),
                                            _cTemp.arac_kodu + "".toString().trim(),
                                            _cTemp.arac_rfid + "".toString().trim(),
                                            _cTemp.arac_plaka + "".toString().trim(),
                                            _cTemp.kont_kodu + "".toString().trim(),
                                            _cTemp.kont_rfid + "".toString().trim(),
                                            _cTemp.rota_id + "".toString().trim(),
                                            _cTemp.id_aracisemri + "".toString().trim(),
                                            _cTemp.kod_sap + "".toString().trim(),
                                            _cTemp.kayıt_parametresi + "".toString().trim(),
                                            _cTemp.isemri_tipi + "".toString().trim(),
                                            _cTemp.isemri_tipi_alt + "".toString().trim(),
                                            _cTemp.islem_durumu + "".toString().trim(),
                                            _cTemp.islem_id + "".toString().trim(),
                                            _cTemp.bookingno + "".toString().trim(),
                                            _cTemp.alici_isletme + "".toString().trim(),
                                            _cTemp.alici + "".toString().trim(),
                                            _cTemp.aktif_pasif + "".toString().trim(),
                                            _cTemp.hedef_isletme_kodu + "".toString().trim(),
                                            _cTemp.hedef_isletme_alt_kodu + "".toString().trim(),
                                            _cTemp.hedef_depo + "".toString().trim(),
                                            _cTemp.hedef_isletme_esleme + "".toString().trim(),
                                            _cTemp.isletme_esleme + "".toString().trim(),
                                            _cTemp.sefer_no + "".toString().trim(),
                                            _cTemp.count + "".toString().trim(),
                                            _cTemp.dolu_konteyner_sayisi + "",
                                            _cTemp.bos_konteyner_sayisi + "",
                                            _cTemp.dolu_konteyner_toplam_miktar + "",
                                            _cTemp.isletme + "".toString().trim(),
                                            _cTemp.alt_rota + "".toString().trim(),
                                            _cTemp.aciklama + "".toString().trim(),
                                            _cTemp.update_id + "".toString().trim(),
                                            _cTemp.vardiya + "".toString().trim()
                                            )
                                    );
                                }
                                adapter = new apmblIsEmriSecimi(dataModels, getContext());

                                _Liste.setAdapter(adapter);
                                //fn_Listele();
                            }

                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        pDialog.hide();

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
        int socketTimeout = 45000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);
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
            _OnlineUrlIsEmriDegistir = "http://" + _ayarsunucuip + ":" + _zportWifi + "/api/onaylaSevkIsDegisimi";
        } else {
            _OnlineUrl = "http://"+_ipAdresi3G+":" + _zport3G + "/api/secSevkiyatIsemriListesi";
            _OnlineUrlIsEmriDegistir = "http://"+_ipAdresi3G+":" + _zport3G + "/api/onaylaSevkIsDegisimi";
        }
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

    private class fn_btn_03 implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (_SeciliListe.getkod_sap().equals("-1")) {

            } else {
                pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE);
                pDialog.setTitleText("YÜKLENİYOR");
                pDialog.setContentText("İşlem yapılıyor. Lütfen bekleyinizi");
                //pDialog.setContentText(_TempEpc);
                pDialog.setCancelable(false);
                pDialog.setContentTextSize(22);
                pDialog.show();
                pDialog.findViewById(R.id.confirm_button).setVisibility(View.GONE);


                if (!(aktif_sevk_isemri.yapilan_adet.equals("0") || (aktif_sevk_isemri.urun_kodu.equals(_SeciliListe.geturun_kodu())))) {
                    pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    pDialog.setTitleText("İşlem Başarısız");
                    pDialog.setContentTextSize(22);
                    pDialog.setContentText("Farklı ürün yüklemesi yapılmış işemri değişimi yapamazsınız.");
                    pDialog.findViewById(R.id.confirm_button).setVisibility(View.VISIBLE);
                    //  Program.giveHataMesaji("İşlem Başarısız", "Farklı ürün yüklemesi yapılmış işemri değişimi yapamazsınız.", "Veritabanı hatası");

                } else {


                    JSONObject parametre = new JSONObject();

                    try {
                        parametre.put("_zsunucu_ip_adresi", _ayarsunucuip);
                        parametre.put("_zaktif_alt_tesis", _ayaraktifalttesis);
                        parametre.put("_zaktif_tesis", _ayaraktiftesis);
                        parametre.put("aktif_sunucu", _ayaraktifsunucu);
                        parametre.put("aktif_kullanici", _ayaraktifkullanici);
                        parametre.put("_zkullaniciadi", _zkullaniciadi);
                        parametre.put("_zsifre", _zsifre);
                        parametre.put("yeni_sevk_isemri_id", _SeciliListe.getisemri_id());
                        parametre.put("yeni_sevk_isemri_detay_id", _SeciliListe.getisemri_detay_id());
                        parametre.put("yeni_sevk_kod_sap", _SeciliListe.getkod_sap());
                        parametre.put("yeni_sevk_urun_kodu", _SeciliListe.geturun_kodu());
                        parametre.put("eski_sevk_rota_id", aktif_sevk_isemri.rota_id);
                        parametre.put("eski_sevk_id_aracisemri", aktif_sevk_isemri.id_aracisemri);
                        parametre.put("yeni_sevk_alici", _SeciliListe.getalici());
                        parametre.put("yeni_sevk_depo_kodu", _SeciliListe.getdepo_kodu());
                        parametre.put("yeni_sevk_depo_adi", _SeciliListe.getdepo_adi());
                        parametre.put("yeni_sevk_urun_adi", _SeciliListe.geturun_adi());

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
                            _OnlineUrlIsEmriDegistir,
                            parametre,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    ObjectMapper objectMapper = new ObjectMapper();

                                    try {
                                        ViewonaylaSevkIsDegisimi _Yanit = objectMapper.readValue(response.toString(), ViewonaylaSevkIsDegisimi.class);

                                        if (_Yanit._zSonuc.equals("0")) {
                                            pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                            pDialog.setTitle("HATA");
                                            pDialog.setContentTextSize(22);
                                            pDialog.setContentText(_Yanit._zHataAciklama);
                                            pDialog.findViewById(R.id.confirm_button).setVisibility(View.VISIBLE);
                                        }
                                        else {
                                            pDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                            pDialog.setTitle("BAŞARILI");
                                            pDialog.setContentTextSize(22);
                                            pDialog.setContentText("İŞLEM BAŞARILI");
                                            pDialog.findViewById(R.id.confirm_button).setVisibility(View.VISIBLE);

                                            pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sDialog)
                                                {
                                                    sDialog.dismissWithAnimation();

                                                    sDialog.hide();

                                                    frg_arac_aktivasyon fragmentyeni = new frg_arac_aktivasyon();
                                                    fragmentyeni.fn_EpcEkle(aktif_sevk_isemri.arac_rfid);
                                                    FragmentManager fragmentManager = getFragmentManager();
                                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                    fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_arac_aktivasyon").addToBackStack(null);
                                                    fragmentTransaction.commit();
                                                }});
                                        }

                                    } catch (JsonProcessingException e) {
                                        e.printStackTrace();
                                    }
                                  //  pDialog.hide();

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
                    int socketTimeout = 45000;
                    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                    request.setRetryPolicy(policy);
                    queue.add(request);
                }
            }
        }
    }
}
