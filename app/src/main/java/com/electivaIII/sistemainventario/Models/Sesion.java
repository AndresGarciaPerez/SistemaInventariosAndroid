package com.electivaIII.sistemainventario.Models;

import java.util.ArrayList;
import java.util.List;

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

    private static List users = new ArrayList<User>();

    public String getToken() {
        return access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public static List getUsers() {
        return users;
    }

    public static void setUsers(List users) {
        Sesion.users = users;
    }
}
