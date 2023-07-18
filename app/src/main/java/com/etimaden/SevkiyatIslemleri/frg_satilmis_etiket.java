package com.etimaden.SevkiyatIslemleri;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

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
import com.etimaden.DataModel.mblDigerEtiket;
import com.etimaden.GirisSayfasi;
import com.etimaden.adapter.apmblDigerEtiket;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cResponseResult.Viewetiket_durum_getir;
import com.etimaden.ugr_demo.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.etimaden.cSabitDegerler._ipAdresi3G;
import static com.etimaden.cSabitDegerler._zkullaniciadi;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;

public class frg_satilmis_etiket extends Fragment {

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

    TextView _txtSayi;

    public String _OnlineUrl = "";

    CountDownTimer countDownTimer;

    apmblDigerEtiket _Adapter;

    ArrayList<mblDigerEtiket> dataModels;

    public ListView _Liste;


    public static frg_satilmis_etiket newInstance() {

        return new frg_satilmis_etiket();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_satilmis_etiket , container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((GirisSayfasi) getActivity()).fn_ModRFID();
        ((GirisSayfasi) getActivity()).fn_GucAyarla(300);
        ((GirisSayfasi) getActivity()).fn_ReadModeAyarla(0);

        _txtSayi = (TextView)getView().findViewById(R.id.txtSayi);
        _txtSayi.setText("0");

        _myIslem = new VeriTabani(getContext());

        _myIslem.fn_SatilmisEtiketTabloTemizle();

        fn_AyarlariYukle();

        _Liste = (ListView) getView().findViewById(R.id.liste_satilmis_etiket);

        countDownTimer = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {

                fn_EtiketKontrolEt();

            }

            @Override
            public void onFinish() {
                // btn_basla_dur.setText("başla");

             //   countDownTimer.start();

            }
        }.start();

       // fn_OkumayaBasla();
    }


    private void fn_Listele()
    {
        ArrayList<HashMap<String, String>> _Dizim =   _myIslem.fn_DigerEtiketListele();

        dataModels= new ArrayList<>();


        for(int intSayac = 0;intSayac<_Dizim.size();intSayac++)
        {
            dataModels.add(new mblDigerEtiket(
                    (intSayac+1),
                    _Dizim.get(intSayac).get("epc")+"",
                    _Dizim.get(intSayac).get("durum")+""));

        }

        _Adapter= new apmblDigerEtiket(dataModels,getContext());

        _Liste.setAdapter(_Adapter);

        countDownTimer.cancel(); // cancel
        countDownTimer.start();  // then restart

    }


    private void fn_EtiketKontrolEt() {

        try
        {
            final String _KontrolEpc = _myIslem.fn_SiradakiEpc();

            if(_KontrolEpc.equals("0")==false)
            {
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


                    parametre.put("_zrfid", _KontrolEpc);
                }
                catch (JSONException error)
                {
                    error.printStackTrace();
                }

                RequestQueue mRequestQueue;

                Cache cache = new DiskBasedCache(getContext().getCacheDir(), 2*1024 * 1024); // 2 MB cap

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

                                    Viewetiket_durum_getir _Yanit = objectMapper.readValue(response.toString(), Viewetiket_durum_getir.class);

                                    if(_Yanit.equals("0"))
                                    {

                                    }

                                    else
                                    {
                                        try {

                                            _myIslem.fn_EtiketDurumUpdate(_KontrolEpc,_Yanit._zdurum);

                                            _txtSayi.setText(_myIslem.fn_SaglamEtiketSayisi());

                                            fn_Listele();


                                        }catch (Exception ex)
                                        {
                                            Toast.makeText(getContext(), ex.toString(), Toast.LENGTH_SHORT).show();

                                        }
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

                                Toast.makeText(getContext(), "error =" + error.toString(), Toast.LENGTH_SHORT).show();
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
                fn_Listele();
            }

        }catch (Exception ex)
        {
            fn_Listele();
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


        if(_ayarbaglantituru.equals("wifi"))
        {
            _OnlineUrl = "http://"+_ayarsunucuip+":"+_zportWifi+"/api/etiketkontrol";
        }
        else
        {
            _OnlineUrl = "http://"+_ipAdresi3G+":"+_zport3G+"/api/etiketkontrol";
        }
    }

    public void fn_RfidOkutuldu(String tempEpc)
    {

        if (tempEpc.startsWith("737767")) {
            _myIslem.fn_SatilmisEtiketInsert(tempEpc);
        }
    }
}
