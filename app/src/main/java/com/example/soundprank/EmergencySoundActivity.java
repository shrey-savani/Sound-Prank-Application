package com.example.soundprank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class EmergencySoundActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_sound);

        ADSMain.getInstance(this).showNative(findViewById(R.id.nativead), false);
        ImageView policeSiren = findViewById(R.id.imgpolicesiren);
        ImageView policeChatting = findViewById(R.id.imgpollicechatting);
        ImageView fireAlarm = findViewById(R.id.imgfirealarm);
        ImageView ambulance = findViewById(R.id.imgambulance);
        ImageView back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        policeSiren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ADSMain.getInstance(EmergencySoundActivity.this).showInterstitial(new ADSMain.InterAdListener() {
                    @Override
                    public void onAdClosed() {
                        startActivity(new Intent(getApplicationContext(), EmergencySoundNextActivity.class).putExtra(Constant.position, 0));
                    }
                });
            }
        });

        policeChatting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ADSMain.getInstance(EmergencySoundActivity.this).showInterstitial(new ADSMain.InterAdListener() {
                    @Override
                    public void onAdClosed() {
                        startActivity(new Intent(getApplicationContext(), EmergencySoundNextActivity.class).putExtra(Constant.position, 1));
                    }
                });
            }
        });

        fireAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ADSMain.getInstance(EmergencySoundActivity.this).showInterstitial(new ADSMain.InterAdListener() {
                    @Override
                    public void onAdClosed() {
                        startActivity(new Intent(getApplicationContext(), EmergencySoundNextActivity.class).putExtra(Constant.position, 2));
                    }
                });
            }
        });

        ambulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ADSMain.getInstance(EmergencySoundActivity.this).showInterstitial(new ADSMain.InterAdListener() {
                    @Override
                    public void onAdClosed() {
                        startActivity(new Intent(getApplicationContext(), EmergencySoundNextActivity.class).putExtra(Constant.position, 3));
                    }
                });
            }
        });
    }
    public void onBackPressed() {
        ADSMain.getInstance(this).showBackInterstitial(new ADSMain.BackInterAdListener() {
            @Override
            public void onAdClosed() {
                finish();
            }
        });
    }
}