package com.etimaden.SayimIslemleri.Demirbas_sayim_islemi.Demirbas_sorgulama;

import static com.etimaden.cSabitDegerler._ipAdresi3G;
import static com.etimaden.cSabitDegerler._sbtVerisyon;
import static com.etimaden.cSabitDegerler._zkullaniciadi;
import static com.etimaden.cSabitDegerler._zport3G;
import static com.etimaden.cSabitDegerler._zportWifi;
import static com.etimaden.cSabitDegerler._zsifre;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.GirisSayfasi;
import com.etimaden.SayimIslemleri.Demirbas_sayim_islemi.frg_demirbas_sayim_menu_panel;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.genel.Genel;
import com.etimaden.genel.SweetAlertDialogG;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.Duran_Varlik_sap;
import com.etimaden.request.request_string;
import com.etimaden.ugr_demo.R;

public class frg_demirbas_sorgula extends Fragment {

    private static final String TAG = "frg_demirbas_sorgula";

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

    Button _btnGeri;
    Button _btnOkuma;
    TextView _txtYazi;

    boolean isReadable = true;

    public frg_demirbas_sorgula() {
    }

    public static frg_demirbas_sorgula newInstance() {
        return new frg_demirbas_sorgula();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_demirbas_sorgula, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    }

    private void fn_AyarlariYukle() {
        try {
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
                _OnlineUrl = "http:/" + _ipAdresi3G + ":" + _zport3G + "/";
            }

            persos = new Persos(_OnlineUrl, getContext());
        } catch (Exception ex) {
            Genel.printStackTrace(ex, getContext());
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            new VeriTabani(getContext()).fn_EpcTemizle();
            ((GirisSayfasi) getActivity()).fn_GucAyarla(180);
            _myIslem = new VeriTabani(getContext());

            _txtYazi = (TextView) getView().findViewById(R.id.txtYazi);

            _btnGeri = (Button) getView().findViewById(R.id.btnGeri);
            _btnGeri.playSoundEffect(SoundEffectConstants.CLICK);
            _btnGeri.setOnClickListener(new fn_Geri());

            _btnOkuma = (Button) getView().findViewById(R.id.btnOkuma);
            _btnOkuma.playSoundEffect(0);
            _btnOkuma.setOnClickListener(new fn_okumaDegistir());

            fn_AyarlariYukle();

            // ⭐ Kayıtlı modu yükle
            android.content.SharedPreferences prefs = getContext().getSharedPreferences("okuma_modu", android.content.Context.MODE_PRIVATE);
            String kayitliMod = prefs.getString("mod", "RFID");

            if(kayitliMod.equals("RFID")){
                ((GirisSayfasi) getActivity()).fn_ModRFID();
                _btnOkuma.setText("RFID");
                _txtYazi.setText("RFID ETİKETİ OKUTUNUZ...");
            } else {
                ((GirisSayfasi) getActivity()).fn_ModBarkod();
                _btnOkuma.setText("KAREKOD");
                _txtYazi.setText("KAREKOD OKUTUNUZ...");
            }

            isReadable = true;
        } catch (Exception ex) {
            Genel.printStackTrace(ex, getContext());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            if (getActivity() != null) {
                android.content.SharedPreferences prefs = getContext().getSharedPreferences("okuma_modu", android.content.Context.MODE_PRIVATE);
                String kayitliMod = prefs.getString("mod", "RFID");

                if(kayitliMod.equals("RFID")){
                    ((GirisSayfasi) getActivity()).fn_ModRFID();
                } else {
                    ((GirisSayfasi) getActivity()).fn_ModBarkod();
                }
            }
            isReadable = true;
        } catch (Exception ex) {
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        isReadable = false;
    }

