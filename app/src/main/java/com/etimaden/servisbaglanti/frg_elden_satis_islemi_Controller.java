package com.etimaden.servisbaglanti;

import com.etimaden.request.request_bos;
import com.etimaden.request.request_integer_sevkiyat_isemri;
import com.etimaden.request.request_integer_sevkiyat_isemri_stringlist;
import com.etimaden.request.request_string;
import com.etimaden.request.request_string_string_string;
import com.etimaden.request.request_uruntag;
import com.etimaden.request.request_uruntag_depotag;
import com.etimaden.request.request_uruntag_list;
import com.etimaden.response.depolar_arasi_sevk.View_lot_detay_list;
import com.etimaden.response.frg_paket_uretim_ekrani.View_bool_response;
import com.etimaden.response.frg_paket_uretim_ekrani.View_secEtiket;
import com.etimaden.response.frg_paket_uretim_ekrani.View_string_response;
import com.etimaden.response.sevkiyat_islemleri.View_sevkiyat_isemri_listesi;
import com.etimaden.response.sevkiyat_islemleri.View_string_list;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface frg_elden_satis_islemi_Controller
{
    @POST("api/sec_perakende_satis_isemri_listesi")
    Call<View_sevkiyat_isemri_listesi> fn_sec_perakende_satis_isemri_listesi(@Body request_bos v_Gelen);

    //todo aynı isimde request_uretim_etiket tipinde parametre alan bi servis daha var.alt satirdaki ismi tanimamana göre ayarlarsın
    @POST("api/sec_toplam_dkmlot_miktar_satis")
    Call<View_string_response> fn_sec_toplam_dkmlot_miktar_satis(@Body request_string_string_string v_Gelen);

    @POST("api/sec_dkm_etiket_listesi")
    Call<View_string_list> fn_sec_dkm_etiket_listesi(@Body request_integer_sevkiyat_isemri v_Gelen);

    @POST("api/onayla_perakende_satis")
    Call<View_bool_response> fn_onayla_perakende_satis(@Body request_integer_sevkiyat_isemri_stringlist v_Gelen);
}
