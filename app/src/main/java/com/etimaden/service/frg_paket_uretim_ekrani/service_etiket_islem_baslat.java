package com.etimaden.service.frg_paket_uretim_ekrani;

import com.etimaden.service.request.request_etiket_islem_baslat;
import com.etimaden.service.response.response_etiket_islem_baslat;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface service_etiket_islem_baslat
{
    @POST("api/etiket_islem_baslat")//@FormUrlEncoded
    Call<response_etiket_islem_baslat> fn_etiket_islem_baslat(@Body request_etiket_islem_baslat userInfo);
}
