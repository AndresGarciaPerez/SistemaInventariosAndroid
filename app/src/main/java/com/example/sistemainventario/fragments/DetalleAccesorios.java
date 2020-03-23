package com.example.sistemainventario.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sistemainventario.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleAccesorios extends Fragment {

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

        btnUbicacionDetalleAccesorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new ListUbicaciones());
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
