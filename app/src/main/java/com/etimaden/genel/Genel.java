package com.etimaden.genel;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.SystemClock;
import android.view.SoundEffectConstants;
import android.view.View;

import com.etimaden.ugr_demo.R;


public class Genel {

    private  static SweetAlertDialogG pDialog;

    public static void showProgressDialog(Context context) {
        dismissProgressDialog();
        pDialog = new SweetAlertDialogG(context, SweetAlertDialogG.PROGRESS_TYPE);
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
            audioManager.playSoundEffect(AudioManager.FX_KEY_CLICK);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playQuestionSound(Context context){
        try {
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            audioManager.playSoundEffect(AudioManager.FX_FOCUS_NAVIGATION_RIGHT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printStackTrace(Exception ex,Context context){
        Boolean showMessages=false;
        dismissProgressDialog();
        if(showMessages==true){
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

    public static void lockButtonClick(final View view, final Activity activity){
        try {
            if (view != null) {
                view.setEnabled(false);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(1000);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    view.setEnabled(true);
                                }catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        });

                    }
                }).start();
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
