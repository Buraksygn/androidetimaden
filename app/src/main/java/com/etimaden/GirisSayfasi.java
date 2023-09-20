package com.etimaden;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.etimaden.SevkiyatIslemleri.Arac_aktivayon_islemleri.frg_arac_aktivasyon;
import com.etimaden.SevkiyatIslemleri.Arac_aktivayon_islemleri.frg_konteyner_kamyon_esleme;
import com.etimaden.SevkiyatIslemleri.Arac_aktivayon_islemleri.frg_konteyner_vagon_esleme;
import com.etimaden.SevkiyatIslemleri.Silobas_islemleri.frg_aktif_silobas_arac_secimi;
import com.etimaden.SevkiyatIslemleri.Zayiat_islemleri.Zayi_depo_kabul.frg_zayi_isemri_indirme;
import com.etimaden.SevkiyatIslemleri.Zayiat_islemleri.frg_zayi_aktivasyon;
import com.etimaden.SevkiyatIslemleri.frg_aktif_arac_secimi;
import com.etimaden.SevkiyatIslemleri.frg_aktif_isemri_indirme;
import com.etimaden.SevkiyatIslemleri.frg_aktif_isemri_yukleme;
import com.etimaden.SevkiyatIslemleri.frg_arac_aktivasyon_eski;
import com.etimaden.SevkiyatIslemleri.frg_konteyner_aktivasyon;
import com.etimaden.SevkiyatIslemleri.frg_konteyner_yukleme_aktivasyon;
import com.etimaden.SevkiyatIslemleri.frg_satilmis_etiket;
import com.etimaden.UretimIslemleri.Paket_uretim.frg_shrink_ayirma;
import com.etimaden.UretimIslemleri.Paket_uretim.frg_shrink_onay;
import com.etimaden.UretimIslemleri.Paket_uretim.frg_uretim_iptal;
import com.etimaden.UretimIslemleri.Paket_uretim.frg_uretim_zayi;
import com.etimaden.UretimIslemleri.frg_ambalaj_tipi_degisimi;
import com.etimaden.UretimIslemleri.frg_paket_uretim_ekrani;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.digerislemler.frg_sifre_degistir;
import com.etimaden.manipulasyon.frg_geribesleme_onay;
import com.etimaden.ugr_demo.AccessActivity;
import com.etimaden.ugr_demo.ConfigPreferenceActivity;
import com.etimaden.ugr_demo.LockActivity;
import com.etimaden.ugr_demo.R;
import com.etimaden.ugr_demo.UGRApplication;
import com.etimaden.ugr_demo.UhfTag;

