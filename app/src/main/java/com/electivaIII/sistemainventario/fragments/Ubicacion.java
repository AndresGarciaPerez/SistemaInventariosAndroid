package com.electivaIII.sistemainventario.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.electivaIII.sistemainventario.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Ubicacion extends Fragment {



    public Ubicacion() {
        // Required empty public constructor
    }

    Double latitud, longitud;
    String almacen;
    TextView txtAlmacenUbicacion;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ubicacion, container, false);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            latitud = bundle.getDouble("latitud");
            longitud = bundle.getDouble("longitud");
            almacen = bundle.getString("almacen");
        }

        txtAlmacenUbicacion = v.findViewById(R.id.txtAlmacenUbicacion);
        txtAlmacenUbicacion.setText("Almacen: "+almacen);

        return v;
    }

}
