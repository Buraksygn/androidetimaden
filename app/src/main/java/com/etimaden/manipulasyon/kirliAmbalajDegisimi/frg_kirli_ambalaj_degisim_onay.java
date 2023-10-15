package com.etimaden.manipulasyon.kirliAmbalajDegisimi;

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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.GirisSayfasi;
import com.etimaden.UretimIslemleri.frg_uretim_menu_panel;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.genel.Genel;
import com.etimaden.genel.SweetAlertDialogG;
import com.etimaden.manipulasyon.Ambalaj_tipi_degisimi.frg_ambalaj_tipi_degisimi;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.Urun_tag;
import com.etimaden.request.request_secEtiket;
import com.etimaden.request.request_shrink_onayi_al;
import com.etimaden.request.request_uruntag_string;
import com.etimaden.ugr_demo.R;

public class frg_kirli_ambalaj_degisim_onay extends Fragment {


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



    ImageView _imageTanimResmi;
    TextView _txtYazi;
    Button _btnGeri;
    Button _btnOkuma;

    Urun_tag aktif_etiket = null;
    boolean isReadable = true;
    int islemDurumu = 0;
    //  0 : etiket okutma bekleniyor
    //  1 : 2. etiket okutması bekleniyor

    public frg_kirli_ambalaj_degisim_onay() {
        // Required empty public constructor
    }

    public static frg_kirli_ambalaj_degisim_onay newInstance()
    {
        return new frg_kirli_ambalaj_degisim_onay();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_kirli_ambalaj_degisim_onay, container, false);
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

        _txtYazi=(TextView)getView().findViewById(R.id.txtYazi);
        _txtYazi.setText("ESKİ ETİKETİ OKUTUNUZ...");

        _imageTanimResmi = (ImageView) getView().findViewById(R.id.imageTanimResmi);

        _btnGeri = (Button)getView().findViewById(R.id.btnGeri);
        _btnGeri.playSoundEffect(SoundEffectConstants.CLICK);
        _btnGeri.setOnClickListener(new fn_Geri());

        _btnOkuma = (Button)getView().findViewById(R.id.btnOkuma);
        _btnOkuma.playSoundEffect(SoundEffectConstants.CLICK);
        _btnOkuma.setOnClickListener(new fn_okumaDegistir());
        _btnOkuma.setText("KAREKOD");

