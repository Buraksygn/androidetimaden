package com.etimaden.UretimIslemleri.Uretim_sorgulama;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.GirisSayfasi;
import com.etimaden.UretimIslemleri.frg_uretim_menu_panel;
import com.etimaden.adapter.apmbluretimdetayAktifIsEmirleri;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.genel.Genel;
import com.etimaden.genel.SweetAlertDialogG;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.uretim_detay;
import com.etimaden.request.request_bos;
import com.etimaden.ugr_demo.MainActivity;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;
import java.util.List;


import static com.etimaden.cSabitDegerler._ipAdresi3G;
import static com.etimaden.cSabitDegerler._sbtVerisyon;
import static com.etimaden.cSabitDegerler._zkullaniciadi;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;

public class frg_uretim_detay_ekrani extends Fragment {

    SweetAlertDialogG pDialog;
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


    Button _btnyenile;
    ListView _aktif_isemri_list;
    Button _btngeri;


    //Urun_tag depo;
    uretim_detay uretimDetay;

    //uretim_etiket urun;

    ArrayList<uretim_detay> dataModels;
    uretim_detay _Secili = new uretim_detay();
    private static apmbluretimdetayAktifIsEmirleri adapter;



    public frg_uretim_detay_ekrani() {
        // Required empty public constructor
    }

    public static frg_uretim_detay_ekrani newInstance()
    {
        return new frg_uretim_detay_ekrani();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_uretim_detay_ekrani, container, false);
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

        //barkod,rfid,ikiside
        ((GirisSayfasi) getActivity()).fn_ModBoth();

        _btnyenile = (Button)getView().findViewById(R.id.btnyenile);
        _btnyenile.playSoundEffect(SoundEffectConstants.CLICK);
        _btnyenile.setOnClickListener(new fn_btnYenile());

        _btngeri = (Button)getView().findViewById(R.id.btngeri);
        _btngeri.playSoundEffect(SoundEffectConstants.CLICK);
        _btngeri.setOnClickListener(new fn_Geri());

        _aktif_isemri_list = (ListView) getView().findViewById(R.id.aktif_isemri_list);

        _aktif_isemri_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _Secili = dataModels.get(position);
            }
        });

        fn_AyarlariYukle();
        dataModels= new ArrayList<uretim_detay>();
        //fn_SiloListele();

        Genel.showProgressDialog(getContext());
        uretim_detay_degerlendir();
        Genel.dismissProgressDialog();


    }

    private void uretim_detay_degerlendir()
    {
        try
        {
            request_bos v_Gelen = new request_bos();
            v_Gelen.set_zaktif_alt_tesis(_ayaraktifalttesis);
            v_Gelen.set_zaktif_tesis(_ayaraktiftesis);
            v_Gelen.set_zkullaniciadi(_zkullaniciadi);
            v_Gelen.set_zsifre(_zsifre);
            v_Gelen.set_zsunucu_ip_adresi(_ayarsunucuip);
            v_Gelen.set_zsurum(_sbtVerisyon);
            v_Gelen.setAktif_kullanici(_ayaraktifkullanici);
            v_Gelen.setAktif_sunucu(_ayaraktifsunucu);



            List<uretim_detay> uretimDetayList =persos.fn_sec_uretim_detay(v_Gelen);
            if ( uretimDetayList!=null ){
                dataModels = new ArrayList<>(uretimDetayList);
                //dataModels = new ArrayList<>(persos.fn_sec_uretim_detay(v_Gelen));
            }

            updateListviewItem();

        }
        catch (Exception ex)
        {
            //pDialog.hide();
            new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                    .setTitleText("BAĞLANTI HATASI")
                    .setContentTextSize(25)
                    .setContentText("Üretim bilgisi alınamadı.")
                    .showCancelButton(false)
                    .show();
            return;
            //Program.giveHataMesaji("BAĞLANTI HATASI", "Üretim bilgisi alınamadı.", "");
        }
    }

    private void updateListviewItem()
    {
        try
        {
                int gorev_id = 0;
                if(adapter!=null){
                    adapter.clear();
                    _aktif_isemri_list.setAdapter(adapter);
                }

            adapter=new apmbluretimdetayAktifIsEmirleri(dataModels,getContext());
            _aktif_isemri_list.setAdapter(adapter);
            //pDialog.hide();

        }
        catch (Exception ex)
        {
            Genel.printStackTrace(ex,getContext());
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


    private class fn_btnYenile implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            try
            {
                Genel.lockButtonClick(view,getActivity());
                Genel.showProgressDialog(getContext());
                uretim_detay_degerlendir();
                Genel.dismissProgressDialog();
            }
            catch (Exception ex)
            {
                Genel.printStackTrace(ex,getContext());
            }


        }
    }
}
