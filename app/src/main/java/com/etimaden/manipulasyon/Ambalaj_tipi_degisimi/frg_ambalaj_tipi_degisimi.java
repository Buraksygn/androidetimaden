package com.etimaden.manipulasyon.Ambalaj_tipi_degisimi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.GirisSayfasi;
import com.etimaden.SevkiyatIslemleri.frg_aktif_isemri_yukleme;
import com.etimaden.adapter.apmblManipulasyonAmbalajTipiDegisimi;
import com.etimaden.adapter.apmblSevkiyatAktifIsEmiriYukleme;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cResponseResult.Urun_sevkiyat;
import com.etimaden.genel.Genel;
import com.etimaden.genel.SweetAlertDialogG;
import com.etimaden.manipulasyon.frg_manipulasyon_menu_panel;
import com.etimaden.persosclass.Urun_tag;
import com.etimaden.persos.Persos;
import com.etimaden.request.request_ambalaj_tipi_secEtiket;
import com.etimaden.request.request_secEtiket;
import com.etimaden.request.request_string;
import com.etimaden.request.request_uruntag_list_uruntag;
import com.etimaden.response.frg_paket_uretim_ekrani.View_ambalaj_tipi_secEtiket;
import com.etimaden.ugr_demo.R;


import static com.etimaden.cSabitDegerler._ipAdresi3G;
import static com.etimaden.cSabitDegerler._sbtVerisyon;
import static com.etimaden.cSabitDegerler._zkullaniciadi;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;

import java.util.ArrayList;

public class frg_ambalaj_tipi_degisimi extends Fragment {


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
    TextView _txtMiktar;
    Button _btnGeri;
    Button _btnOkuma;

    boolean isReadable = true;
    boolean isemri_secildi = false;
    Urun_tag aktif_isemri = null;
    ArrayList<Urun_tag> urun_listesi = new ArrayList<Urun_tag>();

    private apmblManipulasyonAmbalajTipiDegisimi adapter;

