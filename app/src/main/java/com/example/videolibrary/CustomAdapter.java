package com.example.videolibrary;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList movie_id, movie_title, movie_year, movie_director, movie_rating, movie_duration;

    Animation translate_anim;

    CustomAdapter(Activity activity, Context context, ArrayList movie_id, ArrayList movie_title, ArrayList movie_year, ArrayList movie_director, ArrayList movie_rating, ArrayList movie_duration) {
        this.activity = activity;
        this.context = context;
        this.movie_id = movie_id;
        this.movie_title = movie_title;
        this.movie_year = movie_year;
        this.movie_director = movie_director;
        this.movie_rating = movie_rating;
        this.movie_duration = movie_duration;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.movie_id_txt.setText(String.valueOf(movie_id.get(position)));
        holder.movie_title_txt.setText(String.valueOf(movie_title.get(position)));
        holder.movie_director_txt.setText(String.valueOf(movie_director.get(position)));
        holder.movie_rating_txt.setText(String.valueOf(movie_rating.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(movie_id.get(position)));
                intent.putExtra("title", String.valueOf(movie_title.get(position)));
                intent.putExtra("year", String.valueOf(movie_year.get(position)));
                intent.putExtra("director", String.valueOf(movie_director.get(position)));
                intent.putExtra("rating", String.valueOf(movie_rating.get(position)));
                intent.putExtra("duration", String.valueOf(movie_duration.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movie_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView movie_id_txt, movie_title_txt, movie_director_txt, movie_rating_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            movie_id_txt = itemView.findViewById(R.id.movie_id_txt);
            movie_title_txt = itemView.findViewById(R.id.movie_title_txt);
            movie_director_txt = itemView.findViewById(R.id.movie_director_txt);
            movie_rating_txt = itemView.findViewById(R.id.movie_rating_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            // Animate recycleview
            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }
    }
}
