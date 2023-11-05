package com.etimaden.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.etimaden.cResponseResult.Sevkiyat_isemri;
import com.etimaden.persosclass.Urun_tag;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;

public class apmblEldenSatisPerakendeSatisIsEmriSecimi extends ArrayAdapter<Sevkiyat_isemri> {

    private ArrayList<Sevkiyat_isemri> dataSet;
    Context mContext;

    public apmblEldenSatisPerakendeSatisIsEmriSecimi(ArrayList<Sevkiyat_isemri> data, Context context) {
        super(context, R.layout.liste_elden_satis_perakende_satis_is_emri_secimi_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    private static class ViewHolder {
        TextView _sira;
        TextView _kod_sap;
        TextView _alici;
        TextView _bookingno;
        TextView _urun_adi;
        TextView _kalan_agirlik;
        TextView _kalan_palet_sayisi;
        TextView _yapilan_miktar;
        TextView _yapilan_adet;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Sevkiyat_isemri dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.liste_elden_satis_perakende_satis_is_emri_secimi_item, parent, false);

            viewHolder._sira = (TextView) convertView.findViewById(R.id.sira);
            viewHolder._kod_sap = (TextView) convertView.findViewById(R.id.kod_sap);
            viewHolder._alici = (TextView) convertView.findViewById(R.id.alici);
            viewHolder._bookingno = (TextView) convertView.findViewById(R.id.bookingno);
            viewHolder._urun_adi = (TextView) convertView.findViewById(R.id.urun_adi);
            viewHolder._kalan_agirlik = (TextView) convertView.findViewById(R.id.kalan_agirlik);
            viewHolder._kalan_palet_sayisi = (TextView) convertView.findViewById(R.id.kalan_palet_sayisi);
            viewHolder._yapilan_miktar = (TextView) convertView.findViewById(R.id.yapilan_miktar);
            viewHolder._yapilan_adet = (TextView) convertView.findViewById(R.id.yapilan_adet);
            result=convertView;

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();

            result=convertView;
        }

        viewHolder._sira.setText(position + 1 + "" );
        viewHolder._kod_sap.setText(dataModel.kod_sap);
        viewHolder._alici.setText(dataModel.alici_isletme==null || dataModel.alici_isletme.equals("") ?  dataModel.alici : dataModel.alici_isletme);
        viewHolder._bookingno.setText(dataModel.bookingno);
        viewHolder._urun_adi.setText(dataModel.urun_adi);
        viewHolder._kalan_agirlik.setText(dataModel.kalan_agirlik);
        viewHolder._kalan_palet_sayisi.setText(dataModel.kalan_palet_sayisi);
        viewHolder._yapilan_miktar.setText(dataModel.yapilan_miktar);
        viewHolder._yapilan_adet.setText(dataModel.yapilan_adet);

        return convertView;
    }

}
