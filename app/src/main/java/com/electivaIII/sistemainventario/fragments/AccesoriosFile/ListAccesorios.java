package com.electivaIII.sistemainventario.fragments.AccesoriosFile;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.electivaIII.sistemainventario.Adapters.AccesoriosAdapter;
import com.electivaIII.sistemainventario.MainActivity;
import com.electivaIII.sistemainventario.Models.Accesorio;
import com.electivaIII.sistemainventario.Models.AccesoriosRepuestosModel;
import com.electivaIII.sistemainventario.Models.Sesion;
import com.electivaIII.sistemainventario.Models.Ubicacion;
import com.electivaIII.sistemainventario.R;
import com.electivaIII.sistemainventario.Utils.ChangeFragment;
import com.electivaIII.sistemainventario.Utils.TypeOfDevice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.electivaIII.sistemainventario.Interfaces.Globals.BASE_URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListAccesorios extends Fragment {
    Boolean language=false;

    public ListAccesorios() {
        // Required empty public constructor
    }

    ListView listAccesorios;
    AccesoriosAdapter accesoriosAdapter;
    TextView txtFindListAccesorios;

    Sesion sesion;

    List<Accesorio> accesoriosModel = new ArrayList<>();
    ArrayList<String> warehousesName = new ArrayList<>();
    ArrayList<String> warehousesAddress = new ArrayList<>();
    ArrayList<String> warehousesLat = new ArrayList<>();
    ArrayList<String> warehousesLong = new ArrayList<>();
    Accesorio accesorio;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_accesorios, container, false);
        listAccesorios = v.findViewById(R.id.listAccesorios);
        txtFindListAccesorios = v.findViewById(R.id.txtFindListAccesorios);

        HTTPaccesories();

        SharedPreferences idioma = getActivity().getSharedPreferences("idioma", Context.MODE_PRIVATE);
        language = idioma.getBoolean("trueIdioma",false);
        if (language==true){
           txtFindListAccesorios.setHint("Search");
        }




        return v;
    }

    public void accesorios() {


        accesoriosAdapter = new AccesoriosAdapter(getContext(), accesoriosModel);

        listAccesorios.setAdapter(accesoriosAdapter);


        listAccesorios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View
                    view, final int position, long id) {



                Fragment fragmentDetalle = new DetalleAccesorios();
                Bundle data = new Bundle();
                data.putInt("inventorie_id", accesoriosModel.get(position).getInventorie_id());
                data.putInt("quantity", accesoriosModel.get(position).getQuantity());

                data.putInt("warehouse_id", accesoriosModel.get(position).getWarehouse_id());
                data.putString("warehouse", accesoriosModel.get(position).getWarehouse());

                data.putString("name", accesoriosModel.get(position).getName());
                data.putString("image", accesoriosModel.get(position).getImage());
                data.putInt("product_id", accesoriosModel.get(position).getProduct_id());
                data.putString("product_code", accesoriosModel.get(position).getProduct_code());
                data.putStringArrayList("warehousesName", warehousesName);
                data.putStringArrayList("warehousesAddress", warehousesAddress);
                data.putStringArrayList("warehousesLat", warehousesLat);
                data.putStringArrayList("warehousesLong", warehousesLong);

                fragmentDetalle.setArguments(data);

                Fragment fragment = getFragmentManager().findFragmentById(R.id.f_detalle_accesorio);
                if (fragment == null) {

                    new TypeOfDevice(true);

                    ChangeFragment.changeFragment(R.id.frMainAccesorio, getActivity(), fragmentDetalle);

                } else {
                    new TypeOfDevice(false);
                    ChangeFragment.changeFragment(R.id.f_detalle_accesorio, getActivity(), fragmentDetalle);
                }




            }
        });
        txtFindListAccesorios.addTextChangedListener(new TextWatcher() {
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

    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }

    ProgressDialog progressDialog;
    private void HTTPaccesories() {

        sesion = new Sesion();
        SharedPreferences sesionAct =  getContext().getSharedPreferences("sesionActiva", Context.MODE_PRIVATE);
        String token = sesionAct.getString("token","");
        String url = "";
        if (token!=""){

            url = BASE_URL+"api/v1/inventories?access_token=" + token;

        } else {

            url = BASE_URL+"api/v1/inventories?access_token=" + sesion.getToken();
        }

        progressDialog = new ProgressDialog(getActivity(), R.style.AlertDialogStyle);
        progressDialog.setMessage("Obteniendo accesorios...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        progressDialog.show();

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {



                if (response.length() != 0) {

                    for (int i=0; i< response.length(); i++) {
                        try {

                            JSONObject jsonObject = response.getJSONObject(i);
                            int inventorie_id = jsonObject.getInt("id");
                            int quantity = jsonObject.getInt("quantity");

                            JSONObject warehouse = jsonObject.getJSONObject("warehouse");
                            int warehouse_id = warehouse.getInt("id");
                            String warehousename = warehouse.getString("name");


                            JSONObject product = jsonObject.getJSONObject("product");
                            int product_id = product.getInt("id");
                            String name = product.getString("name");
                            String product_code = product.getString("product_code");

                            String image = product.getString("image");

                            JSONArray warehousearray = product.getJSONArray("warehouse");

                            for (int w=0; w<warehousearray.length(); w++) {

                                JSONObject jsonObjectWarehouse = warehousearray.getJSONObject(i);
                                String objectNameWarehouse = jsonObjectWarehouse.getString("name");
                                String objectAddressWarehouse = jsonObjectWarehouse.getString("address");
                                String objectLatWarehouse = jsonObjectWarehouse.getString("lat");
                                String objectLongWarehouse = jsonObjectWarehouse.getString("long");

                                warehousesName.add(objectNameWarehouse);
                                warehousesAddress.add(objectAddressWarehouse);
                                warehousesLat.add(objectLatWarehouse);
                                warehousesLong.add(objectLongWarehouse);


                            }
                            if (image.isEmpty()) {
                                image = "https://via.placeholder.com/500";
                            }

                            accesorio = new Accesorio(inventorie_id, quantity, warehouse_id, warehousename, product_id,
                                    name, image, product_code);

                            accesoriosModel.add(accesorio);


                         } catch (JSONException e) {
                            Log.e("error: ", e.toString());
                        }
                    }

                    accesorios();
                } else {
                    Toast.makeText(getActivity(), "Sin accesorios", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override

            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Ocurrió un error!!", Toast.LENGTH_SHORT).show();
                Log.e("ERROR", error.toString());
                progressDialog.dismiss();
            }
        });
        queue.add(request);
    }


}
