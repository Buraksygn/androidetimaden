package com.etimaden.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.etimaden.persosclass.IFTag;
import com.etimaden.persosclass.malzeme_sayim_isemri;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;

public class apmblSayimIslemleriDepoSayimIslemi extends ArrayAdapter<IFTag> {

    private ArrayList<IFTag> dataSet;
    Context mContext;

    public apmblSayimIslemleriDepoSayimIslemi(ArrayList<IFTag> data, Context context) {
        super(context, R.layout.liste_sayim_islemleri_depo_sayim_islemi_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    private static class ViewHolder {
        TextView _sira;
        TextView _epc;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        IFTag dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.liste_sayim_islemleri_depo_sayim_islemi_item, parent, false);


            viewHolder._sira = (TextView) convertView.findViewById(R.id.sira);
            viewHolder._epc = (TextView) convertView.findViewById(R.id.epc);

            result=convertView;

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();

            result=convertView;
        }

        viewHolder._sira.setText(position + 1 + "" );
        viewHolder._epc.setText(dataModel.getEPC().substring(dataModel.getEPC().length()-14));

        return convertView;
    }

}
