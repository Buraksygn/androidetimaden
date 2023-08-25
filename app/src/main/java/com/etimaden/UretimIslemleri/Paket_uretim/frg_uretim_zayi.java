package com.etimaden.UretimIslemleri.Paket_uretim;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.GirisSayfasi;
import com.etimaden.UretimIslemleri.frg_uretim_menu_panel;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.Urun_tag;
import com.etimaden.request.request_secEtiket;
import com.etimaden.request.request_uretim_zayi;
import com.etimaden.ugr_demo.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.etimaden.cSabitDegerler._ipAdresi3G;
import static com.etimaden.cSabitDegerler._sbtVerisyon;
import static com.etimaden.cSabitDegerler._zkullaniciadi;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;

public class frg_uretim_zayi extends Fragment {

    boolean isReadeable = true;
    String _OnlineUrl = "";
    Persos persos;
    Button _btngeri;
    RadioButton _radioButton1;
    RadioButton _radioButton2;
    RadioButton _radioButton3;
    RadioButton _radioButton4;

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

        if (_ayarbaglantituru.equals("wifi")) {
            _OnlineUrl = "http://" + _ayarsunucuip + ":" + _zportWifi + "/";
        } else {
            _OnlineUrl = "http://" + _ipAdresi3G + ":" + _zport3G + "/";
        }

        persos = new Persos(_OnlineUrl);

