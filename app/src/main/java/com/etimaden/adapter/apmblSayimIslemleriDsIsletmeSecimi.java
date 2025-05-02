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

public class apmblSayimIslemleriDsIsletmeSecimi extends ArrayAdapter<Demirbas_Konum> {

    private ArrayList<Demirbas_Konum> dataSet;
    Context mContext;

    public apmblSayimIslemleriDsIsletmeSecimi(ArrayList<Demirbas_Konum> data, Context context) {
        super(context, R.layout.liste_sayim_islemleri_ds_isletme_secimi_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    private static class ViewHolder {
        TextView _sira;
        TextView _isletme_kod;
        TextView _isletme_adi;
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
            convertView = inflater.inflate(R.layout.liste_sayim_islemleri_ds_isletme_secimi_item, parent, false);


            viewHolder._sira = (TextView) convertView.findViewById(R.id.sira);
            viewHolder._isletme_kod = (TextView) convertView.findViewById(R.id.isletme_kod);
            viewHolder._isletme_adi = (TextView) convertView.findViewById(R.id.isletme_adi);

            result=convertView;

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();

            result=convertView;
        }

        viewHolder._sira.setText(position + 1 + "" );
        viewHolder._isletme_kod.setText(dataModel.getIsletme_kod());
        viewHolder._isletme_adi.setText(dataModel.getIsletme_adi());

        return convertView;
    }

}
