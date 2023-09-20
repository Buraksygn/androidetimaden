package com.etimaden.SevkiyatIslemleri;

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
import com.etimaden.SevkiyatIslemleri.Zayiat_islemleri.Zayi_depo_kabul.frg_zayi_isemri_indirme;
import com.etimaden.adapter.apmblSevkiyatAktifIsEmiriIndirme;
import com.etimaden.adapter.apmblSevkiyatZayiIsEmiriIndirme;
import com.etimaden.adapterclass.Urun_tag_data;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cResponseResult.Sevkiyat_isemri;
import com.etimaden.genel.Genel;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.Urun_tag;
import com.etimaden.persosclass.Zayi_urun;
import com.etimaden.request.request_secEtiket;
import com.etimaden.request.request_sevkiyat_isemri;
import com.etimaden.request.request_sevkiyat_isemri_uruntag_list_uruntag;
import com.etimaden.request.request_sevkiyat_zayi_zayiurun_list_zayiurun;
import com.etimaden.request.request_string;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class frg_aktif_isemri_indirme  extends Fragment {

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

    ArrayList<Urun_tag> urun_listesi_indirilen;
    ArrayList<Urun_tag> urun_listesi_yuklenen;
    ArrayList<Urun_tag_data> urun_listesi;

    Sevkiyat_isemri aktif_sevk_isemri = null;
    Urun_tag_data _Secili = null;

    private apmblSevkiyatAktifIsEmiriIndirme adapter;

    public static frg_aktif_isemri_indirme newInstance() {

        return new frg_aktif_isemri_indirme();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_aktif_isemri_indirme, container, false);
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
        baslik += "\r\nMAL KABUL LİSTESİ";
        _txtBaslik.setText(baslik);


        _btnTamam = (Button)getView().findViewById(R.id.btnTamam);
        _btnTamam.playSoundEffect(0);
        _btnTamam.setOnClickListener(new fn_btnTamam());

        _btngeri = (Button)getView().findViewById(R.id.btnGeri);
        _btngeri.playSoundEffect(0);
        _btngeri.setOnClickListener(new fn_Geri());

        _urun_list = (ListView) getView().findViewById(R.id.indirme_list);

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

        urun_listesi= new ArrayList<Urun_tag_data>();
        adapter=new apmblSevkiyatAktifIsEmiriIndirme(urun_listesi,getContext());

        request_sevkiyat_isemri _Param1= new request_sevkiyat_isemri();
        _Param1.set_zsunucu_ip_adresi(_ayarsunucuip);
        _Param1.set_zaktif_alt_tesis(_ayaraktifalttesis);
        _Param1.set_zaktif_tesis(_ayaraktiftesis);
        _Param1.set_zsurum(_sbtVerisyon);
        _Param1.set_zkullaniciadi(_zkullaniciadi);
        _Param1.set_zsifre(_zsifre);
        _Param1.setAktif_sunucu(_ayaraktifsunucu);
        _Param1.setAktif_kullanici(_ayaraktifkullanici);

        _Param1.set_sevkiyat_ismeri(aktif_sevk_isemri);

        Genel.showProgressDialog(getContext());
        List<Urun_tag> result = persos.fn_secYuklenenUrunListesi(_Param1);
        urun_listesi_indirilen=new ArrayList<>();
        if(result!=null) {
            urun_listesi_indirilen = new ArrayList<>(result);
        }
        Genel.dismissProgressDialog();
        if (urun_listesi_indirilen == null)
        {
            urun_listesi_indirilen = new ArrayList<Urun_tag>();
        }

        Genel.showProgressDialog(getContext());
        result = persos.fn_secYuklenenUrunListesi_nakil(_Param1);
        urun_listesi_yuklenen=new ArrayList<>();
        if(result!=null) {
            urun_listesi_yuklenen = new ArrayList<>(result);
        }
        Genel.dismissProgressDialog();

        if (urun_listesi_yuklenen == null)
        {
            urun_listesi_yuklenen = new ArrayList<Urun_tag>();
        }

        updateListviewItem();


    }

    private void updateListviewItem()
    {
        try
        {
            urun_listesi= new ArrayList<Urun_tag_data>();
            ArrayList<Urun_tag> ul_bekleyen = new ArrayList<Urun_tag>();
            ArrayList<Urun_tag> ul_indirilen = new ArrayList<Urun_tag>();
            int miktar = 0;
            boolean listedeYok=true;
            for (Urun_tag w : urun_listesi_yuklenen ) {
                listedeYok=true;
                for (Urun_tag a : urun_listesi_indirilen ) {
                    if(a.palet_kod.equals(w.palet_kod)){
                        listedeYok=false;
                    }
                }
                if(listedeYok==true){
                    ul_bekleyen.add(w);
                }
            }

            for (Urun_tag w : urun_listesi_indirilen ) {
                listedeYok=true;
                for (Urun_tag a : urun_listesi_yuklenen ) {
                    if(a.palet_kod.equals(w.palet_kod)){
                        listedeYok=false;
                    }
                }
                if(listedeYok==false){
                    ul_indirilen.add(w);
                }
            }
            for(Urun_tag urun : ul_bekleyen){
                urun_listesi.add(new Urun_tag_data(urun, Color.RED,R.drawable.redpoint,true));
            }
            for(Urun_tag urun : ul_indirilen){
                urun_listesi.add(new Urun_tag_data(urun, Color.GREEN,R.drawable.greenpoint,false));
                try{ miktar += Integer.parseInt(urun.palet_agirligi); }catch (Exception ex){ ex.printStackTrace(); }
            }

            _txtYuklemeMiktar.setText("İNDİRME MİKTARI = " + miktar);
            aktif_sevk_isemri.yapilan_miktar = miktar + "";
            aktif_sevk_isemri.yapilan_adet = "" + urun_listesi_indirilen.size();

            if (adapter != null) {
                adapter.clear();
                adapter.addAll(urun_listesi);
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

            if (urun.islem_durumu.equals("0"))
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

            if (urun.islem_durumu.equals("2"))
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

            if (urun.islem_durumu.equals("4"))
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

            if (urun.islem_durumu.equals("200"))
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
            if (urun.islem_durumu.equals("201"))
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

            if (urun.islem_durumu.equals("350"))
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

            if (urun.islem_durumu.equals("351"))
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


            if (urun.islem_durumu.equals("352"))
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

            if (urun.islem_durumu.equals("353"))
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

            if (urun.islem_durumu.equals("354"))
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

            if (tag == null || (!tag.islem_durumu.equals("1") && (!tag.islem_durumu.equals("3") && !tag.islem_durumu.equals("7"))))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("İŞLEM İÇİN UYGUN OLMAYAN ÜRÜN")
                        .setContentTextSize(25)
                        .setContentText("Ürün yapmak istediğiniz işlem için uygun değildir.\r\n İşleme uygun olmayan etiket.")
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

            if (urun.islem_durumu.equals("0"))
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

            if (urun.islem_durumu.equals("2"))
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

            if (urun.islem_durumu.equals("4"))
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

            if (urun.islem_durumu.equals("200"))
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
            if (urun.islem_durumu.equals("201"))
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

            if (urun.islem_durumu.equals("350"))
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

            if (urun.islem_durumu.equals("351"))
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


            if (urun.islem_durumu.equals("352"))
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

            if (urun.islem_durumu.equals("353"))
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

            if (urun.islem_durumu.equals("354"))
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

            if (tag == null || (!tag.islem_durumu.equals("1") && (!tag.islem_durumu.equals("3") && !tag.islem_durumu.equals("7"))))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("İŞLEM İÇİN UYGUN OLMAYAN ÜRÜN")
                        .setContentTextSize(25)
                        .setContentText("Ürün yapmak istediğiniz işlem için uygun değildir.\r\n İşleme uygun olmayan etiket.")
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
        try {
            boolean kodVarIndirilen = false;
            for (Urun_tag w : urun_listesi_indirilen) {
                if (w.palet_kod.equals(tag.palet_kod)) {
                    kodVarIndirilen = true;
                }
            }

            boolean kodVarYuklenen = false;
            for (Urun_tag w : urun_listesi_yuklenen) {
                if (w.palet_kod.equals(tag.palet_kod)) {
                    kodVarYuklenen = true;
                }
            }
            if (kodVarIndirilen == true) {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("UYARI")
                        .setContentTextSize(25)
                        .setContentText("BU ÜRÜN ZATEN İNDİRİLMİŞ OLARAK SİSTEMDE GÖRÜNMEKTEDİR.")
                        .showCancelButton(false)
                        .show();
                return;
            } else if (kodVarYuklenen == true) {

                request_sevkiyat_isemri_uruntag_list_uruntag _Param1 = new request_sevkiyat_isemri_uruntag_list_uruntag();
                _Param1.set_zsunucu_ip_adresi(_ayarsunucuip);
                _Param1.set_zaktif_alt_tesis(_ayaraktifalttesis);
                _Param1.set_zaktif_tesis(_ayaraktiftesis);
                _Param1.set_zsurum(_sbtVerisyon);
                _Param1.set_zkullaniciadi(_zkullaniciadi);
                _Param1.set_zsifre(_zsifre);
                _Param1.setAktif_sunucu(_ayaraktifsunucu);
                _Param1.setAktif_kullanici(_ayaraktifkullanici);

                _Param1.setSevk(aktif_sevk_isemri);
                _Param1.setDepo_urun(tag);
                _Param1.setUrun_listesi(urun_listesi_indirilen);

                Genel.showProgressDialog(getContext());
                Boolean islem_sonucu = persos.fn_ekleSevkiyatUrun_cikarma(_Param1);
                Genel.dismissProgressDialog();

                if (!islem_sonucu) {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("İşlem Başarısız")
                            .setContentTextSize(25)
                            .setContentText("Kayıt yapılamadı. \r\n Veritabanı hatası")
                            .showCancelButton(false)
                            .show();
                    return;
                }
            } else {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("UYARI")
                        .setContentTextSize(25)
                        .setContentText("BU ÜRÜN BU ARAÇ İÇERİSİNDE GÖRÜNMEMEKTEDİR.")
                        .showCancelButton(false)
                        .show();
                return;
            }

            //urun_listesi_indirilen = Program.persos.secYuklenenUrunListesi(aktif_sevk_isemri);

            //urun_listesi_indirilen.Add(urun_listesi_yuklenen.Where(w => w.palet_kod.Equals(tag.palet_kod)).ElementAt(0));

            updateListviewItem();
        } catch (Exception ex) {
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
            try
            {
                request_sevkiyat_isemri _Param1= new request_sevkiyat_isemri();
                _Param1.set_zsunucu_ip_adresi(_ayarsunucuip);
                _Param1.set_zaktif_alt_tesis(_ayaraktifalttesis);
                _Param1.set_zaktif_tesis(_ayaraktiftesis);
                _Param1.set_zsurum(_sbtVerisyon);
                _Param1.set_zkullaniciadi(_zkullaniciadi);
                _Param1.set_zsifre(_zsifre);
                _Param1.setAktif_sunucu(_ayaraktifsunucu);
                _Param1.setAktif_kullanici(_ayaraktifkullanici);

                _Param1.set_sevkiyat_ismeri(aktif_sevk_isemri);

                Genel.showProgressDialog(getContext());
                List<Urun_tag> result = persos.fn_secYuklenenUrunListesi(_Param1);
                urun_listesi_indirilen=new ArrayList<>();
                if(result!=null) {
                    urun_listesi_indirilen = new ArrayList<>(result);
                }
                Genel.dismissProgressDialog();
                if (urun_listesi_indirilen == null)
                {
                    urun_listesi_indirilen = new ArrayList<Urun_tag>();
                }

                ArrayList<Urun_tag> ul_bekleyen = new ArrayList<Urun_tag>();
                boolean listedeYok=true;
                for (Urun_tag w : urun_listesi_yuklenen ) {
                    listedeYok=true;
                    for (Urun_tag a : urun_listesi_indirilen ) {
                        if(a.palet_kod.equals(w.palet_kod)){
                            listedeYok=false;
                        }
                    }
                    if(listedeYok==true){
                        ul_bekleyen.add(w);
                    }
                }

                for (int i = 0; i < ul_bekleyen.size(); i++)
                {
                    request_sevkiyat_isemri_uruntag_list_uruntag _Param2 = new request_sevkiyat_isemri_uruntag_list_uruntag();
                    _Param2.set_zsunucu_ip_adresi(_ayarsunucuip);
                    _Param2.set_zaktif_alt_tesis(_ayaraktifalttesis);
                    _Param2.set_zaktif_tesis(_ayaraktiftesis);
                    _Param2.set_zsurum(_sbtVerisyon);
                    _Param2.set_zkullaniciadi(_zkullaniciadi);
                    _Param2.set_zsifre(_zsifre);
                    _Param2.setAktif_sunucu(_ayaraktifsunucu);
                    _Param2.setAktif_kullanici(_ayaraktifkullanici);

                    _Param2.setSevk(aktif_sevk_isemri);
                    _Param2.setDepo_urun(ul_bekleyen.get(i));
                    _Param2.setUrun_listesi(urun_listesi_indirilen);

                    Genel.showProgressDialog(getContext());
                    Boolean islem_sonucu = persos.fn_ekleSevkiyatUrun_cikarma(_Param2);
                    Genel.dismissProgressDialog();

                    if (!islem_sonucu)
                    {
                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("İşlem Başarısız")
                                .setContentTextSize(25)
                                .setContentText("Kayıt yapılamadı. \r\n Veritabanı hatası")
                                .showCancelButton(false)
                                .show();

                    }
                }

                Genel.showProgressDialog(getContext());
                result = persos.fn_secYuklenenUrunListesi(_Param1);
                urun_listesi_indirilen=new ArrayList<>();
                if(result!=null) {
                    urun_listesi_indirilen = new ArrayList<>(result);
                }
                Genel.dismissProgressDialog();
                if (urun_listesi_indirilen == null)
                {
                    urun_listesi_indirilen = new ArrayList<Urun_tag>();
                }

                updateListviewItem();

            }
            catch (Exception ex)
            {
                Genel.printStackTrace(ex,getContext());
            }
        }
    }

    private void fn_listview_longclick(){
        try {

            if (_Secili != null && _Secili.getBekleyen() == true) {
                final Urun_tag tag = _Secili.getUrun_tag();
                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("SORU")
                        .setContentText("SERİ NO : " + tag.palet_kod + "\r\n Seri nolu ürün için kayıt değişimi yapılacak. Onaylıyor musunuz ?")
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

                                _Param1.set_value(tag.palet_kod);

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
