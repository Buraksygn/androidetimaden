package com.etimaden.SayimIslemleri.Demirbas_sayim_islemi.Demirbas_sorgulama;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.GirisSayfasi;
import com.etimaden.SayimIslemleri.Demirbas_sayim_islemi.frg_demirbas_sayim_menu_panel;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.genel.Genel;
import com.etimaden.genel.SweetAlertDialogG;
import com.etimaden.manipulasyon.geribesleme.frg_geribesleme_harcama_yeri_secimi;
import com.etimaden.manipulasyon.geribesleme.frg_geribesleme_menu_panel;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.Duran_Varlik_sap;
import com.etimaden.persosclass.Urun_tag;
import com.etimaden.request.request_secEtiket;
import com.etimaden.request.request_string;
import com.etimaden.ugr_demo.R;

public class frg_demirbas_sorgula extends Fragment {

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


    Button _btnGeri;

    boolean isReadable = true;


    public frg_demirbas_sorgula() {
        // Required empty public constructor
    }

    public static frg_demirbas_sorgula newInstance()
    {
        return new frg_demirbas_sorgula();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_demirbas_sorgula, container, false);
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

        new VeriTabani(getContext()).fn_EpcTemizle();
        ((GirisSayfasi) getActivity()).fn_GucAyarla(180);
        _myIslem = new VeriTabani(getContext());

        ((GirisSayfasi) getActivity()).fn_ModRFID();

        _btnGeri = (Button)getView().findViewById(R.id.btnGeri);
        _btnGeri.playSoundEffect(SoundEffectConstants.CLICK);
        _btnGeri.setOnClickListener(new fn_Geri());

        fn_AyarlariYukle();

    }

    public void rfidOkundu(String rfid){
        try
        {
            if (!isReadable)
            {
                return;
            }
            isReadable = false;

            sorgula_demirbas(rfid.substring(12));

        }
        catch (Exception ex){
            Genel.printStackTrace(ex,getContext());
        }

        isReadable = true;

    }

    private void sorgula_demirbas(String id)
    {
        try
        {
            request_string _Param1 = new request_string();
            _Param1.set_zsunucu_ip_adresi(_ayarsunucuip);
            _Param1.set_zaktif_alt_tesis(_ayaraktifalttesis);
            _Param1.set_zaktif_tesis(_ayaraktiftesis);
            _Param1.set_zsurum(_sbtVerisyon);
            _Param1.set_zkullaniciadi(_zkullaniciadi);
            _Param1.set_zsifre(_zsifre);
            _Param1.setAktif_sunucu(_ayaraktifsunucu);
            _Param1.setAktif_kullanici(_ayaraktifkullanici);

            _Param1.set_value(id);

            Genel.showProgressDialog(getContext());
            Duran_Varlik_sap db_detay = persos.fn_sorgula_duran_varlik(_Param1);
            Genel.dismissProgressDialog();

            if (db_detay != null)
            {
                if (db_detay.islem_sonucu.equals("0"))
                {
                    String str = "";
                    str += "DURAN VARLIK NO : " + db_detay.duran_varlik_no;
                    str += "\r\n ESKİ DURAN VARLIK NO : " + db_detay.eski_demirbas_kod;
                    str += "\r\n ADI : " + db_detay.isim_1;
                    str += "\r\n MARKA : " + db_detay.marka;
                    str += "\r\n MASRAF YERİ : " + db_detay.masraf_yeri;
                    str += "\r\n BAŞKANLIK : " + db_detay.daire_baskanligi;
                    str += "\r\n BİNA : " + db_detay.bina_adi + "--" + db_detay.bina;
                    str += "\r\n KAT : " + db_detay.kat_adi + "--" + db_detay.kat;
                    str += "\r\n ODA : " + db_detay.oda_adi + "--" + db_detay.oda;
                    str += "\r\n PERSONEL : " + db_detay.personel_adi + " " + db_detay.personel_soyadi + "--" + db_detay.personel_numarasi;
                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.NORMAL_TYPE)
                            .setTitleText("DURAN VARLIK DETAYI")
                            .setContentText(str)
                            .setContentTextSize(20)
                            .setConfirmText("TAMAM")
                            .showCancelButton(false)
                            .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialogG sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .show();

                }
                else {
                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                            .setTitleText("DEMİRBAŞ BULUNAMADI")
                            .setContentTextSize(25)
                            .setContentText("Demirbaş bulunamadı... \r\n DV_NO :"+id)
                            .showCancelButton(false)
                            .show();
                }

            }
            else {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("BAĞLANTI HATASI")
                        .setContentTextSize(25)
                        .setContentText("Uzak sunucu bağlantısı yapılamadı")
                        .showCancelButton(false)
                        .show();
            }


        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_demirbas_sayim_menu_panel fragmentyeni = new frg_demirbas_sayim_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_demirbas_sayim_menu_panel").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

}
