package com.etimaden.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.etimaden.persosclass.IFTag;
import com.etimaden.persosclass.Urun_tag;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;

public class apmblSayimIslemleriSayimIslemiUrunAktivasyon extends ArrayAdapter<Urun_tag> {

    private ArrayList<Urun_tag> dataSet;
    Context mContext;

    public apmblSayimIslemleriSayimIslemiUrunAktivasyon(ArrayList<Urun_tag> data, Context context) {
        super(context, R.layout.liste_sayim_islemleri_sayim_islemi_urun_aktivasyon_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    private static class ViewHolder {
        TextView _sira;
        TextView _kod;
        TextView _lot_no;
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
            convertView = inflater.inflate(R.layout.liste_sayim_islemleri_sayim_islemi_urun_aktivasyon_item, parent, false);


            viewHolder._sira = (TextView) convertView.findViewById(R.id.sira);
            viewHolder._kod = (TextView) convertView.findViewById(R.id.kod);
            viewHolder._lot_no = (TextView) convertView.findViewById(R.id.lot_no);

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
        viewHolder._lot_no.setText(dataModel.getLotno());

        return convertView;
    }

}
