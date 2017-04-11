package com.effone.hostismeandroid.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.effone.hostismeandroid.R;


public class HotDishHomePageImageFragment extends Fragment {


    public HotDishHomePageImageFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View root= inflater.inflate(R.layout.fragment_hot_dish_home_page_image, container, false);
        init(root);
        return  root;
    }

    private void init(View root) {

    }

    // TODO: Rename method, update argument and hook method into UI event



}
