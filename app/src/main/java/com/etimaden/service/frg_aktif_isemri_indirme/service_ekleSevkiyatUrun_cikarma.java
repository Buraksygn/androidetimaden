package com.etimaden.service.frg_aktif_isemri_indirme;

import com.etimaden.service.request.requestekleSevkiyatUrun_cikarma;
import com.etimaden.service.response.ViewekleSevkiyatUrun_cikarma;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface service_ekleSevkiyatUrun_cikarma
{
    @POST("api/ekleSevkiyatUrun_cikarma")//@FormUrlEncoded
    Call<ViewekleSevkiyatUrun_cikarma> fn_ekleSevkiyatUrun_cikarma(@Body requestekleSevkiyatUrun_cikarma userInfo);
}
