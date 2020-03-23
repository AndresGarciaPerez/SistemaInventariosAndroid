package com.example.sistemainventario.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sistemainventario.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Repuestos extends Fragment {

    public Repuestos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_repuestos, container, false);
        return v;
    }
}
