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
import com.etimaden.DataModel.mblBekleyenArac;
import com.etimaden.GirisSayfasi;
import com.etimaden.adapter.apmblBekleyenArac;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cResponseResult.ViewAracAktivasyon;
import com.etimaden.cResponseResult.Viewbekleyen_arac;
import com.etimaden.ugr_demo.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.etimaden.cSabitDegerler._zkullaniciadi;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;

public class frg_arac_aktivasyon extends Fragment {

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

    Button btnBekleyenArac;

    public String _OnlineUrl = "";
    public String _OnlineUrlBekleyenArac = "";
//    public String _OnlineUrlAracAc = "";

    Viewbekleyen_arac _Yanit_01;

    String _OtomatikEpc = "-1";

    ArrayList<mblBekleyenArac> dataModels;

    mblBekleyenArac _Secili;

    public Boolean _IslemVar = false;

    public ListView _Liste;

    private static apmblBekleyenArac adapter;

    public frg_arac_aktivasyon()
    {

    }

    public static frg_arac_aktivasyon newInstance() {

        return new frg_arac_aktivasyon();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_arac_aktivasyon, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((GirisSayfasi) getActivity()).fn_ModRFID();

        _Secili = new mblBekleyenArac(0,"-1","-1","-1","-1");

        _myIslem = new VeriTabani(getContext());

        fn_AyarlariYukle();

        new VeriTabani(getContext()).fn_EpcTemizle();

        ((GirisSayfasi) getActivity()).fn_ListeTemizle();
        ((GirisSayfasi) getActivity()).fn_GucAyarla(248);

        _btngeri = (Button) getView().findViewById(R.id.btngeri);
        _btngeri.playSoundEffect(0);
        _btngeri.setOnClickListener(new fn_Geri());

        btnBekleyenArac=(Button)getView().findViewById(R.id.btnBekleyenArac);
        btnBekleyenArac.playSoundEffect(0);
        btnBekleyenArac.setOnClickListener(new fn_BekleyenArac());

        _Liste = (ListView) getView().findViewById(R.id.liste_bekleyen_arac);

        _Liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _Secili = dataModels.get(position);
            }
        });


        if(_OtomatikEpc.equals("-1") == false)
        {
            fn_arac_ac(_OtomatikEpc);
        }
        else
        {
            fn_BekleyenAracListele();
        }
    }

    private void fn_BekleyenAracListele() {
        JSONObject parametre = new JSONObject();

        try {

            //parametre.put("_zrfid", "0000000000000000");
            //parametre.put("_zrfid", "737767300020220000002884");
            parametre.put("_zsunucu_ip_adresi", _ayarsunucuip);
            parametre.put("_zaktif_alt_tesis", _ayaraktifalttesis);
            parametre.put("_zaktif_tesis", _ayaraktiftesis);
            parametre.put("_zkullaniciadi", _zkullaniciadi);
            parametre.put("_zsifre", _zsifre);
            parametre.put("aktif_sunucu", _ayaraktifsunucu);
            parametre.put("aktif_kullanici", _ayaraktifkullanici);

        } catch (JSONException error) {
            SweetAlertDialog pHataDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
            pHataDialog.setTitleText("Hata 01");
            pHataDialog.setContentText(error.toString());
            //pDialog.setContentText(_TempEpc);
            //pHataDialog.setCancelable(false);
            pHataDialog.show();
        }

        RequestQueue mRequestQueue;

        Cache cache = new DiskBasedCache(getContext().getCacheDir(), 2 * 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());

        RequestQueue queue = new RequestQueue(cache, network);
        queue.start();
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                _OnlineUrlBekleyenArac,
                parametre,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            ObjectMapper objectMapper = new ObjectMapper();

                            String _Temp = response.toString();

                            _Yanit_01 = objectMapper.readValue(response.toString(), Viewbekleyen_arac.class);

                            String _zYanit = _Yanit_01._zSonuc + "";

                            if (_zYanit.equals("0"))
                            {
                                pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                pDialog.setTitle("HATA 02");
                                pDialog.setContentText(_Yanit_01._zHataAciklama);
                            }
                            else
                            {
                                try
                                {
                                    int _Boyut = _Yanit_01._zDizi.size();

                                    dataModels= new ArrayList<>();

                                    for(int iSayac = 0;iSayac<_Boyut;iSayac++)
                                    {
                                        dataModels.add(new mblBekleyenArac(
                                                iSayac + 1,
                                                _Yanit_01._zDizi.get(iSayac).kolon1,
                                                _Yanit_01._zDizi.get(iSayac).kolon2,
                                                _Yanit_01._zDizi.get(iSayac).kolon3,
                                                _Yanit_01._zDizi.get(iSayac).kolon6
                                                )
                                        );
                                    }

                                    adapter= new apmblBekleyenArac(dataModels,getContext());

                                    _Liste.setAdapter(adapter);

                                } catch (Exception ex)
                                {
                                    SweetAlertDialog pHataDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                                    pHataDialog.setTitleText("Hata 03");
                                    pHataDialog.setContentText(ex.toString());
                                    //pDialog.setContentText(_TempEpc);
                                    //pHataDialog.setCancelable(false);
                                    pHataDialog.show();
                                }
                                //pDialog.hide();
                            }
                        }
                        catch (JsonMappingException e)
                        {
                            SweetAlertDialog pHataDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                            pHataDialog.setTitleText("Hata 04");
                            pHataDialog.setContentText(e.toString());
                            //pDialog.setContentText(_TempEpc);
                            //pHataDialog.setCancelable(false);
                            pHataDialog.show();
                        }
                        catch (JsonProcessingException e)
                        {
                            SweetAlertDialog pHataDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                            pHataDialog.setTitleText("Hata 05");
                            pHataDialog.setContentText(e.toString());
                            //pDialog.setContentText(_TempEpc);
                            //pHataDialog.setCancelable(false);
                            pHataDialog.show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try
                        {
                            pDialog.hide();
                        }catch (Exception ex)
                        {
                        }
                        SweetAlertDialog pHataDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                        pHataDialog.setTitleText("Hata 06");
                        pHataDialog.setContentText(error.toString());
                        //pDialog.setContentText(_TempEpc);
                        //pHataDialog.setCancelable(false);
                        pHataDialog.show();
                    }
                }


        );
        int socketTimeout = 45000;//30 seconds - change to what you want
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
            _OnlineUrl = "http://"+_ayarsunucuip+":"+_zportWifi+"/api/AracAktivasyon";
            _OnlineUrlBekleyenArac = "http://"+_ayarsunucuip+":"+_zportWifi+"/api/bekleyen_arac";
        }
        else
        {
            _OnlineUrl = "http://88.255.50.73:"+_zport3G+"/api/AracAktivasyon";
            _OnlineUrlBekleyenArac = "http://88.255.50.73:"+_zport3G+"/api/bekleyen_arac";
        }
    }

    private class fn_Geri implements View.OnClickListener {

        @Override
        public void onClick(View view)
        {
            frg_sevkiyat_menu_panel fragmentyeni = new frg_sevkiyat_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_sevkiyat_menu_panel").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }


    public void fn_EpcEkle(String v_epc)
    {
        _OtomatikEpc = v_epc;
    }

    public void fn_arac_ac(String v_epc) {
        try {

            if (_IslemVar == false)
            {
                _IslemVar = true;

                String _TempEpc = v_epc.substring(v_epc.length() - 24, v_epc.length());

                if (_TempEpc.startsWith("737767302"))
                {
                    pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE);
                    pDialog.setContentTextSize(25);

                    pDialog.setTitleText("YÜKLENİYOR");
                    pDialog.setContentText(_TempEpc + " Kontrol ediliyor Lütfen bekleyiniz.");
                    //pDialog.setContentText(_TempEpc);
                    pDialog.setCancelable(false);
                    pDialog.show();
                    pDialog.findViewById(R.id.confirm_button).setVisibility(View.GONE);
                    // mIsReading=true;

                    Cache cache = new DiskBasedCache(getContext().getCacheDir(), 2 * 1024 * 1024); // 2MB cap
                    Network network = new BasicNetwork(new HurlStack());
                    JSONObject parametre = new JSONObject();
                    RequestQueue queue = new RequestQueue(cache, network);
                    queue.start();

                    try {

                        parametre.put("_zrfid", _TempEpc);
                        //parametre.put("_zrfid", "737767300020220000002884");
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
                            _OnlineUrl,
                            parametre,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        // pDialog.hide();

                                        ObjectMapper objectMapper = new ObjectMapper();

                                        ViewAracAktivasyon _Yanit = objectMapper.readValue(response.toString(), ViewAracAktivasyon.class);

                                        if (_Yanit._zSonuc.equals("0"))
                                        {
                                            pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                            pDialog.setTitle("HATA");
                                            //int _Font = pDialog.getContentTextSize();

                                            pDialog.setContentText(_Yanit._zHataAciklama);
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
                                            JSONObject _caktif_sevk_isemri = response.getJSONObject("_zaktif_sevk_isemri");

                                            //Arac_bulundu
                                            if (_Yanit._zSayfaAdi.toString().equals("1")) {
                                                pDialog.hide();

                                                frg_arac_bulundu fragmentyeni = new frg_arac_bulundu();
                                                fragmentyeni.fn_senddata(_Yanit._zaktif_sevk_isemri);
                                                FragmentManager fragmentManager = getFragmentManager();
                                                FragmentTransaction fragmentTransaction = fragmentManager.
                                                        beginTransaction();
                                                fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_arac_bulundu").addToBackStack(null);
                                                fragmentTransaction.commit();
                                            } else {
                                                //Konteyner_kamyon_esleme
                                                if (_Yanit._zSayfaAdi.toString().equals("2")) {
                                                    pDialog.hide();

                                                    frg_konteyner_kamyon_esleme fragmentyeni = new frg_konteyner_kamyon_esleme();
                                                    fragmentyeni.fn_senddata(_Yanit._zaktif_sevk_isemri);
                                                    FragmentManager fragmentManager = getFragmentManager();
                                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                    fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_konteyner_kamyon_esleme").addToBackStack(null);
                                                    fragmentTransaction.commit();
                                                }
                                            }
                                        }

                                    } catch (JSONException ex)
                                    {
                                        Toast.makeText(getContext(), "error =" + ex.toString(), Toast.LENGTH_SHORT).show();
                                    } catch (JsonMappingException ex2)
                                    {
                                        Toast.makeText(getContext(), "error =" + ex2.toString(), Toast.LENGTH_SHORT).show();
                                    } catch (JsonProcessingException ex1)
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
        } catch (Exception ex) {
            SweetAlertDialog pHataDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
            pHataDialog.setTitleText("HATA");
            pHataDialog.setContentText("Hata Açıklaması" + ex.toString());
            //pDialog.setContentText(_TempEpc);
            //pHataDialog.setCancelable(false);
            pHataDialog.show();

            try {
                pDialog.hide();
            } catch (Exception exx) {

            }
        }
    }

    private class fn_BekleyenArac implements View.OnClickListener {
        @Override
        public void onClick(View view)
        {
            String _Epc = _Secili.getrfidkod();

            if(_Epc.equals("-1")==false)
            {
                fn_arac_ac(_Epc);
            }

/*

            frg_bekleyen_arac fragmentyeni = new frg_bekleyen_arac();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_bekleyen_arac").addToBackStack(null);
            fragmentTransaction.commit();
*/

        }
    }
}
