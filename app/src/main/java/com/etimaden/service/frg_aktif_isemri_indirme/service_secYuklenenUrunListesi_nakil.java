package com.etimaden.service.frg_aktif_isemri_indirme;

import com.etimaden.service.request.requestsecYuklenenUrunListesi_nakil;
import com.etimaden.service.response.ViewsecYuklenenUrunListesi_nakil;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface service_secYuklenenUrunListesi_nakil {

    @POST("api/secYuklenenUrunListesi_nakil")//@FormUrlEncoded
    Call<ViewsecYuklenenUrunListesi_nakil> fn_secYuklenenUrunListesi_nakil(@Body requestsecYuklenenUrunListesi_nakil userInfo);

}
