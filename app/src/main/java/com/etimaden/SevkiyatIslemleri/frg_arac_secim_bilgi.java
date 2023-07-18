package com.etimaden.SevkiyatIslemleri;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
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
import com.etimaden.cResponseResult.Viewaktif_sevk_kalan_miktar_palet;
import com.etimaden.ugr_demo.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.etimaden.cSabitDegerler._zkullaniciadi;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;

public class frg_arac_secim_bilgi extends Fragment {

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

    public Sevkiyat_isemri aktif_sevk_isemri;

    String _OnlineUrl="";

    TextView _txt01Alici;
    TextView _txt02BookingNo;
    TextView _txt03AracPlaka;
    TextView _txt04Konteyner;
    TextView _txt05SapKodu;
    TextView _txt06UrunAdi;
    TextView _txt07TorbaAgirlik;
    TextView _txt08PaletAgirlik;
    TextView _txt09YapilanAdetMiktar;
    TextView _txt10IsEmriKalanAdetMiktar;


    public frg_arac_secim_bilgi() {
        // Required empty public constructor
    }

    public static frg_arac_secim_bilgi newInstance() {

        return new frg_arac_secim_bilgi();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_arac_secim_bilgi, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    }

    public void fn_senddata(Sevkiyat_isemri v_aktif_sevk_isemri)
    {
        this.aktif_sevk_isemri=v_aktif_sevk_isemri;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        _myIslem = new VeriTabani(getContext());

        ((GirisSayfasi) getActivity()).fn_ModRFID();

        _btngeri = (Button) getView().findViewById(R.id.btngeri);
        _btngeri.playSoundEffect(0);
        _btngeri.setOnClickListener(new fn_Geri());

        _txt01Alici=(TextView)getView().findViewById(R.id.txt01Alici);
        _txt02BookingNo=(TextView)getView().findViewById(R.id.txt02BookingNo);
        _txt03AracPlaka=(TextView)getView().findViewById(R.id.txt03AracPlaka);
        _txt04Konteyner=(TextView)getView().findViewById(R.id.txt04Konteyner);
        _txt05SapKodu=(TextView)getView().findViewById(R.id.txt05SapKodu);
        _txt06UrunAdi=(TextView)getView().findViewById(R.id.txt01Alici);
        _txt07TorbaAgirlik=(TextView)getView().findViewById(R.id.txt07TorbaAgirlik);
        _txt08PaletAgirlik=(TextView)getView().findViewById(R.id.txt08PaletAgirlik);
        _txt09YapilanAdetMiktar=(TextView)getView().findViewById(R.id.txt09YapilanAdetMiktar);
        _txt10IsEmriKalanAdetMiktar=(TextView)getView().findViewById(R.id.txt10IsEmriKalanAdetMiktar);

        fn_AyarlariYukle();

        fn_ServisBilgileriCek();
    }

    private void fn_ServisBilgileriCek() {


        pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE);
        pDialog.setTitleText("YÜKLENİYOR");
        pDialog.setContentText(" Kontrol ediliyor Lütfen bekleyiniz.");
        //pDialog.setContentText(_TempEpc);
        pDialog.setCancelable(false);
        pDialog.show();
        pDialog.findViewById(R.id.confirm_button).setVisibility(View.GONE);

        Cache cache = new DiskBasedCache(getContext().getCacheDir(), 2*1024 * 1024); // 2MB cap
        Network network = new BasicNetwork(new HurlStack());
        JSONObject parametre = new JSONObject();
        RequestQueue queue = new RequestQueue(cache, network);
        queue.start();

        try {

            parametre.put("isd_id", aktif_sevk_isemri.isemri_detay_id);
            parametre.put("harekettipi", aktif_sevk_isemri.indirmeBindirme);
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

                            Viewaktif_sevk_kalan_miktar_palet _Yanit = objectMapper.readValue(response.toString(), Viewaktif_sevk_kalan_miktar_palet.class);

                            if (_Yanit._zSonuc.equals("0"))
                            {
                                pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                pDialog.setTitle("HATA");
                                pDialog.setContentText(_Yanit._zHataAciklama);
                            }
                            else
                            {
                                aktif_sevk_isemri.kalan_agirlik = _Yanit.kalan_miktar_palet.get(0);
                                aktif_sevk_isemri.kalan_palet_sayisi = _Yanit.kalan_miktar_palet.get(1);

                                pDialog.hide();

                                fn_DegerleriYaz();
                            }

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

    private void fn_DegerleriYaz() {

        if (aktif_sevk_isemri.alici_isletme.equals(""))
        {
            _txt01Alici.setText("ALICI : " +aktif_sevk_isemri.alici);
            _txt02BookingNo.setText("BOOKING NO : " +aktif_sevk_isemri.bookingno);
        }
        else
        {
            _txt01Alici.setText("ALICI : " +aktif_sevk_isemri.alici_isletme);
            _txt02BookingNo.setText("");
        }

        _txt03AracPlaka.setText("ARAÇ PLAKASI : " + aktif_sevk_isemri.arac_plaka);

        if (!aktif_sevk_isemri.konteyner_turu.equals(""))
        {
            _txt04Konteyner.setText("KONTEYNER  : " +aktif_sevk_isemri.kont_kodu);
        }

        _txt05SapKodu.setText("SAP KODU : " +aktif_sevk_isemri.kod_sap);
        _txt06UrunAdi.setText( "ÜRÜN ADI : " +  aktif_sevk_isemri.urun_adi);
        _txt07TorbaAgirlik.setText("TORBA AĞIRLIĞI : " + aktif_sevk_isemri.miktar_torba);
        _txt08PaletAgirlik.setText("PALET AĞIRLIĞI : " + aktif_sevk_isemri.palet_agirligi);
        _txt09YapilanAdetMiktar.setText("YAPILAN ADET / MİKTAR : " +aktif_sevk_isemri.yapilan_adet + " / " + aktif_sevk_isemri.yapilan_miktar);
        _txt10IsEmriKalanAdetMiktar.setText("İŞ EMRİ KALAN ADET / MİKTAR : " +aktif_sevk_isemri.kalan_palet_sayisi + " / " + aktif_sevk_isemri.kalan_agirlik);
    }


    private void fn_AyarlariYukle()
    {
        ((GirisSayfasi)getActivity()).fn_ModRFID();
        _ayarbaglantituru = _myIslem.fn_baglanti_turu();
        _ayarsunucuip = _myIslem.fn_sunucu_ip();
        _ayaraktifkullanici = _myIslem.fn_aktif_kullanici();
        _ayaraktifdepo = _myIslem.fn_aktif_depo();
        _ayaraktifalttesis = _myIslem.fn_aktif_alt_tesis();
        _ayaraktiftesis = _myIslem.fn_aktif_tesis();
        _ayaraktifsunucu = _myIslem.fn_aktif_sunucu();
        _ayaraktifisletmeeslesme = _myIslem.fn_isletmeeslesme();

        if(_ayarbaglantituru.equals("wifi"))
        {
            _OnlineUrl = "http://"+_ayarsunucuip+":"+_zportWifi+"/api/aktif_sevk_kalan_miktar_palet";
        }
        else
        {
            _OnlineUrl = "http://"+_ipAdresi3G+":"+_zport3G+"/api/aktif_sevk_kalan_miktar_palet";
        }
    }

    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            frg_arac_bulundu fragmentyeni = new frg_arac_bulundu();
            fragmentyeni.fn_senddata(aktif_sevk_isemri);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_arac_bulundu").addToBackStack(null);
            fragmentTransaction.commit();

        }
    }
}
