package com.etimaden.manipulasyon.geribesleme;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.GirisSayfasi;
import com.etimaden.adapter.apmblManipulasyonGeribeslemeAyirma;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.genel.Genel;
import com.etimaden.genel.SweetAlertDialogG;
import com.etimaden.manipulasyon.ellecleme.frg_ellecleme_menu_panel;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.Urun_tag;
import com.etimaden.request.request_secEtiket;
import com.etimaden.request.request_string;
import com.etimaden.request.request_uruntag;
import com.etimaden.request.request_uruntag_string;
import com.etimaden.ugr_demo.R;

import android.widget.ListView;

import java.util.ArrayList;

public class frg_geribesleme_onay extends Fragment {

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


    Button _btn_01;

    TextView _txtYazi;
    ListView _listisemirleri;
    Button _btnGeri;
    Button _btnOkuma;


    ArrayList<Urun_tag> urun_listesi = new ArrayList<Urun_tag>();

    private apmblManipulasyonGeribeslemeAyirma adapter;

    Urun_tag aktif_etiket = null;
    boolean isReadable = true;
    int islemDurumu = 0;
    //  0 : etiket okutma bekleniyor
    //  1 : 2. etiket okutması bekleniyor

    public frg_geribesleme_onay() {
        // Required empty public constructor
    }

    public static frg_geribesleme_onay newInstance()
    {
        return new frg_geribesleme_onay();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_geribesleme_onay, container, false);
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

        ((GirisSayfasi) getActivity()).fn_ModBarkod();


        _btnGeri = (Button)getView().findViewById(R.id.btnGeri);
        _btnGeri.playSoundEffect(SoundEffectConstants.CLICK);
        _btnGeri.setOnClickListener(new fn_Geri());

        _btnOkuma = (Button)getView().findViewById(R.id.btnOkuma);
        _btnOkuma.playSoundEffect(SoundEffectConstants.CLICK);
        _btnOkuma.setOnClickListener(new fn_okumaDegistir());
        _btnOkuma.setText("KAREKOD");

        _btn_01= (Button)getView().findViewById(R.id.btn_01);
        _btn_01.playSoundEffect(SoundEffectConstants.CLICK);
        _btn_01.setOnClickListener(new frg_geribesleme_onay.fn_btn_01());

        _listisemirleri=(ListView)getView().findViewById(R.id.isemri_list);
        adapter=new apmblManipulasyonGeribeslemeAyirma(new ArrayList<Urun_tag>(),getContext());
        _listisemirleri.setAdapter(adapter);

