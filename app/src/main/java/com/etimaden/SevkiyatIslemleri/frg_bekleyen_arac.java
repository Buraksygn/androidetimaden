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
import com.etimaden.GirisSayfasi;
import com.etimaden.SevkiyatIslemleri.Arac_aktivayon_islemleri.frg_arac_bulundu;
import com.etimaden.SevkiyatIslemleri.Arac_aktivayon_islemleri.frg_konteyner_kamyon_esleme;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cResponseResult.ViewAracAktivasyon;
import com.etimaden.cResponseResult.Viewbekleyen_arac;
import com.etimaden.cResponseResult.bekleyen_arac_liste;
import com.etimaden.ugr_demo.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.etimaden.cSabitDegerler._ipAdresi3G;
import static com.etimaden.cSabitDegerler._zkullaniciadi;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;


public class frg_bekleyen_arac extends Fragment {

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
    public String _OnlineUrlBekleyenArac = "";

    Button _btngeri;

    Viewbekleyen_arac _Yanit_01;

    List<bekleyen_arac_liste> _zDizi;

    public ListAdapter adapter;

    ArrayList<HashMap<String, String>> _Dizi;

    public ListView _Liste;

    public bekleyen_arac_liste _cSeciliArac;

    Button _btn_03;

    public String _OnlineUrlAracAc="";

