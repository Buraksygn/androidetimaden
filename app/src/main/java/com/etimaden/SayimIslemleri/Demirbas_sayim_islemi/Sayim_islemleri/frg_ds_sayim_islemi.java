package com.etimaden.SayimIslemleri.Demirbas_sayim_islemi.Sayim_islemleri;

import static com.etimaden.cSabitDegerler._ipAdresi3G;
import static com.etimaden.cSabitDegerler._sbtVerisyon;
import static com.etimaden.cSabitDegerler._zkullaniciadi;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.etimaden.SayimIslemleri.frg_sayim_menu_panel;
import com.etimaden.adapter.apmblDemirbasSayimDsSayimIslemi;
import com.etimaden.adapter.apmblSayimIslemleriDepoSayimIslemi;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.genel.Genel;
import com.etimaden.genel.SweetAlertDialogG;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.Demirbas_Konum;
import com.etimaden.persosclass.IFTag;
import com.etimaden.persosclass.aktarim;
import com.etimaden.persosclass.demirbas_sayim;
import com.etimaden.persosclass.malzeme_sayim_isemri;
import com.etimaden.request.request_aktarim_list;
import com.etimaden.request.request_string;
import com.etimaden.request.request_string_aktif_isletme_esleme;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;
import java.util.Iterator;


public class frg_ds_sayim_islemi extends Fragment {

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
    Button _btnListeyiKaydet;
    ListView _aktif_is_emirleri_list;
    SeekBar _sbPower;
    TextView _txtPower;
    ImageView _imgHepsi;
    ImageView _imgBulunan;
    ImageView _imgEksik;
    ImageView _imgKullanimDisi;
    TextView _txtHepsi;
    TextView _txtBulunan;
    TextView _txtEksik;
    TextView _txtKullanimDisi;
    TextView _txtYuklemeMiktari;

    boolean isReadable = true;
    ArrayList<aktarim> urun_listesi;
    ArrayList<demirbas_sayim> sayilacak_liste;
    Demirbas_Konum konum_sayim_isemri = null;
    int filtre_durum = 0;

    private apmblDemirbasSayimDsSayimIslemi adapter;

    public frg_ds_sayim_islemi() {
        // Required empty public constructor
    }

    public static frg_ds_sayim_islemi newInstance()
    {
        return new frg_ds_sayim_islemi();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.frg_ds_sayim_islemi, container, false);
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

        ((GirisSayfasi) getActivity()).fn_ModRFID();
        ((GirisSayfasi) getActivity()).fn_ListeTemizle();
        ((GirisSayfasi) getActivity()).fn_GucAyarla(250);

        _txtBaslik = (TextView) getView().findViewById(R.id.txtBaslik);
        _txtBaslik.setText("SAYIM LİSTESİ");

        _imgBilgi = (ImageView)getView().findViewById(R.id.imgBilgi);
        _imgBilgi.playSoundEffect(0);
        _imgBilgi.setOnClickListener(new fn_imgBilgi());

        _btnListeyiKaydet = (Button)getView().findViewById(R.id.btnListeyiKaydet);
        _btnListeyiKaydet.playSoundEffect(0);
        _btnListeyiKaydet.setOnClickListener(new fn_btnListeyiKaydet());


        _aktif_is_emirleri_list = (ListView) getView().findViewById(R.id.aktif_is_emirleri_list);
        adapter=new apmblDemirbasSayimDsSayimIslemi(new ArrayList<demirbas_sayim>(),getContext());
        _aktif_is_emirleri_list.setAdapter(adapter);

        _sbPower = (SeekBar)getView().findViewById(R.id.sbPower);
        _sbPower.playSoundEffect(0);
        _sbPower.setOnSeekBarChangeListener(new fn_sbPower());

        _txtPower = (TextView) getView().findViewById(R.id.txtPower);
        _txtPower.setText("250 DB");

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

        _imgKullanimDisi = (ImageView)getView().findViewById(R.id.imgKullanimDisi);
        _imgKullanimDisi.playSoundEffect(0);
        _imgKullanimDisi.setOnClickListener(new fn_imgKullanimDisi());
        _txtKullanimDisi = (TextView) getView().findViewById(R.id.txtKullanimDisi);

        _txtYuklemeMiktari = (TextView) getView().findViewById(R.id.txtYuklemeMiktari);

        fn_AyarlariYukle();
        urun_listesi= new ArrayList<aktarim>();

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
            sayilacak_liste = _myIslem.fn_sec_ds_detay(this.konum_sayim_isemri);

            Iterator<demirbas_sayim> itr = sayilacak_liste.iterator();
            while (itr.hasNext()) {
                demirbas_sayim ds = itr.next();
                if (ds.getDs_durum().equals("31")) {
                    itr.remove();
                }
            }

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
            //updateListviewItem();
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

