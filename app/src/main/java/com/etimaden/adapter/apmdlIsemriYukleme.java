package com.etimaden.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.etimaden.DataModel.mdlIsemriYukleme;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;

public class apmdlIsemriYukleme extends ArrayAdapter<mdlIsemriYukleme> {

    private ArrayList<mdlIsemriYukleme> dataSet;
    Context mContext;

    private static class ViewHolder {
        TextView _yazi_sira;
        TextView _yazi_bos;
        TextView _yazi_kod;
        TextView _yazi_lotno;
    }

    public apmdlIsemriYukleme(ArrayList<mdlIsemriYukleme> data, Context context) {
        super(context, R.layout.liste_yuklenen_etiket, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        mdlIsemriYukleme dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.liste_yuklenen_etiket, parent, false);

            viewHolder._yazi_kod = (TextView) convertView.findViewById(R.id.yazi_kod);
            viewHolder._yazi_bos = (TextView) convertView.findViewById(R.id.yazi_bos);
            viewHolder._yazi_lotno = (TextView) convertView.findViewById(R.id.yazi_lotno);
            viewHolder._yazi_sira = (TextView) convertView.findViewById(R.id.yazi_sira);

            result=convertView;

            convertView.setTag(viewHolder);
        }
        else
            {
            viewHolder = (ViewHolder) convertView.getTag();

            result=convertView;
        }

        viewHolder._yazi_kod.setText(dataModel.getkod());
        viewHolder._yazi_sira.setText(dataModel.getsirano());
        viewHolder._yazi_lotno.setText(dataModel.getlotno());
        viewHolder._yazi_bos.setText(dataModel.getbos());

        return convertView;
    }
}
