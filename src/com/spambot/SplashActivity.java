package com.spambot;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;



public class SplashActivity extends Activity {
    protected boolean _active = true;
    protected int _splashTime = 1500;
   SharedPreferences pref;
    SharedPreferences.Editor editor;
    boolean entry =false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        initUISetup();
    }

    private void initUISetup() {
    	
    	RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.splashlayout);
         mainLayout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent myIntent = new Intent(SplashActivity.this, MainActivity.class);
                 finish();
                 startActivity(myIntent);
             }
         });
       /* Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (_active && (waited < _splashTime)) {
                        sleep(100);
                        if (_active) {
                            waited += 100;
                        }
                    }
                } catch (InterruptedException e) {
                } finally {

               	 	 Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
            	                 }
            }
        };
        splashTread.start();*/
    }

}
