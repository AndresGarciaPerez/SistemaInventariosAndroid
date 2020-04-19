package com.electivaIII.sistemainventario.fragments.AccesoriosFile;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.electivaIII.sistemainventario.Adapters.AccesoriosAdapter;
import com.electivaIII.sistemainventario.Models.AccesoriosRepuestosModel;
import com.electivaIII.sistemainventario.R;
import com.electivaIII.sistemainventario.Utils.ChangeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListAccesorios extends Fragment {
    Boolean language=false;
    public ListAccesorios() {
        // Required empty public constructor
    }

    ListView listAccesorios;
    AccesoriosAdapter accesoriosAdapter;
    List<AccesoriosRepuestosModel> accesoriosRepuestosModelsList = new ArrayList<>();
    AccesoriosRepuestosModel accesoriosRepuestosModel;
    TextView txtFindListAccesorios;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_accesorios, container, false);
        listAccesorios = v.findViewById(R.id.listAccesorios);
        txtFindListAccesorios = v.findViewById(R.id.txtFindListAccesorios);

        SharedPreferences idioma = getActivity().getSharedPreferences("idioma", Context.MODE_PRIVATE);
        language = idioma.getBoolean("trueIdioma",false);
        if (language==true){
           txtFindListAccesorios.setHint("Search");
        }

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

            accesoriosRepuestosModel = new AccesoriosRepuestosModel(name[i], disponibles[i], images[i]);
            accesoriosRepuestosModelsList.add(accesoriosRepuestosModel);
        }

        accesoriosAdapter = new AccesoriosAdapter(getContext(), accesoriosRepuestosModelsList);

        // LLenamos el modelo con la informacion
        accesoriosRepuestosModel.setAccesorios(accesoriosRepuestosModelsList);

        listAccesorios.setAdapter(accesoriosAdapter);


        listAccesorios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View
                    view, final int position, long id) {
                Fragment fragmentDetalle = new DetalleAccesorios();
                Bundle data = new Bundle();
                data.putString("name", accesoriosRepuestosModelsList.get(position).getName());
                data.putString("item", accesoriosRepuestosModelsList.get(position).getItem());
                data.putInt("image", accesoriosRepuestosModelsList.get(position).getImage());
                fragmentDetalle.setArguments(data);

                ChangeFragment.changeFragment(R.id.frMainAccesorio, getActivity(), fragmentDetalle);


            }
        });

        txtFindListAccesorios.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                accesoriosAdapter.getFilter().filter(s.toString());
            }
        });

        return v;
    }




}
