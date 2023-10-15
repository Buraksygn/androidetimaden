package com.etimaden.UretimIslemleri.Paket_uretim;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.GirisSayfasi;
import com.etimaden.UretimIslemleri.frg_uretim_menu_panel;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.genel.Genel;
import com.etimaden.genel.SweetAlertDialogG;
import com.etimaden.manipulasyon.ellecleme.frg_ellecleme_onay;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.Urun_tag;
import com.etimaden.request.request_secEtiket;
import com.etimaden.request.request_uretim_iptali;
import com.etimaden.ugr_demo.R;


import static com.etimaden.cSabitDegerler._ipAdresi3G;
import static com.etimaden.cSabitDegerler._sbtVerisyon;
import static com.etimaden.cSabitDegerler._zkullaniciadi;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;

public class frg_uretim_iptal extends Fragment {

    SweetAlertDialogG pDialog;
    boolean isReadable = true;
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


    //Button _btnShrinkOlustur;
    //ListView _isemri_list;
    Button _btngeri;
    Button _btnOkuma;
    TextView _txtYazi;


    //Urun_tag aktif_etiket;

    //uretim_etiket urun;

    //ArrayList<Urun_tag> dataModels;
    //Urun_tag _Secili = new Urun_tag();
    //private static apmblAktifIsEmirleri adapter;

    int islemDurumu = 0;
    //  0 : etiket okutma bekleniyor
    //  1 : bigbag üretim işlemi yapılıyor
    //  2 : paketli palet üretim işlemi yapılıyor
    //  3 : paket üretim işlemi yapılıyor
    public static int _iIptalOncesiDepoKontrol = 0;


    public frg_uretim_iptal() {
        // Required empty public constructor
    }

    public static frg_uretim_iptal newInstance()
    {
        return new frg_uretim_iptal();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_uretim_iptal, container, false);
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

        //barkod,rfid,ikiside
        ((GirisSayfasi) getActivity()).fn_ModBarkod();

        _txtYazi=(TextView)getView().findViewById(R.id.txtYazi);


        _btngeri = (Button)getView().findViewById(R.id.btnGeri);
        _btngeri.playSoundEffect(SoundEffectConstants.CLICK);
        _btngeri.setOnClickListener(new fn_Geri());

        _btnOkuma = (Button)getView().findViewById(R.id.btnOkuma);
        _btnOkuma.playSoundEffect(SoundEffectConstants.CLICK);
        _btnOkuma.setOnClickListener(new fn_okumaDegistir());
        _btnOkuma.setText("KAREKOD");


