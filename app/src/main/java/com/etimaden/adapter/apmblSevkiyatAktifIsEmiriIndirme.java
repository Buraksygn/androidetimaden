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
import com.etimaden.adapterclass.Zayi_urun_data;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;

public class apmblSevkiyatAktifIsEmiriIndirme extends ArrayAdapter<Urun_tag_data> {

    private ArrayList<Urun_tag_data> dataSet;
    Context mContext;

    public apmblSevkiyatAktifIsEmiriIndirme(ArrayList<Urun_tag_data> data, Context context) {
        super(context, R.layout.liste_sevkiyat_aktif_isemri_indirme_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    private static class ViewHolder {
        ImageView _img;
        TextView _sira;
        TextView _palet_kod;
        TextView _lotno;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Urun_tag_data dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.liste_sevkiyat_aktif_isemri_indirme_item, parent, false);

            viewHolder._img = (ImageView) convertView.findViewById(R.id.img);
            viewHolder._sira = (TextView) convertView.findViewById(R.id.sira);
            viewHolder._palet_kod = (TextView) convertView.findViewById(R.id.palet_kod);
            viewHolder._lotno = (TextView) convertView.findViewById(R.id.lotno);

            result=convertView;

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();

            result=convertView;
        }

        Drawable myDrawable = this.mContext.getResources().getDrawable(dataModel.getRowIconDrawable());
        viewHolder._img.setImageDrawable(myDrawable);
        viewHolder._sira.setText(position + 1 + "" );
        viewHolder._sira.setTextColor(dataModel.getRowColor());
        viewHolder._palet_kod.setText(dataModel.getUrun_tag().palet_kod);
        viewHolder._palet_kod.setTextColor(dataModel.getRowColor());
        viewHolder._lotno.setText(dataModel.getUrun_tag().getLotno());
        viewHolder._lotno.setTextColor(dataModel.getRowColor());

        return convertView;
    }

}
