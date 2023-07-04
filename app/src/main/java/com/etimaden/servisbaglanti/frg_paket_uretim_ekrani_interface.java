package com.etimaden.servisbaglanti;

import com.etimaden.request.requestsec_etiket_uretim;
import com.etimaden.response.frg_paket_uretim_ekrani.Viewsec_etiket_uretim;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface frg_paket_uretim_ekrani_interface {

    @POST("api/sec_etiket_uretim")
    Call<Viewsec_etiket_uretim> fn_sec_etiket_uretim(@Body requestsec_etiket_uretim v_Gelen);

}
