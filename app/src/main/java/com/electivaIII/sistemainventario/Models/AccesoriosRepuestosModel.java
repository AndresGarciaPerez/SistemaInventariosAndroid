package com.electivaIII.sistemainventario.Models;

import java.util.ArrayList;
import java.util.List;

public class AccesoriosRepuestosModel {
    private String name;
    private String item;
    private String image;
    private static List accesorios = new ArrayList<>();

    public AccesoriosRepuestosModel(String name, String item, String image) {
        this.name = name;
        this.item = item;
        this.image = image;
    }

    public String getName() {
        return name;
    }


    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getImage() {
        return image;
    }

    public static void setAccesorios(List accesorios) {
        AccesoriosRepuestosModel.accesorios = accesorios;
    }

    public static List getAccesorios() {
        return accesorios;
    }
}
