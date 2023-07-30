package com.etimaden.SevkiyatIslemleri;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etimaden.DataModel.mblDigerEtiket;
import com.etimaden.adapter.apmblDigerEtiket;
import com.etimaden.cIslem.VeriTabani;
import com.etimaden.ugr_demo.R;

import java.util.ArrayList;
import java.util.HashMap;

public class frg_satilmis_tum_liste extends Fragment {

    VeriTabani _myIslem;

    public ListView _Liste;

    Button _btngeri;

    apmblDigerEtiket _Adapter;

    ArrayList<mblDigerEtiket> dataModels;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_satilmis_tum_liste , container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        _myIslem = new VeriTabani(getContext());

        _btngeri = (Button)getView().findViewById(R.id.btncikis);
        _btngeri.playSoundEffect(0);
        _btngeri.setOnClickListener(new fn_Geri());

        _Liste = (ListView) getView().findViewById(R.id.liste_satilmis_etiket);

        fn_Listele();

    }

    private void fn_Listele()
    {
        ArrayList<HashMap<String, String>> _Dizim =   _myIslem.fn_DigerEtiketListele();

        dataModels= new ArrayList<>();

        int Toplam = _Dizim.size();

        for(int intSayac = 0;intSayac<Toplam;intSayac++)
        {
            dataModels.add(new mblDigerEtiket(
                    (Toplam-(intSayac)),
                    _Dizim.get(intSayac).get("epc")+"",
                    _Dizim.get(intSayac).get("durum")+"",
                    _Dizim.get(intSayac).get("urunadi")+""));
        }

        _Adapter= new apmblDigerEtiket(dataModels,getContext());

        _Liste.setAdapter(_Adapter);


    }

    private class fn_Geri implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            frg_satilmis_etiket fragmentyeni = new frg_satilmis_etiket();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutForFragments, fragmentyeni,"frg_satilmis_etiket").addToBackStack(null);
            fragmentTransaction.commit();

        }
    }
}
