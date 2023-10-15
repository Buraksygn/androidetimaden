package com.etimaden.SevkiyatIslemleri;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.GirisSayfasi;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cResponseResult.Sevkiyat_isemri;
import com.etimaden.genel.Genel;
import com.etimaden.genel.SweetAlertDialogG;
import com.etimaden.ugr_demo.R;


public class frg_konteyner_bulundu extends Fragment {


    SweetAlertDialogG pDialog;

    String arac_plaka="";
    String arac_kod="";

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
    Button _btngeri;

    TextView _txtAciklama;
    TextView _txtdepoadi;

    Button _btnileri;
    Button _btnIsEmriDegistir;

    ImageView imageView6;

    public Sevkiyat_isemri aktif_sevk_isemri ;

    public frg_konteyner_bulundu() {
        // Required empty public constructor
    }
    public static frg_konteyner_bulundu newInstance() {

        return new frg_konteyner_bulundu();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_konteyner_bulundu, container, false);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        _myIslem = new VeriTabani(getContext());

        ((GirisSayfasi)getActivity()).fn_ModRFID();

        _txtAciklama=(TextView)getView().findViewById(R.id.txtaciklama);
        _txtdepoadi=(TextView)getView().findViewById(R.id.txtdepoadi);

        fn_AyarlariYukle();

        new VeriTabani(getContext()).fn_EpcTemizle();

        ((GirisSayfasi) getActivity()).fn_ListeTemizle();
        ((GirisSayfasi) getActivity()).fn_GucAyarla(248);

        _btngeri = (Button) getView().findViewById(R.id.btngeri);
        _btngeri.playSoundEffect(0);
        _btngeri.setOnClickListener(new fn_Geri());

        _btnileri = (Button) getView().findViewById(R.id.btnileri);
        _btnileri.playSoundEffect(0);
        _btnileri.setOnClickListener(new fn_Ileri());

        _btnIsEmriDegistir=(Button)getView().findViewById(R.id.btnisemridegistir);
        _btnIsEmriDegistir.playSoundEffect(0);
        _btnIsEmriDegistir.setOnClickListener(new fn_IsemirDegistir());

        fn_AracBilgileri();

