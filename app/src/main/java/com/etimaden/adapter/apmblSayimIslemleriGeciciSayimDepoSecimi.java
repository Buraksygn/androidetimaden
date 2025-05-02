package com.etimaden.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.etimaden.persosclass.DEPOTag;
import com.etimaden.persosclass.Demirbas_Konum;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;

public class apmblSayimIslemleriGeciciSayimDepoSecimi extends ArrayAdapter<DEPOTag> {

    private ArrayList<DEPOTag> dataSet;
    Context mContext;

    public apmblSayimIslemleriGeciciSayimDepoSecimi(ArrayList<DEPOTag> data, Context context) {
        super(context, R.layout.liste_sayim_islemleri_gecici_sayim_depo_secimi_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    private static class ViewHolder {
        TextView _sira;
        TextView _depo_id;
        TextView _depo_adi;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        DEPOTag dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.liste_sayim_islemleri_gecici_sayim_depo_secimi_item, parent, false);


            viewHolder._sira = (TextView) convertView.findViewById(R.id.sira);
            viewHolder._depo_id = (TextView) convertView.findViewById(R.id.depo_id);
            viewHolder._depo_adi = (TextView) convertView.findViewById(R.id.depo_adi);

            result=convertView;

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();

            result=convertView;
        }

        viewHolder._sira.setText(position + 1 + "" );
        viewHolder._depo_id.setText(dataModel.getDepo_id());
        viewHolder._depo_adi.setText(dataModel.getDepo_adi());

        return convertView;
    }

}
