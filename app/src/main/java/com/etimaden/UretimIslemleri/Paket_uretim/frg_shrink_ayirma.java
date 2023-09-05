package com.etimaden.UretimIslemleri.Paket_uretim;

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
import com.etimaden.UretimIslemleri.frg_paket_uretim_ekrani;
import com.etimaden.UretimIslemleri.frg_uretim_menu_panel;
import com.etimaden.adapter.apmblAktifIsEmirleri;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.genel.Genel;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.Urun_tag;
import com.etimaden.persosclass.uretim_etiket;
import com.etimaden.request.request_secEtiket;
import com.etimaden.request.request_shrink_is_emri;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.etimaden.cSabitDegerler._ipAdresi3G;
import static com.etimaden.cSabitDegerler._sbtVerisyon;
import static com.etimaden.cSabitDegerler._zkullaniciadi;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;

public class frg_shrink_ayirma extends Fragment {

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


    Button _btnShrinkOlustur;
    ListView _isemri_list;
    Button _btngeri;


    Urun_tag depo;

    uretim_etiket urun;

    ArrayList<Urun_tag> dataModels;
    Urun_tag _Secili = new Urun_tag();
    private static apmblAktifIsEmirleri adapter;



    public frg_shrink_ayirma() {
        // Required empty public constructor
    }

    public static frg_shrink_ayirma newInstance()
    {
        return new frg_shrink_ayirma();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.frg_shrink_ayirma, container, false);
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        new VeriTabani(getContext()).fn_EpcTemizle();

        _myIslem = new VeriTabani(getContext());

        //barkod,rfid,ikiside
        ((GirisSayfasi) getActivity()).fn_ModBarkod();

        _btnShrinkOlustur = (Button)getView().findViewById(R.id.btnShrinkOlustur);
        _btnShrinkOlustur.playSoundEffect(0);
        _btnShrinkOlustur.setOnClickListener(new fn_btnShrinkOlustur());

        _btngeri = (Button)getView().findViewById(R.id.btncikis);
        _btngeri.playSoundEffect(0);
        _btngeri.setOnClickListener(new fn_Geri());

        _isemri_list = (ListView) getView().findViewById(R.id.isemri_list);

