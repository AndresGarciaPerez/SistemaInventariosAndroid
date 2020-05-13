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
import android.widget.Toast;

import com.electivaIII.sistemainventario.R;
import com.electivaIII.sistemainventario.Utils.ChangeFragment;
import com.electivaIII.sistemainventario.Utils.TypeOfDevice;
import com.electivaIII.sistemainventario.fragments.ListUbicaciones;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleAccesorios extends Fragment {
    Boolean language=false;
    public DetalleAccesorios() {
        // Required empty public constructor
    }

    TextView txtNameAccesorioDescription, txtquantity, txtalmacen, txtCodigo;
    ImageView imgAccesorioDetalle;
    Button btnUbicacionDetalleAccesorio;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detalle_accesorios, container, false);

        txtquantity = v.findViewById(R.id.txtquantity);
        txtalmacen = v.findViewById(R.id.txtalmacen);
        txtCodigo = v.findViewById(R.id.txtCodigo);


        txtNameAccesorioDescription = v.findViewById(R.id.txtNameAccesorioDescription);
        imgAccesorioDetalle = v.findViewById(R.id.imgAccesorioDetalle);
        btnUbicacionDetalleAccesorio = v.findViewById(R.id.btnUbicacionDetalleAccesorio);


        Bundle bundle = this.getArguments();

        int inventorie_id = 0;
        int quantity = 0;

        int warehouse_id = 0;
        String warehouse = "";

        int product_id = 0;
        String name = "";
        String image = "";
        String product_code = "";

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

        txtquantity.setText(String.valueOf(quantity));
        txtalmacen.setText(warehouse);
        txtCodigo.setText(product_code);

        txtNameAccesorioDescription.setText(name);

        //String imageUrl = "https://via.placeholder.com/500";

        Picasso.get().load(image).placeholder(R.drawable.progress_animation).into(imgAccesorioDetalle);

        //imgAccesorioDetalle.setImageResource(image);

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
                int fragmentMain;

                if (new TypeOfDevice().isMovil()) {

                    fragmentMain = R.id.frMainAccesorio;
                } else {

                    fragmentMain = R.id.f_detalle_accesorio;
                }

                data.putInt("frMain", fragmentMain);
                fragmentListUbicaciones.setArguments(data);

                ChangeFragment.changeFragment(fragmentMain, getActivity(), fragmentListUbicaciones);

            }
        });


        return v;
    }

}
