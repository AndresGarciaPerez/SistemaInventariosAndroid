package com.electivaIII.sistemainventario.fragments.AccesoriosFile;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.electivaIII.sistemainventario.Adapters.AccesoriosAdapter;
import com.electivaIII.sistemainventario.Models.AccesoriosModel;
import com.electivaIII.sistemainventario.R;
import com.electivaIII.sistemainventario.Utils.ChangeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListAccesorios extends Fragment {


    public ListAccesorios() {
        // Required empty public constructor
    }

    ListView listAccesorios;
    AccesoriosAdapter accesoriosAdapter;
    List<AccesoriosModel> accesoriosModelsList = new ArrayList<>();
    AccesoriosModel accesoriosModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_accesorios, container, false);
        listAccesorios = v.findViewById(R.id.listAccesorios);


        String[] name = {
                "Pantalla LG",
                "Pantalla TV",
                "Espiga para DVD",
                "USB de 16GB"

        };
        String[] disponibles = {
                "item 2",
                "item 4",
                "item 6",
                "item 8"
        };

        int[] images = {
                R.drawable.movil,
                R.drawable.desktop,
                R.drawable.espiga,
                R.drawable.usb
        };
        for (int i=0; i<name.length; i ++){

            accesoriosModel = new AccesoriosModel(name[i], disponibles[i], images[i]);
            accesoriosModelsList.add(accesoriosModel);
        }

        accesoriosAdapter = new AccesoriosAdapter(getContext(), accesoriosModelsList);

        // LLenamos el modelo con la informacion
        accesoriosModel.setAccesorios(accesoriosModelsList);

        listAccesorios.setAdapter(accesoriosAdapter);


        listAccesorios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View
                    view, final int position, long id) {
                Fragment fragmentDetalle = new DetalleAccesorios();
                Bundle data = new Bundle();
                data.putString("name", accesoriosModelsList.get(position).getName());
                data.putString("item", accesoriosModelsList.get(position).getItem());
                data.putInt("image", accesoriosModelsList.get(position).getImage());
                fragmentDetalle.setArguments(data);

                ChangeFragment.changeFragment(R.id.frMainAccesorio, getActivity(), fragmentDetalle);


            }
        });

        return v;
    }


}
