package com.etimaden.SevkiyatIslemleri.Zayiat_islemleri;

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
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.GirisSayfasi;
import com.etimaden.SevkiyatIslemleri.frg_sevkiyat_menu_panel;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.persosclass.Arac;
import com.etimaden.genel.Genel;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.Zayi;
import com.etimaden.request.request_bos;
import com.etimaden.request.request_string;
import com.etimaden.request.request_string_aktif_isletme_esleme;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class frg_zayi_aktivasyon extends Fragment {

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


    Button _btngeri;
    TextView _txtYazi;


    boolean okunabilir = true;

    public frg_zayi_aktivasyon() {
        // Required empty public constructor
    }

    public static frg_zayi_aktivasyon newInstance()
    {
        return new frg_zayi_aktivasyon();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_zayi_aktivasyon, container, false);
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

        _myIslem = new VeriTabani(getContext());

        ((GirisSayfasi) getActivity()).fn_ModRFID();

        _txtYazi=(TextView)getView().findViewById(R.id.txtYazi);


        _btngeri = (Button)getView().findViewById(R.id.btngeri);
        _btngeri.playSoundEffect(0);
        _btngeri.setOnClickListener(new fn_Geri());


        fn_AyarlariYukle();

    }

    public void fn_RfidOkundu(String v_epc){
        System.out.println("Rfid okutuldu : " + v_epc);

        try
        {
            if (!okunabilir)
                return;
            okunabilir = false;
            Genel.playQuestionSound(getContext());

            request_string _Param= new request_string();
            _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
            _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
            _Param.set_zaktif_tesis(_ayaraktiftesis);
            _Param.set_zsurum(_sbtVerisyon);
            _Param.set_zkullaniciadi(_zkullaniciadi);
            _Param.set_zsifre(_zsifre);
            _Param.setAktif_sunucu(_ayaraktifsunucu);
            _Param.setAktif_kullanici(_ayaraktifkullanici);

            _Param.set_value(v_epc);

            Genel.showProgressDialog(getContext());
            Arac arac = persos.fn_sec_arac(_Param);
            Genel.dismissProgressDialog();


            if (arac == null)
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("İş Bulunamadı")
                        .setContentTextSize(25)
                        .setContentText("Aranılan zayi iş detayına ulaşılamadı. \r\n Bağlantılarınızı ve iş emrini kontrol ettikten sonra devam edebilirsiniz.")
                        .showCancelButton(false)
                        .show();
                okunabilir = true;
                return;
            }

            request_string_aktif_isletme_esleme _Param1= new request_string_aktif_isletme_esleme();
            _Param1.set_zsunucu_ip_adresi(_ayarsunucuip);
            _Param1.set_zaktif_alt_tesis(_ayaraktifalttesis);
            _Param1.set_zaktif_tesis(_ayaraktiftesis);
            _Param1.set_zsurum(_sbtVerisyon);
            _Param1.set_zkullaniciadi(_zkullaniciadi);
            _Param1.set_zsifre(_zsifre);
            _Param1.setAktif_sunucu(_ayaraktifsunucu);
            _Param1.setAktif_kullanici(_ayaraktifkullanici);

            _Param1.set_value(v_epc);
            _Param1.set_aktif_isletme_esleme(_ayaraktifisletmeeslesme);
            Genel.showProgressDialog(getContext());
            List<Zayi> result = persos.fn_sec_zayi_aynı_arac(_Param1);
            ArrayList<Zayi> zayi_listesi=new ArrayList<>(result);
            Genel.dismissProgressDialog();


            if (zayi_listesi.size() == 0){
                request_bos _Param2= new request_bos();
                _Param2.set_zsunucu_ip_adresi(_ayarsunucuip);
                _Param2.set_zaktif_alt_tesis(_ayaraktifalttesis);
                _Param2.set_zaktif_tesis(_ayaraktiftesis);
                _Param2.set_zsurum(_sbtVerisyon);
                _Param2.set_zkullaniciadi(_zkullaniciadi);
                _Param2.set_zsifre(_zsifre);
                _Param2.setAktif_sunucu(_ayaraktifsunucu);
                _Param2.setAktif_kullanici(_ayaraktifkullanici);

                Genel.showProgressDialog(getContext());
                result = persos.fn_sec_zayi_farklı_arac(_Param2);
                zayi_listesi=new ArrayList<>(result);
                Genel.dismissProgressDialog();
            }

            if (zayi_listesi.size() == 0)
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("İş Bulunamadı")
                        .setContentTextSize(25)
                        .setContentText("Aranılan zayi iş detayına ulaşılamadı. \r\n Bağlantılarınızı ve iş emrini kontrol ettikten sonra devam edebilirsiniz.")
                        .showCancelButton(false)
                        .show();
                okunabilir = true;
                return;
            } else {
                frg_zayi_isemri_degistir fragmentyeni = new frg_zayi_isemri_degistir();
                fragmentyeni.fn_senddata(zayi_listesi,arac);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_zayi_isemri_degistir").addToBackStack(null);
                fragmentTransaction.commit();
            }

        }
        catch (Exception ex)
        {
            Genel.printStackTrace(ex,getContext());
        }
        okunabilir = true;

    }


    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_zayi_menu_panel fragmentyeni = new frg_zayi_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_zayi_menu_panel").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

}
