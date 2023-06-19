package com.etimaden.service.frg_aktif_isemri_indirme;

import com.etimaden.service.request.requestsecYuklenenUrunListesi;
import com.etimaden.service.response.ViewsecYuklenenUrunListesi;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface service_secYuklenenUrunListesi
{
    @POST("api/secYuklenenUrunListesi")//@FormUrlEncoded
    Call<ViewsecYuklenenUrunListesi> fn_secYuklenenUrunListesi(@Body requestsecYuklenenUrunListesi userInfo);

}
