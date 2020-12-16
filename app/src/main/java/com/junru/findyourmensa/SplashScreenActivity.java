package com.junru.findyourmensa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.view.View;
import android.os.Bundle;
import android.graphics.Color;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EasySplashScreen config = new EasySplashScreen(SplashScreenActivity.this)
                .withFullScreen()
                .withTargetActivity(MapsActivity.class)
                .withSplashTimeOut(3000)
                .withBackgroundColor(Color.parseColor("#ffffff"))
                .withAfterLogoText("Find Your Mensa")
                .withLogo(R.drawable.logo_mensa);


        config.getAfterLogoTextView().setTextColor(Color.parseColor("#FABD41"));
        config.getAfterLogoTextView().setTextSize(36);
        config.getAfterLogoTextView().setTypeface(ResourcesCompat.getFont(this, R.font.cuprum_regular));


        View easySplashScreen = config.create();
        setContentView(easySplashScreen);
    }
}