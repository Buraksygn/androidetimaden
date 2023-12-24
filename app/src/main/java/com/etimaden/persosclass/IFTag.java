package com.etimaden.persosclass;

public class IFTag {
    private String EPC = "";
    private String antenna = "";
    private String count = "";

    public IFTag() {
    }

    public IFTag(String EPC, String antenna, String count) {
        this.EPC = EPC;
        this.antenna = antenna;
        this.count = count;
    }

    public String getEPC() {
        return EPC;
    }

    public void setEPC(String EPC) {
        this.EPC = EPC;
    }

    public String getAntenna() {
        return antenna;
    }

    public void setAntenna(String antenna) {
        this.antenna = antenna;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