        fn_AyarlariYukle();

    }
    public void barkodOkundu(String barkod){

        try
        {
            barkod = barkod.substring(barkod.length() - 24);
            if (!isReadable)
            {
                return;
            }
            isReadable = false;

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


            etiketDegerlendir(tag,barkod);

        }
        catch (Exception ex){
            Genel.printStackTrace(ex,getContext());
        }
        isReadable = true;
    }

    public void rfidOkundu(String rfid){
        try
        {
            if (!isReadable)
            {
                return;
            }
            isReadable = false;
            //System.Media.SystemSounds.Question.Play();
            request_secEtiket v_Gelen=new request_secEtiket();
            v_Gelen.set_rfid(rfid);
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

            etiketDegerlendir(tag,rfid);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        isReadable = true;

    }

    private void etiketDegerlendir(final Urun_tag tag,final String okunanEtiket)
    {
        try
        {
            if (islemDurumu == 0)
            {

                if (tag == null || (!tag.islem_durumu.equals("360") && !tag.islem_durumu.equals("1")))
                {
                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                            .setTitleText("İŞLEM İÇİN UYGUN OLMAYAN ÜRÜN")
                            .setContentTextSize(25)
                            .setContentText("Ürün yapmak istediğiniz işlem için uygun değildir. \r\n İşleme uygun olmayan etiket.")
                            .showCancelButton(false)
                            .show();
                }
                else
                {
                    aktif_etiket = tag;
                    islemDurumu = 1;
                    _txtYazi.setText("YENİ ETİKETİ OKUTUNUZ...");
                    _imageTanimResmi.setImageResource(R.mipmap.tag_red);
                }
            }
            else if (islemDurumu == 1)
            {

                if (aktif_etiket.etiket_turu.equals("3") && tag.kod.equals(aktif_etiket.kod))
                {
                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                            .setTitleText("İşlem Onay Sorgusu")
                            .setContentText("Kirli ambalaj değişim  işlemi gerçekleştirilecek. Bu işlemi onaylıyor musunuz? ")
                            .setContentTextSize(20)
                            .setConfirmText("EVET")
                            .setCancelText("HAYIR")
                            .showCancelButton(true)
                            .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialogG sDialog) {
                                    sDialog.dismissWithAnimation();

                                    request_uruntag_string v_Gelen=new request_uruntag_string();
                                    v_Gelen.set_zaktif_alt_tesis(_ayaraktifalttesis);
                                    v_Gelen.set_zaktif_tesis(_ayaraktiftesis);
                                    v_Gelen.set_zkullaniciadi(_zkullaniciadi);
                                    v_Gelen.set_zsifre(_zsifre);
                                    v_Gelen.set_zsunucu_ip_adresi(_ayarsunucuip);
                                    v_Gelen.set_zsurum(_sbtVerisyon);
                                    v_Gelen.setAktif_kullanici(_ayaraktifkullanici);
                                    v_Gelen.setAktif_sunucu(_ayaraktifsunucu);

                                    v_Gelen.setEtiket(tag);
                                    v_Gelen.setStringValue("");

                                    Genel.showProgressDialog(getContext());
                                    Boolean result = persos.fn_kirli_ambalaj_degistir_onay(v_Gelen);
                                    Genel.dismissProgressDialog();

                                    aktif_etiket = null;
                                    islemDurumu = 0;
                                    _txtYazi.setText("ESKİ ETİKETİ OKUTUNUZ...");
                                    _imageTanimResmi.setImageResource(R.mipmap.tag_yellow);

                                    return;
                                }
                            })
                            .setCancelClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialogG sDialog) {
                                    sDialog.dismissWithAnimation();
                                    return;
                                }
                            })
                            .show();


                }
                else if (aktif_etiket.etiket_turu.equals("2") || aktif_etiket.etiket_turu.equals("1"))
                {
                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                            .setTitleText("İşlem Onay Sorgusu")
                            .setContentText("Kirli ambalaj değişim  işlemi gerçekleştirilecek. Bu işlemi onaylıyor musunuz?")
                            .setContentTextSize(20)
                            .setConfirmText("EVET")
                            .setCancelText("HAYIR")
                            .showCancelButton(true)
                            .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialogG sDialog) {
                                    sDialog.dismissWithAnimation();

                                    request_uruntag_string v_Gelen=new request_uruntag_string();
                                    v_Gelen.set_zaktif_alt_tesis(_ayaraktifalttesis);
                                    v_Gelen.set_zaktif_tesis(_ayaraktiftesis);
                                    v_Gelen.set_zkullaniciadi(_zkullaniciadi);
                                    v_Gelen.set_zsifre(_zsifre);
                                    v_Gelen.set_zsunucu_ip_adresi(_ayarsunucuip);
                                    v_Gelen.set_zsurum(_sbtVerisyon);
                                    v_Gelen.setAktif_kullanici(_ayaraktifkullanici);
                                    v_Gelen.setAktif_sunucu(_ayaraktifsunucu);

                                    v_Gelen.setEtiket(tag);
                                    v_Gelen.setStringValue("");

                                    Genel.showProgressDialog(getContext());
                                    Boolean result = persos.fn_kirli_ambalaj_degistir_onay(v_Gelen);
                                    Genel.dismissProgressDialog();

                                    aktif_etiket = null;
                                    islemDurumu = 0;
                                    _txtYazi.setText("ESKİ ETİKETİ OKUTUNUZ...");
                                    _imageTanimResmi.setImageResource(R.mipmap.tag_yellow);

                                    return;
                                }
                            })
                            .setCancelClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialogG sDialog) {
                                    sDialog.dismissWithAnimation();
                                    return;
                                }
                            })
                            .show();

                }
                else if(aktif_etiket.etiket_turu == "0" && tag == null)
                {
                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                            .setTitleText("İşlem Onay Sorgusu")
                            .setContentText("Kirli ambalaj değişim  işlemi gerçekleştirilecek. Bu işlemi onaylıyor musunuz?")
                            .setContentTextSize(20)
                            .setConfirmText("EVET")
                            .setCancelText("HAYIR")
                            .showCancelButton(true)
                            .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialogG sDialog) {
                                    sDialog.dismissWithAnimation();

                                    request_uruntag_string v_Gelen=new request_uruntag_string();
                                    v_Gelen.set_zaktif_alt_tesis(_ayaraktifalttesis);
                                    v_Gelen.set_zaktif_tesis(_ayaraktiftesis);
                                    v_Gelen.set_zkullaniciadi(_zkullaniciadi);
                                    v_Gelen.set_zsifre(_zsifre);
                                    v_Gelen.set_zsunucu_ip_adresi(_ayarsunucuip);
                                    v_Gelen.set_zsurum(_sbtVerisyon);
                                    v_Gelen.setAktif_kullanici(_ayaraktifkullanici);
                                    v_Gelen.setAktif_sunucu(_ayaraktifsunucu);

                                    v_Gelen.setEtiket(aktif_etiket);
                                    v_Gelen.setStringValue(okunanEtiket);

                                    Genel.showProgressDialog(getContext());
                                    Boolean result = persos.fn_kirli_ambalaj_degistir_onay(v_Gelen);
                                    Genel.dismissProgressDialog();

                                    aktif_etiket = null;
                                    islemDurumu = 0;
                                    _txtYazi.setText("ESKİ ETİKETİ OKUTUNUZ...");
                                    _imageTanimResmi.setImageResource(R.mipmap.tag_yellow);

                                    return;
                                }
                            })
                            .setCancelClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialogG sDialog) {
                                    sDialog.dismissWithAnimation();
                                    return;
                                }
                            })
                            .show();

                }
                else
                {
                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                            .setTitleText("HATALI İŞLEM")
                            .setContentTextSize(25)
                            .setContentText("İşleme uygun olmayan etiket.")
                            .showCancelButton(false)
                            .show();

                }
            }

        }
        catch (Exception ex) {
            Genel.printStackTrace(ex,getContext());
        }

    }

    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_kirli_ambalaj_menu_panel fragmentyeni = new frg_kirli_ambalaj_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_kirli_ambalaj_menu_panel").addToBackStack(null);
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
