package com.electivaIII.sistemainventario.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.electivaIII.sistemainventario.LoginActivity;
import com.electivaIII.sistemainventario.Models.Sesion;
import com.electivaIII.sistemainventario.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.electivaIII.sistemainventario.Interfaces.Globals.BASE_URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePassword extends Fragment {

    Boolean language=false;
    EditText edtActual, edtNueva, edtConfirmar;
    Button btnConfirmar;
    TextView tvUsuario, txtConfirmPassword, txtNewPassword, txtCurrentPassword;
    public ChangePassword() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container!=null){
            container.removeAllViews();
        }
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_change_password, container, false);
        btnConfirmar = v.findViewById(R.id.btnConfirmarChangePassword);
        edtActual = v.findViewById(R.id.edtActual);
        edtNueva = v.findViewById(R.id.edtNueva);
        edtConfirmar = v.findViewById(R.id.edtConfirmar);
        tvUsuario = v.findViewById(R.id.tUser);

        txtConfirmPassword = v.findViewById(R.id.txtConfirmPassword);
        txtNewPassword = v.findViewById(R.id.txtNewPassword);
        txtCurrentPassword = v.findViewById(R.id.txtCurrentPassword);


        SharedPreferences idioma = getActivity().getSharedPreferences("idioma", Context.MODE_PRIVATE);

        SharedPreferences sesionAct =  getContext().getSharedPreferences("sesionActiva", Context.MODE_PRIVATE);

        tvUsuario.setText(sesionAct.getString("user_name",""));

        language = idioma.getBoolean("trueIdioma",false);
        if (language){
            txtCurrentPassword.setText("Current Password");
            txtNewPassword.setText("New Password");
            txtConfirmPassword.setText("Confirm Password");
            btnConfirmar.setText("Confirm");
        } else {

            txtCurrentPassword.setText("Contraseña actual");
            txtNewPassword.setText("Nueva contraseña");
            txtConfirmPassword.setText("Confirmar nueva contraseña");
            btnConfirmar.setText("Confirmar");
        }

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String actual = edtActual.getText().toString().trim();
                String nueva = edtNueva.getText().toString().trim();
                String confirmacion = edtConfirmar.getText().toString().trim();

                String errorMessage, errorPasswordNotMatch;
                if (language) {
                    errorMessage = "Getting accesories...";
                    errorPasswordNotMatch = "Password does not match";
                } else {
                    errorMessage = "Obteniendo accesorios...";
                    errorPasswordNotMatch = "Contraseñas no coinciden";
                }


                if (nueva.isEmpty()) {
                    edtNueva.setError(errorMessage);
                    edtNueva.setFocusable(true);
                } else if(confirmacion.isEmpty()) {
                    edtConfirmar.setError(errorMessage);
                    edtConfirmar.setFocusable(true);
                } else if (!actual.isEmpty()) {
                    if (nueva.equals(confirmacion)) {

                        LogoutRequest(actual, nueva, confirmacion);

                    } else {
                        Toast.makeText(getActivity(), errorPasswordNotMatch, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    edtActual.setError(errorMessage);
                    edtActual.setFocusable(true);
                }
            }
        });

        return v;
    }

    ProgressDialog progressDialog;
    public void LogoutRequest(String password, String new_password, String confirm_password) {
        progressDialog = new ProgressDialog(getActivity(), R.style.AlertDialogStyle);
        progressDialog.setMessage("Espere...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        progressDialog.show();

        SharedPreferences sesionAct =  getContext().getSharedPreferences("sesionActiva", Context.MODE_PRIVATE);
        Sesion sesion = new Sesion();
        String token = sesionAct.getString("token","");
        String url = "";
        if (token!=""){

            url = BASE_URL+"api/v1/users/" + sesionAct.getString("user_id","") +"?access_token="+ token;

        } else {

            url = BASE_URL+"api/v1/users/" + sesionAct.getString("user_id","") +"?access_token="+ sesion.getToken();
        }

        Map<String, String> params = new HashMap();
        params.put("password", password);
        params.put("new_password", new_password);
        params.put("confirm_new_password", confirm_password);

        JSONObject parameters = new JSONObject(params);
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Intent login = new Intent(getContext(), LoginActivity.class);
                startActivity(login);
                progressDialog.dismiss();

                SharedPreferences sesionActiva = getActivity().getSharedPreferences("sesionActiva",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sesionActiva.edit();
                editor.putString("token","");
                editor.apply();

                getActivity().finish();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                NetworkResponse response = error.networkResponse;
                if (response != null) {

                    String json = new String(response.data);

                    Log.i("VOLLEY", json);
                    if (error.networkResponse.statusCode == 400) {
                    }
                }
                //txtShowError.setText("Something went wrong try again later, or contact you with the administrator");

                progressDialog.dismiss();

            }
        });
        queue.add(request);
    }
}