    public frg_bekleyen_arac() {
        // Required empty public constructor
    }
    public static frg_bekleyen_arac newInstance()
    {
        return new frg_bekleyen_arac();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_bekleyen_arac, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        new VeriTabani(getContext()).fn_EpcTemizle();

        _myIslem = new VeriTabani(getContext());

        _btngeri = (Button) getView().findViewById(R.id.btncikis);
        _btngeri.playSoundEffect(0);
        _btngeri.setOnClickListener(new fn_Geri());

        _btn_03=(Button) getView().findViewById(R.id.btn_03);
        _btn_03.playSoundEffect(0);
        _btn_03.setOnClickListener(new fn_btn_03());

        fn_AyarlariYukle();

        fn_BekleyenAracListele();

        _Liste=(ListView)getView().findViewById(R.id.liste_bekleyen_arac);



        _Liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                String _DegerYazi = "";

                _DegerYazi = _Dizi.get(position).get("arac_plaka");

                for (int i = 0; i < _zDizi.size(); i++)
                {
                    if (_zDizi.get(i).kolon1.equals(_DegerYazi))
                    {
                        _cSeciliArac = _zDizi.get(i);
                    }
                }
            }
        });
    }



    private void fn_BekleyenAracListele()
    {
        _myIslem.fn_BekleyenAracListeSil();

        fn_DiziGetir( new VolleyCallBack() {
            @Override
            public void onSuccess() {

                if(_zDizi ==null)
                {

                }
                else
                {
                    for (int intSayac = 0; intSayac < _zDizi.size();intSayac++)
                    {
                        String v_arac_plaka = _zDizi.get(intSayac).kolon1;
                        String v_is_emri = _zDizi.get(intSayac).kolon3;
                        String v_rfid_kod = _zDizi.get(intSayac).kolon6;
                        String v_konteyner_plaka = _zDizi.get(intSayac).kolon2;

                        _myIslem.fn_BekleyenAracKayit(v_arac_plaka, v_is_emri,v_rfid_kod,v_konteyner_plaka);
                    }

                    try
                    {
                        pDialog.hide();

                    }catch (Exception ex_01 )
                    {

                    }

                    fn_ListeDoldur();
                }

            }
        });
    }


    private  void fn_ListeDoldur()
    {
        _Dizi = _myIslem.fn_BekleyenAracDizi();

        int count =_Dizi.size();

        adapter = new SimpleAdapter(getContext(), _Dizi,
                R.layout.liste_bekleyen_arac,
                new String[]{"arac_plaka","arac_isemri","arac_rfid_kod"},
                // new String[]{"kod_sap","count","dolu_konteyner_sayisi","dolu_konteyner_toplam_miktar","bos_konteyner_sayisi"},
                new int[]{R.id.yazi_arac_plaka,R.id.yazi_isemri,R.id.yazi_arac_rfid}){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View row=convertView;
                LayoutInflater inflater = getActivity().getLayoutInflater();
                if(convertView==null)
                    row = inflater.inflate(R.layout.liste_bekleyen_arac, null, true);

                // get filled view from SimpleAdapter
                View itemView=super.getView(position, convertView, parent);

                //TextView _TExt = (TextView)row.findViewById(R.id.yazi_arac_plaka);

                //ImageView imageFlag = (ImageView) row.findViewById(R.id.imageViewFlag);

                //imageFlag.setImageResource(R.drawable.greenpoint);

                return itemView;
            }

        };
        _Liste.setAdapter(adapter);
    }




    public void fn_DiziGetir(final VolleyCallBack callBack)
    {
        try
        {
            pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE);
            pDialog.setTitleText("YÜKLENİYOR");
            pDialog.setContentText(" Araç Listesi Yükleniyor.Lütfen bekleyiniz.");
            //pDialog.setContentText(_TempEpc);
            pDialog.setCancelable(false);
            pDialog.show();
            pDialog.findViewById(R.id.confirm_button).setVisibility(View.GONE);

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

                                String _TEmp=response.toString();

                                _Yanit_01 = objectMapper.readValue(response.toString(), Viewbekleyen_arac.class);

                                String _zYanit = _Yanit_01._zSonuc + "";

                                if (_zYanit.equals("0"))
                                {
                                    pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                    pDialog.setTitle("HATA 02");
                                    pDialog.setContentText(_Yanit_01._zHataAciklama);

                                    callBack.onSuccess();
                                }
                                else
                                {
                                    try
                                    {
                                        try
                                        {
                                            pDialog.hide();
                                        } catch (Exception ex)
                                        {

                                        }
                                        _zDizi = _Yanit_01._zDizi;
                                        callBack.onSuccess();

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

            // endregion
        }catch (Exception ex)
        {

        }

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
            _OnlineUrlBekleyenArac = "http://"+_ayarsunucuip+":"+_zportWifi+"/api/bekleyen_arac";
            _OnlineUrlAracAc = "http://"+_ayarsunucuip+":"+_zportWifi+"/api/AracAktivasyon";
        }
        else
        {
            _OnlineUrlBekleyenArac = "http://"+_ipAdresi3G+":"+_zport3G+"/api/bekleyen_arac";
            _OnlineUrlAracAc = "http://"+_ipAdresi3G+":"+_zport3G+"/api/AracAktivasyon";
        }
    }

    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View view)
        {
            frg_arac_aktivasyon_eski fragmentyeni = new frg_arac_aktivasyon_eski();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_arac_aktivasyon").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    public interface VolleyCallBack {
        void onSuccess();
    }

    private class fn_btn_03 implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(_cSeciliArac == null)
            {
                SweetAlertDialog pHataDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                pHataDialog.setTitleText("Araç Yok");
                pHataDialog.setContentText("Lütfen araç seçiniz");
                //pDialog.setContentText(_TempEpc);
                //pHataDialog.setCancelable(false);
                pHataDialog.show();
            }
            else
            {
                fn_arac_ac(_cSeciliArac.kolon6);

            }
        }
    }


    public void fn_arac_ac(String v_epc) {
        try {
            final String _TempEpc = v_epc.substring(v_epc.length() - 24, v_epc.length());

            if (_TempEpc.startsWith("737767")) {
                pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE);
                pDialog.setTitleText("YÜKLENİYOR");
                pDialog.setContentText(_TempEpc + " Kontrol ediliyor Lütfen bekleyiniz.");
                //pDialog.setContentText(_TempEpc);
                pDialog.setCancelable(false);
                pDialog.show();
                pDialog.findViewById(R.id.confirm_button).setVisibility(View.GONE);
                // mIsReading=true;

                Cache cache = new DiskBasedCache(getContext().getCacheDir(), 2*1024 * 1024); // 2MB cap
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
                        _OnlineUrlAracAc,
                        parametre,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    // pDialog.hide();

                                    ObjectMapper objectMapper = new ObjectMapper();

                                    ViewAracAktivasyon _Yanit = objectMapper.readValue(response.toString(), ViewAracAktivasyon.class);
/*
                                    String _zSayfaAdiAciklama = response.getString("_zSayfaAdiAciklama");
                                    String _zSayfaAdi = response.getString("_zSayfaAdi");
                                    String _zHataAciklamasi = response.getString("_zHataAciklamasi");
                                    String _zYanit = response.getString("_zYanit");
                                    String _zvagon_hareket = response.getString("_zvagon_hareket");
                                    String _zaktif_sevk_isemri = response.getString("_zaktif_sevk_isemri");
*/
                                    if (_Yanit._zSonuc.equals("0"))
                                    {
                                        pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                        pDialog.setTitle("HATA");
                                        pDialog.setContentText(_Yanit._zHataAciklama);
                                    }
                                    else
                                    {



                                        JSONObject _caktif_sevk_isemri = response.getJSONObject("_zaktif_sevk_isemri");

                                        //Arac_bulundu
                                        if (_Yanit._zSayfaAdi.equals("1")) {
                                            pDialog.hide();

                                            frg_arac_bulundu fragmentyeni = new frg_arac_bulundu();
                                            fragmentyeni.fn_senddata(_Yanit._zaktif_sevk_isemri);
                                            FragmentManager fragmentManager = getFragmentManager();
                                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_arac_bulundu").addToBackStack(null);
                                            fragmentTransaction.commit();
                                        }
                                        else
                                        {
                                            //Konteyner_kamyon_esleme
                                            if (_Yanit._zSayfaAdi.equals("2"))
                                            {
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

                                } catch (JSONException ex) {
                                    Toast.makeText(getContext(), "error =" + ex.toString(), Toast.LENGTH_SHORT).show();
                                } catch (JsonMappingException ex2) {
                                    Toast.makeText(getContext(), "error =" + ex2.toString(), Toast.LENGTH_SHORT).show();
                                } catch (JsonProcessingException ex1) {
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
}
