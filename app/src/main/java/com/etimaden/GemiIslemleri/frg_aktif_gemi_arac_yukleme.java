package com.etimaden.GemiIslemleri;

import static com.etimaden.cSabitDegerler._ipAdresi3G;
import static com.etimaden.cSabitDegerler._sbtVerisyon;
import static com.etimaden.cSabitDegerler._zkullaniciadi;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.GirisSayfasi;
import com.etimaden.adapter.apmblGemiGelismeBildirimListesi;
import com.etimaden.adapter.apmblGemiItemYuklemeListesi;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cResponseResult.Sevkiyat_isemri;
import com.etimaden.cResponseResult.Urun_sevkiyat;
import com.etimaden.genel.Genel;
import com.etimaden.genel.SweetAlertDialogG;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.GemiGelismeDurum;
import com.etimaden.persosclass.Gemi_Sevkiyat;
import com.etimaden.persosclass.Gemi_Teslimat;
import com.etimaden.request.request_string;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;
import java.util.List;

public class frg_aktif_gemi_arac_yukleme extends Fragment {
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
    Button _btngemiYuklemeGeri;
    TextView _txtgemiYuklemeBaslik;
    TextView _txtYuklenenArac;
    TextView _txtTamamlananArac;
    TextView _txtHasarliArac;

    String gemiAd = null;
    String iseSapKod = null;

    ListView _arac_list;
    Sevkiyat_isemri aktif_sevk_isemri = null;
    Gemi_Sevkiyat _SeciliArac = null;

    List<Gemi_Sevkiyat> _seciliAraclar = new ArrayList<>();
    ArrayList<Gemi_Sevkiyat> arac_listesi ;
    private apmblGemiItemYuklemeListesi adapter;

    public void fn_senddata(String iseSapKod){
        this.iseSapKod = iseSapKod;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_aktif_gemi_arac_yukleme, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        ((GirisSayfasi) getActivity()).fn_ModBarkod();
        _myIslem = new VeriTabani(getContext());
        fn_AyarlariYukle();
        _myIslem.fn_EpcTemizle();
        ((GirisSayfasi) getActivity()).fn_ListeTemizle();

        _btngemiYuklemeGeri = getView().findViewById(R.id.btngemiYuklemeGeri);
        _btngemiYuklemeGeri.playSoundEffect(0);
        _btngemiYuklemeGeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frg_gemi_sevkiyat_basla fragmentyeni = new frg_gemi_sevkiyat_basla();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayoutForFragments,fragmentyeni,"frg_gemi_sevkiyat_basla").addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        _txtgemiYuklemeBaslik = getView().findViewById(R.id.txtgemiYuklemeBaslik);
        String baslik = "İşemri Sap Kodu : " + iseSapKod ;
        _txtgemiYuklemeBaslik.setText(baslik);

        _txtYuklenenArac = getView().findViewById(R.id.txtYuklenenArac);
        _txtTamamlananArac = getView().findViewById(R.id.txtTamamlananArac);
        _txtHasarliArac = getView().findViewById(R.id.txtHasarliArac);

        _arac_list = getView().findViewById(R.id.gemi_yukleme_list);
        _arac_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Gemi_Sevkiyat item = (Gemi_Sevkiyat) parent.getItemAtPosition(position);
                String secilen = item.ara_plaka;

                new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                        .setTitleText("UYARI")
                        .setContentTextSize(25)
                        .setContentText("SEÇİLEN :" + secilen)
                        .showCancelButton(false)
                        .show();
            }
        });
        arac_listesi = new ArrayList<Gemi_Sevkiyat>();
        aracListesiGetir();
        adapter=new apmblGemiItemYuklemeListesi((ArrayList<Gemi_Sevkiyat>) arac_listesi,getContext());
        _arac_list.setAdapter(adapter);
    }

    public void aracListesiGetir(){
        arac_listesi.add(new Gemi_Sevkiyat("","","","","","","06CPS049","","","",""));
        arac_listesi.add(new Gemi_Sevkiyat("","","","","","","06PSE59","","","",""));
        arac_listesi.add(new Gemi_Sevkiyat("","","","","","","07DCT96","","","",""));

        int miktar1 = 12;
        int miktar2 = 15;
        int miktar3 = 3;
        String msg="Yüklenen = " + miktar1;
        _txtYuklenenArac.setText(msg);
        String msg1="Tamamlanan = " + miktar2;
        _txtTamamlananArac.setText(msg1);
        String msg2="Hasarlı = " + miktar3;
        _txtHasarliArac.setText(msg2);
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

    public void barkodOkundu(String barkod){
        try{
            barkod = barkod.substring(barkod.length()-24);
            if (!isReadable)
            {
                return;
            }
            isReadable = false;
            Genel.playQuestionSound(getContext());
            request_string v_Gelen=new request_string();
            v_Gelen.set_value(barkod);
            v_Gelen.set_zaktif_alt_tesis(_ayaraktifalttesis);
            v_Gelen.set_zaktif_tesis(_ayaraktiftesis);
            v_Gelen.set_zkullaniciadi(_zkullaniciadi);
            v_Gelen.set_zsifre(_zsifre);
            v_Gelen.set_zsunucu_ip_adresi(_ayarsunucuip);
            v_Gelen.set_zsurum(_sbtVerisyon);
            v_Gelen.setAktif_kullanici(_ayaraktifkullanici);
            v_Gelen.setAktif_sunucu(_ayaraktifsunucu);

            Genel.showProgressDialog(getContext());

        }catch (Exception ex){
            Genel.printStackTrace(ex,getContext());
            Log.d("Exception", "",ex);
        }
        isReadable = true;
    }

}
