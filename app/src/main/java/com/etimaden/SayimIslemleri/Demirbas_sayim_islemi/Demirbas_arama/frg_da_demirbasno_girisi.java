package com.etimaden.SayimIslemleri.Demirbas_sayim_islemi.Demirbas_arama;

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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.GirisSayfasi;
import com.etimaden.SayimIslemleri.Demirbas_sayim_islemi.frg_demirbas_sayim_menu_panel;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.genel.Genel;
import com.etimaden.genel.SweetAlertDialogG;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.demirbas_sayim;
import com.etimaden.request.request_string;
import com.etimaden.ugr_demo.R;

import java.util.Timer;
import java.util.TimerTask;

public class frg_da_demirbasno_girisi extends Fragment {

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


    EditText _editTextDemirbasNo;
    Button _btnGeri;
    Button _btnTamam;


    public frg_da_demirbasno_girisi() {
        // Required empty public constructor
    }

    public static frg_da_demirbasno_girisi newInstance()
    {
        return new frg_da_demirbasno_girisi();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_da_demirbasno_girisi, container, false);
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


        _myIslem = new VeriTabani(getContext());

        _editTextDemirbasNo=(EditText)getView().findViewById(R.id.editTextDemirbasNo);

        _btnTamam = (Button)getView().findViewById(R.id.btnTamam);
        _btnTamam.playSoundEffect(0);
        _btnTamam.setOnClickListener(new fn_Tamam());

        _btnGeri = (Button)getView().findViewById(R.id.btnGeri);
        _btnGeri.playSoundEffect(0);
        _btnGeri.setOnClickListener(new fn_Geri());

        fn_AyarlariYukle();

    }

    private class fn_Tamam implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            try
            {
                Genel.lockButtonClick(view,getActivity());
                if (_editTextDemirbasNo.getText().toString().trim().length() == 12)
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

                    _Param1.set_value(_editTextDemirbasNo.getText().toString().trim());

                    Genel.showProgressDialog(getContext());
                    demirbas_sayim dmrb = persos.fn_sec_demirbas_detay(_Param1);
                    Genel.dismissProgressDialog();

                    frg_demirbas_arama fragmentyeni = new frg_demirbas_arama();
                    fragmentyeni.fn_senddata(dmrb);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_demirbas_arama").addToBackStack(null);
                    fragmentTransaction.commit();

                }
                else
                {
                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                            .setTitleText("HATA")
                            .setContentTextSize(25)
                            .setContentText("Lütfen 12 haneli demirbaş numarasını doğru bir şekilde giriniz.")
                            .showCancelButton(false)
                            .show();
                }

            }
            catch (Exception ex)
            { }

        }
    }

    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_demirbas_arama_menu_panel fragmentyeni = new frg_demirbas_arama_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_demirbas_arama_menu_panel").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

}
