package com.etimaden.digerislemler;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.cIslem.VeriTabani;
import com.etimaden.frg_ana_sayfa;
import com.etimaden.ugr_demo.R;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.etimaden.cSabitDegerler._ipAdresi3G;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;

public class frg_sifre_degistir extends Fragment {

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

    Retrofit _retrofit;

    Button _btncikis;
    Button _btnsifredegistir;

    EditText _txteskisifre;
    EditText _txtyenisifre;
    EditText _txtyenisifretekrar;


    public frg_sifre_degistir() {
        // Required empty public constructor
    }
    public static frg_sifre_degistir newInstance()
    {
        return new frg_sifre_degistir();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_sifre_degistir, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        _myIslem = new VeriTabani(getContext());

        fn_AyarlariYukle();

        _btncikis = (Button) getView().findViewById(R.id.btncikis);
        _btncikis.playSoundEffect(0);
        _btncikis.setOnClickListener(new fn_Geri());

        _btnsifredegistir = (Button) getView().findViewById(R.id.btnsifredegistir);
        _btnsifredegistir.playSoundEffect(0);
        _btnsifredegistir.setOnClickListener(new fn_btnsifredegistir());

        _txteskisifre = (EditText)getView().findViewById(R.id.txteskisifre);
        _txtyenisifre = (EditText)getView().findViewById(R.id.txtyenisifre);
        _txtyenisifretekrar = (EditText)getView().findViewById(R.id.txtyenisifretekrar);

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
            _OnlineUrl = "http://"+_ipAdresi3G+":"+_zport3G+"/";
        }
        _retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(_OnlineUrl)
                .build();
    }



    public void fn_BarkodOkutuldu(String str)
    {

    }


    public void fn_RfidOkutuldu(String str)
    {

    }



    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View v)
        {
            frg_ana_sayfa fragmentyeni = new frg_ana_sayfa();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_ana_sayfa").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_btnsifredegistir implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            String streskisifre =_txteskisifre.getText().toString().trim();
            String stryenisifre =_txtyenisifre.getText().toString().trim();
            String strtekraryenisifre =_txtyenisifretekrar.getText().toString().trim();

            if(streskisifre.length()<5 || stryenisifre.length()<5  || strtekraryenisifre.length()<5)
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("UYARI")
                        .setContentText("Lütfen ilgili alanları doldurunuz")
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

                return;

            }

            if(stryenisifre!=strtekraryenisifre)
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("UYARI")
                        .setContentText("Girilen yeni şifre değerleri uyuşmuyor")
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

                return;
            }

        }
    }
}
