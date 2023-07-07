package com.etimaden.UretimIslemleri;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.app.NotificationCompatSideChannelService;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.GirisSayfasi;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cResponseResult.Sevkiyat_isemri;
import com.etimaden.persosclass.DEPOTag;
import com.etimaden.persosclass.etiket_no;
import com.etimaden.persosclass.uretim_etiket;
import com.etimaden.request.request_etiket_kontrol;
import com.etimaden.request.request_sec_etiket_no;
import com.etimaden.request.request_yari_otomatik_paket_kontrol_et;
import com.etimaden.request.requestsec_etiket_uretim;
import com.etimaden.response.frg_paket_uretim_ekrani.View_etiket_kontrol;
import com.etimaden.response.frg_paket_uretim_ekrani.View_sec_etiket_no;
import com.etimaden.response.frg_paket_uretim_ekrani.View_yari_otomatik_paket_kontrol_et;
import com.etimaden.response.frg_paket_uretim_ekrani.Viewsec_etiket_uretim;
import com.etimaden.servisbaglanti.frg_paket_uretim_ekrani_interface;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.etimaden.cSabitDegerler._sbtVerisyon;
import static com.etimaden.cSabitDegerler._zkullaniciadi;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;


public class frg_paket_uretim_ekrani extends Fragment {


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

    Button _btnEtiketsizUretim;
    Button _btnIptal;
    Button _btncikis;

   // TextView _txtPaletId;
    TextView _txtParcaBir;
    TextView _txtAra;
    TextView _txtParcaIki;

   // cTanimEnum._eDurum islemDurumu = cTanimEnum._eDurum.ISLEM_YOK;

   uretim_etiket aktif_Palet = null;


    List<String> paket_listesi=new ArrayList<>();

    DEPOTag depo = null;
    uretim_etiket urun = null;
    DEPOTag silo = null;

    Retrofit _retrofit;

    CountDownTimer countDownTimer;

    Boolean isReadeable = true;

    int islemDurumu = 0;
    //  0 : etiket okutma bekleniyor
    //  1 : bigbag üretim işlemi yapılıyor
    //  2 : paketli palet üretim işlemi yapılıyor
    //  3 : paket üretim işlemi yapılıyor



    public static frg_paket_uretim_ekrani newInstance() {

        return new frg_paket_uretim_ekrani();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_paket_uretim_ekrani , container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((GirisSayfasi) getActivity()).fn_ModBarkod();

        _myIslem = new VeriTabani(getContext());

        fn_AyarlariYukle();

        _myIslem.fn_EpcTemizle();

        ((GirisSayfasi) getActivity()).fn_ListeTemizle();

        _btnEtiketsizUretim = (Button)getView().findViewById(R.id.btnEtiketsizUretim);
        _btnEtiketsizUretim.playSoundEffect(0);
        _btnEtiketsizUretim.setOnClickListener(new fn_btnEtiketsizUretim());

        _btncikis = (Button)getView().findViewById(R.id.btncikis);
        _btncikis.playSoundEffect(0);
        _btncikis.setOnClickListener(new fn_btncikis());

        _btnIptal = (Button)getView().findViewById(R.id.btnIptal);
        _btnIptal.playSoundEffect(0);
        _btnIptal.setOnClickListener(new fn_btnIptal());


        _txtParcaBir = (TextView)getView().findViewById(R.id.txtParcaBir);

        _txtAra = (TextView)getView().findViewById(R.id.txtAra);

        _txtParcaIki = (TextView)getView().findViewById(R.id.txtParcaIki);

        countDownTimer = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {

                etiket_islem_baslat(urun.getSerino_rfid());

            }

            @Override
            public void onFinish() {
                // btn_basla_dur.setText("başla");

                //   countDownTimer.start();

            }
        };

        if(silo==null)
        {
            fn_AltPanelGorunsunmu(false);
        }
        else
        {
            countDownTimer.cancel(); // cancel
            countDownTimer.start();  // then restart
        }
    }

    private void fn_AltPanelGorunsunmu(boolean _bGoster) {
        if (_bGoster == true)
        {
            _btnEtiketsizUretim.setVisibility(View.VISIBLE);

            _btnIptal.setVisibility(View.VISIBLE);


            _txtParcaBir.setVisibility(View.VISIBLE);
            _txtAra.setVisibility(View.VISIBLE);
            _txtParcaIki.setVisibility(View.VISIBLE);
        }
        else
            {
            _btnEtiketsizUretim.setVisibility(View.INVISIBLE);

            _btnIptal.setVisibility(View.INVISIBLE);


            _txtParcaBir.setVisibility(View.INVISIBLE);
            _txtAra.setVisibility(View.INVISIBLE);
            _txtParcaIki.setVisibility(View.INVISIBLE);

        }
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
            _OnlineUrl = "http://88.255.50.73:"+_zport3G+"/";
        }
        _retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(_OnlineUrl)
                .build();
    }


    private void etiket_islem_baslat(String str)
    {

    }




    public void barkodOkundu(String barkod) {
        barkod = barkod.trim();

        if (barkod.length() >= 24) {
            int Sayac = barkod.length();

            barkod = barkod.substring(barkod.length() - 24);

            etiket_islem_baslat(barkod);
        }
    }





    private class fn_btnEtiketsizUretim implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }


    private String fn_set_etiketno(etiket_no eno, int sirano)
    {
        String a = "";
        try
        {
            a = "7377670000";
            String yil = eno.getEti_yil().substring(eno.getEti_yil().length()-2);
            String ay = eno.getEti_ay();
            if (eno.getEti_ay().length() != 2)
            {
                ay = "0" + eno.getEti_ay();
            }
            String gun = eno.getEti_gun();
            if (eno.getEti_gun().length() != 2)
            {
                gun = "0" + eno.getEti_gun();
            }

            String b = yil + ay + gun + eno.getEti_isletme();

            while ((b.length() + (sirano+"").length()) != 14)
            {
                b = b + "0";
            }

            a = a + b + (sirano+"");

            //MessageBox.Show(a);

        }
        catch (Exception ex)
        {}
        return a;
    }

    private class fn_btnIptal implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            frg_uretim_menu_panel fragmentyeni = new frg_uretim_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_uretim_menu_panel").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_btncikis implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            frg_uretim_menu_panel fragmentyeni = new frg_uretim_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_uretim_menu_panel").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    public void fn_senddata(DEPOTag v_depo, DEPOTag v_silo, uretim_etiket v_aktif_urun)
    {
        urun = v_aktif_urun;
        depo=v_depo;
        silo=v_silo;
    }

    private void fn_yariotomatik_paketli_palet_uretim_tamamla(uretim_etiket etiket , String lot)
    {

        new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("ONAY")
                .setContentText(etiket.getSerino_kod() + " seri nolu ürünün üretim işlemini tamamlamak istiyor musunuz ?")
                .setContentTextSize(20)
                .setConfirmText("TAMAM")
                .setCancelText("VAZGEÇ")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

                        sDialog.dismissWithAnimation();
                    }
                })
                .show();

    }


    public boolean paketliUret_otomatik(uretim_etiket urun,String lotno) {

        return true;

    }
}
