package com.electivaIII.sistemainventario.fragments.AccesoriosFile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.electivaIII.sistemainventario.Models.Accesorio;
import com.electivaIII.sistemainventario.Models.Sesion;
import com.electivaIII.sistemainventario.R;
import com.electivaIII.sistemainventario.Utils.ChangeFragment;
import com.electivaIII.sistemainventario.Utils.TypeOfDevice;
import com.electivaIII.sistemainventario.fragments.ListUbicaciones;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static com.electivaIII.sistemainventario.Interfaces.Globals.BASE_URL;

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


    ArrayList<String> warehousesName = new ArrayList<>();
    ArrayList<String> warehousesAddress = new ArrayList<>();
    ArrayList<String> warehousesLat = new ArrayList<>();
    ArrayList<String> warehousesLong = new ArrayList<>();

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
        View v = inflater.inflate(R.layout.fragment_detalle_accesorios, container, false);

        txtquantity = v.findViewById(R.id.txtquantity);
        txtalmacen = v.findViewById(R.id.txtalmacen);
        txtCodigo = v.findViewById(R.id.txtCodigo);


        txtNameAccesorioDescription = v.findViewById(R.id.txtNameAccesorioDescription);
        imgAccesorioDetalle = v.findViewById(R.id.imgAccesorioDetalle);
        btnUbicacionDetalleAccesorio = v.findViewById(R.id.btnUbicacionDetalleAccesorio);


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


                HTTPaccesories();

            }
        });


        return v;
    }

    ProgressDialog progressDialog;
    private void HTTPaccesories() {

        Sesion sesion = new Sesion();
        SharedPreferences sesionAct =  getContext().getSharedPreferences("sesionActiva", Context.MODE_PRIVATE);
        String token = sesionAct.getString("token","");
        String url = "";
        if (token!=""){

            url = BASE_URL+"api/v1/inventories/"+inventorie_id+"?access_token=" + token;

        } else {

            url = BASE_URL+"api/v1/inventories/"+inventorie_id+"?access_token=" + sesion.getToken();
        }

        progressDialog = new ProgressDialog(getActivity(), R.style.AlertDialogStyle);
        String messageDialog;
        if (language) {
            messageDialog = "Getting location of "+ name;
        } else {
            messageDialog = "Obteniendo ubicación de "+name;
        }
        progressDialog.setMessage(messageDialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        progressDialog.show();

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject product = response.getJSONObject("product");
                            JSONArray warehousearray = product.getJSONArray("warehouse");


                            Log.i("total", warehousesName.size()+"");
                            if (warehousesName.size() == 0) {
                                for (int w=0; w<warehousearray.length(); w++) {

                                    JSONObject jsonObjectWarehouse = warehousearray.getJSONObject(w);
                                    String objectNameWarehouse = jsonObjectWarehouse.getString("name");
                                    String objectAddressWarehouse = jsonObjectWarehouse.getString("address");
                                    String objectLatWarehouse = jsonObjectWarehouse.getString("lat");
                                    String objectLongWarehouse = jsonObjectWarehouse.getString("long");

                                    warehousesName.add(objectNameWarehouse);
                                    warehousesAddress.add(objectAddressWarehouse);
                                    warehousesLat.add(objectLatWarehouse);
                                    warehousesLong.add(objectLongWarehouse);

                                }

                            }

                            Fragment fragmentListUbicaciones = new ListUbicaciones();

                            Log.i("sizewarehouses", warehousesName.size()+"");
                            Bundle data = new Bundle();
                            int fragmentMain;

                            if (new TypeOfDevice().isMovil()) {

                                fragmentMain = R.id.frMainAccesorio;
                            } else {

                                fragmentMain = R.id.f_detalle_accesorio;
                            }

                            data.putInt("frMain", fragmentMain);

                            data.putStringArrayList("warehousesName", warehousesName);
                            data.putStringArrayList("warehousesAddress", warehousesAddress);
                            data.putStringArrayList("warehousesLat", warehousesLat);
                            data.putStringArrayList("warehousesLong", warehousesLong);
                            fragmentListUbicaciones.setArguments(data);

                            ChangeFragment.changeFragment(fragmentMain, getActivity(), fragmentListUbicaciones);


                        } catch (JSONException e) {
                            Log.e("error: ", e.toString());
                        }

                        progressDialog.dismiss();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getContext(), "Ocurrió un error!!", Toast.LENGTH_SHORT).show();
                        Log.e("ERROR", error.toString());
                        progressDialog.dismiss();

                    }
                }
        );

        queue.add(request);
    }

}