import net.m3mobile.ugremul.IUGRTestService;
import net.m3mobile.ugremul.IUHFServiceCallback;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class GirisSayfasi extends AppCompatActivity {

    VeriTabani _myIslem;
    String _ayaraktifkullanici = "";
    String _ayarKullaniciAdi = "";
    String _ayaraktifdepo = "";
    String _ayaraktifalttesis = "";
    String _ayaraktiftesis = "";
    String _ayaraktifsunucu = "";
    String _ayaraktifisletmeeslesme = "";
    String _ayarbaglantituru = "";
    String _ayarsunucuip = "";
    String _ayarversiyon = "";
    TextView _txtSetKullanici, _txtVersiyon;

    private final String TAG = "GirisSayfasi";

    boolean mIsReading = false;

    private ArrayList<HashMap<String, UhfTag>> mTAGs;

    private IUGRTestService m_remoteSvc = null;

    private ResultWindowReceiver resultReceiver;
    private BarcodeReceiver mCodeReceiver;
    private IntentFilter mBarcodeFilter;
    RadioGroup triggerGroup;
    int mLastTriggerMode = 0;

    static public boolean bNeedConnect = false;

    private ArrayList<UhfTag> tagArrayList;

    private IUHFServiceCallback m_remoteCallback = null;
    private ServiceConnection m_UHFSvcConnection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.giris_sayfasi);

        _myIslem = new VeriTabani(GirisSayfasi.this);

        _txtSetKullanici = (TextView) findViewById(R.id.txtkullaniciadi);
        _txtVersiyon = (TextView) findViewById(R.id.txtversiyon);

       fn_KlavyeKapat();

        fn_AyarlariYukle();

        mainScreen();

        resultReceiver = new ResultWindowReceiver();

        IntentFilter filter = new IntentFilter();
        filter.addAction(UGRApplication.SCANNER_KEY_INTENT);

        mCodeReceiver = new BarcodeReceiver();
        mBarcodeFilter = new IntentFilter();
        mBarcodeFilter.addAction(UGRApplication.SCANNER_ACTION_BARCODE);

        registerReceiver(resultReceiver, filter);

        registerReceiver(mCodeReceiver, mBarcodeFilter);

        fn_FragmentAyarla();

        //RFIDEnable(true);
        if (m_UHFSvcConnection == null) {
            m_UHFSvcConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    m_remoteSvc = IUGRTestService.Stub.asInterface(service);

                    Log.d(GirisSayfasi.class.getSimpleName(), "Service is Connected");
                    try {
                        if (m_remoteSvc.registerUHFServiceCallback(m_remoteCallback))
                            Log.d(GirisSayfasi.class.getSimpleName(), "Callback was registered");
                        else
                            Log.d(GirisSayfasi.class.getSimpleName(), "Registering Callback was failed");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                    RFIDEnable(true);

                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    m_remoteSvc = null;
                    Log.d(GirisSayfasi.class.getSimpleName(), "Service is Disconnected");
                }
            };
        }

        m_remoteCallback = new IUHFServiceCallback.Stub() {
            @Override
            public void onInventory(String epc) throws RemoteException {
                UIHandler.sendMessage(UIHandler.obtainMessage(UGRApplication.MSG_HANDLE_DATA, epc));
            }

            @Override
            public void onIsReading(boolean isReading) throws RemoteException {
                mIsReading = isReading;
                UIHandler.sendMessage(UIHandler.obtainMessage(UGRApplication.MSG_IS_READING));
            }
        };

        Intent intent = new Intent("net.m3mobile.ugremul.start");
        intent.setPackage("net.m3mobile.ugremul");
        bindService(intent, m_UHFSvcConnection, Context.BIND_AUTO_CREATE);

        intent = new Intent(UGRApplication.UGR_ACTION_SETTING_CHANGE);
        intent.putExtra("setting", "intent_enable");
        intent.putExtra("intent_enable_value", false);
        sendOrderedBroadcast(intent, null);

        // RFID Okuma modülünü tek tek okuma yap
        Intent intent_Once = new Intent(UGRApplication.UGR_ACTION_SETTING_CHANGE);
        intent_Once.putExtra("setting", "read_mode");
        intent_Once.putExtra("read_mode_value", 2);
        sendOrderedBroadcast(intent_Once, null);

        // RFID gücünü 248 yap
        Intent intent_rfid_power = new Intent(UGRApplication.UGR_ACTION_SETTING_CHANGE);
        intent_rfid_power.putExtra("setting", "power");
        intent_rfid_power.putExtra("power_value", 248);
        sendOrderedBroadcast(intent_rfid_power, null);


        // Sadece RFID okumaya ayarla
        Intent triggerIntent = new Intent(UGRApplication.UGR_ACTION_SETTING_CHANGE);
        triggerIntent.putExtra("setting", "trigger_mode");

        triggerIntent.putExtra("trigger_mode_value", 0);
        mLastTriggerMode = 0;

        sendBroadcast(triggerIntent, null);

    }


    public  void fn_ModRFID()
    {
        // Sadece RFID okumaya ayarla
        Intent triggerIntent = new Intent(UGRApplication.UGR_ACTION_SETTING_CHANGE);
        triggerIntent.putExtra("setting", "trigger_mode");


        triggerIntent.putExtra("trigger_mode_value", 0);
        mLastTriggerMode = 0;

        sendBroadcast(triggerIntent, null);

    }


    public  void fn_ModBarkod()
    {
        // Sadece RFID okumaya ayarla
        Intent triggerIntent = new Intent(UGRApplication.UGR_ACTION_SETTING_CHANGE);
        triggerIntent.putExtra("setting", "trigger_mode");


        triggerIntent.putExtra("trigger_mode_value", 1);
        mLastTriggerMode = 1;

        sendBroadcast(triggerIntent, null);

    }

    public  void fn_ModBoth()
    {
        // Sadece RFID okumaya ayarla
        Intent triggerIntent = new Intent(UGRApplication.UGR_ACTION_SETTING_CHANGE);
        triggerIntent.putExtra("setting", "trigger_mode");


        triggerIntent.putExtra("trigger_mode_value", 2);
        mLastTriggerMode = 2;

        sendBroadcast(triggerIntent, null);

    }

    public void fn_GucAyarla(int v_value)
    {
        Intent intent_rfid_power = new Intent(UGRApplication.UGR_ACTION_SETTING_CHANGE);
        intent_rfid_power.putExtra("setting", "power");
        intent_rfid_power.putExtra("power_value", v_value);
        sendOrderedBroadcast(intent_rfid_power, null);

    }

    public void fn_OkumaSesAyarla(int v_value) throws RemoteException {


        //Intent intent_rfid_power = new Intent(UGRApplication.UGR_ACTION_SETTING_CHANGE);
        //intent_rfid_power.putExtra("setting", "power");
        //intent_rfid_power.putExtra("power_value", v_value);
        //sendOrderedBroadcast(intent_rfid_power, null);

    }

    private void fn_FragmentAyarla() {

        Fragment fragment = null;
        fragment = frg_ana_sayfa.newInstance();

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        //fragmentManager.beginTransaction().replace(R.id.frameLayoutForFragments, fragment, "frg_ana_sayfa").addToBackStack(null).commit();
        fragmentManager.beginTransaction().replace(R.id.frameLayoutForFragments, fragment, "frg_ana_sayfa").commit();
    }


    private void fn_AyarlariYukle() {
       fn_ModRFID();

        _ayarbaglantituru = _myIslem.fn_baglanti_turu();

        _ayaraktifkullanici = _myIslem.fn_aktif_kullanici();
        _ayarKullaniciAdi = _myIslem.fn_aktif_kullanici_adi();
        _ayaraktifdepo = _myIslem.fn_aktif_depo();
        _ayaraktifalttesis = _myIslem.fn_aktif_alt_tesis();
        _ayaraktiftesis = _myIslem.fn_aktif_tesis();
        _ayaraktifsunucu = _myIslem.fn_aktif_sunucu();
        _ayaraktifisletmeeslesme = _myIslem.fn_isletmeeslesme();
        _ayarversiyon = _myIslem.fn_versiyon();

        //_txtSetAktifAltTesis.setText(_ayaraktifalttesis);
        //_txtBaglanti.setText(_ayarbaglantituru);
        _txtSetKullanici.setText((_ayarKullaniciAdi));

        _txtVersiyon.setText(_ayarversiyon);

        //
        //_ayarsunucuip=_myIslem.fn_sunucu_ip();

        //


    }

    protected void mainScreen() {
        /*
        triggerGroup = (RadioGroup) findViewById(R.id.radio_trigger_mode);
        RadioButton triggerRFID = (RadioButton) findViewById(R.id.radio_trigger_rfid);
        RadioButton triggerScanner = (RadioButton) findViewById(R.id.radio_trigger_scanner);
        RadioButton triggerBoth = (RadioButton) findViewById(R.id.radio_trigger_both);
        triggerRFID.setOnClickListener(OnTriggerClickListener2);
        triggerScanner.setOnClickListener(OnTriggerClickListener2);
        triggerBoth.setOnClickListener(OnTriggerClickListener2);
        triggerRFID.setChecked(true);
*/
        mTAGs = new ArrayList<>();

        tagArrayList = new ArrayList<>();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = new Intent(UGRApplication.UGR_ACTION_SETTING_CHANGE);
        intent.putExtra("setting", "read_mode");
        intent.putExtra("read_mode_value", 2);
        sendOrderedBroadcast(intent, null);

        intent = new Intent(UGRApplication.UGR_ACTION_SETTING_CHANGE);
        intent.putExtra("setting", "trigger_mode");
        intent.putExtra("trigger_mode_value", mLastTriggerMode);
        sendOrderedBroadcast(intent, null);

        intent = new Intent(UGRApplication.UGR_ACTION_SETTING_CHANGE);
        intent.putExtra("setting", "end_char");
        intent.putExtra("end_char_value", 6);
        sendOrderedBroadcast(intent, null);

        intent = new Intent(UGRApplication.UGR_ACTION_SETTING_CHANGE);
        intent.putExtra("setting", "output_mode");
        intent.putExtra("output_mode_value", 2);
        sendOrderedBroadcast(intent, null);

        if (!bNeedConnect)
            RFIDEnable(true);
    }

    public void fn_KlavyeAc()
    {
        InputMethodManager imm = (InputMethodManager)   getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

    }

    public void fn_KlavyeKapat()
    {
        InputMethodManager imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(_txtSetKullanici.getWindowToken(), 0);

        //imm.hideSoftInputFromWindow((IBinder) getApplicationContext(),0);
    }

    public class ResultWindowReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            String epc;

            if (intent.getAction().equals(UGRApplication.UGR_ACTION_EPC)) {
                epc = intent.getExtras().getString(UGRApplication.UGR_EXTRA_EPC_DATA);
                tagArrayList.add(new UhfTag(epc, 1));

                if (epc != null) {
                    boolean existTag = false;

                    HashMap<String, UhfTag> hashMap = new HashMap<>();
                    hashMap.put(epc, new UhfTag(epc, 1));

                    for (int i = 0; i < mTAGs.size(); i++) {
                        HashMap<String, UhfTag> tm = mTAGs.get(i);
                        if (tm != null) {
                            if (tm.containsKey(epc)) {
                                tm.get(epc).Reads++;
                                existTag = true;
                                break;
                            }
                        }
                    }
                    if (!existTag) {
                        mTAGs.add(hashMap);

                        int nSize = mTAGs.size();

                        //mTagsCount.setText(getString(R.string.tags_count, nSize));
                    }


                }

            } else if (intent.getAction().equals(UGRApplication.UGR_ACTION_IS_READING)) {
                mIsReading = intent.getExtras().getBoolean(UGRApplication.UGR_EXTRA_IS_READING);
                //if(mIsReading)
                //{
                //mBtnStart.setText("Stop");
                //}
                //else
                //  {
                //mBtnStart.setText("Start");
                //}
            } else if (intent.getAction().equals(UGRApplication.SCANNER_KEY_INTENT)) {
                int nExtra = intent.getIntExtra(UGRApplication.SCANNER_KEY_EXTRA, 0);
                if (nExtra == 1) {
                    myBaseTime = SystemClock.elapsedRealtime();
                    //  myTimer.sendEmptyMessage(0);
                } else {
                    //myTimer.removeMessages(0);
                    myPauseTime = SystemClock.elapsedRealtime();
                    tagArrayList.clear();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(resultReceiver);
        unregisterReceiver(mCodeReceiver);
        resultReceiver = null;
        //RFIDEnable(false);

        Intent intent = new Intent(UGRApplication.UGR_ACTION_SETTING_CHANGE);
        intent.putExtra("setting", "trigger_mode");
        intent.putExtra("trigger_mode_value", 2);
        sendOrderedBroadcast(intent, null);

        unbindService(m_UHFSvcConnection);
    }

    @Override
    protected void onPause() {
        super.onPause();

        Intent intent = new Intent(UGRApplication.UGR_ACTION_SETTING_CHANGE);
        intent.putExtra("setting", "read_mode");
        intent.putExtra("read_mode_value", 2);
        sendOrderedBroadcast(intent, null);

        intent = new Intent(UGRApplication.UGR_ACTION_SETTING_CHANGE);
        intent.putExtra("setting", "trigger_mode");
        intent.putExtra("trigger_mode_value", 2);
        sendOrderedBroadcast(intent, null);

        intent = new Intent(UGRApplication.UGR_ACTION_SETTING_CHANGE);
        intent.putExtra("setting", "intent_enable");
        intent.putExtra("intent_enable_value", true);
        sendOrderedBroadcast(intent, null);
    }

    @Override
    protected void onStop() {
        super.onStop();
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT_WATCH) {
            boolean isScreenOn = powerManager.isInteractive();
            if (isScreenOn && !bNeedConnect) {
                Log.d(getClass().getSimpleName(), "isScreenOn && !bNeedConnect");
                RFIDEnable(false);
            }
        }
    }


    public boolean exportTxtFile(String strFolderName, String strFileName, String strData) {
        File folder = new File(strFolderName);
        if (!folder.exists()) {
            try {
                boolean bMk = folder.mkdir();
                Log.d(GirisSayfasi.class.getSimpleName(), "exportTxtFile: mkdir: " + strFolderName + " : " + bMk);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        Log.d(GirisSayfasi.class.getSimpleName(), "exportTxtFile: " + strFolderName + strFileName);
        File exportFile = new File(strFolderName + strFileName);
        if (!exportFile.exists()) {
            try {
                exportFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        try {
            BufferedWriter buf = new BufferedWriter(new FileWriter(exportFile, true));
            buf.append(strData);
            buf.newLine();
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    RadioButton.OnClickListener OnTriggerClickListener = new RadioButton.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent scannerIntent = new Intent(UGRApplication.SCANNER_ACTION_SETTING_CHANGE);
            scannerIntent.putExtra("setting", "key_press");
            Intent rfidIntent = new Intent(UGRApplication.SCANNER_KEY_ENABLE_INTENT);

            switch (view.getId()) {
                case R.id.radio_trigger_rfid:
                    scannerIntent.putExtra("key_press_value", 0);
                    rfidIntent.putExtra(UGRApplication.SCANNER_KEY_ENABLE_EXTRA, 1);
                    break;
                case R.id.radio_trigger_scanner:
                    scannerIntent.putExtra("key_press_value", 1);
                    rfidIntent.putExtra(UGRApplication.SCANNER_KEY_ENABLE_EXTRA, 0);
                    break;
                case R.id.radio_trigger_both:
                    scannerIntent.putExtra("key_press_value", 1);
                    rfidIntent.putExtra(UGRApplication.SCANNER_KEY_ENABLE_EXTRA, 1);
                    break;
            }

            sendBroadcast(scannerIntent, null);

            sendBroadcast(rfidIntent, null);
        }
    };

    RadioButton.OnClickListener OnTriggerClickListener2 = new RadioButton.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent triggerIntent = new Intent(UGRApplication.UGR_ACTION_SETTING_CHANGE);
            triggerIntent.putExtra("setting", "trigger_mode");

            switch (view.getId()) {
                case R.id.radio_trigger_rfid:
                    triggerIntent.putExtra("trigger_mode_value", 0);
                    mLastTriggerMode = 0;
                    break;
                case R.id.radio_trigger_scanner:
                    triggerIntent.putExtra("trigger_mode_value", 1);
                    mLastTriggerMode = 1;
                    break;
                case R.id.radio_trigger_both:
                    triggerIntent.putExtra("trigger_mode_value", 2);
                    mLastTriggerMode = 2;
                    break;
            }

            sendBroadcast(triggerIntent, null);
        }
    };

    public class BarcodeReceiver extends BroadcastReceiver {

        private String barcode;
        private String type;

        private static final String SCANNER_EXTRA_BARCODE_DATA = "m3scannerdata";
        private static final String SCANNER_EXTRA_BARCODE_CODE_TYPE = "m3scanner_code_type";

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(UGRApplication.SCANNER_ACTION_BARCODE)) {

                barcode = intent.getExtras().getString(SCANNER_EXTRA_BARCODE_DATA);
                type = intent.getExtras().getString(SCANNER_EXTRA_BARCODE_CODE_TYPE);

                if (barcode != null)
                {
                    // BURASI BARKOD

                    frg_aktif_isemri_yukleme _frg_aktif_isemri_yukleme = (frg_aktif_isemri_yukleme) getSupportFragmentManager().findFragmentByTag("frg_aktif_isemri_yukleme");

                    if (_frg_aktif_isemri_yukleme != null && _frg_aktif_isemri_yukleme.isVisible())
                    {
                        _frg_aktif_isemri_yukleme.barkodOkundu(barcode);
                    }
                    else
                    {
                        frg_paket_uretim_ekrani _frg_paket_uretim_ekrani = (frg_paket_uretim_ekrani) getSupportFragmentManager().findFragmentByTag("frg_paket_uretim_ekrani");

                        if (_frg_paket_uretim_ekrani != null && _frg_paket_uretim_ekrani.isVisible())
                        {
                            _frg_paket_uretim_ekrani.fn_barkodOkundu(barcode);
                        }
                        else
                        {
                            frg_aktif_isemri_indirme _frg_aktif_isemri_indirme = (frg_aktif_isemri_indirme) getSupportFragmentManager().findFragmentByTag("frg_aktif_isemri_indirme");

                            if (_frg_aktif_isemri_indirme != null && _frg_aktif_isemri_indirme.isVisible())
                            {
                                _frg_aktif_isemri_indirme.barkodOkundu(barcode.toString());
                            }
                            else
                            {
                                frg_ana_sayfa _frg_ana_sayfa = (frg_ana_sayfa) getSupportFragmentManager().findFragmentByTag("frg_ana_sayfa");

                                if (_frg_ana_sayfa != null && _frg_ana_sayfa.isVisible())
                                {
                                    _frg_ana_sayfa.fn_BarkodOkutuldu(barcode.toString());
                                }

                                frg_geribesleme_onay _frg_geribesleme_onay = (frg_geribesleme_onay) getSupportFragmentManager().findFragmentByTag("frg_geribesleme_onay");

                                if (_frg_geribesleme_onay != null && _frg_geribesleme_onay.isVisible())
                                {
                                    _frg_geribesleme_onay.fn_BarkodOkutuldu(barcode.toString());
                                }

                                frg_ambalaj_tipi_degisimi _frg_ambalaj_tipi_degisimi = (frg_ambalaj_tipi_degisimi) getSupportFragmentManager().findFragmentByTag("frg_ambalaj_tipi_degisimi");

                                if (_frg_ambalaj_tipi_degisimi != null && _frg_ambalaj_tipi_degisimi.isVisible())
                                {
                                    _frg_ambalaj_tipi_degisimi.fn_BarkodOkutuldu(barcode.toString());
                                }

                                else
                                {
                                    frg_sifre_degistir _frg_sifre_degistir = (frg_sifre_degistir) getSupportFragmentManager().findFragmentByTag("frg_sifre_degistir");

                                    if (_frg_sifre_degistir != null && _frg_sifre_degistir.isVisible())
                                    {
                                        _frg_sifre_degistir.fn_BarkodOkutuldu(barcode.toString());
                                    }
                                    else {
                                        frg_uretim_zayi _frg_frg_uretim_zayi = (frg_uretim_zayi) getSupportFragmentManager().findFragmentByTag("frg_uretim_zayi");

                                        if (_frg_frg_uretim_zayi != null && _frg_frg_uretim_zayi.isVisible())
                                        {
                                            _frg_frg_uretim_zayi.fn_BarkodOkutuldu(barcode.toString());
                                        }
                                        else {
                                            frg_shrink_ayirma _frg_frg_shrink_ayirma = (frg_shrink_ayirma) getSupportFragmentManager().findFragmentByTag("frg_shrink_ayirma");

                                            if (_frg_frg_shrink_ayirma != null && _frg_frg_shrink_ayirma.isVisible()) {
                                                _frg_frg_shrink_ayirma.fn_BarkodOkutuldu(barcode.toString());
                                            }
                                            else {
                                                frg_shrink_onay _frg_frg_shrink_onay = (frg_shrink_onay) getSupportFragmentManager().findFragmentByTag("frg_shrink_onay");

                                                if (_frg_frg_shrink_onay != null && _frg_frg_shrink_onay.isVisible()) {
                                                    _frg_frg_shrink_onay.fn_BarkodOkutuldu(barcode.toString());
                                                }
                                                else {
                                                    frg_uretim_iptal _frg_frg_uretim_iptal = (frg_uretim_iptal) getSupportFragmentManager().findFragmentByTag("frg_uretim_iptal");

                                                    if (_frg_frg_uretim_iptal != null && _frg_frg_uretim_iptal.isVisible()) {
                                                        _frg_frg_uretim_iptal.fn_BarkodOkutuldu(barcode.toString());
                                                    }else {
                                                        frg_zayi_isemri_indirme _frg_zayi_isemri_indirme = (frg_zayi_isemri_indirme) getSupportFragmentManager().findFragmentByTag("frg_zayi_isemri_indirme");

                                                        if (_frg_zayi_isemri_indirme != null && _frg_zayi_isemri_indirme.isVisible())
                                                        {
                                                            _frg_zayi_isemri_indirme.fn_barkodOkundu(barcode);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }


                    //    mTvScannerResult.setText("Code : " + barcode + " / Type : " + type);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_rfid, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mIsReading) {
            inventory(false);
        }

        int id = item.getItemId();
        Log.i(getClass().getSimpleName(), "Selected menu - " + id);

        switch (id) {
            case R.id.action_menu_config: {
                bNeedConnect = true;
                startActivity(new Intent(this, ConfigPreferenceActivity.class));
            }
            break;
            case R.id.action_menu_access: {
                bNeedConnect = true;
                startActivity(new Intent(this, AccessActivity.class));
            }
            break;
            case R.id.action_menu_lock: {
                bNeedConnect = true;
                startActivity(new Intent(this, LockActivity.class));
            }
            break;
        }

        return super.onOptionsItemSelected(item);
    }

    long myBaseTime = 0;
    long myPauseTime;

    private void inventory(boolean bStart) {
        Intent intent;
        if (bStart) {
            intent = new Intent(UGRApplication.UGR_ACTION_START, null);
            myBaseTime = SystemClock.elapsedRealtime();
            // System.out.println(myBaseTime);
            // myTimer.sendEmptyMessage(0);
        } else {
            intent = new Intent(UGRApplication.UGR_ACTION_CANCEL, null);
            //myTimer.removeMessages(0);
            myPauseTime = SystemClock.elapsedRealtime();
            tagArrayList.clear();
        }
        sendOrderedBroadcast(intent, null);
    }

    private void RFIDEnable(boolean bOn) {
        Log.d(getClass().getSimpleName(), "RFIDEnable");
        int nExtra;
        if (bOn)
            nExtra = 1;
        else
            nExtra = 0;
        Intent intent = new Intent(UGRApplication.UGR_ACTION_ENABLE, null);
        intent.putExtra(UGRApplication.UGR_EXTRA_ENABLE, nExtra);
        intent.putExtra("module_reset", false);
        sendOrderedBroadcast(intent, null);
    }


    private String getTimeOut() {
        long now = SystemClock.elapsedRealtime();
        long outTime = now - myBaseTime;
        @SuppressLint("DefaultLocale") String easy_outTime = String.format("%02d:%02d:%02d", outTime / 1000 / 60, (outTime / 1000) % 60, (outTime % 1000) / 10);

        return easy_outTime;
    }

    @SuppressLint("HandlerLeak")
    final Handler UIHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UGRApplication.MSG_IS_READING:
                    /*
                    if(mIsReading)
                    {
                        mBtnStart.setText("Stop");
                    }
                    else
                        {
                        mBtnStart.setText("Start");
                    }*/
                    break;
                case UGRApplication.MSG_HANDLE_DATA:
                    String epc = (String) msg.obj;
                    tagArrayList.add(new UhfTag(epc, 1));

                    if (epc != null) {
                        boolean existTag = false;

                        HashMap<String, UhfTag> hashMap = new HashMap<>();
                        hashMap.put(epc, new UhfTag(epc, 1));

                        for (int i = 0; i < mTAGs.size(); i++) {
                            HashMap<String, UhfTag> tm = mTAGs.get(i);
                            if (tm != null) {
                                if (tm.containsKey(epc)) {
                                    tm.get(epc).Reads++;
                                    existTag = true;
                                    break;
                                }
                            }
                        }
                        if (!existTag) {
                            mTAGs.add(hashMap);

                           // mTAGs.clear();

                            //BURASI RFID

                            if(epc.length()>23)
                            {
                                String _TempEpc = epc.substring(epc.length() - 24, epc.length());

                                frg_arac_aktivasyon_eski fragment = (frg_arac_aktivasyon_eski) getSupportFragmentManager().findFragmentByTag("frg_arac_aktivasyon_eski");

                                if (fragment != null && fragment.isVisible())
                                {
                                    fragment.fn_arac_ac(_TempEpc);
                                }
                                else {
                                    frg_konteyner_aktivasyon _frg_konteyner_aktivasyon = (frg_konteyner_aktivasyon) getSupportFragmentManager().findFragmentByTag("frg_konteyner_aktivasyon");

                                    if (_frg_konteyner_aktivasyon != null && _frg_konteyner_aktivasyon.isVisible()) {
                                        _frg_konteyner_aktivasyon.fn_arac_ac(_TempEpc);
                                    } else {
                                        frg_konteyner_yukleme_aktivasyon _frg_konteyner_yukleme_aktivasyon = (frg_konteyner_yukleme_aktivasyon) getSupportFragmentManager().findFragmentByTag("frg_konteyner_yukleme_aktivasyon");

                                        if (_frg_konteyner_yukleme_aktivasyon != null && _frg_konteyner_yukleme_aktivasyon.isVisible()) {
                                            _frg_konteyner_yukleme_aktivasyon.fn_rfidOkundu(_TempEpc);
                                        } else {

                                            frg_aktif_isemri_yukleme _frg_aktif_isemri_yukleme = (frg_aktif_isemri_yukleme) getSupportFragmentManager().findFragmentByTag("frg_aktif_isemri_yukleme");

                                            if (_frg_aktif_isemri_yukleme != null && _frg_aktif_isemri_yukleme.isVisible()) {
                                                _frg_aktif_isemri_yukleme.rfidOkundu(_TempEpc);
                                            } else {
                                                frg_aktif_arac_secimi _frg_aktif_arac_secimi = (frg_aktif_arac_secimi) getSupportFragmentManager().findFragmentByTag("frg_aktif_arac_secimi");

                                                if (_frg_aktif_arac_secimi != null && _frg_aktif_arac_secimi.isVisible()) {
                                                    _frg_aktif_arac_secimi.fn_rfidOkundu(_TempEpc);
                                                }
                                                else {

                                                    frg_satilmis_etiket _frg_satilmis_etiket = (frg_satilmis_etiket) getSupportFragmentManager().findFragmentByTag("frg_satilmis_etiket");

                                                    if (_frg_satilmis_etiket != null && _frg_satilmis_etiket.isVisible())
                                                    {
                                                        _frg_satilmis_etiket.fn_RfidOkutuldu(_TempEpc);
                                                    }
                                                    else {
                                                        frg_uretim_zayi _frg_frg_uretim_zayi = (frg_uretim_zayi) getSupportFragmentManager().findFragmentByTag("frg_uretim_zayi");

                                                        if (_frg_frg_uretim_zayi != null && _frg_frg_uretim_zayi.isVisible())
                                                        {
                                                            _frg_frg_uretim_zayi.fn_rfidOkundu(_TempEpc);
                                                        }
                                                        else {
                                                            frg_shrink_ayirma _frg_frg_shrink_ayirma = (frg_shrink_ayirma) getSupportFragmentManager().findFragmentByTag("frg_shrink_ayirma");

                                                            if (_frg_frg_shrink_ayirma != null && _frg_frg_shrink_ayirma.isVisible())
                                                            {
                                                                _frg_frg_shrink_ayirma.fn_RfidOkundu(_TempEpc);
                                                            }
                                                            else {
                                                                frg_shrink_onay _frg_frg_shrink_onay = (frg_shrink_onay) getSupportFragmentManager().findFragmentByTag("frg_shrink_onay");

                                                                if (_frg_frg_shrink_onay != null && _frg_frg_shrink_onay.isVisible())
                                                                {
                                                                    _frg_frg_shrink_onay.fn_RfidOkundu(_TempEpc);
                                                                }
                                                                else {
                                                                    frg_uretim_iptal _frg_frg_uretim_iptal = (frg_uretim_iptal) getSupportFragmentManager().findFragmentByTag("frg_uretim_iptal");

                                                                    if (_frg_frg_uretim_iptal != null && _frg_frg_uretim_iptal.isVisible())
                                                                    {
                                                                        _frg_frg_uretim_iptal.fn_RfidOkundu(_TempEpc);
                                                                    }else {
                                                                        frg_aktif_silobas_arac_secimi _frg_aktif_silobas_arac_secimi = (frg_aktif_silobas_arac_secimi) getSupportFragmentManager().findFragmentByTag("frg_aktif_silobas_arac_secimi");

                                                                        if (_frg_aktif_silobas_arac_secimi != null && _frg_aktif_silobas_arac_secimi.isVisible())
                                                                        {
                                                                            _frg_aktif_silobas_arac_secimi.fn_RfidOkundu(_TempEpc);
                                                                        }else {
                                                                            frg_zayi_aktivasyon _frg_zayi_aktivasyon = (frg_zayi_aktivasyon) getSupportFragmentManager().findFragmentByTag("frg_zayi_aktivasyon");

                                                                            if (_frg_zayi_aktivasyon != null && _frg_zayi_aktivasyon.isVisible())
                                                                            {
                                                                                _frg_zayi_aktivasyon.fn_RfidOkundu(_TempEpc);
                                                                            }else {
                                                                                frg_zayi_isemri_indirme _frg_zayi_isemri_indirme = (frg_zayi_isemri_indirme) getSupportFragmentManager().findFragmentByTag("frg_zayi_isemri_indirme");

                                                                                if (_frg_zayi_isemri_indirme != null && _frg_zayi_isemri_indirme.isVisible())
                                                                                {
                                                                                    _frg_zayi_isemri_indirme.fn_rfidOkundu(_TempEpc);
                                                                                }else{
                                                                                    frg_arac_aktivasyon _frg_arac_aktivasyon = (frg_arac_aktivasyon) getSupportFragmentManager().findFragmentByTag("frg_arac_aktivasyon");

                                                                                    if (_frg_arac_aktivasyon != null && _frg_arac_aktivasyon.isVisible())
                                                                                    {
                                                                                        _frg_arac_aktivasyon.rfidOkundu(_TempEpc);
                                                                                    }else {
                                                                                        frg_konteyner_kamyon_esleme _frg_konteyner_kamyon_esleme = (frg_konteyner_kamyon_esleme) getSupportFragmentManager().findFragmentByTag("frg_konteyner_kamyon_esleme");

                                                                                        if (_frg_konteyner_kamyon_esleme != null && _frg_konteyner_kamyon_esleme.isVisible()) {
                                                                                            _frg_konteyner_kamyon_esleme.rfidOkundu(_TempEpc);
                                                                                        }else {
                                                                                            frg_konteyner_vagon_esleme _frg_konteyner_vagon_esleme = (frg_konteyner_vagon_esleme) getSupportFragmentManager().findFragmentByTag("frg_konteyner_vagon_esleme");

                                                                                            if (_frg_konteyner_vagon_esleme != null && _frg_konteyner_vagon_esleme.isVisible()) {
                                                                                                _frg_konteyner_vagon_esleme.rfidOkundu(_TempEpc);
                                                                                            }else {
                                                                                                frg_aktif_isemri_indirme _frg_aktif_isemri_indirme = (frg_aktif_isemri_indirme) getSupportFragmentManager().findFragmentByTag("frg_aktif_isemri_indirme");

                                                                                                if (_frg_aktif_isemri_indirme != null && _frg_aktif_isemri_indirme.isVisible()) {
                                                                                                    _frg_aktif_isemri_indirme.rfidOkundu(_TempEpc);
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    //Toast.makeText(getApplicationContext() ,"Hatalı Kod", Toast.LENGTH_LONG).show();
                                }


                                //
                                //frg_arac_aktivasyon fragment = (frg_arac_aktivasyon) getFragmentManager().findFragmentById(R.id.frameLayoutForFragments);

                                //Toast.makeText(getApplicationContext(), _TempEpc, Toast.LENGTH_LONG).show();

                               // new VeriTabani(getApplicationContext()).fn_EpcKayit(_TempEpc);
                                //int nSize = mTAGs.size();
                                //mTagsCount.setText(getString(R.string.tags_count, nSize));
                            }
                        }


                    }
                    break;
            }
        }
    };

    public  void fn_ListeTemizle()
    {
       // Toast.makeText(getApplicationContext(), "fn_ListeTemizle Çalıştı", Toast.LENGTH_LONG).show();
        mTAGs.clear();

    }


    public void fn_ReadModeAyarla(int v_Gelen)
    {
        try {
            // On Pause
            Intent intent = new Intent(UGRApplication.UGR_ACTION_SETTING_CHANGE);
            intent.putExtra("setting", "read_mode");
            intent.putExtra("read_mode_value", v_Gelen);
            sendOrderedBroadcast(intent, null);

            // onpausebitti
        } catch (Exception ex) {

        }


    }

    public void fn_Cikis() {
        //Toast.makeText(getApplicationContext(), "dedededed", Toast.LENGTH_LONG).show();
        try {
            // On Pause
            Intent intent = new Intent(UGRApplication.UGR_ACTION_SETTING_CHANGE);
            intent.putExtra("setting", "read_mode");
            intent.putExtra("read_mode_value", 2);
            sendOrderedBroadcast(intent, null);

            intent = new Intent(UGRApplication.UGR_ACTION_SETTING_CHANGE);
            intent.putExtra("setting", "trigger_mode");
            intent.putExtra("trigger_mode_value", 2);
            sendOrderedBroadcast(intent, null);

            intent = new Intent(UGRApplication.UGR_ACTION_SETTING_CHANGE);
            intent.putExtra("setting", "intent_enable");
            intent.putExtra("intent_enable_value", true);
            sendOrderedBroadcast(intent, null);
            // onpausebitti
        } catch (Exception ex) {

        }


        try {
            // Onstop
            PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT_WATCH) {
                boolean isScreenOn = powerManager.isInteractive();
                if (isScreenOn && !bNeedConnect) {
                    Log.d(getClass().getSimpleName(), "isScreenOn && !bNeedConnect");
                    RFIDEnable(false);
                }
            }
            //onstop bitti
        } catch (Exception ex) {


        }


        try {
//ondestroy
            unregisterReceiver(resultReceiver);
            unregisterReceiver(mCodeReceiver);
            resultReceiver = null;
            //RFIDEnable(false);

            Intent intent = new Intent(UGRApplication.UGR_ACTION_SETTING_CHANGE);
            intent.putExtra("setting", "trigger_mode");
            intent.putExtra("trigger_mode_value", 2);
            sendOrderedBroadcast(intent, null);

            unbindService(m_UHFSvcConnection);
//ondestroy bitti
        } catch (Exception ex)
        {


        }


        System.exit(1);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent KEvent)
    {
        int keyaction = KEvent.getAction();

        if(keyaction == KeyEvent.KEYCODE_ENTER || keyaction == KeyEvent.KEYCODE_NUMPAD_ENTER)
        {
            int keycode = KEvent.getKeyCode();
            int keyunicode = KEvent.getUnicodeChar(KEvent.getMetaState() );
            char character = (char) keyunicode;
            System.out.println("DEBUG MESSAGE KEY=" + character + " KEYCODE=" +  keycode);
            return false;
        }


        return super.dispatchKeyEvent(KEvent);
    }
}
