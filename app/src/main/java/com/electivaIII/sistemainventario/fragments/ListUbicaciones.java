package com.electivaIII.sistemainventario.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.electivaIII.sistemainventario.Adapters.UbicacionesAdapter;
import com.electivaIII.sistemainventario.MapsActivity;
import com.electivaIII.sistemainventario.R;
import com.electivaIII.sistemainventario.Utils.ChangeFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListUbicaciones extends Fragment {


    public ListUbicaciones() {
        // Required empty public constructor
    }

    ListView listUbicaciones;
    int frMain = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_ubicaciones, container, false);
        listUbicaciones = v.findViewById(R.id.listUbicaciones);


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            frMain = bundle.getInt("frMain");
        }


        final String[] almacenes = {
                "San Salvador",
                "Santa Ana",
                "Santa Tecla",
                "San Jacinto",
                "Apopa"
        };
        int[] images = {
            R.drawable.ubicacion,
            R.drawable.ubicacion,
            R.drawable.ubicacion,
            R.drawable.ubicacion,
            R.drawable.ubicacion,
        };

        String[] cantidades = {
                "10",
                "14",
                "No disponible",
                "1",
                "34"
        };

        final double[] latitud = {
                13.68935,
                13.99417,
                13.67694,
                13.686014,
                13.80722
        };

        final double[] longitud = {
                -89.18718,
                -89.55972,
                -89.27972,
                -89.1894223,
                -89.17917
        };

        UbicacionesAdapter ubicacionesAdapter = new UbicacionesAdapter(getContext(), almacenes, cantidades, images);
        listUbicaciones.setAdapter(ubicacionesAdapter);

        listUbicaciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View
                    view, final int position, long id) {
                //ChangeFragment.changeFragment(frMain, getActivity(), new Ubicacion());

                Intent googleMaps = new Intent(getActivity(), MapsActivity.class);
                googleMaps.putExtra("latitud", latitud[position]);
                googleMaps.putExtra("longitud", longitud[position]);
                googleMaps.putExtra("almacenes", almacenes[position]);
                startActivity(googleMaps);

            }
        });
        return v;
    }
}
