package com.etimaden.SayimIslemleri.Depo_sayim_islemi;

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
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.GirisSayfasi;
import com.etimaden.SayimIslemleri.frg_sayim_menu_panel;
import com.etimaden.adapter.apmblSayimIslemleriAktifDepoSayimIsemriSecimi;
import com.etimaden.adapter.apmblSayimIslemleriDepoSayimIslemi;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cResponseResult.Urun_sevkiyat;
import com.etimaden.genel.Genel;
import com.etimaden.genel.SweetAlertDialogG;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.IFTag;
import com.etimaden.persosclass.aktarim;
import com.etimaden.persosclass.malzeme_sayim_isemri;
import com.etimaden.request.request_aktarim_list;
import com.etimaden.request.request_bos;
import com.etimaden.request.request_string;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;
import java.util.List;


public class frg_depo_sayim_islemi extends Fragment {

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
    Button _btnListeyiKaydet;
    ListView _aktif_is_emirleri_list;
    Button _btngeri;

    boolean isReadable = true;
    ArrayList<aktarim> urun_listesi;
    malzeme_sayim_isemri sayim_isemri = null;

    private apmblSayimIslemleriDepoSayimIslemi adapter;

    public frg_depo_sayim_islemi() {
        // Required empty public constructor
    }

    public static frg_depo_sayim_islemi newInstance()
    {
        return new frg_depo_sayim_islemi();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.frg_depo_sayim_islemi, container, false);
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

    public void fn_senddata(malzeme_sayim_isemri secilen_sevk) {
        this.sayim_isemri=secilen_sevk;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        new VeriTabani(getContext()).fn_EpcTemizle();

        _myIslem = new VeriTabani(getContext());

        ((GirisSayfasi) getActivity()).fn_ModRFID();
        ((GirisSayfasi) getActivity()).fn_ListeTemizle();

        _txtBaslik = (TextView) getView().findViewById(R.id.txtBaslik);

        _txtBaslik.setText(" SAP KODU : "+this.sayim_isemri.getMls_kod_sap()+" \n İŞLETME/DEPO : "+this.sayim_isemri.getMls_kod_isletme()+" / "+this.sayim_isemri.getMls_kod_depo()+" \n SAYIM LİSTESİ");

        _btnListeyiKaydet = (Button)getView().findViewById(R.id.btnListeyiKaydet);
        _btnListeyiKaydet.playSoundEffect(0);
        _btnListeyiKaydet.setOnClickListener(new fn_btnListeyiKaydet());

        _btngeri = (Button)getView().findViewById(R.id.btncikis);
        _btngeri.playSoundEffect(0);
        _btngeri.setOnClickListener(new fn_Geri());

        _aktif_is_emirleri_list = (ListView) getView().findViewById(R.id.aktif_is_emirleri_list);

        adapter=new apmblSayimIslemleriDepoSayimIslemi(new ArrayList<IFTag>(),getContext());
        _aktif_is_emirleri_list.setAdapter(adapter);

        fn_AyarlariYukle();
        urun_listesi= new ArrayList<aktarim>();

        request_string _Param1= new request_string();
        _Param1.set_zsunucu_ip_adresi(_ayarsunucuip);
        _Param1.set_zaktif_alt_tesis(_ayaraktifalttesis);
        _Param1.set_zaktif_tesis(_ayaraktiftesis);
        _Param1.set_zsurum(_sbtVerisyon);
        _Param1.set_zkullaniciadi(_zkullaniciadi);
        _Param1.set_zsifre(_zsifre);
        _Param1.setAktif_sunucu(_ayaraktifsunucu);
        _Param1.setAktif_kullanici(_ayaraktifkullanici);

        _Param1.set_value(sayim_isemri.getMls_kod_sap());

        Genel.showProgressDialog(getContext());
        String result = persos.fn_sorgula_sayım_uygunluk(_Param1);
        Genel.dismissProgressDialog();

        if(!result.equals("1")){
            new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                    .setTitleText("UYARI")
                    .setContentText("Bu sayım işlemine devam edemezsiniz. Sayım iş emri kapatılmıştır.")
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
        }

    }

    public void rfidOkundu(String rfid){
        try
        {
            if(rfid.startsWith("737767")) {
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


    private void updateListviewItem(IFTag tag)
    {
        try
        {
            if (adapter != null) {
                adapter.insert(tag,0);
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
