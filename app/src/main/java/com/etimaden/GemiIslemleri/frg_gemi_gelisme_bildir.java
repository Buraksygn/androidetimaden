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

import com.etimaden.SayimIslemleri.Demirbas_sayim_islemi.frg_demirbas_sayim_menu_panel;
import com.etimaden.adapter.apmblGemiGelismeBildirimListesi;
import com.etimaden.adapter.apmblGemiListesi;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cResponseResult.Urun_sevkiyat;
import com.etimaden.frg_ana_sayfa;
import com.etimaden.genel.Genel;
import com.etimaden.genel.SweetAlertDialogG;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.GelismeTuruEnum;
import com.etimaden.persosclass.Gemi;
import com.etimaden.persosclass.GemiGelismeDurum;
import com.etimaden.request.request_string;
import com.etimaden.request.request_string_gemi;
import com.etimaden.ugr_demo.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class frg_gemi_gelisme_bildir extends Fragment {
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

    Button _btnGemiGeri;
    TextView _txtGemiBaslik;
    Button _btngemiKaydet;

    Gemi aktif_gemi_bilgi=null;

    ListView _gelisme_list;
    GemiGelismeDurum seciliGelisme =null;
    private apmblGemiGelismeBildirimListesi adapter;
    List<GemiGelismeDurum> gelismeListesi;

    public frg_gemi_gelisme_bildir(){}
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_gemi_gelisme_bildir,container,false);
    }

    public void fn_senddata(Gemi v_aktif_gemi_bilgi){
        this.aktif_gemi_bilgi = v_aktif_gemi_bilgi;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        _myIslem = new VeriTabani(getContext());

        fn_AyarlariYukle();

        _btnGemiGeri = getView().findViewById(R.id.btnGemiGeri);
        _btnGemiGeri.playSoundEffect(0);
        _btnGemiGeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frg_aktif_gemi_secimi fragmentyeni = new frg_aktif_gemi_secimi();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayoutForFragments,fragmentyeni,"frg_aktif_gemi_secimi").addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        _txtGemiBaslik = getView().findViewById(R.id.txtGemiBaslik);
         String baslik = "GEMİ : " + aktif_gemi_bilgi.gemiAd ;
                 //+ " - SAP KODU : " + aktif_gemi_bilgi.gemiKod ;
        _txtGemiBaslik.setText(baslik);

        _gelisme_list = getView().findViewById(R.id.gelisme_list);
        _gelisme_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                seciliGelisme = (GemiGelismeDurum) parent.getItemAtPosition(position);
                //seciliGemi = gemiListesi.get(position);
            }
        });

        gelismeListesi = new ArrayList<GemiGelismeDurum>();
        gelismeDurumlarıGetir();

        adapter=new apmblGemiGelismeBildirimListesi((ArrayList<GemiGelismeDurum>) gelismeListesi,getContext());
        _gelisme_list.setAdapter(adapter);

        _btngemiKaydet = getView().findViewById(R.id.btngemiKaydet);
        _btngemiKaydet.playSoundEffect(0);
        _btngemiKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seciliGelisme == null){
                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                            .setTitleText("UYARI")
                            .setContentTextSize(25)
                            .setContentText("SEÇİM İŞLEMİ YAPILMADI.")
                            .showCancelButton(false)
                            .show();
                    return;
                }
                try{
                    request_string_gemi _Param1 = new request_string_gemi();
                    _Param1.set_zsunucu_ip_adresi(_ayarsunucuip);
                    _Param1.set_zaktif_alt_tesis(_ayaraktifalttesis);
                    _Param1.set_zaktif_tesis(_ayaraktiftesis);
                    _Param1.set_zsurum(_sbtVerisyon);
                    _Param1.set_zkullaniciadi(_zkullaniciadi);
                    _Param1.set_zsifre(_zsifre);
                    _Param1.setAktif_sunucu(_ayaraktifsunucu);
                    _Param1.setAktif_kullanici(_ayaraktifkullanici);

                    _Param1.set_gemi(aktif_gemi_bilgi);
                    _Param1.set_gelismeTuru(seciliGelisme);

                    Genel.showProgressDialog(getContext());
                    Boolean result = persos.fn_gemi_gelisme_kaydet(_Param1);
                    Genel.dismissProgressDialog();

                    if(result != null && result){
                        new SweetAlertDialogG(getContext(), SweetAlertDialogG.NORMAL_TYPE)
                                .setTitleText("Bilgi")
                                .setContentTextSize(25)
                                .setContentText("Kaydetme işlemi başarıyla gerçekleşti.")
                                .showCancelButton(false)
                                .show();

                        frg_aktif_gemi_secimi fragmentyeni = new frg_aktif_gemi_secimi();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_aktif_gemi_secimi").addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                }
                catch (Exception ex){
                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                            .setTitleText("Uyarı")
                            .setContentTextSize(25)
                            .setContentText("Kaydetme işlemi sırasında hata oluştu." + ex.toString())
                            .showCancelButton(false)
                            .show();
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
    public void gelismeDurumlarıGetir(){

        for (GelismeTuruEnum durum : GelismeTuruEnum.values()) {
            gelismeListesi.add(new GemiGelismeDurum(durum.getAd()));
        }
    }
}
