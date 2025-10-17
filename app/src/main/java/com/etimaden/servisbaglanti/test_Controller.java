package com.etimaden.servisbaglanti;


import com.etimaden.request.request_string;
import com.etimaden.response.frg_paket_uretim_ekrani.View_string_response;
import com.etimaden.senkronResponse.ViewtoplamaTest;
import com.etimaden.senkronResult.requesttoplamaTest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface test_Controller {

    @POST("api/toplamaTest_01")
    Call<ViewtoplamaTest> fn_toplamaTest_01(@Body requesttoplamaTest v_Gelen);

    @POST("api/toplamaTest_02")
    Call<ViewtoplamaTest> fn_toplamaTest_02(@Body requesttoplamaTest v_Gelen);

    @POST("api/GetRfidGucAyari")
    Call<View_string_response> fn_GetRfidGucAyari();
}
