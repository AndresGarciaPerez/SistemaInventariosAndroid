package com.example.sistemainventario.fragments;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.sistemainventario.Adapters.UbicacionesAdapter;
import com.example.sistemainventario.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListUbicaciones extends Fragment {


    public ListUbicaciones() {
        // Required empty public constructor
    }

    ListView listUbicaciones;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_ubicaciones, container, false);
        listUbicaciones = v.findViewById(R.id.listUbicaciones);

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
                changeFragment(new Ubicacion());
            }
        });
        return v;
    }

    public void changeFragment(Fragment fragment) {

        getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .addToBackStack(null).replace(R.id.frContenido, fragment).commit();
    }
}
