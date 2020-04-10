package com.example.sistemainventario.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.sistemainventario.Adapters.UbicacionesAdapter;
import com.example.sistemainventario.R;
import com.example.sistemainventario.Utils.ChangeFragment;

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


        String[] almacenes = {
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

        UbicacionesAdapter ubicacionesAdapter = new UbicacionesAdapter(getContext(), almacenes, cantidades, images);
        listUbicaciones.setAdapter(ubicacionesAdapter);

        listUbicaciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View
                    view, final int position, long id) {
                ChangeFragment.changeFragment(frMain, getActivity(), new Ubicacion());

            }
        });
        return v;
    }
}
