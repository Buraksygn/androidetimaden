package com.etimaden.SevkiyatIslemleri.Zayiat_islemleri.Zayi_depo_kabul;

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
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.SevkiyatIslemleri.Zayiat_islemleri.frg_zayi_aktivasyon;
import com.etimaden.SevkiyatIslemleri.Zayiat_islemleri.frg_zayi_menu_panel;
import com.etimaden.adapter.apmblSevkiyatZayiIsEmirleri;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.genel.Genel;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.Zayi;
import com.etimaden.request.request_bos;
import com.etimaden.request.request_bos_aktif_isletme_esleme;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class frg_zayi_aktif_isemri_secimi extends Fragment {

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


    Button _btnIsEmriSeciminiOnayla;
    ListView _aktif_is_emirleri_list;
    Button _btngeri;

    ArrayList<Zayi> zayi_listesi;
    Zayi _Secili = null;

    private apmblSevkiyatZayiIsEmirleri adapter;


    public frg_zayi_aktif_isemri_secimi() {
        // Required empty public constructor
    }

    public static frg_zayi_aktif_isemri_secimi newInstance()
    {
        return new frg_zayi_aktif_isemri_secimi();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.frg_zayi_aktif_isemri_secimi, container, false);
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


        _btnIsEmriSeciminiOnayla = (Button)getView().findViewById(R.id.btnIsEmriSeciminiOnayla);
        _btnIsEmriSeciminiOnayla.playSoundEffect(SoundEffectConstants.CLICK);
        _btnIsEmriSeciminiOnayla.setOnClickListener(new fn_btnIsEmriSeciminiOnayla());

        _btngeri = (Button)getView().findViewById(R.id.btncikis);
        _btngeri.playSoundEffect(SoundEffectConstants.CLICK);
        _btngeri.setOnClickListener(new fn_Geri());

        _aktif_is_emirleri_list = (ListView) getView().findViewById(R.id.aktif_is_emirleri_list);

        _aktif_is_emirleri_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _Secili = zayi_listesi.get(position);
            }
        });

        fn_AyarlariYukle();
        zayi_listesi= new ArrayList<Zayi>();
        sevkDegerlendir();
    }

    private void sevkDegerlendir()
    {
        try
        {
            request_bos_aktif_isletme_esleme _Param1= new request_bos_aktif_isletme_esleme();
            _Param1.set_zsunucu_ip_adresi(_ayarsunucuip);
            _Param1.set_zaktif_alt_tesis(_ayaraktifalttesis);
            _Param1.set_zaktif_tesis(_ayaraktiftesis);
            _Param1.set_zsurum(_sbtVerisyon);
            _Param1.set_zkullaniciadi(_zkullaniciadi);
            _Param1.set_zsifre(_zsifre);
            _Param1.setAktif_sunucu(_ayaraktifsunucu);
            _Param1.setAktif_kullanici(_ayaraktifkullanici);

            Genel.showProgressDialog(getContext());
            List<Zayi> result = persos.fn_sec_zayi_arac(_Param1);
            zayi_listesi=new ArrayList<>();
            if(result!=null) {
                zayi_listesi = new ArrayList<>(result);
            }
            Genel.dismissProgressDialog();
            updateListviewItem();
        }
        catch (Exception ex)
        {
            Genel.printStackTrace(ex,getContext());
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

           adapter=new apmblSevkiyatZayiIsEmirleri(zayi_listesi,getContext());
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


    private class fn_btnIsEmriSeciminiOnayla implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Genel.lockButtonClick(view,getActivity());
            try
            {
                if (_Secili!=null){
                    Zayi secilen_zayi = _Secili;
                    frg_zayi_isemri_indirme fragmentyeni = new frg_zayi_isemri_indirme();
                    fragmentyeni.fn_senddata(secilen_zayi);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_zayi_isemri_indirme").addToBackStack(null);
                    fragmentTransaction.commit();

                }
            }
            catch (Exception ex)
            {
                Genel.printStackTrace(ex,getContext());
            }

        }
    }
}
