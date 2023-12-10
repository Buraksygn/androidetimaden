package com.etimaden.depolarArasiSevkIslemi.Depo_cikis;

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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.GirisSayfasi;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.genel.Genel;
import com.etimaden.genel.SweetAlertDialogG;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.DEPOTag;
import com.etimaden.persosclass.Urun_tag;
import com.etimaden.request.request_string;
import com.etimaden.request.request_uruntag_depotag;
import com.etimaden.ugr_demo.R;

public class frg_depo_transfer_ekrani extends Fragment {

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

    boolean isReadable = true;
    DEPOTag depo = null;


    public frg_depo_transfer_ekrani() {
        // Required empty public constructor
    }

    public static frg_depo_transfer_ekrani newInstance()
    {
        return new frg_depo_transfer_ekrani();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_depo_transfer_ekrani, container, false);
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

    public void fn_senddata(DEPOTag depo)
    {
        this.depo=depo;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        new VeriTabani(getContext()).fn_EpcTemizle();

        _myIslem = new VeriTabani(getContext());

        ((GirisSayfasi) getActivity()).fn_ModBarkod();

        _txtYazi=(TextView)getView().findViewById(R.id.txtYazi);
        _txtYazi.setText(" DEPO TRANSFER İŞLEMİ İÇİN ÜRÜN ETİKETİ OKUTUNUZ... ");

        _imageTanimResmi = (ImageView) getView().findViewById(R.id.imageTanimResmi);

        _btnGeri = (Button)getView().findViewById(R.id.btnGeri);
        _btnGeri.playSoundEffect(0);
        _btnGeri.setOnClickListener(new fn_Geri());

        _btnOkuma = (Button)getView().findViewById(R.id.btnOkuma);
        _btnOkuma.playSoundEffect(0);
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

            etiketYorumla(barkod);

        }
        catch (Exception ex){
            Genel.printStackTrace(ex,getContext());
            new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                    .setTitleText("HATA")
                    .setContentTextSize(25)
                    .setContentText("Lütfen uygun bir ürün etiketi okutunuz. Ürün kaydı bulunamadı..")
                    .showCancelButton(false)
                    .show();
            isReadable = true;
        }

    }

    public void rfidOkundu(String rfid){
        try
        {
            if (!isReadable)
            {
                return;
            }
            isReadable = false;

            etiketYorumla(rfid);

        }
        catch (Exception ex){
            Genel.printStackTrace(ex,getContext());
            new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                    .setTitleText("HATA")
                    .setContentTextSize(25)
                    .setContentText("Lütfen uygun bir ürün etiketi okutunuz. Ürün kaydı bulunamadı..")
                    .showCancelButton(false)
                    .show();
            isReadable = true;
        }

    }

    private void etiketYorumla(final String etiket)
    {
        try
        {
            request_string v_Gelen=new request_string();
            v_Gelen.set_zaktif_alt_tesis(_ayaraktifalttesis);
            v_Gelen.set_zaktif_tesis(_ayaraktiftesis);
            v_Gelen.set_zkullaniciadi(_zkullaniciadi);
            v_Gelen.set_zsifre(_zsifre);
            v_Gelen.set_zsunucu_ip_adresi(_ayarsunucuip);
            v_Gelen.set_zsurum(_sbtVerisyon);
            v_Gelen.setAktif_kullanici(_ayaraktifkullanici);
            v_Gelen.setAktif_sunucu(_ayaraktifsunucu);

            v_Gelen.set_value(etiket);

            Genel.showProgressDialog(getContext());
            Urun_tag urun = persos.fn_secDepoSevkEtiket(v_Gelen);
            Genel.dismissProgressDialog();

            if (urun == null)
            {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Lütfen uygun bir ürün etiketi okutunuz. Ürün kaydı bulunamadı..")
                        .showCancelButton(false)
                        .show();
                isReadable = true;
                return;
            }
            else if (!urun.isletme.equals(depo.getIsletme_kod()))
            {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Seçilen depo işletme verisi ile ürün işletme verisi uyuşmadığı için bu işlemi yapamazsınız. ")
                        .showCancelButton(false)
                        .show();
                isReadable = true;
                return;
            }
            else if (urun.son_depo_kod.equals(depo.getDepo_id()))
            {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Seçilen ürün zaten aynı depoda bulunmaktadır. ")
                        .showCancelButton(false)
                        .show();
                isReadable = true;
                return;
            }
            else if (urun.kilitli.equals("True"))
            {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                    .setTitleText("HATA")
                    .setContentTextSize(25)
                    .setContentText("Ürünün manipülasyon işlemi tamamlanması bekleniyor. ")
                    .showCancelButton(false)
                    .show();
                isReadable = true;
                return;
            }
            else if (urun.islem_durumu.equals("3"))
            {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Ürün satılmış ürünler içerinde görünmektedir. Lütfen ilgili kişiye durumu bildiriniz. ")
                        .showCancelButton(false)
                        .show();
                isReadable = true;
                return;
            }
            else if (urun.islem_durumu.equals("0"))
            {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Ürün üretilmemiş ürünler içerinde görünmektedir. Lütfen ilgili kişiye durumu bildiriniz. ")
                        .showCancelButton(false)
                        .show();
                isReadable = true;
                return;
            }
            if (urun.islem_durumu.equals("1") && !urun.etiket_turu.equals("0") )
            {
                request_uruntag_depotag _Param=new request_uruntag_depotag();
                _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
                _Param.set_zaktif_tesis(_ayaraktiftesis);
                _Param.set_zkullaniciadi(_zkullaniciadi);
                _Param.set_zsifre(_zsifre);
                _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
                _Param.set_zsurum(_sbtVerisyon);
                _Param.setAktif_kullanici(_ayaraktifkullanici);
                _Param.setAktif_sunucu(_ayaraktifsunucu);

                _Param.setUrun(urun);
                _Param.setDepo(depo);

                Genel.showProgressDialog(getContext());
                Boolean islemRes = persos.fn_depoSevkTamamla(_Param);
                Genel.dismissProgressDialog();

                if (islemRes)
                {
                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.SUCCESS_TYPE)
                            .setTitleText("DEPO GİRİŞ KAYDI")
                            .setContentText("Depo giriş kaydı oluşturuldu..")
                            .setContentTextSize(20)
                            .setConfirmText("TAMAM")
                            .showCancelButton(false)
                            .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialogG sDialog) {
                                    sDialog.dismissWithAnimation();
                                    return;
                                }
                            }).show();
                }
                else
                {
                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                            .setTitleText("HATA")
                            .setContentTextSize(25)
                            .setContentText("Kayıt yapılamadı. Daha sonra tekrar deneyiniz..")
                            .showCancelButton(false)
                            .show();

                }

                isReadable=true;
                return;
            }
            else
            {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Ürün yapmak istediğiniz işlem için uygun değil.\n HATA KODU :"+urun.islem_durumu)
                        .showCancelButton(false)
                        .show();
                isReadable = true;
                return;
            }

        }
        catch (Exception ex)
        {
            Genel.printStackTrace(ex,getContext());
            new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                    .setTitleText("HATA")
                    .setContentTextSize(25)
                    .setContentText("Lütfen uygun bir ürün etiketi okutunuz. Ürün kaydı bulunamadı..")
                    .showCancelButton(false)
                    .show();
            isReadable = true;
            return;
        }
    }

    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_depo_secimi_transfer fragmentyeni = new frg_depo_secimi_transfer();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_depo_secimi_transfer").addToBackStack(null);
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
