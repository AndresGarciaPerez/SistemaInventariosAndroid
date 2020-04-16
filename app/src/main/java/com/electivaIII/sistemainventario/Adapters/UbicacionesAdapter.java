package com.electivaIII.sistemainventario.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.electivaIII.sistemainventario.Models.UbicacionesModel;
import com.electivaIII.sistemainventario.R;

import java.util.ArrayList;
import java.util.List;

public class UbicacionesAdapter extends BaseAdapter implements Filterable {
    private Context context;
    TextView tvTitulo;
    TextView tvSubTitulo;
    ImageView imgImagenes;


    private List<UbicacionesModel> ubicacionesModelsList;

    public List<UbicacionesModel> ubicacionesModels;


    public UbicacionesAdapter(Context context, List<UbicacionesModel> ubicacionesModelsArray) {
        this.context = context;
        this.ubicacionesModels = ubicacionesModelsArray;
        this.ubicacionesModelsList = ubicacionesModelsArray;
    }


    @Override
    public int getCount() {
        return ubicacionesModels.size();
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

        tvTitulo.setText(ubicacionesModels.get(position).getUbication_de_almacen());
        tvSubTitulo.setText(ubicacionesModels.get(position).getCantidades());
        imgImagenes.setImageResource(ubicacionesModels.get(position).getImage());
        return v;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.count = ubicacionesModelsList.size();
                    filterResults.values = ubicacionesModelsList;

                }else{
                    List<UbicacionesModel> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(UbicacionesModel itemsModel:ubicacionesModelsList){

                        if(itemsModel.getUbication_de_almacen().toLowerCase().contains(searchStr)
                                || itemsModel.getUbication_de_almacen().toLowerCase().startsWith(searchStr)){

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

                ubicacionesModels = (List<UbicacionesModel>) results.values;
                notifyDataSetChanged();
            }
        };

        return filter;
    }
}
