package com.etimaden.SayimIslemleri.Depo_gecici_sayimi;

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

import com.etimaden.SayimIslemleri.Depo_sayim_islemi.frg_depo_sayim_islemi;
import com.etimaden.SayimIslemleri.Depo_sayim_islemi.frg_depo_sayim_menu_panel;
import com.etimaden.SayimIslemleri.frg_sayim_menu_panel;
import com.etimaden.adapter.apmblSayimIslemleriAktifDepoGeciciSayimIsemriSecimi;
import com.etimaden.adapter.apmblSayimIslemleriAktifDepoSayimIsemriSecimi;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.genel.Genel;
import com.etimaden.genel.SweetAlertDialogG;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.Depo_sayım_isemri;
import com.etimaden.persosclass.malzeme_sayim_isemri;
import com.etimaden.request.request_bos_aktif_isletme_esleme;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;
import java.util.List;


public class frg_aktif_depo_gecici_sayim_isemri_secimi extends Fragment {

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


    Button _btnSeciliSayimaBasla;
    ListView _aktif_is_emirleri_list;
    Button _btngeri;

    ArrayList<Depo_sayım_isemri> sayim_listesi;
    Depo_sayım_isemri _Secili = null;

    private apmblSayimIslemleriAktifDepoGeciciSayimIsemriSecimi adapter;

    public frg_aktif_depo_gecici_sayim_isemri_secimi() {
        // Required empty public constructor
    }

    public static frg_aktif_depo_gecici_sayim_isemri_secimi newInstance()
    {
        return new frg_aktif_depo_gecici_sayim_isemri_secimi();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.frg_aktif_depo_gecici_sayim_isemri_secimi, container, false);
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


        _btnSeciliSayimaBasla = (Button)getView().findViewById(R.id.btnSeciliSayimaBasla);
        _btnSeciliSayimaBasla.playSoundEffect(0);
        _btnSeciliSayimaBasla.setOnClickListener(new fn_btnSeciliSayimaBasla());

        _btngeri = (Button)getView().findViewById(R.id.btncikis);
        _btngeri.playSoundEffect(0);
        _btngeri.setOnClickListener(new fn_Geri());

        _aktif_is_emirleri_list = (ListView) getView().findViewById(R.id.aktif_is_emirleri_list);

        _aktif_is_emirleri_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _Secili = sayim_listesi.get(position);
            }
        });

        adapter=new apmblSayimIslemleriAktifDepoGeciciSayimIsemriSecimi(new ArrayList<Depo_sayım_isemri>(),getContext());
        _aktif_is_emirleri_list.setAdapter(adapter);

        fn_AyarlariYukle();
        sayim_listesi= new ArrayList<Depo_sayım_isemri>();
        sayimListele();
    }

    private void sayimListele()
    {
        try
        {
            request_bos_aktif_isletme_esleme _Param=new request_bos_aktif_isletme_esleme();

            _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
            _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
            _Param.set_zaktif_tesis(_ayaraktiftesis);
            _Param.set_zsurum(_sbtVerisyon);
            _Param.set_zkullaniciadi(_zkullaniciadi);
            _Param.set_zsifre(_zsifre);
            _Param.setAktif_sunucu(_ayaraktifsunucu);
            _Param.setAktif_kullanici(_ayaraktifkullanici);
            _Param.set_aktif_isletme_esleme(_ayaraktifisletmeeslesme);

            Genel.showProgressDialog(getContext());
            List<Depo_sayım_isemri> result = persos.fn_sec_depo_sayim_isemri(_Param);
            sayim_listesi=new ArrayList<>();
            if(result!=null) {
                sayim_listesi = new ArrayList<>(result);
            }
            Genel.dismissProgressDialog();

            updateListviewItem();

        }
        catch (Exception ex)
        {
            Genel.printStackTrace(ex,getContext());
            new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                    .setTitleText("BAĞLANTI HATASI")
                    .setContentTextSize(25)
                    .setContentText("Sayım işemri listesi alınamadı.")
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
                adapter.addAll(sayim_listesi);
                adapter.notifyDataSetChanged();
            }

        }
        catch (Exception ex)
        {
            Genel.printStackTrace(ex,getContext());
        }
    }





    private class fn_btnSeciliSayimaBasla implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Genel.lockButtonClick(view,getActivity());
            try
            {
                if (_Secili!=null) {
                    Depo_sayım_isemri secilen_sevk = _Secili;

                    frg_gecici_sayim_depo_secimi fragmentyeni = new frg_gecici_sayim_depo_secimi();
                    fragmentyeni.fn_senddata(secilen_sevk);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_gecici_sayim_depo_secimi").addToBackStack(null);
                    fragmentTransaction.commit();
                }
                else
                {
                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                            .setTitleText("UYARI")
                            .setContentTextSize(25)
                            .setContentText("SEÇİM İŞLEMİ YAPILMADI.")
                            .showCancelButton(false)
                            .show();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("UYARI")
                        .setContentTextSize(25)
                        .setContentText("SEÇİM İŞLEMİ YAPILMADI.")
                        .showCancelButton(false)
                        .show();
            }

        }
    }

    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_sayim_menu_panel fragmentyeni = new frg_sayim_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_sayim_menu_panel").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}
