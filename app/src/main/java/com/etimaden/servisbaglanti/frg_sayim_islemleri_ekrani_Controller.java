package com.etimaden.servisbaglanti;

import com.etimaden.persosclass.demirbas_sayim;
import com.etimaden.request.request_aktarim_list;
import com.etimaden.request.request_bos;
import com.etimaden.request.request_bos_aktif_isletme_esleme;
import com.etimaden.request.request_demirbas_konum;
import com.etimaden.request.request_demirbas_sayim_list;
import com.etimaden.request.request_demirbas_sayim_string;
import com.etimaden.request.request_string;
import com.etimaden.request.request_string_aktif_isletme_esleme;
import com.etimaden.request.request_uruntag_list;
import com.etimaden.response.frg_paket_uretim_ekrani.View_bool_response;
import com.etimaden.response.frg_paket_uretim_ekrani.View_string_response;
import com.etimaden.response.frg_paket_uretim_ekrani.ViewsecDepoTanimlari;
import com.etimaden.response.sayim_islemleri.View_demirbas_konum_listesi;
import com.etimaden.response.sayim_islemleri.View_demirbas_sayim;
import com.etimaden.response.sayim_islemleri.View_demirbas_sayim_listesi;
import com.etimaden.response.sayim_islemleri.View_depo_sayim_isemri_listesi;
import com.etimaden.response.sayim_islemleri.View_duran_varlik_sap;
import com.etimaden.response.sayim_islemleri.View_malzeme_sayim_isemri_listesi;
import com.etimaden.response.sayim_islemleri.View_zimmet_sonuc_sap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface frg_sayim_islemleri_ekrani_Controller {

    @POST("api/sec_malzeme_sayim_isemri")
    Call<View_malzeme_sayim_isemri_listesi> fn_sec_malzeme_sayim_isemri(@Body request_bos_aktif_isletme_esleme v_Gelen);

    @POST("api/sorgula_sayım_uygunluk")
    Call<View_string_response> fn_sorgula_sayım_uygunluk(@Body request_string_aktif_isletme_esleme v_Gelen);

    @POST("api/aktar_malzeme_sayim_listesi")
    Call<View_bool_response> fn_aktar_malzeme_sayim_listesi(@Body request_aktarim_list v_Gelen);

    @POST("api/sayim_sırasinda_devre_disi_bırak")
    Call<View_bool_response> fn_sayim_sırasinda_devre_disi_bırak(@Body request_uruntag_list v_Gelen);

    @POST("api/guncelle_demirbas_sayim_detay_listesi")
    Call<View_bool_response> fn_guncelle_demirbas_sayim_detay_listesi(@Body request_demirbas_sayim_list v_Gelen);

    @POST("api/iptal_demirbas_sayim_detay_listesi")
    Call<View_bool_response> fn_iptal_demirbas_sayim_detay_listesi(@Body request_demirbas_sayim_list v_Gelen);

    @POST("api/kaydet_demirbas_sayim_detay_listesi")
    Call<View_bool_response> fn_kaydet_demirbas_sayim_detay_listesi(@Body request_demirbas_sayim_list v_Gelen);

    @POST("api/sorgula_duran_varlik")
    Call<View_duran_varlik_sap> fn_sorgula_duran_varlik(@Body request_string v_Gelen);

    @POST("api/sorgula_zimmet")
    Call<View_zimmet_sonuc_sap> fn_sorgula_zimmet(@Body request_demirbas_sayim_string v_Gelen);

    @POST("api/sec_demirbas_detay") //Program.ds_persos.sec_demirbas_detay
    Call<View_demirbas_sayim> fn_sec_demirbas_detay(@Body request_string v_Gelen);

    @POST("api/sec_ek_demirbas_isletme")
    Call<View_demirbas_konum_listesi> fn_sec_ek_demirbas_isletme(@Body request_bos v_Gelen);

    @POST("api/sec_ek_demirbas_bina")
    Call<View_demirbas_konum_listesi> fn_sec_ek_demirbas_bina(@Body request_demirbas_konum v_Gelen);

    @POST("api/sec_ek_demirbas_kat")
    Call<View_demirbas_konum_listesi> fn_sec_ek_demirbas_kat(@Body request_demirbas_konum v_Gelen);

    @POST("api/sec_ek_demirbas_oda")
    Call<View_demirbas_konum_listesi> fn_sec_ek_demirbas_oda(@Body request_demirbas_konum v_Gelen);

    @POST("api/sec_ek_demirbas_detay")
    Call<View_demirbas_sayim_listesi> fn_sec_ek_demirbas_detay(@Body request_demirbas_konum v_Gelen);

    @POST("api/sec_demirbas_isletme")
    Call<View_demirbas_konum_listesi> fn_sec_demirbas_isletme(@Body request_bos v_Gelen);

    @POST("api/sec_demirbas_bina")
    Call<View_demirbas_konum_listesi> fn_sec_demirbas_bina(@Body request_demirbas_konum v_Gelen);

    @POST("api/sec_demirbas_kat")
    Call<View_demirbas_konum_listesi> fn_sec_demirbas_kat(@Body request_demirbas_konum v_Gelen);

    @POST("api/sec_demirbas_oda")
    Call<View_demirbas_konum_listesi> fn_sec_demirbas_oda(@Body request_demirbas_konum v_Gelen);

    //merve
    @POST("api/sec_demirbas_oda_sayim_list")
    Call<View_demirbas_sayim_listesi> fn_sec_demirbas_oda_sayim_list(@Body request_demirbas_konum v_Gelen);
    @POST("api/fn_ekle_ds")
    Call<View_bool_response> fn_ekle_ds(@Body request_demirbas_sayim_string v_Gelen);

    //-----------
    @POST("api/sec_demirbas_detay_list")
    Call<View_demirbas_sayim_listesi> fn_sec_demirbas_detay_list(@Body request_demirbas_konum v_Gelen);

    //merve api
    @POST("api/sec_demirbas_oda_list")
    Call<View_demirbas_konum_listesi> fn_sec_demirbas_oda_list(@Body request_demirbas_konum v_Gelen);

    @POST("api/sec_depo_sayim_isemri")
    Call<View_depo_sayim_isemri_listesi> fn_sec_depo_sayim_isemri(@Body request_bos_aktif_isletme_esleme v_Gelen);

    @POST("api/secDepoTanimlari")
    Call<ViewsecDepoTanimlari> fn_secDepoTanimlari(@Body request_bos v_Gelen);

    @POST("api/sayim_isemri_dosyaid_update")
    Call<View_string_response> fn_sayim_isemri_dosyaid_update(@Body request_string v_Gelen);

    @POST("api/ktar_depo_sayim_listesi")
    Call<View_bool_response> fn_aktar_depo_sayim_listesi(@Body request_aktarim_list v_Gelen);

}
