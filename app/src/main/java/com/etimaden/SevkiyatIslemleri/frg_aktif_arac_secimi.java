package com.etimaden.SevkiyatIslemleri;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.SoundEffectConstants;
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
import com.etimaden.cResponseResult.ViewsecAktifSevkIsemriListesi;
import com.etimaden.genel.Genel;
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

public class frg_aktif_arac_secimi  extends Fragment {


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

    Button _btn_05;

    public String _OnlineUrl = "";

    public frg_aktif_arac_secimi() {
        // Required empty public constructor
    }

    public static frg_aktif_arac_secimi newInstance()
    {
        return new frg_aktif_arac_secimi();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_aktif_arac_secimi, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        _myIslem=new VeriTabani(getContext());

        _myIslem.fn_EpcTemizle();

        fn_AyarlariYukle();

        ((GirisSayfasi)getActivity()).fn_ListeTemizle();

        _btngeri=(Button)getView().findViewById(R.id.btngeri);
        _btngeri.playSoundEffect(SoundEffectConstants.CLICK);
        _btngeri.setOnClickListener(new fn_Geri());

        _btn_05=(Button)getView().findViewById(R.id.btn_05);
        _btn_05.playSoundEffect(SoundEffectConstants.CLICK);
        _btn_05.setOnClickListener(new fn_btn_05());

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

            _OnlineUrl = "http://"+_ayarsunucuip+":"+_zportWifi+"/api/secAktifSevkIsemriListesi";
        }
        else
        {
            _OnlineUrl = "http://"+_ipAdresi3G+":"+_zport3G+"/api/secAktifSevkIsemriListesi";
        }
    }

    public void fn_rfidOkundu(String tempEpc) {

        pDialog = new SweetAlertDialogG(getContext(), SweetAlertDialogG.NORMAL_TYPE);
        pDialog.setTitleText("YÜKLENİYOR");
        pDialog.setContentText(tempEpc+ " Kontrol ediliyor Lütfen bekleyiniz.");
        //pDialog.setContentText(_TempEpc);
        pDialog.setCancelable(false);
        pDialog.show();
        pDialog.findViewById(R.id.confirm_button).setVisibility(View.GONE);

        JSONObject parametre = new JSONObject();


        try
        {
            parametre.put("_zkullaniciadi", _zkullaniciadi);
            parametre.put("_zsifre", _zsifre);
            parametre.put("_zrfid", tempEpc);
            parametre.put("_zsunucu_ip_adresi", _ayarsunucuip);
            parametre.put("_zaktif_alt_tesis", _ayaraktifalttesis);
            parametre.put("_zaktif_tesis", _ayaraktiftesis);
            parametre.put("aktif_sunucu", _ayaraktifsunucu);
            parametre.put("aktif_kullanici", _ayaraktifkullanici);

            //parametre.put("aktif_sunucu", _ayaraktifsunucu);
            //parametre.put("aktif_kullanici", _ayaraktifkullanici);

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

                            ViewsecAktifSevkIsemriListesi _Yanit = objectMapper.readValue(response.toString(), ViewsecAktifSevkIsemriListesi.class);

                           // String _Asd = _Yanit._zaktif_sevk_isemri.arac_kodu;

                            if(_Yanit._zSonuc.equals("0"))
                            {
                                pDialog.changeAlertType(SweetAlertDialogG.ERROR_TYPE);
                                pDialog.setTitle("HATA");
                                pDialog.setContentText(_Yanit._zHataAciklama);
                            }
                            else
                            {
                                pDialog.hide();


                                if(_Yanit._zsevkisemi.indirmeBindirme.equals(("")))
                                {
                                    pDialog.changeAlertType(SweetAlertDialogG.ERROR_TYPE);
                                    pDialog.setTitle("HATA");
                                    pDialog.setContentText(" HTN 01:Aktif araç işemri bulunamadı.. Araç kantardan geçiş işlemini tamamlamamış.");

                                }
                                else
                                {
                                    if(_Yanit._zsevkisemi.indirmeBindirme.equals("0"))
                                    {

                                        frg_aktif_isemri_indirme fragmentyeni = new frg_aktif_isemri_indirme();
                                        fragmentyeni.fn_senddata(_Yanit._zsevkisemi);
                                        FragmentManager fragmentManager = getFragmentManager();
                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                        fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_aktif_isemri_indirme").addToBackStack(null);
                                        fragmentTransaction.commit();

                                    }
                                    else
                                    {
                                        frg_aktif_isemri_yukleme fragmentyeni = new frg_aktif_isemri_yukleme();
                                        fragmentyeni.fn_senddata(_Yanit._zsevkisemi);
                                        FragmentManager fragmentManager = getFragmentManager();
                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                        fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_aktif_isemri_yukleme").addToBackStack(null);
                                        fragmentTransaction.commit();
                                    }
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

    private class fn_btn_05 implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Genel.lockButtonClick(view,getActivity());
            if (_ayaraktiftesis.toString().equals("5002"))
            {

                frg_aktif_isemri_secimi_group fragmentyeni = new frg_aktif_isemri_secimi_group();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_aktif_isemri_secimi_group").addToBackStack(null);
                fragmentTransaction.commit();

                //Program.setPage(new Aktif_isemri_secimi_group());
            }

            else
                {
                    frg_aktif_isemri_secimi fragmentyeni = new frg_aktif_isemri_secimi();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_aktif_isemri_secimi").addToBackStack(null);
                    fragmentTransaction.commit();
                    //Program.setPage(new Aktif_isemri_secimi());
            }

        }
    }
}
