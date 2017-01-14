package com.cholo.sourabhzalke.gotcha;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class start extends AppCompatActivity {

    private InterstitialAd interstitial;

    private SoundPlayer sound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        //Create the interstitial.
        interstitial= new InterstitialAd(this);

        //SET YOUR UNIT ID. THIS IS TEST
        interstitial.setAdUnitId("ca-app-pub-7889896830289138/7492084408");

        //CREATE REQUEST.
        AdRequest adRequest = new AdRequest.Builder().build();

        //Start loading...

        interstitial.loadAd(adRequest);

        //Once Request is loaded, display ad.
        interstitial.setAdListener(new AdListener() {

            public void onAdLoaded() {
                displayInterstitial();
            }
        });
        sound = new SoundPlayer(this);
    }

    public void displayInterstitial()
    {
        if(interstitial.isLoaded())
        {
            interstitial.show();
        }
    }


    public void startGame(View view)
    {
        sound.playBellSound();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }
    //Disable Return Button
    @Override
    public boolean dispatchKeyEvent(KeyEvent event)
    {
        if(event.getAction()== KeyEvent.ACTION_DOWN)
        {
            switch (event.getKeyCode())
            {
                case KeyEvent.KEYCODE_BACK:
                    return true;
            }
        }

        return super.dispatchKeyEvent(event);
    }
}
