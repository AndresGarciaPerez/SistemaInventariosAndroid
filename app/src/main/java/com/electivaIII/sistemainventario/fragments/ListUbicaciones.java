package com.electivaIII.sistemainventario.fragments;


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
import com.electivaIII.sistemainventario.Models.UbicacionesModel;
import com.electivaIII.sistemainventario.R;
import com.electivaIII.sistemainventario.Utils.ChangeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListUbicaciones extends Fragment {


    public ListUbicaciones() {
        // Required empty public constructor
    }

    ListView listUbicaciones;
    int frMain = 0;
    TextView txtFindListLocation;

    List<UbicacionesModel> ubicacionesModelsArray = new ArrayList<>();
    UbicacionesModel ubicacionesModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_ubicaciones, container, false);
        listUbicaciones = v.findViewById(R.id.listUbicaciones);

        txtFindListLocation = v.findViewById(R.id.editText);

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

        if (ubicacionesModelsArray.size() == 0){

            for (int i= 0; i<almacenes.length; i++){
                ubicacionesModel = new UbicacionesModel(almacenes[i], cantidades[i],
                        images[i], latitud[i], longitud[i]);
                ubicacionesModelsArray.add(ubicacionesModel);
            }
        }


        Log.i("value", String.valueOf(ubicacionesModelsArray.size()));
        Log.i("value", String.valueOf(ubicacionesModel.getUbicaciones().size()));

        final UbicacionesAdapter ubicacionesAdapter = new UbicacionesAdapter(getContext(), ubicacionesModelsArray);
        listUbicaciones.setAdapter(ubicacionesAdapter);

        listUbicaciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View
                    view, final int position, long id) {
                MapsUbi mapsUbi = new MapsUbi();
                Bundle bundle = new Bundle();
                bundle.putDouble("latitud", ubicacionesModelsArray.get(position).getLatitud());
                bundle.putDouble("longitud", ubicacionesModelsArray.get(position).getLongitud());
                bundle.putString("almacen", ubicacionesModelsArray.get(position).getUbication_de_almacen());
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
