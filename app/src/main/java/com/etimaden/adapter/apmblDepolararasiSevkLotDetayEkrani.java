package com.etimaden.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.etimaden.persosclass.Urun_tag;
import com.etimaden.persosclass.lot_detay;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;

public class apmblDepolararasiSevkLotDetayEkrani extends ArrayAdapter<lot_detay> {

    private ArrayList<lot_detay> dataSet;
    Context mContext;

    public apmblDepolararasiSevkLotDetayEkrani(ArrayList<lot_detay> data, Context context) {
        super(context, R.layout.liste_depolararasi_sevk_lot_detay_ekrani_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    private static class ViewHolder {
        TextView _sira;
        TextView _serino;
        TextView _durum;
        TextView _serino_isletme;
        TextView _serino_lot;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        lot_detay dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.liste_depolararasi_sevk_lot_detay_ekrani_item, parent, false);

            viewHolder._sira = (TextView) convertView.findViewById(R.id.sira);
            viewHolder._serino = (TextView) convertView.findViewById(R.id.serino);
            viewHolder._durum = (TextView) convertView.findViewById(R.id.durum);
            viewHolder._serino_isletme = (TextView) convertView.findViewById(R.id.serino_isletme);
            viewHolder._serino_lot = (TextView) convertView.findViewById(R.id.serino_lot);
            result=convertView;

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();

            result=convertView;
        }

        viewHolder._sira.setText(position + 1 + "" );
        viewHolder._serino.setText(dataModel.getSerino());
        viewHolder._durum.setText(dataModel.getDurum());
        viewHolder._serino_isletme.setText(dataModel.getSerino_isletme());
        viewHolder._serino_lot.setText(dataModel.getSerino_lot());

        return convertView;
    }

}
