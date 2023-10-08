package com.etimaden.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.etimaden.persosclass.DEPOTag;
import com.etimaden.persosclass.Urun_tag;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;

public class apmblManipulasyonGeribeslemeHarcamaYeriSecimi extends ArrayAdapter<DEPOTag> {

    private ArrayList<DEPOTag> dataSet;
    Context mContext;

    public apmblManipulasyonGeribeslemeHarcamaYeriSecimi(ArrayList<DEPOTag> data, Context context) {
        super(context, R.layout.liste_manipulasyon_geribesleme_harcama_yeri_secimi_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    private static class ViewHolder {
        TextView _sira;
        TextView _depoAdi;
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
            convertView = inflater.inflate(R.layout.liste_manipulasyon_geribesleme_harcama_yeri_secimi_item, parent, false);

            viewHolder._sira = (TextView) convertView.findViewById(R.id.sira);
            viewHolder._depoAdi = (TextView) convertView.findViewById(R.id.depoAdi);
            result=convertView;

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();

            result=convertView;
        }

        viewHolder._sira.setText(position + 1 + "" );
        viewHolder._depoAdi.setText(dataModel.getDepo_adi());

        return convertView;
    }

}
