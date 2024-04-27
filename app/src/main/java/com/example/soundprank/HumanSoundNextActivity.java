package com.example.soundprank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class HumanSoundNextActivity extends AppCompatActivity {
    ImageView volumeUp, volumeDown;
    MediaPlayer mp;
    TextView seekProgress, humanHeading;
    SeekBar volumeSeekBar;
    YoYo.YoYoString yoYoString;
    int i = 0;
    Switch loopAudio;
    ImageView imageView;

    int[] humanCharacterImage = new int[]{
            R.drawable.kisscheek_c,
            R.drawable.breathe_c,
            R.drawable.fart_c,
            R.drawable.yelling_c,
            R.drawable.hello_c,
            R.drawable.snoaring_c,
            R.drawable.spookygroans_c,
            R.drawable.kisssound_c,
            R.drawable.mancough_c,
            R.drawable.womencough_c,
            R.drawable.cryinggirl_c,
            R.drawable.cryingbaby_c,
            R.drawable.laughingbaby_c,
            R.drawable.burp_c,
            R.drawable.witchlaughter_c,
            R.drawable.clap_c
    };

    int[] audioHumanCharacter = new int[]{
            R.raw.kiss_cheek_sound,
            R.raw.breathe_sound,
            R.raw.fart_sound,
            R.raw.yelling_sound,
            R.raw.hello_sound,
            R.raw.snoring_sound,
            R.raw.spooky_groans_sound,
            R.raw.kiss_sound,
            R.raw.man_cough_sound,
            R.raw.woman_cough_sound,
            R.raw.crying_girl_sound,
            R.raw.crying_baby_sound,
            R.raw.laughing_baby_sound,
            R.raw.burp_sound,
            R.raw.witch_laughter_sound,
            R.raw.clap_sound
    };

     String[] heading = new String[]{
            "Kiss Cheek",
            "Breathe Sound",
            "Fart Sound",
            "Yelling Sound",
            "Hello Sound",
            "Snoring Sound",
            "Spooky Groans",
            "Kiss Sound",
            "Man Cough",
            "Woman Cough",
            "Crying Girl",
            "Crying Baby",
            "Laughing Baby",
            "Burp Sound",
            "Witch Laughter",
            "Clap Sound"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_human_sound_next);
        ADSMain.getInstance(this).showBanner(findViewById(R.id.bannerad));

        i = getIntent().getIntExtra(Constant.position, 0);
        imageView = findViewById(R.id.imgcyelling);
        mp = MediaPlayer.create(getApplicationContext(), audioHumanCharacter[i]);

        imageView.setImageResource(humanCharacterImage[i]);
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
                if (mp != null) {
                    mp.stop();
                    mp.release();
                    mp = null;
                }
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