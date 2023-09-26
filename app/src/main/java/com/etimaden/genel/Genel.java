package com.etimaden.genel;

import android.content.Context;
import android.media.AudioManager;
import android.view.SoundEffectConstants;

import com.etimaden.ugr_demo.R;

import java.util.Calendar;


public class Genel {

    private  static SweetAlertDialogG pDialog;
    private  static Long milis;

    public static void showProgressDialog(Context context) {
        milis= Calendar.getInstance().getTimeInMillis();
        pDialog = new SweetAlertDialogG(context, SweetAlertDialogG.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.colorPrimary));
        pDialog.setTitleText("Yükleniyor...");
        pDialog.setCancelable(false);
        pDialog.show();

    }

    public static void dismissProgressDialog() {
        Long currMilis=Calendar.getInstance().getTimeInMillis();
        if(currMilis-milis<100){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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

    public static void printStackTrace(Exception ex,Context context){
        Boolean showMessages=false;
        dismissProgressDialog();
        if(showMessages){
            new SweetAlertDialogG(context, SweetAlertDialogG.ERROR_TYPE)
                    .setTitleText("HATA OLUŞTU")
                    .setContentTextSize(25)
                    .setContentText(ex.getMessage())
                    .showCancelButton(false)
                    .show();
        }else{
            ex.printStackTrace();
        }
    }
}
