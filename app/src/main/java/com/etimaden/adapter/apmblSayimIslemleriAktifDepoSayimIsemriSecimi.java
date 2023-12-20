package com.etimaden.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.etimaden.persosclass.DEPOTag;
import com.etimaden.persosclass.malzeme_sayim_isemri;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;

public class apmblSayimIslemleriAktifDepoSayimIsemriSecimi extends ArrayAdapter<malzeme_sayim_isemri> {

    private ArrayList<malzeme_sayim_isemri> dataSet;
    Context mContext;

    public apmblSayimIslemleriAktifDepoSayimIsemriSecimi(ArrayList<malzeme_sayim_isemri> data, Context context) {
        super(context, R.layout.liste_sayim_islemleri_aktif_depo_sayim_isemri_secimi_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    private static class ViewHolder {
        TextView _sira;
        TextView _mls_kod_sap;
        TextView _mls_kod_isletme;
        TextView _mls_kod_depo;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        malzeme_sayim_isemri dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.liste_sayim_islemleri_aktif_depo_sayim_isemri_secimi_item, parent, false);


            viewHolder._sira = (TextView) convertView.findViewById(R.id.sira);
            viewHolder._mls_kod_sap = (TextView) convertView.findViewById(R.id.mls_kod_sap);
            viewHolder._mls_kod_isletme = (TextView) convertView.findViewById(R.id.mls_kod_isletme);
            viewHolder._mls_kod_depo = (TextView) convertView.findViewById(R.id.mls_kod_depo);

            result=convertView;

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();

            result=convertView;
        }

        viewHolder._sira.setText(position + 1 + "" );
        viewHolder._mls_kod_sap.setText(dataModel.getMls_kod_sap());
        viewHolder._mls_kod_isletme.setText(dataModel.getMls_kod_isletme());
        viewHolder._mls_kod_depo.setText(dataModel.getMls_kod_depo());

        return convertView;
    }

}
