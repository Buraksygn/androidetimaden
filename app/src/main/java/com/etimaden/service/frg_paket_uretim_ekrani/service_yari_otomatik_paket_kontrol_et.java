package com.etimaden.service.frg_paket_uretim_ekrani;

import com.etimaden.service.request.requestyari_otomatik_paket_kontrol_et;
import com.etimaden.service.response.Viewyari_otomatik_paket_kontrol_et;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface service_yari_otomatik_paket_kontrol_et
{
    @POST("api/yari_otomatik_paket_kontrol_et")//@FormUrlEncoded
    Call<Viewyari_otomatik_paket_kontrol_et> fn_yari_otomatik_paket_kontrol_et(@Body requestyari_otomatik_paket_kontrol_et userInfo);
}