        imageView6 = (ImageView)getView().findViewById(R.id.imageView6);
        imageView6.playSoundEffect(0);
        imageView6.setOnClickListener(new fn_DetayGoster());

    }

    private void fn_AracBilgileri()
    {
        /*
        String _Aciklama="KONTEYNER İŞ EMRİ DETAYI"+ "\n";

        _Aciklama="";

        if (aktif_sevk_isemri.alici_isletme.equals(""))
        {
            _Aciklama += "ALICI : " + aktif_sevk_isemri.alici + "\n";
            _Aciklama += "BOOKING NO : " + aktif_sevk_isemri.bookingno + "\n";
        }
        else
        {
            _Aciklama += "ALICI : " + aktif_sevk_isemri.alici_isletme + "\n";
        }

        _Aciklama += "ARAÇ PLAKASI : " + aktif_sevk_isemri.arac_plaka + "\n\n";

        if (!aktif_sevk_isemri.konteyner_turu.equals(""))
        {
            _Aciklama += "KONTEYNER     : " + aktif_sevk_isemri.kont_kodu + "\n";
        }

        _Aciklama += "SAP KODU        : " + aktif_sevk_isemri.kod_sap + "\n";
        _Aciklama += "ÜRÜN ADI         : " + aktif_sevk_isemri.urun_adi + "\n";
        _Aciklama += "TORBA AĞIRLIĞI : " + aktif_sevk_isemri.miktar_torba + "\n";
        _Aciklama += "PALET AĞIRLIĞI : " + aktif_sevk_isemri.palet_agirligi + "\n";
        _Aciklama += "YAPILAN ADET \\ MİKTAR : " + aktif_sevk_isemri.yapilan_adet +"  \\  "+ aktif_sevk_isemri.yapilan_miktar+ "\n";
        //_Aciklama += "YAPILAN MİKTAR : " + aktif_sevk_isemri.yapilan_miktar + "\n";
        _Aciklama += "İŞEMRİ KALAN ADET \\ MİKTAR : " + aktif_sevk_isemri.kalan_palet_sayisi + "  \\  "+aktif_sevk_isemri.kalan_agirlik +"\n";
       // _Aciklama += "İŞEMRİ KALAN MİKTAR : " + aktif_sevk_isemri.kalan_agirlik + "\n";
*/

        String _Aciklama = "ARAÇ İŞ EMRİ DETAYI" + "\n";

        _Aciklama="";
        _Aciklama += "ARAÇ PLAKASI : " + aktif_sevk_isemri.arac_plaka + "\n\n";

        if (aktif_sevk_isemri.alici_isletme.equals(""))
        {
            _Aciklama += "ALICI : " + aktif_sevk_isemri.alici + "\n\n";
            _Aciklama += "SEVKİYAT NO : " + aktif_sevk_isemri.kod_sap+ "\n\n";
            _Aciklama += "BOOKING NO : " + aktif_sevk_isemri.bookingno+ "\n\n";
        }
        else
        {
            _Aciklama += "ALICI : " + aktif_sevk_isemri.alici_isletme + "\n\n";
            _Aciklama += "SEVKİYAT NO : " + aktif_sevk_isemri.kod_sap+ "\n\n";
        }

        _Aciklama += "ÜRÜN ADI : " + aktif_sevk_isemri.urun_adi+ "\n\n";

        _txtAciklama.setText(_Aciklama);


        String _DepoYazi="("+aktif_sevk_isemri.depo_adi+") DEPO";

        _txtdepoadi.setText(_DepoYazi);

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
    }


    public void fn_senddata(Sevkiyat_isemri v_aktif_sevk_isemri)
    {
        aktif_sevk_isemri=v_aktif_sevk_isemri;
    }

    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_konteyner_yukleme_aktivasyon fragmentyeni = new frg_konteyner_yukleme_aktivasyon();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_konteyner_yukleme_aktivasyon").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_Ileri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Genel.lockButtonClick(view,getActivity());

            int kalan_palet_sayisi = 0;
            int kalan_kilo = 0;

            try{
                kalan_palet_sayisi = Integer.parseInt(aktif_sevk_isemri.kalan_palet_sayisi);
            }catch (Exception ex)
            {

            }

            try
            {
                kalan_kilo = (Integer.parseInt(aktif_sevk_isemri.kalan_agirlik));


            }catch (Exception ex)
            {

            }





                   /* if (kalan_palet_sayisi < 0)
                    {
                        Program.giveHataMesaji("SİPARİŞ MİKTAR UYARISI", "Seçilmiş iş uygun değildir.Sipariş miktarı değerlerini kontrol ediniz.", "Sistem üzerinde sipariş miktarı tamamlanmış veya iptal edilmiş bir iş olabilir.");
                        return;
                    }
                    else*/
                   if (kalan_kilo < 0)
                {

                    SweetAlertDialogG pHataDialog = new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE);
                    pHataDialog.setTitleText("SİPARİŞ MİKTAR UYARISI");
                    pHataDialog.setContentText("Seçilmiş iş uygun değildir.Sipariş miktarı değerlerini kontrol ediniz.Sistem üzerinde sipariş miktarı tamamlanmış veya iptal edilmiş bir iş olabilir.");
                    pHataDialog.setContentTextSize(22);
                    //pDialog.setContentText(_TempEpc);
                    //pHataDialog.setCancelable(false);
                    pHataDialog.show();

                }
                   else
                   {
                       frg_konteyner_onayla fragmentyeni = new frg_konteyner_onayla();
                       fragmentyeni.fn_senddata(aktif_sevk_isemri);
                       FragmentManager fragmentManager = getFragmentManager();
                       FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                       fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_konteyner_onayla").addToBackStack(null);
                       fragmentTransaction.commit();

                     //  Program.setPage(new Konteyner_onayla(aktif_sevk_isemri));
                   }



        }
    }

    private class fn_IsemirDegistir implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_isemri_degistir fragmentyeni = new frg_isemri_degistir();
            fragmentyeni.fn_senddata(aktif_sevk_isemri,1);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_isemri_degistir").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_DetayGoster implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_konteyner_emir_detay fragmentyeni = new frg_konteyner_emir_detay();
            fragmentyeni.fn_senddata(aktif_sevk_isemri);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_konteyner_emir_detay").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}
