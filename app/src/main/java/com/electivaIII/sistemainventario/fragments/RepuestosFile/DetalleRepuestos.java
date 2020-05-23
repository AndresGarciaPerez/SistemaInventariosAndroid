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
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleRepuestos extends Fragment {
    Boolean language=false;
    public DetalleRepuestos() {
        // Required empty public constructor
    }

    ImageView imageViewDetalleRespuesto;
    TextView txtProductoDetalleRepuestos, txtquantityRepuesto, txtalmacenRepuesto, txtCodigoRepuesto;
    Button btnUbicacionDetalleRepuesto;

    int inventorie_id = 0;
    int quantity = 0;

    int warehouse_id = 0;
    String warehouse = "";

    int product_id = 0;
    String name = "";
    String image = "";
    String product_code = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detalle_repuestos, container, false);
        Bundle bundle = this.getArguments();
        if (bundle != null) {

            inventorie_id = bundle.getInt("inventorie_id");
            quantity = bundle.getInt("quantity");
            warehouse_id = bundle.getInt("warehouse_id");
            warehouse = bundle.getString("warehouse");
            product_id = bundle.getInt("product_id");
            name = bundle.getString("name");
            image = bundle.getString("image");
            product_code = bundle.getString("product_code");
        }

        imageViewDetalleRespuesto = v.findViewById(R.id.imageViewDetalleRespuesto);
        txtProductoDetalleRepuestos = v.findViewById(R.id.txtProductoDetalleRepuestos);

        txtquantityRepuesto = v.findViewById(R.id.txtquantityRepuesto);
        txtalmacenRepuesto = v.findViewById(R.id.txtalmacenRepuesto);
        txtCodigoRepuesto = v.findViewById(R.id.txtCodigoRepuesto);

        btnUbicacionDetalleRepuesto = v.findViewById(R.id.btnUbicacionDetalleRepuesto);


        txtProductoDetalleRepuestos.setText(name);
        txtquantityRepuesto.setText(String.valueOf(quantity));
        txtalmacenRepuesto.setText(warehouse);
        txtCodigoRepuesto.setText(product_code);



        Picasso.get().load(image).placeholder(R.drawable.progress_animation).into(imageViewDetalleRespuesto);

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
