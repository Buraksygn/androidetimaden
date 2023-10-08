package com.etimaden.persos;

import android.content.Context;
import android.os.StrictMode;

import com.etimaden.cResponseResult.Urun_sevkiyat;
import com.etimaden.cResponseResult.Viewsec_sevkiyat_urun;
import com.etimaden.genel.Genel;
import com.etimaden.persosclass.Arac;
import com.etimaden.cResponseResult.Sevkiyat_isemri;
import com.etimaden.cResponseResult.View_sec_sevk_miktar;
import com.etimaden.persosclass.DEPOTag;
import com.etimaden.persosclass.Urun_tag;
import com.etimaden.persosclass.Vagon_hareket;
import com.etimaden.persosclass.Zayi;
import com.etimaden.persosclass.Zayi_urun;
import com.etimaden.persosclass.cBekleyen_Arac_Listesi;
import com.etimaden.persosclass.etiket_no;
import com.etimaden.persosclass.uretim_detay;
import com.etimaden.persosclass.uretim_etiket;
import com.etimaden.request.request_ambalaj_tipi_secEtiket;
import com.etimaden.request.request_bos;
import com.etimaden.request.request_bos_aktif_isletme_esleme;
import com.etimaden.request.request_get_lot_toplami;
import com.etimaden.request.request_guncelle_sevk_hareket;
import com.etimaden.request.request_paketliUret;
import com.etimaden.request.request_paketliUretKontrol;
import com.etimaden.request.request_paketliUret_otomatik;
import com.etimaden.request.request_secEtiket;
import com.etimaden.request.request_sevkiyat_aktarim;
import com.etimaden.request.request_sevkiyat_eski_sevk_yeni_sevk;
import com.etimaden.request.request_sevkiyat_isemri;
import com.etimaden.request.request_sevkiyat_isemri_depo;
import com.etimaden.request.request_sevkiyat_isemri_sevkiyat_isemri;
import com.etimaden.request.request_sevkiyat_isemri_uruntag_list_uruntag;
import com.etimaden.request.request_sevkiyat_rota_agirlik_konteyner;
import com.etimaden.request.request_sevkiyat_vagon_hareket_isemri;
import com.etimaden.request.request_sevkiyat_zayi;
import com.etimaden.request.request_sevkiyat_zayi_arac;
import com.etimaden.request.request_sevkiyat_zayi_zayiurun_list_zayiurun;
import com.etimaden.request.request_shrink_is_emri;
import com.etimaden.request.request_shrink_onayi_al;
import com.etimaden.request.request_string;
import com.etimaden.request.request_string_aktif_isletme_esleme;
import com.etimaden.request.request_string_string;
import com.etimaden.request.request_uretim_etiket;
import com.etimaden.request.request_uretim_iptali;
import com.etimaden.request.request_uretim_zayi;
import com.etimaden.request.request_uruntag;
import com.etimaden.request.request_uruntag_list_uruntag;
import com.etimaden.request.request_uruntag_string;
import com.etimaden.request.requestsecDepoTanimlari;
import com.etimaden.response.frg_paket_uretim_ekrani.View_ambalaj_tipi_secEtiket;
import com.etimaden.response.frg_paket_uretim_ekrani.View_bool_response;
import com.etimaden.response.frg_paket_uretim_ekrani.View_bos;
import com.etimaden.response.frg_paket_uretim_ekrani.View_secEtiket;
import com.etimaden.response.frg_paket_uretim_ekrani.View_sec_etiket_no;
import com.etimaden.response.frg_paket_uretim_ekrani.View_sec_uretim_detay;
import com.etimaden.response.frg_paket_uretim_ekrani.View_string_response;
import com.etimaden.response.frg_paket_uretim_ekrani.ViewsecDepoTanimlari;
import com.etimaden.response.frg_paket_uretim_ekrani.Viewsec_etiket_uretim;
import com.etimaden.response.sevkiyat_islemleri.View_arac;
import com.etimaden.response.sevkiyat_islemleri.View_sevkiyat_bekleyen_arac_listesi;
import com.etimaden.response.sevkiyat_islemleri.View_sevkiyat_isemri;
import com.etimaden.response.sevkiyat_islemleri.View_sevkiyat_isemri_listesi;
import com.etimaden.response.sevkiyat_islemleri.View_sevkiyat_urun_sevkiyat;
import com.etimaden.response.sevkiyat_islemleri.View_sevkiyat_urun_sevkiyat_listesi;
import com.etimaden.response.sevkiyat_islemleri.View_sevkiyat_urun_tag_listesi;
import com.etimaden.response.sevkiyat_islemleri.View_sevkiyat_vagon_hareket;
import com.etimaden.response.sevkiyat_islemleri.View_sevkiyat_zayi_listesi;
import com.etimaden.response.sevkiyat_islemleri.View_sevkiyat_zayi_urun_listesi;
import com.etimaden.response.sevkiyat_islemleri.View_string_list;
import com.etimaden.servisbaglanti.frg_manipulasyon_islemleri_ekrani_Controller;
import com.etimaden.servisbaglanti.frg_paket_uretim_ekrani_Controller;
import com.etimaden.servisbaglanti.frg_sevkiyat_islemleri_ekrani_Controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Persos {

    Retrofit retrofit;
    Context context;


    public Persos(String _OnlineUrl,Context context) {

        this.context=context;
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
            frg_manipulasyon_islemleri_ekrani_Controller _Servis=retrofit.create(frg_manipulasyon_islemleri_ekrani_Controller.class);

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


    public Boolean fn_paketliUret(request_paketliUret v_gelen)
    {
        Boolean _bsonuc=false;

        View_bos _yanit = new View_bos();

        try
        {
            frg_paket_uretim_ekrani_Controller _Servis=retrofit.create(frg_paket_uretim_ekrani_Controller.class);

            Call<View_bos> fn_Servis = _Servis.fn_paketliUret(v_gelen);

            Response<View_bos> _Response = fn_Servis.execute();

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
    // Persos içerisindeki fn_paketliUret(uretim_etiket _urun) fonksiyonu fn_paketliUretKontrol şeklinde tanımlandı.
    public Boolean fn_paketliUretKontrol(request_paketliUretKontrol v_gelen)
    {
        Boolean _bsonuc;

        View_bool_response _yanit ;

        try
        {
            frg_paket_uretim_ekrani_Controller _Servis=retrofit.create(frg_paket_uretim_ekrani_Controller.class);

            Call<View_bool_response> fn_Servis = _Servis.fn_paketliUretKontrol(v_gelen);

            Response<View_bool_response> _Response = fn_Servis.execute();

            if(_Response.isSuccessful())
            {
                _yanit = _Response.body();
                _bsonuc=_yanit._result;


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

                return  _yanit._tag;
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



    public View_string_response fn_get_lot_toplami(request_get_lot_toplami v_Gelen)
    {
        View_string_response _yanit=new View_string_response();

        try
        {
            frg_paket_uretim_ekrani_Controller _Servis=retrofit.create(frg_paket_uretim_ekrani_Controller.class);

            Call<View_string_response> fn_Servis = _Servis.fn_get_lot_toplami(v_Gelen);

            Response<View_string_response> _Response = fn_Servis.execute();

            if(_Response.isSuccessful())
            {
                _yanit = _Response.body();

                return  _yanit;
            }
            else
            {
                _yanit=new View_string_response();
                _yanit._zSonuc="0";
                _yanit._zAciklama="";
                _yanit._zHataAciklama="Sistemsel hata";

                return _yanit;
            }

        }catch (Exception ex)
        {
            _yanit=new View_string_response();
            _yanit._zAciklama="";
            _yanit._zHataAciklama=ex.toString();
            _yanit._zSonuc="0";

            return  _yanit;
        }
    }


    public View_bool_response paketliUret_otomatik(request_paketliUret_otomatik v_Gelen)
    {
        View_bool_response _Yanit = new View_bool_response();
        try
        {

            frg_paket_uretim_ekrani_Controller _Servis=retrofit.create(frg_paket_uretim_ekrani_Controller.class);

            Call<View_bool_response> fn_Servis = _Servis.fn_paketliUret_otomatik(v_Gelen);

            Response<View_bool_response> _Response = fn_Servis.execute();

            if(_Response.isSuccessful())
            {
                _Yanit = _Response.body();

                return  _Yanit;
            }
            else
            {
                _Yanit=new View_bool_response();
                _Yanit._result=false;
                _Yanit._zAciklama="";
                _Yanit._zSonuc="0";
                _Yanit._zHataAciklama="Sistemsel hata";

                return _Yanit;
            }

        }catch (Exception ex)
        {
            _Yanit=new View_bool_response();
            _Yanit._result=false;
            _Yanit._zAciklama="";
            _Yanit._zHataAciklama=ex.toString();
            _Yanit._zSonuc="0";

            return _Yanit;
        }


    }




    public String yari_otomatik_paket_kontrol_et(request_string v_Gelen)
    {
        String _Cevap ="";

        try
        {
            frg_paket_uretim_ekrani_Controller _Servis=retrofit.create(frg_paket_uretim_ekrani_Controller.class);

            Call<View_string_response> fn_Servis = _Servis.fn_yari_otomatik_paket_kontrol_et(v_Gelen);

            Response<View_string_response> _Response = fn_Servis.execute();

            if(_Response.isSuccessful())
            {
                View_string_response _Yanit = _Response.body();

                if(_Yanit.get_zSonuc().equals("0"))
                {
                    _Cevap = null;
                }
                else
                {
                    _Cevap= _Yanit.get_result();
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

    public String etiket_kontrol(request_string v_Gelen)
    {
        String _Cevap ="";

        frg_paket_uretim_ekrani_Controller _Servis=retrofit.create(frg_paket_uretim_ekrani_Controller.class);

        Call<View_string_response> fn_Servis= _Servis.fn_etiket_kontrol(v_Gelen);

        try
        {
            Response<View_string_response> _Response = fn_Servis.execute();

            if(_Response.isSuccessful())
            {
                View_string_response _Yanit = _Response.body();

                if(_Yanit.get_zSonuc().equals("0"))
                {
                    _Cevap = null;
                }
                else
                {
                    _Cevap= _Yanit.get_result();
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

    public etiket_no sec_etiket_no(request_string v_Gelen) {

        etiket_no _Cevap ;

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

    public uretim_etiket sec_etiket_uretim(request_string _Param)
    {
        uretim_etiket _Cevap;

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
        View_bool_response _yanit = new View_bool_response();

        try
        {
            frg_paket_uretim_ekrani_Controller _Servis=retrofit.create(frg_paket_uretim_ekrani_Controller.class);

            Call<View_bool_response> fn_Servis = _Servis.fn_uretim_zayi(v_Gelen);

            Response<View_bool_response> _Response = fn_Servis.execute();

            if(_Response.isSuccessful())
            {
                _yanit = _Response.body();

                return  _yanit.get_result();
            }
            else
            {
                _yanit=new View_bool_response();
                _yanit._zSonuc="0";
                _yanit._zAciklama="";
                _yanit._zHataAciklama="Sistemsel hata";

                return null;
            }

        }catch (Exception ex)
        {
            _yanit=new View_bool_response();
            _yanit._zAciklama="";
            _yanit._zHataAciklama=ex.toString();
            _yanit._zSonuc="0";

            return  null;
        }

    }

    public View_bool_response fn_bigBag_uret(request_uretim_etiket v_Gelen)
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

                return  _yanit;
            }
            else
            {
                return null;
            }

        }catch (Exception ex)
        {
            Genel.printStackTrace(ex,context);
            return  null;
        }

        /*
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
            Genel.printStackTrace(ex,context);
            return  null;
        }
*/
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
            Genel.printStackTrace(ex,context);
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
            Genel.printStackTrace(ex,context);
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
            Genel.printStackTrace(ex,context);
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
            Genel.printStackTrace(ex,context);
            return  null;
        }

    }
/*
    public View_bool_response fn_manipulasyon_bigbag_uret(request_uretim_etiket v_Gelen)
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

                return  _yanit;
            }
            else
            {
                return null;
            }

        }catch (Exception ex)
        {
            Genel.printStackTrace(ex,context);
            return  null;
        }

    }*/

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
            Genel.printStackTrace(ex,context);
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
            Genel.printStackTrace(ex,context);
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
            Genel.printStackTrace(ex,context);
            return  null;
        }

    }

    public String fn_sec_palet_dizim(request_bos v_Gelen)
    {
        View_string_response _yanit;

        try
        {
            frg_paket_uretim_ekrani_Controller _Servis=retrofit.create(frg_paket_uretim_ekrani_Controller.class);

            Call<View_string_response> fn_Servis = _Servis.fn_sec_palet_dizim(v_Gelen);

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
            Genel.printStackTrace(ex,context);
            return  null;
        }

    }

    public Boolean fn_guncelle_palet_dizim(request_string v_Gelen)
    {
        View_bool_response _yanit;

        try
        {
            frg_paket_uretim_ekrani_Controller _Servis=retrofit.create(frg_paket_uretim_ekrani_Controller.class);

            Call<View_bool_response> fn_Servis = _Servis.fn_guncelle_palet_dizim(v_Gelen);

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
            Genel.printStackTrace(ex,context);
            return  null;
        }

    }
    public String fn_sec_palet_sirano_acilis(request_bos v_Gelen)
    {
        View_string_response _yanit;

        try
        {
            frg_paket_uretim_ekrani_Controller _Servis=retrofit.create(frg_paket_uretim_ekrani_Controller.class);

            Call<View_string_response> fn_Servis = _Servis.fn_sec_palet_sirano_acilis(v_Gelen);

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
            Genel.printStackTrace(ex,context);
            return  null;
        }

    }

    public String fn_sec_palet_torba_sayisi(request_string v_Gelen)
    {
        View_string_response _yanit;

        try
        {
            frg_paket_uretim_ekrani_Controller _Servis=retrofit.create(frg_paket_uretim_ekrani_Controller.class);

            Call<View_string_response> fn_Servis = _Servis.fn_sec_palet_torba_sayisi(v_Gelen);

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
            Genel.printStackTrace(ex,context);
            return  null;
        }

    }
    public String fn_sec_palet_sirano(request_bos v_Gelen)
    {
        View_string_response _yanit;

        try
        {
            frg_paket_uretim_ekrani_Controller _Servis=retrofit.create(frg_paket_uretim_ekrani_Controller.class);

            Call<View_string_response> fn_Servis = _Servis.fn_sec_palet_sirano(v_Gelen);

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
            Genel.printStackTrace(ex,context);
            return  null;
        }

    }

    public List<String> fn_sec_sevk_miktar(request_string v_Gelen)
    {
        List<String> _Cevap=null ;

        try
        {
            frg_sevkiyat_islemleri_ekrani_Controller _Servis=retrofit.create(frg_sevkiyat_islemleri_ekrani_Controller.class);

            Call<View_sec_sevk_miktar> fn_Servis = _Servis.fn_sec_sevk_miktar(v_Gelen);

            Response<View_sec_sevk_miktar> _Response = fn_Servis.execute();

            if(_Response.isSuccessful())
            {
                View_sec_sevk_miktar _Yanit = _Response.body();

                if(_Yanit.get_zSonuc().equals("0"))
                {
                    _Cevap = null;
                }
                else
                {
                    _Cevap= _Yanit.get_miktarlar();
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

    public Boolean fn_sevkiyatIptal(request_sevkiyat_isemri v_Gelen)
    {
        View_bool_response _yanit;

        try
        {
            frg_sevkiyat_islemleri_ekrani_Controller _Servis=retrofit.create(frg_sevkiyat_islemleri_ekrani_Controller.class);

            Call<View_bool_response> fn_Servis = _Servis.fn_sevkiyatIptal(v_Gelen);

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
            Genel.printStackTrace(ex,context);
            return  null;
        }

    }

    public Boolean fn_sevkiyatDevam(request_sevkiyat_isemri v_Gelen)
    {
        View_bool_response _yanit;

        try
        {
            frg_sevkiyat_islemleri_ekrani_Controller _Servis=retrofit.create(frg_sevkiyat_islemleri_ekrani_Controller.class);

            Call<View_bool_response> fn_Servis = _Servis.fn_sevkiyatDevam(v_Gelen);

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
            Genel.printStackTrace(ex,context);
            return  null;
        }

    }

    public Boolean fn_guncelle_sevk_hareket(request_guncelle_sevk_hareket v_Gelen)
    {
        View_bool_response _yanit;

        try
        {
            frg_sevkiyat_islemleri_ekrani_Controller _Servis=retrofit.create(frg_sevkiyat_islemleri_ekrani_Controller.class);

            Call<View_bool_response> fn_Servis = _Servis.fn_guncelle_sevk_hareket(v_Gelen);

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
            Genel.printStackTrace(ex,context);
            return  null;
        }

    }
    public List<String> fn_get_yukleme_palet_sayisi_miktar(request_string v_Gelen)
    {
        List<String> _Cevap=null ;

        try
        {
            frg_sevkiyat_islemleri_ekrani_Controller _Servis=retrofit.create(frg_sevkiyat_islemleri_ekrani_Controller.class);

            Call<View_sec_sevk_miktar> fn_Servis = _Servis.fn_get_yukleme_palet_sayisi_miktar(v_Gelen);

            Response<View_sec_sevk_miktar> _Response = fn_Servis.execute();

            if(_Response.isSuccessful())
            {
                View_sec_sevk_miktar _Yanit = _Response.body();

                if(_Yanit.get_zSonuc().equals("0"))
                {
                    _Cevap = null;
                }
                else
                {
                    _Cevap= _Yanit.get_miktarlar();
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
    //Buradan itibaren Sevkiyat yeni ekranlar.

    public Sevkiyat_isemri fn_sec_aktif_silobas(request_string v_Gelen)
    {
        View_sevkiyat_isemri _yanit;

        try
        {
            frg_sevkiyat_islemleri_ekrani_Controller _Servis=retrofit.create(frg_sevkiyat_islemleri_ekrani_Controller.class);

            Call<View_sevkiyat_isemri> fn_Servis = _Servis.fn_sec_aktif_silobas(v_Gelen);

            Response<View_sevkiyat_isemri> _Response = fn_Servis.execute();

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
            Genel.printStackTrace(ex,context);
            return  null;
        }

    }

    public List<Sevkiyat_isemri> fn_sec_silobas_SevkiyatIsemriListesi(request_sevkiyat_isemri v_Gelen)
    {
        List<Sevkiyat_isemri> _Cevap=null ;

        try
        {
            frg_sevkiyat_islemleri_ekrani_Controller _Servis=retrofit.create(frg_sevkiyat_islemleri_ekrani_Controller.class);

            Call<View_sevkiyat_isemri_listesi> fn_Servis = _Servis.fn_sec_silobas_SevkiyatIsemriListesi(v_Gelen);

            Response<View_sevkiyat_isemri_listesi> _Response = fn_Servis.execute();

            if(_Response.isSuccessful())
            {
                View_sevkiyat_isemri_listesi _Yanit = _Response.body();

                if(_Yanit.get_zSonuc().equals("0"))
                {
                    _Cevap = null;
                }
                else
                {
                    _Cevap= _Yanit.get_sevk_listesi();
                }
            }
            else
            {
                _Cevap = null;
            }


        }catch (Exception ex)
        {
            Genel.printStackTrace(ex,context);
        }

        return  _Cevap;
    }

    public Boolean fn_onayla_silobas_SevkIsDegisimi(request_sevkiyat_eski_sevk_yeni_sevk v_Gelen)
    {
        View_bool_response _yanit;

        try
        {
            frg_sevkiyat_islemleri_ekrani_Controller _Servis=retrofit.create(frg_sevkiyat_islemleri_ekrani_Controller.class);

            Call<View_bool_response> fn_Servis = _Servis.fn_onayla_silobas_SevkIsDegisimi(v_Gelen);

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
            Genel.printStackTrace(ex,context);
            return  null;
        }

    }

    public Arac fn_sec_arac(request_string v_Gelen)
    {
        View_arac _yanit;

        try
        {
            frg_sevkiyat_islemleri_ekrani_Controller _Servis=retrofit.create(frg_sevkiyat_islemleri_ekrani_Controller.class);

            Call<View_arac> fn_Servis = _Servis.fn_sec_arac(v_Gelen);

            Response<View_arac> _Response = fn_Servis.execute();

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
            Genel.printStackTrace(ex,context);
            return  null;
        }

    }

    public List<Zayi> fn_sec_zayi_aynı_arac(request_string_aktif_isletme_esleme v_Gelen)
    {
        List<Zayi> _Cevap=null ;

        try
        {
            frg_sevkiyat_islemleri_ekrani_Controller _Servis=retrofit.create(frg_sevkiyat_islemleri_ekrani_Controller.class);

            Call<View_sevkiyat_zayi_listesi> fn_Servis = _Servis.fn_sec_zayi_aynı_arac(v_Gelen);

            Response<View_sevkiyat_zayi_listesi> _Response = fn_Servis.execute();

            if(_Response.isSuccessful())
            {
                View_sevkiyat_zayi_listesi _Yanit = _Response.body();

                if(_Yanit.get_zSonuc().equals("0"))
                {
                    _Cevap = null;
                }
                else
                {
                    _Cevap= _Yanit.get_zayi_listesi();
                }
            }
            else
            {
                _Cevap = null;
            }


        }catch (Exception ex)
        {
            Genel.printStackTrace(ex,context);
        }

        return  _Cevap;
    }

    public List<Zayi> fn_sec_zayi_farklı_arac(request_bos v_Gelen)
    {
        List<Zayi> _Cevap=null ;

        try
        {
            frg_sevkiyat_islemleri_ekrani_Controller _Servis=retrofit.create(frg_sevkiyat_islemleri_ekrani_Controller.class);

            Call<View_sevkiyat_zayi_listesi> fn_Servis = _Servis.fn_sec_zayi_farklı_arac(v_Gelen);

            Response<View_sevkiyat_zayi_listesi> _Response = fn_Servis.execute();

            if(_Response.isSuccessful())
            {
                View_sevkiyat_zayi_listesi _Yanit = _Response.body();

                if(_Yanit.get_zSonuc().equals("0"))
                {
                    _Cevap = null;
                }
                else
                {
                    _Cevap= _Yanit.get_zayi_listesi();
                }
            }
            else
            {
                _Cevap = null;
            }


        }catch (Exception ex)
        {
            Genel.printStackTrace(ex,context);
        }

        return  _Cevap;
    }

    public Boolean fn_ac_zayi_arac(request_sevkiyat_zayi_arac v_Gelen)
    {
        View_bool_response _yanit;

        try
        {
            frg_sevkiyat_islemleri_ekrani_Controller _Servis=retrofit.create(frg_sevkiyat_islemleri_ekrani_Controller.class);

            Call<View_bool_response> fn_Servis = _Servis.fn_ac_zayi_arac(v_Gelen);

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
            Genel.printStackTrace(ex,context);
            return  null;
        }

    }

    public Boolean fn_guncelle_depo_zayi_arac(request_sevkiyat_zayi v_Gelen)
    {
        View_bool_response _yanit;

        try
        {
            frg_sevkiyat_islemleri_ekrani_Controller _Servis=retrofit.create(frg_sevkiyat_islemleri_ekrani_Controller.class);

            Call<View_bool_response> fn_Servis = _Servis.fn_guncelle_depo_zayi_arac(v_Gelen);

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
            Genel.printStackTrace(ex,context);
            return  null;
        }

    }

    public Boolean fn_kapat_zayi_arac(request_sevkiyat_zayi_arac v_Gelen)
    {
        View_bool_response _yanit;

        try
        {
            frg_sevkiyat_islemleri_ekrani_Controller _Servis=retrofit.create(frg_sevkiyat_islemleri_ekrani_Controller.class);

            Call<View_bool_response> fn_Servis = _Servis.fn_kapat_zayi_arac(v_Gelen);

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
            Genel.printStackTrace(ex,context);
            return  null;
        }

    }

    public Boolean fn_guncelle_vagon_satis(request_bos v_Gelen)
    {
        View_bool_response _yanit;

        try
        {
            frg_sevkiyat_islemleri_ekrani_Controller _Servis=retrofit.create(frg_sevkiyat_islemleri_ekrani_Controller.class);

            Call<View_bool_response> fn_Servis = _Servis.fn_guncelle_vagon_satis(v_Gelen);

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
            Genel.printStackTrace(ex,context);
            return  null;
        }

    }

    public List<DEPOTag> fn_secDepoTanimlari(requestsecDepoTanimlari v_Gelen)
    {
        ViewsecDepoTanimlari _yanit;

        try
        {
            frg_sevkiyat_islemleri_ekrani_Controller _Servis=retrofit.create(frg_sevkiyat_islemleri_ekrani_Controller.class);

            Call<ViewsecDepoTanimlari> fn_Servis = _Servis.fn_secDepoTanimlari(v_Gelen);

            Response<ViewsecDepoTanimlari> _Response = fn_Servis.execute();

            if(_Response.isSuccessful())
            {
                _yanit = _Response.body();

                return  _yanit.get_DepoListesi();
            }
            else
            {
                return null;
            }

        }catch (Exception ex)
        {
            Genel.printStackTrace(ex,context);
            return  null;
        }

    }

    public List<Zayi> fn_sec_zayi_arac(request_bos_aktif_isletme_esleme v_Gelen)
    {
        List<Zayi> _Cevap=null ;

        try
        {
            frg_sevkiyat_islemleri_ekrani_Controller _Servis=retrofit.create(frg_sevkiyat_islemleri_ekrani_Controller.class);

            Call<View_sevkiyat_zayi_listesi> fn_Servis = _Servis.fn_sec_zayi_arac(v_Gelen);

            Response<View_sevkiyat_zayi_listesi> _Response = fn_Servis.execute();

            if(_Response.isSuccessful())
            {
                View_sevkiyat_zayi_listesi _Yanit = _Response.body();

                if(_Yanit.get_zSonuc().equals("0"))
                {
                    _Cevap = null;
                }
                else
                {
                    _Cevap= _Yanit.get_zayi_listesi();
                }
            }
            else
            {
                _Cevap = null;
            }


        }catch (Exception ex)
        {
            Genel.printStackTrace(ex,context);
        }

        return  _Cevap;
    }

    public List<Zayi_urun> fn_sec_zayi_urun_listesi(request_string v_Gelen)
    {
        List<Zayi_urun> _Cevap=null ;

        try
        {
            frg_sevkiyat_islemleri_ekrani_Controller _Servis=retrofit.create(frg_sevkiyat_islemleri_ekrani_Controller.class);

            Call<View_sevkiyat_zayi_urun_listesi> fn_Servis = _Servis.fn_sec_zayi_urun_listesi(v_Gelen);

            Response<View_sevkiyat_zayi_urun_listesi> _Response = fn_Servis.execute();

            if(_Response.isSuccessful())
            {
                View_sevkiyat_zayi_urun_listesi _Yanit = _Response.body();

                if(_Yanit.get_zSonuc().equals("0"))
                {
                    _Cevap = null;
                }
                else
                {
                    _Cevap= _Yanit.get_zayi_urun_listesi();
                }
            }
            else
            {
                _Cevap = null;
            }


        }catch (Exception ex)
        {
            Genel.printStackTrace(ex,context);
        }

        return  _Cevap;
    }

    public Boolean fn_ekleZayitUrun_cikarma(request_sevkiyat_zayi_zayiurun_list_zayiurun v_Gelen)
    {
        View_bool_response _yanit;

        try
        {
            frg_sevkiyat_islemleri_ekrani_Controller _Servis=retrofit.create(frg_sevkiyat_islemleri_ekrani_Controller.class);

            Call<View_bool_response> fn_Servis = _Servis.fn_ekleZayitUrun_cikarma(v_Gelen);

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
            Genel.printStackTrace(ex,context);
            return  null;
        }

    }

    public Boolean fn_update_bas_etiket(request_string v_Gelen)
    {
        View_bool_response _yanit;

        try
        {
            frg_sevkiyat_islemleri_ekrani_Controller _Servis=retrofit.create(frg_sevkiyat_islemleri_ekrani_Controller.class);

            Call<View_bool_response> fn_Servis = _Servis.fn_update_bas_etiket(v_Gelen);

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
            Genel.printStackTrace(ex,context);
            return  null;
        }

    }

    public List<Sevkiyat_isemri> fn_secKantarIsemriListesi(request_string v_Gelen)
    {
        List<Sevkiyat_isemri> _Cevap=null ;

        try
        {
            frg_sevkiyat_islemleri_ekrani_Controller _Servis=retrofit.create(frg_sevkiyat_islemleri_ekrani_Controller.class);

            Call<View_sevkiyat_isemri_listesi> fn_Servis = _Servis.fn_secKantarIsemriListesi(v_Gelen);

            Response<View_sevkiyat_isemri_listesi> _Response = fn_Servis.execute();

            if(_Response.isSuccessful())
            {
                View_sevkiyat_isemri_listesi _Yanit = _Response.body();

                if(_Yanit.get_zSonuc().equals("0"))
                {
                    _Cevap = null;
                }
                else
                {
                    _Cevap= _Yanit.get_sevk_listesi();
                }
            }
            else
            {
                _Cevap = null;
            }


        }catch (Exception ex)
        {
            Genel.printStackTrace(ex,context);
        }

        return  _Cevap;
    }

    public Vagon_hareket fn_sec_vagon_hareket(request_string v_Gelen)
    {
        View_sevkiyat_vagon_hareket _yanit;

        try
        {
            frg_sevkiyat_islemleri_ekrani_Controller _Servis=retrofit.create(frg_sevkiyat_islemleri_ekrani_Controller.class);

            Call<View_sevkiyat_vagon_hareket> fn_Servis = _Servis.fn_sec_vagon_hareket(v_Gelen);

            Response<View_sevkiyat_vagon_hareket> _Response = fn_Servis.execute();

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
            Genel.printStackTrace(ex,context);
            return  null;
        }

    }

    public List<Sevkiyat_isemri> fn_secKantarIndirmeIsemriListesi(request_string v_Gelen)
    {
        List<Sevkiyat_isemri> _Cevap=null ;

        try
        {
            frg_sevkiyat_islemleri_ekrani_Controller _Servis=retrofit.create(frg_sevkiyat_islemleri_ekrani_Controller.class);

            Call<View_sevkiyat_isemri_listesi> fn_Servis = _Servis.fn_secKantarIndirmeIsemriListesi(v_Gelen);

            Response<View_sevkiyat_isemri_listesi> _Response = fn_Servis.execute();

            if(_Response.isSuccessful())
            {
                View_sevkiyat_isemri_listesi _Yanit = _Response.body();

                if(_Yanit.get_zSonuc().equals("0"))
                {
                    _Cevap = null;
                }
                else
                {
                    _Cevap= _Yanit.get_sevk_listesi();
                }
            }
            else
            {
                _Cevap = null;
            }


        }catch (Exception ex)
        {
            Genel.printStackTrace(ex,context);
        }

        return  _Cevap;
    }

    public Boolean fn_update_gemi_transfer_direct(request_sevkiyat_isemri v_Gelen)
    {
        View_bool_response _yanit;

        try
        {
            frg_sevkiyat_islemleri_ekrani_Controller _Servis=retrofit.create(frg_sevkiyat_islemleri_ekrani_Controller.class);

            Call<View_bool_response> fn_Servis = _Servis.fn_update_gemi_transfer_direct(v_Gelen);

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
            Genel.printStackTrace(ex,context);
            return  null;
        }

    }
    public Boolean fn_guncelle_innerliner_bos(request_sevkiyat_rota_agirlik_konteyner v_Gelen)
    {
        View_bool_response _yanit;

        try
        {
            frg_sevkiyat_islemleri_ekrani_Controller _Servis=retrofit.create(frg_sevkiyat_islemleri_ekrani_Controller.class);

            Call<View_bool_response> fn_Servis = _Servis.fn_guncelle_innerliner_bos(v_Gelen);

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
            Genel.printStackTrace(ex,context);
            return  null;
        }
    }

    public List<Sevkiyat_isemri> fn_sec_yerde_konteyner(request_string v_Gelen)
    {
        List<Sevkiyat_isemri> _Cevap=null ;

        try
        {
            frg_sevkiyat_islemleri_ekrani_Controller _Servis=retrofit.create(frg_sevkiyat_islemleri_ekrani_Controller.class);

            Call<View_sevkiyat_isemri_listesi> fn_Servis = _Servis.fn_sec_yerde_konteyner(v_Gelen);

            Response<View_sevkiyat_isemri_listesi> _Response = fn_Servis.execute();

            if(_Response.isSuccessful())
            {
                View_sevkiyat_isemri_listesi _Yanit = _Response.body();

                if(_Yanit.get_zSonuc().equals("0"))
                {
                    _Cevap = null;
                }
                else
                {
                    _Cevap= _Yanit.get_sevk_listesi();
                }
            }
            else
            {
                _Cevap = null;
            }


        }catch (Exception ex)
        {
            Genel.printStackTrace(ex,context);
        }

        return  _Cevap;
    }

    public Boolean fn_vagon_konteyner_satıs_tamamla(request_sevkiyat_vagon_hareket_isemri v_Gelen)
    {
        View_bool_response _yanit;

        try
        {
            frg_sevkiyat_islemleri_ekrani_Controller _Servis=retrofit.create(frg_sevkiyat_islemleri_ekrani_Controller.class);

            Call<View_bool_response> fn_Servis = _Servis.fn_vagon_konteyner_satıs_tamamla(v_Gelen);

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
            Genel.printStackTrace(ex,context);
            return  null;
        }
    }

    public Boolean fn_sevkiyatDevam_nakil(request_sevkiyat_isemri v_Gelen)
    {
        View_bool_response _yanit;

        try
        {
            frg_sevkiyat_islemleri_ekrani_Controller _Servis=retrofit.create(frg_sevkiyat_islemleri_ekrani_Controller.class);

            Call<View_bool_response> fn_Servis = _Servis.fn_sevkiyatDevam_nakil(v_Gelen);

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
            Genel.printStackTrace(ex,context);
            return  null;
        }
    }

    public Boolean fn_sevkiyatKapat(request_sevkiyat_isemri v_Gelen)
    {
        View_bool_response _yanit;

        try
        {
            frg_sevkiyat_islemleri_ekrani_Controller _Servis=retrofit.create(frg_sevkiyat_islemleri_ekrani_Controller.class);

            Call<View_bool_response> fn_Servis = _Servis.fn_sevkiyatKapat(v_Gelen);

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
            Genel.printStackTrace(ex,context);
            return  null;
        }
    }

    public Boolean fn_ekleAracIndirmeAktivasyonu(request_sevkiyat_isemri_depo v_Gelen)
    {
        View_bool_response _yanit;

        try
        {
            frg_sevkiyat_islemleri_ekrani_Controller _Servis=retrofit.create(frg_sevkiyat_islemleri_ekrani_Controller.class);

            Call<View_bool_response> fn_Servis = _Servis.fn_ekleAracIndirmeAktivasyonu(v_Gelen);

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
            Genel.printStackTrace(ex,context);
            return  null;
        }
    }

    public Boolean fn_updateAracIndirmeAktivasyonu(request_sevkiyat_isemri_depo v_Gelen)
    {
        View_bool_response _yanit;

        try
        {
            frg_sevkiyat_islemleri_ekrani_Controller _Servis=retrofit.create(frg_sevkiyat_islemleri_ekrani_Controller.class);

            Call<View_bool_response> fn_Servis = _Servis.fn_updateAracIndirmeAktivasyonu(v_Gelen);

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
            Genel.printStackTrace(ex,context);
            return  null;
        }
    }

    public List<cBekleyen_Arac_Listesi> fn_BekleyenAracListesi(request_bos v_Gelen)
    {
        List<cBekleyen_Arac_Listesi> _Cevap=null ;

        try
        {
            frg_sevkiyat_islemleri_ekrani_Controller _Servis=retrofit.create(frg_sevkiyat_islemleri_ekrani_Controller.class);

            Call<View_sevkiyat_bekleyen_arac_listesi> fn_Servis = _Servis.fn_BekleyenAracListesi(v_Gelen);

            Response<View_sevkiyat_bekleyen_arac_listesi> _Response = fn_Servis.execute();

            if(_Response.isSuccessful())
            {
                View_sevkiyat_bekleyen_arac_listesi _Yanit = _Response.body();

                if(_Yanit.get_zSonuc().equals("0"))
                {
                    _Cevap = null;
                }
                else
                {
                    _Cevap= _Yanit.get_bekleyen_arac_listesi();
                }
            }
            else
            {
                _Cevap = null;
            }


        }catch (Exception ex)
        {
            Genel.printStackTrace(ex,context);
        }

        return  _Cevap;
    }

    public List<Urun_tag> fn_secYuklenenUrunListesi(request_sevkiyat_isemri v_Gelen) {
        List<Urun_tag> _Cevap = null;

        try {
            frg_sevkiyat_islemleri_ekrani_Controller _Servis = retrofit.create(frg_sevkiyat_islemleri_ekrani_Controller.class);

            Call<View_sevkiyat_urun_tag_listesi> fn_Servis = _Servis.fn_secYuklenenUrunListesi(v_Gelen);

            Response<View_sevkiyat_urun_tag_listesi> _Response = fn_Servis.execute();

            if (_Response.isSuccessful()) {
                View_sevkiyat_urun_tag_listesi _Yanit = _Response.body();

                if (_Yanit.get_zSonuc().equals("0")) {
                    _Cevap = null;
                } else {
                    _Cevap = _Yanit.get_urun_tag_listesi();
                }
            } else {
                _Cevap = null;
            }


        } catch (Exception ex) {
            Genel.printStackTrace(ex,context);
        }
        return  _Cevap;
    }

    public List<Urun_tag> fn_secYuklenenUrunListesi_nakil(request_sevkiyat_isemri v_Gelen) {
        List<Urun_tag> _Cevap = null;

        try {
            frg_sevkiyat_islemleri_ekrani_Controller _Servis = retrofit.create(frg_sevkiyat_islemleri_ekrani_Controller.class);

            Call<View_sevkiyat_urun_tag_listesi> fn_Servis = _Servis.fn_secYuklenenUrunListesi_nakil(v_Gelen);

            Response<View_sevkiyat_urun_tag_listesi> _Response = fn_Servis.execute();

            if (_Response.isSuccessful()) {
                View_sevkiyat_urun_tag_listesi _Yanit = _Response.body();

                if (_Yanit.get_zSonuc().equals("0")) {
                    _Cevap = null;
                } else {
                    _Cevap = _Yanit.get_urun_tag_listesi();
                }
            } else {
                _Cevap = null;
            }


        } catch (Exception ex) {
            Genel.printStackTrace(ex,context);
        }
        return  _Cevap;
    }

    public Boolean fn_ekleSevkiyatUrun_cikarma(request_sevkiyat_isemri_uruntag_list_uruntag v_Gelen)
    {
        View_bool_response _yanit;

        try
        {
            frg_sevkiyat_islemleri_ekrani_Controller _Servis=retrofit.create(frg_sevkiyat_islemleri_ekrani_Controller.class);

            Call<View_bool_response> fn_Servis = _Servis.fn_ekleSevkiyatUrun_cikarma(v_Gelen);

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
            Genel.printStackTrace(ex,context);
            return  null;
        }
    }

    public Boolean fn_iptal_yerde_konteyner(request_sevkiyat_isemri v_Gelen)
    {
        View_bool_response _yanit;

        try
        {
            frg_sevkiyat_islemleri_ekrani_Controller _Servis=retrofit.create(frg_sevkiyat_islemleri_ekrani_Controller.class);

            Call<View_bool_response> fn_Servis = _Servis.fn_iptal_yerde_konteyner(v_Gelen);

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
            Genel.printStackTrace(ex,context);
            return  null;
        }
    }

    public List<Urun_sevkiyat> fn_sec_sevkiyat_urun_listesi(request_string v_Gelen)
    {
        List<Urun_sevkiyat> _Cevap=null ;

        try
        {
            frg_sevkiyat_islemleri_ekrani_Controller _Servis=retrofit.create(frg_sevkiyat_islemleri_ekrani_Controller.class);

            Call<View_sevkiyat_urun_sevkiyat_listesi> fn_Servis = _Servis.fn_sec_sevkiyat_urun_listesi(v_Gelen);

            Response<View_sevkiyat_urun_sevkiyat_listesi> _Response = fn_Servis.execute();

            if(_Response.isSuccessful())
            {
                View_sevkiyat_urun_sevkiyat_listesi _Yanit = _Response.body();

                if(_Yanit.get_zSonuc().equals("0"))
                {
                    _Cevap = null;
                }
                else
                {
                    _Cevap= _Yanit.get_urun_sevkiyat_listesi();
                }
            }
            else
            {
                _Cevap = null;
            }


        }catch (Exception ex)
        {
            Genel.printStackTrace(ex,context);
        }

        return  _Cevap;
    }

    public List<String> fn_aktif_sevk_kalan_miktar_palet(request_string_string v_Gelen)
    {
        List<String> _Cevap=null ;

        try
        {
            frg_sevkiyat_islemleri_ekrani_Controller _Servis=retrofit.create(frg_sevkiyat_islemleri_ekrani_Controller.class);

            Call<View_string_list> fn_Servis = _Servis.fn_aktif_sevk_kalan_miktar_palet(v_Gelen);

            Response<View_string_list> _Response = fn_Servis.execute();

            if(_Response.isSuccessful())
            {
                View_string_list _Yanit = _Response.body();

                if(_Yanit.get_zSonuc().equals("0"))
                {
                    _Cevap = null;
                }
                else
                {
                    _Cevap= _Yanit.get_list();
                }
            }
            else
            {
                _Cevap = null;
            }


        }catch (Exception ex)
        {
            Genel.printStackTrace(ex,context);
        }

        return  _Cevap;
    }

    public Urun_sevkiyat fn_sec_sevkiyat_urun(request_string v_Gelen)
    {
        View_sevkiyat_urun_sevkiyat _yanit;

        try
        {
            frg_sevkiyat_islemleri_ekrani_Controller _Servis=retrofit.create(frg_sevkiyat_islemleri_ekrani_Controller.class);

            Call<View_sevkiyat_urun_sevkiyat> fn_Servis = _Servis.fn_sec_sevkiyat_urun(v_Gelen);

            Response<View_sevkiyat_urun_sevkiyat> _Response = fn_Servis.execute();

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
            Genel.printStackTrace(ex,context);
            return  null;
        }
    }

    public Boolean fn_ekle_aktarim(request_sevkiyat_aktarim v_Gelen)
    {
        View_bool_response _yanit;

        try
        {
            frg_sevkiyat_islemleri_ekrani_Controller _Servis=retrofit.create(frg_sevkiyat_islemleri_ekrani_Controller.class);

            Call<View_bool_response> fn_Servis = _Servis.fn_ekle_aktarim(v_Gelen);

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
            Genel.printStackTrace(ex,context);
            return  null;
        }
    }

    public Boolean fn_arac_konteyner_esleme(request_sevkiyat_isemri_sevkiyat_isemri v_Gelen)
    {
        View_bool_response _yanit;

        try
        {
            frg_sevkiyat_islemleri_ekrani_Controller _Servis=retrofit.create(frg_sevkiyat_islemleri_ekrani_Controller.class);

            Call<View_bool_response> fn_Servis = _Servis.fn_arac_konteyner_esleme(v_Gelen);

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
            Genel.printStackTrace(ex,context);
            return  null;
        }
    }
    //Manipulasyon burdan basliyor

    public Boolean fn_paket_ambalaj_degistir(request_uruntag_list_uruntag v_Gelen)
    {
        View_bool_response _yanit;

        try
        {
            frg_manipulasyon_islemleri_ekrani_Controller _Servis=retrofit.create(frg_manipulasyon_islemleri_ekrani_Controller.class);

            Call<View_bool_response> fn_Servis = _Servis.fn_paket_ambalaj_degistir(v_Gelen);

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
            Genel.printStackTrace(ex,context);
            return  null;
        }
    }

    public String fn_flag_islemtipi(request_string v_Gelen)
    {
        View_string_response _yanit;

        try
        {
            frg_manipulasyon_islemleri_ekrani_Controller _Servis=retrofit.create(frg_manipulasyon_islemleri_ekrani_Controller.class);

            Call<View_string_response> fn_Servis = _Servis.fn_flag_islemtipi(v_Gelen);

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
            Genel.printStackTrace(ex,context);
            return  null;
        }
    }

    public Urun_tag fn_kirli_ambalaj_degistir_onay(request_uruntag_string v_Gelen)
    {
        View_secEtiket _yanit;

        try
        {
            frg_manipulasyon_islemleri_ekrani_Controller _Servis=retrofit.create(frg_manipulasyon_islemleri_ekrani_Controller.class);

            Call<View_secEtiket> fn_Servis = _Servis.fn_kirli_ambalaj_degistir_onay(v_Gelen);

            Response<View_secEtiket> _Response = fn_Servis.execute();

            if(_Response.isSuccessful())
            {
                _yanit = _Response.body();

                return  _yanit.get_tag();
            }
            else
            {
                return null;
            }

        }catch (Exception ex)
        {
            Genel.printStackTrace(ex,context);
            return  null;
        }
    }

    public Boolean fn_bigBag_ellecleme(request_uruntag_string v_Gelen)
    {
        View_bool_response _yanit;

        try
        {
            frg_manipulasyon_islemleri_ekrani_Controller _Servis=retrofit.create(frg_manipulasyon_islemleri_ekrani_Controller.class);

            Call<View_bool_response> fn_Servis = _Servis.fn_bigBag_ellecleme(v_Gelen);

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
            Genel.printStackTrace(ex,context);
            return  null;
        }
    }

    public Boolean fn_ellecleme_onay(request_uruntag_string v_Gelen)
    {
        View_bool_response _yanit;

        try
        {
            frg_manipulasyon_islemleri_ekrani_Controller _Servis=retrofit.create(frg_manipulasyon_islemleri_ekrani_Controller.class);

            Call<View_bool_response> fn_Servis = _Servis.fn_ellecleme_onay(v_Gelen);

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
            Genel.printStackTrace(ex,context);
            return  null;
        }
    }

    public Boolean fn_geribesleme_isaretle(request_uruntag v_Gelen)
    {
        View_bool_response _yanit;

        try
        {
            frg_manipulasyon_islemleri_ekrani_Controller _Servis=retrofit.create(frg_manipulasyon_islemleri_ekrani_Controller.class);

            Call<View_bool_response> fn_Servis = _Servis.fn_geribesleme_isaretle(v_Gelen);

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
            Genel.printStackTrace(ex,context);
            return  null;
        }
    }

    public Boolean fn_geribesleme_onay(request_uruntag_string v_Gelen)
    {
        View_bool_response _yanit;

        try
        {
            frg_manipulasyon_islemleri_ekrani_Controller _Servis=retrofit.create(frg_manipulasyon_islemleri_ekrani_Controller.class);

            Call<View_bool_response> fn_Servis = _Servis.fn_geribesleme_onay(v_Gelen);

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
            Genel.printStackTrace(ex,context);
            return  null;
        }
    }

}
