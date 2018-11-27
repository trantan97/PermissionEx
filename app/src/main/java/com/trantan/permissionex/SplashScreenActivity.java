package com.trantan.permissionex;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {
    private static final int SPLASH_DELAY = 1000;

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = MainActivity.getProfileIntent(SplashScreenActivity.this);
                startActivity(intent);
                finish();
            }
        }, SPLASH_DELAY);
    }
}
