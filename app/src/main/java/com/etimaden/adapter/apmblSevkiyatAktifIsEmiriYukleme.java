package com.etimaden.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.etimaden.adapterclass.Urun_tag_data;
import com.etimaden.cResponseResult.Urun_sevkiyat;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;

public class apmblSevkiyatAktifIsEmiriYukleme extends ArrayAdapter<Urun_sevkiyat> {

    private ArrayList<Urun_sevkiyat> dataSet;
    Context mContext;

    public apmblSevkiyatAktifIsEmiriYukleme(ArrayList<Urun_sevkiyat> data, Context context) {
        super(context, R.layout.liste_sevkiyat_aktif_isemri_yukleme_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    private static class ViewHolder {
        TextView _sira;
        TextView _kod;
        TextView _lotno;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Urun_sevkiyat dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.liste_sevkiyat_aktif_isemri_yukleme_item, parent, false);

            viewHolder._sira = (TextView) convertView.findViewById(R.id.sira);
            viewHolder._kod = (TextView) convertView.findViewById(R.id.kod);
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
        viewHolder._kod.setText(dataModel.kod);
        viewHolder._lotno.setText(dataModel.lotno);

        return convertView;
    }

}
