package com.etimaden.DataModel;

public class mblBekleyenArac {

    int sirano;

    String plaka = "";
    String konteyner = "";
    String isemri = "";
    String rfidkod = "";

    public mblBekleyenArac( int v_sirano,String v_plaka,String v_konteyner,String v_isemri,String v_rfidkod )
    {
        sirano=v_sirano;
        plaka = v_plaka;
        konteyner = v_konteyner;
        isemri = v_isemri;
        rfidkod = v_rfidkod;
    }

    public String getsirano()
    {
        return sirano+"";
    }

    public String getplaka()
    {
        return plaka+"";
    }

    public String getkonteyner()
    {
        return konteyner;
    }

    public String getisemri()
    {
        return isemri;
    }

    public String getrfidkod()
    {
        return rfidkod;
    }


}
