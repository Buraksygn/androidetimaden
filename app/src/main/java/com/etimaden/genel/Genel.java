package com.etimaden.genel;

import android.content.Context;
import android.media.AudioManager;
import android.view.SoundEffectConstants;

import com.etimaden.ugr_demo.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Genel {

    private  static SweetAlertDialog pDialog;

    public static void showProgressDialog(Context context) {
        pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.colorPrimary));
        pDialog.setTitleText("Yükleniyor...");
        pDialog.setCancelable(false);
        pDialog.show();

    }

    public static void dismissProgressDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
            pDialog.hide();
        }

    }

    public static void playButtonClikSound(Context context){
        try {
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            audioManager.playSoundEffect(SoundEffectConstants.CLICK);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playQuestionSound(Context context){
        try {
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            audioManager.playSoundEffect(SoundEffectConstants.NAVIGATION_DOWN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
