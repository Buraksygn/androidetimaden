package com.etimaden.SayimIslemleri.Demirbas_sayim_islemi.Demirbas_basim_sorgula;

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
import com.etimaden.adapter.apmblSayimIslemleriDbBinaSecimi;
import com.etimaden.adapter.apmblSayimIslemleriDbKatSecimi;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.genel.Genel;
import com.etimaden.genel.SweetAlertDialogG;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.Demirbas_Konum;
import com.etimaden.request.request_demirbas_konum;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;
import java.util.List;


public class frg_db_kat_secimi extends Fragment {

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


    Button _btnIleri;
    ListView _kat_list;
    Button _btnGeri;

    boolean isReadable = true;
    List<Demirbas_Konum> kat_listesi;
    Demirbas_Konum _Secili = null;
    Demirbas_Konum bina = null;

    private apmblSayimIslemleriDbKatSecimi adapter;

    public frg_db_kat_secimi() {
        // Required empty public constructor
    }

    public static frg_db_kat_secimi newInstance()
    {
        return new frg_db_kat_secimi();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.frg_db_kat_secimi, container, false);
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

    public void fn_senddata(Demirbas_Konum bina)
    {
        this.bina=bina;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((GirisSayfasi) getActivity()).fn_ModBarkod();
        _myIslem = new VeriTabani(getContext());
        _myIslem.fn_EpcTemizle();
        ((GirisSayfasi) getActivity()).fn_ListeTemizle();
        fn_AyarlariYukle();

        _btnIleri = (Button)getView().findViewById(R.id.btnIleri);
        _btnIleri.playSoundEffect(0);
        _btnIleri.setOnClickListener(new fn_btnIleri());

        _btnGeri = (Button)getView().findViewById(R.id.btnGeri);
        _btnGeri.playSoundEffect(0);
        _btnGeri.setOnClickListener(new fn_Geri());



        _kat_list = (ListView) getView().findViewById(R.id.kat_list);

        _kat_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _Secili = kat_listesi.get(position);
            }
        });

        adapter=new apmblSayimIslemleriDbKatSecimi(new ArrayList<Demirbas_Konum>(),getContext());
        _kat_list.setAdapter(adapter);


        kat_listesi= new ArrayList<Demirbas_Konum>();
        binaDegerlendir();
    }
    private void binaDegerlendir()
    {
        try
        {
            // ← Bina kontrolü ekle
            if(bina == null) {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentText("Bina bilgisi null!")
                        .show();
                return;
            }

            request_demirbas_konum _Param1 = new request_demirbas_konum();
            _Param1.set_zsunucu_ip_adresi(_ayarsunucuip);
            _Param1.set_zaktif_alt_tesis(_ayaraktifalttesis);
            _Param1.set_zaktif_tesis(_ayaraktiftesis);
            _Param1.set_zsurum(_sbtVerisyon);
            _Param1.set_zkullaniciadi(_zkullaniciadi);
            _Param1.set_zsifre(_zsifre);
            _Param1.setAktif_sunucu(_ayaraktifsunucu);
            _Param1.setAktif_kullanici(_ayaraktifkullanici);

            _Param1.setDemirbasKonum(bina);

            Genel.showProgressDialog(getContext());
            kat_listesi = persos.fn_sec_ek_demirbas_kat(_Param1);
            Genel.dismissProgressDialog();

            // ← Liste kontrolü ekle
            if(kat_listesi == null || kat_listesi.isEmpty()) {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("BİLGİ")
                        .setContentText("Bu binada kat bulunamadı.")
                        .show();
            } else {
                updateListviewItem();
            }

        }
        catch (Exception ex)
        {
            Genel.printStackTrace(ex,getContext());
            new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                    .setTitleText("BAĞLANTI HATASI")
                    .setContentTextSize(25)
                    .setContentText("Kat listesi alınamadı: " + ex.getMessage())
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
                adapter.addAll(kat_listesi);
                adapter.notifyDataSetChanged();
            }

        }
        catch (Exception ex)
        {
            Genel.printStackTrace(ex,getContext());
        }
    }

    private class fn_btnIleri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Genel.lockButtonClick(view,getActivity());
            try
            {
                if (_Secili!=null) {
                    Demirbas_Konum secilen_kat = _Secili;

                    frg_db_oda_secimi fragmentyeni = new frg_db_oda_secimi();
                    fragmentyeni.fn_senddata(secilen_kat);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_db_oda_secimi").addToBackStack(null);
                    fragmentTransaction.commit();
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
            frg_db_bina_secimi fragmentyeni = new frg_db_bina_secimi();
            fragmentyeni.fn_senddata(bina);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_db_bina_secimi").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}
