package com.etimaden.SevkiyatIslemleri.Arac_aktivayon_islemleri;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.SevkiyatIslemleri.Zayiat_islemleri.frg_zayi_arac_bulundu_indirme;
import com.etimaden.SevkiyatIslemleri.Zayiat_islemleri.frg_zayi_menu_panel;
import com.etimaden.SevkiyatIslemleri.frg_sevkiyat_menu_panel;
import com.etimaden.adapter.apmblDepoListesi;
import com.etimaden.adapter.apmblSevkiyatBekleyenAracListesi;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cResponseResult.Sevkiyat_isemri;
import com.etimaden.genel.Genel;
import com.etimaden.genel.SweetAlertDialogG;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.Arac;
import com.etimaden.persosclass.DEPOTag;
import com.etimaden.persosclass.Zayi;
import com.etimaden.persosclass.cBekleyen_Arac_Listesi;
import com.etimaden.request.request_bos;
import com.etimaden.request.request_string;
import com.etimaden.request.requestsecDepoTanimlari;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;
import java.util.List;


public class frg_bekleyen_arac_listesi extends Fragment {

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


    Button _btnSeciliIseBasla;
    ListView _arac_list;
    Button _btnGeri;
    Button _btnYenile;

    ArrayList<cBekleyen_Arac_Listesi> aracListesi;
    cBekleyen_Arac_Listesi _Secili = null;

    private apmblSevkiyatBekleyenAracListesi adapter;

    public frg_bekleyen_arac_listesi() {
        // Required empty public constructor
    }

    public static frg_bekleyen_arac_listesi newInstance()
    {
        return new frg_bekleyen_arac_listesi();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.frg_bekleyen_arac_listesi, container, false);
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

        //Button _btnSeciliIseBasla;
        //ListView _arac_list;
        //Button _btnGeri;
        //Button _btnYenile;

        _btnSeciliIseBasla = (Button)getView().findViewById(R.id.btnSeciliIseBasla);
        _btnSeciliIseBasla.playSoundEffect(0);
        _btnSeciliIseBasla.setOnClickListener(new fn_btnSeciliIseBasla());

        _btnYenile = (Button)getView().findViewById(R.id.btnYenile);
        _btnYenile.playSoundEffect(0);
        _btnYenile.setOnClickListener(new fn_btnYenile());

        _btnGeri = (Button)getView().findViewById(R.id.btnGeri);
        _btnGeri.playSoundEffect(0);
        _btnGeri.setOnClickListener(new fn_btnGeri());

        _arac_list = (ListView) getView().findViewById(R.id.arac_list);

