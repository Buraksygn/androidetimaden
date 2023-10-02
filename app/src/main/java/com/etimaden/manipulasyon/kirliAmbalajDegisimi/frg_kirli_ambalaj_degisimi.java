package com.etimaden.manipulasyon.kirliAmbalajDegisimi;

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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.etimaden.DataModel.mdlIsemriSecimi;
import com.etimaden.GirisSayfasi;
import com.etimaden.SevkiyatIslemleri.frg_aktif_arac_secimi;
import com.etimaden.SevkiyatIslemleri.frg_aktif_isemri_indirme;
import com.etimaden.SevkiyatIslemleri.frg_aktif_isemri_yukleme;
import com.etimaden.adapter.apmblIsEmriSecimi;
import com.etimaden.adapter.apmblManipulasyonAmbalajTipiDegisimi;
import com.etimaden.adapter.apmblManipulasyonKirliAmbalajDegisimi;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cResponseResult.Sevkiyat_isemri;
import com.etimaden.cResponseResult.ViewsevkDegerlendir;
import com.etimaden.genel.Genel;
import com.etimaden.genel.SweetAlertDialogG;
import com.etimaden.manipulasyon.Ambalaj_tipi_degisimi.frg_ambalaj_tipi_degisimi;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.Urun_tag;
import com.etimaden.request.request_secEtiket;
import com.etimaden.request.request_string;
import com.etimaden.ugr_demo.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class frg_kirli_ambalaj_degisimi extends Fragment {
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
    Button _btnOkuma;

    boolean isReadable = true;
    ArrayList<Urun_tag> urun_listesi = new ArrayList<Urun_tag>();

    private apmblManipulasyonKirliAmbalajDegisimi adapter;



    public static frg_kirli_ambalaj_degisimi newInstance() {

        return new frg_kirli_ambalaj_degisimi();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_kirli_ambalaj_degisimi, container, false);
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
        _btnGeri.playSoundEffect(0);
        _btnGeri.setOnClickListener(new fn_Geri());

        _btnOkuma = (Button)getView().findViewById(R.id.btnOkuma);
        _btnOkuma.playSoundEffect(0);
        _btnOkuma.setOnClickListener(new fn_okumaDegistir());
        _btnOkuma.setText("KAREKOD");

        _btn_01= (Button)getView().findViewById(R.id.btn_01);
        _btn_01.playSoundEffect(0);
        _btn_01.setOnClickListener(new fn_btn_01());

        _listisemirleri=(ListView)getView().findViewById(R.id.listisemirleri);
    }

    private void updateListviewItem(){
        try
        {
            if (adapter != null) {
                adapter.clear();
                for (Urun_tag l : urun_listesi ) {
                    if(!l.etiket_turu.equals("0")){
                        adapter.add(l);
                    }
                }
                adapter.notifyDataSetChanged();
            }

        }
        catch (Exception ex)
        {
            Genel.printStackTrace(ex,getContext());
        }
    }

    public void barkodOkundu(String barkod){

        try
        {
            barkod = barkod.substring(barkod.length()-24);
            if (!isReadable)
            {
                return;
            }
            isReadable = false;
            Genel.playQuestionSound(getContext());

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

            Genel.showProgressDialog(getContext());
            Urun_tag tag = persos.fn_secEtiket(v_Gelen);
            Genel.dismissProgressDialog();
            if (tag == null || !tag.islem_durumu.equals("1"))
            {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("İŞLEM İÇİN UYGUN OLMAYAN ÜRÜN")
                        .setContentTextSize(25)
                        .setContentText("Ürün yapmak istediğiniz işlem için uygun değildir. \r\n İşleme uygun olmayan etiket.")
                        .showCancelButton(false)
                        .show();
            }
            else
            {
                urunDegerlendir(tag);
            }


        }
        catch (Exception ex)
        {
            Genel.printStackTrace(ex,getContext());
        }

        isReadable = true;

    }

    private void rfidOkundu(String rfid){
        try
        {

            if (!isReadable)
            {
                return;
            }
            isReadable = false;
            Genel.playQuestionSound(getContext());

            request_secEtiket v_Gelen=new request_secEtiket();
            v_Gelen.set_rfid(rfid);
            v_Gelen.set_zaktif_alt_tesis(_ayaraktifalttesis);
            v_Gelen.set_zaktif_tesis(_ayaraktiftesis);
            v_Gelen.set_zkullaniciadi(_zkullaniciadi);
            v_Gelen.set_zsifre(_zsifre);
            v_Gelen.set_zsunucu_ip_adresi(_ayarsunucuip);
            v_Gelen.set_zsurum(_sbtVerisyon);
            v_Gelen.setAktif_kullanici(_ayaraktifkullanici);
            v_Gelen.setAktif_sunucu(_ayaraktifsunucu);

            Genel.showProgressDialog(getContext());
            Urun_tag tag = persos.fn_secEtiket(v_Gelen);
            Genel.dismissProgressDialog();
            if (tag == null || !tag.islem_durumu.equals("1"))
            {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("İŞLEM İÇİN UYGUN OLMAYAN ÜRÜN")
                        .setContentTextSize(25)
                        .setContentText("Ürün yapmak istediğiniz işlem için uygun değildir. \r\n İşleme uygun olmayan etiket.")
                        .showCancelButton(false)
                        .show();
            }
            else
            {
                urunDegerlendir(tag);
            }


        }
        catch (Exception ex)
        {
            Genel.printStackTrace(ex,getContext());
        }

        isReadable = true;
    }

    private void urunDegerlendir(Urun_tag tag)
    {
        try
        {
            Boolean palet_kodVar=false;
            for(Urun_tag w:urun_listesi){
                if(w.kod.equals(tag.palet_kod)){
                    palet_kodVar=true;
                    break;
                }
            }
            Boolean kodVar=false;
            Urun_tag tmpObj=null;
            for(Urun_tag w:urun_listesi){
                if(w.kod.equals(tag.kod)){
                    tmpObj=w;
                    kodVar=true;
                    break;
                }
            }
            if (tag.etiket_turu.equals("0") )
            {
                if(palet_kodVar)
                {
                    if (kodVar)
                    {
                        urun_listesi.remove(tmpObj);
                        for(Urun_tag w:urun_listesi){
                            if (w.kod.equals(tag.palet_kod))
                            {
                                int x =0;
                                for(Urun_tag a:urun_listesi){
                                    if(a.palet_kod.equals(tag.palet_kod)){
                                        x++;
                                    }
                                }
                                x = x - 1;
                                if (x == 0)
                                    x = Integer.parseInt(w.palet_dizim_sayisi);
                                w.aciklama = "" + (x);
                            }
                        }
                    }
                    else
                    {
                        urun_listesi.add(tag);
                        for(Urun_tag w:urun_listesi){
                            if (w.kod.equals(tag.palet_kod))
                            {
                                int x =0;
                                for(Urun_tag a:urun_listesi){
                                    if(a.palet_kod.equals(tag.palet_kod)){
                                        x++;
                                    }
                                }
                                x = x - 1;
                                if (x == 0)
                                    x = Integer.parseInt(w.palet_dizim_sayisi);
                                w.aciklama = "" + (x);
                            }
                        }
                    }
                }
                    else
                {
                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                            .setTitleText("İŞLEM İÇİN UYGUN OLMAYAN ÜRÜN")
                            .setContentTextSize(25)
                            .setContentText("İlk önce paketin bulunduğu palet etiketini okutunuz. \r\n İşleme uygun olmayan etiket.")
                            .showCancelButton(false)
                            .show();

                }
            }
            else if (kodVar)
            {
                Urun_tag tmpObj1=null;
                for (Urun_tag w : urun_listesi)
                {
                    if(w.kod.equals(tag.kod) || w.palet_kod.equals(tag.kod)){
                        tmpObj1=w;
                    }
                }
                if(tmpObj1!=null) {
                    urun_listesi.remove(tmpObj1);
                }
            }
            else
            {
                if (tag.etiket_turu.equals("1") || tag.etiket_turu.equals("2")) {
                    tag.aciklama = tag.palet_dizim_sayisi;
                }else {
                    tag.aciklama = "1";
                }
                urun_listesi.add(tag);
            }
            updateListviewItem();

        }
        catch (Exception ex)
        {
            Genel.printStackTrace(ex,getContext());
        }
    }


    private class fn_btn_01 implements View.OnClickListener {
        @Override
        public void onClick(View view)
        {
            try
            {
                if (urun_listesi.size() == 0)
                {
                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                            .setTitleText("HATALI İŞLEM")
                            .setContentTextSize(25)
                            .setContentText("Ürün listenizde ürün bulunmamaktadır. \r\n Hatalı İşlem.")
                            .showCancelButton(false)
                            .show();
                }
                else
                {
                    for (int i = 0; i < urun_listesi.size(); i++)
                    {
                        Urun_tag tag = urun_listesi.get(i);
                        request_string v_Gelen=new request_string();
                        //todo substring 10 var aynı anlama mı geliyor bak.
                        v_Gelen.set_value(tag.rfid.substring(10));
                        v_Gelen.set_zaktif_alt_tesis(_ayaraktifalttesis);
                        v_Gelen.set_zaktif_tesis(_ayaraktiftesis);
                        v_Gelen.set_zkullaniciadi(_zkullaniciadi);
                        v_Gelen.set_zsifre(_zsifre);
                        v_Gelen.set_zsunucu_ip_adresi(_ayarsunucuip);
                        v_Gelen.set_zsurum(_sbtVerisyon);
                        v_Gelen.setAktif_kullanici(_ayaraktifkullanici);
                        v_Gelen.setAktif_sunucu(_ayaraktifsunucu);

                        Genel.showProgressDialog(getContext());
                        String _FlagDurum = persos.fn_flag_islemtipi(v_Gelen);
                        Genel.dismissProgressDialog();

                        new SweetAlertDialogG(getContext(), SweetAlertDialogG.PROGRESS_TYPE)
                                .setTitleText("DURUM")
                                .setContentText(_FlagDurum)
                                .setContentTextSize(20)
                                .setConfirmText("TAMAM")
                                .showCancelButton(false)
                                .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialogG sDialog) {
                                        sDialog.dismissWithAnimation();
                                        return;
                                    }
                                }).show();
                    }
                }
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
            frg_kirli_ambalaj_menu_panel fragmentyeni = new frg_kirli_ambalaj_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_kirli_ambalaj_menu_panel").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_okumaDegistir implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Genel.showProgressDialog(getContext());
            if(_btnOkuma.getText().toString().equals("KAREKOD")){
                ((GirisSayfasi) getActivity()).fn_ModRFID();
                _btnOkuma.setText("RFID");
            }else{
                ((GirisSayfasi) getActivity()).fn_ModBarkod();
                _btnOkuma.setText("KAREKOD");
            }
            Genel.dismissProgressDialog();
        }
    }

}
