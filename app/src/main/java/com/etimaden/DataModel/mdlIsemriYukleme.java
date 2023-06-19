package com.etimaden.DataModel;

public class mdlIsemriYukleme
{
    int sirano;

    String bos = "";
    String kod = "";
    String lotno = "";

    public mdlIsemriYukleme( int v_sirano,String v_bos,String v_kod,String v_lotno )
    {
        sirano=v_sirano;
        bos = v_bos;
        kod = v_kod;
        lotno = v_lotno;
    }

        public String getsirano()
        {
            return sirano+"";
        }

    public String getbos(){

        return bos;
    }

    public String getkod()
    {
        return kod;
    }

    public String getlotno()
    {
        return lotno;
    }
}
