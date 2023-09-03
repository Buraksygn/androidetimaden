package com.etimaden.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.etimaden.persosclass.uretim_detay;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;

public class apmbluretimdetayAktifIsEmirleri extends ArrayAdapter<uretim_detay> {

    private ArrayList<uretim_detay> dataSet;
    Context mContext;

    public apmbluretimdetayAktifIsEmirleri(ArrayList<uretim_detay> data, Context context) {
        super(context, R.layout.liste_uretim_sorgulama_aktif_is_emirleri_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    private static class ViewHolder {
        TextView _sira;
        TextView _uretim_lot;
        TextView _uretim_adet;
        TextView _uretim_bekleyen_adet;
        TextView _uretim_birim_miktar;
        TextView _uretim_miktar;
        TextView _uretim_vardiya;
        TextView _uretim_urun_ad;
        TextView _uretim_urun_depo;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        uretim_detay dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.liste_uretim_sorgulama_aktif_is_emirleri_item, parent, false);


            viewHolder._sira = (TextView) convertView.findViewById(R.id.sira);
            viewHolder._uretim_lot = (TextView) convertView.findViewById(R.id.uretim_lot);
            viewHolder._uretim_adet = (TextView) convertView.findViewById(R.id.uretim_adet);
            viewHolder._uretim_bekleyen_adet = (TextView) convertView.findViewById(R.id.uretim_bekleyen_adet);
            viewHolder._uretim_birim_miktar = (TextView) convertView.findViewById(R.id.uretim_birim_miktar);
            viewHolder._uretim_miktar = (TextView) convertView.findViewById(R.id.uretim_miktar);
            viewHolder._uretim_vardiya = (TextView) convertView.findViewById(R.id.uretim_vardiya);
            viewHolder._uretim_urun_ad = (TextView) convertView.findViewById(R.id.uretim_urun_ad);
            viewHolder._uretim_urun_depo = (TextView) convertView.findViewById(R.id.uretim_urun_depo);

            result=convertView;

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();

            result=convertView;
        }


        viewHolder._sira.setText(position + 1 + "" );
        viewHolder._uretim_lot.setText(dataModel.getUretim_lot());
        viewHolder._uretim_adet.setText(dataModel.getUretim_adet());

        viewHolder._uretim_bekleyen_adet.setText(dataModel.getUretim_adet());
        viewHolder._uretim_birim_miktar.setText(dataModel.getUretim_birim_miktar());
        viewHolder._uretim_miktar.setText(dataModel.getUretim_miktar());
        viewHolder._uretim_vardiya.setText(dataModel.getUretim_vardiya());
        viewHolder._uretim_urun_ad.setText(dataModel.getUretim_urun_ad());
        viewHolder._uretim_urun_depo.setText(dataModel.getUretim_urun_depo());


        return convertView;
    }

}
