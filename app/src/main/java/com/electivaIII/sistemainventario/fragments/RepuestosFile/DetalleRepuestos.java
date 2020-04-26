package com.electivaIII.sistemainventario.fragments.RepuestosFile;

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
import android.widget.Toast;

import com.electivaIII.sistemainventario.R;
import com.electivaIII.sistemainventario.Utils.ChangeFragment;
import com.electivaIII.sistemainventario.Utils.TypeOfDevice;
import com.electivaIII.sistemainventario.fragments.ListUbicaciones;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleRepuestos extends Fragment {
    Boolean language=false;
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

        SharedPreferences idioma = getActivity().getSharedPreferences("idioma", Context.MODE_PRIVATE);
        language = idioma.getBoolean("trueIdioma",false);
        if (language==true){
            btnUbicacionDetalleRepuesto.setText("Location");
        }


        btnUbicacionDetalleRepuesto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragmentListUbicaciones = new ListUbicaciones();
                Bundle data = new Bundle();
                int fragmentMain;

                if (new TypeOfDevice().isMovil()) {

                    fragmentMain = R.id.frMainRepuestos;
                } else {

                    fragmentMain = R.id.f_detalle_repuesto;
                }

                data.putInt("frMain", fragmentMain);
                fragmentListUbicaciones.setArguments(data);


                ChangeFragment.changeFragment(fragmentMain, getActivity(), fragmentListUbicaciones);
            }
        });

        return v;
    }
}
