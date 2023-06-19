package com.etimaden.ugr_demo;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
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
import com.etimaden.SevkiyatIslemleri.csurumkontrol;
import com.etimaden.SevkiyatIslemleri.frg_arac_bulundu;
import com.etimaden.SevkiyatIslemleri.frg_konteyner_kamyon_esleme;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cIslem.Viewsistemgiris;
import com.etimaden.cIslem.ViewtestParseClass;
import com.etimaden.cSabitDegerler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.etimaden.cSabitDegerler._sbtVerisyon;
import static com.etimaden.cSabitDegerler._zkullaniciadi;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;

public class MainActivity extends AppCompatActivity {

    public String _GirisUrl = "";
    public String _BaslangicUrl = "";

    public SweetAlertDialog pDialog;

    Button _btnCikis;
    Button _btnGiris;

    EditText _editTxtKullaniciAdi, _editTxtSifre;

    TextView _txtKullanici, _txtSifre,_txtversiyon;

    String _kullaniciAdiTut;

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

    public String deger="";
    public String _zBaglantiTuru = "";
    public String _zIpAdresi = "";

    public String sonuc="";

    public int zSonuc = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){


        }
        setContentView(R.layout.activity_main);

        _btnCikis = (Button) findViewById(R.id.btnCikis);
        _btnCikis.playSoundEffect(0);
        _btnCikis.setOnClickListener(new fn_Cikis());

        _btnGiris = (Button) findViewById(R.id.btnGiris);
        _btnGiris.setFocusable(true);
        _btnGiris.setFocusableInTouchMode(true);///add this line
        _btnGiris.requestFocus();
        _btnGiris.playSoundEffect(SoundEffectConstants.CLICK);
        _btnGiris.setOnClickListener(new fn_Giris());

        _editTxtKullaniciAdi = (EditText) findViewById(R.id.editTextAd);

        _editTxtSifre = (EditText) findViewById(R.id.editTextSifre);

        _txtKullanici = (TextView) findViewById(R.id.txtAd);
        _txtSifre = (TextView) findViewById(R.id.txtSifre);
        _txtversiyon = (TextView) findViewById(R.id.txtversiyon2);

        pDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.NORMAL_TYPE);
        pDialog.setTitleText("YÜKLENİYOR");
        pDialog.setContentText("Ayarlar yükleniyor Lütfen bekleyiniz.");
        pDialog.setCancelable(false);

        _txtversiyon.setText(_sbtVerisyon);
