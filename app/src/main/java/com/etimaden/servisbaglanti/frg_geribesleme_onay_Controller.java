package com.etimaden.servisbaglanti;

import com.etimaden.request.request_secetikettag;
import com.etimaden.response.frg_paket_uretim_ekrani.View_secetikettag;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface frg_geribesleme_onay_Controller {

    @POST("api/secetikettag")
    Call<View_secetikettag> fn_secDepoTanimlari(@Body request_secetikettag v_Gelen);
}
