package com.etimaden.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.etimaden.persosclass.GemiGelismeDurum;
import com.etimaden.persosclass.Gemi_Sevkiyat;
import com.etimaden.persosclass.Gemi_Teslimat;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;
import java.util.List;

public class apmblGemiSevkiyatIsemriListesi extends ArrayAdapter<Gemi_Teslimat> {

    private ArrayList<Gemi_Teslimat> dataSet;
    private List<Gemi_Teslimat> sevkiyatIsemriListesi;
    Context mContext;
    public apmblGemiSevkiyatIsemriListesi(ArrayList<Gemi_Teslimat> data, Context context) {
        super(context, R.layout.liste_gemi_sevkiyat_isemri,data);
        this.dataSet = data;
        this.sevkiyatIsemriListesi = data;
        this.mContext=context;

    }

    @Override
    public int getCount() {
        return sevkiyatIsemriListesi.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        TextView _sira;
        TextView _sap_kodu;
        TextView _urun_adi;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        apmblGemiSevkiyatIsemriListesi.ViewHolder viewHolder;

        final View result;

        if(convertView == null){
            viewHolder = new apmblGemiSevkiyatIsemriListesi.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.liste_gemi_sevkiyat_isemri, parent, false);

            viewHolder._sira = (TextView) convertView.findViewById(R.id.txtgemi_sira);
            viewHolder._sap_kodu = (TextView) convertView.findViewById(R.id.txtgemi_sapKodu);
            viewHolder._urun_adi = (TextView) convertView.findViewById(R.id.txtgemi_urunAdi);

            result = convertView;

            convertView.setTag(viewHolder);
        }else{
            viewHolder =(apmblGemiSevkiyatIsemriListesi.ViewHolder) convertView.getTag();
        }

        Gemi_Teslimat gemiSevkiyat = sevkiyatIsemriListesi.get(position);
        viewHolder._sira.setText(position + 1 + "");
        viewHolder._sap_kodu.setText(gemiSevkiyat.getIse_sap_kod());
        viewHolder._urun_adi.setText(gemiSevkiyat.getUrun_ad());

        return convertView;
    }
}
