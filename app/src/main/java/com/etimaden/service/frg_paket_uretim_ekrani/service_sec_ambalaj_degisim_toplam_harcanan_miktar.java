package com.etimaden.service.frg_paket_uretim_ekrani;

import com.etimaden.service.request.requestsec_ambalaj_degisim_toplam_harcanan_miktar;
import com.etimaden.service.response.Viewsec_ambalaj_degisim_toplam_harcanan_miktar;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface service_sec_ambalaj_degisim_toplam_harcanan_miktar {

    @POST("api/sec_ambalaj_degisim_toplam_harcanan_miktar")//@FormUrlEncoded
    Call<Viewsec_ambalaj_degisim_toplam_harcanan_miktar> fn_sec_ambalaj_degisim_toplam_harcanan_miktar(@Body requestsec_ambalaj_degisim_toplam_harcanan_miktar userInfo);

}
