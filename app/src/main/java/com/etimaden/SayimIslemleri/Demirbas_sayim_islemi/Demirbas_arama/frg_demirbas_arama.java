package com.etimaden.SayimIslemleri.Demirbas_sayim_islemi.Demirbas_arama;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.GirisSayfasi;
import com.etimaden.SayimIslemleri.Demirbas_sayim_islemi.frg_demirbas_sayim_menu_panel;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.persos.Persos;
import com.etimaden.persosclass.demirbas_sayim;
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
    TextView _txtUzak;
    TextView _txtYakin;
    Button _btnGeri;
    ProgressBar _progressbar;

    demirbas_sayim secilen_demirbas = null;
    boolean isReadable = true;
    boolean demirbas_bulundu = false;
    int terminal_gücü = 300;
    Long son_okuma = 0L;
    Timer timer = null;
    boolean ilk_okuma_yapildi = false;

    public frg_demirbas_arama() {
    }

    public static frg_demirbas_arama newInstance() {
        return new frg_demirbas_arama();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_demirbas_arama, container, false);
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

        if (_ayarbaglantituru.equals("wifi")) {
            _OnlineUrl = "http://" + _ayarsunucuip + ":" + _zportWifi + "/";
        } else {
            _OnlineUrl = "http:/" + _ipAdresi3G + ":" + _zport3G + "/";
        }
        persos = new Persos(_OnlineUrl, getContext());
    }

    public void fn_senddata(demirbas_sayim secilen_demirbas) {
        this.secilen_demirbas = secilen_demirbas;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        _myIslem = new VeriTabani(getContext());
        _myIslem.fn_EpcTemizle();

        ((GirisSayfasi) getActivity()).fn_ModRFID();

        _txtDemirbasDetay = (TextView) getView().findViewById(R.id.txtDemirbasDetay);
        _txtUzak = (TextView) getView().findViewById(R.id.txtUzak);
        _txtYakin = (TextView) getView().findViewById(R.id.txtYakin);
        _progressbar = (ProgressBar) getView().findViewById(R.id.progressbar);
        _btnGeri = (Button) getView().findViewById(R.id.btnGeri);

        _progressbar.setMax(100);
        _progressbar.setProgress(0);

        update_demirbas_bilgi();

        terminal_gücü = 300;
        ((GirisSayfasi) getActivity()).fn_GucAyarla(terminal_gücü);
        update_yakin_uzak_gosterge();

        _btnGeri.setOnClickListener(new fn_Geri());

        fn_AyarlariYukle();

        baslatTimer();
    }

    private void baslatTimer() {
        if (timer != null) {
            timer.cancel();
        }

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!ilk_okuma_yapildi) {
                    return;
                }

                long gecen_sure = System.currentTimeMillis() - son_okuma;

                if (son_okuma == 0) {
                    return;
                }

                if (gecen_sure < 1500) {
                    if (demirbas_bulundu) {
                        if (terminal_gücü > 50) {
                            terminal_gücü = terminal_gücü - 20;
                            Log.d("TIMER", "✅ DOĞRU! Güç azaldı: " + terminal_gücü);
                        }
                    } else {
                        if (terminal_gücü < 300) {
                            terminal_gücü = terminal_gücü + 20;
                            Log.d("TIMER", " YANLIŞ! Güç arttı: " + terminal_gücü);
                        }
                    }

                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int progress = ((300 - terminal_gücü) * 100) / 300;
                                _progressbar.setProgress(progress);
                                update_yakin_uzak_gosterge();
                            }
                        });

                        ((GirisSayfasi) getActivity()).fn_GucAyarla(terminal_gücü);
                    }
                }
                else if (gecen_sure > 3000) {
                    if (terminal_gücü < 300 && getActivity() != null) {
                        terminal_gücü = Math.min(terminal_gücü + 10, 300);

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int progress = ((300 - terminal_gücü) * 100) / 300;
                                _progressbar.setProgress(progress);
                                update_yakin_uzak_gosterge();
                            }
                        });

                        ((GirisSayfasi) getActivity()).fn_GucAyarla(terminal_gücü);
                    }
                }
            }
        }, 0, 300);
    }

    private void update_demirbas_bilgi() {
        try {
            if (secilen_demirbas != null) {
                String text = "Demirbas SAP Kodu : " + secilen_demirbas.getDs_demirbas_kod() +
                        "\r\n Demirbas Eski Kodu : " + secilen_demirbas.getDs_demirbas_eski_kod() +
                        "\r\n Demirbas Adi : " + secilen_demirbas.getDs_demirbas_ad_1() + "-" + secilen_demirbas.getDs_demirbas_ad_2() + "-" + secilen_demirbas.getDs_demirbas_ad_3() +
                        "\r\n Demirbas Konum : " + secilen_demirbas.getDs_isletme_adi() + "(" + secilen_demirbas.getDs_isletme_kod() + ")" +
                        "\r\n" + secilen_demirbas.getDs_bina_adi() + "(" + secilen_demirbas.getDs_bina_kod() + ")" +
                        "\r\n" + secilen_demirbas.getDs_kat_adi() + "(" + secilen_demirbas.getDs_kat_kod() + ")" +
                        "\r\n" + secilen_demirbas.getDs_oda_adi() + "(" + secilen_demirbas.getDs_oda_kod() + ")";
                _txtDemirbasDetay.setText(text);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void update_yakin_uzak_gosterge() {
        try {
            if (terminal_gücü >= 250) {
                _txtUzak.setTextColor(getResources().getColor(R.color.red1));
                _txtYakin.setTextColor(getResources().getColor(android.R.color.darker_gray));
            } else if (terminal_gücü >= 150) {
                _txtUzak.setTextColor(getResources().getColor(android.R.color.holo_orange_light));
                _txtYakin.setTextColor(getResources().getColor(android.R.color.darker_gray));
            } else if (terminal_gücü >= 100) {
                _txtUzak.setTextColor(getResources().getColor(android.R.color.holo_orange_light));
                _txtYakin.setTextColor(getResources().getColor(android.R.color.holo_orange_light));
            } else if (terminal_gücü >= 60) {
                _txtUzak.setTextColor(getResources().getColor(android.R.color.darker_gray));
                _txtYakin.setTextColor(getResources().getColor(android.R.color.holo_orange_light));
            } else {
                _txtUzak.setTextColor(getResources().getColor(android.R.color.darker_gray));
                _txtYakin.setTextColor(getResources().getColor(R.color.green));
            }
        } catch (Exception ex) {
            Log.e("GOSTERGE_UPDATE", "Hata: " + ex.getMessage());
        }
    }

    public void rfidOkundu(String rfid) {
        try {
            if (!isReadable) {
                return;
            }
            isReadable = false;

            ilk_okuma_yapildi = true;
            son_okuma = System.currentTimeMillis();

            if (secilen_demirbas != null) {
                String sap_kodu = secilen_demirbas.getDs_demirbas_kod();
                String aranan_rfid = "7377675" + sap_kodu;

                if (rfid != null && aranan_rfid != null && rfid.trim().equalsIgnoreCase(aranan_rfid.trim())) {
                    demirbas_bulundu = true;

                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), "Doğru demirbaş!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    demirbas_bulundu = false;
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), " Yanlış demirbaş!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

                _myIslem.fn_EpcTemizle();
            }
        } catch (Exception ex) {
            Log.e("RFID_OKUNDU", "Hata: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            isReadable = true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (timer != null) {
            timer.cancel();
            timer.purge();
        }

        if (getActivity() != null) {
            ((GirisSayfasi) getActivity()).fn_GucAyarla(200);
        }
    }

    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (timer != null) {
                timer.cancel();
                timer.purge();
            }

            ((GirisSayfasi) getActivity()).fn_GucAyarla(200);

            frg_demirbas_sayim_menu_panel fragmentyeni = new frg_demirbas_sayim_menu_panel();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni, "frg_demirbas_sayim_menu_panel").addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}