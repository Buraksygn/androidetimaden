package com.etimaden.servisbaglanti;

import com.etimaden.request.requestsec_etiket_uretim;
import com.etimaden.response.frg_paket_uretim_ekrani.Viewsec_etiket_uretim;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface test_interface {

    @POST("api/toplamaTest")
    public  Call<ViewtoplamaTest> fn_toplamaTest(@Body requesttoplamaTest v_Gelen);

}
