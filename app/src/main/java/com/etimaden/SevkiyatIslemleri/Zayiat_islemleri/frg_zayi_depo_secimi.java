package com.etimaden.SevkiyatIslemleri.Zayiat_islemleri;

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

import com.etimaden.adapter.apmblDepoListesi;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.persosclass.Arac;
import com.etimaden.genel.Genel;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.DEPOTag;
import com.etimaden.persosclass.Zayi;
import com.etimaden.request.requestsecDepoTanimlari;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class frg_zayi_depo_secimi extends Fragment {

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
    Zayi aktif_zayi = null;
    Arac arac = null;

    private apmblDepoListesi adapter;

    public frg_zayi_depo_secimi() {
        // Required empty public constructor
    }

    public static frg_zayi_depo_secimi newInstance()
    {
        return new frg_zayi_depo_secimi();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.frg_zayi_depo_secimi, container, false);
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

    public void fn_senddata(Zayi aktif_zayi,Arac arac)
    {
        this.aktif_zayi = aktif_zayi;
        this.arac=arac;
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
            _Param.setIsletme(aktif_zayi.zay_isletme);
            _Param.setDepo_silo_secimi("");
            _Param.setDepo_silo_secimi_kontrol(false);

            Genel.showProgressDialog(getContext());
            List<DEPOTag> result = persos.fn_secDepoTanimlari(_Param);
            depo_listesi=new ArrayList<>();
            if(result!=null) {
                depo_listesi = new ArrayList<>(result);
            }
            Genel.dismissProgressDialog();

            updateListviewItem();

        }
        catch (Exception ex)
        {
            Genel.printStackTrace(ex,getContext());
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
            Genel.printStackTrace(ex,getContext());
        }
    }


    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_zayi_menu_panel fragmentyeni = new frg_zayi_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_zayi_menu_panel").addToBackStack(null);
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
                    Genel.playButtonClikSound(getContext());
                    DEPOTag secilen_depo = _Secili;
                    aktif_zayi.zay_depo = secilen_depo.getDepo_id();
                    if(secilen_depo != null && !secilen_depo.getDepo_id().equals("")){
                        frg_zayi_arac_bulundu_indirme fragmentyeni = new frg_zayi_arac_bulundu_indirme();
                        fragmentyeni.fn_senddata(aktif_zayi,arac);
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_zayi_arac_bulundu_indirme").addToBackStack(null);
                        fragmentTransaction.commit();
                    } else {
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
                Genel.printStackTrace(ex,getContext());
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
