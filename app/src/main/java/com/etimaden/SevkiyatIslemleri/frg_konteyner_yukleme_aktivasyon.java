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
import com.etimaden.GirisSayfasi;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cResponseResult.Sevkiyat_isemri;
import com.etimaden.cResponseResult.ViewAracAktivasyon;
import com.etimaden.genel.SweetAlertDialogG;
import com.etimaden.ugr_demo.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;


import static com.etimaden.cSabitDegerler._ipAdresi3G;
import static com.etimaden.cSabitDegerler._zkullaniciadi;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;

public class frg_konteyner_yukleme_aktivasyon  extends Fragment {

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

    public Sevkiyat_isemri aktif_sevk_isemri;

    public Boolean _IslemVar = false;
    public Boolean _Devam = true;

    public ViewAracAktivasyon _Yanit;

    public SweetAlertDialogG pDialog;

    public String _OnlineUrl = "";

    public frg_konteyner_yukleme_aktivasyon() {
        // Required empty public constructor
    }

    public static frg_konteyner_yukleme_aktivasyon newInstance()
    {
        return new frg_konteyner_yukleme_aktivasyon();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_konteyner_yukleme_aktivasyon, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        ((GirisSayfasi) getActivity()).fn_ModRFID();

        _myIslem = new VeriTabani(getContext());

        fn_AyarlariYukle();

        new VeriTabani(getContext()).fn_EpcTemizle();

        ((GirisSayfasi) getActivity()).fn_ListeTemizle();
        ((GirisSayfasi) getActivity()).fn_GucAyarla(190);


        _btngeri = (Button) getView().findViewById(R.id.btngeri);
        _btngeri.playSoundEffect(0);
        _btngeri.setOnClickListener(new fn_Geri());

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
            _OnlineUrl = "http://"+_ayarsunucuip+":"+_zportWifi+"/api/KonteynerAktivasyon";
        }
        else
        {
            _OnlineUrl = "http://"+_ipAdresi3G+":"+_zport3G+"/api/KonteynerAktivasyon";
        }
    }

    public void fn_rfidOkundu(String v_epc)
    {

        try
        {
            if(v_epc.toString().length()>=23)
            {
                final String _TempEpc=v_epc.substring(v_epc.length()-24,v_epc.length());

                if (_IslemVar == false)
                {
                    _IslemVar = true;

                    _Devam = true;

                    if(_TempEpc.startsWith("737767"))
                    {

                        pDialog = new SweetAlertDialogG(getContext(), SweetAlertDialogG.NORMAL_TYPE);
                        pDialog.setContentTextSize(25);

                        pDialog.setTitleText("YÜKLENİYOR");
                        pDialog.setContentText(_TempEpc + " Kontrol ediliyor Lütfen bekleyiniz.");
                        //pDialog.setContentText(_TempEpc);
                        pDialog.setCancelable(false);
                        pDialog.show();
                        pDialog.findViewById(R.id.confirm_button).setVisibility(View.GONE);

                        Cache cache = new DiskBasedCache(getContext().getCacheDir(), 2 * 1024 * 1024); // 2MB cap
                        Network network = new BasicNetwork(new HurlStack());
                        JSONObject parametre = new JSONObject();
                        RequestQueue queue = new RequestQueue(cache, network);
                        queue.start();

                        try
                        {
                            parametre.put("_zkullaniciadi", _zkullaniciadi);
                            parametre.put("_zsifre", _zsifre);
                            parametre.put("_zrfid", _TempEpc);
                            parametre.put("_zsunucu_ip_adresi", _ayarsunucuip);
                            parametre.put("_zaktif_alt_tesis", _ayaraktifalttesis);
                            parametre.put("_zaktif_tesis", _ayaraktiftesis);
                        }
                        catch (JSONException error) {

                            _Devam = false;

                            _IslemVar = false;

                            new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                                    .setTitleText("HATA")
                                    .setContentTextSize(25)
                                    .setContentText("Hata : " + error.getMessage())
                                    .showCancelButton(false)
                                    .show();
                        }

                        JsonObjectRequest request = new JsonObjectRequest(
                                Request.Method.POST,
                                _OnlineUrl,
                                parametre,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {

                                            ObjectMapper objectMapper = new ObjectMapper();

                                            _Yanit = objectMapper.readValue(response.toString(), ViewAracAktivasyon.class);

                                            if(_Yanit._zSonuc.equals("0"))
                                            {
                                                ((GirisSayfasi)getActivity()).fn_ListeTemizle();

                                                pDialog.changeAlertType(SweetAlertDialogG.ERROR_TYPE);
                                                pDialog.setTitle("HATA");
                                                pDialog.setContentText(_Yanit._zHataAciklama + " "+_TempEpc);

                                                pDialog.setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialogG sDialog)
                                                    {
                                                        sDialog.dismissWithAnimation();

                                                        sDialog.hide();

                                                        ((GirisSayfasi)getActivity()).fn_ListeTemizle();

                                                        _IslemVar = false;

                                                    }});

                                               // _IslemVar = false;
                                            }
                                            else
                                            {
                                                aktif_sevk_isemri= _Yanit._zaktif_sevk_isemri;

                                                pDialog.hide();

                                                String _Aciklama="KONTEYNER PLAKA : "+aktif_sevk_isemri.arac_plaka+" KONTEYNER OKUNDU İŞLEME DEVAM ETMEK İSTEDİĞİNİZDEN EMİN MİSİNİZ?";

                                                final String Arac_Plaka=aktif_sevk_isemri.arac_plaka;

                                                final String Arac_Kodu=aktif_sevk_isemri.arac_kodu;

                                                new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                                                        .setTitleText("KONTEYNER ONAY")
                                                        .setContentText(_Aciklama)
                                                        .setContentTextSize(25)
                                                        .setCancelText("Hayır")
                                                        .setConfirmText("EVET")
                                                        .showCancelButton(true)
                                                        .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                                            @Override
                                                            public void onClick(SweetAlertDialogG sDialog)
                                                            {
                                                                sDialog.dismissWithAnimation();

                                                                ((GirisSayfasi)getActivity()).fn_ListeTemizle();

                                                                sDialog.hide();

                                                                if(aktif_sevk_isemri.rota_id.equals(("")))
                                                                {
                                                                    frg_konteyner_isemri_secimi fragmentyeni = new frg_konteyner_isemri_secimi();
                                                                    fragmentyeni.fn_senddata(aktif_sevk_isemri);
                                                                    FragmentManager fragmentManager = getFragmentManager();
                                                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                                    fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_konteyner_isemri_secimi").addToBackStack(null);
                                                                    fragmentTransaction.commit();
                                                                }
                                                                else
                                                                {
                                                                    frg_konteyner_bulundu fragmentyeni = new frg_konteyner_bulundu();
                                                                    fragmentyeni.fn_senddata(aktif_sevk_isemri);
                                                                    FragmentManager fragmentManager = getFragmentManager();
                                                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                                    fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_konteyner_bulundu").addToBackStack(null);
                                                                    fragmentTransaction.commit();
                                                                }


                                                                //Konteyner_isemri_secimi
                                                                if(_Yanit._zSayfaAdi.equals("4"))
                                                                {
                                                                    frg_konteyner_isemri_secimi fragmentyeni = new frg_konteyner_isemri_secimi();
                                                                    fragmentyeni.fn_senddata(aktif_sevk_isemri);
                                                                    FragmentManager fragmentManager = getFragmentManager();
                                                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                                    fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_konteyner_isemri_secimi").addToBackStack(null);
                                                                    fragmentTransaction.commit();
                                                                }
                                                            }
                                                        })
                                                        .setCancelClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                                            @Override
                                                            public void onClick(SweetAlertDialogG sDialog)
                                                            {
                                                                sDialog.cancel();

                                                                ((GirisSayfasi)getActivity()).fn_ListeTemizle();

                                                                _IslemVar = false;
                                                            }
                                                        })
                                                        .show();
                                            }
                                            //Toast.makeText(getApplicationContext(), "_zSayfaAdiAciklama =" + _zHataAciklamasi, Toast.LENGTH_SHORT).show();

                                        } catch (JsonMappingException exx) {
                                            new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                                                    .setTitleText("HATA-02")
                                                    .setContentText(exx.toString())
                                                    .setCancelText("Hayır")
                                                    .setConfirmText("EVET")
                                                    .showCancelButton(true)
                                                    .show();
                                        } catch (JsonProcessingException exxx) {
                                            new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                                                    .setTitleText("HATA-03")
                                                    .setContentText(exxx.toString())
                                                    .setCancelText("Hayır")
                                                    .setConfirmText("EVET")
                                                    .showCancelButton(true)
                                                    .show();
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



                        // Toast.makeText(getApplicationContext(), _TempEpc, Toast.LENGTH_LONG).show();
                    }

                }




            }

        }catch (Exception ex)
        {

        }






    }


    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_sevkiyat_menu_panel fragmentyeni = new frg_sevkiyat_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_sevkiyat_menu_panel").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}

