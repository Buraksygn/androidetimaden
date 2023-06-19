package com.etimaden.SevkiyatIslemleri;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.GirisSayfasi;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cResponseResult.Sevkiyat_isemri;
import com.etimaden.ugr_demo.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class frg_arac_indirme_bilgi extends Fragment{



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

    public Sevkiyat_isemri aktif_sevk_isemri;

    TextView _txtAlici;
    TextView _txtBooking;
    TextView _txtAracPlakasi;
    TextView _txtKonteyner;
    TextView _txtSapKodu;
    TextView _txtUrunAdi;
    TextView _txttorbaAgirlik;
    TextView _txtPaletAgirlik ;
    TextView _txtBitenKalan ;

    public frg_arac_indirme_bilgi() {
        // Required empty public constructor
    }

    public static frg_arac_indirme_bilgi newInstance() {

        return new frg_arac_indirme_bilgi();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_arac_indirme_bilgi, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        _myIslem = new VeriTabani(getContext());

        _btngeri = (Button) getView().findViewById(R.id.btngeri);
        _btngeri.playSoundEffect(0);
        _btngeri.setOnClickListener(new fn_Geri());

        _txtAlici=(TextView)getView().findViewById(R.id.txtAlici);
        _txtBooking=(TextView)getView().findViewById(R.id.txtBooking);
        _txtAracPlakasi=(TextView)getView().findViewById(R.id.txtAracPlakasi);
        _txtKonteyner=(TextView)getView().findViewById(R.id.txtKonteyner);
        _txtSapKodu=(TextView)getView().findViewById(R.id.txtSapKodu);
        _txtUrunAdi=(TextView)getView().findViewById(R.id.txtUrunAdi);
        _txttorbaAgirlik=(TextView)getView().findViewById(R.id.txttorbaAgirlik);
        _txtPaletAgirlik=(TextView)getView().findViewById(R.id.txtPaletAgirlik);
        _txtBitenKalan=(TextView)getView().findViewById(R.id.txtBitenKalan);

        fn_DegerleriYaz();
    }

    public void fn_senddata(Sevkiyat_isemri v_aktif_sevk_isemri)
    {
        this.aktif_sevk_isemri=v_aktif_sevk_isemri;
    }

    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_aktif_isemri_indirme fragmentyeni = new frg_aktif_isemri_indirme();

            fragmentyeni.fn_senddata(aktif_sevk_isemri);

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_aktif_isemri_indirme").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private void fn_DegerleriYaz() {

        if (aktif_sevk_isemri.alici_isletme.equals(""))
        {
            _txtAlici.setText("ALICI : " +aktif_sevk_isemri.alici);
            _txtBooking.setText("BOOKING NO : " +aktif_sevk_isemri.bookingno);
        }
        else
        {
            _txtAlici.setText("ALICI : " +aktif_sevk_isemri.alici_isletme);
            _txtBooking.setText("");
        }

        _txtAracPlakasi.setText("ARAÇ PLAKASI : " + aktif_sevk_isemri.arac_plaka);

        if (!aktif_sevk_isemri.konteyner_turu.equals(""))
        {
            _txtKonteyner.setText("KONTEYNER  : " +aktif_sevk_isemri.kont_kodu);
        }

        _txtSapKodu.setText("SAP KODU : " +aktif_sevk_isemri.kod_sap);
        _txtUrunAdi.setText( "ÜRÜN ADI : " +  aktif_sevk_isemri.urun_adi);
        _txttorbaAgirlik.setText("TORBA AĞIRLIĞI : " + aktif_sevk_isemri.miktar_torba);
        _txtPaletAgirlik.setText("PALET AĞIRLIĞI : " + aktif_sevk_isemri.palet_agirligi);
        _txtBitenKalan.setText("Y.ADET/ İŞEMRİ K.ADET : " +aktif_sevk_isemri.yapilan_adet + " / " + aktif_sevk_isemri.kalan_palet_sayisi);
    }


}