        fn_AyarlariYukle();

    }
    public void fn_BarkodOkutuldu(String barkod) {

        try
        {
            barkod = barkod.substring(barkod.length() - 24);
            if (!isReadable)
            {
                return;
            }
            isReadable = false;
            //System.Media.SystemSounds.Question.Play();

            request_secEtiket v_Gelen=new request_secEtiket();

            v_Gelen.set_rfid(barkod);
            v_Gelen.set_zaktif_alt_tesis(_ayaraktifalttesis);
            v_Gelen.set_zaktif_tesis(_ayaraktiftesis);
            v_Gelen.set_zkullaniciadi(_zkullaniciadi);
            v_Gelen.set_zsifre(_zsifre);
            v_Gelen.set_zsunucu_ip_adresi(_ayarsunucuip);
            v_Gelen.set_zsurum(_sbtVerisyon);
            v_Gelen.setAktif_kullanici(_ayaraktifkullanici);
            v_Gelen.setAktif_sunucu(_ayaraktifsunucu);

            Genel.showProgressDialog(getContext());
            Urun_tag tag = persos.fn_secEtiket(v_Gelen);
            Genel.dismissProgressDialog();


            etiketDegerlendir(tag);

        }
        catch (Exception ex){
            Genel.printStackTrace(ex,getContext());
        }
        //Thread.Sleep(1000);
        //isReadable = true;


    }

    public void fn_RfidOkundu(String v_epc){
        System.out.println("Rfid okutuldu : " + v_epc);

        try
        {
            if (!isReadable)
            {
                return;
            }
            isReadable = false;
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

            Genel.showProgressDialog(getContext());
            Urun_tag tag = persos.fn_secEtiket(v_Gelen);
            Genel.dismissProgressDialog();

            etiketDegerlendir(tag);
        }
        catch (Exception ex){
            Genel.printStackTrace(ex,getContext());
        }

        //isReadable = true;

    }

    private void etiketDegerlendirOnConfirm(Urun_tag tag){

        try {
            request_uretim_iptali _Param = new request_uretim_iptali();
            _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
            _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
            _Param.set_zaktif_tesis(_ayaraktiftesis);
            _Param.set_zsurum(_sbtVerisyon);
            _Param.set_zkullaniciadi(_zkullaniciadi);
            _Param.set_zsifre(_zsifre);
            _Param.setAktif_sunucu(_ayaraktifsunucu);
            _Param.setAktif_kullanici(_ayaraktifkullanici);
            _Param.setUrun_tag(tag);

            //String miktar = persos.fn_sec_ambalaj_degisim_toplam_harcanan_miktar(_Param);
            Genel.showProgressDialog(getContext());
            Boolean result = persos.fn_uretim_iptali(_Param);
            Genel.dismissProgressDialog();
            //Boolean islem_res = persos.fn_uretim_iptali(tag);

            if (result) {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                        .setTitleText("İŞLEM ONAYI")
                        .setContentText("ÜRETİM İPTAL İŞLEM KAYDI OLUŞTURULDU")
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
                isReadable = true;

            } else {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("BAĞLANTI PROBLEMİ")
                        .setContentTextSize(25)
                        .setContentText("İŞLEM YAPILAMADI. DAHA SONRA TEKRAR DENEYİNİZ. \r\n İŞLEM KAYDI TAMAMLANAMADI.")
                        .showCancelButton(false)
                        .show();
                isReadable = true;
                return;

            }
        }catch(Exception ex){
            isReadable = true;
            Genel.printStackTrace(ex,getContext());
        }

    }

    private void etiketDegerlendir(final Urun_tag tag)    {
        try
        {
            if (tag == null)
            {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("BAĞLANTI PROBLEMİ")
                        .setContentTextSize(25)
                        .setContentText("ÜRÜN BİLGİSİ ALINAMADI. DAHA SONRA TEKRAR DENEYİNİZ. \r\n Etiket verisine ulaşılamadı.")
                        .showCancelButton(false)
                        .show();
                return;

            }
            else if (!tag.islem_durumu.equals("1") )
            {

                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("İŞLEM İÇİN UYGUN OLMAYAN ÜRÜN")
                        .setContentTextSize(25)
                        .setContentText("Ürün yapmak istediğiniz işlem için uygun değildir. \r\n İşleme uygun olmayan etiket. \r\n HATA KODU : " + tag.islem_durumu)
                        .showCancelButton(false)
                        .show();
                isReadable = true;
                return;

               }
            else if (!tag.uretim_dakika_onayi.equals("1") )
            {

                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("ONAY ALINAMADI")
                        .setContentTextSize(25)
                        .setContentText("ÜRETİM İPTAL ONAYI ALINAMADI. DETAYLAR KISMINDAN ÜRETİM İPTAL KURALLARINI OKUYABİLİRSİNİZ. " +
                                "\r\n ÜRETİM KURALLARI " +
                                "\r\n 1) 3 GÜN İÇİNDE ÜRETİM İPTAL KAYDI GERÇEKLEŞMEYEN ETİKET İÇİN ÜRETİM İPTAL KAYDI OLUŞTURULAMAZ.")
                        .showCancelButton(false)
                        .show();
                isReadable = true;
                return;

            }
            else
            {

                if (_iIptalOncesiDepoKontrol==1  && tag.son_depo_kod.trim().startsWith("URN"))
                {
                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                            .setTitleText("İŞLEM İÇİN UYGUN OLMAYAN DEPO")
                            .setContentTextSize(25)
                            .setContentText("Ürünün bulunduğu depo yapmak istediğiniz işlem için uygun değildir. " +
                                    "\r\n İşleme uygun olmayan depo. " +
                                    "\r\n DEPO KODU :" + tag.son_depo_kod)
                            .showCancelButton(false)
                            .show();
                    isReadable = true;

                }
                else
                {
                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                            .setTitleText("ÜRETİM İPTALİ")
                            .setContentText("SERİNO : " + tag.kod + " \r\n LOTNO : " + tag.lotno + " \r\n ÜRÜN : " + tag.urun_adi + " \r\n ÜRETİM İPTAL İŞLEMİNE DEVAM ETMEK İSTİYOR MUSUNUZ ?")
                            .setContentTextSize(20)
                            .setConfirmText("EVET")
                            .setCancelText("HAYIR")
                            .showCancelButton(true)
                            .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialogG sDialog) {
                                    sDialog.dismissWithAnimation();
                                    etiketDegerlendirOnConfirm(tag);

                                }
                            })
                            .show();



                }
            }

        }
        catch (Exception ex){
            isReadable = true;
            Genel.printStackTrace(ex,getContext());
        }


    }

    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_uretim_menu_panel fragmentyeni = new frg_uretim_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_uretim_menu_panel").addToBackStack(null);
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
