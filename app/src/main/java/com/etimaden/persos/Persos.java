package com.etimaden.persos;

import android.os.StrictMode;

import com.etimaden.persosclass.etiket_no;
import com.etimaden.persosclass.uretim_etiket;
import com.etimaden.request.request_etiket_kontrol;
import com.etimaden.request.request_sec_etiket_no;
import com.etimaden.request.request_yari_otomatik_paket_kontrol_et;
import com.etimaden.request.requestsec_etiket_uretim;
import com.etimaden.response.frg_paket_uretim_ekrani.View_etiket_kontrol;
import com.etimaden.response.frg_paket_uretim_ekrani.View_sec_etiket_no;
import com.etimaden.response.frg_paket_uretim_ekrani.View_yari_otomatik_paket_kontrol_et;
import com.etimaden.response.frg_paket_uretim_ekrani.Viewsec_etiket_uretim;
import com.etimaden.servisbaglanti.frg_paket_uretim_ekrani_Controller;

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

}
