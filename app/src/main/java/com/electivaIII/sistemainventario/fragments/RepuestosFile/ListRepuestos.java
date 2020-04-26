package com.electivaIII.sistemainventario.fragments.RepuestosFile;


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
import com.electivaIII.sistemainventario.fragments.AccesoriosFile.ListAccesorios;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListRepuestos extends Fragment {
    Boolean language=false;
    public ListRepuestos() {
        // Required empty public constructor
    }
    ListView listRepuestos;
    AccesoriosAdapter accesoriosAdapter;
    List<AccesoriosRepuestosModel> accesoriosRepuestosModelsList = new ArrayList<>();
    AccesoriosRepuestosModel accesoriosRepuestosModel;

    TextView txtFindListRespuestos;



    setInfoListRepuestos setInfoListRepuestos;

    public interface setInfoListRepuestos {
        void showdatadetailRepuestos(String name, String item, int image);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_repuestos, container, false);
        listRepuestos = v.findViewById(R.id.listRespuestos);
        txtFindListRespuestos = v.findViewById(R.id.txtFindListRespuestos);

        SharedPreferences idioma = getActivity().getSharedPreferences("idioma", Context.MODE_PRIVATE);
        language = idioma.getBoolean("trueIdioma",false);
        if (language==true){
            txtFindListRespuestos.setHint("Search");
        }

        String[] name = {
                "Placa iPhone 6s",
                "Bateria Samsung S7 Edge",
                "Bandeja Sim Samsung S8"

        };
        String[] disponibles = {
                "item 2",
                "item 4",
                "item 6"
        };

        int[] images = {
                R.drawable.ejemplo2,
                R.drawable.baterias7,
                R.drawable.simgalaxys8,
        };

        for (int i=0; i<name.length; i ++){

            accesoriosRepuestosModel = new AccesoriosRepuestosModel(name[i], disponibles[i], images[i]);
            accesoriosRepuestosModelsList.add(accesoriosRepuestosModel);
        }

        accesoriosAdapter = new AccesoriosAdapter(getContext(), accesoriosRepuestosModelsList);

        // LLenamos el modelo con la informacion
        accesoriosRepuestosModel.setAccesorios(accesoriosRepuestosModelsList);
        listRepuestos.setAdapter(accesoriosAdapter);


        listRepuestos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View
                    view, final int position, long id) {
                Fragment fragmentDetalle = new DetalleRepuestos();
                Bundle data = new Bundle();
                data.putString("name", accesoriosRepuestosModelsList.get(position).getName());
                data.putString("item", accesoriosRepuestosModelsList.get(position).getItem());
                data.putInt("image", accesoriosRepuestosModelsList.get(position).getImage());
                fragmentDetalle.setArguments(data);

                ChangeFragment.changeFragment(R.id.f_detalle_repuesto, getActivity(), fragmentDetalle);


            }
        });


        txtFindListRespuestos.addTextChangedListener(new TextWatcher() {
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

    @Override
    public void onAttach(Context context){
        setInfoListRepuestos=(setInfoListRepuestos) context;
        super.onAttach(context);
    }

}
