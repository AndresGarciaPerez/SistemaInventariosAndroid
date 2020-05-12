package com.electivaIII.sistemainventario.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.electivaIII.sistemainventario.MainActivity;
import com.electivaIII.sistemainventario.Models.Sesion;
import com.electivaIII.sistemainventario.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Preferences extends Fragment {

    public static final String MyPREFERENCES = "nightModePrefs";
    public static final String KEY_ISNIGHTMODE = "isNightMode";
    SharedPreferences sharedPreferences;

    public Preferences() {
        // Required empty public constructor
    }
    public Switch aSwitch;
    public Switch inglesSwitch, sesionActivaSwitch;
    private TextView tvNoche, tvIngles, tvPantalla, tvSesion;
    private  Boolean estadoIdioma;
    String sesion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container!=null){
            container.removeAllViews();
        }
        View v = inflater.inflate(R.layout.fragment_preferences, container, false);

        sharedPreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        aSwitch = v.findViewById(R.id.sModoOscuro);
        inglesSwitch = v.findViewById(R.id.swIngles);
        sesionActivaSwitch = v.findViewById(R.id.swSesion);
        tvNoche = v.findViewById(R.id.tvNoche);
        tvIngles = v.findViewById(R.id.tvIngles);
        //tvPantalla = v.findViewById(R.id.tvPantalla);
        tvSesion = v.findViewById(R.id.tvSesion);

        SharedPreferences idioma = getActivity().getSharedPreferences("idioma",Context.MODE_PRIVATE);
        estadoIdioma = idioma.getBoolean("trueIdioma",false);

        if (estadoIdioma==true){inglesSwitch.setChecked(true);
            tvNoche.setText("DarkMode");
            tvSesion.setText("keep session active");
            tvIngles.setText("change to Spanish");
           // tvPantalla.setText("Select start view");
        }

        SharedPreferences sesionAct = getActivity().getSharedPreferences("sesionActiva",Context.MODE_PRIVATE);
        sesion = sesionAct.getString("token","");
        if(sesion!=""){
            sesionActivaSwitch.setChecked(true);
        }

        inglesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    SharedPreferences idioma = getActivity().getSharedPreferences("idioma",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = idioma.edit();
                    editor.putBoolean("trueIdioma",true);
                    editor.apply();
                    Intent i = new Intent(getActivity(), MainActivity.class);
                    startActivity(i);
                }else{
                    tvNoche.setText("Modo noche");
                    tvSesion.setText("Mantener sesion activa");
                    tvIngles.setText("Cambiar a idioma ingles");
                   // tvPantalla.setText("Seleccionar pantalla de inicio");

                    SharedPreferences idioma = getActivity().getSharedPreferences("idioma",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = idioma.edit();
                    editor.putBoolean("trueIdioma",false);
                    editor.apply();

                    Intent i = new Intent(getActivity(), MainActivity.class);
                    startActivity(i);
                }
            }
        });
        checkNightModeActivated();

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    saveNightModeState(true);

                    SharedPreferences estado = getActivity().getSharedPreferences("estado",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = estado.edit();
                    editor.putBoolean("true",true);
                    editor.apply();
                   // getActivity().recreate();
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    saveNightModeState(false);

                    SharedPreferences estado = getActivity().getSharedPreferences("estado",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = estado.edit();
                    editor.putBoolean("true",false);
                    editor.apply();
                   // getActivity().recreate();
                }
            }
        });

        sesionActivaSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Sesion sesion = new Sesion();
                if (isChecked){
                    sesionActivaSwitch.setChecked(true);
                    SharedPreferences sesionActiva = getActivity().getSharedPreferences("sesionActiva",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sesionActiva.edit();
                    editor.putString("token",sesion.getToken()); //En lugar de apiToken, debera ir el token generado por la Api
                    editor.apply();
                }else{
                    SharedPreferences sesionActiva = getActivity().getSharedPreferences("sesionActiva",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sesionActiva.edit();
                    editor.putString("token","");
                    editor.apply();
                }
            }
        });


        return v;
    }

    private void saveNightModeState(boolean nightMode) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_ISNIGHTMODE, nightMode);
        editor.apply();
    }

    public void checkNightModeActivated(){
        if (sharedPreferences.getBoolean(KEY_ISNIGHTMODE, false)){
            aSwitch.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            aSwitch.setChecked(false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}
