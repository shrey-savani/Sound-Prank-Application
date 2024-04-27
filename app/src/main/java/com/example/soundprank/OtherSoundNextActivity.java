package com.example.soundprank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class OtherSoundNextActivity extends AppCompatActivity {
    ImageView volumeUp, volumeDown;
    MediaPlayer mp;
    TextView seekProgress, humanHeading;
    SeekBar volumeSeekBar;
    YoYo.YoYoString yoYoString;
    int i = 0;
    Switch loopAudio;
    ImageView imageView;

    int[] otherCharacterImage = new int[]{
            R.drawable.airhorn_c,
            R.drawable.doorbell_c,
            R.drawable.thunder_c,
            R.drawable.crackedglass_c,
            R.drawable.dooropening_c,
            R.drawable.wind_c,
            R.drawable.electricsound_c,
            R.drawable.rain_c,
            R.drawable.waterflow_c,
            R.drawable.drumbeat_c,
            R.drawable.templebell_c,
            R.drawable.dooescratch_c,
            R.drawable.tearfabric_c,
            R.drawable.bombexplosion_c
    };

    int[] audioOtherCharacter = new int[]{
            R.raw.air_horn_sound,
            R.raw.door_bell_sound,
            R.raw.thunder_sound,
            R.raw.cracked_glass_sound,
            R.raw.door_opening_sound,
            R.raw.wind_sound,
            R.raw.electric_sound,
            R.raw.rain_sound,
            R.raw.water_flow_sound,
            R.raw.drumbeat_sound,
            R.raw.temple_bell_sound,
            R.raw.door_scratch_sound,
            R.raw.tear_fabric_sound,
            R.raw.bomb_explosion_sound
    };

    String[] heading = new String[]{
           "Air Horn",
           "Door Bell",
           "Thunder Sound",
           "Cracked Glass",
           "Door Opening",
           "Wind Sound",
           "Electric Sound",
           "Rain Sound",
           "Water Flow",
           "Drumbeat Sound",
           "Temple Bell",
           "Door Scratch",
           "Tear Fabric",
           "Bomb Explosion"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_sound_next);

        ADSMain.getInstance(this).showBanner(findViewById(R.id.bannerad));


        i = getIntent().getIntExtra(Constant.position, 0);
        imageView = findViewById(R.id.imgcyelling);
        mp = MediaPlayer.create(getApplicationContext(), audioOtherCharacter[i]);

        imageView.setImageResource(otherCharacterImage[i]);
        volumeUp = findViewById(R.id.imgvolumeup);
        volumeDown = findViewById(R.id.imgvolumedown);
        volumeSeekBar = findViewById(R.id.volumeseekbar);
        loopAudio = findViewById(R.id.swtlooop);
        humanHeading= findViewById(R.id.txthumanheading);
        humanHeading.setText(heading[i]);
        ImageView back = findViewById(R.id.back);

        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        volumeSeekBar.setMax(100);
        volumeSeekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));

        loopAudio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                handleAnimAudio();
            }
        });

        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekProgress = findViewById(R.id.txtprogress);
                seekProgress.setText(String.valueOf(progress));
                seekProgress.setVisibility(View.VISIBLE);

                int width = seekBar.getWidth() - seekBar.getPaddingLeft() - seekBar.getPaddingRight();
                int thumbPos = seekBar.getPaddingLeft() + width * seekBar.getProgress() / seekBar.getMax();

                seekProgress.measure(0, 0);
                int txtW = seekProgress.getMeasuredWidth();
                int delta = txtW / 2;
                seekProgress.setX(seekBar.getX() + thumbPos - delta);

                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekProgress.setVisibility(View.GONE);
            }
        });

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                handleAnimAudio();
                return true;
            }
        });

        volumeUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volumeSeekBar.setProgress(100);
            }
        });

        volumeDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volumeSeekBar.setProgress(0);
            }
        });

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                yoYoString.stop();
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
        if (mp != null) {
            mp.stop();
            mp.release();
            mp = null;
        }
        ADSMain.getInstance(this).showBackInterstitial(new ADSMain.BackInterAdListener() {
            @Override
            public void onAdClosed() {
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            int index = volumeSeekBar.getProgress();
            volumeSeekBar.setProgress(index + 1);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            int index = volumeSeekBar.getProgress();
            volumeSeekBar.setProgress(index - 1);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void handleAnimAudio() {
        mp.start();
        if (loopAudio.isChecked()) {
            yoYoString = YoYo.with(Techniques.Tada).duration(1500L).repeat(-1).playOn(imageView);
            mp.setLooping(true);
        } else {
            if (yoYoString != null && yoYoString.isRunning()) {
                yoYoString.stop();
            }
            yoYoString = YoYo.with(Techniques.Tada).duration(1500L).repeat(1).playOn(imageView);
            mp.setLooping(false);
        }
    }
}