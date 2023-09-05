package com.etimaden.servisbaglanti;

import com.etimaden.cResponseResult.View_sec_sevk_miktar;
import com.etimaden.request.request_guncelle_sevk_hareket;
import com.etimaden.request.request_sevkiyat_isemri;
import com.etimaden.request.request_string;
import com.etimaden.response.frg_paket_uretim_ekrani.View_bool_response;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface frg_sevkiyat_islemleri_ekrani_Controller {

    @POST("api/sec_sevk_miktar2")
    Call<View_sec_sevk_miktar> fn_sec_sevk_miktar(@Body request_string v_Gelen);

    @POST("api/sevkiyatIptal2")
    Call<View_bool_response> fn_sevkiyatIptal(@Body request_sevkiyat_isemri v_Gelen);

    @POST("api/guncelle_sevk_hareket2")
    Call<View_bool_response> fn_guncelle_sevk_hareket(@Body request_guncelle_sevk_hareket v_Gelen);

    @POST("api/get_yukleme_palet_sayisi_miktar2")
    Call<View_sec_sevk_miktar> fn_get_yukleme_palet_sayisi_miktar(@Body request_string v_Gelen);

    @POST("api/sevkiyatDevam2")
    Call<View_bool_response> fn_sevkiyatDevam(@Body request_sevkiyat_isemri v_Gelen);


}
