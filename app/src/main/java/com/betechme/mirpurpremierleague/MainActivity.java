package com.betechme.mirpurpremierleague;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.betechme.mirpurpremierleague.util.Constant;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.team).setOnClickListener(this);
        findViewById(R.id.players).setOnClickListener(this);
        findViewById(R.id.matchSchedule).setOnClickListener(this);
        findViewById(R.id.matchResult).setOnClickListener(this);
        findViewById(R.id.updateNews).setOnClickListener(this);

        init_adMob();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.team:
                Intent intent = new Intent(this, TeamActivity.class);
                startActivity(intent);

                break;
            case R.id.players:

                startWebActivity(Constant.players_url);


                break;
            case R.id.matchSchedule:

                startWebActivity(Constant.match_result_url);

                break;
            case R.id.matchResult:

                startWebActivity(Constant.match_schecule_url);

                break;
            case R.id.updateNews:

                startWebActivity(Constant.update_url);

                break;

                default:

                    break;
        }
    }

    private void startWebActivity(String url){
        Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
        intent.putExtra("data", url);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        showInterstitial();
        finish();
    }

    /*Ad mob*/
    private void init_adMob(){
        /*ads*/
        MobileAds.initialize(this, getString(R.string.add_unit));

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        mAdView.loadAd(adRequest);
        setAdMobInit();
    }

    private void setAdMobInit() {
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.ad_interstitial_id));
        interstitialAd.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {

            }
        });
    }

    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (interstitialAd != null && interstitialAd.isLoaded()) {
            interstitialAd.show();
        }
    }
}
