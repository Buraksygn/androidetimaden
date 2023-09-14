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

import com.etimaden.GirisSayfasi;
import com.etimaden.SevkiyatIslemleri.frg_arac_bulundu_indirme;
import com.etimaden.SevkiyatIslemleri.frg_sevkiyat_menu_panel;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cResponseResult.Sevkiyat_isemri;
import com.etimaden.genel.Genel;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.Vagon_hareket;
import com.etimaden.request.request_sevkiyat_isemri;
import com.etimaden.request.request_string;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class frg_arac_aktivasyon extends Fragment {

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


    Button _btnBekleyenAracListesi;
    Button _btngeri;

    boolean okunabilir = true;

    public frg_arac_aktivasyon() {
        // Required empty public constructor
    }

    public static frg_arac_aktivasyon newInstance()
    {
        return new frg_arac_aktivasyon();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_arac_aktivasyon, container, false);
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        new VeriTabani(getContext()).fn_EpcTemizle();

        _myIslem = new VeriTabani(getContext());

        ((GirisSayfasi) getActivity()).fn_ModRFID();


        _btnBekleyenAracListesi = (Button)getView().findViewById(R.id.btnBekleyenAracListesi);
        _btnBekleyenAracListesi.playSoundEffect(0);
        _btnBekleyenAracListesi.setOnClickListener(new fn_btnBekleyenAracListesi());

        _btngeri = (Button)getView().findViewById(R.id.btngeri);
        _btngeri.playSoundEffect(0);
        _btngeri.setOnClickListener(new fn_Geri());


        fn_AyarlariYukle();

    }

    public void rfidOkundu(String rfid)
    {
        try
        {
            if (!okunabilir)
                return;
            okunabilir = false;
            Genel.playQuestionSound(getContext());

            if (!rfid.startsWith("737767302"))
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
                List<Sevkiyat_isemri> result = persos.fn_secKantarIsemriListesi(_Param);
                ArrayList<Sevkiyat_isemri> sevk_isemri_listesi=new ArrayList<>(result);
                Genel.dismissProgressDialog();

                Sevkiyat_isemri aktif_sevk_isemri=new Sevkiyat_isemri();
                for(Sevkiyat_isemri w : sevk_isemri_listesi){
                    if(w.arac_rfid.equals(rfid)){
                        aktif_sevk_isemri=w;
                        break;
                    }
                }

                if(aktif_sevk_isemri!=null && aktif_sevk_isemri.karakteristikler!=null) {
                    String[] karakterler = aktif_sevk_isemri.karakteristikler.split(",");

                    if (karakterler[7].equals("0020")) {
                        frg_konteyner_kamyon_esleme fragmentyeni = new frg_konteyner_kamyon_esleme();
                        fragmentyeni.fn_senddata(aktif_sevk_isemri);
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_konteyner_kamyon_esleme").addToBackStack(null);
                        fragmentTransaction.commit();
                    } else if (!aktif_sevk_isemri.hedef_isletme_alt_kodu.equals(_ayaraktifalttesis)) {
                        frg_arac_bulundu fragmentyeni = new frg_arac_bulundu();
                        fragmentyeni.fn_senddata(aktif_sevk_isemri);
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_arac_bulundu").addToBackStack(null);
                        fragmentTransaction.commit();
                    } else {

                    }
                }
            }
            else
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
                Vagon_hareket vagon = persos.fn_sec_vagon_hareket(_Param);
                Genel.dismissProgressDialog();

                if (vagon == null)
                {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("HATA")
                            .setContentTextSize(25)
                            .setContentText("Aktif vagon kaydı bulunamadı..")
                            .showCancelButton(false)
                            .show();
                }
                else
                {
                    frg_konteyner_vagon_esleme fragmentyeni = new frg_konteyner_vagon_esleme();
                    fragmentyeni.fn_senddata(vagon);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_konteyner_vagon_esleme").addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        }
        catch (Exception ex)
        {
            try
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
                List<Sevkiyat_isemri> result = persos.fn_secKantarIndirmeIsemriListesi(_Param);
                ArrayList<Sevkiyat_isemri> sevk_isemri_listesi=new ArrayList<>(result);
                Genel.dismissProgressDialog();

                Sevkiyat_isemri aktif_sevk_isemri=new Sevkiyat_isemri();
                for(Sevkiyat_isemri w : sevk_isemri_listesi){
                    if(w.arac_rfid.equals(rfid)){
                        aktif_sevk_isemri=w;
                        break;
                    }
                }
                if (_ayaraktiftesis.equals("2003") && (aktif_sevk_isemri.aciklama.equals("17") || aktif_sevk_isemri.aciklama.equals("1")) && !aktif_sevk_isemri.vardiya.equals(""))
                {
                    final Sevkiyat_isemri _aktif_sevk_isemri=aktif_sevk_isemri;
                    new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("SEVKİYAT YÖNLENDİRMESİ")
                            .setContentText("ARAÇ İÇİN GEMİ YÜKLEMESİ SEÇİLMİŞ. GEMİ YÜKLEMESİ TAMAMLANDIYSA \"EVET\" \n DEPOYA ALMA İÇİN \"HAYIR\" \n SEÇENEĞİNİ SEÇİNİZ!! ")
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

                                    _Param.set_sevkiyat_ismeri(_aktif_sevk_isemri);

                                    Genel.showProgressDialog(getContext());
                                    Boolean res_aktarım = persos.fn_update_gemi_transfer_direct(_Param);
                                    Genel.dismissProgressDialog();


                                    if (res_aktarım==true)
                                    {
                                        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                                                .setTitleText("İşlem Onayı")
                                                .setContentText("İşlem başarı ile tamamlanmıştır. Aracı kantara gönderiniz.")
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
                                                .setTitleText("İşlem Başarısız")
                                                .setContentTextSize(25)
                                                .setContentText("Kayıt yapılamadı. \r\n Veritabanı hatası")
                                                .showCancelButton(false)
                                                .show();
                                    }
                                }
                            })
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    if (_aktif_sevk_isemri.hedef_isletme_alt_kodu.equals(_ayaraktifalttesis))
                                    {
                                        frg_arac_bulundu_indirme fragmentyeni = new frg_arac_bulundu_indirme();
                                        fragmentyeni.fn_senddata(_aktif_sevk_isemri);
                                        FragmentManager fragmentManager = getFragmentManager();
                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                        fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_sevkiyat_menu_panel").addToBackStack(null);
                                        fragmentTransaction.commit();
                                    }
                                    else
                                    {
                                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                                .setTitleText("HATA")
                                                .setContentTextSize(25)
                                                .setContentText("Aktif araç işemri bulunamadı.. \r\n Araç kantardan geçiş işlemini tamamlamamış.")
                                                .showCancelButton(false)
                                                .show();
                                    }
                                }
                            })
                            .show();

                }
                else
                {
                    if (aktif_sevk_isemri.hedef_isletme_alt_kodu.equals(_ayaraktifalttesis))
                    {
                        frg_arac_bulundu_indirme fragmentyeni = new frg_arac_bulundu_indirme();
                        fragmentyeni.fn_senddata(aktif_sevk_isemri);
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_sevkiyat_menu_panel").addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                    else
                    {
                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("HATA")
                                .setContentTextSize(25)
                                .setContentText("Aktif araç işemri bulunamadı.. \r\n Araç kantardan geçiş işlemini tamamlamamış.")
                                .showCancelButton(false)
                                .show();
                    }
                }
            }
            catch (Exception exx)
            {
                exx.printStackTrace();
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Aktif araç işemri bulunamadı.. \r\n Araç kantardan geçiş işlemini tamamlamamış.")
                        .showCancelButton(false)
                        .show();
            }

        }
        okunabilir = true;


    }

    private class fn_btnBekleyenAracListesi implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_bekleyen_arac_listesi fragmentyeni = new frg_bekleyen_arac_listesi();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_bekleyen_arac_listesi").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_sevkiyat_menu_panel fragmentyeni = new frg_sevkiyat_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_sevkiyat_menu_panel").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

}
