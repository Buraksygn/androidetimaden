package com.etimaden.SayimIslemleri.Demirbas_sayim_islemi.Sayim_islemleri;

import static com.etimaden.cSabitDegerler._ipAdresi3G;
import static com.etimaden.cSabitDegerler._sbtVerisyon;
import static com.etimaden.cSabitDegerler._zkullaniciadi;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;

import android.bluetooth.le.ScanSettings;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.etimaden.GirisSayfasi;
import com.etimaden.SayimIslemleri.Demirbas_sayim_islemi.frg_demirbas_sayim_menu_panel;
import com.etimaden.SayimIslemleri.frg_sayim_menu_panel;
import com.etimaden.adapter.apmblDemirbasSayimDsSayimIslemi;
import com.etimaden.adapter.apmblSayimIslemleriDepoSayimIslemi;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.genel.Genel;
import com.etimaden.genel.SweetAlertDialogG;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.Demirbas_Konum;
import com.etimaden.persosclass.Duran_Varlik_sap;
import com.etimaden.persosclass.IFTag;
import com.etimaden.persosclass.Zimmet_sonuc_sap;
import com.etimaden.persosclass.aktarim;
import com.etimaden.persosclass.demirbas_sayim;
import com.etimaden.persosclass.malzeme_sayim_isemri;
import com.etimaden.request.request_aktarim_list;
import com.etimaden.request.request_demirbas_sayim_list;
import com.etimaden.request.request_demirbas_sayim_string;
import com.etimaden.request.request_sevkiyat_aktarim;
import com.etimaden.request.request_string;
import com.etimaden.request.request_string_aktif_isletme_esleme;
import com.etimaden.ugr_demo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;


public class frg_ds_sayim_islemi extends Fragment {

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
    private int DEFAULT_GUC = 100;
    private int MIN_GUC_GERCEK = 0;
    private int maxGuc = 11;
    private int maxGucGercek = 111;

    TextView _txtBaslik;
    ImageView _imgBilgi;
    Button _btnListeyiKaydet;
    ListView _aktif_is_emirleri_list;
    SeekBar _sbPower;
    TextView _txtPower;
    ImageView _imgHepsi;
    ImageView _imgBulunan;
    ImageView _imgEksik;
    ImageView _imgKullanimDisi;
    TextView _txtHepsi;
    TextView _txtBulunan;
    TextView _txtEksik;
    TextView _txtKullanimDisi;
    TextView _txtYuklemeMiktari;
    Button _btnGeri;

    boolean isReadable = true;
    ArrayList<demirbas_sayim> sayilacak_liste;
    ArrayList<demirbas_sayim> kayıt_yapılacak_urun;
    Demirbas_Konum konum_sayim_isemri = null;
    int filtre_durum = 0;
    int longClickedItemIndex;

    private apmblDemirbasSayimDsSayimIslemi adapter;

    public frg_ds_sayim_islemi() {
        // Required empty public constructor
    }

    public static frg_ds_sayim_islemi newInstance()
    {
        return new frg_ds_sayim_islemi();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.frg_ds_sayim_islemi, container, false);
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