    public frg_ambalaj_tipi_degisimi() {
        // Required empty public constructor
    }
    public static frg_ambalaj_tipi_degisimi newInstance()
    {
        return new frg_ambalaj_tipi_degisimi();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_ambalaj_tipi_degisimi, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    private void fn_AyarlariYukle() {


        _ayarbaglantituru = _myIslem.fn_baglanti_turu();
        _ayarsunucuip = _myIslem.fn_sunucu_ip();
        _ayaraktifkullanici = _myIslem.fn_aktif_kullanici();
        _ayaraktifdepo = _myIslem.fn_aktif_depo();
        _ayaraktifalttesis = _myIslem.fn_aktif_alt_tesis();
        _ayaraktiftesis = _myIslem.fn_aktif_tesis();
        _ayaraktifsunucu = _myIslem.fn_aktif_sunucu();
        _ayaraktifisletmeeslesme = _myIslem.fn_isletmeeslesme();

        if (_ayarbaglantituru.equals("wifi"))
        {
            _OnlineUrl = "http://" + _ayarsunucuip + ":" + _zportWifi + "/";
        }
        else
        {
            _OnlineUrl = "http://" + _ipAdresi3G + ":" + _zport3G + "/";
        }

        persos = new Persos(_OnlineUrl,getContext());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((GirisSayfasi) getActivity()).fn_ModBarkod();

        _myIslem = new VeriTabani(getContext());

        fn_AyarlariYukle();

        _myIslem.fn_EpcTemizle();

        ((GirisSayfasi) getActivity()).fn_ListeTemizle();

        _btnGeri = (Button)getView().findViewById(R.id.btnGeri);
        _btnGeri.playSoundEffect(0);
        _btnGeri.setOnClickListener(new fn_Geri());

        _btnOkuma = (Button)getView().findViewById(R.id.btnOkuma);
        _btnOkuma.playSoundEffect(0);
        _btnOkuma.setOnClickListener(new fn_okumaDegistir());
        _btnOkuma.setText("KAREKOD");

        _btn_01= (Button)getView().findViewById(R.id.btn_01);
        _btn_01.playSoundEffect(0);
        _btn_01.setOnClickListener(new fn_btn_01());

        _txtYazi=(TextView)getView().findViewById(R.id.txtYazi);

        _txtMiktar=(TextView)getView().findViewById(R.id.txtMiktar);

        _btn_01.setVisibility(View.INVISIBLE);

        _listisemirleri=(ListView)getView().findViewById(R.id.listisemirleri);

        _listisemirleri.setVisibility(View.INVISIBLE);

        adapter=new apmblManipulasyonAmbalajTipiDegisimi(urun_listesi,getContext());
        _listisemirleri.setAdapter(adapter);
    }

    private void updateListviewItem(){
        try
        {

            int miktar = 0;

            for (Urun_tag l : urun_listesi ) {
                miktar += Integer.parseInt(l.palet_agirligi);
            }

            String msg=miktar+ "KG";
            _txtMiktar.setText(msg);

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

    public void barkodOkundu(String barkod){

        try
        {

            barkod = barkod.substring(barkod.length()-24);
            if (!isReadable)
            {
                return;
            }
            isReadable = false;

            Genel.playQuestionSound(getContext());

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

            if (!isemri_secildi && tag != null && (tag.uretim_isemri_tipi_alt.equals("350") || tag.uretim_isemri_tipi_alt.equals("351")))
            {
                isemri_degerlendir(tag);
            }
            else if (!isemri_secildi || tag == null || !tag.islem_durumu.equals("1"))
            {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("İŞLEM İÇİN UYGUN OLMAYAN ÜRÜN")
                        .setContentTextSize(25)
                        .setContentText("Ürün yapmak istediğiniz işlem için uygun değildir. \r\n İşleme uygun olmayan etiket.")
                        .showCancelButton(false)
                        .show();
            }
            else
            {
                urunDegerlendir(tag);
            }

        }
        catch (Exception ex)
        {
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

            Genel.playQuestionSound(getContext());

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

            if (!isemri_secildi && tag != null && (tag.uretim_isemri_tipi_alt.equals("350") || tag.uretim_isemri_tipi_alt.equals("351")))
            {
                isemri_degerlendir(tag);
            }
            else if (!isemri_secildi || tag == null || !tag.islem_durumu.equals("1"))
            {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("İŞLEM İÇİN UYGUN OLMAYAN ÜRÜN")
                        .setContentTextSize(25)
                        .setContentText("Ürün yapmak istediğiniz işlem için uygun değildir. \r\n İşleme uygun olmayan etiket.")
                        .showCancelButton(false)
                        .show();
            }
            else
            {
                urunDegerlendir(tag);
            }

        }
        catch (Exception ex)
        {
            Genel.printStackTrace(ex,getContext());
        }
        isReadable = true;

    }

    private void urunDegerlendir( final Urun_tag tag){
        try
        {
            Boolean palet_kodVar=false;
            for(Urun_tag w:urun_listesi){
                if(w.kod.equals(tag.palet_kod)){
                    palet_kodVar=true;
                    break;
                }
            }
            Boolean kodVar=false;
            Urun_tag tmpObj=null;
            for(Urun_tag w:urun_listesi){
                if(w.kod.equals(tag.kod)){
                    tmpObj=w;
                    kodVar=true;
                    break;
                }
            }

            if (tag.etiket_turu.equals("0") )
            {
                if(palet_kodVar)
                {
                    if (kodVar)
                    {
                        urun_listesi.remove(tmpObj);
                        for(Urun_tag w:urun_listesi){
                            if (w.kod.equals(tag.palet_kod))
                            {
                                int x =0;
                                for(Urun_tag a:urun_listesi){
                                    if(a.palet_kod.equals(tag.palet_kod)){
                                        x++;
                                    }
                                }
                                x = x - 1;
                                if (x == 0)
                                    x = Integer.parseInt(w.palet_dizim_sayisi);
                                w.aciklama = "" + (x);
                            }
                        }

                    }
                        else
                    {
                        urun_listesi.add(tag);
                        for(Urun_tag w:urun_listesi){
                            if (w.kod.equals(tag.palet_kod))
                            {
                                int x =0;
                                for(Urun_tag a:urun_listesi){
                                    if(a.palet_kod.equals(tag.palet_kod)){
                                        x++;
                                    }
                                }
                                x = x - 1;
                                if (x == 0)
                                    x = Integer.parseInt(w.palet_dizim_sayisi);
                                w.aciklama = "" + (x);
                            }
                        }

                    }
                }
                else
                {
                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                            .setTitleText("İŞLEM İÇİN UYGUN OLMAYAN ÜRÜN")
                            .setContentTextSize(25)
                            .setContentText("İlk önce paketin bulunduğu palet etiketini okutunuz. \r\n İşleme uygun olmayan etiket.")
                            .showCancelButton(false)
                            .show();
                }
            }
            else if (kodVar)
            {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                        .setTitleText("UYARI")
                        .setContentText("Ürün serino :" + tag.kod + "\r\nBu ürünü listenizden kaldırmak istediğinize emin misniz ?")
                        .setContentTextSize(20)
                        .setConfirmText("EVET")
                        .setCancelText("HAYIR")
                        .showCancelButton(true)
                        .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialogG sDialog) {
                                sDialog.dismissWithAnimation();

                                Urun_tag tmpObj=null;
                                for (Urun_tag w : urun_listesi)
                                {
                                    if(w.kod.equals(tag.kod) || w.palet_kod.equals(tag.kod)){
                                        tmpObj=w;
                                    }
                                }
                                if(tmpObj!=null) {
                                    urun_listesi.remove(tmpObj);
                                }

                                return;
                            }
                        })
                        .setCancelClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialogG sDialog) {
                                sDialog.dismissWithAnimation();
                                return;
                            }
                        })
                        .show();
                return;

            }
                else
            {

                String[] sevk_char = aktif_isemri.karakteristikler.split(",",-1);
                String[] urun_char = tag.karakteristikler.split(",",-1);

                if (_ayaraktiftesis.equals("4001") && !sevk_char[9].equals(urun_char[9]))
                {
                    if (tag.etiket_turu.equals("1") || tag.etiket_turu.equals("2"))
                        tag.aciklama = tag.palet_dizim_sayisi;
                    else
                        tag.aciklama = "1";
                    urun_listesi.add(tag);
                    updateListviewItem();
                }
                else if ( tag.urun_kod.equals(aktif_isemri.urun_kod))
                {
                    if (tag.etiket_turu.equals("1") || tag.etiket_turu.equals("2"))
                        tag.aciklama = tag.palet_dizim_sayisi;
                    else
                        tag.aciklama = "1";
                    urun_listesi.add(tag);
                    updateListviewItem();
                }
                else
                {
                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                            .setTitleText("İŞLEM İÇİN UYGUN OLMAYAN ÜRÜN")
                            .setContentTextSize(25)
                            .setContentText("Ürün kodu iş emri ile uyuşmamaktadır. \r\n İşleme uygun olmayan etiket.")
                            .showCancelButton(false)
                            .show();
                }
            }


        }
        catch (Exception ex)
        {
            Genel.printStackTrace(ex,getContext());
        }
    }

    private void isemri_degerlendir(Urun_tag tag){
        try
        {
            if (!tag.uretim_dakika_onayi.equals("1"))
            {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("ONAY ALINAMADI")
                        .setContentTextSize(25)
                        .setContentText("PAKET TİPİ DEĞİŞİMİ ONAYI ALINAMADI. DETAYLAR KISMINDAN PAKET TİPİ DEĞİŞİMİ KURALLARINI OKUYABİLİRSİNİZ. \r\n ÜRETİM KURALLARI \r\n 1) 3 GÜN İÇİNDE PAKET TİPİ DEĞİŞİMİ KAYDI GERÇEKLEŞMEYEN ETİKET İÇİN PAKET TİPİ DEĞİŞİMİ KAYDI OLUŞTURULAMAZ.")
                        .showCancelButton(false)
                        .show();
                return;
            }
            else
            {

                isemri_secildi = true;
                _btn_01.setVisibility(View.VISIBLE);
                _listisemirleri.setVisibility(View.VISIBLE);
                _txtYazi.setVisibility(View.INVISIBLE);
                aktif_isemri = tag;
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                        .setTitleText("")
                        .setContentText("Ambalaj tipi değişimi için harcanacak ürünleri okutunuz.")
                        .setContentTextSize(20)
                        .setConfirmText("TAMAM")
                        .showCancelButton(false)
                        .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialogG sDialog) {
                                sDialog.dismissWithAnimation();
                                return;
                            }
                        }).show();
            }
        }
        catch (Exception ex)
        {
            Genel.printStackTrace(ex,getContext());
        }
    }

    private class fn_btn_01 implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            try
            {
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

                    request_uruntag_list_uruntag v_Gelen=new request_uruntag_list_uruntag();
                    v_Gelen.set_zaktif_alt_tesis(_ayaraktifalttesis);
                    v_Gelen.set_zaktif_tesis(_ayaraktiftesis);
                    v_Gelen.set_zkullaniciadi(_zkullaniciadi);
                    v_Gelen.set_zsifre(_zsifre);
                    v_Gelen.set_zsunucu_ip_adresi(_ayarsunucuip);
                    v_Gelen.set_zsurum(_sbtVerisyon);
                    v_Gelen.setAktif_kullanici(_ayaraktifkullanici);
                    v_Gelen.setAktif_sunucu(_ayaraktifsunucu);

                    v_Gelen.setUrun_listesi(urun_listesi);
                    v_Gelen.setEtiket(aktif_isemri);

                    Genel.showProgressDialog(getContext());
                    Boolean islem_sonucu = persos.fn_paket_ambalaj_degistir(v_Gelen);
                    Genel.dismissProgressDialog();

                    if(islem_sonucu==true)
                    {
                        new SweetAlertDialogG(getContext(), SweetAlertDialogG.SUCCESS_TYPE)
                                .setTitleText("İşlem Onayı")
                                .setContentText("İşlem başarı ile tamamlanmıştır.")
                                .setContentTextSize(20)
                                .setConfirmText("TAMAM")
                                .showCancelButton(false)
                                .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialogG sDialog) {
                                        sDialog.dismissWithAnimation();

                                        frg_manipulasyon_menu_panel fragmentyeni = new frg_manipulasyon_menu_panel();
                                        FragmentManager fragmentManager = getFragmentManager();
                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                        fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_manipulasyon_menu_panel").addToBackStack(null);
                                        fragmentTransaction.commit();

                                        return;
                                    }
                                }).show();

                    }
                    else
                    {
                        new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                                .setTitleText("HATA")
                                .setContentTextSize(25)
                                .setContentText("İşlem yapilamadı. \r\n İşlem bağlantı hatası")
                                .showCancelButton(false)
                                .show();
                    }
                }
            }
            catch (Exception ex)
            {
                Genel.printStackTrace(ex,getContext());
            }

        }
    }

    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            frg_manipulasyon_menu_panel fragmentyeni = new frg_manipulasyon_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_manipulasyon_menu_panel").addToBackStack(null);
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
}
