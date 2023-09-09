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
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.GirisSayfasi;
import com.etimaden.SevkiyatIslemleri.frg_sevkiyat_menu_panel;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cResponseResult.Sevkiyat_isemri;
import com.etimaden.genel.Genel;
import com.etimaden.persos.Persos;
import com.etimaden.request.request_string;
import com.etimaden.ugr_demo.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class frg_aktif_silobas_arac_secimi extends Fragment {

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

    public frg_aktif_silobas_arac_secimi() {
        // Required empty public constructor
    }

    public static frg_aktif_silobas_arac_secimi newInstance()
    {
        return new frg_aktif_silobas_arac_secimi();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_aktif_silobas_arac_secimi, container, false);
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
            //System.Media.SystemSounds.Question.Play();

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
            Sevkiyat_isemri sevk = persos.fn_sec_aktif_silobas(_Param);
            Genel.dismissProgressDialog();

            if (sevk == null)
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Aktif araç işemri bulunamadı.. \r\n Araç kantardan geçiş işlemini tamamlamamış.")
                        .showCancelButton(false)
                        .show();
            }
            else
            {
                try
                {
                    //todo GURKAN frg_aktif_silobas_arac_bulundu sayfasını neye göre açacaz?
                    Genel.playButtonClikSound(getContext());
                    if (_ayaraktiftesis.equals("5001")){
                        frg_aktif_silobas_isemri_degistir fragmentyeni = new frg_aktif_silobas_isemri_degistir();
                        fragmentyeni.fn_senddata(sevk);
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_aktif_silobas_Isemri_degistir").addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }



        }
        catch (Exception ex)
        {
            Genel.dismissProgressDialog();
            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("HATA")
                    .setContentTextSize(25)
                    .setContentText("Aktif araç işemri bulunamadı.. \r\n Araç kantardan geçiş işlemini tamamlamamış.")
                    .showCancelButton(false)
                    .show();
        }
        okunabilir = true;
    }


    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_sevkiyat_menu_panel fragmentyeni = new frg_sevkiyat_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_sevkiyat_menu_panel").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

}
