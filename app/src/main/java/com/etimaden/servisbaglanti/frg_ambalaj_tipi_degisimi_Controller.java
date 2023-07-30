package com.etimaden.servisbaglanti;

import com.etimaden.request.request_ambalaj_tipi_secEtiket;
import com.etimaden.response.frg_paket_uretim_ekrani.View_ambalaj_tipi_secEtiket;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface frg_ambalaj_tipi_degisimi_Controller
{
    @POST("api/ambalaj_tipi_secEtiket")
    Call<View_ambalaj_tipi_secEtiket> fn_ambalaj_tipi_secEtiket(@Body request_ambalaj_tipi_secEtiket v_Gelen);
}
