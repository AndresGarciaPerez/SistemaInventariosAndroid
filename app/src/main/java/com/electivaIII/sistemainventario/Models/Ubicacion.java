package com.electivaIII.sistemainventario.Models;

public class Ubicacion {

    String name = "";
    String address = "";
    double lat = 0.0;
    double lon = 0.0;

    public Ubicacion() {
    }

    public Ubicacion(String name, String address, double lat, double lon) {
        this.name = name;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
