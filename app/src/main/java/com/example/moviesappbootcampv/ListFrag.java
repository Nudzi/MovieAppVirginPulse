package com.example.moviesappbootcampv;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class ListFrag extends Fragment{

    public RecyclerView recyclerView;
    public static RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    View view;

    ArrayList<Movie> movies;
    public ListFrag() {// Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = view.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);

        myAdapter = new MovieAdapter(this.getActivity(), ApplicationClass.movies);
        recyclerView.setAdapter(myAdapter);
    }

    public void notifyDataChanged() {
        myAdapter.notifyDataSetChanged();
    }

    public void setMyList(data data, String param) {
        myAdapter = new MovieAdapter(this.getActivity(), data.setData());
        recyclerView.setAdapter(myAdapter);
    }
}
