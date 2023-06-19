package com.etimaden.service.frg_paket_uretim_ekrani;

import com.etimaden.service.request.requestsec_etiket_no;
import com.etimaden.service.response.Viewsec_etiket_no;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface service_sec_etiket_no
{
    @POST("api/sec_etiket_no")//@FormUrlEncoded
    Call<Viewsec_etiket_no> fn_sec_etiket_no(@Body requestsec_etiket_no userInfo);
}
