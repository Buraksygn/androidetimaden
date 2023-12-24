package com.etimaden.servisbaglanti;

import com.etimaden.cResponseResult.View_sec_sevk_miktar;
import com.etimaden.request.request_aktarim_list;
import com.etimaden.request.request_bos;
import com.etimaden.request.request_bos_aktif_isletme_esleme;
import com.etimaden.request.request_guncelle_sevk_hareket;
import com.etimaden.request.request_sevkiyat_aktarim;
import com.etimaden.request.request_sevkiyat_eski_sevk_yeni_sevk;
import com.etimaden.request.request_sevkiyat_isemri;
import com.etimaden.request.request_sevkiyat_isemri_depo;
import com.etimaden.request.request_sevkiyat_isemri_sevkiyat_isemri;
import com.etimaden.request.request_sevkiyat_isemri_uruntag_list_uruntag;
import com.etimaden.request.request_sevkiyat_rota_agirlik_konteyner;
import com.etimaden.request.request_sevkiyat_vagon_hareket_isemri;
import com.etimaden.request.request_sevkiyat_zayi;
import com.etimaden.request.request_sevkiyat_zayi_arac;
import com.etimaden.request.request_sevkiyat_zayi_zayiurun_list_zayiurun;
import com.etimaden.request.request_string;
import com.etimaden.request.request_string_aktif_isletme_esleme;
import com.etimaden.request.request_string_string;
import com.etimaden.request.request_uruntag_list;
import com.etimaden.request.requestsecDepoTanimlari;
import com.etimaden.response.frg_paket_uretim_ekrani.View_bool_response;
import com.etimaden.response.frg_paket_uretim_ekrani.View_string_response;
import com.etimaden.response.frg_paket_uretim_ekrani.ViewsecDepoTanimlari;
import com.etimaden.response.sayim_islemleri.View_malzeme_sayim_isemri_listesi;
import com.etimaden.response.sevkiyat_islemleri.View_arac;
import com.etimaden.response.sevkiyat_islemleri.View_sevkiyat_bekleyen_arac_listesi;
import com.etimaden.response.sevkiyat_islemleri.View_sevkiyat_isemri;
import com.etimaden.response.sevkiyat_islemleri.View_sevkiyat_isemri_listesi;
import com.etimaden.response.sevkiyat_islemleri.View_sevkiyat_urun_sevkiyat;
import com.etimaden.response.sevkiyat_islemleri.View_sevkiyat_urun_sevkiyat_listesi;
import com.etimaden.response.sevkiyat_islemleri.View_sevkiyat_urun_tag_listesi;
import com.etimaden.response.sevkiyat_islemleri.View_sevkiyat_vagon_hareket;
import com.etimaden.response.sevkiyat_islemleri.View_sevkiyat_zayi_listesi;
import com.etimaden.response.sevkiyat_islemleri.View_sevkiyat_zayi_urun_listesi;
import com.etimaden.response.sevkiyat_islemleri.View_string_list;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface frg_sayim_islemleri_ekrani_Controller {

    @POST("api/sec_malzeme_sayim_isemri")
    Call<View_malzeme_sayim_isemri_listesi> fn_sec_malzeme_sayim_isemri(@Body request_bos v_Gelen);

    @POST("api/sorgula_sayım_uygunluk")
    Call<View_string_response> fn_sorgula_sayım_uygunluk(@Body request_string v_Gelen);

    @POST("api/aktar_malzeme_sayim_listesi")
    Call<View_bool_response> fn_aktar_malzeme_sayim_listesi(@Body request_aktarim_list v_Gelen);

    @POST("api/sayim_sırasinda_devre_disi_bırak")
    Call<View_bool_response> fn_sayim_sırasinda_devre_disi_bırak(@Body request_uruntag_list v_Gelen);



}
