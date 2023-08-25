package com.etimaden.manipulasyon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.GirisSayfasi;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.persosclass.Urun_tag;
import com.etimaden.persos.Persos;
import com.etimaden.request.request_secetikettag;
import com.etimaden.response.frg_paket_uretim_ekrani.View_secetikettag;
import com.etimaden.ugr_demo.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.etimaden.cSabitDegerler._ipAdresi3G;
import static com.etimaden.cSabitDegerler._zkullaniciadi;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;

public class frg_geribesleme_onay extends Fragment {

    VeriTabani _myIslem;
    public String _ayaraktifkullanici = "";
    public String _ayaraktifdepo = "";
    public String _ayaraktifalttesis = "";
    public String _ayaraktiftesis = "";
    public String _ayaraktifsunucu = "";
    public String _ayaraktifisletmeeslesme = "";
    public String _ayarbaglantituru = "";
    public String _ayarsunucuip = "";
    public String _ayarversiyon = "";
    public String _OnlineUrl = "";

    Persos persos;

    Button _btngeri;


    public frg_geribesleme_onay() {
        // Required empty public constructor
    }

    public static frg_geribesleme_onay newInstance()
    {
        return new frg_geribesleme_onay();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_geribesleme_onay, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((GirisSayfasi)getActivity()).fn_ModBarkod();

        new VeriTabani(getContext()).fn_EpcTemizle();

        _myIslem = new VeriTabani(getContext());

        fn_AyarlariYukle();

        _btngeri=(Button)getView().findViewById(R.id.btngeri);
        _btngeri.playSoundEffect(0);
        _btngeri.setOnClickListener(new fn_Geri());
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

        if (_ayarbaglantituru.equals("wifi"))
        {
            _OnlineUrl = "http://" + _ayarsunucuip + ":" + _zportWifi + "/";
        }
        else
         {
            _OnlineUrl = "http://" + _ipAdresi3G + ":" + _zport3G + "/";
        }

        persos = new Persos(_OnlineUrl);
    }



    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            frg_geribesleme_menu_panel fragmentyeni = new frg_geribesleme_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_geribesleme_menu_panel").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    public void fn_BarkodOkutuldu(String barkod)
    {
        if(barkod.length()<24)
        {
            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("HATA")
                    .setContentText("Uygun olmayan etiket")
                    .setContentTextSize(20)
                    .setConfirmText("TAMAM")
                    .showCancelButton(false)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();

                            return;
                        }
                    })
                    .show();

            return;

        }
        else
        {
            String gidecekbarkod = barkod.substring(barkod.length()-24);

            request_secetikettag v_giden = new request_secetikettag();
            v_giden._rfid=gidecekbarkod;
            v_giden._zaktif_alt_tesis=_ayaraktiftesis;
            v_giden._zaktif_tesis=_ayaraktiftesis;
            v_giden._zkullaniciadi=_zkullaniciadi;
            v_giden._zsifre=_zsifre;
            v_giden._zsunucu_ip_adresi=_ayarsunucuip;
            v_giden._zsurum=_ayarversiyon;
            v_giden.aktif_kullanici=_ayaraktifkullanici;
            v_giden.aktif_sunucu=_ayaraktifsunucu;

            View_secetikettag _yanit = persos.fn_secetikettag(v_giden);

            if(_yanit.get_zSonuc().equals("0"))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentText(_yanit.get_zHataAciklama())
                        .setContentTextSize(20)
                        .setConfirmText("TAMAM")
                        .showCancelButton(false)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();

                                return;
                            }
                        })
                        .show();

                return;
            }


        }
    }

    private void fn_etiketDegerlendir(Urun_tag tag)
    {
        if (tag == null)
        {
            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("HATA")
                    .setContentText("Etiket detayına ulaşılamadı. Ağ bağlantısını kontrol ediniz..")
                    .setContentTextSize(20)
                    .setConfirmText("TAMAM")
                    .showCancelButton(false)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();

                            return;
                        }
                    })
                    .show();

            return;
        }
        else if ( tag.etiket_turu.equals("0"))
        {
            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("HATA")
                    .setContentText("İşlem için uygun olmayan ürün seçimi yaptınız.<br/>Bu işlem için lütfen üretim işlemi tamamlanmış ve satış işlemi yapılmamış bir palet etiketi okutunuz.")
                    .setContentTextSize(20)
                    .setConfirmText("TAMAM")
                    .showCancelButton(false)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();

                            return;
                        }
                    })
                    .show();

            return;
        }
        else if (tag.islem_durumu.equals("1") || tag.islem_durumu.equals("370"))
        {


           // Program.setPage(new geribesleme_harcama_yeri_secimi(tag));
        }
        else
        {

            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("HATA")
                    .setContentText("İşlem için uygun olmayan ürün seçimi yaptınız.<br/>Bu işlem için lütfen üretim işlemi tamamlanmış ve satış işlemi yapılmamış bir palet etiketi okutunuz.")
                    .setContentTextSize(20)
                    .setConfirmText("TAMAM")
                    .showCancelButton(false)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();

                            return;
                        }
                    })
                    .show();

            return;
        }
    }
}
