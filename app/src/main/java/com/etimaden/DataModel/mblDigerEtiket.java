package com.etimaden.DataModel;

public class mblDigerEtiket {

    int sirano;

    String kod = "";
    String durum= "";

    public mblDigerEtiket( int v_sirano,String v_kod,String v_durum)
    {
        sirano = v_sirano;
        kod = v_kod;
        durum = v_durum;
    }

    public String getsirano()
    {
        return sirano+"";
    }

    public String getkod()
    {
        return kod+"";
    }

    public String getdurum()
    {
        return durum;
    }

}
