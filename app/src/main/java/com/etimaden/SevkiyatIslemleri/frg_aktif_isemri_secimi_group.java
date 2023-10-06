package com.etimaden.SevkiyatIslemleri;

import android.os.Bundle;
import android.util.Log;
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
import com.etimaden.DataModel.mdlIsemriGroupSecimi;
import com.etimaden.DataModel.mdlIsemriSecimi;
import com.etimaden.GirisSayfasi;
import com.etimaden.adapter.apmblIsEmriGroupSecimi;
import com.etimaden.adapter.apmblIsEmriSecimi;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cIslem.ViewtestParseClass;
import com.etimaden.cResponseResult.Sevkiyat_isemri;
import com.etimaden.cResponseResult.ViewsevkDegerlendirGroup;
import com.etimaden.cResponseResult.cResultGridListe;
import com.etimaden.genel.SweetAlertDialogG;
import com.etimaden.ugr_demo.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import static com.etimaden.cSabitDegerler._zkullaniciadi;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;

public class frg_aktif_isemri_secimi_group  extends Fragment {

    SweetAlertDialogG pDialog;

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

    public ListAdapter adapter;

    public ListView _Liste;

    Button _btn_03;

    public ArrayList<HashMap<String, String>> _GorevListe;

    public ViewsevkDegerlendirGroup _Yanit;

   // Sevkiyat_isemri _cSecili = new Sevkiyat_isemri();

    ArrayList<mdlIsemriGroupSecimi> dataModels;
    public mdlIsemriGroupSecimi _Secili;

    public static frg_aktif_isemri_secimi_group newInstance() {

        return new frg_aktif_isemri_secimi_group();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_aktif_isemri_secimi_group, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        _myIslem = new VeriTabani(getContext());

        fn_AyarlariYukle();

        _myIslem.fn_EpcTemizle();

        ((GirisSayfasi) getActivity()).fn_ListeTemizle();
        ((GirisSayfasi) getActivity()).fn_GucAyarla(248);

        _btngeri = (Button) getView().findViewById(R.id.btngeri);
        _btngeri.playSoundEffect(0);
        _btngeri.setOnClickListener(new fn_Geri());

        _btn_03 = (Button) getView().findViewById(R.id.btn_03);
        _btn_03.playSoundEffect(0);
        _btn_03.setOnClickListener(new fn_Btn_03());

        _Secili=new mdlIsemriGroupSecimi("-1");

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


/*
        _Liste = (ListView) getView().findViewById(R.id.isemri_list);
        _Liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                String _DegerYazi = "";

                _DegerYazi = _GorevListe.get(position).get("kod_sap");

                for (int i = 0; i < _Yanit._zDiziaktif_sevk_isemri.size(); i++)
                {
                    if (_Yanit._zDiziaktif_sevk_isemri.get(i).kod_sap.equals(_DegerYazi))
                    {
                        _cSecili = _Yanit._zDiziaktif_sevk_isemri.get(i);
                    }
                }
            }
        });
*/

        fn_SevkDegerlendir();
    }

