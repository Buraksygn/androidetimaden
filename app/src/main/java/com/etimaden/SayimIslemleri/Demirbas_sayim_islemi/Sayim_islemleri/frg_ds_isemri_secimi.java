package com.etimaden.SayimIslemleri.Demirbas_sayim_islemi.Sayim_islemleri;

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

import com.etimaden.GirisSayfasi;
import com.etimaden.SayimIslemleri.Demirbas_sayim_islemi.frg_demirbas_sayim_menu_panel;
import com.etimaden.adapter.apmblSayimIslemleriDaIsemriSecimi;
import com.etimaden.adapter.apmblSayimIslemleriDsIsemriSecimi;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.genel.Genel;
import com.etimaden.genel.SweetAlertDialogG;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.Demirbas_Konum;
import com.etimaden.persosclass.Zayi_urun;
import com.etimaden.persosclass.demirbas_sayim;
import com.etimaden.request.request_bos_aktif_isletme_esleme;
import com.etimaden.request.request_demirbas_konum;
import com.etimaden.request.request_string;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;
import java.util.List;


public class frg_ds_isemri_secimi extends Fragment {

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


    Button _btnOdaSec;
    ListView _aktif_is_emirleri_list;
    Button _btngeri;
    Button _btnOkuma;

    boolean isReadable = true;
    ArrayList<Demirbas_Konum> oda_listesi;
    Demirbas_Konum _Secili = null;

    private apmblSayimIslemleriDsIsemriSecimi adapter;

    public frg_ds_isemri_secimi() {
        // Required empty public constructor
    }

