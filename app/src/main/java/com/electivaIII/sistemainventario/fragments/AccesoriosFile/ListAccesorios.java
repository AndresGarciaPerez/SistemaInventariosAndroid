package com.electivaIII.sistemainventario.fragments.AccesoriosFile;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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
import com.electivaIII.sistemainventario.Models.AccesoriosRepuestosModel;
import com.electivaIII.sistemainventario.Models.Sesion;
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
    List<AccesoriosRepuestosModel> accesoriosRepuestosModelsList = new ArrayList<>();
    AccesoriosRepuestosModel accesoriosRepuestosModel;
    TextView txtFindListAccesorios;

    Sesion sesion;
    setInfoListAccesorios setInfoListAccesorios;


    public interface setInfoListAccesorios {
        void showdatadetailAccess(String name, String item, int image);
    }
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

        return v;
    }

    public void accesorios(ArrayList name, ArrayList disponibles, ArrayList images) {

        for (int i=0; i<name.size(); i ++){

            accesoriosRepuestosModel = new AccesoriosRepuestosModel(name.get(i).toString(), disponibles.get(i).toString(), Integer.valueOf(images.get(i).toString()));
            accesoriosRepuestosModelsList.add(accesoriosRepuestosModel);
        }

        accesoriosAdapter = new AccesoriosAdapter(getContext(), accesoriosRepuestosModelsList);

        // LLenamos el modelo con la informacion
        accesoriosRepuestosModel.setAccesorios(accesoriosRepuestosModelsList);

        listAccesorios.setAdapter(accesoriosAdapter);


        listAccesorios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View
                    view, final int position, long id) {
                Fragment fragmentDetalle = new DetalleAccesorios();
                Bundle data = new Bundle();
                data.putString("name", accesoriosRepuestosModelsList.get(position).getName());
                data.putString("item", accesoriosRepuestosModelsList.get(position).getItem());
                data.putInt("image", accesoriosRepuestosModelsList.get(position).getImage());
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

    }

    @Override
    public void onAttach(Context context){
        setInfoListAccesorios=(setInfoListAccesorios) context;
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

                ArrayList names = new ArrayList<>();
                ArrayList disponibles = new ArrayList<>();
                ArrayList images = new ArrayList<>();

                if (response.length() != 0) {

                    for (int i=0; i< response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            String quantity = jsonObject.getString("quantity");

                            JSONObject product = jsonObject.getJSONObject("product");

                            String name = product.getString("name");

                            names.add(name);
                            disponibles.add(quantity);
                            images.add(R.drawable.baterias7);


                         } catch (JSONException e) {
                            Log.e("error: ", e.toString());
                        }
                    }

                    accesorios(names, disponibles, images);
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
