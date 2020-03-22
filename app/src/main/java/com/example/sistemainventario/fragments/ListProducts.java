package com.example.sistemainventario.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sistemainventario.Adaptador;
import com.example.sistemainventario.Models.ProductsModel;
import com.example.sistemainventario.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListProducts extends Fragment {
    ListView lista;
    String[] nameProduct;
    String[] quantity;
    String[] categorie;
    String[] descripction;
    List<ProductsModel> productModelList = new ArrayList<>();
    Enlace enlace;

    public ListProducts() {
        // Required empty public constructor
    }

    public interface Enlace {
        void enviarData(String product, String quantity, String categorie, String description, int idImage);
        //void enviarData(String product, String quantity, int idImage);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_products, container, false);

        lista = (ListView)v.findViewById(R.id.lvLista);
        nameProduct =getResources().getStringArray(R.array.nombreProducto);
        categorie =getResources().getStringArray(R.array.categoria);
        quantity =getResources().getStringArray(R.array.cantidad);
        descripction =getResources().getStringArray(R.array.descripcion);
        int[]imagen = {R.drawable.ejemplo2,R.drawable.ejemplo1};

        ProductsModel products = new ProductsModel();

        if (products.getProducts().size() == 0) {

            for (int i= 0; i< imagen.length; i++){
                ProductsModel productsModel = new ProductsModel(
                        nameProduct[i],
                        quantity[i],
                        categorie[i],
                        descripction[i],
                        imagen[i]);
                productModelList.add(productsModel);
            }
            products.setProducts(productModelList);

        } else {

            productModelList = products.getProducts();

        }


        Adaptador adapte = new Adaptador(getActivity().getApplicationContext(),categorie,quantity,imagen);
        lista.setAdapter(adapte);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                enlace.enviarData(productModelList.get(position).getProductName(),
                        productModelList.get(position).getProductQuantity(),
                        productModelList.get(position).getProductCategories(),
                        productModelList.get(position).getProductDescripcion(),
                        productModelList.get(position).getImages());

                if (position==0){

                } else {

                }


            }
        });



            return v;

    }

    @Override
    public void onAttach(Context context) {
        enlace=(Enlace)context;
        super.onAttach(context);
    }

}
