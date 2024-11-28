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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.GirisSayfasi;
import com.etimaden.SayimIslemleri.Demirbas_sayim_islemi.frg_demirbas_sayim_menu_panel;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cResponseResult.Sevkiyat_isemri;
import com.etimaden.genel.Genel;
import com.etimaden.genel.SweetAlertDialogG;
import com.etimaden.manipulasyon.ellecleme.frg_ellecleme_menu_panel;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.Urun_tag;
import com.etimaden.persosclass.demirbas_sayim;
import com.etimaden.request.request_secEtiket;
import com.etimaden.request.request_uruntag_string;
import com.etimaden.ugr_demo.R;

import java.util.Timer;
import java.util.TimerTask;

public class frg_demirbas_arama extends Fragment {

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


    TextView _txtDemirbasDetay;
    Button _btnGeri;
    ProgressBar _progressbar;

    demirbas_sayim secilen_demirbas = null;
    boolean isReadable = true;
    boolean demirbas_bulundu = false;
    int terminal_gücü = 300;
    Long son_okuma=5000L;
    Timer timer=new Timer();

    public frg_demirbas_arama() {
        // Required empty public constructor
    }

    public static frg_demirbas_arama newInstance()
    {
        return new frg_demirbas_arama();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_demirbas_arama, container, false);
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

    public void fn_senddata(demirbas_sayim secilen_demirbas)
    {
        this.secilen_demirbas=secilen_demirbas;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        _myIslem = new VeriTabani(getContext());
        _myIslem.fn_EpcTemizle();

        ((GirisSayfasi) getActivity()).fn_ModRFID();

        _txtDemirbasDetay=(TextView)getView().findViewById(R.id.txtDemirbasDetay);
        update_demirbas_bilgi();
        _progressbar = (ProgressBar) getView().findViewById(R.id.progressbar);
        _progressbar.setProgress((300-terminal_gücü)/3);
        ((GirisSayfasi) getActivity()).fn_GucAyarla(terminal_gücü);

        _btnGeri = (Button)getView().findViewById(R.id.btnGeri);
        _btnGeri.playSoundEffect(0);
        _btnGeri.setOnClickListener(new fn_Geri());


        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(System.currentTimeMillis()-son_okuma<400) {
                    if (demirbas_bulundu) {
                        if (terminal_gücü > 30) {
                            terminal_gücü = terminal_gücü - 30;
                        }
                    }else{
                        if (terminal_gücü < 300) {
                            terminal_gücü = terminal_gücü + 30;
                        }
                    }
                    demirbas_bulundu=false;
                    _progressbar.setProgress((300-terminal_gücü)/3);
                    ((GirisSayfasi) getActivity()).fn_GucAyarla(terminal_gücü);
                }
            }
        },0,400);

        fn_AyarlariYukle();

    }

    private void update_demirbas_bilgi()
    {
        try
        {
            if(secilen_demirbas!=null) {
                String text = "Demirbaş SAP Kodu : " + secilen_demirbas.getDs_demirbas_kod() +
                        "\r\n Demirbaş Eski Kodu : " + secilen_demirbas.getDs_demirbas_eski_kod() +
                        "\r\n Demirbaş Adı : " + secilen_demirbas.getDs_demirbas_ad_1() + "-" + secilen_demirbas.getDs_demirbas_ad_2() + "-" + secilen_demirbas.getDs_demirbas_ad_3() +
                        "\r\n Demirbaş Konum : " + secilen_demirbas.getDs_isletme_adi() + "(" + secilen_demirbas.getDs_isletme_kod() + ")" +
                        "\r\n" + secilen_demirbas.getDs_bina_adi() + "(" + secilen_demirbas.getDs_bina_kod() + ")" +
                        "\r\n" + secilen_demirbas.getDs_kat_adi() + "(" + secilen_demirbas.getDs_kat_kod() + ")" +
                        "\r\n" + secilen_demirbas.getDs_oda_adi() + "(" + secilen_demirbas.getDs_oda_kod() + ")";
                _txtDemirbasDetay.setText(text);
            }
        }
        catch (Exception ex)
        {}
    }

    public void rfidOkundu(String rfid){
        try
        {
            if (!isReadable)
            {
                return;
            }
            isReadable = false;
            son_okuma=System.currentTimeMillis();
            if(rfid==secilen_demirbas.getDs_rfid()){
                demirbas_bulundu=true;
                _myIslem.fn_EpcTemizle();
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        isReadable = true;

    }

    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            timer.cancel();
            timer.purge();
            ((GirisSayfasi) getActivity()).fn_GucAyarla(200);
            frg_demirbas_sayim_menu_panel fragmentyeni = new frg_demirbas_sayim_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_demirbas_sayim_menu_panel").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

}
