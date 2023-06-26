package com.etimaden.SevkiyatIslemleri;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.etimaden.UretimIslemleri.frg_silo_secimi;
import com.etimaden.ugr_demo.R;

import java.util.Timer;
import java.util.TimerTask;

public class frg_timer_test extends Fragment {


    Button _btnBaslat;
    Button _btnDurdur;

    EditText _txtYazi;

    int _iSayac=0;

    CountDownTimer countDownTimer;

    public frg_timer_test() {
        // Required empty public constructor
    }

    public static frg_timer_test newInstance()
    {
        return new frg_timer_test();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_timer_test, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        _btnBaslat=(Button)getView().findViewById(R.id.btnBaslat);
        _btnBaslat.playSoundEffect(0);
        _btnBaslat.setOnClickListener(new fn_btnBaslat());

        _btnDurdur=(Button)getView().findViewById(R.id.btnDurdur);
        _btnDurdur.playSoundEffect(0);
        _btnDurdur.setOnClickListener(new fn_btnDurdur());

        _txtYazi = (EditText)getView().findViewById(R.id.txtYazi);


        countDownTimer = new CountDownTimer(600000, 1000) {
            @Override
            public void onTick(long l) {

                _iSayac += 1;

                _txtYazi.setText(_iSayac+"");
            }

            @Override
            public void onFinish() {
                // btn_basla_dur.setText("başla");

            }
        };

    }


    private class fn_btnBaslat implements View.OnClickListener {
        @Override
        public void onClick(View v)
        {
            fn_SayacBaslat();
        }
    }

    private void fn_SayacBaslat()
    {

        countDownTimer.start();
    }

    private class fn_btnDurdur implements View.OnClickListener {
        @Override
        public void onClick(View v)
        {
            countDownTimer.cancel();
        }
    }
}
