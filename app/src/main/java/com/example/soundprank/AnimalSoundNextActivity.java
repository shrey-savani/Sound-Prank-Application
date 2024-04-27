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

public class AnimalSoundNextActivity extends AppCompatActivity {

    ImageView volumeUp, volumeDown;
    MediaPlayer mp;
    TextView seekProgress, humanHeading;
    SeekBar volumeSeekBar;
    YoYo.YoYoString yoYoString;
    int i = 0;
    Switch loopAudio;
    ImageView imageView;

    int[] animalCharacterImage = new int[]{
            R.drawable.dogbarking_c,
            R.drawable.catmeow_c,
            R.drawable.mosquitoflying_c,
            R.drawable.mousesqueal_c,
            R.drawable.birdsinging_c,
            R.drawable.roostersound_c,
            R.drawable.beeflying_c,
            R.drawable.frogsound_c,
            R.drawable.doghowling_c,
            R.drawable.bearroar_c,
            R.drawable.wolfhowling_c,
            R.drawable.pigsound_c,
            R.drawable.horseneigh_c,
            R.drawable.hensound_c,
            R.drawable.ducksound_c,
            R.drawable.tigerroar_c
    };

    int[] audioAnimalCharacter = new int[]{
            R.raw.dog_barking_sound,
            R.raw.cat_meow_sound,
            R.raw.mosquito_flying_sound,
            R.raw.mouse_squeal_sound,
            R.raw.bird_singing_sound,
            R.raw.rooster_sound,
            R.raw.bee_flying_sound,
            R.raw.frog_sound,
            R.raw.dog_howling_sound,
            R.raw.bear_roar_sound,
            R.raw.wolf_howling_sound,
            R.raw.pig_sound,
            R.raw.horse_neigh_sound,
            R.raw.hen_sound,
            R.raw.duck_sound,
            R.raw.tiger_roar_sound
    };

    String[] heading = new String[]{
            "Dog Barking Sound",
            "Cat Meow Sound",
            "Mosquito Flying",
            "Mouse Squeal",
            "Bird Singing",
            "Rooster Sound",
            "Bee Flying",
            "Frog Sound",
            "Dog Howling",
            "Bear Roar",
            "Wolf Howling",
            "Pig Sound",
            "Horse Neigh",
            "Hen Sound",
            "Duck Sound",
            "Tiger Roar"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_sound_next);
        ADSMain.getInstance(this).showBanner(findViewById(R.id.bannerad));

        i = getIntent().getIntExtra(Constant.position, 0);
        imageView = findViewById(R.id.imgcyelling);
        mp = MediaPlayer.create(getApplicationContext(), audioAnimalCharacter[i]);

        imageView.setImageResource(animalCharacterImage[i]);
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