package com.etimaden.service.frg_paket_uretim_ekrani;

import com.etimaden.service.request.requestetiket_kontrol;
import com.etimaden.service.response.Viewetiket_kontrol;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface service_etiket_kontrol {

    @POST("api/etiket_kontrol")//@FormUrlEncoded
    Call<Viewetiket_kontrol> fn_etiket_kontrol(@Body requestetiket_kontrol userInfo);
}
