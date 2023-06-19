package com.etimaden.UretimIslemleri;

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
import com.etimaden.SevkiyatIslemleri.ViewsecDepoTanimlari;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cResponseResult.DEPOTag;
import com.etimaden.service.response.uretim_etiket;
import com.etimaden.ugr_demo.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.etimaden.cSabitDegerler._zkullaniciadi;

import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;

public class frg_depo_secimi extends Fragment {

    SweetAlertDialog pDialog;

    VeriTabani _myIslem;

    public String _ayaraktifkullanici = "";
    public String _ayaraktifdepo = "";
    public String _ayaraktifalttesis = "";
    public String _ayaraktiftesis = "";
    public String _ayaraktifsunucu = "";
    public String _ayaraktifisletmeeslesme = "";
    public String _ayarbaglantituru = "";
    public String _ayarsunucuip = "";
    public String _ayarversiyon = "";
    public String _OnlineUrl = "";

    Button _btngeri;
    Button _btn_03;

    ViewsecDepoTanimlari _Yanit;

    ArrayList<HashMap<String, String>> _Depoliste;

    public ListAdapter adapter;

    public ListView _Liste;

    public DEPOTag _cSeciliDepo=new DEPOTag();

    uretim_etiket urun = null;

    public frg_depo_secimi() {
        // Required empty public constructor
    }
    public static frg_depo_secimi newInstance()
    {
        return new frg_depo_secimi();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_depo_secimi, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        new VeriTabani(getContext()).fn_EpcTemizle();

        _myIslem = new VeriTabani(getContext());

        fn_AyarlariYukle();

        _btngeri=(Button)getView().findViewById(R.id.btncikis);
        _btngeri.playSoundEffect(0);
        _btngeri.setOnClickListener(new fn_Geri());

        _btn_03=(Button)getView().findViewById(R.id.btn_03);
        _btn_03.playSoundEffect(0);
        _btn_03.setOnClickListener(new fn_btn03());


        //_btn_03

        fn_DepoListele();

        _cSeciliDepo.depo_id = "-1";

        _Liste=(ListView)getView().findViewById(R.id.depo_list);
        _Liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                String _DegerYazi = "";

                _DegerYazi = _Depoliste.get(position).get("depo_adi");

                for (int i = 0; i < _Yanit._DepoListesi.size(); i++)
                {
                    if (_Yanit._DepoListesi.get(i).depo_adi.equals(_DegerYazi))
                    {
                        _cSeciliDepo = _Yanit._DepoListesi.get(i);
                    }
                }
            }
        });


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
            _OnlineUrl = "http://"+_ayarsunucuip+":"+_zportWifi+"/api/secDepoTanimlari";
        }
        else
        {
            _OnlineUrl = "http://88.255.50.73:"+_zportWifi+"/api/secDepoTanimlari";
        }
    }

    private  void fn_GridDoldur()
    {
        _Depoliste = _myIslem.fn_DepoListe();

        int count =_Depoliste.size();

        adapter = new SimpleAdapter(getContext(), _Depoliste,
                R.layout.liste_depo_secimi,
                new String[]{"depo_sira","depo_id","depo_adi"},
                // new String[]{"kod_sap","count","dolu_konteyner_sayisi","dolu_konteyner_toplam_miktar","bos_konteyner_sayisi"},
                new int[]{R.id.yazi_kod_sira,R.id.yazi_kod_depo_id, R.id.yazi_kod_depo_adi});
        _Liste.setAdapter(adapter);
    }



    private void fn_DepoListele()
    {

        _myIslem.fn_DepoListeSil();

        pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE);
        pDialog.setTitleText("YÜKLENİYOR");
        pDialog.setContentText("Depo listesi yükleniyor Lütfen bekleyiniz.");
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

            parametre.put("isletme", urun.getisletme());
            parametre.put("depo_silo_secimi", urun.getdepo_silo_secimi());
            parametre.put("depo_turu","0");

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

                            _Yanit = objectMapper.readValue(response.toString(), ViewsecDepoTanimlari.class);

                            String _zHataAciklamasi=_Yanit._zHataAciklama+"";
                            String _zYanit=_Yanit._zSonuc+"";

                            if(_zYanit.equals("0"))
                            {
                                pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                pDialog.setTitle("HATA");
                                pDialog.setContentText(_zHataAciklamasi);
                            }
                            else
                            {
                                try {

                                    _myIslem.fn_DepoListeTemizle();

                                    for (int intSayac = 0; intSayac < _Yanit._DepoListesi.size();intSayac++)
                                    {
                                        String v_depo_id = _Yanit._DepoListesi.get(intSayac).depo_id+"".trim();
                                        String v_depo_adi = _Yanit._DepoListesi.get(intSayac).depo_adi+"".trim();

                                        _myIslem.fn_DepoListesiKayit(v_depo_id,v_depo_adi);
                                    }

                                    try
                                    {
                                        pDialog.hide();

                                    }catch (Exception ex_01 )
                                    {

                                    }

                                    fn_GridDoldur();
                                }
                                catch (Exception ex)
                                {
                                    try
                                    {
                                        pDialog.hide();

                                    }
                                    catch (Exception exxx) {

                                    }

                                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("HATA")
                                            .setContentText(ex.toString())
                                            .setConfirmText("TAMAM")
                                            .showCancelButton(false).show();
                                }

                            }

                        } catch (JsonMappingException e)
                        {
                            try
                            {
                                pDialog.hide();

                            }
                            catch (Exception exxx) {

                            }

                            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("HATA")
                                    .setContentText(e.toString())
                                    .setConfirmText("TAMAM")
                                    .showCancelButton(false).show();
                        } catch (JsonProcessingException e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try
                        {
                            pDialog.hide();

                        }
                        catch (Exception exxx) {

                        }

                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("HATA")
                                .setContentText(error.toString())
                                .setConfirmText("TAMAM")
                                .showCancelButton(false).show();
                    }
                }


        );
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);

    }

    public void fn_senddata(uretim_etiket v_uretim_etiket)
    {
        this.urun=v_uretim_etiket;
    }

    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_paket_uretim_ekrani fragmentyeni = new frg_paket_uretim_ekrani();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_paket_uretim_ekrani").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_btn03 implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            if(_cSeciliDepo.depo_id.equals(("-1")))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentText("Lütfen depo seçimi yapınız")
                        .setConfirmText("TAMAM")
                        .showCancelButton(false).show();

            }
            else
            {
                frg_silo_secimi fragmentyeni = new frg_silo_secimi();
                FragmentManager fragmentManager = getFragmentManager();
               fragmentyeni.fn_senddata(urun,_cSeciliDepo);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_silo_secimi").addToBackStack(null);
                fragmentTransaction.commit();
            }
        }
    }
}
