package com.etimaden.SevkiyatIslemleri;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.etimaden.GirisSayfasi;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.kurulum;
import com.etimaden.ugr_demo.R;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.etimaden.cSabitDegerler._zkullaniciadi;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;

public class csurumkontrol extends AppCompatActivity  {

    public SweetAlertDialog pDialog;

    VeriTabani _myIslem;
    String _ayaraktifkullanici="";
    String _ayaraktifdepo="";
    String _ayaraktifalttesis="";
    String _ayaraktiftesis="";
    String _ayaraktifsunucu="";
    String _ayaraktifisletmeeslesme="";
    String _ayarbaglantituru="";
    String _ayarsurum="";
    String _ayarsunucuip="";
    public String _zGuncellemeGerekli = "";
    String _OnlineUrl = "";
    String _Kurulum = "";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layoutsurumkontrol);

        _myIslem = new VeriTabani(getApplicationContext());

        fn_AyarlariYukle();

        fn_SurumDegerGetir();
    }

    private void fn_AyarlariYukle()
    {

        Log.d("_ayarbaglantituru",_ayarbaglantituru);
        _ayarbaglantituru = _myIslem.fn_baglanti_turu();
        _ayarsunucuip = _myIslem.fn_sunucu_ip();
        _ayaraktifkullanici = _myIslem.fn_aktif_kullanici();
        _ayaraktifdepo = _myIslem.fn_aktif_depo();
        _ayaraktifalttesis = _myIslem.fn_aktif_alt_tesis();
        _ayaraktiftesis = _myIslem.fn_aktif_tesis();
        _ayaraktifsunucu = _myIslem.fn_aktif_sunucu();
        _ayaraktifisletmeeslesme = _myIslem.fn_isletmeeslesme();
        _ayarsurum = _myIslem.fn_versiyon();

        if(_ayarbaglantituru.equals("wifi"))
        {
            _OnlineUrl = "http://"+_ayarsunucuip+":"+_zportWifi+"/api/VersiyonKontrolEt";
            _Kurulum = "https://"+_ayarsunucuip+":"+_zportWifi+"/uygulama.apk";
        }
        else
        {
            _OnlineUrl = "http://"+_ipAdresi3G+":"+_zport3G+"/api/VersiyonKontrolEt";
            _Kurulum = "http://"+_ipAdresi3G+":"+_zport3G+"/uygulama.apk";
        }
    }

    private void fn_SurumDegerGetir()
    {

        pDialog = new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE);
        pDialog.setTitleText("BEKLEYİNİZ");
        pDialog.setContentText("Sürüm kontrol edilliyor. Lütfen bekleyiniz");
        pDialog.setCancelable(false);
//        pDialog.show();
  //      pDialog.findViewById(R.id.confirm_button).setVisibility(View.GONE);


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JSONObject parametre = new JSONObject();

        try {

            parametre.put("_zsunucu_ip_adresi", _ayarsunucuip);
            parametre.put("_zaktif_tesis", _ayaraktiftesis);
            parametre.put("_zsurum", _ayarsurum);
            parametre.put("_zkullaniciadi", _zkullaniciadi  );
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

                            _zGuncellemeGerekli = response.getString("_zGuncellemeGerekli").trim();
                            fn_SurumKontrol();
                            pDialog.hide();

                            //pDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                            //pDialog.setTitleText("TAMAMLANDI");

/*
                                BaslangicDegerleri.settaktif_kullanici(response.getString(""));
                                BaslangicDegerleri.setaktif_depo(response.getString(""));
                                BaslangicDegerleri.setaktif_alt_tesis(response.getString(""));
                                BaslangicDegerleri.setaktif_tesis(response.getString(""));
                                BaslangicDegerleri.setaktif_sunucu(response.getString(""));
                                BaslangicDegerleri.setaktif_isletmeesleme(response.getString(""));
                                BaslangicDegerleri.setsunucu_ip(response.getString("_zSunucuIp"));
                                BaslangicDegerleri.setaciklama(response.getString("_zHataAciklama"));
*/
                             // Toast.makeText(getApplicationContext(),_zGuncellemeGerekli, Toast.LENGTH_SHORT).show();

                        } catch (JSONException ex) {
                            Toast.makeText(getApplicationContext(), "error =" + ex.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.hide();
                        Toast.makeText(getApplicationContext(), "error =" + error, Toast.LENGTH_SHORT).show();
                    }
                }


        );
        int socketTimeout = 55000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);
    }

    private void fn_SurumKontrol()
    {

        int _Dur = 1;


        if(_zGuncellemeGerekli.equals("1"))
        {

            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("VERSİYON UYARISI")
                    .setContentText("UYGULAMANIN YENİ SÜRÜMÜ BULUNMAKTADIR.<br><a href = '"+_Kurulum+"'>"+_Kurulum.replace("https://","")+"</a> ADRESİNDEN YENİ SÜRÜMÜ KURABİLİRSİNİZ")
                    .setConfirmText("Tamam")
                    .showCancelButton(false)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog)
                        {

                            sDialog.dismissWithAnimation();

                            Intent _anasayfayaGit = new Intent(getApplicationContext(), GirisSayfasi.class);
                            _anasayfayaGit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(_anasayfayaGit);
                        }
                    })
                    .show();


            /*

            Intent _frmAnaSayfa=new Intent(getApplicationContext(), kurulum.class);
            _frmAnaSayfa.putExtra("_OnlineUrl", _Kurulum);
            _frmAnaSayfa.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(_frmAnaSayfa);
        */

        }
        else
        {
            //Toast.makeText(getApplicationContext(),"DEvam et", Toast.LENGTH_SHORT).show();

            Intent _anasayfayaGit = new Intent(getApplicationContext(), GirisSayfasi.class);
            _anasayfayaGit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(_anasayfayaGit);
        }
    }
}
