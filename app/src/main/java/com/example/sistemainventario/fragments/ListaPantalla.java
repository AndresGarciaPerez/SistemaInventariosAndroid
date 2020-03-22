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
public class ListaPantalla extends Fragment {

    public ListaPantalla() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_pantalla, container, false);
    }
}
