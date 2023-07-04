package com.etimaden.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.etimaden.DataModel.mblBekleyenArac;
import com.etimaden.persosclass.DEPOTag;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;

public class apmblDepoListesi extends ArrayAdapter<DEPOTag> {

    private ArrayList<DEPOTag> dataSet;
    Context mContext;

    public apmblDepoListesi(ArrayList<DEPOTag> data, Context context) {
        super(context, R.layout.liste_depo_secimi, data);
        this.dataSet = data;
        this.mContext=context;

    }

    private static class ViewHolder {
        TextView _yazi_kod_depo_id;
        TextView _yazi_kod_depo_adi;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        DEPOTag dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.liste_depo_secimi, parent, false);


            viewHolder._yazi_kod_depo_id = (TextView) convertView.findViewById(R.id.yazi_kod_depo_id);
            viewHolder._yazi_kod_depo_adi = (TextView) convertView.findViewById(R.id.yazi_kod_depo_adi);

            result=convertView;

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();

            result=convertView;
        }


        viewHolder._yazi_kod_depo_id.setText(dataModel.getDepo_id());
        viewHolder._yazi_kod_depo_adi.setText(dataModel.getDepo_adi());

        return convertView;
    }

}
