package com.etimaden.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.etimaden.cResponseResult.Sevkiyat_isemri;
import com.etimaden.persosclass.Urun_tag;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;

public class apmblSevkiyatAktifIsEmirleri extends ArrayAdapter<Sevkiyat_isemri> {

    private ArrayList<Sevkiyat_isemri> dataSet;
    Context mContext;

    public apmblSevkiyatAktifIsEmirleri(ArrayList<Sevkiyat_isemri> data, Context context) {
        super(context, R.layout.liste_sevkiyat_aktif_is_emirleri_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    private static class ViewHolder {
        TextView _sira;
        TextView _silo;
        TextView _silo_adi;
        TextView _urun_adi;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Sevkiyat_isemri dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.liste_sevkiyat_aktif_is_emirleri_item, parent, false);


            viewHolder._sira = (TextView) convertView.findViewById(R.id.sira);
            viewHolder._silo = (TextView) convertView.findViewById(R.id.silo);
            viewHolder._silo_adi = (TextView) convertView.findViewById(R.id.silo_adi);
            viewHolder._urun_adi = (TextView) convertView.findViewById(R.id.urun_adi);

            result=convertView;

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();

            result=convertView;
        }


        viewHolder._sira.setText(position + 1 + "" );
        viewHolder._silo.setText(dataModel.depo_kodu);
        viewHolder._silo_adi.setText(dataModel.depo_adi);
        viewHolder._urun_adi.setText(dataModel.urun_adi);

        return convertView;
    }

}
