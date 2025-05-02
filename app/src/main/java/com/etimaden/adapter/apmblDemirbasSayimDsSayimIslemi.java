package com.etimaden.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.etimaden.adapterclass.Urun_tag_data;
import com.etimaden.persosclass.demirbas_sayim;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;

public class apmblDemirbasSayimDsSayimIslemi extends ArrayAdapter<demirbas_sayim> {

    private ArrayList<demirbas_sayim> dataSet;
    Context mContext;

    public apmblDemirbasSayimDsSayimIslemi(ArrayList<demirbas_sayim> data, Context context) {
        super(context, R.layout.liste_demirbas_sayim_ds_sayim_islemi_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    private static class ViewHolder {
        ImageView _img;
        TextView _sira;
        TextView _ds_demirbas_kod;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        demirbas_sayim dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            //convertView = inflater.inflate(R.layout.liste_sevkiyat_aktif_isemri_indirme_item, parent, false);
            convertView = inflater.inflate(R.layout.liste_demirbas_sayim_ds_sayim_islemi_item,parent,false);

            viewHolder._img = (ImageView) convertView.findViewById(R.id.img);
            viewHolder._sira = (TextView) convertView.findViewById(R.id.sira);
            viewHolder._ds_demirbas_kod = (TextView) convertView.findViewById(R.id.ds_demirbas_kod);

            result=convertView;

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();

            result=convertView;
        }

        if(dataModel.getDs_durum().equals("0")){
            viewHolder._img.setImageResource(R.mipmap.yellowpoint);
        }else if(dataModel.getDs_durum().equals("1")){
            viewHolder._img.setImageResource(R.mipmap.greenpoint);
        }else if(dataModel.getDs_durum().equals("2")){
            viewHolder._img.setImageResource(R.mipmap.redpoint);
        }
        viewHolder._sira.setText(position + 1 + "" );
        viewHolder._ds_demirbas_kod.setText(dataModel.getDs_demirbas_kod());

        return convertView;
    }

}
