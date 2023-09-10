package com.etimaden.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.etimaden.adapterclass.Zayi_urun_data;
import com.etimaden.persosclass.Zayi;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;

public class apmblSevkiyatZayiIsEmiriIndirme extends ArrayAdapter<Zayi_urun_data> {

    private ArrayList<Zayi_urun_data> dataSet;
    Context mContext;

    public apmblSevkiyatZayiIsEmiriIndirme(ArrayList<Zayi_urun_data> data, Context context) {
        super(context, R.layout.liste_sevkiyat_zayi_isemri_indirme_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    private static class ViewHolder {
        TextView _sira;
        TextView _serino;
        TextView _lotno;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Zayi_urun_data dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.liste_sevkiyat_zayi_isemri_indirme_item, parent, false);


            viewHolder._sira = (TextView) convertView.findViewById(R.id.sira);
            viewHolder._serino = (TextView) convertView.findViewById(R.id.serino);
            viewHolder._lotno = (TextView) convertView.findViewById(R.id.lotno);

            result=convertView;

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();

            result=convertView;
        }


        viewHolder._sira.setText(position + "" );
        viewHolder._sira.setTextColor(dataModel.getRowColor());
        viewHolder._serino.setText(dataModel.getZayi_urun().serino);
        viewHolder._serino.setTextColor(dataModel.getRowColor());
        viewHolder._lotno.setText(dataModel.getZayi_urun().getLotno());
        viewHolder._lotno.setTextColor(dataModel.getRowColor());

        return convertView;
    }

}
