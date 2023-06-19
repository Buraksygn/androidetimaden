package com.etimaden.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.etimaden.DataModel.mblBekleyenArac;
import com.etimaden.DataModel.mdlIsemriYukleme;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;

public class apmblBekleyenArac extends ArrayAdapter<mblBekleyenArac> {

    private ArrayList<mblBekleyenArac> dataSet;
    Context mContext;

    public apmblBekleyenArac(ArrayList<mblBekleyenArac> data, Context context) {
        super(context, R.layout.liste_bekleyen_arac, data);
        this.dataSet = data;
        this.mContext=context;

    }

    private static class ViewHolder {
        TextView _yazi_arac_plaka;
        TextView _yazi_sira_no;
        TextView _yazi_konteyner_plaka;
        TextView _yazi_isemri;
        TextView _yazi_arac_rfid;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        mblBekleyenArac dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.liste_bekleyen_arac, parent, false);

            viewHolder._yazi_sira_no  = (TextView) convertView.findViewById(R.id.yazi_sira_no);
            viewHolder._yazi_arac_plaka = (TextView) convertView.findViewById(R.id.yazi_arac_plaka);
            viewHolder._yazi_konteyner_plaka = (TextView) convertView.findViewById(R.id.yazi_konteyner_plaka);
            viewHolder._yazi_isemri = (TextView) convertView.findViewById(R.id.yazi_isemri);
            viewHolder._yazi_arac_rfid = (TextView) convertView.findViewById(R.id.yazi_arac_rfid);

            result=convertView;

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();

            result=convertView;
        }

        viewHolder._yazi_arac_plaka.setText(dataModel.getplaka());
        viewHolder._yazi_sira_no.setText(dataModel.getsirano());
        viewHolder._yazi_konteyner_plaka.setText(dataModel.getkonteyner());
        viewHolder._yazi_isemri.setText(dataModel.getisemri());
        viewHolder._yazi_arac_rfid.setText(dataModel.getrfidkod());

        return convertView;
    }
}
