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


public class HumanSoundActivity extends AppCompatActivity {

    int[] humanPicArray = new int[]{
            R.drawable.kisscheek,
            R.drawable.breathe,
            R.drawable.fart,
            R.drawable.yelling,
            R.drawable.hello,
            R.drawable.snoaring,
            R.drawable.spookygroans,
            R.drawable.kisssound,
            R.drawable.mancough,
            R.drawable.womencough,
            R.drawable.cryinggirl,
            R.drawable.cryingbaby,
            R.drawable.laughingbaby,
            R.drawable.burp,
            R.drawable.witchlaughter,
            R.drawable.clap
    };

    HumanSoundAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_human_sound);
        ImageView back = findViewById(R.id.back);
        ADSMain.getInstance(this).showBanner(findViewById(R.id.bannerad));

        RecyclerView recyclerView = findViewById(R.id.recyclehumansound);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new HumanSoundAdapter(this, humanPicArray);
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

    public class HumanSoundAdapter extends RecyclerView.Adapter<HumanSoundAdapter.viewHolder> {
        Activity activity;
        int[] humanPicArray;

        public HumanSoundAdapter(Activity humanSoundActivity, int[] humanPicArray) {
            this.activity = humanSoundActivity;
            this.humanPicArray = humanPicArray;
        }

        @NonNull
        @Override
        public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(activity).inflate(R.layout.human_sound_list, parent, false);
            return new viewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.imageView.setImageResource(humanPicArray[position]);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ADSMain.getInstance(HumanSoundActivity.this).showInterstitial(new ADSMain.InterAdListener() {
                        @Override
                        public void onAdClosed() {
                            startActivity(new Intent(getApplicationContext(), HumanSoundNextActivity.class).putExtra(Constant.position, position));
                        }
                    });
                }
            });
        }

        @Override
        public int getItemCount() {
            return humanPicArray.length;
        }

        public class viewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            public viewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imghellosound);

            }
        }
    }

}