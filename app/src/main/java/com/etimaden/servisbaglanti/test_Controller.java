package com.etimaden.servisbaglanti;


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
}
