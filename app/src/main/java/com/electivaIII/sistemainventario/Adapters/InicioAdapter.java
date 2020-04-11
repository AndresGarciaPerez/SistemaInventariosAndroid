package com.electivaIII.sistemainventario.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.electivaIII.sistemainventario.R;

public class InicioAdapter extends BaseAdapter {
    private Context context;
    private String[] titulo;
    private String[] subTitulo;
    private int[] imagenes;
    TextView tvTitulo;
    TextView tvSubTitulo;
    ImageView imgImagenes;

    public InicioAdapter(Context context, String[] titulo, String[] subTitulo, int[] imagenes) {
        this.context = context;
        this.titulo = titulo;
        this.subTitulo = subTitulo;
        this.imagenes = imagenes;
    }


    @Override
    public int getCount() {
        return titulo.length;
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

        tvTitulo.setText(titulo[position]);
        tvSubTitulo.setText(subTitulo[position]);
        imgImagenes.setImageResource(imagenes[position]);
        return v;
    }
}
