package com.etimaden.SayimIslemleri.Demirbas_sayim_islemi.Demirbas_arama;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.GirisSayfasi;
import com.etimaden.SayimIslemleri.Demirbas_sayim_islemi.Sayim_islemleri.frg_ds_sayim_islemi;
import com.etimaden.SayimIslemleri.Demirbas_sayim_islemi.frg_demirbas_sayim_menu_panel;
import com.etimaden.SayimIslemleri.Depo_sayim_islemi.frg_depo_sayim_islemi;
import com.etimaden.SayimIslemleri.Depo_sayim_islemi.frg_depo_sayim_menu_panel;
import com.etimaden.SevkiyatIslemleri.frg_aktif_isemri_yukleme;
import com.etimaden.adapter.apmblSayimIslemleriAktifDepoSayimIsemriSecimi;
import com.etimaden.adapter.apmblSayimIslemleriDaIsemriSecimi;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cResponseResult.Urun_sevkiyat;
import com.etimaden.genel.Genel;
import com.etimaden.genel.SweetAlertDialogG;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.Demirbas_Konum;
import com.etimaden.persosclass.malzeme_sayim_isemri;
import com.etimaden.request.request_bos;
import com.etimaden.request.request_string;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;
import java.util.List;


public class frg_da_isemri_secimi extends Fragment {

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

    private apmblSayimIslemleriDaIsemriSecimi adapter;

    public frg_da_isemri_secimi() {
        // Required empty public constructor
    }

    public static frg_da_isemri_secimi newInstance()
    {
        return new frg_da_isemri_secimi();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.frg_da_isemri_secimi, container, false);
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

        ((GirisSayfasi) getActivity()).fn_ModBarkod();
        _myIslem = new VeriTabani(getContext());
        _myIslem.fn_EpcTemizle();
        ((GirisSayfasi) getActivity()).fn_ListeTemizle();

        fn_AyarlariYukle();

        _btnOdaSec = (Button)getView().findViewById(R.id.btnOdaSec);
        _btnOdaSec.playSoundEffect(0);
        _btnOdaSec.setOnClickListener(new fn_btnOdaSec());

        _btngeri = (Button)getView().findViewById(R.id.btnGeri);
        _btngeri.playSoundEffect(0);
        _btngeri.setOnClickListener(new fn_Geri());

        _btnOkuma = (Button)getView().findViewById(R.id.btnOkuma);
        _btnOkuma.playSoundEffect(0);
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

        adapter=new apmblSayimIslemleriDaIsemriSecimi(new ArrayList<Demirbas_Konum>(),getContext());
        _aktif_is_emirleri_list.setAdapter(adapter);


        oda_listesi= new ArrayList<Demirbas_Konum>();
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
                    if(barkod.equals(oda + w.getBina_kod() + w.getKat_kod() + w.getOda_kod())){
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

            String oda = "7377678"; //737767-6(bina)-7(kat)-8(oda)-bina(4)-kat(4)-oda(4)
            Demirbas_Konum konum = null;
            try
            {
                for(Demirbas_Konum w : oda_listesi){
                    if(rfid.equals(oda + w.getBina_kod() + w.getKat_kod() + w.getOda_kod())){
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

                    frg_da_sayim_islemi fragmentyeni = new frg_da_sayim_islemi();
                    fragmentyeni.fn_senddata(secilen_oda);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_da_sayim_islemi").addToBackStack(null);
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

    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_demirbas_arama_menu_panel fragmentyeni = new frg_demirbas_arama_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_demirbas_arama_menu_panel").addToBackStack(null);
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