    public void fn_senddata(Demirbas_Konum secilen_sevk) {
        this.konum_sayim_isemri=secilen_sevk;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        new VeriTabani(getContext()).fn_EpcTemizle();

        _myIslem = new VeriTabani(getContext());

        ((GirisSayfasi) getActivity()).fn_ModBoth();
        ((GirisSayfasi) getActivity()).fn_ListeTemizle();
        ((GirisSayfasi) getActivity()).fn_GucAyarla(DEFAULT_GUC);

        _txtBaslik = (TextView) getView().findViewById(R.id.txtBaslik);
        _txtBaslik.setText("SAYIM LİSTESİ");

        _imgBilgi = (ImageView)getView().findViewById(R.id.imgBilgi);
        _imgBilgi.playSoundEffect(0);
        _imgBilgi.setOnClickListener(new fn_imgBilgi());

        _btnListeyiKaydet = (Button)getView().findViewById(R.id.btnListeyiKaydet);
        _btnListeyiKaydet.playSoundEffect(0);
        _btnListeyiKaydet.setOnClickListener(new fn_btnListeyiKaydet());

        _btnGeri = (Button)getView().findViewById(R.id.btnGeri);
        _btnGeri.playSoundEffect(0);
        _btnGeri.setOnClickListener(new fn_btnGeri());

        _aktif_is_emirleri_list = (ListView) getView().findViewById(R.id.aktif_is_emirleri_list);
        adapter=new apmblDemirbasSayimDsSayimIslemi(new ArrayList<demirbas_sayim>(),getContext());
        _aktif_is_emirleri_list.setAdapter(adapter);
        _aktif_is_emirleri_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                longClickedItemIndex=position;
                return false;
            }
        });
        registerForContextMenu(_aktif_is_emirleri_list);

        _sbPower = (SeekBar)getView().findViewById(R.id.sbPower);
        _sbPower.playSoundEffect(0);
        _sbPower.setMin(0);
        _sbPower.setMax(11);
        _sbPower.setProgress(DEFAULT_GUC);
        _sbPower.setEnabled(true);
        _sbPower.setOnSeekBarChangeListener(new fn_sbPower());

        _txtPower = (TextView) getView().findViewById(R.id.txtPower);
        _txtPower.setText("105 / ... DB");

        _imgHepsi = (ImageView)getView().findViewById(R.id.imgHepsi);
        _imgHepsi.playSoundEffect(0);
        _imgHepsi.setOnClickListener(new fn_imgHepsi());
        _txtHepsi = (TextView) getView().findViewById(R.id.txtHepsi);
        _txtHepsi.setTypeface(null,Typeface.BOLD);
        filtre_durum = 0;

        _imgBulunan = (ImageView)getView().findViewById(R.id.imgBulunan);
        _imgBulunan.playSoundEffect(0);
        _imgBulunan.setOnClickListener(new fn_imgBulunan());
        _txtBulunan = (TextView) getView().findViewById(R.id.txtBulunan);

        _imgEksik = (ImageView)getView().findViewById(R.id.imgEksik);
        _imgEksik.playSoundEffect(0);
        _imgEksik.setOnClickListener(new fn_imgEksik());
        _txtEksik = (TextView) getView().findViewById(R.id.txtEksik);

        _imgKullanimDisi = (ImageView)getView().findViewById(R.id.imgKullanimDisi);
        _imgKullanimDisi.playSoundEffect(0);
        _imgKullanimDisi.setOnClickListener(new fn_imgKullanimDisi());
        _txtKullanimDisi = (TextView) getView().findViewById(R.id.txtKullanimDisi);

        _txtYuklemeMiktari = (TextView) getView().findViewById(R.id.txtYuklemeMiktari);

        fn_AyarlariYukle();
        loadMaxGucFromService();
        kayıt_yapılacak_urun=new ArrayList<>();

        if (konum_sayim_isemri.getSayim_kod().equals(""))
        {
            new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                    .setTitleText("HATA")
                    .setContentText("Sayım dosyası alınamadı. Lütfen bir daha deneyiniz. BAĞLANTI HATASI.")
                    .setContentTextSize(20)
                    .setConfirmText("TAMAM")
                    .showCancelButton(false)
                    .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialogG sDialog) {
                            sDialog.dismissWithAnimation();

                            frg_demirbas_sayim_menu_panel fragmentyeni = new frg_demirbas_sayim_menu_panel();
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_demirbas_sayim_menu_panel").addToBackStack(null);
                            fragmentTransaction.commit();

                            return;
                        }
                    }).show();

        }
        try {
            sayilacak_liste = _myIslem.fn_sec_ds_detay(this.konum_sayim_isemri);

            Iterator<demirbas_sayim> itr = sayilacak_liste.iterator();
            while (itr.hasNext()) {
                demirbas_sayim ds = itr.next();
                if (ds.getDs_durum().equals("31")) {
                    itr.remove();
                }
            }

            if (sayilacak_liste.size() == 0) {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("UYARI")
                        .setContentText("Demirbaş listesinde hiç bir kayıt bulunmamaktadır.")
                        .setContentTextSize(20)
                        .setConfirmText("TAMAM")
                        .showCancelButton(false)
                        .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialogG sDialog) {
                                sDialog.dismissWithAnimation();

                                frg_demirbas_sayim_menu_panel fragmentyeni = new frg_demirbas_sayim_menu_panel();
                                FragmentManager fragmentManager = getFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_demirbas_sayim_menu_panel").addToBackStack(null);
                                fragmentTransaction.commit();

                                return;
                            }
                        }).show();

            }

            updateListviewItem();
        }catch (Exception e){
            Genel.printStackTrace(e,getContext());
            new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                    .setTitleText("HATA")
                    .setContentText("Sayım dosyası alınamıyor. Lütfen sorunu ilgili kişiye bildiriniz.")
                    .setContentTextSize(20)
                    .setConfirmText("TAMAM")
                    .showCancelButton(false)
                    .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialogG sDialog) {
                            sDialog.dismissWithAnimation();

                            frg_demirbas_sayim_menu_panel fragmentyeni = new frg_demirbas_sayim_menu_panel();
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_demirbas_sayim_menu_panel").addToBackStack(null);
                            fragmentTransaction.commit();

                            return;
                        }
                    }).show();
        }


    }

    private void loadMaxGucFromService() {
        try {
            JSONObject parametre = new JSONObject();
            parametre.put("_zsunucu_ip_adresi", _ayarsunucuip);
            parametre.put("_zkullaniciadi", _zkullaniciadi);
            parametre.put("_zsifre", _zsifre);

            String fullUrl = _OnlineUrl + "api/GetRfidGucAyari";
            RequestQueue queue = Volley.newRequestQueue(getContext());

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    fullUrl,
                    parametre,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                maxGucGercek = response.getInt("_zMaxGuc");

                                maxGuc = maxGucGercek - MIN_GUC_GERCEK;

                                if(maxGuc < 0) {
                                    maxGuc = 11;
                                    maxGucGercek = 111;
                                }


                                if(getActivity() != null) {
                                    final int finalMaxGuc = maxGuc;
                                    final int finalMaxGucGercek = maxGucGercek;

                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            _sbPower.setMax(finalMaxGuc);

                                            int baslangicDegeri = DEFAULT_GUC;
                                            if(baslangicDegeri > finalMaxGuc) {
                                                baslangicDegeri = finalMaxGuc;
                                            }
                                            _sbPower.setProgress(baslangicDegeri);

                                            int currentGucGercek = MIN_GUC_GERCEK + baslangicDegeri;
                                            ((GirisSayfasi) getActivity()).fn_GucAyarla(currentGucGercek);

                                            _txtPower.setText(currentGucGercek + " / " + finalMaxGucGercek + " DB");

                                            android.util.Log.d("RFID_POWER", "API: Max=" + finalMaxGucGercek + " (UI: 0-" + finalMaxGuc + ")");
                                        }
                                    });
                                }
                            } catch (JSONException e) {
                                android.util.Log.e("RFID_POWER", "Parse hatası: " + e.getMessage());
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            android.util.Log.e("RFID_POWER", "Bağlantı hatası");

                            if(getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        _txtPower.setText("105 / 111 DB (Varsayılan)");
                                    }
                                });
                            }
                        }
                    }
            );

            queue.add(request);

        } catch (Exception e) {
            android.util.Log.e("RFID_POWER", "Hata: " + e.getMessage());
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if(getActivity() != null) {
            int defaultGercek = MIN_GUC_GERCEK + DEFAULT_GUC;
            ((GirisSayfasi) getActivity()).fn_GucAyarla(defaultGercek);
        }
    }

    public void barkodOkundu(String barkod){
        try {
            barkod = barkod.substring(barkod.length() - 24);

            if(barkod.startsWith("7377675")) {
                etiketDegerlendir(barkod);
            } else {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Etiket formatı hatalı!")
                        .showCancelButton(false)
                        .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialogG sDialog) {
                                sDialog.dismissWithAnimation();
                                isReadable = true;
                            }
                        })
                        .show();
            }
        }
        catch (Exception ex){
            Genel.printStackTrace(ex,getContext());
            new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                    .setTitleText("HATA")
                    .setContentTextSize(25)
                    .setContentText("Lütfen uygun bir ürün etiketi okutunuz.")
                    .showCancelButton(false)
                    .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialogG sDialog) {
                            sDialog.dismissWithAnimation();
                            isReadable = true;
                        }
                    })
                    .show();
        }
    }

    public void rfidOkundu(String rfid){
        try {
            if(rfid.startsWith("7377675")) {
                etiketDegerlendir(rfid);
            } else {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Etiket formatı hatalı!")
                        .showCancelButton(false)
                        .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialogG sDialog) {
                                sDialog.dismissWithAnimation();
                                isReadable = true;
                            }
                        })
                        .show();
            }
        }
        catch (Exception ex){
            Genel.printStackTrace(ex,getContext());
            new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                    .setTitleText("HATA")
                    .setContentTextSize(25)
                    .setContentText("Lütfen uygun bir ürün etiketi okutunuz.")
                    .showCancelButton(false)
                    .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialogG sDialog) {
                            sDialog.dismissWithAnimation();
                            isReadable = true;
                        }
                    })
                    .show();
        }
    }

    private void etiketDegerlendir(final String etiket)
    {
        int index=-1;
        for(demirbas_sayim ds : sayilacak_liste){
            if( etiket.contains(ds.getDs_demirbas_kod())){
                index=sayilacak_liste.indexOf(ds);
                break;
            }
        }

        if(index!=-1){
            if(sayilacak_liste.get(index).getDs_durum().equals("0")){
                sayilacak_liste.get(index).setDs_durum("1");

                _myIslem.fn_guncelle_ds(sayilacak_liste.get(index));

                kayıt_yapılacak_urun.add(sayilacak_liste.get(index));
            }
        }else{
            demirbas_sayim sayim_item = new demirbas_sayim();
            sayim_item.setDs_durum("2");
            sayim_item.setDs_aktarim("1");
            sayim_item.setDs_bina_adi(konum_sayim_isemri.getBina_adi());
            sayim_item.setDs_bina_kod(konum_sayim_isemri.getBina_kod());
            sayim_item.setDs_demirbas_kod(etiket.substring(etiket.length()-12));
            sayim_item.setDs_eski_yeni("1");
            sayim_item.setDs_isletme_adi(konum_sayim_isemri.getIsletme_adi());
            sayim_item.setDs_isletme_kod(konum_sayim_isemri.getIsletme_kod());
            sayim_item.setDs_kat_adi(konum_sayim_isemri.getKat_adi());
            sayim_item.setDs_kat_kod(konum_sayim_isemri.getKat_kod());
            sayim_item.setDs_oda_adi(konum_sayim_isemri.getOda_adi());
            sayim_item.setDs_oda_kod(konum_sayim_isemri.getOda_kod());
            sayim_item.setDs_rfid(etiket);
            sayim_item.setDs_sap_kod(sayilacak_liste.get(0).getDs_sap_kod());
            sayim_item.setDs_sayim_id(sayilacak_liste.get(0).getDs_sayim_id());
            sayim_item.setDs_sayim_kod(sayilacak_liste.get(0).getDs_sayim_kod());

            sayilacak_liste.add(sayim_item);

            _myIslem.fn_ekle_ds(sayim_item);

            kayıt_yapılacak_urun.add(sayim_item);
        }
        updateListviewItem();
    }
    private void updateListviewItem() {
        try {
            ArrayList<demirbas_sayim> sayilacak_liste_filtreli = new ArrayList<>();
            int sayilan_demirbas_sayisi = 0;
            int eksik_demirbas_sayisi = 0;
            int kullanim_disi_sayisi = 0;
            int toplam_sayisi = 0;

            for (demirbas_sayim ds : sayilacak_liste) {
                if (ds.getDs_durum().equals("1")) {
                    sayilan_demirbas_sayisi++;
                    if (filtre_durum == 1) {
                        sayilacak_liste_filtreli.add(ds);
                    }
                } else if (ds.getDs_durum().equals("0")) {
                    eksik_demirbas_sayisi++;
                    if (filtre_durum == 2) {
                        sayilacak_liste_filtreli.add(ds);
                    }
                } else if (ds.getDs_durum().equals("2")) {
                    kullanim_disi_sayisi++;
                    if (filtre_durum == 3) {
                        sayilacak_liste_filtreli.add(ds);
                    }
                }
            }

            toplam_sayisi = sayilacak_liste.size();

            if (filtre_durum == 0) {
                sayilacak_liste_filtreli = sayilacak_liste;
            }

            if (adapter != null) {
                adapter.clear();
                adapter.addAll(sayilacak_liste_filtreli);
                adapter.notifyDataSetChanged();
            }

            String bilgi_metni = "";
            switch (filtre_durum) {
                case 0:
                    bilgi_metni = "TOPLAM ADET = " + toplam_sayisi;
                    break;
                case 1:
                    bilgi_metni = "SAYILAN ADET = " + sayilan_demirbas_sayisi;
                    break;
                case 2:
                    bilgi_metni = "EKSİK ADET = " + eksik_demirbas_sayisi;
                    break;
                case 3:
                    bilgi_metni = "KULLANIM DIŞI ADET = " + kullanim_disi_sayisi;
                    break;
            }

            _txtYuklemeMiktari.setText(bilgi_metni);
        } catch (Exception ex) {
            Genel.printStackTrace(ex, getContext());
        }
    }

    private void databaseKayit(ArrayList<demirbas_sayim> liste)
    {
        for(demirbas_sayim sayim : liste){
            if (!sayim.getDs_id().isEmpty() && !sayim.getDs_durum().equals("31"))
            {
                demirbas_sayim ds = new demirbas_sayim();
                ds.setDs_id(sayim.getDs_id());
                ds.setDs_durum(sayim.getDs_durum());
                ds.setDs_aktarim("0");

                _myIslem.fn_guncelle_ds(ds);
            }
            else
            {
                sayim.setDs_aktarim("0");
                _myIslem.fn_ekle_ds(sayim);
            }
        }
    }

    private class fn_btnListeyiKaydet implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Genel.lockButtonClick(view,getActivity());
            try
            {
                ArrayList<demirbas_sayim> guncelle_demirbas_sayim_detay_listesi = new ArrayList<>();
                ArrayList<demirbas_sayim> iptal_demirbas_sayim_detay_listesi = new ArrayList<>();
                ArrayList<demirbas_sayim> kaydet_demirbas_sayim_detay_listesi = new ArrayList<>();
                for (demirbas_sayim sayim : kayıt_yapılacak_urun){
                    if (sayim.getDs_eski_yeni().equals("0"))
                    {
                        guncelle_demirbas_sayim_detay_listesi.add(sayim);
                    }
                    else if (sayim.getDs_eski_yeni().equals("1") && sayim.getDs_durum().equals("31"))
                    {
                        iptal_demirbas_sayim_detay_listesi.add(sayim);
                    }
                    else
                    {
                        kaydet_demirbas_sayim_detay_listesi.add(sayim);
                    }
                }
                request_demirbas_sayim_list _Param1= new request_demirbas_sayim_list();
                _Param1.set_zsunucu_ip_adresi(_ayarsunucuip);
                _Param1.set_zaktif_alt_tesis(_ayaraktifalttesis);
                _Param1.set_zaktif_tesis(_ayaraktiftesis);
                _Param1.set_zsurum(_sbtVerisyon);
                _Param1.set_zkullaniciadi(_zkullaniciadi);
                _Param1.set_zsifre(_zsifre);
                _Param1.setAktif_sunucu(_ayaraktifsunucu);
                _Param1.setAktif_kullanici(_ayaraktifkullanici);

                Boolean result=false;
                if(guncelle_demirbas_sayim_detay_listesi.size()>0) {
                    _Param1.setDemirbasSayimListesi(guncelle_demirbas_sayim_detay_listesi);

                    Genel.showProgressDialog(getContext());
                    result = persos.fn_guncelle_demirbas_sayim_detay_listesi(_Param1);
                    Genel.dismissProgressDialog();

                    if (result) {
                        databaseKayit(guncelle_demirbas_sayim_detay_listesi);
                        kayıt_yapılacak_urun = new ArrayList<>();
                        kayıt_yapılacak_urun.addAll(iptal_demirbas_sayim_detay_listesi);
                        kayıt_yapılacak_urun.addAll(kaydet_demirbas_sayim_detay_listesi);
                    }
                }

                if(iptal_demirbas_sayim_detay_listesi.size()>0) {
                    _Param1.setDemirbasSayimListesi(iptal_demirbas_sayim_detay_listesi);

                    Genel.showProgressDialog(getContext());
                    result = persos.fn_iptal_demirbas_sayim_detay_listesi(_Param1);
                    Genel.dismissProgressDialog();

                    if (result) {
                        databaseKayit(iptal_demirbas_sayim_detay_listesi);
                        kayıt_yapılacak_urun = new ArrayList<>();
                        kayıt_yapılacak_urun.addAll(kaydet_demirbas_sayim_detay_listesi);
                    }
                }

                if(kaydet_demirbas_sayim_detay_listesi.size()>0) {
                    _Param1.setDemirbasSayimListesi(kaydet_demirbas_sayim_detay_listesi);

                    Genel.showProgressDialog(getContext());
                    result = persos.fn_kaydet_demirbas_sayim_detay_listesi(_Param1);
                    Genel.dismissProgressDialog();

                    if (result) {
                        databaseKayit(kaydet_demirbas_sayim_detay_listesi);
                        kayıt_yapılacak_urun = new ArrayList<>();
                    }
                }

                new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                        .setTitleText("SAYIM TAMAMLAMA")
                        .setContentText("Sayım işlemini tamamlamak ve sonuçları SAP tarafına aktarmak istiyor musunuz ? ")
                        .setContentTextSize(20)
                        .setConfirmText("EVET")
                        .setCancelText("HAYIR")
                        .showCancelButton(true)
                        .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialogG sDialog) {
                                sDialog.dismissWithAnimation();

                                aktarim akt = new aktarim();
                                akt.akt_aktarimtipi = "20";
                                akt.akt_sevk_har_id = konum_sayim_isemri.getSayim_id();
                                akt.akt_kod_sap = konum_sayim_isemri.getSayim_kod_sap();
                                akt.akt_kod_isemri = konum_sayim_isemri.getSayim_kod();

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
                                Boolean islem_sonucu = persos.fn_ekle_aktarim(_Param1);
                                Genel.dismissProgressDialog();

                                if (islem_sonucu == false)
                                {
                                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                                            .setTitleText("İşlem Başarısız")
                                            .setContentTextSize(25)
                                            .setContentText("Kayıt yapılamadı. \r\n Veritabanı hatası")
                                            .showCancelButton(false)
                                            .show();
                                }
                                else
                                {
                                    _myIslem.fn_sil_demirbas_sayim(konum_sayim_isemri);
                                    frg_demirbas_sayim_menu_panel fragmentyeni = new frg_demirbas_sayim_menu_panel();
                                    FragmentManager fragmentManager = getFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_demirbas_sayim_menu_panel").addToBackStack(null);
                                    fragmentTransaction.commit();
                                }

                                return;
                            }
                        })
                        .setCancelClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialogG sDialog) {
                                sDialog.dismissWithAnimation();

                                frg_demirbas_sayim_menu_panel fragmentyeni = new frg_demirbas_sayim_menu_panel();
                                FragmentManager fragmentManager = getFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_demirbas_sayim_menu_panel").addToBackStack(null);
                                fragmentTransaction.commit();

                                return;
                            }
                        })
                        .show();

            } catch (Exception ex) {
                ex.printStackTrace();
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("UYARI")
                        .setContentTextSize(25)
                        .setContentText("Kaydetme işlemi sırasında hata oluştu.")
                        .showCancelButton(false)
                        .show();
            }

        }
    }

    private class fn_imgBilgi implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Genel.lockButtonClick(view,getActivity());
            try
            {
                Genel.playButtonClikSound(getContext());
                String str = "";
                str += " BİNA : " + konum_sayim_isemri.getBina_adi() + "-" + konum_sayim_isemri.getBina_kod();
                str += "\r\n KAT : " + konum_sayim_isemri.getKat_adi() + "-" + konum_sayim_isemri.getKat_kod();
                str += "\r\n ODA : " + konum_sayim_isemri.getOda_adi() + "-" + konum_sayim_isemri.getOda_kod();
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.NORMAL_TYPE)
                        .setTitleText("SAYIM DETAYI")
                        .setContentText(str)
                        .setContentTextSize(20)
                        .setConfirmText("TAMAM")
                        .showCancelButton(false)
                        .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialogG sDialog) {
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

    private class fn_sbPower implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                int gercekGuc = MIN_GUC_GERCEK + progress;

                ((GirisSayfasi) getActivity()).fn_GucAyarla(gercekGuc);

                _txtPower.setText(gercekGuc + " / " + maxGucGercek + " DB");

                android.util.Log.d("SEEKBAR", "UI: " + progress + " → Cihaz: " + gercekGuc + " DB");
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            android.util.Log.d("SEEKBAR", "Başladı - Aralık: 0-" + maxGuc + " (Gerçek: " + MIN_GUC_GERCEK + "-" + maxGucGercek + ")");
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            int finalGucUI = seekBar.getProgress();
            int finalGucGercek = MIN_GUC_GERCEK + finalGucUI;
            ((GirisSayfasi) getActivity()).fn_GucAyarla(finalGucGercek);
            android.util.Log.d("SEEKBAR", "Bırakıldı - Son değer: " + finalGucGercek + " DB");
        }
    }
    private class fn_imgHepsi implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(filtre_durum !=0) {
                _txtHepsi.setTypeface(null, Typeface.BOLD);
                _txtBulunan.setTypeface(null, Typeface.NORMAL);
                _txtEksik.setTypeface(null, Typeface.NORMAL);
                _txtKullanimDisi.setTypeface(null, Typeface.NORMAL);
                filtre_durum = 0;
                updateListviewItem();
            }
        }
    }

    private class fn_imgBulunan implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(filtre_durum !=1) {
                _txtHepsi.setTypeface(null, Typeface.NORMAL);
                _txtBulunan.setTypeface(null, Typeface.BOLD);
                _txtEksik.setTypeface(null, Typeface.NORMAL);
                _txtKullanimDisi.setTypeface(null, Typeface.NORMAL);
                filtre_durum = 1;
                updateListviewItem();
            }
        }
    }

    private class fn_imgEksik implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(filtre_durum !=2) {
                _txtHepsi.setTypeface(null, Typeface.NORMAL);
                _txtBulunan.setTypeface(null, Typeface.NORMAL);
                _txtEksik.setTypeface(null, Typeface.BOLD);
                _txtKullanimDisi.setTypeface(null, Typeface.NORMAL);
                filtre_durum = 2;
                updateListviewItem();
            }
        }
    }

    private class fn_imgKullanimDisi implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(filtre_durum !=3) {
                _txtHepsi.setTypeface(null, Typeface.NORMAL);
                _txtBulunan.setTypeface(null, Typeface.NORMAL);
                _txtEksik.setTypeface(null, Typeface.NORMAL);
                _txtKullanimDisi.setTypeface(null, Typeface.BOLD);
                filtre_durum = 3;
                updateListviewItem();
            }
        }
    }

    demirbas_sayim zimmet_ds=new demirbas_sayim();
    demirbas_sayim zimmet_muadil=new demirbas_sayim();
    private void zimmetDegistir(String op) {
        zimmet_ds.setDs_masraf_yeri(zimmet_muadil.getDs_masraf_yeri());
        zimmet_ds.setDs_bina_kod(zimmet_muadil.getDs_bina_kod());
        zimmet_ds.setDs_kat_kod(zimmet_muadil.getDs_kat_kod());
        zimmet_ds.setDs_oda_kod(zimmet_muadil.getDs_oda_kod());

        request_demirbas_sayim_string _Param1 = new request_demirbas_sayim_string();
        _Param1.set_zsunucu_ip_adresi(_ayarsunucuip);
        _Param1.set_zaktif_alt_tesis(_ayaraktifalttesis);
        _Param1.set_zaktif_tesis(_ayaraktiftesis);
        _Param1.set_zsurum(_sbtVerisyon);
        _Param1.set_zkullaniciadi(_zkullaniciadi);
        _Param1.set_zsifre(_zsifre);
        _Param1.setAktif_sunucu(_ayaraktifsunucu);
        _Param1.setAktif_kullanici(_ayaraktifkullanici);

        _Param1.setDemirbasSayim(zimmet_ds);
        _Param1.setValue(op);

        Genel.showProgressDialog(getContext());
        Zimmet_sonuc_sap zr = persos.fn_sorgula_zimmet(_Param1);
        Genel.dismissProgressDialog();

        if (zr == null) {
            new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                    .setTitleText("HATA")
                    .setContentTextSize(25)
                    .setContentText("Bağlantı Hatası..... İşlem yapılamadı.")
                    .showCancelButton(false)
                    .show();
        } else if (zr.sonuc.equals("0")) {
            String msg="MASRAF YERİ";
            if(op.equals("1")){
                msg="KONUM";
            }
            new SweetAlertDialogG(getContext(), SweetAlertDialogG.SUCCESS_TYPE)
                    .setTitleText("İŞLEM ONAYI")
                    .setContentText(msg+" değişiklik talebi başarıyla alındı.")
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
            if(op.equals("1")) {
                zimmet_ds.setDs_aktarim("0");
                zimmet_ds.setDs_durum("1");
                zimmet_ds.setDs_eski_yeni("1");

                _myIslem.fn_ekle_ds(zimmet_ds);

                kayıt_yapılacak_urun.add(zimmet_ds);
                updateListviewItem();
            }
        } else {
            new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                    .setTitleText("HATA")
                    .setContentTextSize(25)
                    .setContentText("İşlem yapılamadı. HATA KODU :" + zr.sonuc)
                    .showCancelButton(false)
                    .show();
        }
    }

    private void sorgula_demirbas(String id)
    {
        try
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

            _Param1.set_value(id);

            Genel.showProgressDialog(getContext());
            Duran_Varlik_sap db_detay = persos.fn_sorgula_duran_varlik(_Param1);
            Genel.dismissProgressDialog();

            if (db_detay != null)
            {
                if (db_detay.islem_sonucu.equals("0"))
                {
                    String str = "";
                    str += "DURAN VARLIK NO : " + db_detay.duran_varlik_no;
                    str += "\r\n ESKİ DURAN VARLIK NO : " + db_detay.eski_demirbas_kod;
                    str += "\r\n ADI : " + db_detay.isim_1;
                    str += "\r\n MARKA : " + db_detay.marka;
                    str += "\r\n MASRAF YERİ : " + db_detay.masraf_yeri;
                    str += "\r\n BAŞKANLIK : " + db_detay.daire_baskanligi;
                    str += "\r\n BİNA : " + db_detay.bina_adi + "--" + db_detay.bina;
                    str += "\r\n KAT : " + db_detay.kat_adi + "--" + db_detay.kat;
                    str += "\r\n ODA : " + db_detay.oda_adi + "--" + db_detay.oda;
                    str += "\r\n PERSONEL : " + db_detay.personel_adi + " " + db_detay.personel_soyadi + "--" + db_detay.personel_numarasi;
                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.NORMAL_TYPE)
                            .setTitleText("DURAN VARLIK DETAYI")
                            .setContentText(str)
                            .setContentTextSize(20)
                            .setConfirmText("TAMAM")
                            .showCancelButton(false)
                            .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialogG sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .show();

                }
                else {
                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                            .setTitleText("DEMİRBAŞ BULUNAMADI")
                            .setContentTextSize(25)
                            .setContentText("Demirbaş bulunamadı... \r\n DV_NO :"+id)
                            .showCancelButton(false)
                            .show();
                }

            }
            else {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("BAĞLANTI HATASI")
                        .setContentTextSize(25)
                        .setContentText("Uzak sunucu bağlantısı yapılamadı")
                        .showCancelButton(false)
                        .show();
            }


        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Genel.playButtonClikSound(getContext());
        demirbas_sayim ds = adapter.getItem(longClickedItemIndex);
        switch(item.getItemId()) {
            case R.id.action_menu_etiket_detayi:
                try
                {
                    if (ds.getDs_eski_yeni().equals("1"))
                    {
                        sorgula_demirbas(ds.getDs_demirbas_kod());
                        return true;
                    }
                    String str = "";
                    str += "DEMİRBAŞ : " + ds.getDs_demirbas_ad_1() + "-" + ds.getDs_demirbas_ad_2() + "-" + ds.getDs_demirbas_ad_3();
                    str += "\r\n DEMİRBAŞ SAP KOD : " + ds.getDs_demirbas_kod();
                    str += "\r\n DEMİRBAŞ ESKİ KOD : " + ds.getDs_demirbas_eski_kod();
                    str += "\r\n DEMİRBAŞ BİNA : " + ds.getDs_bina_adi() + "-" + ds.getDs_bina_kod();
                    str += "\r\n DEMİRBAŞ KAT : " + ds.getDs_kat_adi() + "-" + ds.getDs_kat_kod();
                    str += "\r\n DEMİRBAŞ ODA : " + ds.getDs_oda_kod() + "-" + ds.getDs_oda_kod();
                    str += "\r\n ZİMMETLİ : " + ds.getDs_zimmetli_adi() + "-" + ds.getDs_zimmetli_id();
                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.NORMAL_TYPE)
                            .setTitleText("DEMİRBAŞ DETAY")
                            .setContentText(str)
                            .setContentTextSize(20)
                            .setConfirmText("TAMAM")
                            .showCancelButton(false)
                            .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialogG sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .show();

                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
                return true;
            case R.id.action_menu_etiket_sayim_iptal:
                try {
                    if (ds.getDs_eski_yeni().equals("1")) {
                        ds.setDs_durum("31");
                        ds.setDs_aktarim("1");

                        _myIslem.fn_guncelle_ds(ds);

                        kayıt_yapılacak_urun.add(ds);
                        sayilacak_liste.remove(ds);
                    } else {
                        ds.setDs_durum("0");
                        ds.setDs_aktarim("1");

                        _myIslem.fn_guncelle_ds(ds);

                        kayıt_yapılacak_urun.add(ds);
                        sayilacak_liste.remove(ds);
                    }
                    updateListviewItem();
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
                return true;
            case R.id.action_menu_zimmet_degistir:
                try {
                    if (ds.getDs_durum().equals("2")) {

                        request_string _Param1 = new request_string();
                        _Param1.set_zsunucu_ip_adresi(_ayarsunucuip);
                        _Param1.set_zaktif_alt_tesis(_ayaraktifalttesis);
                        _Param1.set_zaktif_tesis(_ayaraktiftesis);
                        _Param1.set_zsurum(_sbtVerisyon);
                        _Param1.set_zkullaniciadi(_zkullaniciadi);
                        _Param1.set_zsifre(_zsifre);
                        _Param1.setAktif_sunucu(_ayaraktifsunucu);
                        _Param1.setAktif_kullanici(_ayaraktifkullanici);

                        _Param1.set_value(ds.getDs_demirbas_kod());

                        Genel.showProgressDialog(getContext());
                        Duran_Varlik_sap detay = persos.fn_sorgula_duran_varlik(_Param1);
                        Genel.dismissProgressDialog();

                        if (detay.islem_sonucu.equals("0")) {
                            demirbas_sayim ds_muadil = new demirbas_sayim();
                            for(demirbas_sayim sayim : sayilacak_liste){
                                if((sayim.getDs_durum().equals("0") || sayim.getDs_durum().equals("1")) && !sayim.getDs_masraf_yeri().equals("")){
                                    ds_muadil=sayim;
                                    break;
                                }
                            }
                            if (detay.masraf_yeri.equals(ds_muadil.getDs_masraf_yeri())) {
                                zimmet_ds=ds;
                                zimmet_muadil=ds_muadil;
                                new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                                        .setTitleText("KONUM DEĞİŞTİR")
                                        .setContentText(detay.duran_varlik_no + " nolu demirbaşın konum bilgisini sayılan yer ile güncellemek istiyor musunuz ?")
                                        .setContentTextSize(20)
                                        .setConfirmText("EVET")
                                        .setCancelText("HAYIR")
                                        .showCancelButton(true)
                                        .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialogG sDialog) {
                                                sDialog.dismissWithAnimation();

                                                zimmetDegistir("1");

                                                return;
                                            }
                                        })
                                        .setCancelClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialogG sDialog) {
                                                sDialog.dismissWithAnimation();
                                                return;
                                            }
                                        })
                                        .show();

                            } else if (!detay.masraf_yeri.equals(ds_muadil.getDs_masraf_yeri())) {
                                zimmet_ds=ds;
                                zimmet_muadil=ds_muadil;
                                new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                                        .setTitleText("MASRAF YERİ DEĞİŞTİR")
                                        .setContentText(detay.duran_varlik_no + " nolu demirbaşın 'MASRAF YERİ' bilgisini sayılan yer ile güncellemek istiyor musunuz ?")
                                        .setContentTextSize(20)
                                        .setConfirmText("EVET")
                                        .setCancelText("HAYIR")
                                        .showCancelButton(true)
                                        .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialogG sDialog) {
                                                sDialog.dismissWithAnimation();

                                                zimmetDegistir("2");

                                                return;
                                            }
                                        })
                                        .setCancelClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialogG sDialog) {
                                                sDialog.dismissWithAnimation();
                                                return;
                                            }
                                        })
                                        .show();
                            }
                        } else {
                            new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                                    .setTitleText("DEMİRBAŞ BULUNAMADI")
                                    .setContentTextSize(25)
                                    .setContentText("Demirbaş bulunamadı... \r\n DV_NO :"+ds.getDs_demirbas_kod())
                                    .showCancelButton(false)
                                    .show();
                        }
                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
                return true;
        }
        return super.onContextItemSelected(item);
    }
    private class fn_btnGeri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Genel.lockButtonClick(view, getActivity());

            if(getActivity() != null) {
                int defaultGercek = MIN_GUC_GERCEK + DEFAULT_GUC;
                ((GirisSayfasi) getActivity()).fn_GucAyarla(defaultGercek);
            }

            frg_ds_isemri_secimi fragmentyeni = new frg_ds_isemri_secimi();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_ds_isemri_secimi").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.menu_frg_ds_sayim_islemi, menu);
    }
}