package com.etimaden;

import com.etimaden.servisbaglanti.test_Controller;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.etimaden.cSabitDegerler._ipAdresi3G;

public class ApiServis {

    private static test_Controller api;

    public static synchronized test_Controller Api_test_Controller() {

        if (api == null)
        {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();

            Retrofit retrofit = null;

            try {
                retrofit = new Retrofit.Builder().baseUrl("http://" + _ipAdresi3G + ":9988/")
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
            } catch (Exception ex) {
                String _Hata = ex.toString();
                int _Dur = 1;
            }

            api = retrofit.create(test_Controller.class);
        }
        return api;
    }
}
