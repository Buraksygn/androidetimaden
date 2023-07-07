package com.etimaden.servisbaglanti;

import com.etimaden.request.request_etiket_kontrol;
import com.etimaden.request.request_sec_etiket_no;
import com.etimaden.request.request_yari_otomatik_paket_kontrol_et;
import com.etimaden.request.requestsec_etiket_uretim;
import com.etimaden.response.frg_paket_uretim_ekrani.View_etiket_kontrol;
import com.etimaden.response.frg_paket_uretim_ekrani.View_sec_etiket_no;
import com.etimaden.response.frg_paket_uretim_ekrani.View_yari_otomatik_paket_kontrol_et;
import com.etimaden.response.frg_paket_uretim_ekrani.Viewsec_etiket_uretim;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface frg_paket_uretim_ekrani_interface {

    @POST("api/sec_etiket_uretim")
    Call<Viewsec_etiket_uretim> fn_sec_etiket_uretim(@Body requestsec_etiket_uretim v_Gelen);

    @POST("api/sec_etiket_no")
    Call<View_sec_etiket_no> fn_sec_etiket_no(@Body request_sec_etiket_no v_Gelen);

    @POST("api/etiket_kontrol")
    Call<View_etiket_kontrol> fn_etiket_kontrol(@Body request_etiket_kontrol v_Gelen);

    @POST("api/yari_otomatik_paket_kontrol_et")
    Call<View_yari_otomatik_paket_kontrol_et> fn_yari_otomatik_paket_kontrol_et(@Body request_yari_otomatik_paket_kontrol_et v_Gelen);

}
