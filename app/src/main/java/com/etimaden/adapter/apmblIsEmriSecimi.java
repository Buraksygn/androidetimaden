package com.etimaden.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.etimaden.DataModel.mblsEmriKonteyner;
import com.etimaden.DataModel.mdlIsemriSecimi;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;

public class apmblIsEmriSecimi extends ArrayAdapter<mdlIsemriSecimi> {

    private ArrayList<mdlIsemriSecimi> dataSet;
    Context mContext;

    public apmblIsEmriSecimi(ArrayList<mdlIsemriSecimi> data, Context context) {
        super(context, R.layout.liste_isemri_secimi, data);
        this.dataSet = data;
        this.mContext=context;
    }

    private static class ViewHolder
    {
        TextView _yazi_kod_sira;
        TextView _yazi_kod_sap;
        TextView _yazi_arac_plaka;
        TextView _yazi_urun_adi;
        TextView _yazi_kalan_agirlik;
        TextView _yazi_kalan_palet_sayisi;
        TextView _yazi_yapilan_miktar;
        TextView _yazi_yapilan_adet;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        mdlIsemriSecimi dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.liste_isemri_secimi, parent, false);

            viewHolder._yazi_kod_sira = (TextView) convertView.findViewById(R.id.yazi_kod_sira);
            viewHolder._yazi_kod_sap = (TextView) convertView.findViewById(R.id.yazi_kod_sap);
            viewHolder._yazi_arac_plaka = (TextView) convertView.findViewById(R.id.yazi_arac_plaka);
            viewHolder._yazi_urun_adi = (TextView) convertView.findViewById(R.id.yazi_urun_adi);
            viewHolder._yazi_kalan_agirlik = (TextView) convertView.findViewById(R.id.yazi_kalan_agirlik);
            viewHolder._yazi_kalan_palet_sayisi = (TextView) convertView.findViewById(R.id.yazi_kalan_palet_sayisi);
            viewHolder._yazi_yapilan_miktar = (TextView) convertView.findViewById(R.id.yazi_yapilan_miktar);
            viewHolder._yazi_yapilan_adet = (TextView) convertView.findViewById(R.id.yazi_yapilan_adet);
            result=convertView;

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();

            result=convertView;
        }

        viewHolder._yazi_kod_sira.setText(dataModel.getsirano());
        viewHolder._yazi_kod_sap.setText(dataModel.getkod_sap());
        viewHolder._yazi_arac_plaka.setText(dataModel.getarac_plaka());
        viewHolder._yazi_urun_adi.setText(dataModel.geturun_adi());
        viewHolder._yazi_kalan_agirlik.setText(dataModel.getkalan_agirlik());
        viewHolder._yazi_kalan_palet_sayisi.setText(dataModel.getkalan_palet_sayisi());
        viewHolder._yazi_yapilan_miktar.setText(dataModel.getyapilan_miktar());
        viewHolder._yazi_yapilan_adet.setText(dataModel.getyapilan_adet());

        return convertView;
    }


}
