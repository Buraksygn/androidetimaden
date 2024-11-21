package com.etimaden.UretimIslemleri.Paket_uretim;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.GirisSayfasi;
import com.etimaden.UretimIslemleri.frg_uretim_menu_panel;
import com.etimaden.adapter.apmblAktifIsEmirleri;
import com.etimaden.adapter.apmblSevkiyatAktifIsEmiriYukleme;
import com.etimaden.adapter.apmblShirinklenecekListesi;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.genel.Genel;
import com.etimaden.genel.SweetAlertDialogG;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.Urun_tag;
import com.etimaden.request.request_secEtiket;
import com.etimaden.request.request_shrink_is_emri;
import com.etimaden.request.request_shrink_onayi_al;
import com.etimaden.ugr_demo.R;


import static com.etimaden.cSabitDegerler._ipAdresi3G;
import static com.etimaden.cSabitDegerler._sbtVerisyon;
import static com.etimaden.cSabitDegerler._zkullaniciadi;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;

import java.util.ArrayList;

public class  frg_shrink_onay extends Fragment {

    SweetAlertDialogG pDialog;
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


    //Button _btnShrinkOlustur;
    //ListView _isemri_list;
    Button _btngeri;
    Button _btnShrinkOlustur;
    //TextView _txtYazi;
    ListView _etiket_list;
    TextView _txtDurum;
    TextView _txtEtiket;

    Urun_tag aktif_etiket;

    ArrayList<Urun_tag> dataModels;
    Urun_tag _Secili = new Urun_tag();
    private static apmblShirinklenecekListesi adapter;
    //uretim_etiket urun;


    //Urun_tag _Secili = new Urun_tag();
    //private static apmblAktifIsEmirleri adapter;

    int islemDurumu = 0;
    //  0 : etiket okutma bekleniyor
    //  1 : bigbag üretim işlemi yapılıyor
    //  2 : paketli palet üretim işlemi yapılıyor
    //  3 : paket üretim işlemi yapılıyor

    public frg_shrink_onay() {
        // Required empty public constructor
    }

    public static frg_shrink_onay newInstance()
    {
        return new frg_shrink_onay();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_shrink_onay, container, false);
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

        new VeriTabani(getContext()).fn_EpcTemizle();

        _myIslem = new VeriTabani(getContext());

        //barkod,rfid,ikiside
        ((GirisSayfasi) getActivity()).fn_ModBarkod();

       // _txtYazi=(TextView)getView().findViewById(R.id.txtYazi);
        _etiket_list = (ListView) getView().findViewById(R.id.etiket_list);