    public void barkodOkundu(String barkod) {
        try {
            if (barkod == null || barkod.length() < 24) {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Etiket formatı hatalı!\r\nLütfen uygun bir demirbaş etiketi okutunuz.")
                        .showCancelButton(false)
                        .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialogG sDialog) {
                                sDialog.dismissWithAnimation();
                                isReadable = true;
                            }
                        })
                        .show();
                return;
            }

            barkod = barkod.substring(barkod.length() - 24);

            if (!barkod.startsWith("7377675")) {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Etiket formatı hatalı!\r\nLütfen uygun bir demirbaş etiketi okutunuz.")
                        .showCancelButton(false)
                        .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialogG sDialog) {
                                sDialog.dismissWithAnimation();
                                isReadable = true;
                            }
                        })
                        .show();
                return;
            }

            if (!isReadable) {
                return;
            }
            isReadable = false;

            final String demirbas_kod = barkod.substring(barkod.length() - 12);
            sorgula_demirbas(demirbas_kod);

        } catch (Exception ex) {
            Genel.printStackTrace(ex, getContext());
            new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                    .setTitleText("HATA")
                    .setContentTextSize(25)
                    .setContentText("Etiket formatı hatalı!\r\nLütfen uygun bir demirbaş etiketi okutunuz.")
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

    public void rfidOkundu(String rfid) {
        try {
            if (rfid == null || rfid.length() < 24) {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Etiket formatı hatalı!\r\nLütfen uygun bir demirbaş etiketi okutunuz.")
                        .showCancelButton(false)
                        .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialogG sDialog) {
                                sDialog.dismissWithAnimation();
                                isReadable = true;
                            }
                        })
                        .show();
                return;
            }

            rfid = rfid.substring(rfid.length() - 24);

            if (!rfid.startsWith("7377675")) {
                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("HATA")
                        .setContentTextSize(25)
                        .setContentText("Etiket formatı hatalı!\r\nLütfen uygun bir demirbaş etiketi okutunuz.")
                        .showCancelButton(false)
                        .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialogG sDialog) {
                                sDialog.dismissWithAnimation();
                                isReadable = true;
                            }
                        })
                        .show();
                return;
            }

            if (!isReadable) {
                return;
            }
            isReadable = false;

            final String demirbas_kod = rfid.substring(rfid.length() - 12);
            sorgula_demirbas(demirbas_kod);

        } catch (Exception ex) {
            Genel.printStackTrace(ex, getContext());
            new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                    .setTitleText("HATA")
                    .setContentTextSize(25)
                    .setContentText("Etiket formatı hatalı!\r\nLütfen uygun bir demirbaş etiketi okutunuz.")
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

    private void sorgula_demirbas(final String id) {
        try {

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

            if (db_detay != null) {
                Log.d(TAG, "   - islem_sonucu: " + db_detay.islem_sonucu);
            }

            if (db_detay != null && db_detay.islem_sonucu != null && db_detay.islem_sonucu.equals("0")) {
                String str = "DEMİRBAŞ NO: " + db_detay.duran_varlik_no +
                        "\n\nESKİ DEMİRBAŞ NO: " + db_detay.eski_demirbas_kod +
                        "\n\nTANIM: " + db_detay.isim_1 +
                        "\n\nSAP KODU: " + db_detay.masraf_yeri +
                        "\n\nTESİS: " + db_detay.daire_baskanligi +
                        "\n\nBİNA: " + db_detay.bina +
                        "\n\nKAT: " + db_detay.kat +
                        "\n\nODA: " + db_detay.oda +
                        "\n\nPERSONEL: " + db_detay.personel_adi + " " + db_detay.personel_soyadi +
                        "\n\nPERSONEL NO: " + db_detay.personel_numarasi;

                new SweetAlertDialogG(getContext(), SweetAlertDialogG.NORMAL_TYPE)
                        .setTitleText("DEMİRBAŞ BİLGİLERİ")
                        .setContentText(str)
                        .setContentTextSize(16)
                        .setConfirmText("TAMAM")
                        .showCancelButton(false)
                        .setConfirmClickListener(new SweetAlertDialogG.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialogG sDialog) {
                                sDialog.dismissWithAnimation();
                                isReadable = true;
                            }
                        })
                        .show();
            } else {
                String hataMesaji = "Demirbaş bulunamadı.\r\nDV_NO: " + id;

                if (db_detay != null && db_detay.islem_sonucu != null) {
                    hataMesaji += "\r\n\r\nAPI Sonucu: " + db_detay.islem_sonucu;
                }

                if (db_detay == null) {
                    hataMesaji += "\r\n\r\nSunucu yanıt vermedi!";
                }

                new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                        .setTitleText("DEMİRBAŞ BULUNAMADI")
                        .setContentTextSize(20)
                        .setContentText(hataMesaji)
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

        } catch (Exception ex) {
            ex.printStackTrace();
            Genel.printStackTrace(ex, getContext());
            Genel.dismissProgressDialog();

            new SweetAlertDialogG(getContext(), SweetAlertDialogG.ERROR_TYPE)
                    .setTitleText("HATA")
                    .setContentTextSize(25)
                    .setContentText("Sorgulama hatası:\r\n\r\n" + ex.getMessage())
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

    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            frg_demirbas_sayim_menu_panel fragmentyeni = new frg_demirbas_sayim_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_demirbas_sayim_menu_panel").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private class fn_okumaDegistir implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Genel.showProgressDialog(getContext());

            android.content.SharedPreferences prefs = getContext().getSharedPreferences("okuma_modu", android.content.Context.MODE_PRIVATE);
            android.content.SharedPreferences.Editor editor = prefs.edit();

            if(_btnOkuma.getText().toString().equals("KAREKOD")){
                ((GirisSayfasi) getActivity()).fn_ModRFID();
                _btnOkuma.setText("RFID");
                _txtYazi.setText("RFID ETİKETİ OKUTUNUZ...");
                editor.putString("mod", "RFID");
                editor.apply();
            }else{
                ((GirisSayfasi) getActivity()).fn_ModBarkod();
                _btnOkuma.setText("KAREKOD");
                _txtYazi.setText("KAREKOD OKUTUNUZ...");
                editor.putString("mod", "BARKOD");
                editor.apply();
            }

            Genel.dismissProgressDialog();
        }
    }
}