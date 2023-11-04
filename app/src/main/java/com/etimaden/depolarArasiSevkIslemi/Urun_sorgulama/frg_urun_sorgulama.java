package com.etimaden.depolarArasiSevkIslemi.Urun_sorgulama;

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
import com.etimaden.depolarArasiSevkIslemi.frg_depolar_arasi_transfer_menu_panel;
import com.etimaden.genel.Genel;
import com.etimaden.genel.SweetAlertDialogG;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.Urun_tag;
import com.etimaden.request.request_secEtiket;
import com.etimaden.request.request_string;
import com.etimaden.request.request_uruntag;
import com.etimaden.ugr_demo.R;

public class frg_urun_sorgulama extends Fragment {

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

    public frg_urun_sorgulama() {
        // Required empty public constructor
    }

    public static frg_urun_sorgulama newInstance()
    {
        return new frg_urun_sorgulama();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_urun_sorgulama, container, false);
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
        _txtYazi.setText("BASILAN YENİ LOT ETİKETİNİ OKUTUNUZ...");

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

            etiketDetayAc(barkod);

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

            etiketDetayAc(rfid);

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

    private void etiketDetayAc(final String etiket)
    {
        try
        {
            request_secEtiket v_Gelen=new request_secEtiket();
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
            Urun_tag tag = persos.fn_secEtiket(v_Gelen);
            Genel.dismissProgressDialog();

            String durum = "";

            if (tag == null)
            {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("HATALI ÜRÜN")
                        .setContentTextSize(25)
                        .setContentText("ARANILAN ÜRÜN KAYDI BULUNAMADI")
                        .showCancelButton(false)
                        .show();
                isReadable = true;
                return;
            }
            else
            {
                if (tag.islem_durumu.equals("3"))
                {
                    durum = "Satılmış Ürün";
                }
                else if (tag.islem_durumu.equals("7"))
                {
                    durum = "Sevk Edilmiş Ürün";
                }
                else if (tag.islem_durumu.equals("2"))
                {
                    durum = "Sevk Edilemez Ürün";
                }
                else if (tag.islem_durumu.equals("4"))
                {
                    durum = "Etiket Değişimi Bekliyor";
                }
                else if (tag.islem_durumu.equals("200"))
                {
                    durum = "TSE tarafından ayrılmış ürün";
                }
                else if (tag.islem_durumu.equals("201"))
                {
                    durum = "İşletme tarafından ürün";
                }
                else if (tag.islem_durumu.equals("350"))
                {
                    durum = "Torba tipi değişimi bekleyen ürün";
                }
                else if (tag.islem_durumu.equals("351") || tag.MAN_TİP.equals("360"))
                {
                    durum = "Kirli torba değişimi bekleyen ürün";
                }
                else if (tag.islem_durumu.equals("352") || tag.MAN_TİP.equals("370"))
                {
                    durum = "Geri besleme bekleyen ürün";
                }
                else if (tag.islem_durumu.equals("353") )
                {
                    durum = "Palet düzenleme bekleyen ürün";
                }
                else if (tag.islem_durumu.equals("354") || tag.MAN_TİP.equals("390"))
                {
                    durum = "Elleçleme bekleyen ürün";
                }
                else if (tag.islem_durumu.equals("0"))
                {
                    durum = "Üretim Bekleyen Ürün";
                }
                else if (tag.islem_durumu.equals("8"))
                {
                    durum = "Araca Yüklenmiş ürün";
                }
                else if (tag.aciklama.trim().equals(""))
                {
                    durum = "Üretim Bekleyen Ürün";

                }
                else if (tag.islem_durumu.equals("1"))
                {
                    durum = "Üretimi Tamamlanan Ürün";

                }
                    /*if (tag.kilitli.Equals("True"))
                    {
                        durum = durum + " / " + " Manipülasyon için ayrılmış blokeli ürün ";
                    }*/

                String str = "";
                str = " ÜRÜN SERİ NO : " +      tag.kod +
                        "\r\n LOT NO : " + tag.lotno +
                        "\r\n ÜRÜN ADI : " + tag.urun_adi +
                        "\r\n PALET KOD : " + tag.palet_kod +
                        "\r\n VARDİYA : " + tag.vardiya +
                        "\r\n TOPLAM AĞIRLIĞI : " + tag.palet_agirligi +
                        "\r\n PAKET AĞIRLIĞI : " + tag.torba_agirlik +
                        "\r\n İŞLETME : " + tag.isletme_adi +
                        "\r\n SON DEPO : " + tag.son_depo_adi +
                        "\r\n DURUM : " + durum +
                        "\r\n VERSIYON : " + _ayarversiyon;

                new SweetAlertDialogG(getContext(), SweetAlertDialogG.SUCCESS_TYPE)
                        .setTitleText("ÜRÜN DETAYI")
                        .setContentText(str)
                        .setContentTextSize(20)
                        .setConfirmText("TAMAM")
                        .showCancelButton(false)
                        .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialogG sDialog) {
                                sDialog.dismissWithAnimation();
                                isReadable=true;
                                return;
                            }
                        }).show();



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
