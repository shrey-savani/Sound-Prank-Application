package com.example.soundprank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class AnimalSoundActivity extends AppCompatActivity {

    int[] animalPicArray = new int[]{
            R.drawable.dogbarking,
            R.drawable.catmeow,
            R.drawable.mosquitoflying,
            R.drawable.mousesqueal,
            R.drawable.birdsinging,
            R.drawable.roostersound,
            R.drawable.beeflying,
            R.drawable.frogsound,
            R.drawable.doghowling,
            R.drawable.bearroar,
            R.drawable.wolfhowling,
            R.drawable.pigsound,
            R.drawable.horseneigh,
            R.drawable.hensound,
            R.drawable.ducksound,
            R.drawable.tigerroar
    };

    AnimalSoundAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_sound);

        ADSMain.getInstance(this).showBanner(findViewById(R.id.bannerad));

        RecyclerView recyclerView = findViewById(R.id.recycleanimalsound);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new AnimalSoundAdapter(this, animalPicArray);
        recyclerView.setAdapter(adapter);

        ImageView back = findViewById(R.id.back);

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

    public class AnimalSoundAdapter extends RecyclerView.Adapter<AnimalSoundAdapter.viewHolder> {
        Activity activity;
        int[] animalPicArray;

        public AnimalSoundAdapter(Activity animalSoundActivity, int[] animalPicArray) {
            this.activity = animalSoundActivity;
            this.animalPicArray = animalPicArray;
        }

        @NonNull
        @Override
        public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(activity).inflate(R.layout.animal_sound_list, parent, false);
            return new viewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.imageView.setImageResource(animalPicArray[position]);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ADSMain.getInstance(AnimalSoundActivity.this).showInterstitial(new ADSMain.InterAdListener() {
                        @Override
                        public void onAdClosed() {
                            startActivity(new Intent(getApplicationContext(), AnimalSoundNextActivity.class).putExtra(Constant.position, position));
                        }
                    });
                }
            });
        }

        @Override
        public int getItemCount() {
            return animalPicArray.length;
        }

        public class viewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            public viewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imgdogbarkingsound);

            }
        }
    }

}