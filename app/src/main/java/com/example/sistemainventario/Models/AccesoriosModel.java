package com.example.sistemainventario.Models;

import java.util.ArrayList;
import java.util.List;

public class AccesoriosModel {
    private String name;
    private String item;
    private int image;
    private static List accesorios = new ArrayList<>();

    public AccesoriosModel(String name, String item, int image) {
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

    public int getImage() {
        return image;
    }

    public static void setAccesorios(List accesorios) {
        AccesoriosModel.accesorios = accesorios;
    }

    public static List getAccesorios() {
        return accesorios;
    }
}
