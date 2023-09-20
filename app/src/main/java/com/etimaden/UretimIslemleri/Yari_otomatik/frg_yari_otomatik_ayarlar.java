package com.etimaden.UretimIslemleri.Yari_otomatik;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.GirisSayfasi;
import com.etimaden.UretimIslemleri.frg_uretim_menu_panel;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.genel.Genel;
import com.etimaden.genel.SweetAlertDialogG;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.Urun_tag;
import com.etimaden.request.request_bos;
import com.etimaden.request.request_secEtiket;
import com.etimaden.request.request_string;
import com.etimaden.request.request_uretim_iptali;
import com.etimaden.ugr_demo.R;


import static com.etimaden.cSabitDegerler._ipAdresi3G;
import static com.etimaden.cSabitDegerler._sbtVerisyon;
import static com.etimaden.cSabitDegerler._zkullaniciadi;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;

public class frg_yari_otomatik_ayarlar extends Fragment {

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


    EditText _editTextPaletDizim;
    Button _btnKaydet;
    TextView _txt_palet_sirano;
    TextView _txt_paket_sirano;
    Button _btnYenile;
    Button _btnSifirla;
    Button _btngeri;


    public frg_yari_otomatik_ayarlar() {
        // Required empty public constructor
    }

    public static frg_yari_otomatik_ayarlar newInstance()
    {
        return new frg_yari_otomatik_ayarlar();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_yari_otomatik_ayarlar, container, false);
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

        _editTextPaletDizim = (EditText)getView().findViewById(R.id.editTextPaletDizim);

        _btnKaydet = (Button)getView().findViewById(R.id.btnKaydet);
        _btnKaydet.playSoundEffect(0);
        _btnKaydet.setOnClickListener(new fn_btnKaydet());

        _txt_palet_sirano = (TextView)getView().findViewById(R.id.txt_palet_sirano);

        _txt_paket_sirano = (TextView)getView().findViewById(R.id.txt_paket_sirano);

        _btnYenile = (Button)getView().findViewById(R.id.btnYenile);
        _btnYenile.playSoundEffect(0);
        _btnYenile.setOnClickListener(new fn_btnYenile());

        _btnSifirla = (Button)getView().findViewById(R.id.btnSifirla);
        _btnSifirla.playSoundEffect(0);
        _btnSifirla.setOnClickListener(new fn_btnSifirla());

        _btngeri = (Button)getView().findViewById(R.id.btngeri);
        _btngeri.playSoundEffect(0);
        _btngeri.setOnClickListener(new fn_Geri());


        fn_AyarlariYukle();

        request_bos _Param= new request_bos();
        _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
        _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
        _Param.set_zaktif_tesis(_ayaraktiftesis);
        _Param.set_zsurum(_sbtVerisyon);
        _Param.set_zkullaniciadi(_zkullaniciadi);
        _Param.set_zsifre(_zsifre);
        _Param.setAktif_sunucu(_ayaraktifsunucu);
        _Param.setAktif_kullanici(_ayaraktifkullanici);

