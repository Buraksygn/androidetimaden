package com.etimaden.cResponseResult;

public class Arac
{
    public String arac_kod = "";
    public String arac_rfid  = "";
    public String arac_plaka  = "";
    public String arac_istihab_haddi  = "";
    public String arac_bos_tartim = "";

    public Arac(String arac_kod, String arac_rfid, String arac_plaka, String arac_istihab_haddi, String arac_bos_tartim) {
        this.arac_kod = arac_kod;
        this.arac_rfid = arac_rfid;
        this.arac_plaka = arac_plaka;
        this.arac_istihab_haddi = arac_istihab_haddi;
        this.arac_bos_tartim = arac_bos_tartim;
    }

    public String getArac_kod() {
        return arac_kod;
    }

    public void setArac_kod(String arac_kod) {
        this.arac_kod = arac_kod;
    }

    public String getArac_rfid() {
        return arac_rfid;
    }

    public void setArac_rfid(String arac_rfid) {
        this.arac_rfid = arac_rfid;
    }

    public String getArac_plaka() {
        return arac_plaka;
    }

    public void setArac_plaka(String arac_plaka) {
        this.arac_plaka = arac_plaka;
    }

    public String getArac_istihab_haddi() {
        return arac_istihab_haddi;
    }

    public void setArac_istihab_haddi(String arac_istihab_haddi) {
        this.arac_istihab_haddi = arac_istihab_haddi;
    }

    public String getArac_bos_tartim() {
        return arac_bos_tartim;
    }

    public void setArac_bos_tartim(String arac_bos_tartim) {
        this.arac_bos_tartim = arac_bos_tartim;
    }
}
