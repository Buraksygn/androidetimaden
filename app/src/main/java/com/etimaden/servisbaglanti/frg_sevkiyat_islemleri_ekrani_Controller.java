package com.etimaden.servisbaglanti;

import com.etimaden.cResponseResult.View_sec_sevk_miktar;
import com.etimaden.request.request_bos;
import com.etimaden.request.request_guncelle_sevk_hareket;
import com.etimaden.request.request_sevkiyat_eski_sevk_yeni_sevk;
import com.etimaden.request.request_sevkiyat_isemri;
import com.etimaden.request.request_sevkiyat_zayi;
import com.etimaden.request.request_sevkiyat_zayi_arac;
import com.etimaden.request.request_sevkiyat_zayi_zayiurun_list_zayiurun;
import com.etimaden.request.request_string;
import com.etimaden.response.frg_paket_uretim_ekrani.View_bool_response;
import com.etimaden.response.sevkiyat_islemleri.View_arac;
import com.etimaden.response.sevkiyat_islemleri.View_sevkiyat_isemri;
import com.etimaden.response.sevkiyat_islemleri.View_sevkiyat_isemri_listesi;
import com.etimaden.response.sevkiyat_islemleri.View_sevkiyat_zayi_listesi;
import com.etimaden.response.sevkiyat_islemleri.View_sevkiyat_zayi_urun_listesi;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface frg_sevkiyat_islemleri_ekrani_Controller {

    @POST("api/sec_sevk_miktar2")
    Call<View_sec_sevk_miktar> fn_sec_sevk_miktar(@Body request_string v_Gelen);

    @POST("api/sevkiyatIptal2")
    Call<View_bool_response> fn_sevkiyatIptal(@Body request_sevkiyat_isemri v_Gelen);

    @POST("api/guncelle_sevk_hareket2")
    Call<View_bool_response> fn_guncelle_sevk_hareket(@Body request_guncelle_sevk_hareket v_Gelen);

    @POST("api/get_yukleme_palet_sayisi_miktar2")
    Call<View_sec_sevk_miktar> fn_get_yukleme_palet_sayisi_miktar(@Body request_string v_Gelen);

    @POST("api/sevkiyatDevam2")
    Call<View_bool_response> fn_sevkiyatDevam(@Body request_sevkiyat_isemri v_Gelen);

    //Burdan itibaren eklenecek

    @POST("api/sec_aktif_silobas")
    Call<View_sevkiyat_isemri> fn_sec_aktif_silobas(@Body request_string v_Gelen);

    @POST("api/sec_silobas_SevkiyatIsemriListesi")
    Call<View_sevkiyat_isemri_listesi> fn_sec_silobas_SevkiyatIsemriListesi(@Body request_sevkiyat_isemri v_Gelen);

    @POST("api/onayla_silobas_SevkIsDegisimi")
    Call<View_bool_response> fn_onayla_silobas_SevkIsDegisimi(@Body request_sevkiyat_eski_sevk_yeni_sevk v_Gelen);

    @POST("api/sec_arac")
    Call<View_arac> fn_sec_arac(@Body request_string v_Gelen);

    @POST("api/sec_zayi_aynı_arac")
    Call<View_sevkiyat_zayi_listesi> fn_sec_zayi_aynı_arac(@Body request_string v_Gelen);

    @POST("api/sec_zayi_farklı_arac")
    Call<View_sevkiyat_zayi_listesi> fn_sec_zayi_farklı_arac(@Body request_bos v_Gelen);

    @POST("api/ac_zayi_arac")
    Call<View_bool_response> fn_ac_zayi_arac(@Body request_sevkiyat_zayi_arac v_Gelen);

    @POST("api/guncelle_depo_zayi_arac")
    Call<View_bool_response> fn_guncelle_depo_zayi_arac(@Body request_sevkiyat_zayi v_Gelen);

    @POST("api/kapat_zayi_arac")
    Call<View_bool_response> fn_kapat_zayi_arac(@Body request_sevkiyat_zayi_arac v_Gelen);

    @POST("api/guncelle_vagon_satis")
    Call<View_bool_response> fn_guncelle_vagon_satis(@Body request_bos v_Gelen);

    @POST("api/sec_zayi_arac")
    Call<View_sevkiyat_zayi_listesi> fn_sec_zayi_arac(@Body request_bos v_Gelen);

    @POST("api/sec_zayi_urun_listesi")
    Call<View_sevkiyat_zayi_urun_listesi> fn_sec_zayi_urun_listesi(@Body request_string v_Gelen);

    @POST("api/ekleZayitUrun_cikarma")
    Call<View_bool_response> fn_ekleZayitUrun_cikarma(@Body request_sevkiyat_zayi_zayiurun_list_zayiurun v_Gelen);
}
