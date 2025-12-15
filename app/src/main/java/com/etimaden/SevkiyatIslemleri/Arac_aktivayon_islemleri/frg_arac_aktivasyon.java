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
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.etimaden.GirisSayfasi;
import com.etimaden.SevkiyatIslemleri.frg_aktif_arac_secimi;
import com.etimaden.SevkiyatIslemleri.frg_aktif_isemri_indirme;
import com.etimaden.SevkiyatIslemleri.frg_aktif_isemri_yukleme;
import com.etimaden.SevkiyatIslemleri.frg_arac_bulundu_indirme;
import com.etimaden.SevkiyatIslemleri.frg_sevkiyat_menu_panel;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cResponseResult.Sevkiyat_isemri;
import com.etimaden.cResponseResult.ViewsecAktifSevkIsemriListesi;
import com.etimaden.genel.Genel;
import com.etimaden.genel.SweetAlertDialogG;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.Gemi;
import com.etimaden.persosclass.Gemi_Yukleme;
import com.etimaden.persosclass.Vagon_hareket;
import com.etimaden.request.request_sevkiyat_isemri;
import com.etimaden.request.request_string;
import com.etimaden.ugr_demo.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


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
    Button _btnOkuma;


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

        ((GirisSayfasi) getActivity()).fn_ModBarkod();


        _btnBekleyenAracListesi = (Button)getView().findViewById(R.id.btnBekleyenAracListesi);
        _btnBekleyenAracListesi.playSoundEffect(SoundEffectConstants.CLICK);
        _btnBekleyenAracListesi.setOnClickListener(new fn_btnBekleyenAracListesi());

        _btngeri = (Button)getView().findViewById(R.id.btngeri);
        _btngeri.playSoundEffect(SoundEffectConstants.CLICK);
        _btngeri.setOnClickListener(new fn_Geri());

        _btnOkuma = (Button)getView().findViewById(R.id.btnOkuma);
        _btnOkuma.playSoundEffect(SoundEffectConstants.CLICK);
        _btnOkuma.setOnClickListener(new frg_arac_aktivasyon.fn_okumaDegistir());
        _btnOkuma.setText("KAREKOD");

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
                ArrayList<Sevkiyat_isemri> sevk_isemri_listesi = new ArrayList<>();
                if(result!=null) {
                    sevk_isemri_listesi = new ArrayList<>(result);
                }
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
                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
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
                ArrayList<Sevkiyat_isemri> sevk_isemri_listesi=new ArrayList<>();
                if(result!=null) {
                    sevk_isemri_listesi = new ArrayList<>(result);
                }
                Genel.dismissProgressDialog();

                Sevkiyat_isemri aktif_sevk_isemri=new Sevkiyat_isemri();
                for(Sevkiyat_isemri w : sevk_isemri_listesi){
                    if(w.arac_rfid.equals(rfid)){
                        aktif_sevk_isemri=w;
                        break;
                    }
                }
                //String paletSayisi = aktif_sevk_isemri.ish_palet_sayisi; //OZGUR EKLEDİ
                String paletSayisi = aktif_sevk_isemri.yapilan_adet; //OZGUR EKLEDİ




                if (_ayaraktiftesis.equals("2003") && (aktif_sevk_isemri.aciklama.equals("17") || aktif_sevk_isemri.aciklama.equals("1")) && !aktif_sevk_isemri.vardiya.equals(""))
                {
                    final Sevkiyat_isemri _aktif_sevk_isemri=aktif_sevk_isemri;
                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                            //.setTitleText("SEVKİYAT YÖNLENDİRMESİ")
                            .setTitleText("SEVKİYAT YÖNLENDİRMESİ\n\nPALET SAYISI : " + paletSayisi) //OZGUR EKLEDİ
                            .setContentText("ARAÇ İÇİN GEMİ YÜKLEMESİ SEÇİLMİŞ. GEMİ YÜKLEMESİ TAMAMLANDIYSA \"EVET\" \n DEPOYA ALMA İÇİN \"HAYIR\" \n SEÇENEĞİNİ SEÇİNİZ!! ")
                            .setContentTextSize(20)
                            .setConfirmText("EVET")
                            .setCancelText("HAYIR")
                            .showCancelButton(true)
                            .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialogG sDialog) {
                                    sDialog.dismissWithAnimation();

                                    request_string _Param= new request_string();
                                    _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
                                    _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
                                    _Param.set_zaktif_tesis(_ayaraktiftesis);
                                    _Param.set_zsurum(_sbtVerisyon);
                                    _Param.set_zkullaniciadi(_zkullaniciadi);
                                    _Param.set_zsifre(_zsifre);
                                    _Param.setAktif_sunucu(_ayaraktifsunucu);
                                    _Param.setAktif_kullanici(_ayaraktifkullanici);
                                    _Param.set_value(_aktif_sevk_isemri.kod_sap);

                                    Gemi_Yukleme gemi = persos.fn_gemi_yukleme_bul(_Param); //Özgür


                                    int yapilanAdet = 0;
                                    int miktarTorba = 0;

                                    try {
                                        yapilanAdet = Integer.parseInt(_aktif_sevk_isemri.yapilan_adet);
                                    } catch (Exception e) {
                                        yapilanAdet = 0;
                                    }

                                    try {
                                        miktarTorba = Integer.parseInt(_aktif_sevk_isemri.miktar_torba);
                                    } catch (Exception e) {
                                        miktarTorba = 0;
                                    }

                                    int aracYukInt = yapilanAdet * miktarTorba;
                                    String arac_yuk = String.valueOf(aracYukInt);
                                    String isemrikod = _aktif_sevk_isemri.kod_sap;
                                    String isemrimiktar= gemi.toplam_miktar;
                                    String yuklenenmiktar = gemi.yuklenen_miktar;
                                    int kalanmiktar = Integer.parseInt(gemi.kalan_miktar);
                                    //int kalanmiktar = 8000;


                                    //---------------------------------------------------------------------------
                                    StringBuilder mesaj = new StringBuilder();
                                    mesaj.append("IS EMRI : ").append(isemrikod)
                                            .append("\rIS EMRI MIKTARI : ").append(isemrimiktar)
                                            .append("\rYUKLENEN MIKTAR : ").append(yuklenenmiktar)
                                            .append("\rKALAN MIKTAR : ").append(kalanmiktar)
                                            .append("\rARAÇ YÜK : ").append(arac_yuk);
                                    if (aracYukInt > kalanmiktar) {
                                        mesaj.append("\r\n⚠️ ARACI **INDIRMEYINIZ**; IS EMRI MIKTARI AŞILIYOR!");
                                    }
                                    //---------------------------------------------------------------------------

                                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE) //merve ekledi
                                            .setTitleText("SEVKİYAT YÖNLENDİRMESİ")
                                            //.setContentText("GEMİYE YÜKLEMESİ TAMAMLANDI. \r\n İŞLEME DEVAM ETMEK İSTİYOR MUSUNUZ?")
                                            .setContentText(mesaj.toString())
                                            .setContentTextSize(20)
                                            .setConfirmText("EVET")
                                            .setCancelText("HAYIR")
                                            .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialogG sweetAlertDialog) {
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
                                                        new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                                                                .setTitleText("İşlem Onayı")
                                                                .setContentText("İşlem başarı ile tamamlanmıştır. Aracı kantara gönderiniz.")
                                                                .setContentTextSize(20)
                                                                .setConfirmText("TAMAM")
                                                                .showCancelButton(false)
                                                                .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                                                    @Override
                                                                    public void onClick(SweetAlertDialogG sDialog) {
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
                                                        new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                                                                .setTitleText("İşlem Başarısız")
                                                                .setContentTextSize(25)
                                                                .setContentText("Kayıt yapılamadı. \r\n Veritabanı hatası")
                                                                .showCancelButton(false)
                                                                .show();
                                                    }
                                                }
                                            })
                                            .setCancelClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialogG sweetAlertDialog) {
                                                    sweetAlertDialog.dismissWithAnimation();
                                                }
                                            }).show();
                                }
                            })
                            .setCancelClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialogG sDialog) {
                                    sDialog.dismissWithAnimation();

                                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE) // merve ekledi
                                            .setTitleText("SEVKİYAT YÖNLENDİRMESİ")
                                            .setContentText("DEPOYA ALMA İŞLEMİ YAPILACAKTIR. \r\n ONAYLIYOR MUSUNUZ?")
                                            .setContentTextSize(20)
                                            .setConfirmText("EVET")
                                            .setCancelText("HAYIR")
                                            .showCancelButton(true)
                                            .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialogG sweetAlertDialog) {
                                                    sweetAlertDialog.dismissWithAnimation();

                                                    if (_aktif_sevk_isemri.hedef_isletme_alt_kodu.equals(_ayaraktifalttesis))
                                                    {
                                                        frg_arac_bulundu_indirme fragmentyeni = new frg_arac_bulundu_indirme();
                                                        fragmentyeni.fn_senddata(_aktif_sevk_isemri);
                                                        FragmentManager fragmentManager = getFragmentManager();
                                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                        fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_arac_bulundu_indirme").addToBackStack(null);
                                                        fragmentTransaction.commit();
                                                    }
                                                    else
                                                    {
                                                        new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                                                                .setTitleText("HATA")
                                                                .setContentTextSize(25)
                                                                .setContentText("Aktif araç işemri bulunamadı.. \r\n Araç kantardan geçiş işlemini tamamlamamış.")
                                                                .showCancelButton(false)
                                                                .show();
                                                    }
                                                }
                                            }).setCancelClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialogG sweetAlertDialog) {
                                                    sweetAlertDialog.dismissWithAnimation();
                                                }
                                            }).show();
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
                        fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_arac_bulundu_indirme").addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                    else
                    {
                        new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
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
                Genel.printStackTrace(ex,getContext());
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Aktif araç işemri bulunamadı.. \r\n Araç kantardan geçiş işlemini tamamlamamış.")
                        .showCancelButton(false)
                        .show();
            }

        }
        okunabilir = true;


    }

    public void fn_BarkodOkutuldu(String barkod) {

        try
        {
            barkod = barkod.substring(barkod.length() - 24);


            rfidOkundu(barkod);

        }
        catch (Exception ex){
            Genel.printStackTrace(ex,getContext());
        }
        //Thread.Sleep(1000);
        //isReadable = true;


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

    private class fn_okumaDegistir implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Genel.showProgressDialog(getContext());
            if(_btnOkuma.getText().toString().equals("KAREKOD")){
                ((GirisSayfasi) getActivity()).fn_ModRFID();
                _btnOkuma.setText("RFID");
            }else{
                ((GirisSayfasi) getActivity()).fn_ModBarkod();
                _btnOkuma.setText("KAREKOD");
            }
            Genel.dismissProgressDialog();
        }
    }

}
