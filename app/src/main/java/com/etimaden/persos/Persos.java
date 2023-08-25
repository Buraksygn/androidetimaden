package com.etimaden.persos;

import android.os.StrictMode;

import com.etimaden.persosclass.Urun_tag;
import com.etimaden.persosclass.etiket_no;
import com.etimaden.persosclass.uretim_detay;
import com.etimaden.persosclass.uretim_etiket;
import com.etimaden.request.request_ambalaj_tipi_secEtiket;
import com.etimaden.request.request_bos;
import com.etimaden.request.request_etiket_kontrol;
import com.etimaden.request.request_get_lot_toplami;
import com.etimaden.request.request_paketliUret;
import com.etimaden.request.request_paketliUret_otomatik;
import com.etimaden.request.request_secEtiket;
import com.etimaden.request.request_sec_etiket_no;
import com.etimaden.request.request_secetikettag;
import com.etimaden.request.request_shrink_is_emri;
import com.etimaden.request.request_shrink_onayi_al;
import com.etimaden.request.request_uretim_etiket;
import com.etimaden.request.request_uretim_iptali;
import com.etimaden.request.request_uretim_zayi;
import com.etimaden.request.request_yari_otomatik_paket_kontrol_et;
import com.etimaden.request.requestsec_etiket_uretim;
import com.etimaden.response.frg_paket_uretim_ekrani.View_ambalaj_tipi_secEtiket;
import com.etimaden.response.frg_paket_uretim_ekrani.View_bool_response;
import com.etimaden.response.frg_paket_uretim_ekrani.View_etiket_kontrol;
import com.etimaden.response.frg_paket_uretim_ekrani.View_get_lot_toplami;
import com.etimaden.response.frg_paket_uretim_ekrani.View_paketliUret;
import com.etimaden.response.frg_paket_uretim_ekrani.View_paketliUret_otomatik;
import com.etimaden.response.frg_paket_uretim_ekrani.View_secEtiket;
import com.etimaden.response.frg_paket_uretim_ekrani.View_sec_etiket_no;
import com.etimaden.response.frg_paket_uretim_ekrani.View_sec_uretim_detay;
import com.etimaden.response.frg_paket_uretim_ekrani.View_secetikettag;
import com.etimaden.response.frg_paket_uretim_ekrani.View_string_response;
import com.etimaden.response.frg_paket_uretim_ekrani.View_uretim_zayi;
import com.etimaden.response.frg_paket_uretim_ekrani.View_yari_otomatik_paket_kontrol_et;
import com.etimaden.response.frg_paket_uretim_ekrani.Viewsec_etiket_uretim;
import com.etimaden.servisbaglanti.frg_ambalaj_tipi_degisimi_Controller;
import com.etimaden.servisbaglanti.frg_geribesleme_onay_Controller;
import com.etimaden.servisbaglanti.frg_paket_uretim_ekrani_Controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Persos {

    Retrofit retrofit;


    public Persos(String _OnlineUrl) {

        StrictMode.ThreadPolicy gfgPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(gfgPolicy);

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(75, TimeUnit.SECONDS)
                .connectTimeout(75, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(_OnlineUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public View_ambalaj_tipi_secEtiket fn_ambalaj_tipi_secEtiket (request_ambalaj_tipi_secEtiket v_gelen)
    {
        View_ambalaj_tipi_secEtiket _yanit = new View_ambalaj_tipi_secEtiket();

        try
        {
            frg_ambalaj_tipi_degisimi_Controller _Servis=retrofit.create(frg_ambalaj_tipi_degisimi_Controller.class);

            Call<View_ambalaj_tipi_secEtiket> fn_Servis = _Servis.fn_ambalaj_tipi_secEtiket(v_gelen);

            Response<View_ambalaj_tipi_secEtiket> _response = fn_Servis.execute();

            if(_response.isSuccessful())
            {
                _yanit = _response.body();

                return  _yanit;
            }
            else
            {
                _yanit = new View_ambalaj_tipi_secEtiket();
                _yanit.set_tag(null);
                _yanit.set_zAciklama("");
                _yanit.set_zSonuc("0");
                _yanit.set_zHataAciklama("HTN ANDROID 24_07_2023_11_40_00 "+_response.errorBody().string());

                return _yanit;
            }

        }catch (Exception ex)
        {
            _yanit = new View_ambalaj_tipi_secEtiket();
            _yanit.set_tag(null);
            _yanit.set_zAciklama("");
            _yanit.set_zSonuc("0");
            _yanit.set_zHataAciklama("HTN ANDROID 24_07_2023_11_40_00 Sistemsel hata");

            return  _yanit;

        }

    }

    public View_secetikettag fn_secetikettag(request_secetikettag v_Gelen)
    {
        View_secetikettag _yanit = new View_secetikettag();

        try
        {
            frg_geribesleme_onay_Controller _Servis=retrofit.create(frg_geribesleme_onay_Controller.class);

            Call<View_secetikettag> fn_Servis = _Servis.fn_secDepoTanimlari(v_Gelen);

            Response<View_secetikettag> _Response = fn_Servis.execute();

            if(_Response.isSuccessful())
            {
                _yanit = _Response.body();

                return  _yanit;
            }
            else
            {
                _yanit=new View_secetikettag();
                _yanit._zSonuc="0";
                _yanit._zAciklama="";
                _yanit._zHataAciklama="Sistemsel hata";

                return _yanit;
            }

        }catch (Exception ex) {
            _yanit = new View_secetikettag();
            _yanit._zAciklama = "";
            _yanit._zHataAciklama = ex.toString();
            _yanit._zSonuc = "0";

            return _yanit;
        }
    }


    public Boolean fn_paketliUret(request_paketliUret v_gelen)
    {
        Boolean _bsonuc=false;

        View_paketliUret _yanit = new View_paketliUret();

        try
        {
            frg_paket_uretim_ekrani_Controller _Servis=retrofit.create(frg_paket_uretim_ekrani_Controller.class);

            Call<View_paketliUret> fn_Servis = _Servis.fn_paketliUret(v_gelen);

            Response<View_paketliUret> _Response = fn_Servis.execute();

            if(_Response.isSuccessful())
            {
                _yanit = _Response.body();

               if(_yanit._zSonuc.equals("0"))
               {
                   _bsonuc = false;
               }
               else
               {
                   _bsonuc = true;
               }
            }
            else
            {
                _bsonuc = false;
            }

        }catch (Exception ex )
        {
            _bsonuc = false;
        }

        return  _bsonuc;

    }

    // burası
    public Urun_tag fn_secEtiket(request_secEtiket v_Gelen)
    {
        View_secEtiket _yanit = new View_secEtiket();

        try
        {
            frg_paket_uretim_ekrani_Controller _Servis=retrofit.create(frg_paket_uretim_ekrani_Controller.class);

            Call<View_secEtiket> fn_Servis = _Servis.fn_secEtiket(v_Gelen);

            Response<View_secEtiket> _Response = fn_Servis.execute();

            if(_Response.isSuccessful())
            {
                _yanit = _Response.body();

                return  _yanit.get_tag();
            }
            else
            {
                _yanit=new View_secEtiket();
                _yanit._zSonuc="0";
                _yanit._zAciklama="";
                _yanit._zHataAciklama="Sistemsel hata";

                return null;
            }

        }catch (Exception ex)
        {
            _yanit=new View_secEtiket();
            _yanit._zAciklama="";
            _yanit._zHataAciklama=ex.toString();
            _yanit._zSonuc="0";

            return  null;
        }

    }



    public View_get_lot_toplami fn_get_lot_toplami(request_get_lot_toplami v_Gelen)
    {
        View_get_lot_toplami _yanit=new View_get_lot_toplami();

        try
        {
            frg_paket_uretim_ekrani_Controller _Servis=retrofit.create(frg_paket_uretim_ekrani_Controller.class);

            Call<View_get_lot_toplami> fn_Servis = _Servis.fn_get_lot_toplami(v_Gelen);

            Response<View_get_lot_toplami> _Response = fn_Servis.execute();

            if(_Response.isSuccessful())
            {
                _yanit = _Response.body();

                return  _yanit;
            }
            else
            {
                _yanit=new View_get_lot_toplami();
                _yanit._zSonuc="0";
                _yanit._zAciklama="";
                _yanit._zHataAciklama="Sistemsel hata";

                return _yanit;
            }

        }catch (Exception ex)
        {
            _yanit=new View_get_lot_toplami();
            _yanit._zAciklama="";
            _yanit._zHataAciklama=ex.toString();
            _yanit._zSonuc="0";

            return  _yanit;
        }
    }


    public View_paketliUret_otomatik paketliUret_otomatik(request_paketliUret_otomatik v_Gelen)
    {
        View_paketliUret_otomatik _Yanit = new View_paketliUret_otomatik();
        try
        {

            frg_paket_uretim_ekrani_Controller _Servis=retrofit.create(frg_paket_uretim_ekrani_Controller.class);

            Call<View_paketliUret_otomatik> fn_Servis = _Servis.fn_paketliUret_otomatik(v_Gelen);

            Response<View_paketliUret_otomatik> _Response = fn_Servis.execute();

            if(_Response.isSuccessful())
            {
                _Yanit = _Response.body();

                return  _Yanit;
            }
            else
            {
                _Yanit=new View_paketliUret_otomatik();
                _Yanit._bSonuc=false;
                _Yanit._zAciklama="";
                _Yanit._zSonuc="0";
                _Yanit._zHataAciklama="Sistemsel hata";

                return _Yanit;
            }

        }catch (Exception ex)
        {
            _Yanit=new View_paketliUret_otomatik();
            _Yanit._bSonuc=false;
            _Yanit._zAciklama="";
            _Yanit._zHataAciklama=ex.toString();
            _Yanit._zSonuc="0";

            return _Yanit;
        }


    }




    public String yari_otomatik_paket_kontrol_et(request_yari_otomatik_paket_kontrol_et v_Gelen)
    {
        String _Cevap ="";

        try
        {
            frg_paket_uretim_ekrani_Controller _Servis=retrofit.create(frg_paket_uretim_ekrani_Controller.class);

            Call<View_yari_otomatik_paket_kontrol_et> fn_Servis = _Servis.fn_yari_otomatik_paket_kontrol_et(v_Gelen);

            Response<View_yari_otomatik_paket_kontrol_et> _Response = fn_Servis.execute();

            if(_Response.isSuccessful())
            {
                View_yari_otomatik_paket_kontrol_et _Yanit = _Response.body();

                if(_Yanit.get_zSonuc().equals("0"))
                {
                    _Cevap = null;
                }
                else
                {
                    _Cevap= _Yanit.get_lot();
                }
            }
            else
            {
                _Cevap = null;
            }


        }catch (Exception ex)
        {

        }

        return  _Cevap;
    }

    public String etiket_kontrol(request_etiket_kontrol v_Gelen)
    {
        String _Cevap ="";

        frg_paket_uretim_ekrani_Controller _Servis=retrofit.create(frg_paket_uretim_ekrani_Controller.class);

        Call<View_etiket_kontrol> fn_Servis= _Servis.fn_etiket_kontrol(v_Gelen);

        try
        {
            Response<View_etiket_kontrol> _Response = fn_Servis.execute();

            if(_Response.isSuccessful())
            {
                View_etiket_kontrol _Yanit = _Response.body();

                if(_Yanit.get_zSonuc().equals("0"))
                {
                    _Cevap = null;
                }
                else
                {
                    _Cevap= _Yanit.get_durum();
                }
            }
            else
            {
                _Cevap = null;
            }

        }catch (Exception ex )
        {
            _Cevap="";
        }

        return  _Cevap;

    }

    public etiket_no sec_etiket_no(request_sec_etiket_no v_Gelen) {

        etiket_no _Cevap = new etiket_no();

        frg_paket_uretim_ekrani_Controller _Servis=retrofit.create(frg_paket_uretim_ekrani_Controller.class);

        Call<View_sec_etiket_no> fn_Servis = _Servis.fn_sec_etiket_no(v_Gelen);

        try
        {
            Response<View_sec_etiket_no> _Response = fn_Servis.execute();

            if(_Response.isSuccessful())
            {
                View_sec_etiket_no _Yanit = _Response.body();

                if(_Yanit.get_zSonuc().equals("0"))
                {
                    _Cevap = null;
                }
                else
                {
                    _Cevap= _Yanit.get_tag();
                }
            }
            else
            {
                _Cevap = null;
            }

        }catch (Exception ex )
        {
            _Cevap=null;

            return _Cevap;

        }

        return  _Cevap;
    }

    public uretim_etiket sec_etiket_uretim(requestsec_etiket_uretim _Param)
    {
        uretim_etiket _Cevap=new uretim_etiket();

        frg_paket_uretim_ekrani_Controller _Servis=retrofit.create(frg_paket_uretim_ekrani_Controller.class);

        Call<Viewsec_etiket_uretim> fn_Servis = _Servis.fn_sec_etiket_uretim(_Param);

        try
        {
            Response<Viewsec_etiket_uretim> _Response = fn_Servis.execute();

            if(_Response.isSuccessful())
            {
                Viewsec_etiket_uretim _Yanit = _Response.body();

              if(_Yanit.get_zSonuc().equals("0"))
              {
                  _Cevap = null;
              }
              else
              {
                  _Cevap= _Yanit.get_tag();
              }

            }else
            {
                _Cevap=null;
            }

        }catch (Exception ex)
        {
            _Cevap=null;
        }


        return _Cevap;
    }

    public Boolean fn_uretim_zayi(request_uretim_zayi v_Gelen)
    {
        View_uretim_zayi _yanit = new View_uretim_zayi();

        try
        {
            frg_paket_uretim_ekrani_Controller _Servis=retrofit.create(frg_paket_uretim_ekrani_Controller.class);

            Call<View_uretim_zayi> fn_Servis = _Servis.fn_uretim_zayi(v_Gelen);

            Response<View_uretim_zayi> _Response = fn_Servis.execute();

            if(_Response.isSuccessful())
            {
                _yanit = _Response.body();

                return  _yanit.get_uretim_zayi();
            }
            else
            {
                _yanit=new View_uretim_zayi();
                _yanit._zSonuc="0";
                _yanit._zAciklama="";
                _yanit._zHataAciklama="Sistemsel hata";

                return null;
            }

        }catch (Exception ex)
        {
            _yanit=new View_uretim_zayi();
            _yanit._zAciklama="";
            _yanit._zHataAciklama=ex.toString();
            _yanit._zSonuc="0";

            return  null;
        }

    }

    public Boolean fn_bigBag_uret(request_uretim_etiket v_Gelen)
    {
        View_bool_response _yanit;

        try
        {
            frg_paket_uretim_ekrani_Controller _Servis=retrofit.create(frg_paket_uretim_ekrani_Controller.class);

            Call<View_bool_response> fn_Servis = _Servis.fn_bigBag_uret(v_Gelen);

            Response<View_bool_response> _Response = fn_Servis.execute();

            if(_Response.isSuccessful())
            {
                _yanit = _Response.body();

                return  _yanit.get_result();
            }
            else
            {
                return null;
            }

        }catch (Exception ex)
        {
            ex.printStackTrace();
            return  null;
        }

    }

    public String fn_sec_ambalaj_degisim_toplam_harcanan_miktar(request_uretim_etiket v_Gelen)
    {
        View_string_response _yanit;

        try
        {
            frg_paket_uretim_ekrani_Controller _Servis=retrofit.create(frg_paket_uretim_ekrani_Controller.class);

            Call<View_string_response> fn_Servis = _Servis.fn_sec_ambalaj_degisim_toplam_harcanan_miktar(v_Gelen);

            Response<View_string_response> _Response = fn_Servis.execute();

            if(_Response.isSuccessful())
            {
                _yanit = _Response.body();

                return  _yanit.get_result();
            }
            else
            {
                return null;
            }

        }catch (Exception ex)
        {
            ex.printStackTrace();
            return  null;
        }

    }

    public String fn_sec_toplam_dkmlot_miktar(request_uretim_etiket v_Gelen)
    {
        View_string_response _yanit;

        try
        {
            frg_paket_uretim_ekrani_Controller _Servis=retrofit.create(frg_paket_uretim_ekrani_Controller.class);

            Call<View_string_response> fn_Servis = _Servis.fn_sec_toplam_dkmlot_miktar(v_Gelen);

            Response<View_string_response> _Response = fn_Servis.execute();

            if(_Response.isSuccessful())
            {
                _yanit = _Response.body();

                return  _yanit.get_result();
            }
            else
            {
                return null;
            }

        }catch (Exception ex)
        {
            ex.printStackTrace();
            return  null;
        }

    }

    public Boolean fn_dkmpalet_lotundan_uretim(request_uretim_etiket v_Gelen)
    {
        View_bool_response _yanit;

        try
        {
            frg_paket_uretim_ekrani_Controller _Servis=retrofit.create(frg_paket_uretim_ekrani_Controller.class);

            Call<View_bool_response> fn_Servis = _Servis.fn_dkmpalet_lotundan_uretim(v_Gelen);

            Response<View_bool_response> _Response = fn_Servis.execute();

            if(_Response.isSuccessful())
            {
                _yanit = _Response.body();

                return  _yanit.get_result();
            }
            else
            {
                return null;
            }

        }catch (Exception ex)
        {
            ex.printStackTrace();
            return  null;
        }

    }
    public Boolean fn_setShrinkIsemri(request_shrink_is_emri v_Gelen)
    {
        View_bool_response _yanit;

        try
        {
            frg_paket_uretim_ekrani_Controller _Servis=retrofit.create(frg_paket_uretim_ekrani_Controller.class);

            Call<View_bool_response> fn_Servis = _Servis.fn_setShrinkIsemri(v_Gelen);

            Response<View_bool_response> _Response = fn_Servis.execute();

            if(_Response.isSuccessful())
            {
                _yanit = _Response.body();

                return  _yanit.get_result();
            }
            else
            {
                return null;
            }

        }catch (Exception ex)
        {
            ex.printStackTrace();
            return  null;
        }

    }

    public Boolean fn_manipulasyon_bigbag_uret(request_uretim_etiket v_Gelen)
    {
        View_bool_response _yanit;

        try
        {
            frg_paket_uretim_ekrani_Controller _Servis=retrofit.create(frg_paket_uretim_ekrani_Controller.class);

            Call<View_bool_response> fn_Servis = _Servis.fn_manipulasyon_bigbag_uret(v_Gelen);

            Response<View_bool_response> _Response = fn_Servis.execute();

            if(_Response.isSuccessful())
            {
                _yanit = _Response.body();

                return  _yanit.get_result();
            }
            else
            {
                return null;
            }

        }catch (Exception ex)
        {
            ex.printStackTrace();
            return  null;
        }

    }

    public Boolean fn_shrink_onayi_al(request_shrink_onayi_al v_Gelen)
    {
        View_bool_response _yanit;

        try
        {
            frg_paket_uretim_ekrani_Controller _Servis=retrofit.create(frg_paket_uretim_ekrani_Controller.class);

            Call<View_bool_response> fn_Servis = _Servis.fn_shrink_onayi_al(v_Gelen);

            Response<View_bool_response> _Response = fn_Servis.execute();

            if(_Response.isSuccessful())
            {
                _yanit = _Response.body();

                return  _yanit.get_result();
            }
            else
            {
                return null;
            }

        }catch (Exception ex)
        {
            ex.printStackTrace();
            return  null;
        }

    }

    public Boolean fn_uretim_iptali(request_uretim_iptali v_Gelen)
    {
        View_bool_response _yanit;

        try
        {
            frg_paket_uretim_ekrani_Controller _Servis=retrofit.create(frg_paket_uretim_ekrani_Controller.class);

            Call<View_bool_response> fn_Servis = _Servis.fn_uretim_iptali(v_Gelen);

            Response<View_bool_response> _Response = fn_Servis.execute();

            if(_Response.isSuccessful())
            {
                _yanit = _Response.body();

                return  _yanit.get_result();
            }
            else
            {
                return null;
            }

        }catch (Exception ex)
        {
            ex.printStackTrace();
            return  null;
        }

    }
    public List<uretim_detay> fn_sec_uretim_detay(request_bos v_Gelen)
    {
        View_sec_uretim_detay _yanit;

        try
        {
            frg_paket_uretim_ekrani_Controller _Servis=retrofit.create(frg_paket_uretim_ekrani_Controller.class);

            Call<View_sec_uretim_detay> fn_Servis = _Servis.fn_sec_uretim_detay(v_Gelen);

            Response<View_sec_uretim_detay> _Response = fn_Servis.execute();

            if(_Response.isSuccessful())
            {
                _yanit = _Response.body();

                return  _yanit.get_uretim_detay_list();
            }
            else
            {
                return null;
            }

        }catch (Exception ex)
        {
            ex.printStackTrace();
            return  null;
        }

    }

}
