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

public class apmblAktifIsEmirleri extends ArrayAdapter<Urun_tag> {

    private ArrayList<Urun_tag> dataSet;
    Context mContext;

    public apmblAktifIsEmirleri(ArrayList<Urun_tag> data, Context context) {
        super(context, R.layout.liste_aktif_is_emirleri_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    private static class ViewHolder {
        TextView _sira;
        TextView _etiket_id;
        TextView _palet_id;
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
            convertView = inflater.inflate(R.layout.liste_aktif_is_emirleri_item, parent, false);


            viewHolder._sira = (TextView) convertView.findViewById(R.id.sira);
            viewHolder._etiket_id = (TextView) convertView.findViewById(R.id.etiket_id);
            viewHolder._palet_id = (TextView) convertView.findViewById(R.id.palet_id);

            result=convertView;

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();

            result=convertView;
        }


        viewHolder._sira.setText(position + "" );
        viewHolder._etiket_id.setText(dataModel.getKod());
        viewHolder._palet_id.setText(dataModel.getPalet_kod());


        return convertView;
    }

}
