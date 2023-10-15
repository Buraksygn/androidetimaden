package com.etimaden.manipulasyon.geribesleme;

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
import com.etimaden.adapter.apmblManipulasyonGeribeslemeAyirma;
import com.etimaden.adapter.apmblManipulasyonGeribeslemeHarcamaYeriSecimi;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cResponseResult.Sevkiyat_isemri;
import com.etimaden.genel.Genel;
import com.etimaden.genel.SweetAlertDialogG;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.DEPOTag;
import com.etimaden.persosclass.Urun_tag;
import com.etimaden.request.request_secEtiket;
import com.etimaden.request.request_uruntag;
import com.etimaden.request.request_uruntag_string;
import com.etimaden.request.requestsecDepoTanimlari;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;
import java.util.List;

public class frg_geribesleme_harcama_yeri_secimi extends Fragment {
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

    Urun_tag aktif_tag = null;
    ArrayList<DEPOTag> depo_listesi = new ArrayList<DEPOTag>();
    DEPOTag _Secili = new DEPOTag();

    private apmblManipulasyonGeribeslemeHarcamaYeriSecimi adapter;



    public static frg_geribesleme_harcama_yeri_secimi newInstance() {

        return new frg_geribesleme_harcama_yeri_secimi();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_geribesleme_harcama_yeri_secimi, container, false);
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

    public void fn_senddata(Urun_tag aktif_tag)
    {
        this.aktif_tag=aktif_tag;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        ((GirisSayfasi) getActivity()).fn_ModBarkod();

        _myIslem = new VeriTabani(getContext());

        fn_AyarlariYukle();

        _myIslem.fn_EpcTemizle();

        ((GirisSayfasi) getActivity()).fn_ListeTemizle();

        _btnGeri = (Button)getView().findViewById(R.id.btnGeri);
        _btnGeri.playSoundEffect(SoundEffectConstants.CLICK);
        _btnGeri.setOnClickListener(new fn_Geri());

        _btn_01= (Button)getView().findViewById(R.id.btn_01);
        _btn_01.playSoundEffect(SoundEffectConstants.CLICK);
        _btn_01.setOnClickListener(new fn_btn_01());

        _listisemirleri=(ListView)getView().findViewById(R.id.isemri_list);
        _listisemirleri.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _Secili = depo_listesi.get(position);
            }
        });
        adapter=new apmblManipulasyonGeribeslemeHarcamaYeriSecimi(depo_listesi,getContext());
        _listisemirleri.setAdapter(adapter);

        setSiloListesi();
    }

    private void updateListviewItem(){
        try
        {
            if (adapter != null) {
                adapter.clear();
                adapter.addAll(depo_listesi);
                adapter.notifyDataSetChanged();
            }

        }
        catch (Exception ex)
        {
            Genel.printStackTrace(ex,getContext());
        }
    }

    private void setSiloListesi()
    {
        try
        {
            requestsecDepoTanimlari _Param=new requestsecDepoTanimlari();

            _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
            _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
            _Param.set_zaktif_tesis(_ayaraktiftesis);
            _Param.set_zsurum(_sbtVerisyon);
            _Param.set_zkullaniciadi(_zkullaniciadi);
            _Param.set_zsifre(_zsifre);
            _Param.setAktif_sunucu(_ayaraktifsunucu);
            _Param.setAktif_kullanici(_ayaraktifkullanici);

            _Param.setDepo_turu("1");
            _Param.setIsletme(aktif_tag.isletme);
            _Param.setDepo_silo_secimi("");
            _Param.setDepo_silo_secimi_kontrol(false);

            Genel.showProgressDialog(getContext());
            List<DEPOTag> result = persos.fn_secDepoTanimlari(_Param);
            depo_listesi=new ArrayList<>();
            if(result!=null) {
                depo_listesi = new ArrayList<>(result);
            }
            Genel.dismissProgressDialog();

            updateListviewItem();

        }
        catch (Exception ex)
        {
            Genel.printStackTrace(ex,getContext());
            new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                    .setTitleText("BAĞLANTI HATASI")
                    .setContentTextSize(25)
                    .setContentText("Depo bilgisi alınamadı.")
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
                if (_Secili.getDepo_id().equals(""))
                {
                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                            .setTitleText("HATA")
                            .setContentTextSize(25)
                            .setContentText("İşlem yapılamadı. Lütfen bir geri besleme noktası seçiniz. \r\n Geri besleme noktası seçimi yapılmadı")
                            .showCancelButton(false)
                            .show();
                    return;
                }
                final DEPOTag secilenDepo=_Secili;
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                        .setTitleText("İşlem Onay Sorgusu")
                        .setContentText(aktif_tag.kod +" seri numaralı "+ aktif_tag.urun_adi +" ürün geri besleme işlemi gerçekleştirilecek. Bu işlemi onaylıyor musunuz? ")
                        .setContentTextSize(20)
                        .setConfirmText("EVET")
                        .setCancelText("HAYIR")
                        .showCancelButton(true)
                        .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialogG sDialog) {
                                sDialog.dismissWithAnimation();
//todo burada mükerrer kayıt atıyor
                                String depoIdSecili=secilenDepo.getDepo_id();
                                int idx=depoIdSecili.indexOf("*");
                                if(idx!=-1){
                                    depoIdSecili=depoIdSecili.substring(0,idx);
                                }

                                request_uruntag_string v_Gelen=new request_uruntag_string();
                                v_Gelen.set_zaktif_alt_tesis(_ayaraktifalttesis);
                                v_Gelen.set_zaktif_tesis(_ayaraktiftesis);
                                v_Gelen.set_zkullaniciadi(_zkullaniciadi);
                                v_Gelen.set_zsifre(_zsifre);
                                v_Gelen.set_zsunucu_ip_adresi(_ayarsunucuip);
                                v_Gelen.set_zsurum(_sbtVerisyon);
                                v_Gelen.setAktif_kullanici(_ayaraktifkullanici);
                                v_Gelen.setAktif_sunucu(_ayaraktifsunucu);

                                v_Gelen.setEtiket(aktif_tag);
                                v_Gelen.setStringValue(depoIdSecili);


                                Genel.showProgressDialog(getContext());
                                Boolean islem_sonucu = persos.fn_geribesleme_onay(v_Gelen);
                                Genel.dismissProgressDialog();

                                if (!islem_sonucu)
                                //if (false)
                                {
                                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                                            .setTitleText("HATA")
                                            .setContentTextSize(25)
                                            .setContentText("İşlem yapılamadı \r\n Veritabanı hatası")
                                            .showCancelButton(false)
                                            .show();
                                    return;
                                }
                                new SweetAlertDialogG(getContext(), SweetAlertDialogG.SUCCESS_TYPE)
                                        .setTitleText("ONAY")
                                        .setContentText("İşlem kaydı oluşturuldu.")
                                        .setContentTextSize(20)
                                        .setConfirmText("TAMAM")
                                        .showCancelButton(false)
                                        .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialogG sDialog) {
                                                sDialog.dismissWithAnimation();

                                                frg_geribesleme_onay fragmentyeni = new frg_geribesleme_onay();
                                                FragmentManager fragmentManager = getFragmentManager();
                                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_geribesleme_onay").addToBackStack(null);
                                                fragmentTransaction.commit();

                                                return;
                                            }
                                        }).show();

                                return;
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
            frg_geribesleme_onay fragmentyeni = new frg_geribesleme_onay();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_geribesleme_onay").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

}
