package com.electivaIII.sistemainventario.fragments.RepuestosFile;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.electivaIII.sistemainventario.Adapters.AccesoriosAdapter;
import com.electivaIII.sistemainventario.Adapters.RepuestosAdapter;
import com.electivaIII.sistemainventario.Models.Accesorio;
import com.electivaIII.sistemainventario.Models.AccesoriosRepuestosModel;
import com.electivaIII.sistemainventario.Models.Repuesto;
import com.electivaIII.sistemainventario.Models.Sesion;
import com.electivaIII.sistemainventario.R;
import com.electivaIII.sistemainventario.Utils.ChangeFragment;
import com.electivaIII.sistemainventario.Utils.TypeOfDevice;
import com.electivaIII.sistemainventario.fragments.AccesoriosFile.ListAccesorios;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.electivaIII.sistemainventario.Interfaces.Globals.BASE_URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListRepuestos extends Fragment {
    Boolean language=false;
    public ListRepuestos() {
        // Required empty public constructor
    }
    ListView listRepuestos;
    RepuestosAdapter accesoriosAdapter;
    List<Repuesto> accesoriosRepuestosModelsList = new ArrayList<>();
    AccesoriosRepuestosModel accesoriosRepuestosModel;

    TextView txtFindListRespuestos;
    Sesion sesion;
    Repuesto repuestos;

    List<Repuesto> repuestoList = new ArrayList<>();


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

        HTTPrespuestos();

        return v;
    }

    private void repuesto() {

        accesoriosAdapter = new RepuestosAdapter(getContext(), repuestoList);

        // LLenamos el modelo con la informacion
        accesoriosRepuestosModel.setAccesorios(accesoriosRepuestosModelsList);
        listRepuestos.setAdapter(accesoriosAdapter);


        listRepuestos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View
                    view, final int position, long id) {
                Fragment fragmentDetalle = new DetalleRepuestos();
                Bundle data = new Bundle();
                data.putInt("inventorie_id", repuestoList.get(position).getInventorie_id());
                data.putInt("quantity", repuestoList.get(position).getQuantity());

                data.putInt("warehouse_id", repuestoList.get(position).getWarehouse_id());
                data.putString("warehouse", repuestoList.get(position).getWarehouse());

                data.putString("name", repuestoList.get(position).getName());
                data.putString("image", repuestoList.get(position).getImage());
                data.putInt("product_id", repuestoList.get(position).getProduct_id());
                data.putString("product_code", repuestoList.get(position).getProduct_code());

                fragmentDetalle.setArguments(data);

                Fragment fragment = getFragmentManager().findFragmentById(R.id.f_detalle_repuesto);
                if (fragment == null) {

                    new TypeOfDevice(true);

                    ChangeFragment.changeFragment(R.id.frMainRepuestos, getActivity(), fragmentDetalle);

                } else {
                    new TypeOfDevice(false);
                    ChangeFragment.changeFragment(R.id.f_detalle_repuesto, getActivity(), fragmentDetalle);
                }


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
    }

    @Override
    public void onAttach(Context context){
        setInfoListRepuestos=(setInfoListRepuestos) context;
        super.onAttach(context);
    }

    ProgressDialog progressDialog;
    private void HTTPrespuestos() {

        sesion = new Sesion();
        SharedPreferences sesionAct =  getContext().getSharedPreferences("sesionActiva", Context.MODE_PRIVATE);
        String token = sesionAct.getString("token","");
        String url = "";
        if (token!=""){

            url = BASE_URL+"api/v1/inventories?access_token=" + token+"&product_type=2";

        } else {

            url = BASE_URL+"api/v1/inventories?access_token=" + sesion.getToken()+"&product_type=2";
        }

        Log.i("url", url);

        progressDialog = new ProgressDialog(getActivity(), R.style.AlertDialogStyle);
        String messageDialog;
        if (language) {
            messageDialog = "Getting spare part...";
        } else {
            messageDialog = "Obteniendo los repuestos...";
        }
        progressDialog.setMessage(messageDialog);
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


                            if (image.isEmpty()) {
                                image = "https://via.placeholder.com/500";
                            }

                            repuestos = new Repuesto(inventorie_id, quantity, warehouse_id, warehousename, product_id,
                                    name, image, product_code);

                            repuestoList.add(repuestos);

                        } catch (JSONException e) {
                            Log.e("error: ", e.toString());
                        }
                    }


                } else {
                    Toast.makeText(getActivity(), "Sin accesorios", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();

                repuesto();


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
