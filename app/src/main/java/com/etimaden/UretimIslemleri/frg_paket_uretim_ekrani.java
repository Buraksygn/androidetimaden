package com.etimaden.UretimIslemleri;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.GirisSayfasi;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.persosclass.DEPOTag;
import com.etimaden.persosclass.uretim_etiket;
import com.etimaden.request.requestsec_etiket_uretim;
import com.etimaden.response.frg_paket_uretim_ekrani.Viewsec_etiket_uretim;
import com.etimaden.servisbaglanti.frg_paket_uretim_ekrani_interface;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.etimaden.cSabitDegerler._sbtVerisyon;
import static com.etimaden.cSabitDegerler._zkullaniciadi;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;


public class frg_paket_uretim_ekrani extends Fragment {


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


    uretim_etiket urun;



    Button _btnEtiketsizUretim;
    Button _btnIptal;
    Button _btncikis;

    TextView _txtPaletId;
    TextView _txtParcaBir;
    TextView _txtAra;
    TextView _txtParcaIki;

   // cTanimEnum._eDurum islemDurumu = cTanimEnum._eDurum.ISLEM_YOK;



    List<String> paket_listesi=new ArrayList<>();

    DEPOTag depo = null;

    DEPOTag silo = null;

    Retrofit _retrofit;



    Boolean isReadeable = true;

    int islemDurumu = 0;
    //  0 : etiket okutma bekleniyor
    //  1 : bigbag üretim işlemi yapılıyor
    //  2 : paketli palet üretim işlemi yapılıyor
    //  3 : paket üretim işlemi yapılıyor



    public static frg_paket_uretim_ekrani newInstance() {

        return new frg_paket_uretim_ekrani();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_paket_uretim_ekrani , container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((GirisSayfasi) getActivity()).fn_ModBarkod();

        _myIslem = new VeriTabani(getContext());

        fn_AyarlariYukle();

        _myIslem.fn_EpcTemizle();

        ((GirisSayfasi) getActivity()).fn_ListeTemizle();

        _btnEtiketsizUretim = (Button)getView().findViewById(R.id.btnEtiketsizUretim);
        _btnEtiketsizUretim.playSoundEffect(0);
        _btnEtiketsizUretim.setOnClickListener(new fn_btnEtiketsizUretim());

        _btncikis = (Button)getView().findViewById(R.id.btncikis);
        _btncikis.playSoundEffect(0);
        _btncikis.setOnClickListener(new fn_btncikis());

        _btnIptal = (Button)getView().findViewById(R.id.btnIptal);
        _btnIptal.playSoundEffect(0);
        _btnIptal.setOnClickListener(new fn_btnIptal());

        _txtPaletId = (TextView)getView().findViewById(R.id.txtPaletId);

        _txtParcaBir = (TextView)getView().findViewById(R.id.txtParcaBir);

        _txtAra = (TextView)getView().findViewById(R.id.txtAra);

        _txtParcaIki = (TextView)getView().findViewById(R.id.txtParcaIki);

        fn_AltPanelGorunsunmu(false);
    }

