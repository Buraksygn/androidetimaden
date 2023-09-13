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
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cResponseResult.Sevkiyat_isemri;
import com.etimaden.genel.Genel;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.Arac;
import com.etimaden.persosclass.DEPOTag;
import com.etimaden.persosclass.Zayi;
import com.etimaden.request.request_sevkiyat_isemri;
import com.etimaden.request.request_sevkiyat_isemri_depo;
import com.etimaden.request.requestsecDepoTanimlari;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class frg_depo_secimi_transfer_indirme extends Fragment {

    SweetAlertDialog pDialog;
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


    Button _btnDepoOnayla;
    ListView _aktif_is_emirleri_list;
    Button _btngeri;

    ArrayList<DEPOTag> depo_listesi;
    DEPOTag _Secili = null;
    Sevkiyat_isemri aktif_sevk_isemri = null;

    private apmblDepoListesi adapter;

    public frg_depo_secimi_transfer_indirme() {
        // Required empty public constructor
    }

    public static frg_depo_secimi_transfer_indirme newInstance()
    {
        return new frg_depo_secimi_transfer_indirme();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.frg_depo_secimi_transfer_indirme, container, false);
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
        persos = new Persos(_OnlineUrl);
    }

    public void fn_senddata(Sevkiyat_isemri aktif_sevk_isemri)
    {
        this.aktif_sevk_isemri = aktif_sevk_isemri;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        new VeriTabani(getContext()).fn_EpcTemizle();

        _myIslem = new VeriTabani(getContext());


        _btnDepoOnayla = (Button)getView().findViewById(R.id.btnDepoOnayla);
        _btnDepoOnayla.playSoundEffect(0);
        _btnDepoOnayla.setOnClickListener(new fn_btnDepoOnayla());

        _btngeri = (Button)getView().findViewById(R.id.btncikis);
        _btngeri.playSoundEffect(0);
        _btngeri.setOnClickListener(new fn_Geri());

        _aktif_is_emirleri_list = (ListView) getView().findViewById(R.id.aktif_is_emirleri_list);

        _aktif_is_emirleri_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _Secili = depo_listesi.get(position);
            }
        });

        fn_AyarlariYukle();
        depo_listesi= new ArrayList<DEPOTag>();
        depoDegerlendir();
    }

    private void depoDegerlendir()
    {
        try
        {
            requestsecDepoTanimlari _Param=new requestsecDepoTanimlari();

            //region Sabit değerleri yükle
            _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
            _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
            _Param.set_zaktif_tesis(_ayaraktiftesis);
            _Param.set_zsurum(_sbtVerisyon);
            _Param.set_zkullaniciadi(_zkullaniciadi);
            _Param.set_zsifre(_zsifre);
            _Param.setAktif_sunucu(_ayaraktifsunucu);
            _Param.setAktif_kullanici(_ayaraktifkullanici);

            _Param.setDepo_turu("0");
            _Param.setIsletme(aktif_sevk_isemri.hedef_isletme_kodu);
            _Param.setDepo_silo_secimi("");
            _Param.setDepo_silo_secimi_kontrol(false);

            Genel.showProgressDialog(getContext());
            List<DEPOTag> result = persos.fn_secDepoTanimlari(_Param);
            depo_listesi=new ArrayList<>();
            for(DEPOTag depo : result){
                if(depo.getAlt_isletme_kod().equals(_ayaraktifalttesis)){
                    depo_listesi.add(depo);
                }
            }
            Genel.dismissProgressDialog();

            updateListviewItem();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("BAĞLANTI HATASI")
                    .setContentTextSize(25)
                    .setContentText("Depo bilgisi alınamadı.")
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
                _aktif_is_emirleri_list.setAdapter(adapter);
            }

           adapter=new apmblDepoListesi(depo_listesi,getContext());
            _aktif_is_emirleri_list.setAdapter(adapter);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_arac_onayla_indirme fragmentyeni = new frg_arac_onayla_indirme();
            fragmentyeni.fn_senddata(aktif_sevk_isemri);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_arac_onayla_indirme").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }


    private class fn_btnDepoOnayla implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            try
            {
                if (_Secili!=null)
                {
                    DEPOTag secilen_depo = _Secili;
                    try
                    {
                        request_sevkiyat_isemri_depo _Param= new request_sevkiyat_isemri_depo();
                        _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
                        _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
                        _Param.set_zaktif_tesis(_ayaraktiftesis);
                        _Param.set_zsurum(_sbtVerisyon);
                        _Param.set_zkullaniciadi(_zkullaniciadi);
                        _Param.set_zsifre(_zsifre);
                        _Param.setAktif_sunucu(_ayaraktifsunucu);
                        _Param.setAktif_kullanici(_ayaraktifkullanici);

                        _Param.set_sevk(aktif_sevk_isemri);
                        _Param.set_depo(secilen_depo);

                        Boolean result=false;

                        if (aktif_sevk_isemri.islem_id.equals(""))
                        {
                            Genel.showProgressDialog(getContext());
                            result = persos.fn_ekleAracIndirmeAktivasyonu(_Param);
                            Genel.dismissProgressDialog();
                            if(result==true) {
                                new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("ONAY")
                                        .setContentText("İşlem onaylandı.")
                                        .setContentTextSize(20)
                                        .setConfirmText("TAMAM")
                                        .showCancelButton(false)
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                sDialog.dismissWithAnimation();

                                                frg_sevkiyat_menu_panel fragmentyeni = new frg_sevkiyat_menu_panel();
                                                FragmentManager fragmentManager = getFragmentManager();
                                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_sevkiyat_menu_panel").addToBackStack(null);
                                                fragmentTransaction.commit();

                                                return;
                                            }
                                        }).show();
                                return;
                            } else {
                                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("DEPO SEÇİMİ UYARISI")
                                        .setContentTextSize(25)
                                        .setContentText("DEPO SEÇİM İŞLEMİ YAPILMADI.")
                                        .showCancelButton(false)
                                        .show();
                            }
                        }
                        else if (!aktif_sevk_isemri.islem_id.equals(""))
                        {
                            Genel.showProgressDialog(getContext());
                            result = persos.fn_updateAracIndirmeAktivasyonu(_Param);
                            Genel.dismissProgressDialog();
                            if(result==true){
                                new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("ONAY")
                                        .setContentText("İşlem onaylandı.")
                                        .setContentTextSize(20)
                                        .setConfirmText("TAMAM")
                                        .showCancelButton(false)
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                sDialog.dismissWithAnimation();

                                                frg_sevkiyat_menu_panel fragmentyeni = new frg_sevkiyat_menu_panel();
                                                FragmentManager fragmentManager = getFragmentManager();
                                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_sevkiyat_menu_panel").addToBackStack(null);
                                                fragmentTransaction.commit();

                                                return;
                                            }
                                        })
                                        .show();
                                return;
                            } else {
                                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("DEPO SEÇİMİ UYARISI")
                                        .setContentTextSize(25)
                                        .setContentText("DEPO SEÇİM İŞLEMİ YAPILMADI.")
                                        .showCancelButton(false)
                                        .show();
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("DEPO SEÇİMİ UYARISI")
                                .setContentTextSize(25)
                                .setContentText("DEPO SEÇİM İŞLEMİ YAPILMADI.")
                                .showCancelButton(false)
                                .show();
                    }
                }
                else
                {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("DEPO SEÇİMİ UYARISI")
                            .setContentTextSize(25)
                            .setContentText("DEPO SEÇİM İŞLEMİ YAPILMADI.")
                            .showCancelButton(false)
                            .show();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("DEPO SEÇİMİ UYARISI")
                        .setContentTextSize(25)
                        .setContentText("DEPO SEÇİM İŞLEMİ YAPILMADI.")
                        .showCancelButton(false)
                        .show();
            }

        }
    }
}
