package com.electivaIII.sistemainventario.Models;

import java.util.ArrayList;
import java.util.List;

public class UbicacionesModel {

    private String ubication_de_almacen;
    private String cantidades;
    private Double latitud, longitud;
    private int image;
    private static List ubicaciones = new ArrayList<>();

    public UbicacionesModel() {

    }

    public UbicacionesModel(String almacen, String cantidades, int image,
                            Double latitud, Double longitud) {

        this.ubication_de_almacen = almacen;
        this.cantidades = cantidades;
        this.image = image;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getUbication_de_almacen() {
        return ubication_de_almacen;
    }

    public String getCantidades() {
        return cantidades;
    }

    public Double getLatitud() {
        return latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public int getImage() {
        return image;
    }

    public static List getUbicaciones() {
        return ubicaciones;
    }
}
