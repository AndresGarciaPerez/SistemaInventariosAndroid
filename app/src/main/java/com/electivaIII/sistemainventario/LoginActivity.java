package com.electivaIII.sistemainventario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.electivaIII.sistemainventario.Models.Sesion;
import com.electivaIII.sistemainventario.fragments.Preferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.electivaIII.sistemainventario.Interfaces.Globals.BASE_URL;

public class LoginActivity extends AppCompatActivity {

    Button btnIniciarSesion;
    TextView tvPassword, txtShowError;
    EditText etCorreo, etPass;
    Preferences preferences;
    Boolean estado=false, language=false;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferences = new Preferences();
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        tvPassword = findViewById(R.id.tvPass);
        txtShowError = findViewById(R.id.txtShowError);

        txtShowError.setText("");

        etPass = findViewById(R.id.etPass);
        etCorreo = findViewById(R.id.etMail);
        setTitle("Login");

        SharedPreferences sesionAct =  this.getSharedPreferences("sesionActiva", Context.MODE_PRIVATE);
        token = sesionAct.getString("token","");
        if (token!=""){
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }

       SharedPreferences estad = this.getSharedPreferences("estado",Context.MODE_PRIVATE);
       estado = estad.getBoolean("true",false);
        if (estado==true){AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); }

        SharedPreferences idioma = this.getSharedPreferences("idioma",Context.MODE_PRIVATE);
        language = idioma.getBoolean("trueIdioma",false);
        if (language==true){
            tvPassword.setText("I forgot my password");
            btnIniciarSesion.setText("log In");
            etCorreo.setHint("Example@mail.com");
        }


        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo = etCorreo.getText().toString().trim();
                String password = etPass.getText().toString().trim();

                if (!correo.isEmpty() && !password.isEmpty()) {

                    LoginRequest(correo, password);

                }

            }
        });

    }

    ProgressDialog progressDialog;
    public void LoginRequest(String mail, String pass) {
        progressDialog = new ProgressDialog(this, R.style.AlertDialogStyle);
        progressDialog.setMessage("Iniciando sesión...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        progressDialog.show();

        String url = BASE_URL+"oauth/token";

        Map<String, String> params = new HashMap();
        params.put("email", mail);
        params.put("password", pass);
        params.put("grant_type", "password");

        JSONObject parameters = new JSONObject(params);
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    String access_token = response.getString("access_token");
                    String refresh_token = response.getString("refresh_token");
                    String token_type = response.getString("token_type");
                    int expires_in = response.getInt("expires_in");

                    new Sesion(access_token, token_type, refresh_token, expires_in);

                    // USER INFO

                    JSONObject mJsonUser = response.getJSONObject("user");

                    int id = mJsonUser.getInt("id");
                    String firstName = mJsonUser.getString("first_name");
                    String lastName = mJsonUser.getString("last_name");

                    SharedPreferences sesionActiva = getApplicationContext().getSharedPreferences("sesionActiva",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sesionActiva.edit();
                    editor.putString("user_id", String.valueOf(id));
                    editor.putString("user_name", firstName + " " + lastName);
                    editor.apply();


                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();

                } catch (JSONException e){


                    txtShowError.setText("Something went wrong, try again later, or contact you with the administrator");
                    Log.e("VOLLEY","Error de parcing en Login - method: LoginRequest "+ e.toString());
                    e.printStackTrace();

                }

                progressDialog.dismiss();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                NetworkResponse response = error.networkResponse;
                if (response != null) {

                    String json = new String(response.data);

                    Log.i("VOLLEY", json);
                    if (error.networkResponse.statusCode == 400) {
                        txtShowError.setText("Credentials invalid, try out again");
                    }
                }
                txtShowError.setText("Something went wrong try again later, or contact you with the administrator");


                progressDialog.dismiss();

            }
        });

        queue.add(request);

    }


}
