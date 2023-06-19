package com.etimaden.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.etimaden.DataModel.mdlIsemriGroupSecimi;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;

public class apmblIsEmriGroupSecimi extends ArrayAdapter<mdlIsemriGroupSecimi> {

    private ArrayList<mdlIsemriGroupSecimi> dataSet;
    Context mContext;

    public apmblIsEmriGroupSecimi(ArrayList<mdlIsemriGroupSecimi> data, Context context) {
        super(context, R.layout.liste_isemri_secimi_grup, data);
        this.dataSet = data;
        this.mContext=context;
    }

    private static class ViewHolder
    {
        TextView _yazi_kod_sira;
        TextView _yazi_kod_sap;
        TextView _yazi_aktif_adet;
        TextView _yazi_dolu_konteyner_adet;
        TextView _yazi_dolu_konteyner_miktar;
        TextView _yazi_bos_konteyner_adet;
        TextView _yazi_bookingno;
        TextView _yazi_urun_adi;
        TextView _yazi_kalan_miktar;
        TextView _yazi_kalan_adet;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        mdlIsemriGroupSecimi dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.liste_isemri_secimi_grup, parent, false);

            viewHolder._yazi_kod_sira = (TextView) convertView.findViewById(R.id.yazi_kod_sira);
            viewHolder._yazi_kod_sap = (TextView) convertView.findViewById(R.id.yazi_kod_sap);
            viewHolder._yazi_aktif_adet = (TextView) convertView.findViewById(R.id.yazi_aktif_adet);
            viewHolder._yazi_dolu_konteyner_adet = (TextView) convertView.findViewById(R.id.yazi_dolu_konteyner_adet);
            viewHolder._yazi_dolu_konteyner_miktar = (TextView) convertView.findViewById(R.id.yazi_dolu_konteyner_miktar);
            viewHolder._yazi_bos_konteyner_adet = (TextView) convertView.findViewById(R.id.yazi_bos_konteyner_adet);
            viewHolder._yazi_bookingno = (TextView) convertView.findViewById(R.id.yazi_bookingno);
            viewHolder._yazi_urun_adi = (TextView) convertView.findViewById(R.id.yazi_urun_adi);
            viewHolder._yazi_kalan_miktar = (TextView) convertView.findViewById(R.id.yazi_kalan_miktar);
            viewHolder._yazi_kalan_adet = (TextView) convertView.findViewById(R.id.yazi_kalan_adet);
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
        viewHolder._yazi_aktif_adet.setText(dataModel.getcount());
        viewHolder._yazi_dolu_konteyner_adet.setText(dataModel.getdolu_konteyner_sayisi());
        viewHolder._yazi_dolu_konteyner_miktar.setText(dataModel.getdolu_konteyner_toplam_miktar());
        viewHolder._yazi_bos_konteyner_adet.setText(dataModel.getbos_konteyner_sayisi());
        viewHolder._yazi_bookingno.setText(dataModel.getbookingno());
        viewHolder._yazi_urun_adi.setText(dataModel.geturun_adi());
        viewHolder._yazi_kalan_miktar.setText(dataModel.getkalan_agirlik());
        viewHolder._yazi_kalan_adet.setText(dataModel.getkalan_palet_sayisi());

        return convertView;
    }
}
