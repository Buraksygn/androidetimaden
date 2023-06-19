package com.etimaden.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.etimaden.DataModel.mblBekleyenArac;
import com.etimaden.DataModel.mblsEmriKonteyner;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;

public class apmblKonteynerIsEmri extends ArrayAdapter<mblsEmriKonteyner> {

    private ArrayList<mblsEmriKonteyner> dataSet;
    Context mContext;

    public apmblKonteynerIsEmri(ArrayList<mblsEmriKonteyner> data, Context context) {
        super(context, R.layout.lst_konteyner_isemri_listesi, data);
        this.dataSet = data;
        this.mContext=context;
    }

    private static class ViewHolder
    {
        TextView _yazi_sira;
        TextView _yazi_kod_sap;
        TextView _yazi_alici_isletme;
        TextView _yazi_bookingno;
        TextView _yazi_urun_adi;
        TextView _yazi_kalan_agirlik;
        TextView _yazi_kalan_palet_sayisi;
        TextView _yazi_yapilan_miktar;
        TextView _yazi_yapilan_adet;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        mblsEmriKonteyner dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.lst_konteyner_isemri_listesi, parent, false);

            viewHolder._yazi_sira = (TextView) convertView.findViewById(R.id.yazi_sira);
            viewHolder._yazi_kod_sap = (TextView) convertView.findViewById(R.id.yazi_kod_sap);
            viewHolder._yazi_alici_isletme = (TextView) convertView.findViewById(R.id.yazi_alici_isletme);
            viewHolder._yazi_bookingno = (TextView) convertView.findViewById(R.id.yazi_bookingno);
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

        viewHolder._yazi_sira.setText(dataModel.getyazi_sira_no());
        viewHolder._yazi_kod_sap.setText(dataModel.getkod_sap());
        viewHolder._yazi_alici_isletme.setText(dataModel.getalici_isletme());
        viewHolder._yazi_bookingno.setText(dataModel.getbookingno());
        viewHolder._yazi_urun_adi.setText(dataModel.geturun_adi());
        viewHolder._yazi_kalan_agirlik.setText(dataModel.getkalan_agirlik());
        viewHolder._yazi_kalan_palet_sayisi.setText(dataModel.getkalan_palet_sayisi());
        viewHolder._yazi_yapilan_miktar.setText(dataModel.getyapilan_miktar());
        viewHolder._yazi_yapilan_adet.setText(dataModel.getyapilan_adet());

        return convertView;
    }
}


