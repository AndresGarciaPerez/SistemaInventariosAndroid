package com.electivaIII.sistemainventario.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.electivaIII.sistemainventario.Models.Accesorio;
import com.electivaIII.sistemainventario.Models.AccesoriosRepuestosModel;
import com.electivaIII.sistemainventario.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AccesoriosAdapter extends BaseAdapter implements Filterable {
    private Context context;

    private List<Accesorio> accesoriosRepuestosModelsList;

    public List<Accesorio> itemsAccesorioModel;
    TextView tvTitulo;
    TextView tvSubTitulo;
    ImageView imgImagenes;



    public AccesoriosAdapter(Context context, List<Accesorio> modelList) {
        this.context = context;
        this.accesoriosRepuestosModelsList = modelList;
        this.itemsAccesorioModel = modelList;
    }


    @Override
    public int getCount() {
        return accesoriosRepuestosModelsList.size();
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
        Log.i("name", accesoriosRepuestosModelsList.get(position).getName());

        tvTitulo.setText(accesoriosRepuestosModelsList.get(position).getName());
        tvSubTitulo.setText(String.valueOf(accesoriosRepuestosModelsList.get(position).getQuantity()));

        String imageUrlr = accesoriosRepuestosModelsList.get(position).getImage();

        Picasso.get().load(imageUrlr).placeholder(R.drawable.progress_animation).into(imgImagenes);
        return v;
    }


    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.count = itemsAccesorioModel.size();
                    filterResults.values = itemsAccesorioModel;

                }else{
                    List<Accesorio> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(Accesorio itemsModel:itemsAccesorioModel){

                        if(itemsModel.getName().toLowerCase().contains(searchStr)
                                || itemsModel.getName().toLowerCase().startsWith(searchStr)){

                            resultsModel.add(itemsModel);
                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                accesoriosRepuestosModelsList = (List<Accesorio>) results.values;
                notifyDataSetChanged();
            }
        };

        return filter;
    }
}
