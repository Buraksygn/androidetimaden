package com.etimaden.servisbaglanti;

import com.etimaden.request.request_gemi;
import com.etimaden.response.frg_paket_uretim_ekrani.View_bool_response;
import com.etimaden.response.gemi_islemleri.ViewAktifGemiListesi;
import com.etimaden.response.gemi_islemleri.ViewAktifIsemirleriListesi;
import com.etimaden.response.gemi_islemleri.View_gemi_response;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface frg_gemi_islemleri_Controller {

    @POST("api/gemiList")
    Call<ViewAktifGemiListesi> fn_secGemi(@Body request_gemi v_Gelen);

    @POST("api/kaydet_gemi_gelisme")
    Call<View_bool_response> fn_gemi_gelisme_kaydet(@Body request_gemi v_Gelen);

    @POST("api/gemiIsemirleriList")
    Call<ViewAktifIsemirleriListesi> fn_sec_gemi_isemirleri(@Body request_gemi v_Gelen);

    @POST("api/gemiIsemriAraclari")
    Call<View_gemi_response> fn_gemi_isemri_araclari(@Body request_gemi v_Gelen);

    @POST("api/kaydet_arac_yukleme")
    Call<View_gemi_response> fn_arac_yukleme_kaydet(@Body request_gemi v_Gelen);

    @POST("api/arac_hasar_guncelle")
    Call<View_gemi_response> fn_arac_hasar_guncelle(@Body request_gemi v_Gelen);

    @POST("api/yukleme_ozet")
    Call<View_gemi_response> fn_yukleme_ozet(@Body request_gemi v_Gelen);
    @POST("api/arac_10dk_kontrol")
    Call<View_gemi_response> fn_arac_10dk_kontrol(@Body request_gemi v_Gelen);
}