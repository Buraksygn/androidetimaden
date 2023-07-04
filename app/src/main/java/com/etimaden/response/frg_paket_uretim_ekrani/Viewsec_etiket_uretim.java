package com.etimaden.response.frg_paket_uretim_ekrani;

import com.etimaden.persosclass.uretim_etiket;

public class Viewsec_etiket_uretim
{
    private String _zHataAciklama = "";
    private String _zAciklama = "";
    private String _zSonuc = "";
    private uretim_etiket _tag;

    public Viewsec_etiket_uretim(String v_zHataAciklama, String v_zAciklama, String v_zSonuc,uretim_etiket v_tag) {
        this._zHataAciklama = v_zHataAciklama;
        this._zAciklama = v_zAciklama;
        this._zSonuc = v_zSonuc;
        _tag=v_tag;
    }

    // region Sabit değerler get set
    public String get_zHataAciklama() {return _zHataAciklama;}
    public String get_zAciklama() {return _zAciklama;}
    public String get_zSonuc() {return _zSonuc;}
    public void set_zHataAciklama(String v_zHataAciklama) {this._zHataAciklama = v_zHataAciklama;}
    public void set_zAciklama( String v_zAciklama) {this._zAciklama = v_zAciklama;}
    public void set_zSonuc(String v_zSonuc) {this._zSonuc = v_zSonuc;}
    //

    public uretim_etiket get_tag() {return _tag;}
    public void set_tag(uretim_etiket v_tag) {_tag=v_tag;}





}