    public static frg_ds_isemri_secimi newInstance()
    {
        return new frg_ds_isemri_secimi();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //return inflater.inflate(R.layout.frg_da_isemri_secimi, container, false);
        return inflater.inflate(R.layout.frg_ds_isemri_secimi, container, false);
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

        _myIslem = new VeriTabani(getContext());
        _myIslem.fn_EpcTemizle();
        ((GirisSayfasi) getActivity()).fn_ListeTemizle();

        fn_AyarlariYukle();
        _btnOdaSec = (Button)getView().findViewById(R.id.btnOdaSec);
        _btnOdaSec.playSoundEffect(SoundEffectConstants.CLICK);
        _btnOdaSec.setOnClickListener(new fn_btnOdaSec());

        _btngeri = (Button)getView().findViewById(R.id.btnGeri);
        _btngeri.playSoundEffect(SoundEffectConstants.CLICK);
        _btngeri.setOnClickListener(new fn_Geri());

        _btnOkuma = (Button)getView().findViewById(R.id.btnOkuma);
        _btnOkuma.playSoundEffect(SoundEffectConstants.CLICK);
        _btnOkuma.setOnClickListener(new fn_okumaDegistir());

        ((GirisSayfasi) getActivity()).fn_ModRFID();
        _btnOkuma.setText("RFID");

        _aktif_is_emirleri_list = (ListView) getView().findViewById(R.id.aktif_is_emirleri_list);

        _aktif_is_emirleri_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _Secili = oda_listesi.get(position);
            }
        });

        _aktif_is_emirleri_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                _Secili = oda_listesi.get(position);
                fn_listview_longclick();
                return false;
            }
        });

        adapter=new apmblSayimIslemleriDsIsemriSecimi(new ArrayList<Demirbas_Konum>(),getContext());
        _aktif_is_emirleri_list.setAdapter(adapter);


        oda_listesi= new ArrayList<Demirbas_Konum>();
        fn_AyarlariYukle();
        binaDegerlendir();
    }

    private void binaDegerlendir()
    {
        try
        {

            Genel.showProgressDialog(getContext());
            oda_listesi=_myIslem.fn_sec_ds_isemri_listesi();
            Genel.dismissProgressDialog();

            updateListviewItem();

        }
        catch (Exception ex)
        {
            Genel.printStackTrace(ex,getContext());
            new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                    .setTitleText("BAĞLANTI HATASI")
                    .setContentTextSize(25)
                    .setContentText("Oda listesi alınamadı.")
                    .showCancelButton(false)
                    .show();
        }
    }

    private void updateListviewItem()
    {
        try
        {
            if (adapter != null) {
                adapter.clear();
                adapter.addAll(oda_listesi);
                adapter.notifyDataSetChanged();
            }

        }
        catch (Exception ex)
        {
            Genel.printStackTrace(ex,getContext());
        }
    }

    public void barkodOkundu(String barkod)
    {
        try
        {
            barkod = barkod.substring(barkod.length()-24);
            if (!isReadable)
            {
                return;
            }
            isReadable = false;
            Genel.playQuestionSound(getContext());

            String oda = "7377678";
            Demirbas_Konum konum = null;
            try
            {
                for(Demirbas_Konum w : oda_listesi){
                    //if(barkod.equals(oda + w.getBina_kod() + w.getKat_kod() + w.getOda_kod())){
                    if(barkod.contains(w.getDs_TEKNIK())){
                        konum = w;
                        break;
                    }
                }
            }
            catch (Exception ex)
            { }
            if (konum != null)
            {

                frg_ds_sayim_islemi fragmentyeni = new frg_ds_sayim_islemi();
                fragmentyeni.fn_senddata(konum);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_ds_sayim_islemi").addToBackStack(null);
                fragmentTransaction.commit();

            }
            else
            {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("HATALI SEÇİM")
                        .setContentTextSize(25)
                        .setContentText("Seçim yapılan konum kaydı aktif işlemler içinde bulunamadı.")
                        .showCancelButton(false)
                        .show();
                isReadable = true;
                return;
            }

        }
        catch (Exception ex)
        {
            Genel.printStackTrace(ex,getContext());
            isReadable = true;
        }
    }

    public void rfidOkundu(String rfid)
    {
        try
        {
            if (!isReadable)
            {
                return;
            }
            isReadable = false;
            Genel.playQuestionSound(getContext());

            String oda = "7377678";
            Demirbas_Konum konum = null;
            try
            {
                for(Demirbas_Konum w : oda_listesi){
                    //if(rfid.equals(oda + w.getBina_kod() + w.getKat_kod() + w.getOda_kod())){
                    if(rfid.contains(w.getDs_TEKNIK())){
                        konum = w;
                        break;
                    }
                }
            }
            catch (Exception ex)
            { }
            if (konum != null)
            {

                frg_ds_sayim_islemi fragmentyeni = new frg_ds_sayim_islemi();
                fragmentyeni.fn_senddata(konum);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_ds_sayim_islemi").addToBackStack(null);
                fragmentTransaction.commit();

            }
            else
            {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("HATALI SEÇİM")
                        .setContentTextSize(25)
                        .setContentText("Seçim yapılan konum kaydı aktif işlemler içinde bulunamadı.")
                        .showCancelButton(false)
                        .show();
                isReadable = true;
                return;
            }

        }
        catch (Exception ex)
        {
            Genel.printStackTrace(ex,getContext());
            isReadable = true;
        }
    }



    private class fn_btnOdaSec implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Genel.lockButtonClick(view,getActivity());
            try
            {

                if (_Secili!=null) {
                    Demirbas_Konum secilen_oda = _Secili;

                    frg_ds_sayim_islemi fragmentyeni = new frg_ds_sayim_islemi();
                    fragmentyeni.fn_senddata(secilen_oda);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_ds_sayim_islemi").addToBackStack(null);
                    fragmentTransaction.commit();


                }
                else
                {
                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                            .setTitleText("UYARI")
                            .setContentTextSize(25)
                            .setContentText("SEÇİM İŞLEMİ YAPILMADI.")
                            .showCancelButton(false)
                            .show();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("UYARI")
                        .setContentTextSize(25)
                        .setContentText("SEÇİM İŞLEMİ YAPILMADI.")
                        .showCancelButton(false)
                        .show();
            }

        }
    }

    private void fn_listview_longclick(){

        try {
            final Demirbas_Konum secilenKonum = _Secili;
            if (_Secili != null ) {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                        .setTitleText("SİL")
                        .setContentText( _Secili.getSayim_kod_sap() + " nolu iş emri silmek istiyor musunuz ?")
                        .setContentTextSize(20)
                        .setConfirmText("EVET")
                        .setCancelText("HAYIR")
                        .showCancelButton(true)
                        .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialogG sDialog) {
                                sDialog.dismissWithAnimation();

                                Genel.showProgressDialog(getContext());
                                _myIslem.fn_ds_sil_detay(secilenKonum);
                                Genel.dismissProgressDialog();

                                frg_ds_isemri_secimi fragmentyeni = new frg_ds_isemri_secimi();
                                FragmentManager fragmentManager = getFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_ds_isemri_secimi").addToBackStack(null);
                                fragmentTransaction.commit();

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


            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_demirbas_sayim_menu_panel fragmentyeni = new frg_demirbas_sayim_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_demirbas_sayim_menu_panel").addToBackStack(null);
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
            } else {
                ((GirisSayfasi) getActivity()).fn_ModBarkod();
                _btnOkuma.setText("KAREKOD");
            }

            Genel.dismissProgressDialog();
        }
    }
}
