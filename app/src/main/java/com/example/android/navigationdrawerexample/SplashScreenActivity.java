package com.example.android.navigationdrawerexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;


public class SplashScreenActivity extends Activity {

        ProgressBar pbar;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            // TODO Auto-generated method stub
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash_screen);
            pbar = (ProgressBar) findViewById(R.id.progressBar1);
            pbar.setVisibility(View.VISIBLE);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                        Intent intent=new Intent(SplashScreenActivity.this,MainActivity.class);
                        startActivity(intent);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }
}
