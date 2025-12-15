package com.etimaden.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.etimaden.persosclass.Gemi_Sevkiyat;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class apmblGemiItemYuklemeListesi extends ArrayAdapter<Gemi_Sevkiyat> {

    private ArrayList<Gemi_Sevkiyat> dataSet;
    private List<Gemi_Sevkiyat> gemiAracListesi;
    Context mContext;

    public interface OnHasarChangeListener {
        void onHasarChanged(Gemi_Sevkiyat arac, int position, String yeniHasar);
    }

    private OnHasarChangeListener hasarChangeListener;

    public apmblGemiItemYuklemeListesi(ArrayList<Gemi_Sevkiyat> data, Context context) {
        super(context, R.layout.liste_item_gemi_yukleme, data);
        this.gemiAracListesi = data;
        this.mContext = context;
    }

    public void setOnHasarChangeListener(OnHasarChangeListener listener) {
        this.hasarChangeListener = listener;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.liste_item_gemi_yukleme, parent, false);

            viewHolder._arac_plaka = (TextView) convertView.findViewById(R.id.txtAracPlaka);
            viewHolder._spinnerHasar = (Spinner) convertView.findViewById(R.id.spinnerHasar);

            final List<String> hasarListesi = Arrays.asList(
                    "Hasarsız",
                    "Çizik",
                    "Kırık",
                    "Ezik",
                    "Boyalı",
                    "Cam Kırık",
                    "Ayna Eksik",
                    "Diğer"
            );

            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                    getContext(),
                    android.R.layout.simple_spinner_item,
                    hasarListesi
            );
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            viewHolder._spinnerHasar.setAdapter(spinnerAdapter);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Gemi_Sevkiyat arac = gemiAracListesi.get(position);
        final ViewHolder finalViewHolder = viewHolder;

        viewHolder._arac_plaka.setText(arac.getAra_plaka() + " - " + arac.getSofor_adi());

        viewHolder._spinnerHasar.setOnItemSelectedListener(null);

        String mevcutHasar = arac.getHasar_durumu();
        if (mevcutHasar == null || mevcutHasar.isEmpty() || mevcutHasar.equals("0")) {
            viewHolder._spinnerHasar.setSelection(0); // Hasarsız
            convertView.setBackgroundColor(Color.WHITE);
        } else {
            List<String> hasarListesi = Arrays.asList(
                    "Hasarsız", "Çizik", "Kırık", "Ezik", "Boyalı", "Cam Kırık", "Ayna Eksik", "Diğer"
            );
            int index = hasarListesi.indexOf(mevcutHasar);
            if (index != -1) {
                viewHolder._spinnerHasar.setSelection(index);
            } else {
                viewHolder._spinnerHasar.setSelection(0);
            }
            convertView.setBackgroundColor(Color.parseColor("#FFCCCC"));
        }

        viewHolder._spinnerHasar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            private boolean ilkTetikleme = true;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int spinnerPosition, long id) {
                if (ilkTetikleme) {
                    ilkTetikleme = false;
                    return;
                }

                String secilenHasar = parent.getItemAtPosition(spinnerPosition).toString();

                if (secilenHasar.equals("Hasarsız")) {
                    secilenHasar = "";
                }

                String mevcutHasar = arac.getHasar_durumu();
                if (mevcutHasar == null) mevcutHasar = "";
                
                if (!secilenHasar.equals(mevcutHasar)) {
                    if (hasarChangeListener != null) {
                        hasarChangeListener.onHasarChanged(arac, position, secilenHasar);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return convertView;
    }

    private static class ViewHolder {
        TextView _arac_plaka;
        Spinner _spinnerHasar;
    }
}