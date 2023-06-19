package com.etimaden.UretimIslemleri;

import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.cIslem.VeriTabani;
import com.etimaden.cResponseResult.DEPOTag;
import com.etimaden.cResponseResult.Urun_tag;
import com.etimaden.service.frg_paket_uretim_ekrani.service_etiket_islem_baslat;
import com.etimaden.service.frg_paket_uretim_ekrani.service_etiket_kontrol;
import com.etimaden.service.frg_paket_uretim_ekrani.service_sec_ambalaj_degisim_toplam_harcanan_miktar;
import com.etimaden.service.frg_paket_uretim_ekrani.service_sec_etiket_no;
import com.etimaden.service.frg_paket_uretim_ekrani.service_yari_otomatik_paket_kontrol_et;
import com.etimaden.service.request.request_etiket_islem_baslat;
import com.etimaden.service.request.requestetiket_kontrol;
import com.etimaden.service.request.requestsec_ambalaj_degisim_toplam_harcanan_miktar;
import com.etimaden.service.request.requestsec_etiket_no;
import com.etimaden.service.request.requestyari_otomatik_paket_kontrol_et;
import com.etimaden.service.response.Viewetiket_kontrol;
import com.etimaden.service.response.Viewsec_ambalaj_degisim_toplam_harcanan_miktar;
import com.etimaden.service.response.Viewsec_etiket_no;
import com.etimaden.service.response.Viewyari_otomatik_paket_kontrol_et;
import com.etimaden.service.response.etiket_no;
import com.etimaden.service.response.response_etiket_islem_baslat;
import com.etimaden.service.response.uretim_etiket;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.etimaden.cSabitDegerler._zkullaniciadi;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;

public class frg_paket_uretim_ekrani extends Fragment {

    SweetAlertDialog pDialog;

    VeriTabani _myIslem;

    public String _ayaraktifkullanici = "";
    public String _ayaraktifdepo = "";
    public String _ayaraktifalttesis = "";
    public String _ayaraktiftesis = "";
    public String _ayaraktifsunucu = "";
    public String _ayaraktifisletmeeslesme = "";
    public String _ayarbaglantituru = "";
    public String _ayarsunucuip = "";
    public String _ayarversiyon = "";
    public String _OnlineUrl = "http://10.48.24.24:7775/";
    public String _OnlineUrlSecEtiket = "";
    public String _OnlineUrletiket_kontrol = "";
    public String _OnlineUrlyari_otomatik_paket_kontrol_et = "";

    Button _btngeri;
    Button etiketsiz_uret;

    TextView _txtPaletId;
    TextView yapilanAdet;
    TextView _txtAra;
    TextView hedefAdet;
    Button _btnIptal;

    int islemDurumu = 0;// 0 : etiket okutma bekleniyor    1: bigbag üretim işlemi yapılıyor   2: paketli palet üretim işlemi yapılıyor  3 : paket üretim işlemi yapılıyor
    uretim_etiket aktif_Palet = null;
    List<String> paket_listesi = new ArrayList<>();
    DEPOTag depo = null;
    DEPOTag silo = null;
    uretim_etiket aktif_urun = null;

