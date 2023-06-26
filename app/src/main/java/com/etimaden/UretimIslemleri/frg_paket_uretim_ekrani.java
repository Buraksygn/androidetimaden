package com.etimaden.UretimIslemleri;

import android.opengl.Visibility;
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
import com.etimaden.SevkiyatIslemleri.frg_aktif_isemri_secimi;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cIslem.cTanimEnum;
import com.etimaden.cResponseResult.DEPOTag;
import com.etimaden.ugr_demo.R;

import org.json.JSONObject;

import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;


public class frg_paket_uretim_ekrani extends Fragment {


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


    String _OnlineUrlsecEtiket = "";


    Button _btnEtiketsizUretim;
    Button _btnIptal;
    Button _btncikis;

    TextView _txtPaletId;
    TextView _txtParcaBir;
    TextView _txtAra;
    TextView _txtParcaIki;

    cTanimEnum._eDurum _IslemDurum = cTanimEnum._eDurum.ISLEM_YOK;

    int _islemDurumu = 0;
    //  0 : etiket okutma bekleniyor
    //  1 : bigbag üretim işlemi yapılıyor
    //  2 : paketli palet üretim işlemi yapılıyor
    //  3 : paket üretim işlemi yapılıyor



    public static frg_paket_uretim_ekrani newInstance() {

        return new frg_paket_uretim_ekrani();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_paket_uretim_ekrani , container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((GirisSayfasi) getActivity()).fn_ModBarkod();

        _myIslem = new VeriTabani(getContext());

        fn_AyarlariYukle();

        _myIslem.fn_EpcTemizle();

        ((GirisSayfasi) getActivity()).fn_ListeTemizle();

        _btnEtiketsizUretim = (Button)getView().findViewById(R.id.btnEtiketsizUretim);
        _btnEtiketsizUretim.playSoundEffect(0);
        _btnEtiketsizUretim.setOnClickListener(new fn_btnEtiketsizUretim());

        _btncikis = (Button)getView().findViewById(R.id.btncikis);
        _btncikis.playSoundEffect(0);
        _btncikis.setOnClickListener(new fn_btncikis());

        _btnIptal = (Button)getView().findViewById(R.id.btnIptal);
        _btnIptal.playSoundEffect(0);
        _btnIptal.setOnClickListener(new fn_btnIptal());

        _txtPaletId = (TextView)getView().findViewById(R.id.txtPaletId);

        _txtParcaBir = (TextView)getView().findViewById(R.id.txtParcaBir);

        _txtAra = (TextView)getView().findViewById(R.id.txtAra);

        _txtParcaIki = (TextView)getView().findViewById(R.id.txtParcaIki);

        fn_AltPanelGorunsunmu(false);

        DEPOTag _Depo = null;

        DEPOTag _Silo = null;


    }

    private void fn_AltPanelGorunsunmu(boolean _bGoster) {
        if (_bGoster == true)
        {
            _btnEtiketsizUretim.setVisibility(View.VISIBLE);

            _btnIptal.setVisibility(View.VISIBLE);

            _txtPaletId.setVisibility(View.VISIBLE);
            _txtParcaBir.setVisibility(View.VISIBLE);
            _txtAra.setVisibility(View.VISIBLE);
            _txtParcaIki.setVisibility(View.VISIBLE);
        }
        else
            {
            _btnEtiketsizUretim.setVisibility(View.INVISIBLE);

            _btnIptal.setVisibility(View.INVISIBLE);

            _txtPaletId.setVisibility(View.INVISIBLE);
            _txtParcaBir.setVisibility(View.INVISIBLE);
            _txtAra.setVisibility(View.INVISIBLE);
            _txtParcaIki.setVisibility(View.INVISIBLE);

        }
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
            _OnlineUrlsecEtiket = "http://"+_ayarsunucuip+":"+_zportWifi+"/api/secEtiket";
        }
        else
        {
            _OnlineUrlsecEtiket = "http://88.255.50.73:"+_zport3G+"/api/secEtiket";
        }
    }

    public void fn_BarkodOkutuldu(String barcode)
    {
        if(_IslemDurum == cTanimEnum._eDurum.ISLEM_YOK)
        {
            fn_EtiketSorgula(barcode);

        }
    }

    private void fn_EtiketSorgula(String barcode) {

        JSONObject parametre = new JSONObject();

    }

    private class fn_btnEtiketsizUretim implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }

    private class fn_btnIptal implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }

    private class fn_btncikis implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            frg_uretim_menu_panel fragmentyeni = new frg_uretim_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_uretim_menu_panel").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}
