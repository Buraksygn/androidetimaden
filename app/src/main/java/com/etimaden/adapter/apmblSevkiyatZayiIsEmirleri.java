package com.etimaden.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.etimaden.cResponseResult.Sevkiyat_isemri;
import com.etimaden.persosclass.Zayi;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;

public class apmblSevkiyatZayiIsEmirleri extends ArrayAdapter<Zayi> {

    private ArrayList<Zayi> dataSet;
    Context mContext;

    public apmblSevkiyatZayiIsEmirleri(ArrayList<Zayi> data, Context context) {
        super(context, R.layout.liste_sevkiyat_zayi_is_emirleri_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    private static class ViewHolder {
        TextView _sira;
        TextView _plaka;
        TextView _sap_kodu;
        TextView _urun_adi;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Zayi dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.liste_sevkiyat_zayi_is_emirleri_item, parent, false);


            viewHolder._sira = (TextView) convertView.findViewById(R.id.sira);
            viewHolder._plaka = (TextView) convertView.findViewById(R.id.plaka);
            viewHolder._sap_kodu = (TextView) convertView.findViewById(R.id.sap_kodu);
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
        viewHolder._plaka.setText(dataModel.zay_eski_plaka);
        viewHolder._sap_kodu.setText(dataModel.zay_sap_kodu);
        viewHolder._urun_adi.setText(dataModel.zay_urun_adi);

        return convertView;
    }

}
