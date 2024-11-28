package com.etimaden.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.etimaden.persosclass.Demirbas_Konum;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;

public class apmblSayimIslemleriDbKatSecimi extends ArrayAdapter<Demirbas_Konum> {

    private ArrayList<Demirbas_Konum> dataSet;
    Context mContext;

    public apmblSayimIslemleriDbKatSecimi(ArrayList<Demirbas_Konum> data, Context context) {
        super(context, R.layout.liste_sayim_islemleri_db_kat_secimi_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    private static class ViewHolder {
        TextView _sira;
        TextView _kat_kod;
        TextView _kat_adi;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Demirbas_Konum dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.liste_sayim_islemleri_depo_sayim_islemi_item, parent, false);


            viewHolder._sira = (TextView) convertView.findViewById(R.id.sira);
            viewHolder._kat_kod = (TextView) convertView.findViewById(R.id.kat_kod);
            viewHolder._kat_adi = (TextView) convertView.findViewById(R.id.kat_adi);

            result=convertView;

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();

            result=convertView;
        }

        viewHolder._sira.setText(position + 1 + "" );
        viewHolder._kat_kod.setText(dataModel.getKat_kod());
        viewHolder._kat_adi.setText(dataModel.getKat_adi());

        return convertView;
    }

}
