package com.example.moviesappbootcampv;

import android.app.Application;

import java.util.ArrayList;


public class ApplicationClass extends Application {
    public static ArrayList<Movie> movies;

    @Override
    public void onCreate() {
        super.onCreate();
        movies = new ArrayList<Movie>();
    }
}
