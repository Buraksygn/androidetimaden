package com.etimaden.servisbaglanti;

import com.etimaden.request.request_ambalaj_tipi_secEtiket;
import com.etimaden.request.request_string;
import com.etimaden.request.request_string_string;
import com.etimaden.request.request_uruntag;
import com.etimaden.request.request_uruntag_list_uruntag;
import com.etimaden.request.request_uruntag_string;
import com.etimaden.response.frg_paket_uretim_ekrani.View_ambalaj_tipi_secEtiket;
import com.etimaden.response.frg_paket_uretim_ekrani.View_bool_response;
import com.etimaden.response.frg_paket_uretim_ekrani.View_secEtiket;
import com.etimaden.response.frg_paket_uretim_ekrani.View_string_response;
import com.etimaden.response.sevkiyat_islemleri.View_string_list;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface frg_manipulasyon_islemleri_ekrani_Controller
{
    //TODO response zaten var. View_secEtiket. burası hiç kullanılmamış neden var ?
    @POST("api/ambalaj_tipi_secEtiket")
    Call<View_ambalaj_tipi_secEtiket> fn_ambalaj_tipi_secEtiket(@Body request_ambalaj_tipi_secEtiket v_Gelen);

    @POST("api/paket_ambalaj_degistir")
    Call<View_bool_response> fn_paket_ambalaj_degistir(@Body request_uruntag_list_uruntag v_Gelen);

    @POST("api/flag_islemtipi")
    Call<View_string_response> fn_flag_islemtipi(@Body request_string v_Gelen);

    @POST("api/kirli_ambalaj_degistir_onay")
    Call<View_bool_response> fn_kirli_ambalaj_degistir_onay(@Body request_uruntag_string v_Gelen);

    @POST("api/bigBag_ellecleme")
    Call<View_bool_response> fn_bigBag_ellecleme(@Body request_uruntag_string v_Gelen);

    @POST("api/ellecleme_onay")
    Call<View_bool_response> fn_ellecleme_onay(@Body request_uruntag_string v_Gelen);

    @POST("api/geribesleme_isaretle")
    Call<View_bool_response> fn_geribesleme_isaretle(@Body request_uruntag v_Gelen);

    @POST("api/geribesleme_onay")
    Call<View_bool_response> fn_geribesleme_onay(@Body request_uruntag_string v_Gelen);

    @POST("api/palet_dagit")
    Call<View_bool_response> fn_palet_dagit(@Body request_uruntag v_Gelen);
    //Buraya kadar yapıldı.
}