    public void rfidOkundu(String rfid){
        try
        {
            /*if(rfid.startsWith("737767")) {
                aktarim akt = new aktarim();
                akt.akt_sym_no = "0";
                akt.akt_urn_depo = sayim_isemri.getMls_kod_depo();
                akt.akt_sym_isletme = sayim_isemri.getMls_kod_isletme();
                akt.akt_urn_palet_rfid = rfid;
                akt.akt_urn_rfid = rfid;
                akt.akt_urn_serino = rfid.substring(rfid.length()-14);
                akt.akt_urn_palet_serino = rfid.substring(rfid.length()-14);;
                akt.akt_user_id = _ayaraktifkullanici;
                akt.akt_kod_sap = sayim_isemri.getMls_kod_sap();
                akt.akt_kod_isemri = sayim_isemri.getMls_kod();
                akt.akt_isemri_detay = sayim_isemri.getMsd_id();
                akt.akt_kod_urun = sayim_isemri.getMsd_kod_urun();
                akt.akt_aktarimdurumu = "0";
                akt.akt_aktarimtipi = "30";

                urun_listesi.add(akt);
                IFTag tag = new IFTag();
                tag.setEPC(rfid);
                updateListviewItem(tag);
            }*/

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


    private void updateListviewItem(IFTag tag)
    {
        try
        {
            if (adapter != null) {
                //adapter.insert(tag,0);
                adapter.notifyDataSetChanged();
            }

        }
        catch (Exception ex)
        {
            Genel.printStackTrace(ex,getContext());
        }
    }



    private class fn_btnListeyiKaydet implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Genel.lockButtonClick(view,getActivity());
            try
            {
                request_aktarim_list _Param1= new request_aktarim_list();
                _Param1.set_zsunucu_ip_adresi(_ayarsunucuip);
                _Param1.set_zaktif_alt_tesis(_ayaraktifalttesis);
                _Param1.set_zaktif_tesis(_ayaraktiftesis);
                _Param1.set_zsurum(_sbtVerisyon);
                _Param1.set_zkullaniciadi(_zkullaniciadi);
                _Param1.set_zsifre(_zsifre);
                _Param1.setAktif_sunucu(_ayaraktifsunucu);
                _Param1.setAktif_kullanici(_ayaraktifkullanici);

                _Param1.setAktarimListesi(urun_listesi);

                Genel.showProgressDialog(getContext());
                Boolean result = persos.fn_aktar_malzeme_sayim_listesi(_Param1);
                Genel.dismissProgressDialog();

                if(result){
                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.SUCCESS_TYPE)
                            .setTitleText("UYARI")
                            .setContentText("Kaydetme işlemi başarılı bir şekilde tamamlanmıştır.")
                            .setContentTextSize(20)
                            .setConfirmText("TAMAM")
                            .showCancelButton(false)
                            .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialogG sDialog) {
                                    sDialog.dismissWithAnimation();

                                    frg_sayim_menu_panel fragmentyeni = new frg_sayim_menu_panel();
                                    FragmentManager fragmentManager = getFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_sayim_menu_panel").addToBackStack(null);
                                    fragmentTransaction.commit();

                                    return;
                                }
                            }).show();
                }else{
                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                            .setTitleText("HATA")
                            .setContentTextSize(25)
                            .setContentText("Kaydetme işlemi başarısız tekrar deneyiniz.")
                            .showCancelButton(false)
                            .show();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("UYARI")
                        .setContentTextSize(25)
                        .setContentText("Kaydetme işlemi sırasında hata oluştu.")
                        .showCancelButton(false)
                        .show();
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

    private class fn_sbPower implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) { // If the changes are from the User only then accept the changes
                ((GirisSayfasi) getActivity()).fn_GucAyarla(progress);
                _txtPower.setText(progress+" DB");
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {}
    }

    private class fn_imgHepsi implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(filtre_durum !=0) {
                _txtHepsi.setTypeface(null, Typeface.BOLD);
                _txtBulunan.setTypeface(null, Typeface.NORMAL);
                _txtEksik.setTypeface(null, Typeface.NORMAL);
                _txtKullanimDisi.setTypeface(null, Typeface.NORMAL);
                filtre_durum = 0;
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
                _txtKullanimDisi.setTypeface(null, Typeface.NORMAL);
                filtre_durum = 1;
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
                _txtKullanimDisi.setTypeface(null, Typeface.NORMAL);
                filtre_durum = 2;
            }
        }
    }

    private class fn_imgKullanimDisi implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(filtre_durum !=3) {
                _txtHepsi.setTypeface(null, Typeface.NORMAL);
                _txtBulunan.setTypeface(null, Typeface.NORMAL);
                _txtEksik.setTypeface(null, Typeface.NORMAL);
                _txtKullanimDisi.setTypeface(null, Typeface.BOLD);
                filtre_durum = 3;
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
