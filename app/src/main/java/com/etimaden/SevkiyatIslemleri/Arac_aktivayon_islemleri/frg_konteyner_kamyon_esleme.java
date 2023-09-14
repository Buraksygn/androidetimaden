package com.etimaden.SevkiyatIslemleri.Arac_aktivayon_islemleri;

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
import com.etimaden.SevkiyatIslemleri.frg_sevkiyat_menu_panel;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.persosclass.Arac;
import com.etimaden.cResponseResult.Sevkiyat_isemri;
import com.etimaden.genel.Genel;
import com.etimaden.persos.Persos;
import com.etimaden.request.request_sevkiyat_isemri;
import com.etimaden.request.request_sevkiyat_rota_agirlik_konteyner;
import com.etimaden.request.request_string;
import com.etimaden.ugr_demo.R;

import static com.etimaden.cSabitDegerler._ipAdresi3G;
import static com.etimaden.cSabitDegerler._sbtVerisyon;
import static com.etimaden.cSabitDegerler._zkullaniciadi;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class frg_konteyner_kamyon_esleme  extends Fragment {

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
    Persos persos;

    Button _btngeri;

    boolean okunabilir = true;
    Sevkiyat_isemri kamyon = null;


    public static frg_konteyner_kamyon_esleme newInstance() {

        return new frg_konteyner_kamyon_esleme();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_konteyner_kamyon_esleme , container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

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
            _OnlineUrl = "http:/"+_ipAdresi3G+":"+_zport3G+"/";
        }
        persos = new Persos(_OnlineUrl,getContext());
    }

    public void fn_senddata(Sevkiyat_isemri v_aktif_sevk_isemri)
    {
        this.kamyon=v_aktif_sevk_isemri;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        ((GirisSayfasi)getActivity()).fn_ModRFID();

        _myIslem = new VeriTabani(getContext());

        fn_AyarlariYukle();

        _myIslem.fn_EpcTemizle();

        ((GirisSayfasi) getActivity()).fn_ListeTemizle();
        ((GirisSayfasi) getActivity()).fn_GucAyarla(248);

        _btngeri = (Button) getView().findViewById(R.id.btngeri);
        _btngeri.playSoundEffect(0);
        _btngeri.setOnClickListener(new fn_Geri());
    }



    public void rfidOkundu(String rfid)
    {
        try
        {
            if (!okunabilir)
                return;
            okunabilir = false;
            Genel.playQuestionSound(getContext());
            if ( _ayaraktiftesis.equals("5002"))
            {
                request_string _Param= new request_string();
                _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
                _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
                _Param.set_zaktif_tesis(_ayaraktiftesis);
                _Param.set_zsurum(_sbtVerisyon);
                _Param.set_zkullaniciadi(_zkullaniciadi);
                _Param.set_zsifre(_zsifre);
                _Param.setAktif_sunucu(_ayaraktifsunucu);
                _Param.setAktif_kullanici(_ayaraktifkullanici);

                _Param.set_value(rfid);

                Genel.showProgressDialog(getContext());
                final Arac arac = persos.fn_sec_arac(_Param);
                Genel.dismissProgressDialog();

                if ( arac != null && !arac.arac_plaka.equals( ""))
                {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("KONTEYNER ONAY")
                            .setContentText("KONTEYNER PLAKA : " + arac.arac_plaka + "\r\nKONTEYNER OKUNDU. EŞLEŞTİRME İŞLEMİNİ TAMAMLAMAK İSTEDİĞİNİZDEN EMİN MİSNİZ ?")
                            .setContentTextSize(20)
                            .setConfirmText("EVET")
                            .setCancelText("HAYIR")
                            .showCancelButton(true)
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();

                                    Integer toplam_agırlik = Integer.parseInt( arac.arac_bos_tartim )+ Integer.parseInt(kamyon.vardiya) - Integer.parseInt(kamyon.aciklama);

                                    request_sevkiyat_rota_agirlik_konteyner _Param= new request_sevkiyat_rota_agirlik_konteyner();
                                    _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
                                    _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
                                    _Param.set_zaktif_tesis(_ayaraktiftesis);
                                    _Param.set_zsurum(_sbtVerisyon);
                                    _Param.set_zkullaniciadi(_zkullaniciadi);
                                    _Param.set_zsifre(_zsifre);
                                    _Param.setAktif_sunucu(_ayaraktifsunucu);
                                    _Param.setAktif_kullanici(_ayaraktifkullanici);

                                    _Param.set_rota_id(kamyon.rota_id);
                                    _Param.set_agirlik(toplam_agırlik.toString());
                                    _Param.set_konteyner(arac.arac_kod);


                                    Genel.showProgressDialog(getContext());
                                    Boolean result = persos.fn_guncelle_innerliner_bos(_Param);
                                    Genel.dismissProgressDialog();

                                    if (result)
                                    {
                                        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                                                .setTitleText("ONAY")
                                                .setContentText("İşlem onaylandı.")
                                                .setContentTextSize(20)
                                                .setConfirmText("TAMAM")
                                                .showCancelButton(false)
                                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sDialog) {
                                                        sDialog.dismissWithAnimation();
                                                        frg_sevkiyat_menu_panel fragmentyeni = new frg_sevkiyat_menu_panel();
                                                        FragmentManager fragmentManager = getFragmentManager();
                                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                        fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_sevkiyat_menu_panel").addToBackStack(null);
                                                        fragmentTransaction.commit();
                                                        return;
                                                    }
                                                })
                                                .show();

                                    }
                                    else
                                    {
                                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                                .setTitleText("HATA")
                                                .setContentTextSize(25)
                                                .setContentText("KAYIT YAPILAMADI \r\n NETWORK BAĞLANTISINI KONTROL EDİNİZ..")
                                                .showCancelButton(false)
                                                .show();
                                    }
                                    return;
                                }
                            })
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    return;
                                }
                            })
                            .show();

                }

            }
            else
            {

                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("YETKİ DIŞI İŞLEM..BU İŞLETME ÜZERİNDE KONTEYNER SATIŞI TAMAMLAYAMAZSINIZ. \r\n BU İŞLETME ÜZERİNDE KONTEYNER SATIŞI TAMAMLAYAMAZSINIZ.")
                        .showCancelButton(false)
                        .show();
            }

        }
        catch (Exception ex)
        {
            Genel.printStackTrace(ex,getContext());
            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("HATA")
                    .setContentTextSize(25)
                    .setContentText("Aktif konteyner bulunamadı.. \r\n Konteyner tanımlama işlemi tamamlanmamış.")
                    .showCancelButton(false)
                    .show();
        }
        okunabilir = true;


    }

    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_sevkiyat_menu_panel fragmentyeni = new frg_sevkiyat_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_sevkiyat_menu_panel").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}
