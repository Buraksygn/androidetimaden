package com.etimaden.SevkiyatIslemleri;

import static com.etimaden.cSabitDegerler._ipAdresi3G;
import static com.etimaden.cSabitDegerler._sbtVerisyon;
import static com.etimaden.cSabitDegerler._zkullaniciadi;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;

import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
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
import com.etimaden.adapter.apmblSevkiyatAktifIsEmiriIndirme;
import com.etimaden.adapter.apmblSevkiyatAktifIsEmiriYukleme;
import com.etimaden.adapterclass.Urun_tag_data;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cResponseResult.Sevkiyat_isemri;
import com.etimaden.cResponseResult.Urun_sevkiyat;
import com.etimaden.genel.Genel;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.Urun_tag;
import com.etimaden.persosclass.aktarim;
import com.etimaden.request.request_secEtiket;
import com.etimaden.request.request_sevkiyat_aktarim;
import com.etimaden.request.request_sevkiyat_isemri;
import com.etimaden.request.request_sevkiyat_isemri_uruntag_list_uruntag;
import com.etimaden.request.request_string;
import com.etimaden.request.request_string_string;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class frg_aktif_isemri_yukleme extends Fragment {

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
    ImageButton _btnYenile;
    ListView _urun_list;
    Button _btngeri;


    ArrayList<Urun_sevkiyat> urun_listesi;
    Sevkiyat_isemri aktif_sevk_isemri = null;
    Urun_sevkiyat _Secili = null;

    private apmblSevkiyatAktifIsEmiriYukleme adapter;

    public static frg_aktif_isemri_yukleme newInstance() {

        return new frg_aktif_isemri_yukleme();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_aktif_isemri_yukleme, container, false);
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

    public void fn_senddata(Sevkiyat_isemri v_aktif_sevk_isemri)
    {
        this.aktif_sevk_isemri=v_aktif_sevk_isemri;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_NUMPAD_ENTER)
                {
                    System.out.println("ENTER YAKALANDI  frg_aktif_isemri_yukleme !!!!!!");
                    return true;
                }
                return false;
            }
        });

        //barkod,rfid,ikiside
        //((GirisSayfasi) getActivity()).fn_ModBoth();
        ((GirisSayfasi) getActivity()).fn_ModBarkod();
        _myIslem = new VeriTabani(getContext());
        fn_AyarlariYukle();
        _myIslem.fn_EpcTemizle();
        ((GirisSayfasi) getActivity()).fn_ListeTemizle();

        _txtYuklemeMiktar = (TextView) getView().findViewById(R.id.txtYuklemeMiktar);

        _imgBilgi = (ImageView) getView().findViewById(R.id.imgBilgi);
        _imgBilgi.setOnClickListener(new fn_imgBilgi());

        _btnYenile = (ImageButton) getView().findViewById(R.id.btnYenile);
        _btnYenile.setOnClickListener(new fn_btnYenile());

        _txtBaslik = (TextView) getView().findViewById(R.id.txtBaslik);
        String baslik = "ARAÇ : " + aktif_sevk_isemri.arac_plaka + " - SAP KODU : " + aktif_sevk_isemri.kod_sap ;
        if (aktif_sevk_isemri.alici_isletme.equals(""))
        {
            baslik += "\r\nALICI : " + aktif_sevk_isemri.alici;
            baslik += "\r\nBOOKING NO : " + aktif_sevk_isemri.bookingno;
        }
        else
            baslik += "\r\nALICI : " + aktif_sevk_isemri.alici_isletme ;
        baslik += "\r\nYÜKLEME LİSTESİ";
        _txtBaslik.setText(baslik);

        _btnTamam = (Button)getView().findViewById(R.id.btnTamam);
        _btnTamam.playSoundEffect(0);
        _btnTamam.setOnClickListener(new fn_btnTamam());

        _btngeri = (Button)getView().findViewById(R.id.btnGeri);
        _btngeri.playSoundEffect(0);
        _btngeri.setOnClickListener(new fn_Geri());

        _urun_list = (ListView) getView().findViewById(R.id.yukleme_list);

        _urun_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _Secili = urun_listesi.get(position);
            }
        });
        _urun_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                _Secili = urun_listesi.get(position);
                fn_listview_longclick();
                return false;
            }
        });

        fn_AyarlariYukle();

        urun_listesi= new ArrayList<Urun_sevkiyat>();
        adapter=new apmblSevkiyatAktifIsEmiriYukleme(urun_listesi,getContext());
        _urun_list.setAdapter(adapter);

        request_string _Param1= new request_string();
        _Param1.set_zsunucu_ip_adresi(_ayarsunucuip);
        _Param1.set_zaktif_alt_tesis(_ayaraktifalttesis);
        _Param1.set_zaktif_tesis(_ayaraktiftesis);
        _Param1.set_zsurum(_sbtVerisyon);
        _Param1.set_zkullaniciadi(_zkullaniciadi);
        _Param1.set_zsifre(_zsifre);
        _Param1.setAktif_sunucu(_ayaraktifsunucu);
        _Param1.setAktif_kullanici(_ayaraktifkullanici);

        _Param1.set_value(aktif_sevk_isemri.islem_id);

        Genel.showProgressDialog(getContext());
        List<Urun_sevkiyat> result = persos.fn_sec_sevkiyat_urun_listesi(_Param1);
        urun_listesi=new ArrayList<>();
        if(result!=null) {
            urun_listesi = new ArrayList<>(result);
        }
        Genel.dismissProgressDialog();

        try
        {
            request_string_string _Param= new request_string_string();
            _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
            _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
            _Param.set_zaktif_tesis(_ayaraktiftesis);
            _Param.set_zsurum(_sbtVerisyon);
            _Param.set_zkullaniciadi(_zkullaniciadi);
            _Param.set_zsifre(_zsifre);
            _Param.setAktif_sunucu(_ayaraktifsunucu);
            _Param.setAktif_kullanici(_ayaraktifkullanici);

            _Param.set_value(aktif_sevk_isemri.isemri_detay_id);
            _Param.set_value2(aktif_sevk_isemri.indirmeBindirme);

            Genel.showProgressDialog(getContext());
            List<String> miktar_palet = persos.fn_aktif_sevk_kalan_miktar_palet(_Param);
            Genel.dismissProgressDialog();
            if (miktar_palet!=null){
                aktif_sevk_isemri.kalan_agirlik = miktar_palet.get(0);
                aktif_sevk_isemri.kalan_palet_sayisi = miktar_palet.get(1);
            }
            else{
                aktif_sevk_isemri.kalan_agirlik = "0";
                aktif_sevk_isemri.kalan_palet_sayisi = "0";;
            }

        }
        catch (Exception ex)
        {
            Genel.printStackTrace(ex,getContext());
            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("İşlem Başarısız")
                    .setContentTextSize(25)
                    .setContentText("Araç detayına ulaşılamadı. Daha sonra tekrar deneyiniz \r\n \"network hata kodu = 2")
                    .showCancelButton(false)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            frg_sevkiyat_menu_panel fragmentyeni = new frg_sevkiyat_menu_panel();
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_sevkiyat_menu_panel").addToBackStack(null);
                            fragmentTransaction.commit();
                            return;
                        }
                    })
                    .show();
            return;
        }
        if (urun_listesi == null)
        {
            urun_listesi = new ArrayList<>();
            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("İşlem Başarısız")
                    .setContentTextSize(25)
                    .setContentText("Araç detayına ulaşılamadı. Daha sonra tekrar deneyiniz \r\n \"network hata kodu = 2")
                    .showCancelButton(false)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            frg_sevkiyat_menu_panel fragmentyeni = new frg_sevkiyat_menu_panel();
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_sevkiyat_menu_panel").addToBackStack(null);
                            fragmentTransaction.commit();
                            return;
                        }
                    })
                    .show();
            return;
        }

        updateListviewItem();
    }

    private void updateListviewItem()
    {
        try
        {


            int miktar = 0;

            for (Urun_sevkiyat l : urun_listesi ) {
                miktar += Integer.parseInt(l.palet_agirligi);
            }

            String msg="YÜKLENEN MİKTAR = " + miktar;
            _txtYuklemeMiktar.setText(msg);
            aktif_sevk_isemri.yapilan_miktar = msg;
            aktif_sevk_isemri.yapilan_adet = "" + urun_listesi.size();

            if (adapter != null) {
                adapter.clear();
                //_urun_list.setAdapter(adapter);

                //adapter=new apmblSevkiyatAktifIsEmiriYukleme(urun_listesi,getContext());
                adapter.addAll(urun_listesi);
                //_urun_list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

        }
        catch (Exception ex)
        {
            Genel.printStackTrace(ex,getContext());
        }
    }

    public void barkodOkundu(String barkod)
    {
        try
        {
            barkod = barkod.substring(barkod.length()-24);
            if (!isReadable)
            {
                return;
            }
            isReadable = false;
            Genel.playQuestionSound(getContext());

            request_string v_Gelen=new request_string();
            v_Gelen.set_value(barkod);
            v_Gelen.set_zaktif_alt_tesis(_ayaraktifalttesis);
            v_Gelen.set_zaktif_tesis(_ayaraktiftesis);
            v_Gelen.set_zkullaniciadi(_zkullaniciadi);
            v_Gelen.set_zsifre(_zsifre);
            v_Gelen.set_zsunucu_ip_adresi(_ayarsunucuip);
            v_Gelen.set_zsurum(_sbtVerisyon);
            v_Gelen.setAktif_kullanici(_ayaraktifkullanici);
            v_Gelen.setAktif_sunucu(_ayaraktifsunucu);

            Genel.showProgressDialog(getContext());
            Urun_sevkiyat tag = persos.fn_sec_sevkiyat_urun(v_Gelen);
            Genel.dismissProgressDialog();

            Urun_sevkiyat urun = tag;

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

            if (urun.islem_durumu.equals("3"))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Ürün satılmış ürünler içerinde görünmektedir. Lütfen ilgili kişiye durumu bildiriniz.")
                        .showCancelButton(false)
                        .show();
                isReadable = true;
                return;
            }
            else if (urun.islem_durumu.equals("0"))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Ürün üretilmemiş ürünler içerinde görünmektedir. Lütfen ilgili kişiye durumu bildiriniz.")
                        .showCancelButton(false)
                        .show();
                isReadable = true;
                return;
            }
            else if (urun.islem_durumu.equals("2"))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Ürün sevk edilemez ürünler içerinde görünmektedir. Lütfen ilgili kişiye durumu bildiriniz.")
                        .showCancelButton(false)
                        .show();
                isReadable = true;
                return;
            }
            else if (urun.islem_durumu.equals("4"))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Ürün etiket değişimi bekleyen ürünler içerinde görünmektedir. Lütfen ilgili kişiye durumu bildiriniz.")
                        .showCancelButton(false)
                        .show();
                isReadable = true;
                return;
            }
            else if (urun.islem_durumu.equals("200"))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Ürün TSE tarafından ayrılmış ürünler içerinde görünmektedir. Lütfen ilgili kişiye durumu bildiriniz.")
                        .showCancelButton(false)
                        .show();
                isReadable = true;
                return;
            }
            else if (urun.islem_durumu.equals("201"))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Ürün işletme tarafından ayrılmış ürünler içerinde görünmektedir. Lütfen ilgili kişiye durumu bildiriniz.")
                        .showCancelButton(false)
                        .show();
                isReadable = true;
                return;
            }
            else if (urun.islem_durumu.equals("350"))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Ürün torba tipi değişimi bekleyen ürünler içerinde görünmektedir. Lütfen ilgili kişiye durumu bildiriniz.")
                        .showCancelButton(false)
                        .show();
                isReadable = true;
                return;
            }
            else if (urun.islem_durumu.equals("351"))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Ürün kirli torba değişimi bekleyen ürünler içerinde görünmektedir. Lütfen ilgili kişiye durumu bildiriniz.")
                        .showCancelButton(false)
                        .show();
                isReadable = true;
                return;
            }
            else if (urun.islem_durumu.equals("352"))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Ürün geribesleme bekleyen ürünler içerinde görünmektedir. Lütfen ilgili kişiye durumu bildiriniz.")
                        .showCancelButton(false)
                        .show();
                isReadable = true;
                return;
            }
            else if (urun.islem_durumu.equals("353"))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Ürün palet düzenleme bekleyen ürünler içerinde görünmektedir. Lütfen ilgili kişiye durumu bildiriniz.")
                        .showCancelButton(false)
                        .show();
                isReadable = true;
                return;
            }
            else if (urun.islem_durumu.equals("354"))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Ürün elleçleme bekleyen ürünler içerinde görünmektedir. Lütfen ilgili kişiye durumu bildiriniz.")
                        .showCancelButton(false)
                        .show();
                isReadable = true;
                return;
            }
            else if (urun.kilitli.equals("True"))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Ürün manipülasyon bekleyen ürünler içerinde görünmektedir. Lütfen ilgili kişiye durumu bildiriniz.")
                        .showCancelButton(false)
                        .show();
                isReadable = true;
                return;
            }
           // else if (tag == null || (tag.islem_durumu != "401" && tag.islem_durumu != "1" && tag.islem_durumu != "8"))
            else if (tag == null || (!tag.islem_durumu.equals("401")  && !tag.islem_durumu.equals("1")  && !tag.islem_durumu.equals("8")))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("İŞLEM İÇİN UYGUN OLMAYAN ÜRÜN")
                        .setContentTextSize(25)
                        .setContentText("Ürün yapmak istediğiniz işlem için uygun değildir.\r\n İşleme uygun olmayan etiket.")
                        .showCancelButton(false)
                        .show();
                isReadable = true;
                return;
            }
            else if (tag.etiket_turu.equals("1") || tag.etiket_turu.equals("0") )
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("İŞLEM İÇİN UYGUN OLMAYAN ÜRÜN")
                        .setContentTextSize(25)
                        .setContentText("Shrinkleme işlemi yapılmamış veya Bigbag olmayan bir ürün sevk edilemez.\r\n İşleme uygun olmayan etiket.")
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

    public void rfidOkundu(String rfid)
    {
        try
        {
            if (!isReadable)
            {
                return;
            }
            isReadable = false;
            Genel.playQuestionSound(getContext());

            request_string v_Gelen=new request_string();
            v_Gelen.set_value(rfid);
            v_Gelen.set_zaktif_alt_tesis(_ayaraktifalttesis);
            v_Gelen.set_zaktif_tesis(_ayaraktiftesis);
            v_Gelen.set_zkullaniciadi(_zkullaniciadi);
            v_Gelen.set_zsifre(_zsifre);
            v_Gelen.set_zsunucu_ip_adresi(_ayarsunucuip);
            v_Gelen.set_zsurum(_sbtVerisyon);
            v_Gelen.setAktif_kullanici(_ayaraktifkullanici);
            v_Gelen.setAktif_sunucu(_ayaraktifsunucu);

            Genel.showProgressDialog(getContext());
            Urun_sevkiyat tag = persos.fn_sec_sevkiyat_urun(v_Gelen);
            Genel.dismissProgressDialog();

            Urun_sevkiyat urun = tag;

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

            if (urun.islem_durumu.equals("3"))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Ürün satılmış ürünler içerinde görünmektedir. Lütfen ilgili kişiye durumu bildiriniz.")
                        .showCancelButton(false)
                        .show();
                isReadable = true;
                return;
            }
            else if (urun.islem_durumu.equals("0"))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Ürün üretilmemiş ürünler içerinde görünmektedir. Lütfen ilgili kişiye durumu bildiriniz.")
                        .showCancelButton(false)
                        .show();
                isReadable = true;
                return;
            }
            else if (urun.islem_durumu.equals("2"))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Ürün sevk edilemez ürünler içerinde görünmektedir. Lütfen ilgili kişiye durumu bildiriniz.")
                        .showCancelButton(false)
                        .show();
                isReadable = true;
                return;
            }
            else if (urun.islem_durumu.equals("4"))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Ürün etiket değişimi bekleyen ürünler içerinde görünmektedir. Lütfen ilgili kişiye durumu bildiriniz.")
                        .showCancelButton(false)
                        .show();
                isReadable = true;
                return;
            }
            else if (urun.islem_durumu.equals("200"))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Ürün TSE tarafından ayrılmış ürünler içerinde görünmektedir. Lütfen ilgili kişiye durumu bildiriniz.")
                        .showCancelButton(false)
                        .show();
                isReadable = true;
                return;
            }
            else if (urun.islem_durumu.equals("201"))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Ürün işletme tarafından ayrılmış ürünler içerinde görünmektedir. Lütfen ilgili kişiye durumu bildiriniz.")
                        .showCancelButton(false)
                        .show();
                isReadable = true;
                return;
            }
            else if (urun.islem_durumu.equals("350"))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Ürün torba tipi değişimi bekleyen ürünler içerinde görünmektedir. Lütfen ilgili kişiye durumu bildiriniz.")
                        .showCancelButton(false)
                        .show();
                isReadable = true;
                return;
            }
            else if (urun.islem_durumu.equals("351"))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Ürün kirli torba değişimi bekleyen ürünler içerinde görünmektedir. Lütfen ilgili kişiye durumu bildiriniz.")
                        .showCancelButton(false)
                        .show();
                isReadable = true;
                return;
            }
            else if (urun.islem_durumu.equals("352"))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Ürün geribesleme bekleyen ürünler içerinde görünmektedir. Lütfen ilgili kişiye durumu bildiriniz.")
                        .showCancelButton(false)
                        .show();
                isReadable = true;
                return;
            }
            else if (urun.islem_durumu.equals("353"))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Ürün palet düzenleme bekleyen ürünler içerinde görünmektedir. Lütfen ilgili kişiye durumu bildiriniz.")
                        .showCancelButton(false)
                        .show();
                isReadable = true;
                return;
            }
            else if (urun.islem_durumu.equals("354"))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Ürün elleçleme bekleyen ürünler içerinde görünmektedir. Lütfen ilgili kişiye durumu bildiriniz.")
                        .showCancelButton(false)
                        .show();
                isReadable = true;
                return;
            }
            else if (urun.kilitli.equals("True"))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Ürün manipülasyon bekleyen ürünler içerinde görünmektedir. Lütfen ilgili kişiye durumu bildiriniz.")
                        .showCancelButton(false)
                        .show();
                isReadable = true;
                return;
            }
            else if (tag == null || (!tag.islem_durumu.equals("401") && !tag.islem_durumu.equals("1") && !tag.islem_durumu.equals("8")))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("İŞLEM İÇİN UYGUN OLMAYAN ÜRÜN")
                        .setContentTextSize(25)
                        .setContentText("Ürün yapmak istediğiniz işlem için uygun değildir.\r\n İşleme uygun olmayan etiket.")
                        .showCancelButton(false)
                        .show();
                isReadable = true;
                return;
            }
            else if (tag.etiket_turu.equals("1") || tag.etiket_turu.equals("0"))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("İŞLEM İÇİN UYGUN OLMAYAN ÜRÜN")
                        .setContentTextSize(25)
                        .setContentText("Shrinkleme işlemi yapılmamış veya Bigbag olmayan bir ürün sevk edilemez.\r\n İşleme uygun olmayan etiket.")
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

    private void urunDegerlendir(final Urun_sevkiyat tag)
    {
        try {
            String[] urun_char12 = tag.karakteristikler.split(",",-1);
            boolean kodVar = false;
            for (Urun_sevkiyat w : urun_listesi) {
                if (w.kod.equals(tag.kod)) {
                    kodVar = true;
                    break;
                }
            }

            if (kodVar == true) {

                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("ÜRÜN ÇIKARMA")
                        .setContentText(tag.kod + " SERİ NUMARALI ÜRÜNÜ YÜKLEME LİSTESİNDEN ÇIKARMAK İSTİYOR MUSUNUZ ?")
                        .setContentTextSize(20)
                        .setConfirmText("EVET")
                        .setCancelText("HAYIR")
                        .showCancelButton(true)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();

                                aktarim akt = new aktarim();
                                akt.akt_aktarimdurumu = "0";
                                akt.akt_aktarimtipi = "5";
                                akt.akt_isemri_detay = aktif_sevk_isemri.isemri_detay_id;
                                akt.akt_isletme = aktif_sevk_isemri.isletme;
                                akt.akt_kod_isemri = aktif_sevk_isemri.isemri_id;
                                akt.akt_kod_sap = aktif_sevk_isemri.kod_sap;
                                akt.akt_kod_urun = aktif_sevk_isemri.urun_kodu;
                                akt.akt_sevk_har_id = aktif_sevk_isemri.islem_id;
                                akt.akt_urn_palet_rfid = tag.rfid;
                                akt.akt_urn_palet_serino = tag.palet_kod;
                                akt.akt_urn_rfid = tag.rfid;
                                akt.akt_urn_serino = tag.palet_kod;
                                akt.akt_user_id = _ayaraktifkullanici;

                                request_sevkiyat_aktarim _Param1 = new request_sevkiyat_aktarim();
                                _Param1.set_zsunucu_ip_adresi(_ayarsunucuip);
                                _Param1.set_zaktif_alt_tesis(_ayaraktifalttesis);
                                _Param1.set_zaktif_tesis(_ayaraktiftesis);
                                _Param1.set_zsurum(_sbtVerisyon);
                                _Param1.set_zkullaniciadi(_zkullaniciadi);
                                _Param1.set_zsifre(_zsifre);
                                _Param1.setAktif_sunucu(_ayaraktifsunucu);
                                _Param1.setAktif_kullanici(_ayaraktifkullanici);

                                _Param1.set_aktarim(akt);

                                Genel.showProgressDialog(getContext());
                                //todo persos_aktarim classı icinde ama burda persosa ekliyorum.
                                Boolean islem_sonucu = persos.fn_ekle_aktarim(_Param1);
                                Genel.dismissProgressDialog();

                                if (islem_sonucu == false)
                                {
                                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("İşlem Başarısız")
                                            .setContentTextSize(25)
                                            .setContentText("Kayıt yapılamadı. \r\n Veritabanı hatası")
                                            .showCancelButton(false)
                                            .show();
                                }
                                else
                                {
                                    for(int i=0;i<urun_listesi.size();i++){
                                        if(urun_listesi.get(i).rfid.equals(tag.rfid)){
                                            urun_listesi.remove(i);
                                        }
                                    }
                                    updateListviewItem();
                                }
                                return;
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

                return;
            }
            else if (!tag.isletme.equals(aktif_sevk_isemri.isletme))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Ürün " + tag.isletme_adi + " ürünü olarak görünmektedir. İş emri ile işletme uyum sağlamıyor. Lütfen ilgili kişiye durumu bildiriniz.")
                        .showCancelButton(false)
                        .show();
                return;
            }
            else if (tag.islem_durumu.equals("3"))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Ürün satılmış ürünler içerinde görünmektedir. Lütfen ilgili kişiye durumu bildiriniz.")
                        .showCancelButton(false)
                        .show();
                return;
            }
            else if (tag.islem_durumu.equals("2"))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Ürün sevk edilemez ürünler içerinde görünmektedir. Lütfen ilgili kişiye durumu bildiriniz.")
                        .showCancelButton(false)
                        .show();
                return;
            }
            else if (tag.islem_durumu.equals("4"))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Ürün etiket değiişimi bekleyen ürünler içerinde görünmektedir. Lütfen ilgili kişiye durumu bildiriniz.")
                        .showCancelButton(false)
                        .show();
                return;
            }
            else if (tag.islem_durumu.equals("200"))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Ürün TSE tarafından ayrılmış ürünler içerinde görünmektedir. Lütfen ilgili kişiye durumu bildiriniz.")
                        .showCancelButton(false)
                        .show();
                return;
            }
            else if (tag.islem_durumu.equals("201"))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Ürün işletme tarafından ayrılmış ürünler içerinde görünmektedir. Lütfen ilgili kişiye durumu bildiriniz.")
                        .showCancelButton(false)
                        .show();
                return;
            }
            else if (tag.islem_durumu.equals("350") )
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Ürün torba tipi değişimi bekleyen ürünler içerinde görünmektedir. Lütfen ilgili kişiye durumu bildiriniz.")
                        .showCancelButton(false)
                        .show();
                return;
            }
            else if (tag.islem_durumu.equals("351") || urun_char12[11].equals("360"))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Ürün kirli torba değişimi bekleyen ürünler içerinde görünmektedir. Lütfen ilgili kişiye durumu bildiriniz.")
                        .showCancelButton(false)
                        .show();
                return;
            }
            else if (tag.islem_durumu.equals("352") || urun_char12[11].equals("370"))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Ürün geribesleme bekleyen ürünler içerinde görünmektedir. Lütfen ilgili kişiye durumu bildiriniz.")
                        .showCancelButton(false)
                        .show();
                return;
            }
            else if (tag.islem_durumu.equals("353") )
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Ürün palet düzenleme bekleyen ürünler içerinde görünmektedir. Lütfen ilgili kişiye durumu bildiriniz.")
                        .showCancelButton(false)
                        .show();
                return;
            }
            else if (tag.islem_durumu.equals("354") || urun_char12[11].equals("390"))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Ürün elleçleme bekleyen ürünler içerinde görünmektedir. Lütfen ilgili kişiye durumu bildiriniz.")
                        .showCancelButton(false)
                        .show();
                return;
            }
            else if (tag.islem_durumu.equals("0"))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Üretilmemiş Etiket")
                        .setContentTextSize(25)
                        .setContentText("ÜRETİM İŞLEMİ TAMAMLANMAMIŞ ÜRÜN ETİKETİ \r\n Etiketin üretim işlemi tamamlanmamıştır.")
                        .showCancelButton(false)
                        .show();
                return;
            }
            else if (tag.islem_durumu.equals("8"))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Yükleme İşlemi Tamamlanmış")
                        .setContentTextSize(25)
                        .setContentText("BAŞKA BİR ARACA YÜKLEME İŞLEMİ TAMAMLANMIŞ ÜRÜN ETİKETİ \r\n Etiketin YÜKLEME işlemi tamamlanmıştır.")
                        .showCancelButton(false)
                        .show();
                return;
            }
            else if (!aktif_sevk_isemri.urun_kodu.equals(tag.urun_kod))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("İŞLEM İÇİN UYGUN OLMAYAN ÜRÜN")
                        .setContentTextSize(25)
                        .setContentText("Yanlış ürün seçimi yapıldı. \r\n İşleme uygun olmayan ürün.")
                        .showCancelButton(false)
                        .show();
                return;
            }
            else if (tag.islem_durumu.equals("1") || tag.islem_durumu.equals("401"))
            {
                String tag_lot_array = tag.lotno.replace('.', ' ');
                tag_lot_array = tag_lot_array.trim();

                String[] sevk_char = aktif_sevk_isemri.karakteristikler.split(",",-1);
                String[] urun_char = tag.karakteristikler.split(",",-1);

                for (int i = 0; i < sevk_char.length; i++)
                {
                    if (!sevk_char[i].equals("") && (i != 4 && i != 5 && i != 1 && i != 6))
                    {
                        String degisken_adi = "";

                        if (i == 0)
                        {
                            degisken_adi = " 'ALTTAN BOŞALTMALI' ";
                        }
                        else if (i == 2)
                        {
                            degisken_adi = " 'BUFFLE' ";
                        }
                        else if (i == 3)
                        {
                            degisken_adi = " 'ÇİFT KULAKÇIK' ";
                        }

                        else if (i == 7)
                        {
                            degisken_adi = " 'TORBA BOYUTU' ";
                        }
                        else if (i == 8)
                        {
                            degisken_adi = " 'TORBALAMA TÜRÜ' ";
                        }
                        else if (i == 9)
                        {
                            degisken_adi = " 'SÜLFAT DURUMU' ";
                        }
                        else if (i == 10)
                        {
                            degisken_adi = " 'PARÇACIK BOYUTU' ";
                        }

                        if (!sevk_char[i].equals(urun_char[i]))
                        {
                            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("İŞLEM İÇİN UYGUN OLMAYAN ÜRÜN")
                                    .setContentTextSize(25)
                                    .setContentText("Yanlış karakteristeki ürün seçimi yapıldı." + degisken_adi + " değişken değeri uyuşmuyor. \r\n İşleme uygun olmayan ürün.")
                                    .showCancelButton(false)
                                    .show();
                            return;
                        }
                    }
                }

                if (!tag.palet_agirligi.equals(aktif_sevk_isemri.palet_agirligi))
                {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("İŞLEM İÇİN UYGUN OLMAYAN ÜRÜN")
                            .setContentTextSize(25)
                            .setContentText("Yanlış ağırlıkta ürün seçimi yapıldı.\r\n Ürün ağırlığı : " + tag.palet_agirligi + "\r\n Sipariş ağırlığı : " + aktif_sevk_isemri.palet_agirligi + "\r\nİşleme uygun olmayan ürün.")
                            .showCancelButton(false)
                            .show();
                    return;
                }

                else if (!kodVar)
                {

                    if (!aktif_sevk_isemri.lotno.equals("") && !aktif_sevk_isemri.lotno.contains(tag_lot_array))
                    {
                        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("YANLIŞ LOT SEÇİMİ")
                                .setContentText(aktif_sevk_isemri.lotno + " nolu lottan ürün seçimi yapılmalı. Ürün kriterleri yüklemek için uygun. Yüklemeye devam etmek istiyor musunuz ?")
                                .setContentTextSize(20)
                                .setConfirmText("EVET")
                                .setCancelText("HAYIR")
                                .showCancelButton(true)
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                        aktarim akt = new aktarim();
                                        akt.akt_aktarimdurumu = "0";
                                        akt.akt_aktarimtipi = "4";
                                        akt.akt_isemri_detay = aktif_sevk_isemri.isemri_detay_id;
                                        akt.akt_isletme = aktif_sevk_isemri.isletme;
                                        akt.akt_kod_isemri = aktif_sevk_isemri.isemri_id;
                                        akt.akt_kod_sap = aktif_sevk_isemri.kod_sap;
                                        akt.akt_kod_urun = aktif_sevk_isemri.urun_kodu;
                                        akt.akt_sevk_har_id = aktif_sevk_isemri.islem_id;
                                        akt.akt_urn_palet_rfid = tag.rfid;
                                        akt.akt_urn_palet_serino = tag.palet_kod;
                                        akt.akt_urn_rfid = tag.rfid;
                                        akt.akt_urn_serino = tag.palet_kod;
                                        akt.akt_user_id = _ayaraktifkullanici;

                                        request_sevkiyat_aktarim _Param1 = new request_sevkiyat_aktarim();
                                        _Param1.set_zsunucu_ip_adresi(_ayarsunucuip);
                                        _Param1.set_zaktif_alt_tesis(_ayaraktifalttesis);
                                        _Param1.set_zaktif_tesis(_ayaraktiftesis);
                                        _Param1.set_zsurum(_sbtVerisyon);
                                        _Param1.set_zkullaniciadi(_zkullaniciadi);
                                        _Param1.set_zsifre(_zsifre);
                                        _Param1.setAktif_sunucu(_ayaraktifsunucu);
                                        _Param1.setAktif_kullanici(_ayaraktifkullanici);

                                        _Param1.set_aktarim(akt);

                                        Genel.showProgressDialog(getContext());
                                        //todo persos_aktarim classı icinde ama burda persosa ekliyorum.
                                        Boolean islem_sonucu = persos.fn_ekle_aktarim(_Param1);
                                        Genel.dismissProgressDialog();

                                        if (!islem_sonucu)
                                        {
                                            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                                    .setTitleText("İşlem Başarısız")
                                                    .setContentTextSize(25)
                                                    .setContentText("Kayıt yapılamadı.\r\n Veritabanı hatası")
                                                    .showCancelButton(false)
                                                    .show();
                                        }
                                        urun_listesi.add(tag);
                                        updateListviewItem();
                                        return;
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
                    else {
                        aktarim akt = new aktarim();
                        akt.akt_aktarimdurumu = "0";
                        akt.akt_aktarimtipi = "4";
                        akt.akt_isemri_detay = aktif_sevk_isemri.isemri_detay_id;
                        akt.akt_isletme = aktif_sevk_isemri.isletme;
                        akt.akt_kod_isemri = aktif_sevk_isemri.isemri_id;
                        akt.akt_kod_sap = aktif_sevk_isemri.kod_sap;
                        akt.akt_kod_urun = aktif_sevk_isemri.urun_kodu;
                        akt.akt_sevk_har_id = aktif_sevk_isemri.islem_id;
                        akt.akt_urn_palet_rfid = tag.rfid;
                        akt.akt_urn_palet_serino = tag.palet_kod;
                        akt.akt_urn_rfid = tag.rfid;
                        akt.akt_urn_serino = tag.palet_kod;
                        akt.akt_user_id = _ayaraktifkullanici;

                        request_sevkiyat_aktarim _Param1 = new request_sevkiyat_aktarim();
                        _Param1.set_zsunucu_ip_adresi(_ayarsunucuip);
                        _Param1.set_zaktif_alt_tesis(_ayaraktifalttesis);
                        _Param1.set_zaktif_tesis(_ayaraktiftesis);
                        _Param1.set_zsurum(_sbtVerisyon);
                        _Param1.set_zkullaniciadi(_zkullaniciadi);
                        _Param1.set_zsifre(_zsifre);
                        _Param1.setAktif_sunucu(_ayaraktifsunucu);
                        _Param1.setAktif_kullanici(_ayaraktifkullanici);

                        _Param1.set_aktarim(akt);

                        Genel.showProgressDialog(getContext());
                        //todo persos_aktarim classı icinde ama burda persosa ekliyorum.
                        Boolean islem_sonucu = persos.fn_ekle_aktarim(_Param1);
                        Genel.dismissProgressDialog();

                        if (!islem_sonucu)
                        {
                            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("İşlem Başarısız")
                                    .setContentTextSize(25)
                                    .setContentText("Kayıt yapılamadı.\r\n Veritabanı hatası")
                                    .showCancelButton(false)
                                    .show();
                        }
                        urun_listesi.add(tag);
                        updateListviewItem();
                        return;
                    }

                }
            }
            else {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("İŞLEM İÇİN UYGUN OLMAYAN ÜRÜN")
                        .setContentTextSize(25)
                        .setContentText("Yanlış ürün seçimi yapıldı.\r\n İşleme uygun olmayan ürün.")
                        .showCancelButton(false)
                        .show();
                return;
            }
        } catch (Exception ex) {
            Genel.printStackTrace(ex,getContext());
            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("")
                    .setContentTextSize(25)
                    .setContentText("İlgili iş emri kaydı bulunamadı. \r\n " + ex.toString())
                    .showCancelButton(false)
                    .show();
        }
    }

    private class fn_imgBilgi implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            try
            {
                Genel.playButtonClikSound(getContext());

                String str = "";
                if (aktif_sevk_isemri.alici_isletme.equals(""))
                {
                    str += "ALICI : " + aktif_sevk_isemri.alici;
                    str += "\r\nBOOKING NO : " + aktif_sevk_isemri.bookingno;
                } else {
                    str += "\r\nALICI : " + aktif_sevk_isemri.alici_isletme;
                }
                str += "\r\nARAÇ PLAKASI : " + aktif_sevk_isemri.arac_plaka;
                if (!aktif_sevk_isemri.konteyner_turu.equals("")) {
                    str += "\r\nKONTEYNER  : " + aktif_sevk_isemri.kont_kodu;
                }
                str += "\r\nSAP KODU : " + aktif_sevk_isemri.kod_sap;
                str += "\r\nÜRÜN ADI : " + aktif_sevk_isemri.urun_adi;
                str += "\r\nTORBA AĞIRLIĞI : " + aktif_sevk_isemri.miktar_torba;
                str += "\r\nPALET AĞIRLIĞI : " + aktif_sevk_isemri.palet_agirligi;
                str += "\r\nY.ADET/ İŞEMRİ K.ADET : \r\n" + aktif_sevk_isemri.yapilan_adet + " / " + aktif_sevk_isemri.kalan_palet_sayisi;

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
                Genel.printStackTrace(ex,getContext());
            }
        }
    }

    private class fn_btnYenile implements View.OnClickListener {
        @Override
        public void onClick(View view) {

        }
    }

    private void fn_listview_longclick(){
        try {

            if (_Secili != null) {
                final Urun_sevkiyat tag = _Secili;
                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("SORU")
                        .setContentText("SERİ NO : " + tag.palet_kod + "\r\n Seri nolu ürün için 'İNDİRME' işlemi uygulanacak. Onaylıyor musunuz ?")
                        .setContentTextSize(20)
                        .setConfirmText("EVET")
                        .setCancelText("HAYIR")
                        .showCancelButton(true)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();

                                aktarim akt = new aktarim();
                                akt.akt_aktarimdurumu = "0";
                                akt.akt_aktarimtipi = "5";
                                akt.akt_isemri_detay = aktif_sevk_isemri.isemri_detay_id;
                                akt.akt_isletme = aktif_sevk_isemri.isletme;
                                akt.akt_kod_isemri = aktif_sevk_isemri.isemri_id;
                                akt.akt_kod_sap = aktif_sevk_isemri.kod_sap;
                                akt.akt_kod_urun = aktif_sevk_isemri.urun_kodu;
                                akt.akt_sevk_har_id = aktif_sevk_isemri.islem_id;
                                akt.akt_urn_palet_rfid = tag.rfid;
                                akt.akt_urn_palet_serino = tag.palet_kod;
                                akt.akt_urn_rfid = tag.rfid;
                                akt.akt_urn_serino = tag.palet_kod;
                                akt.akt_user_id = _ayaraktifkullanici;

                                request_sevkiyat_aktarim _Param1 = new request_sevkiyat_aktarim();
                                _Param1.set_zsunucu_ip_adresi(_ayarsunucuip);
                                _Param1.set_zaktif_alt_tesis(_ayaraktifalttesis);
                                _Param1.set_zaktif_tesis(_ayaraktiftesis);
                                _Param1.set_zsurum(_sbtVerisyon);
                                _Param1.set_zkullaniciadi(_zkullaniciadi);
                                _Param1.set_zsifre(_zsifre);
                                _Param1.setAktif_sunucu(_ayaraktifsunucu);
                                _Param1.setAktif_kullanici(_ayaraktifkullanici);

                                _Param1.set_aktarim(akt);

                                Genel.showProgressDialog(getContext());
                                //todo persos_aktarim classı icinde ama burda persosa ekliyorum.
                                Boolean islem_sonucu = persos.fn_ekle_aktarim(_Param1);
                                Genel.dismissProgressDialog();

                                if (islem_sonucu) {
                                    for(int i=0;i<urun_listesi.size();i++){
                                        if(urun_listesi.get(i).rfid.equals(tag.rfid)){
                                            urun_listesi.remove(i);
                                        }
                                    }
                                    updateListviewItem();
                                    new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("Onay")
                                            .setContentText("İndirme işlemi başarı ile tamamlandı.")
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
                                            .setTitleText("İşlem Başarısız")
                                            .setContentTextSize(25)
                                            .setContentText("İndirme işlemi başarısız. Kayıt yapılamadı. \r\n Veritabanı hatası")
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
            Genel.printStackTrace(ex,getContext());
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
