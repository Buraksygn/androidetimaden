package com.etimaden.servisbaglanti;

import com.etimaden.request.request_demirbas_sayim_list;
import com.etimaden.request.request_string;
import com.etimaden.request.request_string_gemi;
import com.etimaden.response.frg_paket_uretim_ekrani.View_bool_response;
import com.etimaden.response.frg_paket_uretim_ekrani.View_secEtiket;
import com.etimaden.response.gemi_islemleri.ViewAktifGemiListesi;
import com.etimaden.response.gemi_islemleri.ViewAktifIsemirleriListesi;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface frg_gemi_islemleri_Controller {
    @POST("api/gemiList")
    Call<ViewAktifGemiListesi> fn_secGemi(@Body request_string v_Gelen);

    @POST("api/kaydet_gemi_gelisme")
    Call<View_bool_response> fn_gemi_gelisme_kaydet(@Body request_string_gemi v_Gelen);

    @POST("api/isemirleriList")
    Call<ViewAktifIsemirleriListesi> fn_sec_isemirleri_listesi(@Body request_string v_Gelen);

}
