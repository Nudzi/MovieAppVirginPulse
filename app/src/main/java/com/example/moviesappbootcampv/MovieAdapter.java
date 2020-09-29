package com.example.moviesappbootcampv;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    public static ArrayList<Movie> Movies;

    ItemClicked activity;
    public interface ItemClicked {
    }

    public MovieAdapter (Context context, ArrayList<Movie> list){
        super();
        Movies = list;
        activity = (ItemClicked) context;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvMovieName, tvRealeaseDate, tvFullMovieOverview;
        ImageView imgPoster;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvFullMovieOverview = itemView.findViewById(R.id.tvFullMovieOverview);
            tvRealeaseDate = itemView.findViewById(R.id.tvRealeaseDate);
            tvMovieName = itemView.findViewById(R.id.tvMovieName);
            imgPoster = itemView.findViewById(R.id.imgPoster);
        }
    }

    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setTag(Movies.get(position));
        holder.tvMovieName.setText(Movies.get(position).getTitle());
        holder.tvFullMovieOverview.setText(Movies.get(position).getOverview());
        holder.tvRealeaseDate.setText(Movies.get(position).getReleaseDate());
        if (Movies.get(position).getImage() != null) {
            Glide.with((Activity) activity)
                    .load(Movies.get(position).getImage()).into(holder.imgPoster);
        }
    }

    @Override
    public int getItemCount() {
        return Movies.size();
    }
}
