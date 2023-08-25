package com.etimaden.servisbaglanti;

import com.etimaden.request.request_bos;
import com.etimaden.request.request_etiket_kontrol;
import com.etimaden.request.request_get_lot_toplami;
import com.etimaden.request.request_paketliUret;
import com.etimaden.request.request_paketliUret_otomatik;
import com.etimaden.request.request_secEtiket;
import com.etimaden.request.request_sec_etiket_no;
import com.etimaden.request.request_shrink_is_emri;
import com.etimaden.request.request_shrink_onayi_al;
import com.etimaden.request.request_uretim_etiket;
import com.etimaden.request.request_uretim_iptali;
import com.etimaden.request.request_uretim_zayi;
import com.etimaden.request.request_yari_otomatik_paket_kontrol_et;
import com.etimaden.request.requestsec_etiket_uretim;
import com.etimaden.response.frg_paket_uretim_ekrani.View_bool_response;
import com.etimaden.response.frg_paket_uretim_ekrani.View_etiket_kontrol;
import com.etimaden.response.frg_paket_uretim_ekrani.View_get_lot_toplami;
import com.etimaden.response.frg_paket_uretim_ekrani.View_paketliUret;
import com.etimaden.response.frg_paket_uretim_ekrani.View_paketliUret_otomatik;
import com.etimaden.response.frg_paket_uretim_ekrani.View_secEtiket;
import com.etimaden.response.frg_paket_uretim_ekrani.View_sec_etiket_no;
import com.etimaden.response.frg_paket_uretim_ekrani.View_sec_uretim_detay;
import com.etimaden.response.frg_paket_uretim_ekrani.View_string_response;
import com.etimaden.response.frg_paket_uretim_ekrani.View_uretim_zayi;
import com.etimaden.response.frg_paket_uretim_ekrani.View_yari_otomatik_paket_kontrol_et;
import com.etimaden.response.frg_paket_uretim_ekrani.Viewsec_etiket_uretim;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface frg_paket_uretim_ekrani_Controller {

    @POST("api/paketliUret")
    Call<View_paketliUret> fn_paketliUret(@Body request_paketliUret v_Gelen);

    @POST("api/secEtiket")
    Call<View_secEtiket> fn_secEtiket(@Body request_secEtiket v_Gelen);

    @POST("api/get_lot_toplami")
    Call<View_get_lot_toplami> fn_get_lot_toplami(@Body request_get_lot_toplami v_Gelen);

    @POST("api/paketliUret_otomatik")
    Call<View_paketliUret_otomatik> fn_paketliUret_otomatik(@Body request_paketliUret_otomatik v_Gelen);

    @POST("api/sec_etiket_uretim")
    Call<Viewsec_etiket_uretim> fn_sec_etiket_uretim(@Body requestsec_etiket_uretim v_Gelen);

    @POST("api/sec_etiket_no")
    Call<View_sec_etiket_no> fn_sec_etiket_no(@Body request_sec_etiket_no v_Gelen);

    @POST("api/etiket_kontrol")
    Call<View_etiket_kontrol> fn_etiket_kontrol(@Body request_etiket_kontrol v_Gelen);

    @POST("api/yari_otomatik_paket_kontrol_et")
    Call<View_yari_otomatik_paket_kontrol_et> fn_yari_otomatik_paket_kontrol_et(@Body request_yari_otomatik_paket_kontrol_et v_Gelen);

    @POST("api/uretim_zayi")
    Call<View_uretim_zayi> fn_uretim_zayi(@Body request_uretim_zayi v_Gelen);

    @POST("api/bigBag_uret")
    Call<View_bool_response> fn_bigBag_uret(@Body request_uretim_etiket v_Gelen);

    @POST("api/sec_ambalaj_degisim_toplam_harcanan_miktar")
    Call<View_string_response> fn_sec_ambalaj_degisim_toplam_harcanan_miktar(@Body request_uretim_etiket v_Gelen);

    @POST("api/sec_toplam_dkmlot_miktar")
    Call<View_string_response> fn_sec_toplam_dkmlot_miktar(@Body request_uretim_etiket v_Gelen);

    @POST("api/dkmpalet_lotundan_uretim")
    Call<View_bool_response> fn_dkmpalet_lotundan_uretim(@Body request_uretim_etiket v_Gelen);

    @POST("api/setShrinkIsemri")
    Call<View_bool_response> fn_setShrinkIsemri(@Body request_shrink_is_emri v_Gelen);

    @POST("api/manipulasyon_bigbag_uret")
    Call<View_bool_response> fn_manipulasyon_bigbag_uret(@Body request_uretim_etiket v_Gelen);

    @POST("api/shrink_onayi_al")
    Call<View_bool_response> fn_shrink_onayi_al(@Body request_shrink_onayi_al v_Gelen);

    @POST("api/uretim_iptali")
    Call<View_bool_response> fn_uretim_iptali(@Body request_uretim_iptali v_Gelen);

    @POST("api/sec_uretim_detay")
    Call<View_sec_uretim_detay> fn_sec_uretim_detay(@Body request_bos v_Gelen);
}
