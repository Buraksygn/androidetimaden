package com.etimaden.UretimIslemleri;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.adapter.apmblDepoListesi;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.genel.SweetAlertDialogG;
import com.etimaden.persosclass.DEPOTag;
import com.etimaden.persosclass.uretim_etiket;
import com.etimaden.request.requestsecDepoTanimlari;
import com.etimaden.response.frg_paket_uretim_ekrani.ViewsecDepoTanimlari;
import com.etimaden.servisbaglanti.frg_depo_secimi_interface;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.etimaden.cSabitDegerler._ipAdresi3G;
import static com.etimaden.cSabitDegerler._sbtVerisyon;
import static com.etimaden.cSabitDegerler._zkullaniciadi;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;


public class frg_depo_secimi extends Fragment {

    VeriTabani _myIslem;
    String _ayaraktifkullanici = "";
    String _ayaraktifdepo = "";
    String _ayaraktifalttesis = "";
    String _ayaraktiftesis = "";
    String _ayaraktifsunucu = "";
    String _ayaraktifisletmeeslesme = "";
    String _ayarbaglantituru = "";
    String _ayarsunucuip = "";
    String _ayarversiyon = "";
    String _OnlineUrl = "";

    Retrofit _retrofit;

    uretim_etiket urun;

    Button _btncikis;
    Button _btn_03;

    ArrayList<DEPOTag> dataModels;
    DEPOTag _Secili = new DEPOTag();
    private static apmblDepoListesi adapter;

    public ListView _Liste;

    public frg_depo_secimi() {
        // Required empty public constructor
    }
    public static frg_depo_secimi newInstance()
    {
        return new frg_depo_secimi();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_depo_secimi, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        _myIslem = new VeriTabani(getContext());

        _btn_03 = (Button) getView().findViewById(R.id.btn_03);
        _btn_03.playSoundEffect(0);
        _btn_03.setOnClickListener(new fn_btn03());

        _btncikis = (Button) getView().findViewById(R.id.btncikis);
        _btncikis.playSoundEffect(0);
        _btncikis.setOnClickListener(new fn_Geri());

        _Liste = (ListView) getView().findViewById(R.id.depo_list);

        _Liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _Secili = dataModels.get(position);
            }
        });

        fn_AyarlariYukle();

        fn_DepoListele();

    }

    private void fn_AyarlariYukle()
    {
        _ayarbaglantituru=_myIslem.fn_baglanti_turu();
        _ayarsunucuip=_myIslem.fn_sunucu_ip();
        _ayaraktifkullanici=_myIslem.fn_aktif_kullanici();
        _ayaraktifdepo=_myIslem.fn_aktif_depo();
        _ayaraktifalttesis=_myIslem.fn_aktif_alt_tesis();
        _ayaraktiftesis=_myIslem.fn_aktif_tesis();
        _ayaraktifsunucu=_myIslem.fn_aktif_sunucu();
        _ayaraktifisletmeeslesme=_myIslem.fn_isletmeeslesme();

        if(_ayarbaglantituru.equals("wifi"))
        {
            _OnlineUrl = "http://"+_ayarsunucuip+":"+_zportWifi+"/";
        }
        else
        {
            _OnlineUrl = "http://"+_ipAdresi3G+":"+_zport3G+"/";
        }
        _retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(_OnlineUrl)
                .build();
    }

    private void fn_DepoListele()
    {
        frg_depo_secimi_interface _ApiServis = _retrofit.create(frg_depo_secimi_interface.class);

        requestsecDepoTanimlari _Param=new requestsecDepoTanimlari();

        //region Sabit değerleri yükle
        _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
        _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
        _Param.set_zaktif_tesis(_ayaraktiftesis);
        _Param.set_zsurum(_sbtVerisyon);
        _Param.set_zkullaniciadi(_zkullaniciadi);
        _Param.set_zsifre(_zsifre);
        _Param.setAktif_sunucu(_ayaraktifsunucu);
        _Param.setAktif_kullanici(_ayaraktifkullanici);

        _Param.setDepo_turu("0");//var
        _Param.setIsletme(urun.getIsletme());//var
        //_Param.setDepo_id(urun.getDepo_silo_secimi());
        _Param.setDepo_silo_secimi(urun.getDepo_silo_secimi());
        _Param.setDepo_silo_secimi_kontrol(true);
        //

        Call<ViewsecDepoTanimlari> call = _ApiServis.fn_secDepoTanimlari(_Param);

        call.enqueue(new Callback<ViewsecDepoTanimlari>() {
            @Override
            public void onResponse(Call<ViewsecDepoTanimlari> call, Response<ViewsecDepoTanimlari> response) {

                ViewsecDepoTanimlari _Yanit = response.body();

                List<DEPOTag> _DepoListesi= _Yanit.get_DepoListesi();

                if (_Yanit.get_zSonuc().equals("0")) {
                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                            .setTitleText("HATA")
                            .setContentText(_Yanit.get_zHataAciklama())
                            .setContentTextSize(20)
                            .setConfirmText("TAMAM")
                            .showCancelButton(false)
                            .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialogG sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                }
                else
                {
                    dataModels= new ArrayList<>();

                    for(int iSayac = 0;iSayac<_DepoListesi.size();iSayac++)
                    {
                        dataModels.add(new DEPOTag(
                                _DepoListesi.get(iSayac).getDepo_id()+"",
                                _DepoListesi.get(iSayac).getDepo_adi()+"",
                                _DepoListesi.get(iSayac).getIsletme_kod()+"",
                                _DepoListesi.get(iSayac).getAlt_isletme_kod()+"",
                                _DepoListesi.get(iSayac).getDepo_giris_tag()+"",
                                _DepoListesi.get(iSayac).getDepo_cikis_tag()+"",
                                _DepoListesi.get(iSayac).getDepo_turu()+"",
                                _DepoListesi.get(iSayac).getDepo_kod_isletmeesleme()+""
                                )
                        );
                    }

                    adapter= new apmblDepoListesi(dataModels,getContext());

                    _Liste.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ViewsecDepoTanimlari> call, Throwable t) {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentText("Sistemsel Hata = "+t.getMessage())
                        .setContentTextSize(20)
                        .setConfirmText("TAMAM")
                        .showCancelButton(false)
                        .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener()
                        {
                            @Override
                            public void onClick(SweetAlertDialogG sDialog)
                            {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });

    }



    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_paket_uretim_ekrani fragmentyeni = new frg_paket_uretim_ekrani();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_paket_uretim_ekrani").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_btn03 implements View.OnClickListener {
        @Override
        public void onClick(View view) {

           if(_Secili.getDepo_id().equals(""))
           {

               new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                       .setTitleText("HATA")
                       .setContentText("Lütfen depo seçiniz")
                       .setContentTextSize(20)
                       .setConfirmText("TAMAM")
                       .showCancelButton(false)
                       .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener()
                       {
                           @Override
                           public void onClick(SweetAlertDialogG sDialog)
                           {
                               sDialog.dismissWithAnimation();




                           }
                       })
                       .show();

           }
           else
           {
               frg_silo_secimi fragmentyeni = new frg_silo_secimi();
               fragmentyeni.fn_senddata(_Secili,urun);
               FragmentManager fragmentManager = getFragmentManager();
               FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
               fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_silo_secimi").addToBackStack(null);
               fragmentTransaction.commit();
           }
        }
    }

    public void fn_senddata(uretim_etiket v_uretim_etiket)
    {
        urun = v_uretim_etiket;
    }
}
