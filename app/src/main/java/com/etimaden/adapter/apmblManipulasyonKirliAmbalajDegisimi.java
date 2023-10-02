package com.etimaden.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.etimaden.persosclass.Urun_tag;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;

public class apmblManipulasyonKirliAmbalajDegisimi extends ArrayAdapter<Urun_tag> {

    private ArrayList<Urun_tag> dataSet;
    Context mContext;

    public apmblManipulasyonKirliAmbalajDegisimi(ArrayList<Urun_tag> data, Context context) {
        super(context, R.layout.liste_manipulasyon_kirli_ambalaj_degisimi_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    private static class ViewHolder {
        TextView _sira;
        TextView _kod;
        TextView _aciklama;
        TextView _palet_kod;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Urun_tag dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.liste_manipulasyon_kirli_ambalaj_degisimi_item, parent, false);

            viewHolder._sira = (TextView) convertView.findViewById(R.id.sira);
            viewHolder._kod = (TextView) convertView.findViewById(R.id.kod);
            viewHolder._aciklama = (TextView) convertView.findViewById(R.id.aciklama);
            viewHolder._palet_kod = (TextView) convertView.findViewById(R.id.palet_kod);
            result=convertView;

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();

            result=convertView;
        }

        viewHolder._sira.setText(position + 1 + "" );
        viewHolder._kod.setText(dataModel.getKod());
        viewHolder._aciklama.setText(dataModel.aciklama);
        viewHolder._palet_kod.setText(dataModel.palet_kod);

        return convertView;
    }

}
