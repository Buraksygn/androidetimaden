package com.etimaden.UretimIslemleri;

import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.DEPOTag;
import com.etimaden.persosclass.etiket_no;
import com.etimaden.persosclass.uretim_etiket;
import com.etimaden.request.request_etiket_kontrol;
import com.etimaden.request.request_sec_etiket_no;
import com.etimaden.request.request_yari_otomatik_paket_kontrol_et;
import com.etimaden.request.requestsec_etiket_uretim;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.etimaden.cSabitDegerler._ipAdresi3G;
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

    Persos persos;

    Button _btnEtiketsizUretim;
    Button _btnIptal;
    Button _btncikis;

    TextView _txtParcaBir;
    TextView _txtAra;
    TextView _txtParcaIki;

    uretim_etiket aktif_Palet = null;


    List<String> paket_listesi = new ArrayList<>();

    DEPOTag depo = null;
    uretim_etiket urun = null;
    DEPOTag silo = null;


    CountDownTimer countDownTimer;

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
        return inflater.inflate(R.layout.frg_paket_uretim_ekrani, container, false);
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

        _btnEtiketsizUretim = (Button) getView().findViewById(R.id.btnEtiketsizUretim);
        _btnEtiketsizUretim.playSoundEffect(0);
        _btnEtiketsizUretim.setOnClickListener(new fn_btnEtiketsizUretim());

        _btncikis = (Button) getView().findViewById(R.id.btncikis);
        _btncikis.playSoundEffect(0);
        _btncikis.setOnClickListener(new fn_btncikis());

        _btnIptal = (Button) getView().findViewById(R.id.btnIptal);
        _btnIptal.playSoundEffect(0);
        _btnIptal.setOnClickListener(new fn_btnIptal());

        _txtParcaBir = (TextView) getView().findViewById(R.id.txtParcaBir);

        _txtAra = (TextView) getView().findViewById(R.id.txtAra);

        _txtParcaIki = (TextView) getView().findViewById(R.id.txtParcaIki);

        countDownTimer = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {

                etiket_islem_baslat(urun.getSerino_rfid());
            }

            @Override
            public void onFinish() {
                // btn_basla_dur.setText("başla");

                //   countDownTimer.start();

            }
        };

        if (silo == null) {
            fn_AltPanelGorunsunmu(false);
        }
        else {
            countDownTimer.cancel(); // cancel
            countDownTimer.start();  // then restart
        }
    }

    private void fn_AltPanelGorunsunmu(boolean _bGoster) {
        if (_bGoster == true) {
            _btnEtiketsizUretim.setVisibility(View.VISIBLE);

            _btnIptal.setVisibility(View.VISIBLE);


            _txtParcaBir.setVisibility(View.VISIBLE);
            _txtAra.setVisibility(View.VISIBLE);
            _txtParcaIki.setVisibility(View.VISIBLE);
        } else {
            _btnEtiketsizUretim.setVisibility(View.INVISIBLE);

            _btnIptal.setVisibility(View.INVISIBLE);


            _txtParcaBir.setVisibility(View.INVISIBLE);
            _txtAra.setVisibility(View.INVISIBLE);
            _txtParcaIki.setVisibility(View.INVISIBLE);

        }
    }

    private void fn_AyarlariYukle() {


        _ayarbaglantituru = _myIslem.fn_baglanti_turu();
        _ayarsunucuip = _myIslem.fn_sunucu_ip();
        _ayaraktifkullanici = _myIslem.fn_aktif_kullanici();
        _ayaraktifdepo = _myIslem.fn_aktif_depo();
        _ayaraktifalttesis = _myIslem.fn_aktif_alt_tesis();
        _ayaraktiftesis = _myIslem.fn_aktif_tesis();
        _ayaraktifsunucu = _myIslem.fn_aktif_sunucu();
        _ayaraktifisletmeeslesme = _myIslem.fn_isletmeeslesme();

        if (_ayarbaglantituru.equals("wifi")) {
            _OnlineUrl = "http://" + _ayarsunucuip + ":" + _zportWifi + "/";
        } else {
            _OnlineUrl = "http://" + _ipAdresi3G + ":" + _zport3G + "/";
        }

        persos = new Persos(_OnlineUrl);




    }


    private void paketli_palet_paket_ekle(String etiket)
    {
        request_etiket_kontrol _Param_01=new request_etiket_kontrol();
        request_etiket_kontrol _Param_02=new request_etiket_kontrol();

        _Param_01.set_zsunucu_ip_adresi(_ayarsunucuip);
        _Param_01.set_zaktif_alt_tesis(_ayaraktifalttesis);
        _Param_01.set_zaktif_tesis(_ayaraktiftesis);
        _Param_01.set_zsurum(_sbtVerisyon);
        _Param_01.set_zkullaniciadi(_zkullaniciadi);
        _Param_01.set_zsifre(_zsifre);
        _Param_01.setAktif_sunucu(_ayaraktifsunucu);
        _Param_01.setAktif_kullanici(_ayaraktifkullanici);
        _Param_01.set_rfid(paket_listesi.get(0));

        _Param_02.set_zsunucu_ip_adresi(_ayarsunucuip);
        _Param_02.set_zaktif_alt_tesis(_ayaraktifalttesis);
        _Param_02.set_zaktif_tesis(_ayaraktiftesis);
        _Param_02.set_zsurum(_sbtVerisyon);
        _Param_02.set_zkullaniciadi(_zkullaniciadi);
        _Param_02.set_zsifre(_zsifre);
        _Param_02.setAktif_sunucu(_ayaraktifsunucu);
        _Param_02.setAktif_kullanici(_ayaraktifkullanici);
        _Param_02.set_rfid(paket_listesi.get(0));
        _Param_02.set_rfid(paket_listesi.get(paket_listesi.size() - 1));


       if (paket_listesi.size() > 0 && (!persos.etiket_kontrol(_Param_01).equals("")|| !persos.etiket_kontrol(_Param_02).equals("")))
        {

            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("HATALI")
                    .setContentText("HTN 174258 Üretim işlemi yapılmış etiket üzerinden bir daha işlem yapamazsınız. ")
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
           request_yari_otomatik_paket_kontrol_et _Param = new request_yari_otomatik_paket_kontrol_et();
           _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
           _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
           _Param.set_zaktif_tesis(_ayaraktiftesis);
           _Param.set_zsurum(_sbtVerisyon);
           _Param.set_zkullaniciadi(_zkullaniciadi);
           _Param.set_zsifre(_zsifre);
           _Param.setAktif_sunucu(_ayaraktifsunucu);
           _Param.setAktif_kullanici(_ayaraktifkullanici);

           _Param.set_serino(etiket.substring(10,24));

           String lot = persos.yari_otomatik_paket_kontrol_et(_Param);

           if (paket_listesi.size() == 0 && !lot.equals(""))
           {
               yariotomatik_paketli_palet_uretim_tamamla(aktif_Palet, lot);
               return;
           }
           else
           {

           }
       }
    }

    private void yariotomatik_paketli_palet_uretim_tamamla(uretim_etiket v_Gelen , String lot)
    {

        final uretim_etiket _urun = v_Gelen;

        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("ONAY")
                .setContentText(_urun.getSerino_kod() + " seri nolu ürünün üretim işlemini tamamlamak istiyor musunuz ?")
                .setCancelText("İptal")
                .setContentTextSize(20)
                .setConfirmText("TAMAM")
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
                {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog)
                    {
                        sweetAlertDialog.dismissWithAnimation();


                        if (_urun.getPaket_tipi().equals("3") || _urun.getPaket_tipi().equals("0"))
                        {

                            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("HATA")
                                    .setContentText("Üretim Yapılamadı. Paket tipi uyumsuzluğu ")
                                    .setContentTextSize(20)
                                    .setConfirmText("TAMAM")
                                    .showCancelButton(false)
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
                                    {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog_01)
                                        {
                                            sDialog_01.dismissWithAnimation();

                                            return;
                                        }
                                    })
                                    .show();

                                    return;
                        }
                        if (_urun.getIsemri_tipialt().equals("351"))
                        {
                            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("HATA")
                                    .setContentText("Üretim Yapılamadı. İşemri tipi uyumsuzluğu ")
                                    .setContentTextSize(20)
                                    .setConfirmText("TAMAM")
                                    .showCancelButton(false)
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
                                    {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog_01)
                                        {
                                            sDialog_01.dismissWithAnimation();

                                            return;
                                        }
                                    })
                                    .show();

                            return;
                        }
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();

                        return;
                    }
                })
                .show();


    }

    private class fn_btnEtiketsizUretim implements View.OnClickListener {
        @Override
        public void onClick(View v)
        {
            int hAdet = Integer.parseInt(aktif_Palet.getPalet_dizim());

            request_sec_etiket_no _Param = new request_sec_etiket_no();

            _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
            _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
            _Param.set_zaktif_tesis(_ayaraktiftesis);
            _Param.set_zsurum(_sbtVerisyon);
            _Param.set_zkullaniciadi(_zkullaniciadi);
            _Param.set_zsifre(_zsifre);
            _Param.setAktif_sunucu(_ayaraktifsunucu);
            _Param.setAktif_kullanici(_ayaraktifkullanici);

            _Param.set_palet_dizim(hAdet+"");


            etiket_no eno = persos.sec_etiket_no(_Param);

            if(eno==null)
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentText("HTN 173216 Sistemsel Hata ")
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

                int sirano_1 =Integer.parseInt(eno.getEti_sirano()) - hAdet + 1;
                int sirano_2 = Integer.parseInt(eno.getEti_sirano());

                paketli_palet_paket_ekle(fn_set_etiketno(eno, sirano_1));
            }
        }
    }


    private String fn_set_etiketno(etiket_no eno, int sirano) {
        String a = "";
        try {
            a = "7377670000";
            String yil = eno.getEti_yil().substring(eno.getEti_yil().length() - 2);
            String ay = eno.getEti_ay();
            if (eno.getEti_ay().length() != 2) {
                ay = "0" + eno.getEti_ay();
            }
            String gun = eno.getEti_gun();
            if (eno.getEti_gun().length() != 2) {
                gun = "0" + eno.getEti_gun();
            }

            String b = yil + ay + gun + eno.getEti_isletme();

            while ((b.length() + (sirano + "").length()) != 14) {
                b = b + "0";
            }

            a = a + b + (sirano + "");

        } catch (Exception ex) {
        }
        return a;
    }

    private class fn_btnIptal implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            frg_uretim_menu_panel fragmentyeni = new frg_uretim_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_uretim_menu_panel").addToBackStack(null);
            fragmentTransaction.commit();
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

    public void fn_senddata(DEPOTag v_depo, DEPOTag v_silo, uretim_etiket v_aktif_urun) {
        urun = v_aktif_urun;
        depo = v_depo;
        silo = v_silo;
    }

    public void fn_barkodOkundu(String barkod) {

        if (barkod.length() < 24) {
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

                        }
                    })
                    .show();

        } else {
            barkod = barkod.substring(barkod.length() - 24);

            etiket_islem_baslat(barkod);
        }
    }

    private void etiket_islem_baslat(String str) {

        try {

            if (islemDurumu == 0)
            {
                requestsec_etiket_uretim _Param = new requestsec_etiket_uretim();

                _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
                _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
                _Param.set_zaktif_tesis(_ayaraktiftesis);
                _Param.set_zsurum(_sbtVerisyon);
                _Param.set_zkullaniciadi(_zkullaniciadi);
                _Param.set_zsifre(_zsifre);
                _Param.setaktif_sunucu(_ayaraktifsunucu);
                _Param.setaktif_kullanici(_ayaraktifkullanici);

                _Param.set_etiket(str);

                uretim_etiket etiket = persos.sec_etiket_uretim(_Param);

                if (etiket == null)
                {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("UYARI")
                            .setContentText("Lütfen uygun bir ürün etiketi okutunuz. Ürün kaydı bulunamadı..")
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
                }

                else if (!etiket.getEtiket_durumu().equals("0"))
                {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Üretilmiş Etiket")
                            .setContentText("Üretim için uygun olmayan etiket<br/>Etiketin üretim işlemi tamamlanmıştır.")
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
                }
                else if (!etiket.getEtiket_uretim_uygunlugu().equals("1")) {

                    String _Yazi = "";
                    _Yazi += "ÜRETİM ONAYI ALINAMADI.";
                    _Yazi += "<br/><b>1)</b> İLK 5 DAKİKA İÇİNDE ÜRETİM İŞLEMİNİ GERÇEKLEŞTİREMEZSİNİZ.";
                    _Yazi += "<br/><b>2)</b> 3 GÜN İÇİNDE ÜRETİM KAYDI GERÇEKLEŞMEYEN ETİKET İÇİN ÜRETİM KAYDI OLUŞTURULAMAZ.";
                    _Yazi += "<br/><b>3)</b> SİSTEM İÇİNDE AYNI İŞ EMRİNE AİT EN FAZLA 2 ADET ÜRETİMİ TAMAMLANMAMIŞ LOT BULUNABİLİR.";

                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("ONAY ALINAMADI")
                            .setContentText(_Yazi)
                            .setContentTextSize(16)
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

                }
                else if (depo == null || silo == null)
                {
                    frg_depo_secimi fragmentyeni = new frg_depo_secimi();
                    fragmentyeni.fn_senddata(etiket);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_depo_secimi").addToBackStack(null);
                    fragmentTransaction.commit();
                }
                else if ((!etiket.getDepo_silo_secimi().equals("") && (!etiket.getDepo_silo_secimi().contains(depo.getDepo_id()) || !etiket.getDepo_silo_secimi().contains(silo.getDepo_id()))))
                {
                    frg_depo_secimi fragmentyeni = new frg_depo_secimi();
                    fragmentyeni.fn_senddata(etiket);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_depo_secimi").addToBackStack(null);
                    fragmentTransaction.commit();
                }
                else if (etiket.getPaket_tipi().equals("1"))
                {
                    if (etiket.getIsemri_tipialt().equals("150"))
                    {
                        paketli_palet_basla(etiket);
                    }
                }
            }


        } catch (Exception ex)
        {
            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("HATA")
                    .setContentText("Sistemsel hata = " + ex.toString())
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

        }
    }

    private void manipulasyon_paketli_uret(uretim_etiket etiket){


    }

    private void paketli_palet_basla(uretim_etiket etiket)
    {
        islemDurumu = 1;

        aktif_Palet = etiket;

        paket_listesi.clear();

        _txtParcaBir.setText(paket_listesi.size()+"");
        _txtParcaIki.setText(etiket.getPalet_dizim()+"");
        fn_AltPanelGorunsunmu(true);
    }
}
