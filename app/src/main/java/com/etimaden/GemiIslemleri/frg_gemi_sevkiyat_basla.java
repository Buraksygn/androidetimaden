package com.etimaden.GemiIslemleri;

import static com.etimaden.cSabitDegerler._ipAdresi3G;
import static com.etimaden.cSabitDegerler._sbtVerisyon;
import static com.etimaden.cSabitDegerler._zkullaniciadi;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.adapter.apmblGemiListesi;
import com.etimaden.adapter.apmblGemiSevkiyatIsemriListesi;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.frg_ana_sayfa;
import com.etimaden.genel.SweetAlertDialogG;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.Gemi;
import com.etimaden.persosclass.GemiGelismeDurum;
import com.etimaden.persosclass.Gemi_Sevkiyat;
import com.etimaden.persosclass.Gemi_Teslimat;
import com.etimaden.request.request_string;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;
import java.util.List;

public class frg_gemi_sevkiyat_basla extends Fragment {
    VeriTabani _myIslem;
    Button _btngemiSevkGeri;
    Button _btngemiSevkIleri;
    TextView _txtgemiSevkBaslik;
    ListView _teslimatList;
    Gemi aktif_gemi_bilgi=null;

    List<Gemi_Teslimat> teslimatList;
    Gemi_Teslimat seciliTeslimat =null;

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
    private apmblGemiSevkiyatIsemriListesi adapter;


    Persos persos;


    public frg_gemi_sevkiyat_basla(){}
    public void fn_senddata(Gemi v_aktif_gemi_bilgi){
        this.aktif_gemi_bilgi = v_aktif_gemi_bilgi;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_gemi_sevkiyat_basla,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        _myIslem = new VeriTabani(getContext());
        fn_AyarlariYukle();

        _btngemiSevkGeri = getView().findViewById(R.id.btngemiSevkGeri);
        _btngemiSevkGeri.playSoundEffect(0);
        _btngemiSevkGeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frg_aktif_gemi_secimi fragmentyeni = new frg_aktif_gemi_secimi();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayoutForFragments,fragmentyeni,"frg_aktif_gemi_secimi").addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        _txtgemiSevkBaslik = getView().findViewById(R.id.txtgemiSevkBaslik);
        //String baslik = "GEMİ : " + aktif_gemi_bilgi.gemiAd ;
        //_txtgemiSevkBaslik.setText(baslik);

        _teslimatList = getView().findViewById(R.id.teslimat_list);
        _teslimatList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                seciliTeslimat = (Gemi_Teslimat) parent.getItemAtPosition(position);
            }
        });

        teslimatList = new ArrayList<Gemi_Teslimat>();

        teslimatListesiGetir();
        adapter=new apmblGemiSevkiyatIsemriListesi((ArrayList<Gemi_Teslimat>) teslimatList,getContext());
        _teslimatList.setAdapter(adapter);

        _btngemiSevkIleri = getView().findViewById(R.id.btngemiSevkIleri);
        _btngemiSevkIleri.playSoundEffect(0);
        _btngemiSevkIleri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seciliTeslimat == null){
                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                            .setTitleText("UYARI")
                            .setContentTextSize(25)
                            .setContentText("SEÇİM İŞLEMİ YAPILMADI.")
                            .showCancelButton(false)
                            .show();
                }
                else{
                    frg_aktif_gemi_arac_yukleme fragmentyeni = new frg_aktif_gemi_arac_yukleme();
                    fragmentyeni.fn_senddata(seciliTeslimat.ise_sap_kod);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayoutForFragments,fragmentyeni,"frg_aktif_gemi_arac_yukleme").addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });

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

    public void teslimatListesiGetir(){
        fn_AyarlariYukle();
        request_string _Param1= new request_string();
        _Param1.set_zsunucu_ip_adresi(_ayarsunucuip);
        _Param1.set_zaktif_alt_tesis(_ayaraktifalttesis);
        _Param1.set_zaktif_tesis(_ayaraktiftesis);
        _Param1.set_zsurum(_sbtVerisyon);
        _Param1.set_zkullaniciadi(_zkullaniciadi);
        _Param1.set_zsifre(_zsifre);
        _Param1.setAktif_sunucu(_ayaraktifsunucu);
        _Param1.setAktif_kullanici(_ayaraktifkullanici);
        List<Gemi_Teslimat> result = persos.fn_sec_gemi_teslimat_listesi(_Param1);
        teslimatList = new ArrayList<>();
        if(result != null){
            teslimatList = new ArrayList<>(result);
        }
    }


}
