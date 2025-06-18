package com.etimaden.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.etimaden.persosclass.Gemi;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;
import java.util.List;

public class apmblGemiListesi extends ArrayAdapter<Gemi> {

    private ArrayList<Gemi> dataSet;
    private List<Gemi> orjinalListe;
    private List<Gemi> filtreliListe;
    Context mContext;

    public apmblGemiListesi(ArrayList<Gemi> data, Context context) {
        super(context, R.layout.liste_aktif_gemi_secimi, data);
        //this.dataSet = data;
        this.orjinalListe = data;
        this.filtreliListe = new ArrayList<>(data);
        this.mContext=context;

    }

    @Override
    public int getCount() {
        return filtreliListe.size();
    }

    @Nullable
    @Override
    public Gemi getItem(int position) {
        return filtreliListe.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        TextView _sira;
        TextView _gemi_kod;
        TextView _gemi_adi;
    }
    public apmblGemiListesi(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Gemi dataModel = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new apmblGemiListesi.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.liste_aktif_gemi_secimi, parent, false);


            viewHolder._sira = (TextView) convertView.findViewById(R.id.sira);
            //viewHolder._gemi_kod = (TextView) convertView.findViewById(R.id.gemi_kod);
            viewHolder._gemi_adi = (TextView) convertView.findViewById(R.id.gemi_ad);

            result=convertView;

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (apmblGemiListesi.ViewHolder) convertView.getTag();

            //result=convertView;
        }

        Gemi gemi = filtreliListe.get(position);
        viewHolder._sira.setText(position + 1 + "" );
        //viewHolder._gemi_kod.setText(gemi.getGemiKod());
        viewHolder._gemi_adi.setText(gemi.getGemiAd());

        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Gemi> filteredResults = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredResults.addAll(orjinalListe);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (Gemi gemi : orjinalListe) {
                        if (gemi.getGemiAd().toLowerCase().contains(filterPattern) ||
                                gemi.getGemiKod().toLowerCase().contains(filterPattern)) {
                            filteredResults.add(gemi);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredResults;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filtreliListe.clear();
                filtreliListe.addAll((List<Gemi>) results.values);
                notifyDataSetChanged();
            }
        };
    }
}