        fn_AyarlariYukle();

    }



    public void barkodOkundu(String barkod){

        try
        {
            barkod = barkod.substring(barkod.length() - 24);
            if (!isReadable)
            {
                return;
            }
            isReadable = false;

            request_secEtiket v_Gelen=new request_secEtiket();

            v_Gelen.set_rfid(barkod);
            v_Gelen.set_zaktif_alt_tesis(_ayaraktifalttesis);
            v_Gelen.set_zaktif_tesis(_ayaraktiftesis);
            v_Gelen.set_zkullaniciadi(_zkullaniciadi);
            v_Gelen.set_zsifre(_zsifre);
            v_Gelen.set_zsunucu_ip_adresi(_ayarsunucuip);
            v_Gelen.set_zsurum(_sbtVerisyon);
            v_Gelen.setAktif_kullanici(_ayaraktifkullanici);
            v_Gelen.setAktif_sunucu(_ayaraktifsunucu);

            Genel.showProgressDialog(getContext());
            Urun_tag tag = persos.fn_secEtiket(v_Gelen);
            Genel.dismissProgressDialog();


            etiketDegerlendir(tag);

        }
        catch (Exception ex){
            Genel.printStackTrace(ex,getContext());
        }
        isReadable = true;
    }

    public void rfidOkundu(String rfid){
        try
        {
            if (!isReadable)
            {
                return;
            }
            isReadable = false;
            //System.Media.SystemSounds.Question.Play();
            request_secEtiket v_Gelen=new request_secEtiket();
            v_Gelen.set_rfid(rfid);
            v_Gelen.set_zaktif_alt_tesis(_ayaraktifalttesis);
            v_Gelen.set_zaktif_tesis(_ayaraktiftesis);
            v_Gelen.set_zkullaniciadi(_zkullaniciadi);
            v_Gelen.set_zsifre(_zsifre);
            v_Gelen.set_zsunucu_ip_adresi(_ayarsunucuip);
            v_Gelen.set_zsurum(_sbtVerisyon);
            v_Gelen.setAktif_kullanici(_ayaraktifkullanici);
            v_Gelen.setAktif_sunucu(_ayaraktifsunucu);

            Genel.showProgressDialog(getContext());
            Urun_tag tag = persos.fn_secEtiket(v_Gelen);
            Genel.dismissProgressDialog();

            etiketDegerlendir(tag);

        }
        catch (Exception ex){
            Genel.printStackTrace(ex,getContext());
        }

        isReadable = true;

    }

    private void etiketDegerlendir(final Urun_tag tag)
    {
        try
        {
            if (tag == null) {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Etiket detayına ulaşılamadı. Ağ bağlantısını kontrol ediniz..")
                        .showCancelButton(false)
                        .show();
            }
            else if ( tag.etiket_turu.equals("0"))
            {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("İşlem için uygun olmayan ürün seçimi yaptınız. \r\n Bu işlem için lütfen üretim işlemi tamamlanmış ve satış işlemi yapılmamış bir palet etiketi okutunuz.")
                        .showCancelButton(false)
                        .show();
            }

            else if (tag.islem_durumu.equals("1") || tag.islem_durumu.equals("370"))
            {

                Boolean kodVar=false;
                Urun_tag tmpObj=null;
                for(Urun_tag w:urun_listesi){
                    if(w.kod.equals(tag.kod)){
                        tmpObj=w;
                        kodVar=true;
                        break;
                    }
                }

                if (kodVar)
                {
                    urun_listesi.remove(tmpObj);
                }
                else
                {
                    urun_listesi.add(tag);
                }
            }

            else
            {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("İşlem için uygun olmayan ürün seçimi yaptınız. \r\n Bu işlem için lütfen üretim işlemi tamamlanmış ve satış işlemi yapılmamış bir palet etiketi okutunuz.")
                        .showCancelButton(false)
                        .show();
            }

            updateListviewItem();

        }
        catch (Exception ex) {
            Genel.printStackTrace(ex,getContext());
        }

    }

    private void updateListviewItem(){
        try
        {
            if (adapter != null) {
                adapter.clear();
                adapter.addAll(urun_listesi);
                adapter.notifyDataSetChanged();
            }

        }
        catch (Exception ex)
        {
            Genel.printStackTrace(ex,getContext());
        }
    }

    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_geribesleme_menu_panel fragmentyeni = new frg_geribesleme_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_geribesleme_menu_panel").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
    private class fn_okumaDegistir implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Genel.showProgressDialog(getContext());
            if(_btnOkuma.getText().toString().equals("KAREKOD")){
                ((GirisSayfasi) getActivity()).fn_ModRFID();
                _btnOkuma.setText("RFID");
            }else{
                ((GirisSayfasi) getActivity()).fn_ModBarkod();
                _btnOkuma.setText("KAREKOD");
            }
            Genel.dismissProgressDialog();
        }
    }

    private class fn_btn_01 implements View.OnClickListener {
        @Override
        public void onClick(View view)
        {
            ArrayList<Urun_tag> urunListesi = new ArrayList<>(urun_listesi); // Tüm listeyi kopyala
            if (urun_listesi.size() == 0)
            {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("HATALI İŞLEM")
                        .setContentTextSize(25)
                        .setContentText("Ürün listenizde ürün bulunmamaktadır. \r\n Hatalı İşlem.")
                        .showCancelButton(false)
                        .show();
            }
            else
            {
                //---------------------------------------------------------------
                String kod_serino = urunListesi.get(0).kod; //burasi serino_kod degeri ozgur

                String rota_islemdurum = "";
                request_string _Param = new request_string();
                _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
                _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
                _Param.set_zaktif_tesis(_ayaraktiftesis);
                _Param.set_zsurum(_sbtVerisyon);
                _Param.set_zkullaniciadi(_zkullaniciadi);
                _Param.set_zsifre(_zsifre);
                _Param.setAktif_sunucu(_ayaraktifsunucu);
                _Param.setAktif_kullanici(_ayaraktifkullanici);
                _Param.set_value(kod_serino); // özgür
                //---------------------------------------------------------------

                rota_islemdurum = persos.fn_geri_besleme_onay(_Param);


                if (rota_islemdurum.equals("10") || rota_islemdurum.equals("4") || rota_islemdurum.equals("1"))
                {
                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                            .setTitleText("İŞLEM İÇİN UYGUN OLMAYAN ÜRÜN")
                            .setContentTextSize(25)
                            .setContentText("Ürün yapmak istediğiniz işlem için uygun değildir. \r\n Rota Dumumu Uygun Değil.")
                            .showCancelButton(false)
                            .show();
                }
                else
                {
                    try
                    {

                        Genel.showProgressDialog(getContext());
                        Genel.dismissProgressDialog();

                        frg_geribesleme_harcama_yeri_secimi fragmentyeni = new frg_geribesleme_harcama_yeri_secimi();
                        fragmentyeni.fn_senddata(urunListesi);
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_geribesleme_harcama_yeri_secimi").addToBackStack(null);
                        fragmentTransaction.commit();

                    }
                    catch (Exception ex)
                    {
                        Genel.printStackTrace(ex,getContext());
                    }
                }
            }



        }
    }

}
