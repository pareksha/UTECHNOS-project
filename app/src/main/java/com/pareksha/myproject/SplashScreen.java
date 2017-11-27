package com.pareksha.myproject;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pareksha.myproject.R;

public class SplashScreen extends AppCompatActivity {

    int SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent mainIntent = new Intent(SplashScreen.this, com.pareksha.myproject.ScreenOne.class);
                SplashScreen.this.startActivity(mainIntent);
                SplashScreen.this.finish();

            }
        },SPLASH_DISPLAY_LENGTH);

    }
}
