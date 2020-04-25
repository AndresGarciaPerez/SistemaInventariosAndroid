package com.electivaIII.sistemainventario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.electivaIII.sistemainventario.fragments.Preferences;

public class LoginActivity extends AppCompatActivity {
    Button btnIniciarSesion;
    TextView tvPassword;
    EditText etCorreo;
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
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
