package com.etimaden.GemiIslemleri;

import static com.etimaden.cSabitDegerler._ipAdresi3G;
import static com.etimaden.cSabitDegerler._sbtVerisyon;
import static com.etimaden.cSabitDegerler._zkullaniciadi;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.GirisSayfasi;
import com.etimaden.adapter.apmblGemiItemYuklemeListesi;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.genel.Genel;
import com.etimaden.genel.SweetAlertDialogG;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.Gemi_Sevkiyat;
import com.etimaden.request.request_gemi;
import com.etimaden.response.gemi_islemleri.View_gemi_response;
import com.etimaden.ugr_demo.R;
import com.etimaden.persosclass.Gemi;
import com.etimaden.persosclass.Gemi_Teslimat;

import java.util.ArrayList;
import java.util.List;

public class frg_aktif_gemi_arac_yukleme extends Fragment {

    private static final String TAG = "GemiAracYukleme";

    boolean isReadable = true;
    boolean yuklemeBitti = false;

    VeriTabani _myIslem;
    Persos persos;
    String _OnlineUrl = "";

    String _ayaraktifkullanici = "";
    String _ayaraktifdepo = "";
    String _ayaraktifalttesis = "";
    String _ayaraktiftesis = "";
    String _ayaraktifsunucu = "";
    String _ayaraktifisletmeeslesme = "";
    String _ayarbaglantituru = "";
    String _ayarsunucuip = "";

    Button _btngemiYuklemeGeri;
    Button _btnGemiOkuma;
    Button _btnYuklemeBaslat;
    Button _btnYuklemeBitir;
    TextView _txtgemiYuklemeBaslik;
    TextView _txtYuklenenArac;
    TextView _txtToplamArac;
    TextView _txtHasarliArac;
    ListView _arac_list;

    String iseSapKod = null;
    Gemi aktif_gemi_bilgi = null;
    Gemi_Teslimat secili_teslimat = null;

    ArrayList<Gemi_Sevkiyat> hafizadakiAracListesi = new ArrayList<>();

    ArrayList<Gemi_Sevkiyat> yuklenenAracListesi = new ArrayList<>();

    private apmblGemiItemYuklemeListesi adapter;

    public void fn_senddata(Gemi gemi, Gemi_Teslimat teslimat) {
        this.aktif_gemi_bilgi = gemi;
        this.secili_teslimat = teslimat;
        this.iseSapKod = teslimat != null ? teslimat.ise_sap_kod : null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_aktif_gemi_arac_yukleme, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((GirisSayfasi) getActivity()).fn_ModBarkod();
        _myIslem = new VeriTabani(getContext());
        fn_AyarlariYukle();
        _myIslem.fn_EpcTemizle();
        ((GirisSayfasi) getActivity()).fn_ListeTemizle();

        initializeViews();
        setupListeners();

        adapter = new apmblGemiItemYuklemeListesi(yuklenenAracListesi, getContext());
        _arac_list.setAdapter(adapter);

        adapter.setOnHasarChangeListener(new apmblGemiItemYuklemeListesi.OnHasarChangeListener() {
            @Override
            public void onHasarChanged(Gemi_Sevkiyat arac, int position, String yeniHasar) {
                hasarGuncelle(arac, position, yeniHasar);
            }
        });


        _btnYuklemeBaslat.setEnabled(true);
        _btnYuklemeBitir.setEnabled(false);
        _btnGemiOkuma.setEnabled(false);

        if (aktif_gemi_bilgi != null && secili_teslimat != null) {
            String baslik = "Gemi: " + aktif_gemi_bilgi.gemiAd +
                    "\nİş Emri: " + secili_teslimat.ise_sap_kod;
            _txtgemiYuklemeBaslik.setText(baslik);
        } else if (iseSapKod != null) {
            String baslik = "İş Emri: " + iseSapKod;
            _txtgemiYuklemeBaslik.setText(baslik);
        }

        updateCounters();
    }

