package com.etimaden.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.etimaden.persosclass.Zayi;
import com.etimaden.persosclass.cBekleyen_Arac_Listesi;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;

public class apmblSevkiyatBekleyenAracListesi extends ArrayAdapter<cBekleyen_Arac_Listesi> {

    private ArrayList<cBekleyen_Arac_Listesi> dataSet;
    Context mContext;

    public apmblSevkiyatBekleyenAracListesi(ArrayList<cBekleyen_Arac_Listesi> data, Context context) {
        super(context, R.layout.liste_sevkiyat_bekleyen_arac_listesi_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    private static class ViewHolder {
        TextView _sira;
        TextView _skolon1;
        TextView _skolon2;
        TextView _skolon3;
        TextView _skolon4;
        TextView _skolon5;
        TextView _skolon6;
        TextView _skolon7;
        TextView _skolon8;
        TextView _skolon9;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        cBekleyen_Arac_Listesi dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.liste_sevkiyat_bekleyen_arac_listesi_item, parent, false);


            viewHolder._sira = (TextView) convertView.findViewById(R.id.sira);
            viewHolder._skolon1 = (TextView) convertView.findViewById(R.id.skolon1);
            viewHolder._skolon2 = (TextView) convertView.findViewById(R.id.skolon2);
            viewHolder._skolon3 = (TextView) convertView.findViewById(R.id.skolon3);
            viewHolder._skolon4 = (TextView) convertView.findViewById(R.id.skolon4);
            viewHolder._skolon5 = (TextView) convertView.findViewById(R.id.skolon5);
            viewHolder._skolon6 = (TextView) convertView.findViewById(R.id.skolon6);
            viewHolder._skolon7 = (TextView) convertView.findViewById(R.id.skolon7);
            viewHolder._skolon8 = (TextView) convertView.findViewById(R.id.skolon8);
            viewHolder._skolon9 = (TextView) convertView.findViewById(R.id.skolon9);

            result=convertView;

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();

            result=convertView;
        }


        viewHolder._sira.setText(position + "" );
        viewHolder._skolon1.setText(dataModel.skolon1);
        viewHolder._skolon2.setText(dataModel.skolon2);
        viewHolder._skolon3.setText(dataModel.skolon3);
        viewHolder._skolon4.setText(dataModel.skolon4);
        viewHolder._skolon5.setText(dataModel.skolon5);
        viewHolder._skolon6.setText(dataModel.skolon6);
        viewHolder._skolon7.setText(dataModel.skolon7);
        viewHolder._skolon8.setText(dataModel.skolon8);
        viewHolder._skolon9.setText(dataModel.skolon9);

        return convertView;
    }

}