        _arac_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _Secili = aracListesi.get(position);
            }
        });

        fn_AyarlariYukle();
        aracListesi= new ArrayList<cBekleyen_Arac_Listesi>();
        fnAracListele();
    }

    private void fnAracListele()
    {
        try
        {
            request_bos _Param=new request_bos();
            _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
            _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
            _Param.set_zaktif_tesis(_ayaraktiftesis);
            _Param.set_zsurum(_sbtVerisyon);
            _Param.set_zkullaniciadi(_zkullaniciadi);
            _Param.set_zsifre(_zsifre);
            _Param.setAktif_sunucu(_ayaraktifsunucu);
            _Param.setAktif_kullanici(_ayaraktifkullanici);


            Genel.showProgressDialog(getContext());
            List<cBekleyen_Arac_Listesi> result = persos.fn_BekleyenAracListesi(_Param);
            //todo skolon1 servisten fn_PlakaCevir den geçerek gelse iyi olur.
            //Program.persos.fn_PlakaCevir(l.skolon1)
            if(result!=null){
                aracListesi=new ArrayList<>(result);
            }


            Genel.dismissProgressDialog();

            updateListviewItem();

        }
        catch (Exception ex)
        {
            Genel.printStackTrace(ex,getContext());
            new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                    .setTitleText("BAL-0E1 ")
                    .setContentTextSize(25)
                    .setContentText(ex.toString())
                    .showCancelButton(false)
                    .show();
        }
    }

    private void updateListviewItem()
    {
        try
        {
            if (adapter != null) {
                adapter.clear();
                _arac_list.setAdapter(adapter);
            }

           adapter=new apmblSevkiyatBekleyenAracListesi(aracListesi,getContext());
            _arac_list.setAdapter(adapter);

        }
        catch (Exception ex)
        {
            Genel.printStackTrace(ex,getContext());
        }
    }

    private class fn_btnSeciliIseBasla implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            try
            {
                if(_Secili!=null) {

                    cBekleyen_Arac_Listesi _cTemp = _Secili;

                    final String _sPlaka = _cTemp.skolon1;
                    final String _sRfid = _cTemp.skolon6;

                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                            .setTitleText("İŞLEM ONAYI")
                            .setContentText(_sPlaka+" PLAKALI ARAÇ İÇİN YAPILAN İŞLEMİ OANAYLIYOR MUSUNUZ?")
                            .setContentTextSize(20)
                            .setConfirmText("EVET")
                            .setCancelText("HAYIR")
                            .showCancelButton(true)
                            .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialogG sDialog) {
                                    sDialog.dismissWithAnimation();

                                    try
                                    {
                                        if (!_sRfid.startsWith("737767302"))
                                        {
                                            request_string _Param=new request_string();
                                            _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
                                            _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
                                            _Param.set_zaktif_tesis(_ayaraktiftesis);
                                            _Param.set_zsurum(_sbtVerisyon);
                                            _Param.set_zkullaniciadi(_zkullaniciadi);
                                            _Param.set_zsifre(_zsifre);
                                            _Param.setAktif_sunucu(_ayaraktifsunucu);
                                            _Param.setAktif_kullanici(_ayaraktifkullanici);

                                            _Param.set_value(_sRfid);

                                            Genel.showProgressDialog(getContext());
                                            List<Sevkiyat_isemri> result = persos.fn_secKantarIsemriListesi(_Param);
                                            //todo skolon1 servisten fn_PlakaCevir den geçerek gelse iyi olur.
                                            //Program.persos.fn_PlakaCevir(l.skolon1)
                                            ArrayList<Sevkiyat_isemri> sevk_isemri_listesi = new ArrayList<>();
                                            if(result!=null) {
                                                sevk_isemri_listesi = new ArrayList<>(result);
                                            }
                                            Genel.dismissProgressDialog();

                                            Sevkiyat_isemri aktif_sevk_isemri=null;

                                            for(Sevkiyat_isemri isemri : sevk_isemri_listesi) {
                                                if(isemri.arac_rfid.equals(_sRfid)) {
                                                    aktif_sevk_isemri=isemri;
                                                    break;
                                                }
                                            }

                                            //todo aktif_sevk_listesi null gelebiliyor. Kontrol edilmeli mi ?

                                            String[] karakterler = aktif_sevk_isemri.karakteristikler.split(",");

                                            if (karakterler[7].equals("0020"))
                                            {
                                                frg_konteyner_kamyon_esleme fragmentyeni = new frg_konteyner_kamyon_esleme();
                                                fragmentyeni.fn_senddata(aktif_sevk_isemri);
                                                FragmentManager fragmentManager = getFragmentManager();
                                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_konteyner_kamyon_esleme").addToBackStack(null);
                                                fragmentTransaction.commit();
                                            }
                                            else if (!aktif_sevk_isemri.hedef_isletme_alt_kodu.equals(_ayaraktifalttesis))
                                            {
                                                frg_arac_bulundu fragmentyeni = new frg_arac_bulundu();
                                                fragmentyeni.fn_senddata(aktif_sevk_isemri);
                                                FragmentManager fragmentManager = getFragmentManager();
                                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_arac_bulundu").addToBackStack(null);
                                                fragmentTransaction.commit();
                                            }
                                            else
                                            {

                                            }
                                        }
                                    }
                                    catch (Exception ex)
                                    {
                                        new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                                                .setTitleText("HATA")
                                                .setContentTextSize(25)
                                                .setContentText(ex.toString())
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
                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                            .setTitleText("SEÇİM")
                            .setContentText("Lütfen listeden araç seçiniz!")
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
            }
            catch (Exception ex)
            {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("HATA A-AB01")
                        .setContentTextSize(25)
                        .setContentText(ex.toString())
                        .showCancelButton(false)
                        .show();
            }
        }
    }

    private class fn_btnGeri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_sevkiyat_menu_panel fragmentyeni = new frg_sevkiyat_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_sevkiyat_menu_panel").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_btnYenile implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            fnAracListele();
        }
    }
}
