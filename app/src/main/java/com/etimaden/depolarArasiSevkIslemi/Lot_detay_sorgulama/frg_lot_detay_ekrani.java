package com.etimaden.depolarArasiSevkIslemi.Lot_detay_sorgulama;

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
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.GirisSayfasi;
import com.etimaden.adapter.apmblDepolararasiSevkLotDetayEkrani;
import com.etimaden.adapter.apmblDepolararasiSevkSayilamayanAyirma;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cResponseResult.Urun_sevkiyat;
import com.etimaden.depolarArasiSevkIslemi.frg_depolar_arasi_transfer_menu_panel;
import com.etimaden.genel.Genel;
import com.etimaden.genel.SweetAlertDialogG;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.Urun_tag;
import com.etimaden.persosclass.Vagon_hareket;
import com.etimaden.persosclass.lot_detay;
import com.etimaden.request.request_secEtiket;
import com.etimaden.request.request_string;
import com.etimaden.request.request_uruntag_list;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;
import java.util.List;

public class frg_lot_detay_ekrani extends Fragment {
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
    ListView _listisemirleri;
    Button _btnGeri;


    List<lot_detay> lot_detay_listesi = new ArrayList<lot_detay>();
    Urun_sevkiyat tag = null;

    private apmblDepolararasiSevkLotDetayEkrani adapter;

    public static frg_lot_detay_ekrani newInstance() {

        return new frg_lot_detay_ekrani();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_lot_detay_ekrani, container, false);
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

    public void fn_senddata(Urun_sevkiyat tag)
    {
        this.tag=tag;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        _myIslem = new VeriTabani(getContext());

        fn_AyarlariYukle();

        _myIslem.fn_EpcTemizle();

        ((GirisSayfasi) getActivity()).fn_ListeTemizle();

        _btnGeri = (Button)getView().findViewById(R.id.btnGeri);
        _btnGeri.playSoundEffect(0);
        _btnGeri.setOnClickListener(new fn_Geri());

        _btn_01= (Button)getView().findViewById(R.id.btn_01);
        _btn_01.playSoundEffect(0);
        _btn_01.setOnClickListener(new fn_btn_01());

        _listisemirleri=(ListView)getView().findViewById(R.id.isemri_list);
        adapter=new apmblDepolararasiSevkLotDetayEkrani(new ArrayList<lot_detay>(),getContext());
        _listisemirleri.setAdapter(adapter);

        lot_detay_degerlendir();
    }

    private void updateListviewItem(){
        try
        {
            if (adapter != null) {
                adapter.clear();
                adapter.addAll(lot_detay_listesi);
                adapter.notifyDataSetChanged();
            }

        }
        catch (Exception ex)
        {
            Genel.printStackTrace(ex,getContext());
        }
    }

    private void lot_detay_degerlendir()
    {
        try
        {
            request_string v_Gelen=new request_string();
            v_Gelen.set_zaktif_alt_tesis(_ayaraktifalttesis);
            v_Gelen.set_zaktif_tesis(_ayaraktiftesis);
            v_Gelen.set_zkullaniciadi(_zkullaniciadi);
            v_Gelen.set_zsifre(_zsifre);
            v_Gelen.set_zsunucu_ip_adresi(_ayarsunucuip);
            v_Gelen.set_zsurum(_sbtVerisyon);
            v_Gelen.setAktif_kullanici(_ayaraktifkullanici);
            v_Gelen.setAktif_sunucu(_ayaraktifsunucu);

            v_Gelen.set_value(tag.lotno);

            Genel.showProgressDialog(getContext());
            lot_detay_listesi = persos.fn_sec_lot_detay(v_Gelen);
            Genel.dismissProgressDialog();
            updateListviewItem();

        }
        catch (Exception ex)
        {
            Genel.printStackTrace(ex,getContext());
            new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                    .setTitleText("BAĞLANTI HATASI")
                    .setContentTextSize(25)
                    .setContentText("Üretim bilgisi alınamadı.")
                    .showCancelButton(false)
                    .show();
        }
    }


    private class fn_btn_01 implements View.OnClickListener {
        @Override
        public void onClick(View view)
        {
            try
            {
                Genel.lockButtonClick(view,getActivity());
                lot_detay_degerlendir();
            }
            catch (Exception ex)
            {
                Genel.printStackTrace(ex,getContext());
            }
        }
    }

    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View view)
        {
            frg_lot_sorgulama fragmentyeni = new frg_lot_sorgulama();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_lot_sorgulama").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

}
