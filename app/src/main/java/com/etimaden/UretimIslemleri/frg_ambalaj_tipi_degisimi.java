package com.etimaden.UretimIslemleri;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.GirisSayfasi;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.persosclass.Urun_tag;
import com.etimaden.manipulasyon.frg_manipulasyon_panel;
import com.etimaden.persos.Persos;
import com.etimaden.request.request_ambalaj_tipi_secEtiket;
import com.etimaden.response.frg_paket_uretim_ekrani.View_ambalaj_tipi_secEtiket;
import com.etimaden.ugr_demo.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.etimaden.cSabitDegerler._ipAdresi3G;
import static com.etimaden.cSabitDegerler._sbtVerisyon;
import static com.etimaden.cSabitDegerler._zkullaniciadi;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;

public class frg_ambalaj_tipi_degisimi extends Fragment {


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


    Button _btncikis;
    Button _btn_01;

    boolean isReadable = true;
    boolean isemri_secildi = false;

    TextView _yazi;

    Urun_tag aktif_isemri = null;

    ListView _listisemirleri;

    public frg_ambalaj_tipi_degisimi() {
        // Required empty public constructor
    }
    public static frg_ambalaj_tipi_degisimi newInstance()
    {
        return new frg_ambalaj_tipi_degisimi();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_ambalaj_tipi_degisimi, container, false);
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

        _btncikis = (Button)getView().findViewById(R.id.btngeri);
        _btncikis.playSoundEffect(0);
        _btncikis.setOnClickListener(new fn_Geri());

        _btn_01= (Button)getView().findViewById(R.id.btn_01);
        _btn_01.playSoundEffect(0);
        _btn_01.setOnClickListener(new fn_btn_01());

        _yazi=(TextView)getView().findViewById(R.id.txtYazi);

        _btn_01.setVisibility(View.INVISIBLE);

        _listisemirleri=(ListView)getView().findViewById(R.id.listisemirleri);

        _listisemirleri.setVisibility(View.INVISIBLE);
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

        if (_ayarbaglantituru.equals("wifi"))
        {
            _OnlineUrl = "http://" + _ayarsunucuip + ":" + _zportWifi + "/";
        }
        else
        {
            _OnlineUrl = "http://" + _ipAdresi3G + ":" + _zport3G + "/";
        }

        persos = new Persos(_OnlineUrl);
    }



    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            frg_manipulasyon_panel fragmentyeni = new frg_manipulasyon_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_manipulasyon_panel").addToBackStack(null);
            fragmentTransaction.commit();

        }
    }

    public void fn_BarkodOkutuldu(String barkod)
    {
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

        } else
        {
            barkod = barkod.substring(barkod.length() - 24);

            if (!isReadable )
            {
                return;
            }

            isReadable = false;

            request_ambalaj_tipi_secEtiket _param = new request_ambalaj_tipi_secEtiket();

            _param.set_rfid(barkod);
            _param.set_zaktif_alt_tesis(_ayaraktifalttesis);
            _param.set_zaktif_tesis(_ayaraktiftesis);
            _param.set_zkullaniciadi(_zkullaniciadi);
            _param.set_zsifre(_zsifre);
            _param.set_zsunucu_ip_adresi(_ayarsunucuip);
            _param.set_zsurum(_sbtVerisyon);
            _param.setAktif_kullanici(_ayaraktifkullanici);
            _param.setAktif_sunucu(_ayaraktifsunucu);

            View_ambalaj_tipi_secEtiket _yanit = persos.fn_ambalaj_tipi_secEtiket(_param);

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
                Urun_tag _tag =_yanit.get_tag();

                if (!isemri_secildi && _tag != null && (_tag.uretim_isemri_tipi_alt.equals("350") || _tag.uretim_isemri_tipi_alt.equals("351")))
                {
                    isemri_degerlendir(_tag);
                }
                else if (!isemri_secildi || _tag == null || _tag.islem_durumu != "1")
                {
                 //   Cursor.Current = Cursors.Default;
                  //  Program.giveHataMesaji("İŞLEM İÇİN UYGUN OLMAYAN ÜRÜN", "Ürün yapmak istediğiniz işlem için uygun değildir.", "İşleme uygun olmayan etiket.");
                }
            }
        }
    }

    private void isemri_degerlendir(Urun_tag v_tag)
    {
        if (v_tag.uretim_dakika_onayi != "1")
        {
            String _yazi = "";
            _yazi += "PAKET TİPİ DEĞİŞİMİ ONAYI ALINAMADI. DETAYLAR KISMINDAN PAKET TİPİ DEĞİŞİMİ KURALLARINI OKUYABİLİRSİNİZ.";
            _yazi += "<br/>ÜRETİM KURALLARI";
            _yazi += "<br/>1) 3 GÜN İÇİNDE PAKET TİPİ DEĞİŞİMİ KAYDI GERÇEKLEŞMEYEN ETİKET İÇİN PAKET TİPİ DEĞİŞİMİ KAYDI OLUŞTURULAMAZ.";

            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("HATA")
                    .setContentText(_yazi)
                    .setContentTextSize(18)
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

            isemri_secildi = true;
            _btn_01.setVisibility(View.VISIBLE);
          //  aktif_is_emirleri_listview.Visible = true;
            _yazi.setVisibility(View.INVISIBLE);
            aktif_isemri = v_tag;
        }
    }

    private class fn_btn_01 implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }
}
