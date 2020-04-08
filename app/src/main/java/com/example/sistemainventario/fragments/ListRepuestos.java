package com.example.sistemainventario.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.sistemainventario.Adapters.AccesoriosAdapter;
import com.example.sistemainventario.Models.AccesoriosModel;
import com.example.sistemainventario.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListRepuestos extends Fragment {


    public ListRepuestos() {
        // Required empty public constructor
    }
    ListView listRepuestos;
    AccesoriosAdapter accesoriosAdapter;
    List<AccesoriosModel> accesoriosModelsList = new ArrayList<>();
    AccesoriosModel accesoriosModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_repuestos, container, false);
        listRepuestos = v.findViewById(R.id.listRespuestos);

        String[] name = {
                "Placa iPhone 6s",
                "Bateria Samsung S7 Edge",
                "Bandeja Sim Samsung S8",
                "USB de 16GB"

        };
        String[] disponibles = {
                "item 2",
                "item 4",
                "item 6",
                "item 8"
        };

        int[] images = {
                R.drawable.ejemplo2,
                R.drawable.baterias7,
                R.drawable.simgalaxys8,
                R.drawable.usb
        };

        for (int i=0; i<name.length; i ++){

            accesoriosModel = new AccesoriosModel(name[i], disponibles[i], images[i]);
            accesoriosModelsList.add(accesoriosModel);
        }

        accesoriosAdapter = new AccesoriosAdapter(getContext(), accesoriosModelsList);

        // LLenamos el modelo con la informacion
        accesoriosModel.setAccesorios(accesoriosModelsList);
        listRepuestos.setAdapter(accesoriosAdapter);


        listRepuestos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View
                    view, final int position, long id) {
                Fragment fragmentDetalle = new DetalleAccesorios();
                Bundle data = new Bundle();
                data.putString("name", accesoriosModelsList.get(position).getName());
                data.putString("item", accesoriosModelsList.get(position).getItem());
                data.putInt("image", accesoriosModelsList.get(position).getImage());
                fragmentDetalle.setArguments(data);

                changeFragment(fragmentDetalle);

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
