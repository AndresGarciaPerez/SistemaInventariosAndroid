package com.electivaIII.sistemainventario.Models;

import java.util.ArrayList;
import java.util.List;

public class User {
    public int id;
    public String first_name;
    public String last_name;
    public String username;
    public String email;
    public boolean status;
    public int role_id;
    public String role_name;


    public User() {

    }
    public User(int id, String first_name,
                String last_name, String username, String email,
                boolean status, int role_id, String role_name) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.email = email;
        this.status = status;
        this.role_id = role_id;
        this.role_name = role_name;
    }

    public boolean isStatus() {
        return status;
    }

    public int getRole_id() {
        return role_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public int getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
