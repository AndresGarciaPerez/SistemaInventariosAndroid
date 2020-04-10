package com.example.sistemainventario.fragments.RepuestosFile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sistemainventario.R;
import com.example.sistemainventario.Utils.ChangeFragment;
import com.example.sistemainventario.fragments.ListUbicaciones;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleRepuestos extends Fragment {

    public DetalleRepuestos() {
        // Required empty public constructor
    }

    ImageView imageViewDetalleRespuesto;
    TextView txtProductoDetalleRepuestos;
    Button btnUbicacionDetalleRepuesto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detalle_repuestos, container, false);
        Bundle bundle = this.getArguments();
        String name = "";
        int image = 0;
        if (bundle != null) {
            name = bundle.getString("name");
            image = bundle.getInt("image");
        }

        imageViewDetalleRespuesto = v.findViewById(R.id.imageViewDetalleRespuesto);
        txtProductoDetalleRepuestos = v.findViewById(R.id.txtProductoDetalleRepuestos);
        btnUbicacionDetalleRepuesto = v.findViewById(R.id.btnUbicacionDetalleRepuesto);

        txtProductoDetalleRepuestos.setText(name);
        imageViewDetalleRespuesto.setImageResource(image);

        btnUbicacionDetalleRepuesto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Fragment fragmentListUbicaciones = new ListUbicaciones();
                Bundle data = new Bundle();
                data.putInt("frMain", R.id.frMainRepuestos);
                fragmentListUbicaciones.setArguments(data);

                ChangeFragment.changeFragment(R.id.frMainRepuestos, getActivity(), fragmentListUbicaciones);
            }
        });

        return v;
    }
}
