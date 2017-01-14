package com.cholo.sourabhzalke.gotcha;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class result extends AppCompatActivity {
    private SoundPlayer sound;
    private InterstitialAd interstitial;
    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        sound = new SoundPlayer(this);
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

// Prepare the Interstitial Ad
        interstitial = new InterstitialAd(this);
// Insert the Ad Unit ID
        interstitial.setAdUnitId("ca-app-pub-7889896830289138/7492084408");

        interstitial.loadAd(adRequest);
// Prepare an Interstitial Ad Listener
        interstitial.setAdListener(new AdListener() {
            public void onAdLoaded() {
                // Call displayInterstitial() function
                displayInterstitial();
            }
        });




        TextView scorelabel = (TextView)findViewById(R.id.scorelabel);
        TextView highScorelabel = (TextView)findViewById(R.id.highScorelabel);


        int score = getIntent().getIntExtra("SCORE",0);
        scorelabel.setText(score+"");

        SharedPreferences settings = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        int highScore = settings.getInt("HIGH_SCORE",0);

        if(score > highScore)
        {
            highScorelabel.setText("High Score : "+ score);
            //Save
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("HIGH_SCORE",score);
            editor.commit();
        }
        else
        {
            highScorelabel.setText("High Score : " + highScore);
        }
    }

    public void tryAgain(View view)
    {   sound.playTrySound();
        startActivity(new Intent(getApplicationContext(),start.class));
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

    public void displayInterstitial() {
// If Ads are loaded, show Interstitial else show nothing.
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }
}
