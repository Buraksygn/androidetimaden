package com.etimaden.servisbaglanti;

import com.etimaden.request.request_ambalaj_tipi_secEtiket;
import com.etimaden.request.request_string;
import com.etimaden.request.request_uruntag;
import com.etimaden.request.request_uruntag_depotag;
import com.etimaden.request.request_uruntag_list_uruntag;
import com.etimaden.request.request_uruntag_string;
import com.etimaden.response.frg_paket_uretim_ekrani.View_ambalaj_tipi_secEtiket;
import com.etimaden.response.frg_paket_uretim_ekrani.View_bool_response;
import com.etimaden.response.frg_paket_uretim_ekrani.View_secEtiket;
import com.etimaden.response.frg_paket_uretim_ekrani.View_string_response;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface frg_depolar_arasi_sevk_islemleri_ekrani_Controller
{
    @POST("api/secDepoSevkEtiket")
    Call<View_secEtiket> fn_secDepoSevkEtiket(@Body request_string v_Gelen);

    @POST("api/depoSevkTamamla")
    Call<View_bool_response> fn_depoSevkTamamla(@Body request_uruntag_depotag v_Gelen);
}
