package com.electivaIII.sistemainventario.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.electivaIII.sistemainventario.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePassword extends Fragment {
    Boolean language=false;
    EditText edtActual, edtNueva, edtConfirmar;
    Button btnConfirmar;
    TextView tvUsuario, tvPass;
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
        btnConfirmar = v.findViewById(R.id.btnConfirmar);
        edtActual = v.findViewById(R.id.edtActual);
        edtNueva = v.findViewById(R.id.edtNueva);
        edtConfirmar = v.findViewById(R.id.edtConfirmar);
        tvUsuario = v.findViewById(R.id.tUser);
        tvPass = v.findViewById(R.id.tPass);

        SharedPreferences idioma = getActivity().getSharedPreferences("idioma", Context.MODE_PRIVATE);
        language = idioma.getBoolean("trueIdioma",false);
        if (language==true){
            edtActual.setHint("Current Password");
            edtNueva.setHint("New Password");
            edtConfirmar.setHint("Confirm Password");
            btnConfirmar.setText("Confirm");
        }

        return v;
    }
}
