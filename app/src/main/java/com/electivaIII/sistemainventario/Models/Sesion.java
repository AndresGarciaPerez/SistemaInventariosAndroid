package com.electivaIII.sistemainventario.Models;

public class Sesion {

    public static String access_token;
    public String token_type;
    public String refresh_token;
    public int expires_in;

    public Sesion(){

    }

    public Sesion(String access_token, String token_type, String refresh_token, int expires_in) {
        this.access_token = access_token;
        this.token_type = token_type;
        this.refresh_token = refresh_token;
        this.expires_in = expires_in;
    }


    public String getToken() {
        return access_token;
    }

}
