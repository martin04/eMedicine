package com.diplomska.emed.martin.e_medicine.splashscrn;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.diplomska.emed.martin.e_medicine.ActionsActivity;
import com.diplomska.emed.martin.e_medicine.MainActivity;
import com.diplomska.emed.martin.e_medicine.R;


/**
 * Created by Martin on 15-Jun-15.
 */
public class SplashScreen extends Activity {

    private static int TIME_OUT=1250;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, ActionsActivity.class);
                startActivity(i);
                finish();
            }
        },TIME_OUT);

    }
}