        String paletDizimText = persos.fn_sec_palet_dizim(_Param);
        paletDizimText = paletDizimText==null ? "" : paletDizimText;
        _editTextPaletDizim.setText(paletDizimText);
    }


    private class fn_btnKaydet implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            try
            {

                int plt_dizim_int = Integer.parseInt(_editTextPaletDizim.getText().toString().trim());

                request_string _Param= new request_string();
                _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
                _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
                _Param.set_zaktif_tesis(_ayaraktiftesis);
                _Param.set_zsurum(_sbtVerisyon);
                _Param.set_zkullaniciadi(_zkullaniciadi);
                _Param.set_zsifre(_zsifre);
                _Param.setAktif_sunucu(_ayaraktifsunucu);
                _Param.setAktif_kullanici(_ayaraktifkullanici);
                _Param.set_value(_editTextPaletDizim.getText().toString().trim());

                Boolean res = persos.fn_guncelle_palet_dizim(_Param);

                if (res)
                {
                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                            .setTitleText("Onay")
                            .setContentText("Ayarlar kaydedildi.")
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

                    return;

                }
                else{
                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                            .setTitleText("Hata")
                            .setContentText("Kayıt yapılamadı.Lütfen bağlantı ayarlarını kontrol ediniz.")
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

                    return;
                }

            }
            catch (Exception ex)
            {
                Genel.printStackTrace(ex,getContext());
            }
        }
    }

    public void yenile()
    {

            try
            {
                Genel.showProgressDialog(getContext());
                request_bos _Param= new request_bos();
                _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
                _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
                _Param.set_zaktif_tesis(_ayaraktiftesis);
                _Param.set_zsurum(_sbtVerisyon);
                _Param.set_zkullaniciadi(_zkullaniciadi);
                _Param.set_zsifre(_zsifre);
                _Param.setAktif_sunucu(_ayaraktifsunucu);
                _Param.setAktif_kullanici(_ayaraktifkullanici);
                String palet_sirano =persos.fn_sec_palet_sirano_acilis(_Param);

                if(palet_sirano!=null){
                    request_string _Param1= new request_string();
                    _Param1.set_zsunucu_ip_adresi(_ayarsunucuip);
                    _Param1.set_zaktif_alt_tesis(_ayaraktifalttesis);
                    _Param1.set_zaktif_tesis(_ayaraktiftesis);
                    _Param1.set_zsurum(_sbtVerisyon);
                    _Param1.set_zkullaniciadi(_zkullaniciadi);
                    _Param1.set_zsifre(_zsifre);
                    _Param1.setAktif_sunucu(_ayaraktifsunucu);
                    _Param1.setAktif_kullanici(_ayaraktifkullanici);
                    _Param1.set_value(palet_sirano);

                    String paket_sirano =persos.fn_sec_palet_torba_sayisi(_Param1);

                    paket_sirano=paket_sirano==null?"":paket_sirano;

                    _txt_palet_sirano.setText(palet_sirano);
                    _txt_paket_sirano.setText(paket_sirano);
                }
                else{
                    _txt_palet_sirano.setText("");
                    _txt_paket_sirano.setText("");
                }
                Genel.dismissProgressDialog();

            }
            catch (Exception ex)
            {
                Genel.dismissProgressDialog();
                Genel.printStackTrace(ex,getContext());
            }


    }


    public void sıfırla()
    {

            try
            {
                Genel.showProgressDialog(getContext());
                request_bos _Param= new request_bos();
                _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
                _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
                _Param.set_zaktif_tesis(_ayaraktiftesis);
                _Param.set_zsurum(_sbtVerisyon);
                _Param.set_zkullaniciadi(_zkullaniciadi);
                _Param.set_zsifre(_zsifre);
                _Param.setAktif_sunucu(_ayaraktifsunucu);
                _Param.setAktif_kullanici(_ayaraktifkullanici);
                String palet_sirano =persos.fn_sec_palet_sirano(_Param);

                if(palet_sirano!=null){
                    request_string _Param1= new request_string();
                    _Param1.set_zsunucu_ip_adresi(_ayarsunucuip);
                    _Param1.set_zaktif_alt_tesis(_ayaraktifalttesis);
                    _Param1.set_zaktif_tesis(_ayaraktiftesis);
                    _Param1.set_zsurum(_sbtVerisyon);
                    _Param1.set_zkullaniciadi(_zkullaniciadi);
                    _Param1.set_zsifre(_zsifre);
                    _Param1.setAktif_sunucu(_ayaraktifsunucu);
                    _Param1.setAktif_kullanici(_ayaraktifkullanici);
                    _Param1.set_value(palet_sirano);

                    String paket_sirano =persos.fn_sec_palet_torba_sayisi(_Param1);

                    paket_sirano=paket_sirano==null?"":paket_sirano;

                    _txt_palet_sirano.setText(palet_sirano);
                    _txt_paket_sirano.setText(paket_sirano);
                }
                else{
                    _txt_palet_sirano.setText("");
                    _txt_paket_sirano.setText("");
                }
                Genel.dismissProgressDialog();

            }
            catch (Exception ex)
            {
                Genel.dismissProgressDialog();
                Genel.printStackTrace(ex,getContext());
            }

    }

    private class fn_btnYenile implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            yenile();

        }
    }

    private class fn_btnSifirla implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            try
            {

                new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                        .setTitleText("UYARI")
                        .setContentText("Bu işlem sonnucunda sistem sıfırlanacaktır. Bu işleme devam etmek istiyor musunuz ?" )
                        .setContentTextSize(20)
                        .setConfirmText("EVET")
                        .setCancelText("HAYIR")
                        .showCancelButton(true)
                        .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialogG sDialog) {
                                sıfırla();
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();




            }
            catch (Exception ex)
            {
                Genel.printStackTrace(ex,getContext());
            }

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

}
