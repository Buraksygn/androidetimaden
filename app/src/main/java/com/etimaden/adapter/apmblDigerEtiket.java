package com.etimaden.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.etimaden.DataModel.mblDigerEtiket;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;

public class apmblDigerEtiket extends ArrayAdapter<mblDigerEtiket> {

    private ArrayList<mblDigerEtiket> dataSet;
    Context mContext;

    public apmblDigerEtiket(ArrayList<mblDigerEtiket> data, Context context) {
        super(context, R.layout.liste_digeretiket, data);
        this.dataSet = data;
        this.mContext=context;

    }

    private static class ViewHolder {
        TextView _yazi_kod_sira;
        TextView _yazi_kod;
        TextView _yazi_durum;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        mblDigerEtiket dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.liste_digeretiket, parent, false);

            viewHolder._yazi_kod_sira  = (TextView) convertView.findViewById(R.id.yazi_kod_sira);
            viewHolder._yazi_kod = (TextView) convertView.findViewById(R.id.yazi_kod);
            viewHolder._yazi_durum = (TextView) convertView.findViewById(R.id.yazi_durum);


            result=convertView;

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();

            result=convertView;
        }

        viewHolder._yazi_kod_sira.setText(dataModel.getsirano());
        viewHolder._yazi_kod.setText(dataModel.getkod());
        viewHolder._yazi_durum.setText(dataModel.getdurum());

        return convertView;
    }


}