    private void fn_AltPanelGorunsunmu(boolean _bGoster) {
        if (_bGoster == true)
        {
            _btnEtiketsizUretim.setVisibility(View.VISIBLE);

            _btnIptal.setVisibility(View.VISIBLE);

            _txtPaletId.setVisibility(View.VISIBLE);
            _txtParcaBir.setVisibility(View.VISIBLE);
            _txtAra.setVisibility(View.VISIBLE);
            _txtParcaIki.setVisibility(View.VISIBLE);
        }
        else
            {
            _btnEtiketsizUretim.setVisibility(View.INVISIBLE);

            _btnIptal.setVisibility(View.INVISIBLE);

            _txtPaletId.setVisibility(View.INVISIBLE);
            _txtParcaBir.setVisibility(View.INVISIBLE);
            _txtAra.setVisibility(View.INVISIBLE);
            _txtParcaIki.setVisibility(View.INVISIBLE);

        }
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
            _OnlineUrl = "http://88.255.50.73:"+_zport3G+"/";
        }
        _retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(_OnlineUrl)
                .build();
    }


    public void barkodOkundu(String barkod)
    {
        barkod = barkod.trim();

        if(barkod.length()>=24)
        {
            int Sayac = barkod.length();

            barkod = barkod.substring(barkod.length()-24);

            frg_paket_uretim_ekrani_interface _ApiServis = _retrofit.create(frg_paket_uretim_ekrani_interface.class);

            requestsec_etiket_uretim _Param=new requestsec_etiket_uretim();
            //region Sabit değerleri yükle
            _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
            _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
            _Param.set_zaktif_tesis(_ayaraktiftesis);
            _Param.set_zsurum(_sbtVerisyon);
            _Param.set_zkullaniciadi(_zkullaniciadi);
            _Param.set_zsifre(_zsifre);
            _Param.setaktif_sunucu(_ayaraktifsunucu);
            _Param.setaktif_kullanici(_ayaraktifkullanici);
            //
            _Param.set_etiket(barkod);

            Call<Viewsec_etiket_uretim> call = _ApiServis.fn_sec_etiket_uretim(_Param);
            call.enqueue(new Callback<Viewsec_etiket_uretim>() {

                @Override
                public void onResponse(Call<Viewsec_etiket_uretim> call, Response<Viewsec_etiket_uretim> response) {

                    Viewsec_etiket_uretim _Yanit = response.body();

                    urun = _Yanit.get_tag();

                    if (_Yanit.get_zSonuc().equals("0")) {
                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("HATA")
                                .setContentText(_Yanit.get_zHataAciklama())
                                .setContentTextSize(20)
                                .setConfirmText("TAMAM")
                                .showCancelButton(false)
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                    }
                                })
                                .show();
                    } else if (!_Yanit.get_tag().getetiket_durumu().equals("0")) {
                        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Üretilmiş Etiket")
                                .setContentText("Üretim için uygun olmayan etiketi.Etiketin üretim işlemi tamamlanmıştır.")
                                .setContentTextSize(20)
                                .setConfirmText("TAMAM")
                                .showCancelButton(false)
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                    }
                                })
                                .show();
                        return;
                    } else if (!_Yanit.get_tag().getetiket_uretim_uygunlugu().equals("1")) {
                        String _Yazi = "ÜRETİM ONAYI ALINAMADI.ÜRETİM KURALLARI";
                        _Yazi += "<br/>1) İLK 5 DAKİKA İÇİNDE ÜRETİM İŞLEMİNİ GERÇEKLEŞTİREMEZSİNİZ.";
                        _Yazi += "<br/>2) 3 GÜN İÇİNDE ÜRETİM KAYDI GERÇEKLEŞMEYEN ETİKET İÇİN ÜRETİM KAYDI OLUŞTURULAMAZ.";
                        _Yazi += "<br/>3) SİSTEM İÇİNDE AYNI İŞ EMRİNE AİT EN FAZLA 2 ADET ÜRETİMİ TAMAMLANMAMIŞ LOT BULUNABİLİR.";


                        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("ONAY ALINAMADI")
                                .setContentText(_Yazi)
                                .setContentTextSize(16)
                                .setConfirmText("TAMAM")
                                .showCancelButton(false)
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                    }
                                })
                                .show();
                        return;
                    }else if (depo == null || silo == null)
                    {
                        frg_depo_secimi fragmentyeni = new frg_depo_secimi();
                        fragmentyeni.fn_senddata(urun);
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_depo_secimi").addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                }

                @Override
                public void onFailure(Call<Viewsec_etiket_uretim> call, Throwable t) {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("HATA")
                            .setContentText("Sistemsel Hata = "+t.getMessage())
                            .setContentTextSize(20)
                            .setConfirmText("TAMAM")
                            .showCancelButton(false)
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
                            {
                                @Override
                                public void onClick(SweetAlertDialog sDialog)
                                {
                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .show();

                }
            });
        }

    }





    private class fn_btnEtiketsizUretim implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }

    private class fn_btnIptal implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }

    private class fn_btncikis implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            frg_uretim_menu_panel fragmentyeni = new frg_uretim_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_uretim_menu_panel").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}