try
{
    pDialog.show();
}catch (Exception ex)
{

    Log.d("Hata",ex.toString());
}


        pDialog.findViewById(R.id.confirm_button).setVisibility(View.GONE);

       fn_BaslangicAyarlari();

       fn_AyarlariYukle();
    }




    private class fn_Cikis implements View.OnClickListener {
        @Override
        public void onClick(View view) {
         /*
            Intent _frmAnaSayfa=new Intent(getApplicationContext(), cSevkiyatAnaMenu.class);
            _frmAnaSayfa.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(_frmAnaSayfa);
*/
            System.exit(0);
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
            _GirisUrl = "http://"+_ayarsunucuip+":"+_zportWifi+"/api/sistemgiris";
            _BaslangicUrl = "http://"+_ayarsunucuip+":"+_zportWifi+"/api/BaslangicBilgileri";
        }
        else
        {
            _GirisUrl = "http://88.255.50.73:"+_zport3G+"/api/sistemgiris";
            _BaslangicUrl = "http://88.255.50.73:"+_zport3G+"/api/BaslangicBilgileri";
        }
    }


    public void fn_BaslangicAyarlari() {

        _myIslem = new VeriTabani(MainActivity.this);
        _myIslem.fn_Kayit();

        final ConnectivityManager connMgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        _zBaglantiTuru = "-1";

        if (wifi.isConnectedOrConnecting())
        {
            _zBaglantiTuru = "wifi";
            _zIpAdresi = fn_GetWifiIPAddress();

        } else if (mobile.isConnectedOrConnecting()) {

            _zBaglantiTuru = "3G";
            _zIpAdresi = fn_GetMobileIPAddress(true);
        }
        int _Dur = 0;
        if (_zBaglantiTuru.equals("-1")) {
            pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
            pDialog.setTitle("HATA");
            pDialog.setContentText("Herhangi bir internet bağlantısı bulunamadı");
        } else {


            if (_zBaglantiTuru.equals("wifi"))
            {
                cSetIlsletmeAyari _Ayarlar=new cSetIlsletmeAyari ();

                String _Temp=_zIpAdresi.toString().trim();

                String[] _IpAdresiDizi = _Temp.split("\\.");

                if (_IpAdresiDizi.length != 4)
                {

                    pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    pDialog.setTitle("HATA");
                    pDialog.setContentText("Kablosuz Ağ Bağlantısı Bulunamadı");
                }
                else
                {
                    String regionCode = _IpAdresiDizi[2];
                    if (regionCode.equals("160"))
                    {
                        _Ayarlar.fn_setKirkaDegirmenozu(getApplicationContext());
                    }

                    else if (regionCode.equals("80"))
                    {
                        _Ayarlar.fn_setEmetIsletme(getApplicationContext());
                    }
                    else if (regionCode.equals("88"))
                    {
                        _Ayarlar.fn_setEmetEspey(getApplicationContext());
                    }
                    else if (regionCode.equals("96"))
                    {
                        _Ayarlar.fn_setEmetHisarcik(getApplicationContext());
                    }
                    else if (regionCode.equals("104"))
                    {
                        _Ayarlar.fn_setEmetHisarcik(getApplicationContext());
                    }
                    else if (regionCode.equals("112"))
                    {

                    }
                    else if (regionCode.equals("120"))
                    {
                        _Ayarlar.fn_setEmetEmirler(getApplicationContext());
                    }
                    else if (regionCode.equals("40"))
                    {
                        _Ayarlar.fn_setBigadicIsletme(getApplicationContext());
                    }
                    else if (regionCode.equals("48"))
                    {
                        _Ayarlar.fn_setBigadicNusrat(getApplicationContext());
                    }
                    else if (regionCode.equals("56"))
                    {

                    }
                    else if (regionCode.equals("64"))
                    {
                        _Ayarlar.fn_setBigadicKestelek(getApplicationContext());
                    }
                    else if (regionCode.equals("72"))
                    {
                        _Ayarlar.fn_setBigadicKestelek(getApplicationContext());
                    }
                    else if (regionCode.equals("8"))
                    {
                        _Ayarlar.fn_setBandirmaBorAsit(getApplicationContext());
                    }
                    else if (regionCode.equals("16"))
                    {
                        _Ayarlar.fn_setBandirmaLojistik(getApplicationContext());
                        //setKırkaDegirmenozu();
                    }
                    else if (regionCode.equals("24"))
                    {

                    }
                    else if (regionCode.equals("32"))
                    {

                    }
                    else if (regionCode.equals("128"))
                    {
                        _Ayarlar.fn_setKirkaIsletme(getApplicationContext());
                        //setKırkaDegirmenozu();
                    }
                    else if (regionCode.equals("136"))
                    {
                        _Ayarlar.fn_setKirkaDegirmenozu(getApplicationContext());
                    }
                    else if (regionCode.equals("0"))
                    {
                        _Ayarlar.fn_setAnkara(getApplicationContext());
                    }
                    else
                    {
                        _Ayarlar.fn_setGenel(getApplicationContext());
                    }

                    pDialog.hide();
                    pDialog.dismiss();
                }
            } else
                {


                    _BaslangicUrl = "http://88.255.50.73:"+_zport3G+"/api/BaslangicBilgileri";

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                JSONObject parametre = new JSONObject();

                try {

                    parametre.put("_zBaglantiTuru", _zBaglantiTuru);
                    parametre.put("_zIpAdresi", _zIpAdresi);
                    parametre.put("_zkullaniciadi", _zkullaniciadi);
                    parametre.put("_zsifre", _zsifre);
                } catch (JSONException error) {
                    error.printStackTrace();


                }
int x= 1;

                JsonObjectRequest request = new JsonObjectRequest(
                        Request.Method.POST,
                        _BaslangicUrl,
                        parametre,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {

                            /*

                            String sKullanici = response.getString("_zKullaniciAdi");
                            String sAktifDepo = response.getString("_zAktifDepo");
                            String sAktifAltTesis = response.getString("_zAktif_Alt_Tesis");
                            String sAktifTesis = response.getString("_zAktif_Tesis");
                            String sAktifSunucu = response.getString("_zAktif_Sunucu");
                            String sAktifIsletmeEsleme = response.getString("_zAktifIsletmeEslesme");
                            String sSunucuIp = response.getString("_zSunucuIp");
                            String sHataAciklama = response.getString("_zHataAciklama");

                            // deger=response.getString("_zKullaniciAdi");

                            BaslangicDegerleri.settaktif_kullanici(sKullanici);
                            BaslangicDegerleri.setaktif_depo(sAktifDepo);
                            BaslangicDegerleri.setaktif_alt_tesis(sAktifAltTesis);
                            BaslangicDegerleri.setaktif_tesis(sAktifTesis);
                            BaslangicDegerleri.setaktif_sunucu(sAktifSunucu);
                            BaslangicDegerleri.setaktif_isletmeesleme(sAktifIsletmeEsleme);
                            BaslangicDegerleri.setsunucu_ip(sSunucuIp);
                            BaslangicDegerleri.setaciklama(sHataAciklama);

                            Intent intent = new Intent(MainActivity.this, Anasayfa.class);
                            intent.putExtra("_zKullaniciAdi", BaslangicDegerleri.getaktif_kullanici());
                            startActivity(intent);*/


                            String _Yanit = response.toString();


                                    String _zKullaniciAdi = response.getString("_zKullaniciAdi");
                                    String _zAktifDepo = response.getString("_zAktifDepo");
                                    String _zAktif_Alt_Tesis = response.getString("_zAktif_Alt_Tesis");
                                    String _zAktif_Tesis = response.getString("_zAktif_Tesis");
                                    String _zAktif_Sunucu = response.getString("_zAktif_Sunucu");
                                    String _zAktifIsletmeEslesme = response.getString("_zAktifIsletmeEslesme");
                                    String _zSunucuIp = response.getString("_zSunucuIp");
                                    String _zHataAciklama = response.getString("_zHataAciklama");
                                    String _zBaglantiTuru = response.getString("_zBaglantiTuru");

                                    _myIslem.fn_BaslangicAyarlari(_sbtVerisyon,_zBaglantiTuru, _zKullaniciAdi, _zAktifDepo, _zAktif_Alt_Tesis, _zAktif_Tesis, _zAktif_Sunucu, _zAktifIsletmeEslesme, _zSunucuIp);

                                    pDialog.hide();
                                    pDialog.dismiss();

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
                                    //  Toast.makeText(getApplicationContext(),deger, Toast.LENGTH_SHORT).show();

                                } catch (JSONException ex) {
                                    Toast.makeText(getApplicationContext(), "error =" + ex.toString(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                pDialog.hide();
                                Toast.makeText(getApplicationContext(), "error =" + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }


                );
                int socketTimeout = 55000;//30 seconds - change to what you want
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                request.setRetryPolicy(policy);
                queue.add(request);

            }
        }
    }

    public String fn_GetWifiIPAddress() {
        WifiManager wifiMgr = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        return Formatter.formatIpAddress(ip);
    }

    public static String fn_GetMobileIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':') < 0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim < 0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
        } // for now eat exceptions
        return "";


    }


    private class fn_Giris implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            pDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.setTitleText("YÜKLENİYOR");
            pDialog.setContentText("Kontrol ediliyor. Lütfen bekleyiniz.");
            pDialog.setCancelable(false);
            if(pDialog != null && pDialog.isShowing() ==false)
            {
               pDialog.show();

            }


            pDialog.findViewById(R.id.confirm_button).setVisibility(View.GONE);


            String _kul_kod=_editTxtKullaniciAdi.getText().toString().trim();
            String _kul_sifre = _editTxtSifre.getText().toString().trim();

            Cache cache = new DiskBasedCache(getApplicationContext().getCacheDir(), 2*1024 * 1024); // 2MB cap
            Network network = new BasicNetwork(new HurlStack());
            JSONObject parametre = new JSONObject();
            RequestQueue queue = new RequestQueue(cache, network);
            queue.start();


            try {

                parametre.put("kul_kod", _kul_kod);
                parametre.put("kul_sifre", _kul_sifre);
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
                    _GirisUrl,
                    parametre,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                ObjectMapper objectMapper = new ObjectMapper();

                                Viewsistemgiris _Yanit = objectMapper.readValue(response.toString(), Viewsistemgiris.class);

                                if (_Yanit._zSonuc.equals("0"))
                                {
                                    pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                    pDialog.setTitle("HATA");
                                    pDialog.setContentText(_Yanit._zHataAciklama);
                                }
                                else
                                {
                                    pDialog.hide();

                                    _myIslem.fn_KullaniciAdiEkle(_Yanit._zkul_kod,_Yanit._zkul_ad);

                                    /*
                                    while (pDialog.isShowing()==true)
                                    {

                                    }*/

                                    pDialog.dismiss();

                                     Intent _anasayfayaGit = new Intent(getApplicationContext(), csurumkontrol.class);
                                    _anasayfayaGit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(_anasayfayaGit);
                                }

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
                            Toast.makeText(getApplicationContext(), "error =" + error, Toast.LENGTH_SHORT).show();
                        }
                    }
            );
            int socketTimeout = 30000;//30 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            request.setRetryPolicy(policy);
            queue.add(request);
        }
    }
}