    private void fn_SevkDegerlendir() {
        pDialog = new SweetAlertDialogG(getContext(), SweetAlertDialogG.NORMAL_TYPE);
        pDialog.setTitleText("YÜKLENİYOR");
        pDialog.setContentText(" Kontrol ediliyor Lütfen bekleyiniz.");
        //pDialog.setContentText(_TempEpc);
        pDialog.setCancelable(false);
        pDialog.show();
        pDialog.findViewById(R.id.confirm_button).setVisibility(View.GONE);

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

        }
        catch (JSONException error)
        {
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

                            _Yanit = objectMapper.readValue(response.toString(), ViewsevkDegerlendirGroup.class);

                            if (_Yanit._zSonuc.equals("0"))
                            {
                                pDialog.changeAlertType(SweetAlertDialogG.ERROR_TYPE);
                                pDialog.setTitle("HATA");
                                pDialog.setContentTextSize(22);
                                pDialog.setContentText(_Yanit._zHataAciklama);
                            }
                            else
                            {
                                try
                                {
                                    _myIslem.fn_IsEmirleriTemizle();

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

                                        String v_dolu_konteyner_sayisi = _Yanit._zDiziaktif_sevk_isemri.get(i).dolu_konteyner_sayisi + "";
                                        String v_bos_konteyner_sayisi = _Yanit._zDiziaktif_sevk_isemri.get(i).bos_konteyner_sayisi + "";
                                        String v_dolu_konteyner_toplam_miktar = _Yanit._zDiziaktif_sevk_isemri.get(i).dolu_konteyner_toplam_miktar + "";
                                        String v_isletme = _Yanit._zDiziaktif_sevk_isemri.get(i).isletme + "".trim();
                                        String v_alt_rota = _Yanit._zDiziaktif_sevk_isemri.get(i).alt_rota + "".trim();
                                        String v_aciklama = _Yanit._zDiziaktif_sevk_isemri.get(i).aciklama + "".trim();
                                        String v_update_id = _Yanit._zDiziaktif_sevk_isemri.get(i).update_id + "".trim();
                                        String v_vardiya = _Yanit._zDiziaktif_sevk_isemri.get(i).vardiya + "".trim();
                                        String v_count = _Yanit._zDiziaktif_sevk_isemri.get(i).count + "";


                                        mdlIsemriGroupSecimi _Temp=new mdlIsemriGroupSecimi( v_sirano,
                                                v_isemri_detay_id+"",
                                                v_isemri_id+"",
                                                v_urun_adi+"",
                                                v_urun_kodu+"",
                                                v_depo_adi+"",
                                                v_depo_kodu+"",
                                                v_lotno+"",
                                                v_kalan_agirlik+"",
                                                v_palet_agirligi+"",
                                                v_paletdizim_sayisi+"",
                                                v_kalan_palet_sayisi+"",
                                                v_miktar_torba+"",
                                                v_konteyner_turu+"",
                                                v_karakteristikler+"",
                                                v_yapilan_miktar+"",
                                                v_yapilan_adet+"",
                                                v_indirmeBindirme+"",
                                                v_arac_kodu+"",
                                                v_arac_rfid+"",
                                                v_arac_plaka+"",
                                                v_kont_kodu+"",
                                                v_kont_rfid+"",
                                                v_rota_id+"",
                                                v_id_aracisemri+"",
                                                v_kod_sap+"",
                                                v_kayit_parametresi+"",
                                                v_isemri_tipi+"",
                                                v_isemri_tipi_alt+"",
                                                v_islem_durumu+"",
                                                v_islem_id+"",
                                                v_bookingno+"",
                                                v_alici_isletme+"",
                                                v_alici+"",
                                                v_aktif_pasif+"",
                                                v_hedef_isletme_kodu+"",
                                                v_hedef_isletme_alt_kodu+"",
                                                v_hedef_depo+"",
                                                v_hedef_isletme_esleme+"",
                                                v_isletme_esleme+"",
                                                v_sefer_no+"",
                                                v_count+"",
                                                v_dolu_konteyner_sayisi +"",
                                                v_bos_konteyner_sayisi+"",
                                                v_dolu_konteyner_toplam_miktar +"",
                                                v_isletme +"",
                                                v_alt_rota +"",
                                                v_aciklama+"",
                                                v_update_id+"",
                                                v_vardiya+"");

                                        dataModels.add(_Temp);
                                    }

                                    adapter= new apmblIsEmriGroupSecimi(dataModels,getContext());

                                    _Liste.setAdapter(adapter);

                                    //fn_Listele();
                                }
                                catch (Exception ex)
                                {
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

    private void fn_AyarlariYukle() {
        ((GirisSayfasi)getActivity()).fn_ModRFID();

        _ayarbaglantituru = _myIslem.fn_baglanti_turu();
        _ayarsunucuip = _myIslem.fn_sunucu_ip();
        _ayaraktifkullanici = _myIslem.fn_aktif_kullanici();
        _ayaraktifdepo = _myIslem.fn_aktif_depo();
        _ayaraktifalttesis = _myIslem.fn_aktif_alt_tesis();
        _ayaraktiftesis = _myIslem.fn_aktif_tesis();
        _ayaraktifsunucu = _myIslem.fn_aktif_sunucu();
        _ayaraktifisletmeeslesme = _myIslem.fn_isletmeeslesme();

        if (_ayarbaglantituru.equals("wifi")) {
            _OnlineUrl = "http://" + _ayarsunucuip + ":" + _zportWifi + "/api/sevkDegerlendirGroup";
        } else {
            _OnlineUrl = "http://88.255.50.73:" + _zport3G + "/api/sevkDegerlendirGroup";
        }
    }

    private void fn_Listele() {
        // ArrayList<HashMap<String, String>> _GorevListe = _myIslem.fn_ListeSevkListesiGrupIsEmri();
        _GorevListe = _myIslem.fn_ListeSevkListesiGrupIsEmri();

        int count = _GorevListe.size();

        adapter = new SimpleAdapter(getContext(), _GorevListe,
                R.layout.liste_isemri_secimi_grup,
                //new String[]{"Sap Kodu","Aktif Adet","Booking No","Ürün Adı","Kalan Miktar","Kalan Adet"},
                new String[]{"kod_sap", "count", "dolu_konteyner_sayisi", "dolu_konteyner_toplam_miktar", "bos_konteyner_sayisi", "bookingno", "urun_adi", "kalan_agirlik", "kalan_palet_sayisi"},
                new int[]{R.id.yazi_kod_sap, R.id.yazi_aktif_adet, R.id.yazi_dolu_konteyner_adet, R.id.yazi_dolu_konteyner_miktar, R.id.yazi_bos_konteyner_adet, R.id.yazi_bookingno, R.id.yazi_urun_adi, R.id.yazi_kalan_miktar, R.id.yazi_kalan_adet});

        _Liste.setAdapter(adapter);
    }

    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_sevkiyat_menu_panel fragmentyeni = new frg_sevkiyat_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_sevkiyat_menu_panel").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_Btn_03 implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            if (_Secili.getkod_sap().equals("-1") == false) {

                    Sevkiyat_isemri v_aktif_sevk_isemri = new Sevkiyat_isemri();
                    v_aktif_sevk_isemri.isemri_detay_id = _Secili.getisemri_detay_id();
                    v_aktif_sevk_isemri.isemri_id  = _Secili.getisemri_id();
                    v_aktif_sevk_isemri.urun_adi  = _Secili.geturun_adi();
                    v_aktif_sevk_isemri.urun_kodu = _Secili.geturun_kodu();
                    v_aktif_sevk_isemri.depo_adi  = _Secili.getdepo_adi();
                    v_aktif_sevk_isemri.depo_kodu = _Secili.getdepo_kodu();
                    v_aktif_sevk_isemri.lotno  = _Secili.getlotno();
                    v_aktif_sevk_isemri.kalan_agirlik  = _Secili.getkalan_agirlik();
                    v_aktif_sevk_isemri.palet_agirligi  = _Secili.getpalet_agirligi();
                    v_aktif_sevk_isemri.paletdizim_sayisi = _Secili.getpaletdizim_sayisi();
                    v_aktif_sevk_isemri.kalan_palet_sayisi  = _Secili.getkalan_palet_sayisi();
                    v_aktif_sevk_isemri.miktar_torba  = _Secili.getmiktar_torba();
                    v_aktif_sevk_isemri.konteyner_turu  = _Secili.getkonteyner_turu();
                    v_aktif_sevk_isemri.karakteristikler  = _Secili.getkarakteristikler();
                    v_aktif_sevk_isemri.yapilan_miktar = _Secili.getyapilan_miktar();
                    v_aktif_sevk_isemri.yapilan_adet  = _Secili.getyapilan_adet();
                    v_aktif_sevk_isemri.indirmeBindirme  = _Secili.getindirmeBindirme();
                    v_aktif_sevk_isemri.arac_kodu  = _Secili.getarac_kodu();
                    v_aktif_sevk_isemri.arac_rfid  = _Secili.getarac_rfid();
                    v_aktif_sevk_isemri.arac_plaka  = _Secili.getarac_plaka();
                    v_aktif_sevk_isemri.kont_kodu  = _Secili.getkont_kodu();
                    v_aktif_sevk_isemri.kont_rfid  = _Secili.getkont_rfid();
                    v_aktif_sevk_isemri.rota_id  = _Secili.getrota_id();
                    v_aktif_sevk_isemri.id_aracisemri  = _Secili.getid_aracisemri();
                    v_aktif_sevk_isemri.kod_sap  = _Secili.getkod_sap();
                    v_aktif_sevk_isemri.kayıt_parametresi  = _Secili.getkayit_parametresi();
                    v_aktif_sevk_isemri.isemri_tipi  = _Secili.getisemri_tipi();
                    v_aktif_sevk_isemri.isemri_tipi_alt  = _Secili.getisemri_tipi_alt();
                    v_aktif_sevk_isemri.islem_durumu = _Secili.getislem_durumu();
                    v_aktif_sevk_isemri.islem_id  = _Secili.getislem_id();
                    v_aktif_sevk_isemri.bookingno  = _Secili.getbookingno();
                    v_aktif_sevk_isemri.alici_isletme  = _Secili.getalici_isletme();
                    v_aktif_sevk_isemri.alici  = _Secili.getalici();
                    v_aktif_sevk_isemri.aktif_pasif  = _Secili.getaktif_pasif();
                    v_aktif_sevk_isemri.hedef_isletme_kodu  = _Secili.gethedef_isletme_kodu();
                    v_aktif_sevk_isemri.hedef_isletme_alt_kodu  = _Secili.gethedef_isletme_alt_kodu();
                    v_aktif_sevk_isemri.hedef_depo = _Secili.gethedef_depo();
                    v_aktif_sevk_isemri.hedef_isletme_esleme = _Secili.gethedef_isletme_esleme();
                    v_aktif_sevk_isemri.isletme_esleme  = _Secili.getisletme_esleme();
                    v_aktif_sevk_isemri.sefer_no  = _Secili.getsefer_no();
                    v_aktif_sevk_isemri.isletme  = _Secili.getisletme();
                    v_aktif_sevk_isemri.alt_rota  = _Secili.getalt_rota();
                    v_aktif_sevk_isemri.aciklama  = _Secili.getaciklama();
                    v_aktif_sevk_isemri.update_id  = _Secili.getupdate_id();
                    v_aktif_sevk_isemri.vardiya  = _Secili.getvardiya();
                    try
                    {
                        v_aktif_sevk_isemri.count  =Integer.parseInt(_Secili.getcount());

                    }catch (Exception ex)
                    {
                        v_aktif_sevk_isemri.count=0;
                    }

                    try
                    {
                        v_aktif_sevk_isemri.dolu_konteyner_sayisi  =Integer.parseInt(_Secili.getdolu_konteyner_sayisi());

                    }catch (Exception ex)
                    {
                        v_aktif_sevk_isemri.dolu_konteyner_sayisi  = 0 ;
                    }

                    try
                    {
                        v_aktif_sevk_isemri.bos_konteyner_sayisi  =Integer.parseInt(_Secili.getbos_konteyner_sayisi());

                    }catch (Exception ex)
                    {
                        v_aktif_sevk_isemri.bos_konteyner_sayisi  = 0 ;
                    }

                    try
                    {
                        v_aktif_sevk_isemri.bos_konteyner_sayisi  =Integer.parseInt(_Secili.getdolu_konteyner_sayisi());

                    }catch (Exception ex)
                    {
                        v_aktif_sevk_isemri.dolu_konteyner_toplam_miktar  = 0 ;
                    }

                if (_Secili.getindirmeBindirme().equals("0")) {

                    frg_aktif_isemri_indirme fragmentyeni = new frg_aktif_isemri_indirme();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentyeni.fn_senddata(v_aktif_sevk_isemri);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_aktif_isemri_indirme").addToBackStack(null);
                    fragmentTransaction.commit();
                } else {

                    frg_aktif_isemri_secimi fragmentyeni = new frg_aktif_isemri_secimi();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentyeni.fn_senddata(v_aktif_sevk_isemri);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_aktif_isemri_secimi").addToBackStack(null);
                    fragmentTransaction.commit();

                }

            }

/*
            if (_cSecili.kod_sap.equals("-1") == false)
            {
                if (_cSecili.indirmeBindirme.equals("0")) {

                }
                else {
                    frg_aktif_isemri_secimi fragmentyeni = new frg_aktif_isemri_secimi();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentyeni.fn_senddata(_cSecili);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_aktif_isemri_secimi").addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }

            */
        }
    }
}