        StrictMode.ThreadPolicy gfgPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(gfgPolicy);
    }

    public frg_uretim_zayi() {
        // Required empty public constructor
    }

    public static frg_uretim_zayi newInstance()
    {
        return new frg_uretim_zayi();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_uretim_zayi, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        new VeriTabani(getContext()).fn_EpcTemizle();

        _myIslem = new VeriTabani(getContext());

        fn_AyarlariYukle();

        //barkod,rfid,ikiside
        ((GirisSayfasi) getActivity()).fn_ModBoth();

        _btngeri = (Button)getView().findViewById(R.id.btncikis);
        _btngeri.playSoundEffect(0);
        _btngeri.setOnClickListener(new fn_Geri());

        _radioButton1=(RadioButton) getView().findViewById(R.id.radioButton1);
        _radioButton2=(RadioButton) getView().findViewById(R.id.radioButton2);
        _radioButton3=(RadioButton) getView().findViewById(R.id.radioButton3);
        _radioButton4=(RadioButton) getView().findViewById(R.id.radioButton4);

    }




    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_uretim_menu_panel fragmentyeni = new frg_uretim_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_uretim_menu_panel").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    public void fn_BarkodOkutuldu(final String barcode)
    {
        System.out.println("Barkod okutuldu : " + barcode);
        try
        {
            if (_radioButton1.isChecked() || _radioButton2.isChecked() || _radioButton3.isChecked() || _radioButton4.isChecked())
            { }
            else
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("UYARI")
                        .setContentTextSize(25)
                        .setContentText("UYARI : " + "Lütfen yapmak istediğiniz zayi türünü seçiniz.")
                        .showCancelButton(false)
                        .show();
                return;
            }

            String _barcode = barcode.substring(barcode.length() - 24, 24);
            if (!isReadeable)
            {
                return;
            }
            isReadeable = false;
            //System.Media.SystemSounds.Question.Play();
            request_secEtiket v_Gelen=new request_secEtiket();

            v_Gelen.set_rfid(_barcode);
            v_Gelen.set_zaktif_alt_tesis(_ayaraktifalttesis);
            v_Gelen.set_zaktif_tesis(_ayaraktiftesis);
            v_Gelen.set_zkullaniciadi(_zkullaniciadi);
            v_Gelen.set_zsifre(_zsifre);
            v_Gelen.set_zsunucu_ip_adresi(_ayarsunucuip);
            v_Gelen.set_zsurum(_sbtVerisyon);
            v_Gelen.setAktif_kullanici(_ayaraktifkullanici);
            v_Gelen.setAktif_sunucu(_ayaraktifsunucu);

            Urun_tag tag = persos.fn_secEtiket(v_Gelen);
            etiketDegerlendir(tag);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        //Thread.Sleep(2000);
        isReadeable = true;

    }

    public void fn_rfidOkundu(String v_epc)
    {
        System.out.println("Rfid okutuldu : " + v_epc);
        try
        {
            if (_radioButton1.isChecked() || _radioButton2.isChecked() || _radioButton3.isChecked() || _radioButton4.isChecked())
            { }
            else
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("UYARI")
                        .setContentTextSize(25)
                        .setContentText("UYARI : " + "Lütfen yapmak istediğiniz zayi türünü seçiniz.")
                        .showCancelButton(false)
                        .show();
                return;
            }

            if (!isReadeable)
            {
                return;
            }
            isReadeable = false;
            //System.Media.SystemSounds.Question.Play();
            request_secEtiket v_Gelen=new request_secEtiket();

            v_Gelen.set_rfid(v_epc);
            v_Gelen.set_zaktif_alt_tesis(_ayaraktifalttesis);
            v_Gelen.set_zaktif_tesis(_ayaraktiftesis);
            v_Gelen.set_zkullaniciadi(_zkullaniciadi);
            v_Gelen.set_zsifre(_zsifre);
            v_Gelen.set_zsunucu_ip_adresi(_ayarsunucuip);
            v_Gelen.set_zsurum(_sbtVerisyon);
            v_Gelen.setAktif_kullanici(_ayaraktifkullanici);
            v_Gelen.setAktif_sunucu(_ayaraktifsunucu);

            Urun_tag tag = persos.fn_secEtiket(v_Gelen);
            etiketDegerlendir(tag);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        //Thread.Sleep(2000);
        isReadeable = true;
    }

    private void etiketDegerlendirOnConfirm(Urun_tag tag){
        String islem_turu = "D";
        if (_radioButton1.isChecked())
            islem_turu = "D";
        else if (_radioButton2.isChecked())
            islem_turu = "H";
        else if (_radioButton3.isChecked())
            islem_turu = "S";
        else if (_radioButton4.isChecked())
            islem_turu = "I";
        else
            islem_turu = "D";

        request_uretim_zayi v_Gelen=new request_uretim_zayi();
        v_Gelen.set_zaktif_alt_tesis(_ayaraktifalttesis);
        v_Gelen.set_zaktif_tesis(_ayaraktiftesis);
        v_Gelen.set_zkullaniciadi(_zkullaniciadi);
        v_Gelen.set_zsifre(_zsifre);
        v_Gelen.set_zsunucu_ip_adresi(_ayarsunucuip);
        v_Gelen.set_zsurum(_sbtVerisyon);
        v_Gelen.setAktif_kullanici(_ayaraktifkullanici);
        v_Gelen.setAktif_sunucu(_ayaraktifsunucu);
        v_Gelen.set_Urun_tag(tag);
        v_Gelen.set_islem_turu(islem_turu);
        boolean islem_res = persos.fn_uretim_zayi(v_Gelen);

        if (islem_res)
        {
            if (_radioButton4.isChecked())
                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("İŞLEM ONAYI")
                        .setContentText("ÜRETİM ZAYİ İPTAL İŞLEM KAYDI OLUŞTURULDU")
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
            else{
                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("İŞLEM ONAYI")
                        .setContentText("ÜRETİM ZAYİ İŞLEM KAYDI OLUŞTURULDU")
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
        }
    }

    private void etiketDegerlendir(final Urun_tag tag)
    {
        try
        {
            if (tag == null )
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("BAĞLANTI PROBLEMİ")
                        .setContentText("ÜRÜN BİLGİSİ ALINAMADI. DAHA SONRA TEKRAR DENEYİNİZ.\r\n Etiket verisine ulaşılamadı.")
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
            else if (tag.islem_durumu != "1" && tag.islem_durumu != "10")
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("İŞLEM İÇİN UYGUN OLMAYAN ÜRÜN")
                        .setContentText("Ürün yapmak istediğiniz işlem için uygun değildir.\r\n İşleme uygun olmayan etiket.\r\n HATA KODU : " + tag.islem_durumu )
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
            else if (tag.etiket_turu == "0" && !tag.lotno.equals("DKMPAKET"))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("İŞLEM İÇİN UYGUN OLMAYAN ÜRÜN")
                        .setContentText("Ürün yapmak istediğiniz işlem için uygun değildir.\r\n İşleme uygun olmayan etiket.\r\n HATA KODU : " + tag.islem_durumu )
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
               /* else if (tag.uretim_dakika_onayi != "1")
                {
                    Dialogs.ErrorDialog ed = new Dialogs.ErrorDialog("ONAY ALINAMADI", "ÜRETİM ZAYİ ONAYI ALINAMADI. DETAYLAR KISMINDAN ÜRETİM ZAYİ KURALLARINI OKUYABİLİRSİNİZ."
                                                                                , "ÜRETİM KURALLARI" + Environment.NewLine + "1) 3 GÜN İÇİNDE ÜRETİM ZAYİ KAYDI GERÇEKLEŞMEYEN ETİKET İÇİN ÜRETİM ZAYİ KAYDI OLUŞTURULAMAZ." );
                    ed.ShowDialog();
                    return;
                }*/
            else
            {

                if (tag.islem_durumu.equals("10") && _radioButton4.isChecked())
                {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("ÜRETİM ZAYİ")
                            .setContentText("SERİNO : " + tag.kod + "\r\n" +"LOTNO : " + tag.lotno + "\r\n" + "ÜRÜN : " + tag.urun_adi + "\r\n" + "ÜRETİM ZAYİ İPTAL İŞLEMİNE DEVAM ETMEK İSTİYOR MUSUNUZ ?")
                            .setContentTextSize(20)
                            .setConfirmText("TAMAM")
                            .showCancelButton(true)
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    etiketDegerlendirOnConfirm(tag);
                                    sDialog.dismissWithAnimation();

                                }
                            })
                            .show();
                }

                else
                {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("ÜRETİM ZAYİ")
                            .setContentText("SERİNO : " + tag.kod + "\r\n" + "LOTNO : " + tag.lotno + "\r\n" + "ÜRÜN : " + tag.urun_adi + "\r\n" + "ÜRETİM ZAYİ İŞLEMİNE DEVAM ETMEK İSTİYOR MUSUNUZ ?")
                            .setContentTextSize(20)
                            .setConfirmText("TAMAM")
                            .showCancelButton(true)
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    etiketDegerlendirOnConfirm(tag);
                                    sDialog.dismissWithAnimation();

                                }
                            })
                            .show();

                }

            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


}
