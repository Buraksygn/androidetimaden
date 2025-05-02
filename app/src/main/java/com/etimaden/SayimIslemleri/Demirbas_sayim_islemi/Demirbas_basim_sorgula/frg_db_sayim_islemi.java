package com.etimaden.SayimIslemleri.Demirbas_sayim_islemi.Demirbas_basim_sorgula;

import static com.etimaden.cSabitDegerler._ipAdresi3G;
import static com.etimaden.cSabitDegerler._sbtVerisyon;
import static com.etimaden.cSabitDegerler._zkullaniciadi;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.GirisSayfasi;
import com.etimaden.SayimIslemleri.Demirbas_sayim_islemi.frg_demirbas_sayim_menu_panel;
import com.etimaden.adapter.apmblDemirbasSayimDsSayimIslemi;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.genel.Genel;
import com.etimaden.genel.SweetAlertDialogG;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.Demirbas_Konum;
import com.etimaden.persosclass.Duran_Varlik_sap;
import com.etimaden.persosclass.Zimmet_sonuc_sap;
import com.etimaden.persosclass.aktarim;
import com.etimaden.persosclass.demirbas_sayim;
import com.etimaden.request.request_demirbas_konum;
import com.etimaden.request.request_demirbas_sayim_list;
import com.etimaden.request.request_demirbas_sayim_string;
import com.etimaden.request.request_sevkiyat_aktarim;
import com.etimaden.request.request_string;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class frg_db_sayim_islemi extends Fragment {

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


    TextView _txtBaslik;
    ImageView _imgBilgi;
    Button _btnTamam;
    ListView _aktif_is_emirleri_list;
    ImageView _imgHepsi;
    ImageView _imgBulunan;
    ImageView _imgEksik;
    TextView _txtHepsi;
    TextView _txtBulunan;
    TextView _txtEksik;
    TextView _txtYuklemeMiktari;

    boolean isReadable = true;
    ArrayList<demirbas_sayim> sayilacak_liste;
    ArrayList<demirbas_sayim> kayıt_yapılacak_urun;
    Demirbas_Konum konum_sayim_isemri = null;
    int filtre_durum = 0;
    int longClickedItemIndex;

    private apmblDemirbasSayimDsSayimIslemi adapter;

    public frg_db_sayim_islemi() {
        // Required empty public constructor
    }

    public static frg_db_sayim_islemi newInstance()
    {
        return new frg_db_sayim_islemi();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.frg_db_sayim_islemi, container, false);
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

    public void fn_senddata(Demirbas_Konum secilen_sevk) {
        this.konum_sayim_isemri=secilen_sevk;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        new VeriTabani(getContext()).fn_EpcTemizle();

        _myIslem = new VeriTabani(getContext());

        ((GirisSayfasi) getActivity()).fn_ModBoth();
        ((GirisSayfasi) getActivity()).fn_ListeTemizle();
        ((GirisSayfasi) getActivity()).fn_GucAyarla(250);

        _txtBaslik = (TextView) getView().findViewById(R.id.txtBaslik);
        _txtBaslik.setText("SAYIM LİSTESİ");

        _imgBilgi = (ImageView)getView().findViewById(R.id.imgBilgi);
        _imgBilgi.playSoundEffect(0);
        _imgBilgi.setOnClickListener(new fn_imgBilgi());

        _btnTamam = (Button)getView().findViewById(R.id.btnTamam);
        _btnTamam.playSoundEffect(0);
        _btnTamam.setOnClickListener(new fn_btnTamam());


        _aktif_is_emirleri_list = (ListView) getView().findViewById(R.id.aktif_is_emirleri_list);
        adapter=new apmblDemirbasSayimDsSayimIslemi(new ArrayList<demirbas_sayim>(),getContext());
        _aktif_is_emirleri_list.setAdapter(adapter);
        _aktif_is_emirleri_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                longClickedItemIndex=position;
                return false;
            }
        });
        registerForContextMenu(_aktif_is_emirleri_list);

        _imgHepsi = (ImageView)getView().findViewById(R.id.imgHepsi);
        _imgHepsi.playSoundEffect(0);
        _imgHepsi.setOnClickListener(new fn_imgHepsi());
        _txtHepsi = (TextView) getView().findViewById(R.id.txtHepsi);
        _txtHepsi.setTypeface(null,Typeface.BOLD);
        filtre_durum = 0;

        _imgBulunan = (ImageView)getView().findViewById(R.id.imgBulunan);
        _imgBulunan.playSoundEffect(0);
        _imgBulunan.setOnClickListener(new fn_imgBulunan());
        _txtBulunan = (TextView) getView().findViewById(R.id.txtBulunan);

        _imgEksik = (ImageView)getView().findViewById(R.id.imgEksik);
        _imgEksik.playSoundEffect(0);
        _imgEksik.setOnClickListener(new fn_imgEksik());
        _txtEksik = (TextView) getView().findViewById(R.id.txtEksik);

        _txtYuklemeMiktari = (TextView) getView().findViewById(R.id.txtYuklemeMiktari);

        fn_AyarlariYukle();
        kayıt_yapılacak_urun=new ArrayList<>();

        if (konum_sayim_isemri.getSayim_kod().equals(""))
        {
            new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                    .setTitleText("HATA")
                    .setContentText("Sayım dosyası alınamadı. Lütfen bir daha deneyiniz. BAĞLANTI HATASI.")
                    .setContentTextSize(20)
                    .setConfirmText("TAMAM")
                    .showCancelButton(false)
                    .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialogG sDialog) {
                            sDialog.dismissWithAnimation();

                            frg_demirbas_sayim_menu_panel fragmentyeni = new frg_demirbas_sayim_menu_panel();
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_demirbas_sayim_menu_panel").addToBackStack(null);
                            fragmentTransaction.commit();

                            return;
                        }
                    }).show();

        }
        try {

            request_demirbas_konum _Param1 = new request_demirbas_konum();
            _Param1.set_zsunucu_ip_adresi(_ayarsunucuip);
            _Param1.set_zaktif_alt_tesis(_ayaraktifalttesis);
            _Param1.set_zaktif_tesis(_ayaraktiftesis);
            _Param1.set_zsurum(_sbtVerisyon);
            _Param1.set_zkullaniciadi(_zkullaniciadi);
            _Param1.set_zsifre(_zsifre);
            _Param1.setAktif_sunucu(_ayaraktifsunucu);
            _Param1.setAktif_kullanici(_ayaraktifkullanici);

            _Param1.setDemirbasKonum(konum_sayim_isemri);

            Genel.showProgressDialog(getContext());
            List<demirbas_sayim> liste = persos.fn_sec_ek_demirbas_detay(_Param1);
            Genel.dismissProgressDialog();
            sayilacak_liste=new ArrayList<>(liste);

            if (sayilacak_liste.size() == 0) {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("UYARI")
                        .setContentText("Demirbaş listesinde hiç bir kayıt bulunmamaktadır.")
                        .setContentTextSize(20)
                        .setConfirmText("TAMAM")
                        .showCancelButton(false)
                        .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialogG sDialog) {
                                sDialog.dismissWithAnimation();

                                frg_demirbas_sayim_menu_panel fragmentyeni = new frg_demirbas_sayim_menu_panel();
                                FragmentManager fragmentManager = getFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_demirbas_sayim_menu_panel").addToBackStack(null);
                                fragmentTransaction.commit();

                                return;
                            }
                        }).show();

            }

            updateListviewItem();
        }catch (Exception e){
            Genel.printStackTrace(e,getContext());
            new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                    .setTitleText("HATA")
                    .setContentText("Sayım dosyası alınamıyor. Lütfen sorunu ilgili kişiye bildiriniz.")
                    .setContentTextSize(20)
                    .setConfirmText("TAMAM")
                    .showCancelButton(false)
                    .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialogG sDialog) {
                            sDialog.dismissWithAnimation();

                            frg_demirbas_sayim_menu_panel fragmentyeni = new frg_demirbas_sayim_menu_panel();
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_demirbas_sayim_menu_panel").addToBackStack(null);
                            fragmentTransaction.commit();

                            return;
                        }
                    }).show();
        }


    }

    public void barkodOkundu(String barkod){

        try
        {
            barkod = barkod.substring(barkod.length() - 24);
            if(barkod.startsWith("7377675")) {
                etiketDegerlendir(barkod);
            }
        }
        catch (Exception ex){
            Genel.printStackTrace(ex,getContext());
            new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                    .setTitleText("HATA")
                    .setContentTextSize(25)
                    .setContentText("Lütfen uygun bir ürün etiketi okutunuz. Ürün kaydı bulunamadı..")
                    .showCancelButton(false)
                    .show();
            isReadable = true;
        }

    }

    public void rfidOkundu(String rfid){
        try
        {
            if(rfid.startsWith("7377675")) {
                etiketDegerlendir(rfid);
            }
        }
        catch (Exception ex){
            Genel.printStackTrace(ex,getContext());
            new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                    .setTitleText("HATA")
                    .setContentTextSize(25)
                    .setContentText("Lütfen uygun bir ürün etiketi okutunuz. Ürün kaydı bulunamadı..")
                    .showCancelButton(false)
                    .show();
            isReadable = true;
        }

    }

    private void etiketDegerlendir(final String etiket)
    {
        int index=-1;
        for(demirbas_sayim ds : sayilacak_liste){
            if( etiket.contains(ds.getDs_demirbas_kod())){
                index=sayilacak_liste.indexOf(ds);
                break;
            }
        }

        if(index!=-1){
            if(sayilacak_liste.get(index).getDs_durum().equals("0")){
                sayilacak_liste.get(index).setDs_durum("1");
                kayıt_yapılacak_urun.add(sayilacak_liste.get(index));
            }
        }
        updateListviewItem();
    }


    private void updateListviewItem()
    {
        try
        {
            ArrayList<demirbas_sayim> sayilacak_liste_filtreli=new ArrayList<>();
            int sayilan_demirbas_sayisi=0;
            for(demirbas_sayim ds : sayilacak_liste){
                if(ds.getDs_durum().equals("1")){
                    sayilan_demirbas_sayisi++;
                    if(filtre_durum==1) {
                        sayilacak_liste_filtreli.add(ds);
                    }
                }else if( ds.getDs_durum().equals("0") && filtre_durum==2 ){
                    sayilacak_liste_filtreli.add(ds);
                }
                else if( ds.getDs_durum().equals("2") && filtre_durum==3 ){
                    sayilacak_liste_filtreli.add(ds);
                }
            }
            if(filtre_durum==0){
                sayilacak_liste_filtreli=sayilacak_liste;
            }

            if (adapter != null) {
                adapter.clear();
                adapter.addAll(sayilacak_liste_filtreli);
                adapter.notifyDataSetChanged();
            }

            _txtYuklemeMiktari.setText("SAYILAN ADET = " +sayilan_demirbas_sayisi);
        }
        catch (Exception ex)
        {
            Genel.printStackTrace(ex,getContext());
        }
    }



    private class fn_btnTamam implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Genel.lockButtonClick(view,getActivity());
            try
            {
                frg_demirbas_sayim_menu_panel fragmentyeni = new frg_demirbas_sayim_menu_panel();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_demirbas_sayim_menu_panel").addToBackStack(null);
                fragmentTransaction.commit();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    private class fn_imgBilgi implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Genel.lockButtonClick(view,getActivity());
            try
            {
                Genel.playButtonClikSound(getContext());
                String str = "";
                str += " BİNA : " + konum_sayim_isemri.getBina_adi() + "-" + konum_sayim_isemri.getBina_kod();
                str += "\r\n KAT : " + konum_sayim_isemri.getKat_adi() + "-" + konum_sayim_isemri.getKat_kod();
                str += "\r\n ODA : " + konum_sayim_isemri.getOda_adi() + "-" + konum_sayim_isemri.getOda_kod();
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.NORMAL_TYPE)
                        .setTitleText("SAYIM DETAYI")
                        .setContentText(str)
                        .setContentTextSize(20)
                        .setConfirmText("TAMAM")
                        .showCancelButton(false)
                        .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialogG sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();

            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }

    private class fn_imgHepsi implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(filtre_durum !=0) {
                _txtHepsi.setTypeface(null, Typeface.BOLD);
                _txtBulunan.setTypeface(null, Typeface.NORMAL);
                _txtEksik.setTypeface(null, Typeface.NORMAL);
                filtre_durum = 0;
                updateListviewItem();
            }
        }
    }

    private class fn_imgBulunan implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(filtre_durum !=1) {
                _txtHepsi.setTypeface(null, Typeface.NORMAL);
                _txtBulunan.setTypeface(null, Typeface.BOLD);
                _txtEksik.setTypeface(null, Typeface.NORMAL);
                filtre_durum = 1;
                updateListviewItem();
            }
        }
    }

    private class fn_imgEksik implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(filtre_durum !=2) {
                _txtHepsi.setTypeface(null, Typeface.NORMAL);
                _txtBulunan.setTypeface(null, Typeface.NORMAL);
                _txtEksik.setTypeface(null, Typeface.BOLD);
                filtre_durum = 2;
                updateListviewItem();
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Genel.playButtonClikSound(getContext());
        demirbas_sayim ds = adapter.getItem(longClickedItemIndex);
        switch(item.getItemId()) {
            case R.id.action_menu_demirbas_detayi:
                try
                {

                    String str = "";
                    str += "DEMİRBAŞ : " + ds.getDs_demirbas_ad_1() + "-" + ds.getDs_demirbas_ad_2() + "-" + ds.getDs_demirbas_ad_3();
                    str += "\r\n DEMİRBAŞ SAP KOD : " + ds.getDs_demirbas_kod();
                    str += "\r\n DEMİRBAŞ ESKİ KOD : " + ds.getDs_demirbas_eski_kod();
                    str += "\r\n DEMİRBAŞ BİNA : " + ds.getDs_bina_adi() + "-" + ds.getDs_bina_kod();
                    str += "\r\n DEMİRBAŞ KAT : " + ds.getDs_kat_adi() + "-" + ds.getDs_kat_kod();
                    str += "\r\n DEMİRBAŞ ODA : " + ds.getDs_oda_kod() + "-" + ds.getDs_oda_kod();
                    str += "\r\n ZİMMETLİ : " + ds.getDs_zimmetli_adi() + "-" + ds.getDs_zimmetli_id();
                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.NORMAL_TYPE)
                            .setTitleText("DEMİRBAŞ DETAY")
                            .setContentText(str)
                            .setContentTextSize(20)
                            .setConfirmText("TAMAM")
                            .showCancelButton(false)
                            .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialogG sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .show();

                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.menu_frg_da_sayim_islemi, menu);
    }
}
