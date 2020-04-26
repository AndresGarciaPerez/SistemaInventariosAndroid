package com.electivaIII.sistemainventario.fragments.AccesoriosFile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.electivaIII.sistemainventario.R;
import com.electivaIII.sistemainventario.Utils.ChangeFragment;
import com.electivaIII.sistemainventario.fragments.ListUbicaciones;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleAccesorios extends Fragment {
    Boolean language=false;
    public DetalleAccesorios() {
        // Required empty public constructor
    }

    TextView txtNameAccesorioDescription;
    ImageView imgAccesorioDetalle;
    Button btnUbicacionDetalleAccesorio;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detalle_accesorios, container, false);

        Bundle bundle = this.getArguments();
        String name = "";
        int image = 0;
        if (bundle != null) {
            name = bundle.getString("name");
            image = bundle.getInt("image");
        }
        txtNameAccesorioDescription = v.findViewById(R.id.txtNameAccesorioDescription);
        imgAccesorioDetalle = v.findViewById(R.id.imgAccesorioDetalle);
        btnUbicacionDetalleAccesorio = v.findViewById(R.id.btnUbicacionDetalleAccesorio);

        txtNameAccesorioDescription.setText(name);
        imgAccesorioDetalle.setImageResource(image);

        SharedPreferences idioma = getActivity().getSharedPreferences("idioma", Context.MODE_PRIVATE);
        language = idioma.getBoolean("trueIdioma",false);
        if (language==true){
            btnUbicacionDetalleAccesorio.setText("Location");
        }


        btnUbicacionDetalleAccesorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragmentListUbicaciones = new ListUbicaciones();
                Bundle data = new Bundle();
                data.putInt("frMain", R.id.f_detalle_accesorio);
                fragmentListUbicaciones.setArguments(data);

                ChangeFragment.changeFragment(R.id.f_detalle_accesorio, getActivity(), fragmentListUbicaciones);

            }
        });


        return v;
    }

}