        _isemri_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _Secili = dataModels.get(position);
            }
        });

        fn_AyarlariYukle();
        dataModels= new ArrayList<Urun_tag>();
        //fn_SiloListele();
    }
    public void fn_BarkodOkutuldu(String barkod) {

        try
        {

            barkod = barkod.substring(barkod.length()-24);

            int lenght = barkod.length();
            boolean readable = isReadable;

            if (!(isReadable && barkod.length() == 24))
            {
                return;
            }
            isReadable = false;
            //System.Media.SystemSounds.Question.Play();

            request_secEtiket v_Gelen=new request_secEtiket();

            v_Gelen.set_rfid(barkod);
            v_Gelen.set_zaktif_alt_tesis(_ayaraktifalttesis);
            v_Gelen.set_zaktif_tesis(_ayaraktiftesis);
            v_Gelen.set_zkullaniciadi(_zkullaniciadi);
            v_Gelen.set_zsifre(_zsifre);
            v_Gelen.set_zsunucu_ip_adresi(_ayarsunucuip);
            v_Gelen.set_zsurum(_sbtVerisyon);
            v_Gelen.setAktif_kullanici(_ayaraktifkullanici);
            v_Gelen.setAktif_sunucu(_ayaraktifsunucu);

            Urun_tag tag = persos.fn_secEtiket(v_Gelen);
            if (tag == null || (!tag.islem_durumu.equals("1")) || (!tag.etiket_turu.equals("1")))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("İŞLEM İÇİN UYGUN OLMAYAN ÜRÜN")
                        .setContentTextSize(25)
                        .setContentText("Ürün yapmak istediğiniz işlem için uygun değildir. \r\n İşleme uygun olmayan etiket.")
                        .showCancelButton(false)
                        .show();
                return;

            }
            else
            {
                urunDegerlendir(tag);
            }


        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        //Thread.Sleep(2000);
        isReadable = true;

    }

    public void fn_RfidOkundu(String v_epc){
        System.out.println("Rfid okutuldu : " + v_epc);
        try
        {
            if (!isReadable && v_epc.length() == 24)
            {
                return;
            }
            isReadable = false;
            //System.Media.SystemSounds.Question.Play();
            request_secEtiket v_Gelen=new request_secEtiket();
            v_Gelen.set_rfid(v_epc);
            v_Gelen.set_zaktif_alt_tesis(_ayaraktifalttesis);
            v_Gelen.set_zaktif_tesis(_ayaraktiftesis);
            v_Gelen.set_zkullaniciadi(_zkullaniciadi);
            v_Gelen.set_zsifre(_zsifre);
            v_Gelen.set_zsunucu_ip_adresi(_ayarsunucuip);
            v_Gelen.set_zsurum(_sbtVerisyon);
            v_Gelen.setAktif_kullanici(_ayaraktifkullanici);
            v_Gelen.setAktif_sunucu(_ayaraktifsunucu);

            Urun_tag tag = persos.fn_secEtiket(v_Gelen);

            if (tag == null || (!tag.islem_durumu.equals("1"))  || (!tag.etiket_turu.equals("1")))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("İŞLEM İÇİN UYGUN OLMAYAN ÜRÜN")
                        .setContentTextSize(25)
                        .setContentText("Ürün yapmak istediğiniz işlem için uygun değildir. \r\n İşleme uygun olmayan etiket.")
                        .showCancelButton(false)
                        .show();
                return;
            }
            else
            {
                urunDegerlendir(tag);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        //Thread.Sleep(2000);
        isReadable = true;


    }

    private void urunDegerlendir(Urun_tag tag)
    {
        if ( dataModels.contains(tag)){
            dataModels.remove(tag);
        }
        else{
            dataModels.add(tag);
        }

        updateListviewItem();


    }
    private void updateListviewItem()
    {
        try
        {
           int gorev_id = 0;

            if (adapter != null) {
                adapter.clear();
                _isemri_list.setAdapter(adapter);
            }

           adapter=new apmblAktifIsEmirleri(dataModels,getContext());
           _isemri_list.setAdapter(adapter);



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


    private class fn_btnShrinkOlustur implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            try
            {
                if (dataModels.size() == 0)
                {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("HATALI İŞLEM")
                            .setContentTextSize(25)
                            .setContentText("Ürün listenizde ürün bulunmamaktadır. \r\n Hatalı İşlem.")
                            .showCancelButton(false)
                            .show();
                    return;

                }
                else
                {
                    //Cursor.Current = Cursors.WaitCursor;
                    request_shrink_is_emri _Param= new request_shrink_is_emri();
                    _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
                    _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
                    _Param.set_zaktif_tesis(_ayaraktiftesis);
                    _Param.set_zsurum(_sbtVerisyon);
                    _Param.set_zkullaniciadi(_zkullaniciadi);
                    _Param.set_zsifre(_zsifre);
                    _Param.setAktif_sunucu(_ayaraktifsunucu);
                    _Param.setAktif_kullanici(_ayaraktifkullanici);
                    _Param.set_urunlist(dataModels);

                    //String miktar = persos.fn_sec_ambalaj_degisim_toplam_harcanan_miktar(_Param);

                    Genel.showProgressDialog(getContext());
                    Boolean result = persos.fn_setShrinkIsemri(_Param);
                    Genel.dismissProgressDialog();

                    //Cursor.Current = Cursors.Default;
                    if (result)
                    {
                        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("İşlem Onayı")
                                .setContentText("İşlem başarı ile tamamlanmıştır.")
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
                        //Program.giveUyariMesaji("İşlem Onayı", "İşlem başarı ile tamamlanmıştır.");

                        frg_uretim_menu_panel fragmentyeni = new frg_uretim_menu_panel();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_uretim_menu_panel").addToBackStack(null);
                        fragmentTransaction.commit();
                        //Program.setPage(new Uretim_Islemleri.Uretim_Menu_Panel.Uretim_Menu_Panel());
                    }
                    else
                    {
                        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Uyarı")
                                .setContentText("İşlem yapılamadı.Bağlantı ayarlarınızı kontrol ettikten sonra tekrar deneyiniz.")
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
                        //Program.giveUyariMesaji("Uyarı", "İşlem yapılamadı.Bağlantı ayarlarınızı kontrol ettikten sonra tekrar deneyiniz.");
                    }
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }


        }
    }
}
