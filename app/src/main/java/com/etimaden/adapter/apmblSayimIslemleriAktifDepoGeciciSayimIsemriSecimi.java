package com.etimaden.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.etimaden.persosclass.Depo_sayım_isemri;
import com.etimaden.persosclass.malzeme_sayim_isemri;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;

public class apmblSayimIslemleriAktifDepoGeciciSayimIsemriSecimi extends ArrayAdapter<Depo_sayım_isemri> {

    private ArrayList<Depo_sayım_isemri> dataSet;
    Context mContext;

    public apmblSayimIslemleriAktifDepoGeciciSayimIsemriSecimi(ArrayList<Depo_sayım_isemri> data, Context context) {
        super(context, R.layout.liste_sayim_islemleri_aktif_depo_gecici_sayim_isemri_secimi_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    private static class ViewHolder {
        TextView _sira;
        TextView _say_ad;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Depo_sayım_isemri dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.liste_sayim_islemleri_aktif_depo_gecici_sayim_isemri_secimi_item, parent, false);


            viewHolder._sira = (TextView) convertView.findViewById(R.id.sira);
            viewHolder._say_ad = (TextView) convertView.findViewById(R.id.say_ad);

            result=convertView;

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();

            result=convertView;
        }

        viewHolder._sira.setText(position + 1 + "" );
        viewHolder._say_ad.setText(dataModel.getSay_ad());

        return convertView;
    }

}
