package com.etimaden.servisbaglanti;

import com.etimaden.request.requestsecDepoTanimlari;
import com.etimaden.response.frg_paket_uretim_ekrani.ViewsecDepoTanimlari;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface frg_depo_secimi_interface {

    @POST("api/secDepoTanimlari")
    Call<ViewsecDepoTanimlari> fn_secDepoTanimlari(@Body requestsecDepoTanimlari v_Gelen);
}
