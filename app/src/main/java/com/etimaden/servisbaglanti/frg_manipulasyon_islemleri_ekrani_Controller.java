package com.etimaden.servisbaglanti;

import com.etimaden.request.request_ambalaj_tipi_secEtiket;
import com.etimaden.request.request_string;
import com.etimaden.request.request_string_string;
import com.etimaden.request.request_uruntag_list_uruntag;
import com.etimaden.response.frg_paket_uretim_ekrani.View_ambalaj_tipi_secEtiket;
import com.etimaden.response.frg_paket_uretim_ekrani.View_bool_response;
import com.etimaden.response.frg_paket_uretim_ekrani.View_string_response;
import com.etimaden.response.sevkiyat_islemleri.View_string_list;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface frg_manipulasyon_islemleri_ekrani_Controller
{
    @POST("api/ambalaj_tipi_secEtiket")
    Call<View_ambalaj_tipi_secEtiket> fn_ambalaj_tipi_secEtiket(@Body request_ambalaj_tipi_secEtiket v_Gelen);

    @POST("api/paket_ambalaj_degistir")
    Call<View_bool_response> fn_paket_ambalaj_degistir(@Body request_uruntag_list_uruntag v_Gelen);

    @POST("api/fn_flag_islemtipi")
    Call<View_string_response> fn_flag_islemtipi(@Body request_string v_Gelen);
}
