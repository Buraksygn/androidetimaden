package com.etimaden.manipulasyon.palet_duzenleme_islemleri;

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
import com.etimaden.manipulasyon.ellecleme.frg_ellecleme_menu_panel;
import com.etimaden.manipulasyon.frg_manipulasyon_menu_panel;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.Urun_tag;
import com.etimaden.request.request_secEtiket;
import com.etimaden.request.request_uruntag;
import com.etimaden.request.request_uruntag_string;
import com.etimaden.ugr_demo.R;

public class frg_palet_dagitma_islemi extends Fragment {

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

    public frg_palet_dagitma_islemi() {
        // Required empty public constructor
    }

    public static frg_palet_dagitma_islemi newInstance()
    {
        return new frg_palet_dagitma_islemi();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_palet_dagitma_islemi, container, false);
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

            etiketDegerlendir(tag);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        isReadable = true;

    }

    private void etiketDegerlendir(final Urun_tag tag)
    {
        try
        {

            if(tag == null)
            {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("BAĞLANTI HATASI")
                        .setContentTextSize(25)
                        .setContentText("Etiket bilgisine ulaşılamadı. \r\n Bağlantı problemi.")
                        .showCancelButton(false)
                        .show();
            }
            else if (tag.islem_durumu.equals("1") && (tag.etiket_turu.equals("2") || tag.etiket_turu.equals("1")))
            {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                        .setTitleText("PALET DAĞITMA")
                        .setContentText("SERİNO : " + tag.kod + "\r\nLOTNO : " + tag.lotno + "\r\nÜRÜN : " + tag.urun_adi + "\r\nPALET DAĞITMA İŞLEMİNE DEVAM ETMEK İSTİYOR MUSUNUZ ?")
                        .setContentTextSize(20)
                        .setConfirmText("EVET")
                        .setCancelText("HAYIR")
                        .showCancelButton(true)
                        .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialogG sDialog) {
                                sDialog.dismissWithAnimation();

                                request_uruntag v_Gelen=new request_uruntag();
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
                                Boolean islem_res = persos.fn_palet_dagit(v_Gelen);
                                Genel.dismissProgressDialog();

                                if (islem_res)
                                {
                                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.SUCCESS_TYPE)
                                            .setTitleText("İŞLEM ONAYI")
                                            .setContentText("PALET DAĞITMA İŞLEM KAYDI OLUŞTURULDU")
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
                                            .setTitleText("BAĞLANTI PROBLEMİ")
                                            .setContentTextSize(25)
                                            .setContentText("İŞLEM YAPILAMADI. DAHA SONRA TEKRAR DENEYİNİZ. \r\n İŞLEM KAYDI TAMAMLANAMADI.")
                                            .showCancelButton(false)
                                            .show();
                                }

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
                        .setTitleText("YANLIŞ ÜRÜN SEÇİMİ")
                        .setContentTextSize(25)
                        .setContentText("Bu menüden sadece üretilmiş ve 25/50 palet dağıtma işlemi yapabilirsiniz. \r\n İşleme uygun olmayan etiket.")
                        .showCancelButton(false)
                        .show();
            }

        }
        catch (Exception ex) {
            Genel.printStackTrace(ex,getContext());
        }

    }

    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_manipulasyon_menu_panel fragmentyeni = new frg_manipulasyon_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_manipulasyon_menu_panel").addToBackStack(null);
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