        _etiket_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _Secili = dataModels.get(position);
            }
        });
        _txtDurum=(TextView)getView().findViewById(R.id.txtDurum);
        _txtEtiket=(TextView)getView().findViewById(R.id.txtEtiket);



        _btngeri = (Button)getView().findViewById(R.id.btngeri);
        _btngeri.playSoundEffect(SoundEffectConstants.CLICK);
        _btngeri.setOnClickListener(new fn_Geri());

        _btnShrinkOlustur=(Button)getView().findViewById(R.id.btnShrinkOlustur);
        _btnShrinkOlustur.playSoundEffect(SoundEffectConstants.CLICK);
        _btnShrinkOlustur.setOnClickListener(new fn_btnShrinkEslestir());

        //dataModels= new ArrayList<Urun_tag>();
        //adapter=new apmblShirinklenecekListesi(dataModels,getContext());
       // _etiket_list.setAdapter(adapter);
        fn_AyarlariYukle();
        dataModels= new ArrayList<Urun_tag>();
    }
    public void fn_BarkodOkutuldu(String barkod) {

        try
        {
            barkod = barkod.substring(barkod.length() - 24);
            if (!isReadable)
            {
                return;
            }
            isReadable = false;
            //System.Media.SystemSounds.Question.Play();

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


            etiketDegerlendir(tag);

        }
        catch (Exception ex){
            Genel.printStackTrace(ex,getContext());
        }
        //Thread.Sleep(1000);
        isReadable = true;


    }
    private void updateListviewItem()
    {
        try
        {
            int gorev_id = 0;
//            if (adapter != null) {
//                adapter.clear();
//                //_urun_list.setAdapter(adapter);
//
//                adapter=new apmblShirinklenecekListesi(dataModels,getContext());
//                adapter.addAll(dataModels);
//                //_urun_list.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//            }
//            else{
//                adapter=new apmblShirinklenecekListesi(dataModels,getContext());
//                adapter.addAll(dataModels);
//                //_urun_list.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//            }
            if (adapter != null) {
                //adapter.clear();
                _etiket_list.setAdapter(adapter);
            }

            adapter=new apmblShirinklenecekListesi(dataModels,getContext());
            _etiket_list.setAdapter(adapter);



        }
        catch (Exception ex)
        {
            Genel.printStackTrace(ex,getContext());
        }
    }
    public void fn_RfidOkundu(String v_epc){
        System.out.println("Rfid okutuldu : " + v_epc);

        try
        {
            if (!isReadable)
            {
                return;
            }
            isReadable = false;
            //System.Media.SystemSounds.Question.Play();
            request_secEtiket v_Gelen=new request_secEtiket();
            v_Gelen.set_rfid(v_epc);
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
            etiketDegerlendir(tag);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        isReadable = true;

    }
    private void etiketDegerlendir(Urun_tag tag)
    {
        try
        {

            if (islemDurumu == 0)
            {
                if (tag == null )
                {
                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                            .setTitleText("BAĞLANTI PROBLEMİ")
                            .setContentTextSize(25)
                            .setContentText("ÜRÜN BİLGİSİ ALINAMADI. DAHA SONRA TEKRAR DENEYİNİZ. \r\n Etiket verisine ulaşılamadı.")
                            .showCancelButton(false)
                            .show();
                    return;

                }
                //  a.ser_flag_serinodurumu, a.ser_flag_serinotipi,
                else if ( (!tag.islem_durumu.equals("1")) || (!tag.etiket_turu.equals("1")) )
                {
                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                            .setTitleText("İŞLEM İÇİN UYGUN OLMAYAN ÜRÜN")
                            .setContentTextSize(25)
                            .setContentText("Ürün yapmak istediğiniz işlem için uygun değildir. \r\n İşleme uygun olmayan etiket.")
                            .showCancelButton(false)
                            .show();
                    return;

                }
                else if(tag.etiket_turu.equals("1"))
                {
                    aktif_etiket = tag;
                    _txtEtiket.setText(tag.rfid);
                    _txtDurum.setText("Shrink etiketi okutunuz ...");
                    islemDurumu = 1;




                    //Program.setbildirimMesaji("Shrink etiketi okutunuz.");
                    //tanim_resmi.Image = ımageList1.Images[islemDurumu];
                    //_txtYazi.setText("SHRINK ETİKETİ OKUTUNUZ.");
                }
            }
            else if (islemDurumu == 1){
                if (_txtEtiket.getText().equals(tag.rfid)){
                    if (dataModels.contains(tag)){
                        dataModels.remove(tag);
                    }
                    else{
                        dataModels.add(tag);
                    }

                    updateListviewItem();
                    islemDurumu=0;
                    _txtEtiket.setText("");
                    _txtDurum.setText("Açık palet etiketi okutunuz ...");
                }

            }

//            else if (islemDurumu == 1)
//            {
//                if (tag == null)
//                {
//                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
//                            .setTitleText("BAĞLANTI PROBLEMİ")
//                            .setContentTextSize(25)
//                            .setContentText("ÜRÜN BİLGİSİ ALINAMADI. DAHA SONRA TEKRAR DENEYİNİZ. \r\n Etiket verisine ulaşılamadı.")
//                            .showCancelButton(false)
//                            .show();
//                    return;
//
//                }
//                else if(aktif_etiket.etiket_turu.equals("1") && tag.kod.equals(aktif_etiket.kod))
//                {
//                    //Cursor.Current = Cursors.WaitCursor;
//                    request_shrink_is_emri _Param= new request_shrink_is_emri();
//                    _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
//                    _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
//                    _Param.set_zaktif_tesis(_ayaraktiftesis);
//                    _Param.set_zsurum(_sbtVerisyon);
//                    _Param.set_zkullaniciadi(_zkullaniciadi);
//                    _Param.set_zsifre(_zsifre);
//                    _Param.setAktif_sunucu(_ayaraktifsunucu);
//                    _Param.setAktif_kullanici(_ayaraktifkullanici);
//                    _Param.set_urunlist(tag);
//
//                    //String miktar = persos.fn_sec_ambalaj_degisim_toplam_harcanan_miktar(_Param);
//
//                    Genel.showProgressDialog(getContext());
//                    Boolean result = persos.fn_shrink_onayi_al(_Param);
//                    Genel.dismissProgressDialog();
//
//
//                    //Boolean res = Program.persos.shrink_onayi_al(tag); ;
//                    //Cursor.Current = Cursors.Default;
//
//                    if (result)
//                    {
//                        new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
//                                .setTitleText("Onay Mesaji")
//                                .setContentText("İŞLEM BAŞARIYLA YAPILDI")
//                                .setContentTextSize(20)
//                                .setConfirmText("TAMAM")
//                                .showCancelButton(false)
//                                .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
//                                    @Override
//                                    public void onClick(SweetAlertDialogG sDialog) {
//                                        sDialog.dismissWithAnimation();
//
//                                    }
//                                })
//                                .show();
//
//
//                            aktif_etiket = null;
//                            islemDurumu = 0;
//                            //Program.setbildirimMesaji("Açık palet etiketi okutunuz.");
//                            //tanim_resmi.Image = ımageList1.Images[islemDurumu];
//                           // _txtYazi.setText("AÇIK PALET ETİKETİ OKUTUNUZ.");
//
//
//                    }
//                    else
//                    {
//                        new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
//                            .setTitleText("Uyarı")
//                            .setContentText("İşlem yapılamadı.Bağlantı ayarlarınızı kontrol ettikten sonra tekrar deneyiniz.")
//                            .setContentTextSize(20)
//                            .setConfirmText("TAMAM")
//                            .showCancelButton(false)
//                            .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
//                                @Override
//                                public void onClick(SweetAlertDialogG sDialog) {
//                                    sDialog.dismissWithAnimation();
//
//                                }
//                            })
//                            .show();
//                        //Program.giveUyariMesaji("Uyarı", "İşlem yapılamadı.Bağlantı ayarlarınızı kontrol ettikten sonra tekrar deneyiniz.");
//                    }
//
//                }
//                else
//                {
//                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
//                            .setTitleText("İŞLEM YAPILAMADI")
//                            .setContentTextSize(25)
//                            .setContentText("Ürün eşleme başarız oldu. Etiketleri kontrol ederek tekrar deneyiniz \r\n İşleme uygun olmayan etiket.")
//                            .showCancelButton(false)
//                            .show();
//                    return;
//
//
//                }
//            }

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_uretim_menu_panel fragmentyeni = new frg_uretim_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_uretim_menu_panel").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
    private class fn_btnShrinkEslestir implements View.OnClickListener {
        @Override
        public void onClick(final View view) {
            view.setEnabled(false);
            try
            {
                if (dataModels.size() == 0)
                {
                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                            .setTitleText("HATALI İŞLEM")
                            .setContentTextSize(25)
                            .setContentText("Ürün listenizde ürün bulunmamaktadır. \r\n Hatalı İşlem.")
                            .showCancelButton(false)
                            .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialogG sDialog) {
                                    sDialog.dismissWithAnimation();
                                    view.setEnabled(true);
                                }
                            })
                            .show();

                    return;

                }
                else
                {
                    //Cursor.Current = Cursors.WaitCursor;
                    request_shrink_is_emri _Param= new request_shrink_is_emri();
                    _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
                    _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
                    _Param.set_zaktif_tesis(_ayaraktiftesis);
                    _Param.set_zsurum(_sbtVerisyon);
                    _Param.set_zkullaniciadi(_zkullaniciadi);
                    _Param.set_zsifre(_zsifre);
                    _Param.setAktif_sunucu(_ayaraktifsunucu);
                    _Param.setAktif_kullanici(_ayaraktifkullanici);
                    _Param.set_urunlist(dataModels);

                    //String miktar = persos.fn_sec_ambalaj_degisim_toplam_harcanan_miktar(_Param);

                    Genel.showProgressDialog(getContext());
                    Boolean result = persos.fn_shrink_onayi_al_toplu(_Param);
                    Genel.dismissProgressDialog();


                    //Boolean res = Program.persos.shrink_onayi_al(tag); ;
                    //Cursor.Current = Cursors.Default;

                    if (result)
                    {
                        new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                                .setTitleText("Onay Mesaji")
                                .setContentText("İŞLEM BAŞARIYLA YAPILDI")
                                .setContentTextSize(20)
                                .setConfirmText("TAMAM")
                                .showCancelButton(false)
                                .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialogG sDialog) {
                                        sDialog.dismissWithAnimation();
                                        frg_uretim_menu_panel fragmentyeni = new frg_uretim_menu_panel();
                                        FragmentManager fragmentManager = getFragmentManager();
                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                        fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_uretim_menu_panel").addToBackStack(null);
                                        fragmentTransaction.commit();
                                    }
                                })
                                .show();


                        aktif_etiket = null;
                        islemDurumu = 0;
                        //Program.setbildirimMesaji("Açık palet etiketi okutunuz.");
                        //tanim_resmi.Image = ımageList1.Images[islemDurumu];
                        // _txtYazi.setText("AÇIK PALET ETİKETİ OKUTUNUZ.");


                    }
                    else
                    {
                        new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                                .setTitleText("Uyarı")
                                .setContentText("İşlem yapılamadı.Bağlantı ayarlarınızı kontrol ettikten sonra tekrar deneyiniz.")
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
                        //Program.giveUyariMesaji("Uyarı", "İşlem yapılamadı.Bağlantı ayarlarınızı kontrol ettikten sonra tekrar deneyiniz.");
                    }
                }
            }
            catch (Exception ex)
            {
                Genel.printStackTrace(ex,getContext());
                view.setEnabled(true);
            }


        }
    }
}
