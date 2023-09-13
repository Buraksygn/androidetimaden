package com.etimaden.SevkiyatIslemleri;

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
import com.etimaden.cResponseResult.Sevkiyat_isemri;
import com.etimaden.genel.Genel;
import com.etimaden.persos.Persos;
import com.etimaden.request.request_sevkiyat_isemri;
import com.etimaden.request.request_sevkiyat_zayi_arac;
import com.etimaden.ugr_demo.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class frg_konteyner_onayla extends Fragment {


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

    Button btnIsEmriIptal;
    Button btnAnaMenu;
    Button _btngeri;

    Sevkiyat_isemri aktif_sevk_isemri;

    public frg_konteyner_onayla() {
        // Required empty public constructor
    }
    public static frg_konteyner_onayla newInstance() {

        return new frg_konteyner_onayla();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_konteyner_onayla, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    private void fn_AyarlariYukle()
    {
        ((GirisSayfasi) getActivity()).fn_ModRFID();


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
        persos = new Persos(_OnlineUrl);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((GirisSayfasi)getActivity()).fn_ModRFID();

        _myIslem = new VeriTabani(getContext());

        fn_AyarlariYukle();

        new VeriTabani(getContext()).fn_EpcTemizle();

        ((GirisSayfasi) getActivity()).fn_ListeTemizle();
        ((GirisSayfasi) getActivity()).fn_GucAyarla(248);

        btnIsEmriIptal = (Button) getView().findViewById(R.id.btn_01);
        btnIsEmriIptal.playSoundEffect(0);
        btnIsEmriIptal.setOnClickListener(new fn_btnIsEmriIptal());

        btnAnaMenu = (Button) getView().findViewById(R.id.btn_02);
        btnAnaMenu.playSoundEffect(0);
        btnAnaMenu.setOnClickListener(new fn_btnAnaMenu());

        _btngeri = (Button) getView().findViewById(R.id.btngeri);
        _btngeri.playSoundEffect(0);
        _btngeri.setOnClickListener(new fn_Geri());



    }

    public void fn_senddata(Sevkiyat_isemri v_aktif_sevk_isemri)
    {
        aktif_sevk_isemri=v_aktif_sevk_isemri;
    }

    private class fn_btnIsEmriIptal implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            try
            {
                if (aktif_sevk_isemri.islem_id == "")
                {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("KONTEYNER TANIMI EKSİK")
                            .setContentTextSize(25)
                            .setContentText("SEVKİYAT DETAYLARINI TAMAMLAMADAN YAPMADAN BU İŞLEMİ GERÇEKLEŞTİREMEZSİNİZ.")
                            .showCancelButton(false)
                            .show();
                }
                else if (aktif_sevk_isemri.yapilan_adet.equals("0"))
                {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("KONTEYNER İPTAL")
                            .setContentText("KONTEYNER YÜKLEME İŞLEMİNİ İPTAL ETMEK İSTEDİĞİNİZDEN EMİN MİSİNİZ?")
                            .setContentTextSize(20)
                            .setConfirmText("EVET")
                            .setCancelText("HAYIR")
                            .showCancelButton(true)
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    request_sevkiyat_isemri _Param= new request_sevkiyat_isemri();
                                    _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
                                    _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
                                    _Param.set_zaktif_tesis(_ayaraktiftesis);
                                    _Param.set_zsurum(_sbtVerisyon);
                                    _Param.set_zkullaniciadi(_zkullaniciadi);
                                    _Param.set_zsifre(_zsifre);
                                    _Param.setAktif_sunucu(_ayaraktifsunucu);
                                    _Param.setAktif_kullanici(_ayaraktifkullanici);

                                    _Param.set_sevkiyat_ismeri(aktif_sevk_isemri);

                                    Genel.showProgressDialog(getContext());
                                    Boolean islem_sonucu = persos.fn_iptal_yerde_konteyner(_Param);
                                    Genel.dismissProgressDialog();
                                    if (islem_sonucu)
                                    {
                                        new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                                .setTitleText("İşlem Onayı")
                                                .setContentText("İşlem başarı ile tamamlanmıştır.")
                                                .setContentTextSize(20)
                                                .setConfirmText("TAMAM")
                                                .showCancelButton(false)
                                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sDialog) {
                                                        sDialog.dismissWithAnimation();
                                                        return;
                                                    }
                                                }).show();
                                        frg_sevkiyat_menu_panel fragmentyeni = new frg_sevkiyat_menu_panel();
                                        FragmentManager fragmentManager = getFragmentManager();
                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                        fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_sevkiyat_menu_panel").addToBackStack(null);
                                        fragmentTransaction.commit();

                                    }
                                    else
                                    {
                                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                                .setTitleText("İşlem Başarısız")
                                                .setContentTextSize(25)
                                                .setContentText("Kayıt yapılamadı. \r\n Veritabanı hatası")
                                                .showCancelButton(false)
                                                .show();

                                    }
                                    return;
                                }
                            })
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    return;
                                }
                            })
                            .show();
                }
                else
                {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("KONTEYNER YÜKLEME UYARISI")
                            .setContentTextSize(25)
                            .setContentText("KONTEYNER İÇERİSİNDE BULUNAN ÜRÜNLERİ BOŞALTMA YAPMADAN BU İŞLEMİ GERÇEKLEŞTİREMEZSİNİZ.")
                            .showCancelButton(false)
                            .show();
                }

            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }

    private class fn_btnAnaMenu implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            frg_sevkiyat_menu_panel fragmentyeni = new frg_sevkiyat_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_sevkiyat_menu_panel").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            frg_konteyner_bulundu fragmentyeni = new frg_konteyner_bulundu();
            fragmentyeni.fn_senddata(aktif_sevk_isemri);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_konteyner_bulundu").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}
