package com.etimaden.SevkiyatIslemleri.Zayiat_islemleri.Zayi_depo_kabul;

import static com.etimaden.cSabitDegerler._ipAdresi3G;
import static com.etimaden.cSabitDegerler._sbtVerisyon;
import static com.etimaden.cSabitDegerler._zkullaniciadi;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.GirisSayfasi;
import com.etimaden.SevkiyatIslemleri.Zayiat_islemleri.frg_zayi_aktivasyon;
import com.etimaden.SevkiyatIslemleri.Zayiat_islemleri.frg_zayi_depo_secimi;
import com.etimaden.SevkiyatIslemleri.frg_aktif_isemri_indirme;
import com.etimaden.SevkiyatIslemleri.frg_sevkiyat_menu_panel;
import com.etimaden.adapter.apmblSevkiyatZayiIsEmiriIndirme;
import com.etimaden.adapter.apmblSevkiyatZayiIsEmirleri;
import com.etimaden.adapterclass.Zayi_urun_data;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cResponseResult.Sevkiyat_isemri;
import com.etimaden.genel.Genel;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.Urun_tag;
import com.etimaden.persosclass.Zayi;
import com.etimaden.persosclass.Zayi_urun;
import com.etimaden.request.request_bos;
import com.etimaden.request.request_secEtiket;
import com.etimaden.request.request_sevkiyat_isemri;
import com.etimaden.request.request_sevkiyat_isemri_uruntag_list_uruntag;
import com.etimaden.request.request_sevkiyat_zayi_arac;
import com.etimaden.request.request_sevkiyat_zayi_zayiurun_list_zayiurun;
import com.etimaden.request.request_string;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class frg_zayi_isemri_indirme extends Fragment {

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


    ImageView _imgBilgi;
    TextView _txtBaslik;
    TextView _txtYuklemeMiktar;
    Button _btnTamam;
    ListView _zayi_urun_list;
    Button _btngeri;

    ArrayList<Zayi_urun> urun_listesi_indirilen;
    ArrayList<Zayi_urun> urun_listesi_yuklenen;
    ArrayList<Zayi_urun_data> urun_listesi;

    Zayi aktif_sevk_isemri = null;
    Zayi_urun_data _Secili = null;

    private apmblSevkiyatZayiIsEmiriIndirme adapter;


    public frg_zayi_isemri_indirme() {
        // Required empty public constructor
    }

    public static frg_zayi_isemri_indirme newInstance()
    {
        return new frg_zayi_isemri_indirme();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.frg_zayi_isemri_indirme, container, false);
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

    public void fn_senddata(Zayi v_aktif_sevk_isemri)
    {
        this.aktif_sevk_isemri=v_aktif_sevk_isemri;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        new VeriTabani(getContext()).fn_EpcTemizle();

        _myIslem = new VeriTabani(getContext());


        _txtYuklemeMiktar = (TextView) getView().findViewById(R.id.txtYuklemeMiktar);

        _imgBilgi = (ImageView) getView().findViewById(R.id.imgBilgi);
        _imgBilgi.setOnClickListener(new fn_imgBilgi());

        _txtBaslik = (TextView) getView().findViewById(R.id.txtBaslik);
        String baslik = "ARAÇ : " + aktif_sevk_isemri.zay_eski_plaka + " - SAP KODU : " + aktif_sevk_isemri.zay_sap_kodu ;
        baslik += "\r\nMAL KABUL LİSTESİ";
        _txtBaslik.setText(baslik);


        _btnTamam = (Button)getView().findViewById(R.id.btnTamam);
        _btnTamam.playSoundEffect(0);
        _btnTamam.setOnClickListener(new fn_btnTamam());

        _btngeri = (Button)getView().findViewById(R.id.btncikis);
        _btngeri.playSoundEffect(0);
        _btngeri.setOnClickListener(new fn_Geri());

        _zayi_urun_list = (ListView) getView().findViewById(R.id.zayi_urun_list);

        _zayi_urun_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _Secili = urun_listesi.get(position);
            }
        });
        _zayi_urun_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                _Secili = urun_listesi.get(position);
                fn_listview_longclick();
                return false;
            }
        });

        fn_AyarlariYukle();

        //barkod,rfid,ikiside
        ((GirisSayfasi) getActivity()).fn_ModBoth();

        urun_listesi= new ArrayList<Zayi_urun_data>();

        request_string _Param1= new request_string();
        _Param1.set_zsunucu_ip_adresi(_ayarsunucuip);
        _Param1.set_zaktif_alt_tesis(_ayaraktifalttesis);
        _Param1.set_zaktif_tesis(_ayaraktiftesis);
        _Param1.set_zsurum(_sbtVerisyon);
        _Param1.set_zkullaniciadi(_zkullaniciadi);
        _Param1.set_zsifre(_zsifre);
        _Param1.setAktif_sunucu(_ayaraktifsunucu);
        _Param1.setAktif_kullanici(_ayaraktifkullanici);

        _Param1.set_value(aktif_sevk_isemri.zay_id_isemrihareket_yeni);

        Genel.showProgressDialog(getContext());
        List<Zayi_urun> result = persos.fn_sec_zayi_urun_listesi(_Param1);
        urun_listesi_indirilen=new ArrayList<>();
        if(result!=null) {
            urun_listesi_indirilen = new ArrayList<>(result);
        }
        Genel.dismissProgressDialog();
        if (urun_listesi_indirilen == null)
        {
            urun_listesi_indirilen = new ArrayList<Zayi_urun>();
        }


        _Param1.set_value(aktif_sevk_isemri.zay_id_isemrihareket_eski);

        Genel.showProgressDialog(getContext());
        result = persos.fn_sec_zayi_urun_listesi(_Param1);
        urun_listesi_yuklenen=new ArrayList<>();
        if(result!=null) {
            urun_listesi_yuklenen = new ArrayList<>(result);
        }
        Genel.dismissProgressDialog();

        if (urun_listesi_yuklenen == null)
        {
            urun_listesi_yuklenen = new ArrayList<Zayi_urun>();
        }

        updateListviewItem();
    }



    private void updateListviewItem()
    {
        try
        {
            urun_listesi= new ArrayList<Zayi_urun_data>();
            ArrayList<Zayi_urun> ul_bekleyen = new ArrayList<Zayi_urun>();
            ArrayList<Zayi_urun> ul_indirilen = new ArrayList<Zayi_urun>();
            int miktar = 0;
            boolean listedeYok=true;
            for (Zayi_urun w : urun_listesi_yuklenen ) {
                listedeYok=true;
                for (Zayi_urun a : urun_listesi_indirilen ) {
                    if(a.serino.equals(w.serino)){
                        listedeYok=false;
                    }
                }
                if(listedeYok==true){
                    ul_bekleyen.add(w);
                }
            }

            for (Zayi_urun w : urun_listesi_indirilen ) {
                listedeYok=true;
                for (Zayi_urun a : urun_listesi_yuklenen ) {
                    if(a.serino.equals(w.serino)){
                        listedeYok=false;
                    }
                }
                if(listedeYok==false){
                    ul_indirilen.add(w);
                }
            }
            for(Zayi_urun urun : ul_bekleyen){
                urun_listesi.add(new Zayi_urun_data(urun, Color.RED,R.drawable.redpoint,true));
            }
            for(Zayi_urun urun : ul_indirilen){
                urun_listesi.add(new Zayi_urun_data(urun, Color.GREEN,R.drawable.greenpoint,false));
                try{ miktar += Integer.parseInt(urun.agirlik); }catch (Exception ex){ ex.printStackTrace(); }
            }

            _txtYuklemeMiktar.setText("İNDİRME MİKTARI = " + miktar);

            if (adapter != null) {
                adapter.clear();
                _zayi_urun_list.setAdapter(adapter);
            }

           adapter=new apmblSevkiyatZayiIsEmiriIndirme(urun_listesi,getContext());
            _zayi_urun_list.setAdapter(adapter);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void fn_barkodOkundu(String barkod)
    {

        try
        {
            barkod = barkod.substring(barkod.length()-24);
            if (!isReadable && barkod.length() == 24)
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

            Urun_tag urun = tag;

            if (urun == null)
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Lütfen uygun bir ürün etiketi okutunuz. Ürün kaydı bulunamadı..")
                        .showCancelButton(false)
                        .show();
                isReadable = true;
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
        isReadable = true;

    }

    public void fn_rfidOkundu(String rfid)
    {
        try
        {
            if (!isReadable && rfid.length() == 24)
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

            Urun_tag urun = tag;

            if (urun == null)
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Lütfen uygun bir ürün etiketi okutunuz. Ürün kaydı bulunamadı..")
                        .showCancelButton(false)
                        .show();
                isReadable = true;
                return;
            }
            else
            {
                urunDegerlendir(tag);
            }
        }
        catch (Exception ex)
        {
            Genel.dismissProgressDialog();
            ex.printStackTrace();
        }
        isReadable = true;
    }

    private void urunDegerlendir(Urun_tag tag)
    {
        try
        {
            boolean kodVarIndirilen=false;
            for(Zayi_urun w : urun_listesi_indirilen){
                if(w.serino.equals(tag.palet_kod)){
                    kodVarIndirilen=true;
                }
            }

            boolean kodVarYuklenen=false;
            for(Zayi_urun w : urun_listesi_yuklenen){
                if(w.serino.equals(tag.palet_kod)){
                    kodVarYuklenen=true;
                }
            }

            if (kodVarIndirilen==true)
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("UYARI")
                        .setContentTextSize(25)
                        .setContentText("BU ÜRÜN ZATEN İNDİRİLMİŞ OLARAK SİSTEMDE GÖRÜNMEKTEDİR.")
                        .showCancelButton(false)
                        .show();
                return;
            } else if (kodVarYuklenen==true){
                Zayi_urun zay_urn = new Zayi_urun();
                zay_urn.agirlik = tag.palet_agirligi;
                zay_urn.serino = tag.kod;

                request_sevkiyat_zayi_zayiurun_list_zayiurun _Param1= new request_sevkiyat_zayi_zayiurun_list_zayiurun();
                _Param1.set_zsunucu_ip_adresi(_ayarsunucuip);
                _Param1.set_zaktif_alt_tesis(_ayaraktifalttesis);
                _Param1.set_zaktif_tesis(_ayaraktiftesis);
                _Param1.set_zsurum(_sbtVerisyon);
                _Param1.set_zkullaniciadi(_zkullaniciadi);
                _Param1.set_zsifre(_zsifre);
                _Param1.setAktif_sunucu(_ayaraktifsunucu);
                _Param1.setAktif_kullanici(_ayaraktifkullanici);

                _Param1.setSevk(aktif_sevk_isemri);
                _Param1.setDepo_urun(zay_urn);
                _Param1.setUrun_listesi(urun_listesi_indirilen);

                Genel.showProgressDialog(getContext());
                Boolean islem_sonucu = persos.fn_ekleZayitUrun_cikarma(_Param1);
                Genel.dismissProgressDialog();


                if (!islem_sonucu)
                {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("İşlem Başarısız")
                            .setContentTextSize(25)
                            .setContentText("Kayıt yapılamadı. \r\n Veritabanı hatası")
                            .showCancelButton(false)
                            .show();
                    return;
                }
            }
                else
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("UYARI")
                        .setContentText("BU ÜRÜN BU ARAÇ İÇERİSİNDE GÖRÜNMEMEKTEDİR.")
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

            //urun_listesi_indirilen = Program.persos.sec_zayi_urun_listesi(aktif_sevk_isemri.zay_id_isemrihareket_yeni);

            //urun_listesi_indirilen.Add(urun_listesi_yuklenen.Where(w => w.serino.Equals(tag.kod)).ElementAt(0));

            updateListviewItem();
        }
        catch (Exception ex)
        {
            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("")
                    .setContentTextSize(25)
                    .setContentText("İlgili iş emri kaydı bulunamadı. \r\n "+ ex.toString())
                    .showCancelButton(false)
                    .show();
        }
    }

    private void fn_listview_longclick(){

        try {
            if (_Secili != null && _Secili.getBekleyen() == true) {
                final Zayi_urun tag = _Secili.getZayi_urun();
                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("SORU")
                        .setContentText("SERİ NO : " + tag.serino + "\r\n Seri nolu ürün için kayıt değişimi yapılacak. Onaylıyor musunuz ?")
                        .setContentTextSize(20)
                        .setConfirmText("EVET")
                        .setCancelText("HAYIR")
                        .showCancelButton(true)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();

                                request_string _Param1 = new request_string();
                                _Param1.set_zsunucu_ip_adresi(_ayarsunucuip);
                                _Param1.set_zaktif_alt_tesis(_ayaraktifalttesis);
                                _Param1.set_zaktif_tesis(_ayaraktiftesis);
                                _Param1.set_zsurum(_sbtVerisyon);
                                _Param1.set_zkullaniciadi(_zkullaniciadi);
                                _Param1.set_zsifre(_zsifre);
                                _Param1.setAktif_sunucu(_ayaraktifsunucu);
                                _Param1.setAktif_kullanici(_ayaraktifkullanici);

                                _Param1.set_value(tag.serino);

                                Genel.showProgressDialog(getContext());
                                Boolean rr = persos.fn_update_bas_etiket(_Param1);
                                Genel.dismissProgressDialog();
                                if (rr == true) {
                                    new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("Onay")
                                            .setContentText("İşlem başarı ile tamamlandı. Lütfen etikent basmayı şimdi deneyiniz.")
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
                                } else {
                                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("HATA")
                                            .setContentTextSize(25)
                                            .setContentText("İşlem yapılamadı. Tekrar deneyiniz!")
                                            .showCancelButton(false)
                                            .show();
                                }
                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                return;
                            }
                        })
                        .show();


            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private class fn_imgBilgi implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            try
            {
                Genel.playButtonClikSound(getContext());
                String str = "";
                str += "SAP KODU : " + aktif_sevk_isemri.zay_sap_kodu;
                str += "\r\nÜRÜN ADI : " + aktif_sevk_isemri.zay_urun_adi;
                str += "\r\nPLAKA: " + aktif_sevk_isemri.zay_eski_plaka;
                new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText("DETAY")
                        .setContentText(str)
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

            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
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


    private class fn_btnTamam implements View.OnClickListener {
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
