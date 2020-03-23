package com.example.sistemainventario.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sistemainventario.Models.AccesoriosModel;
import com.example.sistemainventario.R;
import com.example.sistemainventario.fragments.ListAccesorios;

import java.util.List;

public class AccesoriosAdapter extends BaseAdapter {
    private Context context;

    private List<AccesoriosModel> accesoriosModelsList;
    TextView tvTitulo;
    TextView tvSubTitulo;
    ImageView imgImagenes;

    public AccesoriosAdapter(Context context, List<AccesoriosModel> modelList) {
        this.context = context;
        this.accesoriosModelsList = modelList;
    }


    @Override
    public int getCount() {
        return accesoriosModelsList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            v= new View(context);
            v=inflater.inflate(R.layout.desing,null);
        } else{
            v=(View) convertView;
        }

        tvTitulo = (TextView)v.findViewById(R.id.tvTitulo);
        tvSubTitulo = (TextView)v.findViewById(R.id.tvSubTitulo);
        imgImagenes = (ImageView)v.findViewById(R.id.imgImagenes);

        tvTitulo.setText(accesoriosModelsList.get(position).getName());
        tvSubTitulo.setText(accesoriosModelsList.get(position).getItem());
        imgImagenes.setImageResource(accesoriosModelsList.get(position).getImage());
        return v;
    }
}
