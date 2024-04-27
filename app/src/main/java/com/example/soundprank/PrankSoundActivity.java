    package com.example.soundprank;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class PrankSoundActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prank_sound);

        ADSMain.getInstance(this).showNative(findViewById(R.id.nativead), false);

        ImageView humanSound = findViewById(R.id.imghumansound);
        ImageView animalSound = findViewById(R.id.imganimalsound);
        ImageView machineSound = findViewById(R.id.imgmachinesound);
        ImageView otherSound = findViewById(R.id.imgothersound);
        ImageView emergencySound = findViewById(R.id.imgemergencysound);
        ImageView setting = findViewById(R.id.imgsetting);
        ImageView back = findViewById(R.id.back);

        humanSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ADSMain.getInstance(PrankSoundActivity.this).showInterstitial(new ADSMain.InterAdListener() {
                    @Override
                    public void onAdClosed() {
                        startActivity(new Intent(getApplicationContext(), HumanSoundActivity.class));
                    }
                });
            }
        });

        animalSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ADSMain.getInstance(PrankSoundActivity.this).showInterstitial(new ADSMain.InterAdListener() {
                    @Override
                    public void onAdClosed() {
                        startActivity(new Intent(getApplicationContext(), AnimalSoundActivity.class));
                    }
                });
            }
        });

        machineSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ADSMain.getInstance(PrankSoundActivity.this).showInterstitial(new ADSMain.InterAdListener() {
                    @Override
                    public void onAdClosed() {
                        startActivity(new Intent(getApplicationContext(), MachineSoundActivity.class));
                    }
                });
            }
        });

        otherSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ADSMain.getInstance(PrankSoundActivity.this).showInterstitial(new ADSMain.InterAdListener() {
                    @Override
                    public void onAdClosed() {
                        startActivity(new Intent(getApplicationContext(), OtherSoundActivity.class));
                    }
                });
            }
        });

        emergencySound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ADSMain.getInstance(PrankSoundActivity.this).showInterstitial(new ADSMain.InterAdListener() {
                    @Override
                    public void onAdClosed() {
                        startActivity(new Intent(getApplicationContext(), EmergencySoundActivity.class));
                    }
                });
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SettingActivity.class));
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
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
    }
}