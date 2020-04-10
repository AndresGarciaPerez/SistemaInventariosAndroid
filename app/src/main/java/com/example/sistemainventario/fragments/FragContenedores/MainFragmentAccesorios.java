package com.example.sistemainventario.fragments.FragContenedores;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sistemainventario.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragmentAccesorios extends Fragment {
    private View view;

    public MainFragmentAccesorios() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null){
            view = inflater.inflate(R.layout.fragment_main_fragment_accesorios, container, false);
        }
        return view;
    }

}
