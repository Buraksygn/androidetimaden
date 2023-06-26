package com.etimaden.cIslem;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;

import com.etimaden.cSabitDegerler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class VeriTabani extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "elterminali";

    // KİMLİK TABLOSU
    private static final String TABLO_01_KIMLIK = "ayarlar";
    private static final String KIMLIK_AKTIF_KULLANICI= "aktif_kullanici";
    private static final String KIMLIK_AKTIF_ADSOYAD= "aktif_kul_ad";
    private static final String KIMLIK_AKTIF_DEPO = "aktif_depo";
    private static final String KIMLIK_AKTIF_ALT_TESIS = "aktif_alt_tesis";
    private static final String KIMLIK_AKTIF_TESIS = "aktif_tesis";
    private static final String KIMLIK_AKTIF_SUNUCU = "aktif_sunucu";
    private static final String KIMLIK_AKTIF_ISLETME_ESLEME = "aktif_isletmeesleme";
    private static final String KIMLIK_AKTIF_SUNUCU_IP = "sunucu_ip";
    private static final String KIMLIK_BAGLANTITURU = "baglanti_turu";
    private static final String KIMLIK_VERSIYON = "versiyon";


    // YERDE ACMA İŞ EMİRLERİ
    private static final String TABLO_02_KONTEYNER_ISEMRI = "konteynerisemri";
    private static final String KONTEYNER_ISEMRI_sefer_no = "sefer_no";
    private static final String KONTEYNER_ISEMRI_hedef_isletme_esleme = "hedef_isletme_esleme";
    private static final String KONTEYNER_ISEMRI_hedef_isletme_kodu = "hedef_isletme_kodu";
    private static final String KONTEYNER_ISEMRI_hedef_isletme_alt_kodu = "hedef_isletme_alt_kodu";
    private static final String KONTEYNER_ISEMRI_hedef_depo = "hedef_depo";
    private static final String KONTEYNER_ISEMRI_secilen_sevk = "secilen_sevk";
    private static final String KONTEYNER_ISEMRI_isemri_detay_id = "isemri_detay_id";
    private static final String KONTEYNER_ISEMRI_isletme_esleme = "isletme_esleme";
    private static final String KONTEYNER_ISEMRI_kod_sap = "kod_sap";
    private static final String KONTEYNER_ISEMRI_isemri_id = "isemri_id";
    private static final String KONTEYNER_ISEMRI_urun_kodu = "urun_kodu";
    private static final String KONTEYNER_ISEMRI_depo_kodu = "depo_kodu";
    private static final String KONTEYNER_ISEMRI_bookingno = "bookingno";
    private static final String KONTEYNER_ISEMRI_urun_adi = "urun_adi";
    private static final String KONTEYNER_ISEMRI_kalan_agirlik = "kalan_agirlik";
    private static final String KONTEYNER_ISEMRI_kalan_palet_sayisi = "kalan_palet_sayisi";
    private static final String KONTEYNER_ISEMRI_yapilan_adet = "yapilan_adet";
    private static final String KONTEYNER_ISEMRI_alici = "alici";
    private static final String KONTEYNER_ISEMRI_yyapilan_miktar = "yapilan_miktar";


    // SEVK_LISTESI_GROUP_ISEMRI
    private static final String TABLO_03_SEVK_LISTESI_GROUP_ISEMRI = "sevklistesigrupisemi";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_isemri_detay_id = "isemri_detay_id";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_isemri_id = "isemri_id";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_urun_adi = "urun_adi";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_urun_kodu = "urun_kodu";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_depo_adi = "depo_adi";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_depo_kodu = "depo_kodu";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_lotno = "lotno";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_kalan_agirlik = "kalan_agirlik";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_palet_agirligi = "palet_agirligi";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_paletdizim_sayisi = "paletdizim_sayisi";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_kalan_palet_sayisi = "kalan_palet_sayisi";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_miktar_torba = "miktar_torba";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_konteyner_turu = "konteyner_turu";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_karakteristikler = "karakteristikler";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_yapilan_miktar = "yapilan_miktar";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_yapilan_adet = "yapilan_adet";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_indirmeBindirme = "indirmeBindirme";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_arac_kodu = "arac_kodu";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_arac_rfid = "arac_rfid";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_arac_plaka = "arac_plaka";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_kont_kodu = "kont_kodu";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_kont_rfid = "kont_rfid";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_rota_id = "rota_id";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_id_aracisemri = "id_aracisemri";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_kod_sap = "kod_sap";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_kayit_parametresi = "kayit_parametresi";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_isemri_tipi = "isemri_tipi";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_isemri_tipi_alt = "isemri_tipi_alt";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_islem_durumu = "islem_durumu";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_islem_id = "islem_id";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_bookingno = "bookingno";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_alici_isletme = "alici_isletme";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_alici = "alici";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_aktif_pasif = "aktif_pasif";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_hedef_isletme_kodu = "hedef_isletme_kodu";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_hedef_isletme_alt_kodu = "hedef_isletme_alt_kodu";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_hedef_depo = "hedef_depo";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_hedef_isletme_esleme = "hedef_isletme_esleme";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_isletme_esleme = "isletme_esleme";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_sefer_no = "sefer_no";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_count = "count";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_dolu_konteyner_sayisi = "dolu_konteyner_sayisi";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_bos_konteyner_sayisi = "bos_konteyner_sayisi";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_dolu_konteyner_toplam_miktar = "dolu_konteyner_toplam_miktar";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_isletme = "isletme";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_alt_rota = "alt_rota";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_aciklama = "aciklama";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_update_id = "update_id";
    private static final String SEVK_LISTESI_GROUP_ISEMRI_vardiya = "vardiya";

    // GECICI TABLOSU
    private static final String TABLO_04_GECICI = "gecici";
    private static final String GECICI_NO = "ad";

    // RFID OKUMA TABLOSU
    private static final String TABLO_05_RFIDOKUMA = "rfidokuma";
    private static final String TABLO_RFIDOKUMA_SIRANO = "sirano";
    private static final String TABLO_RFIDOKUMA_EPC = "epc";

    // YÜKLENEN_ETIKET_TABLOSU
    private static final String TABLO_06_OKUTULANETIKET = "yuklenenetiket";
    private static final String TABLO_urun_kod = "urun_kod";
    private static final String TABLO_palet_agirligi = "palet_agirligi";
    private static final String TABLO_palet_dizim = "palet_dizim";
    private static final String TABLO_torba_agirlik = "torba_agirlik";
    private static final String TABLO_karakteristikler = "karakteristikler";
    private static final String TABLO_lotno = "lotno";
    private static final String TABLO_urun_adi = "urun_adi";
    private static final String TABLO_rfid = "rfid";
    private static final String TABLO_kod = "kod";
    private static final String TABLO_palet_rfid = "palet_rfid";
    private static final String TABLO_palet_kod = "palet_kod";
    private static final String TABLO_islem_durumu = "islem_durumu";
    private static final String TABLO_etiket_turu = "etiket_turu";
    private static final String TABLO_isletme = "isletme";
    private static final String TABLO_isletme_esleme = "isletme_esleme";
    private static final String TABLO_isletme_adi = "isletme_adi";
    private static final String TABLO_kilitli = "kilitli";

    // DEPO LİSTESİ
    private static final String TABLO_07_DEPO = "depo";
    private static final String TABLO_07_DEPO_ID = "depo_id";
    private static final String TABLO_07_DEPO_ADI = "depo_adi";

    // BEKLEYEN ARAÇ LİSTESİ
    private static final String TABLO_08_BEKLEYEN_ARAC = "bekleyenarac";
    private static final String TABLO_08_BEKLEYEN_ARAC_PLAKA = "bekleyenarac_plaka";
    private static final String TABLO_08_BEKLEYEN_ARAC_KONTEYNER = "bekleyenarac_konteyner";
    private static final String TABLO_08_BEKLEYEN_ARAC_ISEMRI = "bekleyenarac_isemri";
    private static final String TABLO_08_BEKLEYEN_ARAC_RFIDKOD = "bekleyenarac_rfidkod";

    // urun_listesi_yuklenen
    private static final String TABLO_09_INDIRME_YUKLENEN = "listeyuklenen";
    private static final String TABLO_09_INDIRME_YUKLENEN_PALET_KOD = "paletkod";
    private static final String TABLO_09_INDIRME_YUKLENEN_LOT_NO = "lotno";
    private static final String TABLO_09_INDIRME_YUKLENEN_PALET_AGIRLIK = "paletagirligi";
    private static final String TABLO_09_INDIRME_YUKLENEN_DURUM  = "durum";

    // satilsmis_etiket_kontrol
    private static final String TABLO_10_ETIKET_KONTROL = "etiketkontrol";
    private static final String TABLO_10_ETIKET_KONTROL_EPC = "epc";
    private static final String TABLO_10_ETIKET_KONTROL_DURUM = "durum";



    public VeriTabani(Context context)
    {
        super(context, DATABASE_NAME, null, cSabitDegerler.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {




        sqLiteDatabase.execSQL("CREATE TABLE " + TABLO_10_ETIKET_KONTROL+ "("
                + TABLO_10_ETIKET_KONTROL_EPC + " TEXT,"
                + TABLO_10_ETIKET_KONTROL_DURUM + " TEXT )");


        sqLiteDatabase.execSQL("CREATE TABLE " + TABLO_09_INDIRME_YUKLENEN+ "("
                + TABLO_09_INDIRME_YUKLENEN_PALET_KOD + " TEXT,"
                + TABLO_09_INDIRME_YUKLENEN_LOT_NO + " TEXT,"
                + TABLO_09_INDIRME_YUKLENEN_PALET_AGIRLIK + " TEXT,"
                + TABLO_09_INDIRME_YUKLENEN_DURUM + " TEXT )");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLO_08_BEKLEYEN_ARAC+ "("
                + TABLO_08_BEKLEYEN_ARAC_PLAKA + " TEXT,"
                + TABLO_08_BEKLEYEN_ARAC_KONTEYNER + " TEXT,"
                + TABLO_08_BEKLEYEN_ARAC_RFIDKOD + " TEXT,"
                + TABLO_08_BEKLEYEN_ARAC_ISEMRI + " TEXT )");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLO_07_DEPO+ "("
                + TABLO_07_DEPO_ID + " TEXT,"
                + TABLO_07_DEPO_ADI + " TEXT )");


        sqLiteDatabase.execSQL("CREATE TABLE " + TABLO_03_SEVK_LISTESI_GROUP_ISEMRI+ "("
                + SEVK_LISTESI_GROUP_ISEMRI_isemri_detay_id + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_isemri_id + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_urun_adi + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_urun_kodu + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_depo_kodu + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_depo_adi + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_lotno + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_kalan_agirlik + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_palet_agirligi + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_paletdizim_sayisi + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_kalan_palet_sayisi + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_miktar_torba + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_konteyner_turu + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_karakteristikler + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_yapilan_miktar + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_yapilan_adet + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_indirmeBindirme + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_arac_kodu + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_arac_rfid + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_arac_plaka + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_kont_kodu + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_kont_rfid + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_rota_id + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_id_aracisemri + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_kod_sap + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_kayit_parametresi + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_isemri_tipi + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_isemri_tipi_alt + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_islem_durumu + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_islem_id + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_bookingno + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_alici_isletme + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_alici + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_aktif_pasif + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_hedef_isletme_kodu + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_hedef_isletme_alt_kodu + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_hedef_depo + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_hedef_isletme_esleme + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_isletme_esleme + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_sefer_no + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_count + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_dolu_konteyner_sayisi + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_bos_konteyner_sayisi + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_dolu_konteyner_toplam_miktar + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_isletme + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_alt_rota + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_aciklama + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_update_id + " TEXT,"
                + SEVK_LISTESI_GROUP_ISEMRI_vardiya + " TEXT )");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLO_01_KIMLIK+ "("
                + KIMLIK_AKTIF_KULLANICI + " TEXT,"
                + KIMLIK_AKTIF_ADSOYAD + " TEXT,"
                + KIMLIK_AKTIF_DEPO + " TEXT,"
                + KIMLIK_AKTIF_ALT_TESIS + " TEXT,"
                + KIMLIK_AKTIF_TESIS + " TEXT,"
                + KIMLIK_AKTIF_SUNUCU + " TEXT,"
                + KIMLIK_AKTIF_ISLETME_ESLEME + " TEXT,"
                + KIMLIK_BAGLANTITURU + " TEXT,"
                + KIMLIK_VERSIYON + " TEXT,"
                + KIMLIK_AKTIF_SUNUCU_IP + " TEXT )");


        sqLiteDatabase.execSQL("CREATE TABLE " + TABLO_02_KONTEYNER_ISEMRI+ "("
                + KONTEYNER_ISEMRI_sefer_no + " TEXT,"
                + KONTEYNER_ISEMRI_hedef_isletme_esleme + " TEXT,"
                + KONTEYNER_ISEMRI_hedef_isletme_kodu + " TEXT,"
                + KONTEYNER_ISEMRI_hedef_isletme_alt_kodu + " TEXT,"
                + KONTEYNER_ISEMRI_hedef_depo + " TEXT,"
                + KONTEYNER_ISEMRI_secilen_sevk + " TEXT,"
                + KONTEYNER_ISEMRI_isemri_detay_id + " TEXT,"
                + KONTEYNER_ISEMRI_isletme_esleme + " TEXT,"
                + KONTEYNER_ISEMRI_kod_sap + " TEXT,"
                + KONTEYNER_ISEMRI_isemri_id + " TEXT,"
                + KONTEYNER_ISEMRI_urun_kodu + " TEXT,"
                + KONTEYNER_ISEMRI_bookingno + " TEXT,"
                + KONTEYNER_ISEMRI_depo_kodu + " TEXT,"
                + KONTEYNER_ISEMRI_urun_adi + " TEXT,"
                + KONTEYNER_ISEMRI_kalan_agirlik + " TEXT,"
                + KONTEYNER_ISEMRI_kalan_palet_sayisi + " TEXT,"
                + KONTEYNER_ISEMRI_yapilan_adet + " TEXT,"
                + KONTEYNER_ISEMRI_alici + " TEXT,"
                + KONTEYNER_ISEMRI_yyapilan_miktar + " TEXT )");


        sqLiteDatabase.execSQL("CREATE TABLE " + TABLO_04_GECICI+ "("
                + GECICI_NO + " TEXT )");


        //rfidokuma
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLO_05_RFIDOKUMA+ "("
                       + TABLO_RFIDOKUMA_SIRANO + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TABLO_RFIDOKUMA_EPC + " TEXT )");


        //yuklenenetiket
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLO_06_OKUTULANETIKET+ "("
                + TABLO_urun_kod + " TEXT,"
                + TABLO_palet_agirligi + " TEXT,"
                + TABLO_palet_dizim + " TEXT,"
                + TABLO_torba_agirlik + " TEXT,"
                + TABLO_karakteristikler + " TEXT,"
                + TABLO_lotno + " TEXT,"
                + TABLO_urun_adi + " TEXT,"
                + TABLO_rfid + " TEXT,"
                + TABLO_kod + " TEXT,"
                + TABLO_palet_rfid + " TEXT,"
                + TABLO_palet_kod + " TEXT,"
                + TABLO_islem_durumu + " TEXT,"
                + TABLO_etiket_turu + " TEXT,"
                + TABLO_isletme + " TEXT,"
                + TABLO_isletme_esleme + " TEXT,"
                + TABLO_isletme_adi + " TEXT,"
                + TABLO_kilitli + " TEXT )");

    }


    public String  fn_ToplamMiktar()
    {
        String _Sonuc="-";

        try
        {
            SQLiteDatabase db = this.getWritableDatabase();

            Cursor cursor = db.rawQuery("select sum(palet_agirligi) as toplam from yuklenenetiket", null);

            if (cursor != null) {
                try {
                    if (cursor.moveToFirst()) {
                        _Sonuc = cursor.getString(0);


                    }
                } finally {
                    cursor.close();

                    if(db.isOpen())
                    {
                        db.close();
                    }
                }
            }
            else
            {
                _Sonuc="0";

            }
        }catch (Exception ex)
        {
            _Sonuc="-";
        }

        return _Sonuc;




    }

    public ArrayList<HashMap<String, String>> fn_ListeYuklenenEtiket()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> _IsEmirleriListesi = new ArrayList<>();
        //String query = "SELECT ROW_NUMBER() OVER (ORDER BY demirbasisim ASC) AS Row,demirbasisim, mahalno, rfidno FROM demirbas WHERE mahalno='F01-B438'";
        //String query=getString(R.string.sql_sorgusu)

        String query = "SELECT kod, lotno   from yuklenenetiket ";
        // String query = getString(R.string.sql_sorgusu);
        Cursor cursor = db.rawQuery(query,null);

        int gorev_id=0;

        gorev_id =cursor.getCount()+1;

        while (cursor.moveToNext())
        {
            gorev_id = gorev_id-1;

            HashMap<String,String> _IsEmri = new HashMap<>();

            _IsEmri.put("sira",gorev_id+"");
            _IsEmri.put("bos","");
            _IsEmri.put("kod",cursor.getString(cursor.getColumnIndex("kod")));
            _IsEmri.put("lotno",cursor.getString(cursor.getColumnIndex("lotno")));
            _IsEmirleriListesi.add(_IsEmri);
        }

        if(db.isOpen())
        {
            db.close();
        }

        return  _IsEmirleriListesi;
    }





    public ArrayList<HashMap<String, String>> fn_ListeSevkListesiIsEmri()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> _IsEmirleriListesi = new ArrayList<>();
        //String query = "SELECT ROW_NUMBER() OVER (ORDER BY demirbasisim ASC) AS Row,demirbasisim, mahalno, rfidno FROM demirbas WHERE mahalno='F01-B438'";
        //String query=getString(R.string.sql_sorgusu)

        String query = "SELECT kod_sap, arac_plaka,urun_adi,kalan_agirlik,kalan_palet_sayisi,yapilan_miktar,yapilan_adet  from sevklistesigrupisemi order by kod_sap  ";
        // String query = getString(R.string.sql_sorgusu);
        Cursor cursor = db.rawQuery(query,null);

        int gorev_id=0;

        while (cursor.moveToNext())
        {
            HashMap<String,String> _IsEmri = new HashMap<>();

            gorev_id = gorev_id + 1;

            _IsEmri.put("gorev_id",gorev_id+"");

            _IsEmri.put("kod_sap",cursor.getString(cursor.getColumnIndex("kod_sap")));
            _IsEmri.put("arac_plaka",cursor.getString(cursor.getColumnIndex("arac_plaka")));
            _IsEmri.put("urun_adi",cursor.getString(cursor.getColumnIndex("urun_adi")));
            _IsEmri.put("kalan_agirlik",cursor.getString(cursor.getColumnIndex("kalan_agirlik")));
            _IsEmri.put("kalan_palet_sayisi",cursor.getString(cursor.getColumnIndex("kalan_palet_sayisi")));
            _IsEmri.put("yapilan_miktar",cursor.getString(cursor.getColumnIndex("yapilan_miktar")));
            _IsEmri.put("yapilan_adet",cursor.getString(cursor.getColumnIndex("yapilan_adet")));
            _IsEmirleriListesi.add(_IsEmri);
        }

        if(db.isOpen())
        {
            db.close();
        }

        return  _IsEmirleriListesi;
    }


    public ArrayList<HashMap<String, String>> fn_ListeSevkListesiGrupIsEmri(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> _IsEmirleriListesi = new ArrayList<>();
        //String query = "SELECT ROW_NUMBER() OVER (ORDER BY demirbasisim ASC) AS Row,demirbasisim, mahalno, rfidno FROM demirbas WHERE mahalno='F01-B438'";
        //String query=getString(R.string.sql_sorgusu)

        String query = "SELECT kod_sap, count,dolu_konteyner_sayisi, dolu_konteyner_toplam_miktar, bos_konteyner_sayisi, bookingno, urun_adi, kalan_agirlik, kalan_palet_sayisi from sevklistesigrupisemi order by kod_sap  ";
        // String query = getString(R.string.sql_sorgusu);
        Cursor cursor = db.rawQuery(query,null);

        while (cursor.moveToNext())
        {
            HashMap<String,String> _IsEmri = new HashMap<>();

            _IsEmri.put("kod_sap",cursor.getString(cursor.getColumnIndex("kod_sap")));
            _IsEmri.put("count",cursor.getString(cursor.getColumnIndex("count")));
            _IsEmri.put("dolu_konteyner_sayisi",cursor.getString(cursor.getColumnIndex("dolu_konteyner_sayisi")));
            _IsEmri.put("dolu_konteyner_toplam_miktar",cursor.getString(cursor.getColumnIndex("dolu_konteyner_toplam_miktar")));
            _IsEmri.put("bos_konteyner_sayisi",cursor.getString(cursor.getColumnIndex("bos_konteyner_sayisi")));
            _IsEmri.put("bookingno",cursor.getString(cursor.getColumnIndex("bookingno")));
            _IsEmri.put("urun_adi",cursor.getString(cursor.getColumnIndex("urun_adi")));
            _IsEmri.put("kalan_agirlik",cursor.getString(cursor.getColumnIndex("kalan_agirlik")));
            _IsEmri.put("kalan_palet_sayisi",cursor.getString(cursor.getColumnIndex("kalan_palet_sayisi")));

            _IsEmirleriListesi.add(_IsEmri);
        }

        if(db.isOpen())
        {
            db.close();
        }

        return  _IsEmirleriListesi;
    }


    public void fn_yuklenenetiketYukle(String v_urun_kod,String v_palet_agirligi,String v_palet_dizim,String v_torba_agirlik,String v_karakteristikler,String v_lotno,String v_urun_adi,String v_rfid,String v_kod,String v_palet_rfid,String v_palet_kod,String v_islem_durumu,String v_etiket_turu,String v_isletme,String v_isletme_esleme,String v_isletme_adi,String v_kilitli)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String Sonuc="";

        String _strSql="insert into yuklenenetiket('urun_kod','palet_agirligi','palet_dizim','torba_agirlik','karakteristikler','lotno','urun_adi','rfid','kod','palet_rfid','palet_kod','islem_durumu','etiket_turu','isletme','isletme_esleme','isletme_adi','kilitli') ";
        _strSql +=" values ";
        _strSql +=" ('"+v_urun_kod+"','"+v_palet_agirligi+"','"+v_palet_dizim+"','"+v_torba_agirlik+"','"+v_karakteristikler+"','"+v_lotno+"','"+v_urun_adi+"','"+v_rfid+"','"+v_kod+"','"+v_palet_rfid+"','"+v_palet_kod+"','"+v_islem_durumu+"','"+v_etiket_turu+"','"+v_isletme+"','"+v_isletme_esleme+"','"+v_isletme_adi+"','"+v_kilitli+"') ";
        db.execSQL(_strSql);

        if(db.isOpen())
        {
            db.close();
        }
    }


    public void fn_SevkListesiGrupIsEmriKayit(String v_isemri_detay_id,String v_isemri_id,String v_urun_adi,String v_urun_kodu,String v_depo_adi,String v_depo_kodu,String v_lotno,String v_kalan_agirlik,String v_palet_agirligi,String v_paletdizim_sayisi,
                                              String v_kalan_palet_sayisi,String v_miktar_torba,String v_konteyner_turu,String v_karakteristikler,String v_yapilan_miktar,String v_yapilan_adet,String v_indirmeBindirme,String v_arac_kodu,
                                              String v_arac_rfid,String v_arac_plaka,String v_kont_kodu,String v_kont_rfid,String v_rota_id,String v_id_aracisemri,String v_kod_sap,String v_kayit_parametresi,
                                              String v_isemri_tipi,String v_isemri_tipi_alt,String v_islem_durumu,String v_islem_id,String v_bookingno,String v_alici_isletme,String v_alici,String v_aktif_pasif,String v_hedef_isletme_kodu,String v_hedef_isletme_alt_kodu,String v_hedef_depo,String v_hedef_isletme_esleme,
                                              String v_isletme_esleme,String v_sefer_no,String v_count,String v_dolu_konteyner_sayisi,String v_bos_konteyner_sayisi, String v_dolu_konteyner_toplam_miktar,String v_isletme,String v_alt_rota,String v_aciklama,String v_update_id,String v_vardiya)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String Sonuc="";

        String _strSql="insert into sevklistesigrupisemi('isemri_detay_id','isemri_id','urun_adi','urun_kodu','depo_adi','depo_kodu','lotno','kalan_agirlik','palet_agirligi','paletdizim_sayisi','kalan_palet_sayisi','miktar_torba','konteyner_turu','karakteristikler','yapilan_miktar','yapilan_adet','indirmeBindirme','arac_kodu','arac_rfid','arac_plaka','kont_kodu','kont_rfid','rota_id','id_aracisemri','kod_sap','kayit_parametresi','isemri_tipi','isemri_tipi_alt','islem_durumu','islem_id','bookingno','alici_isletme','alici','aktif_pasif','hedef_isletme_kodu','hedef_isletme_alt_kodu','hedef_depo','hedef_isletme_esleme','isletme_esleme','sefer_no','count','dolu_konteyner_sayisi','bos_konteyner_sayisi','dolu_konteyner_toplam_miktar','isletme','alt_rota','aciklama','update_id','vardiya') ";
        _strSql +=" values ";
        _strSql +=" ('"+v_isemri_detay_id+"','"+v_isemri_id+"','"+v_urun_adi+"','"+v_urun_kodu+"','"+v_depo_adi+"','"+v_depo_kodu+"','"+v_lotno+"','"+v_kalan_agirlik+"','"+v_palet_agirligi+"','"+v_paletdizim_sayisi+"','"+v_kalan_palet_sayisi+"','"+v_miktar_torba+"','"+v_konteyner_turu+"','"+v_karakteristikler+"','"+v_yapilan_miktar+"','"+v_yapilan_adet+"','"+v_indirmeBindirme+"','"+v_arac_kodu+"','"+v_arac_rfid+"','"+v_arac_plaka+"','"+v_kont_kodu+"','"+v_kont_rfid+"','"+v_rota_id+"','"+v_id_aracisemri+"','"+v_kod_sap+"','"+v_kayit_parametresi+"','"+v_isemri_tipi+"','"+v_isemri_tipi_alt+"','"+v_islem_durumu+"','"+v_islem_id+"','"+v_bookingno+"','"+v_alici_isletme+"','"+v_alici+"','"+v_aktif_pasif+"','"+v_hedef_isletme_kodu+"','"+v_hedef_isletme_alt_kodu+"','"+v_hedef_depo+"','"+v_hedef_isletme_esleme+"','"+v_isletme_esleme+"','"+v_sefer_no+"','"+v_count+"','"+v_dolu_konteyner_sayisi+"','"+v_bos_konteyner_sayisi+"','"+v_dolu_konteyner_toplam_miktar+"','"+v_isletme+"','"+v_alt_rota+"','"+v_aciklama+"','"+v_update_id+"','"+v_vardiya+"') ";
        db.execSQL(_strSql);

        if(db.isOpen())
        {
            db.close();
        }
    }


    public void fn_DepoListesiKayit(String v_depo_id,String v_depo_adi)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String Sonuc="";

        String _strSql="insert into "+TABLO_07_DEPO+"('depo_id','depo_adi') ";
        _strSql +=" values ";
        _strSql +=" ('"+v_depo_id+"','"+v_depo_adi+"') ";
        db.execSQL(_strSql);

        if(db.isOpen())
        {
            db.close();
        }
    }





    public void fn_BekleyenAracKayit(String v_arac_plaka,String v_is_emri,String v_rfid_kod, String v_konteyner_plaka)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String Sonuc="";

        String _strSql="insert into "+TABLO_08_BEKLEYEN_ARAC+"('bekleyenarac_plaka','bekleyenarac_isemri','bekleyenarac_rfidkod','bekleyenarac_konteyner') ";
        _strSql +=" values ";
        _strSql +=" ('"+v_arac_plaka+"','"+v_is_emri+"','"+v_rfid_kod+"','"+v_konteyner_plaka+"') ";
        db.execSQL(_strSql);

        if(db.isOpen())
        {
            db.close();
        }
    }





    public ArrayList<HashMap<String, String>> fn_KonteynerIsEmriListele(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> _IsEmirleriListesi = new ArrayList<>();
        //String query = "SELECT ROW_NUMBER() OVER (ORDER BY demirbasisim ASC) AS Row,demirbasisim, mahalno, rfidno FROM demirbas WHERE mahalno='F01-B438'";
        //String query=getString(R.string.sql_sorgusu)

        String query = "SELECT kod_sap, bookingno, urun_adi, kalan_agirlik, kalan_palet_sayisi, yapilan_miktar, yapilan_adet,alici from konteynerisemri order by kod_sap";
        // String query = getString(R.string.sql_sorgusu);
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> _IsEmri = new HashMap<>();

           _IsEmri.put("kod_sap",cursor.getString(cursor.getColumnIndex("kod_sap")));
           _IsEmri.put("bookingno",cursor.getString(cursor.getColumnIndex("bookingno")));
           _IsEmri.put("urun_adi",cursor.getString(cursor.getColumnIndex("urun_adi")));
           _IsEmri.put("kalan_agirlik",cursor.getString(cursor.getColumnIndex("kalan_agirlik")));
           _IsEmri.put("kalan_palet_sayisi",cursor.getString(cursor.getColumnIndex("kalan_palet_sayisi")));
           _IsEmri.put("yapilan_miktar",cursor.getString(cursor.getColumnIndex("yapilan_miktar")));
           _IsEmri.put("yapilan_adet",cursor.getString(cursor.getColumnIndex("yapilan_adet")));
           _IsEmri.put("alici",cursor.getString(cursor.getColumnIndex("alici")));
            _IsEmirleriListesi.add(_IsEmri);
        }
        if(db.isOpen())
        {
            db.close();
        }

        return  _IsEmirleriListesi;
    }

    public void fn_IsEmirleriTemizle() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String Sonuc = "";

            String _strSql = "delete from konteynerisemri";
            db.execSQL(_strSql);

            _strSql = "delete from sevklistesigrupisemi";
            db.execSQL(_strSql);

            _strSql = "delete from yuklenenetiket";
            db.execSQL(_strSql);

            if(db.isOpen())
            {
                db.close();
            }
        } catch (Exception ex) {

        }
    }

    public void fn_EpcKayit(String v_epc)
    {
        try {

            SQLiteDatabase db = this.getWritableDatabase();
            String Sonuc = "";

            String _strSql = "insert into rfidokuma('epc') values ('" + v_epc + "')";
            db.execSQL(_strSql);

            if(db.isOpen())
            {
                db.close();
            }
        } catch (Exception ex) {

        }
    }

    public String fn_IslemEpc() {
        String _sonuc = "";
        String _sirano = "";
        String _epc = "";
        String _sql = "";
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            // Önce işe yaramayan kayıtları sil
            _sql = "delete from rfidokuma where epc not like '737767%'";
            db.execSQL(_sql);
            // bitti

            Cursor cursor = db.rawQuery("SELECT sirano,epc from rfidokuma order by sirano desc limit 1", null);

            _sonuc = "-1";

            if (cursor != null) {
                try {
                    if (cursor.moveToFirst()) {
                        _sirano = cursor.getString(0);
                        _epc = cursor.getString(10);
                        _sonuc =_sirano.toString()+"_"+ cursor.getString(1);

                        try {
                            _sql = "delete from rfidokuma where epc = '"+_epc+"'";
                            db.execSQL(_sql);
                        } catch (Exception ex) {

                        }

                    }
                } finally {
                    cursor.close();

                    if(db.isOpen())
                    {
                        db.close();
                    }
                }
            }
        } catch (Exception ex) {
            _sonuc = "-1";
        }
        return _sonuc;

    }

    public void fn_EpcTemizle() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String Sonuc = "";

            String _strSql = "delete from rfidokuma";
            db.execSQL(_strSql);

            if(db.isOpen())
            {
                db.close();
            }
        } catch (Exception ex)
        {


        }
    }

    public void fn_IsEmriKayit(String sefer_no, String hedef_isletme_esleme,String hedef_isletme_kodu ,String hedef_isletme_alt_kodu,String hedef_depo,String secilen_sevk,String isemri_detay_id,String isletme_esleme,String kod_sap,String isemri_id,String urun_kodu,String depo_kodu,String bookingno, String urun_adi,String kalan_agirlik,String kalan_palet_sayisi,String yapilan_adet,String alici,String yapilan_miktar)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String Sonuc="";

        String _strSql="insert into konteynerisemri('sefer_no','hedef_isletme_esleme','hedef_isletme_kodu','hedef_isletme_alt_kodu','hedef_depo','secilen_sevk','isemri_detay_id','isletme_esleme','kod_sap','isemri_id','urun_kodu','depo_kodu','bookingno','urun_adi','kalan_agirlik','kalan_palet_sayisi','yapilan_adet','alici','yapilan_miktar') ";
        _strSql +=" values ";
        _strSql +=" ('"+sefer_no+"','"+hedef_isletme_esleme+"','"+hedef_isletme_kodu+"','"+hedef_isletme_alt_kodu+"','"+hedef_depo+"','"+secilen_sevk+"','"+isemri_detay_id+"','"+isletme_esleme+"','"+kod_sap+"','"+isemri_id+"','"+urun_kodu+"','"+depo_kodu+"','"+bookingno+"','"+urun_adi+"','"+kalan_agirlik+"','"+kalan_palet_sayisi+"','"+yapilan_adet+"','"+alici+"','"+yapilan_miktar+"') ";
        db.execSQL(_strSql);

        if(db.isOpen())
        {
            db.close();
        }
    }





    public void  fn_KullaniciAdiEkle(String aktif_kullanici, String aktif_kul_ad)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String _strSql="";

        _strSql="update ayarlar set aktif_kullanici = '"+aktif_kullanici+"', aktif_kul_ad = '"+aktif_kul_ad+"' ";

        db.execSQL(_strSql);
        if(db.isOpen())
        {
            db.close();
        }
    }

    public void  fn_BaslangicAyarlari(String versiyon, String baglanti_turu,String aktif_kullanici, String aktif_depo, String aktif_alt_tesis, String aktif_tesis, String aktif_sunucu, String aktif_isletmeesleme, String sunucu_ip)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String Sonuc="";
        String _strSql="delete from ayarlar";
        db.execSQL(_strSql);

        _strSql="insert into ayarlar(versiyon,baglanti_turu,aktif_kullanici,aktif_depo,aktif_alt_tesis,aktif_tesis,aktif_sunucu,aktif_isletmeesleme,sunucu_ip) ";
        _strSql +=" values ";
        _strSql +=" ('"+versiyon+"','"+baglanti_turu+"','"+aktif_kullanici+"','"+aktif_depo+"','"+aktif_alt_tesis+"','"+aktif_tesis+"','"+aktif_sunucu+"','"+aktif_isletmeesleme+"','"+sunucu_ip+"') ";
        db.execSQL(_strSql);
        if(db.isOpen())
        {
            db.close();
        }
    }

    public String fn_sefer_no(String kod_Sap)
    {
        String Sonuc="HATALI";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor=db.rawQuery("SELECT sefer_no from konteynerisemri where kod_sap='"+kod_Sap+"'" ,null);

        if (cursor != null)
        {
            try {
                if (cursor.moveToFirst())
                {
                    Sonuc =cursor.getString(0);
                }
            } finally
            {
                cursor.close();

                if(db.isOpen())
                {
                    db.close();
                }
            }
        }
        return Sonuc;
    }


    public String fn_isemri_detay_id(String kod_Sap)
    {
        String Sonuc="HATALI";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor=db.rawQuery("SELECT isemri_detay_id from konteynerisemri where kod_sap='"+kod_Sap+"'" ,null);

        if (cursor != null)
        {
            try {
                if (cursor.moveToFirst())
                {
                    Sonuc =cursor.getString(0);
                }
            } finally
            {
                cursor.close();

                if(db.isOpen())
                {
                    db.close();
                }
            }
        }
        return Sonuc;
    }

    public String fn_isletme_esleme(String kod_Sap)
    {
        String Sonuc="HATALI";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor=db.rawQuery("SELECT isletme_esleme from konteynerisemri where kod_sap='"+kod_Sap+"'" ,null);

        if (cursor != null)
        {
            try {
                if (cursor.moveToFirst())
                {
                    Sonuc =cursor.getString(0);
                }
            } finally
            {
                cursor.close();

                if(db.isOpen())
                {
                    db.close();
                }
            }
        }
        return Sonuc;
    }

    public String fn_isemri_id(String kod_Sap)
    {
        String Sonuc="HATALI";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor=db.rawQuery("SELECT isemri_id from konteynerisemri where kod_sap='"+kod_Sap+"'" ,null);

        if (cursor != null)
        {
            try {
                if (cursor.moveToFirst())
                {
                    Sonuc =cursor.getString(0);
                }
            } finally
            {
                cursor.close();

                if(db.isOpen())
                {
                    db.close();
                }
            }
        }
        return Sonuc;
    }

    public String fn_hedef_isletme_esleme(String kod_Sap)
    {
        String Sonuc="HATALI";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor=db.rawQuery("SELECT hedef_isletme_esleme from konteynerisemri where kod_sap='"+kod_Sap+"'" ,null);

        if (cursor != null)
        {
            try {
                if (cursor.moveToFirst())
                {
                    Sonuc =cursor.getString(0);
                }
            } finally
            {
                cursor.close();

                if(db.isOpen())
                {
                    db.close();
                }
            }
        }
        return Sonuc;
    }

    public String fn_hedef_isletme_kodu(String kod_Sap)
    {
        String Sonuc="HATALI";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor=db.rawQuery("SELECT hedef_isletme_kodu from konteynerisemri where kod_sap='"+kod_Sap+"'" ,null);

        if (cursor != null)
        {
            try {
                if (cursor.moveToFirst())
                {
                    Sonuc =cursor.getString(0);
                }
            } finally
            {
                cursor.close();

                if(db.isOpen())
                {
                    db.close();
                }
            }
        }
        return Sonuc;
    }

    public String fn_hedef_isletme_alt_kodu(String kod_Sap)
    {
        String Sonuc="HATALI";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor=db.rawQuery("SELECT hedef_isletme_alt_kodu from konteynerisemri where kod_sap='"+kod_Sap+"'" ,null);

        if (cursor != null)
        {
            try {
                if (cursor.moveToFirst())
                {
                    Sonuc =cursor.getString(0);
                }
            } finally
            {
                cursor.close();

                if(db.isOpen())
                {
                    db.close();
                }
            }
        }
        return Sonuc;
    }

    public String fn_depo_kodu(String kod_Sap)
    {
        String Sonuc="HATALI";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor=db.rawQuery("SELECT depo_kodu from konteynerisemri where kod_sap='"+kod_Sap+"'" ,null);

        if (cursor != null)
        {
            try {
                if (cursor.moveToFirst())
                {
                    Sonuc =cursor.getString(0);
                }
            } finally
            {
                cursor.close();

                if(db.isOpen())
                {
                    db.close();
                }
            }
        }
        return Sonuc;
    }

    public String fn_hedef_depo(String kod_Sap)
    {
        String Sonuc="HATALI";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor=db.rawQuery("SELECT hedef_depo from konteynerisemri where kod_sap='"+kod_Sap+"'" ,null);

        if (cursor != null)
        {
            try {
                if (cursor.moveToFirst())
                {
                    Sonuc =cursor.getString(0);
                }
            } finally
            {
                cursor.close();

                if(db.isOpen())
                {
                    db.close();
                }
            }
        }
        return Sonuc;
    }

    public String fn_urun_kodu(String kod_Sap)
    {
        String Sonuc="HATALI";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor=db.rawQuery("SELECT urun_kodu from konteynerisemri where kod_sap='"+kod_Sap+"'" ,null);

        if (cursor != null)
        {
            try {
                if (cursor.moveToFirst())
                {
                    Sonuc =cursor.getString(0);
                }
            } finally
            {
                cursor.close();

                if(db.isOpen())
                {
                    db.close();
                }
            }
        }
        return Sonuc;
    }





    public String fn_aktif_depo()
    {
        String Sonuc="HATALI";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor=db.rawQuery("SELECT aktif_depo from ayarlar" ,null);

        if (cursor != null)
        {
            try {
                if (cursor.moveToFirst())
                {
                    Sonuc =cursor.getString(0);
                }
            } finally
            {
                cursor.close();

                if(db.isOpen())
                {
                    db.close();
                }
            }
        }
        return Sonuc;
    }

    public String fn_aktif_kullanici_adi()
    {
        String Sonuc="HATALI";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor=db.rawQuery("SELECT "+KIMLIK_AKTIF_ADSOYAD +" from ayarlar" ,null);

        if (cursor != null)
        {
            try {
                if (cursor.moveToFirst())
                {
                    Sonuc =cursor.getString(0);
                }
            } finally
            {
                cursor.close();

                if(db.isOpen())
                {
                    db.close();
                }
            }
        }
        return Sonuc;
    }

    public String fn_aktif_kullanici()
    {
        String Sonuc="HATALI";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor=db.rawQuery("SELECT aktif_kullanici from ayarlar" ,null);

        if (cursor != null)
        {
            try {
                if (cursor.moveToFirst())
                {
                    Sonuc =cursor.getString(0);
                }
            } finally
            {
                cursor.close();

                if(db.isOpen())
                {
                    db.close();
                }
            }
        }
        return Sonuc;
    }
    public String fn_aktif_alt_tesis()
    {
        String Sonuc="HATALI";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor=db.rawQuery("SELECT aktif_alt_tesis from ayarlar" ,null);

        if (cursor != null)
        {
            try {
                if (cursor.moveToFirst())
                {
                    Sonuc =cursor.getString(0);
                }
            } finally
            {
                cursor.close();

                if(db.isOpen())
                {
                    db.close();
                }
            }
        }
        return Sonuc;
    }

    public String fn_aktif_tesis()
    {
        String Sonuc="HATALI";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor=db.rawQuery("SELECT aktif_tesis from ayarlar" ,null);

        if (cursor != null)
        {
            try {
                if (cursor.moveToFirst())
                {
                    Sonuc =cursor.getString(0);
                }
            } finally
            {
                cursor.close();

                if(db.isOpen())
                {
                    db.close();
                }
            }
        }
        return Sonuc;
    }

    public String fn_aktif_sunucu()
    {
        String Sonuc="HATALI";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor=db.rawQuery("SELECT aktif_sunucu from ayarlar" ,null);

        if (cursor != null)
        {
            try {
                if (cursor.moveToFirst())
                {
                    Sonuc =cursor.getString(0);
                }
            } finally
            {
                cursor.close();

                if(db.isOpen())
                {
                    db.close();
                }
            }
        }
        return Sonuc;
    }

    public String fn_versiyon()
    {
        String Sonuc="HATALI";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor=db.rawQuery("SELECT versiyon from ayarlar" ,null);

        if (cursor != null)
        {
            try {
                if (cursor.moveToFirst())
                {
                    Sonuc =cursor.getString(0);
                }
            } finally
            {
                cursor.close();

                if(db.isOpen())
                {
                    db.close();
                }
            }
        }
        return Sonuc;
    }

    public String fn_isletmeeslesme()
    {
        String Sonuc="HATALI";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor=db.rawQuery("SELECT aktif_isletmeesleme from ayarlar" ,null);

        if (cursor != null)
        {
            try {
                if (cursor.moveToFirst())
                {
                    Sonuc =cursor.getString(0);
                }
            } finally
            {
                cursor.close();

                if(db.isOpen())
                {
                    db.close();
                }
            }
        }
        return Sonuc;
    }

    public String fn_baglanti_turu()
    {
        String Sonuc="HATALI";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor=db.rawQuery("SELECT baglanti_turu from ayarlar" ,null);

        if (cursor != null)
        {
            try {
                if (cursor.moveToFirst())
                {
                    Sonuc =cursor.getString(0);
                }
            } finally
            {
                cursor.close();

                if(db.isOpen())
                {
                    db.close();
                }
            }
        }
        return Sonuc;
    }

    public String fn_sunucu_ip()
    {
        String Sonuc="HATALI";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor=db.rawQuery("SELECT sunucu_ip from ayarlar" ,null);

        if (cursor != null)
        {
            try {
                if (cursor.moveToFirst())
                {
                    Sonuc =cursor.getString(0);
                }
            } finally
            {
                cursor.close();

                if(db.isOpen())
                {
                    db.close();
                }
            }
        }
        return Sonuc;
    }





    public void fn_BekleyenAracListeSil()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String Sonuc="";
        String _strSql = "";
        try
        {
            _strSql="delete from "+TABLO_08_BEKLEYEN_ARAC;
            db.execSQL(_strSql);

            if(db.isOpen())
            {
                db.close();
            }


        }catch (Exception ex)
        {

        }
    }



    public void fn_DepoListeSil()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String Sonuc="";
        String _strSql = "";
        try
        {
            _strSql="delete from "+TABLO_07_DEPO;
            db.execSQL(_strSql);

            if(db.isOpen())
            {
                db.close();
            }


        }catch (Exception ex)
        {

        }
    }


    public void fn_Kayit()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String Sonuc="";
        String _strSql = "";
        try
        {
            _strSql="insert into gecici (ad) values('id2')";
            db.execSQL(_strSql);

            _strSql="delete from gecici";
            db.execSQL(_strSql);

            _strSql="DROP TABLE IF EXISTS " + TABLO_05_RFIDOKUMA;
            db.execSQL(_strSql);

            _strSql="CREATE TABLE " + TABLO_05_RFIDOKUMA + "("
                    + TABLO_RFIDOKUMA_SIRANO + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + TABLO_RFIDOKUMA_EPC + " TEXT )";
            db.execSQL(_strSql);



            if(db.isOpen())
            {
                db.close();
            }


        }catch (Exception ex)
        {

        }
    }

    public ArrayList<HashMap<String, String>> fn_BekleyenAracDizi()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> _AracListesi = new ArrayList<>();
        //String query = "SELECT ROW_NUMBER() OVER (ORDER BY demirbasisim ASC) AS Row,demirbasisim, mahalno, rfidno FROM demirbas WHERE mahalno='F01-B438'";
        //String query=getString(R.string.sql_sorgusu)

        String query = "SELECT bekleyenarac_rfidkod, bekleyenarac_isemri , bekleyenarac_konteyner, bekleyenarac_plaka from bekleyenarac order by bekleyenarac_plaka  ";
        // String query = getString(R.string.sql_sorgusu);
        Cursor cursor = db.rawQuery(query,null);

        int _DepoSira=1;

        while (cursor.moveToNext())
        {
            HashMap<String,String> _DepoListe = new HashMap<>();

            _DepoListe.put("arac_sira",_DepoSira+"".toString());
            _DepoListe.put("arac_plaka",cursor.getString(cursor.getColumnIndex("bekleyenarac_plaka")));
            _DepoListe.put("arac_isemri",cursor.getString(cursor.getColumnIndex("bekleyenarac_isemri")));
            _DepoListe.put("arac_rfid_kod",cursor.getString(cursor.getColumnIndex("bekleyenarac_rfidkod")));
            _DepoListe.put("konteyner_plaka",cursor.getString(cursor.getColumnIndex("bekleyenarac_konteyner")));

            _AracListesi.add(_DepoListe);
        }

        if(db.isOpen())
        {
            db.close();
        }

        return  _AracListesi;
    }

    public ArrayList<HashMap<String, String>> fn_DepoListe()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> _DepoYeriListesi = new ArrayList<>();
        //String query = "SELECT ROW_NUMBER() OVER (ORDER BY demirbasisim ASC) AS Row,demirbasisim, mahalno, rfidno FROM demirbas WHERE mahalno='F01-B438'";
        //String query=getString(R.string.sql_sorgusu)

        String query = "SELECT depo_id, depo_adi from depo order by depo_adi  ";
        // String query = getString(R.string.sql_sorgusu);
        Cursor cursor = db.rawQuery(query,null);

        int _DepoSira=1;

        while (cursor.moveToNext())
        {
            HashMap<String,String> _DepoListe = new HashMap<>();

            _DepoListe.put("depo_sira",_DepoSira+"".toString());
            _DepoListe.put("depo_id",cursor.getString(cursor.getColumnIndex("depo_id")));
            _DepoListe.put("depo_adi",cursor.getString(cursor.getColumnIndex("depo_adi")));
            _DepoYeriListesi.add(_DepoListe);
        }

        if(db.isOpen())
        {
            db.close();
        }

        return  _DepoYeriListesi;
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLO_01_KIMLIK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLO_02_KONTEYNER_ISEMRI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLO_03_SEVK_LISTESI_GROUP_ISEMRI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLO_04_GECICI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLO_05_RFIDOKUMA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLO_06_OKUTULANETIKET);
        db.execSQL("DROP TABLE IF EXISTS " + TABLO_07_DEPO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLO_08_BEKLEYEN_ARAC);
        db.execSQL("DROP TABLE IF EXISTS " + TABLO_09_INDIRME_YUKLENEN);

        onCreate(db);
    }

    public void fn_DepoListeTemizle() {
    }


    public void fn_SatilmisEtiketTabloTemizle() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String Sonuc = "";

            String _strSql = "delete from "+TABLO_10_ETIKET_KONTROL;
            db.execSQL(_strSql);

            if(db.isOpen())
            {
                db.close();
            }
        } catch (Exception ex) {

        }
    }


    public void fn_IndirmeListeTemize() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String Sonuc = "";

            String _strSql = "delete from listeyuklenen";
            db.execSQL(_strSql);

            if(db.isOpen())
            {
                db.close();
            }
        } catch (Exception ex) {

        }
    }


    public void fn_ListeIndirildi(String v_paletkod,String v_lotno,String v_paletagirligi, String v_durum)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String Sonuc="";

        String _strSql="update listeyuklenen set durum = '"+v_durum+"' where paletkod = '"+v_paletkod+"'";

        db.execSQL(_strSql);

        if(db.isOpen())
        {
            db.close();
        }
    }

    public void fn_ListeKayit(String v_paletkod,String v_lotno,String v_paletagirligi, String v_durum)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String Sonuc="";

        String _strSql="insert into listeyuklenen('paletkod','lotno','paletagirligi','durum') ";
        _strSql +=" values ";
        _strSql +=" ('"+v_paletkod+"','"+v_lotno+"','"+v_paletagirligi+"','"+v_durum+"') ";
        db.execSQL(_strSql);

        if(db.isOpen())
        {
            db.close();
        }
    }

    public ArrayList<HashMap<String, String>> fn_IndirmeListe()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> _DepoYeriListesi = new ArrayList<>();
        //String query = "SELECT ROW_NUMBER() OVER (ORDER BY demirbasisim ASC) AS Row,demirbasisim, mahalno, rfidno FROM demirbas WHERE mahalno='F01-B438'";
        //String query=getString(R.string.sql_sorgusu)

        String query = "SELECT durum, paletkod, lotno, paletagirligi, durum from listeyuklenen order by durum  ";
        // String query = getString(R.string.sql_sorgusu);
        Cursor cursor = db.rawQuery(query,null);

        int _DepoSira=0;

        _DepoSira = cursor.getCount();

        while (cursor.moveToNext()) {
            HashMap<String, String> _DepoListe = new HashMap<>();

            _DepoListe.put("depo_sira", _DepoSira + "".toString());
            _DepoListe.put("paletkod", cursor.getString(cursor.getColumnIndex("paletkod")));
            _DepoListe.put("lotno", cursor.getString(cursor.getColumnIndex("lotno")));
            _DepoListe.put("paletagirligi", cursor.getString(cursor.getColumnIndex("paletagirligi")));
            _DepoListe.put("durum", cursor.getString(cursor.getColumnIndex("durum")));
            _DepoYeriListesi.add(_DepoListe);

            _DepoSira = _DepoSira - 1;
        }

        if(db.isOpen())
        {
            db.close();
        }

        return  _DepoYeriListesi;
    }



}