    private void fn_AyarlariYukle()
    {
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
           // _OnlineUrl = "http://" + _ayarsunucuip + ":" + _zportWifi + "/api/etiket_islem_baslat";
            _OnlineUrl = "http://" + _ayarsunucuip + ":" + _zportWifi + "/";

           // _OnlineUrlSecEtiket = "http://" + _ayarsunucuip + ":" + _zportWifi + "/api/sec_etiket_no";
            // _OnlineUrletiket_kontrol = "http://" + _ayarsunucuip + ":" + _zportWifi + "/api/etiket_kontrol";
            //_OnlineUrlyari_otomatik_paket_kontrol_et = "http://" + _ayarsunucuip + ":" + _zportWifi + "/api/yari_otomatik_paket_kontrol_et";
        }
        else
        {
            //_OnlineUrl = "http://88.255.50.73:" + _zport3G + "/api/etiket_islem_baslat";
            _OnlineUrl = "http://88.255.50.73:" + _zport3G + "/";


            //_OnlineUrlSecEtiket = "http://88.255.50.73:" + _zport3G + "/api/sec_etiket_no";
            //_OnlineUrletiket_kontrol = "http://88.255.50.73:" + _zport3G + "/api/etiket_kontrol";
            //_OnlineUrlyari_otomatik_paket_kontrol_et = "http://88.255.50.73:" + _zport3G + "/api/yari_otomatik_paket_kontrol_et";
        }
    }

    public frg_paket_uretim_ekrani() {
        // Required empty public constructor
    }

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
        return inflater.inflate(R.layout.frg_paket_uretim_ekrani, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        new VeriTabani(getContext()).fn_EpcTemizle();

        _myIslem = new VeriTabani(getContext());

        fn_AyarlariYukle();

        _btngeri = (Button) getView().findViewById(R.id.btncikis);
        _btngeri.playSoundEffect(0);
        _btngeri.setOnClickListener(new fn_Geri());

        etiketsiz_uret = (Button) getView().findViewById(R.id.btnEtiketsizUretim);
        etiketsiz_uret.playSoundEffect(0);
        etiketsiz_uret.setOnClickListener(new fn_EtiketsizUretim());

        _txtPaletId = (TextView) getView().findViewById(R.id.txtPaletId);
        yapilanAdet = (TextView) getView().findViewById(R.id.txtParcaBir);
        _txtAra = (TextView) getView().findViewById(R.id.txtAra);
        hedefAdet = (TextView) getView().findViewById(R.id.txtParcaIki);
        _btnIptal = (Button) getView().findViewById(R.id.btnIptal);

        fn_Sakla();

        if(depo==null)
        {
            //Toast.makeText(getContext(), "null geldiii", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(depo !=null & silo !=null)
            {
               // Toast.makeText(getContext(), aktif_urun.getpaket_tipi(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(getContext(), aktif_urun.getisemri_tipi(), Toast.LENGTH_SHORT).show();
                etiket_islem_baslat(aktif_urun.getserino_rfid());
                // etiket_islem_baslat(aktif_urun.serino_rfid);
            }
        }
    }

    public void fn_senddata(DEPOTag v_depo, DEPOTag v_silo, uretim_etiket v_aktif_urun)
    {
        depo = v_depo;
        silo = v_silo;
        aktif_urun = v_aktif_urun;
    }

    private void fn_Sakla()
    {
        etiketsiz_uret.setVisibility(View.INVISIBLE);
        _txtPaletId.setVisibility(View.INVISIBLE);
        yapilanAdet.setVisibility(View.INVISIBLE);
        _txtAra.setVisibility(View.INVISIBLE);
        hedefAdet.setVisibility(View.INVISIBLE);
        _btnIptal.setVisibility(View.INVISIBLE);
    }

    private void fn_PanelAc()
    {
        etiketsiz_uret.setVisibility(View.VISIBLE);
        _txtPaletId.setVisibility(View.VISIBLE);
        yapilanAdet.setVisibility(View.VISIBLE);
        _txtAra.setVisibility(View.VISIBLE);
        hedefAdet.setVisibility(View.VISIBLE);
        _btnIptal.setVisibility(View.VISIBLE);
    }


    public void fn_BarkodOkutuldu(String v_Gelen)
    {
        String barkod = v_Gelen;

        if(barkod.length()>24)
        {
            int _Sayi = barkod.length();

            barkod = barkod.substring(barkod.length()- 24);
        }

        etiket_islem_baslat(barkod);
    }




    public void etiket_islem_baslat(String str)
    {
        try
        {
            if (islemDurumu == 0)
            {
                //region
                uretim_etiket etiket = sec_etiket_uretim(str);
                //endregion

                //region etiket_durumunu_kontrol_et

                if (etiket == null)
                {
                    SweetAlertDialog pHataDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                    pHataDialog.setTitleText("UYARI");
                    pHataDialog.setContentText("Lütfen uygun bir ürün etiketi okutunuz. <br>Ürün kaydı bulunamadı..");
                    //pDialog.setContentText(_TempEpc);
                    //pHataDialog.setCancelable(false);
                    pHataDialog.show();

                   return;
                }
                else if (!etiket.getetiket_durumu().equals("0"))
                {

                    SweetAlertDialog pHataDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                    pHataDialog.setTitleText("Üretilmiş Etiket");
                    pHataDialog.setContentText("Üretim için uygun olmayan etiket.<br><b>Etiketin üretim işlemi tamamlanmıştır.</b>");
                    pHataDialog.show();

                    return;
                }
                else if (!etiket.getetiket_uretim_uygunlugu().equals("1"))
                {

                    String _Yazi ="ÜRETİM ONAYI ALINAMADI. DETAYLAR KISMINDAN ÜRETİM KURALLARINI OKUYABİLİRSİNİZ.";
                    _Yazi+="<br>ÜRETİM KURALLARI";
                    _Yazi+="<br>1) İLK 5 DAKİKA İÇİNDE ÜRETİM İŞLEMİNİ GERÇEKLEŞTİREMEZSİNİZ.";
                    _Yazi+="<br>2) 3 GÜN İÇİNDE ÜRETİM KAYDI GERÇEKLEŞMEYEN ETİKET İÇİN ÜRETİM KAYDI OLUŞTURULAMAZ";
                    _Yazi+="<br>3) SİSTEM İÇİNDE AYNI İŞ EMRİNE AİT EN FAZLA 2 ADET ÜRETİMİ TAMAMLANMAMIŞ LOT BULUNABİLİR.";

                    SweetAlertDialog pHataDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                    pHataDialog.setTitleText("ONAY ALINAMADI");
                    pHataDialog.setContentText(_Yazi);
                    pHataDialog.show();

                    return;

                }

                else if (depo == null || silo == null)
                {
                    frg_depo_secimi fragmentyeni = new frg_depo_secimi();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentyeni.fn_senddata(etiket);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_depo_secimi").addToBackStack(null);
                    fragmentTransaction.commit();
                }
                else if ((!etiket.getdepo_silo_secimi().equals("") && (!etiket.getdepo_silo_secimi().contains(depo.depo_id) || !etiket.getdepo_silo_secimi().contains(silo.depo_id))))
                {
                    frg_depo_secimi fragmentyeni = new frg_depo_secimi();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentyeni.fn_senddata(etiket);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_depo_secimi").addToBackStack(null);
                    fragmentTransaction.commit();
                }

                else if (etiket.getpaket_tipi().equals("1"))
                {
                    if (etiket.getisemri_tipialt().equals("350"))
                    {
                        manipulasyon_paketli_uret(etiket);
                    }
                    else if (etiket.getisemri_tipialt().equals("150"))
                    {
                        paketli_palet_basla(etiket);
                    }
                    else if (etiket.getisemri_tipialt().equals("152"))
                    {
                        bigbag_uret(etiket);
                    }
                    else if (etiket.getisemri_tipialt().equals("400"))
                    {
                        manipulasyon_palet_topla(etiket);
                    }


                }
                else if (etiket.getpaket_tipi().equals("2") || etiket.getpaket_tipi().equals("3"))
                {
                    if (etiket.getisemri_tipialt().equals("350"))
                    {
                        manipulasyon_paketli_uret(etiket);
                    }
                    else if (etiket.getisemri_tipialt().equals("151") || etiket.getisemri_tipialt().equals("152") || etiket.getisemri_tipialt().equals("153"))
                    {
                        bigbag_uret(etiket);
                    }
                    else if (etiket.getisemri_tipialt().equals("351"))
                    {
                        manipulasyon_bigbag_uret(etiket);
                    }

                }
                else
                {
                    SweetAlertDialog pHataDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                    pHataDialog.setTitleText("Üretilmiş Etiket");

                    pHataDialog.setContentText("Üretim için uygun olmayan etiket.<br>Etiketin üretim işlemi tamamlanmıştır.");
                    //pDialog.setContentText(_TempEpc);
                    //pHataDialog.setCancelable(false);
                    pHataDialog.show();
                }



                    //endregion



            }


        }catch (Exception ex)
        {
            SweetAlertDialog pHataDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
            pHataDialog.setTitleText("HATA 01");
            pHataDialog.setContentText(ex.toString());
            pHataDialog.show();
        }
    }

    private void manipulasyon_bigbag_uret(uretim_etiket etiket) {
    }

    private void bigbag_uret(uretim_etiket etiket) {
    }

    private void manipulasyon_palet_topla(uretim_etiket etiket) {
    }



    private void manipulasyon_paketli_uret(final uretim_etiket etiket) {

        int miktar_int = 0;

        final uretim_etiket v_Param=etiket;

        String miktar = sec_ambalaj_degisim_toplam_harcanan_miktar(etiket);

        if (miktar == null)
        {
            SweetAlertDialog pHataDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
            pHataDialog.setTitleText("YETERSİZ ÜRÜN MİKTARI");
            pHataDialog.setContentText("BU İŞ EMRİNE BAĞLI ÜRETİM YAPMAK İÇİN YETERLİ ÜRÜN HARCAMA İŞLEMİ YAPILMAMIŞ.<br> YETERLİ HARCAMA YAPTIKTAN SONRA İLGİLİ İŞ EMRİNE DEVAM EDEBİLİRSİNİZ.<br><b>İŞLEM KAYDI TAMAMLANAMADI.</b>");
            //pDialog.setContentText(_TempEpc);
            //pHataDialog.setCancelable(false);
            return;
        }
        else if (miktar.equals(""))
        {
            SweetAlertDialog pHataDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
            pHataDialog.setTitleText("BAĞLANTI PROBLEMİ");
            pHataDialog.setContentText("İŞLEM YAPILAMADI. DAHA SONRA TEKRAR DENEYİNİZ.<br> İŞLEM KAYDI TAMAMLANAMADI.");
            //pDialog.setContentText(_TempEpc);
            //pHataDialog.setCancelable(false);
            return;
        }
        else
        {
            try
            {
                miktar_int = Integer.parseInt(miktar);
                int eklenecek_miktar = Integer.parseInt(etiket.getpalet_miktar());
                miktar_int = miktar_int - eklenecek_miktar;
                if (miktar_int >= 0)
                {
                    String _TempYazi ="SERİNO : " + etiket.getserino_kod() + "<br>";
                    _TempYazi += "LOTNO : " + etiket.getser_lotno() + "<br>";
                    _TempYazi += "ÜRÜN : " + etiket.geturun_kodu() + "<br>";
                    _TempYazi += "KULLANILABİLİR MİKTAR : " + miktar + " KG" + "<br>";
                    _TempYazi +=  "PAKET TİPİ DEĞİŞTİRME İŞLEMİNE DEVAM ETMEK İSTİYOR MUSUNUZ ?";


                    SweetAlertDialog pConfirm = new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE);
                    pConfirm.setTitle("PAKET TİPİ DEĞİŞTİR");
                    pConfirm.setContentText(_TempYazi);
                    pConfirm.showCancelButton(true);
                    pConfirm.setCancelText("VAZGEÇ");
                    pConfirm.setConfirmText("DEVAM");
                    pConfirm.findViewById(R.id.confirm_button).setVisibility(View.VISIBLE);
                    pConfirm.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            return;
                        }
                    });
                    pConfirm.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                            sweetAlertDialog.dismissWithAnimation();

                            sweetAlertDialog.hide();


                            if (islemDurumu == 0) {
                                paketli_palet_basla(v_Param);
                            }
                            else if (islemDurumu == 2){
                                paketli_palet_uretim_tamamla(v_Param);
                            }
                        }
                    });

                }
                else
                {
                    SweetAlertDialog pHataDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                    pHataDialog.setTitleText("YETERSİZ ÜRÜN MİKTARI");
                    pHataDialog.setContentText("BU İŞ EMRİNE BAĞLI ÜRETİM YAPMAK İÇİN YETERLİ ÜRÜN HARCAMA İŞLEMİ YAPILMAMIŞ..<br>KULLANILABİLİR MİKTAR : "+miktar);
                    //pDialog.setContentText(_TempEpc);
                    //pHataDialog.setCancelable(false);
                    pHataDialog.show();
                    return;
                }
            }
            catch (Exception ex_03)
            {

            }
        }
    }



    private String set_etiketno(etiket_no eno, int sirano) {
        String a = "";
        try {
            a = "7377670000";
            String yil = eno.geteti_yil().substring(eno.geteti_yil().length() - 2);
            String ay = eno.geteti_ay();
            if (eno.geteti_ay().length() != 2) {
                ay = "0" + eno.geteti_ay();
            }
            String gun = eno.geteti_gun();
            if (eno.geteti_gun().length() != 2) {
                gun = "0" + eno.geteti_gun();
            }

            String b = yil + ay + gun + eno.geteti_isletme();

            while ((b.length()) + ((sirano + "".trim()).length()) != 14) {
                int _Sayi_01 = b.length();
                int _Sayi_02 = (sirano + "".trim()).length();


                b = b + "0";
            }

            a = a + b + sirano + "";

            //MessageBox.Show(a);

        } catch (Exception exx) {
        }
        return a;
    }


    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_uretim_menu_panel fragmentyeni = new frg_uretim_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_uretim_menu_panel").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_EtiketsizUretim implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            int hAdet = Integer.parseInt(aktif_Palet.getpalet_dizim());
            etiket_no eno = sec_etiket_no(hAdet);
            int sirano_1 = Integer.parseInt(eno.geteti_sirano()) - hAdet + 1;
            int sirano_2 = Integer.parseInt(eno.geteti_sirano());

            paketli_palet_paket_ekle(set_etiketno(eno, sirano_1));

            paketli_palet_paket_ekle(set_etiketno(eno, sirano_2));


        }
    }

    //region

    private void paketli_palet_paket_ekle(String etiket){

        if (paket_listesi.size() > 0 && (!etiket_kontrol(paket_listesi.get(0)).equals("") || !etiket_kontrol(paket_listesi.get(paket_listesi.size() - 1)).equals("")))
        {

            SweetAlertDialog pHataDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
            pHataDialog.setTitleText("HATALI");
            pHataDialog.setContentText("Üretim işlemi yapılmış etiket üzerinden bir daha işlem yapamazsınız.");
            pHataDialog.show();

            return;
        }
        else
        {
            String lot = yari_otomatik_paket_kontrol_et(etiket.substring(10, 24));
            if (paket_listesi.size() == 0 && !lot.equals(""))
            {
                yariotomatik_paketli_palet_uretim_tamamla(aktif_Palet,lot);
            }

            paket_listesi.remove(aktif_Palet.getserino_rfid());
            int tadet = paket_listesi.size();
            int hAdet = Integer.parseInt(aktif_Palet.getpalet_dizim());

            if (hAdet == tadet)
            {
                SweetAlertDialog pHataDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                pHataDialog.setTitleText("Hatalı İşlem");
                pHataDialog.setContentText("Paket ekleme işlemi başarısız!<br>Palet kapatma limitine ulaştınız.<br>Hatalı işlem!!Lütfen paleti kapatınız!!");
                pHataDialog.show();
                return;
            }
            if (!etiket.substring(6, 7).equals("0"))
            {
                SweetAlertDialog pHataDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                pHataDialog.setTitleText("Hatalı İşlem");
                pHataDialog.setContentText("Paket ekleme işlemi başarısız!<br>Lütfen uygun bir paket etiketi okutunuz..<br>Hatalı işlem!! Etiket paket etiketi değil!!");
                pHataDialog.show();
                return;
            }



            //if (!paket_listesi.Any(w => w.Equals(etiket)))
            if (paket_listesi.indexOf(etiket)==-1)
            {
                if (paket_listesi.size() == 1)
                {
                    int aktif_paket_sayisi = Integer.parseInt(etiket.substring(16,24)) - Integer.parseInt(paket_listesi.get(0).substring(16, 24));

                    if (Integer.parseInt(aktif_Palet.getpalet_dizim()) == (aktif_paket_sayisi + 1))
                    {
                        String str_substring = etiket.substring(0, 16);

                        for (int i = 1; i < aktif_paket_sayisi + 1; i++)
                        {

                            String str_endstring = (Integer.parseInt(paket_listesi.get(0).substring(16, 24)) + i)+"";

                            while (true)
                            {
                                if (str_endstring.length() >= 8)
                                {
                                    break;
                                }
                                else
                                {
                                    str_endstring = "0" + str_endstring;
                                }
                            }

                            etiket = str_substring + str_endstring;

                            paket_listesi.add(etiket);

                        }

                        // MessageBox.Show(paket_listesi.ElementAt(0) + Environment.NewLine + paket_listesi.ElementAt(paket_listesi.Count-1));
                    }
                    else
                    {
                        paket_listesi.add(etiket);
                    }
                }
                else
                {
                    paket_listesi.add(etiket);
                }
            }
                        else
            {
                paket_listesi.remove(etiket);

            }

            try
            {
                    yapilanAdet.setText(paket_listesi.size() + "");
                    hedefAdet.setText(aktif_Palet.getpalet_dizim()+"");


            }
            catch (Exception exx)
            {

            }
            tadet = paket_listesi.size();
            hAdet = Integer.parseInt(aktif_Palet.getpalet_dizim());
            if (hAdet == tadet)
            {
                islemDurumu = 2;
                return;
            }

        }
    }

    private void yariotomatik_paketli_palet_uretim_tamamla(uretim_etiket aktif_palet, String lot)
    {





    }


    public uretim_etiket sec_etiket_uretim(String etiket)
    {
        uretim_etiket _Cevap=new uretim_etiket();

        pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE);
        pDialog.setTitleText("YÜKLENİYOR{sec_etiket_uretim}");
        pDialog.setContentText("Kontrol ediliyor Lütfen bekleyiniz.");
        //pDialog.setContentText(_TempEpc);
        pDialog.setCancelable(false);
        pDialog.show();
        pDialog.findViewById(R.id.confirm_button).setVisibility(View.GONE);

        try
        {
            StrictMode.ThreadPolicy gfgPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(gfgPolicy);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(_OnlineUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            service_etiket_islem_baslat _Service = retrofit.create(service_etiket_islem_baslat.class);

            request_etiket_islem_baslat _Param = new request_etiket_islem_baslat();
            _Param._zsunucu_ip_adresi = _ayarsunucuip;
            _Param._zaktif_alt_tesis = _ayaraktifalttesis;
            _Param._zaktif_tesis = _ayaraktiftesis;
            _Param._zkullaniciadi = _zkullaniciadi;
            _Param._zsifre = _zsifre;
            _Param.aktif_sunucu = _ayaraktifsunucu;
            _Param.aktif_kullanici = _ayaraktifkullanici;
            _Param._zetiket = etiket;

            Call<response_etiket_islem_baslat> callSync = _Service.fn_etiket_islem_baslat(_Param);

            try
            {
                Response<response_etiket_islem_baslat> response = callSync.execute();
                // UserApiResponse apiResponse = response.body();

                _Cevap=response.body().get_zEtiket();

                try
                {
                    pDialog.hide();
                }catch (Exception ex)
                {

                }

                //API response
                return  _Cevap;
            }
            catch (Exception ex)
            {
                try
                {
                    pDialog.hide();
                }catch (Exception ex_02)
                {

                }

                SweetAlertDialog pHataDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                pHataDialog.setTitleText("HATA 03");
                pHataDialog.setContentText(ex.toString());
                pHataDialog.show();

                _Cevap =null;






                return  _Cevap;
            }

        }catch (Exception ex)
        {

            try
            {
                pDialog.hide();
            }catch (Exception ex_01)
            {

            }

            SweetAlertDialog pHataDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
            pHataDialog.setTitleText("HATA 02");
            pHataDialog.setContentText(ex.toString());
            pHataDialog.show();
        }



        return _Cevap;

    }

    private void paketli_palet_basla(uretim_etiket etiket)
    {
        islemDurumu = 1;

        aktif_Palet = etiket;

        paket_listesi.clear();

        yapilanAdet.setText(paket_listesi.size()+ "");

        hedefAdet.setText(etiket.getpalet_dizim()+"");

        fn_PanelAc();

    }

    public etiket_no sec_etiket_no(int palet_dizim)
    {
        etiket_no _Cevap=new etiket_no();

        pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE);
        pDialog.setTitleText("YÜKLENİYOR{sec_etiket_no}");
        pDialog.setContentText("Kontrol ediliyor Lütfen bekleyiniz.");
        //pDialog.setContentText(_TempEpc);
        pDialog.setCancelable(false);
        pDialog.show();
        pDialog.findViewById(R.id.confirm_button).setVisibility(View.GONE);

        try
        {

            StrictMode.ThreadPolicy gfgPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(gfgPolicy);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(_OnlineUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            service_sec_etiket_no _Service = retrofit.create(service_sec_etiket_no.class);

            requestsec_etiket_no _Param = new requestsec_etiket_no();
            _Param._zsunucu_ip_adresi = _ayarsunucuip;
            _Param._zaktif_alt_tesis = _ayaraktifalttesis;
            _Param._zaktif_tesis = _ayaraktiftesis;
            _Param._zkullaniciadi = _zkullaniciadi;
            _Param._zsifre = _zsifre;
            _Param.aktif_sunucu = _ayaraktifsunucu;
            _Param.aktif_kullanici = _ayaraktifkullanici;

            _Param.palet_dizim=palet_dizim+"";

            Call<Viewsec_etiket_no> callSync = _Service.fn_sec_etiket_no(_Param);

            try {
                Response<Viewsec_etiket_no> response = callSync.execute();



                Viewsec_etiket_no _Temp = response.body();

                Viewsec_etiket_no _Gelen = response.body();

                if (_Gelen.get_zSonuc().equals("0")) {
                    SweetAlertDialog pHataDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                    pHataDialog.setTitleText("HATA 02");
                    pHataDialog.setContentText(_Gelen.get_zHataAciklama().toString());
                    pHataDialog.show();

                    _Cevap = null;
                }
                else
                    {
                    _Cevap = response.body().geteno();

                    try {
                        pDialog.hide();
                    } catch (Exception ex) {

                    }
                }
                return _Cevap;


            }catch (Exception ex_03)
            {
                SweetAlertDialog pHataDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                pHataDialog.setTitleText("HATA 03");
                pHataDialog.setContentText(ex_03.toString());
                pHataDialog.show();

                try
                {
                    pDialog.hide();
                }catch (Exception ex)
                {

                }

                _Cevap = null;

                return _Cevap;
            }



        }catch (Exception ex_02 )
        {
            SweetAlertDialog pHataDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
            pHataDialog.setTitleText("HATA 02");
            pHataDialog.setContentText(ex_02.toString());
            pHataDialog.show();

            try
            {
                pDialog.hide();
            }catch (Exception ex)
            {

            }
            _Cevap = null;

            return _Cevap;
        }
    }

    public String etiket_kontrol(String rfid){
        String _Cevap="";

        try
        {
            pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE);
            pDialog.setTitleText("YÜKLENİYOR{etiket_kontrol}");
            pDialog.setContentText("Kontrol ediliyor Lütfen bekleyiniz.");
            //pDialog.setContentText(_TempEpc);
            pDialog.setCancelable(false);
            pDialog.show();
            pDialog.findViewById(R.id.confirm_button).setVisibility(View.GONE);

            StrictMode.ThreadPolicy gfgPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(gfgPolicy);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(_OnlineUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            service_etiket_kontrol _Service = retrofit.create(service_etiket_kontrol.class);

            requestetiket_kontrol _Param = new requestetiket_kontrol();
            _Param._zsunucu_ip_adresi = _ayarsunucuip;
            _Param._zaktif_alt_tesis = _ayaraktifalttesis;
            _Param._zaktif_tesis = _ayaraktiftesis;
            _Param._zkullaniciadi = _zkullaniciadi;
            _Param._zsifre = _zsifre;
            _Param.aktif_sunucu = _ayaraktifsunucu;
            _Param.aktif_kullanici = _ayaraktifkullanici;
            _Param.etiket = rfid;

            Call<Viewetiket_kontrol> callSync = _Service.fn_etiket_kontrol(_Param);

            try
            {
                Response<Viewetiket_kontrol> response = callSync.execute();
                // UserApiResponse apiResponse = response.body();

                _Cevap=response.body().get_zAciklama();

                try
                {
                    pDialog.hide();
                }catch (Exception ex)
                {

                }
                return  _Cevap;
            }
            catch (Exception ex)
            {
                try {
                    pDialog.hide();
                } catch (Exception ex_02) {

                }

                SweetAlertDialog pHataDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                pHataDialog.setTitleText("HATA 03");
                pHataDialog.setContentText(ex.toString());
                pHataDialog.show();

                _Cevap = "-1";

                return _Cevap;
            }


        }catch (Exception ex_04)
        {
            try {
                pDialog.hide();
            } catch (Exception ex_02) {

            }

            SweetAlertDialog pHataDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
            pHataDialog.setTitleText("HATA 04");
            pHataDialog.setContentText(ex_04.toString());
            pHataDialog.show();

            _Cevap = "-1";

            return _Cevap;
        }

    }

    public String yari_otomatik_paket_kontrol_et(String serino)
    {
        String _Cevap="";

        pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE);
        pDialog.setTitleText("YÜKLENİYOR{yari_otomatik_paket_kontrol_et}");
        pDialog.setContentText("Kontrol ediliyor Lütfen bekleyiniz.");
        //pDialog.setContentText(_TempEpc);
        pDialog.setCancelable(false);
        pDialog.show();
        pDialog.findViewById(R.id.confirm_button).setVisibility(View.GONE);

        try
        {
            StrictMode.ThreadPolicy gfgPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(gfgPolicy);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(_OnlineUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            service_yari_otomatik_paket_kontrol_et _Service = retrofit.create(service_yari_otomatik_paket_kontrol_et.class);

            requestyari_otomatik_paket_kontrol_et _Param = new requestyari_otomatik_paket_kontrol_et();
            _Param._zsunucu_ip_adresi = _ayarsunucuip;
            _Param._zaktif_alt_tesis = _ayaraktifalttesis;
            _Param._zaktif_tesis = _ayaraktiftesis;
            _Param._zkullaniciadi = _zkullaniciadi;
            _Param._zsifre = _zsifre;
            _Param.aktif_sunucu = _ayaraktifsunucu;
            _Param.aktif_kullanici = _ayaraktifkullanici;
            _Param.serino = serino;

            Call<Viewyari_otomatik_paket_kontrol_et> callSync = _Service.fn_yari_otomatik_paket_kontrol_et(_Param);

            try
            {
                Response<Viewyari_otomatik_paket_kontrol_et> response = callSync.execute();

                Viewyari_otomatik_paket_kontrol_et _Yanit = response.body();

                //String _zYanit = response.body().get_zSonuc();

                if (_Yanit.get_zSonuc().equals("0"))
                {
                    pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    pDialog.setTitle("HATA 09");
                    pDialog.setContentText(_Yanit.get_zHataAciklama());

                    _Cevap="-1";

                    return _Cevap;
                }
                else
                {
                    try
                    {
                        pDialog.hide();
                    }catch (Exception ex)
                    {

                    }

                    _Cevap= _Yanit.get_zAciklama();

                    return  _Cevap;
                }

            }catch (Exception ex)
            {
                try
                {
                    pDialog.hide();
                }catch (Exception ex_01)
                {}

                SweetAlertDialog pHataDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                pHataDialog.setTitleText("HATA 10");
                pHataDialog.setContentText(ex.toString());
                //pDialog.setContentText(_TempEpc);
                //pHataDialog.setCancelable(false);
                pHataDialog.show();

                _Cevap= "-1";

                return  _Cevap;
            }
        }catch (Exception ex)
        {
            try
            {
                pDialog.hide();
            }catch (Exception ex_02)
            {}

            SweetAlertDialog pHataDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
            pHataDialog.setTitleText("HATA 11");
            pHataDialog.setContentText(ex.toString());
            //pDialog.setContentText(_TempEpc);
            //pHataDialog.setCancelable(false);
            pHataDialog.show();

            _Cevap= "-1";

            return  _Cevap;
        }
    }



    private String sec_ambalaj_degisim_toplam_harcanan_miktar(uretim_etiket etiket)
    {
        String _Cevap="";

        pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE);
        pDialog.setTitleText("YÜKLENİYOR{sec_ambalaj_degisim_toplam_harcanan_miktar}");
        pDialog.setContentText("Kontrol ediliyor Lütfen bekleyiniz.");
        //pDialog.setContentText(_TempEpc);
        pDialog.setCancelable(false);
        pDialog.show();
        pDialog.findViewById(R.id.confirm_button).setVisibility(View.GONE);

        try
        {
            StrictMode.ThreadPolicy gfgPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(gfgPolicy);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(_OnlineUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            service_sec_ambalaj_degisim_toplam_harcanan_miktar _Service = retrofit.create(service_sec_ambalaj_degisim_toplam_harcanan_miktar.class);

            try
            {
                requestsec_ambalaj_degisim_toplam_harcanan_miktar _Param = new requestsec_ambalaj_degisim_toplam_harcanan_miktar();
                _Param._zsunucu_ip_adresi = _ayarsunucuip;
                _Param._zaktif_alt_tesis = _ayaraktifalttesis;
                _Param._zaktif_tesis = _ayaraktiftesis;
                _Param._zkullaniciadi = _zkullaniciadi;
                _Param._zsifre = _zsifre;
                _Param.aktif_sunucu = _ayaraktifsunucu;
                _Param.aktif_kullanici = _ayaraktifkullanici;
                _Param.sap_kodu = etiket.getsap_kodu();

                Call<Viewsec_ambalaj_degisim_toplam_harcanan_miktar> callSync = _Service.fn_sec_ambalaj_degisim_toplam_harcanan_miktar(_Param);

                Response<Viewsec_ambalaj_degisim_toplam_harcanan_miktar> response = callSync.execute();

                Viewsec_ambalaj_degisim_toplam_harcanan_miktar _Yanit = response.body();

                if (_Yanit.get_zSonuc().equals("0"))
                {
                    pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    pDialog.setTitle("HATA 09");
                    pDialog.setContentText(_Yanit.get_zHataAciklama());

                    _Cevap="";

                    return _Cevap;
                }

                else
                {
                    try
                    {
                        pDialog.hide();
                    }catch (Exception ex)
                    {

                    }

                    _Cevap= _Yanit.get_miktar();

                    return  _Cevap;
                }

            }catch (Exception ex_02)
            {
                try
                {
                    pDialog.hide();

                }catch (Exception ex_01)
                {

                }

                SweetAlertDialog pHataDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                pHataDialog.setTitleText("HATA 10");
                pHataDialog.setContentText(ex_02.toString());
                //pDialog.setContentText(_TempEpc);
                //pHataDialog.setCancelable(false);
                pHataDialog.show();

                _Cevap= "";

                return  _Cevap;
            }




        }catch (Exception ex_003 )
        {
            try
            {
                pDialog.hide();
            }catch (Exception ex_01)
            {}

            SweetAlertDialog pHataDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
            pHataDialog.setTitleText("HATA 10");
            pHataDialog.setContentText(ex_003.toString());
            //pDialog.setContentText(_TempEpc);
            //pHataDialog.setCancelable(false);
            pHataDialog.show();

            _Cevap= "";

            return  _Cevap;
        }
    }


    private void paketli_palet_uretim_tamamla(uretim_etiket etiket){



        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("ONAY")
                .setContentText(etiket.getserino_kod() + " seri nolu ürünün üretim işlemini tamamlamak istiyor musunuz ?")
                .setCancelText("Hayır")
                .setConfirmText("EVET")
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog)
                    {
                        sDialog.dismissWithAnimation();

                        sDialog.hide();


                        if (paket_listesi.size() > 0 && (!etiket_kontrol(paket_listesi.get(0)).equals("") || !etiket_kontrol(paket_listesi.get(paket_listesi.size()- 1)).equals("")))
                        {
                            SweetAlertDialog pHataDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                            pHataDialog.setTitleText("HATA ");
                            pHataDialog.setContentText("Üretim işlemi yapılmış etiket üzerinden bir daha işlem yapamazsınız");
                            //pDialog.setContentText(_TempEpc);
                            //pHataDialog.setCancelable(false);
                            pHataDialog.show();
                        }

                        int tadet = paket_listesi.size();

                        int hAdet = Integer.parseInt(aktif_Palet.getpalet_dizim());




                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog)
                    {

                    }
                })
                .show();
    }


    //endregion
}

