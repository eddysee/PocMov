package com.gilandeddy.pocketmovie;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecentFragment extends Fragment {
    private RecentMovieAdapter recentMovieAdapter;


    public RecentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recentRecyclerView;
        recentRecyclerView = container.findViewById(R.id.recentMovieRecycler);
        recentMovieAdapter =  new RecentMovieAdapter();
        recentRecyclerView.setAdapter(recentMovieAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recentRecyclerView.setLayoutManager(linearLayoutManager);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recent, container, false);

    }

}