    private void initializeViews() {
        _btngemiYuklemeGeri = getView().findViewById(R.id.btngemiYuklemeGeri);
        _btnGemiOkuma = getView().findViewById(R.id.btnGemiOkuma);
        _btnYuklemeBaslat = getView().findViewById(R.id.button4);
        _btnYuklemeBitir = getView().findViewById(R.id.button5);
        _txtgemiYuklemeBaslik = getView().findViewById(R.id.txtgemiYuklemeBaslik);
        _txtYuklenenArac = getView().findViewById(R.id.txtYuklenenArac);
        _txtToplamArac = getView().findViewById(R.id.txtTamamlananArac);
        _txtHasarliArac = getView().findViewById(R.id.txtHasarliArac);
        _arac_list = getView().findViewById(R.id.gemi_yukleme_list);
    }

    private void setupListeners() {
        // Geri butonu
        _btngemiYuklemeGeri.playSoundEffect(0);
        _btngemiYuklemeGeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                geriDon();
            }
        });

        _btnYuklemeBaslat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yuklemeBaslat();
            }
        });

        _btnYuklemeBitir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yuklemeBitir();
            }
        });

        _btnGemiOkuma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okumaDegistir();
            }
        });

    }

    private void yuklemeBaslat() {
        try {

            request_gemi _Param = new request_gemi();
            _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
            _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
            _Param.set_zaktif_tesis(_ayaraktiftesis);
            _Param.set_zsurum(_sbtVerisyon);
            _Param.set_zkullaniciadi(_zkullaniciadi);
            _Param.set_zsifre(_zsifre);
            _Param.setAktif_sunucu(_ayaraktifsunucu);
            _Param.setAktif_kullanici(_ayaraktifkullanici);
            _Param.setIseSapKod(iseSapKod);

            Genel.showProgressDialog(getContext());
            List<Gemi_Sevkiyat> result = persos.fn_gemi_isemri_araclari(_Param);
            Genel.dismissProgressDialog();

            if (result != null && result.size() > 0) {
                hafizadakiAracListesi.clear();
                hafizadakiAracListesi.addAll(result);

                Log.d(TAG, "Hafızaya yüklenen araç sayısı: " + hafizadakiAracListesi.size());

                _btnYuklemeBaslat.setEnabled(false);
                _btnYuklemeBitir.setEnabled(true);
                _btnGemiOkuma.setEnabled(true);

                updateCounters();

                new SweetAlertDialogG(getContext(), SweetAlertDialogG.SUCCESS_TYPE)
                        .setTitleText("BAŞARILI")
                        .setContentTextSize(20)
                        .setContentText("Yükleme başlatıldı!\n\n" +
                                "Toplam Araç: " + hafizadakiAracListesi.size() +
                                "\n\nAraç etiketlerini okutabilirsiniz.")
                        .showCancelButton(false)
                        .show();

            } else {

                new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                        .setTitleText("UYARI")
                        .setContentTextSize(20)
                        .setContentText("Bu iş emrine ait araç bulunamadı!")
                        .showCancelButton(false)
                        .show();
            }

        } catch (Exception ex) {
            Genel.dismissProgressDialog();
            Genel.printStackTrace(ex, getContext());

            new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                    .setTitleText("HATA")
                    .setContentTextSize(20)
                    .setContentText("Liste getirilemedi: " + ex.getMessage())
                    .showCancelButton(false)
                    .show();
        }
    }

    private void yuklemeBitir() {
        if (yuklenenAracListesi.isEmpty()) {
            new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                    .setTitleText("UYARI")
                    .setContentTextSize(20)
                    .setContentText("Henüz hiçbir araç yüklemediniz!")
                    .showCancelButton(false)
                    .show();
            return;
        }

        new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                .setTitleText("ONAY")
                .setContentTextSize(20)
                .setContentText("Yüklenen " + yuklenenAracListesi.size() +
                        " araç kaydedilecek.\nDevam etmek istiyor musunuz?")
                .setCancelText("HAYIR")
                .setConfirmText("EVET")
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialogG sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();

                        try {
                            Genel.showProgressDialog(getContext());

                            int basarili = 0;
                            int basarisiz = 0;
                            StringBuilder hataMesajlari = new StringBuilder();

                            int toplamAracSayisi = hafizadakiAracListesi.size() + yuklenenAracListesi.size();

                            for (Gemi_Sevkiyat arac : yuklenenAracListesi) {
                                request_gemi v_Gelen = new request_gemi();
                                v_Gelen.setBarkod(arac.ara_barkod);
                                v_Gelen.set_zaktif_alt_tesis(_ayaraktifalttesis);
                                v_Gelen.set_zaktif_tesis(_ayaraktiftesis);
                                v_Gelen.set_zkullaniciadi(_zkullaniciadi);
                                v_Gelen.set_zsifre(_zsifre);
                                v_Gelen.set_zsunucu_ip_adresi(_ayarsunucuip);
                                v_Gelen.set_zsurum(_sbtVerisyon);
                                v_Gelen.setAktif_kullanici(_ayaraktifkullanici);
                                v_Gelen.setAktif_sunucu(_ayaraktifsunucu);
                                v_Gelen.setIseSapKod(iseSapKod);
                                v_Gelen.setGemiSevkiyatId(arac.gemi_sevkiyat_id);
                                v_Gelen.setAraPlaka(arac.ara_plaka);
                                v_Gelen.setSoforAdi(arac.sofor_adi);
                                v_Gelen.setHasarDurumu(arac.hasar_durumu != null ? arac.hasar_durumu : "0");

                                if (aktif_gemi_bilgi != null) {
                                    v_Gelen.setGemi(aktif_gemi_bilgi);
                                }

                                View_gemi_response result = persos.fn_arac_yukleme_kaydet(v_Gelen);

                                if (result != null && result.get_result() != null && result.get_result()) {
                                    basarili++;
                                } else {
                                    basarisiz++;
                                    hataMesajlari.append("\n- ").append(arac.ara_plaka)
                                            .append(": ").append(result != null ? result.get_zHataAciklama() : "Bilinmeyen hata");
                                }
                            }

                            Genel.dismissProgressDialog();

                            int hasarliSayisi = 0;
                            for (Gemi_Sevkiyat arac : yuklenenAracListesi) {
                                if (arac.hasar_durumu != null &&
                                        !arac.hasar_durumu.isEmpty() &&
                                        !arac.hasar_durumu.equals("0")) {
                                    hasarliSayisi++;
                                }
                            }

                            int yuklenmeyen = toplamAracSayisi - basarili;

                            yuklemeBitti = true;
                            _btnYuklemeBitir.setEnabled(false);
                            _btnGemiOkuma.setEnabled(false);

                            String ozetMesaj = "KAYIT TAMAMLANDI!\n\n" +
                                    "Başarılı: " + basarili + "\n" +
                                    "Başarısız: " + basarisiz + "\n\n" +
                                    "Toplam: " + toplamAracSayisi + " | " +
                                    "Yüklenen: " + basarili + " | " +
                                    "Yüklenmeyen: " + yuklenmeyen + " | " +
                                    "Hasarlı: " + hasarliSayisi;

                            if (basarisiz > 0) {
                                ozetMesaj += "\n\nHatalar:" + hataMesajlari.toString();
                            }

                            new SweetAlertDialogG(getContext(),
                                    basarisiz == 0 ? SweetAlertDialogG.SUCCESS_TYPE : SweetAlertDialogG.WARNING_TYPE)
                                    .setTitleText(basarisiz == 0 ? "BAŞARILI" : "UYARI")
                                    .setContentTextSize(20)
                                    .setContentText(ozetMesaj)
                                    .showCancelButton(false)
                                    .show();

                        } catch (Exception ex) {
                            Genel.dismissProgressDialog();
                            new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                                    .setTitleText("HATA")
                                    .setContentTextSize(20)
                                    .setContentText("Hata: " + ex.getMessage())
                                    .showCancelButton(false)
                                    .show();
                        }
                    }
                })
                .show();
    }

    private void okumaDegistir() {
        Genel.showProgressDialog(getContext());
        if (_btnGemiOkuma.getText().toString().equals("KAREKOD")) {
            ((GirisSayfasi) getActivity()).fn_ModRFID();
            _btnGemiOkuma.setText("RFID");

            new SweetAlertDialogG(getContext(), SweetAlertDialogG.NORMAL_TYPE)
                    .setTitleText("BİLGİ")
                    .setContentTextSize(20)
                    .setContentText("RFID okuma moduna geçildi.\nAraç RFID etiketini okutunuz.")
                    .showCancelButton(false)
                    .show();
        } else {
            ((GirisSayfasi) getActivity()).fn_ModBarkod();
            _btnGemiOkuma.setText("KAREKOD");

            new SweetAlertDialogG(getContext(), SweetAlertDialogG.NORMAL_TYPE)
                    .setTitleText("BİLGİ")
                    .setContentTextSize(20)
                    .setContentText("Karekod okuma moduna geçildi.\nAraç barkodunu okutunuz.")
                    .showCancelButton(false)
                    .show();
        }
        Genel.dismissProgressDialog();
    }

    private void hasarGuncelle(final Gemi_Sevkiyat item, final int position, final String hasarDurumu) {

        if (yuklemeBitti) {
            Genel.playQuestionSound(getContext());
            new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                    .setTitleText("UYARI")
                    .setContentTextSize(20)
                    .setContentText("Yükleme tamamlandı!\nHasar durumu değiştirilemez.")
                    .showCancelButton(false)
                    .show();
            adapter.notifyDataSetChanged();
            return;
        }

        if (hasarDurumu == null || hasarDurumu.isEmpty() || hasarDurumu.equals("0")) {
            adapter.notifyDataSetChanged();
            return;
        }

        String mesaj = "Bu aracı \"" + hasarDurumu + "\" olarak işaretlemek istiyor musunuz?";

        new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                .setTitleText("ONAY")
                .setContentTextSize(20)
                .setContentText(mesaj)
                .setCancelText("HAYIR")
                .setConfirmText("EVET")
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialogG sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();

                        request_gemi _Param = new request_gemi();
                        _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
                        _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
                        _Param.set_zaktif_tesis(_ayaraktiftesis);
                        _Param.set_zsurum(_sbtVerisyon);
                        _Param.set_zkullaniciadi(_zkullaniciadi);
                        _Param.set_zsifre(_zsifre);
                        _Param.setAktif_sunucu(_ayaraktifsunucu);
                        _Param.setAktif_kullanici(_ayaraktifkullanici);
                        _Param.setGemiSevkiyatId(item.gemi_sevkiyat_id);
                        _Param.setHasarDurumu(hasarDurumu);

                        Genel.showProgressDialog(getContext());
                        View_gemi_response result = persos.fn_arac_hasar_guncelle(_Param);
                        Genel.dismissProgressDialog();

                        if (result != null && result.get_result() != null && result.get_result()) {
                            item.hasar_durumu = hasarDurumu;
                            yuklenenAracListesi.set(position, item);
                            adapter.notifyDataSetChanged();
                            updateCounters();

                            Genel.playQuestionSound(getContext());

                            String basariMesaj = "Hasar durumu güncellendi: " + hasarDurumu;

                            new SweetAlertDialogG(getContext(), SweetAlertDialogG.SUCCESS_TYPE)
                                    .setTitleText("BAŞARILI")
                                    .setContentTextSize(20)
                                    .setContentText(basariMesaj)
                                    .showCancelButton(false)
                                    .show();
                        } else {
                            adapter.notifyDataSetChanged();

                            Genel.playQuestionSound(getContext());
                            new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                                    .setTitleText("HATA")
                                    .setContentTextSize(20)
                                    .setContentText(result != null ? result.get_zHataAciklama() : "Güncelleme başarısız")
                                    .showCancelButton(false)
                                    .show();
                        }
                    }
                })
                .setCancelClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialogG sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        adapter.notifyDataSetChanged();
                    }
                })
                .show();
    }

    private void updateCounters() {
        int toplamArac = hafizadakiAracListesi.size();
        int yuklenenSayisi = yuklenenAracListesi.size();
        int hasarliSayisi = 0;

        for (Gemi_Sevkiyat arac : yuklenenAracListesi) {
            if (arac.hasar_durumu != null &&
                    !arac.hasar_durumu.isEmpty() &&
                    !arac.hasar_durumu.equals("0")) {
                hasarliSayisi++;
            }
        }

        _txtToplamArac.setText("Kalan: " + toplamArac);
        _txtYuklenenArac.setText("Yüklenen: " + yuklenenSayisi);
        _txtHasarliArac.setText("Hasarlı: " + hasarliSayisi);
    }

    public void rfidOkundu(String rfid) {
        try {
            if (yuklemeBitti) {
                Log.e(TAG, "Yükleme zaten bitti!");
                Genel.playQuestionSound(getContext());
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(20)
                        .setContentText("Yükleme zaten tamamlandı!")
                        .showCancelButton(false)
                        .show();
                return;
            }

            if (hafizadakiAracListesi.isEmpty()) {
                Genel.playQuestionSound(getContext());
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                        .setTitleText("UYARI")
                        .setContentTextSize(20)
                        .setContentText("Lütfen önce 'Yüklemeyi Başlat' butonuna basınız!")
                        .showCancelButton(false)
                        .show();
                return;
            }

            if (!isReadable) {
                Log.w(TAG, "isReadable = false, okuma yapılmadı");
                return;
            }

            isReadable = false;

            for (Gemi_Sevkiyat arac : yuklenenAracListesi) {
                boolean barkodEsit = arac.ara_barkod != null && arac.ara_barkod.equals(rfid);
                boolean plakaEsit = arac.ara_plaka != null && arac.ara_plaka.equals(rfid);

                if (barkodEsit || plakaEsit) {
                    Genel.playQuestionSound(getContext());
                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                            .setTitleText("UYARI")
                            .setContentTextSize(20)
                            .setContentText("Bu araç zaten yüklendi!\n\nPlaka: " + arac.ara_plaka +
                                    "\nŞoför: " + arac.sofor_adi)
                            .showCancelButton(false)
                            .show();
                    isReadable = true;
                    return;
                }
            }

            Gemi_Sevkiyat bulunanArac = null;
            int bulunanIndex = -1;

            for (int i = 0; i < hafizadakiAracListesi.size(); i++) {
                Gemi_Sevkiyat arac = hafizadakiAracListesi.get(i);

                boolean barkodEsit = arac.ara_barkod != null && arac.ara_barkod.equals(rfid);
                boolean plakaEsit = arac.ara_plaka != null && arac.ara_plaka.equals(rfid);

                if (barkodEsit || plakaEsit) {
                    bulunanArac = arac;
                    bulunanIndex = i;
                    break;
                }
            }

            if (bulunanArac == null) {
                Genel.playQuestionSound(getContext());
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(20)
                        .setContentText("Bu araç bu iş emrine ait değil!\n\nOkutulan: " + rfid)
                        .showCancelButton(false)
                        .show();
                isReadable = true;
                return;
            }

            final Gemi_Sevkiyat finalBulunanArac = bulunanArac;
            final int finalBulunanIndex = bulunanIndex;

            request_gemi _Param = new request_gemi();
            _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
            _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
            _Param.set_zaktif_tesis(_ayaraktiftesis);
            _Param.set_zsurum(_sbtVerisyon);
            _Param.set_zkullaniciadi(_zkullaniciadi);
            _Param.set_zsifre(_zsifre);
            _Param.setAktif_sunucu(_ayaraktifsunucu);
            _Param.setAktif_kullanici(_ayaraktifkullanici);
            _Param.setIseSapKod(iseSapKod);
            _Param.setBarkod(rfid);

            Genel.showProgressDialog(getContext());
            View_gemi_response kontrolSonuc = persos.fn_arac_10dk_kontrol(_Param);
            Genel.dismissProgressDialog();

            if (kontrolSonuc != null && kontrolSonuc.get_result() != null && !kontrolSonuc.get_result()) {
                Genel.playQuestionSound(getContext());
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                        .setTitleText("UYARI")
                        .setContentTextSize(20)
                        .setContentText(kontrolSonuc.get_zHataAciklama())
                        .showCancelButton(false)
                        .show();
                isReadable = true;
                return;
            }

            Genel.playQuestionSound(getContext());

            hafizadakiAracListesi.remove(finalBulunanIndex);
            finalBulunanArac.yukleme_durumu = "1";
            finalBulunanArac.hasar_durumu = "0";
            yuklenenAracListesi.add(0, finalBulunanArac);

            adapter.notifyDataSetChanged();
            updateCounters();

            Log.d(TAG, "Araç listeye eklendi (DB'ye kaydedilmedi): " + finalBulunanArac.ara_plaka);

            new SweetAlertDialogG(getContext(), SweetAlertDialogG.SUCCESS_TYPE)
                    .setTitleText("BAŞARILI")
                    .setContentTextSize(20)
                    .setContentText("Araç eklendi: " + finalBulunanArac.ara_plaka +
                            "\nŞoför: " + finalBulunanArac.sofor_adi +
                            "\n\nOkutma işlemleriniz bittiğinde 'Yüklemeyi Bitir' butonuna basarak kaydetmeyi unutmayınız.")
                    .showCancelButton(false)
                    .show();

        } catch (Exception ex) {
            Genel.printStackTrace(ex, getContext());

            Genel.playQuestionSound(getContext());
            new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                    .setTitleText("HATA")
                    .setContentTextSize(20)
                    .setContentText("Bir hata oluştu: " + ex.getMessage())
                    .showCancelButton(false)
                    .show();
        } finally {
            isReadable = true;
        }
    }

    public void barkodOkundu(String barkod) {
        try {
            if (yuklemeBitti) {
                Genel.playQuestionSound(getContext());
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(20)
                        .setContentText("Yükleme zaten tamamlandı!")
                        .showCancelButton(false)
                        .show();
                return;
            }

            if (hafizadakiAracListesi.isEmpty()) {
                Genel.playQuestionSound(getContext());
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                        .setTitleText("UYARI")
                        .setContentTextSize(20)
                        .setContentText("Lütfen önce 'Yükleme Başlat' butonuna basınız!")
                        .showCancelButton(false)
                        .show();
                return;
            }

            if (!isReadable) {
                return;
            }

            isReadable = false;

            if (barkod != null && barkod.length() > 24) {
                barkod = barkod.substring(barkod.length() - 24);
            }

            Log.d(TAG, "Barkod okutuldu: " + barkod);

            for (Gemi_Sevkiyat arac : yuklenenAracListesi) {
                boolean barkodEsit = arac.ara_barkod != null && arac.ara_barkod.equals(barkod);
                boolean plakaEsit = arac.ara_plaka != null && arac.ara_plaka.equals(barkod);

                if (barkodEsit || plakaEsit) {
                    Genel.playQuestionSound(getContext());
                    new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                            .setTitleText("UYARI")
                            .setContentTextSize(20)
                            .setContentText("Bu araç zaten yüklendi!\n\nPlaka: " + arac.ara_plaka +
                                    "\nŞoför: " + arac.sofor_adi)
                            .showCancelButton(false)
                            .show();
                    isReadable = true;
                    return;
                }
            }

            Gemi_Sevkiyat bulunanArac = null;
            int bulunanIndex = -1;

            for (int i = 0; i < hafizadakiAracListesi.size(); i++) {
                Gemi_Sevkiyat arac = hafizadakiAracListesi.get(i);
                boolean barkodEsit = arac.ara_barkod != null && arac.ara_barkod.equals(barkod);
                boolean plakaEsit = arac.ara_plaka != null && arac.ara_plaka.equals(barkod);

                if (barkodEsit || plakaEsit) {
                    bulunanArac = arac;
                    bulunanIndex = i;
                    break;
                }
            }

            if (bulunanArac == null) {
                Genel.playQuestionSound(getContext());
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(20)
                        .setContentText("Bu araç bu iş emrine ait değil!\n\nOkutulan: " + barkod)
                        .showCancelButton(false)
                        .show();
                isReadable = true;
                return;
            }

            final Gemi_Sevkiyat finalBulunanArac = bulunanArac;
            final int finalBulunanIndex = bulunanIndex;

            request_gemi _Param = new request_gemi();
            _Param.set_zsunucu_ip_adresi(_ayarsunucuip);
            _Param.set_zaktif_alt_tesis(_ayaraktifalttesis);
            _Param.set_zaktif_tesis(_ayaraktiftesis);
            _Param.set_zsurum(_sbtVerisyon);
            _Param.set_zkullaniciadi(_zkullaniciadi);
            _Param.set_zsifre(_zsifre);
            _Param.setAktif_sunucu(_ayaraktifsunucu);
            _Param.setAktif_kullanici(_ayaraktifkullanici);
            _Param.setIseSapKod(iseSapKod);
            _Param.setBarkod(barkod);

            Genel.showProgressDialog(getContext());
            View_gemi_response kontrolSonuc = persos.fn_arac_10dk_kontrol(_Param);
            Genel.dismissProgressDialog();

            if (kontrolSonuc != null && kontrolSonuc.get_result() != null && !kontrolSonuc.get_result()) {
                Genel.playQuestionSound(getContext());
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.WARNING_TYPE)
                        .setTitleText("UYARI")
                        .setContentTextSize(20)
                        .setContentText(kontrolSonuc.get_zHataAciklama())
                        .showCancelButton(false)
                        .show();
                isReadable = true;
                return;
            }

            Genel.playQuestionSound(getContext());

            hafizadakiAracListesi.remove(finalBulunanIndex);
            finalBulunanArac.yukleme_durumu = "1";
            finalBulunanArac.hasar_durumu = "0";
            yuklenenAracListesi.add(0, finalBulunanArac);

            adapter.notifyDataSetChanged();
            updateCounters();

            Log.d(TAG, "Araç listeye eklendi (DB'ye kaydedilmedi): " + finalBulunanArac.ara_plaka);

            new SweetAlertDialogG(getContext(), SweetAlertDialogG.SUCCESS_TYPE)
                    .setTitleText("BAŞARILI")
                    .setContentTextSize(20)
                    .setContentText("Araç eklendi: " + finalBulunanArac.ara_plaka +
                            "\nŞoför: " + finalBulunanArac.sofor_adi +
                            "\n\nOkutma işlemleriniz bittiğinde 'Yüklemeyi Bitir' butonuna basarak kaydetmeyi unutmayınız.")
                    .showCancelButton(false)
                    .show();

        } catch (Exception ex) {
            Genel.printStackTrace(ex, getContext());
            Log.e(TAG, "Barkod okuma hatası", ex);

            Genel.playQuestionSound(getContext());
            new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                    .setTitleText("HATA")
                    .setContentTextSize(20)
                    .setContentText("Bir hata oluştu: " + ex.getMessage())
                    .showCancelButton(false)
                    .show();
        } finally {
            isReadable = true;
        }
    }
    public void fn_BarkodOkutuldu(String barkod) {
        barkodOkundu(barkod);
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

        if (_ayarbaglantituru.equals("wifi")) {
            _OnlineUrl = "http://" + _ayarsunucuip + ":" + _zportWifi + "/";
        } else {
            _OnlineUrl = "http://" + _ipAdresi3G + ":" + _zport3G + "/";
        }
        persos = new Persos(_OnlineUrl, getContext());
    }

    private void geriDon() {
        if (aktif_gemi_bilgi != null) {
            frg_gemi_sevkiyat_basla fragmentyeni = new frg_gemi_sevkiyat_basla();
            fragmentyeni.fn_senddata(aktif_gemi_bilgi);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_gemi_sevkiyat_basla")
                    .addToBackStack(null);
            fragmentTransaction.commit();
        } else {
            getFragmentManager().popBackStack();
        }
    }
}