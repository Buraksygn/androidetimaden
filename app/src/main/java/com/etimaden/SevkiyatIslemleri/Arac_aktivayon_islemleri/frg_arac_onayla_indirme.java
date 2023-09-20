package com.etimaden.SevkiyatIslemleri.Arac_aktivayon_islemleri;

import static com.etimaden.cSabitDegerler._ipAdresi3G;
import static com.etimaden.cSabitDegerler._sbtVerisyon;
import static com.etimaden.cSabitDegerler._zkullaniciadi;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;

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

import com.etimaden.SevkiyatIslemleri.frg_arac_bulundu_indirme;
import com.etimaden.SevkiyatIslemleri.frg_sevkiyat_menu_panel;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cResponseResult.Sevkiyat_isemri;
import com.etimaden.genel.Genel;
import com.etimaden.persos.Persos;
import com.etimaden.request.request_sevkiyat_isemri;
import com.etimaden.ugr_demo.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class frg_arac_onayla_indirme extends Fragment {

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

    Button _btn_01;
    Button _btn_02;
    Button _btn_03;
    Button _btn_04;
    Button _btngeri;

    Sevkiyat_isemri aktif_sevk_isemri;

    public frg_arac_onayla_indirme()
    {

    }

    public static frg_arac_onayla_indirme newInstance() {

        return new frg_arac_onayla_indirme();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_arac_onayla_indirme, container, false);
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

    public void fn_senddata(Sevkiyat_isemri v_aktif_sevk_isemri) {
        this.aktif_sevk_isemri = v_aktif_sevk_isemri;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        new VeriTabani(getContext()).fn_EpcTemizle();

        _myIslem = new VeriTabani(getContext());

        _btn_01=(Button)getView().findViewById(R.id.btn_01);
        _btn_01.playSoundEffect(0);
        _btn_01.setOnClickListener(new fn_btn_01());

        _btn_02=(Button)getView().findViewById(R.id.btn_02);
        _btn_02.playSoundEffect(0);
        _btn_02.setOnClickListener(new fn_btn_02());

        _btn_03=(Button)getView().findViewById(R.id.btn_03);
        _btn_03.playSoundEffect(0);
        _btn_03.setOnClickListener(new fn_btn_03());

        _btn_04=(Button)getView().findViewById(R.id.btn_04);
        _btn_04.playSoundEffect(0);
        _btn_04.setOnClickListener(new fn_btn_04());

        _btngeri=(Button)getView().findViewById(R.id.btngeri);
        _btngeri.playSoundEffect(0);
        _btngeri.setOnClickListener(new fn_Geri());

        fn_AyarlariYukle();

    }

    private class fn_btn_01 implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_depo_secimi_transfer_indirme fragmentyeni = new frg_depo_secimi_transfer_indirme();
            fragmentyeni.fn_senddata(aktif_sevk_isemri);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_depo_secimi_transfer_indirme").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
    private class fn_btn_02 implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            try
            {
                if (aktif_sevk_isemri.islem_id.equals(""))
                {
                    return;
                }

                if (aktif_sevk_isemri.yapilan_adet.equals("0"))
                {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("İNDİRME MİKTARI UYARISI")
                            .setContentText("ARAÇ ÜZERİNDEKİ ÜRÜN İNDİRME YAPILMAMIŞ DURUMDA. BU ARACIN KAYDINI İPTAL ETMEK İSTEDİĞİNİZDEN EMİN MİSİNİZ?")
                            .setContentTextSize(20)
                            .setConfirmText("EVET")
                            .setCancelText("HAYIR")
                            .showCancelButton(true)
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    request_sevkiyat_isemri _Param= new request_sevkiyat_isemri();
                                    _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
                                    _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
                                    _Param.set_zaktif_tesis(_ayaraktiftesis);
                                    _Param.set_zsurum(_sbtVerisyon);
                                    _Param.set_zkullaniciadi(_zkullaniciadi);
                                    _Param.set_zsifre(_zsifre);
                                    _Param.setAktif_sunucu(_ayaraktifsunucu);
                                    _Param.setAktif_kullanici(_ayaraktifkullanici);

                                    _Param.set_sevkiyat_ismeri(aktif_sevk_isemri);

                                    Genel.showProgressDialog(getContext());
                                    Boolean result = persos.fn_sevkiyatIptal(_Param);
                                    Genel.dismissProgressDialog();

                                    if(result==true){
                                        frg_sevkiyat_menu_panel fragmentyeni = new frg_sevkiyat_menu_panel();
                                        FragmentManager fragmentManager = getFragmentManager();
                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                        fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_sevkiyat_menu_panel").addToBackStack(null);
                                        fragmentTransaction.commit();
                                    }else{
                                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                                .setTitleText("HATA")
                                                .setContentTextSize(25)
                                                .setContentText("İŞLEM YAPILAMADI \r\n NETWORK BAĞLANTISINI KONTROL EDİNİZ..")
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
                    return;
                }
            }
            catch (Exception ex)
            {
                Genel.printStackTrace(ex,getContext());
            }

            try
            {
                request_sevkiyat_isemri _Param= new request_sevkiyat_isemri();
                _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
                _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
                _Param.set_zaktif_tesis(_ayaraktiftesis);
                _Param.set_zsurum(_sbtVerisyon);
                _Param.set_zkullaniciadi(_zkullaniciadi);
                _Param.set_zsifre(_zsifre);
                _Param.setAktif_sunucu(_ayaraktifsunucu);
                _Param.setAktif_kullanici(_ayaraktifkullanici);

                _Param.set_sevkiyat_ismeri(aktif_sevk_isemri);

                Genel.showProgressDialog(getContext());
                Boolean islem_sonucu = persos.fn_sevkiyatDevam_nakil(_Param);
                Genel.dismissProgressDialog();
                if (islem_sonucu)
                {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("ONAY")
                            .setContentText("İşlem başarı ile tamamlanmıştır.")
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
                    return;
                }
                else
                {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("İşlem Başarısız")
                            .setContentTextSize(25)
                            .setContentText("Kayıt yapılamadı. \r\n Veritabanı hatası")
                            .showCancelButton(false)
                            .show();

                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }
    private class fn_btn_03 implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            try
            {
                if (aktif_sevk_isemri.islem_id.equals(""))
                {
                    return;
                }

                if (aktif_sevk_isemri.yapilan_adet.equals("0"))
                {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("İNDİRME MİKTARI UYARISI")
                            .setContentText("ARAÇTAN İNDİRME YAPILMAMIŞ DURUMDA. BU ARACIN KAYDINI İPTAL ETMEK İSTEDİĞİNİZDEN EMİN MİSİNİZ?")
                            .setContentTextSize(20)
                            .setConfirmText("EVET")
                            .setCancelText("HAYIR")
                            .showCancelButton(true)
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    request_sevkiyat_isemri _Param= new request_sevkiyat_isemri();
                                    _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
                                    _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
                                    _Param.set_zaktif_tesis(_ayaraktiftesis);
                                    _Param.set_zsurum(_sbtVerisyon);
                                    _Param.set_zkullaniciadi(_zkullaniciadi);
                                    _Param.set_zsifre(_zsifre);
                                    _Param.setAktif_sunucu(_ayaraktifsunucu);
                                    _Param.setAktif_kullanici(_ayaraktifkullanici);

                                    _Param.set_sevkiyat_ismeri(aktif_sevk_isemri);

                                    Genel.showProgressDialog(getContext());
                                    Boolean result = persos.fn_sevkiyatIptal(_Param);
                                    Genel.dismissProgressDialog();

                                    if(result==true){
                                        frg_sevkiyat_menu_panel fragmentyeni = new frg_sevkiyat_menu_panel();
                                        FragmentManager fragmentManager = getFragmentManager();
                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                        fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_sevkiyat_menu_panel").addToBackStack(null);
                                        fragmentTransaction.commit();
                                    }else{
                                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                                .setTitleText("HATA")
                                                .setContentTextSize(25)
                                                .setContentText("İŞLEM YAPILAMADI \r\n NETWORK BAĞLANTISINI KONTROL EDİNİZ..")
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
                    return;
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }


            try
            {
                request_sevkiyat_isemri _Param= new request_sevkiyat_isemri();
                _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
                _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
                _Param.set_zaktif_tesis(_ayaraktiftesis);
                _Param.set_zsurum(_sbtVerisyon);
                _Param.set_zkullaniciadi(_zkullaniciadi);
                _Param.set_zsifre(_zsifre);
                _Param.setAktif_sunucu(_ayaraktifsunucu);
                _Param.setAktif_kullanici(_ayaraktifkullanici);

                _Param.set_sevkiyat_ismeri(aktif_sevk_isemri);

                Genel.showProgressDialog(getContext());
                Boolean islem_sonucu = persos.fn_sevkiyatKapat(_Param);
                Genel.dismissProgressDialog();
                if (islem_sonucu)
                {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("ONAY")
                            .setContentText("İşlem başarı ile tamamlanmıştır.")
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
                    return;
                }
                else
                {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("İşlem Başarısız")
                            .setContentTextSize(25)
                            .setContentText("Kayıt yapılamadı. \r\n Veritabanı hatası")
                            .showCancelButton(false)
                            .show();

                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }
    private class fn_btn_04 implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_sevkiyat_menu_panel fragmentyeni = new frg_sevkiyat_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_sevkiyat_menu_panel").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_arac_bulundu_indirme fragmentyeni = new frg_arac_bulundu_indirme();
            fragmentyeni.fn_senddata(aktif_sevk_isemri);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_arac_bulundu_indirme").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}
