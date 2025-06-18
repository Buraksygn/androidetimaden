package com.etimaden.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.etimaden.persosclass.Gemi;
import com.etimaden.persosclass.GemiGelismeDurum;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;
import java.util.List;

public class apmblGemiGelismeBildirimListesi extends ArrayAdapter<GemiGelismeDurum> {
    private ArrayList<GemiGelismeDurum> dataSet;
    private List<GemiGelismeDurum> gelismeDurumListesi;
    Context mContext;

    public apmblGemiGelismeBildirimListesi(ArrayList<GemiGelismeDurum> data, Context context) {
        super(context, R.layout.liste_gemi_gelisme_bildirim, data);
        this.dataSet = data;
        this.gelismeDurumListesi = data;
        this.mContext=context;

    }

    @Override
    public int getCount() {
        return gelismeDurumListesi.size();
    }
    public long getItemId(int position) {
        return position;
    }
    private static class ViewHolder {
        TextView _sira;
        TextView _gelisme_durum;
        //TextView _gelisme_kod;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        apmblGemiGelismeBildirimListesi.ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new apmblGemiGelismeBildirimListesi.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.liste_gemi_gelisme_bildirim, parent, false);


            //viewHolder._sira = (TextView) convertView.findViewById(R.id.sira);
            viewHolder._gelisme_durum = (TextView) convertView.findViewById(R.id.txtgelisme_ad);
            //viewHolder._gelisme_kod = (TextView) convertView.findViewById(R.id.txtgelisme_kod);

            result=convertView;

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (apmblGemiGelismeBildirimListesi.ViewHolder) convertView.getTag();

            //result=convertView;
        }

        GemiGelismeDurum gelismeDurum = gelismeDurumListesi.get(position);
        //viewHolder._sira.setText(position + 1 + "" );
        viewHolder._gelisme_durum.setText(gelismeDurum.getGelismeDurum());
        //viewHolder._gelisme_kod.setText(gelismeDurum.getGelismeKod());

        return convertView;
    }

    public apmblGemiGelismeBildirimListesi(@NonNull Context context, int resource) {
        super(context, resource);
    }
}
