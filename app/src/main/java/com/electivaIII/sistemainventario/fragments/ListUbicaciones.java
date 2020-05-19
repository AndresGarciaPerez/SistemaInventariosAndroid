package com.electivaIII.sistemainventario.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.electivaIII.sistemainventario.Adapters.UbicacionesAdapter;
import com.electivaIII.sistemainventario.Models.Ubicacion;
import com.electivaIII.sistemainventario.R;
import com.electivaIII.sistemainventario.Utils.ChangeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListUbicaciones extends Fragment {
    Boolean language=false;
    public ListUbicaciones() {
        // Required empty public constructor
    }

    ListView listUbicaciones;
    int frMain = 0;
    TextView txtFindListLocation;

    List<Ubicacion> ubicacionesArray = new ArrayList<>();
    Ubicacion ubicacion;



    ArrayList<String> warehousesName = new ArrayList<>();
    ArrayList<String> warehousesAddress = new ArrayList<>();
    ArrayList<String> warehousesLat = new ArrayList<>();
    ArrayList<String> warehousesLong = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_ubicaciones, container, false);
        listUbicaciones = v.findViewById(R.id.listUbicaciones);

        txtFindListLocation = v.findViewById(R.id.editText);

        SharedPreferences idioma = getActivity().getSharedPreferences("idioma", Context.MODE_PRIVATE);
        language = idioma.getBoolean("trueIdioma",false);
        if (language==true){
            txtFindListLocation.setHint("Search");
        }

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            frMain = bundle.getInt("frMain");

            warehousesName = bundle.getStringArrayList("warehousesName");
            warehousesAddress = bundle.getStringArrayList("warehousesAddress");
            warehousesLat = bundle.getStringArrayList("warehousesLat");
            warehousesLong = bundle.getStringArrayList("warehousesLong");


            if (ubicacionesArray.size() == 0) {
                for (int i=0; i<warehousesName.size(); i++) {
                    ubicacion = new Ubicacion(warehousesName.get(i), warehousesAddress.get(i), Double.parseDouble(warehousesLat.get(i)), Double.parseDouble(warehousesLong.get(i)));
                    ubicacionesArray.add(ubicacion);

                }
            }

        }



        final UbicacionesAdapter ubicacionesAdapter = new UbicacionesAdapter(getContext(), ubicacionesArray);
        listUbicaciones.setAdapter(ubicacionesAdapter);

        listUbicaciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View
                    view, final int position, long id) {
                MapsUbi mapsUbi = new MapsUbi();
                Bundle bundle = new Bundle();
                bundle.putDouble("latitud", ubicacionesArray.get(position).getLat());
                bundle.putDouble("longitud", ubicacionesArray.get(position).getLon());
                bundle.putString("almacen", ubicacionesArray.get(position).getName());
                bundle.putString("direccion", ubicacionesArray.get(position).getAddress());
                mapsUbi.setArguments(bundle);

                ChangeFragment.changeFragment(frMain, getActivity(), mapsUbi);

            }
        });


        txtFindListLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ubicacionesAdapter.getFilter().filter(s.toString());
            }
        });


        return v;
    }
}
