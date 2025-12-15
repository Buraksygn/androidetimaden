package com.etimaden.GemiIslemleri;

import static com.etimaden.cSabitDegerler._ipAdresi3G;
import static com.etimaden.cSabitDegerler._sbtVerisyon;
import static com.etimaden.cSabitDegerler._zkullaniciadi;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.adapter.apmblGemiListesi;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cResponseResult.Urun_sevkiyat;
import com.etimaden.frg_ana_sayfa;
import com.etimaden.genel.Genel;
import com.etimaden.genel.SweetAlertDialogG;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.Gemi;
import com.etimaden.request.request_gemi;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;
import java.util.List;

public class frg_aktif_gemi_secimi extends Fragment {

    VeriTabani _myIslem;
    Button _btngeriGemi;
    Button _btngelismebildir;
    Button _btnsevkiyatbasla;
    Gemi seciliGemi =null;
    ListView _gemi_list;
    List<Gemi> gemiListesi;
    Gemi gemi =null;
    private apmblGemiListesi adapter;

    EditText _searchBox;

    ArrayAdapter<String> adapterList;
    ArrayList<String> dataList;
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

    public void fn_senddata(Gemi _gemi){
        this.gemi = _gemi;
    }

    public frg_aktif_gemi_secimi() {
        // Required empty public constructor
    }
    public static frg_aktif_gemi_secimi newInstance()
    {
        return new frg_aktif_gemi_secimi();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_aktif_gemi_secimi, container, false);

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
            _OnlineUrl = "http://"+_ipAdresi3G+":"+_zport3G+"/";
        }
        persos = new Persos(_OnlineUrl,getContext());
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        _myIslem = new VeriTabani(getContext());
        fn_AyarlariYukle();
        _btngeriGemi = getView().findViewById(R.id.btngeriGemi);
        _btngeriGemi.playSoundEffect(0);
        _btngeriGemi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frg_ana_sayfa fragmentyeni = new frg_ana_sayfa();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayoutForFragments,fragmentyeni,"frg_ana_sayfa").addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        _btngelismebildir = getView().findViewById(R.id.btngelismebildir);
        _btngelismebildir.playSoundEffect(0);
        _btngelismebildir.setOnClickListener(new fn_gelismebildir());

        _btnsevkiyatbasla = getView().findViewById(R.id.btnsevkiyatbasla);
        _btnsevkiyatbasla.playSoundEffect(0);
        _btnsevkiyatbasla.setOnClickListener(new fn_sevkiyatabasla());

        _gemi_list = (ListView)getView().findViewById(R.id.gemi_list);

        _gemi_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                seciliGemi = (Gemi) parent.getItemAtPosition(position);
                //seciliGemi = gemiListesi.get(position);
            }
        });

        gemiListesi = new ArrayList<Gemi>();

        gemiListesiGetir();


        adapter=new apmblGemiListesi((ArrayList<Gemi>) gemiListesi,getContext());
        _gemi_list.setAdapter(adapter);

        _searchBox = (EditText) getView().findViewById(R.id.searchBox);

        _searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //adapter.getFilter().filter(s);
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });


    }
    private void gemiListesiGetir(){
        fn_AyarlariYukle();

        request_gemi _Param1= new request_gemi();
        _Param1.set_zsunucu_ip_adresi(_ayarsunucuip);
        _Param1.set_zaktif_alt_tesis(_ayaraktifalttesis);
        _Param1.set_zaktif_tesis(_ayaraktiftesis);
        _Param1.set_zsurum(_sbtVerisyon);
        _Param1.set_zkullaniciadi(_zkullaniciadi);
        _Param1.set_zsifre(_zsifre);
        _Param1.setAktif_sunucu(_ayaraktifsunucu);
        _Param1.setAktif_kullanici(_ayaraktifkullanici);

        List<Gemi> result = persos.fn_sec_aktif_gemi_listesi(_Param1);
        gemiListesi=new ArrayList<>();
        if(result!=null) {
            gemiListesi = new ArrayList<>(result);
        }
    }

    public class fn_gelismebildir implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if(seciliGemi == null){
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("UYARI")
                        .setContentTextSize(25)
                        .setContentText("SEÇİM İŞLEMİ YAPILMADI.")
                        .showCancelButton(false)
                        .show();
            }else{

                frg_gemi_gelisme_bildir fragmentyeni = new frg_gemi_gelisme_bildir();
                fragmentyeni.fn_senddata(seciliGemi);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayoutForFragments,fragmentyeni,"frg_gemi_gelisme_bildir").addToBackStack(null);
                fragmentTransaction.commit();

                //new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                //        .setTitleText("GEMİ BİLGİLERİ")
                //        .setContentTextSize(25)
                //        .setContentText("Gemi Adı : " + seciliGemi.gemiAd + " \nGemi Kod : " +seciliGemi.gemiKod)
                //        .showCancelButton(false)
                //        .show();
            }
        }
    }

    public class fn_sevkiyatabasla implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if(seciliGemi == null){
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("UYARI")
                        .setContentTextSize(25)
                        .setContentText("SEÇİM İŞLEMİ YAPILMADI.")
                        .showCancelButton(false)
                        .show();
            }else{
                frg_gemi_sevkiyat_basla fragmentyeni = new frg_gemi_sevkiyat_basla();
                fragmentyeni.fn_senddata(seciliGemi);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayoutForFragments,fragmentyeni,"frg_gemi_sevkiyat_basla").addToBackStack(null);
                fragmentTransaction.commit();
            }
        }
    }
}
