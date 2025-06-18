package com.etimaden.persosclass;

public enum GelismeTuruEnum {
    HAVA_MUHALEFETI("Hava Muhalefeti", "A123"),
    LIMAN_OPERASYONU("Liman Operasyonu", "B123"),
    GEMI_KAPTANI("Gemi Kaptanı", "C123"),
    DIGER("Diğer", "MSC123");

    private  String gelismead;
    private  String gelismekod;

    GelismeTuruEnum(String gelismead, String gelismekod) {
        this.gelismead = gelismead;
        this.gelismekod = gelismekod;
    }

    public String getAd() {
        return gelismead;
    }

    public String getKod() {
        return gelismekod;
    }
}
