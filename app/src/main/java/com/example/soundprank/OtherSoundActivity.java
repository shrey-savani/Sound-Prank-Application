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


public class OtherSoundActivity extends AppCompatActivity {

    int[] otherPicArray = new int[]{
            R.drawable.airhorn,
            R.drawable.doorbell,
            R.drawable.thunder,
            R.drawable.crackedglass,
            R.drawable.dooropening,
            R.drawable.wind,
            R.drawable.electricsound,
            R.drawable.rain,
            R.drawable.waterflow,
            R.drawable.drumbeat,
            R.drawable.templebell,
            R.drawable.dooescratch,
            R.drawable.tearfabric,
            R.drawable.bombexplosion
    };

    OtherSoundAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_sound);
        ADSMain.getInstance(this).showBanner(findViewById(R.id.bannerad));

        RecyclerView recyclerView = findViewById(R.id.recycleothersound);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        ImageView back = findViewById(R.id.back);

        adapter = new OtherSoundAdapter(this, otherPicArray);
        recyclerView.setAdapter(adapter);

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

    public class OtherSoundAdapter extends RecyclerView.Adapter<OtherSoundAdapter.viewHolder> {
        Activity activity;
        int[] otherPicArray;

        public OtherSoundAdapter(Activity otherSoundActivity, int[] otherPicArray) {
            this.activity = otherSoundActivity;
            this.otherPicArray = otherPicArray;
        }

        @NonNull
        @Override
        public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(activity).inflate(R.layout.other_sound_list, parent, false);
            return new viewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.imageView.setImageResource(otherPicArray[position]);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ADSMain.getInstance(OtherSoundActivity.this).showInterstitial(new ADSMain.InterAdListener() {
                        @Override
                        public void onAdClosed() {
                            startActivity(new Intent(getApplicationContext(), OtherSoundNextActivity.class).putExtra(Constant.position, position));
                        }
                    });
                }
            });

        }

        @Override
        public int getItemCount() {
            return otherPicArray.length;
        }

        public class viewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            public viewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imgairhornsound);
            }
        }
    }

}