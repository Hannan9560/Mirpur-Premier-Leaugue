package com.betechme.mirpurpremierleague;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class WebViewActivity extends AppCompatActivity {
    private static final String TAG = "WebViewActivity";

    private WebView mWebview;
    private ProgressDialog progressDialog;
    private InterstitialAd interstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Intent intent = getIntent();
        String url = intent.getStringExtra("data");
        Log.d(TAG, "onCreate: url" + url);
        init_adMob();
        mWebview = findViewById(R.id.webView);
        mWebview  = new WebView(this);

        progressDialog = new ProgressDialog(this);

        startWebView(url);


    }

    @SuppressLint("SetJavaScriptEnabled")
    private void startWebView(String url) {

        WebSettings settings = mWebview.getSettings();

        settings.setJavaScriptEnabled(true);
        mWebview.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);

        mWebview.getSettings().setBuiltInZoomControls(true);
        mWebview.getSettings().setUseWideViewPort(true);
        mWebview.getSettings().setLoadWithOverviewMode(true);

        progressDialog = new ProgressDialog(WebViewActivity.this);
        progressDialog.setMessage("Progressing...");
        progressDialog.show();

        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(WebViewActivity.this, "Error:" + description, Toast.LENGTH_SHORT).show();

            }
        });
        mWebview.loadUrl(url);
        setContentView(mWebview);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        showInterstitial();
    }
}
