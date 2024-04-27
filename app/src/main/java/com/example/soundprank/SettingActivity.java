package com.example.soundprank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ADSMain.getInstance(this).showBanner(findViewById(R.id.bannerad));
        TextView privacy = findViewById(R.id.txtprivacy);
        TextView rate = findViewById(R.id.txtrate);
        TextView share = findViewById(R.id.txtshare);
        ImageView back = findViewById(R.id.back);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent("android.intent.action.SEND");
                sharingIntent.setType("text/plain");
                String shareBody = ("https://play.google.com/store/apps/details?id=" + getPackageName() + "&hl=en");
                sharingIntent.putExtra("android.intent.extra.SUBJECT", "Subject Here");
                sharingIntent.putExtra("android.intent.extra.TEXT", shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomTabsIntent.Builder builder1 = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder1.build();
                customTabsIntent.launchUrl(SettingActivity.this, Uri.parse(""));
            }
        });

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String appPackageName = getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        ADSMain.getInstance(this).showBackInterstitial(new ADSMain.BackInterAdListener() {
            @Override
            public void onAdClosed() {
                finish();
            }
        });
    }
}