package com.etimaden.SevkiyatIslemleri;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
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
import com.etimaden.DataModel.mdlIsemriSecimi;
import com.etimaden.GirisSayfasi;
import com.etimaden.adapter.apmblIsEmriSecimi;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cResponseResult.Sevkiyat_isemri;
import com.etimaden.cResponseResult.ViewsevkDegerlendir;
import com.etimaden.ugr_demo.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.etimaden.cSabitDegerler._zkullaniciadi;

import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;

public class frg_aktif_isemri_secimi extends Fragment {

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
    public String _OnlineUrlGroup = "";
    public String _OnlineUrl_Lojistik = "";

    private static apmblIsEmriSecimi adapter;

    public ListView _Liste;

    public Sevkiyat_isemri aktif_sevk_isemri;

    //Sevkiyat_isemri _cSecili = new Sevkiyat_isemri();

    public ViewsevkDegerlendir _Yanit;

    Button _btn_03;
    ImageButton _btnYenile;

    public JSONArray _Array=new JSONArray();

  //  ArrayList<HashMap<String, String>> _GorevListe;

    ArrayList<mdlIsemriSecimi> dataModels;
    public mdlIsemriSecimi _Secili;


    public static frg_aktif_isemri_secimi newInstance() {

        return new frg_aktif_isemri_secimi();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_aktif_isemri_secimi, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        _myIslem = new VeriTabani(getContext());

        fn_AyarlariYukle();

        _Secili=new mdlIsemriSecimi("-1");

        _myIslem.fn_EpcTemizle();

        ((GirisSayfasi) getActivity()).fn_ListeTemizle();
        ((GirisSayfasi) getActivity()).fn_GucAyarla(248);

        _btngeri = (Button) getView().findViewById(R.id.btngeri);
        _btngeri.playSoundEffect(0);
        _btngeri.setOnClickListener(new fn_Geri());

        _btn_03 = (Button) getView().findViewById(R.id.btn_03);
        _btn_03.playSoundEffect(0);
        _btn_03.setOnClickListener(new fn_btn_03());

        _btnYenile= (ImageButton) getView().findViewById(R.id.btnyenile);
        _btnYenile.playSoundEffect(0);
        _btnYenile.setOnClickListener(new fn_Yenile());


        //btnyenile

       // _cSecili.kod_sap = "-1";

        _Liste=(ListView)getView().findViewById(R.id.isemri_list);

        _Liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //  mblsEmriKonteyner dataModel= dataModels.get(position);

                _Secili = dataModels.get(position);
                // _SeciliEpc = dataModel.getetiketepc();
                //Toast.makeText(getContext(), _SeciliEpc, Toast.LENGTH_SHORT).show();
            }
        });


       fn_SevkiyatlariYukle();
    }

    private void fn_SevkiyatlariYukle()
    {
        if(aktif_sevk_isemri == null || aktif_sevk_isemri.kod_sap == null || (aktif_sevk_isemri.kod_sap+"").trim().length()<4)
        {
            fn_SevkDegerlendir();
        }
        else
        {
            fn_SevkDegerlendir_Group();
        }
    }

    private void fn_SevkDegerlendir_Group() {
        pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE);
        pDialog.setTitleText("YÜKLENİYOR");
        pDialog.setContentText(" Kontrol ediliyor Lütfen bekleyiniz.");
        //pDialog.setContentText(_TempEpc);
        pDialog.setCancelable(false);
        pDialog.show();
        pDialog.findViewById(R.id.confirm_button).setVisibility(View.GONE);

        JSONObject parametre = new JSONObject();

        try {

            parametre.put("_zsunucu_ip_adresi", _ayarsunucuip);
            parametre.put("_zaktif_alt_tesis", _ayaraktifalttesis);
            parametre.put("_zaktif_tesis", _ayaraktiftesis);
            parametre.put("_zkullaniciadi", _zkullaniciadi);
            parametre.put("_zsifre", _zsifre);
            parametre.put("aktif_sunucu", _ayaraktifsunucu);
            parametre.put("aktif_kullanici", _ayaraktifkullanici);
            parametre.put("kod_sap", aktif_sevk_isemri.kod_sap);
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

                            _Yanit = objectMapper.readValue(response.toString(), ViewsevkDegerlendir.class);

                            String _zHataAciklamasi=_Yanit._zHataAciklama+"";
                            String _zYanit=_Yanit._zSonuc+"";

                            if(_zYanit.equals("0"))
                            {
                                pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                pDialog.setTitle("HATA");
                                pDialog.setContentTextSize(23);
                                pDialog.setContentText(_zHataAciklamasi);
                            }
                            else
                            {
                                try {

                                    //  _myIslem.fn_IsEmirleriTemizle();

                                    int v_sirano=0;

                                    dataModels= new ArrayList<>();

                                    for (int i = 0; i < _Yanit._zDiziaktif_sevk_isemri.size(); i++)
                                    {
                                        if( _Yanit._zDiziaktif_sevk_isemri.get(i).kod_sap.toString().equals(aktif_sevk_isemri.kod_sap))
                                        {

                                            v_sirano = v_sirano + 1;

                                            String v_isemri_detay_id = _Yanit._zDiziaktif_sevk_isemri.get(i).isemri_detay_id + "".trim();
                                            String v_isemri_id = _Yanit._zDiziaktif_sevk_isemri.get(i).isemri_id + "".trim();
                                            String v_urun_adi = _Yanit._zDiziaktif_sevk_isemri.get(i).urun_adi + "".trim();
                                            String v_urun_kodu = _Yanit._zDiziaktif_sevk_isemri.get(i).urun_kodu + "".trim();
                                            String v_depo_adi = _Yanit._zDiziaktif_sevk_isemri.get(i).depo_adi + "".trim();
                                            String v_depo_kodu = _Yanit._zDiziaktif_sevk_isemri.get(i).depo_kodu + "".trim();
                                            String v_lotno = _Yanit._zDiziaktif_sevk_isemri.get(i).lotno + "".trim();
                                            String v_kalan_agirlik = _Yanit._zDiziaktif_sevk_isemri.get(i).kalan_agirlik + "".trim();
                                            String v_palet_agirligi = _Yanit._zDiziaktif_sevk_isemri.get(i).palet_agirligi + "".trim();
                                            String v_paletdizim_sayisi = _Yanit._zDiziaktif_sevk_isemri.get(i).paletdizim_sayisi + "".trim();
                                            String v_kalan_palet_sayisi = _Yanit._zDiziaktif_sevk_isemri.get(i).kalan_palet_sayisi + "".trim();
                                            String v_miktar_torba = _Yanit._zDiziaktif_sevk_isemri.get(i).miktar_torba + "".trim();
                                            String v_konteyner_turu = _Yanit._zDiziaktif_sevk_isemri.get(i).konteyner_turu + "".trim();
                                            String v_karakteristikler = _Yanit._zDiziaktif_sevk_isemri.get(i).karakteristikler + "".trim();
                                            String v_yapilan_miktar = _Yanit._zDiziaktif_sevk_isemri.get(i).yapilan_miktar + "".trim();
                                            String v_yapilan_adet = _Yanit._zDiziaktif_sevk_isemri.get(i).yapilan_adet + "".trim();
                                            String v_indirmeBindirme = _Yanit._zDiziaktif_sevk_isemri.get(i).indirmeBindirme + "".trim();
                                            String v_arac_kodu = _Yanit._zDiziaktif_sevk_isemri.get(i).arac_kodu + "".trim();
                                            String v_arac_rfid = _Yanit._zDiziaktif_sevk_isemri.get(i).arac_rfid + "".trim();
                                            String v_arac_plaka = _Yanit._zDiziaktif_sevk_isemri.get(i).arac_plaka + "".trim();
                                            String v_kont_kodu = _Yanit._zDiziaktif_sevk_isemri.get(i).kont_kodu + "".trim();
                                            String v_kont_rfid = _Yanit._zDiziaktif_sevk_isemri.get(i).kont_rfid + "".trim();
                                            String v_rota_id = _Yanit._zDiziaktif_sevk_isemri.get(i).rota_id + "".trim();
                                            String v_id_aracisemri = _Yanit._zDiziaktif_sevk_isemri.get(i).id_aracisemri + "".trim();
                                            String v_kod_sap = _Yanit._zDiziaktif_sevk_isemri.get(i).kod_sap + "".trim();
                                            String v_kayit_parametresi = _Yanit._zDiziaktif_sevk_isemri.get(i).kayıt_parametresi + "" + "".trim();
                                            String v_isemri_tipi = _Yanit._zDiziaktif_sevk_isemri.get(i).isemri_tipi + "".trim();
                                            String v_isemri_tipi_alt = _Yanit._zDiziaktif_sevk_isemri.get(i).isemri_tipi_alt + "".trim();
                                            String v_islem_durumu = _Yanit._zDiziaktif_sevk_isemri.get(i).islem_durumu + "".trim();
                                            String v_islem_id = _Yanit._zDiziaktif_sevk_isemri.get(i).islem_id + "".trim();
                                            String v_bookingno = _Yanit._zDiziaktif_sevk_isemri.get(i).bookingno + "".trim();
                                            String v_alici_isletme = _Yanit._zDiziaktif_sevk_isemri.get(i).alici_isletme + "".trim();
                                            String v_alici = _Yanit._zDiziaktif_sevk_isemri.get(i).alici + "".trim();
                                            String v_aktif_pasif = _Yanit._zDiziaktif_sevk_isemri.get(i).aktif_pasif + "".trim();
                                            String v_hedef_isletme_kodu = _Yanit._zDiziaktif_sevk_isemri.get(i).hedef_isletme_kodu + "".trim();
                                            String v_hedef_isletme_alt_kodu = _Yanit._zDiziaktif_sevk_isemri.get(i).hedef_isletme_alt_kodu + "".trim();
                                            String v_hedef_depo = _Yanit._zDiziaktif_sevk_isemri.get(i).hedef_depo + "".trim();
                                            String v_hedef_isletme_esleme = _Yanit._zDiziaktif_sevk_isemri.get(i).hedef_isletme_esleme + "".trim();
                                            String v_isletme_esleme = _Yanit._zDiziaktif_sevk_isemri.get(i).isletme_esleme + "".trim();
                                            String v_sefer_no = _Yanit._zDiziaktif_sevk_isemri.get(i).sefer_no + "".trim();
                                            String v_count = _Yanit._zDiziaktif_sevk_isemri.get(i).count + "";
                                            String v_dolu_konteyner_sayisi = _Yanit._zDiziaktif_sevk_isemri.get(i).dolu_konteyner_sayisi + "";
                                            String v_bos_konteyner_sayisi = _Yanit._zDiziaktif_sevk_isemri.get(i).bos_konteyner_sayisi + "";
                                            String v_dolu_konteyner_toplam_miktar = _Yanit._zDiziaktif_sevk_isemri.get(i).dolu_konteyner_toplam_miktar + "";
                                            String v_isletme = _Yanit._zDiziaktif_sevk_isemri.get(i).isletme + "".trim();
                                            String v_alt_rota = _Yanit._zDiziaktif_sevk_isemri.get(i).alt_rota + "".trim();
                                            String v_aciklama = _Yanit._zDiziaktif_sevk_isemri.get(i).aciklama + "".trim();
                                            String v_update_id = _Yanit._zDiziaktif_sevk_isemri.get(i).update_id + "".trim();
                                            String v_vardiya = _Yanit._zDiziaktif_sevk_isemri.get(i).vardiya + "".trim();

                                            mdlIsemriSecimi _Temp = new mdlIsemriSecimi(v_sirano, v_isemri_detay_id, v_isemri_id, v_urun_adi, v_urun_kodu, v_depo_adi, v_depo_kodu, v_lotno, v_kalan_agirlik, v_palet_agirligi, v_paletdizim_sayisi, v_kalan_palet_sayisi, v_miktar_torba, v_konteyner_turu, v_karakteristikler, v_yapilan_miktar, v_yapilan_adet, v_indirmeBindirme, v_arac_kodu, v_arac_rfid, v_arac_plaka, v_kont_kodu, v_kont_rfid, v_rota_id, v_id_aracisemri, v_kod_sap, v_kayit_parametresi, v_isemri_tipi, v_isemri_tipi_alt, v_islem_durumu, v_islem_id, v_bookingno, v_alici_isletme, v_alici, v_aktif_pasif, v_hedef_isletme_kodu, v_hedef_isletme_alt_kodu, v_hedef_depo, v_hedef_isletme_esleme, v_isletme_esleme, v_sefer_no, v_count, v_dolu_konteyner_sayisi,
                                                    v_bos_konteyner_sayisi, v_dolu_konteyner_toplam_miktar, v_isletme, v_alt_rota, v_aciklama, v_update_id, v_vardiya);

                                            dataModels.add(_Temp);

                                        }

                                    }
                                    adapter= new apmblIsEmriSecimi (dataModels,getContext());

                                    _Liste.setAdapter(adapter);

                                }
                                catch (Exception ex)
                                {
                                    Toast.makeText(getContext(), ex.toString(), Toast.LENGTH_SHORT).show();
                                }
                                pDialog.hide();
                            }

                        } catch (JsonMappingException e)
                        {
                            e.printStackTrace();
                        } catch (JsonProcessingException e)
                        {
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

            parametre.put("_zsunucu_ip_adresi", _ayarsunucuip);
            parametre.put("_zaktif_alt_tesis", _ayaraktifalttesis);
            parametre.put("_zaktif_tesis", _ayaraktiftesis);
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

                            _Yanit = objectMapper.readValue(response.toString(), ViewsevkDegerlendir.class);

                            String _zHataAciklamasi=_Yanit._zHataAciklama+"";
                            String _zYanit=_Yanit._zSonuc+"";

                            if(_zYanit.equals("0"))
                            {
                                pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                pDialog.setTitle("HATA");
                                pDialog.setContentTextSize(23);
                                pDialog.setContentText(_zHataAciklamasi);
                            }
                            else
                            {
                                try {

                                   //  _myIslem.fn_IsEmirleriTemizle();

                                    int v_sirano=0;

                                    dataModels= new ArrayList<>();

                                    for (int i = 0; i < _Yanit._zDiziaktif_sevk_isemri.size(); i++)
                                    {
                                        v_sirano= i+1;

                                        String v_isemri_detay_id = _Yanit._zDiziaktif_sevk_isemri.get(i).isemri_detay_id + "".trim();
                                        String v_isemri_id = _Yanit._zDiziaktif_sevk_isemri.get(i).isemri_id + "".trim();
                                        String v_urun_adi = _Yanit._zDiziaktif_sevk_isemri.get(i).urun_adi + "".trim();
                                        String v_urun_kodu = _Yanit._zDiziaktif_sevk_isemri.get(i).urun_kodu + "".trim();
                                        String v_depo_adi = _Yanit._zDiziaktif_sevk_isemri.get(i).depo_adi + "".trim();
                                        String v_depo_kodu = _Yanit._zDiziaktif_sevk_isemri.get(i).depo_kodu + "".trim();
                                        String v_lotno = _Yanit._zDiziaktif_sevk_isemri.get(i).lotno + "".trim();
                                        String v_kalan_agirlik = _Yanit._zDiziaktif_sevk_isemri.get(i).kalan_agirlik + "".trim();
                                        String v_palet_agirligi = _Yanit._zDiziaktif_sevk_isemri.get(i).palet_agirligi + "".trim();
                                        String v_paletdizim_sayisi = _Yanit._zDiziaktif_sevk_isemri.get(i).paletdizim_sayisi + "".trim();
                                        String v_kalan_palet_sayisi = _Yanit._zDiziaktif_sevk_isemri.get(i).kalan_palet_sayisi + "".trim();
                                        String v_miktar_torba = _Yanit._zDiziaktif_sevk_isemri.get(i).miktar_torba + "".trim();
                                        String v_konteyner_turu = _Yanit._zDiziaktif_sevk_isemri.get(i).konteyner_turu + "".trim();
                                        String v_karakteristikler = _Yanit._zDiziaktif_sevk_isemri.get(i).karakteristikler + "".trim();
                                        String v_yapilan_miktar = _Yanit._zDiziaktif_sevk_isemri.get(i).yapilan_miktar + "".trim();
                                        String v_yapilan_adet = _Yanit._zDiziaktif_sevk_isemri.get(i).yapilan_adet + "".trim();
                                        String v_indirmeBindirme = _Yanit._zDiziaktif_sevk_isemri.get(i).indirmeBindirme + "".trim();
                                        String v_arac_kodu = _Yanit._zDiziaktif_sevk_isemri.get(i).arac_kodu + "".trim();
                                        String v_arac_rfid = _Yanit._zDiziaktif_sevk_isemri.get(i).arac_rfid + "".trim();
                                        String v_arac_plaka = _Yanit._zDiziaktif_sevk_isemri.get(i).arac_plaka + "".trim();
                                        String v_kont_kodu = _Yanit._zDiziaktif_sevk_isemri.get(i).kont_kodu + "".trim();
                                        String v_kont_rfid = _Yanit._zDiziaktif_sevk_isemri.get(i).kont_rfid + "".trim();
                                        String v_rota_id = _Yanit._zDiziaktif_sevk_isemri.get(i).rota_id + "".trim();
                                        String v_id_aracisemri = _Yanit._zDiziaktif_sevk_isemri.get(i).id_aracisemri + "".trim();
                                        String v_kod_sap = _Yanit._zDiziaktif_sevk_isemri.get(i).kod_sap + "".trim();
                                        String v_kayit_parametresi = _Yanit._zDiziaktif_sevk_isemri.get(i).kayıt_parametresi + "" + "".trim();
                                        String v_isemri_tipi = _Yanit._zDiziaktif_sevk_isemri.get(i).isemri_tipi + "".trim();
                                        String v_isemri_tipi_alt = _Yanit._zDiziaktif_sevk_isemri.get(i).isemri_tipi_alt + "".trim();
                                        String v_islem_durumu = _Yanit._zDiziaktif_sevk_isemri.get(i).islem_durumu + "".trim();
                                        String v_islem_id = _Yanit._zDiziaktif_sevk_isemri.get(i).islem_id + "".trim();
                                        String v_bookingno = _Yanit._zDiziaktif_sevk_isemri.get(i).bookingno + "".trim();
                                        String v_alici_isletme = _Yanit._zDiziaktif_sevk_isemri.get(i).alici_isletme + "".trim();
                                        String v_alici = _Yanit._zDiziaktif_sevk_isemri.get(i).alici + "".trim();
                                        String v_aktif_pasif = _Yanit._zDiziaktif_sevk_isemri.get(i).aktif_pasif + "".trim();
                                        String v_hedef_isletme_kodu = _Yanit._zDiziaktif_sevk_isemri.get(i).hedef_isletme_kodu + "".trim();
                                        String v_hedef_isletme_alt_kodu = _Yanit._zDiziaktif_sevk_isemri.get(i).hedef_isletme_alt_kodu + "".trim();
                                        String v_hedef_depo = _Yanit._zDiziaktif_sevk_isemri.get(i).hedef_depo + "".trim();
                                        String v_hedef_isletme_esleme = _Yanit._zDiziaktif_sevk_isemri.get(i).hedef_isletme_esleme + "".trim();
                                        String v_isletme_esleme = _Yanit._zDiziaktif_sevk_isemri.get(i).isletme_esleme + "".trim();
                                        String v_sefer_no = _Yanit._zDiziaktif_sevk_isemri.get(i).sefer_no + "".trim();
                                        String v_count = _Yanit._zDiziaktif_sevk_isemri.get(i).count + "";
                                        String v_dolu_konteyner_sayisi = _Yanit._zDiziaktif_sevk_isemri.get(i).dolu_konteyner_sayisi + "";
                                        String v_bos_konteyner_sayisi = _Yanit._zDiziaktif_sevk_isemri.get(i).bos_konteyner_sayisi + "";
                                        String v_dolu_konteyner_toplam_miktar = _Yanit._zDiziaktif_sevk_isemri.get(i).dolu_konteyner_toplam_miktar + "";
                                        String v_isletme = _Yanit._zDiziaktif_sevk_isemri.get(i).isletme + "".trim();
                                        String v_alt_rota = _Yanit._zDiziaktif_sevk_isemri.get(i).alt_rota + "".trim();
                                        String v_aciklama = _Yanit._zDiziaktif_sevk_isemri.get(i).aciklama + "".trim();
                                        String v_update_id = _Yanit._zDiziaktif_sevk_isemri.get(i).update_id + "".trim();
                                        String v_vardiya = _Yanit._zDiziaktif_sevk_isemri.get(i).vardiya + "".trim();

                                        mdlIsemriSecimi _Temp=new mdlIsemriSecimi( v_sirano,  v_isemri_detay_id,  v_isemri_id,  v_urun_adi,  v_urun_kodu,  v_depo_adi,  v_depo_kodu,  v_lotno,  v_kalan_agirlik,  v_palet_agirligi,  v_paletdizim_sayisi,  v_kalan_palet_sayisi,  v_miktar_torba,  v_konteyner_turu,  v_karakteristikler,  v_yapilan_miktar,  v_yapilan_adet,  v_indirmeBindirme,  v_arac_kodu,  v_arac_rfid,  v_arac_plaka,  v_kont_kodu,  v_kont_rfid,  v_rota_id,  v_id_aracisemri,  v_kod_sap,  v_kayit_parametresi,  v_isemri_tipi,  v_isemri_tipi_alt,  v_islem_durumu,  v_islem_id,  v_bookingno,  v_alici_isletme,  v_alici,  v_aktif_pasif,  v_hedef_isletme_kodu,  v_hedef_isletme_alt_kodu,  v_hedef_depo,  v_hedef_isletme_esleme,  v_isletme_esleme,  v_sefer_no,  v_count,  v_dolu_konteyner_sayisi,
                                             v_bos_konteyner_sayisi,  v_dolu_konteyner_toplam_miktar,  v_isletme,  v_alt_rota,  v_aciklama,  v_update_id,  v_vardiya);

                                        dataModels.add(_Temp);


/*
                                        _myIslem.fn_SevkListesiGrupIsEmriKayit(v_isemri_detay_id, v_isemri_id, v_urun_adi, v_urun_kodu, v_depo_adi, v_depo_kodu, v_lotno, v_kalan_agirlik, v_palet_agirligi, v_paletdizim_sayisi,
                                                v_kalan_palet_sayisi, v_miktar_torba, v_konteyner_turu, v_karakteristikler, v_yapilan_miktar, v_yapilan_adet, v_indirmeBindirme, v_arac_kodu,
                                                v_arac_rfid, v_arac_plaka, v_kont_kodu, v_kont_rfid, v_rota_id, v_id_aracisemri, v_kod_sap, v_kayit_parametresi,
                                                v_isemri_tipi, v_isemri_tipi_alt, v_islem_durumu, v_islem_id, v_bookingno, v_alici_isletme, v_alici, v_aktif_pasif, v_hedef_isletme_kodu, v_hedef_isletme_alt_kodu, v_hedef_depo, v_hedef_isletme_esleme,
                                                v_isletme_esleme, v_sefer_no, v_count, v_dolu_konteyner_sayisi, v_bos_konteyner_sayisi, v_dolu_konteyner_toplam_miktar, v_isletme, v_alt_rota, v_aciklama, v_update_id, v_vardiya);

                                    */

                                    }

                                  //  fn_Listele();

                                    adapter= new apmblIsEmriSecimi (dataModels,getContext());

                                    _Liste.setAdapter(adapter);

                                }
                                catch (Exception ex)
                                {
                                    Toast.makeText(getContext(), ex.toString(), Toast.LENGTH_SHORT).show();
                                }
                                pDialog.hide();
                            }

                        } catch (JsonMappingException e)
                        {
                            e.printStackTrace();
                        } catch (JsonProcessingException e)
                        {
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
            _OnlineUrl = "http://"+_ayarsunucuip+":"+_zportWifi+"/api/sevkDegerlendir";
            _OnlineUrlGroup = "http://"+_ayarsunucuip+":"+_zportWifi+"/api/sevkListesiKodSap";
            _OnlineUrl_Lojistik = "http://"+_ayarsunucuip+":"+_zportWifi+"/api/secAktifSevkIsemriListesi_lojistik";
        }
        else
        {
            _OnlineUrl = "http://88.255.50.73:"+_zport3G+"/api/sevkDegerlendir";
            _OnlineUrlGroup = "http://88.255.50.73:"+_zport3G+"/api/sevkListesiKodSap";
            _OnlineUrl_Lojistik = "http://88.255.50.73:"+_zport3G+"/api/secAktifSevkIsemriListesi_lojistik";
        }
    }

    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View view)
        {
            frg_aktif_arac_secimi fragmentyeni = new frg_aktif_arac_secimi();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_aktif_arac_secimi").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    public void fn_senddata(Sevkiyat_isemri v_aktif_sevk_isemri)
    {
        this.aktif_sevk_isemri=v_aktif_sevk_isemri;
    }

    private class fn_btn_03 implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            //Toast.makeText(getContext(), _SeciliKod, Toast.LENGTH_SHORT).show();

            String _Yanitlar = "";

            if (_Secili.getkod_sap().equals("-1") == false)
            {
                Sevkiyat_isemri _Param=new Sevkiyat_isemri();

                _Param.isemri_detay_id =_Secili.getisemri_detay_id();
                _Param.isemri_id  =_Secili.getisemri_id();
                _Param.urun_adi  =_Secili.geturun_adi();
                _Param.urun_kodu =_Secili.geturun_kodu();
                _Param.depo_adi  =_Secili.getdepo_adi();
                _Param.depo_kodu =_Secili.getdepo_kodu();
                _Param.lotno  =_Secili.getlotno();
                _Param.kalan_agirlik  =_Secili.getkalan_agirlik();
                _Param.palet_agirligi  =_Secili.getpalet_agirligi();
                _Param.paletdizim_sayisi =_Secili.getpaletdizim_sayisi();
                _Param.kalan_palet_sayisi  =_Secili.getkalan_palet_sayisi();
                _Param.miktar_torba  =_Secili.getmiktar_torba();
                _Param.konteyner_turu  =_Secili.getkonteyner_turu();
                _Param.karakteristikler  =_Secili.getkarakteristikler();
                _Param.yapilan_miktar =_Secili.getyapilan_miktar();
                _Param.yapilan_adet  =_Secili.getyapilan_adet();
                _Param.indirmeBindirme  =_Secili.getindirmeBindirme();
                _Param.arac_kodu  =_Secili.getarac_kodu();
                _Param.arac_rfid  =_Secili.getarac_rfid();
                _Param.arac_plaka  =_Secili.getarac_plaka();
                _Param.kont_kodu  =_Secili.getkont_kodu();
                _Param.kont_rfid  =_Secili.getkont_rfid();
                _Param.rota_id  =_Secili.getrota_id();
                _Param.id_aracisemri  =_Secili.getid_aracisemri();
                _Param.kod_sap  =_Secili.getkod_sap();
                _Param.kayıt_parametresi  =_Secili.getkayit_parametresi();
                _Param.isemri_tipi  =_Secili.getisemri_tipi();
                _Param.isemri_tipi_alt  =_Secili.getisemri_tipi_alt();
                _Param.islem_durumu =_Secili.getislem_durumu();
                _Param.islem_id  =_Secili.getislem_id();
                _Param.bookingno  =_Secili.getbookingno();
                _Param.alici_isletme  =_Secili.getalici_isletme();
                _Param.alici  =_Secili.getalici();
                _Param.aktif_pasif  =_Secili.getaktif_pasif();
                _Param.hedef_isletme_kodu  =_Secili.gethedef_isletme_kodu();
                _Param.hedef_isletme_alt_kodu  =_Secili.gethedef_isletme_alt_kodu();
                _Param.hedef_depo =_Secili.gethedef_depo();
                _Param.hedef_isletme_esleme =_Secili.gethedef_isletme_esleme();
                _Param.isletme_esleme  =_Secili.getisletme_esleme();
                _Param.sefer_no  =_Secili.getsefer_no();
                _Param.isletme  =_Secili.getisletme();
                _Param.alt_rota  =_Secili.getalt_rota();
                _Param.aciklama  =_Secili.getaciklama();
                _Param.update_id  =_Secili.getupdate_id();
                _Param.vardiya  =_Secili.getvardiya();
                _Param.count  = Integer.parseInt(_Secili.getcount());
                _Param.dolu_konteyner_sayisi  =Integer.parseInt( _Secili.getdolu_konteyner_sayisi());
                _Param.bos_konteyner_sayisi = Integer.parseInt(_Secili.getbos_konteyner_sayisi());
                _Param.dolu_konteyner_toplam_miktar =  Integer.parseInt(_Secili.getdolu_konteyner_toplam_miktar());

                if (_Secili.getindirmeBindirme().equals("0"))
                {
                    frg_aktif_isemri_indirme fragmentyeni = new frg_aktif_isemri_indirme();
                    fragmentyeni.fn_senddata(_Param);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_aktif_isemri_indirme").addToBackStack(null);
                    fragmentTransaction.commit();
                }
                else
                {
                    frg_aktif_isemri_yukleme fragmentyeni = new frg_aktif_isemri_yukleme();
                    fragmentyeni.fn_senddata(_Param);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_aktif_isemri_yukleme").addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        }
    }

    private class fn_Yenile implements View.OnClickListener {
        @Override
        public void onClick(View v)
        {
            fn_SevkiyatlariYukle();
        }
    }
}
