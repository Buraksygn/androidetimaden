package com.etimaden.depolarArasiSevkIslemi.sayilamayan_aktivasyon;

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
import com.etimaden.depolarArasiSevkIslemi.Depo_cikis.frg_depo_secimi_transfer;
import com.etimaden.depolarArasiSevkIslemi.frg_depolar_arasi_transfer_menu_panel;
import com.etimaden.genel.Genel;
import com.etimaden.genel.SweetAlertDialogG;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.DEPOTag;
import com.etimaden.persosclass.Urun_tag;
import com.etimaden.request.request_secEtiket;
import com.etimaden.request.request_string;
import com.etimaden.request.request_uruntag;
import com.etimaden.request.request_uruntag_depotag;
import com.etimaden.ugr_demo.R;

public class frg_sayilamayan_onay extends Fragment {

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

    public frg_sayilamayan_onay() {
        // Required empty public constructor
    }

    public static frg_sayilamayan_onay newInstance()
    {
        return new frg_sayilamayan_onay();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_sayilamayan_onay, container, false);
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
            etiketDegerlendir(barkod);
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
            etiketDegerlendir(rfid);
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
    private void etiketDegerlendir(final String etiket)
    {
        try {
            request_secEtiket v_Gelen = new request_secEtiket();
            v_Gelen.set_zaktif_alt_tesis(_ayaraktifalttesis);
            v_Gelen.set_zaktif_tesis(_ayaraktiftesis);
            v_Gelen.set_zkullaniciadi(_zkullaniciadi);
            v_Gelen.set_zsifre(_zsifre);
            v_Gelen.set_zsunucu_ip_adresi(_ayarsunucuip);
            v_Gelen.set_zsurum(_sbtVerisyon);
            v_Gelen.setAktif_kullanici(_ayaraktifkullanici);
            v_Gelen.setAktif_sunucu(_ayaraktifsunucu);

            v_Gelen.set_rfid(etiket);

            Genel.showProgressDialog(getContext());
            final Urun_tag tag = persos.fn_secEtiket(v_Gelen);
            Genel.dismissProgressDialog();

            if (tag == null) {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("BAĞLANTI PROBLEMİ")
                        .setContentTextSize(25)
                        .setContentText("ÜRÜN BİLGİSİ ALINAMADI. DAHA SONRA TEKRAR DENEYİNİZ. \r\n Etiket verisine ulaşılamadı.")
                        .showCancelButton(false)
                        .show();
                isReadable = true;
            } else if (!tag.islem_durumu.equals("33")) {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("İŞLEM İÇİN UYGUN OLMAYAN ÜRÜN")
                        .setContentTextSize(25)
                        .setContentText("Ürün yapmak istediğiniz işlem için uygun değildir. \r\n İşleme uygun olmayan etiket.")
                        .showCancelButton(false)
                        .show();
                isReadable = true;
            } else {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                        .setTitleText("Uyarı")
                        .setContentText("Sayılamayan etiket değişim işlemi tamamlanacaktır. Bu işlemi onaylıyor musunuz ?")
                        .setContentTextSize(20)
                        .setConfirmText("EVET")
                        .setCancelText("HAYIR")
                        .showCancelButton(true)
                        .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialogG sDialog) {
                                sDialog.dismissWithAnimation();
                                request_uruntag v_Gelen = new request_uruntag();
                                v_Gelen.set_zaktif_alt_tesis(_ayaraktifalttesis);
                                v_Gelen.set_zaktif_tesis(_ayaraktiftesis);
                                v_Gelen.set_zkullaniciadi(_zkullaniciadi);
                                v_Gelen.set_zsifre(_zsifre);
                                v_Gelen.set_zsunucu_ip_adresi(_ayarsunucuip);
                                v_Gelen.set_zsurum(_sbtVerisyon);
                                v_Gelen.setAktif_kullanici(_ayaraktifkullanici);
                                v_Gelen.setAktif_sunucu(_ayaraktifsunucu);

                                v_Gelen.setEtiket(tag);

                                Genel.showProgressDialog(getContext());
                                Boolean sonuc = persos.fn_sayilamayan_etiket_onayi_al(v_Gelen);
                                Genel.dismissProgressDialog();

                                if (sonuc) {
                                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.SUCCESS_TYPE)
                                            .setTitleText("Onay Mesaji")
                                            .setContentText("İŞLEM BAŞARIYLA YAPILDI")
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
                                } else {
                                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                                            .setTitleText("BAĞLANTI PROBLEMİ")
                                            .setContentTextSize(25)
                                            .setContentText("ÜRÜN BİLGİSİ ALINAMADI. DAHA SONRA TEKRAR DENEYİNİZ. \r\n Etiket verisine ulaşılamadı.")
                                            .showCancelButton(false)
                                            .show();
                                }
                                isReadable = true;
                                return;
                            }
                        })
                        .setCancelClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialogG sDialog) {
                                sDialog.dismissWithAnimation();
                                isReadable = true;
                                return;
                            }
                        })
                        .show();
            }
            //todo Burada vindows tarafında islemdurumu kontrolu var ama hic 1 olarak set edilmiyor. Bir de shrink_onay çağırmış. kopyaladıkları şey kalmış diye düşünüyorum bu sebepten islemdurumu eklenmedi.

        }
        catch (Exception ex)
        {
            Genel.printStackTrace(ex,getContext());
            new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                    .setTitleText("HATA")
                    .setContentTextSize(25)
                    .setContentText("Onay işlemi yapılırken hata oluştu..")
                    .showCancelButton(false)
                    .show();
            isReadable = true;
            return;
        }
    }

    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_depolar_arasi_transfer_menu_panel fragmentyeni = new frg_depolar_arasi_transfer_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_depolar_arasi_transfer_menu_panel").addToBackStack(null);
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
