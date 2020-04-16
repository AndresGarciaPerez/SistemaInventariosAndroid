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

import com.electivaIII.sistemainventario.Models.AccesoriosRepuestosModel;
import com.electivaIII.sistemainventario.R;

import java.util.ArrayList;
import java.util.List;

public class AccesoriosAdapter extends BaseAdapter implements Filterable {
    private Context context;

    private List<AccesoriosRepuestosModel> accesoriosRepuestosModelsList;

    public List<AccesoriosRepuestosModel> itemsAccesorioModel;
    TextView tvTitulo;
    TextView tvSubTitulo;
    ImageView imgImagenes;



    public AccesoriosAdapter(Context context, List<AccesoriosRepuestosModel> modelList) {
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

        tvTitulo.setText(accesoriosRepuestosModelsList.get(position).getName());
        tvSubTitulo.setText(accesoriosRepuestosModelsList.get(position).getItem());
        imgImagenes.setImageResource(accesoriosRepuestosModelsList.get(position).getImage());
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
                    List<AccesoriosRepuestosModel> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(AccesoriosRepuestosModel itemsModel:itemsAccesorioModel){

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

                accesoriosRepuestosModelsList = (List<AccesoriosRepuestosModel>) results.values;
                notifyDataSetChanged();
            }
        };

        return filter;
    }
}
