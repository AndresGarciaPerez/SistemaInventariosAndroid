package com.electivaIII.sistemainventario.Models;

public class LatLong {

    private static Double lat, lon;
    private static String ubication;
    public LatLong() {

    }
    public LatLong(Double lat, Double lon, String ubication) {
        this.lat = lat;
        this.lon = lon;
        this.ubication = ubication;
    }

    public static Double getLat() {
        return lat;
    }

    public static Double getLon() {
        return lon;
    }

    public static String getUbication() {
        return ubication;
    }
}
