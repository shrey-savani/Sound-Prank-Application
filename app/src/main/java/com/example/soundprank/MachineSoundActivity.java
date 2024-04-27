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


public class MachineSoundActivity extends AppCompatActivity {

    int[] machinePicArray = new int[]{
            R.drawable.hairtrimmer,
            R.drawable.fakemessage,
            R.drawable.fakecall,
            R.drawable.drill,
            R.drawable.gunfire,
            R.drawable.woodshaw,
            R.drawable.aircraft,
            R.drawable.carbrake,
            R.drawable.carhorn,
            R.drawable.cameraiphone,
            R.drawable.mouseclick,
            R.drawable.keyboard
    };

    MachineSoundAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine_sound);
        ADSMain.getInstance(this).showBanner(findViewById(R.id.bannerad));

        RecyclerView recyclerView = findViewById(R.id.recyclemachinesound);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        ImageView back = findViewById(R.id.back);

        adapter = new MachineSoundAdapter(this, machinePicArray);
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

    public class MachineSoundAdapter extends RecyclerView.Adapter<MachineSoundAdapter.viewHolder> {
        Activity activity;
        int[] machinePicArray;

        public MachineSoundAdapter(Activity machineSoundActivity, int[] machinePicArray) {
            this.activity = machineSoundActivity;
            this.machinePicArray = machinePicArray;
        }

        @NonNull
        @Override
        public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(activity).inflate(R.layout.machine_sound_list, parent, false);
            return new viewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.imageView.setImageResource(machinePicArray[position]);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ADSMain.getInstance(MachineSoundActivity.this).showInterstitial(new ADSMain.InterAdListener() {
                        @Override
                        public void onAdClosed() {
                            startActivity(new Intent(getApplicationContext(), MachineSoundNextActivity.class).putExtra(Constant.position, position));
                        }
                    });
                }
            });
        }

        @Override
        public int getItemCount() {
            return machinePicArray.length;
        }

        public class viewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            public viewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imgdrillsound);

            }
        }
    }

}