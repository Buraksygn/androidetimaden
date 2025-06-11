package com.etimaden.persosenum;

public enum enumSeriNoDurum {

    olusturuldu(0, "Üretim Bekleyen Ürün"),
    uretimiyapildi(1, "Üretimi Tamamlanan Ürün"),
    kullanilamaz(2, "Sevk Edilemez Ürün"),
    cikisyapildi(3, "Satılmış Ürün"),
    etiketdegisecek(4, "Etiket Değişimi Bekliyor"),
    geribeslendi(5, "Geri Beslendi"),
    uygunolmayanurun(6, "Uygun Olmayan Ürün"),
    yoldakiurun(7, "Sevk Edilmiş Ürün"),
    yuklemeicinayrildi(8, "Araca Yüklenmiş ürün"),
    uretimiptaliyapildi(9, "Üretim İptali Yapıldı"),
    zayiyapildi(10, "Zayiat Yapıldı"),
    sayimdasayilamayan(31, "Sayımda Sayılamayan"),
    sayimdasayilamayaniptal(32, "Sayımda Sayılamayan İptal"),
    sayimdasayilamayanyeni(33, "Sayımda Sayılamayan Yeni"),
    ayrilditse(200, "TSE tarafından ayrılmış ürün"),
    ayrildiisletme(201, "İşletme tarafından ürün"),
    torbatipidegisimi(350, "Torba tipi değişimi bekleyen ürün"),
    kirlitorbadegisimi(360, "Kirli torba değişimi bekleyen ürün"),
    geribesleme(370, "Geri besleme bekleyen ürün"),
    paletduzenleme(380, "Palet Düzenleme"),
    ellecleme(390, "Elleçleme bekleyen ürün"),
    sayilmakicinisaretlenen(400, "Sayılmak İçin İşaretlenen"),
    sayimdasevkicinisaretlenen(401, "Sayımda Sevk İçin İşaretlenen"),
    paletdagitma(500, "Palet Dağıtma"),
    iptaledilenler(700, "İptal Edilenler");

    private final int value;
    private final String label;

    enumSeriNoDurum(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public int getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    public static enumSeriNoDurum fromInt(int value) {
        for (enumSeriNoDurum durum : values()) {
            if (durum.value == value)
                return durum;
        }
        return null;
    }

    public static String getLabelByValues(String _islemDurum , String _islemManTip){
        int islemDurum = Integer.parseInt(_islemDurum);
        int islemManTip = Integer.parseInt(_islemManTip);

        if(islemDurum == 351 || islemManTip == 360)
            return "Kirli torba değişimi bekleyen ürün";
        else if(islemDurum == 352 || islemManTip == 370)
            return "Geri besleme bekleyen ürün";
        else if(islemDurum == 354 || islemManTip == 390)
            return "Elleçleme bekleyen ürün";

        enumSeriNoDurum durum = fromInt(islemDurum);
                return (durum !=null) ? durum.getLabel() : "Bilinmeyen durum";
    }
}
