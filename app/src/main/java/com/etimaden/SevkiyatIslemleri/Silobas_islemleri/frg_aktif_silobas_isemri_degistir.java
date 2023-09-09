package com.etimaden.SevkiyatIslemleri.Silobas_islemleri;

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

import com.etimaden.SevkiyatIslemleri.frg_sevkiyat_menu_panel;
import com.etimaden.UretimIslemleri.frg_uretim_menu_panel;
import com.etimaden.adapter.apmblSevkiyatAktifIsEmirleri;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cResponseResult.Sevkiyat_isemri;
import com.etimaden.genel.Genel;
import com.etimaden.persos.Persos;
import com.etimaden.request.request_sevkiyat_eski_sevk_yeni_sevk;
import com.etimaden.request.request_sevkiyat_isemri;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class frg_aktif_silobas_isemri_degistir extends Fragment {

    SweetAlertDialog pDialog;
    boolean isReadable = true;
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


    Button _btnIsEmriDegisiminiOnayla;
    ListView _aktif_is_emirleri_list;
    Button _btngeri;

    ArrayList<Sevkiyat_isemri> sevk_listesi;
    Sevkiyat_isemri _Secili = null;
    Sevkiyat_isemri eski_sevk = null;

    private apmblSevkiyatAktifIsEmirleri adapter;



    public frg_aktif_silobas_isemri_degistir() {
        // Required empty public constructor
    }

    public static frg_aktif_silobas_isemri_degistir newInstance()
    {
        return new frg_aktif_silobas_isemri_degistir();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.frg_aktif_silobas_isemri_degistir, container, false);
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
        persos = new Persos(_OnlineUrl);
    }

    public void fn_senddata(Sevkiyat_isemri  eski_sevk)
    {
        this.eski_sevk = eski_sevk;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        new VeriTabani(getContext()).fn_EpcTemizle();

        _myIslem = new VeriTabani(getContext());


        _btnIsEmriDegisiminiOnayla = (Button)getView().findViewById(R.id.btnIsEmriDegisiminiOnayla);
        _btnIsEmriDegisiminiOnayla.playSoundEffect(0);
        _btnIsEmriDegisiminiOnayla.setOnClickListener(new fn_btnIsEmriDegisiminiOnayla());

        _btngeri = (Button)getView().findViewById(R.id.btncikis);
        _btngeri.playSoundEffect(0);
        _btngeri.setOnClickListener(new fn_Geri());

        _aktif_is_emirleri_list = (ListView) getView().findViewById(R.id.aktif_is_emirleri_list);

        _aktif_is_emirleri_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _Secili = sevk_listesi.get(position);
            }
        });

        fn_AyarlariYukle();
        sevk_listesi= new ArrayList<Sevkiyat_isemri>();
        sevkDegerlendir();
    }

    private void sevkDegerlendir()
    {
        try
        {
            request_sevkiyat_isemri v_Gelen=new request_sevkiyat_isemri();
            v_Gelen.set_zaktif_alt_tesis(_ayaraktifalttesis);
            v_Gelen.set_zaktif_tesis(_ayaraktiftesis);
            v_Gelen.set_zkullaniciadi(_zkullaniciadi);
            v_Gelen.set_zsifre(_zsifre);
            v_Gelen.set_zsunucu_ip_adresi(_ayarsunucuip);
            v_Gelen.set_zsurum(_sbtVerisyon);
            v_Gelen.setAktif_kullanici(_ayaraktifkullanici);
            v_Gelen.setAktif_sunucu(_ayaraktifsunucu);
            v_Gelen.set_sevkiyat_ismeri(eski_sevk);

            Genel.showProgressDialog(getContext());
            List<Sevkiyat_isemri> result = persos.fn_sec_silobas_SevkiyatIsemriListesi(v_Gelen);
            sevk_listesi=new ArrayList<>(result);
            Genel.dismissProgressDialog();


            updateListviewItem();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void updateListviewItem()
    {
        try
        {
            if (adapter != null) {
                adapter.clear();
                _aktif_is_emirleri_list.setAdapter(adapter);
            }

           adapter=new apmblSevkiyatAktifIsEmirleri(sevk_listesi,getContext());
            _aktif_is_emirleri_list.setAdapter(adapter);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_uretim_menu_panel fragmentyeni = new frg_uretim_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_uretim_menu_panel").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }


    private class fn_btnIsEmriDegisiminiOnayla implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            try
            {
                if (_Secili!=null){

                    Genel.playButtonClikSound(getContext());
                    Sevkiyat_isemri secilen_sevk = _Secili;

                    request_sevkiyat_eski_sevk_yeni_sevk _Param= new request_sevkiyat_eski_sevk_yeni_sevk();
                    _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
                    _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
                    _Param.set_zaktif_tesis(_ayaraktiftesis);
                    _Param.set_zsurum(_sbtVerisyon);
                    _Param.set_zkullaniciadi(_zkullaniciadi);
                    _Param.set_zsifre(_zsifre);
                    _Param.setAktif_sunucu(_ayaraktifsunucu);
                    _Param.setAktif_kullanici(_ayaraktifkullanici);
                    _Param.set_eski_sevk(eski_sevk);
                    _Param.set_yeni_sevk(secilen_sevk);

                    //String miktar = persos.fn_sec_ambalaj_degisim_toplam_harcanan_miktar(_Param);

                    Genel.showProgressDialog(getContext());
                    Boolean islem_sonucu = persos.fn_onayla_silobas_SevkIsDegisimi(_Param);
                    Genel.dismissProgressDialog();

                    if (islem_sonucu)
                    {
                        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("İşlem Başarılı")
                                .setContentText("İş değişim işlemi tamamlandı.")
                                .setContentTextSize(20)
                                .setConfirmText("TAMAM")
                                .showCancelButton(false)
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();

                                    }
                                })
                                .show();

                        frg_sevkiyat_menu_panel fragmentyeni = new frg_sevkiyat_menu_panel();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_sevkiyat_menu_panel").addToBackStack(null);
                        fragmentTransaction.commit();

                    }
                    else
                    {
                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("İşlem Başarısız")
                                .setContentTextSize(25)
                                .setContentText("Kayıt yapılamadı. \r\n Veritabanı hatası.")
                                .showCancelButton(false)
                                .show();
                    }

                }
                else
                {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("İş seçimi yapılmadı.")
                            .setContentTextSize(25)
                            .setContentText("Lütfen bir iş seçimi yapınız.")
                            .showCancelButton(false)
                            .show();
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }

        }
    }
}
