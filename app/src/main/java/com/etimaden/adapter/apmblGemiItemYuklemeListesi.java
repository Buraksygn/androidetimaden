package com.etimaden.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.etimaden.persosclass.GemiGelismeDurum;
import com.etimaden.persosclass.Gemi_Sevkiyat;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class apmblGemiItemYuklemeListesi extends ArrayAdapter<Gemi_Sevkiyat> {

    private ArrayList<Gemi_Sevkiyat> dataSet;
    private List<Gemi_Sevkiyat> gemiAracListesi;
    Context mContext;
    public apmblGemiItemYuklemeListesi(ArrayList<Gemi_Sevkiyat> data, Context context) {
        super(context, R.layout.liste_item_gemi_yukleme, data);
        this.gemiAracListesi = data;
        this.mContext=context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        apmblGemiItemYuklemeListesi.ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new apmblGemiItemYuklemeListesi.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.liste_item_gemi_yukleme, parent, false);


            //viewHolder._sira = (TextView) convertView.findViewById(R.id.sira);
            viewHolder._arac_plaka = (TextView) convertView.findViewById(R.id.txtAracPlaka);
            viewHolder._spinnerHasar =(Spinner) convertView.findViewById(R.id.spinnerHasar);
            viewHolder._spinnerHasar.setBackgroundColor(Color.DKGRAY);

            List<String> hasarListesi = Arrays.asList("Seçiniz","Cam Kırık", "Çizik", "Lastik Patlak", "Diğer");
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                    getContext(),
                    R.layout.spinner_item,
                    hasarListesi
            );
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            viewHolder._spinnerHasar.setAdapter(spinnerAdapter);

            result=convertView;

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (apmblGemiItemYuklemeListesi.ViewHolder) convertView.getTag();
        }

        Gemi_Sevkiyat aracListesi = gemiAracListesi.get(position);
        //viewHolder._sira.setText(position + 1 + "" );
        viewHolder._arac_plaka.setText(aracListesi.getAra_plaka());

        return convertView;
    }

    private static class ViewHolder {
        TextView _sira;
        TextView _arac_plaka;
        Spinner _spinnerHasar;
    }

}
