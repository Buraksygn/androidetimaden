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
import com.etimaden.genel.Genel;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.DEPOTag;
import com.etimaden.persosclass.Urun_tag;
import com.etimaden.persosclass.etiket_no;
import com.etimaden.persosclass.uretim_etiket;
import com.etimaden.request.request_etiket_kontrol;
import com.etimaden.request.request_get_lot_toplami;
import com.etimaden.request.request_paketliUret;
import com.etimaden.request.request_paketliUretKontrol;
import com.etimaden.request.request_paketliUret_otomatik;
import com.etimaden.request.request_secEtiket;
import com.etimaden.request.request_sec_etiket_no;
import com.etimaden.request.request_string;
import com.etimaden.request.request_uretim_etiket;
import com.etimaden.request.request_yari_otomatik_paket_kontrol_et;
import com.etimaden.request.requestsec_etiket_uretim;
import com.etimaden.response.frg_paket_uretim_ekrani.View_bool_response;
import com.etimaden.response.frg_paket_uretim_ekrani.View_paketliUret_otomatik;
import com.etimaden.response.frg_paket_uretim_ekrani.View_string_response;
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
    TextView _txtLot;
    TextView _txtMiktar;


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

        _txtLot = (TextView) getView().findViewById(R.id.txtLot);
        _txtMiktar = (TextView) getView().findViewById(R.id.txtMiktar);


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

    private void update_lot_panel(uretim_etiket tag) {
        String v_ser_lotno=tag.getSer_lotno();

        request_get_lot_toplami v_param=new request_get_lot_toplami();
        v_param.set_zsunucu_ip_adresi(_ayarsunucuip);
        v_param.set_zaktif_alt_tesis(_ayaraktifalttesis);
        v_param.set_zaktif_tesis(_ayaraktiftesis);
        v_param.set_zsurum(_sbtVerisyon);
        v_param.set_zkullaniciadi(_zkullaniciadi);
        v_param.set_zsifre(_zsifre);
        v_param.setAktif_sunucu(_ayaraktifsunucu);
        v_param.setAktif_kullanici(_ayaraktifkullanici);

        v_param.set_lotno(v_ser_lotno);

        View_string_response _yanit;

        _yanit = persos.fn_get_lot_toplami(v_param);

        if(_yanit._zSonuc.equals("0"))
        {
            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("HATA")
                    .setContentText(_yanit.get_zHataAciklama())
                    .setContentTextSize(20)
                    .setConfirmText("TAMAM")
                    .showCancelButton(false)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
                    {
                        @Override
                        public void onClick(SweetAlertDialog sDialog)
                        {
                            sDialog.dismissWithAnimation();

                            return;
                        }
                    })
                    .show();

            return;
        }
        else
        {
            _txtMiktar.setText( "Miktar : " + _yanit.get_result());
            _txtLot.setText("Lot : " + v_ser_lotno);
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
            _txtParcaBir.setText("");

            _txtAra.setVisibility(View.INVISIBLE);

            _txtParcaIki.setVisibility(View.INVISIBLE);
            _txtParcaIki.setText("");
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

    private void paketli_palet_paket_ekle(String etiket) {

        request_string _Param_01 = new request_string();
        request_string _Param_02 = new request_string();

        _Param_01.set_zsunucu_ip_adresi(_ayarsunucuip);
        _Param_01.set_zaktif_alt_tesis(_ayaraktifalttesis);
        _Param_01.set_zaktif_tesis(_ayaraktiftesis);
        _Param_01.set_zsurum(_sbtVerisyon);
        _Param_01.set_zkullaniciadi(_zkullaniciadi);
        _Param_01.set_zsifre(_zsifre);
        _Param_01.setAktif_sunucu(_ayaraktifsunucu);
        _Param_01.setAktif_kullanici(_ayaraktifkullanici);

       if(paket_listesi.size()>0)
       {
        _Param_01.set_value(paket_listesi.get(0));
       }

        _Param_02.set_zsunucu_ip_adresi(_ayarsunucuip);
        _Param_02.set_zaktif_alt_tesis(_ayaraktifalttesis);
        _Param_02.set_zaktif_tesis(_ayaraktiftesis);
        _Param_02.set_zsurum(_sbtVerisyon);
        _Param_02.set_zkullaniciadi(_zkullaniciadi);
        _Param_02.set_zsifre(_zsifre);
        _Param_02.setAktif_sunucu(_ayaraktifsunucu);
        _Param_02.setAktif_kullanici(_ayaraktifkullanici);

        if(paket_listesi.size()>0)
        {
            _Param_02.set_value(paket_listesi.get(paket_listesi.size() - 1));
        }

      //  _Param_02.set_rfid(paket_listesi.get(0));

        Genel.showProgressDialog(getContext());
        String p1kontrol = persos.etiket_kontrol(_Param_01);
        String p2kontrol = persos.etiket_kontrol(_Param_02);
        Genel.dismissProgressDialog();

        p1kontrol=p1kontrol==null?"":p1kontrol;

        p2kontrol=p2kontrol==null?"":p2kontrol;




        if (paket_listesi.size() > 0 && (!p1kontrol.equals("") || !p2kontrol.equals("") )  ) {

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
        } else {
            String lot="";
            request_string _Param = new request_string();
            _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
            _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
            _Param.set_zaktif_tesis(_ayaraktiftesis);
            _Param.set_zsurum(_sbtVerisyon);
            _Param.set_zkullaniciadi(_zkullaniciadi);
            _Param.set_zsifre(_zsifre);
            _Param.setAktif_sunucu(_ayaraktifsunucu);
            _Param.setAktif_kullanici(_ayaraktifkullanici);

            _Param.set_value(etiket.substring(10, 24));

            Genel.showProgressDialog(getContext());
            lot = persos.yari_otomatik_paket_kontrol_et(_Param);
            Genel.dismissProgressDialog();

            if(lot==null){
                lot="";
            }
            int _Dur = 0;


            if (paket_listesi.size() == 0 && !lot.equals("")) {
                yariotomatik_paketli_palet_uretim_tamamla(aktif_Palet, lot);
                return;
            } else {
                paket_listesi.remove(aktif_Palet.getSerino_rfid());

                int tadet = paket_listesi.size();
                int hAdet = Integer.parseInt(aktif_Palet.getPalet_dizim());

                if (hAdet == tadet) {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Hatalı İşlem")
                            .setContentText("Paket ekleme işlemi başarısız! <br/> Palet kapatma limitine ulaştınız.")
                            .setContentTextSize(20)
                            .setConfirmText("TAMAM")
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

                if (!etiket.substring(6, 7).equals("0")) {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Hatalı İşlem")
                            .setContentText("Paket ekleme işlemi başarısız! <br/> Lütfen uygun bir paket etiketi okutunuz.<br/>Hatalı işlem!! Etiket paket etiketi değil!!")
                            .setContentTextSize(20)
                            .setConfirmText("TAMAM")
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

                if (paket_listesi.indexOf(etiket) >= 0) {
                    paket_listesi.remove(etiket);
                } else {
                    if (paket_listesi.size() == 1) {
                        int aktif_paket_sayisi = Integer.parseInt(etiket.substring(16, 24)) - Integer.parseInt(paket_listesi.get(0).substring(16, 24));

                        if (Integer.parseInt(aktif_Palet.getPalet_dizim()) == (aktif_paket_sayisi + 1)) {
                            String str_substring = etiket.substring(0, 16);

                            for (int i = 1; i < aktif_paket_sayisi + 1; i++) {

                                String str_endstring = (Integer.parseInt(paket_listesi.get(0).substring(16, 24)) + i) + "";

                                while (true) {
                                    if (str_endstring.length() >= 8) {
                                        break;
                                    } else {
                                        str_endstring = "0" + str_endstring;
                                    }
                                }

                                etiket = str_substring + str_endstring;

                                paket_listesi.add(etiket);

                            }

                            // MessageBox.Show(paket_listesi.ElementAt(0) + Environment.NewLine + paket_listesi.ElementAt(paket_listesi.Count-1));
                        } else {
                            paket_listesi.add(etiket);
                        }
                    } else {
                        paket_listesi.add(etiket);
                    }
                }


                try {
                    _txtParcaBir.setText(paket_listesi.size() + "");

                    _txtParcaIki.setText(aktif_Palet.getPalet_dizim() + "");

                    tadet = paket_listesi.size();
                    hAdet = Integer.parseInt(aktif_Palet.getPalet_dizim());
                    if (hAdet == tadet)
                    {
                        islemDurumu = 2;

                        return;
                    }

                } catch (Exception ex)
                {
                    ex.printStackTrace();

                }
            }
        }
    }

    private void yariotomatik_paketli_palet_uretim_tamamla(final uretim_etiket v_Gelen , String lot) {

        final uretim_etiket _urun = v_Gelen;
        final String _LotNo = lot;

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

                        request_paketliUret_otomatik v_Param=new request_paketliUret_otomatik();

                        v_Param._lotno = _LotNo;
                        v_Param._zaktif_alt_tesis = _ayaraktifalttesis;
                        v_Param._zaktif_tesis = _ayaraktiftesis;
                        v_Param._zkullaniciadi=_zkullaniciadi;
                        v_Param._zsifre=_zsifre;
                        v_Param._zsunucu_ip_adresi=_ayarsunucuip;
                        v_Param._zsurum = _sbtVerisyon;
                        v_Param.aktif_kullanici=_ayaraktifkullanici;
                        v_Param.aktif_sunucu=_ayaraktifsunucu;

                        Genel.showProgressDialog(getContext());
                        View_bool_response _Yanit=persos.paketliUret_otomatik(v_Param);
                        Genel.dismissProgressDialog();

                        if(_Yanit._zSonuc.equals("0"))
                        {

                            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("HATA")
                                    .setContentText("HTN 12072023092930 "+_Yanit.get_zHataAciklama())
                                    .setContentTextSize(16)
                                    .setConfirmText("TAMAM")
                                    .showCancelButton(false)
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
                                    {
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
                            if( _Yanit._result==null || _Yanit._result==false)
                            {
                                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("HATA")
                                        .setContentText("Üretim Yapılamadı.Açık palet etiketini yeniden okutunuz.")
                                        .setContentTextSize(20)
                                        .setConfirmText("TAMAM")
                                        .showCancelButton(false)
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
                                        {
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
                                update_lot_panel(aktif_Palet);
                                fn_AltPanelGorunsunmu(false);
                                paket_listesi.clear();
                                aktif_Palet = null;

                                return;
                            }
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
        public void onClick(View v) {
            int hAdet = Integer.parseInt(aktif_Palet.getPalet_dizim());

            request_string _Param = new request_string();

            _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
            _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
            _Param.set_zaktif_tesis(_ayaraktiftesis);
            _Param.set_zsurum(_sbtVerisyon);
            _Param.set_zkullaniciadi(_zkullaniciadi);
            _Param.set_zsifre(_zsifre);
            _Param.setAktif_sunucu(_ayaraktifsunucu);
            _Param.setAktif_kullanici(_ayaraktifkullanici);

            _Param.set_value(hAdet+"");

            Genel.showProgressDialog(getContext());
            etiket_no eno = persos.sec_etiket_no(_Param);
            Genel.dismissProgressDialog();

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

                paketli_palet_paket_ekle(fn_set_etiketno(eno, sirano_2));
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

    private void paketli_palet_uretim_tamamla(final uretim_etiket v_etiket) {

        uretim_etiket etiket =v_etiket;

        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("ONAY")
                .setContentText(etiket.getSerino_kod()+ " seri nolu ürünün üretim işlemini tamamlamak istiyor musunuz ?")
                .setCancelText("İptal")
                .setContentTextSize(20)
                .setConfirmText("TAMAM")
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
                {
                    @Override
                    public void onClick(SweetAlertDialog sDialog)
                    {
                        sDialog.dismissWithAnimation();


                        request_string _Param_01 = new request_string();
                        request_string _Param_02 = new request_string();

                        _Param_01.set_zsunucu_ip_adresi(_ayarsunucuip);
                        _Param_01.set_zaktif_alt_tesis(_ayaraktifalttesis);
                        _Param_01.set_zaktif_tesis(_ayaraktiftesis);
                        _Param_01.set_zsurum(_sbtVerisyon);
                        _Param_01.set_zkullaniciadi(_zkullaniciadi);
                        _Param_01.set_zsifre(_zsifre);
                        _Param_01.setAktif_sunucu(_ayaraktifsunucu);
                        _Param_01.setAktif_kullanici(_ayaraktifkullanici);

                        if(paket_listesi.size()>0)
                        {
                            _Param_01.set_value(paket_listesi.get(0));
                        }

                        _Param_02.set_zsunucu_ip_adresi(_ayarsunucuip);
                        _Param_02.set_zaktif_alt_tesis(_ayaraktifalttesis);
                        _Param_02.set_zaktif_tesis(_ayaraktiftesis);
                        _Param_02.set_zsurum(_sbtVerisyon);
                        _Param_02.set_zkullaniciadi(_zkullaniciadi);
                        _Param_02.set_zsifre(_zsifre);
                        _Param_02.setAktif_sunucu(_ayaraktifsunucu);
                        _Param_02.setAktif_kullanici(_ayaraktifkullanici);

                        if(paket_listesi.size()>0)
                        {
                            _Param_02.set_value(paket_listesi.get(paket_listesi.size() - 1));
                        }

                        Genel.showProgressDialog(getContext());
                        String p1kontrol = persos.etiket_kontrol(_Param_01);
                        String p2kontrol = persos.etiket_kontrol(_Param_02);
                        Genel.dismissProgressDialog();

                        p1kontrol=p1kontrol==null?"":p1kontrol;
                        p2kontrol=p2kontrol==null?"":p2kontrol;




                        //if (paket_listesi.size() > 0 && (!p1kontrol.equals("") || !p2kontrol.equals("") )  ) {

                            if (paket_listesi.size() > 0 && (!p1kontrol.equals("") || !p2kontrol.equals("") )  )
                        {

                            new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("HATALI")
                                    .setContentText("Üretim işlemi yapılmış etiket üzerinden bir daha işlem yapamazsınız.")
                                    .setContentTextSize(20)
                                    .setConfirmText("TAMAM")
                                    .showCancelButton(false)
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
                                    {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog)
                                        {
                                            sDialog.dismissWithAnimation();

                                            return;
                                        }
                                    })
                                    .show();
                            return;
                        }

                        int tadet = paket_listesi.size();

                        int hAdet = Integer.parseInt(aktif_Palet.getPalet_dizim());

                        Urun_tag tt = null;

                        if (hAdet == tadet)
                        {

                            aktif_Palet.setDepo(depo.getDepo_id());

                            aktif_Palet.setSilo(silo.getDepo_id());


                            request_secEtiket v_giden=new request_secEtiket();

                                v_giden.set_rfid(aktif_Palet.getSerino_rfid());
                                v_giden.set_zaktif_alt_tesis(_ayaraktifalttesis);
                                v_giden.set_zaktif_tesis(_ayaraktiftesis);
                                v_giden.set_zkullaniciadi(_zkullaniciadi);
                                v_giden.set_zsifre(_zsifre);
                                v_giden.set_zsunucu_ip_adresi(_ayarsunucuip);
                                v_giden.set_zsurum(_sbtVerisyon);
                                v_giden.setAktif_kullanici(_ayaraktifkullanici);
                                v_giden.setAktif_sunucu(_ayaraktifsunucu);
                                v_giden.set_rfid(aktif_Palet.getSerino_rfid());

                            //Urun_tag v_gelen ;
                            Genel.showProgressDialog(getContext());
                            Urun_tag v_gelen = persos.fn_secEtiket(v_giden);
                            Genel.dismissProgressDialog();

                            if(v_gelen==null)
                            {

                                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Üretim Yapılamadı")
                                        .setContentText("Açık palet etiketini yeniden okutunuz.")
                                        .setContentTextSize(20)
                                        .setConfirmText("TAMAM")
                                        .showCancelButton(false)
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
                                        {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog)
                                            {
                                                sDialog.dismissWithAnimation();

                                                return;
                                            }
                                        })
                                        .show();

                                return ;
                            }


                            request_paketliUretKontrol v_param = new request_paketliUretKontrol();
                            v_param.set_urun(aktif_Palet);
                            v_param.set_zaktif_alt_tesis(_ayaraktifalttesis);
                            v_param.set_zaktif_tesis(_ayaraktiftesis);
                            v_param.set_zkullaniciadi(_zkullaniciadi);
                            v_param.set_zsifre(_zsifre);
                            v_param.set_zsunucu_ip_adresi(_ayarsunucuip);
                            v_param.set_zsurum(_sbtVerisyon);
                            v_param.setAktif_kullanici(_ayaraktifkullanici);
                            v_param.setAktif_sunucu(_ayaraktifsunucu);


                            Boolean uretim_onayı=persos.fn_paketliUretKontrol(v_param);

                            if (uretim_onayı)
                            {
                                islemDurumu = 0;

                                List<String> pl = new ArrayList();

                                for (int i = 0; i < paket_listesi.size(); i++)
                                {
                                    pl.add(paket_listesi.get(i));
                                }

                                update_lot_panel(aktif_Palet);
                                request_paketliUret v_param2 = new request_paketliUret();
                                v_param2.set_urun(v_gelen);
                                v_param2.set_zaktif_alt_tesis(_ayaraktifalttesis);
                                v_param2.set_zaktif_tesis(_ayaraktiftesis);
                                v_param2.set_zkullaniciadi(_zkullaniciadi);
                                v_param2.set_zsifre(_zsifre);
                                v_param2.set_zsunucu_ip_adresi(_ayarsunucuip);
                                v_param2.set_zsurum(_sbtVerisyon);
                                v_param2.setAktif_kullanici(_ayaraktifkullanici);
                                v_param2.setAktif_sunucu(_ayaraktifsunucu);
                                v_param2.setPaketList(pl);

                                Boolean b_yanit=persos.fn_paketliUret(v_param2);
                                fn_AltPanelGorunsunmu(false);

                                aktif_Palet = null;

                                paket_listesi.clear();
                                if(b_yanit==true)
                                {
                                    new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("TAMAM")
                                            .setContentText("İşlem başarı tamamlandı")
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
                                return;
                            }
                            else
                            {

                                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("HATA")
                                        .setContentText("HTN Sistemsel bir hata oluştu")
                                        .setContentTextSize(20)
                                        .setConfirmText("TAMAM")
                                        .showCancelButton(false)
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
                                        {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog)
                                            {
                                                sDialog.dismissWithAnimation();

                                                return;
                                            }
                                        })
                                        .show();
                            }

                        } else
                        {

                            new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Hatalı Palet Kapatma")
                                    .setContentText("Palet kapatma limitine ulaşılmadı.\r\n Palet kapatma sayısına ulaştıktan sonra tekrar deneyiniz")
                                    .setContentTextSize(20)
                                    .setConfirmText("TAMAM")
                                    .showCancelButton(false)
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
                                    {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog)
                                        {
                                            sDialog.dismissWithAnimation();

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

    private void etiket_islem_baslat(String str) {

        try {
            int _Dur = 1;

            if (islemDurumu == 0)
            {
                request_string _Param = new request_string();

                _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
                _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
                _Param.set_zaktif_tesis(_ayaraktiftesis);
                _Param.set_zsurum(_sbtVerisyon);
                _Param.set_zkullaniciadi(_zkullaniciadi);
                _Param.set_zsifre(_zsifre);
                _Param.setAktif_sunucu(_ayaraktifsunucu);
                _Param.setAktif_kullanici(_ayaraktifkullanici);

                _Param.set_value(str);

                Genel.showProgressDialog(getContext());
                uretim_etiket etiket = persos.sec_etiket_uretim(_Param);
                Genel.dismissProgressDialog();

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
                    return;
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
                    if (etiket.getIsemri_tipialt().equals("350"))
                    {
                        manipulasyon_paketli_uret(etiket);
                    }
                    else if (etiket.getIsemri_tipialt().equals("150"))
                    {
                        paketli_palet_basla(etiket);
                    }
                    else if (etiket.getIsemri_tipialt().equals("152"))
                    {
                        bigbag_uret(etiket);
                    }
                    else if (etiket.getIsemri_tipialt().equals("400"))
                    {
                        manipulasyon_palet_topla(etiket);
                    }
                }
                else if (etiket.getPaket_tipi().equals("2") || etiket.getPaket_tipi().equals("3"))
                {
                    if (etiket.getIsemri_tipialt().equals("350"))
                    {
                        manipulasyon_paketli_uret(etiket);
                    }
                    else if (etiket.getIsemri_tipialt().equals("151") || etiket.getIsemri_tipialt().equals("152") || etiket.getIsemri_tipialt().equals("153"))
                    {
                        bigbag_uret(etiket);
                    }
                    else if (etiket.getIsemri_tipialt().equals("351"))
                    {
                        manipulasyon_bigbag_uret(etiket);
                    }
                }
                else
                {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Üretilmiş Etiket")
                            .setContentText("Üretim için uygun olmayan etiket \r\n Etiketin üretim işlemi tamamlanmıştır.")
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
            else if (islemDurumu == 1)
            {
                paketli_palet_paket_ekle(str);
            }
            else if (islemDurumu == 2)
            {
                if (paket_listesi.indexOf(str) >= 0)
                {
                    paketli_palet_paket_ekle(str);
                }
                else
                {
                    request_string _Param = new request_string();

                    _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
                    _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
                    _Param.set_zaktif_tesis(_ayaraktiftesis);
                    _Param.set_zsurum(_sbtVerisyon);
                    _Param.set_zkullaniciadi(_zkullaniciadi);
                    _Param.set_zsifre(_zsifre);
                    _Param.setAktif_sunucu(_ayaraktifsunucu);
                    _Param.setAktif_kullanici(_ayaraktifkullanici);

                    _Param.set_value(str);

                    Genel.showProgressDialog(getContext());
                    uretim_etiket etiket = persos.sec_etiket_uretim(_Param);
                    Genel.dismissProgressDialog();

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
                        return;
                    }
                    else if (!etiket.getEtiket_durumu().equals("0"))
                    {
                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Üretilmiş Etiket")
                                .setContentText("Üretim için uygun olmayan etiket.<br/> Etiketin üretim işlemi tamamlanmıştır.")
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
                    else if (!aktif_Palet.getSerino_rfid().equals(etiket.getSerino_rfid()))
                    {

                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("UYARI")
                                .setContentText("Kapatmak için aynı palet etiketini okutunuz...<br/> Aktif : "+aktif_Palet.getSerino_kod()+"<br/>Okutulan :"+etiket.getSerino_kod() )
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
                    else if (etiket.getIsemri_tipialt().equals("350"))
                    {
                        manipulasyon_paketli_uret(etiket);
                    }
                    else if (etiket.getIsemri_tipialt().equals("150"))
                    {
                        paketli_palet_uretim_tamamla(etiket);
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

    private void manipulasyon_palet_toplaOnConfirm(uretim_etiket it_etiket) {

        it_etiket.setDepo(depo.getDepo_id());
        it_etiket.setSilo(silo.getDepo_id());

        request_uretim_etiket _Param= new request_uretim_etiket();
        _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
        _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
        _Param.set_zaktif_tesis(_ayaraktiftesis);
        _Param.set_zsurum(_sbtVerisyon);
        _Param.set_zkullaniciadi(_zkullaniciadi);
        _Param.set_zsifre(_zsifre);
        _Param.setAktif_sunucu(_ayaraktifsunucu);
        _Param.setAktif_kullanici(_ayaraktifkullanici);
        _Param.set_etiket(it_etiket);

        Genel.showProgressDialog(getContext());
        boolean islem_res = persos.fn_dkmpalet_lotundan_uretim(_Param);
        Genel.dismissProgressDialog();

        if (islem_res)
        {
            new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("İŞLEM ONAYI")
                    .setContentText("PALET TOPLAMA İŞLEM KAYDI OLUŞTURULDU")
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
        }
        else
        {
            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("BAĞLANTI PROBLEMİ")
                    .setContentText("İŞLEM YAPILAMADI. DAHA SONRA TEKRAR DENEYİNİZ. \r\n İŞLEM KAYDI TAMAMLANAMADI.")
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
        }
    }

    private void manipulasyon_palet_topla(final uretim_etiket it_etiket) {

        try
        {
            request_uretim_etiket _Param= new request_uretim_etiket();
            _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
            _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
            _Param.set_zaktif_tesis(_ayaraktiftesis);
            _Param.set_zsurum(_sbtVerisyon);
            _Param.set_zkullaniciadi(_zkullaniciadi);
            _Param.set_zsifre(_zsifre);
            _Param.setAktif_sunucu(_ayaraktifsunucu);
            _Param.setAktif_kullanici(_ayaraktifkullanici);
            _Param.set_etiket(it_etiket);
                String miktar = persos.fn_sec_toplam_dkmlot_miktar(_Param);

                if (!miktar.equals(""))
                {
                    int miktar_int = 0;
                    int hedef_miktar = 0;
                    try
                    {
                        miktar_int = Integer.parseInt(miktar);
                        hedef_miktar = Integer.parseInt(it_etiket.getPalet_miktar());
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                        miktar_int = 0;
                        hedef_miktar = 0;
                    }
                    if (miktar_int > 0 && miktar_int >= hedef_miktar)
                    {

                        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("DKMPAKET LOTU PALET OLUŞTUR")
                                .setContentText("SERİNO : " + it_etiket.getSerino_kod() +
                                        "\r\n LOTNO : " + it_etiket.getSer_lotno() +
                                        "\r\nÜRÜN : " + it_etiket.getUrun_kodu() +
                                        "\n\n DKMPAKET LOTUNDAN PALET OLUŞTURMA İŞLEMİNE DEVAM ETMEK İSTİYOR MUSUNUZ ?")
                                .setContentTextSize(20)
                                .setConfirmText("TAMAM")
                                .setCancelText("İPTAL")
                                .showCancelButton(true)
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        manipulasyon_palet_toplaOnConfirm(it_etiket);
                                        sDialog.dismissWithAnimation();
                                    }
                                })
                                .show();


                    }
                    else
                    {

                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("DKMPAKET LOT MİKTAR UYARISI")
                                .setContentText("DKMPAKET LOTUNDA YETERLİ MİKTARDA ÜRÜN BULUNMAMAKTADIR. " +
                                        "\r\n ÜRÜN : " + it_etiket.getUrun_kodu() + ")" +
                                        "\r\n DKMPAKET LOT MİKTARI : " + miktar + " KG " +
                                        "\r\nEKSİK DKMPAKET LOT MİKTARI : " + (hedef_miktar - miktar_int) + " KG" +
                                        "\r\nİŞLEME DEVAM EDEBİLMEK İÇİN EN AZ EKSİK MİKTAR 'DKMPAKET' LOTUNA 'PALET DAĞITMA' İŞLEMİNDEN ÜRÜN AKTARMALISINIZ.  " +
                                        "\r\nİŞLEME DEVAM EDEBİLMEK İÇİN EKSİK ADIMLARI TAMAMLAYINIZ.")
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
                else
                {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("BAĞLANTI PROBLEMİ")
                            .setContentText("İŞLEM YAPILAMADI. DAHA SONRA TEKRAR DENEYİNİZ.\r\n İŞLEM KAYDI TAMAMLANAMADI." )
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
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    private void manipulasyon_paketli_uretOnConfirm(uretim_etiket etiket){
        if (islemDurumu == 0)
            paketli_palet_basla(etiket);
        else if (islemDurumu == 2)
            paketli_palet_uretim_tamamla(etiket);
    }

    private void manipulasyon_paketli_uret(final uretim_etiket etiket) {
        try
        {

                int miktar_int = 0;

                request_uretim_etiket _Param= new request_uretim_etiket();
                _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
                _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
                _Param.set_zaktif_tesis(_ayaraktiftesis);
                _Param.set_zsurum(_sbtVerisyon);
                _Param.set_zkullaniciadi(_zkullaniciadi);
                _Param.set_zsifre(_zsifre);
                _Param.setAktif_sunucu(_ayaraktifsunucu);
                _Param.setAktif_kullanici(_ayaraktifkullanici);
                _Param.set_etiket(etiket);

                String miktar = persos.fn_sec_ambalaj_degisim_toplam_harcanan_miktar(_Param);

                if (miktar == null )
                {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("YETERSİZ ÜRÜN MİKTARI")
                            .setContentText("BU İŞ EMRİNE BAĞLI ÜRETİM YAPMAK İÇİN YETERLİ ÜRÜN HARCAMA İŞLEMİ YAPILMAMIŞ. YETERLİ HARCAMA YAPTIKTAN SONRA İLGİLİ İŞ EMRİNE DEVAM EDEBİLİRSİNİZ. \r\n İŞLEM KAYDI TAMAMLANAMADI.")
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
                }
                else if (miktar.equals(""))
                {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("BAĞLANTI PROBLEMİ")
                            .setContentText("İŞLEM YAPILAMADI. DAHA SONRA TEKRAR DENEYİNİZ.\r\n İŞLEM KAYDI TAMAMLANAMADI.")
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
                }
                else
                {
                    try
                    {
                        miktar_int = Integer.parseInt(miktar);
                        int eklenecek_miktar = Integer.parseInt(etiket.getPalet_miktar());
                        miktar_int = miktar_int - eklenecek_miktar;
                        if (miktar_int >= 0)
                        {
                            new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("PAKET TİPİ DEĞİŞTİR")
                                    .setContentText("SERİNO : " + etiket.getSerino_kod() +
                                            "\r\n LOTNO : " + etiket.getSer_lotno() +
                                            "\r\nÜRÜN : " + etiket.getUrun_kodu() +
                                            "\r\nKULLANILABİLİR MİKTAR : " + miktar + " KG" +
                                            "\r\n PAKET TİPİ DEĞİŞTİRME İŞLEMİNE DEVAM ETMEK İSTİYOR MUSUNUZ ?")
                                    .setContentTextSize(20)
                                    .setConfirmText("TAMAM")
                                    .setCancelText("İPTAL")
                                    .showCancelButton(true)
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            manipulasyon_paketli_uretOnConfirm(etiket);
                                            sDialog.dismissWithAnimation();
                                        }
                                    })
                                    .show();




                        }
                        else
                        {
                            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("YETERSİZ ÜRÜN MİKTARI")
                                    .setContentText("BU İŞ EMRİNE BAĞLI ÜRETİM YAPMAK İÇİN YETERLİ ÜRÜN HARCAMA İŞLEMİ YAPILMAMIŞ.. \r\n KULLANILABİLİR MİKTAR : " + miktar)
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
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }


        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void paketli_palet_basla(uretim_etiket etiket) {

        islemDurumu = 1;
        aktif_Palet = etiket;
        paket_listesi.clear();

        _txtParcaBir.setText(paket_listesi.size()+"");
        _txtParcaIki.setText(etiket.getPalet_dizim()+"");
        fn_AltPanelGorunsunmu(true);
    }

    private void bigbag_uretOnConfirm(uretim_etiket etiket){
        etiket.setDepo(depo.getDepo_id());
        etiket.setSilo(silo.getDepo_id());

        request_uretim_etiket _Param= new request_uretim_etiket();
        _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
        _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
        _Param.set_zaktif_tesis(_ayaraktiftesis);
        _Param.set_zsurum(_sbtVerisyon);
        _Param.set_zkullaniciadi(_zkullaniciadi);
        _Param.set_zsifre(_zsifre);
        _Param.setAktif_sunucu(_ayaraktifsunucu);
        _Param.setAktif_kullanici(_ayaraktifkullanici);

        _Param.set_etiket(etiket);

        Genel.showProgressDialog(getContext());
        View_bool_response result = persos.fn_bigBag_uret(_Param);
        Genel.dismissProgressDialog();

        if (result._result==null || !result._result)
        {
            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText(result._zAciklama)
                    .setContentText(result._zHataAciklama)
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
        }
        else
        {
            update_lot_panel(etiket);
        }
        islemDurumu = 0;
    }

    private void bigbag_uret(final uretim_etiket etiket) {
        try
        {

            new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE)
                    .setTitleText("ONAY")
                    .setContentText(etiket.getSerino_kod() + " seri nolu ürünün üretim işlemini tamamlamak istiyor musunuz ?")
                    .setContentTextSize(20)
                    .setConfirmText("EVET")
                    .setCancelText("HAYIR")
                    .showCancelButton(true)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            bigbag_uretOnConfirm(etiket);
                            sDialog.dismissWithAnimation();
                        }
                    })
                    .show();

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void manipulasyon_bigbag_uretOnConfirm(uretim_etiket etiket){


            if (depo == null || silo == null)
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

            else
            {
                etiket.setDepo(depo.getDepo_id());
                etiket.setSilo(silo.getDepo_id());


                request_uretim_etiket _Param= new request_uretim_etiket();
                _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
                _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
                _Param.set_zaktif_tesis(_ayaraktiftesis);
                _Param.set_zsurum(_sbtVerisyon);
                _Param.set_zkullaniciadi(_zkullaniciadi);
                _Param.set_zsifre(_zsifre);
                _Param.setAktif_sunucu(_ayaraktifsunucu);
                _Param.setAktif_kullanici(_ayaraktifkullanici);

                Genel.showProgressDialog(getContext());
                _Param.set_etiket(etiket);
                Genel.dismissProgressDialog();

                View_bool_response result = persos.fn_bigBag_uret(_Param);

                if (result._result==null || !result._result)
                {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(result._zAciklama)
                            .setContentText(result._zHataAciklama)
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

                }
                else
                {
                    update_lot_panel(etiket);
                }

            }




    }

    private void manipulasyon_bigbag_uret(final uretim_etiket etiket) {
        try
        {

                int miktar_int = 0;
            request_uretim_etiket _Param= new request_uretim_etiket();
            _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
            _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
            _Param.set_zaktif_tesis(_ayaraktiftesis);
            _Param.set_zsurum(_sbtVerisyon);
            _Param.set_zkullaniciadi(_zkullaniciadi);
            _Param.set_zsifre(_zsifre);
            _Param.setAktif_sunucu(_ayaraktifsunucu);
            _Param.setAktif_kullanici(_ayaraktifkullanici);
            _Param.set_etiket(etiket);

            String miktar = persos.fn_sec_ambalaj_degisim_toplam_harcanan_miktar(_Param);
                //String miktar = persos.sec_ambalaj_degisim_toplam_harcanan_miktar(etiket);

                if (miktar == null)
                {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("YETERSİZ ÜRÜN MİKTARI")
                            .setContentText("BU İŞ EMRİNE BAĞLI ÜRETİM YAPMAK İÇİN YETERLİ ÜRÜN HARCAMA İŞLEMİ YAPILMAMIŞ. YETERLİ HARCAMA YAPTIKTAN SONRA İLGİLİ İŞ EMRİNE DEVAM EDEBİLİRSİNİZ. \r\n İŞLEM KAYDI TAMAMLANAMADI.")
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
                }
                else if (miktar.equals(""))
                {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("BAĞLANTI PROBLEMİ")
                            .setContentText("İŞLEM YAPILAMADI. DAHA SONRA TEKRAR DENEYİNİZ. \r\n İŞLEM KAYDI TAMAMLANAMADI.")
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
                }
                else
                {
                    try
                    {
                        miktar_int = Integer.parseInt(miktar);
                        int eklenecek_miktar = Integer.parseInt(etiket.getPalet_miktar());
                        miktar_int = miktar_int - eklenecek_miktar;
                        if (miktar_int >= 0)
                        {
                            new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("BİLGİLENDİRME")
                                    .setContentText("SAP KOD :" + etiket.getSap_kodu() + "\r\n KULLANILABİLİR MİKTAR : " + miktar)
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

                            new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE)
                                    .setTitleText("ONAY")
                                    .setContentText(etiket.getSerino_kod() + " seri nolu ürünün üretim işlemini tamamlamak istiyor musunuz ?")
                                    .setContentTextSize(20)
                                    .setConfirmText("TAMAM")
                                    .setCancelText("İPTAL")
                                    .showCancelButton(true)
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            manipulasyon_bigbag_uretOnConfirm(etiket);
                                            sDialog.dismissWithAnimation();
                                        }
                                    })
                                    .show();





                        }
                        else
                        {
                            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("YETERSİZ ÜRÜN MİKTARI")
                                    .setContentText("BU İŞ EMRİNE BAĞLI ÜRETİM YAPMAK İÇİN YETERLİ ÜRÜN HARCAMA İŞLEMİ YAPILMAMIŞ.. \r\n KULLANILABİLİR MİKTAR : " + miktar)
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

                        }
                    }
                    catch (Exception ex)
                    {
                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("YETERSİZ ÜRÜN MİKTARI")
                                .setContentText("BU İŞ EMRİNE BAĞLI ÜRETİM YAPMAK İÇİN YETERLİ ÜRÜN HARCAMA İŞLEMİ YAPILMAMIŞ.. \r\n KULLANILABİLİR MİKTAR : " + miktar)
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

                    }
                }

        }

        catch (Exception ex)
        {
            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("YETERSİZ ÜRÜN MİKTARI")
                    .setContentText("BU İŞ EMRİNE BAĞLI ÜRETİM YAPMAK İÇİN YETERLİ ÜRÜN HARCAMA İŞLEMİ YAPILMAMIŞ.. \r\n KULLANILABİLİR MİKTAR : " + 0)
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

        }


    }
}
